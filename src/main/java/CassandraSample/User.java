/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CassandraSample;

import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import java.util.UUID;

/**
 *
 * @author microchel
 */
@Table(name="user_table")
public class User {
    
    @ClusteringColumn(0)
    @Column(name="first_name")
    private String firstName;
    
    @ClusteringColumn(1)
    @Column(name="second_name")
    private String secondName;

    @PartitionKey
    @Column(name="user_id")
    private UUID userId;

    public User(String firstName, String secondName, UUID userId) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "User{" + "firstName=" + firstName + ", secondName=" + secondName + ", userId=" + userId + '}';
    }
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
    
    
    
}
