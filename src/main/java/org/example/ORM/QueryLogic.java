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
import java.util.HashMap;
import java.util.Map;

public class QueryLogic<T> {


    private static Logger logger = LogManager.getLogger(QueryLogic.class.getName());
    private final Class<T> clz;
    private final Gson gson = new Gson();
    private StringBuilder sb = new StringBuilder();


    public QueryLogic(Class<T> clz) {
        this.clz = clz;
    }


    String selectAllQueryLogic() {
        logger.info("creating " + prefixSelectQuery() + " Query");
        return prefixSelectQuery() + ";";
    }


    String selectByIdQuery(String fieldName, Integer value) {
        logger.info("creating " + selectAllQueryLogic() + " WHERE " + fieldName + " = " + value);

        sb.append(prefixSelectQueryPlusWHERE());
        sb.append(fieldName);
        sb.append("= ").append(value.toString());
        sb.append(";");
        return sb.toString();
    }


    String selectByManyFiltersQuery(String filedName, Object fieldValue, String filterFieldName, Object filterValue) {

        Map<String, Object> currMap = new HashMap<>();
        currMap.put(filedName, fieldValue);
        currMap.put(filterFieldName, filterValue);


        logger.info("creating " + prefixSelectQueryPlusWHERE());
        sb.delete(0, sb.length());

        sb.append(prefixSelectQueryPlusWHERE());

        for(Map.Entry<String, Object> entry : currMap.entrySet()) {
            sb.append(entry.getKey());
            sb.append(" = ");
            sb.append(valueMapChecker(entry.getValue(), " AND "));
        }
        sb.replace(sb.length() - 4, sb.length(), ";");
        return sb.toString();
    }


    String findObjQuery(Object object) {
        logger.info("creating " + prefixSelectQueryPlusWHERE());
        sb.delete(0, sb.length());

        sb.append(prefixSelectQueryPlusWHERE());

        for(Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                if(! field.isAnnotationPresent(AutoIncrement.class)) {
                    sb.append(field.getName());
                    sb.append(" = ");
                    sb.append(valueMapChecker(field.get(object), " AND "));
                }
            } catch(IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        sb.replace(sb.length() - 5, sb.length(), ";");

        return sb.toString();
    }


    String insertObjectQuery(T object) {
        sb.delete(0, sb.length());

        logger.info("creating " + prefixInsertQuery() + " Query");
        sb.append(prefixInsertQuery());
        sb.append("(");

        reflectionHandlerHelper(object, sb);

        sb.deleteCharAt(sb.length() - 1);
        sb.append(");");
        return sb.toString();
    }


    String createTableQuery() {
        sb.delete(0, sb.length());
        logger.info("Creating " + prefixCreateTableQuery() + " Query");

        sb.append(prefixCreateTableQuery());
        sb.append(" (\n");

        AnnotationsHandler annotationHandler = new AnnotationsHandler(0, 0, null);

        for(Field field : clz.getDeclaredFields()) {
            sb.append(field.getName());
            sb.append(" ");
            sb.append(getMySQLDataType(field.getType().getSimpleName()));
            annotationHandlerHelper(field, sb, annotationHandler);
            sb.append(",\n");
        }

        sb.append("PRIMARY KEY (").append(annotationHandler.getPrimaryField()).append(")");
        sb.append("\n);");
        return sb.toString();
    }


    String truncateTableQuery() {
        sb.delete(0, sb.length());
        logger.info("creating " + prefixTruncateTableQuery() + "Query");

        sb.append(prefixTruncateTableQuery()).append(";\n");
        return sb.toString();
    }


    String deleteSingleByAnyPropertyQuery(Object obj) {
        sb.delete(0, sb.length());
        logger.info("creating" + prefixDeleteQuery() + "Query");

        sb.append(prefixDeleteQuery());

        for(Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                sb.append(field.getName());
                sb.append(" = ");
                sb.append(valueMapChecker(field.get(obj), " AND "));

            } catch(IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        sb.replace(sb.length() - 5, sb.length(), ";");
        return sb.toString();
    }


    String deleteManyByAnyPropertyQuery(String property, Object value) {
        sb.delete(0, sb.length());
        logger.info("Deleting many items by specific property");

        sb.append(prefixDeleteQuery());
        sb.append(property).append("=");
        sb.append(valueMapChecker(value, null));

        return sb.toString();
    }


    String updateEntireObjectQuery(Object object) {
        sb.delete(0, sb.length());
        StringBuilder whereString = new StringBuilder();

        sb.append("UPDATE ");
        sb.append(clz.getSimpleName().toLowerCase());
        sb.append(" SET ");

        for(Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                if(field.isAnnotationPresent(PrimaryKey.class)) {
                    whereString.append(" WHERE ").append(field.getName());
                    whereString.append(" = ").append(field.get(object));
                    continue;
                }

                sb.append(field.getName());
                sb.append(" = ");
                sb.append(valueMapChecker(field.get(object), " , "));
            } catch(IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        sb.replace(sb.length() - 3, sb.length(), whereString.toString());
        sb.append(";");
        return sb.toString();
    }


    String updateSinglePropertyQuery(String filedName, Object fieldValue, String filterFieldName, Object filterValue) {
        sb.delete(0, sb.length());

        sb.append("UPDATE ");
        sb.append(clz.getSimpleName().toLowerCase());
        sb.append(" SET ");
        sb.append(filedName).append(" = ");


        sb.append(valueMapChecker(fieldValue, null));
        sb.append(" WHERE ");
        sb.append(filterFieldName).append(" = ");
        sb.append(valueMapChecker(filterValue, null));
        sb.append(";");

        return sb.toString();
    }


    //<----------------------------------HELPERS---------------------------------->
    private String getMySQLDataType(String javaType) {
        switch(javaType) {
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


    private void reflectionHandlerHelper(T object, StringBuilder sb) {
        for(Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                if(field.isAnnotationPresent(AutoIncrement.class)) {
                    sb.append("NULL,");
                }
                else {
                    sb.append(valueMapChecker(field.get(object), ","));
                }
            } catch(IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }


    private void annotationHandlerHelper(Field field, StringBuilder sb, AnnotationsHandler annotationsHandler) {
        if(field.getAnnotation(PrimaryKey.class) != null) {
            annotationsHandler.setCountPrimaryKeys(annotationsHandler.getCountPrimaryKeys() + 1);
            annotationsHandler.setPrimaryField(field.getName());
            if(annotationsHandler.getCountPrimaryKeys() > 1) {
                throw new IllegalArgumentException(annotationsHandler.messagePrimaryKey());
            }
        }

        if(field.isAnnotationPresent(NotNull.class)) {
            sb.append(" NOT NULL");
        }

        if(field.isAnnotationPresent(Unique.class)) {
            sb.append(" UNIQUE");
        }

        if(field.isAnnotationPresent(AutoIncrement.class)) {
            sb.append(" AUTO_INCREMENT");
            annotationsHandler.setCountAutoIncrement(annotationsHandler.getCountAutoIncrement() + 1);
            if(annotationsHandler.getCountAutoIncrement() > 1) {
                throw new IllegalArgumentException(annotationsHandler.messageAutoIncrement());
            }
        }
    }


    private String valueMapChecker(Object value, String bettwen) {
        StringBuilder sb = new StringBuilder();

        if(Utils.map.containsKey(value.getClass())) {
            sb.append(value);
        }
        else if(value.getClass().equals(String.class)) {
            sb.append("'");
            sb.append(value);
            sb.append("'");
        }
        else {
            sb.append("'");
            sb.append(gson.toJson(value));
            sb.append("'");
        }
        if(bettwen != null) {
            sb.append(bettwen);
        }
        return sb.toString();
    }


    private String prefixSelectQuery() {
        return "SELECT * FROM " + clz.getSimpleName().toLowerCase();
    }


    private String prefixSelectQueryPlusWHERE() {
        return "SELECT * FROM " + clz.getSimpleName().toLowerCase() + " WHERE ";
    }


    private String prefixInsertQuery() {
        return "INSERT INTO " + clz.getSimpleName().toLowerCase() + " VALUES ";
    }


    private String prefixCreateTableQuery() {
        return "CREATE TABLE " + clz.getSimpleName().toLowerCase();
    }


    private String prefixTruncateTableQuery() {
        return "TRUNCATE TABLE " + clz.getSimpleName().toLowerCase();
    }


    private String prefixDeleteQuery() {
        return "DELETE FROM " + clz.getSimpleName().toLowerCase() + " WHERE ";
    }


    private String prefixUpdateQuery() {
        return "UPDATE " + clz.getSimpleName().toLowerCase() + " SET ";
    }

}
