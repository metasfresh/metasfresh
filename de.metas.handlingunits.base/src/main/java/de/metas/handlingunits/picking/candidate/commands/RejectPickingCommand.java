package de.metas.handlingunits.picking.candidate.commands;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;

import de.metas.handlingunits.picking.PickingCandidate;
import de.metas.handlingunits.picking.PickingCandidateRepository;
import de.metas.handlingunits.picking.PickingCandidateStatus;
import de.metas.handlingunits.picking.requests.RejectPickingRequest;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class RejectPickingCommand
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final PickingCandidateRepository pickingCandidateRepository;

	private final RejectPickingRequest request;

	@Builder
	private RejectPickingCommand(
			@NonNull final PickingCandidateRepository pickingCandidateRepository,
			@NonNull final RejectPickingRequest request)
	{
		this.pickingCandidateRepository = pickingCandidateRepository;
		this.request = request;
	}

	public RejectPickingResult perform()
	{
		return trxManager.callInThreadInheritedTrx(this::performInTrx);
	}

	private RejectPickingResult performInTrx()
	{
		final PickingCandidate pickingCandidate = getOrCreatePickingCandidate();
		pickingCandidate.assertDraft();

		pickingCandidate.rejectPicking(request.getQtyToReject());
		pickingCandidateRepository.save(pickingCandidate);

		return RejectPickingResult.of(pickingCandidate);
	}

	private PickingCandidate getOrCreatePickingCandidate()
	{
		if (request.getExistingPickingCandidateId() != null)
		{
			final PickingCandidate existingPickingCandidate = pickingCandidateRepository.getById(request.getExistingPickingCandidateId());
			if (!request.getShipmentScheduleId().equals(existingPickingCandidate.getShipmentScheduleId()))
			{
				throw new AdempiereException("ShipmentScheduleId does not match.")
						.appendParametersToMessage()
						.setParameter("expectedShipmentScheduleId", request.getShipmentScheduleId())
						.setParameter("pickingCandidate", existingPickingCandidate);
			}

			return existingPickingCandidate;
		}
		else
		{
			return PickingCandidate.builder()
					.processingStatus(PickingCandidateStatus.Draft)
					.qtyPicked(request.getQtyToReject())
					.shipmentScheduleId(request.getShipmentScheduleId())
					.pickFromHuId(request.getRejectPickingFromHuId())
					.build();
		}
	}
}
