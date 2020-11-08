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

import de.metas.i18n.ITranslatableString;
import de.metas.util.ISingletonService;
import de.metas.workflow.WFNodeId;
import de.metas.workflow.WFNodeTransitionId;
import de.metas.workflow.WFResponsible;
import de.metas.workflow.WFResponsibleId;
import de.metas.workflow.Workflow;
import de.metas.workflow.WorkflowId;
import lombok.NonNull;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.service.ClientId;
import org.compiere.model.I_AD_WF_Node;

import java.util.Collection;

public interface IADWorkflowDAO extends ISingletonService
{
	Workflow getById(WorkflowId workflowId);

	Collection<Workflow> getDocValueWorkflows(
			@NonNull ClientId clientId,
			@NonNull AdTableId adTableId);

	void deleteNodeTransitionById(WFNodeTransitionId transitionId);

	void deleteNodeById(WFNodeId id);

	void changeNodeLayout(WFNodeLayoutChangeRequest request);

	void createWFNode(WFNodeCreateRequest request);

	WFResponsible getWFResponsibleById(@NonNull WFResponsibleId responsibleId);

	void onBeforeSave(I_AD_WF_Node wfNodeRecord, boolean newRecord);

	ITranslatableString getWFNodeName(
			@NonNull WorkflowId workflowId,
			@NonNull WFNodeId nodeId);
}
