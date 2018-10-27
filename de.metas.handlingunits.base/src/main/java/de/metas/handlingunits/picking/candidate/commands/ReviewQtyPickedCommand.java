package de.metas.handlingunits.picking.candidate.commands;

import java.math.BigDecimal;

import org.adempiere.ad.trx.api.ITrxManager;

import de.metas.handlingunits.picking.PickingCandidate;
import de.metas.handlingunits.picking.PickingCandidateId;
import de.metas.handlingunits.picking.PickingCandidateRepository;
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

public class ReviewQtyPickedCommand
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final PickingCandidateRepository pickingCandidateRepository;

	private PickingCandidateId pickingCandidateId;
	private BigDecimal qtyReviewed;

	@Builder
	private ReviewQtyPickedCommand(
			@NonNull final PickingCandidateRepository pickingCandidateRepository,
			@NonNull PickingCandidateId pickingCandidateId,
			@NonNull BigDecimal qtyReviewed)
	{
		this.pickingCandidateRepository = pickingCandidateRepository;
		this.pickingCandidateId = pickingCandidateId;
		this.qtyReviewed = qtyReviewed;
	}

	public PickingCandidate perform()
	{
		return trxManager.callInThreadInheritedTrx(this::performInTrx);
	}

	private PickingCandidate performInTrx()
	{
		final PickingCandidate pickingCandidate = pickingCandidateRepository.getById(pickingCandidateId);
		pickingCandidate.assertDraft();

		pickingCandidate.reviewPicking(qtyReviewed);
		pickingCandidateRepository.save(pickingCandidate);
		return pickingCandidate;
	}
}
