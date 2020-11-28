package de.metas.invoicecandidate.compensationGroup;

import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;

import com.google.common.collect.ImmutableMap;

import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.order.compensationGroup.GroupId;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

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

public class InvoiceCandidatesStorage
{
	@Getter
	private final GroupId groupId;
	private final boolean performDatabaseChanges;
	private final ImmutableMap<Integer, I_C_Invoice_Candidate> invoiceCandidatesById;

	@Builder
	private InvoiceCandidatesStorage(
			@NonNull final GroupId groupId,
			@NonNull @Singular final List<I_C_Invoice_Candidate> invoiceCandidates,
			@NonNull final Boolean performDatabaseChanges)
	{
		this.groupId = groupId;
		this.performDatabaseChanges = performDatabaseChanges;
		invoiceCandidatesById = invoiceCandidates.stream()
				.peek(InvoiceCandidateCompensationGroupUtils::assertCompensationLine)
				.collect(GuavaCollectors.toImmutableMapByKey(I_C_Invoice_Candidate::getC_Invoice_Candidate_ID));
	}

	public void save(final I_C_Invoice_Candidate compensationLinePO)
	{
		Check.assume(!compensationLinePO.isProcessed(), "Changing a processed line is not allowed: {}", compensationLinePO); // shall not happen

		setGroupNo(compensationLinePO);

		if (performDatabaseChanges)
		{
			InterfaceWrapperHelper.save(compensationLinePO);
		}
	}

	private void setGroupNo(final I_C_Invoice_Candidate compensationLinePO)
	{
		if (compensationLinePO.getC_Order_CompensationGroup_ID() <= 0)
		{
			compensationLinePO.setC_Order_CompensationGroup_ID(groupId.getOrderCompensationGroupId());
		}
		else if (compensationLinePO.getC_Order_CompensationGroup_ID() != groupId.getOrderCompensationGroupId())
		{
			throw new AdempiereException("Invoice candidate has already another groupId set: " + compensationLinePO)
					.setParameter("expectedGroupId", groupId.getOrderCompensationGroupId())
					.appendParametersToMessage();
		}
	}

	public I_C_Invoice_Candidate getByIdIfPresent(final int invoiceCandidateId)
	{
		return invoiceCandidatesById.get(invoiceCandidateId);
	}
}
