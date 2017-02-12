package de.metas.session.jaxrs;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */
/**
 * A POJO that is filled by the metasfresh server and send to the client.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class StatusServiceResult
{
	private String dateVersion;
	private String mainVersion;
	private String dbType;
	private String dbHost;
	private int dbPort;
	private String dbName;
	private String connectionURL;
	private String dbUid;
	private String dbPwd;
	private int versionCount;
	private int dataBaseCount;
	private String status;

	/**
	 * 	Get Version (Date)
	 *  @return version e.g. 2002-09-02
	 */
	public String getDateVersion()
	{
		return dateVersion;
	}

	/**
	 * 	Get Main Version
	 *  @return main version - e.g. Version 2.4.3b
	 */
	public String getMainVersion()
	{
		return mainVersion;
	}

	/**
	 *  Get Database Type
	 *  @return Database Type
	 */
	public String getDbType()
	{
		return dbType;
	}

	/**
	 *  Get Database Host
	 *  @return Database Host Name
	 */
	public String getDbHost()
	{
		return dbHost;
	}

	/**
	 *  Get Database Port
	 *  @return Database Port
	 */
	public int getDbPort()
	{
		return dbPort;
	}

	/**
	 *  Get Database SID
	 *  @return Database SID
	 */
	public String getDbName()
	{
		return dbName;
	}

	/**
	 *  Get Database URL
	 *  @return Database URL
	 */
	public String getConnectionURL()
	{
		return connectionURL;
	}

	/**
	 *  Get Database UID
	 *  @return Database User Name
	 */
	public String getDbUid()
	{
		return dbUid;
	}

	/**
	 *  Get Database PWD
	 *  @return Database User Password
	 */
	public String getDbPwd()
	{
		return dbPwd;
	}

	/**
	 * 	Get Version Count
	 * 	@return number of version inquiries
	 */
	public int getVersionCount()
	{
		return versionCount;
	}

	/**
	 * 	Get Database Count
	 * 	@return number of database inquiries
	 */
	public int getDataBaseCount()
	{
		return dataBaseCount;
	}

	/**
	 * 	Describes the instance and its content for debugging purpose
	 * 	@return Debugging information about the instance and its content
	 */
	public String getStatus()
	{
		return status;
	}

	public void setDateVersion(String dateVersion)
	{
		this.dateVersion = dateVersion;
	}

	public void setMainVersion(String mainVersion)
	{
		this.mainVersion = mainVersion;
	}

	public void setDbType(String dbType)
	{
		this.dbType = dbType;
	}

	public void setDbHost(String dbHost)
	{
		this.dbHost = dbHost;
	}

	public void setDbPort(int dbPort)
	{
		this.dbPort = dbPort;
	}

	public void setDbName(String dbName)
	{
		this.dbName = dbName;
	}

	public void setConnectionURL(String connectionURL)
	{
		this.connectionURL = connectionURL;
	}

	public void setDbUid(String dbUid)
	{
		this.dbUid = dbUid;
	}

	public void setDbPwd(String dbPwd)
	{
		this.dbPwd = dbPwd;
	}

	public void setVersionCount(int versionCount)
	{
		this.versionCount = versionCount;
	}

	public void setDataBaseCount(int dataBaseCount)
	{
		this.dataBaseCount = dataBaseCount;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}
}
