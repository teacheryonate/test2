package kr.co.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.domain.BoardDTO;
import kr.co.domain.CommandAction;
import kr.co.domain.SearchPageTO;
import kr.co.repository.BoardDAO;

public class SearchCommand implements Command {

	@Override
	public CommandAction execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		int curPage = 1;
		
		String scurPage = request.getParameter("curPage");
		
		if(scurPage != null) {
			curPage = Integer.parseInt(scurPage);
		}
		
		
		String criteria = request.getParameter("criteria");
		String keyword = request.getParameter("keyword");
		
		BoardDAO dao = new BoardDAO();
		SearchPageTO<BoardDTO> pt = dao.search(curPage, criteria, keyword);
		
		request.setAttribute("pt", pt);
		
			
		return new CommandAction(false, 
				"/jsp/board/search.jsp?criteria="+criteria+"&keyword="+keyword);
	}

}
