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

package de.metas.workflow;

import com.google.common.collect.ImmutableList;
import de.metas.ad_reference.ReferenceId;
import de.metas.workflow.execution.approval.strategy.DocApprovalStrategyId;
import de.metas.email.EMailAddress;
import de.metas.email.templates.MailTemplateId;
import de.metas.i18n.ITranslatableString;
import de.metas.process.AdProcessId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.service.ClientId;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Optional;

@Value
@Builder
public class WFNode
{
	@NonNull WFNodeId id;

	@NonNull ClientId clientId;

	boolean active;

	@NonNull ITranslatableString name;
	@NonNull ITranslatableString description;
	@NonNull ITranslatableString help;

	@Nullable
	WFResponsibleId responsibleId;

	int priority;
	@NonNull
	Duration dynPriorityUnitDuration;
	@NonNull
	BigDecimal dynPriorityChange;

	@NonNull WFNodeAction action;

	@NonNull Duration durationLimit;
	@NonNull Duration waitTime;

	@NonNull WFNodeJoinType joinType;
	@NonNull WFNodeSplitType splitType;
	@NonNull ImmutableList<WFNodeTransition> transitions;

	//
	// Document column
	String documentColumnName;
	int documentColumnId;
	ReferenceId documentColumnValueType;
	ReferenceId documentColumnValueReferenceId;

	//
	// Action: Document Action
	String docAction;

	//
	// Action: Set Variable
	String attributeValue;

	//
	// Action: User Choice
	boolean userApproval;
	@Nullable DocApprovalStrategyId docApprovalStrategyId;

	//
	// Action: Open Form
	int adFormId;

	//
	// Action: Open Window
	AdWindowId adWindowId;

	//
	// Action: Mail
	EMailAddress emailTo;
	WFNodeEmailRecipient emailRecipient;
	MailTemplateId mailTemplateId;

	//
	// Action: process/report
	AdProcessId processId;
	@NonNull
	ImmutableList<WFNodeParameter> processParameters;

	//
	// UI Editor
	int xPosition;
	int yPosition;

	public ImmutableList<WFNodeTransition> getTransitions(@NonNull final ClientId clientId)
	{
		return getTransitions()
				.stream()
				.filter(transition -> transition.isMatchingClientId(clientId))
				.collect(ImmutableList.toImmutableList());
	}

	public Optional<DocApprovalStrategyId> getDocApprovalStrategyId() {return Optional.ofNullable(docApprovalStrategyId);}
}
