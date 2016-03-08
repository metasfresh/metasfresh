/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package de.metas.session.jaxrs.impl;

import org.compiere.Adempiere;
import org.compiere.db.CConnection;
import org.compiere.util.CLogger;

import de.metas.session.jaxrs.IStatusService;


/**
 * 	Adempiere Status Bean
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: StatusBean.java,v 1.3 2006/07/30 00:53:33 jjanke Exp $
 */

public class StatusService implements IStatusService
{
	private static final String ALLOW_CLIENT_QUERY_DB_PWD = "adempiere.client.getDBPwd";

	/**	Logging				*/
	private static CLogger	log = CLogger.getCLogger(StatusService.class);

	private int				m_no = 0;
	//
	private int				m_versionCount = 0;
	private int				m_databaseCount = 0;


	/* (non-Javadoc)
	 * @see de.metas.session.jaxrs.IStatusService#getDateVersion()
	 */
	@Override
	public String getDateVersion()
	{
		m_versionCount++;
		log.info ("getDateVersion " + m_versionCount);
		return Adempiere.getDateVersion();
	}	//	getDateVersion

	/* (non-Javadoc)
	 * @see de.metas.session.jaxrs.IStatusService#getMainVersion()
	 */
	@Override
	public String getMainVersion()
	{
		return Adempiere.getMainVersion();
	}	//	getMainVersion

	/* (non-Javadoc)
	 * @see de.metas.session.jaxrs.IStatusService#getDbType()
	 */
	@Override
	public String getDbType()
	{
		return CConnection.get().getType();
	}   //  getDbType

	/* (non-Javadoc)
	 * @see de.metas.session.jaxrs.IStatusService#getDbHost()
	 */
	@Override
	public String getDbHost()
	{
		m_databaseCount++;
		log.info ("getDbHost " + m_databaseCount);
		return CConnection.get().getDbHost();
	}   //  getDbHost

	/* (non-Javadoc)
	 * @see de.metas.session.jaxrs.IStatusService#getDbPort()
	 */
	@Override
	public int getDbPort()
	{
		return CConnection.get().getDbPort();
	}   //  getDbPort

	/* (non-Javadoc)
	 * @see de.metas.session.jaxrs.IStatusService#getDbName()
	 */
	@Override
	public String getDbName()
	{
		return CConnection.get().getDbName();
	}   //  getDbSID

	/* (non-Javadoc)
	 * @see de.metas.session.jaxrs.IStatusService#getConnectionURL()
	 */
	@Override
	public String getConnectionURL()
	{
		return CConnection.get().getConnectionURL();
	}   //  getConnectionURL

	/* (non-Javadoc)
	 * @see de.metas.session.jaxrs.IStatusService#getDbUid()
	 */
	@Override
	public String getDbUid()
	{
		return CConnection.get().getDbUid();
	}   //  getDbUID

	/* (non-Javadoc)
	 * @see de.metas.session.jaxrs.IStatusService#getDbPwd()
	 */
	@Override
	public String getDbPwd()
	{
		String f = System.getProperty(ALLOW_CLIENT_QUERY_DB_PWD);
		if ("false".equalsIgnoreCase(f))
			return "";

		return CConnection.get().getDbPwd();
	}   //  getDbPWD

	/* (non-Javadoc)
	 * @see de.metas.session.jaxrs.IStatusService#getFwHost()
	 */
	@Override
	public String getFwHost()
	{
		return CConnection.get().getFwHost();
	}   //  getCMHost

	/* (non-Javadoc)
	 * @see de.metas.session.jaxrs.IStatusService#getFwPort()
	 */
	@Override
	public int getFwPort()
	{
		return CConnection.get().getFwPort();
	}   //  getCMPort


	/* (non-Javadoc)
	 * @see de.metas.session.jaxrs.IStatusService#getVersionCount()
	 */
	@Override
	public int getVersionCount()
	{
		return m_versionCount;
	}	//	getVersionCount

	/* (non-Javadoc)
	 * @see de.metas.session.jaxrs.IStatusService#getDatabaseCount()
	 */
	@Override
	public int getDatabaseCount()
	{
		return m_databaseCount;
	}	//	getVersionCount

	/* (non-Javadoc)
	 * @see de.metas.session.jaxrs.IStatusService#getStatus()
	 */
	@Override
	public String getStatus()
	{
		StringBuffer sb = new StringBuffer("StatusBean[No=");
		sb.append(m_no)
			.append(",VersionCount=").append(m_versionCount)
			.append(",DatabaseCount=").append(m_versionCount)
			.append("]");
		return sb.toString();
	}	//	getStatus


	/**
	 * 	String Representation
	 * 	@return info
	 */
	@Override
	public String toString()
	{
		return getStatus();
	}	//	toString



}
