package kr.co.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import kr.co.domain.BoardDTO;
import kr.co.domain.PageTO;
import kr.co.domain.SearchPageTO;

public class BoardDAO {
	
	private DataSource dataFactory;
	
	public BoardDAO() {
		try {
			Context ctx = new InitialContext();
			dataFactory = (DataSource) ctx.lookup("java:comp/env/jdbc/Oracle11g");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	
	

	private void commitOrRollback(Connection conn, int result) {
		if (result > 0) {
			try {
				conn.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
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
	
	public PageTO<BoardDTO> list2(int curPage) {
		PageTO<BoardDTO> pt = new PageTO<>(curPage);
		
		List<BoardDTO> list = new ArrayList<BoardDTO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "SELECT * FROM "
				+ "(SELECT rownum rnum, num, author, title, TO_CHAR(writeday, 'yyyy-mm-dd') writeday, "
				+ "readcnt, repIndent FROM "
				+ "(SELECT * FROM tbl_board ORDER BY repRoot DESC, repStep ASC)) "
				+ "WHERE rnum BETWEEN ? AND ?";
		ResultSet rs = null;
		try {
			conn = dataFactory.getConnection();
			int amount = getAmount(conn);
			pt.setAmount(amount);
			int startNum = pt.getStartNum();
			int endNum = pt.getEndNum();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, startNum);
			pstmt.setInt(2, endNum);		
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int num = rs.getInt("num");
				String author = rs.getString("author");
				String title = rs.getString("title");
				String writeday = rs.getString("writeday");
				int readcnt = rs.getInt("readcnt");
				int repIndent = rs.getInt("repIndent");
				BoardDTO dto = new BoardDTO(num, author, title, writeday, readcnt, -1, -1, repIndent);
				list.add(dto);
			}
			pt.setList(list);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, pstmt, conn);
		}

		return pt;
	}

	private int getAmount(Connection conn) {
		int amount = 0;
		
		PreparedStatement pstmt = null;
		String sql = "SELECT COUNT(num) FROM tbl_board";
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				amount = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, pstmt, null);
		}

		return amount;
	}

	public List<BoardDTO> list() {
		List<BoardDTO> list = new ArrayList<BoardDTO>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "SELECT num, author, title, TO_CHAR(writeday, 'yyyy-mm-dd') writeday, "
				+ "readcnt, repRoot, repStep, " + "repIndent FROM tbl_board ORDER BY repRoot DESC, repStep ASC";
		ResultSet rs = null;

		try {
			conn = dataFactory.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int num = rs.getInt("num");
				String author = rs.getString("author");
				String title = rs.getString("title");
				String writeday = rs.getString("writeday");
				int readcnt = rs.getInt("readcnt");

				int repRoot = rs.getInt("repRoot");
				int repStep = rs.getInt("repStep");
				int repIndent = rs.getInt("repIndent");

				BoardDTO dto = new BoardDTO(num, author, title, writeday, readcnt, repRoot, repStep, repIndent);

				list.add(dto);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, pstmt, conn);
		}

		return list;
	}

	public void insert(BoardDTO dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO tbl_board " + "(num, author, pw, title, content, "
				+ "repRoot, repStep, repIndent) VALUES " + "(seq_board_num.NEXTVAL, ?, ?, ?, ?, "
				+ "seq_board_num.CURRVAL, ?, ?)";

		try {
			conn = dataFactory.getConnection();
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, dto.getAuthor());
			pstmt.setInt(2, dto.getPw());
			pstmt.setString(3, dto.getTitle());
			pstmt.setString(4, dto.getContent());

			pstmt.setInt(5, 0);
			pstmt.setInt(6, 0);

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(null, pstmt, conn);
		}

	}

	public BoardDTO read(int num) {
		BoardDTO dto = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "SELECT * FROM tbl_board WHERE num = ?";
		ResultSet rs = null;

		int result1 = 0;
		int result2 = 0;
		int result = 0;

		try {
			conn = dataFactory.getConnection();

			conn.setAutoCommit(false);

			result1 = increaseReadcnt(conn, num);

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				String author = rs.getString("author");
				String title = rs.getString("title");
				String content = rs.getString("content");

				content = content.replace("\n", "<br>");

				String writeday = rs.getString("writeday");
				int readcnt = rs.getInt("readcnt");

				dto = new BoardDTO(num, author, title, content, -1, writeday, readcnt, -1, -1, -1);

			}

			result2 = 1;

			result = result1 * result2;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			commitOrRollback(conn, result);

			closeAll(rs, pstmt, conn);
		}

		return dto;
	}

	private int increaseReadcnt(Connection conn, int num) {
		int result1 = 0;

		PreparedStatement pstmt = null;
		String sql = "UPDATE tbl_board SET readcnt = readcnt + 1 WHERE num = ?";

		try {
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, num);

			result1 = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(null, pstmt, null);
		}

		return result1;
	}

	public BoardDTO updateui(int num) {
		BoardDTO dto = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "SELECT * FROM tbl_board WHERE num = ?";
		ResultSet rs = null;

		try {
			conn = dataFactory.getConnection();

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				String author = rs.getString("author");
				String title = rs.getString("title");
				String content = rs.getString("content");

				String writeday = rs.getString("writeday");
				int readcnt = rs.getInt("readcnt");

				dto = new BoardDTO(num, author, title, content, -1, writeday, readcnt, -1, -1, -1);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, pstmt, conn);
		}

		return dto;
	}

	public void update(BoardDTO dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "UPDATE tbl_board SET author = ?, title=?, content=? " + " WHERE num = ? AND pw = ?";

		try {
			conn = dataFactory.getConnection();

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getAuthor());
			pstmt.setString(2, dto.getTitle());
			pstmt.setString(3, dto.getContent());
			pstmt.setInt(4, dto.getNum());
			pstmt.setInt(5, dto.getPw());

			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(null, pstmt, conn);
		}

	}

	public int delete(int num, int pw) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM tbl_board " + " WHERE num = ? AND pw = ?";
		int result = 0;

		try {
			conn = dataFactory.getConnection();

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, num);
			pstmt.setInt(2, pw);

			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(null, pstmt, conn);
		}

		return result;

	}

	public void reply(int orgNum, BoardDTO dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO tbl_board (num, author, " + "title, content, pw, repRoot, repStep, repIndent) "
				+ "VALUES (seq_board_num.NEXTVAL, ?, ?, ?, ?, ?, ?, ?) ";

		int result = 0;
		int result1 = 0;
		int result2 = 0;

		try {
			conn = dataFactory.getConnection();
			conn.setAutoCommit(false);

			BoardDTO orgDto = getOrgBoard(conn, orgNum);

			boolean isExists = isExistsAnotherReply(conn, orgDto);

			if (isExists) {
				result1 = increaseRepStep(conn, orgDto);
			} else {
				result1 = 1;
			}

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, dto.getAuthor());
			pstmt.setString(2, dto.getTitle());
			pstmt.setString(3, dto.getContent());
			pstmt.setInt(4, dto.getPw());

			pstmt.setInt(5, orgDto.getRepRoot());
			pstmt.setInt(6, orgDto.getRepStep() + 1);
			pstmt.setInt(7, orgDto.getRepIndent() + 1);

			result2 = pstmt.executeUpdate();

			result = result1 * result2;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			commitOrRollback(conn, result);

			closeAll(null, pstmt, conn);
		}

	}

	// 기존 답글 존재여부 확인:
	private boolean isExistsAnotherReply(Connection conn, BoardDTO orgDto) {
		boolean isExists = false;

		PreparedStatement pstmt = null;
		String sql = "SELECT MAX(repStep) FROM tbl_board WHERE repRoot = ? AND repStep > ?";

		ResultSet rs = null;

		try {
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, orgDto.getRepRoot());
			pstmt.setInt(2, orgDto.getRepStep());

			rs = pstmt.executeQuery();

			if (rs.next()) {
				int maxRepStep = rs.getInt(1);
				if (maxRepStep > 0) {
					isExists = true;
				} else {
					isExists = false;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, pstmt, null);
		}

		return isExists;
	}

	private int increaseRepStep(Connection conn, BoardDTO orgDto) {
		int result1 = 0;

		PreparedStatement pstmt = null;
		String sql = "UPDATE tbl_board SET repStep = repStep + 1 WHERE repRoot = ? AND repStep > ?";

		try {

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, orgDto.getRepRoot());
			pstmt.setInt(2, orgDto.getRepStep());
			result1 = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(null, pstmt, null);
		}

		return result1;

	}

	private BoardDTO getOrgBoard(Connection conn, int orgNum) {
		BoardDTO orgDto = null;

		PreparedStatement pstmt = null;
		String sql = "SELECT * FROM tbl_board WHERE num = ?";
		ResultSet rs = null;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, orgNum);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				int repRoot = rs.getInt("repRoot");
				int repStep = rs.getInt("repStep");
				int repIndent = rs.getInt("repIndent");

				orgDto = new BoardDTO(orgNum, null, null, null, -1, repRoot, repStep, repIndent);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, pstmt, null);
		}

		return orgDto;
	}




	public SearchPageTO<BoardDTO> list(int curPage, String criteria, String keyword) {
		SearchPageTO<BoardDTO> spt = new SearchPageTO<BoardDTO>(curPage, criteria, keyword);
		
		List<BoardDTO> list = new ArrayList<BoardDTO>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		String slq = "SELECT * FROM "
				+ "(SELECT rownum rnum, num, author, title, "
				+ "TO_CHAR(writeday, 'yyyy-mm-dd') writeday, readCnt, repIndent FROM "
				+ "(SELECT * FROM tbl_board "
				+ "WHERE "+criteria+" LIKE ? "
				+ "ORDER BY repRoot DESC, repStep ASC)) "
				+ "WHERE rnum BETWEEN ? AND ?";
		ResultSet rs = null;
		
		try {
			conn = dataFactory.getConnection();
			
			int amount = getAmountForSearch(conn, criteria, keyword);
			spt.setAmount(amount);
			
			int startNum = spt.getStartNum();
			int endNum = spt.getEndNum();
			
			
			pstmt = conn.prepareStatement(slq);
			pstmt.setString(1, "%"+keyword+"%");
			pstmt.setInt(2, startNum);
			pstmt.setInt(3, endNum);
			
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int num = rs.getInt("num");
				String author = rs.getString("author");
				String title = rs.getString("title");
				String writeday = rs.getString("writeday");
				int readcnt = rs.getInt("readcnt");
				int repIndent = rs.getInt("repIndent");
				BoardDTO dto = new BoardDTO(num, author, title, writeday, readcnt, -1, -1, repIndent);
				list.add(dto);
			}
			spt.setList(list);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, pstmt, conn);
		}
		
		return spt;
	}




	private int getAmountForSearch(Connection conn, String criteria, String keyword) {
		int amount = 0;
		
		PreparedStatement pstmt = null;
		String sql = "SELECT COUNT(num) FROM tbl_board WHERE "+criteria+" LIKE ?";
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+keyword+"%");
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				amount = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, pstmt, null);
		}
		
		return amount;
	}




	public SearchPageTO<BoardDTO> search(int curPage, String criteria, String keyword) {
		// TODO Auto-generated method stub
		return null;
	}





	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
