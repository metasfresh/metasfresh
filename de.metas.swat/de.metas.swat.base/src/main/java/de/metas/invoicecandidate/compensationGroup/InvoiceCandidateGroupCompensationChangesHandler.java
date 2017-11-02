package de.metas.invoicecandidate.compensationGroup;

import org.compiere.model.X_C_OrderLine;

import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.order.compensationGroup.GroupId;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class InvoiceCandidateGroupCompensationChangesHandler
{
	private final InvoiceCandidateGroupRepository groupsRepo;

	@Builder
	private InvoiceCandidateGroupCompensationChangesHandler(@NonNull final InvoiceCandidateGroupRepository groupsRepo)
	{
		this.groupsRepo = groupsRepo;
	}

	public void onInvoiceCandidateChanged(final I_C_Invoice_Candidate invoiceCandidate)
	{
		// Skip if not a group line
		if (!InvoiceCandidateCompensationGroupUtils.isInGroup(invoiceCandidate))
		{
			return;
		}

		// Don't touch processed lines
		if (invoiceCandidate.isProcessed())
		{
			return;
		}

		final boolean groupCompensationLine = invoiceCandidate.isGroupCompensationLine();
		final String amtType = invoiceCandidate.getGroupCompensationAmtType();
		if (!groupCompensationLine)
		{
			onRegularGroupLineChanged(invoiceCandidate);
		}
		else if (X_C_OrderLine.GROUPCOMPENSATIONAMTTYPE_Percent.equals(amtType))
		{
			onPercentageCompensationLineChanged(invoiceCandidate);
		}
	}

	private void onRegularGroupLineChanged(final I_C_Invoice_Candidate regularLine)
	{
		final GroupId groupId = groupsRepo.extractGroupId(regularLine);
		groupsRepo.invalidateCompensationInvoiceCandidatesOfGroup(groupId);
	}

	private void onPercentageCompensationLineChanged(final I_C_Invoice_Candidate compensationLine)
	{
	}

}
