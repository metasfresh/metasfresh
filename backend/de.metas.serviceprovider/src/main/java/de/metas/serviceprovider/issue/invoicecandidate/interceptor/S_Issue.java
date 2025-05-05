/*
 * #%L
 * de.metas.serviceprovider.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.serviceprovider.issue.invoicecandidate.interceptor;

import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.serviceprovider.effortcontrol.EffortControlService;
import de.metas.serviceprovider.issue.IssueId;
import de.metas.serviceprovider.issue.Status;
import de.metas.serviceprovider.model.I_S_Issue;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Interceptor(I_S_Issue.class)
@Component
public class S_Issue
{
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);

	private final EffortControlService effortControlService;

	public S_Issue(@NonNull final EffortControlService effortControlService)
	{
		this.effortControlService = effortControlService;
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = {
					I_S_Issue.COLUMNNAME_AD_Org_ID,
					I_S_Issue.COLUMNNAME_C_Activity_ID,
					I_S_Issue.COLUMNNAME_C_Project_ID })
	public void forbidUpdatesOnInvoicedIssue(@NonNull final I_S_Issue record)
	{
		final Status status = Status.ofCode(record.getStatus());

		if (status.equals(Status.INVOICED))
		{
			throw new AdempiereException("OrgId, ActivityId & ProjectId cannot be changed for an INVOICED issue record!")
					.appendParametersToMessage()
					.setParameter("S_Issue_ID", record.getS_Issue_ID())
					.setParameter("AD_Org_ID", record.getAD_Org_ID())
					.setParameter("C_Project_ID", record.getC_Project_ID())
					.setParameter("C_Activity_ID", record.getC_Activity_ID());
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_S_Issue.COLUMNNAME_Status })
	public void forbidStatusChangeOnIssueWithIC(@NonNull final I_S_Issue record)
	{
		final Status status = Status.ofCode(record.getStatus());

		if (status.equals(Status.INVOICED))
		{
			return;
		}

		final Set<InvoiceCandidateId> existingCandidateIds = invoiceCandDAO.retrieveReferencingIds(TableRecordReference.of(record));

		if (existingCandidateIds.isEmpty())
		{
			return;
		}

		throw new AdempiereException("There are existing invoice candidates for the given issue. Status cannot be changed from INVOICED!")
				.appendParametersToMessage()
				.setParameter("S_Issue_ID", record.getS_Issue_ID())
				.setParameter("InvoiceCandidateIds", existingCandidateIds);
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE },
			ifColumnsChanged = { I_S_Issue.COLUMNNAME_Status, I_S_Issue.COLUMNNAME_Processed })
	public void createOrUpdateInvoiceCandidateForChildIssues(@NonNull final I_S_Issue record)
	{
		final Status status = Status.ofCode(record.getStatus());

		if (!status.equals(Status.INVOICED) || !record.isProcessed())
		{
			return;
		}

		final Set<InvoiceCandidateId> existingCandidateIds = invoiceCandDAO.retrieveReferencingIds(TableRecordReference.of(record));

		if (existingCandidateIds.isEmpty())
		{
			return;
		}

		effortControlService.createOrUpdateInvoiceCandidateForLinkedSubIssues(IssueId.ofRepoId(record.getS_Issue_ID()));
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE },
			ifColumnsChanged = { I_S_Issue.COLUMNNAME_IssueEffort })
	public void recomputeInvoiceCandidateOnEffortIssueChange(@NonNull final I_S_Issue record)
	{
		if (!record.isEffortIssue())
		{
			return;
		}

		final IssueId parentIssue = IssueId.ofRepoIdOrNull(record.getS_Parent_Issue_ID());
		if (parentIssue == null)
		{
			return;
		}

		final Status status = Status.ofCode(record.getStatus());

		if (!status.equals(Status.INVOICED) || !record.isProcessed())
		{
			return;
		}

		final List<I_C_Invoice_Candidate> existingCandidates = invoiceCandDAO.retrieveReferencing(TableRecordReference.of(record));

		if (existingCandidates.isEmpty())
		{
			return;
		}

		invoiceCandDAO.invalidateCands(existingCandidates);
	}
}
