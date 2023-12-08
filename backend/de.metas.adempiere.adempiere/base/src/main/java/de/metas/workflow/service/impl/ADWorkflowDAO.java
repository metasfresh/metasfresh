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

package de.metas.workflow.service.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import de.metas.ad_reference.ReferenceId;
import de.metas.cache.CCache;
import de.metas.email.EMailAddress;
import de.metas.email.templates.MailTemplateId;
import de.metas.i18n.IModelTranslationMap;
import de.metas.i18n.ITranslatableString;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.process.AdProcessId;
import de.metas.security.RoleId;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import de.metas.workflow.WFDurationUnit;
import de.metas.workflow.WFNode;
import de.metas.workflow.WFNodeAction;
import de.metas.workflow.WFNodeEmailRecipient;
import de.metas.workflow.WFNodeId;
import de.metas.workflow.WFNodeJoinType;
import de.metas.workflow.WFNodeParameter;
import de.metas.workflow.WFNodeSplitType;
import de.metas.workflow.WFNodeTransition;
import de.metas.workflow.WFNodeTransitionCondition;
import de.metas.workflow.WFNodeTransitionId;
import de.metas.workflow.WFResponsible;
import de.metas.workflow.WFResponsibleId;
import de.metas.workflow.WFResponsibleType;
import de.metas.workflow.Workflow;
import de.metas.workflow.WorkflowId;
import de.metas.workflow.service.IADWorkflowDAO;
import de.metas.workflow.service.WFNodeCreateRequest;
import de.metas.workflow.service.WFNodeLayoutChangeRequest;
import lombok.NonNull;
import org.adempiere.ad.column.AdColumnId;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_AD_Process_Para;
import org.compiere.model.I_AD_WF_NextCondition;
import org.compiere.model.I_AD_WF_Node;
import org.compiere.model.I_AD_WF_NodeNext;
import org.compiere.model.I_AD_WF_Node_Para;
import org.compiere.model.I_AD_WF_Responsible;
import org.compiere.model.I_AD_Workflow;
import org.compiere.model.MColumn;
import org.compiere.model.X_AD_WF_NextCondition;
import org.compiere.model.X_AD_WF_Node;
import org.compiere.model.X_AD_Workflow;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@SuppressWarnings("unused")
public class ADWorkflowDAO implements IADWorkflowDAO
{
	private static final Logger log = LogManager.getLogger(ADWorkflowDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<WorkflowId, Workflow> workflowsById = CCache.<WorkflowId, Workflow>builder()
			.tableName(I_AD_Workflow.Table_Name)
			.additionalTableNameToResetFor(I_AD_WF_Node.Table_Name)
			.additionalTableNameToResetFor(I_AD_WF_NodeNext.Table_Name)
			.additionalTableNameToResetFor(I_AD_WF_NextCondition.Table_Name)
			.additionalTableNameToResetFor(I_AD_WF_Node_Para.Table_Name)
			.build();

	private record DocValueWorkflowKey(@NonNull ClientId clientId, @NonNull AdTableId adTableId) {}

	private static final CCache<Integer, ImmutableSetMultimap<DocValueWorkflowKey, WorkflowId>> docValueWorkflowsCache = CCache.<Integer, ImmutableSetMultimap<DocValueWorkflowKey, WorkflowId>>builder()
			.tableName(I_AD_Workflow.Table_Name)
			.build();

	private static final CCache<WFResponsibleId, WFResponsible> wfResponsiblesById = CCache.<WFResponsibleId, WFResponsible>builder()
			.tableName(I_AD_WF_Responsible.Table_Name)
			.build();

	@Override
	public Workflow getById(@NonNull final WorkflowId workflowId)
	{
		return workflowsById.getOrLoad(workflowId, this::retrieveWorkflowById);
	}

	private Workflow retrieveWorkflowById(final WorkflowId workflowId)
	{
		final I_AD_Workflow workflowRecord = InterfaceWrapperHelper.load(workflowId, I_AD_Workflow.class);
		return toWorkflow(workflowRecord);
	}

	private Collection<Workflow> getByIds(@NonNull final Set<WorkflowId> workflowIds)
	{
		return workflowsById.getAllOrLoad(workflowIds, this::retrieveWorkflowByIds);
	}

	private Map<WorkflowId, Workflow> retrieveWorkflowByIds(final Set<WorkflowId> workflowIds)
	{
		if (workflowIds.isEmpty())
		{
			return ImmutableMap.of();
		}

		final List<I_AD_Workflow> workflowRecords = InterfaceWrapperHelper.loadByRepoIdAwaresOutOfTrx(workflowIds, I_AD_Workflow.class);
		return workflowRecords.stream()
				.map(this::toWorkflow)
				.collect(ImmutableMap.toImmutableMap(
						Workflow::getId,
						workflow -> workflow));
	}

	@Override
	public Collection<Workflow> getDocValueWorkflows(
			@NonNull final ClientId clientId,
			@NonNull final AdTableId adTableId)
	{
		final ImmutableSet<WorkflowId> workflowIds = getDocValueWorkflowIds()
				.get(new DocValueWorkflowKey(clientId, adTableId));
		if (workflowIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return getByIds(workflowIds);
	}

	private ImmutableSetMultimap<DocValueWorkflowKey, WorkflowId> getDocValueWorkflowIds()
	{
		return docValueWorkflowsCache.getOrLoad(0, this::retrieveDocValueWorkflowIds);
	}

	private ImmutableSetMultimap<DocValueWorkflowKey, WorkflowId> retrieveDocValueWorkflowIds()
	{
		return queryBL
				.createQueryBuilderOutOfTrx(I_AD_Workflow.class)
				.addEqualsFilter(I_AD_Workflow.COLUMNNAME_WorkflowType, X_AD_Workflow.WORKFLOWTYPE_DocumentValue)
				.addEqualsFilter(I_AD_Workflow.COLUMNNAME_IsValid, true)
				.orderBy(I_AD_Workflow.COLUMNNAME_AD_Client_ID)
				.orderBy(I_AD_Workflow.COLUMNNAME_AD_Table_ID)
				.create()
				.stream()
				.collect(ImmutableSetMultimap.toImmutableSetMultimap(
						ADWorkflowDAO::extractDocValueWorkflowKey,
						workflow -> WorkflowId.ofRepoId(workflow.getAD_Workflow_ID())));
	}

	private static DocValueWorkflowKey extractDocValueWorkflowKey(final I_AD_Workflow workflow)
	{
		return new DocValueWorkflowKey(
				ClientId.ofRepoId(workflow.getAD_Client_ID()),
				AdTableId.ofRepoId(workflow.getAD_Table_ID()));
	}

	private Workflow toWorkflow(final I_AD_Workflow workflowRecord)
	{
		final WorkflowId workflowId = WorkflowId.ofRepoId(workflowRecord.getAD_Workflow_ID());
		final Duration durationPerOneUnit = convertDurationUnitToDuration(workflowRecord.getDurationUnit());
		final IModelTranslationMap trls = InterfaceWrapperHelper.getModelTranslationMap(workflowRecord);

		return Workflow.builder()
				.id(workflowId)
				.clientId(ClientId.ofRepoId(workflowRecord.getAD_Client_ID()))
				.name(trls.getColumnTrl(I_AD_Workflow.COLUMNNAME_Name, workflowRecord.getName()))
				.description(trls.getColumnTrl(I_AD_Workflow.COLUMNNAME_Description, workflowRecord.getDescription()))
				.help(trls.getColumnTrl(I_AD_Workflow.COLUMNNAME_Help, workflowRecord.getHelp()))
				//
				.priority(workflowRecord.getPriority())
				.validFrom(TimeUtil.asInstant(workflowRecord.getValidFrom()))
				.validTo(TimeUtil.asInstant(workflowRecord.getValidTo()))
				//
				.responsibleId(WFResponsibleId.optionalOfRepoId(workflowRecord.getAD_WF_Responsible_ID()).orElse(WFResponsibleId.Invoker))
				//
				.firstNodeId(WFNodeId.ofRepoId(workflowRecord.getAD_WF_Node_ID()))
				.nodes(retrieveNodes(workflowId, durationPerOneUnit))
				//
				.docValueWorkflowTriggerLogic(workflowRecord.getDocValueLogic())
				//
				.build();
	}

	private ImmutableList<WFNode> retrieveNodes(
			@NonNull final WorkflowId workflowId,
			@NonNull final Duration durationPerOneUnit)
	{
		return queryBL.createQueryBuilderOutOfTrx(I_AD_WF_Node.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_WF_Node.COLUMNNAME_AD_Workflow_ID, workflowId)
				.orderBy(I_AD_WF_Node.COLUMNNAME_AD_WF_Node_ID)
				.create()
				.stream()
				.map(nodeRecord -> toWFNode(nodeRecord, durationPerOneUnit))
				.collect(ImmutableList.toImmutableList());
	}

	private WFNode toWFNode(
			@NonNull final I_AD_WF_Node nodeRecord,
			@NonNull final Duration durationPerOneUnit)
	{
		final IModelTranslationMap trls = InterfaceWrapperHelper.getModelTranslationMap(nodeRecord);

		final WFNodeId nodeId = WFNodeId.ofRepoId(nodeRecord.getAD_WF_Node_ID());

		final int documentColumnId = nodeRecord.getAD_Column_ID();
		final MColumn documentColumn = documentColumnId > 0 ? MColumn.get(Env.getCtx(), documentColumnId) : null;
		final String documentColumnName = documentColumn != null ? documentColumn.getColumnName() : null;
		final ReferenceId documentColumnValueType = documentColumn != null ? ReferenceId.ofRepoId(documentColumn.getAD_Reference_ID()) : null;
		final ReferenceId documentColumnValueReferenceId = documentColumn != null ? ReferenceId.ofRepoIdOrNull(documentColumn.getAD_Reference_Value_ID()) : null;

		final WFNodeAction action = WFNodeAction.ofCode(nodeRecord.getAction());

		return WFNode.builder()
				.id(nodeId)
				.clientId(ClientId.ofRepoId(nodeRecord.getAD_Client_ID()))
				.active(nodeRecord.isActive())
				.name(trls.getColumnTrl(I_AD_WF_Node.COLUMNNAME_Name, nodeRecord.getName()))
				.description(trls.getColumnTrl(I_AD_WF_Node.COLUMNNAME_Description, nodeRecord.getDescription()))
				.help(trls.getColumnTrl(I_AD_WF_Node.COLUMNNAME_Help, nodeRecord.getHelp()))
				.responsibleId(WFResponsibleId.ofRepoIdOrNull(nodeRecord.getAD_WF_Responsible_ID()))
				.priority(nodeRecord.getPriority())
				.dynPriorityUnitDuration(convertDurationUnitToDuration(nodeRecord.getDynPriorityUnit()))
				.dynPriorityChange(nodeRecord.getDynPriorityChange())
				.action(action)
				//
				.durationLimit(durationPerOneUnit.multipliedBy(nodeRecord.getDurationLimit()))
				.waitTime(durationPerOneUnit.multipliedBy(nodeRecord.getWaitTime()))
				//
				.joinType(WFNodeJoinType.ofCode(nodeRecord.getJoinElement()))
				.splitType(WFNodeSplitType.ofCode(nodeRecord.getSplitElement()))
				.transitions(retrieveTransitions(nodeRecord))
				//
				// Document column
				.documentColumnId(documentColumnId)
				.documentColumnName(documentColumnName)
				.documentColumnValueType(documentColumnValueType)
				.documentColumnValueReferenceId(documentColumnValueReferenceId)
				//
				// Action: Document Action
				.docAction(nodeRecord.getDocAction())
				//
				// Action: Set Variable
				.attributeValue(nodeRecord.getAttributeValue())
				//
				// Action: User Choice
				.userApproval(action.isUserChoice() && "IsApproved".equals(documentColumnName))
				//
				// Action: Open Form
				.adFormId(nodeRecord.getAD_Form_ID())
				//
				// Action: Open Window
				.adWindowId(AdWindowId.ofRepoIdOrNull(nodeRecord.getAD_Window_ID()))
				//
				// Action: Mail
				.emailTo(EMailAddress.ofNullableString(nodeRecord.getEMail()))
				.emailRecipient(WFNodeEmailRecipient.ofNullableCode(nodeRecord.getEMailRecipient()))
				.mailTemplateId(MailTemplateId.ofRepoIdOrNull(nodeRecord.getR_MailText_ID()))
				//
				// Action: process/report
				.processId(AdProcessId.ofRepoIdOrNull(nodeRecord.getAD_Process_ID()))
				.processParameters(retrieveParameters(nodeId))
				//
				// UI Editor
				.xPosition(nodeRecord.getXPosition())
				.yPosition(nodeRecord.getYPosition())
				//
				.build();
	}

	@NonNull
	private ImmutableList<WFNodeTransition> retrieveTransitions(
			@NonNull final I_AD_WF_Node nodeRecord)
	{
		final WFNodeId nodeId = WFNodeId.ofRepoId(nodeRecord.getAD_WF_Node_ID());

		final List<I_AD_WF_NodeNext> records = queryBL.createQueryBuilderOutOfTrx(I_AD_WF_NodeNext.class)
				.addEqualsFilter(I_AD_WF_NodeNext.COLUMNNAME_AD_WF_Node_ID, nodeId)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_AD_WF_NodeNext.COLUMNNAME_SeqNo)
				.create()
				.list();

		if (records.isEmpty())
		{
			return ImmutableList.of();
		}

		final WFNodeSplitType splitType = WFNodeSplitType.ofCode(nodeRecord.getSplitElement());

		return records.stream()
				.map(record -> toWFNodeTransition(record, splitType))
				.collect(ImmutableList.toImmutableList());
	}

	private WFNodeTransition toWFNodeTransition(
			@NonNull final I_AD_WF_NodeNext record,
			@NonNull final WFNodeSplitType fromSplitType)
	{
		final WFNodeTransitionId transitionId = WFNodeTransitionId.ofRepoId(record.getAD_WF_NodeNext_ID());

		return WFNodeTransition.builder()
				.id(transitionId)
				.clientId(ClientId.ofRepoId(record.getAD_Client_ID()))
				.nextNodeId(WFNodeId.ofRepoId(record.getAD_WF_Next_ID()))
				.stdUserWorkflow(record.isStdUserWorkflow())
				.fromSplitType(fromSplitType)
				.description(record.getDescription())
				.seqNo(record.getSeqNo())
				.conditions(retrieveConditions(transitionId))
				.build();
	}

	private ImmutableList<WFNodeTransitionCondition> retrieveConditions(@NonNull final WFNodeTransitionId transitionId)
	{
		return queryBL.createQueryBuilderOutOfTrx(I_AD_WF_NextCondition.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_WF_NextCondition.COLUMNNAME_AD_WF_NodeNext_ID, transitionId)
				.orderBy(I_AD_WF_NextCondition.COLUMNNAME_SeqNo)
				.orderBy(I_AD_WF_NextCondition.COLUMNNAME_AD_WF_NextCondition_ID)
				.create()
				.stream()
				.map(ADWorkflowDAO::toWFTransitionConditionOrNull)
				.filter(Objects::nonNull)
				.collect(ImmutableList.toImmutableList());
	}

	@Nullable
	private static WFNodeTransitionCondition toWFTransitionConditionOrNull(final I_AD_WF_NextCondition record)
	{
		try
		{
			return WFNodeTransitionCondition.builder()
					.adColumnId(AdColumnId.ofRepoId(record.getAD_Column_ID()))
					.andJoin(X_AD_WF_NextCondition.ANDOR_And.equals(record.getAndOr()))
					.operation(record.getOperation())
					.conditionValue1(record.getValue())
					.conditionValue2(record.getValue2())
					.build();
		}
		catch (final Exception ex)
		{
			log.warn("Invalid {}. Skipped", record, ex);
			return null;
		}
	}

	private ImmutableList<WFNodeParameter> retrieveParameters(@NonNull final WFNodeId nodeId)
	{
		return queryBL.createQueryBuilderOutOfTrx(I_AD_WF_Node_Para.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_WF_Node_Para.COLUMNNAME_AD_WF_Node_ID, nodeId)
				.orderBy(I_AD_WF_Node_Para.COLUMNNAME_AD_Process_Para_ID)
				.create()
				.list(I_AD_WF_Node_Para.class)
				.stream()
				.map(ADWorkflowDAO::toWFNodeParameter)
				.collect(ImmutableList.toImmutableList());
	}

	private static WFNodeParameter toWFNodeParameter(final I_AD_WF_Node_Para record)
	{
		final I_AD_Process_Para adProcessPara = record.getAD_Process_Para();
		String attributeName = record.getAttributeName();
		if (Check.isBlank(attributeName) && adProcessPara != null)
		{
			attributeName = adProcessPara.getColumnName();
		}
		if (attributeName == null)
		{
			throw new AdempiereException("Cannot determine attribute name for " + record);
		}

		return WFNodeParameter.builder()
				.attributeName(attributeName)
				.attributeValue(record.getAttributeValue())
				.mandatory(adProcessPara != null && adProcessPara.isMandatory())
				.valueType(adProcessPara != null ? ReferenceId.ofRepoId(adProcessPara.getAD_Reference_ID()) : ReferenceId.ofRepoId(DisplayType.String))
				.build();
	}

	@Override
	public void deleteNodeTransitionById(@NonNull final WFNodeTransitionId transitionId)
	{
		final I_AD_WF_NodeNext record = InterfaceWrapperHelper.load(transitionId, I_AD_WF_NodeNext.class);
		InterfaceWrapperHelper.delete(record);
	}

	@Override
	public void deleteNodeById(@NonNull final WFNodeId id)
	{
		final I_AD_WF_Node record = InterfaceWrapperHelper.load(id, I_AD_WF_Node.class);
		InterfaceWrapperHelper.delete(record);
	}

	@Override
	public void changeNodeLayout(@NonNull final WFNodeLayoutChangeRequest request)
	{
		final I_AD_WF_Node nodeRecord = InterfaceWrapperHelper.load(request.getNodeId(), I_AD_WF_Node.class);
		nodeRecord.setXPosition(request.getXPosition());
		nodeRecord.setYPosition(request.getYPosition());
		InterfaceWrapperHelper.save(nodeRecord);
	}

	@Override
	public void createWFNode(@NonNull final WFNodeCreateRequest request)
	{
		final I_AD_WF_Node record = InterfaceWrapperHelper.newInstance(I_AD_WF_Node.class);
		record.setAD_Org_ID(OrgId.ANY.getRepoId());
		record.setAD_Workflow_ID(request.getWorkflowId().getRepoId());
		record.setValue(request.getValue());
		record.setName(request.getName());
		record.setAction(WFNodeAction.WaitSleep.getCode());
		record.setCost(BigDecimal.ZERO);
		record.setDuration(0);
		record.setEntityType("U");    // U
		record.setIsCentrallyMaintained(true);    // Y
		record.setJoinElement(WFNodeJoinType.XOR.getCode());    // X
		record.setDurationLimit(0);
		record.setSplitElement(WFNodeSplitType.XOR.getCode());    // X
		record.setWaitingTime(0);
		record.setXPosition(0);
		record.setYPosition(0);
		InterfaceWrapperHelper.save(record);
	}

	private static Duration convertDurationUnitToDuration(@Nullable final String durationUnit)
	{
		return durationUnit != null && Check.isNotBlank(durationUnit)
				? WFDurationUnit.ofCode(durationUnit).getDuration()
				: Duration.ZERO;
	}

	@Override
	public WFResponsible getWFResponsibleById(final @NonNull WFResponsibleId responsibleId)
	{
		return wfResponsiblesById.getOrLoad(responsibleId, this::retrieveWFResponsibleById);
	}

	private WFResponsible retrieveWFResponsibleById(final @NonNull WFResponsibleId responsibleId)
	{
		final I_AD_WF_Responsible record = InterfaceWrapperHelper.loadOutOfTrx(responsibleId, I_AD_WF_Responsible.class);

		return WFResponsible.builder()
				.id(responsibleId)
				.type(WFResponsibleType.ofCode(record.getResponsibleType()))
				.name(record.getName())
				.userId(record.getAD_User_ID() > 0 ? UserId.ofRepoId(record.getAD_User_ID()) : null)
				.roleId(record.getAD_Role_ID() > 0 ? RoleId.ofRepoId(record.getAD_Role_ID()) : null)
				.orgId(record.getAD_Org_ID() > 0 ? OrgId.ofRepoId(record.getAD_Org_ID()) : null)
				.build();
	}

	@Override
	public void onBeforeSave(final I_AD_WF_Node wfNodeRecord, final boolean newRecord)
	{
		final I_AD_Workflow workflow = InterfaceWrapperHelper.load(wfNodeRecord.getAD_Workflow_ID(), I_AD_Workflow.class);
		if (X_AD_Workflow.WORKFLOWTYPE_Manufacturing.equals(workflow.getWorkflowType()))
		{
			wfNodeRecord.setAction(X_AD_WF_Node.ACTION_WaitSleep);
			return;
		}

		final WFNodeAction action = WFNodeAction.ofCode(wfNodeRecord.getAction());
		//noinspection StatementWithEmptyBody
		if (action == WFNodeAction.WaitSleep)
		{
			// do nothing
		}
		else if (action == WFNodeAction.AppsProcess || action == WFNodeAction.AppsReport)
		{
			if (wfNodeRecord.getAD_Process_ID() <= 0)
			{
				throw new FillMandatoryException(I_AD_WF_Node.COLUMNNAME_AD_Process_ID);
			}
		}
		else if (action == WFNodeAction.AppsTask)
		{
			if (wfNodeRecord.getAD_Task_ID() <= 0)
			{
				throw new FillMandatoryException("AD_Task_ID");
			}
		}
		else if (action == WFNodeAction.DocumentAction)
		{
			if (StringUtils.trimBlankToNull(wfNodeRecord.getDocAction()) == null)
			{
				throw new FillMandatoryException("DocAction");
			}
		}
		else if (action == WFNodeAction.EMail)
		{
			if (wfNodeRecord.getR_MailText_ID() <= 0)
			{
				throw new FillMandatoryException("R_MailText_ID");
			}
		}
		else if (action == WFNodeAction.SetVariable)
		{
			if (wfNodeRecord.getAttributeValue() == null)
			{
				throw new FillMandatoryException("AttributeValue");
			}
		}
		else if (action == WFNodeAction.SubWorkflow)
		{
			if (wfNodeRecord.getAD_Workflow_ID() <= 0)
			{
				throw new FillMandatoryException("AD_Workflow_ID");
			}
		}
		else if (action == WFNodeAction.UserChoice)
		{
			if (wfNodeRecord.getAD_Column_ID() <= 0)
			{
				throw new FillMandatoryException("AD_Column_ID");
			}
		}
		else if (action == WFNodeAction.UserForm)
		{
			if (wfNodeRecord.getAD_Form_ID() <= 0)
			{
				throw new FillMandatoryException("AD_Form_ID");
			}
		}
		else if (action == WFNodeAction.UserWindow)
		{
			if (wfNodeRecord.getAD_Window_ID() <= 0)
			{
				throw new FillMandatoryException("AD_Window_ID");
			}
		}
	}

	@Override
	public ITranslatableString getWFNodeName(
			@NonNull final WorkflowId workflowId,
			@NonNull final WFNodeId nodeId)
	{
		return getById(workflowId)
				.getNodeById(nodeId)
				.getName();
	}
}
