package de.metas.handlingunits.picking.candidate.commands;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.IAllocationDestination;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.HUListAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.picking.IHUPickingSlotBL;
import de.metas.handlingunits.picking.PickingCandidate;
import de.metas.handlingunits.picking.PickingCandidateRepository;
import de.metas.handlingunits.picking.requests.RemoveQtyFromHURequest;
import de.metas.handlingunits.sourcehu.HuId2SourceHUsService;
import de.metas.picking.api.PickingSlotId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.adempiere.model.InterfaceWrapperHelper.load;

/*
 * #%L
 * de.metas.handlingunits.base
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

public class RemoveQtyFromHUCommand
{
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);
	private final IHUPickingSlotBL huPickingSlotBL = Services.get(IHUPickingSlotBL.class);
	private final HuId2SourceHUsService sourceHUsRepository;
	private final PickingCandidateRepository pickingCandidateRepository;

	private final HuId huId;
	private final ProductId productId;
	private final Quantity qtyToRemove;

	@Builder
	private RemoveQtyFromHUCommand(
			@NonNull final HuId2SourceHUsService sourceHUsRepository,
			@NonNull final PickingCandidateRepository pickingCandidateRepository,
			@NonNull final RemoveQtyFromHURequest request)
	{
		this.sourceHUsRepository = sourceHUsRepository;
		this.pickingCandidateRepository = pickingCandidateRepository;
		this.huId = request.getHuId();
		this.productId = request.getProductId();
		this.qtyToRemove = request.getQtyToRemove();
	}

	public void perform()
	{
		final HUListAllocationSourceDestination source = createAllocationSourceAsHU();
		final IAllocationDestination destination = createAllocationDestinationAsSourceHUs();

		final List<PickingCandidate> candidates = retrievePickingCandidates();
		if (candidates.isEmpty())
		{
			throw new AdempiereException("No picking candidates found");
		}

		Quantity qtyToRemoveRemaining = qtyToRemove;
		final ArrayList<PickingCandidate> candidatesToDelete = new ArrayList<>();
		for (final PickingCandidate candidate : candidates)
		{
			// skip processed candidates
			if (candidate.isProcessed())
			{
				continue;
			}

			final Quantity qtyPickedOnThisCandidate = candidate.getQtyPicked();
			final Quantity qtyToRemoveOnThisCandidate = qtyToRemoveRemaining.min(qtyPickedOnThisCandidate);

			// Load QtyCU to HU(destination)
			final IAllocationResult loadResult = HULoader.of(source, destination)
					.setAllowPartialLoads(true) // don't fail if the the picking staff attempted to to pick more than the TU's capacity
					.setAllowPartialUnloads(true) // don't fail if the the picking staff attempted to to pick more than the shipment schedule's quantity to deliver.
					.load(createAllocationRequest(candidate, qtyToRemoveOnThisCandidate));

			final Quantity qtyRemovedOnThisCandidate = Quantity.of(loadResult.getQtyAllocated(), qtyToRemoveOnThisCandidate.getUOM());
			final Quantity newQtyPickedOnThisCandidate = qtyPickedOnThisCandidate.subtract(qtyRemovedOnThisCandidate);

			if (newQtyPickedOnThisCandidate.signum() == 0)
			{
				candidatesToDelete.add(candidate);
			}
			else
			{
				candidate.pick(newQtyPickedOnThisCandidate);
				pickingCandidateRepository.save(candidate);
			}

			qtyToRemoveRemaining = qtyToRemoveRemaining.subtract(qtyRemovedOnThisCandidate);
			if (qtyToRemoveRemaining.signum() == 0)
			{
				break;
			}
		}

		if (qtyToRemoveRemaining.signum() != 0)
		{
			throw new AdempiereException("Cannot unpick " + qtyToRemoveRemaining);
		}

		//
		pickingCandidateRepository.deletePickingCandidates(candidatesToDelete);

		//
		final I_M_HU hu = handlingUnitsBL.getById(huId);
		if (handlingUnitsBL.isDestroyed(hu))
		{
			final Set<PickingSlotId> pickingSlotIds = PickingCandidate.extractPickingSlotIds(candidates);
			pickingSlotIds.forEach(huPickingSlotBL::releasePickingSlotIfPossible);

			// Usually there will be no candidates, but just to be resilient.
			pickingCandidateRepository.deletePickingCandidates(retrievePickingCandidates());
		}
	}

	private List<PickingCandidate> retrievePickingCandidates()
	{
		return pickingCandidateRepository.getByHUIds(ImmutableList.of(huId));
	}

	private IAllocationDestination createAllocationDestinationAsSourceHUs()
	{
		final Collection<I_M_HU> sourceHUs = sourceHUsRepository.retrieveActualSourceHUs(ImmutableSet.of(huId));
		if (sourceHUs.isEmpty())
		{
			throw new AdempiereException("No source HUs found for M_HU_ID=" + huId);
		}

		return HUListAllocationSourceDestination.of(sourceHUs);
	}

	/**
	 * Create the context with the tread-inherited transaction! Otherwise, the loader won't be able to access the HU's material item and therefore won't load anything!
	 */
	private IAllocationRequest createAllocationRequest(
			@NonNull final PickingCandidate candidate,
			@NonNull final Quantity qtyToRemove)
	{
		final IMutableHUContext huContext = huContextFactory.createMutableHUContextForProcessing();

		return AllocationUtils.builder()
				.setHUContext(huContext)
				.setProduct(productId)
				.setQuantity(qtyToRemove)
				.setDateAsToday()
				.setFromReferencedTableRecord(pickingCandidateRepository.toTableRecordReference(candidate)) // the m_hu_trx_Line coming out of this will reference the picking candidate
				.setForceQtyAllocation(true)
				.create();
	}

	private HUListAllocationSourceDestination createAllocationSourceAsHU()
	{
		final I_M_HU hu = load(huId, I_M_HU.class);

		// we made sure that if the target HU is active, so the source HU also needs to be active. Otherwise, goods would just seem to vanish
		if (!X_M_HU.HUSTATUS_Active.equals(hu.getHUStatus()))
		{
			throw new AdempiereException("not an active HU").setParameter("hu", hu);
		}

		final HUListAllocationSourceDestination source = HUListAllocationSourceDestination.of(hu);
		source.setDestroyEmptyHUs(true);

		return source;
	}
}
