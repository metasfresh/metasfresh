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

import de.metas.pricing.service.IPriceListDAO;
import de.metas.util.Services;
import org.adempiere.exceptions.DBException;
import org.compiere.util.DB;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Project Model
 *
 * @author Jorg Janke
 * @version $Id: MProject.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 */
public class MProject extends X_C_Project
{
	/**
	 *
	 */
	private static final long serialVersionUID = -1781787100948563589L;

	/**
	 * Create new Project by copying
	 *
	 * @param ctx          context
	 * @param C_Project_ID project
	 * @param dateDoc      date of the document date
	 * @param trxName      transaction
	 * @return Project
	 */
	public static MProject copyFrom(final Properties ctx, final int C_Project_ID, final Timestamp dateDoc, final String trxName)
	{
		final MProject from = new MProject(ctx, C_Project_ID, trxName);
		if (from.getC_Project_ID() == 0)
			throw new IllegalArgumentException("From Project not found C_Project_ID=" + C_Project_ID);
		//
		final MProject to = new MProject(ctx, 0, trxName);
		PO.copyValues(from, to, from.getAD_Client_ID(), from.getAD_Org_ID());
		to.set_ValueNoCheck("C_Project_ID", I_ZERO);
		//	Set Value with Time
		String Value = to.getValue() + " ";
		final String Time = dateDoc.toString();
		final int length = Value.length() + Time.length();
		if (length <= 40)
			Value += Time;
		else
			Value += Time.substring(length - 40);
		to.setValue(Value);
		to.setInvoicedAmt(BigDecimal.ZERO);
		to.setProjectBalanceAmt(BigDecimal.ZERO);
		to.setProcessed(false);
		//
		if (!to.save())
			throw new IllegalStateException("Could not create Project");

		if (to.copyDetailsFrom(from) == 0)
			throw new IllegalStateException("Could not create Project Details");

		return to;
	}    //	copyFrom

	public MProject(final Properties ctx, final int C_Project_ID, final String trxName)
	{
		super(ctx, C_Project_ID, trxName);
		if (is_new())
		{
			//	setC_Project_ID(0);
			//	setValue (null);
			//	setC_Currency_ID (0);
			setCommittedAmt(BigDecimal.ZERO);
			setCommittedQty(BigDecimal.ZERO);
			setInvoicedAmt(BigDecimal.ZERO);
			setInvoicedQty(BigDecimal.ZERO);
			setPlannedAmt(BigDecimal.ZERO);
			setPlannedMarginAmt(BigDecimal.ZERO);
			setPlannedQty(BigDecimal.ZERO);
			setProjectBalanceAmt(BigDecimal.ZERO);
			//	setProjectCategory(PROJECTCATEGORY_General);
			setProjInvoiceRule(PROJINVOICERULE_None);
			setProjectLineLevel(PROJECTLINELEVEL_Project);
			setIsCommitCeiling(false);
			setIsCommitment(false);
			setIsSummary(false);
			setProcessed(false);
		}
	}    //	MProject

	/**
	 * Load Constructor
	 *
	 * @param ctx     context
	 * @param rs      result set
	 * @param trxName transaction
	 */
	public MProject(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}    //	MProject

	/**
	 * Cached PL
	 */
	private int m_M_PriceList_ID = 0;

	/**
	 * String Representation
	 *
	 * @return info
	 */
	@Override
	public String toString()
	{
		return "MProject[" + get_ID()
				+ "-" + getValue() + ",ProjectCategory=" + getProjectCategory()
				+ "]";
	}    //	toString

	/**
	 * Get Price List from Price List Version
	 *
	 * @return price list or 0
	 */
	public int getM_PriceList_ID()
	{
		if (getM_PriceList_Version_ID() == 0)
			return 0;
		if (m_M_PriceList_ID > 0)
			return m_M_PriceList_ID;
		//
		final String sql = "SELECT M_PriceList_ID FROM M_PriceList_Version WHERE M_PriceList_Version_ID=?";
		m_M_PriceList_ID = DB.getSQLValue(null, sql, getM_PriceList_Version_ID());
		return m_M_PriceList_ID;
	}    //	getM_PriceList_ID

	/**
	 * Set PL Version
	 *
	 * @param M_PriceList_Version_ID id
	 */
	@Override
	public void setM_PriceList_Version_ID(final int M_PriceList_Version_ID)
	{
		super.setM_PriceList_Version_ID(M_PriceList_Version_ID);
		m_M_PriceList_ID = 0;    //	reset
	}    //	setM_PriceList_Version_ID

	public List<MProjectLine> getLines()
	{
		final String sql = "SELECT * FROM C_ProjectLine WHERE C_Project_ID=? ORDER BY Line";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, getC_Project_ID());
			rs = pstmt.executeQuery();

			final ArrayList<MProjectLine> list = new ArrayList<>();
			while (rs.next())
			{
				list.add(new MProjectLine(getCtx(), rs, get_TrxName()));
			}

			return list;
		}
		catch (final SQLException ex)
		{
			throw new DBException(ex, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	/**
	 * Get Project Issues
	 *
	 * @return Array of issues
	 */
	public MProjectIssue[] getIssues()
	{
		ArrayList<MProjectIssue> list = new ArrayList<>();
		String sql = "SELECT * FROM C_ProjectIssue WHERE C_Project_ID=? ORDER BY Line";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, getC_Project_ID());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
				list.add(new MProjectIssue(getCtx(), rs, get_TrxName()));
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
		MProjectIssue[] retValue = new MProjectIssue[list.size()];
		list.toArray(retValue);
		return retValue;
	}    //	getIssues

	/**
	 * Get Project Phases
	 *
	 * @return Array of phases
	 */
	public MProjectPhase[] getPhases()
	{
		ArrayList<MProjectPhase> list = new ArrayList<>();
		String sql = "SELECT * FROM C_ProjectPhase WHERE C_Project_ID=? ORDER BY SeqNo";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, getC_Project_ID());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
				list.add(new MProjectPhase(getCtx(), rs, get_TrxName()));
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
		catch (final SQLException ex1)
		{
		}
		pstmt = null;
		//
		MProjectPhase[] retValue = new MProjectPhase[list.size()];
		list.toArray(retValue);
		return retValue;
	}    //	getPhases

	/**************************************************************************
	 * 	Copy Lines/Phase/Task from other Project
	 *    @param project project
	 *    @return number of total lines copied
	 */
	public int copyDetailsFrom(final MProject project)
	{
		if (isProcessed() || project == null)
			return 0;
		return copyLinesFrom(project)
				+ copyPhasesFrom(project);
	}    //	copyDetailsFrom

	/**
	 * Copy Lines From other Project
	 *
	 * @param project project
	 * @return number of lines copied
	 */
	public int copyLinesFrom(final MProject project)
	{
		if (isProcessed() || project == null)
			return 0;
		int count = 0;
		final List<MProjectLine> fromLines = project.getLines();
		for (final MProjectLine fromLine : fromLines)
		{
			final MProjectLine line = new MProjectLine(getCtx(), 0, project.get_TrxName());
			PO.copyValues(fromLine, line, getAD_Client_ID(), getAD_Org_ID());
			line.setC_Project_ID(getC_Project_ID());
			line.setInvoicedAmt(BigDecimal.ZERO);
			line.setInvoicedQty(BigDecimal.ZERO);
			line.setC_OrderPO_ID(0);
			line.setC_Order_ID(0);
			line.setProcessed(false);
			if (line.save())
				count++;
		}
		if (fromLines.size() != count)
			log.error("Lines difference - Project=" + fromLines.size() + " <> Saved=" + count);

		return count;
	}    //	copyLinesFrom

	/**
	 * Copy Phases/Tasks from other Project
	 *
	 * @param fromProject project
	 * @return number of items copied
	 */
	private int copyPhasesFrom(final MProject fromProject)
	{
		if (isProcessed() || fromProject == null)
			return 0;
		int count = 0;
		int taskCount = 0;
		//	Get Phases
		final MProjectPhase[] myPhases = getPhases();
		final MProjectPhase[] fromPhases = fromProject.getPhases();
		//	Copy Phases
		for (final MProjectPhase fromPhase : fromPhases)
		{
			//	Check if Phase already exists
			final int C_Phase_ID = fromPhase.getC_Phase_ID();
			boolean exists = false;
			if (C_Phase_ID == 0)
				exists = false;
			else
			{
				for (final MProjectPhase myPhase : myPhases)
				{
					if (myPhase.getC_Phase_ID() == C_Phase_ID)
					{
						exists = true;
						break;
					}
				}
			}
			//	Phase exist
			if (exists)
				log.info("Phase already exists here, ignored - " + fromPhase);
			else
			{
				final MProjectPhase toPhase = new MProjectPhase(getCtx(), 0, get_TrxName());
				PO.copyValues(fromPhase, toPhase, getAD_Client_ID(), getAD_Org_ID());
				toPhase.setC_Project_ID(getC_Project_ID());
				toPhase.setC_Order_ID(0);
				toPhase.setIsComplete(false);
				if (toPhase.save())
				{
					count++;
					taskCount += toPhase.copyTasksFrom(fromPhase);
				}
			}
		}
		if (fromPhases.length != count)
			log.warn("Count difference - Project=" + fromPhases.length + " <> Saved=" + count);

		return count + taskCount;
	}    //	copyPhasesFrom

	@Override
	protected boolean beforeSave(final boolean newRecord)
	{
		if (getAD_User_ID() == -1)    //	Summary Project in Dimensions
			setAD_User_ID(0);

		//	Set Currency
		if (is_ValueChanged("M_PriceList_Version_ID") && getM_PriceList_Version_ID() != 0)
		{
			final I_M_PriceList pl = Services.get(IPriceListDAO.class).getById(getM_PriceList_ID());
			if (pl != null && pl.getM_PriceList_ID() > 0)
				setC_Currency_ID(pl.getC_Currency_ID());
		}

		return true;
	}    //	beforeSave

	/**
	 * After Save
	 *
	 * @param newRecord new
	 * @param success   success
	 * @return success
	 */
	@Override
	protected boolean afterSave(final boolean newRecord, final boolean success)
	{
		if (newRecord && success)
		{
			insert_Accounting("C_Project_Acct", "C_AcctSchema_Default", null);
		}

		//	Value/Name change
		if (success && !newRecord
				&& (is_ValueChanged("Value") || is_ValueChanged("Name")))
			MAccount.updateValueDescription(getCtx(), "C_Project_ID=" + getC_Project_ID(), get_TrxName());

		return success;
	}    //	afterSave

	/**
	 * Before Delete
	 *
	 * @return true
	 */
	@Override
	protected boolean beforeDelete()
	{
		return delete_Accounting("C_Project_Acct");
	}    //	beforeDelete
}
