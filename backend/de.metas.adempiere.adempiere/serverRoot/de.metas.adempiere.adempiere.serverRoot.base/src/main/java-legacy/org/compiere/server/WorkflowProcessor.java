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

import de.metas.common.util.time.SystemTime;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.Msg;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.organization.OrgInfo;
import de.metas.security.IRoleDAO;
import de.metas.security.RoleId;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.workflow.WFResponsible;
import de.metas.workflow.WFResponsibleId;
import de.metas.workflow.WFResponsibleType;
import de.metas.workflow.execution.WFActivityPendingInfo;
import de.metas.workflow.execution.WFProcessRepository;
import de.metas.workflow.service.IADWorkflowDAO;
import org.adempiere.ad.persistence.TableModelLoader;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.MClient;
import org.compiere.model.PO;
import org.compiere.util.TimeUtil;
import org.compiere.wf.MWorkflowProcessor;
import org.compiere.wf.MWorkflowProcessorLog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Workflow Processor
 *
 * @author Jorg Janke
 * @version $Id: WorkflowProcessor.java,v 1.4 2006/07/30 00:53:33 jjanke Exp $
 */
public class WorkflowProcessor extends AdempiereServer
{
	private final WFProcessRepository wfProcessRepo = SpringContextHolder.instance.getBean(WFProcessRepository.class);

	public WorkflowProcessor(MWorkflowProcessor model)
	{
		super(model, 120);        // 2 minute dalay
		m_model = model;
		m_client = MClient.get(model.getCtx(), model.getAD_Client_ID());
	}    // WorkflowProcessor

	/**
	 * The Concrete Model
	 */
	private MWorkflowProcessor m_model = null;
	/**
	 * Last Summary
	 */
	private StringBuffer m_summary = new StringBuffer();
	/**
	 * Client onfo
	 */
	private MClient m_client = null;

	/**
	 * Work
	 */
	@Override
	protected void doWork()
	{
		m_summary = new StringBuffer();
		//
		// wakeup();
		// dynamicPriority();
		sendAlerts();
		//
		int no = m_model.deleteLog();
		m_summary.append("Logs deleted=").append(no);
		//
		MWorkflowProcessorLog pLog = new MWorkflowProcessorLog(m_model, m_summary.toString());
		pLog.setReference("#" + getRunCount() + " - " + TimeUtil.formatElapsed(getStartWork()));
		pLog.save();
	}    // doWork

	// /**
	//  * Continue Workflow After Sleep
	//  */
	// private void wakeup()
	// {
	// 	String sql = "SELECT * "
	// 			+ "FROM AD_WF_Activity a "
	// 			+ "WHERE Processed='N' AND WFState='OS'"    // suspended
	// 			+ " AND EndWaitTime > now()"
	// 			+ " AND AD_Client_ID=?"
	// 			+ " AND EXISTS (SELECT * FROM AD_Workflow wf "
	// 			+ " INNER JOIN AD_WF_Node wfn ON (wf.AD_Workflow_ID=wfn.AD_Workflow_ID) "
	// 			+ "WHERE a.AD_WF_Node_ID=wfn.AD_WF_Node_ID"
	// 			+ " AND wfn.Action='Z'"        // sleeping
	// 			+ " AND (wf.AD_WorkflowProcessor_ID IS NULL OR wf.AD_WorkflowProcessor_ID=?))";
	// 	PreparedStatement pstmt = null;
	// 	int count = 0;
	// 	int countEMails = 0;
	// 	try
	// 	{
	// 		pstmt = DB.prepareStatement(sql, null);
	// 		pstmt.setInt(1, m_model.getAD_Client_ID());
	// 		pstmt.setInt(2, m_model.getAD_WorkflowProcessor_ID());
	// 		ResultSet rs = pstmt.executeQuery();
	// 		while (rs.next())
	// 		{
	// 			MWFActivity activity = new MWFActivity(getCtx(), rs);
	// 			activity.changeWFStateTo(WFState.Completed);
	// 			// saves and calls MWFProcess.checkActivities();
	// 			count++;
	// 		}
	// 		rs.close();
	// 	}
	// 	catch (final Exception e)
	// 	{
	// 		log.error("wakeup", e);
	// 	}
	// 	finally
	// 	{
	// 		DB.close(pstmt);
	// 	}
	// 	m_summary.append("Wakeup #").append(count).append(" - ");
	// }    // wakeup

	// /**
	//  * Set/Increase Priority dynamically
	//  */
	// private void dynamicPriority()
	// {
	// 	// suspened activities with dynamic priority node
	// 	String sql = "SELECT * "
	// 			+ "FROM AD_WF_Activity a "
	// 			+ "WHERE Processed='N' AND WFState='OS'"    // suspended
	// 			+ " AND EXISTS (SELECT * FROM AD_Workflow wf"
	// 			+ " INNER JOIN AD_WF_Node wfn ON (wf.AD_Workflow_ID=wfn.AD_Workflow_ID) "
	// 			+ "WHERE a.AD_WF_Node_ID=wfn.AD_WF_Node_ID AND wf.AD_WorkflowProcessor_ID=?"
	// 			+ " AND wfn.DynPriorityUnit IS NOT NULL AND wfn.DynPriorityChange IS NOT NULL)";
	// 	PreparedStatement pstmt = null;
	// 	ResultSet rs = null;
	// 	int count = 0;
	// 	int countEMails = 0;
	// 	try
	// 	{
	// 		pstmt = DB.prepareStatement(sql, null);
	// 		pstmt.setInt(1, m_model.getAD_WorkflowProcessor_ID());
	// 		rs = pstmt.executeQuery();
	// 		while (rs.next())
	// 		{
	// 			MWFActivity activity = new MWFActivity(getCtx(), rs);
	// 			if (activity.getDynPriorityStart() == 0)
	// 			{
	// 				activity.setDynPriorityStart(activity.getPriority());
	// 			}
	// 			long ms = System.currentTimeMillis() - activity.getCreated().getTime();
	// 			WFNode node = activity.getNode();
	// 			int prioDiff = calculateDynamicPriority(node, (int)(ms / 1000));
	// 			activity.setPriority(activity.getDynPriorityStart() + prioDiff);
	// 			activity.save();
	// 			count++;
	// 		}
	// 		rs.close();
	// 	}
	// 	catch (Exception e)
	// 	{
	// 		throw new DBException(e, sql);
	// 	}
	// 	finally
	// 	{
	// 		DB.close(rs, pstmt);
	// 	}
	//
	// 	m_summary.append("DynPriority #").append(count).append(" - ");
	// }    // setPriority
	//
	// /**
	//  * Calculate Dynamic Priority
	//  *
	//  * @param seconds second after created
	//  * @return dyn prio
	//  */
	// private static int calculateDynamicPriority(final WFNode wfNode, final int seconds)
	// {
	// 	if (seconds == 0
	// 			|| wfNode.getDynPriorityUnitDuration().isZero()
	// 			|| wfNode.getDynPriorityChange().signum() == 0)
	// 	{
	// 		return 0;
	// 	}
	//
	// 	final BigDecimal change = new BigDecimal(seconds)
	// 			.divide(BigDecimal.valueOf(wfNode.getDynPriorityUnitDuration().getSeconds()), BigDecimal.ROUND_DOWN)
	// 			.multiply(wfNode.getDynPriorityChange());
	// 	return change.intValue();
	// }    //	calculateDynamicPriority

	/**
	 * Send Alerts
	 */
	private void sendAlerts()
	{
		alertOverPriority();
		alertOverEndWaitTime();
		alertInactivity();
	}    // sendAlerts

	private void alertOverPriority()
	{
		if (m_model.getAlertOverPriority() > 0)
		{
			final List<WFActivityPendingInfo> pendingActivities = wfProcessRepo.queryPendingActivitiesOverPriority()
					.alertOverPriority(m_model.getAlertOverPriority())
					.remindDays(m_model.getRemindDays())
					.adWorkflowProcessorId(m_model.getAD_WorkflowProcessor_ID())
					.execute();

			int count = 0;
			int countEMails = 0;
			for (final WFActivityPendingInfo activity : pendingActivities)
			{
				boolean escalate = activity.getDateLastAlert() != null;
				countEMails += sendEmail(activity, "ActivityOverPriority",
						escalate, true);
				wfProcessRepo.setDateLastAlert(activity.getWfActivityId(), SystemTime.asInstant());
				count++;
			}

			m_summary.append("OverPriority #").append(count);
			if (countEMails > 0)
			{
				m_summary.append(" (").append(countEMails).append(" EMail)");
			}
			m_summary.append(" - ");
		}    // Alert over Priority
	}

	private void alertOverEndWaitTime()
	{
		final List<WFActivityPendingInfo> pendingActivities = wfProcessRepo.queryPendingActivitiesOverEndWaitTime()
				.remindDays(m_model.getRemindDays())
				.adWorkflowProcessorId(m_model.getAD_WorkflowProcessor_ID())
				.execute();

		int count = 0;
		int countEMails = 0;
		for (final WFActivityPendingInfo activity : pendingActivities)
		{
			boolean escalate = activity.getDateLastAlert() != null;
			countEMails += sendEmail(activity, "ActivityEndWaitTime",
					escalate, false);
			wfProcessRepo.setDateLastAlert(activity.getWfActivityId(), SystemTime.asInstant());
			count++;
		}

		m_summary.append("EndWaitTime #").append(count);
		if (countEMails > 0)
		{
			m_summary.append(" (").append(countEMails).append(" EMail)");
		}
		m_summary.append(" - ");
	}

	private void alertInactivity()
	{
		if (m_model.getInactivityAlertDays() > 0)
		{
			final List<WFActivityPendingInfo> pendingActivities = wfProcessRepo.queryPendingActivitiesInactive()
					.inactivityAlertDays(m_model.getInactivityAlertDays())
					.remindDays(m_model.getRemindDays())
					.adWorkflowProcessorId(m_model.getAD_WorkflowProcessor_ID())
					.execute();

			int count = 0;
			int countEMails = 0;
			for (final WFActivityPendingInfo activity : pendingActivities)
			{
				boolean escalate = activity.getDateLastAlert() != null;
				countEMails += sendEmail(activity, "ActivityInactivity",
						escalate, false);
				wfProcessRepo.setDateLastAlert(activity.getWfActivityId(), SystemTime.asInstant());
				count++;
			}
			m_summary.append("Inactivity #").append(count);
			if (countEMails > 0)
			{
				m_summary.append(" (").append(countEMails).append(" EMail)");
			}
			m_summary.append(" - ");
		}    // Inactivity
	}

	/**
	 * Send Alert EMail
	 *
	 * @param activity     activity
	 * @param AD_Message   message
	 * @param toProcess    true if to process owner
	 * @param toSupervisor true if to Supervisor
	 * @return number of mails sent
	 */
	private int sendEmail(
			final WFActivityPendingInfo activity,
			final String AD_Message,
			final boolean toProcess,
			final boolean toSupervisor)
	{
		if (m_client == null || m_client.getAD_Client_ID() != activity.getClientId().getRepoId())
		{
			m_client = MClient.get(getCtx(), activity.getClientId().getRepoId());
		}

		// MWFProcess process = new MWFProcess(getCtx(), activity.getAD_WF_Process_ID());

		final ITranslatableString subjectVar = activity.getActivityName();
		String message = activity.getTextMsg();
		if (message == null || message.length() == 0)
		{
			message = activity.getProcessTextMsg();
		}

		File pdf = null;
		{
			final TableRecordReference documentRef = activity.getDocumentRef();
			final PO po = documentRef != null
					? TableModelLoader.instance.getPO(documentRef)
					: null;

			final IDocument document = po != null ? Services.get(IDocumentBL.class).getDocumentOrNull(po) : null;
			if (document != null)
			{
				message = document.getDocumentInfo() + "\n" + message;
				pdf = document.createPDF();
			}
		}

		// Inactivity Alert: Workflow Activity {}
		String subject = Msg.getMsg(m_client.getAD_Language(), AD_Message, new Object[] { subjectVar });

		// Prevent duplicates
		ArrayList<UserId> list = new ArrayList<>();
		int counter = 0;

		// To Activity Owner
		final UserId activityUserId = activity.getUserId();
		if (activityUserId != null)
		{
			if (m_client.sendEMail(activityUserId, subject, message, pdf))
			{
				counter++;
			}
			list.add(activityUserId);
		}

		// To Process Owner
		final UserId processUserId = activity.getProcessUserId();
		if (toProcess && processUserId != null && !list.contains(processUserId))
		{
			if (m_client.sendEMail(processUserId, subject, message, pdf))
			{
				counter++;
			}
			list.add(processUserId);
		}

		// To Activity Responsible
		final IADWorkflowDAO workflowDAO = Services.get(IADWorkflowDAO.class);
		final WFResponsibleId activityResponsibleId = activity.getResponsibleId();
		WFResponsible responsible = workflowDAO.getWFResponsibleById(activityResponsibleId);
		counter += sendAlertToResponsible(responsible, list, activity, subject, message, pdf);

		// To Process Responsible
		final WFResponsibleId processResponsibleId = activity.getProcessResponsibleId();
		if (toProcess
				&& !WFResponsibleId.equals(processResponsibleId, activityResponsibleId))
		{
			responsible = workflowDAO.getWFResponsibleById(processResponsibleId);
			counter += sendAlertToResponsible(responsible, list, activity, subject, message, pdf);
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
	 * @return number of mail sent
	 */
	private int sendAlertToResponsible(
			WFResponsible responsible,
			final ArrayList<UserId> alreadyNotifiedUserIds,
			WFActivityPendingInfo activity,
			String subject,
			String message,
			File pdf)
	{
		int counter = 0;
		if (responsible.isInvoker())
		{
			// nothing
		}
		// Human
		else if (responsible.isHuman()
				&& !alreadyNotifiedUserIds.contains(responsible.getUserId()))
		{
			if (m_client.sendEMail(responsible.getUserId(), subject, message, pdf))
			{
				counter++;
			}
			alreadyNotifiedUserIds.add(responsible.getUserId());
		}
		// Org of the Document
		else if (responsible.getType() == WFResponsibleType.Organization)
		{
			final TableRecordReference documentRef = activity.getProcessDocumentRef();
			PO document = documentRef != null
					? TableModelLoader.instance.getPO(documentRef)
					: null;
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
		else if (responsible.isRole())
		{
			final RoleId roleId = responsible.getRoleId();
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
	}

	@Override
	public String getServerInfo()
	{
		return "#" + getRunCount() + " - Last=" + m_summary.toString();
	}
}
