package com.myllena.locadora.repository;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseTest {

    static Connection connection;

    @BeforeAll // ANTES de todos os testes
    static void SetUpDB() throws Exception {
        connection = DriverManager
                .getConnection("jdbc:h2:mem:locadora","myllena","");
        connection.createStatement().execute("CREATE TABLE users(id INT, name VARCHAR)");
    }

    @BeforeEach // ANTES de CADA UM
    void insertUser() throws Exception{
        connection.createStatement().execute("INSERT INTO users(id, name) values(1, 'Myllena')");
    }

    @Test
    void testUserExist()throws Exception{
        var result = connection.createStatement()
                .executeQuery("SELECT * FROM users where id = 1");
        Assertions.assertTrue(result.next());
    }

    @AfterAll // DEPOIS de tudo
    static void closeDB() throws Exception{
        connection.close();
    }


}
