/*
 * #%L
 * de.metas.materialtracking
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.materialtracking.impl;

import de.metas.common.util.CoalesceUtil;
import de.metas.invoicecandidate.api.IAggregationBL;
import de.metas.materialtracking.IMaterialTrackingBL;
import de.metas.materialtracking.IMaterialTrackingDAO;
import de.metas.materialtracking.MTLinkRequest;
import de.metas.materialtracking.MaterialTrackingId;
import de.metas.materialtracking.model.I_C_Invoice_Candidate;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MaterialTrackingInvoiceCandService
{
	private final MaterialTrackingInvoiceCandRepository repo;
	private final IAggregationBL aggregationBL = Services.get(IAggregationBL.class);
	private final IMaterialTrackingBL materialTrackingBL = Services.get(IMaterialTrackingBL.class);
	private final IMaterialTrackingDAO materialTrackingDAO = Services.get(IMaterialTrackingDAO.class);

	public void unlinkMaterialTrackings(@NonNull final List<I_C_Invoice_Candidate> candidate, final boolean isClearAggregationKeyOverride)
	{
		candidate.forEach(c -> unlinkMaterialTracking(c, isClearAggregationKeyOverride));
	}

	public void unlinkMaterialTracking(@NonNull final I_C_Invoice_Candidate candidate, final boolean isClearAggregationKeyOverride)
	{
		candidate.setM_Material_Tracking_ID(-1);
		if (isClearAggregationKeyOverride)
		{
			candidate.setC_Invoice_Candidate_HeaderAggregation_Override(null);
		}
		aggregationBL.getUpdateProcessor().process(candidate);

		repo.save(candidate);

		materialTrackingBL.unlinkModelFromMaterialTrackings(candidate);
	}

	public void linkMaterialTrackings(@NonNull final List<I_C_Invoice_Candidate> candidate, @NonNull final MaterialTrackingId materialTrackingId)
	{
		candidate.forEach(c -> linkMaterialTracking(c, materialTrackingId));
	}

	public void linkMaterialTracking(@NonNull final I_C_Invoice_Candidate candidate, @NonNull final MaterialTrackingId materialTrackingId)
	{
		final MaterialTrackingId previousMaterialTrackingId = MaterialTrackingId.ofRepoIdOrNull(candidate.getM_Material_Tracking_ID());

		final Optional<I_C_Invoice_Candidate> existingICForMT = repo.getFirstForMaterialTrackingId(materialTrackingId);

		if (existingICForMT.isPresent() && existingICForMT.get().getBill_BPartner_ID() != candidate.getBill_BPartner_ID())
		{
			//Not for the same BillBPartner as other ICs of the material tracking, can't add to group
			return;
		}

		candidate.setM_Material_Tracking_ID(MaterialTrackingId.toRepoId(materialTrackingId));

		existingICForMT.map(ic -> CoalesceUtil.coalesce(ic.getC_Invoice_Candidate_HeaderAggregation_Override(), ic.getC_Invoice_Candidate_HeaderAggregation()))
				.ifPresent(candidate::setC_Invoice_Candidate_HeaderAggregation_Override);

		aggregationBL.getUpdateProcessor().process(candidate);

		repo.save(candidate);

		final I_M_Material_Tracking newMaterialTracking = materialTrackingDAO.getById(materialTrackingId);
		materialTrackingBL.linkModelToMaterialTracking(MTLinkRequest.builder()
				.model(candidate)
				.previousMaterialTrackingId(MaterialTrackingId.toRepoId(previousMaterialTrackingId))
				.ifModelAlreadyLinked(MTLinkRequest.IfModelAlreadyLinked.UNLINK_FROM_PREVIOUS)
				.materialTrackingRecord(newMaterialTracking)
				.build());
	}

}
