package de.metas.handlingunits.shipmentschedule.api;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.allocation.ILUTUConfigurationFactory;
import de.metas.handlingunits.allocation.ILUTUProducerAllocationDestination;
import de.metas.handlingunits.allocation.impl.HUListAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocDAO;
import lombok.Builder;
import lombok.NonNull;

import java.util.List;
import java.util.Map;
import java.util.Set;

class ShipmentSchedulesCUsToTUsAggregator
{
	@NonNull private final IHUShipmentScheduleBL huShipmentScheduleBL;
	@NonNull private final IShipmentScheduleAllocDAO shipmentScheduleAllocDAO;
	@NonNull private final IHandlingUnitsBL handlingUnitsBL;
	@NonNull private final ILUTUConfigurationFactory lutuConfigurationFactory;

	@NonNull final ImmutableSet<ShipmentScheduleId> shipmentScheduleIds;

	@Builder
	private ShipmentSchedulesCUsToTUsAggregator(
			@NonNull final IHUShipmentScheduleBL huShipmentScheduleBL,
			@NonNull final IShipmentScheduleAllocDAO shipmentScheduleAllocDAO,
			@NonNull final IHandlingUnitsBL handlingUnitsBL,
			@NonNull final ILUTUConfigurationFactory lutuConfigurationFactory,
			//
			@NonNull Set<ShipmentScheduleId> shipmentScheduleIds)
	{
		this.huShipmentScheduleBL = huShipmentScheduleBL;
		this.shipmentScheduleAllocDAO = shipmentScheduleAllocDAO;
		this.handlingUnitsBL = handlingUnitsBL;
		this.lutuConfigurationFactory = lutuConfigurationFactory;

		this.shipmentScheduleIds = ImmutableSet.copyOf(shipmentScheduleIds);
	}

	public void execute()
	{
		if (shipmentScheduleIds.isEmpty())
		{
			return;
		}

		final Map<ShipmentScheduleId, I_M_ShipmentSchedule> shipmentSchedulesById = huShipmentScheduleBL.getByIds(shipmentScheduleIds);

		final ImmutableListMultimap<ShipmentScheduleId, I_M_ShipmentSchedule_QtyPicked> qtyPickedRecordsByScheduleId
				= shipmentScheduleAllocDAO.retrieveNotOnShipmentLineRecordsByScheduleIds(shipmentSchedulesById.keySet(), I_M_ShipmentSchedule_QtyPicked.class);

		for (final ShipmentScheduleId shipmentScheduleId : qtyPickedRecordsByScheduleId.keySet())
		{
			final I_M_ShipmentSchedule shipmentSchedule = shipmentSchedulesById.get(shipmentScheduleId);

			final HUPIItemProductId tuPIItemProductId = huShipmentScheduleBL.getEffectivePackingMaterialId(shipmentSchedule);
			if (tuPIItemProductId.isVirtualHU())
			{
				continue;
			}

			final ImmutableList<I_M_ShipmentSchedule_QtyPicked> qtyPickedRecords = qtyPickedRecordsByScheduleId.get(shipmentScheduleId)
					.stream()
					.filter(ShipmentSchedulesCUsToTUsAggregator::isEligibleForAggregation)
					.collect(ImmutableList.toImmutableList());
			final ImmutableSet<HuId> vhuIdsToAggregate = qtyPickedRecords.stream().map(record -> HuId.ofRepoId(record.getVHU_ID())).collect(ImmutableSet.toImmutableSet());
			final List<I_M_HU> vhusToAggregate = handlingUnitsBL.getByIds(vhuIdsToAggregate);
			if (vhusToAggregate.isEmpty())
			{
				continue;
			}

			HULoader.builder()
					.source(HUListAllocationSourceDestination.of(vhusToAggregate))
					.destination(createLUTUProducerDestination(shipmentSchedule))
					.unloadAllFromSource();
		}
	}

	private ILUTUProducerAllocationDestination createLUTUProducerDestination(final I_M_ShipmentSchedule shipmentSchedule)
	{
		final I_M_HU_LUTU_Configuration lutuConfiguration = huShipmentScheduleBL.deriveM_HU_LUTU_Configuration(shipmentSchedule);
		lutuConfiguration.setHUStatus(X_M_HU.HUSTATUS_Picked);
		lutuConfigurationFactory.save(lutuConfiguration);
		return lutuConfigurationFactory.createLUTUProducerAllocationDestination(lutuConfiguration);
	}

	private static boolean isEligibleForAggregation(final I_M_ShipmentSchedule_QtyPicked qtyPickedRecord)
	{
		return qtyPickedRecord.isActive()
				&& qtyPickedRecord.getVHU_ID() > 0 // we have a CU
				&& qtyPickedRecord.getM_TU_HU_ID() <= 0 // not already aggregated to TUs
				&& qtyPickedRecord.getM_LU_HU_ID() <= 0// not already aggregated to LUs
				&& qtyPickedRecord.getM_InOutLine_ID() <= 0 // not already shipped
				;
	}
}
