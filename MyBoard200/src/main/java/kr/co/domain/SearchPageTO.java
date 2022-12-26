package kr.co.domain;

public class SearchPageTO<T> extends PageTO<T>{
	
	private String criteria;
	private String keyword;
	
	public SearchPageTO() {
		// TODO Auto-generated constructor stub
	}

	public SearchPageTO(String criteria, String keyword) {
		super(1);
		this.criteria = criteria;
		this.keyword = keyword;
	}
	
	public SearchPageTO(int curPage, 
			String criteria, String keyword) {
		super(curPage);
		this.criteria = criteria;
		this.keyword = keyword;
	}

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}


	
	
	
	

}
