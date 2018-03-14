package treningsdagbok;

import java.sql.*;

import com.mysql.jdbc.Connection;

public abstract class ActiveDomainObject {
    public abstract void initialize (Connection conn);
    public abstract void refresh (Connection conn);
    public abstract void save (Connection conn);
}

