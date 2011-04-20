package com.xmlmachines.beans;

import java.text.MessageFormat;

public class ApplicationServerConfig {

	private String hostName;
	private String hostPort;
	private String resourceFolderPath;

	/**
	 * Getters and Setters
	 */
	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getHostPort() {
		return hostPort;
	}

	public void setHostPort(String hostPort) {
		this.hostPort = hostPort;
	}

	public String getResourceFolderPath() {
		return resourceFolderPath;
	}

	public void setResourceFolderPath(String resourceFolderPath) {
		this.resourceFolderPath = resourceFolderPath;
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
				+ ((hostName == null) ? 0 : hostName.hashCode());
		result = prime * result
				+ ((hostPort == null) ? 0 : hostPort.hashCode());
		result = prime
				* result
				+ ((resourceFolderPath == null) ? 0 : resourceFolderPath
						.hashCode());
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
		ApplicationServerConfig other = (ApplicationServerConfig) obj;
		if (hostName == null) {
			if (other.hostName != null)
				return false;
		} else if (!hostName.equals(other.hostName))
			return false;
		if (hostPort == null) {
			if (other.hostPort != null)
				return false;
		} else if (!hostPort.equals(other.hostPort))
			return false;
		if (resourceFolderPath == null) {
			if (other.resourceFolderPath != null)
				return false;
		} else if (!resourceFolderPath.equals(other.resourceFolderPath))
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
		return MessageFormat
				.format("ApplicationServerConfig [hostName={0}, hostPort={1}, resourceFolderPath={2}]",
						hostName, hostPort, resourceFolderPath);
	}
}
