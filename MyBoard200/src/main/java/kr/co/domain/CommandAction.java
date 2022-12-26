package kr.co.domain;

public class CommandAction {
	private boolean isRedirect;
	private String where;
	
	public CommandAction() {
	}

	public CommandAction(boolean isRedirect, String where) {
		this.isRedirect = isRedirect;
		this.where = where;
	}

	public boolean isRedirect() {
		return isRedirect;
	}

	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}

	public String getWhere() {
		return where;
	}

	public void setWhere(String where) {
		this.where = where;
	}
	
	
}
