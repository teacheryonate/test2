package kr.co.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.domain.BoardDTO;
import kr.co.domain.CommandAction;
import kr.co.repository.BoardDAO;

public class Board_InsertCommand implements Command {

	@Override
	public CommandAction execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String author = request.getParameter("author");
		String spw = request.getParameter("pw");
		int pw = Integer.parseInt(spw);
		String title = request.getParameter("title");
		String content = request.getParameter("content");

		BoardDTO dto = new BoardDTO(-1, author, title, content, pw, null, 0, 0, 0, 0);
		BoardDAO dao = new BoardDAO();
		dao.insert(dto);

		return new CommandAction(true, "/board/list.do");
	}

}
