package org.jpos.rest.controllers;

import org.hibernate.query.Query;
import org.jpos.ee.DB;
import org.jpos.rest.utilties.Config;
import org.json.JSONObject;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigInteger;
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
    public Response vaInquiry(@Context ContainerRequestContext containerRequestContext, String requestBody)
    {
        Map<String, Object> response = new HashMap<>();
        try
        {
            String authorizationHeader = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
            if (!isCompleteHeader(authorizationHeader))
            {
                response.put("rc", Config.Code_Header_not_complete);
                response.put("message", Config.Desc_Header_not_complete);
                return Response.accepted(response).build();
            }
            else if (!isTokenValid(authorizationHeader))
            {
                response.put("rc", Config.Code_Token_not_valid);
                response.put("message", Config.Desc_Token_not_valid);
                return Response.accepted(response).build();
            }

            JSONObject jsonObject = new JSONObject(requestBody);
            try (DB db = new DB())
            {
                db.open();
                db.beginTransaction();

                String sql = "INSERT INTO public.\"references\" (referenceNumber,clientId) VALUES (:referenceNumber, :clientId)";
                Query query = db.session().createSQLQuery(sql);
                query.setParameter("referenceNumber", jsonObject.get("reference_number"));
                query.setParameter("clientId", jsonObject.get("client_id"));
                query.executeUpdate();
                db.commit();

                String hql = "FROM Clients where " +
                        "clientId = :clientId ";
                db.beginTransaction();
                query = db.session().createQuery(hql);
                query.setParameter("clientId", jsonObject.get("client_id"));
                if (query.list().size() == 0)
                {
                    response.put("rc", Config.Code_Client_ID_not_found);
                    response.put("message", Config.Desc_Client_ID_not_found);
                    return Response.accepted(response).build();
                }
                db.commit();

                hql = "FROM VirtualAccounts where " +
                        "virtualAccountNo = :virtualAccountNo ";
                db.beginTransaction();
                query = db.session().createQuery(hql);
                query.setParameter("virtualAccountNo", jsonObject.get("virtual_account"));
                if (query.list().size() == 0)
                {
                    response.put("rc", Config.Code_VA_not_found);
                    response.put("message", Config.Desc_VA_not_found);
                    return Response.accepted(response).build();
                }
                db.commit();

                List results = query.list();
                response.put("rc", Config.Code_Success);
                response.put("message", Config.Desc_Success);
                JSONObject virtualAccounts = new JSONObject(results.get(0));
                Map<String, Object> data = new HashMap<>();
                data.put("account_name", virtualAccounts.getString("virtualAccountName"));
                response.put("data", data);
                return Response.ok(response).status(Response.Status.OK).build();
            }

        }
        catch (Exception err)
        {
            response.put("rc", Config.Code_General_Error);
            response.put("message", Config.Desc_General_Error);
            return Response.accepted(response).build();
        }

    }

    @POST
    @Path("/payment")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response vaPayment(@Context ContainerRequestContext containerRequestContext, String requestBody)
    {
        Map<String, Object> response = new HashMap<>();
        try
        {
            String authorizationHeader = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
            if (!isCompleteHeader(authorizationHeader))
            {
                response.put("rc", Config.Code_Header_not_complete);
                response.put("message", Config.Desc_Header_not_complete);
                return Response.accepted(response).build();
            }
            else if (!isTokenValid(authorizationHeader))
            {
                response.put("rc", Config.Code_Token_not_valid);
                response.put("message", Config.Desc_Token_not_valid);
                return Response.accepted(response).build();
            }

            JSONObject jsonObject = new JSONObject(requestBody);
            String transactionNumber = jsonObject.get("virtual_account") +
                    "" + jsonObject.get("reference_number") +
                    "" + System.currentTimeMillis();

            try (DB db = new DB())
            {
                db.open();
                db.beginTransaction();
                String sql = "INSERT INTO public.\"references\" (referenceNumber,clientId,transactionNumber) " +
                        "VALUES (:referenceNumber, :clientId, :transactionNumber)";
                Query query = db.session().createSQLQuery(sql);
                query.setParameter("referenceNumber", jsonObject.get("reference_number"));
                query.setParameter("clientId", jsonObject.get("client_id"));
                query.setParameter("transactionNumber", transactionNumber);
                query.executeUpdate();
                db.commit();

                db.beginTransaction();
                sql = "INSERT INTO public.payments (amount,note,clientId,transactionNumber) " +
                        "VALUES (:amount, :note, :clientId, :transactionNumber)";
                query = db.session().createSQLQuery(sql);
                query.setParameter("amount", jsonObject.get("amount"));
                query.setParameter("note", jsonObject.get("note"));
                query.setParameter("clientId", jsonObject.get("client_id"));
                query.setParameter("transactionNumber", transactionNumber);
                query.executeUpdate();
                db.commit();

                response.put("rc", Config.Code_Success);
                response.put("message", Config.Desc_Success);
                Map<String, Object> data = new HashMap<>();
                data.put("transaction_number", transactionNumber);
                response.put("data", data);
                return Response.ok(response).status(Response.Status.OK).build();
            }
        }
        catch (Exception err)
        {
            response.put("rc", Config.Code_General_Error);
            response.put("message", Config.Desc_General_Error);
            return Response.accepted(response).build();
        }
    }

    private boolean isCompleteHeader(String authorizationHeader)
    {
        return authorizationHeader != null && authorizationHeader.toLowerCase()
                .startsWith(Config.AUTHENTICATION_BEARER.toLowerCase() + " ");
    }

    private boolean isTokenValid(String authorizationHeader)
    {
        BigInteger count;
        String token = authorizationHeader.substring(Config.AUTHENTICATION_BEARER.length()).trim();
        try (DB db = new DB())
        {
            db.open();
            db.beginTransaction();
            String sql = "SELECT COUNT(*) FROM tokens where token=:token and timestamp >= :timestamp";
            Query query = db.session().createSQLQuery(sql);
            query.setParameter("token", token);
            query.setParameter("timestamp", System.currentTimeMillis() - Config.expires_in);
            count = (BigInteger) query.uniqueResult();
        }
        return count.intValue() > 0;
    }


}
