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
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Properties;
import org.slf4j.Logger;

import de.metas.cache.CCache;
import de.metas.logging.LogManager;
import de.metas.security.IUserRolePermissions;

import org.compiere.util.DB;

/**
 * 	Project Type Model
 *
 *	@author Jorg Janke
 *	@version $Id: MProjectType.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MProjectType extends X_C_ProjectType
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6041540981032251476L;


	/**
	 * 	Get MProjectType from Cache
	 *	@param ctx context
	 *	@param C_ProjectType_ID id
	 *	@return MProjectType
	 */
	public static MProjectType get (Properties ctx, int C_ProjectType_ID)
	{
		Integer key = new Integer (C_ProjectType_ID);
		MProjectType retValue = s_cache.get (key);
		if (retValue != null)
			return retValue;
		retValue = new MProjectType (ctx, C_ProjectType_ID, null);
		if (retValue.get_ID() != 0)
			s_cache.put (key, retValue);
		return retValue;
	} //	get

	/**	Cache						*/
	private static CCache<Integer, MProjectType> s_cache 
		= new CCache<Integer, MProjectType> ("C_ProjectType", 20);
	
	
	/**************************************************************************
	 * 	Standrad Constructor
	 *	@param ctx context
	 *	@param C_ProjectType_ID id
	 *	@param trxName trx
	 */
	public MProjectType (Properties ctx, int C_ProjectType_ID, String trxName)
	{
		super (ctx, C_ProjectType_ID, trxName);
		/**
		if (C_ProjectType_ID == 0)
		{
			setC_ProjectType_ID (0);
			setName (null);
		}
		**/
	}	//	MProjectType

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName trx
	 */
	public MProjectType (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MProjectType

	/**
	 * 	String Representation
	 *	@return	info
	 */
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer ("MProjectType[")
			.append(get_ID())
			.append("-").append(getName())
			.append("]");
		return sb.toString();
	}	//	toString

	
	/**************************************************************************
	 * 	Get Project Type Phases
	 *	@return Array of phases
	 */
	public MProjectTypePhase[] getPhases()
	{
		ArrayList<MProjectTypePhase> list = new ArrayList<MProjectTypePhase>();
		String sql = "SELECT * FROM C_Phase WHERE C_ProjectType_ID=? ORDER BY SeqNo";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, getC_ProjectType_ID());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
				list.add(new MProjectTypePhase (getCtx(), rs, get_TrxName()));
			rs.close();
			pstmt.close();
			pstmt = null;
		}
		catch (SQLException ex)
		{
			log.error(sql, ex);
		}
		try
		{
			if (pstmt != null)
				pstmt.close();
		}
		catch (SQLException ex1)
		{
		}
		pstmt = null;
		//
		MProjectTypePhase[] retValue = new MProjectTypePhase[list.size()];
		list.toArray(retValue);
		return retValue;
	}	//	getPhases

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
			final IUserRolePermissions role)
	{
		String dateColumn = "Created";
		String orgColumn = "AD_Org_ID";
		String bpColumn = "C_BPartner_ID";
		String pColumn = null;
		//	PlannedAmt -> PlannedQty -> Count
		StringBuffer sb = new StringBuffer("SELECT COALESCE(SUM(PlannedAmt),COALESCE(SUM(PlannedQty),COUNT(*))) "
			+ "FROM C_Project WHERE C_ProjectType_ID=" + getC_ProjectType_ID()
			+ " AND Processed<>'Y')");
		//	Date Restriction
		
		if (MMeasure.MEASUREDATATYPE_QtyAmountInTime.equals(MeasureDataType)
			&& !MGoal.MEASUREDISPLAY_Total.equals(MeasureScope))
		{
			if (reportDate == null)
				reportDate = new Timestamp(System.currentTimeMillis());
			// String dateString = DB.TO_DATE(reportDate);
			String trunc = "D";
			if (MGoal.MEASUREDISPLAY_Year.equals(MeasureScope))
				trunc = "Y";
			else if (MGoal.MEASUREDISPLAY_Quarter.equals(MeasureScope))
				trunc = "Q";
			else if (MGoal.MEASUREDISPLAY_Month.equals(MeasureScope))
				trunc = "MM";
			else if (MGoal.MEASUREDISPLAY_Week.equals(MeasureScope))
				trunc = "W";
		//	else if (MGoal.MEASUREDISPLAY_Day.equals(MeasureDisplay))
		//		;
			sb.append(" AND TRUNC(")
				.append(dateColumn).append(",'").append(trunc).append("')=TRUNC(")
				.append(DB.TO_DATE(reportDate)).append(",'").append(trunc).append("')");
		}	//	date
		//
		String sql = MMeasureCalc.addRestrictions(sb.toString(), false, restrictions, role, 
			"C_Project", orgColumn, bpColumn, pColumn);
		
		log.debug(sql);
		return sql;
	}	//	getSql
	
	/**
	 * 	Get Sql to value for the bar chart
	 *	@param restrictions array of goal restrictions
	 *	@param MeasureDisplay scope of this value  
	 *	@param MeasureDataType data type
	 *	@param startDate optional report start date
	 *	@param role role
	 *	@return sql for Bar Chart
	 */
	public String getSqlBarChart (MGoalRestriction[] restrictions, 
		String MeasureDisplay, String MeasureDataType, 
			Timestamp startDate,
			final IUserRolePermissions role)
	{
		String dateColumn = "Created";
		String orgColumn = "AD_Org_ID";
		String bpColumn = "C_BPartner_ID";
		String pColumn = null;
		//
		StringBuffer sb = new StringBuffer("SELECT COALESCE(SUM(PlannedAmt),COALESCE(SUM(PlannedQty),COUNT(*))), ");
		String orderBy = null;
		String groupBy = null;
		//
		if (MMeasure.MEASUREDATATYPE_QtyAmountInTime.equals(MeasureDataType)
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
			orderBy = "TRUNC(" + dateColumn + ",'" + trunc + "')";
			groupBy = orderBy + ", 0 ";
			sb.append(groupBy)
				.append("FROM C_Project ");
		}
		else
		{
			orderBy = "p.SeqNo"; 
			groupBy = "COALESCE(p.Name,TO_NCHAR('-')), p.C_Phase_ID, p.SeqNo ";
			sb.append(groupBy)
				.append("FROM C_Project LEFT OUTER JOIN C_Phase p ON (C_Project.C_Phase_ID=p.C_Phase_ID) ");
		}
		//	Where
		sb.append("WHERE C_Project.C_ProjectType_ID=").append(getC_ProjectType_ID())
			.append(" AND C_Project.Processed<>'Y'");
		//	Date Restriction
		if (startDate != null
			&& !MGoal.MEASUREDISPLAY_Total.equals(MeasureDisplay))
		{
			String dateString = DB.TO_DATE(startDate);
			sb.append(" AND ").append(dateColumn)
				.append(">=").append(dateString);
		}	//	date
		//
		String sql = MMeasureCalc.addRestrictions(sb.toString(), false, restrictions, role, 
			"C_Project", orgColumn, bpColumn, pColumn);
		if (groupBy != null)
			sql += " GROUP BY " + groupBy + " ORDER BY " + orderBy;
		//
		log.debug(sql);
		return sql;
	}	//	getSqlBarChart
	
	/**
	 * 	Get Zoom Query
	 * 	@param restrictions restrictions
	 * 	@param MeasureDisplay display
	 * 	@param date date
	 * 	@param C_Phase_ID phase
	 * 	@param role role 
	 *	@return query
	 */
	public MQuery getQuery(MGoalRestriction[] restrictions, 
			String MeasureDisplay, Timestamp date, int C_Phase_ID,
			final IUserRolePermissions role)
	{
		String dateColumn = "Created";
		String orgColumn = "AD_Org_ID";
		String bpColumn = "C_BPartner_ID";
		String pColumn = null;
		//
		MQuery query = new MQuery("C_Project");
		query.addRangeRestriction("C_ProjectType_ID", "=", getC_ProjectType_ID());
		//
		String where = null;
		if (C_Phase_ID != 0)
			where = "C_Phase_ID=" + C_Phase_ID;
		else
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
			where = "TRUNC(" + dateColumn + ",'" + trunc
				+ "')=TRUNC(" + DB.TO_DATE(date) + ",'" + trunc + "')";
		}
		String sql = MMeasureCalc.addRestrictions(where + " AND Processed<>'Y' ",
			true, restrictions, role, 
			"C_Project", orgColumn, bpColumn, pColumn);
		query.addRestriction(sql);
		query.setRecordCount(1);
		return query;
	}	//	getQuery

}	//	MProjectType
