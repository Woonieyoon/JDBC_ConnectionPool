import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DBMain {

	private static final Logger LOGGER = LoggerFactory.getLogger(DBMain.class);
	private DBConnection dbConnection;
	private boolean initialize = false;

	public void init() {
		if (!initialize) {
			try {
				dbConnection = new DBConnection();
			} catch (Exception e) {
				LOGGER.error("Connection Pool 생성 실패 : ", e);
			}
			initialize = true;
		}	
	}

	public void execute() {
		
		if(!initialize) {
			init();
		}
		
		ComboPooledDataSource C3PO_POOL = dbConnection.getConnection();
		Connection conn = null;
		EmpDAOImpl empDAOImpl = null;
		try {
			conn = C3PO_POOL.getConnection();
			conn.setAutoCommit(false);
			empDAOImpl = new EmpDAOImpl(conn);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		EmpDAO empDao = empDAOImpl;
		List<EmployeeDTO> dtos = empDao.getEmployees(3);
		try {
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}

//		EmployeeDTO employeeVO = new EmployeeDTO();
//		employeeVO.setEmpno(3335);
//		employeeVO.setEname("HELLO");
//		employeeVO.setJob("MANAGER");
//		employeeVO.setHiredate(new Date(0));
//		employeeVO.setMgr(4000);
//		employeeVO.setSal(5000);
//		employeeVO.setComm(5000);
//		employeeVO.setDeptno(20);
//		empDao.insertEmployee(employeeVO);
		// empDao.updateEmployee(employeeVO);

	}

	public static void main(String[] args) {
		DBMain dbMain = new DBMain();
		dbMain.init();
		dbMain.execute();
	}
}
