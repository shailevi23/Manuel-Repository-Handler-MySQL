package org.example.ORM;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Anottations.AutoIncrement;
import org.example.Anottations.NotNull;
import org.example.Anottations.PrimaryKey;
import org.example.Anottations.Unique;
import org.example.Utils.Utils;

import java.lang.reflect.Field;
import java.util.Map;

public class RepoLogic<T> {

    private final Class<T> clz;

    private static Logger logger = LogManager.getLogger(RepoLogic.class.getName());

    public RepoLogic(Class<T> clz) {
        this.clz = clz;
    }

    public String selectAllQueryLogic() {
        logger.info("creating SELECT * FROM " + clz.getSimpleName() + " Query");
        return "SELECT * FROM " + clz.getSimpleName().toLowerCase() + ";";
    }

    public String selectByIdQuery(String fieldName, Integer value) {
        logger.info("creating SELECT * FROM " + clz.getSimpleName() + " WHERE " + fieldName + " = " + value);
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(clz.getSimpleName().toLowerCase());
        sb.append(" WHERE ").append(fieldName);
        sb.append("= ").append(value.toString());
        sb.append(";");
        return sb.toString();
    }

    public String selectByManyFiltersQuery(Map<String, Object> filters) {
        logger.info("creating SELECT * FROM " + clz.getSimpleName() + " WHERE ");
        StringBuilder sb = new StringBuilder();
        Gson gson = new Gson();
        sb.append("SELECT * FROM ");
        sb.append(clz.getSimpleName().toLowerCase());
        sb.append(" WHERE ");

        for (Map.Entry<String, Object> entry : filters.entrySet()) {
            sb.append(entry.getKey());
            sb.append(" = ");
            if (Utils.map.containsKey(entry.getValue().getClass())) {
                sb.append(entry.getValue());
                sb.append(" AND ");
            } else if (entry.getValue().getClass().equals(String.class)) {
                sb.append("'");
                sb.append(entry.getValue());
                sb.append("' AND ");
            } else {
                sb.append("'");
                sb.append(gson.toJson(entry.getValue()));
                sb.append("' AND ");
            }
        }
        sb.replace(sb.length() - 4, sb.length(), ";");
        return sb.toString();
    }

    public String findObjQuery(Object object) {
        logger.info("creating SELECT * FROM " + clz.getSimpleName());
        StringBuilder sb = new StringBuilder();
        Gson gson = new Gson();
        sb.append("SELECT * FROM ");
        sb.append(clz.getSimpleName().toLowerCase());
        sb.append(" WHERE ");

        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                if (!field.isAnnotationPresent(AutoIncrement.class)) {
                    sb.append(field.getName());
                    sb.append(" = ");
                    if (Utils.map.containsKey(field.getType())) {
                        sb.append(field.get(object));
                        sb.append(" AND ");
                    } else if (field.get(object) instanceof String) {
                        sb.append("'");
                        sb.append(field.get(object));
                        sb.append("' AND ");
                    }
                    else{
                        sb.append("'");
                        sb.append(gson.toJson(field.get(object)));
                        sb.append("' AND ");
                    }
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        sb.replace(sb.length() - 5, sb.length(), ";");

        return sb.toString();
    }


    String insertObjectQuery(T object) {
        StringBuilder sb = new StringBuilder();
        logger.info("creating INSERT INTO " + clz.getSimpleName() + " Query");
        sb.append("INSERT INTO ");
        sb.append(clz.getSimpleName().toLowerCase());
        sb.append(" VALUES (");

        reflectionHandlerHelper(object, sb);

        sb.deleteCharAt(sb.length() - 1);
        sb.append(");");
        return sb.toString();
    }


    String createTableQuery() {
        StringBuilder sb = new StringBuilder();
        logger.info("Creating table for " + clz.getSimpleName());
        sb.append("CREATE TABLE ");
        sb.append(clz.getSimpleName().toLowerCase());
        sb.append(" (\n");

        AnnotationsHandler annotationHandler = new AnnotationsHandler(0, 0, null);

        for (Field field : clz.getDeclaredFields()) {
            sb.append(field.getName());
            sb.append(" ");
            sb.append(getMySQLDataType(field.getType().getSimpleName()));
            annotationHandlerHelper(field, sb, annotationHandler);
            sb.append(",\n");
        }

        sb.append("PRIMARY KEY (").append(annotationHandler.getPrimaryField()).append(")");
        if (annotationHandler.getUniqueField().size() == 1) {
            sb.append(",\n");
            sb.append("UNIQUE (").append(annotationHandler.getUniqueField().get(0)).append(")");
        } else if (annotationHandler.getUniqueField().size() > 1) {
            sb.append(",\n");
            sb.append("CONSTRAINT UC_").append(clz.getSimpleName()).append(" UNIQUE (");
            for (String fieldName : annotationHandler.getUniqueField()) {
                sb.append(fieldName);
                sb.append(",");
            }
            sb.replace(sb.length() - 1, sb.length(), ")");
        }
        sb.append("\n);");
        return sb.toString();
    }


    //<----------------------------------DELETE---------------------------------->
    String truncateTableQuery() {
        StringBuilder sb = new StringBuilder();
        logger.info("Truncating table " + clz.getSimpleName());
        sb.append("TRUNCATE TABLE ").append(clz.getSimpleName().toLowerCase()).append(";\n");
        return sb.toString();
    }


    public String deleteSingleByAnyPropertyQuery(Object obj) {
        StringBuilder sb = new StringBuilder();
        Gson gson = new Gson();
        logger.info("Deleting single item by specific property");
        sb.append("DELETE FROM ").append(clz.getSimpleName().toLowerCase());
        sb.append(" WHERE ");

        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                sb.append(field.getName());
                sb.append(" = ");

                if (Utils.map.containsKey(field.getType())) {
                    sb.append(field.get(obj));
                    sb.append(" AND ");
                } else if (field.getType().equals(String.class)) {
                    sb.append("'");
                    sb.append(field.get(obj));
                    sb.append("' AND ");
                } else {
                    sb.append("'");
                    sb.append(gson.toJson(field.get(obj)));
                    sb.append("' AND ");
                }

            }catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        sb.replace(sb.length() - 5, sb.length(), ";");
        return sb.toString();
    }

    String deleteManyByAnyPropertyQuery(String property, Object value) {
        StringBuilder sb = new StringBuilder();
        Gson gson = new Gson();
        logger.info("Deleting many items by specific property");
        sb.append("DELETE FROM ").append(clz.getSimpleName().toLowerCase());
        sb.append(" WHERE ").append(property).append("=");
        if (Utils.map.containsKey(value.getClass())) {
            sb.append(value);
        } else if (value.getClass().equals(String.class)) {
            sb.append("'").append(value).append("'");
        } else {
            sb.append("'").append(gson.toJson(value)).append("'");
        }
        return sb.toString();
    }


    String updateEntireObjectQuery(Object object) {
        StringBuilder sb = new StringBuilder();
        StringBuilder whereString = new StringBuilder();
        Gson gson = new Gson();

        sb.append("UPDATE ");
        sb.append(clz.getSimpleName().toLowerCase());
        sb.append(" SET ");

        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                if (field.isAnnotationPresent(PrimaryKey.class)) {
                    whereString.append(" WHERE ").append(field.getName());
                    whereString.append(" = ").append(field.get(object));
                    continue;
                }

                sb.append(field.getName());
                sb.append(" = ");

                if (Utils.map.containsKey(field.getType())) {
                    sb.append(field.get(object));
                    sb.append(" , ");
                } else if (field.getType().equals(String.class)) {
                    sb.append("'");
                    sb.append(field.get(object));
                    sb.append("' , ");
                } else {
                    sb.append("'");
                    sb.append(gson.toJson(field.get(object)));
                    sb.append("' , ");
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        sb.replace(sb.length() - 3, sb.length(), whereString.toString());
        sb.append(";");
        return sb.toString();
    }

    String updateSinglePropertyQuery(String filedName, Object fieldValue, String filterFieldName, Object filterValue) {
        StringBuilder sb = new StringBuilder();
        Gson gson = new Gson();
        sb.append("UPDATE ");
        sb.append(clz.getSimpleName().toLowerCase());
        sb.append(" SET ");
        sb.append(filedName).append(" = ");

        if (Utils.map.containsKey(fieldValue.getClass())) {
            sb.append(fieldValue);
        } else if (fieldValue.getClass().equals(String.class)) {
            sb.append("'");
            sb.append(fieldValue);
            sb.append("'");
        } else {
            sb.append("'");
            sb.append(gson.toJson(fieldValue));
            sb.append("'");
        }

        sb.append(" WHERE ");
        sb.append(filterFieldName).append(" = ");

        if (Utils.map.containsKey(filterValue.getClass())) {
            sb.append(filterValue);
        } else if (filterValue.getClass().equals(String.class)) {
            sb.append("'");
            sb.append(filterValue);
            sb.append("'");
        } else {
            sb.append("'");
            sb.append(gson.toJson(filterValue));
            sb.append("'");
        }

        sb.append(";");
        return sb.toString();
    }



    //<----------------------------------HELPERS---------------------------------->
    private String getMySQLDataType(String javaType) {
        switch (javaType) {
            case "int":
            case "Integer":
                return "int(11)";
            case "long":
            case "Long":
                return "BIGINT(50)";
            case "float":
            case "Float":
                return "FLOAT(24)";
            case "double":
            case "Double":
                return "FLOAT(53)";
            case "boolean":
            case "Boolean":
                return "BOOLEAN";
            case "Date":
                return "DATE";
            default:
                return "varchar(255)";
        }
    }

    void reflectionHandlerHelper(T object, StringBuilder sb) {
        Gson gson = new Gson();
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                if (field.isAnnotationPresent(AutoIncrement.class)) {
                    sb.append("NULL,");
                } else if (Utils.map.containsKey(field.getType())) {
                    sb.append(field.get(object));
                    sb.append(",");
                } else if (field.get(object) instanceof String) {
                    sb.append("'");
                    sb.append(field.get(object));
                    sb.append("',");
                } else {
                    sb.append("'");
                    sb.append(gson.toJson(field.get(object)));
                    sb.append("',");
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void annotationHandlerHelper(Field field, StringBuilder sb, AnnotationsHandler annotationsHandler) {
        if (field.getAnnotation(PrimaryKey.class) != null) {
            annotationsHandler.setCountPrimaryKeys(annotationsHandler.getCountPrimaryKeys() + 1);
            annotationsHandler.setPrimaryField(field.getName());
            if (annotationsHandler.getCountPrimaryKeys() > 1) {
                throw new IllegalArgumentException(annotationsHandler.messagePrimaryKey());
            }
        }

        if (field.isAnnotationPresent(Unique.class)) {
            annotationsHandler.getUniqueField().add(field.getName());
        }

        if (field.isAnnotationPresent(NotNull.class)) {
            sb.append(" NOT NULL");
        }

        if (field.isAnnotationPresent(AutoIncrement.class)) {
            sb.append(" AUTO_INCREMENT");
            annotationsHandler.setCountAutoIncrement(annotationsHandler.getCountAutoIncrement() + 1);
            if (annotationsHandler.getCountAutoIncrement() > 1) {
                throw new IllegalArgumentException(annotationsHandler.messageAutoIncrement());
            }
        }
    }


}
