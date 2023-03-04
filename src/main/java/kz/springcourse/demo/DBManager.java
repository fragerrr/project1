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

    public static List<Book> getBooks(){
        List<Book> book = new ArrayList<>();

        try{
            PreparedStatement statement = connection.prepareStatement("" +
                    "SELECT * FROM book");

            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                book.add(new Book(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("author"),
                        rs.getInt("year"),
                        rs.getInt("took_user")
                ));
            }

            statement.close();
        } catch (SQLException e){
            e.printStackTrace();
        }

        return book;
    }

    public static Book getBook(Integer id){
        Book book = null;

        try{
            PreparedStatement statement = connection.prepareStatement("" +
                    "SELECT * FROM book " +
                    "WHERE id = ? ");

            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                book = new Book(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("author"),
                        rs.getInt("year"),
                        rs.getInt("took_user")
                );
            }

            statement.close();
        } catch (SQLException e){
            e.printStackTrace();
        }

        return book;
    }

    public static boolean addBook(Book book){
        int rows = 0;
        try{
            PreparedStatement statement = connection.prepareStatement("" +
                    "INSERT INTO book(name, author, year) values(?,?,?)");

            statement.setString(1, book.getName());
            statement.setString(2, book.getAuthor());
            statement.setInt(3, book.getYear());
            rows = statement.executeUpdate();


            statement.close();
        } catch (SQLException e){
            e.printStackTrace();
        }

        return rows > 0;
    }

    public static boolean updateBook(Book book, Integer id){
        int rows = 0;
        try{
            PreparedStatement statement = connection.prepareStatement("" +
                    "UPDATE book SET name = ?, author = ?, year = ? WHERE id = ?");

            statement.setString(1, book.getName());
            statement.setString(2, book.getAuthor());
            statement.setInt(3, book.getYear());
            statement.setInt(4, id);

            rows = statement.executeUpdate();

            statement.close();
        } catch (SQLException e){
            e.printStackTrace();
        }

        return rows > 0;
    }

    public static boolean deleteBook(Integer id){
        int rows = 0;
        try{
            PreparedStatement statement = connection.prepareStatement("" +
                    "DELETE from book where id = ?");


            statement.setInt(1, id);

            rows = statement.executeUpdate();

            statement.close();
        } catch (SQLException e){
            e.printStackTrace();
        }

        return rows > 0;
    }

    public static ArrayList<Book> getBooksByUserId(Integer id){
        ArrayList<Book> books = new ArrayList<>();

        try{
            PreparedStatement statement = connection.prepareStatement("" +
                    "SELECT * FROM book WHERE took_user=?");

            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                books.add(
                        new Book(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("author"),
                                rs.getInt("year"),
                                rs.getInt("took_user")
                        )
                );
            }


            statement.close();
        } catch (SQLException e){
            e.printStackTrace();
        }

        return books;
    }

    public static boolean updateTookUser(Integer book_id, Integer user_id){
        int rows = 0;
        try{
            PreparedStatement statement = connection.prepareStatement("" +
                    "UPDATE book SET took_user = ? WHERE id = ?");

            statement.setInt(1, user_id);
            statement.setInt(2, book_id);

            rows = statement.executeUpdate();

            statement.close();
        } catch (SQLException e){
            e.printStackTrace();
        }

        return rows > 0;
    }

    public static boolean deleteTookUser(Integer book_id){
        int rows = 0;
        try{
            PreparedStatement statement = connection.prepareStatement("" +
                    "UPDATE book SET took_user = null WHERE id = ?");

            statement.setInt(1, book_id);

            rows = statement.executeUpdate();

            statement.close();
        } catch (SQLException e){
            e.printStackTrace();
        }

        return rows > 0;
    }


}
