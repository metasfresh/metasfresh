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
package org.compiere.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.DB;
import org.compiere.util.Msg;

/**
 *	Record Access Model
 *	
 *  @author Jorg Janke
 *  @version $Id: MRecordAccess.java,v 1.3 2006/07/30 00:58:37 jjanke Exp $
 */
public class MRecordAccess extends X_AD_Record_Access
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5115765616266528435L;

	/**
	 * 	Persistency Constructor
	 *	@param ctx context
	 *	@param ignored ignored
	 *	@param trxName transaction
	 */
	public MRecordAccess (Properties ctx, int ignored, String trxName)
	{
		super(ctx, 0, trxName);
		if (ignored != 0)
			throw new IllegalArgumentException("Multi-Key");
	}	//	MRecordAccess

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MRecordAccess (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MRecordAccess

	/**
	 * 	Full New Constructor
	 *	@param ctx context
	 *	@param AD_Role_ID role
	 *	@param AD_Table_ID table
	 *	@param Record_ID record
	 *	@param trxName transaction
	 */
	public MRecordAccess (Properties ctx, int AD_Role_ID, int AD_Table_ID, int Record_ID, String trxName)
	{
		super (ctx,0, trxName);
		setAD_Role_ID(AD_Role_ID);
		setAD_Table_ID(AD_Table_ID);
		setRecord_ID(Record_ID);
		//
		setIsExclude (true);
		setIsReadOnly (false);
		setIsDependentEntities(false);
	}	//	MRecordAccess

	//	Key Column Name			*/
	private String		m_keyColumnName = null;
	
	// /**
	// * Get Key Column Name
	// * @return Key Column Name
	// */
	// public String getKeyColumnName()
	// {
	// if (m_keyColumnName != null)
	// return m_keyColumnName;
	// //
	// String sql = "SELECT ColumnName "
	// + "FROM AD_Column "
	// + "WHERE AD_Table_ID=? AND IsKey='Y' AND IsActive='Y'";
	// PreparedStatement pstmt = null;
	// try
	// {
	// pstmt = DB.prepareStatement(sql, null);
	// pstmt.setInt(1, getAD_Table_ID());
	// ResultSet rs = pstmt.executeQuery();
	// while (rs.next())
	// {
	// String s = rs.getString(1);
	// if (m_keyColumnName == null)
	// m_keyColumnName = s;
	// else
	// log.error("More than one key = " + s);
	// }
	// rs.close();
	// pstmt.close();
	// pstmt = null;
	// }
	// catch (Exception e)
	// {
	// log.error(sql, e);
	// }
	// try
	// {
	// if (pstmt != null)
	// pstmt.close();
	// pstmt = null;
	// }
	// catch (Exception e)
	// {
	// pstmt = null;
	// }
	// if (m_keyColumnName == null)
	// log.error("Record Access requires Table with one key column");
	// //
	// return m_keyColumnName;
	// } // getKeyColumnName

	// /**
	// * Get Synonym of Column
	// * @return Synonym Column Name
	// */
	// public String getSynonym()
	// {
	// if ("AD_User_ID".equals(getKeyColumnName()))
	// return "SalesRep_ID";
	// else if ("C_ElementValue_ID".equals(getKeyColumnName()))
	// return "Account_ID";
	// //
	// return null;
	// } // getSynonym

	// /**
	// * Key Column has a Synonym
	// * @return true if Key Column has Synonym
	// */
	// public boolean isSynonym()
	// {
	// return getSynonym() == null;
	// } // isSynonym

	/**
	 * 	Is Read Write
	 *	@return rw - false if exclude
	 */
	public boolean isReadWrite()
	{
		if (isExclude())
			return false;
		return !super.isReadOnly();
	}	//	isReadWrite
	
	// /**
	// * Get Key Column Name with consideration of Synonym
	// * @param tableInfo
	// * @return key column name
	// */
	// public String getKeyColumnName (AccessSqlParser.TableInfo[] tableInfo)
	// {
	// String columnSyn = getSynonym();
	// if (columnSyn == null)
	// return m_keyColumnName;
	// // We have a synonym - ignore it if base table inquired
	// for (int i = 0; i < tableInfo.length; i++)
	// {
	// if (m_keyColumnName.equals("AD_User_ID"))
	// {
	// // List of tables where not to use SalesRep_ID
	// if (tableInfo[i].getTableName().equals("AD_User"))
	// return m_keyColumnName;
	// }
	// else if (m_keyColumnName.equals("AD_ElementValue_ID"))
	// {
	// // List of tables where not to use Account_ID
	// if (tableInfo[i].getTableName().equals("AD_ElementValue"))
	// return m_keyColumnName;
	// }
	// } // tables to be ignored
	// return columnSyn;
	// } // getKeyColumnInfo

	/**
	 * 	String Representation
	 *	@return info
	 */
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer("MRecordAccess[AD_Role_ID=")
			.append(getAD_Role_ID())
			.append(",AD_Table_ID=").append(getAD_Table_ID())
			.append(",Record_ID=").append(getRecord_ID())
			.append(",Active=").append(isActive())
			.append(",Exclude=").append(isExclude())
			.append(",ReadOnly=").append(super.isReadOnly())
			.append(",Dependent=").append(isDependentEntities())
			.append("]");
		return sb.toString();
	}	//	toString

	/**
	 * 	Extended String Representation
	 * 	@param ctx context
	 *	@return extended info
	 */
	public String toStringX (Properties ctx)
	{
		String in = Msg.getMsg(ctx, "Include");
		String ex = Msg.getMsg(ctx, "Exclude");
		StringBuffer sb = new StringBuffer();
		sb.append(Msg.translate(ctx, "AD_Table_ID"))
				.append("=").append(getTableName(ctx)).append(", ")
			.append(Msg.translate(ctx, "Record_ID"))
			.	append("=").append(getRecord_ID())
			.append(" - ").append(Msg.translate(ctx, "IsDependentEntities"))
				.append("=").append(isDependentEntities())
			.append(" (").append(Msg.translate(ctx, "IsReadOnly"))
				.append("=").append(super.isReadOnly())
			.append(") - ").append(isExclude() ?  ex : in);
		return sb.toString();
	}	//	toStringX

	/**	TableName			*/
	private String		m_tableName;

	/**
	 * 	Get Table Name
	 *	@param ctx context
	 *	@return table name
	 */
	public String getTableName (Properties ctx)
	{
		if (m_tableName == null)
		{
			String sql = "SELECT TableName FROM AD_Table WHERE AD_Table_ID=?";
			PreparedStatement pstmt = null;
			try
			{
				pstmt = DB.prepareStatement(sql, null);
				pstmt.setInt(1, getAD_Table_ID());
				ResultSet rs = pstmt.executeQuery();
				if (rs.next())
				{
					m_tableName = rs.getString(1);
				}
				rs.close();
				pstmt.close();
				pstmt = null;
			}
			catch (Exception e)
			{
				log.error(sql, e);
			}
			try
			{
				if (pstmt != null)
					pstmt.close();
				pstmt = null;
			}
			catch (Exception e)
			{
				pstmt = null;
			}
			//	Get Clear Text
			String realName = Msg.translate(ctx, m_tableName + "_ID");
			if (!realName.equals(m_tableName + "_ID"))
				m_tableName = realName;
		}		
		return m_tableName;
	}	//	getTableName

}	//	MRecordAccess
