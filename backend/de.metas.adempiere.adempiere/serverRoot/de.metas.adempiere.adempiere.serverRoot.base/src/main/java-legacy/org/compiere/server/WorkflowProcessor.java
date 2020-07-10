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
package org.compiere.server;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Set;

import org.compiere.model.MClient;
import org.compiere.model.PO;
import org.compiere.process.StateEngine;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;
import org.compiere.wf.MWFActivity;
import org.compiere.wf.MWFNode;
import org.compiere.wf.MWFProcess;
import org.compiere.wf.MWFResponsible;
import org.compiere.wf.MWorkflowProcessor;
import org.compiere.wf.MWorkflowProcessorLog;

import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.i18n.Msg;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.organization.OrgInfo;
import de.metas.security.IRoleDAO;
import de.metas.security.RoleId;
import de.metas.user.UserId;
import de.metas.util.Services;

/**
 * Workflow Processor
 *
 * @author Jorg Janke
 * @version $Id: WorkflowProcessor.java,v 1.4 2006/07/30 00:53:33 jjanke Exp $
 */
public class WorkflowProcessor extends AdempiereServer
{
	/**
	 * WorkflowProcessor
	 * 
	 * @param model model
	 */
	public WorkflowProcessor(MWorkflowProcessor model)
	{
		super(model, 120);		// 2 minute dalay
		m_model = model;
		m_client = MClient.get(model.getCtx(), model.getAD_Client_ID());
	}	// WorkflowProcessor

	/** The Concrete Model */
	private MWorkflowProcessor m_model = null;
	/** Last Summary */
	private StringBuffer m_summary = new StringBuffer();
	/** Client onfo */
	private MClient m_client = null;

	/**
	 * Work
	 */
	@Override
	protected void doWork()
	{
		m_summary = new StringBuffer();
		//
		wakeup();
		dynamicPriority();
		sendAlerts();
		//
		int no = m_model.deleteLog();
		m_summary.append("Logs deleted=").append(no);
		//
		MWorkflowProcessorLog pLog = new MWorkflowProcessorLog(m_model, m_summary.toString());
		pLog.setReference("#" + getRunCount() + " - " + TimeUtil.formatElapsed(getStartWork()));
		pLog.save();
	}	// doWork

	/**
	 * Continue Workflow After Sleep
	 */
	private void wakeup()
	{
		String sql = "SELECT * "
				+ "FROM AD_WF_Activity a "
				+ "WHERE Processed='N' AND WFState='OS'"	// suspended
				+ " AND EndWaitTime > now()"
				+ " AND AD_Client_ID=?"
				+ " AND EXISTS (SELECT * FROM AD_Workflow wf "
				+ " INNER JOIN AD_WF_Node wfn ON (wf.AD_Workflow_ID=wfn.AD_Workflow_ID) "
				+ "WHERE a.AD_WF_Node_ID=wfn.AD_WF_Node_ID"
				+ " AND wfn.Action='Z'"		// sleeping
				+ " AND (wf.AD_WorkflowProcessor_ID IS NULL OR wf.AD_WorkflowProcessor_ID=?))";
		PreparedStatement pstmt = null;
		int count = 0;
		int countEMails = 0;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, m_model.getAD_Client_ID());
			pstmt.setInt(2, m_model.getAD_WorkflowProcessor_ID());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				MWFActivity activity = new MWFActivity(getCtx(), rs, null);
				activity.setWFState(StateEngine.STATE_Completed);
				// saves and calls MWFProcess.checkActivities();
				count++;
			}
			rs.close();
		}
		catch (Exception e)
		{
			log.error("wakeup", e);
		}
		finally
		{
			DB.close(pstmt);
		}
		m_summary.append("Wakeup #").append(count).append(" - ");
	}	// wakeup

	/**
	 * Set/Increase Priority dynamically
	 */
	private void dynamicPriority()
	{
		// suspened activities with dynamic priority node
		String sql = "SELECT * "
				+ "FROM AD_WF_Activity a "
				+ "WHERE Processed='N' AND WFState='OS'"	// suspended
				+ " AND EXISTS (SELECT * FROM AD_Workflow wf"
				+ " INNER JOIN AD_WF_Node wfn ON (wf.AD_Workflow_ID=wfn.AD_Workflow_ID) "
				+ "WHERE a.AD_WF_Node_ID=wfn.AD_WF_Node_ID AND wf.AD_WorkflowProcessor_ID=?"
				+ " AND wfn.DynPriorityUnit IS NOT NULL AND wfn.DynPriorityChange IS NOT NULL)";
		PreparedStatement pstmt = null;
		int count = 0;
		int countEMails = 0;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, m_model.getAD_WorkflowProcessor_ID());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				MWFActivity activity = new MWFActivity(getCtx(), rs, null);
				if (activity.getDynPriorityStart() == 0)
				{
					activity.setDynPriorityStart(activity.getPriority());
				}
				long ms = System.currentTimeMillis() - activity.getCreated().getTime();
				MWFNode node = activity.getNode();
				int prioDiff = node.calculateDynamicPriority((int)(ms / 1000));
				activity.setPriority(activity.getDynPriorityStart() + prioDiff);
				activity.save();
				count++;
			}
			rs.close();
		}
		catch (Exception e)
		{
			log.error(sql, e);
		}
		finally
		{
			DB.close(pstmt);
		}

		m_summary.append("DynPriority #").append(count).append(" - ");
	}	// setPriority

	/**
	 * Send Alerts
	 */
	private void sendAlerts()
	{
		// Alert over Priority
		if (m_model.getAlertOverPriority() > 0)
		{
			String sql = "SELECT * "
					+ "FROM AD_WF_Activity a "
					+ "WHERE Processed='N' AND WFState='OS'"	// suspended
					+ " AND Priority >= ?"				// ##1
					+ " AND (DateLastAlert IS NULL";
			if (m_model.getRemindDays() > 0)
			{
				sql += " OR (DateLastAlert+" + m_model.getRemindDays()
						+ ") < now()";
			}
			sql += ") AND EXISTS (SELECT * FROM AD_Workflow wf "
					+ " INNER JOIN AD_WF_Node wfn ON (wf.AD_Workflow_ID=wfn.AD_Workflow_ID) "
					+ "WHERE a.AD_WF_Node_ID=wfn.AD_WF_Node_ID"
					+ " AND (wf.AD_WorkflowProcessor_ID IS NULL OR wf.AD_WorkflowProcessor_ID=?))";
			int count = 0;
			int countEMails = 0;
			PreparedStatement pstmt = null;
			try
			{
				pstmt = DB.prepareStatement(sql, null);
				pstmt.setInt(1, m_model.getAlertOverPriority());
				pstmt.setInt(2, m_model.getAD_WorkflowProcessor_ID());
				ResultSet rs = pstmt.executeQuery();
				while (rs.next())
				{
					MWFActivity activity = new MWFActivity(getCtx(), rs, null);
					boolean escalate = activity.getDateLastAlert() != null;
					countEMails += sendEmail(activity, "ActivityOverPriority",
							escalate, true);
					activity.setDateLastAlert(new Timestamp(System.currentTimeMillis()));
					activity.save();
					count++;
				}
				rs.close();
				pstmt.close();
			}
			catch (SQLException e)
			{
				log.error("(Priority) - " + sql, e);
			}
			finally
			{
				DB.close(pstmt);
			}
			m_summary.append("OverPriority #").append(count);
			if (countEMails > 0)
			{
				m_summary.append(" (").append(countEMails).append(" EMail)");
			}
			m_summary.append(" - ");
		}	// Alert over Priority

		/**
		 * Over End Wait
		 */
		String sql = "SELECT * "
				+ "FROM AD_WF_Activity a "
				+ "WHERE Processed='N' AND WFState='OS'"	// suspended
				+ " AND EndWaitTime > now()"
				+ " AND (DateLastAlert IS NULL";
		if (m_model.getRemindDays() > 0)
		{
			sql += " OR (DateLastAlert+" + m_model.getRemindDays()
					+ ") < now()";
		}
		sql += ") AND EXISTS (SELECT * FROM AD_Workflow wf "
				+ " INNER JOIN AD_WF_Node wfn ON (wf.AD_Workflow_ID=wfn.AD_Workflow_ID) "
				+ "WHERE a.AD_WF_Node_ID=wfn.AD_WF_Node_ID"
				+ " AND wfn.Action<>'Z'"	// not sleeping
				+ " AND (wf.AD_WorkflowProcessor_ID IS NULL OR wf.AD_WorkflowProcessor_ID=?))";
		PreparedStatement pstmt = null;
		int count = 0;
		int countEMails = 0;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, m_model.getAD_WorkflowProcessor_ID());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				MWFActivity activity = new MWFActivity(getCtx(), rs, null);
				boolean escalate = activity.getDateLastAlert() != null;
				countEMails += sendEmail(activity, "ActivityEndWaitTime",
						escalate, false);
				activity.setDateLastAlert(new Timestamp(System.currentTimeMillis()));
				activity.save();
				count++;
			}
			rs.close();
		}
		catch (Exception e)
		{
			log.error("(EndWaitTime) - " + sql, e);
		}
		finally
		{
			DB.close(pstmt);
		}

		m_summary.append("EndWaitTime #").append(count);
		if (countEMails > 0)
		{
			m_summary.append(" (").append(countEMails).append(" EMail)");
		}
		m_summary.append(" - ");

		/**
		 * Send inactivity alerts
		 */
		if (m_model.getInactivityAlertDays() > 0)
		{
			sql = "SELECT * "
					+ "FROM AD_WF_Activity a "
					+ "WHERE Processed='N' AND WFState='OS'"	// suspended
					+ " AND (Updated+" + m_model.getInactivityAlertDays() + ") < now()"
					+ " AND (DateLastAlert IS NULL";
			if (m_model.getRemindDays() > 0)
			{
				sql += " OR (DateLastAlert+" + m_model.getRemindDays()
						+ ") < now()";
			}
			sql += ") AND EXISTS (SELECT * FROM AD_Workflow wf "
					+ " INNER JOIN AD_WF_Node wfn ON (wf.AD_Workflow_ID=wfn.AD_Workflow_ID) "
					+ "WHERE a.AD_WF_Node_ID=wfn.AD_WF_Node_ID"
					+ " AND (wf.AD_WorkflowProcessor_ID IS NULL OR wf.AD_WorkflowProcessor_ID=?))";
			count = 0;
			countEMails = 0;
			try
			{
				pstmt = DB.prepareStatement(sql, null);
				pstmt.setInt(1, m_model.getAD_WorkflowProcessor_ID());
				ResultSet rs = pstmt.executeQuery();
				while (rs.next())
				{
					MWFActivity activity = new MWFActivity(getCtx(), rs, null);
					boolean escalate = activity.getDateLastAlert() != null;
					countEMails += sendEmail(activity, "ActivityInactivity",
							escalate, false);
					activity.setDateLastAlert(new Timestamp(System.currentTimeMillis()));
					activity.save();
					count++;
				}
				rs.close();
				pstmt.close();
			}
			catch (SQLException e)
			{
				log.error("(Inactivity): " + sql, e);
			}
			finally
			{
				DB.close(pstmt);
			}
			m_summary.append("Inactivity #").append(count);
			if (countEMails > 0)
			{
				m_summary.append(" (").append(countEMails).append(" EMail)");
			}
			m_summary.append(" - ");
		}	// Inactivity
	}	// sendAlerts

	/**
	 * Send Alert EMail
	 * 
	 * @param activity activity
	 * @param AD_Message message
	 * @param toProcess true if to process owner
	 * @param toSupervisor true if to Supervisor
	 * @return number of mails sent
	 */
	private int sendEmail(MWFActivity activity, String AD_Message,
			boolean toProcess, boolean toSupervisor)
	{
		if (m_client == null || m_client.getAD_Client_ID() != activity.getAD_Client_ID())
		{
			m_client = MClient.get(getCtx(), activity.getAD_Client_ID());
		}

		MWFProcess process = new MWFProcess(getCtx(), activity.getAD_WF_Process_ID(), null);

		String subjectVar = activity.getNode().getName();
		String message = activity.getTextMsg();
		if (message == null || message.length() == 0)
		{
			message = process.getTextMsg();
		}
		File pdf = null;
		final PO po = activity.getPO();
		final IDocument document = po != null ? Services.get(IDocumentBL.class).getDocumentOrNull(po) : null;
		if (document != null)
		{
			message = document.getDocumentInfo() + "\n" + message;
			pdf = document.createPDF();
		}

		// Inactivity Alert: Workflow Activity {}
		String subject = Msg.getMsg(m_client.getAD_Language(), AD_Message, new Object[] { subjectVar });

		// Prevent duplicates
		ArrayList<UserId> list = new ArrayList<>();
		int counter = 0;

		// To Activity Owner
		final UserId activityUserId = UserId.ofRepoIdOrNull(activity.getAD_User_ID());
		if (activityUserId != null)
		{
			if (m_client.sendEMail(activityUserId, subject, message, pdf))
			{
				counter++;
			}
			list.add(activityUserId);
		}

		// To Process Owner
		final UserId processUserId = UserId.ofRepoIdOrNull(process.getAD_User_ID());
		if (toProcess && processUserId != null && !list.contains(processUserId))
		{
			if (m_client.sendEMail(processUserId, subject, message, pdf))
			{
				counter++;
			}
			list.add(processUserId);
		}

		// To Activity Responsible
		MWFResponsible responsible = MWFResponsible.get(getCtx(), activity.getAD_WF_Responsible_ID());
		counter += sendAlertToResponsible(responsible, list, process,
				subject, message, pdf);

		// To Process Responsible
		if (toProcess
				&& process.getAD_WF_Responsible_ID() != activity.getAD_WF_Responsible_ID())
		{
			responsible = MWFResponsible.get(getCtx(), process.getAD_WF_Responsible_ID());
			counter += sendAlertToResponsible(responsible, list, process,
					subject, message, pdf);
		}

		// Processor SuperVisor
		final UserId supervisorId = m_model.getSupervisor_ID() > 0 ? UserId.ofRepoId(m_model.getSupervisor_ID()) : null;
		if (toSupervisor && supervisorId != null && !list.contains(supervisorId))
		{
			if (m_client.sendEMail(supervisorId, subject, message, pdf))
			{
				counter++;
			}
			list.add(supervisorId);
		}

		return counter;
	}   // sendAlert

	/**
	 * Send Alert To Responsible
	 * 
	 * @param responsible responsible
	 * @param alreadyNotifiedUserIds list of already sent users
	 * @param process process
	 * @param subject subject
	 * @param message message
	 * @param pdf optional pdf
	 * @return number of mail sent
	 */
	private int sendAlertToResponsible(
			MWFResponsible responsible,
			final ArrayList<UserId> alreadyNotifiedUserIds,
			MWFProcess process,
			String subject,
			String message,
			File pdf)
	{
		final UserId responsibleUserId = responsible.getAD_User_ID() > 0 ? UserId.ofRepoId(responsible.getAD_User_ID()) : null;
		final RoleId responsibleRoleId = responsible.getAD_Role_ID() > 0 ? RoleId.ofRepoId(responsible.getAD_Role_ID()) : null;

		int counter = 0;
		if (responsible.isInvoker())
		{
			// nothing
		}
		// Human
		else if (MWFResponsible.RESPONSIBLETYPE_Human.equals(responsible.getResponsibleType())
				&& responsibleUserId != null
				&& !alreadyNotifiedUserIds.contains(responsibleUserId))
		{
			if (m_client.sendEMail(responsibleUserId, subject, message, pdf))
			{
				counter++;
			}
			alreadyNotifiedUserIds.add(responsibleUserId);
		}
		// Org of the Document
		else if (MWFResponsible.RESPONSIBLETYPE_Organization.equals(responsible.getResponsibleType()))
		{
			PO document = process.getPO();
			if (document != null)
			{
				final OrgId orgId = OrgId.ofRepoId(document.getAD_Org_ID());
				final OrgInfo org = Services.get(IOrgDAO.class).getOrgInfoById(orgId);
				final UserId supervisorId = org.getSupervisorId();
				if (supervisorId != null && !alreadyNotifiedUserIds.contains(supervisorId))
				{
					if (m_client.sendEMail(supervisorId, subject, message, pdf))
					{
						counter++;
					}
					alreadyNotifiedUserIds.add(supervisorId);
				}
			}
		}
		// Role
		else if (MWFResponsible.RESPONSIBLETYPE_Role.equals(responsible.getResponsibleType())
				&& responsibleRoleId != null)
		{
			final RoleId roleId = RoleId.ofRepoId(responsible.getAD_Role_ID());
			final Set<UserId> allRoleUserIds = Services.get(IRoleDAO.class).retrieveUserIdsForRoleId(roleId);
			for (final UserId adUserId : allRoleUserIds)
			{
				if (!alreadyNotifiedUserIds.contains(adUserId))
				{
					if (m_client.sendEMail(adUserId, subject, message, pdf))
					{
						counter++;
					}
					alreadyNotifiedUserIds.add(adUserId);
				}
			}
		}
		return counter;
	}	// sendAlertToResponsible

	/**
	 * Get Server Info
	 * 
	 * @return info
	 */
	@Override
	public String getServerInfo()
	{
		return "#" + getRunCount() + " - Last=" + m_summary.toString();
	}	// getServerInfo

}	// WorkflowProcessor
