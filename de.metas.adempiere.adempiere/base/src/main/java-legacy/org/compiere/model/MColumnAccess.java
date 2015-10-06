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
import java.util.logging.Level;

import org.compiere.util.DB;
import org.compiere.util.Msg;

/**
 *	Column Access Model
 *	
 *  @author Jorg Janke
 *  @version $Id: MColumnAccess.java,v 1.3 2006/07/30 00:54:54 jjanke Exp $
 */
public class MColumnAccess extends X_AD_Column_Access
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2362624234744824977L;

	/**
	 * 	Persistency Constructor
	 *	@param ctx context
	 *	@param ignored ignored
	 *	@param trxName transaction
	 */
	public MColumnAccess (Properties ctx, int ignored, String trxName)
	{
		super(ctx, 0, trxName);
		if (ignored != 0)
			throw new IllegalArgumentException("Multi-Key");
	}	//	MColumnAccess

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MColumnAccess(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MColumnAccess

	
	/**
	 * 	String Representation
	 *	@return info
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer("MColumnAccess[");
		sb.append("AD_Role_ID=").append(getAD_Role_ID())
			.append(",AD_Table_ID=").append(getAD_Table_ID())
			.append(",AD_Column_ID=").append(getAD_Column_ID())
			.append(",Exclude=").append(isExclude());
		sb.append("]");
		return sb.toString();
	} 	//	toString

	/**
	 * 	Extended String Representation.
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
			.append(Msg.translate(ctx, "AD_Column_ID"))
			.append("=").append(getColumnName(ctx))
			.append(" (").append(Msg.translate(ctx, "IsReadOnly")).append("=").append(isReadOnly())
			.append(") - ").append(isExclude() ?  ex : in);
		return sb.toString();
	}	//	toStringX

	/**	TableName			*/
	private String		m_tableName;
	/**	ColumnName			*/
	private String		m_columnName;

	/**
	 * 	Get Table Name
	 *	@param ctx context for translatioin
	 *	@return table name
	 */
	public String getTableName (Properties ctx)
	{
		if (m_tableName == null)
			getColumnName(ctx);
		return m_tableName;
	}	//	getTableName

	/**
	 * 	Get Column Name
	 *	@param ctx context for translatioin
	 *	@return column name
	 */
	public String getColumnName (Properties ctx)
	{
		if (m_columnName == null)
		{
			String sql = "SELECT t.TableName,c.ColumnName, t.AD_Table_ID "
				+ "FROM AD_Table t INNER JOIN AD_Column c ON (t.AD_Table_ID=c.AD_Table_ID) "
				+ "WHERE AD_Column_ID=?";
			PreparedStatement pstmt = null;
			try
			{
				pstmt = DB.prepareStatement(sql, get_TrxName());
				pstmt.setInt(1, getAD_Column_ID());
				ResultSet rs = pstmt.executeQuery();
				if (rs.next())
				{
					m_tableName = rs.getString(1);
					m_columnName = rs.getString(2);
					if (rs.getInt(3) != getAD_Table_ID())
						log.log(Level.SEVERE, "AD_Table_ID inconsistent - Access=" + getAD_Table_ID() + " - Table=" + rs.getInt(3));
				}
				rs.close();
				pstmt.close();
				pstmt = null;
			}
			catch (Exception e)
			{
				log.log(Level.SEVERE, sql, e);
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
			m_columnName = Msg.translate(ctx, m_columnName);
		}		
		return m_columnName;
	}	//	getColumnName

}	//	MColumnAccess
