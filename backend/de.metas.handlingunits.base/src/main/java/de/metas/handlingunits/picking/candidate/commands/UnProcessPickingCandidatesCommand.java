package de.metas.handlingunits.picking.candidate.commands;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.HUListAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.PickingCandidate;
import de.metas.handlingunits.picking.PickingCandidateId;
import de.metas.handlingunits.picking.PickingCandidateRepository;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UnProcessPickingCandidatesCommand
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);

	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IHUShipmentScheduleBL huShipmentScheduleBL = Services.get(IHUShipmentScheduleBL.class);
	private final PickingCandidateRepository pickingCandidateRepository;

	private final ImmutableSet<PickingCandidateId> pickingCandidateIds;

	private final Map<ShipmentScheduleId, I_M_ShipmentSchedule> shipmentSchedulesCache = new HashMap<>();

	@Builder
	private UnProcessPickingCandidatesCommand(
			@NonNull final PickingCandidateRepository pickingCandidateRepository,
			@NonNull @Singular final Set<PickingCandidateId> pickingCandidateIds)
	{
		Check.assumeNotEmpty(pickingCandidateIds, "pickingCandidateIds is not empty");
		this.pickingCandidateRepository = pickingCandidateRepository;
		this.pickingCandidateIds = ImmutableSet.copyOf(pickingCandidateIds);
	}

	public UnProcessPickingCandidatesResult execute()
	{
		final List<PickingCandidate> pickingCandidates = pickingCandidateRepository.getByIds(pickingCandidateIds);
		pickingCandidates.forEach(this::assertEligibleForProcessing);

		trxManager.runInThreadInheritedTrx(() -> pickingCandidates.forEach(this::processInTrx));

		return UnProcessPickingCandidatesResult.builder()
				.pickingCandidates(pickingCandidates)
				.build();
	}

	private void assertEligibleForProcessing(final PickingCandidate pickingCandidate)
	{
		pickingCandidate.assertProcessed();
	}

	private void processInTrx(@NonNull final PickingCandidate pickingCandidate)
	{
		if (pickingCandidate.isRejectedToPick())
		{
			// TODO: impl
			throw new AdempiereException("Unprocessing not supported");
		}
		else if (pickingCandidate.getPickFrom().isPickFromHU())
		{
			unpickHU(pickingCandidate);
			deleteShipmentScheduleQtyPickedRecords(pickingCandidate);
		}
		else if (pickingCandidate.getPickFrom().isPickFromPickingOrder())
		{
			// TODO: impl
			throw new AdempiereException("Unprocessing not supported");
		}
		else
		{
			throw new AdempiereException("Unknow " + pickingCandidate.getPickFrom());
		}

		pickingCandidate.changeStatusToDraft();
		pickingCandidateRepository.save(pickingCandidate);
	}

	private void unpickHU(final PickingCandidate pickingCandidate)
	{
		final HuId packedToHUId = pickingCandidate.getPackedToHuId();
		if (packedToHUId == null)
		{
			return;
		}

		final I_M_ShipmentSchedule shipmentSchedule = getShipmentScheduleById(pickingCandidate.getShipmentScheduleId());
		final ProductId productId = ProductId.ofRepoId(shipmentSchedule.getM_Product_ID());

		final Quantity qtyPicked = pickingCandidate.getQtyPicked();

		final IHUContext huContext = huContextFactory.createMutableHUContextForProcessing();

		HULoader.builder()
				.source(HUListAllocationSourceDestination.ofHUId(packedToHUId).setDestroyEmptyHUs(true))
				.destination(HUListAllocationSourceDestination.ofHUId(pickingCandidate.getPickFrom().getHuId()))
				.allowPartialUnloads(false)
				.allowPartialLoads(false)
				.forceLoad(true)
				.load(AllocationUtils.builder()
						.setHUContext(huContext)
						.setProduct(productId)
						.setQuantity(qtyPicked)
						.setFromReferencedTableRecord(pickingCandidate.getId().toTableRecordReference())
						.setForceQtyAllocation(true)
						.create());

		final I_M_HU packedToHU = handlingUnitsBL.getById(packedToHUId);
		if (!huContext.getHUStorageFactory().getStorage(packedToHU).isEmpty())
		{
			throw new AdempiereException("Cannot unprocess because the actual picked HU was alterned in meantime");
		}

		// HULoader.of(pickFromSource, packToDestination).load(request);
		// final I_M_HU packedToHU = packToDestination.getSingleCreatedHU()
		// 		.orElseThrow(() -> new AdempiereException("Nothing packed for " + pickingCandidate));
		//
		// addShipmentScheduleQtyPickedAndUpdateHU(shipmentSchedule, packedToHU, qtyPicked, huContext);
		//
		// huContext.flush();
		//
		// return HuId.ofRepoId(packedToHU.getM_HU_ID());
	}

	private I_M_ShipmentSchedule getShipmentScheduleById(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		return shipmentSchedulesCache.computeIfAbsent(shipmentScheduleId, huShipmentScheduleBL::getById);
	}

	private void deleteShipmentScheduleQtyPickedRecords(@NonNull final PickingCandidate pickingCandidate)
	{
		final HuId packedToHuId = pickingCandidate.getPackedToHuId();
		if (packedToHuId == null)
		{
			return;
		}

		huShipmentScheduleBL.deleteByTopLevelHUAndShipmentScheduleId(packedToHuId, pickingCandidate.getShipmentScheduleId());
	}

}
