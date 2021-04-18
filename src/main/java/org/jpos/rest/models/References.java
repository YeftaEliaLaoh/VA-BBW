package org.jpos.rest.models;

import javax.persistence.*;

@Entity
@Table(name = "\"references\"", schema = "public")
public class References
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "SERIAL")
    private int id;
    private String referenceNumber;
    private String clientId;
    private String transactionNumber;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getReferenceNumber()
    {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber)
    {
        this.referenceNumber = referenceNumber;
    }

    public String getClientId()
    {
        return clientId;
    }

    public void setClientId(String clientId)
    {
        this.clientId = clientId;
    }

    public String getTransactionNumber()
    {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber)
    {
        this.transactionNumber = transactionNumber;
    }

}
