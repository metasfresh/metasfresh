/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2021 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */
package de.metas.project.process.legacy;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.compiere.model.I_C_ProjectLine;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.JavaProcess;

import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.model.MProject;
import org.compiere.model.MProjectIssue;
import org.compiere.model.MProjectLine;
import org.compiere.model.MStorage;
import org.compiere.model.MTimeExpense;
import org.compiere.model.MTimeExpenseLine;
import org.compiere.util.Env;

/**
 *  Issue to Project.
 *
 *	@author Jorg Janke
 *	@version $Id: ProjectIssue.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 */
@Deprecated
public class ProjectIssue extends JavaProcess
{
	/**	Project - Mandatory Parameter		*/
	private int 		m_C_Project_ID = 0;
	/**	Receipt - Option 1					*/
	private int 		m_M_InOut_ID = 0;
	/**	Expenses - Option 2					*/
	private int 		m_S_TimeExpense_ID = 0;
	/** Locator - Option 3,4				*/
	private int			m_M_Locator_ID = 0;
	/** Project Line - Option 3				*/
	private int 		m_C_ProjectLine_ID = 0;
	/** Product - Option 4					*/
	private int 		m_M_Product_ID = 0;
	/** Attribute - Option 4				*/
	@SuppressWarnings("unused")
	private int 		m_M_AttributeSetInstance_ID = 0;
	/** Qty - Option 4						*/
	private BigDecimal	m_MovementQty = null;
	/** Date - Option						*/
	private Timestamp	m_MovementDate = null;
	/** Description - Option				*/
	private String		m_Description = null;

	/**	The Project to be received			*/
	private MProject		m_project = null;
	/**	The Project to be received			*/
	private MProjectIssue[]	m_projectIssues = null;


	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParametersAsArray();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("C_Project_ID"))
				m_C_Project_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("M_InOut_ID"))
				m_M_InOut_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("S_TimeExpense_ID"))
				m_S_TimeExpense_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("M_Locator_ID"))
				m_M_Locator_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("C_ProjectLine_ID"))
				m_C_ProjectLine_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("M_Product_ID"))
				m_M_Product_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("M_AttributeSetInstance_ID"))
				m_M_AttributeSetInstance_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("MovementQty"))
				m_MovementQty = (BigDecimal)para[i].getParameter();
			else if (name.equals("MovementDate"))
				m_MovementDate = (Timestamp)para[i].getParameter();
			else if (name.equals("Description"))
				m_Description = (String)para[i].getParameter();
			else
				log.error("Unknown Parameter: " + name);
		  }
	}	//	prepare

	/**
	 *  Perform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		//	Check Parameter
		m_project = new MProject (getCtx(), m_C_Project_ID, get_TrxName());
		if (!(MProject.PROJECTCATEGORY_WorkOrderJob.equals(m_project.getProjectCategory())
			|| MProject.PROJECTCATEGORY_AssetProject.equals(m_project.getProjectCategory())))
			throw new IllegalArgumentException("Project not Work Order or Asset =" + m_project.getProjectCategory());
		log.info(m_project.toString());
		//
		if (m_M_InOut_ID != 0)
			return issueReceipt();
		if (m_S_TimeExpense_ID != 0)
			return issueExpense();
		if (m_M_Locator_ID == 0)
			throw new IllegalArgumentException("Locator missing");
		if (m_C_ProjectLine_ID != 0)
			return issueProjectLine();
		return issueInventory();
	}	//	doIt

	/**
	 *	Issue Receipt
	 *	@return Message (clear text)
	 */
	private String issueReceipt()
	{
		MInOut inOut = new MInOut (getCtx(), m_M_InOut_ID, null);
		if (inOut.isSOTrx() || !inOut.isProcessed()
			|| !(MInOut.DOCSTATUS_Completed.equals(inOut.getDocStatus()) || MInOut.DOCSTATUS_Closed.equals(inOut.getDocStatus())))
			throw new IllegalArgumentException ("Receipt not valid - " + inOut);
		log.info(inOut.toString());
		//	Set Project of Receipt
		if (inOut.getC_Project_ID() == 0)
		{
			inOut.setC_Project_ID(m_project.getC_Project_ID());
			inOut.save();
		}
		else if (inOut.getC_Project_ID() != m_project.getC_Project_ID())
			throw new IllegalArgumentException ("Receipt for other Project (" 
				+ inOut.getC_Project_ID() + ")");

		int counter = 0;
		for (final MInOutLine inOutLine : inOut.getLines())
		{
			//	Need to have a Product
			if (inOutLine.getM_Product_ID() <= 0)
				continue;
			//	Need to have Quantity
			if (inOutLine.getMovementQty() == null || inOutLine.getMovementQty().signum() == 0)
				continue;
			//	not issued yet
			if (projectIssueHasReceipt(inOutLine.getM_InOutLine_ID()))
				continue;
			//	Create Issue
			MProjectIssue pi = new MProjectIssue (m_project);
			pi.setMandatory (inOutLine.getM_Locator_ID(), inOutLine.getM_Product_ID(), inOutLine.getMovementQty());
			if (m_MovementDate != null)		//	default today
				pi.setMovementDate(m_MovementDate);
			if (m_Description != null && m_Description.length() > 0)
				pi.setDescription(m_Description);
			else if (inOutLine.getDescription() != null)
				pi.setDescription(inOutLine.getDescription());
			else if (inOut.getDescription() != null)
				pi.setDescription(inOut.getDescription());
			pi.setM_InOutLine_ID(inOutLine.getM_InOutLine_ID());
			pi.process();

			//	Find/Create Project Line
			MProjectLine pl = null;
			for (MProjectLine line : m_project.getLines())
			{
				//	The Order we generated is the same as the Order of the receipt
				if (line.getC_OrderPO_ID() == inOut.getC_Order_ID()
					&& line.getM_Product_ID() == inOutLine.getM_Product_ID()
					&& line.getC_ProjectIssue_ID() == 0)		//	not issued
				{
					pl = line;
					break;
				}
			}
			if (pl == null)
				pl = new MProjectLine(m_project);
			pl.setMProjectIssue(pi);		//	setIssue
			pl.save();
			addLog(pi.getLine(), pi.getMovementDate(), pi.getMovementQty(), null);
			counter++;
		}	//	all InOutLines

		return "@Created@ " + counter;
	}	//	issueReceipt


	/**
	 *	Issue Expense Report
	 *	@return Message (clear text)
	 */
	private String issueExpense()
	{
		//	Get Expense Report
		MTimeExpense expense = new MTimeExpense (getCtx(), m_S_TimeExpense_ID, get_TrxName());
		if (!expense.isProcessed())
		  throw new IllegalArgumentException ("Time+Expense not processed - " + expense);

		//	for all expense lines
		MTimeExpenseLine[] expenseLines = expense.getLines(false);
		int counter = 0;
		for (int i = 0; i < expenseLines.length; i++)
		{
			//	Need to have a Product
			if (expenseLines[i].getM_Product_ID() == 0)
				continue;
			//	Need to have Quantity
			if (expenseLines[i].getQty() == null || expenseLines[i].getQty().signum() == 0)
				continue;
			//	Need to the same project
			if (expenseLines[i].getC_Project_ID() != m_project.getC_Project_ID())
				continue;
			//	not issued yet
			if (projectIssueHasExpense(expenseLines[i].getS_TimeExpenseLine_ID()))
				continue;

			//	Find Location
			int M_Locator_ID = 0;
		//	MProduct product = new MProduct (getCtx(), expenseLines[i].getM_Product_ID());
		//	if (product.isStocked())
				M_Locator_ID = MStorage.getM_Locator_ID(expense.getM_Warehouse_ID(), 
					expenseLines[i].getM_Product_ID(), 0, 	//	no ASI
					expenseLines[i].getQty(), null);
			if (M_Locator_ID == 0)	//	Service/Expense - get default (and fallback)
				M_Locator_ID = expense.getM_Locator_ID();

			//	Create Issue
			MProjectIssue pi = new MProjectIssue (m_project);
			pi.setMandatory (M_Locator_ID, expenseLines[i].getM_Product_ID(), expenseLines[i].getQty());
			if (m_MovementDate != null)		//	default today
				pi.setMovementDate(m_MovementDate);
			if (m_Description != null && m_Description.length() > 0)
				pi.setDescription(m_Description);
			else if (expenseLines[i].getDescription() != null)
				pi.setDescription(expenseLines[i].getDescription());
			pi.setS_TimeExpenseLine_ID(expenseLines[i].getS_TimeExpenseLine_ID());
			pi.process();
			//	Find/Create Project Line
			MProjectLine pl = new MProjectLine(m_project);
			pl.setMProjectIssue(pi);		//	setIssue
			pl.save();
			addLog(pi.getLine(), pi.getMovementDate(), pi.getMovementQty(), null);
			counter++;
		}	//	allExpenseLines
		
		return "@Created@ " + counter;
	}	//	issueExpense


	/**
	 *	Issue Project Line
	 *	@return Message (clear text)
	 */
	private String issueProjectLine()
	{
		MProjectLine pl = new MProjectLine(getCtx(), m_C_ProjectLine_ID, get_TrxName());
		if (pl.getM_Product_ID() == 0)
			throw new IllegalArgumentException("Projet Line has no Product");
		if (pl.getC_ProjectIssue_ID() != 0)
			throw new IllegalArgumentException("Projet Line already been issued");
		if (m_M_Locator_ID == 0)
			throw new IllegalArgumentException("No Locator");
		//	Set to Qty 1
		if (pl.getPlannedQty() == null || pl.getPlannedQty().signum() == 0)
			pl.setPlannedQty(Env.ONE);
		//
		MProjectIssue pi = new MProjectIssue (m_project);
		pi.setMandatory (m_M_Locator_ID, pl.getM_Product_ID(), pl.getPlannedQty());
		if (m_MovementDate != null)		//	default today
			pi.setMovementDate(m_MovementDate);
		if (m_Description != null && m_Description.length() > 0)
			pi.setDescription(m_Description);
		else if (pl.getDescription() != null)
			pi.setDescription(pl.getDescription());
		pi.process();

		//	Update Line
		pl.setMProjectIssue(pi);
		pl.save();
		addLog(pi.getLine(), pi.getMovementDate(), pi.getMovementQty(), null);
		return "@Created@ 1";
	}	//	issueProjectLine


	/**
	 *	Issue from Inventory
	 *	@return Message (clear text)
	 */
	private String issueInventory()
	{
		if (m_M_Locator_ID == 0)
			throw new IllegalArgumentException("No Locator");
		if (m_M_Product_ID == 0)
			throw new IllegalArgumentException("No Product");
		//	Set to Qty 1
		if (m_MovementQty == null || m_MovementQty.signum() == 0)
			m_MovementQty = Env.ONE;
		//
		MProjectIssue pi = new MProjectIssue (m_project);
		pi.setMandatory (m_M_Locator_ID, m_M_Product_ID, m_MovementQty);
		if (m_MovementDate != null)		//	default today
			pi.setMovementDate(m_MovementDate);
		if (m_Description != null && m_Description.length() > 0)
			pi.setDescription(m_Description);
		pi.process();

		//	Create Project Line
		MProjectLine pl = new MProjectLine(m_project);
		pl.setMProjectIssue(pi);
		pl.save();
		addLog(pi.getLine(), pi.getMovementDate(), pi.getMovementQty(), null);
		return "@Created@ 1";
	}	//	issueInventory

	/**
	 * 	Check if Project Issue already has Expense
	 *	@param S_TimeExpenseLine_ID line
	 *	@return true if exists
	 */
	private boolean projectIssueHasExpense (int S_TimeExpenseLine_ID)
	{
		if (m_projectIssues == null)
			m_projectIssues = m_project.getIssues();
		for (int i = 0; i < m_projectIssues.length; i++)
		{
			if (m_projectIssues[i].getS_TimeExpenseLine_ID() == S_TimeExpenseLine_ID)
				return true;
		}
		return false;
	}	//	projectIssueHasExpense

	/**
	 * 	Check if Project Issue already has Receipt
	 *	@param M_InOutLine_ID line
	 *	@return true if exists
	 */
	private boolean projectIssueHasReceipt (int M_InOutLine_ID)
	{
		if (m_projectIssues == null)
			m_projectIssues = m_project.getIssues();
		for (int i = 0; i < m_projectIssues.length; i++)
		{
			if (m_projectIssues[i].getM_InOutLine_ID() == M_InOutLine_ID)
				return true;
		}
		return false;
	}	//	projectIssueHasReceipt

}	//	ProjectIssue
