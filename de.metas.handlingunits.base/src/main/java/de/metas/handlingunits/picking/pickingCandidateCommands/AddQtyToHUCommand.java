package de.metas.handlingunits.picking.pickingCandidateCommands;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.uom.api.IUOMConversionContext;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Product;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.IAllocationDestination;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.HUListAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Picking_Candidate;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.picking.IHUPickingSlotBL;
import de.metas.handlingunits.picking.IHUPickingSlotBL.PickingHUsQuery;
import de.metas.handlingunits.picking.PickingCandidateRepository;
import de.metas.inoutcandidate.api.IPackagingDAO;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.logging.LogManager;
import de.metas.quantity.Quantity;
import lombok.Builder;
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

public class AddQtyToHUCommand
{
	private static final Logger logger = LogManager.getLogger(AddQtyToHUCommand.class);

	private final PickingCandidateRepository pickingCandidateRepository;

	private final BigDecimal qtyCU;
	private final int targetHUId;
	private final int pickingSlotId;
	private final int shipmentScheduleId;
	private final boolean isAllowOverdelivery;

	@Builder
	private AddQtyToHUCommand(
			@NonNull final PickingCandidateRepository pickingCandidateRepository,
			@NonNull final BigDecimal qtyCU,
			final boolean isAllowOverdelivery,
			final int targetHUId,
			final int pickingSlotId,
			final int shipmentScheduleId)
	{
		Check.assume(targetHUId > 0, "targetHuId > 0");
		Check.assume(pickingSlotId > 0, "pickingSlotId > 0");
		Check.assume(shipmentScheduleId > 0, "shipmentScheduleId > 0");
		if (qtyCU.signum() <= 0)
		{
			throw new AdempiereException("@Invalid@ @QtyCU@");
		}

		this.pickingCandidateRepository = pickingCandidateRepository;
		this.qtyCU = qtyCU;
		this.targetHUId = targetHUId;
		this.pickingSlotId = pickingSlotId;
		this.shipmentScheduleId = shipmentScheduleId;
		this.isAllowOverdelivery = isAllowOverdelivery;

	}

	/**
	 * @return the quantity that was effectively added. We can only add the quantity that's still left in our source HUs.
	 */
	public Quantity performAndGetQtyPicked()
	{

		final I_M_ShipmentSchedule shipmentSchedule = load(shipmentScheduleId, I_M_ShipmentSchedule.class);
		final boolean overdeliveryError = !isAllowOverdelivery && isOverdelivery();

		if (overdeliveryError)
		{
			throw new AdempiereException("Overdelivery not allowed for shipment schedule").setParameter("ShipmentSchedule", shipmentSchedule);
		}

		final I_M_Product product = shipmentSchedule.getM_Product();

		final I_M_Picking_Candidate candidate = pickingCandidateRepository.getCreateCandidate(targetHUId, pickingSlotId, shipmentScheduleId);

		final HUListAllocationSourceDestination source = createAllocationSource(shipmentSchedule);
		final IAllocationDestination destination = createAllocationDestination(targetHUId);

		final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);

		// create the context with the tread-inherited transaction! Otherwise, the loader won't be able to access the HU's material item and therefore won't load anything!
		final IMutableHUContext huContext = Services.get(IHUContextFactory.class).createMutableHUContextForProcessing();

		final IAllocationRequest request = AllocationUtils.createAllocationRequestBuilder()
				.setHUContext(huContext)
				.setProduct(product)
				.setQuantity(Quantity.of(qtyCU, shipmentScheduleBL.getUomOfProduct(shipmentSchedule)))
				.setDateAsToday()
				.setFromReferencedModel(candidate) // the m_hu_trx_Line coming out of this will reference the picking candidate
				.setForceQtyAllocation(true)
				.create();

		// Load QtyCU to HU(destination)
		final IAllocationResult loadResult = HULoader.of(source, destination)
				.setAllowPartialLoads(true) // don't fail if the the picking staff attempted to to pick more than the TU's capacity
				.setAllowPartialUnloads(true) // don't fail if the the picking staff attempted to to pick more than the shipment schedule's quantity to deliver.
				.load(request);
		logger.info("addQtyToHU done; huId={}, qtyCU={}, loadResult={}", targetHUId, qtyCU, loadResult);

		// Update the candidate
		final Quantity qtyPicked = Quantity.of(loadResult.getQtyAllocated(), request.getC_UOM());
		addQtyToCandidate(candidate, product, qtyPicked);

		return qtyPicked;
	}

	/**
	 * Source - take the preselected sourceHUs
	 *
	 * @param shipmentSchedule
	 * @return
	 */
	private HUListAllocationSourceDestination createAllocationSource(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		final PickingHUsQuery query = PickingHUsQuery.builder()
				.onlyIfAttributesMatchWithShipmentSchedules(true)
				.shipmentSchedules(ImmutableList.of(shipmentSchedule))
				.onlyTopLevelHUs(true)
				.build();

		final List<I_M_HU> sourceHUs = Services.get(IHUPickingSlotBL.class).retrieveAvailableSourceHUs(query);
		final HUListAllocationSourceDestination source = HUListAllocationSourceDestination.of(sourceHUs);
		source.setDestroyEmptyHUs(false); // don't automatically destroy them. we will do that ourselves if the sourceHUs are empty at the time we process our picking candidates

		return source;
	}

	private IAllocationDestination createAllocationDestination(final int huId)
	{
		final I_M_HU hu = InterfaceWrapperHelper.load(huId, I_M_HU.class);

		// we made sure that the source HU is active, so the target HU also needs to be active. Otherwise, goods would just seem to vanish
		if (!X_M_HU.HUSTATUS_Active.equals(hu.getHUStatus()))
		{
			throw new AdempiereException("not an active HU").setParameter("hu", hu);
		}
		final IAllocationDestination destination = HUListAllocationSourceDestination.of(hu);

		return destination;
	}

	private void addQtyToCandidate(
			@NonNull final I_M_Picking_Candidate candidate,
			@NonNull final I_M_Product product,
			@NonNull final Quantity qtyToAdd)
	{
		final Quantity qtyNew;
		if (candidate.getQtyPicked().signum() == 0)
		{
			qtyNew = qtyToAdd;
		}
		else
		{
			final IUOMConversionContext conversionCtx = Services.get(IUOMConversionBL.class).createConversionContext(product);
			final Quantity qty = Quantity.of(candidate.getQtyPicked(), candidate.getC_UOM());
			final Quantity qtyToAddConv = conversionCtx.convertQty(qtyToAdd, qty.getUOM());
			qtyNew = qty.add(qtyToAddConv);
		}

		candidate.setQtyPicked(qtyNew.getQty());
		candidate.setC_UOM(qtyNew.getUOM());
		pickingCandidateRepository.save(candidate);
	}

	private boolean isOverdelivery()
	{
		final I_M_ShipmentSchedule shipmentSchedule = load(shipmentScheduleId, I_M_ShipmentSchedule.class);
		final BigDecimal qtyPickedPlanned = Services.get(IPackagingDAO.class).retrieveQtyPickedPlanned(shipmentSchedule);
		final BigDecimal qtytoDeliver =  shipmentSchedule.getQtyToDeliver().subtract(qtyPickedPlanned); 

		return qtyCU.compareTo(qtytoDeliver) 
	}
}
