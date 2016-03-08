package de.metas.session.jaxrs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.adempiere.util.ISingletonService;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */
@Path("/status/")
public interface IStatusService extends ISingletonService
{
	/**
	 * 	Get Version (Date)
	 *  @return version e.g. 2002-09-02
	 */
	@GET
	@Path("getDateVersion")
	String getDateVersion();

	/**
	 * 	Get Main Version
	 *  @return main version - e.g. Version 2.4.3b
	 */
	@GET
	@Path("getMainVersion")
	String getMainVersion();

	/**
	 *  Get Database Type
	 *  @return Database Type
	 */
	@GET
	@Path("getDbType")
	String getDbType();

	/**
	 *  Get Database Host
	 *  @return Database Host Name
	 */
	@GET
	@Path("getDbHost")
	String getDbHost();

	/**
	 *  Get Database Port
	 *  @return Database Port
	 */
	@GET
	@Path("getDbPort")
	int getDbPort();

	/**
	 *  Get Database SID
	 *  @return Database SID
	 */
	@GET
	@Path("getDbName")
	String getDbName();

	/**
	 *  Get Database URL
	 *  @return Database URL
	 */
	@GET
	@Path("getConnectionURL")
	String getConnectionURL();

	/**
	 *  Get Database UID
	 *  @return Database User Name
	 */
	@GET
	@Path("getDbUid")
	String getDbUid();

	/**
	 *  Get Database PWD
	 *  @return Database User Password
	 */
	@GET
	@Path("getDbPwd")
	String getDbPwd();

	/**
	 *  Get Connection Manager Host
	 *  @return Connection Manager Host
	 */
	@GET
	@Path("getFwHost")
	String getFwHost();

	/**
	 *  Get Connection Manager Port
	 *  @return Connection Manager Port
	 */
	@GET
	@Path("getFwPort")
	int getFwPort();

	/**
	 * 	Get Version Count
	 * 	@return number of version inquiries
	 */
	@GET
	@Path("getVersionCount")
	int getVersionCount();

	/**
	 * 	Get Database Count
	 * 	@return number of database inquiries
	 */
	@GET
	@Path("getDatabaseCount")
	int getDatabaseCount();

	/**
	 * 	Describes the instance and its content for debugging purpose
	 * 	@return Debugging information about the instance and its content
	 */
	@GET
	@Path("getStatus")
	String getStatus();

}