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
import de.metas.organization.ClientAndOrgId;
import de.metas.serviceprovider.eventbus.EffortControlEventBusService;
import de.metas.serviceprovider.eventbus.EffortControlEventRequest;
import de.metas.serviceprovider.issue.IssueEntity;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Optional;

@Service
public class EffortControlService
{
	private final EffortControlEventBusService effortControlEventBusService;

	public EffortControlService(@NonNull final EffortControlEventBusService effortControlEventBusService)
	{
		this.effortControlEventBusService = effortControlEventBusService;
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

	@NonNull
	private EffortControlEventRequest buildEffortControlEventRequestForTarget(@NonNull final EffortTarget effortTarget, @NonNull final EffortChange effortChange)
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
