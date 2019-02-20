//package jdbc.tutorial;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//public class HelloJDBC {
//	
//	private static final String URL = "jdbc:oracle:thin:@localhost:1521:orcl";
//	private static final String USER = "scott";
//	private static final String PASSWORD ="tiger";
//	private static final String SQL = "SELECT 'HELLO JDBC' AS MESSAGE FROM DUAL";
//	
//	public static void main(String[] args) {
//		
//		ResultSet rset = null;
//		try (Connection conn = DriverManager.getConnection(URL,USER,PASSWORD);
//				PreparedStatement pstmt = conn.prepareStatement(SQL);
//				 ) {
//			rset = pstmt.executeQuery();
//			//executeQuery()��
//			//�ٷ� �������� �ʴ´�.
//			//ResultSet���� ���� �غ��ϰ� �ִ�.
//			//ResultSet���� next�� ���� �����͸� �����ü� �ִ�.
//			//next�� �Ҷ��� ��μ� �����Ͱ� ���۵ȴ�.
//			//
//			//�Ѳ����� �ε��ϸ� �ϳ��� ó���Ҽ� �ְԲ� next�� ���� .�޸� ������
//			while(rset.next()) {
//				String resultMessage = rset.getString("MESSAGE");
//				//String resultMessage = rset.getString(1);
//				System.out.println(resultMessage);
//			}
//				
//
//		} catch (Exception e) {
//
//		}finally {
//			if(rset!=null) {
//				try {
//					rset.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}
//}

package sojangnimconnectionpoolexample;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class HelloJDBC2 {
	
	private static ComboPooledDataSource C3PO_POOL = new ComboPooledDataSource();
	private static final String DRIVER = "oracle.jdbc.OracleDriver";
	 
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:orcl";
	private static final String USER = "scott";
	private static final String PASSWORD = "tiger";
	private static final String SQL1 = "SELECT 'HELLO JDBC' AS MESSAGE FROM DUAL";
	private static final String SQL2 = "SELECT ? FROM DUAL";

	private static void initializeConnectionPool() throws PropertyVetoException {
		C3PO_POOL.setDriverClass(DRIVER);
		C3PO_POOL.setJdbcUrl(URL);
		C3PO_POOL.setUser(USER);
		C3PO_POOL.setPassword(PASSWORD);
		C3PO_POOL.setMinPoolSize(10);
		C3PO_POOL.setInitialPoolSize(10);
		C3PO_POOL.setMaxPoolSize(30);
	}
	
	public static void main(String[] args) throws PropertyVetoException {
		Connection conn = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		ResultSet rset1 = null;
		ResultSet rset2 = null;
		String result1 = null;
		String result2 = null;
		initializeConnectionPool();
		long startTime = System.nanoTime();
		try {
			//TCP shacking , ����� ���� , �� ���������� �غ� �ؾ��ϱ� ������ �÷��ɸ� getConnection�� �����ɸ�
			//conn = DriverManager.getConnection(URL, USER, PASSWORD);
			conn = C3PO_POOL.getConnection();
			System.out.println("Connection Getting Time= " + (System.nanoTime() - startTime)/1000_000+"ms");
			pstmt1 = conn.prepareStatement(SQL1);// ��Ȱ���Ҳ��� close�� �ϰ� �ٽ� ������ �Ѵ�.
												// Ŀ���� �սǵȴ�.
			rset1 = pstmt1.executeQuery();

			while (rset1.next()) {
				 result1 = rset1.getString("MESSAGE");
				// String resultMessage = rset.getString(1);
				System.out.println(result1);
			}
			
			pstmt2 = conn.prepareStatement(SQL2);
			pstmt2.setString(1, result1);
			rset2 = pstmt2.executeQuery();
			if(rset2.next()) {
				result2 = rset2.getString(1);
				System.out.println(result2);
			}
		} catch (Exception e) {

		} finally {
			close(rset1);
			close(rset2);
			close(pstmt1);
			close(pstmt2);
			close(conn);
		}
				
	}
	
	private static String getResult1(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;

//		try {
//			pstmt = conn.prepareStatement(SQL1);
//			rset = pstmt.executeQuery();
//			if(rset.next()) {
//			}
//		}
		
		return null;
	}
	
	
	private static String getResult2(Connection conn,String result1) {
		return null;
	}
	
	private static void close(AutoCloseable closeable) {
		try {
			closeable.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
