/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.project.service;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.reservation.HUReservation;
import de.metas.handlingunits.reservation.HUReservationDocRef;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.handlingunits.reservation.ReserveHUsRequest;
import de.metas.project.ProjectAndLineId;
import de.metas.project.ProjectId;
import de.metas.project.ProjectLine;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.model.I_C_Project;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class HUProjectService
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final ProjectService projectService;
	private final HUReservationService huReservationService;

	public HUProjectService(
			@NonNull final ProjectService projectService,
			@NonNull final HUReservationService huReservationService)
	{
		this.projectService = projectService;
		this.huReservationService = huReservationService;
	}

	public I_C_Project getById(@NonNull final ProjectId id)
	{
		return projectService.getById(id);
	}

	public List<ProjectLine> getLines(@NonNull final ProjectId projectId)
	{
		return projectService.getLines(projectId);
	}

	public ProjectLine getLineById(@NonNull final ProjectAndLineId projectLineId)
	{
		return projectService.getLineById(projectLineId);
	}

	public void reserveHUs(
			final ProjectAndLineId projectLineId,
			final ImmutableSet<HuId> huIds)
	{
		final I_C_Project project = projectService.getById(projectLineId.getProjectId());
		final ProjectLine projectLine = projectService.getLineById(projectLineId);

		final HUReservation huReservation = huReservationService.makeReservation(ReserveHUsRequest.builder()
				.documentRef(HUReservationDocRef.ofProjectAndLineId(projectLineId))
				.productId(projectLine.getProductId())
				.qtyToReserve(projectLine.getPlannedQtyButNotCommitted())
				.customerId(BPartnerId.ofRepoId(project.getC_BPartner_ID()))
				.huIds(huIds)
				.build())
				.orElse(null);

		final Quantity qtyReserved = huReservation.getReservedQtySum();
		projectService.changeProjectLine(ChangeProjectLineRequest.builder()
				.projectLineId(projectLineId)
				.committedQtyToAdd(qtyReserved)
				.build());

	}

	public void releaseReservedHUs(@NonNull final Set<ProjectAndLineId> projectLineIds)
	{
		if (projectLineIds.isEmpty())
		{
			return;
		}

		trxManager.runInThreadInheritedTrx(() -> releaseReservedHUsInTrx(projectLineIds));
	}

	private void releaseReservedHUsInTrx(@NonNull final Set<ProjectAndLineId> projectLineIds)
	{
		final HashSet<HuId> vhuIdsToRelease = new HashSet<>();
		final HashMap<ProjectAndLineId, Quantity> qtyToRelease = new HashMap<>();

		for (final ProjectAndLineId projectLineId : projectLineIds)
		{
			huReservationService
					.getByDocumentRef(HUReservationDocRef.ofProjectAndLineId(projectLineId))
					.ifPresent(huReservation -> {
						qtyToRelease.put(projectLineId, huReservation.getReservedQtySum());
						vhuIdsToRelease.addAll(huReservation.getVhuIds());
					});
		}

		huReservationService.deleteReservations(vhuIdsToRelease);

		for (final ProjectAndLineId projectLineId : qtyToRelease.keySet())
		{
			projectService.changeProjectLine(ChangeProjectLineRequest.builder()
					.projectLineId(projectLineId)
					.committedQtyToAdd(qtyToRelease.get(projectLineId).negate())
					.build());
		}
	}
}
