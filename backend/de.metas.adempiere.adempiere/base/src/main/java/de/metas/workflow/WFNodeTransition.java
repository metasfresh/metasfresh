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
import de.metas.document.engine.IDocument;
import de.metas.i18n.BooleanWithReason;
import de.metas.workflow.execution.WFActivity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.service.ClientId;

import javax.annotation.Nullable;

@Value
@Builder
public class WFNodeTransition
{
	@NonNull
	WFNodeTransitionId id;

	@NonNull
	ClientId clientId;

	@NonNull
	WFNodeId nextNodeId;

	boolean stdUserWorkflow;

	@NonNull
	WFNodeSplitType fromSplitType;

	@Nullable
	String description;

	int seqNo;

	@NonNull
	ImmutableList<WFNodeTransitionCondition> conditions;

	public boolean isMatchingClientId(@NonNull final ClientId clientIdToMatch)
	{
		return clientId.isSystem() || ClientId.equals(clientId, clientIdToMatch);
	}

	public boolean isUnconditional()
	{
		return !isStdUserWorkflow() && getConditions().isEmpty();
	}    //	isUnconditional

	public BooleanWithReason checkAllowGoingAwayFrom(final WFActivity fromActivity)
	{
		if (isStdUserWorkflow())
		{
			final IDocument document = fromActivity.getDocumentOrNull();
			if (document != null)
			{
				final String docStatus = document.getDocStatus();
				final String docAction = document.getDocAction();
				if (!IDocument.ACTION_Complete.equals(docAction)
						|| IDocument.STATUS_Completed.equals(docStatus)
						|| IDocument.STATUS_WaitingConfirmation.equals(docStatus)
						|| IDocument.STATUS_WaitingPayment.equals(docStatus)
						|| IDocument.STATUS_Voided.equals(docStatus)
						|| IDocument.STATUS_Closed.equals(docStatus)
						|| IDocument.STATUS_Reversed.equals(docStatus))
				{
					return BooleanWithReason.falseBecause("document state is not valid for a standard workflow transition (docStatus=" + docStatus + ", docAction=" + docAction + ")");
				}
			}
		}

		//	No Conditions
		final ImmutableList<WFNodeTransitionCondition> conditions = getConditions();
		if (conditions.isEmpty())
		{
			return BooleanWithReason.trueBecause("no conditions");
		}

		//	First condition always AND
		boolean ok = conditions.get(0).evaluate(fromActivity);
		for (int i = 1; i < conditions.size(); i++)
		{
			final WFNodeTransitionCondition condition = conditions.get(i);
			if (condition.isOr())
			{
				ok = ok || condition.evaluate(fromActivity);
			}
			else
			{
				ok = ok && condition.evaluate(fromActivity);
			}
		}    //	for all conditions

		return ok
				? BooleanWithReason.trueBecause("transition conditions matched")
				: BooleanWithReason.falseBecause("transition conditions NOT matched");
	}

}
