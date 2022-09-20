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

package de.metas.serviceprovider.eventbus;

import de.metas.Profiles;
import de.metas.organization.OrgId;
import de.metas.serviceprovider.effortcontrol.repository.CreateEffortControlRequest;
import de.metas.serviceprovider.effortcontrol.repository.EffortControl;
import de.metas.serviceprovider.effortcontrol.repository.EffortControlQuery;
import de.metas.serviceprovider.effortcontrol.repository.EffortControlRepository;
import de.metas.serviceprovider.timebooking.Effort;
import lombok.NonNull;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile(Profiles.PROFILE_App) // it's important to have just *one* instance of this listener, because each event needs to be handled exactly once.
public class EffortControlEventHandlerService implements EffortControlEventHandler
{
	private final EffortControlRepository effortControlRepository;

	public EffortControlEventHandlerService(@NonNull final EffortControlRepository effortControlRepository)
	{
		this.effortControlRepository = effortControlRepository;
	}

	@Override
	public void handleRequest(@NonNull final EffortControlEventRequest request)
	{
		final EffortControl effortControl = resolveEffortControlFromEventRequest(request);

		final Effort computedEffortSum = effortControl
				.getEffortSum()
				.addNullSafe(request.mapDeltaEffortSum(Effort::ofDuration));

		final Effort computedPendingEffortSum = effortControl
				.getPendingEffortSum()
				.addNullSafe(request.mapDeltaPendingEffort(Effort::ofDuration));

		final EffortControl effortControlUpdated = effortControl.toBuilder()
				.pendingEffortSum(computedPendingEffortSum)
				.effortSum(computedEffortSum)
				.budget(effortControl.getBudget().add(request.getDeltaBudget()))
				.invoiceableHours(effortControl.getInvoiceableHours().add(request.getDeltaInvoiceableHours()))
				.build();

		effortControlRepository.update(effortControlUpdated);
	}

	@NonNull
	private EffortControl resolveEffortControlFromEventRequest(@NonNull final EffortControlEventRequest request)
	{
		final OrgId orgId = request.getClientAndOrgId().getOrgId();

		final EffortControlQuery query = EffortControlQuery.builder()
				.orgId(orgId)
				.costCenterId(request.getCostCenterId())
				.projectId(request.getProjectId())
				.build();

		return effortControlRepository.getOptionalByQuery(query)
				.orElseGet(() -> {
					final CreateEffortControlRequest createEffortControlRequest = CreateEffortControlRequest.builder()
							.orgId(orgId)
							.costCenterId(request.getCostCenterId())
							.projectId(request.getProjectId())
							.build();

					return effortControlRepository.save(createEffortControlRequest);
				});
	}
}
