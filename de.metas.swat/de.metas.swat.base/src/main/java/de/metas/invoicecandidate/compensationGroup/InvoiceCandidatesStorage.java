package de.metas.invoicecandidate.compensationGroup;

import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;

import com.google.common.collect.ImmutableMap;

import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.order.compensationGroup.GroupId;
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
		if (compensationLinePO.getGroupNo() <= 0)
		{
			compensationLinePO.setGroupNo(groupId.getGroupNo());
		}
		else if (compensationLinePO.getGroupNo() != groupId.getGroupNo())
		{
			throw new AdempiereException("Invoice candidate has already another groupNo set: " + compensationLinePO)
					.setParameter("expectedGroupNo", groupId.getGroupNo())
					.appendParametersToMessage();
		}
	}

	public I_C_Invoice_Candidate getByIdIfPresent(final int invoiceCandidateId)
	{
		return invoiceCandidatesById.get(invoiceCandidateId);
	}
}
