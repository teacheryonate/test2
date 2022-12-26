package kr.co.repository;

import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import kr.co.command.Command;
import kr.co.domain.BoardDTO;

public class ServiceMenusDAO {

	private DataSource dataFactory;

	public ServiceMenusDAO() {
		try {
			Context ctx = new InitialContext();
			dataFactory = (DataSource) ctx.lookup("java:comp/env/jdbc/Oracle11g");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private void closeAll(ResultSet rs, PreparedStatement pstmt, Connection conn) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public Map<String, Command> callMenus() {
		Map<String, Command> menus = new HashMap<String, Command>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "SELECT * FROM tbl_menu";
		ResultSet rs = null;

		try {
			conn = dataFactory.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				String uri = rs.getString("uri");
				String fullpkg = rs.getString("fullpkg");

				Class<?> menu = Class.forName(fullpkg);

				Constructor<?> cons = menu.getConstructor();

				Command t = (Command) cons.newInstance();

				menus.put(uri, t);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, pstmt, conn);
		}

		return menus;
	}

}
