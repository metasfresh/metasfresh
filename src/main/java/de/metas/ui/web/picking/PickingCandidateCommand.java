package de.metas.ui.web.picking;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.ad.dao.ICompositeQueryUpdater;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.uom.api.IUOMConversionContext;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHUPickingSlotBL;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.IAllocationDestination;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.GenericAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HUListAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Picking_Candidate;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.model.X_M_Picking_Candidate;
import de.metas.handlingunits.shipmentschedule.api.impl.ShipmentScheduleQtyPickedProductStorage;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IProductStorage;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.logging.LogManager;
import de.metas.quantity.Quantity;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
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
@Service
public class PickingCandidateCommand
{
	private static final Logger logger = LogManager.getLogger(PickingHUsRepository.class);

	public void addHUToPickingSlot(final int huId, final int pickingSlotId, final int shipmentScheduleId)
	{
		// Get HU's single product storage
		final BigDecimal qty;
		final I_C_UOM uom;
		{
			final I_M_HU hu = InterfaceWrapperHelper.load(huId, I_M_HU.class);
			final List<IHUProductStorage> productStorages = Services.get(IHUContextFactory.class)
					.createMutableHUContext()
					.getHUStorageFactory()
					.getStorage(hu)
					.getProductStorages();
			if (productStorages.isEmpty())
			{
				// Allow empty storage. That's the case when we are adding a newly created HU
				qty = BigDecimal.ZERO;
				uom = null;
				// throw new AdempiereException("HU is empty").setParameter("hu", hu);
			}
			else if (productStorages.size() > 1)
			{
				throw new AdempiereException("HU has more than one product").setParameter("productStorages", productStorages);
			}
			else
			{
				final IHUProductStorage productStorage = productStorages.get(0);
				qty = productStorage.getQty();
				uom = productStorage.getC_UOM();
			}

		}

		final I_M_Picking_Candidate pickingCandidatePO = InterfaceWrapperHelper.newInstance(I_M_Picking_Candidate.class);
		pickingCandidatePO.setM_ShipmentSchedule_ID(shipmentScheduleId);
		pickingCandidatePO.setM_PickingSlot_ID(pickingSlotId);
		pickingCandidatePO.setM_HU_ID(huId);
		pickingCandidatePO.setQtyPicked(qty);
		pickingCandidatePO.setC_UOM(uom);
		InterfaceWrapperHelper.save(pickingCandidatePO);
	}

	/**
	 * 
	 * @param qtyCU
	 * @param huId
	 * @param pickingSlotId
	 * @param shipmentScheduleId
	 * @return the quantity that was effectively added. As determined by {@link ShipmentScheduleQtyPickedProductStorage}, we can only add the quantity that is still open according to the underlyiung shipment schedule.
	 */
	public Quantity addQtyToHU(
			@NonNull final BigDecimal qtyCU,
			final int huId,
			final int pickingSlotId,
			final int shipmentScheduleId)
	{
		if (qtyCU.signum() <= 0)
		{
			throw new AdempiereException("@Invalid@ @QtyCU@");
		}

		final I_M_ShipmentSchedule shipmentSchedule = InterfaceWrapperHelper.load(shipmentScheduleId, I_M_ShipmentSchedule.class);
		final I_M_Product product = shipmentSchedule.getM_Product();

		final I_M_Picking_Candidate candidate = getCreateCandidate(huId, pickingSlotId, shipmentScheduleId);

		//
		// Source
		final IAllocationSource source;
		{
			final IProductStorage storage = new ShipmentScheduleQtyPickedProductStorage(shipmentSchedule);
			source = new GenericAllocationSourceDestination(storage, candidate);
		}

		//
		// Destination: HU
		final IAllocationDestination destination;
		{
			final I_M_HU hu = InterfaceWrapperHelper.load(huId, I_M_HU.class);
			if (!X_M_HU.HUSTATUS_Planning.equals(hu.getHUStatus()))
			{
				throw new AdempiereException("not a planning HU").setParameter("hu", hu);
			}
			destination = HUListAllocationSourceDestination.of(hu);
		}

		//
		// Request
		final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);

		// create the context with the tread-inherited transaction! Otherwise, the loader won't be able to access the HU's material item and therefore won't load anything!
		final IMutableHUContext huContext = Services.get(IHUContextFactory.class).createMutableHUContextForProcessing();

		final IAllocationRequest request = AllocationUtils.createAllocationRequestBuilder()
				.setHUContext(huContext)
				.setProduct(product)
				.setQuantity(Quantity.of(qtyCU, shipmentScheduleBL.getC_UOM(shipmentSchedule)))
				.setDateAsToday()
				.setFromReferencedModel(candidate) // the m_hu_trx_Line coming out of this will reference the HU_trx_Candidate
				.setForceQtyAllocation(true)
				.create();

		//
		// Load QtyCU to HU(destination)
		final IAllocationResult loadResult = HULoader.of(source, destination)
				.setAllowPartialLoads(true) // don't fail if the the picking staff attempted to to pick more than the TU's capacity
				.setAllowPartialUnloads(true) // don't fail if the the picking staff attempted to to pick more than the shipment schedule's quantity to deliver.
				.load(request);
		logger.debug("addQtyToHU done; huId={}, qtyCU={}, loadResult={}", huId, qtyCU, loadResult);

		//
		// Update the candidate
		final Quantity qtyPicked = Quantity.of(loadResult.getQtyAllocated(), request.getC_UOM());
		addQtyToCandidate(candidate, product, qtyPicked);

		return qtyPicked;
	}

	private I_M_Picking_Candidate getCreateCandidate(final int huId, final int pickingSlotId, final int shipmentScheduleId)
	{
		I_M_Picking_Candidate pickingCandidatePO = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Picking_Candidate.class)
				.addEqualsFilter(I_M_Picking_Candidate.COLUMN_M_PickingSlot_ID, pickingSlotId)
				.addEqualsFilter(I_M_Picking_Candidate.COLUMNNAME_M_HU_ID, huId)
				.addEqualsFilter(I_M_Picking_Candidate.COLUMNNAME_M_ShipmentSchedule_ID, shipmentScheduleId)
				.create()
				.firstOnly(I_M_Picking_Candidate.class);
		if (pickingCandidatePO == null)
		{
			pickingCandidatePO = InterfaceWrapperHelper.newInstance(I_M_Picking_Candidate.class);
			pickingCandidatePO.setM_ShipmentSchedule_ID(shipmentScheduleId);
			pickingCandidatePO.setM_PickingSlot_ID(pickingSlotId);
			pickingCandidatePO.setM_HU_ID(huId);
			pickingCandidatePO.setQtyPicked(BigDecimal.ZERO); // will be updated later
			pickingCandidatePO.setC_UOM(null); // will be updated later
			InterfaceWrapperHelper.save(pickingCandidatePO);
		}

		return pickingCandidatePO;
	}

	public void removeHUFromPickingSlot(final int huId, final int pickingSlotId)
	{
		Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Picking_Candidate.class)
				.addEqualsFilter(I_M_Picking_Candidate.COLUMN_M_PickingSlot_ID, pickingSlotId)
				.addEqualsFilter(I_M_Picking_Candidate.COLUMNNAME_M_HU_ID, huId)
				.create()
				.delete();
	}

	private void addQtyToCandidate(@NonNull final I_M_Picking_Candidate candidate, @NonNull final I_M_Product product, @NonNull final Quantity qtyToAdd)
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
		InterfaceWrapperHelper.save(candidate);
	}

	/**
	 * For the given {@code huIds}, this method selects the {@link I_M_Picking_Candidate}s that reference those HUs
	 * and have {@code status == 'IP'} (in progress) and updates them to {@code status='PR'} (processed).
	 * No model interceptors etc will be fired.
	 * <p>
	 * Note: no model interceptors etc are fired when this method is called.
	 * 
	 * @param huIds
	 * 
	 * @return the number of updated {@link I_M_Picking_Candidate}s
	 */
	public int setCandidatesProcessed(@NonNull final List<Integer> huIds)
	{
		if (huIds.isEmpty())
		{
			return 0;
		}
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQuery<I_M_Picking_Candidate> query = queryBL.createQueryBuilder(I_M_Picking_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Picking_Candidate.COLUMNNAME_Status, X_M_Picking_Candidate.STATUS_IP)
				.addInArrayFilter(I_M_Picking_Candidate.COLUMNNAME_M_HU_ID, huIds)
				.create();

		final ICompositeQueryUpdater<I_M_Picking_Candidate> updater = queryBL.createCompositeQueryUpdater(I_M_Picking_Candidate.class)
				.addSetColumnValue(I_M_Picking_Candidate.COLUMNNAME_Status, X_M_Picking_Candidate.STATUS_PR);

		return query.updateDirectly(updater);
	}

	public int setCandidatesInProgress(@NonNull final List<Integer> huIds)
	{
		if (huIds.isEmpty())
		{
			return 0;
		}
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQuery<I_M_Picking_Candidate> query = queryBL.createQueryBuilder(I_M_Picking_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Picking_Candidate.COLUMNNAME_Status, X_M_Picking_Candidate.STATUS_PR)
				.addInArrayFilter(I_M_Picking_Candidate.COLUMNNAME_M_HU_ID, huIds)
				.create();

		final ICompositeQueryUpdater<I_M_Picking_Candidate> updater = queryBL.createCompositeQueryUpdater(I_M_Picking_Candidate.class)
				.addSetColumnValue(I_M_Picking_Candidate.COLUMNNAME_Status, X_M_Picking_Candidate.STATUS_IP);

		return query.updateDirectly(updater);
	}

	/**
	 * For the given {@code shipmentScheduleIds}, this method selects the {@link I_M_Picking_Candidate}s that reference those HUs
	 * and have {@code status == 'PR'} (processed) and updates them to {@code status='CL'} (closed)<br>
	 * <b>and</b> adds the respective candidates to the picking slot queue.<br>
	 * Closed candidates are not shown in the webui's picking view.
	 * <p>
	 * Note: no model interceptors etc are fired when this method is called.
	 * 
	 * @param huIds
	 * 
	 * @return the number of updated {@link I_M_Picking_Candidate}s
	 */
	public void setCandidatesClosed(@NonNull final List<Integer> shipmentScheduleIds)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQuery<I_M_Picking_Candidate> query = queryBL.createQueryBuilder(I_M_Picking_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Picking_Candidate.COLUMNNAME_Status, X_M_Picking_Candidate.STATUS_PR)
				.addInArrayFilter(I_M_Picking_Candidate.COLUMN_M_ShipmentSchedule_ID, shipmentScheduleIds)
				.create();

		final ICompositeQueryUpdater<I_M_Picking_Candidate> updater = queryBL.createCompositeQueryUpdater(I_M_Picking_Candidate.class)
				.addSetColumnValue(I_M_Picking_Candidate.COLUMNNAME_Status, X_M_Picking_Candidate.STATUS_CL);
		query.updateDirectly(updater);
		// note that we only closed "processed" candidates. what's still open shall stay open.

		final List<I_M_Picking_Candidate> pickingCandidates = query.list();
		for (final I_M_Picking_Candidate pickingCandidate : pickingCandidates)
		{
			final IHUPickingSlotBL huPickingSlotBL = Services.get(IHUPickingSlotBL.class);
			huPickingSlotBL.addToPickingSlotQueue(pickingCandidate.getM_PickingSlot(), pickingCandidate.getM_HU());
		}

	}
}
