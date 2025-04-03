package com.src.mn.util;

import com.src.mn.util.annotations.Field;
import com.src.mn.util.annotations.Table;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TableCreator {

    public static void createTableIfNotExists(Class<?> clazz, Connection connection) throws SQLException {
        StringBuilder createTableSQL = new StringBuilder();

        // Check if the class has the @Table annotation
        if (clazz.isAnnotationPresent(Table.class)) {
            Table table = clazz.getAnnotation(Table.class);
            createTableSQL.append("CREATE TABLE IF NOT EXISTS ").append(table.name()).append(" (");

            // Get the fields of the class
            java.lang.reflect.Field[] fields = clazz.getDeclaredFields();
            for (java.lang.reflect.Field field : fields) {
                // Check if the field has the @Field annotation
                if (field.isAnnotationPresent(Field.class)) {
                    Field fieldAnnotation = field.getAnnotation(Field.class);
                    createTableSQL.append(fieldAnnotation.name())
                                  .append(" ")
                                  .append(fieldAnnotation.type());

                    // Add PRIMARY KEY constraint if necessary
                    if (fieldAnnotation.isPrimaryKey()) {
                        createTableSQL.append(" PRIMARY KEY");
                    }
                    // Add UNIQUE constraint if necessary
                    if (fieldAnnotation.isUnique()) {
                        createTableSQL.append(" UNIQUE");
                    }
                    createTableSQL.append(", ");
                }
            }

            // Remove the last comma
            if (createTableSQL.length() > 0 && createTableSQL.charAt(createTableSQL.length() - 2) == ',') {
                createTableSQL.setLength(createTableSQL.length() - 2); // Remove the last comma
            }
            createTableSQL.append(");");

            // Execute the SQL statement to create the table
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate(createTableSQL.toString());
                System.out.println("Table created or already exists: " + table.name());
            }
        }
    }
}
