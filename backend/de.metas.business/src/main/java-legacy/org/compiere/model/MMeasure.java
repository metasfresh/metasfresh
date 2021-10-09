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
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import de.metas.letters.model.I_R_RequestType;
import de.metas.project.ProjectType;
import de.metas.project.ProjectTypeId;
import de.metas.project.ProjectTypeRepository;
import org.adempiere.apps.graph.GraphColumn;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.service.ClientId;
import org.compiere.SpringContextHolder;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import de.metas.cache.CCache;
import de.metas.organization.OrgId;
import de.metas.security.IUserRolePermissions;
import de.metas.security.IUserRolePermissionsDAO;
import de.metas.security.RoleId;
import de.metas.user.UserId;
import de.metas.util.Services;

/**
 * Performance Measure
 * 
 * @author Jorg Janke
 * @version $Id: MMeasure.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 * 
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 *         <li>BF [ 1887674 ] Deadlock when try to modify PA Goal's Measure Target
 */
public class MMeasure extends X_PA_Measure
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6274990637485210675L;

	/**
	 * Get MMeasure from Cache
	 * 
	 * @param ctx context
	 * @param PA_Measure_ID id
	 * @return MMeasure
	 */
	public static MMeasure get(Properties ctx, int PA_Measure_ID)
	{
		Integer key = new Integer(PA_Measure_ID);
		MMeasure retValue = s_cache.get(key);
		if (retValue != null)
			return retValue;
		retValue = new MMeasure(ctx, PA_Measure_ID, null);
		if (retValue.get_ID() != 0)
			s_cache.put(key, retValue);
		return retValue;
	} // get

	/** Cache */
	private static CCache<Integer, MMeasure> s_cache = new CCache<Integer, MMeasure>("PA_Measure", 10);

	/**
	 * Standard Constructor
	 * 
	 * @param ctx context
	 * @param PA_Measure_ID id
	 * @param trxName trx
	 */
	public MMeasure(Properties ctx, int PA_Measure_ID, String trxName)
	{
		super(ctx, PA_Measure_ID, trxName);
	}	// MMeasure

	/**
	 * Load Constructor
	 * 
	 * @param ctx context
	 * @param rs result set
	 * @param trxName trx
	 */
	public MMeasure(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	// MMeasure

	public ArrayList<GraphColumn> getGraphColumnList(MGoal goal)
	{
		ArrayList<GraphColumn> list = new ArrayList<GraphColumn>();
		if (MMeasure.MEASURETYPE_Calculated.equals(getMeasureType()))
		{
			MMeasureCalc mc = MMeasureCalc.get(getCtx(), getPA_MeasureCalc_ID());
			String sql = mc.getSqlBarChart(goal.getRestrictions(false),
					goal.getMeasureDisplay(), goal.getDateFrom(),
					Env.getUserRolePermissions());	// logged in role
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{
				pstmt = DB.prepareStatement(sql, null);
				rs = pstmt.executeQuery();
				ArrayList<Timestamp> dataList = new ArrayList<Timestamp>();
				while (rs.next())
				{
					BigDecimal data = rs.getBigDecimal(1);
					Timestamp date = rs.getTimestamp(2);
					GraphColumn bgc = new GraphColumn(mc, data);
					bgc.setLabel(date, goal.getMeasureDisplay()); // TODO copy order-loop to other measures
					int pos = 0;
					for (int i = 0; i < dataList.size(); i++)
						if (dataList.get(i).before(date))
							pos++;
					dataList.add(date); // list of dates
					list.add(pos, bgc);
				}
			}
			catch (Exception e)
			{
				log.error(sql, e);
			}
			finally
			{
				DB.close(rs, pstmt);
				rs = null;
				pstmt = null;
			}
		}
		else if (MMeasure.MEASURETYPE_Achievements.equals(getMeasureType()))
		{
			if (MMeasure.MEASUREDATATYPE_StatusQtyAmount.equals(getMeasureDataType()))
			{
				MAchievement[] achievements = MAchievement.get(this);
				for (int i = 0; i < achievements.length; i++)
				{
					MAchievement achievement = achievements[i];
					GraphColumn bgc = new GraphColumn(achievement);
					list.add(bgc);
				}
			}
			else	// MMeasure.MEASUREDATATYPE_QtyAmountInTime
			{
				String MeasureDisplay = goal.getMeasureDisplay();
				String trunc = "D";
				if (MGoal.MEASUREDISPLAY_Year.equals(MeasureDisplay))
					trunc = "Y";
				else if (MGoal.MEASUREDISPLAY_Quarter.equals(MeasureDisplay))
					trunc = "Q";
				else if (MGoal.MEASUREDISPLAY_Month.equals(MeasureDisplay))
					trunc = "MM";
				else if (MGoal.MEASUREDISPLAY_Week.equals(MeasureDisplay))
					trunc = "W";
				// else if (MGoal.MEASUREDISPLAY_Day.equals(MeasureDisplay))
				// trunc = "D";
				trunc = "TRUNC(DateDoc,'" + trunc + "')";
				StringBuffer sql = new StringBuffer("SELECT SUM(ManualActual), ")
						.append(trunc).append(" FROM PA_Achievement WHERE PA_Measure_ID=? AND IsAchieved='Y' ")
						.append("GROUP BY ").append(trunc)
						.append(" ORDER BY ").append(trunc);
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				try
				{
					pstmt = DB.prepareStatement(sql.toString(), null);
					pstmt.setInt(1, getPA_Measure_ID());
					rs = pstmt.executeQuery();
					while (rs.next())
					{
						BigDecimal data = rs.getBigDecimal(1);
						Timestamp date = rs.getTimestamp(2);
						GraphColumn bgc = new GraphColumn(goal, data);
						bgc.setLabel(date, goal.getMeasureDisplay());
						list.add(bgc);
					}
				}
				catch (Exception e)
				{
					log.error(sql.toString(), e);
				}
				finally
				{
					DB.close(rs, pstmt);
					rs = null;
					pstmt = null;
				}
			}	// Achievement in time
		}	// Achievement

		// Request
		else if (MMeasure.MEASURETYPE_Request.equals(getMeasureType()))
		{
			MRequestType rt = MRequestType.get(Env.getCtx(), getR_RequestType_ID());
			String sql = rt.getSqlBarChart(goal.getRestrictions(false),
					goal.getMeasureDisplay(), getMeasureDataType(),
					goal.getDateFrom(),
					Env.getUserRolePermissions());	// logged in role
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{
				pstmt = DB.prepareStatement(sql, null);
				rs = pstmt.executeQuery();
				while (rs.next())
				{
					BigDecimal data = rs.getBigDecimal(1);
					int R_Status_ID = rs.getInt(3);
					GraphColumn bgc = new GraphColumn(rt, data, R_Status_ID);
					if (R_Status_ID == 0)
					{
						Timestamp date = rs.getTimestamp(2);
						bgc.setLabel(date, goal.getMeasureDisplay());
					}
					else
					{
						MStatus status = MStatus.get(Env.getCtx(), R_Status_ID);
						bgc.setLabel(status.getName());
					}
					list.add(bgc);
				}
			}
			catch (Exception e)
			{
				log.error(sql, e);
			}
			finally
			{
				DB.close(rs, pstmt);
				rs = null;
				pstmt = null;
			}
		}	// Request

		// Project
		else if (MMeasure.MEASURETYPE_Project.equals(getMeasureType()))
		{
			final ProjectTypeRepository projectTypeRepository = SpringContextHolder.instance.getBean(ProjectTypeRepository.class);
			final ProjectTypeId projectTypeId = ProjectTypeId.ofRepoId(getC_ProjectType_ID());
			final ProjectType projectType = projectTypeRepository.getById(projectTypeId);
			String sql = getSqlBarChart(projectType, goal.getRestrictions(false),
					goal.getMeasureDisplay(), getMeasureDataType(),
					goal.getDateFrom(),
					Env.getUserRolePermissions());	// logged in role
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{
				pstmt = DB.prepareStatement(sql, null);
				rs = pstmt.executeQuery();
				while (rs.next())
				{
					BigDecimal data = rs.getBigDecimal(1);
					Timestamp date = rs.getTimestamp(2);
					int id = rs.getInt(3);
					GraphColumn bgc = new GraphColumn(projectType, data, id);
					bgc.setLabel(date, goal.getMeasureDisplay());
					list.add(bgc);
				}
			}
			catch (Exception e)
			{
				log.error(sql, e);
			}
			finally
			{
				DB.close(rs, pstmt);
				rs = null;
				pstmt = null;
			}
		}	// Project

		return list;
	}

	/**
	 * String Representation
	 * 
	 * @return info
	 */
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer("MMeasure[");
		sb.append(get_ID()).append("-").append(getName()).append("]");
		return sb.toString();
	}	// toString

	/**
	 * Before Save
	 * 
	 * @param newRecord new
	 * @return true
	 */
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		if (MEASURETYPE_Calculated.equals(getMeasureType())
				&& getPA_MeasureCalc_ID() <= 0)
		{
			throw new FillMandatoryException("PA_MeasureCalc_ID");
		}
		else if (MEASURETYPE_Ratio.equals(getMeasureType())
				&& getPA_Ratio_ID() <= 0)
		{
			throw new FillMandatoryException("PA_Ratio_ID");
		}
		else if (MEASURETYPE_UserDefined.equals(getMeasureType())
				&& (getCalculationClass() == null || getCalculationClass().length() == 0))
		{
			throw new FillMandatoryException("CalculationClass");
		}
		else if (MEASURETYPE_Request.equals(getMeasureType())
				&& getR_RequestType_ID() <= 0)
		{
			throw new FillMandatoryException("R_RequestType_ID");
		}
		else if (MEASURETYPE_Project.equals(getMeasureType())
				&& getC_ProjectType_ID() <= 0)
		{
			throw new FillMandatoryException("C_ProjectType_ID");
		}
		return true;
	}	// beforeSave

	/**
	 * After Save
	 * 
	 * @param newRecord new
	 * @param success success
	 * @return succes
	 */
	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		// Update Goals with Manual Measure
		if (success && MEASURETYPE_Manual.equals(getMeasureType()))
			updateManualGoals();

		return success;
	}	// afterSave

	/**
	 * Update/save Goals
	 * 
	 * @return true if updated
	 */
	public boolean updateGoals()
	{
		String mt = getMeasureType();
		try
		{
			if (MEASURETYPE_Manual.equals(mt))
				return updateManualGoals();
			else if (MEASURETYPE_Achievements.equals(mt))
				return updateAchievementGoals();
			else if (MEASURETYPE_Calculated.equals(mt))
				return updateCalculatedGoals();
			else if (MEASURETYPE_Ratio.equals(mt))
				return updateRatios();
			else if (MEASURETYPE_Request.equals(mt))
				return updateRequests();
			else if (MEASURETYPE_Project.equals(mt))
				return updateProjects();
			// Projects
		}
		catch (Exception e)
		{
			log.error("MeasureType=" + mt, e);
		}
		return false;
	}	// updateGoals

	/**
	 * Update/save Manual Goals
	 * 
	 * @return true if updated
	 */
	private boolean updateManualGoals()
	{
		if (!MEASURETYPE_Manual.equals(getMeasureType()))
			return false;
		MGoal[] goals = MGoal.getMeasureGoals(getCtx(), getPA_Measure_ID());
		for (int i = 0; i < goals.length; i++)
		{
			MGoal goal = goals[i];
			goal.setMeasureActual(getManualActual());
			goal.save(get_TrxName());
		}
		return true;
	}	// updateManualGoals

	/**
	 * Update/save Goals with Achievement
	 * 
	 * @return true if updated
	 */
	private boolean updateAchievementGoals()
	{
		if (!MEASURETYPE_Achievements.equals(getMeasureType()))
			return false;
		Timestamp today = new Timestamp(System.currentTimeMillis());
		MGoal[] goals = MGoal.getMeasureGoals(getCtx(), getPA_Measure_ID());
		for (int i = 0; i < goals.length; i++)
		{
			MGoal goal = goals[i];
			String MeasureScope = goal.getMeasureScope();
			String trunc = TimeUtil.TRUNC_DAY;
			if (MGoal.MEASUREDISPLAY_Year.equals(MeasureScope))
				trunc = TimeUtil.TRUNC_YEAR;
			else if (MGoal.MEASUREDISPLAY_Quarter.equals(MeasureScope))
				trunc = TimeUtil.TRUNC_QUARTER;
			else if (MGoal.MEASUREDISPLAY_Month.equals(MeasureScope))
				trunc = TimeUtil.TRUNC_MONTH;
			else if (MGoal.MEASUREDISPLAY_Week.equals(MeasureScope))
				trunc = TimeUtil.TRUNC_WEEK;
			Timestamp compare = TimeUtil.trunc(today, trunc);
			//
			MAchievement[] achievements = MAchievement.getOfMeasure(getCtx(), getPA_Measure_ID());
			BigDecimal ManualActual = Env.ZERO;
			for (int j = 0; j < achievements.length; j++)
			{
				MAchievement achievement = achievements[j];
				if (achievement.isAchieved() && achievement.getDateDoc() != null)
				{
					Timestamp ach = TimeUtil.trunc(achievement.getDateDoc(), trunc);
					if (compare.equals(ach))
						ManualActual = ManualActual.add(achievement.getManualActual());
				}
			}
			goal.setMeasureActual(ManualActual);
			goal.save(get_TrxName());
		}
		return true;
	}	// updateAchievementGoals

	/**
	 * Update/save Goals with Calculation
	 * 
	 * @return true if updated
	 */
	private boolean updateCalculatedGoals()
	{
		if (!MEASURETYPE_Calculated.equals(getMeasureType()))
			return false;
		MGoal[] goals = MGoal.getMeasureGoals(getCtx(), getPA_Measure_ID());
		for (int i = 0; i < goals.length; i++)
		{
			MGoal goal = goals[i];
			// Find Role
			final IUserRolePermissions role = getEffectiveRolePermissions(goal);
			//
			MMeasureCalc mc = MMeasureCalc.get(getCtx(), getPA_MeasureCalc_ID());
			if (mc == null || mc.get_ID() == 0 || mc.get_ID() != getPA_MeasureCalc_ID())
			{
				log.error("Not found PA_MeasureCalc_ID=" + getPA_MeasureCalc_ID());
				return false;
			}
			String sql = mc.getSqlPI(goal.getRestrictions(false),
					goal.getMeasureScope(), getMeasureDataType(), null, role);
			BigDecimal ManualActual = DB.getSQLValueBD(null, sql, new Object[] {});
			// SQL may return no rows or null
			if (ManualActual == null)
			{
				ManualActual = Env.ZERO;
				log.debug("No Value = " + sql);
			}
			goal.setMeasureActual(ManualActual);
			goal.save(get_TrxName());
		}
		return true;
	}	// updateCalculatedGoals

	/**
	 * Update/save Goals with Ratios
	 * 
	 * @return true if updated
	 */
	private boolean updateRatios()
	{
		if (!MEASURETYPE_Ratio.equals(getMeasureType()))
			return false;
		return false;
	}		// updateRatios

	/**
	 * Update/save Goals with Requests
	 * 
	 * @return true if updated
	 */
	private boolean updateRequests()
	{
		if (!MEASURETYPE_Request.equals(getMeasureType())
				|| getR_RequestType_ID() == 0)
			return false;
		MGoal[] goals = MGoal.getMeasureGoals(getCtx(), getPA_Measure_ID());
		for (int i = 0; i < goals.length; i++)
		{
			MGoal goal = goals[i];
			// Find Role
			final IUserRolePermissions role = getEffectiveRolePermissions(goal);
			//
			MRequestType rt = MRequestType.get(getCtx(), getR_RequestType_ID());
			String sql = rt.getSqlPI(goal.getRestrictions(false),
					goal.getMeasureScope(), getMeasureDataType(), null, role);
			BigDecimal ManualActual = DB.getSQLValueBD(null, sql, new Object[] {});
			// SQL may return no rows or null
			if (ManualActual == null)
			{
				ManualActual = Env.ZERO;
				log.debug("No Value = " + sql);
			}
			goal.setMeasureActual(ManualActual);
			goal.save(get_TrxName());
		}
		return true;
	}		// updateRequests

	/**
	 * Update/save Goals with Projects
	 * 
	 * @return true if updated
	 */
	private boolean updateProjects()
	{
		if (!MEASURETYPE_Project.equals(getMeasureType())
				|| getC_ProjectType_ID() == 0)
			return false;
		MGoal[] goals = MGoal.getMeasureGoals(getCtx(), getPA_Measure_ID());
		for (int i = 0; i < goals.length; i++)
		{
			MGoal goal = goals[i];
			// Find Role
			final IUserRolePermissions role = getEffectiveRolePermissions(goal);
			//
			final ProjectTypeRepository projectTypeRepository = SpringContextHolder.instance.getBean(ProjectTypeRepository.class);
			final ProjectTypeId projectTypeId = ProjectTypeId.ofRepoId(getC_ProjectType_ID());
			final ProjectType projectType = projectTypeRepository.getById(projectTypeId);
			String sql = getSqlPI(projectType, goal.getRestrictions(false),
					goal.getMeasureScope(), getMeasureDataType(), null, role);
			BigDecimal ManualActual = DB.getSQLValueBD(null, sql, new Object[] {});
			// SQL may return no rows or null
			if (ManualActual == null)
			{
				ManualActual = Env.ZERO;
				log.debug("No Value = " + sql);
			}
			goal.setMeasureActual(ManualActual);
			goal.save(get_TrxName());
		}
		return true;
	}	// updateProjects

	private final IUserRolePermissions getEffectiveRolePermissions(final I_PA_Goal goal)
	{
		final RoleId goalRoleId = goal.getAD_Role_ID() > 0 ? RoleId.ofRepoId(goal.getAD_Role_ID()) : null;
		final UserId goalUserId = goal.getAD_User_ID() > 0 ? UserId.ofRepoId(goal.getAD_User_ID()) : null;
		final Properties ctx = getCtx();
		final UserId contextUserId = Env.getLoggedUserId(ctx);
		final ClientId contextClientId = Env.getClientId(ctx);
		final LocalDate contextDate = TimeUtil.asLocalDate(Env.getDate(ctx));

		IUserRolePermissions role = null;
		if (goalRoleId != null)
		{
			role = Services.get(IUserRolePermissionsDAO.class).getUserRolePermissions(
					goalRoleId,
					goalUserId != null ? goalUserId : contextUserId,
					contextClientId,
					contextDate);
		}
		else if (goalUserId != null)
		{
			final List<IUserRolePermissions> roles = Services.get(IUserRolePermissionsDAO.class)
					.retrieveUserRolesPermissionsForUserWithOrgAccess(
							Env.getClientId(), 
							OrgId.ofRepoId(getAD_Org_ID()),
							goalUserId,
							Env.getLocalDate()
							);
			if (!roles.isEmpty())
			{
				role = roles.get(0);
			}
		}

		// Fallback
		if (role == null)
		{
			role = Env.getUserRolePermissions(ctx);
		}

		return role;
	}


	/**
	 * 	Get Sql to return single value for the Performance Indicator
	 *	@param restrictions array of goal restrictions
	 *	@param MeasureScope scope of this value
	 *	@param MeasureDataType data type
	 *	@param reportDate optional report date
	 *	@param role role
	 *	@return sql for performance indicator
	 */
	private static String getSqlPI (
			ProjectType projectType,
			MGoalRestriction[] restrictions,
			String MeasureScope, String MeasureDataType, Timestamp reportDate,
			final IUserRolePermissions role)
	{
		String dateColumn = "Created";
		String orgColumn = "AD_Org_ID";
		String bpColumn = "C_BPartner_ID";
		String pColumn = null;
		//	PlannedAmt -> PlannedQty -> Count
		StringBuffer sb = new StringBuffer("SELECT COALESCE(SUM(PlannedAmt),COALESCE(SUM(PlannedQty),COUNT(*))) "
				+ "FROM C_Project WHERE C_ProjectType_ID=" + projectType.getId().getRepoId()
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

		//log.debug(sql);
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
	private static String getSqlBarChart (
			ProjectType projectType,
			MGoalRestriction[] restrictions,
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
		sb.append("WHERE C_Project.C_ProjectType_ID=").append(projectType.getId().getRepoId())
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
		//log.debug(sql);
		return sql;
	}	//	getSqlBarChart

	/**
	 * 	Get Zoom Query
	 */
	public static MQuery getQuery(
			ProjectType projectType,
			MGoalRestriction[] restrictions,
			String MeasureDisplay, Timestamp date, int C_Phase_ID,
			final IUserRolePermissions role)
	{
		String dateColumn = "Created";
		String orgColumn = "AD_Org_ID";
		String bpColumn = "C_BPartner_ID";
		String pColumn = null;
		//
		MQuery query = new MQuery("C_Project");
		query.addRangeRestriction("C_ProjectType_ID", "=", projectType.getId().getRepoId());
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
}	// MMeasure
