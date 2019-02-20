import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import exception.DuplicationException;
import exception.PageNotFoundException;

/**
 * 
 * @author notebiz0020 employee 테이블에 접근하는 객체 스레드에 안전하지 않으므로 생성해놓고 공유해서는 안된다.
 *         스레드마다 만들어서 사용해야한다. 하나의 트랜잭션은 하나의 connection을 가져야함
 */

public class EmpDAOImpl implements EmpDAO {

	private static final int ROWSPERCOUNT = 5;
	private static final Logger LOGGER = LoggerFactory.getLogger(EmpDAOImpl.class);
	private final Connection conn;

	public EmpDAOImpl(Connection conn) {
		this.conn = conn;
	}

	public boolean checkDuplication(EmployeeDTO employee) throws SQLException {
		String sql = "select 1 from dual where exists ( select 1 from emp where empno=? )";
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, employee.getEmpno());
			resultSet = preparedStatement.executeQuery();
			return resultSet.next();
		} catch (SQLException e) {
			throw e;
		} finally {

			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}

			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e1) {
				}
			}
		}
	}

	@Override
	public int insertEmployee(EmployeeDTO employee) {
		String query = "INSERT INTO EMP(EMPNO,ENAME,JOB,MGR,HIREDATE,SAL,COMM,DEPTNO) "
				+ "VALUES(?,?,?,?,TO_DATE(?,'YY/MM/DD'),?,?,?)";

		try {
			if (checkDuplication(employee)) {
				throw new DuplicationException();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		int returnValue = 0;
		PreparedStatement preparedStatement;

		try {
			preparedStatement = conn.prepareStatement(query);
			preparedStatement.setInt(1, employee.getEmpno());
			preparedStatement.setString(2, employee.getEname());
			preparedStatement.setString(3, employee.getJob());
			preparedStatement.setInt(4, employee.getMgr());
			preparedStatement.setDate(5, employee.getHiredate());
			preparedStatement.setInt(6, employee.getSal());
			preparedStatement.setInt(7, employee.getComm());
			preparedStatement.setInt(8, employee.getDeptno());
			returnValue = preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnValue;
	}

	@Override
	public void deleteMember(EmployeeDTO employee) {

	}

	@Override
	public List<EmployeeDTO> getEmployees(int page) {

		List<EmployeeDTO> employeeVOs = new ArrayList<EmployeeDTO>();
		int totalCount = getEmployeesCount();
		int pageNum = totalCount / ROWSPERCOUNT;
		System.out.println(pageNum);
		if (!(page >= 0 && page <= pageNum)) {
			throw new PageNotFoundException();
		}

		String query = "SELECT TAB.* FROM " + "(SELECT ROWNUM RN, TAB1.* "
				+ "FROM (select * from emp ORDER BY empno asc) TAB1 WHERE ROWNUM <= " + ROWSPERCOUNT + "*" + page
				+ ") TAB" + " WHERE RN >=" + ROWSPERCOUNT + "*(" + (page - 1) + ")+1";

		PreparedStatement pstmt;
		ResultSet resultSet;
		try {
			pstmt = conn.prepareStatement(query);
			resultSet = pstmt.executeQuery();
			while (resultSet.next()) {
				EmployeeDTO dto = DtoFactory.fromResultSet(resultSet, EmployeeDTO.class);
//				dto.setEmpno(resultSet.getInt("EMPNO"));
//				dto.setEname(resultSet.getString("ENAME"));
//				dto.setJob(resultSet.getString("JOB"));
//				dto.setMgr(resultSet.getInt("MGR"));
//				dto.setHiredate(resultSet.getDate("HIREDATE"));
//				dto.setSal(resultSet.getInt("SAL"));
//				dto.setComm(resultSet.getInt("COMM"));
//				dto.setDeptno(resultSet.getInt("DEPTNO"));
				employeeVOs.add(dto);
				LOGGER.debug("***" + dto.getDeptno() + "***");
				LOGGER.debug(resultSet.getInt(1)+"");
			}

		} catch (Exception e) {

		}
		return employeeVOs;
	}

	@Override
	public int getEmployeesCount() {
		String query = "SELECT COUNT(*) AS cnt FROM EMP";
		int count = 0;
		try (PreparedStatement preparedStatement = conn.prepareStatement(query);) {
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				count = resultSet.getInt("cnt");
				LOGGER.debug("전체갯수:" + count);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int updateEmployee(EmployeeDTO employee) throws SQLException {

		String query = "UPDATE EMP SET DEPTNO = ? WHERE EMPNO=1111";

		PreparedStatement preparedStatement;
		try {
			preparedStatement = conn.prepareStatement(query);
			preparedStatement.setInt(1, 40);
			LOGGER.debug("업데이트 성공");
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw e;
		}
	}
}
