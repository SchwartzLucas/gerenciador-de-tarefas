package web;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;
import java.util.StringJoiner;

public class Utils {

    private static final Properties properties = new Properties();
    static {
        try {
            InputStream input = Utils.class.getClassLoader().getResourceAsStream("application.properties");
            if (!isEmpty(input)) {
                properties.load(input);

            }
        } catch (Exception e) {
            throw new RuntimeException("Nao foi possivel carregar o properties ", e);
        }
    }

    public static String get(String key){
        return properties.getProperty(key);
    }

    public static Connection getConnection() {
        try{
            HikariConfig hkconfig = new HikariConfig();
            hkconfig.setDriverClassName(get("driver.bancodados"));
            hkconfig.setJdbcUrl(get("url.bancodados"));
            hkconfig.setUsername(get("user.bancodados"));
            hkconfig.setPassword(get("password.bancodados"));
            return new HikariDataSource(hkconfig).getConnection();
        } catch (Exception e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
        return null;
    }

    public static boolean isEmpty(final Object object) {
        return switch (object) {
            case null -> true;
            case String v -> v.replaceAll("\\s", "").isEmpty();
            case StringJoiner v -> v.length() == 0;
            case byte[] v -> v.length == 0;
            case short[] v -> v.length == 0;
            case int[] v -> v.length == 0;
            case long[] v -> v.length == 0;
            case float[] v -> v.length == 0;
            case double[] v -> v.length == 0;
            case boolean[] v -> v.length == 0;
            case char[] v -> v.length == 0;
            case Object[] v -> v.length == 0;
            case Collection<?> v -> v.isEmpty();
            case Map<?, ?> v -> v.isEmpty();
            default -> false;
        };
    }
}
