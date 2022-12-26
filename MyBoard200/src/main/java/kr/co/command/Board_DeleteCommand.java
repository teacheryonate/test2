package kr.co.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.domain.CommandAction;
import kr.co.repository.BoardDAO;

public class Board_DeleteCommand implements Command {

	@Override
	public CommandAction execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String snum = request.getParameter("num");
		int num = Integer.parseInt(snum);
		
		String spw = request.getParameter("pw");
		int pw = Integer.parseInt(spw);
		
		String scurPage = request.getParameter("curPage");
		int curPage = Integer.parseInt(scurPage);
		
		String criteria = request.getParameter("criteria");
		if(criteria == null) {
			criteria = "title";
		}
		
		String keyword = request.getParameter("keyword");
		if(keyword == null) {
			keyword = "";
		}
		
				
		
		BoardDAO dao = new BoardDAO();
		int result = dao.delete(num, pw);
		
		if(result > 0) {
			return new CommandAction(true, "/board/list.do?curPage="+curPage+"&criteria="+criteria+"&keyword="+keyword);
		}else {
			return new CommandAction(true, "/board/read.do?num="+num+"&curPage="+curPage+"&criteria="+criteria+"&keyword="+keyword);
		}
		
	}

}
