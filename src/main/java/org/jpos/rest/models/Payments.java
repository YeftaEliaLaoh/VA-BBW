package org.jpos.rest.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Payments implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "SERIAL")
    private int id;
    private String amount;
    private String note;
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

    public String getAmount()
    {
        return amount;
    }

    public void setAmount(String amount)
    {
        this.amount = amount;
    }

    public String getNote()
    {
        return note;
    }

    public void setNote(String note)
    {
        this.note = note;
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
