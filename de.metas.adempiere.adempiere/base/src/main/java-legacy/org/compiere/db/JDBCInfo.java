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
package org.compiere.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.compiere.util.CLogger;

/**
 *  JDBC Meta Info
 *
 *  @author     Jorg Janke
 *  @version    $Id: JDBCInfo.java,v 1.3 2006/07/30 00:55:13 jjanke Exp $
 */
public class JDBCInfo
{
	/**
	 *  Constructor
	 *  @param conn connection
	 *  @throws SQLException
	 */
	public JDBCInfo(Connection conn) throws SQLException
	{
		m_md = conn.getMetaData(); 
		log.info(m_md.getDatabaseProductName());
		log.config(m_md.getDatabaseProductVersion());
	//	log.config(m_md.getDatabaseMajorVersion() + "/" + m_md.getDatabaseMinorVersion());
		//
		log.info(m_md.getDriverName());
		log.config(m_md.getDriverVersion());
		log.config(m_md.getDriverMajorVersion() + "/" + m_md.getDriverMinorVersion());
		//
	//	log.info("JDBC = " + m_md.getJDBCMajorVersion() + "/" + m_md.getJDBCMinorVersion());
	}   //  JDBCInfo

	/**	Mata Data				*/
	private DatabaseMetaData	m_md = null;

	/**	Logger	*/
	private static CLogger	log	= CLogger.getCLogger (JDBCInfo.class);
	
	/**
	 * 	List All
	 */
	public void listAll()
	{
		try
		{
			listSchemas();
		}
		catch (Exception e)
		{
			log.severe(e.getMessage());
		}
		try
		{
			listCatalogs();
		}
		catch (Exception e)
		{
			log.severe(e.getMessage());
		}
		try
		{
			listTypes();
		}
		catch (Exception e)
		{
			log.severe(e.getMessage());
		}
	}	//	listAll
	
	/**
	 * 	List Catalogs
	 *  @throws SQLException
	 */
	public void listCatalogs() throws SQLException
	{
		log.info(m_md.getCatalogTerm() + " -> " + m_md.getCatalogSeparator());
		ResultSet rs = m_md.getCatalogs();
		while (rs.next())
		{
			dump(rs);
		}
	}	//	listCatalogs
	
	/**
	 * 	List Schemas
	 *  @throws SQLException
	 */
	public void listSchemas() throws SQLException
	{
		log.info(m_md.getSchemaTerm());
		ResultSet rs = m_md.getSchemas();
		while (rs.next())
		{
			dump(rs);
		}
	}	//	listSchemas
	
	/**
	 * 	List Types
	 *  @throws SQLException
	 */
	public void listTypes() throws SQLException
	{
		ResultSet rs = m_md.getTypeInfo();
		while (rs.next())
		{
			log.info("");
			dump(rs);
		}
	}	//	listTypes
	
	/**
	 * 	Dump the current row of a Result Set
	 *	@param rs result set
	 *  @throws SQLException
	 */
	public static void dump(ResultSet rs) throws SQLException
	{
		ResultSetMetaData md = rs.getMetaData();
		for (int i = 0; i < md.getColumnCount(); i++)
		{
			int index = i + 1;
			String info = md.getColumnLabel(index);
			String name = md.getColumnName(index);
			if (info == null)
				info = name;
			else if (name != null && !name.equals(info))
				info += " (" + name + ")";
			info += " = " 
				+ rs.getString(index);
			info += " [" + md.getColumnTypeName(index) 
				+ "(" + md.getPrecision(index);
			if (md.getScale(index) != 0)
				info += "," + md.getScale(index);
			info += ")]"; 
			log.fine(info);
		}
	}	//	dump
}   //  JDBCInfo
