package salary.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import salary.bean.DataAccessBean;
import salary.bean.EmployeeInfo;


@WebServlet(name="EmployeeRegistServlet",urlPatterns = "/employeeregist")
public class EmployeeRegistServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			String name = request.getParameter("name");
			String hourlywage  = request.getParameter("hourlywage");
			String carfare = request.getParameter("carfare");
			String sphourly = request.getParameter("sphourly");

			if(name.length() < 2 || name.length() > 15) {
				request.setAttribute("message1", "名前は3文字から入力できます");
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/employee_regist.jsp");
				rd.forward(request, response);
			}

			if(hourlywage.length() < 2 || hourlywage.length() > 9999) {
				request.setAttribute("message1", "入力金額を確認してください");
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/employee_regist.jsp");
				rd.forward(request, response);
			}
			EmployeeInfo employeeInfo = new EmployeeInfo();
			employeeInfo.setName(name);
			employeeInfo.setHourlywage(hourlywage);
			employeeInfo.setCarfare(carfare);
			employeeInfo.setSphourly(sphourly);

			DataAccessBean dab = new DataAccessBean();
			dab.registEmployeeInfo(employeeInfo);
			response.sendRedirect(request.getContextPath() + "/employeefindall");
		}catch(SQLException e) {
			e.printStackTrace();
			request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
		}catch(DuplicateNameException e) {
			request.setAttribute("message1", "この名前は既に登録されています");
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/employee_regist.jsp");
			rd.forward(request, response);
		}
	}

}
