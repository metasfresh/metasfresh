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

import java.awt.Color;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.service.ClientId;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import de.metas.i18n.Msg;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.security.IUserRolePermissions;
import de.metas.security.IUserRolePermissionsDAO;
import de.metas.security.permissions.Access;
import de.metas.user.UserId;
import de.metas.util.Services;

/**
 * Performance Goal
 * 
 * @author Jorg Janke
 * @version $Id: MGoal.java,v 1.2 2006/07/30 00:51:03 jjanke Exp $
 * 
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 *         <li>BF [ 1887674 ] Deadlock when try to modify PA Goal's Measure Target
 *         <li>BF [ 1760482 ] New Dashboard broke old functionality
 *         <li>BF [ 1887691 ] I get NPE if the PA Goal's target is 0
 */
public class MGoal extends X_PA_Goal
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4612113288233473730L;

	/**
	 * Get User Goals
	 * 
	 * @param ctx context
	 * @param AD_User_ID user
	 * @return array of goals
	 */
	public static MGoal[] getUserGoals(Properties ctx, int AD_User_ID)
	{
		if (AD_User_ID < 0)
			return getTestGoals(ctx);
		ArrayList<MGoal> list = new ArrayList<MGoal>();
		String sql = "SELECT * FROM PA_Goal g "
				+ "WHERE IsActive='Y'"
				+ " AND AD_Client_ID=?"		// #1
				+ " AND ((AD_User_ID IS NULL AND AD_Role_ID IS NULL)"
				+ " OR AD_User_ID=?"	// #2
				+ " OR EXISTS (SELECT * FROM AD_User_Roles ur "
				+ "WHERE ur.AD_User_ID=? AND g.AD_Role_ID=ur.AD_Role_ID AND ur.IsActive='Y')) "
				+ "ORDER BY SeqNo";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, Env.getAD_Client_ID(ctx));
			pstmt.setInt(2, AD_User_ID);
			pstmt.setInt(3, AD_User_ID);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				MGoal goal = new MGoal(ctx, rs, null);
				goal.updateGoal(false);
				list.add(goal);
			}
		}
		catch (Exception e)
		{
			s_log.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		MGoal[] retValue = new MGoal[list.size()];
		list.toArray(retValue);
		return retValue;
	}	// getUserGoals

	/**
	 * Get Accessible Goals
	 * 
	 * @param ctx context
	 * @return array of goals
	 */
	public static MGoal[] getGoals(Properties ctx)
	{
		ArrayList<MGoal> list = new ArrayList<MGoal>();
		String sql = "SELECT * FROM PA_Goal WHERE IsActive='Y' "
				+ "ORDER BY SeqNo";
		sql = Env.getUserRolePermissions(ctx).addAccessSQL(sql, "PA_Goal", IUserRolePermissions.SQL_NOTQUALIFIED, Access.WRITE);	// RW to restrict Access
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				MGoal goal = new MGoal(ctx, rs, null);
				goal.updateGoal(false);
				list.add(goal);
			}
		}
		catch (Exception e)
		{
			s_log.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		MGoal[] retValue = new MGoal[list.size()];
		list.toArray(retValue);
		return retValue;
	}	// getGoals

	/**
	 * Create Test Goals
	 * 
	 * @param ctx context
	 * @return array of goals
	 */
	public static MGoal[] getTestGoals(Properties ctx)
	{
		MGoal[] retValue = new MGoal[4];
		retValue[0] = new MGoal(ctx, "Test 1", "Description 1", new BigDecimal(1000), null);
		retValue[0].setMeasureActual(new BigDecimal(200));
		retValue[1] = new MGoal(ctx, "Test 2", "Description 2", new BigDecimal(1000), null);
		retValue[1].setMeasureActual(new BigDecimal(900));
		retValue[2] = new MGoal(ctx, "Test 3", "Description 3", new BigDecimal(1000), null);
		retValue[2].setMeasureActual(new BigDecimal(1200));
		retValue[3] = new MGoal(ctx, "Test 4", "Description 4", new BigDecimal(1000), null);
		retValue[3].setMeasureActual(new BigDecimal(3200));
		return retValue;
	}	// getTestGoals

	/**
	 * Get Goals with Measure
	 * 
	 * @param ctx context
	 * @param PA_Measure_ID measure
	 * @return goals
	 */
	public static MGoal[] getMeasureGoals(Properties ctx, int PA_Measure_ID)
	{
		ArrayList<MGoal> list = new ArrayList<MGoal>();
		String sql = "SELECT * FROM PA_Goal WHERE IsActive='Y' AND PA_Measure_ID=? "
				+ "ORDER BY SeqNo";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, PA_Measure_ID);
			rs = pstmt.executeQuery();
			while (rs.next())
				list.add(new MGoal(ctx, rs, null));
		}
		catch (Exception e)
		{
			s_log.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		MGoal[] retValue = new MGoal[list.size()];
		list.toArray(retValue);
		return retValue;
	}	// getMeasureGoals

	/**
	 * Get Multiplier from Scope to Display
	 * 
	 * @param goal goal
	 * @return null if error or multiplier
	 */
	public static BigDecimal getMultiplier(MGoal goal)
	{
		String MeasureScope = goal.getMeasureScope();
		String MeasureDisplay = goal.getMeasureDisplay();
		if (MeasureDisplay == null
				|| MeasureScope.equals(MeasureDisplay))
			return Env.ONE;		// 1:1

		if (MeasureScope.equals(MEASURESCOPE_Total)
				|| MeasureDisplay.equals(MEASUREDISPLAY_Total))
			return null;		// Error

		BigDecimal Multiplier = null;
		if (MeasureScope.equals(MEASURESCOPE_Year))
		{
			if (MeasureDisplay.equals(MEASUREDISPLAY_Quarter))
				Multiplier = new BigDecimal(1.0 / 4.0);
			else if (MeasureDisplay.equals(MEASUREDISPLAY_Month))
				Multiplier = new BigDecimal(1.0 / 12.0);
			else if (MeasureDisplay.equals(MEASUREDISPLAY_Week))
				Multiplier = new BigDecimal(1.0 / 52.0);
			else if (MeasureDisplay.equals(MEASUREDISPLAY_Day))
				Multiplier = new BigDecimal(1.0 / 364.0);
		}
		else if (MeasureScope.equals(MEASURESCOPE_Quarter))
		{
			if (MeasureDisplay.equals(MEASUREDISPLAY_Year))
				Multiplier = new BigDecimal(4.0);
			else if (MeasureDisplay.equals(MEASUREDISPLAY_Month))
				Multiplier = new BigDecimal(1.0 / 3.0);
			else if (MeasureDisplay.equals(MEASUREDISPLAY_Week))
				Multiplier = new BigDecimal(1.0 / 13.0);
			else if (MeasureDisplay.equals(MEASUREDISPLAY_Day))
				Multiplier = new BigDecimal(1.0 / 91.0);
		}
		else if (MeasureScope.equals(MEASURESCOPE_Month))
		{
			if (MeasureDisplay.equals(MEASUREDISPLAY_Year))
				Multiplier = new BigDecimal(12.0);
			else if (MeasureDisplay.equals(MEASUREDISPLAY_Quarter))
				Multiplier = new BigDecimal(3.0);
			else if (MeasureDisplay.equals(MEASUREDISPLAY_Week))
				Multiplier = new BigDecimal(1.0 / 4.0);
			else if (MeasureDisplay.equals(MEASUREDISPLAY_Day))
				Multiplier = new BigDecimal(1.0 / 30.0);
		}
		else if (MeasureScope.equals(MEASURESCOPE_Week))
		{
			if (MeasureDisplay.equals(MEASUREDISPLAY_Year))
				Multiplier = new BigDecimal(52.0);
			else if (MeasureDisplay.equals(MEASUREDISPLAY_Quarter))
				Multiplier = new BigDecimal(13.0);
			else if (MeasureDisplay.equals(MEASUREDISPLAY_Month))
				Multiplier = new BigDecimal(4.0);
			else if (MeasureDisplay.equals(MEASUREDISPLAY_Day))
				Multiplier = new BigDecimal(1.0 / 7.0);
		}
		else if (MeasureScope.equals(MEASURESCOPE_Day))
		{
			if (MeasureDisplay.equals(MEASUREDISPLAY_Year))
				Multiplier = new BigDecimal(364.0);
			else if (MeasureDisplay.equals(MEASUREDISPLAY_Quarter))
				Multiplier = new BigDecimal(91.0);
			else if (MeasureDisplay.equals(MEASUREDISPLAY_Month))
				Multiplier = new BigDecimal(30.0);
			else if (MeasureDisplay.equals(MEASUREDISPLAY_Week))
				Multiplier = new BigDecimal(7.0);
		}
		return Multiplier;
	}	// getMultiplier

	/** Logger */
	private static Logger s_log = LogManager.getLogger(MGoal.class);

	/**************************************************************************
	 * Standard Constructor
	 * 
	 * @param ctx context
	 * @param PA_Goal_ID id
	 * @param trxName trx
	 */
	public MGoal(Properties ctx, int PA_Goal_ID, String trxName)
	{
		super(ctx, PA_Goal_ID, trxName);
		if (PA_Goal_ID == 0)
		{
			// setName (null);
			// setAD_User_ID (0);
			// setPA_ColorSchema_ID (0);
			setSeqNo(0);
			setIsSummary(false);
			setMeasureScope(MEASUREDISPLAY_Year);
			setGoalPerformance(Env.ZERO);
			setRelativeWeight(Env.ONE);
			setMeasureTarget(Env.ZERO);
			setMeasureActual(Env.ZERO);
		}
	}	// MGoal

	/**
	 * Load Constructor
	 * 
	 * @param ctx context
	 * @param rs result set
	 * @param trxName trx
	 */
	public MGoal(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	// MGoal

	/**
	 * Base Constructor
	 * 
	 * @param ctx context
	 * @param Name Name
	 * @param Description Decsription
	 * @param MeasureTarget target
	 * @param trxName trx
	 */
	public MGoal(Properties ctx, String Name, String Description,
			BigDecimal MeasureTarget, String trxName)
	{
		super(ctx, 0, trxName);
		setName(Name);
		setDescription(Description);
		setMeasureTarget(MeasureTarget);
	}	// MGoal

	/** Restrictions */
	private MGoalRestriction[] m_restrictions = null;
	/** Performance Color */
	private Color m_color = null;

	/**
	 * Get Restriction Lines
	 * 
	 * @param reload reload data
	 * @return array of lines
	 */
	public MGoalRestriction[] getRestrictions(boolean reload)
	{
		if (m_restrictions != null && !reload)
			return m_restrictions;
		ArrayList<MGoalRestriction> list = new ArrayList<MGoalRestriction>();
		//
		String sql = "SELECT * FROM PA_GoalRestriction "
				+ "WHERE PA_Goal_ID=? AND IsActive='Y' "
				+ "ORDER BY Org_ID, C_BPartner_ID, M_Product_ID";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, getPA_Goal_ID());
			rs = pstmt.executeQuery();
			while (rs.next())
				list.add(new MGoalRestriction(getCtx(), rs, get_TrxName()));
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
		//
		m_restrictions = new MGoalRestriction[list.size()];
		list.toArray(m_restrictions);
		return m_restrictions;
	}	// getRestrictions

	/**
	 * Get Measure
	 * 
	 * @return measure or null
	 */
	public MMeasure getMeasure()
	{
		if (getPA_Measure_ID() != 0)
			return MMeasure.get(getCtx(), getPA_Measure_ID());
		return null;
	}	// getMeasure

	/**************************************************************************
	 * Update/save Goals for the same measure
	 * 
	 * @param force force to update goal (default once per day)
	 * @return true if updated
	 */
	public boolean updateGoal(boolean force)
	{
		log.info("Force=" + force);
		MMeasure measure = MMeasure.get(getCtx(), getPA_Measure_ID());
		if (force
				|| getDateLastRun() == null
				|| !TimeUtil.isSameHour(getDateLastRun(), null))
		{
			measure.set_TrxName(get_TrxName());
			if (measure.updateGoals())		// saves
			{
				load(get_ID(), get_TrxName());
				return true;
			}
		}
		return false;
	}	// updateGoal

	/**
	 * Set Measure Actual
	 * 
	 * @param MeasureActual actual
	 */
	@Override
	public void setMeasureActual(BigDecimal MeasureActual)
	{
		if (MeasureActual == null)
			return;
		super.setMeasureActual(MeasureActual);
		setDateLastRun(new Timestamp(System.currentTimeMillis()));
		setGoalPerformance();
	}	// setMeasureActual

	/**
	 * Calculate Performance Goal as multiplier
	 */
	public void setGoalPerformance()
	{
		BigDecimal MeasureTarget = getMeasureTarget();
		BigDecimal MeasureActual = getMeasureActual();
		BigDecimal GoalPerformance = Env.ZERO;
		if (MeasureTarget.signum() != 0)
			GoalPerformance = MeasureActual.divide(MeasureTarget, 6, BigDecimal.ROUND_HALF_UP);
		super.setGoalPerformance(GoalPerformance);
		m_color = null;
	}	// setGoalPerformance

	/**
	 * Get Goal Performance as Double
	 * 
	 * @return performance as multipier
	 */
	public double getGoalPerformanceDouble()
	{
		BigDecimal bd = getGoalPerformance();
		return bd.doubleValue();
	}	// getGoalPerformanceDouble

	/**
	 * Get Goal Performance in Percent
	 * 
	 * @return performance in percent
	 */
	public int getPercent()
	{
		BigDecimal bd = getGoalPerformance().multiply(Env.ONEHUNDRED);
		return bd.intValue();
	}	// getPercent

	/**
	 * Get Color
	 * 
	 * @return color - white if no target
	 */
	public Color getColor()
	{
		if (m_color == null)
		{
			if (getMeasureTarget().signum() == 0)
				m_color = Color.white;
			else
				m_color = MColorSchema.getColor(getCtx(), getPA_ColorSchema_ID(), getPercent());
		}
		return m_color;
	}	// getColor

	/**
	 * Get the color schema for this goal.
	 * 
	 * @return the color schema
	 */
	public MColorSchema getColorSchema()
	{
		return MColorSchema.get(getCtx(), getPA_ColorSchema_ID());
	}

	/**
	 * Get Measure Display
	 * 
	 * @return Measure Display
	 */
	@Override
	public String getMeasureDisplay()
	{
		String s = super.getMeasureDisplay();
		if (s == null)
		{
			if (MEASURESCOPE_Week.equals(getMeasureScope()))
				s = MEASUREDISPLAY_Week;
			else if (MEASURESCOPE_Day.equals(getMeasureScope()))
				s = MEASUREDISPLAY_Day;
			else
				s = MEASUREDISPLAY_Month;
		}
		return s;
	}	// getMeasureDisplay

	/**
	 * Get Measure Display Text
	 * 
	 * @return Measure Display Text
	 */
	public String getXAxisText()
	{
		MMeasure measure = getMeasure();
		if (measure != null
				&& MMeasure.MEASUREDATATYPE_StatusQtyAmount.equals(measure.getMeasureDataType()))
		{
			if (MMeasure.MEASURETYPE_Request.equals(measure.getMeasureType()))
				return Msg.getElement(getCtx(), "R_Status_ID");
			if (MMeasure.MEASURETYPE_Project.equals(measure.getMeasureType()))
				return Msg.getElement(getCtx(), "C_Phase_ID");
		}
		String value = getMeasureDisplay();
		String display = MRefList.getListName(getCtx(), MEASUREDISPLAY_AD_Reference_ID, value);
		return display == null ? value : display;
	}	// getMeasureDisplayText

	/**
	 * Goal has Target
	 * 
	 * @return true if target
	 */
	public boolean isTarget()
	{
		return getMeasureTarget().signum() != 0;
	}	// isTarget

	/**
	 * String Representation
	 * 
	 * @return info
	 */
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer("MGoal[");
		sb.append(get_ID())
				.append("-").append(getName())
				.append(",").append(getGoalPerformance())
				.append("]");
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
		// if (getMultiplier(this) == null) // error
		// setMeasureDisplay(getMeasureScope());

		// Measure required if nor Summary
		if (!isSummary() && getPA_Measure_ID() == 0)
		{
			throw new FillMandatoryException("PA_Measure_ID");
		}
		if (isSummary() && getPA_Measure_ID() != 0)
			setPA_Measure_ID(0);

		// User/Role Check
		if ((newRecord || is_ValueChanged("AD_User_ID") || is_ValueChanged("AD_Role_ID"))
				&& getAD_User_ID() != 0)
		{
			final List<IUserRolePermissions> roles = Services.get(IUserRolePermissionsDAO.class)
					.retrieveUserRolesPermissionsForUserWithOrgAccess(
							ClientId.ofRepoId(getAD_Client_ID()),
							OrgId.ofRepoId(getAD_Org_ID()),
							UserId.ofRepoId(getAD_User_ID()),
							Env.getLocalDate());
			if (roles.isEmpty())		// No Role
			{
				setAD_Role_ID(0);
			}
			else if (roles.size() == 1)	// One
			{
				setAD_Role_ID(roles.get(0).getRoleId().getRepoId());
			}
			else
			{
				int AD_Role_ID = getAD_Role_ID();
				if (AD_Role_ID != 0)	// validate
				{
					boolean found = false;
					for (IUserRolePermissions role : roles)
					{
						if (AD_Role_ID == role.getRoleId().getRepoId())
						{
							found = true;
							break;
						}
					}
					if (!found)
						AD_Role_ID = 0;
				}
				if (AD_Role_ID == 0)		// set to first one
					setAD_Role_ID(roles.get(0).getRoleId().getRepoId());
			}	// multiple roles
		}	// user check

		return true;
	}	// beforeSave

	/**
	 * After Save
	 * 
	 * @param newRecord new
	 * @param success success
	 * @return true
	 */
	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		if (!success)
			return success;

		// Update Goal if Target / Scope Changed
		if (newRecord
				|| is_ValueChanged("MeasureTarget")
				|| is_ValueChanged("MeasureScope"))
			updateGoal(true);

		return success;
	}

}	// MGoal
