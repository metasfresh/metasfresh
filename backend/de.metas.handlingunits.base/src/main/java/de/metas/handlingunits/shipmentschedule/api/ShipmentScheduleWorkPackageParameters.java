package de.metas.handlingunits.shipmentschedule.api;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.inout.ShipmentScheduleId;
import de.metas.picking.api.PickingJobScheduleId;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleIdSet;
import de.metas.process.PInstanceId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.CompositeQueryFilter;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Set;

@Value
@Builder
public class ShipmentScheduleWorkPackageParameters
{
	public static final String PARAM_OnlyLUIds = "OnlyLUIds";
	public static final String PARAM_QuantityType = "QuantityType";
	public static final String PARAM_IsOnTheFlyPickToPackingInstructions = "IsOnTheFlyPickToPackingInstructions";
	public static final String PARAM_IsCompleteShipments = "IsCompleteShipments";
	public static final String PARAM_IsCloseShipmentSchedules = "IsCloseShipmentSchedules";
	public static final String PARAM_IsShipmentDateToday = "IsShipToday";
	public static final String PARAM_PREFIX_AdvisedShipmentDocumentNo = "Advised_ShipmentDocumentNo_For_M_ShipmentSchedule_ID_"; // (param name can have 255 chars)
	public static final String PARAM_QtyToDeliver_Override = "QtyToDeliver_Override_For_M_ShipmentSchedule_ID";
	/**
	 * Mandatory, even if there is not really an AD_PInstance record. Needed for locking.
	 */
	@NonNull PInstanceId adPInstanceId;

	@Nullable @Getter(AccessLevel.NONE) ShipmentScheduleAndJobScheduleIdSet scheduleIds;
	@Nullable @Getter(AccessLevel.NONE) IQueryFilter<I_M_ShipmentSchedule> queryFilters;
	@Nullable ImmutableSet<HuId> onlyLUIds;

	@NonNull M_ShipmentSchedule_QuantityTypeToUse quantityType;

	/**
	 * If {@code false} and HUs are picked on-the-fly, then those HUs are created as CUs that are taken from bigger LUs, TUs or CUs (the default).
	 * If {@code true}, then the on-the-fly picked HUs are created as TUs, using the respective shipment schedules' packing instructions.
	 */
	@Builder.Default boolean onTheFlyPickToPackingInstructions = false;

	boolean completeShipments;
	boolean isCloseShipmentSchedules;
	boolean isShipmentDateToday;

	/**
	 * Can be used if the caller thinks that the shipping in which the respective shipment-schedules end up shall have the given documentNos.
	 * ShipmentScheduleIds that are not matched are ignored.
	 */
	@Nullable ImmutableMap<ShipmentScheduleId, String> advisedShipmentDocumentNos;

	@Nullable QtyToDeliverMap qtysToDeliverOverride;

	public IQueryFilter<I_M_ShipmentSchedule> getShipmentSchedulesQueryFiltersEffective()
	{
		final CompositeQueryFilter<I_M_ShipmentSchedule> result = CompositeQueryFilter.newInstance(I_M_ShipmentSchedule.class);

		if (this.scheduleIds != null && !this.scheduleIds.isEmpty())
		{
			result.addOnlyActiveRecordsFilter();
			result.addInArrayFilter(I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID, this.scheduleIds.getShipmentScheduleIds());
		}
		if (this.queryFilters != null)
		{
			result.addFilter(this.queryFilters);
		}

		if (result.isEmpty())
		{
			throw new AdempiereException("At least scheduleIds or queryFilters shall be set: " + this);
		}

		return result;
	}

	public Set<PickingJobScheduleId> getPickingJobScheduleIds()
	{
		if (scheduleIds == null || scheduleIds.isEmpty()) {return ImmutableSet.of();}
		
		return scheduleIds.getJobScheduleIds();
	}

	public Set<PickingJobScheduleId> getPickingJobScheduleIds(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		if (scheduleIds == null || scheduleIds.isEmpty()) {return ImmutableSet.of();}

		return scheduleIds.getJobScheduleIds(shipmentScheduleId);
	}

}
