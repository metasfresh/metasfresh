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

package de.metas.workflow.service;

import de.metas.organization.OrgId;
import de.metas.user.UserId;
import de.metas.workflow.WFEventAuditType;
import de.metas.workflow.WFEventAudit;
import de.metas.workflow.WFEventAuditList;
import de.metas.workflow.WFNodeId;
import de.metas.workflow.WFResponsibleId;
import de.metas.workflow.WFState;
import de.metas.workflow.execution.WFProcessId;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_WF_EventAudit;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Duration;

@Repository
public class WFEventAuditRepository
{
	public void save(@NonNull final WFEventAuditList auditList)
	{
		auditList.toList().forEach(this::save);
	}

	public void save(@NonNull final WFEventAudit audit)
	{
		I_AD_WF_EventAudit record = audit.getId() > 0
				? InterfaceWrapperHelper.loadOutOfTrx(audit.getId(), I_AD_WF_EventAudit.class)
				: null;

		if (record == null)
		{
			record = InterfaceWrapperHelper.newInstanceOutOfTrx(I_AD_WF_EventAudit.class);
		}

		record.setEventType(audit.getEventType().getCode());
		record.setAD_Org_ID(audit.getOrgId().getRepoId());
		record.setAD_WF_Process_ID(audit.getWfProcessId().getRepoId());
		record.setAD_WF_Node_ID(WFNodeId.toRepoId(audit.getWfNodeId()));

		record.setAD_Table_ID(audit.getDocumentRef().getAD_Table_ID());
		record.setRecord_ID(audit.getDocumentRef().getRecord_ID());

		record.setAD_WF_Responsible_ID(audit.getWfResponsibleId().getRepoId());
		record.setAD_User_ID(UserId.toRepoId(audit.getUserId()));

		record.setWFState(audit.getWfState().getCode());
		record.setTextMsg(audit.getTextMsg());

		record.setElapsedTimeMS(BigDecimal.valueOf(audit.getElapsedTime().toMillis()));

		record.setAttributeName(audit.getAttributeName());
		record.setOldValue(audit.getAttributeValueOld());
		record.setNewValue(audit.getAttributeValueNew());

		InterfaceWrapperHelper.save(record);

		audit.setId(record.getAD_WF_EventAudit_ID());
		audit.setCreated(TimeUtil.asInstant(record.getCreated()));
	}

	@SuppressWarnings("unused")
	private static WFEventAudit toWFEventAudit(@NonNull final I_AD_WF_EventAudit record)
	{
		return WFEventAudit.builder()
				.id(record.getAD_WF_EventAudit_ID())
				.eventType(WFEventAuditType.ofCode(record.getEventType()))
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				//
				.wfProcessId(WFProcessId.ofRepoId(record.getAD_WF_Process_ID()))
				.wfNodeId(WFNodeId.ofRepoIdOrNull(record.getAD_WF_Node_ID()))
				//
				.documentRef(TableRecordReference.of(record.getAD_Table_ID(), record.getRecord_ID()))
				//
				.wfResponsibleId(WFResponsibleId.ofRepoId(record.getAD_WF_Responsible_ID()))
				.userId(UserId.ofRepoIdOrNullIfSystem(record.getAD_User_ID()))
				//
				.wfState(WFState.ofCode(record.getWFState()))
				.textMsg(record.getTextMsg())
				//
				.created(TimeUtil.asInstant(record.getCreated()))
				.elapsedTime(Duration.ofMillis(record.getElapsedTimeMS().longValue()))
				//
				.attributeName(record.getAttributeName())
				.attributeValueOld(record.getOldValue())
				.attributeValueNew(record.getNewValue())
				//
				.build();
	}
}
