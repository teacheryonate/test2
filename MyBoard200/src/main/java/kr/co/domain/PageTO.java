package kr.co.domain;

import java.util.List;

public class PageTO<T> {
	private int perPage = 10;
	private int amount;
	private int curPage;
	private int lineMax = 10;
	
	private int totalPage;
	private int startNum;
	private int endNum;
	
	private List<T> list;
	
	private int beginPageNum;
	private int finishPageNum;
	
	public PageTO() {
		curPage = 1;
		
		execute();
	}

	public PageTO(int curPage) {
		this.curPage = curPage;
		
		execute();
	}

	public int getPerPage() {
		return perPage;
	}

	public void setPerPage(int perPage) {
		this.perPage = perPage;
		
		execute();
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
		
		execute();
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
		
		execute();
	}

	public int getLineMax() {
		return lineMax;
	}

	public void setLineMax(int linMax) {
		this.lineMax = linMax;
		
		execute();
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getStartNum() {
		return startNum;
	}

	public void setStartNum(int startNum) {
		this.startNum = startNum;
	}

	public int getEndNum() {
		return endNum;
	}

	public void setEndNum(int endNum) {
		this.endNum = endNum;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getBeginPageNum() {
		return beginPageNum;
	}

	public void setBeginPageNum(int beginPageNum) {
		this.beginPageNum = beginPageNum;
	}

	public int getFinishPageNum() {
		return finishPageNum;
	}

	public void setFinishPageNum(int finishPageNum) {
		this.finishPageNum = finishPageNum;
	}
	
	private void execute() {
		totalPage = ((amount - 1) / perPage) + 1;
		startNum = (curPage - 1) * perPage + 1;
		endNum = curPage * perPage;
		if (endNum > amount) {
			endNum = amount;
		}
		
		beginPageNum = ((curPage-1)/lineMax )*lineMax  +1;
		finishPageNum = (((curPage-1)/lineMax )+1) * lineMax;
		if(finishPageNum > totalPage) {
			finishPageNum = totalPage;
		}
	}

}
