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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Properties;

import org.adempiere.ad.table.api.IADTableDAO;
import org.compiere.util.DB;
import org.compiere.util.Env;

import de.metas.cache.CCache;
import de.metas.security.IUserRolePermissions;
import de.metas.security.permissions.Access;
import de.metas.util.Services;

/**
 * 	Performance Measure Calculation
 *	
 *  @author Jorg Janke
 *  @version $Id: MMeasureCalc.java,v 1.4 2006/09/25 00:59:41 jjanke Exp $
 */
public class MMeasureCalc extends X_PA_MeasureCalc
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4720674127987683534L;

	/**
	 * 	Get MMeasureCalc from Cache
	 *	@param ctx context
	 *	@param PA_MeasureCalc_ID id
	 *	@return MMeasureCalc
	 */
	public static MMeasureCalc get (Properties ctx, int PA_MeasureCalc_ID)
	{
		Integer key = new Integer (PA_MeasureCalc_ID);
		MMeasureCalc retValue = s_cache.get (key);
		if (retValue != null)
			return retValue;
		retValue = new MMeasureCalc (ctx, PA_MeasureCalc_ID, null);
		if (retValue.get_ID() != 0)
			s_cache.put (key, retValue);
		return retValue;
	}	//	get

	/**	Cache						*/
	private static CCache<Integer, MMeasureCalc> s_cache 
		= new CCache<Integer, MMeasureCalc> ("PA_MeasureCalc", 10);
	
	/**************************************************************************
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param PA_MeasureCalc_ID id
	 *	@param trxName trx
	 */
	public MMeasureCalc (Properties ctx, int PA_MeasureCalc_ID, String trxName)
	{
		super (ctx, PA_MeasureCalc_ID, trxName);
	}	//	MMeasureCalc

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName trx
	 */
	public MMeasureCalc (Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
	}	//	MMeasureCalc
	
	
	/**
	 * 	Get Sql to return single value for the Performance Indicator
	 *	@param restrictions array of goal restrictions
	 *	@param MeasureScope scope of this value  
	 *	@param MeasureDataType data type
	 *	@param reportDate optional report date
	 *	@param role role
	 *	@return sql for performance indicator
	 */
	public String getSqlPI (MGoalRestriction[] restrictions, 
			String MeasureScope, String MeasureDataType, Timestamp reportDate,
			IUserRolePermissions role)
	{
		StringBuffer sb = new StringBuffer(getSelectClause())
			.append(" ")
			.append(Env.parseContext(getCtx(), 0, getWhereClause(), false)); // metas: tsa: parse whereClause
		//	Date Restriction
		if (getDateColumn() != null 
			&& MMeasure.MEASUREDATATYPE_QtyAmountInTime.equals(MeasureDataType)
			&& !MGoal.MEASUREDISPLAY_Total.equals(MeasureScope))
		{
			if (reportDate == null)
				reportDate = new Timestamp(System.currentTimeMillis());
			// String dateString = DB.TO_DATE(reportDate);
			// http://download-west.oracle.com/docs/cd/B14117_01/server.101/b10759/functions207.htm#i1002084
			String trunc = "DD";
			if (MGoal.MEASUREDISPLAY_Year.equals(MeasureScope))
				trunc = "Y";
			else if (MGoal.MEASUREDISPLAY_Quarter.equals(MeasureScope))
				trunc = "Q";
			else if (MGoal.MEASUREDISPLAY_Month.equals(MeasureScope))
				trunc = "MM";
			else if (MGoal.MEASUREDISPLAY_Week.equals(MeasureScope))
				trunc = "D";
		//	else if (MGoal.MEASUREDISPLAY_Day.equals(MeasureDisplay))
		//		;
			sb.append(" AND TRUNC(")
				.append(getDateColumn()).append(",'").append(trunc).append("')=TRUNC(")
				.append(DB.TO_DATE(reportDate)).append(",'").append(trunc).append("')");
		}	//	date
		String sql = addRestrictions(sb.toString(), restrictions, role);
		
		log.debug(sql);
		return sql;
	}	//	getSql
	
	/**
	 * 	Get Sql to value for the bar chart
	 *	@param restrictions array of goal restrictions
	 *	@param MeasureDisplay scope of this value  
	 *	@param startDate optional report start date
	 *	@param role role
	 *	@return sql for Bar Chart
	 */
	public String getSqlBarChart (MGoalRestriction[] restrictions, 
			String MeasureDisplay, Timestamp startDate,
			IUserRolePermissions role)
	{
		StringBuffer sb = new StringBuffer();
		String dateCol = null;
		String groupBy = null;
		if (getDateColumn() != null 
			&& !MGoal.MEASUREDISPLAY_Total.equals(MeasureDisplay))
		{
			String trunc = "D";
			if (MGoal.MEASUREDISPLAY_Year.equals(MeasureDisplay))
				trunc = "Y";
			else if (MGoal.MEASUREDISPLAY_Quarter.equals(MeasureDisplay))
				trunc = "Q";
			else if (MGoal.MEASUREDISPLAY_Month.equals(MeasureDisplay))
				trunc = "MM";
			else if (MGoal.MEASUREDISPLAY_Week.equals(MeasureDisplay))
				trunc = "W";
		//	else if (MGoal.MEASUREDISPLAY_Day.equals(MeasureDisplay))
		//		;
			dateCol = "TRUNC(" + getDateColumn() + ",'" + trunc + "') ";
			groupBy = dateCol; 
		}
		else
			dateCol = "MAX(" + getDateColumn() + ") ";
		//
		String selectFrom = getSelectClause();
		int index = selectFrom.indexOf("FROM ");
		if (index == -1)
			index = selectFrom.toUpperCase().indexOf("FROM ");
		if (index == -1)
			throw new IllegalArgumentException("Cannot find FROM in sql - " + selectFrom);
		sb.append(selectFrom.substring(0, index))
			.append(",").append(dateCol)
			.append(selectFrom.substring(index));
		
		//	** WHERE
		sb.append(" ")
			.append(Env.parseContext(getCtx(), 0, getWhereClause(), false)); // metas: tsa: parse whereClause
		//	Date Restriction
		if (getDateColumn() != null
			&& startDate != null
			&& !MGoal.MEASUREDISPLAY_Total.equals(MeasureDisplay))
		{
			String dateString = DB.TO_DATE(startDate);
			sb.append(" AND ").append(getDateColumn())
				.append(">=").append(dateString);
		}	//	date
		String sql = addRestrictions(sb.toString(), restrictions, role);
		if (groupBy != null)
			sql += " GROUP BY " + groupBy
					+ " ORDER BY " + groupBy; // teo_sarca, [ 1665129 ] Bar Graph is not ordered
		//
		log.debug(sql);
		return sql;
	}	//	getSqlBarChart
	
	/**
	 * 	Get Zoom Query
	 * 	@param restrictions restrictions
	 * 	@param MeasureDisplay display
	 * 	@param date date
	 * 	@param role role
	 *	@return query
	 */
	public MQuery getQuery(MGoalRestriction[] restrictions, 
			String MeasureDisplay, Timestamp date,
			IUserRolePermissions role)
	{
		MQuery query = new MQuery(getAD_Table_ID());
		//
		StringBuffer sql = new StringBuffer("SELECT ").append(getKeyColumn()).append(" ");
		String from = getSelectClause();
		int index = from.indexOf("FROM ");
		if (index == -1)
			throw new IllegalArgumentException("Cannot find FROM " + from);
		sql.append(from.substring(index)).append(" ")
			.append(Env.parseContext(getCtx(), 0, getWhereClause(), false)); // metas: tsa: parse context
		//	Date Range
		if (getDateColumn() != null 
			&& !MGoal.MEASUREDISPLAY_Total.equals(MeasureDisplay))
		{
			String trunc = "D";
			if (MGoal.MEASUREDISPLAY_Year.equals(MeasureDisplay))
				trunc = "Y";
			else if (MGoal.MEASUREDISPLAY_Quarter.equals(MeasureDisplay))
				trunc = "Q";
			else if (MGoal.MEASUREDISPLAY_Month.equals(MeasureDisplay))
				trunc = "MM";
			else if (MGoal.MEASUREDISPLAY_Week.equals(MeasureDisplay))
				trunc = "W";
		//	else if (MGoal.MEASUREDISPLAY_Day.equals(MeasureDisplay))
		//		trunc = "D";
			sql.append(" AND TRUNC(").append(getDateColumn()).append(",'").append(trunc)
				.append("')=TRUNC(").append(DB.TO_DATE(date)).append(",'").append(trunc).append("')");
		}
		String finalSQL = addRestrictions(sql.toString(), restrictions, role);
		//	Execute
		StringBuffer where = new StringBuffer();
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement (finalSQL, null);
			ResultSet rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				int id = rs.getInt(1);
				if (where.length() > 0)
					where.append(",");
				where.append(id);
			}
			rs.close ();
			pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			log.error(finalSQL, e);
		}
		try
		{
			if (pstmt != null)
				pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			pstmt = null;
		}
		if (where.length() == 0)
			return MQuery.getNoRecordQuery(query.getTableName(), false);
		//
		StringBuffer whereClause = new StringBuffer (getKeyColumn())
			.append(" IN (").append(where).append(")");
		query.addRestriction(whereClause.toString());
		query.setRecordCount(1);
		return query;
	}	//	getQuery
	
	/**
	 * 	Add Restrictions
	 *	@param sql existing sql
	 *	@param restrictions restrictions
	 *	@param role role
	 *	@return updated sql
	 */
	private String addRestrictions(String sql, 
			MGoalRestriction[] restrictions,
			IUserRolePermissions role)
	{
		return addRestrictions(sql, false, restrictions, role,
			getTableName(), getOrgColumn(), getBPartnerColumn(), getProductColumn());
	}	//	addRestrictions


	/**
	 * 	Add Restrictions to SQL
	 *	@param sql orig sql
	 *	@param queryOnly incomplete sql for query restriction
	 *	@param restrictions restrictions
	 *	@param role role
	 *	@param tableName table name
	 *	@param orgColumn org column
	 *	@param bpColumn bpartner column
	 *	@param pColumn product column
	 *	@return updated sql
	 */
	public static String addRestrictions(String sql, boolean queryOnly,
			MGoalRestriction[] restrictions,
			IUserRolePermissions role,
		String tableName, String orgColumn, String bpColumn, String pColumn)
	{
		StringBuffer sb = new StringBuffer(sql);
		//	Org Restrictions
		if (orgColumn != null)
		{
			ArrayList<Integer> list = new ArrayList<Integer>();
			for (int i = 0; i < restrictions.length; i++)
			{
				if (MGoalRestriction.GOALRESTRICTIONTYPE_Organization.equals(restrictions[i].getGoalRestrictionType()))
					list.add(restrictions[i].getOrg_ID());
				//	Hierarchy comes here
			}
			if (list.size() == 1)
				sb.append(" AND ").append(orgColumn)
					.append("=").append(list.get(0));
			else if (list.size() > 1)
			{
				sb.append(" AND ").append(orgColumn).append(" IN (");
				for (int i = 0; i < list.size(); i++)
				{
					if (i > 0)
						sb.append(",");
					sb.append(list.get(i));
				}
				sb.append(")");
			}
		}	//	org
		
		//	BPartner Restrictions
		if (bpColumn != null)
		{
			ArrayList<Integer> listBP = new ArrayList<Integer>();
			ArrayList<Integer> listBPG = new ArrayList<Integer>();
			for (int i = 0; i < restrictions.length; i++)
			{
				if (MGoalRestriction.GOALRESTRICTIONTYPE_BusinessPartner.equals(restrictions[i].getGoalRestrictionType()))
					listBP.add(restrictions[i].getC_BPartner_ID());
				//	Hierarchy comes here
				if (MGoalRestriction.GOALRESTRICTIONTYPE_BusPartnerGroup.equals(restrictions[i].getGoalRestrictionType()))
					listBPG.add(restrictions[i].getC_BP_Group_ID());
			}
			//	BP
			if (listBP.size() == 1)
				sb.append(" AND ").append(bpColumn)
					.append("=").append(listBP.get(0));
			else if (listBP.size() > 1)
			{
				sb.append(" AND ").append(bpColumn).append(" IN (");
				for (int i = 0; i < listBP.size(); i++)
				{
					if (i > 0)
						sb.append(",");
					sb.append(listBP.get(i));
				}
				sb.append(")");
			}
			//	BPG
			if (bpColumn.indexOf('.') == -1)
				bpColumn = tableName + "." + bpColumn;
			if (listBPG.size() == 1)
				sb.append(" AND EXISTS (SELECT * FROM C_BPartner bpx WHERE ")
					.append(bpColumn)
					.append("=bpx.C_BPartner_ID AND bpx.C_BP_GROUP_ID=")
					.append(listBPG.get(0)).append(")"); 
			else if (listBPG.size() > 1)
			{
				sb.append(" AND EXISTS (SELECT * FROM C_BPartner bpx WHERE ")
					.append(bpColumn)
					.append("=bpx.C_BPartner_ID AND bpx.C_BP_GROUP_ID IN (");
				for (int i = 0; i < listBPG.size(); i++)
				{
					if (i > 0)
						sb.append(",");
					sb.append(listBPG.get(i));
				}
				sb.append("))");
			}
		}	//	bp
		
		//	Product Restrictions
		if (pColumn != null)
		{
			ArrayList<Integer> listP = new ArrayList<Integer>();
			ArrayList<Integer> listPC = new ArrayList<Integer>();
			for (int i = 0; i < restrictions.length; i++)
			{
				if (MGoalRestriction.GOALRESTRICTIONTYPE_Product.equals(restrictions[i].getGoalRestrictionType()))
					listP.add(restrictions[i].getM_Product_ID());
				//	Hierarchy comes here
				if (MGoalRestriction.GOALRESTRICTIONTYPE_ProductCategory.equals(restrictions[i].getGoalRestrictionType()))
					listPC.add(restrictions[i].getM_Product_Category_ID());
			}
			//	Product
			if (listP.size() == 1)
				sb.append(" AND ").append(pColumn)
					.append("=").append(listP.get(0));
			else if (listP.size() > 1)
			{
				sb.append(" AND ").append(pColumn).append(" IN (");
				for (int i = 0; i < listP.size(); i++)
				{
					if (i > 0)
						sb.append(",");
					sb.append(listP.get(i));
				}
				sb.append(")");
			}
			//	Category
			if (pColumn.indexOf('.') == -1)
				pColumn = tableName + "." + pColumn;
			if (listPC.size() == 1)
				sb.append(" AND EXISTS (SELECT * FROM M_Product px WHERE ")
					.append(pColumn)
					.append("=px.M_Product_ID AND px.M_Product_Category_ID=")
					.append(listPC.get(0)).append(")"); 
			else if (listPC.size() > 1)
			{
				sb.append(" AND EXISTS (SELECT * FROM M_Product px WHERE ")
				.append(pColumn)
				.append("=px.M_Product_ID AND px.M_Product_Category_ID IN (");
				for (int i = 0; i < listPC.size(); i++)
				{
					if (i > 0)
						sb.append(",");
					sb.append(listPC.get(i));
				}
				sb.append("))");
			}
		}	//	product
		String finalSQL = sb.toString();
		if (queryOnly)
			return finalSQL;
		if (role == null)
			role = Env.getUserRolePermissions();
		String retValue = role.addAccessSQL(finalSQL, tableName, IUserRolePermissions.SQL_FULLYQUALIFIED, Access.READ);
		return retValue;
	}	//	addRestrictions

	/**
	 * 	Get Table Name
	 *	@return Table Name
	 */
	public String getTableName()
	{
		return Services.get(IADTableDAO.class).retrieveTableName(getAD_Table_ID());
	}	//	getTavleName
	
	/**
	 * 	String Representation
	 *	@return info
	 */
	@Override
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("MMeasureCalc[");
		sb.append (get_ID()).append ("-").append (getName()).append ("]");
		return sb.toString ();
	}	//	toString
	
	
}	//	MMeasureCalc
