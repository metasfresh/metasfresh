package de.metas.distribution.ddorder.replenishment.event;

import de.metas.distribution.ddorder.replenishment.DDOrderReplenishmentGroupKey;
import de.metas.event.Event;
import de.metas.organization.ClientAndOrgId;
import de.metas.picking.api.PickingJobScheduleId;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.warehouse.LocatorId;

@UtilityClass
class DDOrderReplenishmentRequestConverter
{
	private static final String EVENT_NAME = "DDOrderPickingReconcile";
	private static final String PROPERTY_M_Product_ID = "M_Product_ID";
	private static final String PROPERTY_M_LocatorTo_ID = "M_LocatorTo_ID";
	private static final String PROPERTY_M_WarehouseTo_ID = "M_WarehouseTo_ID";
	private static final String PROPERTY_C_UOM_ID = "C_UOM_ID";
	private static final String PROPERTY_triggeredBy = "triggeredBy_M_Picking_Job_Schedule_ID";
	private static final String PROPERTY_AD_Client_ID = "AD_Client_ID";
	private static final String PROPERTY_AD_Org_ID = "AD_Org_ID";

	@NonNull
	public Event toEvent(@NonNull final DDOrderReplenishmentRequest request)
	{
		final DDOrderReplenishmentGroupKey groupKey = request.getGroupKey();
		final LocatorId locatorToId = groupKey.getLocatorToId();
		final PickingJobScheduleId triggeredBy = request.getTriggeredBy();
		final ClientAndOrgId clientAndOrgId = request.getClientAndOrgId();

		return Event.builder()
				.setEventName(EVENT_NAME)
				.putProperty(PROPERTY_M_Product_ID, groupKey.getProductId().getRepoId())
				.putProperty(PROPERTY_M_LocatorTo_ID, locatorToId.getRepoId())
				.putProperty(PROPERTY_M_WarehouseTo_ID, locatorToId.getWarehouseId().getRepoId())
				.putProperty(PROPERTY_C_UOM_ID, groupKey.getUomId().getRepoId())
				.putProperty(PROPERTY_triggeredBy, triggeredBy.getRepoId())
				.putProperty(PROPERTY_AD_Client_ID, clientAndOrgId.getClientId().getRepoId())
				.putProperty(PROPERTY_AD_Org_ID, clientAndOrgId.getOrgId().getRepoId())
				// EventLogService.saveEvent copies this into AD_EventLog.AD_Table_ID / Record_ID, so a log entry can be pinned to the assignment that triggered it.
				.setSourceRecordReference(triggeredBy.toTableRecordReference())
				// shallBeLogged() is required: without it the event-bus never sets up the EventLogEntryCollector
				// thread-local, the handler throws "Missing thread-local EventLogEntryCollector", and no AD_EventLog is recorded.
				.shallBeLogged()
				.build();
	}

	public DDOrderReplenishmentRequest fromEvent(final @NonNull Event event)
	{
		return DDOrderReplenishmentRequest.builder()
				.groupKey(DDOrderReplenishmentGroupKey.builder()
						.productId(ProductId.ofRepoId(event.getPropertyAsInt(PROPERTY_M_Product_ID, -1)))
						.locatorToId(LocatorId.ofRepoId(
								event.getPropertyAsInt(PROPERTY_M_WarehouseTo_ID, -1),
								event.getPropertyAsInt(PROPERTY_M_LocatorTo_ID, -1)))
						.uomId(UomId.ofRepoId(event.getPropertyAsInt(PROPERTY_C_UOM_ID, -1)))
						.build())
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(
						event.getPropertyAsInt(PROPERTY_AD_Client_ID, -1),
						event.getPropertyAsInt(PROPERTY_AD_Org_ID, -1)))
				.triggeredBy(PickingJobScheduleId.ofRepoId(event.getPropertyAsInt(PROPERTY_triggeredBy, -1)))
				.build();
	}
}
