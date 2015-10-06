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
package org.compiere.session;

import java.util.logging.Level;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import javax.ejb.CreateException;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.compiere.Adempiere;
import org.compiere.Adempiere.RunMode;
import org.compiere.db.CConnection;
import org.compiere.interfaces.Status;
import org.compiere.interfaces.StatusLocal;
import org.compiere.interfaces.StatusRemote;
import org.compiere.util.CLogger;
import org.compiere.util.Env;


/**
 * 	Adempiere Status Bean
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: StatusBean.java,v 1.3 2006/07/30 00:53:33 jjanke Exp $
 */
@Stateless(mappedName=Status.JNDI_NAME, name=Status.EJB_NAME)
@Local({StatusLocal.class})
@Remote({StatusRemote.class})
@PermitAll
public class StatusBean implements StatusRemote, StatusLocal
{
	private static final String ALLOW_CLIENT_QUERY_DB_PWD = "adempiere.client.getDBPwd";
	
	/**	Logging				*/
	private static CLogger	log = CLogger.getCLogger(StatusBean.class);

	private static int		s_no = 0;
	private int				m_no = 0;
	//
	private int				m_versionCount = 0;
	private int				m_databaseCount = 0;


	/**
	 * 	Get Version (Date)
	 *  @return version e.g. 2002-09-02
	 */
	@Override
	public String getDateVersion()
	{
		m_versionCount++;
		log.info ("getDateVersion " + m_versionCount);
		return Adempiere.getDateVersion();
	}	//	getDateVersion

	/**
	 * 	Get Main Version
	 *  @return main version - e.g. Version 2.4.3b
	 */
	@Override
	public String getMainVersion()
	{
		return Adempiere.getMainVersion();
	}	//	getMainVersion

	/**
	 *  Get Database Type
	 *  @return Database Type
	 */
	@Override
	public String getDbType()
	{
		return CConnection.get().getType();
	}   //  getDbType

	/**
	 *  Get Database Host
	 *  @return Database Host Name
	 */
	@Override
	public String getDbHost()
	{
		m_databaseCount++;
		log.info ("getDbHost " + m_databaseCount);
		return CConnection.get().getDbHost();
	}   //  getDbHost

	/**
	 *  Get Database Port
	 *  @return Database Port
	 */
	@Override
	public int getDbPort()
	{
		return CConnection.get().getDbPort();
	}   //  getDbPort

	/**
	 *  Get Database SID
	 *  @return Database SID
	 */
	@Override
	public String getDbName()
	{
		return CConnection.get().getDbName();
	}   //  getDbSID

	/**
	 *  Get Database URL
	 *  @return Database URL
	 */
	@Override
	public String getConnectionURL()
	{
		return CConnection.get().getConnectionURL();
	}   //  getConnectionURL
	
	/**
	 *  Get Database UID
	 *  @return Database User Name
	 */
	@Override
	public String getDbUid()
	{
		return CConnection.get().getDbUid();
	}   //  getDbUID

	/**
	 *  Get Database PWD
	 *  @return Database User Password
	 */
	@Override
	public String getDbPwd()
	{
		String f = System.getProperty(ALLOW_CLIENT_QUERY_DB_PWD);
		if ("false".equalsIgnoreCase(f)) 
			return "";
		
		return CConnection.get().getDbPwd();
	}   //  getDbPWD

	/**
	 *  Get Connection Manager Host
	 *  @return Connection Manager Host
	 */
	@Override
	public String getFwHost()
	{
		return CConnection.get().getFwHost();
	}   //  getCMHost

	/**
	 *  Get Connection Manager Port
	 *  @return Connection Manager Port
	 */
	@Override
	public int getFwPort()
	{
		return CConnection.get().getFwPort();
	}   //  getCMPort

	
	/**************************************************************************
	 * 	Get Version Count
	 *  @ejb.interface-method view-type="both"
	 * 	@return number of version inquiries
	 */
	@Override
	public int getVersionCount()
	{
		return m_versionCount;
	}	//	getVersionCount

	/**
	 * 	Get Database Count
	 * 	@return number of database inquiries
	 */
	@Override
	public int getDatabaseCount()
	{
		return m_databaseCount;
	}	//	getVersionCount

	/**
	 * 	Describes the instance and its content for debugging purpose
	 * 	@return Debugging information about the instance and its content
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

	
	/**************************************************************************
	 * 	Create the Session Bean
	 */
	@PostConstruct
	public void ejbCreate() 
	{
		m_no = ++s_no;
		try
		{
			if (!Env.getSingleAdempiereInstance().startup(RunMode.BACKEND))
			{
				throw new CreateException(Adempiere.getName() + " could not start");
			}
		}
		catch (Exception ex)
		{
			log.log(Level.SEVERE, "", ex);
		//	throw new CreateException ();
		}
		log.info("#" + m_no + " - " + getStatus());
	}	//	ejbCreate
}	//	StatusBean
