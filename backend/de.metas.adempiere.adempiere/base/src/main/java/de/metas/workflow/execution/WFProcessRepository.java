/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.workflow.execution;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.error.AdIssueId;
import de.metas.i18n.ITranslatableString;
import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import de.metas.workflow.WFNodeId;
import de.metas.workflow.WFResponsibleId;
import de.metas.workflow.WFState;
import de.metas.workflow.WorkflowId;
import de.metas.workflow.service.IADWorkflowDAO;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_WF_Activity;
import org.compiere.model.I_AD_WF_Process;
import org.compiere.model.X_AD_WF_Activity;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class WFProcessRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	ImmutableMap<WFProcessId, WFProcessState> getWFProcessStateByIds(@NonNull final Collection<WFProcessId> wfProcessIds)
	{
		final ImmutableList<WFProcessState> wfProcessStates = InterfaceWrapperHelper.loadByRepoIdAwaresOutOfTrx(wfProcessIds, I_AD_WF_Process.class)
				.stream()
				.map(WFProcessRepository::toWFProcessState)
				.collect(ImmutableList.toImmutableList());

		return Maps.uniqueIndex(wfProcessStates, WFProcessState::getWfProcessId);
	}

	private static WFProcessState toWFProcessState(final I_AD_WF_Process record)
	{
		return WFProcessState.builder()
				.wfProcessId(WFProcessId.ofRepoId(record.getAD_WF_Process_ID()))
				.workflowId(WorkflowId.ofRepoId(record.getAD_Workflow_ID()))
				.priority(record.getPriority())
				.documentRef(TableRecordReference.of(record.getAD_Table_ID(), record.getRecord_ID()))
				.wfState(WFState.ofCode(record.getWFState()))
				.processed(record.isProcessed())
				.wfResponsibleId(WFResponsibleId.ofRepoId(record.getAD_WF_Responsible_ID()))
				.initialUserId(UserId.ofRepoId(record.getWF_Initial_User_ID()))
				.userId(UserId.ofRepoIdOrNullIfSystem(record.getAD_User_ID()))
				.textMsg(record.getTextMsg())
				.issueId(AdIssueId.ofRepoIdOrNull(record.getAD_Issue_ID()))
				.build();
	}

	ImmutableListMultimap<WFProcessId, WFActivityState> getWFActivityStates(@NonNull final Collection<WFProcessId> wfProcessIds)
	{
		if (wfProcessIds.isEmpty())
		{
			return ImmutableListMultimap.of();
		}

		return queryBL.createQueryBuilderOutOfTrx(I_AD_WF_Activity.class)
				.addInArrayFilter(I_AD_WF_Activity.COLUMNNAME_AD_WF_Process_ID, wfProcessIds)
				.orderBy(I_AD_WF_Activity.COLUMNNAME_AD_WF_Process_ID)
				.orderBy(I_AD_WF_Activity.COLUMNNAME_AD_WF_Activity_ID)
				.create()
				.stream()
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						activityRecord -> WFProcessId.ofRepoId(activityRecord.getAD_WF_Process_ID()),
						WFProcessRepository::toWFActivityState));
	}

	private static WFActivityState toWFActivityState(final I_AD_WF_Activity record)
	{
		return WFActivityState.builder()
				.id(WFActivityId.ofRepoId(record.getAD_WF_Activity_ID()))
				.wfNodeId(WFNodeId.ofRepoId(record.getAD_WF_Node_ID()))
				.priority(record.getPriority())
				.wfState(WFState.ofCode(record.getWFState()))
				.processed(record.isProcessed())
				.textMsg(record.getTextMsg())
				.issueId(AdIssueId.ofRepoIdOrNull(record.getAD_Issue_ID()))
				.wfResponsibleId(WFResponsibleId.ofRepoId(record.getAD_WF_Responsible_ID()))
				.userId(UserId.ofRepoIdOrNullIfSystem(record.getAD_User_ID()))
				.endWaitTime(TimeUtil.asInstant(record.getEndWaitTime()))
				.build();
	}

	public void save(@NonNull final WFProcess wfProcess)
	{
		I_AD_WF_Process record = wfProcess.getWfProcessIdOrNull() != null
				? InterfaceWrapperHelper.loadOutOfTrx(wfProcess.getWfProcessIdOrNull(), I_AD_WF_Process.class)
				: null;

		if (record == null)
		{
			record = InterfaceWrapperHelper.newInstanceOutOfTrx(I_AD_WF_Process.class);
		}

		//record.setAD_Org_ID();
		record.setAD_Workflow_ID(wfProcess.getWorkflowId().getRepoId());
		record.setPriority(wfProcess.getPriority());
		record.setWFState(wfProcess.getState().getCode());
		record.setProcessed(wfProcess.isProcessed());
		record.setAD_Table_ID(wfProcess.getDocumentRef().getAD_Table_ID());
		record.setRecord_ID(wfProcess.getDocumentRef().getRecord_ID());

		record.setAD_WF_Responsible_ID(wfProcess.getWfResponsibleId().getRepoId());
		record.setWF_Initial_User_ID(wfProcess.getInitialUserId().getRepoId());
		record.setAD_User_ID(UserId.toRepoId(wfProcess.getUserId()));

		record.setTextMsg(wfProcess.getTextMsg());
		record.setAD_Issue_ID(AdIssueId.toRepoId(wfProcess.getIssueId()));

		//
		InterfaceWrapperHelper.save(record);
		final WFProcessId wfProcessId = WFProcessId.ofRepoId(record.getAD_WF_Process_ID());
		wfProcess.setWfProcessId(wfProcessId);

		saveActivities(wfProcess.getAllActivities());
	}

	private void saveActivities(@NonNull final List<WFActivity> wfActivityList)
	{
		if (wfActivityList.isEmpty())
		{
			return;
		}

		final ImmutableSet<WFActivityId> existingIds = wfActivityList.stream()
				.map(WFActivity::getId)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());

		final HashMap<WFActivityId, I_AD_WF_Activity> existingActivityRecords = InterfaceWrapperHelper.loadByRepoIdAwaresOutOfTrx(existingIds, I_AD_WF_Activity.class)
				.stream()
				.collect(GuavaCollectors.toHashMapByKey(activityRecord -> WFActivityId.ofRepoId(activityRecord.getAD_WF_Activity_ID())));

		wfActivityList.forEach(activityRecord -> save(activityRecord, existingActivityRecords));
	}

	private void save(
			@NonNull final WFActivity wfActivity,
			final HashMap<WFActivityId, I_AD_WF_Activity> existingActivityRecords)
	{
		I_AD_WF_Activity record = wfActivity.getId() != null ? existingActivityRecords.get(wfActivity.getId()) : null;

		if (record == null && wfActivity.getId() != null)
		{
			record = InterfaceWrapperHelper.loadOutOfTrx(wfActivity.getId(), I_AD_WF_Activity.class);
		}

		if (record == null)
		{
			record = InterfaceWrapperHelper.newInstanceOutOfTrx(I_AD_WF_Activity.class);
		}

		//record.setAD_Org_ID();
		record.setAD_WF_Process_ID(wfActivity.getWfProcessId().getRepoId());
		record.setAD_Workflow_ID(wfActivity.getWorkflowId().getRepoId());
		record.setAD_WF_Node_ID(wfActivity.getNode().getId().getRepoId());
		record.setPriority(wfActivity.getPriority());
		record.setWFState(wfActivity.getState().getCode());
		record.setProcessed(wfActivity.isProcessed());
		record.setAD_Table_ID(wfActivity.getDocumentRef().getAD_Table_ID());
		record.setRecord_ID(wfActivity.getDocumentRef().getRecord_ID());
		record.setEndWaitTime(TimeUtil.asTimestamp(wfActivity.getEndWaitTime()));
		record.setTextMsg(wfActivity.getTextMsg());
		record.setAD_Issue_ID(AdIssueId.toRepoId(wfActivity.getIssueId()));

		record.setAD_WF_Responsible_ID(wfActivity.getWFResponsibleId().getRepoId());
		record.setAD_User_ID(UserId.toRepoId(wfActivity.getUserId()));

		//
		InterfaceWrapperHelper.save(record);
		final WFActivityId id = WFActivityId.ofRepoId(record.getAD_WF_Activity_ID());
		wfActivity.setId(id);
		existingActivityRecords.put(id, record);
	}

	@Builder(builderMethodName = "queryPendingActivitiesOverPriority", buildMethodName = "execute", builderClassName = "$retrievePendingActivitiesOverPriority")
	private List<WFActivityPendingInfo> retrievePendingActivitiesOverPriority(
			final int alertOverPriority,
			final int remindDays,
			final int adWorkflowProcessorId)
	{
		String sql = "SELECT a.*"
				+ " FROM AD_WF_Activity a "
				+ " WHERE a.Processed='N' AND a.WFState='OS'"    // suspended
				+ " AND a.Priority >= ?";                // ##1

		sql += " AND (a.DateLastAlert IS NULL";
		if (remindDays > 0)
		{
			sql += " OR (a.DateLastAlert+" + remindDays + ") < now()";
		}
		sql += ") ";

		sql += " AND EXISTS (SELECT 1 FROM AD_Workflow wf "
				+ " INNER JOIN AD_WF_Node wfn ON (wf.AD_Workflow_ID=wfn.AD_Workflow_ID) "
				+ "WHERE a.AD_WF_Node_ID=wfn.AD_WF_Node_ID"
				+ " AND (wf.AD_WorkflowProcessor_ID IS NULL OR wf.AD_WorkflowProcessor_ID=?))";

		final List<Object> sqlParams = Arrays.asList(alertOverPriority, adWorkflowProcessorId);
		return retrieveWFActivityPendingInfoList(sql, sqlParams);
	}

	@Builder(builderMethodName = "queryPendingActivitiesOverEndWaitTime", buildMethodName = "execute", builderClassName = "$retrievePendingActivitiesOverEndWaitTime")
	private List<WFActivityPendingInfo> retrievePendingActivitiesOverEndWaitTime(
			final int remindDays,
			final int adWorkflowProcessorId)
	{
		String sql = "SELECT * "
				+ "FROM AD_WF_Activity a "
				+ "WHERE Processed='N' AND WFState='OS'"    // suspended
				+ " AND EndWaitTime > now()"
				+ " AND (DateLastAlert IS NULL";
		if (remindDays > 0)
		{
			sql += " OR (DateLastAlert+" + remindDays + ") < now()";
		}
		sql += ") AND EXISTS (SELECT * FROM AD_Workflow wf "
				+ " INNER JOIN AD_WF_Node wfn ON (wf.AD_Workflow_ID=wfn.AD_Workflow_ID) "
				+ "WHERE a.AD_WF_Node_ID=wfn.AD_WF_Node_ID"
				+ " AND wfn.Action<>'Z'"    // not sleeping
				+ " AND (wf.AD_WorkflowProcessor_ID IS NULL OR wf.AD_WorkflowProcessor_ID=?))";

		final List<Object> sqlParams = Collections.singletonList(adWorkflowProcessorId);
		return retrieveWFActivityPendingInfoList(sql, sqlParams);
	}

	@Builder(builderMethodName = "queryPendingActivitiesInactive", buildMethodName = "execute", builderClassName = "$retrievePendingActivitiesInactive")
	private List<WFActivityPendingInfo> retrievePendingActivitiesInactive(
			final int inactivityAlertDays,
			final int remindDays,
			final int adWorkflowProcessorId)
	{
		if (inactivityAlertDays <= 0)
		{
			return ImmutableList.of();
		}

		String sql = "SELECT * "
				+ "FROM AD_WF_Activity a "
				+ "WHERE Processed='N' AND WFState='OS'"    // suspended
				+ " AND (Updated+" + inactivityAlertDays + ") < now()"
				+ " AND (DateLastAlert IS NULL";
		if (remindDays > 0)
		{
			sql += " OR (DateLastAlert+" + remindDays + ") < now()";
		}
		sql += ") AND EXISTS (SELECT * FROM AD_Workflow wf "
				+ " INNER JOIN AD_WF_Node wfn ON (wf.AD_Workflow_ID=wfn.AD_Workflow_ID) "
				+ "WHERE a.AD_WF_Node_ID=wfn.AD_WF_Node_ID"
				+ " AND (wf.AD_WorkflowProcessor_ID IS NULL OR wf.AD_WorkflowProcessor_ID=?))";

		final List<Object> sqlParams = Collections.singletonList(adWorkflowProcessorId);
		return retrieveWFActivityPendingInfoList(sql, sqlParams);
	}

	private List<WFActivityPendingInfo> retrieveWFActivityPendingInfoList(
			final String sql,
			final List<Object> sqlParams)
	{
		final Properties ctx = Env.getCtx();
		final List<I_AD_WF_Activity> activities = DB.retrieveRows(sql, sqlParams, rs -> new X_AD_WF_Activity(ctx, rs, ITrx.TRXNAME_None));
		if (activities.isEmpty())
		{
			return ImmutableList.of();
		}

		final ImmutableSet<WFProcessId> wfProcessIds = activities.stream()
				.map(activity -> WFProcessId.ofRepoId(activity.getAD_WF_Process_ID()))
				.collect(ImmutableSet.toImmutableSet());

		final ImmutableMap<WFProcessId, I_AD_WF_Process> processesById = Maps.uniqueIndex(
				InterfaceWrapperHelper.loadByRepoIdAwaresOutOfTrx(wfProcessIds, I_AD_WF_Process.class),
				process -> WFProcessId.ofRepoId(process.getAD_WF_Process_ID()));

		final ArrayList<WFActivityPendingInfo> result = new ArrayList<>(activities.size());
		for (final I_AD_WF_Activity activity : activities)
		{
			final WFProcessId wfProcessId = WFProcessId.ofRepoId(activity.getAD_WF_Process_ID());
			final I_AD_WF_Process wfProcess = processesById.get(wfProcessId);

			result.add(WFActivityPendingInfo.builder()
					.wfActivityId(WFActivityId.ofRepoId(activity.getAD_WF_Activity_ID()))
					.activityName(getActivityName(activity))
					//
					.clientId(ClientId.ofRepoId(activity.getAD_Client_ID()))
					.dateLastAlert(TimeUtil.asInstant(activity.getDateLastAlert()))
					//
					.textMsg(activity.getTextMsg())
					.processTextMsg(wfProcess.getTextMsg())
					//
					.userId(UserId.ofRepoIdOrNullIfSystem(activity.getAD_User_ID()))
					.processUserId(UserId.ofRepoIdOrNullIfSystem(wfProcess.getAD_User_ID()))
					.responsibleId(WFResponsibleId.ofRepoId(activity.getAD_WF_Responsible_ID()))
					.processResponsibleId(WFResponsibleId.ofRepoId(wfProcess.getAD_WF_Responsible_ID()))
					//
					.documentRef(TableRecordReference.ofOrNull(activity.getAD_Table_ID(), activity.getRecord_ID()))
					.processDocumentRef(TableRecordReference.ofOrNull(wfProcess.getAD_Table_ID(), wfProcess.getRecord_ID()))
					//
					.build());
		}

		return result;
	}

	public void setDateLastAlert(
			@NonNull final WFActivityId activityId,
			@NonNull final Instant dateLastAlert)
	{
		final I_AD_WF_Activity activity = InterfaceWrapperHelper.loadOutOfTrx(activityId, I_AD_WF_Activity.class);
		activity.setDateLastAlert(TimeUtil.asTimestamp(dateLastAlert));
		InterfaceWrapperHelper.save(activity);
	}

	/**
	 * @return activity summary
	 */
	@Nullable
	public String getActiveInfo(final int AD_Table_ID, final int Record_ID)
	{
		final ImmutableList<I_AD_WF_Activity> activities = getActiveActivities(AD_Table_ID, Record_ID);
		if (activities.isEmpty())
		{
			return null;
		}

		return activities.stream()
				.map(this::toStringX)
				.collect(Collectors.joining("\n"));
	}

	private String toStringX(final I_AD_WF_Activity activity)
	{
		final StringBuilder sb = new StringBuilder();
		sb.append(WFState.ofCode(activity.getWFState())).append(": ").append(getActivityName(activity).getDefaultValue());

		final UserId userId = UserId.ofRepoIdOrNullIfSystem(activity.getAD_User_ID());
		if (userId != null)
		{
			final IUserDAO userDAO = Services.get(IUserDAO.class);
			final String userFullname = userDAO.retrieveUserFullName(userId);
			sb.append(" (").append(userFullname).append(")");
		}

		return sb.toString();
	}

	private ITranslatableString getActivityName(@NonNull final I_AD_WF_Activity activity)
	{
		final IADWorkflowDAO workflowDAO = Services.get(IADWorkflowDAO.class);

		final WorkflowId workflowId = WorkflowId.ofRepoId(activity.getAD_Workflow_ID());
		final WFNodeId wfNodeId = WFNodeId.ofRepoId(activity.getAD_WF_Node_ID());
		return workflowDAO.getWFNodeName(workflowId, wfNodeId);
	}

	private ImmutableList<I_AD_WF_Activity> getActiveActivities(final int AD_Table_ID, final int Record_ID)
	{
		return queryBL.createQueryBuilderOutOfTrx(I_AD_WF_Activity.class)
				.addEqualsFilter(I_AD_WF_Activity.COLUMNNAME_AD_Table_ID, AD_Table_ID)
				.addEqualsFilter(I_AD_WF_Activity.COLUMNNAME_AD_Table_ID, Record_ID)
				.addEqualsFilter(I_AD_WF_Activity.COLUMNNAME_Processed, false)
				.orderBy(I_AD_WF_Activity.COLUMNNAME_AD_WF_Activity_ID)
				.create()
				.listImmutable(I_AD_WF_Activity.class);
	}

	public Set<WFProcessId> getActiveProcessIds(final TableRecordReference documentRef)
	{
		final ImmutableList<WFProcessId> wfProcessIds = queryBL.createQueryBuilderOutOfTrx(I_AD_WF_Process.class)
				.addEqualsFilter(I_AD_WF_Process.COLUMNNAME_AD_Table_ID, documentRef.getAD_Table_ID())
				.addEqualsFilter(I_AD_WF_Process.COLUMNNAME_Record_ID, documentRef.getRecord_ID())
				.addEqualsFilter(I_AD_WF_Process.COLUMNNAME_Processed, false)
				.create()
				.listDistinct(I_AD_WF_Process.COLUMNNAME_AD_WF_Process_ID, WFProcessId.class);
		return ImmutableSet.copyOf(wfProcessIds);
	}
}
