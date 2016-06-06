/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package de.metas.session.jaxrs.impl;

import org.compiere.Adempiere;
import org.compiere.db.CConnection;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.session.jaxrs.IStatusService;
import de.metas.session.jaxrs.StatusServiceResult;

/**
 * Adempiere Status Bean
 *
 * @author Jorg Janke
 * @version $Id: StatusBean.java,v 1.3 2006/07/30 00:53:33 jjanke Exp $
 */

public class StatusService implements IStatusService
{
	private static final String ALLOW_CLIENT_QUERY_DB_PWD = "adempiere.client.getDBPwd";

	/** Logging */
	private static Logger log = LogManager.getLogger(StatusService.class);

	private int m_no = 0;
	//
	private int m_versionCount = 0;
	private int m_databaseCount = 0;

	private String getDateVersionInternal()
	{
		m_versionCount++;
		log.info("getDateVersion " + m_versionCount);
		return Adempiere.getDateVersion();
	}	// getDateVersion

	private String getDbHostInternal(CConnection c)
	{
		m_databaseCount++;
		log.info("getDbHost " + m_databaseCount);
		return c.getDbHost();
	}   // getDbHost

	private String getDbPwdInternal(CConnection c)
	{
		String f = System.getProperty(ALLOW_CLIENT_QUERY_DB_PWD);
		if ("false".equalsIgnoreCase(f))
			return "";

		return c.getDbPwd();
	}

	@Override
	public StatusServiceResult getStatus()
	{
		final StatusServiceResult r = new StatusServiceResult();

		final CConnection connection = CConnection.get();
		if (connection != null)
		{
			r.setDateVersion(getDateVersionInternal());
			r.setMainVersion(Adempiere.getMainVersion());
			r.setDbType(connection.getType());
			r.setDbHost(getDbHostInternal(connection));
			r.setDbPort(connection.getDbPort());
			r.setDbName(connection.getDbName());
			r.setConnectionURL(connection.getConnectionURL());
			r.setDbUid(connection.getDbUid());
			r.setDbPwd(getDbPwdInternal(connection));
		}
		r.setVersionCount(m_versionCount);
		r.setDataBaseCount(m_databaseCount);
		r.setStatus(getStatusSummary());
		return r;
	}	// getStatus

	/**
	 * String Representation
	 *
	 * @return info
	 */
	@Override
	public String toString()
	{
		return getStatusSummary();
	}

	private String getStatusSummary()
	{
		final StringBuffer sb = new StringBuffer("StatusBean[No=");
		sb.append(m_no)
				.append(",VersionCount=").append(m_versionCount)
				.append(",DatabaseCount=").append(m_versionCount)
				.append("]");
		return sb.toString();
	}

}
