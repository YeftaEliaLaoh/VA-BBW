package org.jpos.rest.controllers;

import org.hibernate.query.Query;
import org.jpos.ee.DB;
import org.jpos.rest.utilties.Config;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Path("/auth")
public class Auth
{

    @POST
    @Path("/token")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response authenticateUser(ContainerRequestContext requestContext)
    {
        try
        {
            Map<String, Object> response = new HashMap<>();

            // Get the Authorization header from the request
            String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

            // Validate the Authorization header
            if (isCompleteHeader(authorizationHeader))
            {
                UUID uuid = UUID.randomUUID();

                // Authorization: Basic base64credentials
                String base64Credentials = authorizationHeader.substring(Config.AUTHENTICATION_BASIC.length()).trim();
                byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
                String credentials = new String(credDecoded, StandardCharsets.UTF_8);
                // credentials = username:password
                final String[] values = credentials.split(":", 2);
                try (DB db = new DB())
                {
                    db.open();
                    db.beginTransaction();
                    String hql = "FROM Users where userName =:userName and password =:password";
                    Query query = db.session().createQuery(hql);
                    query.setParameter("userName", values[0]);
                    query.setParameter("password", values[1]);

                    if (query.list().size() == 0)
                    {
                        response.put("rc", Config.Code_Header_not_complete);
                        response.put("message", Config.Desc_Header_not_complete);
                        return Response.accepted(response).build();
                    }

                    String sql = "INSERT INTO public.tokens (token,\"timestamp\") VALUES (:token, :timestamp)";
                    query = db.session().createSQLQuery(sql);
                    query.setParameter("token", uuid);
                    query.setParameter("timestamp", System.currentTimeMillis());
                    query.executeUpdate();
                    db.commit();
                }
                response.put("auth_token", uuid);
                response.put("token_type", "Bearer");
                response.put("expires_in", Config.expires_in);
            }
            else
            {
                response.put("rc", Config.Code_Header_not_complete);
                response.put("message", Config.Desc_Header_not_complete);
                return Response.accepted(response).build();
            }

            return Response.ok(response, MediaType.APPLICATION_JSON).status(Response.Status.OK).build();

        }
        catch (Exception e)
        {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }

    private boolean isCompleteHeader(String authorizationHeader)
    {
        return authorizationHeader != null && authorizationHeader.toLowerCase()
                .startsWith(Config.AUTHENTICATION_BASIC.toLowerCase() + " ");
    }

}
