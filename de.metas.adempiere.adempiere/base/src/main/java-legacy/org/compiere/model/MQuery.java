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

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.compiere.util.TimeUtil;
import org.compiere.util.ValueNamePair;

/**
 * Query Descriptor. Maintains restrictions (WHERE clause)
 *
 * @author Jorg Janke
 * @version $Id: MQuery.java,v 1.4 2006/07/30 00:58:04 jjanke Exp $
 * 
 * @author Teo Sarca <li>BF [ 2860022 ] MQuery.get() is generating restictions for unexisting column https://sourceforge.net/tracker/?func=detail&aid=2860022&group_id=176962&atid=879332
 */
public class MQuery implements Serializable
{
	/**
	 * Get Query from Parameter
	 *
	 * @param ctx context (to determine language)
	 * @param AD_PInstance_ID instance
	 * @param TableName table name
	 * @return where clause
	 */
	static public MQuery get(Properties ctx, int AD_PInstance_ID, String TableName)
	{
		s_log.info("AD_PInstance_ID=" + AD_PInstance_ID + ", TableName=" + TableName);
		MQuery query = new MQuery(TableName);
		// Temporary Tables - add qualifier (not displayed)
		boolean isTemporaryTable = false;
		MTable table = null;
		if (TableName.startsWith("T_"))
		{
			query.addRestriction(TableName + ".AD_PInstance_ID=" + AD_PInstance_ID);
			isTemporaryTable = true;
			table = MTable.get(ctx, TableName);
		}
		query.m_AD_PInstance_ID = AD_PInstance_ID;

		// How many rows do we have?
		String SQL = "SELECT COUNT(*) FROM AD_PInstance_Para WHERE AD_PInstance_ID=?";
		int rows = DB.getSQLValue(null, SQL, AD_PInstance_ID);

		if (rows < 1)
			return query;

		// Msg.getMsg(Env.getCtx(), "Parameter")
		boolean trl = !Env.isBaseLanguage(ctx, "AD_Process_Para");
		if (!trl)
			SQL = "SELECT ip.ParameterName,ip.P_String,ip.P_String_To,"			// 1..3
					+ "ip.P_Number,ip.P_Number_To,"									// 4..5
					+ "ip.P_Date,ip.P_Date_To, ip.Info,ip.Info_To, "				// 6..9
					+ "pp.Name, pp.IsRange "										// 10..11
					+ "FROM AD_PInstance_Para ip, AD_PInstance i, AD_Process_Para pp "
					+ "WHERE i.AD_PInstance_ID=ip.AD_PInstance_ID"
					+ " AND pp.AD_Process_ID=i.AD_Process_ID"
					+ " AND pp.ColumnName=ip.ParameterName"
					+ " AND pp.IsActive='Y'" // metas
					+ " AND ip.AD_PInstance_ID=?";
		else
			SQL = "SELECT ip.ParameterName,ip.P_String,ip.P_String_To, ip.P_Number,ip.P_Number_To,"
					+ "ip.P_Date,ip.P_Date_To, ip.Info,ip.Info_To, "
					+ "ppt.Name, pp.IsRange "
					+ "FROM AD_PInstance_Para ip, AD_PInstance i, AD_Process_Para pp, AD_Process_Para_Trl ppt "
					+ "WHERE i.AD_PInstance_ID=ip.AD_PInstance_ID"
					+ " AND pp.AD_Process_ID=i.AD_Process_ID"
					+ " AND pp.ColumnName=ip.ParameterName"
					+ " AND pp.IsActive='Y'" // metas
					+ " AND pp.AD_Process_Para_ID=ppt.AD_Process_Para_ID"
					+ " AND ip.AD_PInstance_ID=?"
					+ " AND ppt.AD_Language=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(SQL, null);
			pstmt.setInt(1, AD_PInstance_ID);
			if (trl)
				pstmt.setString(2, Env.getAD_Language(ctx));
			rs = pstmt.executeQuery();
			// all records
			for (int row = 0; rs.next(); row++)
			{
				if (row == rows)
				{
					s_log.log(Level.SEVERE, "(Parameter) - more rows than expected");
					break;
				}
				String ParameterName = rs.getString(1);
				String P_String = rs.getString(2);
				String P_String_To = rs.getString(3);
				//
				Double P_Number = null;
				double d = rs.getDouble(4);
				if (!rs.wasNull())
					P_Number = new Double(d);
				Double P_Number_To = null;
				d = rs.getDouble(5);
				if (!rs.wasNull())
					P_Number_To = new Double(d);
				//
				Timestamp P_Date = rs.getTimestamp(6);
				Timestamp P_Date_To = rs.getTimestamp(7);
				//
				String Info = rs.getString(8);
				String Info_To = rs.getString(9);
				//
				String Name = rs.getString(10);
				boolean isRange = "Y".equals(rs.getString(11));
				//
				s_log.fine(ParameterName + " S=" + P_String + "-" + P_String_To
						+ ", N=" + P_Number + "-" + P_Number_To + ", D=" + P_Date + "-" + P_Date_To
						+ "; Name=" + Name + ", Info=" + Info + "-" + Info_To + ", Range=" + isRange);
				//
				// Check if the parameter exists as column in our table.
				// This condition applies only to temporary tables - teo_sarca [ 2860022 ]
				if (isTemporaryTable && table != null && table.getColumn(ParameterName) == null)
				{
					s_log.info("Skip parameter " + ParameterName + " because there is no column in table " + TableName);
					continue;
				}

				// -------------------------------------------------------------
				if (P_String != null)
				{
					if (P_String_To == null)
					{
						if (P_String.indexOf('%') == -1)
							query.addRestriction(ParameterName, MQuery.EQUAL,
									P_String, Name, Info);
						else
							query.addRestriction(ParameterName, MQuery.LIKE,
									P_String, Name, Info);
					}
					else
						query.addRangeRestriction(ParameterName,
								P_String, P_String_To, Name, Info, Info_To);
				}
				// Number
				else if (P_Number != null || P_Number_To != null)
				{
					if (P_Number_To == null)
					{
						if (isRange)
							query.addRestriction(ParameterName, MQuery.GREATER_EQUAL,
									P_Number, Name, Info);
						else
							query.addRestriction(ParameterName, MQuery.EQUAL,
									P_Number, Name, Info);
					}
					else
					// P_Number_To != null
					{
						if (P_Number == null)
							query.addRestriction("TRUNC(" + ParameterName + ")", MQuery.LESS_EQUAL,
									P_Number_To, Name, Info);
						else
							query.addRangeRestriction(ParameterName,
									P_Number, P_Number_To, Name, Info, Info_To);
					}
				}
				// Date
				else if (P_Date != null || P_Date_To != null)
				{
					if (P_Date_To == null)
					{
						if (isRange)
							query.addRestriction("TRUNC(" + ParameterName + ")", MQuery.GREATER_EQUAL,
									P_Date, Name, Info);
						else
							query.addRestriction("TRUNC(" + ParameterName + ")", MQuery.EQUAL,
									P_Date, Name, Info);
					}
					else
					// P_Date_To != null
					{
						if (P_Date == null)
							query.addRestriction("TRUNC(" + ParameterName + ")", MQuery.LESS_EQUAL,
									P_Date_To, Name, Info);
						else
							query.addRangeRestriction("TRUNC(" + ParameterName + ")",
									P_Date, P_Date_To, Name, Info, Info_To);
					}
				}
			}

			// 03915 : Added to stop reports that get all parameters empty to filter by record ID
			// NOTE: this is affecting directly the logic from org.compiere.print.ReportEngine.get(Properties, ProcessInfo)
			if ((rows > 0) && (query.getRestrictionCount() == 0))
			{
				query.addRestriction("(1=1)");
			}
		}
		catch (SQLException e2)
		{
			s_log.log(Level.SEVERE, SQL, e2);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		s_log.info(query.toString());
		return query;
	}	// get

	/**
	 * Get Zoom Column Name. Converts Synonyms like SalesRep_ID to AD_User_ID
	 *
	 * @param columnName column name
	 * @return column name
	 */
	public static String getZoomColumnName(String columnName)
	{
		if (columnName == null)
			return null;
		if (columnName.equals("SalesRep_ID"))
			return "AD_User_ID";
		if (columnName.equals("C_DocTypeTarget_ID"))
			return "C_DocType_ID";
		if (columnName.equals("Bill_BPartner_ID"))
			return "C_BPartner_ID";
		if (columnName.equals("Bill_Location_ID"))
			return "C_BPartner_Location_ID";
		if (columnName.equals("Account_ID"))
			return "C_ElementValue_ID";
		if (columnName.equals("C_LocFrom_ID") || columnName.equals("C_LocTo_ID"))
			return "C_Location_ID";
		// Fix "*_To" columns
		if (columnName.toUpperCase().endsWith("TO_ID"))
		{
			return columnName.substring(0, columnName.length() - 5) + "_ID";
		}
		if (columnName.toUpperCase().endsWith("_TO_ID"))
		{
			return columnName.substring(0, columnName.length() - 6) + "_ID";
		}
		if (columnName.equals("AD_OrgBP_ID") || columnName.equals("AD_OrgTrx_ID"))
			return "AD_Org_ID";
		if (columnName.endsWith("_Parent_ID")) // metas
			return columnName.substring(0, columnName.length() - "_Parent_ID".length()) + "_ID"; // metas
		// See also GridTab.validateQuery
		//
		return columnName;
	}	// getZoomColumnName

	/**
	 * Derive Zoom Table Name from column name. (e.g. drop _ID)
	 *
	 * @param columnName column name
	 * @return table name
	 */
	public static String getZoomTableName(String columnName)
	{
		String tableName = getZoomColumnName(columnName);
		int index = tableName.lastIndexOf("_ID");
		if (index != -1)
			return tableName.substring(0, index);
		return tableName;
	}	// getZoomTableName

	/*************************************************************************
	 * Create simple Equal Query. Creates columnName=value or columnName='value'
	 * 
	 * @param columnName columnName
	 * @param value value
	 * @return quary
	 */
	public static MQuery getEqualQuery(String columnName, Object value)
	{
		MQuery query = new MQuery();
		query.addRestriction(columnName, EQUAL, value);
		query.setRecordCount(1);	// guess
		return query;
	}	// getEqualQuery

	/**
	 * Create simple Equal Query. Creates columnName=value
	 * 
	 * @param columnName columnName
	 * @param value value
	 * @return quary
	 */
	public static MQuery getEqualQuery(String columnName, int value)
	{
		MQuery query = new MQuery();
		if (columnName.endsWith("_ID"))
			query.setTableName(columnName.substring(0, columnName.length() - 3));
		query.addRestriction(columnName, EQUAL, new Integer(value));
		query.setRecordCount(1);	// guess
		return query;
	}	// getEqualQuery

	/**
	 * Create No Record query.
	 * 
	 * @param tableName table name
	 * @param newRecord new Record Indicator (2=3)
	 * @return query
	 */
	public static MQuery getNoRecordQuery(String tableName, boolean newRecord)
	{
		MQuery query = new MQuery(tableName);
		if (newRecord)
			query.addRestriction(NEWRECORD);
		else
			query.addRestriction("1=2");
		query.setRecordCount(0);
		return query;
	}	// getNoRecordQuery

	/** Static Logger */
	private static CLogger s_log = CLogger.getCLogger(MQuery.class);

	/**************************************************************************
	 * Constructor w/o table name
	 */
	public MQuery()
	{
	}	// MQuery

	/**
	 * Constructor
	 * 
	 * @param TableName Table Name
	 */
	public MQuery(String TableName)
	{
		m_TableName = TableName;
	}	// MQuery

	/**
	 * Constructor get TableNAme from Table
	 * 
	 * @param AD_Table_ID Table_ID
	 */
	public MQuery(int AD_Table_ID)
	{	// Use Client Context as r/o
		m_TableName = MTable.getTableName(Env.getCtx(), AD_Table_ID);
	}	// MQuery

	/** Serialization Info **/
	private static final long serialVersionUID = 4883859385509199305L;

	/** Table Name */
	private String m_TableName = "";
	/** PInstance */
	private int m_AD_PInstance_ID = 0;
	/** List of Restrictions */
	private ArrayList<Restriction> m_list = new ArrayList<Restriction>();
	/** Record Count */
	private int m_recordCount = 999999;
	/** New Record Query */
	private boolean m_newRecord = false;
	/** New Record String */
	private static final String NEWRECORD = "2=3";

	private String m_zoomTable;

	private String m_zoomColumn;

	private Object m_zoomValue;

	/**
	 * Get Record Count
	 *
	 * @return count - default 999999
	 */
	public int getRecordCount()
	{
		return m_recordCount;
	}	// getRecordCount

	/**
	 * Set Record Count
	 *
	 * @param count count
	 */
	public void setRecordCount(int count)
	{
		m_recordCount = count;
	}	// setRecordCount

	// Sys confing for getting the right trunc value for Timestamp entries

	public static final String SYSCONFIG_TruncValue = "org.compiere.model.MQuery_DateTrunc_Value";

	/** Equal */
	public static final String EQUAL = "=";
	/** Equal - 0 */
	public static final int EQUAL_INDEX = 0;
	/** Not Equal */
	public static final String NOT_EQUAL = "!=";
	/** Like */
	public static final String LIKE = " LIKE ";
	/** Like (case-insensitive) */
	// metas-2009_0021_AP1_CR064
	public static final String LIKE_I = " LIKE /*icase*/ "; // metas-2009_0021_AP1_CR064
	/** Not Like */
	public static final String NOT_LIKE = " NOT LIKE ";
	/** Not Like (case-insensitive) */
	// metas-2009_0021_AP1_CR064
	public static final String NOT_LIKE_I = " NOT LIKE /*icase*/ "; // metas-2009_0021_AP1_CR064
	/** Greater */
	public static final String GREATER = ">";
	/** Greater Equal */
	public static final String GREATER_EQUAL = ">=";
	/** Less */
	public static final String LESS = "<";
	/** Less Equal */
	public static final String LESS_EQUAL = "<=";
	/** Between */
	public static final String BETWEEN = " BETWEEN ";
	/** Between - 9 */
	public static final int BETWEEN_INDEX = 9;

	/** Operators for Strings */
	public static final ValueNamePair[] OPERATORS = new ValueNamePair[] {
			new ValueNamePair(EQUAL, " = "),		// 0
			new ValueNamePair(NOT_EQUAL, " != "),	// 1
			new ValueNamePair(LIKE, " ~ "), 	// 2
			new ValueNamePair(LIKE_I, " ~~ "), // metas: c.ghita@metas.ro //3
			new ValueNamePair(NOT_LIKE, " !~ "),	// 4
			new ValueNamePair(GREATER, " > "),	// 5
			new ValueNamePair(GREATER_EQUAL, " >= "),	// 6
			new ValueNamePair(LESS, " < "), // 7
			new ValueNamePair(LESS_EQUAL, " <= "),	// 8
			new ValueNamePair(BETWEEN, " >-< ")	// 9
	};
	/** Operators for IDs */
	public static final ValueNamePair[] OPERATORS_ID = new ValueNamePair[] {
			new ValueNamePair(EQUAL, " = "),		// 0
			new ValueNamePair(NOT_EQUAL, " != ")
	};
	/** Operators for Boolean */
	public static final ValueNamePair[] OPERATORS_YN = new ValueNamePair[] {
			new ValueNamePair(EQUAL, " = ")
	};

	/*************************************************************************
	 * Add Restriction
	 * 
	 * @param ColumnName ColumnName
	 * @param Operator Operator, e.g. = != ..
	 * @param Code Code, e.g 0, All%
	 * @param InfoName Display Name
	 * @param InfoDisplay Display of Code (Lookup)
	 */
	public void addRestriction(String ColumnName, String Operator,
			Object Code, String InfoName, String InfoDisplay)
	{
		// metas: begin
		addRestriction(ColumnName, Operator, Code, InfoName, InfoDisplay, true);
	}

	public void addRestriction(String ColumnName, String Operator, Object Code,
			String InfoName, String InfoDisplay, boolean andCondition)
	{
		// metas: end
		Restriction r = new Restriction(ColumnName, Operator,
				Code, InfoName, InfoDisplay);
		r.andCondition = andCondition; // metas
		m_list.add(r);
	}	// addRestriction

	/**
	 * Add Restriction
	 * 
	 * @param ColumnName ColumnName
	 * @param Operator Operator, e.g. = != ..
	 * @param Code Code, e.g 0, All%
	 */
	public void addRestriction(String ColumnName, String Operator,
			Object Code)
	{
		// metas: begin
		addRestriction(ColumnName, Operator, Code, true);
	}

	public void addRestriction(String ColumnName, String Operator, Object Code, boolean andCondition)
	{
		// metas: end
		Restriction r = new Restriction(ColumnName, Operator,
				Code, null, null);
		r.andCondition = andCondition; // metas
		m_list.add(r);
	}	// addRestriction

	/**
	 * Add Restriction
	 * 
	 * @param ColumnName ColumnName
	 * @param Operator Operator, e.g. = != ..
	 * @param Code Code, e.g 0
	 */
	public void addRestriction(String ColumnName, String Operator,
			int Code)
	{
		// metas: begin
		addRestriction(ColumnName, Operator, Code, true);
	}

	public void addRestriction(String ColumnName, String Operator, int Code, boolean andCondition)
	{
		// metas: end
		Restriction r = new Restriction(ColumnName, Operator,
				new Integer(Code), null, null);
		r.andCondition = andCondition; // metas
		m_list.add(r);
	}	// addRestriction

	/**
	 * Add Range Restriction (BETWEEN)
	 * 
	 * @param ColumnName ColumnName
	 * @param Code Code, e.g 0, All%
	 * @param Code_to Code, e.g 0, All%
	 * @param InfoName Display Name
	 * @param InfoDisplay Display of Code (Lookup)
	 * @param InfoDisplay_to Display of Code (Lookup)
	 */
	public void addRangeRestriction(String ColumnName,
			Object Code, Object Code_to,
			String InfoName, String InfoDisplay, String InfoDisplay_to)
	{
		// metas: begin
		addRangeRestriction(ColumnName, Code, Code_to, InfoName, InfoDisplay, InfoDisplay_to, true);
	}

	public void addRangeRestriction(String ColumnName,
			Object Code, Object Code_to,
			String InfoName, String InfoDisplay, String InfoDisplay_to,
			boolean andCondition)
	{
		// metas: end
		Restriction r = new Restriction(ColumnName, Code, Code_to,
				InfoName, InfoDisplay, InfoDisplay_to);
		r.andCondition = andCondition; // metas
		m_list.add(r);
	}	// addRestriction

	/**
	 * Add Range Restriction (BETWEEN)
	 * 
	 * @param ColumnName ColumnName
	 * @param Code Code, e.g 0, All%
	 * @param Code_to Code, e.g 0, All%
	 */
	public void addRangeRestriction(String ColumnName,
			Object Code, Object Code_to)
	{
		// metas: begin
		addRangeRestriction(ColumnName, Code, Code_to, true);
	}

	public void addRangeRestriction(String ColumnName,
			Object Code, Object Code_to,
			boolean andCondition)
	{
		// metas: end
		Restriction r = new Restriction(ColumnName, Code, Code_to,
				null, null, null);
		r.andCondition = andCondition; // metas
		m_list.add(r);
	}	// addRestriction

	/**
	 * Add Restriction
	 * 
	 * @param r Restriction
	 */
	protected void addRestriction(Restriction r)
	{
		m_list.add(r);
	}	// addRestriction

	/**
	 * Add Restriction
	 * 
	 * @param whereClause SQL WHERE clause
	 */
	public void addRestriction(String whereClause)
	{
		// metas: begin
		addRestriction(whereClause, true);
	}

	public void addRestriction(String whereClause, boolean andCondition)
	{
		// metas: end
		if (whereClause == null || whereClause.trim().length() == 0)
			return;
		Restriction r = new Restriction(whereClause);
		r.andCondition = andCondition; // metas
		m_list.add(r);
		m_newRecord = whereClause.equals(NEWRECORD);
	}	// addRestriction

	/**
	 * New Record Query
	 *
	 * @return true if new nercord query
	 */
	public boolean isNewRecordQuery()
	{
		return m_newRecord;
	}	// isNewRecord

	/*************************************************************************
	 * Create the resulting Query WHERE Clause
	 * 
	 * @return Where Clause
	 */
	public String getWhereClause()
	{
		return getWhereClause(false);
	}	// getWhereClause

	/**
	 * Create the resulting Query WHERE Clause
	 * 
	 * @param fullyQualified fully qualified Table.ColumnName
	 * @return Where Clause
	 */
	public String getWhereClause(boolean fullyQualified)
	{
		boolean qualified = fullyQualified;
		if (qualified && (m_TableName == null || m_TableName.length() == 0))
			qualified = false;
		//
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < m_list.size(); i++)
		{
			Restriction r = (Restriction)m_list.get(i);
			if (i != 0)
				sb.append(r.andCondition ? " AND " : " OR ");
			if (qualified)
				sb.append(r.getSQL(m_TableName));
			else
				sb.append(r.getSQL(null));
		}
		return sb.toString();
	}	// getWhereClause

	/**
	 * Get printable Query Info
	 *
	 * @return info
	 */
	public String getInfo()
	{
		StringBuffer sb = new StringBuffer();
		if (m_TableName != null)
			sb.append(m_TableName).append(": ");
		//
		for (int i = 0; i < m_list.size(); i++)
		{
			Restriction r = (Restriction)m_list.get(i);
			if (i != 0)
				sb.append(r.andCondition ? " AND " : " OR ");
			//
			sb.append(r.getInfoName())
					.append(r.getInfoOperator())
					.append(r.getInfoDisplayAll());
		}
		return sb.toString();
	}	// getInfo

	/**
	 * Create Query WHERE Clause. Not fully qualified
	 * 
	 * @param index restriction index
	 * @return Where Clause or "" if not valid
	 */
	public String getWhereClause(int index)
	{
		StringBuffer sb = new StringBuffer();
		if (index >= 0 && index < m_list.size())
		{
			Restriction r = (Restriction)m_list.get(index);
			sb.append(r.getSQL(null));
		}
		return sb.toString();
	}	// getWhereClause

	/**
	 * Get Restriction Count
	 * 
	 * @return number of restricctions
	 */
	public int getRestrictionCount()
	{
		return m_list.size();
	}	// getRestrictionCount

	/**
	 * Is Query Active
	 * 
	 * @return true if number of restrictions > 0
	 */
	public boolean isActive()
	{
		return m_list.size() != 0;
	}	// isActive

	/**
	 * Get Table Name
	 * 
	 * @return Table Name
	 */
	public String getTableName()
	{
		return m_TableName;
	}	// getTableName

	/**
	 * Set Table Name
	 * 
	 * @param TableName Table Name
	 */
	public void setTableName(String TableName)
	{
		m_TableName = TableName;
	}	// setTableName

	/*************************************************************************
	 * Get ColumnName of index
	 * 
	 * @param index index
	 * @return ColumnName
	 */
	public String getColumnName(int index)
	{
		if (index < 0 || index >= m_list.size())
			return null;
		Restriction r = (Restriction)m_list.get(index);
		return r.ColumnName;
	}	// getColumnName

	/**
	 * Set ColumnName of index
	 * 
	 * @param index index
	 * @param ColumnName new column name
	 */
	protected void setColumnName(int index, String ColumnName)
	{
		if (index < 0 || index >= m_list.size())
			return;
		Restriction r = (Restriction)m_list.get(index);
		r.ColumnName = ColumnName;
	}	// setColumnName

	/**
	 * Get Operator of index
	 * 
	 * @param index index
	 * @return Operator
	 */
	public String getOperator(int index)
	{
		if (index < 0 || index >= m_list.size())
			return null;
		Restriction r = (Restriction)m_list.get(index);
		return r.Operator;
	}	// getOperator

	/**
	 * Get Operator of index
	 * 
	 * @param index index
	 * @return Operator
	 */
	public Object getCode(int index)
	{
		if (index < 0 || index >= m_list.size())
			return null;
		Restriction r = (Restriction)m_list.get(index);
		return r.Code;
	}	// getCode
	
	public Object getCodeTo(int index)
	{
		if (index < 0 || index >= m_list.size())
		{
			return null;
		}
		Restriction r = (Restriction)m_list.get(index);
		return r.Code_to;

	}

	/**
	 * Get Restriction Display of index
	 * 
	 * @param index index
	 * @return Restriction Display
	 */
	public String getInfoDisplay(int index)
	{
		if (index < 0 || index >= m_list.size())
			return null;
		Restriction r = (Restriction)m_list.get(index);
		return r.InfoDisplay;
	}	// getOperator

	/**
	 * Get TO Restriction Display of index
	 * 
	 * @param index index
	 * @return Restriction Display
	 */
	public String getInfoDisplay_to(int index)
	{
		if (index < 0 || index >= m_list.size())
			return null;
		Restriction r = (Restriction)m_list.get(index);
		return r.InfoDisplay_to;
	}	// getOperator

	/**
	 * Get Info Name
	 * 
	 * @param index index
	 * @return Info Name
	 */
	public String getInfoName(int index)
	{
		if (index < 0 || index >= m_list.size())
			return null;
		Restriction r = (Restriction)m_list.get(index);
		return r.InfoName;
	}	// getInfoName

	/**
	 * Get Info Operator
	 * 
	 * @param index index
	 * @return info Operator
	 */
	public String getInfoOperator(int index)
	{
		if (index < 0 || index >= m_list.size())
			return null;
		Restriction r = (Restriction)m_list.get(index);
		return r.getInfoOperator();
	}	// getInfoOperator

	/**
	 * Get Display with optional To
	 * 
	 * @param index index
	 * @return info display
	 */
	public String getInfoDisplayAll(int index)
	{
		if (index < 0 || index >= m_list.size())
			return null;
		Restriction r = (Restriction)m_list.get(index);
		return r.getInfoDisplayAll();
	}	// getInfoDisplay

	/**
	 * Is AND Join
	 * 
	 * @return true if it's AND Join
	 */
	// metas
	public boolean isAndCondition(int index)
	{
		return m_list.get(index).andCondition;
	}

	/**
	 * String representation
	 * 
	 * @return info
	 */
	@Override
	public String toString()
	{
		if (isActive())
			return getWhereClause(true);
		return "MQuery[" + m_TableName + ",Restrictions=0]";
	}	// toString

	/**
	 * Get Display Name
	 *
	 * @param ctx context
	 * @return display Name
	 */
	public String getDisplayName(Properties ctx)
	{
		String keyColumn = null;
		if (m_TableName != null)
			keyColumn = m_TableName + "_ID";
		else
			keyColumn = getColumnName(0);
		String retValue = Msg.translate(ctx, keyColumn);
		if (retValue != null && retValue.length() > 0)
			return retValue;
		return m_TableName;
	}	// getDisplayName

	/**
	 * Clone Query
	 * 
	 * @return Query
	 */
	public MQuery deepCopy()
	{
		MQuery newQuery = new MQuery(m_TableName);
		newQuery.m_AD_PInstance_ID = m_AD_PInstance_ID;
		newQuery.m_newRecord = m_newRecord;
		newQuery.m_recordCount = m_recordCount;
		newQuery.m_TableName = m_TableName;
		newQuery.m_zoomTable = m_zoomTable;
		newQuery.m_zoomColumn = m_zoomColumn;
		newQuery.m_zoomValue = m_zoomValue;
		for (int i = 0; i < m_list.size(); i++)
			newQuery.addRestriction((Restriction)m_list.get(i));
		return newQuery;
	}	// clone

	/**
	 * @return AD_PInstance_ID; this value is set if you created this query by using {@link #get(Properties, int, String)}
	 */
	public int getAD_PInstance_ID()
	{
		return m_AD_PInstance_ID;
	}

	/**
	 * 
	 * @param tableName
	 */
	public void setZoomTableName(String tableName)
	{
		m_zoomTable = tableName;
	}

	/**
	 * 
	 * @return zoom table name
	 */
	public String getZoomTableName()
	{
		return m_zoomTable;
	}

	/**
	 * 
	 * @param column
	 */
	public void setZoomColumnName(String column)
	{
		m_zoomColumn = column;
	}

	/**
	 * 
	 * @return zoom column name
	 */
	public String getZoomColumnName()
	{
		return m_zoomColumn;
	}

	/**
	 * 
	 * @param value
	 */
	public void setZoomValue(Object value)
	{
		m_zoomValue = value;
	}

	/**
	 * 
	 * @return zoom value, usually an integer
	 */
	public Object getZoomValue()
	{
		return m_zoomValue;
	}

	private boolean _forcedSOTrx = false;
	private boolean _isSOTrx = false;

	/** <code>true</code> if this query was issued by the user directly on not generated by system */
	private boolean _userQuery = false;

	public void setForceSOTrx(final boolean isSOTrx)
	{
		_forcedSOTrx = true;
		_isSOTrx = isSOTrx;
	}

	/**
	 * @return true / false for isSOTrx, but null if it's not forced
	 */
	public Boolean isSOTrxOrNull()
	{
		if (!_forcedSOTrx)
		{
			return null;
		}
		return _isSOTrx;
	}

	// metas
	public MQuery(MQuery query)
	{
		this();
		this.m_TableName = query.m_TableName;
		this.m_AD_PInstance_ID = query.m_AD_PInstance_ID;
		this.m_list = new ArrayList<Restriction>(query.m_list);
		this.m_recordCount = query.m_recordCount;
		this.m_newRecord = query.m_newRecord;
		this.m_zoomTable = query.m_zoomTable;
		this.m_zoomColumn = query.m_zoomColumn;
		this.m_zoomValue = query.m_zoomValue;
	}

	/**
	 * 
	 * @param userQuery
	 * @see #isUserQuery()
	 */
	public void setUserQuery(final boolean userQuery)
	{
		this._userQuery = userQuery;
	}

	/**
	 * Returns <code>true</code> if this query was issued by the user directly (i.e. NOT generated by system).
	 * 
	 * Example of queries which are user queries:
	 * <ul>
	 * <li>the query which is created when user is searching in a window/tab
	 * </ul>
	 * 
	 * Example of queries which are NOT user queries:
	 * <ul>
	 * <li>the query which is generated because of Zoom/Zoom accross user action.
	 * </ul>
	 * 
	 * NOTE: if the flag was not set programatically, it's default is <code>false</code> (i.e. not a user query).
	 * 
	 * @return <code>true</code> if this query was issued by the user directly
	 */
	public boolean isUserQuery()
	{
		return this._userQuery;
	}

}	// MQuery

/*****************************************************************************
 * Query Restriction
 */
class Restriction implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4521978087587321242L;

	/**
	 * Restriction
	 * 
	 * @param columnName ColumnName
	 * @param operator Operator, e.g. = != ..
	 * @param code Code, e.g 0, All%
	 * @param infoName Display Name
	 * @param infoDisplay Display of Code (Lookup)
	 */
	Restriction(String columnName, String operator,
			Object code, String infoName, String infoDisplay)
	{
		this.ColumnName = columnName.trim();
		if (infoName != null)
			InfoName = infoName;
		else
			InfoName = ColumnName;
		//
		this.Operator = operator;
		// Boolean
		if (code instanceof Boolean)
			Code = ((Boolean)code).booleanValue() ? "Y" : "N";
		else if (code instanceof KeyNamePair)
			Code = new Integer(((KeyNamePair)code).getKey());
		else if (code instanceof ValueNamePair)
			Code = ((ValueNamePair)code).getValue();
		else
			Code = code;
		// clean code
		if (Code instanceof String)
		{
			if (Code.toString().startsWith("'"))
				Code = Code.toString().substring(1);
			if (Code.toString().endsWith("'"))
				Code = Code.toString().substring(0, Code.toString().length() - 2);
		}
		if (infoDisplay != null)
			InfoDisplay = infoDisplay.trim();
		else if (code != null)
			InfoDisplay = code.toString();
	}	// Restriction

	/**
	 * Range Restriction (BETWEEN)
	 * 
	 * @param columnName ColumnName
	 * @param code Code, e.g 0, All%
	 * @param code_to Code, e.g 0, All%
	 * @param infoName Display Name
	 * @param infoDisplay Display of Code (Lookup)
	 * @param infoDisplay_to Display of Code (Lookup)
	 */
	Restriction(String columnName,
			Object code, Object code_to,
			String infoName, String infoDisplay, String infoDisplay_to)
	{
		this(columnName, MQuery.BETWEEN, code, infoName, infoDisplay);

		// Code_to
		Code_to = code_to;
		if (Code_to instanceof String)
		{
			if (Code_to.toString().startsWith("'"))
				Code_to = Code_to.toString().substring(1);
			if (Code_to.toString().endsWith("'"))
				Code_to = Code_to.toString().substring(0, Code_to.toString().length() - 2);
		}
		// InfoDisplay_to
		if (infoDisplay_to != null)
			InfoDisplay_to = infoDisplay_to.trim();
		else if (Code_to != null)
			InfoDisplay_to = Code_to.toString();
	}	// Restriction

	/**
	 * Create Restriction with direct WHERE clause
	 * 
	 * @param whereClause SQL WHERE Clause
	 */
	Restriction(final String whereClause)
	{
		super();
		this.DircetWhereClause = whereClause;
	}	// Restriction

	/** Direct Where Clause */
	protected String DircetWhereClause = null;
	/** Column Name */
	protected String ColumnName;
	/** Name */
	protected String InfoName;
	/** Operator */
	protected String Operator;
	/** SQL Where Code */
	protected Object Code;
	/** Info */
	protected String InfoDisplay;
	/** SQL Where Code To */
	protected Object Code_to;
	/** Info To */
	protected String InfoDisplay_to;
	/** And/Or Condition */
	protected boolean andCondition = true;

	/**
	 * Return SQL construct for this restriction
	 * 
	 * @param tableName optional table name
	 * @return SQL WHERE construct
	 */
	public String getSQL(String tableName)
	{
		if (DircetWhereClause != null)
		{
			return DircetWhereClause;
		}
		//

		String operatorToUse = Operator;
		Object code_ToUse = Code;
		Object codeTo_ToUse = Code_to;
		String columnNameToUse = ColumnName;

		if (MQuery.BETWEEN.equals(Operator) && Code instanceof String)
		{
			if (Code.toString().trim().isEmpty())
			{
				operatorToUse = MQuery.LESS_EQUAL;
				code_ToUse = Code_to;
			}
			if (Code_to instanceof String && Code_to.toString().trim().isEmpty())
			{
				operatorToUse = MQuery.GREATER_EQUAL;
			}
		}

		final String truncValue = Services.get(ISysConfigBL.class).getValue(MQuery.SYSCONFIG_TruncValue);

		// if trunc value is null, do nothing. This means the timestamp shall not be truncated
		if (truncValue != null && code_ToUse instanceof Timestamp)
		{

			code_ToUse = getTruncatedValue(code_ToUse, truncValue);

			if (codeTo_ToUse instanceof Timestamp)
			{
				codeTo_ToUse = getTruncatedValue(codeTo_ToUse, truncValue);
			}
		}

		StringBuffer sb = new StringBuffer();
		if (MQuery.LIKE_I.equals(operatorToUse) || MQuery.NOT_LIKE_I.equals(operatorToUse)
				|| (code_ToUse instanceof String && MQuery.BETWEEN.equals(operatorToUse))) // task 08757: also add the UPPER in case of BETWEEN operator
		{
			sb.append("UPPER("); // metas-2009_0021_AP1_CR064
		}
		if (tableName != null && tableName.length() > 0)
		{
			// Assumes - REPLACE(INITCAP(variable),'s','X') or UPPER(variable)
			int pos = ColumnName.lastIndexOf('(') + 1;	// including (
			int end = ColumnName.indexOf(')');
			// We have a Function in the ColumnName
			if (pos != -1 && end != -1)
			{
				// metas: cg : task 03914 : start
				if (pos > end)
				{
					// we have more then one function, such as
					// (CASE WHEN coalesce(Bill_BPartner_ID,0) = coalesce(C_BPartner_ID,0) THEN ''Y'' ELSE ''N'' END)
					// before this fix, in this case we had error ; something like : "Index was outside the bounds"

					String finalColumnName = new String(ColumnName);
					// set the real end
					end = finalColumnName.lastIndexOf(')');
					// strip the general brackets
					if (finalColumnName.startsWith("("))
					{
						finalColumnName = finalColumnName.substring(1, finalColumnName.lastIndexOf(')'));
						end = end - 1;
					}
					int prevPos = 0;
					pos = finalColumnName.indexOf('(');
					// process the entire column
					while (pos != -1)
					{
						if (prevPos == pos)
						{
							pos = finalColumnName.indexOf('(');
							continue;
						}
						sb.append(finalColumnName.substring(prevPos, pos + 1))
								.append(tableName).append(".");
						finalColumnName = finalColumnName.substring(pos + 1, finalColumnName.length());
						pos = finalColumnName.indexOf('(');
					}
					columnNameToUse = finalColumnName;

				}
				// metas: cg : task 03914 : end
				else
				{

					columnNameToUse = new StringBuffer().append(ColumnName.substring(0, pos))
							.append(tableName).append(".").append(ColumnName.substring(pos, end))
							.append(ColumnName.substring(end)).toString();
				}
			}
			else
			{
				columnNameToUse = new StringBuffer().append(tableName).append(".").append(ColumnName).toString();
			}
		}
		else
		{
			columnNameToUse = ColumnName;
		}

		if (codeTo_ToUse instanceof Timestamp)
		{
			// make sure if we deal with a timestamp the columnname is also truncated
			if (truncValue != null)
			{
				columnNameToUse = new StringBuffer()
						.append(" date_trunc")
						.append(" ( '")
						.append(truncValue)
						.append("', ")
						.append(columnNameToUse)
						.append(" ) ")
						.toString();

			}
		}
		sb.append(columnNameToUse);
		if ((MQuery.LIKE_I.equals(operatorToUse) || MQuery.NOT_LIKE_I.equals(operatorToUse))
				|| (code_ToUse instanceof String && MQuery.BETWEEN.equals(operatorToUse))) // task 08757: also close the ')' in case of BETWEEN operator
		{
			sb.append(")"); // metas-2009_0021_AP1_CR064
		}
		// NULL Operator
		if ((operatorToUse.equals("=") || operatorToUse.equals("!="))
				&& (code_ToUse == null
				|| "NULL".equals(code_ToUse.toString().toUpperCase())))
		{
			if (operatorToUse.equals("="))
			{
				sb.append(" IS NULL ");
			}
			else
			{
				sb.append(" IS NOT NULL ");
			}
		}
		else
		{
			// 08575
			// In case if Between for strings, we don't actually use the Between operator

			if (code_ToUse instanceof String && MQuery.BETWEEN.equals(operatorToUse))
			{
				sb.append(" >= ");
			}
			else
			{
				sb.append(operatorToUse);
			}

			if (code_ToUse instanceof String)
			{
				// metas-2009_0021_AP1_CR064: begin
				if (MQuery.LIKE_I.equals(operatorToUse)
						|| MQuery.NOT_LIKE_I.equals(operatorToUse)
						|| MQuery.BETWEEN.equals(operatorToUse))
				{
					String ucode = code_ToUse.toString().trim();
					// if (!ucode.startsWith("%"))
					// ucode = "%" + ucode;
					// if (!ucode.endsWith("%"))
					// ucode += "%";
					sb.append("UPPER(").append(DB.TO_STRING(ucode)).append(")");
				}
				else
					// metas-2009_0021_AP1_CR064: end
					sb.append(DB.TO_STRING(code_ToUse.toString()));
			}
			else if (code_ToUse instanceof Timestamp)
			{
				//sb.append(DB.TO_DATE((Timestamp)code_ToUse));
				sb.append(" TIMESTAMP '")
				.append((Timestamp)code_ToUse)
				.append("' ");
			}
			else
			{
				sb.append(code_ToUse);
			}

			// Between
			// if (Code_to != null && InfoDisplay_to != null)
			if (MQuery.BETWEEN.equals(operatorToUse))
			{
				sb.append(" AND ");
				if (codeTo_ToUse instanceof String)
				{
					final int codeToLength = ((String)Code_to).length();
					sb.append("UPPER( SUBSTRING (")
							.append(ColumnName + ",")
							.append(" 1,")
							.append(codeToLength) //
							.append(") )")
							.append(" <= ")
							.append("UPPER(").append(DB.TO_STRING((String)codeTo_ToUse)).append(")");

				}

				else if (codeTo_ToUse instanceof Timestamp)
				{
					//sb.append(DB.TO_DATE((Timestamp)codeTo_ToUse));
					
					sb.append(" TIMESTAMP '")
					.append((Timestamp)codeTo_ToUse)
					.append("' ");
				}
				else
				{
					sb.append(codeTo_ToUse);
				}
			}
		}
		return sb.toString();
	}	// getSQL

	/**
	 * In case the given code is instanceof Timestamp, truncate it by the given truncValue.
	 * In case the trunc value is null, leave it as it is
	 * 
	 * @param code
	 * @param truncValue
	 * @return
	 */
	private Object getTruncatedValue(final Object code, final String truncValue)
	{

		if (truncValue == null)
		{
			// do not truncate
			return code;
		}
		else
		{
			final Timestamp timestampCode = (Timestamp)code;

			// replace code_ToUse with the truncated version of the timestamp
			return TimeUtil.trunc(timestampCode, truncValue);
		}
	}

	/**
	 * Get String Representation
	 * 
	 * @return info
	 */
	@Override
	public String toString()
	{
		return getSQL(null);
	}	// toString

	/**
	 * Get Info Name
	 * 
	 * @return Info Name
	 */
	public String getInfoName()
	{
		return InfoName;
	}	// getInfoName

	/**
	 * Get Info Operator
	 * 
	 * @return info Operator
	 */
	public String getInfoOperator()
	{
		for (int i = 0; i < MQuery.OPERATORS.length; i++)
		{
			if (MQuery.OPERATORS[i].getValue().equals(Operator))
				return MQuery.OPERATORS[i].getName();
		}
		return Operator;
	}	// getInfoOperator

	/**
	 * Get Display with optional To
	 * 
	 * @return info display
	 */
	public String getInfoDisplayAll()
	{
		if (InfoDisplay_to == null)
			return InfoDisplay;
		StringBuffer sb = new StringBuffer(InfoDisplay);
		sb.append(" - ").append(InfoDisplay_to);
		return sb.toString();
	}	// getInfoDisplay
}	// Restriction
