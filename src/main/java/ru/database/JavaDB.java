package ru.database;

import java.sql.*;

public class JavaDB {

    private static final String url= "jdbc:mysql://localhost/mysql?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String username = "root";
    private static final String password = "pass";

    public static void main(String[] args) {

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch(ClassNotFoundException c){
            System.out.println("Драйвер не найден");
        }

        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement();) {


            // Получить DatabaseMetaData объект и показать информацию о соединении
            DatabaseMetaData dma = connection.getMetaData();
            System.out.println("Connected to: " + dma.getURL());
            System.out.println("Driver: " + dma.getDriverName());
            System.out.println("Version: " + dma.getDriverVersion());
            System.out.println();

            statement.execute("INSERT INTO usersdb.users (firstname, age) VALUES (\"Mike\", 29)");
            //Пробуем пакетное выполнение команд
            statement.addBatch("INSERT INTO usersdb.users (firstname, age) VALUES (\"Michelle\", 25)");
            statement.addBatch("INSERT INTO usersdb.users (firstname, age) VALUES (\"Lucy\", 26)");
            statement.addBatch("INSERT INTO usersdb.users (firstname, age) VALUES (\"Naya\", 18)");
            statement.executeBatch();
            statement.clearBatch();

            ResultSet resultSet= statement.executeQuery("SELECT * FROM usersdb.users");

            while (resultSet.next()){
                int id;
                String firstname;
                int age;
                id = resultSet.getInt("id");
                firstname = resultSet.getString(2);
                age = resultSet.getInt("age");
                System.out.println("Person: " + "\t" + id + "\t" + firstname + "\t" + age);

            }
        }
        catch (SQLException s){
            System.out.println("***SQLException caught***");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
