package de.metas.handlingunits.picking.pickingCandidateCommands;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Product;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

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
import de.metas.handlingunits.model.I_M_Picking_Candidate;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.picking.PickingCandidateService.RemoveQtyFromHURequest;
import de.metas.handlingunits.sourcehu.HuId2SourceHUsService;
import de.metas.handlingunits.picking.PickingCandidateRepository;
import de.metas.logging.LogManager;
import de.metas.quantity.Quantity;
import lombok.NonNull;

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

public class RemoveQtyFromHU
{
	private static final Logger logger = LogManager.getLogger(RemoveQtyFromHU.class);

	private final HuId2SourceHUsService sourceHUsRepository;

	private final PickingCandidateRepository pickingCandidateRepository;

	public RemoveQtyFromHU(
			@NonNull final HuId2SourceHUsService sourceHUsRepository,
			@NonNull final PickingCandidateRepository pickingCandidateRepository)
	{
		this.sourceHUsRepository = sourceHUsRepository;
		this.pickingCandidateRepository = pickingCandidateRepository;
	}

	public void perform(@NonNull final RemoveQtyFromHURequest removeQtyFromHURequest)
	{
		final Collection<I_M_HU> sourceHUs = sourceHUsRepository.retrieveActualSourceHUs(ImmutableList.of(removeQtyFromHURequest.getHuId()));
		removeQtyFromHU0(removeQtyFromHURequest, sourceHUs);
	}

	/**
	 * 
	 * @param removeQtyFromHURequest
	 * @param sourceHUs the original source HUs to which the given quantity is returned.
	 */
	private void removeQtyFromHU0(
			@NonNull final RemoveQtyFromHURequest removeQtyFromHURequest,
			@NonNull final Collection<I_M_HU> sourceHUs)
	{
		final BigDecimal qtyCU = removeQtyFromHURequest.getQtyCU();
		if (qtyCU.signum() <= 0)
		{
			throw new AdempiereException("@Invalid@ @QtyCU@");
		}

		final int huId = removeQtyFromHURequest.getHuId();
		final I_M_HU hu = load(huId, I_M_HU.class);
		
		final HUListAllocationSourceDestination source = createAllocationSource(hu);
		final IAllocationDestination destination = HUListAllocationSourceDestination.of(sourceHUs);

		final List<I_M_Picking_Candidate> candidates = Services.get(IQueryBL.class).createQueryBuilder(I_M_Picking_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Picking_Candidate.COLUMN_M_HU_ID, huId)
				.create()
				.list();

		BigDecimal qtyAllocatedSum = BigDecimal.ZERO;
		final I_M_Product product = load(removeQtyFromHURequest.getProductId(), I_M_Product.class);

		for (final I_M_Picking_Candidate candidate : candidates)
		{
			final IAllocationRequest request = createAllocationRequest(qtyCU, product, candidate);

			// Load QtyCU to HU(destination)
			final IAllocationResult loadResult = HULoader.of(source, destination)
					.setAllowPartialLoads(true) // don't fail if the the picking staff attempted to to pick more than the TU's capacity
					.setAllowPartialUnloads(true) // don't fail if the the picking staff attempted to to pick more than the shipment schedule's quantity to deliver.
					.load(request);
			qtyAllocatedSum = qtyAllocatedSum.add(loadResult.getQtyAllocated());
			logger.info("addQtyToHU done; huId={}, qtyCU={}, loadResult={}", huId, qtyCU, loadResult);

			if (qtyAllocatedSum.compareTo(qtyCU) >= 0)
			{
				break;
			}
		}

		if (Services.get(IHandlingUnitsBL.class).isDestroyed(hu))
		{
			pickingCandidateRepository.deletePickingCandidate(huId);
		}
	}

	/**
	 * Create the context with the tread-inherited transaction! Otherwise, the loader won't be able to access the HU's material item and therefore won't load anything!
	 * 
	 * @param qtyCU
	 * @param product
	 * @param candidate
	 * @return
	 */
	private IAllocationRequest createAllocationRequest(
			@NonNull final BigDecimal qtyCU,
			@NonNull final I_M_Product product,
			@NonNull final I_M_Picking_Candidate candidate)
	{
		final IMutableHUContext huContext = Services.get(IHUContextFactory.class).createMutableHUContextForProcessing();

		final IAllocationRequest request = AllocationUtils.createAllocationRequestBuilder()
				.setHUContext(huContext)
				.setProduct(product)
				.setQuantity(Quantity.of(qtyCU, product.getC_UOM()))
				.setDateAsToday()
				.setFromReferencedModel(candidate) // the m_hu_trx_Line coming out of this will reference the picking candidate
				.setForceQtyAllocation(true)
				.create();
		return request;
	}

	private HUListAllocationSourceDestination createAllocationSource(@NonNull final I_M_HU hu)
	{
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
