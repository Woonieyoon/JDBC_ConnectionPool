import java.sql.SQLException;
import java.util.List;

public interface EmpDAO {
	public void deleteMember(EmployeeDTO employee);
	public List<EmployeeDTO> getEmployees(int page);
	public int getEmployeesCount();
	public int insertEmployee(EmployeeDTO e);
	public int updateEmployee(EmployeeDTO e) throws SQLException;
}
