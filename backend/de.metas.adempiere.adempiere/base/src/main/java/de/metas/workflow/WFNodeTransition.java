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
import de.metas.logging.LogManager;
import de.metas.workflow.execution.WFActivity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.service.ClientId;
import org.slf4j.Logger;

@Value
@Builder
public class WFNodeTransition
{
	private static final Logger logger = LogManager.getLogger(WFNodeTransition.class);

	@NonNull
	WFNodeTransitionId id;

	@NonNull
	ClientId clientId;

	@NonNull
	WFNodeId nextNodeId;

	boolean stdUserWorkflow;

	final WFNodeSplitType fromSplitType;

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

	public boolean isValidFor(final WFActivity activity)
	{
		if (isStdUserWorkflow())
		{
			final IDocument document = activity.getDocumentOrNull();
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
					logger.debug("isValidFor =NO= StdUserWF - Status={} - Action={}", docStatus, docAction);
					return false;
				}
			}
		}

		//	No Conditions
		final ImmutableList<WFNodeTransitionCondition> conditions = getConditions();
		if (conditions.isEmpty())
		{
			return true;
		}

		//	First condition always AND
		boolean ok = conditions.get(0).evaluate(activity);
		for (int i = 1; i < conditions.size(); i++)
		{
			final WFNodeTransitionCondition condition = conditions.get(i);
			if (condition.isOr())
			{
				ok = ok || condition.evaluate(activity);
			}
			else
			{
				ok = ok && condition.evaluate(activity);
			}
		}    //	for all conditions

		return ok;
	}

}
