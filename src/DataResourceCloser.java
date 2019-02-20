import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataResourceCloser {

		
	public DataResourceCloser(Connection conn,PreparedStatement pstmt,ResultSet resultSet){
		
		if(resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
