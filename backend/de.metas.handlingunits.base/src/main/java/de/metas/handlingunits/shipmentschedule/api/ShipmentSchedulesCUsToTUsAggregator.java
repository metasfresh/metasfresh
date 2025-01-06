package de.metas.handlingunits.shipmentschedule.api;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.allocation.impl.TULoader;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.shipmentschedule.api.impl.ShipmentScheduleHUTrxListener;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocDAO;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.util.lang.IAutoCloseable;

import java.util.List;
import java.util.Map;
import java.util.Set;

class ShipmentSchedulesCUsToTUsAggregator
{
	@NonNull private final IHUShipmentScheduleBL huShipmentScheduleBL;
	@NonNull private final IShipmentScheduleAllocDAO shipmentScheduleAllocDAO;
	@NonNull private final IHandlingUnitsBL handlingUnitsBL;

	@NonNull final ImmutableSet<ShipmentScheduleId> shipmentScheduleIds;

	@Builder
	private ShipmentSchedulesCUsToTUsAggregator(
			@NonNull final IHUShipmentScheduleBL huShipmentScheduleBL,
			@NonNull final IShipmentScheduleAllocDAO shipmentScheduleAllocDAO,
			@NonNull final IHandlingUnitsBL handlingUnitsBL,
			//
			@NonNull final Set<ShipmentScheduleId> shipmentScheduleIds)
	{
		this.huShipmentScheduleBL = huShipmentScheduleBL;
		this.shipmentScheduleAllocDAO = shipmentScheduleAllocDAO;
		this.handlingUnitsBL = handlingUnitsBL;

		this.shipmentScheduleIds = ImmutableSet.copyOf(shipmentScheduleIds);
	}

	public void execute()
	{
		if (shipmentScheduleIds.isEmpty())
		{
			return;
		}

		final Map<ShipmentScheduleId, I_M_ShipmentSchedule> shipmentSchedulesById = huShipmentScheduleBL.getByIds(shipmentScheduleIds);
		final ImmutableListMultimap<ShipmentScheduleId, I_M_ShipmentSchedule_QtyPicked> qtyPickedRecordsByScheduleId = shipmentScheduleAllocDAO.retrieveNotOnShipmentLineRecordsByScheduleIds(shipmentSchedulesById.keySet(), I_M_ShipmentSchedule_QtyPicked.class);

		for (final ShipmentScheduleId shipmentScheduleId : qtyPickedRecordsByScheduleId.keySet())
		{
			final I_M_ShipmentSchedule shipmentSchedule = shipmentSchedulesById.get(shipmentScheduleId);

			final ImmutableSet<HuId> cuIdsToAggregate = qtyPickedRecordsByScheduleId.get(shipmentScheduleId)
					.stream()
					.filter(ShipmentSchedulesCUsToTUsAggregator::isEligibleForAggregation)
					.map(record -> HuId.ofRepoId(record.getVHU_ID()))
					.collect(ImmutableSet.toImmutableSet());
			final List<I_M_HU> cusToAggregate = handlingUnitsBL.getByIds(cuIdsToAggregate);
			if (cusToAggregate.isEmpty())
			{
				continue;
			}

			final TULoader tuLoader = huShipmentScheduleBL.createTULoader(shipmentSchedule).orElse(null);
			if (tuLoader == null)
			{
				continue;
			}

			try (final IAutoCloseable ignored = ShipmentScheduleHUTrxListener.temporaryEnableUpdateAllocationLUAndTUForCU())
			{
				tuLoader.addCUs(cusToAggregate);
				tuLoader.closeCurrentTUs();
			}
		}
	}

	private static boolean isEligibleForAggregation(final I_M_ShipmentSchedule_QtyPicked qtyPickedRecord)
	{
		return qtyPickedRecord.isActive()
				&& qtyPickedRecord.getVHU_ID() > 0 // we have a CU
				&& qtyPickedRecord.getQtyPicked().signum() != 0
				&& qtyPickedRecord.getM_TU_HU_ID() <= 0 // not already aggregated to TUs
				&& qtyPickedRecord.getM_LU_HU_ID() <= 0// not already aggregated to LUs
				&& qtyPickedRecord.getM_InOutLine_ID() <= 0 // not already shipped
				;
	}
}
