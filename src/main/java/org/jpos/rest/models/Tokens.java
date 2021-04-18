package org.jpos.rest.models;

import javax.persistence.*;

@Entity
@Table(name = "tokens", schema = "public")
public class Tokens
{
    @Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tokens_seq_gen")
    //@SequenceGenerator(name = "tokens_seq_gen", sequenceName = "tokens_id_seq", allocationSize = 1)
    //@Column(insertable = false, updatable = false, columnDefinition = "integer DEFAULT nextval('tokens_id_seq')")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false, columnDefinition = "SERIAL")
    int id;
    String token;
    Long timestamp;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public Long getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(Long timestamp)
    {
        this.timestamp = timestamp;
    }

}
