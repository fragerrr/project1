package kz.springcourse.demo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBManager {
    private static final Connection connection;

    static{
        try {
            Class.forName("org.postgresql.Driver");

            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/spring",
                    "postgres", "postgres");

        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static List<Person> getUsers(){
        List<Person> people = new ArrayList<>();

        try{
            PreparedStatement statement = connection.prepareStatement("" +
                    "SELECT * FROM people");

            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                people.add(new Person(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age")
                ));
            }

            statement.close();
        } catch (SQLException e){
            e.printStackTrace();
        }

        return people;
    }

    public static Person getUser(Integer id){
        Person person = null;

        try{
            PreparedStatement statement = connection.prepareStatement("" +
                    "SELECT * FROM people " +
                    "WHERE id = ? ");

            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                person = new Person(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age")
                );
            }

            statement.close();
        } catch (SQLException e){
            e.printStackTrace();
        }

        return person;
    }

    public static boolean addUser(Person person){
        int rows = 0;
        try{
            PreparedStatement statement = connection.prepareStatement("" +
                    "INSERT INTO people(name, age) values(?,?)");

            statement.setString(1, person.getName());
            statement.setInt(2, person.getAge());

            rows = statement.executeUpdate();


            statement.close();
        } catch (SQLException e){
            e.printStackTrace();
        }

        return rows > 0;
    }

    public static boolean updateUser(Person person, Integer id){
        int rows = 0;
        try{
            PreparedStatement statement = connection.prepareStatement("" +
                    "UPDATE people SET name = ?, age = ? WHERE id = ?");

            statement.setString(1, person.getName());
            statement.setInt(2, person.getAge());

            statement.setInt(3, id);

            rows = statement.executeUpdate();

            statement.close();
        } catch (SQLException e){
            e.printStackTrace();
        }

        return rows > 0;
    }

    public static boolean deleteUser(Integer id){
        int rows = 0;
        try{
            PreparedStatement statement = connection.prepareStatement("" +
                    "DELETE from people where id = ?");


            statement.setInt(1, id);

            rows = statement.executeUpdate();

            statement.close();
        } catch (SQLException e){
            e.printStackTrace();
        }

        return rows > 0;
    }
}
