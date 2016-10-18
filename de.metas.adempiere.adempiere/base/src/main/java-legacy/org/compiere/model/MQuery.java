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
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.stream.Stream;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.model.MQuery.Operator;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.TimeUtil;
import org.compiere.util.ValueNamePair;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import de.metas.logging.LogManager;

/**
 * Query Descriptor. Maintains restrictions (WHERE clause)
 *
 * @author Jorg Janke
 * @version $Id: MQuery.java,v 1.4 2006/07/30 00:58:04 jjanke Exp $
 * 
 * @author Teo Sarca <li>BF [ 2860022 ] MQuery.get() is generating restictions for unexisting column https://sourceforge.net/tracker/?func=detail&aid=2860022&group_id=176962&atid=879332
 */
@SuppressWarnings("serial")
public final class MQuery implements Serializable
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
		s_log.info("AD_PInstance_ID={}, TableName={}", AD_PInstance_ID, TableName);
		
		final MQuery query = new MQuery(TableName);
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
		String SQL = "SELECT COUNT(1) FROM AD_PInstance_Para WHERE AD_PInstance_ID=?";
		final int rows = DB.getSQLValue(ITrx.TRXNAME_None, SQL, AD_PInstance_ID);

		if (rows < 1)
			return query;

		// Msg.getMsg(Env.getCtx(), "Parameter")
		final boolean trl = !Env.isBaseLanguage(ctx, "AD_Process_Para");
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
					s_log.error("(Parameter) - more rows than expected");
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
				boolean isRange = DisplayType.toBoolean(rs.getString(11));
				
				//
				if(s_log.isDebugEnabled())
				{
					s_log.debug(ParameterName + " S=" + P_String + "-" + P_String_To
							+ ", N=" + P_Number + "-" + P_Number_To + ", D=" + P_Date + "-" + P_Date_To
							+ "; Name=" + Name + ", Info=" + Info + "-" + Info_To + ", Range=" + isRange);
				}
				
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
							query.addRestriction(ParameterName, Operator.EQUAL, P_String, Name, Info);
						else
							query.addRestriction(ParameterName, Operator.LIKE, P_String, Name, Info);
					}
					else
						query.addRangeRestriction(ParameterName, P_String, P_String_To, Name, Info, Info_To);
				}
				// Number
				else if (P_Number != null || P_Number_To != null)
				{
					if (P_Number_To == null)
					{
						if (isRange)
							query.addRestriction(ParameterName, Operator.GREATER_EQUAL, P_Number, Name, Info);
						else
							query.addRestriction(ParameterName, Operator.EQUAL, P_Number, Name, Info);
					}
					else
					// P_Number_To != null
					{
						if (P_Number == null)
							query.addRestriction("TRUNC(" + ParameterName + ")", Operator.LESS_EQUAL, P_Number_To, Name, Info);
						else
							query.addRangeRestriction(ParameterName, P_Number, P_Number_To, Name, Info, Info_To);
					}
				}
				// Date
				else if (P_Date != null || P_Date_To != null)
				{
					if (P_Date_To == null)
					{
						if (isRange)
							query.addRestriction("TRUNC(" + ParameterName + ")", Operator.GREATER_EQUAL, P_Date, Name, Info);
						else
							query.addRestriction("TRUNC(" + ParameterName + ")", Operator.EQUAL, P_Date, Name, Info);
					}
					else
					// P_Date_To != null
					{
						if (P_Date == null)
							query.addRestriction("TRUNC(" + ParameterName + ")", Operator.LESS_EQUAL, P_Date_To, Name, Info);
						else
							query.addRangeRestriction("TRUNC(" + ParameterName + ")", P_Date, P_Date_To, Name, Info, Info_To);
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
			s_log.error(SQL, e2);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		
		s_log.info("query: {}", query);
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
		query.addRestriction(columnName, Operator.EQUAL, value);
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
		query.addRestriction(columnName, Operator.EQUAL, new Integer(value));
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
		final MQuery query = tableName == null ? new MQuery() : new MQuery(tableName);
		if (newRecord)
			query.addRestriction(NEWRECORD);
		else
			query.addRestriction("1=2");
		query.setRecordCount(0);
		return query;
	}	// getNoRecordQuery
	
	public static MQuery getNoRecordQuery(final String tableName)
	{
		final boolean newRecord = false;
		return getNoRecordQuery(tableName, newRecord);
	}

	public static MQuery getNoRecordQuery()
	{
		final String tableName = null;
		final boolean newRecord = false;
		return getNoRecordQuery(tableName, newRecord);
	}


	/** Static Logger */
	private static final transient Logger s_log = LogManager.getLogger(MQuery.class);

	/**************************************************************************
	 * Constructor w/o table name
	 */
	public MQuery()
	{
		super();
	}	// MQuery

	/**
	 * Constructor
	 * 
	 * @param TableName Table Name
	 */
	public MQuery(final String TableName)
	{
		super();
		m_TableName = TableName;
	}	// MQuery

	/**
	 * NOTE: if possible, please use "TableName" constructor instead of this one!
	 * 
	 * @param AD_Table_ID Table_ID
	 */
	public MQuery(final int AD_Table_ID)
	{
		super();
		m_TableName = Services.get(IADTableDAO.class).retrieveTableName(AD_Table_ID);
	}	// MQuery

	/** Table Name */
	private String m_TableName = "";
	/** PInstance */
	private int m_AD_PInstance_ID = 0;
	/** List of Restrictions */
	private List<Restriction> m_list = new ArrayList<>();
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

	public static enum Operator
	{
		EQUAL("=", " = "),  //
		NOT_EQUAL("!=", " != "),  //
		LIKE(" LIKE ", " ~ "),  //
		/** Like (case-insensitive) */
		LIKE_I(" ILIKE /*icase*/ ", " ~~ "),  //
		NOT_LIKE(" NOT LIKE ", " !~ "),  //
		/** Not Like (case-insensitive) */
		NOT_LIKE_I(" NOT ILIKE /*icase*/ ", "!~~"),  // TODO hide it from FindPanel display!
		GREATER(">", " > "),  //
		GREATER_EQUAL(">=", " >= "),  //
		LESS("<", " < "),  //
		LESS_EQUAL("<=", " <= "),  //
		BETWEEN(" BETWEEN ", " >-< ") //
		;
		
		private final String code;
		private final String caption;

		Operator(final String code, final String caption)
		{
			this.code = code;
			this.caption = caption;
		}
		
		@Override
		public String toString()
		{
			return caption;
		}
		
		public String getCode()
		{
			return code;
		}
		
		public String getCaption()
		{
			return caption;
		}
		
		public boolean isRangeOperator()
		{
			return this == BETWEEN;
		}
		
		public static final Operator cast(final Object operatorObj)
		{
			return (Operator)operatorObj;
		}

		public static final Operator forCodeOrNull(final String code)
		{
			return operatorsByCode.get(code);
		}

		public static final Operator forCode(final String code)
		{
			final Operator op = operatorsByCode.get(code);
			if (op == null)
			{
				throw new NoSuchElementException("No operator found for: " + code);
			}
			return op;
		}
		
		private static final Map<String, Operator> operatorsByCode = Stream.of(values())
				.collect(GuavaCollectors.toImmutableMapByKey(op -> op.getCode()));
		
		public static final List<Operator> operatorsForBooleans = ImmutableList.of(EQUAL); 
		public static final List<Operator> operatorsForLookups = ImmutableList.of(EQUAL, NOT_EQUAL); 
	}

	/*************************************************************************
	 * Add Restriction
	 * 
	 * @param ColumnName ColumnName
	 * @param Operator Operator, e.g. = != ..
	 * @param Code Code, e.g 0, All%
	 * @param InfoName Display Name
	 * @param InfoDisplay Display of Code (Lookup)
	 */
	public void addRestriction(String ColumnName, Operator Operator, Object Code, String InfoName, String InfoDisplay)
	{
		final boolean andCondition = true;
		addRestriction(ColumnName, Operator, Code, InfoName, InfoDisplay, andCondition);
	}

	public void addRestriction(String ColumnName, Operator Operator, Object Code, String InfoName, String InfoDisplay, boolean joinAnd)
	{
		final Restriction r = new Restriction(joinAnd, ColumnName, Operator, Code, InfoName, InfoDisplay);
		m_list.add(r);
	}

	/**
	 * Add Restriction
	 * 
	 * @param ColumnName ColumnName
	 * @param operator Operator, e.g. = != ..
	 * @param Code Code, e.g 0, All%
	 */
	public void addRestriction(String ColumnName, Operator operator, Object Code)
	{
		final boolean andCondition = true;
		addRestriction(ColumnName, operator, Code, andCondition);
	}

	public void addRestriction(String ColumnName, Operator operator, Object Code, boolean joinAnd)
	{
		final String infoName = null;
		final String infoDisplay = null;
		Restriction r = new Restriction(joinAnd, ColumnName, operator, Code, infoName, infoDisplay);
		m_list.add(r);
	}

	/**
	 * Add Restriction
	 * 
	 * @param ColumnName ColumnName
	 * @param operator Operator, e.g. = != ..
	 * @param Code Code, e.g 0
	 */
	public void addRestriction(String ColumnName, Operator operator, int Code)
	{
		// metas: begin
		final boolean andCondition = true;
		addRestriction(ColumnName, operator, Code, andCondition);
	}

	public void addRestriction(String ColumnName, Operator operator, int Code, boolean joinAnd)
	{
		final String infoName = null;
		final String infoDisplay = null;
		final Restriction r = new Restriction(joinAnd, ColumnName, operator, Code, infoName, infoDisplay);
		m_list.add(r);
	}

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
		final boolean joinAnd = true;
		addRangeRestriction(ColumnName, Code, Code_to, InfoName, InfoDisplay, InfoDisplay_to, joinAnd);
	}

	public void addRangeRestriction(String ColumnName,
			Object Code, Object Code_to,
			String InfoName, String InfoDisplay, String InfoDisplay_to,
			boolean joinAnd)
	{
		final Restriction r = new Restriction(joinAnd, ColumnName, Operator.BETWEEN, Code, Code_to, InfoName, InfoDisplay, InfoDisplay_to);
		m_list.add(r);
	}	// addRestriction

	/**
	 * Add Range Restriction (BETWEEN)
	 * 
	 * @param ColumnName ColumnName
	 * @param Code Code, e.g 0, All%
	 * @param Code_to Code, e.g 0, All%
	 */
	public void addRangeRestriction(String ColumnName, Object Code, Object Code_to)
	{
		final boolean andCondition = true;
		addRangeRestriction(ColumnName, Code, Code_to, andCondition);
	}

	public void addRangeRestriction(String ColumnName, Object Code, Object Code_to, boolean joinAnd)
	{
		final String infoName = null;
		final String infoDisplay = null;
		final String infoDisplayTo = null;
		final Restriction r = new Restriction(joinAnd, ColumnName, Operator.BETWEEN, Code, Code_to, infoName, infoDisplay, infoDisplayTo);
		m_list.add(r);
	}	// addRestriction

	/**
	 * Add Restriction
	 * 
	 * @param r Restriction
	 */
	private void addRestriction(final Restriction r)
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
		final boolean andCondition = true;
		addRestriction(whereClause, andCondition);
	}

	public void addRestriction(String whereClause, boolean joinAnd)
	{
		if(Check.isEmpty(whereClause, true))
		{
			return;
		}
		
		final Restriction r = new Restriction(joinAnd, whereClause);
		m_list.add(r);
		m_newRecord = whereClause.equals(NEWRECORD);
	}

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
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < m_list.size(); i++)
		{
			Restriction r = m_list.get(i);
			if (i != 0)
				sb.append(r.isJoinAnd() ? " AND " : " OR ");
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
		StringBuilder sb = new StringBuilder();
		if (m_TableName != null)
			sb.append(m_TableName).append(": ");
		//
		for (int i = 0; i < m_list.size(); i++)
		{
			Restriction r = m_list.get(i);
			if (i != 0)
				sb.append(r.isJoinAnd() ? " AND " : " OR ");
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
		StringBuilder sb = new StringBuilder();
		if (index >= 0 && index < m_list.size())
		{
			Restriction r = m_list.get(index);
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
		return !m_list.isEmpty();
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
	
	public boolean isDirectWhereClause(final int index)
	{
		if (index < 0 || index >= m_list.size())
			return false;
		return m_list.get(index).isDirectWhereClause();
	}
	
	public String getDirectWhereClause(final int index)
	{
		if (index < 0 || index >= m_list.size())
			return null;
		return m_list.get(index).getDirectWhereClause();
	}


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
		Restriction r = m_list.get(index);
		return r.getColumnName();
	}	// getColumnName
	
	public boolean isJoinAnd(final int index)
	{
		if (index < 0 || index >= m_list.size())
			return true;
		return m_list.get(index).isJoinAnd();
	}

	/**
	 * Set ColumnName of index
	 * 
	 * @param index index
	 * @param ColumnName new column name
	 */
	protected void setColumnName(final int index, final String ColumnName)
	{
		if (index < 0 || index >= m_list.size())
		{
			s_log.warn("No query restriction found for index={} in {}. Skip setting ColumnName={}", index, this, ColumnName);
			return;
		}
		final Restriction r = m_list.get(index);
		r.setColumnName(ColumnName);
	}	// setColumnName

	/**
	 * Get Operator of index
	 * 
	 * @param index index
	 * @return Operator
	 */
	public Operator getOperator(int index)
	{
		if (index < 0 || index >= m_list.size())
			return null;
		Restriction r = m_list.get(index);
		return r.getOperator();
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
		Restriction r = m_list.get(index);
		return r.getCode();
	}	// getCode
	
	public Object getCodeTo(int index)
	{
		if (index < 0 || index >= m_list.size())
		{
			return null;
		}
		Restriction r = m_list.get(index);
		return r.getCodeTo();

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
		Restriction r = m_list.get(index);
		return r.getInfoDisplay();
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
		Restriction r = m_list.get(index);
		return r.getInfoDisplayTo();
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
		Restriction r = m_list.get(index);
		return r.getInfoName();
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
		Restriction r = m_list.get(index);
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
		Restriction r = m_list.get(index);
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
		return m_list.get(index).isJoinAnd();
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
	public String getDisplayName(final Properties ctx)
	{
		String keyColumn = null;
		if (m_TableName != null)
			keyColumn = m_TableName + "_ID";
		else
			keyColumn = getColumnName(0);
		String retValue = Services.get(IMsgBL.class).translate(ctx, keyColumn);
		if (retValue != null && retValue.length() > 0)
			return retValue;
		return m_TableName;
	}	// getDisplayName

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
	private MQuery(final MQuery query)
	{
		this();
		this.m_TableName = query.m_TableName;
		this.m_AD_PInstance_ID = query.m_AD_PInstance_ID;
		this.m_recordCount = query.m_recordCount;
		this.m_newRecord = query.m_newRecord;
		this.m_zoomTable = query.m_zoomTable;
		this.m_zoomColumn = query.m_zoomColumn;
		this.m_zoomValue = query.m_zoomValue;
		
		for (final Restriction restriction : query.m_list)
		{
			this.addRestriction(new Restriction(restriction));
		}
	}

	/**
	 * Clone Query
	 * 
	 * @return Query
	 */
	public MQuery deepCopy()
	{
		return new MQuery(this);
	}	// clone

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
@SuppressWarnings("serial")
/*package*/ final class Restriction implements Serializable
{
	/**
	 * Restriction constructor (without valueTo)
	 * 
	 * @param columnName ColumnName
	 * @param operator Operator, e.g. = != ..
	 * @param code Code, e.g 0, All%
	 * @param infoName Display Name
	 * @param infoDisplay Display of Code (Lookup)
	 */
	Restriction(final boolean joinAnd, final String columnName, final Operator operator, final Object code, final String infoName, final String infoDisplay)
	{
		this(
				joinAnd // joinAnd
				, columnName //
				, operator //
				, code //
				, null // code_to //
				, infoName //
				, infoDisplay //
				, null // infoDisplay_to //
		);
	}

	/**
	 * Restriction (full constructor)
	 * 
	 * @param columnName ColumnName
	 * @param code Code, e.g 0, All%
	 * @param code_to Code, e.g 0, All%
	 * @param infoName Display Name
	 * @param infoDisplay Display of Code (Lookup)
	 * @param infoDisplay_to Display of Code (Lookup)
	 */
	Restriction(
			final boolean joinAnd //
			, final String columnName //
			, final Operator operator //
			, final Object code, final Object code_to //
			, final String infoName //
			, final String infoDisplay, final String infoDisplay_to //
			)
	{
		super();
		
		//
		// Join
		this.joinAnd = joinAnd;
		
		//
		// ColumnName and column's display name
		this.columnName = columnName.trim();
		if (infoName != null)
			this.infoName = infoName;
		else
			this.infoName = columnName;
		
		//
		// Operator
		this.operator = operator;

		//
		// Value
		this.code = normalizeCode(code);
		this.infoDisplay = buildInfoDisplay(infoDisplay, this.code);
		
		//
		// ValueTo
		this.codeTo = normalizeCode(code_to);
		this.infoDisplayTo = buildInfoDisplay(infoDisplay_to, this.codeTo);

		//
		// Direct where clause
		this.directWhereClause = null;

	}	// Restriction

	/**
	 * Create Restriction with direct WHERE clause
	 * 
	 * @param whereClause SQL WHERE Clause
	 */
	Restriction(final boolean joinAnd, final String whereClause)
	{
		super();
		
		this.joinAnd = joinAnd;
		
		this.columnName = null;
		this.infoName = null;
		this.operator = null;
		
		this.code = null;
		this.infoDisplay = null;
		
		this.codeTo = null;
		this.infoDisplayTo = null;
		
		this.directWhereClause = whereClause;
	}	// Restriction
	
	Restriction(final Restriction from)
	{
		super();
		this.joinAnd = from.joinAnd;
		
		this.columnName = from.columnName;
		this.infoName = from.infoName;
		
		this.operator = from.operator;
		
		this.code = from.code;
		this.infoDisplay = from.infoDisplay;
		
		this.codeTo = from.codeTo;
		this.infoDisplayTo = from.infoDisplayTo;
		
		this.directWhereClause= from.directWhereClause;
	}

	/** And/Or Condition */
	private final boolean joinAnd;
	/** Column Name */
	private String columnName;
	/** Name */
	private final String infoName;
	/** Operator */
	private final Operator operator;
	/** SQL Where Code */
	private final Object code;
	/** {@link #code}'s user friendly representation */
	private final String infoDisplay;
	/** SQL Where Code To */
	private final Object codeTo;
	/** {@link #codeTo}'s user friendly representation */
	private final String infoDisplayTo;
	/** Direct Where Clause */
	private final String directWhereClause;
	
	private static final Object normalizeCode(final Object code)
	{
		Object codeNorm;
		if (code instanceof Boolean)
			codeNorm = DisplayType.toBooleanString((Boolean)code);
		else if (code instanceof KeyNamePair)
			codeNorm = new Integer(((KeyNamePair)code).getKey());
		else if (code instanceof ValueNamePair)
			codeNorm = ((ValueNamePair)code).getValue();
		else
			codeNorm = code;
		
		// clean code
		if (codeNorm instanceof String)
		{
			String codeNormStr = codeNorm.toString();
			if (codeNormStr.startsWith("'"))
				codeNormStr = codeNormStr.substring(1);
			if (codeNormStr.endsWith("'"))
				codeNormStr = codeNormStr.substring(0, codeNormStr.length() - 2);
			
			codeNorm = codeNormStr;
		}

		return codeNorm;
	}
	
	private static final String buildInfoDisplay(final String infoDisplay, final Object code)
	{
		if (infoDisplay != null)
			return infoDisplay.trim();
		else if (code != null)
			return code.toString();
		else
			return null;
	}
	
	public boolean isDirectWhereClause()
	{
		return directWhereClause != null;
	}
	
	public String getDirectWhereClause()
	{
		return directWhereClause;
	}

	/**
	 * Return SQL construct for this restriction
	 * 
	 * @param tableName optional table name
	 * @return SQL WHERE construct
	 */
	public String getSQL(final String tableName)
	{
		if (directWhereClause != null)
		{
			return directWhereClause;
		}
		//
		final List<Object> sqlParams = null;
		return buildSqlWhereClause(tableName, columnName, operator, code, codeTo, sqlParams);
	}

	private static final String buildSqlWhereClause(final String tableName, final String columnName, final Operator operator, final Object code, final Object codeTo, final List<Object> sqlParams)
	{
		String columnNameToUse = columnName;
		Operator operatorToUse = operator;
		Object code_ToUse = code;
		Object codeTo_ToUse = codeTo;

		if (Operator.BETWEEN == operator && code instanceof String)
		{
			if (code.toString().trim().isEmpty())
			{
				operatorToUse = Operator.LESS_EQUAL;
				code_ToUse = codeTo;
			}
			if (codeTo instanceof String && codeTo.toString().trim().isEmpty())
			{
				operatorToUse = Operator.GREATER_EQUAL;
			}
		}

		final String truncValue = Services.get(ISysConfigBL.class).getValue(MQuery.SYSCONFIG_TruncValue);

		// if trunc value is null, do nothing. This means the timestamp shall not be truncated
		if (truncValue != null && code_ToUse instanceof java.util.Date)
		{
			code_ToUse = truncDate((java.util.Date)code_ToUse, truncValue);

			if (codeTo_ToUse instanceof java.util.Date)
			{
				codeTo_ToUse = truncDate((java.util.Date)codeTo_ToUse, truncValue);
			}
		}

		final StringBuilder sqlWhereClause = new StringBuilder();
		if (Operator.LIKE_I.equals(operatorToUse) || Operator.NOT_LIKE_I.equals(operatorToUse)
				|| (code_ToUse instanceof String && Operator.BETWEEN.equals(operatorToUse))) // task 08757: also add the UPPER in case of BETWEEN operator
		{
			sqlWhereClause.append("UPPER("); // metas-2009_0021_AP1_CR064
		}
		//
		if (tableName != null && tableName.length() > 0)
		{
			int pos = columnName.lastIndexOf('(') + 1;	// including (
			int end = columnName.indexOf(')');
			// We have a Function in the ColumnName
			if (pos != -1 && end != -1)
			{
				// metas: cg : task 03914 : start
				if (pos > end)
				{
					// we have more then one function, such as
					// (CASE WHEN coalesce(Bill_BPartner_ID,0) = coalesce(C_BPartner_ID,0) THEN ''Y'' ELSE ''N'' END)
					// before this fix, in this case we had error ; something like : "Index was outside the bounds"

					String finalColumnName = columnName;
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
						sqlWhereClause.append(finalColumnName.substring(prevPos, pos + 1))
								.append(tableName).append(".");
						finalColumnName = finalColumnName.substring(pos + 1, finalColumnName.length());
						pos = finalColumnName.indexOf('(');
					}
					columnNameToUse = finalColumnName;

				}
				// metas: cg : task 03914 : end
				else
				{

					columnNameToUse = new StringBuilder().append(columnName.substring(0, pos))
							.append(tableName).append(".").append(columnName.substring(pos, end))
							.append(columnName.substring(end)).toString();
				}
			}
			else
			{
				columnNameToUse = new StringBuilder().append(tableName).append(".").append(columnName).toString();
			}
		}
		else
		{
			columnNameToUse = columnName;
		}

		if (codeTo_ToUse instanceof java.util.Date)
		{
			// make sure if we deal with a Date the ColumnName is also truncated
			if (truncValue != null)
			{
				columnNameToUse = new StringBuilder()
						.append(" date_trunc")
						.append(" ( '")
						.append(truncValue)
						.append("', ")
						.append(columnNameToUse)
						.append(" ) ")
						.toString();

			}
		}
		sqlWhereClause.append(columnNameToUse);
		if ((Operator.LIKE_I.equals(operatorToUse) || Operator.NOT_LIKE_I.equals(operatorToUse))
				|| (code_ToUse instanceof String && Operator.BETWEEN.equals(operatorToUse))) // task 08757: also close the ')' in case of BETWEEN operator
		{
			sqlWhereClause.append(")"); // metas-2009_0021_AP1_CR064
		}
		// NULL Operator
		if ((Operator.EQUAL == operatorToUse || Operator.NOT_EQUAL == operatorToUse)
				&& (code_ToUse == null || "NULL".equalsIgnoreCase(code_ToUse.toString())))
		{
			if(Operator.EQUAL == operatorToUse)
			{
				sqlWhereClause.append(" IS NULL ");
			}
			else
			{
				sqlWhereClause.append(" IS NOT NULL ");
			}
		}
		else
		{
			// 08575
			// In case if Between for strings, we don't actually use the Between operator
			if (code_ToUse instanceof String && Operator.BETWEEN.equals(operatorToUse))
			{
				sqlWhereClause.append(" >= ");
			}
			else
			{
				sqlWhereClause.append(operatorToUse);
			}

			if (code_ToUse instanceof String)
			{
				if (Operator.LIKE_I.equals(operatorToUse)
						|| Operator.NOT_LIKE_I.equals(operatorToUse)
						|| Operator.BETWEEN.equals(operatorToUse))
				{
					final String sqlValue = toSqlValue(code_ToUse.toString().trim(), sqlParams);
					sqlWhereClause.append("UPPER(").append(sqlValue).append(")");
				}
				else
				{
					final String sqlValue = toSqlValue(code_ToUse.toString(), sqlParams);
					sqlWhereClause.append(DB.TO_STRING(sqlValue));
				}
			}
			else
			{
				final String sqlValue = toSqlValue(code_ToUse, sqlParams);
				sqlWhereClause.append(sqlValue);
			}

			// Between
			if (Operator.BETWEEN.equals(operatorToUse))
			{
				sqlWhereClause.append(" AND ");
				if (codeTo_ToUse instanceof String)
				{
					final int codeToLength = ((String)codeTo_ToUse).length();
					final String sqlValue = toSqlValue(codeTo_ToUse, sqlParams);
					sqlWhereClause.append("UPPER( SUBSTRING (")
							.append(columnName + ",")
							.append(" 1,")
							.append(codeToLength) //
							.append(") )")
							.append(" <= ")
							.append("UPPER(").append(sqlValue).append(")");

				}
				else
				{
					final String sqlValue = toSqlValue(codeTo_ToUse, sqlParams);
					sqlWhereClause.append(sqlValue);
				}
			}
		}
		
		return sqlWhereClause.toString();
	}	// getSQL
	
	private static final String toSqlValue(final Object value, final List<Object> sqlParams)
	{
		if(sqlParams != null)
		{
			sqlParams.add(value);
			return "?";
		}
		
		if(value == null)
		{
			return "NULL";
		}
		else if (value instanceof String)
		{
			return DB.TO_STRING((String)value);
		}
		else if(value instanceof java.util.Date)
		{
			final java.util.Date date = (java.util.Date)value;
			return " TIMESTAMP '" + TimeUtil.asTimestamp(date).toString() + "'";
		}
		else
		{
			return value.toString();
		}
	}

	/**
	 * In case the given code is instanceof Timestamp, truncate it by the given truncValue.
	 * In case the truncValue is null, leave it as it is
	 * 
	 * @param code
	 * @param truncValue optional truncate type
	 * @return
	 */
	private static final java.util.Date truncDate(final java.util.Date date, final String truncValue)
	{
		if (truncValue == null)
		{
			// do not truncate
			return date;
		}
		if(date == null)
		{
			return null;
		}

		return TimeUtil.trunc(date, truncValue);
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
	}
	
	public boolean isJoinAnd()
	{
		return joinAnd;
	}
	
	public String getColumnName()
	{
		return columnName;
	}
	
	/*package*/void setColumnName(final String columnName)
	{
		this.columnName = columnName;
	}

	/**
	 * Get Info Name
	 * 
	 * @return Info Name
	 */
	public String getInfoName()
	{
		return infoName;
	}	// getInfoName
	
	public Operator getOperator()
	{
		return operator;
	}

	/**
	 * Get Info Operator
	 * 
	 * @return info Operator
	 */
	public String getInfoOperator()
	{
		return operator == null ? null : operator.getCaption();
	}	// getInfoOperator

	/**
	 * Get Display with optional To
	 * 
	 * @return info display
	 */
	public String getInfoDisplayAll()
	{
		if (infoDisplayTo == null)
			return infoDisplay;
		StringBuilder sb = new StringBuilder(infoDisplay);
		sb.append(" - ").append(infoDisplayTo);
		return sb.toString();
	}	// getInfoDisplay
	
	public Object getCode()
	{
		return code;
	}
	
	public String getInfoDisplay()
	{
		return infoDisplay;
	}
	
	public Object getCodeTo()
	{
		return codeTo;
	}
	
	public String getInfoDisplayTo()
	{
		return infoDisplayTo;
	}
}	// Restriction
