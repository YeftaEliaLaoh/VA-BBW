package org.jpos.rest.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class VirtualAccounts implements Serializable
{
    private static final long serialVersionUID = -8259326520155524014L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "SERIAL")
    private int id;
    private String clientId;
    private String virtualAccountNo;
    private String virtualAccountName;

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

    public String getVirtualAccountNo()
    {
        return virtualAccountNo;
    }

    public void setVirtualAccountNo(String virtualAccountNo)
    {
        this.virtualAccountNo = virtualAccountNo;
    }

    public String getVirtualAccountName()
    {
        return virtualAccountName;
    }

    public void setVirtualAccountName(String virtualAccountName)
    {
        this.virtualAccountName = virtualAccountName;
    }

    @Override
    public String toString()
    {
        return "VirtualAccounts{" +
                "id=" + id +
                ", clientId='" + clientId + '\'' +
                ", virtualAccountNo='" + virtualAccountNo + '\'' +
                ", virtualAccountName='" + virtualAccountName + '\'' +
                '}';
    }
}
