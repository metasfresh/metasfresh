package de.metas.handlingunits.picking;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import org.adempiere.ad.dao.ICompositeQueryUpdater;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.uom.api.IUOMConversionContext;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.IHUCapacityDefinition;
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
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.model.I_M_Source_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.model.X_M_Picking_Candidate;
import de.metas.handlingunits.picking.IHUPickingSlotBL.PickingHUsQuery;
import de.metas.handlingunits.snapshot.IHUSnapshotDAO;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.logging.LogManager;
import de.metas.quantity.Quantity;
import lombok.Builder.Default;
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
	private static final Logger logger = LogManager.getLogger(PickingCandidateCommand.class);

	private final SourceHUsRepository sourceHUsRepository;

	public PickingCandidateCommand(@NonNull final SourceHUsRepository sourceHUsRepository)
	{
		this.sourceHUsRepository = sourceHUsRepository;
	}

	public void addHUToPickingSlot(final int huId, final int pickingSlotId, final int shipmentScheduleId)
	{
		// Get HU's single product storage
		final BigDecimal qty;
		final I_C_UOM uom;
		{
			final I_M_HU hu = load(huId, I_M_HU.class);
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

		final I_M_Picking_Candidate pickingCandidate = getCreateCandidate(huId, pickingSlotId, shipmentScheduleId);
		pickingCandidate.setQtyPicked(qty);
		pickingCandidate.setC_UOM(uom);
		save(pickingCandidate);
	}

	/**
	 * 
	 * @param qtyCU
	 * @param huId
	 * @param pickingSlotId
	 * @param shipmentScheduleId
	 * 
	 * @return the quantity that was effectively added. We can only add the quantity that's still left in our source HUs.
	 */
	public Quantity addQtyToHU(@NonNull final AddQtyToHURequest addQtyToHURequest)
	{
		final BigDecimal qtyCU = addQtyToHURequest.getQtyCU();
		if (qtyCU.signum() <= 0)
		{
			throw new AdempiereException("@Invalid@ @QtyCU@");
		}

		final int shipmentScheduleId = addQtyToHURequest.getShipmentScheduleId();
		final int pickingSlotId = addQtyToHURequest.getPickingSlotId();
		final int huId = addQtyToHURequest.getHuId();

		final I_M_ShipmentSchedule shipmentSchedule = load(shipmentScheduleId, I_M_ShipmentSchedule.class);
		final I_M_Product product = shipmentSchedule.getM_Product();

		final I_M_Picking_Candidate candidate = getCreateCandidate(huId, pickingSlotId, shipmentScheduleId);

		//
		// Source - take the preselected sourceHUs
		final HUListAllocationSourceDestination source;
		{
			final PickingHUsQuery query = PickingHUsQuery.builder()
					.considerAttributes(true)
					.shipmentSchedules(ImmutableList.of(shipmentSchedule))
					.onlyTopLevelHUs(true)
					.build();
			final List<I_M_HU> sourceHUs = Services.get(IHUPickingSlotBL.class).retrieveAvailableSourceHUs(query);
			source = HUListAllocationSourceDestination.of(sourceHUs);
			source.setDestroyEmptyHUs(false); // don't automatically destroy them. we will do that ourselves if the sourceHUs are empty at the time we process our picking candidates
		}

		//
		// Destination: HU
		final IAllocationDestination destination;
		{
			final I_M_HU hu = InterfaceWrapperHelper.load(huId, I_M_HU.class);
			// we made sure that the source HU is active, so the target HU also needs to be active. Otherwise, goods would just seem to vanish
			if (!X_M_HU.HUSTATUS_Active.equals(hu.getHUStatus()))
			{
				throw new AdempiereException("not an active HU").setParameter("hu", hu);
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
				.setFromReferencedModel(candidate) // the m_hu_trx_Line coming out of this will reference the picking candidate
				.setForceQtyAllocation(true)
				.create();

		//
		// Load QtyCU to HU(destination)
		final IAllocationResult loadResult = HULoader.of(source, destination)
				.setAllowPartialLoads(true) // don't fail if the the picking staff attempted to to pick more than the TU's capacity
				.setAllowPartialUnloads(true) // don't fail if the the picking staff attempted to to pick more than the shipment schedule's quantity to deliver.
				.load(request);
		logger.info("addQtyToHU done; huId={}, qtyCU={}, loadResult={}", huId, qtyCU, loadResult);

		//
		// Update the candidate
		final Quantity qtyPicked = Quantity.of(loadResult.getQtyAllocated(), request.getC_UOM());
		addQtyToCandidate(candidate, product, qtyPicked);

		return qtyPicked;
	}

	@lombok.Value
	@lombok.Builder
	public static final class AddQtyToHURequest
	{
		@NonNull
		@Default
		BigDecimal qtyCU = Quantity.QTY_INFINITE;

		@NonNull
		Integer huId;

		@NonNull
		Integer pickingSlotId;

		@NonNull
		Integer shipmentScheduleId;
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
		InterfaceWrapperHelper.save(candidate);
	}

	public void removeQtyFromHU(@NonNull final RemoveQtyFromHURequest removeQtyFromHURequest)
	{
		final Collection<I_M_HU> sourceHUs = sourceHUsRepository.retrieveSourceHUsViaTracing(ImmutableList.of(removeQtyFromHURequest.getHuId()));
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

		//
		// Source
		final HUListAllocationSourceDestination source;
		{
			final I_M_HU hu = InterfaceWrapperHelper.load(huId, I_M_HU.class);
			// we made sure that the source HU is active, so the target HU also needs to be active. Otherwise, goods would just seem to vanish
			if (!X_M_HU.HUSTATUS_Active.equals(hu.getHUStatus()))
			{
				throw new AdempiereException("not an active HU").setParameter("hu", hu);
			}
			source = HUListAllocationSourceDestination.of(hu);
			source.setDestroyEmptyHUs(true);
		}

		//
		// Destination: HU
		final IAllocationDestination destination = HUListAllocationSourceDestination.of(sourceHUs);

		//
		// Request
		final I_M_Product product = load(removeQtyFromHURequest.getProductId(), I_M_Product.class);

		final List<I_M_Picking_Candidate> candidates = Services.get(IQueryBL.class).createQueryBuilder(I_M_Picking_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Picking_Candidate.COLUMN_M_HU_ID, huId)
				.create()
				.list();

		BigDecimal qtyAllocatedSum = BigDecimal.ZERO;
		for (final I_M_Picking_Candidate candidate : candidates)
		{

			// create the context with the tread-inherited transaction! Otherwise, the loader won't be able to access the HU's material item and therefore won't load anything!
			final IMutableHUContext huContext = Services.get(IHUContextFactory.class).createMutableHUContextForProcessing();

			final IAllocationRequest request = AllocationUtils.createAllocationRequestBuilder()
					.setHUContext(huContext)
					.setProduct(product)
					.setQuantity(Quantity.of(qtyCU, product.getC_UOM()))
					.setDateAsToday()
					.setFromReferencedModel(candidate) // the m_hu_trx_Line coming out of this will reference the picking candidate
					.setForceQtyAllocation(true)
					.create();

			//
			// Load QtyCU to HU(destination)
			final IAllocationResult loadResult = HULoader.of(source, destination)
					.setAllowPartialLoads(true) // don't fail if the the picking staff attempted to to pick more than the TU's capacity
					.setAllowPartialUnloads(true) // don't fail if the the picking staff attempted to to pick more than the shipment schedule's quantity to deliver.
					.load(request);
			logger.info("addQtyToHU done; huId={}, qtyCU={}, loadResult={}", huId, qtyCU, loadResult);
			qtyAllocatedSum = qtyAllocatedSum.add(loadResult.getQtyAllocated());

			if (qtyAllocatedSum.compareTo(qtyCU) >= 0)
			{
				break;
			}
		}

		final I_M_HU hu = InterfaceWrapperHelper.load(huId, I_M_HU.class);
		if (Services.get(IHandlingUnitsBL.class).isDestroyed(hu))
		{
			deletePickingCandidate(huId);
		}
	}

	@lombok.Value
	@lombok.Builder
	public static final class RemoveQtyFromHURequest
	{
		@NonNull
		@Default
		BigDecimal qtyCU = IHUCapacityDefinition.INFINITY;

		@NonNull
		Integer huId;

		@NonNull
		Integer pickingSlotId;

		@NonNull
		Integer productId;
	}

	private I_M_Picking_Candidate getCreateCandidate(final int huId, final int pickingSlotId, final int shipmentScheduleId)
	{
		I_M_Picking_Candidate pickingCandidate = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Picking_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Picking_Candidate.COLUMN_M_PickingSlot_ID, pickingSlotId)
				.addEqualsFilter(I_M_Picking_Candidate.COLUMNNAME_M_HU_ID, huId)
				.addEqualsFilter(I_M_Picking_Candidate.COLUMNNAME_M_ShipmentSchedule_ID, shipmentScheduleId)
				.create()
				.firstOnly(I_M_Picking_Candidate.class);

		if (pickingCandidate == null)
		{
			pickingCandidate = InterfaceWrapperHelper.newInstance(I_M_Picking_Candidate.class);
			pickingCandidate.setM_ShipmentSchedule_ID(shipmentScheduleId);
			pickingCandidate.setM_PickingSlot_ID(pickingSlotId);
			pickingCandidate.setM_HU_ID(huId);
			pickingCandidate.setQtyPicked(BigDecimal.ZERO); // will be updated later
			pickingCandidate.setC_UOM(null); // will be updated later
			save(pickingCandidate);

			logger.info("Created new M_Picking_Candidate for M_HU_ID={}, M_PickingSlot_ID={}, M_ShipmentSchedule_ID={}; candidate={}",
					huId, pickingSlotId, shipmentScheduleId, pickingCandidate);
		}

		return pickingCandidate;
	}

	public void removeHUFromPickingSlot(final int huId)
	{
		// final Collection<I_M_HU> sourceHUs = sourceHUsRepository.retrieveSourceHUsViaTracing(ImmutableList.of(removeQtyFromHURequest.getHuId()));
		// if (sourceHUs.isEmpty())
		// {
		deletePickingCandidate(huId);
		// return;
		// }
		//
		// removeQtyFromHU0(removeQtyFromHURequest, sourceHUs);
	}

	void deletePickingCandidate(final int huId)
	{
		Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Picking_Candidate.class)

				.addEqualsFilter(I_M_Picking_Candidate.COLUMNNAME_M_HU_ID, huId)
				.create()
				.delete();
	}

	/**
	 * For the given {@code huIds}, this method does two things:
	 * <ul>
	 * <li>Retrieves the source HUs (if any) of the the given {@code huIds} and if they are empty creates a snapshot and destroys them</li>
	 * <li>selects the {@link I_M_Picking_Candidate}s that reference those HUs
	 * and have {@code status == 'IP'} (in progress) and updates them to {@code status='PR'} (processed).
	 * No model interceptors etc will be fired.</li>
	 * </ul>
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

		final Collection<I_M_HU> sourceHuIds = sourceHUsRepository.retrieveSourceHUsViaTracing(huIds);
		destroyEmptySourceHUs(sourceHuIds);

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

	void destroyEmptySourceHUs(final Collection<I_M_HU> sourceHus)
	{
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		final IHUSnapshotDAO huSnapshotDAO = Services.get(IHUSnapshotDAO.class);

		// clean up and unselect used up source HUs
		for (final I_M_HU sourceHu : sourceHus)
		{
			if (!handlingUnitsBL.getStorageFactory().getStorage(sourceHu).isEmpty())
			{
				continue;
			}

			final String snapshotId = huSnapshotDAO.createSnapshot()
					.setContext(PlainContextAware.newWithThreadInheritedTrx())
					.addModel(sourceHu)
					.createSnapshots()
					.getSnapshotId();

			handlingUnitsBL.destroyIfEmptyStorage(sourceHu);
			Check.errorUnless(handlingUnitsBL.isDestroyed(sourceHu), "We invoked IHandlingUnitsBL.destroyIfEmptyStorage on an HU with empty storage, but its not destroyed; hu={}", sourceHu);

			sourceHuDestroyed(sourceHu.getM_HU_ID(), snapshotId);
			logger.info("Source M_HU with M_HU_ID={} is now destroyed", sourceHu.getM_HU_ID());
		}
	}

	public int setCandidatesInProgress(@NonNull final List<Integer> huIds)
	{
		if (huIds.isEmpty())
		{
			return 0;
		}

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IHUSnapshotDAO huSnapshotDAO = Services.get(IHUSnapshotDAO.class);

		final Collection<I_M_HU> sourceHUs = sourceHUsRepository.retrieveSourceHUsViaTracing(huIds);
		for (final I_M_HU sourceHU : sourceHUs)
		{
			if (!Services.get(IHandlingUnitsBL.class).isDestroyed(sourceHU))
			{
				continue;
			}
			final I_M_Source_HU sourceHuRecord = queryBL.createQueryBuilder(I_M_Source_HU.class)
					.addEqualsFilter(I_M_Source_HU.COLUMN_M_HU_ID, sourceHU.getM_HU_ID())
					.create()
					.firstOnly(I_M_Source_HU.class);
			if (sourceHuRecord == null)
			{
				continue; // shouldn't happen
			}

			huSnapshotDAO.restoreHUs()
					.addModel(sourceHU)
					.setContext(PlainContextAware.newWithThreadInheritedTrx())
					.setDateTrx(SystemTime.asDate())
					.setSnapshotId(sourceHuRecord.getPreDestroy_Snapshot_UUID())
					.restoreFromSnapshot();

			sourceHuRecord.setPreDestroy_Snapshot_UUID(null);
			save(sourceHuRecord);
		}

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

	public I_M_Source_HU addSourceHu(final int huId)
	{
		final I_M_Source_HU sourceHU = newInstance(I_M_Source_HU.class);
		sourceHU.setM_HU_ID(huId);
		save(sourceHU);

		logger.info("Created one M_Source_HU record for M_HU_ID={}", huId);
		return sourceHU;
	}

	public void sourceHuDestroyed(
			final int huId,
			@NonNull final String huSnapShotId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQuery<I_M_Source_HU> query = queryBL.createQueryBuilder(I_M_Source_HU.class)
				.addEqualsFilter(I_M_Source_HU.COLUMN_M_HU_ID, huId)
				.create();

		final ICompositeQueryUpdater<I_M_Source_HU> updater = queryBL.createCompositeQueryUpdater(I_M_Source_HU.class)
				.addSetColumnValue(I_M_Source_HU.COLUMNNAME_PreDestroy_Snapshot_UUID, huSnapShotId);

		query.update(updater);
	}

	/**
	 * 
	 * @param huId
	 * @return {@code true} if anything was deleted.
	 */
	public boolean removeSourceHu(final int huId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final int deleteCount = queryBL.createQueryBuilder(I_M_Source_HU.class)
				.addEqualsFilter(I_M_Source_HU.COLUMN_M_HU_ID, huId)
				.create()
				.delete();

		return deleteCount > 0;
	}
}
