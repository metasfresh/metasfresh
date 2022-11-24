package de.metas.handlingunits.picking.candidate.commands;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.picking.PickingCandidate;
import de.metas.handlingunits.picking.PickingCandidateId;
import de.metas.handlingunits.picking.PickingCandidateRepository;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.PlainContextAware;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class UnProcessPickingCandidatesCommand
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IHUShipmentScheduleBL huShipmentScheduleBL = Services.get(IHUShipmentScheduleBL.class);
	private final PickingCandidateRepository pickingCandidateRepository;

	private final ImmutableSet<PickingCandidateId> pickingCandidateIds;

	private final HashMap<ShipmentScheduleId, I_M_ShipmentSchedule> shipmentSchedulesCache = new HashMap<>();

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
			processInTrx_unpackHU(pickingCandidate);
		}
		else if (pickingCandidate.getPickFrom().isPickFromPickingOrder())
		{
			// TODO: impl
			throw new AdempiereException("Unprocessing not supported");
		}
		else
		{
			throw new AdempiereException("Unknown " + pickingCandidate.getPickFrom());
		}
	}

	private void processInTrx_unpackHU(@NonNull final PickingCandidate pickingCandidate)
	{
		final HuId packedToHUId = pickingCandidate.getPackedToHuId();
		if (packedToHUId == null)
		{
			return;
		}

		final I_M_HU packedToHU = handlingUnitsBL.getById(packedToHUId);
		if (handlingUnitsBL.isDestroyed(packedToHU))
		{
			// shall not happen
			return;
		}

		if (X_M_HU.HUSTATUS_Picked.equals(packedToHU.getHUStatus()))
		{
			handlingUnitsBL.setHUStatus(packedToHU, PlainContextAware.newWithThreadInheritedTrx(), X_M_HU.HUSTATUS_Active);
		}

		pickingCandidate.changeStatusToDraft();
		pickingCandidateRepository.save(pickingCandidate);

		huShipmentScheduleBL.deleteByTopLevelHUAndShipmentScheduleId(packedToHUId, pickingCandidate.getShipmentScheduleId());
	}

	@NonNull
	private ProductId getProductId(final PickingCandidate pickingCandidate)
	{
		final I_M_ShipmentSchedule shipmentSchedule = getShipmentScheduleById(pickingCandidate.getShipmentScheduleId());
		return ProductId.ofRepoId(shipmentSchedule.getM_Product_ID());
	}

	private I_M_ShipmentSchedule getShipmentScheduleById(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		return shipmentSchedulesCache.computeIfAbsent(shipmentScheduleId, huShipmentScheduleBL::getById);
	}
}
