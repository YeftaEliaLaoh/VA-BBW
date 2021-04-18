package org.jpos.rest.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Clients implements Serializable
{
    private static final long serialVersionUID = -8259326520155524014L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "SERIAL")
    private int id;
    private String clientId;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getClientId()
    {
        return clientId;
    }

    public void setClientId(String clientId)
    {
        this.clientId = clientId;
    }

    @Override
    public String toString()
    {
        return "VirtualAccounts{" +
                "id=" + id +
                ", clientId='" + clientId + '\'' +
                '}';
    }
}
