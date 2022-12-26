package kr.co.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.domain.BoardDTO;
import kr.co.domain.CommandAction;
import kr.co.repository.BoardDAO;

public class Board_ReplyCommand implements Command {

	@Override
	public CommandAction execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String sorgNum = request.getParameter("orgNum");
		int orgNum = Integer.parseInt(sorgNum);
		
		
		String scurPage = request.getParameter("curPage");
		int curPage = Integer.parseInt(scurPage);
		
		
		
		String author = request.getParameter("author");
		
		String spw = request.getParameter("pw");
		int pw = Integer.parseInt(spw);
		
		String title = request.getParameter("title");
		
		String content = request.getParameter("content");
		
		BoardDTO dto = new BoardDTO(-1, author, 
				title, content, pw, null, -1, -1, -1, -1);
		
		
		
		String criteria = request.getParameter("criteria");
		if(criteria == null) {
			criteria = "title";
		}
		
		String keyword = request.getParameter("keyword");
		if(keyword == null) {
			keyword = "";
		}
		
		
		
		
		BoardDAO dao = new BoardDAO();
		dao.reply(orgNum, dto);	
		
		
		
		return new CommandAction(true, "/board/list.do?curPage="+curPage+"&criteria="+criteria+"&keyword="+keyword);
	}

}
