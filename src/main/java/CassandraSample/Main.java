/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CassandraSample;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 *
 * @author microchel
 */
public class Main {
    
    public void addUser(Session session, String firstName, String secondName, ZonedDateTime age) {
        final PreparedStatement insertUser = session.prepare("INSERT INTO user_table (user_id, first_name, second_name, age) VALUES (?,?,?,?);");
        final BoundStatement bindStatementInsertUser = insertUser.bind(UUID.randomUUID(), firstName, secondName, age);
        session.execute(bindStatementInsertUser);
    }
    
    public void addUserWithoutAge(Session session, String firstName, String secondName) {
        final PreparedStatement insertUserStatement = session.prepare("INSERT INTO user_table (user_id, first_name, second_name) VALUES (?,?,?);");
        final BoundStatement bindedStatement = insertUserStatement.bind(UUID.randomUUID(), firstName, secondName);
        session.execute(bindedStatement);
    }
    
    public void addUserByStatementObject(Session session, String firstName, String secondName) {
        Statement addUserStatement = QueryBuilder.insertInto("user_table")
                .value("first_name", firstName)
                .value("second_name", secondName)
                .value("user_id", UUID.randomUUID());
        session.execute(addUserStatement);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Cluster cluster;
        Session session;
        Main cas = new Main();
        
        cluster = Cluster.builder()
                .addContactPoint("34.105.198.105")
                .build();
        
        session = cluster.connect("server_auth");
        
        // final ZonedDateTime birthdayTimestamp = ZonedDateTime.parse("2000-05-02T10:00:00.433395762Z");
        // cas.addUserWithoutAge(session, "Ilya", "Tsuprun");
        
        // ResultSet result = session.execute("INSERT INTO server_auth.user_table (user_id, first_name, second_name, age) VALUES (uuid(), 'Ilya', 'Savenko', 20);");
        
        // ResultSet result = session.execute("SELECT * FROM user_table");
        
        cas.addUserByStatementObject(session, "Max", "Fisher");
        
        Statement allUsersStatement = QueryBuilder.select().all().from("server_auth", "user_table");
        ResultSet result = session.execute(allUsersStatement);
        for(Row result_row: result) {
            System.out.format("%s %s %s\n", result_row.getUUID("user_id"), result_row.getString("first_name"), result_row.getString("second_name"));
        }
                
    }
    
}
