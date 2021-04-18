package org.jpos.rest.controllers;

import org.hibernate.query.Query;
import org.jpos.ee.DB;
import org.jpos.rest.models.VirtualAccounts;
import org.json.JSONObject;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/va")
public class Va
{
    @POST
    @Path("/inquiry")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response vaInquiry(@Context ContainerRequestContext request, String requestBody)
    {
        try
        {
            JSONObject jsonObject = new JSONObject(requestBody);

            try (DB db = new DB())
            {
                db.open();
                db.beginTransaction();
                String hql = "FROM VirtualAccounts where " +
                        "clientId = :clientId " +
                        "and referenceNumber = :referenceNumber " +
                        "and virtualAccountNo = :virtualAccountNo ";
                Query query = db.session().createQuery(hql);
                query.setParameter("clientId", jsonObject.get("client_id"));
                query.setParameter("referenceNumber", jsonObject.get("reference_number"));
                query.setParameter("virtualAccountNo", jsonObject.get("virtual_account"));

                if (query.list().size() == 0)
                {
                    return Response.status(Response.Status.UNAUTHORIZED).build();
                }
                else
                {
                    List<VirtualAccounts> virtualAccounts = query.getResultList();
                    //StringBuilder stringBuilder = new StringBuilder();
                    Map<String, Object> response = new HashMap<>();
                    response.put("rc", "000");
                    response.put("message", "Success");
                    Map<String, Object> data = new HashMap<>();
                    String virtualAccountName = virtualAccounts.get(0).getVirtualAccountName();
                    //virtualAccounts.stream().forEach(virtualAccount -> stringBuilder.append(virtualAccount.getVirtualAccountName()));
                    data.put("virtualAccounts", virtualAccountName);
                    response.put("data", data);
                    return Response.ok(response, MediaType.APPLICATION_JSON).status(Response.Status.OK).build();
                }
            }
        }
        catch (Exception err)
        {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

    }
}
