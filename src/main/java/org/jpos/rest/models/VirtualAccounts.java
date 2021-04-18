package org.jpos.rest.models;

import javax.persistence.*;

@Entity
//@Table(name = "virtualaccounts", schema = "public")
public class VirtualAccounts
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false, columnDefinition = "SERIAL")
    int id;
    String clientId;
    String referenceNumber;
    String virtualAccountNo;
    String virtualAccountName;

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

    public String getReferenceNumber()
    {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber)
    {
        this.referenceNumber = referenceNumber;
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

}
