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
package org.compiere.apps;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.ui.api.IWindowBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Menu;
import org.compiere.model.I_AD_WF_Node;
import org.compiere.model.MTask;
import org.compiere.model.MTreeNode;
import org.compiere.model.X_AD_Menu;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.slf4j.Logger;

import de.metas.adempiere.form.IClientUI;
import de.metas.logging.LogManager;
import de.metas.logging.LogManager;

/**
 * Start application action ( process, workflow, window, form, task etc).
 *
 * @author Jorg Janke
 */
public class AMenuStartItem extends SwingWorker<Void, Void>
{
	/**
	 * Start menu item action asynchronously
	 * 
	 * @param node
	 * @param menu
	 */
	public static final void startMenuItem(final MTreeNode node, final AMenu menu)
	{
		new AMenuStartItem(node, menu).start();
	}

	/**
	 * Start menu item action asynchronously
	 * 
	 * @param adMenuId
	 * @param name
	 * @param menu
	 */
	public static final void startMenuItemById(final int adMenuId, final String name, final AMenu menu)
	{
		final boolean isMenu = true;
		new AMenuStartItem(adMenuId, isMenu, name, menu).start();
	}
	
	public static final void startWFNode(final I_AD_WF_Node wfNode, final AMenu menu)
	{
		new AMenuStartItem(wfNode, menu).start();
	}

	/**
	 * Start Menu Item
	 *
	 * @param ID ID
	 * @param isMenu false if Workflow
	 * @param name Name
	 * @param menu Menu
	 */
	private AMenuStartItem(final int ID, final boolean isMenu, final String name, final AMenu menu)
	{
		super();
		m_ID = ID;
		m_isMenu = isMenu;
		m_name = name;

		m_menu = menu == null ? AEnv.getAMenu() : menu;
	}

	private AMenuStartItem(final MTreeNode node, final AMenu menu)
	{
		super();
		Check.assumeNotNull(node, "node not null");
		m_ID = node.getNode_ID();
		m_isMenu = true;
		m_name = node.getName();

		m_menu = menu == null ? AEnv.getAMenu() : menu;
	}
	
	private AMenuStartItem(final I_AD_WF_Node wfNode, final AMenu menu)
	{
		super();
		Check.assumeNotNull(wfNode, "node not null");
		m_ID = wfNode.getAD_WF_Node_ID();
		m_isMenu = false;
		m_name = wfNode.getName();

		m_menu = menu == null ? AEnv.getAMenu() : menu;

		//
		// Load
		this.action = wfNode.getAction();
		this.adWindowId = wfNode.getAD_Window_ID();
		this.adWorkbenchId = -1;
		this.adProcessId = wfNode.getAD_Process_ID();
		this.adFormId = wfNode.getAD_Form_ID();
		this.adTaskId = wfNode.getAD_Task_ID();
		this.adWorkflowId = wfNode.getWorkflow_ID();
		this.IsSOTrx = true;
		this.loaded = true;
	}

	/** Logger */
	private static final transient Logger logger = LogManager.getLogger(AMenuStartItem.class);

	/** The ID (AD_Menu_ID or AD_WF_Node_ID) */
	private final int m_ID;
	/** true if {@link #m_ID} is AD_Menu_ID, false if {@link #m_ID} is AD_WF_Node_ID */
	private final boolean m_isMenu;
	private final String m_name;
	//
	private boolean loaded = false;
	private String action;
	private boolean IsSOTrx = true;
	private int adWindowId = -1;
	private int adWorkbenchId = -1;
	private int adProcessId = -1;
	private int adWorkflowId = -1;
	private int adFormId = -1;
	private int adTaskId = -1;

	private final AMenu m_menu;
	
	public final void start()
	{
		lock();
		execute();
	}

	private final void lock()
	{
		if (m_menu != null)
		{
			m_menu.setBusy(true);
		}
	}

	private final void unlock()
	{
		if (m_menu != null)
		{
			m_menu.setBusy(false);
		}
	}

	@Override
	protected Void doInBackground() throws Exception
	{
		loadIfNeeded();

		if (action == null)
		{
			// shall not happen
			throw new AdempiereException("Unknown action");
		}
		if (X_AD_Menu.ACTION_Window.equals(action))
		{
			startWindow(0, adWindowId);
		}
		else if (X_AD_Menu.ACTION_Process.equals(action)
				|| X_AD_Menu.ACTION_Report.equals(action))
		{
			startProcess(adProcessId, IsSOTrx);
		}
		else if (X_AD_Menu.ACTION_Workbench.equals(action))
		{
			startWindow(adWorkbenchId, 0);
		}
		else if (X_AD_Menu.ACTION_WorkFlow.equals(action))
		{
			startWorkflow(adWorkflowId);
		}
		else if (X_AD_Menu.ACTION_Task.equals(action))
		{
			startTask(adTaskId);
		}
		else if (X_AD_Menu.ACTION_Form.equals(action))
		{
			startForm(adFormId);
		}
		else
		{
			throw new AdempiereException("Unknown action " + action + " for " + m_ID);
		}

		return null;
	}

	private final void loadIfNeeded()
	{
		if (loaded)
		{
			return;
		}

		String sql = "SELECT * FROM AD_Menu WHERE AD_Menu_ID=?";
		if (!m_isMenu)
		{
			sql = "SELECT * FROM AD_WF_Node WHERE AD_WF_Node_ID=?";
		}
		final Object[] sqlParams = new Object[] { m_ID };

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();

			if (rs.next())	// should only be one
			{
				action = rs.getString("Action");
				if (m_isMenu)
				{
					IsSOTrx = DisplayType.toBoolean(rs.getString(I_AD_Menu.COLUMNNAME_IsSOTrx));
				}
				else
				{
					IsSOTrx = true;
				}

				adWindowId = rs.getInt("AD_Window_ID");
				adProcessId = rs.getInt("AD_Process_ID");
				adWorkbenchId = rs.getInt("AD_Workbench_ID");
				adWorkflowId = rs.getInt(m_isMenu ? I_AD_Menu.COLUMNNAME_AD_Workflow_ID : I_AD_WF_Node.COLUMNNAME_Workflow_ID);
				adTaskId = rs.getInt("AD_Task_ID");
				adFormId = rs.getInt("AD_Form_ID");
				loaded = true;
			}
		}
		catch (final SQLException e)
		{
			throw new DBException(e, sql, sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		if (!loaded)
		{
			throw new AdempiereException("@NotFound@ @" + (m_isMenu ? "AD_Menu_ID" : "AD_WF_Node_ID") + "@=" + m_ID);
		}
	}

	@Override
	protected final void done()
	{
		// Unlock
		unlock();

		//
		// Display errors if any
		try
		{
			get();
		}
		catch (final InterruptedException e)
		{
			logger.info("Interrupted", e);
		}
		catch (final ExecutionException e)
		{
			final Throwable cause = e.getCause() == null ? e : e.getCause();
			Services.get(IClientUI.class).error(Env.WINDOW_MAIN, cause);
		}
	}

	/**
	 * Start Window
	 *
	 * @param AD_Workbench_ID workbench
	 * @param AD_Window_ID window
	 */
	private void startWindow(final int AD_Workbench_ID, final int AD_Window_ID)
	{
		// metas-ts: task 05796: moved the code to WindowBL.openWindow() to allow it beeing called on other occasions too
		Services.get(IWindowBL.class).openWindow(
				m_menu.getWindowManager(),
				AD_Workbench_ID,
				AD_Window_ID);
	}	// startWindow

	/**
	 * Start Process. Start/show Process Dialog which calls ProcessCtl
	 *
	 * @param AD_Process_ID process
	 * @param IsSOTrx is SO trx
	 */
	private void startProcess(final int AD_Process_ID, final boolean IsSOTrx)
	{
		ProcessDialog.builder()
				.setAD_Process_ID(AD_Process_ID)
				.setIsSOTrx(IsSOTrx)
				.show();
	}

	private final void startWorkflow(final int AD_Workflow_ID)
	{
		if (m_menu == null)
		{
			new AdempiereException("Cannot start workflow because no menu")
					.throwOrLogWarning(false, logger);
			return;
		}
		m_menu.startWorkFlow(AD_Workflow_ID);
	}

	/**
	 * Start OS Task
	 *
	 * @param AD_Task_ID task
	 */
	private void startTask(final int AD_Task_ID)
	{
		// Get Command
		MTask task = null;
		if (AD_Task_ID > 0)
		{
			task = new MTask(Env.getCtx(), AD_Task_ID, ITrx.TRXNAME_None);
		}
		if (task.getAD_Task_ID() != AD_Task_ID)
		{
			task = null;
		}
		if (task == null)
		{
			return;
		}
		
		m_menu.getWindowManager().add(new ATask(m_name, task));
		// ATask.start(m_name, task);
	}	// startTask

	/**
	 * Start Form
	 *
	 * @param AD_Form_ID form
	 */
	private void startForm(final int AD_Form_ID)
	{
		m_menu.startForm(AD_Form_ID);
	}	// startForm

}
