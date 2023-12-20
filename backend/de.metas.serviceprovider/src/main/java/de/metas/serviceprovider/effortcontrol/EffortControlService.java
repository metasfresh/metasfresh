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

package de.metas.serviceprovider.effortcontrol;

import de.metas.common.util.CoalesceUtil;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerBL;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.organization.ClientAndOrgId;
import de.metas.serviceprovider.effortcontrol.repository.EffortControl;
import de.metas.serviceprovider.eventbus.EffortControlEventBusService;
import de.metas.serviceprovider.eventbus.EffortControlEventRequest;
import de.metas.serviceprovider.issue.IssueEntity;
import de.metas.serviceprovider.issue.IssueId;
import de.metas.serviceprovider.issue.IssueQuery;
import de.metas.serviceprovider.issue.IssueRepository;
import de.metas.serviceprovider.model.I_S_Issue;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EffortControlService
{
	private final IInvoiceCandidateHandlerBL invoiceCandidateHandlerBL = Services.get(IInvoiceCandidateHandlerBL.class);
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);

	private final EffortControlEventBusService effortControlEventBusService;
	private final IssueRepository issueRepository;

	public EffortControlService(
			@NonNull final EffortControlEventBusService effortControlEventBusService,
			@NonNull final IssueRepository issueRepository)
	{
		this.effortControlEventBusService = effortControlEventBusService;
		this.issueRepository = issueRepository;
	}

	public void handleEffortChanges(@NonNull final EffortChange effortChange)
	{
		effortChange.listTargets()
				.stream()
				.map(target -> buildEffortControlEventRequestForTarget(target, effortChange))
				.forEach(effortControlEventBusService::postRequestAfterCommit);
	}

	@NonNull
	public Optional<EffortInfo> getEffortInfo(@NonNull final IssueEntity issue)
	{
		if (!issue.getEffortTarget().isPresent())
		{
			return Optional.empty();
		}

		final Duration issueEffort = Duration.ofSeconds(issue.getIssueEffort().getSeconds());

		return Optional.ofNullable(EffortInfo.builder()
										   .effortTarget(issue.getEffortTarget().get())
										   .issueStatus(issue.getStatusOrNew())
										   .effortSum(issueEffort)
										   //note: budget is spread across multiple effort issues but also kept at budget level;
										   .budget(issue.isEffortIssue() ? CoalesceUtil.coalesceNotNull(issue.getBudgetedEffort(), BigDecimal.ZERO) : BigDecimal.ZERO)
										   //note: invoiceable hours are ultimately set at Budget issue level
										   .invoiceableHours(issue.isEffortIssue() ? BigDecimal.ZERO : CoalesceUtil.coalesceNotNull(issue.getInvoiceableHours(), BigDecimal.ZERO))
										   .build());
	}

	public void generateICFromEffortControl(@NonNull final EffortControl effortControl)
	{
		final Iterator<I_S_Issue> budgetRecordIterator = iterateUnprocessedBudgetIssues(effortControl);

		while (budgetRecordIterator.hasNext())
		{
			final I_S_Issue budgetRecord = budgetRecordIterator.next();
			generateInvoiceCandidate(budgetRecord);
		}
	}

	public void createOrUpdateInvoiceCandidateForLinkedSubIssues(@NonNull final IssueId issueId)
	{
		issueRepository.getDirectlyLinkedSubIssues(issueId)
				.stream()
				.map(IssueEntity::getIssueId)
				.filter(Objects::nonNull)
				.map(issueRepository::getRecordById)
				.forEach(this::generateInvoiceCandidate);
	}

	@NonNull
	private Iterator<I_S_Issue> iterateUnprocessedBudgetIssues(@NonNull final EffortControl effortControl)
	{
		final IssueQuery query = IssueQuery.builder()
				.orgId(effortControl.getOrgId())
				.projectId(effortControl.getProjectId())
				.costCenterId(effortControl.getCostCenterId())
				.effortIssue(false)
				.processed(false)
				.build();

		return issueRepository.iterateRecordsByQuery(query);
	}

	private void generateInvoiceCandidate(@NonNull final I_S_Issue issueRecord)
	{
		final List<I_C_Invoice_Candidate> existingCandidates = invoiceCandDAO.retrieveReferencing(TableRecordReference.of(issueRecord));
		if (existingCandidates.isEmpty())
		{
			invoiceCandidateHandlerBL.scheduleCreateMissingCandidatesFor(issueRecord);
		}
		else
		{
			invoiceCandDAO.invalidateCands(existingCandidates);
		}
	}

	@NonNull
	private static EffortControlEventRequest buildEffortControlEventRequestForTarget(@NonNull final EffortTarget effortTarget, @NonNull final EffortChange effortChange)
	{
		return EffortControlEventRequest.builder()
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(effortChange.getClientId(), effortTarget.getOrgId()))
				.costCenterId(effortTarget.getCostCenterId())
				.projectId(effortTarget.getProjectId())
				.deltaPendingEffortSum(effortChange.getPendingEffortSumForTarget(effortTarget))
				.deltaEffortSum(effortChange.getDeltaEffortSumForTarget(effortTarget))
				.deltaBudget(effortChange.getDeltaBudgetForTarget(effortTarget))
				.deltaInvoiceableHours(effortChange.getDeltaInvoiceableHoursForTarget(effortTarget))
				.build();
	}
}
