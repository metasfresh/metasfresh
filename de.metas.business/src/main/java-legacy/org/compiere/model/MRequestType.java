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

import org.adempiere.ad.security.IUserRolePermissions;
import org.compiere.model.MQuery.Operator;
import org.compiere.util.CCache;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.logging.LogManager;

/**
 *	Request Type Model
 *	
 *  @author Jorg Janke
 *  @version $Id: MRequestType.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 *  
 *  Teo Sarca - bug fix [ 1642833 ] MRequestType minor typo bug
 */
public class MRequestType extends X_R_RequestType
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6235793036503665638L;

	/**
	 * 	Get Request Type (cached)
	 *	@param ctx context
	 *	@param R_RequestType_ID id
	 *	@return Request Type
	 */
	public static MRequestType get (Properties ctx, int R_RequestType_ID)
	{
		Integer key = new Integer (R_RequestType_ID);
		MRequestType retValue = s_cache.get(key);
		if (retValue == null)
		{
			retValue = new MRequestType (ctx, R_RequestType_ID, null);
			s_cache.put(key, retValue);
		}
		return retValue;
	}	//	get

	/** Static Logger					*/
	private static Logger s_log = LogManager.getLogger(MRequestType.class);
	/**	Cache							*/
	static private CCache<Integer,MRequestType> s_cache = new CCache<Integer,MRequestType>("R_RequestType", 10);

	/**
	 * 	Get Default Request Type
	 *	@param ctx context
	 *	@return Request Type
	 */
	public static MRequestType getDefault (Properties ctx)
	{
		MRequestType retValue = null;
		int AD_Client_ID = Env.getAD_Client_ID(ctx);
		String sql = "SELECT * FROM R_RequestType "
			+ "WHERE AD_Client_ID IN (0," + AD_Client_ID + ") "
			+ "ORDER BY IsDefault DESC, AD_Client_ID DESC";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement (sql, null);
			ResultSet rs = pstmt.executeQuery ();
			if (rs.next ())
			{
				retValue = new MRequestType (ctx, rs, null);
				if (!retValue.isDefault())
					retValue = null;
			}
			rs.close ();
			pstmt.close ();
			pstmt = null;
		}
		catch (SQLException ex)
		{
			s_log.error(sql, ex);
		}
		try
		{
			if (pstmt != null)
				pstmt.close ();
		}
		catch (SQLException ex1)
		{
		}
		pstmt = null;
		return retValue;
	}	//	get


	/**************************************************************************
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param R_RequestType_ID id
	 *	@param trxName transaction
	 */
	public MRequestType (Properties ctx, int R_RequestType_ID, String trxName)
	{
		super(ctx, R_RequestType_ID, trxName);
		if (R_RequestType_ID == 0)
		{
		//	setR_RequestType_ID (0);
		//	setName (null);
			setDueDateTolerance (7);
			setIsDefault (false);
			setIsEMailWhenDue (false);
			setIsEMailWhenOverdue (false);
			setIsSelfService (true);	// Y
			setAutoDueDateDays(0);
			setConfidentialType(CONFIDENTIALTYPE_PublicInformation);
			setIsAutoChangeRequest(false);
			setIsConfidentialInfo(false);
			setIsIndexed(true);
			setIsInvoiced(false);
		}	
	}	//	MRequestType

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MRequestType(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MRequestType

	/** Next time stats to be created		*/
	private long m_nextStats = 0;
	
	private int m_openNo = 0;
	private int m_totalNo = 0;
	private int m_new30No = 0;
	private int m_closed30No = 0;
	
	/**
	 * 	Update Statistics
	 */
	private synchronized void updateStatistics()
	{
		if (System.currentTimeMillis() < m_nextStats)
			return;
		
		String sql = "SELECT "
			+ "(SELECT COUNT(*) FROM R_Request r"
			+ " INNER JOIN R_Status s ON (r.R_Status_ID=s.R_Status_ID AND s.IsOpen='Y') "
			+ "WHERE r.R_RequestType_ID=x.R_RequestType_ID) AS OpenNo, "
			+ "(SELECT COUNT(*) FROM R_Request r "
			+ "WHERE r.R_RequestType_ID=x.R_RequestType_ID) AS TotalNo, "
			+ "(SELECT COUNT(*) FROM R_Request r "
			+ "WHERE r.R_RequestType_ID=x.R_RequestType_ID AND Created>addDays(now(),-30)) AS New30No, "
			+ "(SELECT COUNT(*) FROM R_Request r"
			+ " INNER JOIN R_Status s ON (r.R_Status_ID=s.R_Status_ID AND s.IsClosed='Y') "
			+ "WHERE r.R_RequestType_ID=x.R_RequestType_ID AND r.Updated>addDays(now(),-30)) AS Closed30No "
			//
			+ "FROM R_RequestType x WHERE R_RequestType_ID=?";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt (1, getR_RequestType_ID());
			ResultSet rs = pstmt.executeQuery ();
			if (rs.next ())
			{
				m_openNo = rs.getInt(1);
				m_totalNo = rs.getInt(2);
				m_new30No = rs.getInt(3);
				m_closed30No = rs.getInt(4);
			}
			rs.close ();
			pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			log.error(sql, e);
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
		
		m_nextStats = System.currentTimeMillis() + 3600000;		//	every hour
	}	//	updateStatistics
	
	/**
	 * 	Get Total No of requests of type
	 *	@return no
	 */
	public int getTotalNo()
	{
		updateStatistics();
		return m_totalNo;
	}

	/**
	 * 	Get Open No of requests of type
	 *	@return no
	 */
	public int getOpenNo()
	{
		updateStatistics();
		return m_openNo;
	}

	/**
	 * 	Get Closed in last 30 days of type
	 *	@return no
	 */
	public int getClosed30No()
	{
		updateStatistics();
		return m_closed30No;
	}
	
	/**
	 * 	Get New in the last 30 days of type
	 *	@return no
	 */
	public int getNew30No()
	{
		updateStatistics();
		return m_new30No;
	}
	
	/**
	 * 	Get Requests of Type
	 *	@param selfService self service
	 *	@param C_BPartner_ID id or 0 for public
	 *	@return array of requests
	 */
	public MRequest[] getRequests (boolean selfService, int C_BPartner_ID)
	{
		String sql = "SELECT * FROM R_Request WHERE R_RequestType_ID=?";
		if (selfService)
			sql += " AND IsSelfService='Y'";
		if (C_BPartner_ID == 0)
			sql += " AND ConfidentialType='A'";
		else
			sql += " AND (ConfidentialType='A' OR C_BPartner_ID=" + C_BPartner_ID + ")";
		sql += " ORDER BY DocumentNo DESC";
		//
		ArrayList<MRequest> list = new ArrayList<MRequest>();
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt (1, getR_RequestType_ID());
			ResultSet rs = pstmt.executeQuery ();
			while (rs.next ())
				list.add (new MRequest (getCtx(), rs, null));
			rs.close ();
			pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			log.error(sql, e);
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
		
		MRequest[] retValue = new MRequest[list.size ()];
		list.toArray (retValue);
		return retValue;
	}	//	getRequests
	
	/**
	 * 	Get public Requests of Type
	 *	@return array of requests
	 */
	public MRequest[] getRequests ()
	{
		return getRequests(true, 0);
	}	//	getRequests
	
	/**
	 * 	Get Default R_Status_ID for Type
	 *	@return status or 0
	 */
	public int getDefaultR_Status_ID()
	{
		if (getR_StatusCategory_ID() == 0)
		{
			MStatusCategory sc = MStatusCategory.getDefault(getCtx());
			if (sc == null)
				sc = MStatusCategory.createDefault(getCtx());
			if (sc != null && sc.getR_StatusCategory_ID() != 0)
				setR_StatusCategory_ID(sc.getR_StatusCategory_ID());
		}
		if (getR_StatusCategory_ID() != 0)
		{
			MStatusCategory sc = MStatusCategory.get(getCtx(), getR_StatusCategory_ID());
			return sc.getDefaultR_Status_ID();
		}
		return 0;
	}	//	getDefaultR_Status_ID
	
	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true
	 */
	@Override
	protected boolean beforeSave (boolean newRecord)
	{
		if (getR_StatusCategory_ID() == 0)
		{
			MStatusCategory sc = MStatusCategory.getDefault(getCtx());
			if (sc != null && sc.getR_StatusCategory_ID() != 0)
				setR_StatusCategory_ID(sc.getR_StatusCategory_ID());
		}
		return true;
	}	//	beforeSave
	
	/**
	 * 	String Representation
	 *	@return info
	 */
	@Override
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("MRequestType[");
		sb.append(get_ID()).append("-").append(getName())
			.append ("]");
		return sb.toString();
	}	//	toString
	
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
		String pColumn = "M_Product_ID";
		//	PlannedAmt -> PlannedQty -> Count
		StringBuffer sb = new StringBuffer("SELECT COUNT(*) "
			+ "FROM R_Request WHERE R_RequestType_ID=" + getR_RequestType_ID()
			+ " AND Processed<>'Y'");
		//	Date Restriction
		
		if (MMeasure.MEASUREDATATYPE_QtyAmountInTime.equals(MeasureDataType)
			&& !MGoal.MEASUREDISPLAY_Total.equals(MeasureScope))
		{
			if (reportDate == null)
				reportDate = new Timestamp(System.currentTimeMillis());
			String dateString = DB.TO_DATE(reportDate);
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
		String sql = MMeasureCalc.addRestrictions(sb.toString(), false, restrictions,
				role,
				"R_Request", orgColumn, bpColumn, pColumn);
		
		log.debug(sql);
		return sql;
	}	//	getSqlPI
	
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
		String pColumn = "M_Product_ID";
		//
		StringBuffer sb = new StringBuffer("SELECT COUNT(*), ");
		String groupBy = null;
		String orderBy = null;
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
			groupBy = orderBy + ", 3 ";
			sb.append(orderBy)
				.append(", 0 as StatusID ")
				.append("FROM R_Request ");
		}
		else
		{
			orderBy = "s.SeqNo"; 
			groupBy = "COALESCE(s.Name,TO_NCHAR('-')), s.R_Status_ID, s.SeqNo ";
			sb.append(groupBy)
				.append("FROM R_Request LEFT OUTER JOIN R_Status s ON (R_Request.R_Status_ID=s.R_Status_ID) ");
		}
		//	Where
		sb.append("WHERE R_Request.R_RequestType_ID=").append(getR_RequestType_ID())
			.append(" AND R_Request.Processed<>'Y'");
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
			"R_Request", orgColumn, bpColumn, pColumn);
		if (groupBy != null)
			sql += " GROUP BY " + groupBy + " ORDER BY " + orderBy;
		//
		log.debug(sql);
		return sql;
	}	//	getSqlBarChart
	
	/**
	 * 	Get Zoom Query
	 * 	@param restrictions array of restrictions
	 * 	@param MeasureDisplay display
	 * 	@param date date
	 * 	@param R_Status_ID status
	 * 	@param role role
	 *	@return query
	 */
	public MQuery getQuery(MGoalRestriction[] restrictions, 
			String MeasureDisplay, Timestamp date, int R_Status_ID,
			final IUserRolePermissions role)
	{
		String dateColumn = "Created";
		String orgColumn = "AD_Org_ID";
		String bpColumn = "C_BPartner_ID";
		String pColumn = "M_Product_ID";
		//
		MQuery query = new MQuery("R_Request");
		query.addRestriction("R_RequestType_ID", Operator.EQUAL, getR_RequestType_ID());
		//
		String where = null;
		if (R_Status_ID != 0)
			where = "R_Status_ID=" + R_Status_ID;
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
		String whereRestriction = MMeasureCalc.addRestrictions(where + " AND Processed<>'Y' ",
			true, restrictions, role, 
			"R_Request", orgColumn, bpColumn, pColumn);
		query.addRestriction(whereRestriction);
		query.setRecordCount(1);
		return query;
	}	//	getQuery
	
}	//	MRequestType
