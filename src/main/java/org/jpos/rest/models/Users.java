package org.jpos.rest.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users", schema = "public")
public class Users implements Serializable
{
    private static final long serialVersionUID = -8259326520155524014L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String userName;
    String password;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getUsername()
    {
        return userName;
    }

    public void setUsername(String userName)
    {
        this.userName = userName;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}
