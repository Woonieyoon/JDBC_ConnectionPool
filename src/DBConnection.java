
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DBConnection {
	
	//driver와 url이 결국 매챙되기 때문에 Class.forName();을 할 필요가 없다.
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:orcl";
	private static final String USER = "scott";
	private static final String PASSWORD = "tiger";
	
	private static ComboPooledDataSource C3PO_POOL = new ComboPooledDataSource();

	public DBConnection() throws Exception {
		initializeConnectionPool();
	}
	
	public void initializeConnectionPool() throws Exception {
		C3PO_POOL.setDriverClass(DRIVER);
		C3PO_POOL.setJdbcUrl(URL);
		C3PO_POOL.setUser(USER);
		C3PO_POOL.setPassword(PASSWORD);
		C3PO_POOL.setMinPoolSize(10);
		C3PO_POOL.setInitialPoolSize(10);
		C3PO_POOL.setMaxPoolSize(30);
	}
	
	public ComboPooledDataSource getConnection() {
		return C3PO_POOL;
	}
	
}
