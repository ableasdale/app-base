package com.xmlmachines.beans;

/**
 * Describing the necessary parameters to enable a connection to a database
 * 
 * @author alexbleasdale
 * 
 */
public class Config {

	private String serverName;
	private String databaseName;
	private String userName;
	private String password;
	private String xccPort;
	private String jerseyPort;
	private String baseFolderPath;

	/**
	 * Getter / Setter methods
	 */
	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getXccPort() {
		return xccPort;
	}

	public void setXccPort(String xccPort) {
		this.xccPort = xccPort;
	}

	public String getJerseyPort() {
		return jerseyPort;
	}

	public void setJerseyPort(String jerseyPort) {
		this.jerseyPort = jerseyPort;
	}

	public void setBaseFolderPath(String baseFolderPath) {
		this.baseFolderPath = baseFolderPath;
	}

	public String getBaseFolderPath() {
		return baseFolderPath;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((baseFolderPath == null) ? 0 : baseFolderPath.hashCode());
		result = prime * result
				+ ((databaseName == null) ? 0 : databaseName.hashCode());
		result = prime * result
				+ ((jerseyPort == null) ? 0 : jerseyPort.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result
				+ ((serverName == null) ? 0 : serverName.hashCode());
		result = prime * result
				+ ((userName == null) ? 0 : userName.hashCode());
		result = prime * result + ((xccPort == null) ? 0 : xccPort.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Config other = (Config) obj;
		if (baseFolderPath == null) {
			if (other.baseFolderPath != null)
				return false;
		} else if (!baseFolderPath.equals(other.baseFolderPath))
			return false;
		if (databaseName == null) {
			if (other.databaseName != null)
				return false;
		} else if (!databaseName.equals(other.databaseName))
			return false;
		if (jerseyPort == null) {
			if (other.jerseyPort != null)
				return false;
		} else if (!jerseyPort.equals(other.jerseyPort))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (serverName == null) {
			if (other.serverName != null)
				return false;
		} else if (!serverName.equals(other.serverName))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		if (xccPort == null) {
			if (other.xccPort != null)
				return false;
		} else if (!xccPort.equals(other.xccPort))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Config [baseFolderPath=" + baseFolderPath + ", databaseName="
				+ databaseName + ", jerseyPort=" + jerseyPort + ", password="
				+ password + ", serverName=" + serverName + ", userName="
				+ userName + ", xccPort=" + xccPort + "]";
	}

}