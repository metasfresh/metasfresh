package de.metas.distribution.ddorder.replenishment.event;

import de.metas.event.Event;
import de.metas.organization.ClientAndOrgId;
import de.metas.picking.api.PickingJobScheduleId;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
class DDOrderReplenishmentRequestConverter
{
	private static final String EVENT_NAME = "DDOrderPickingReconcile";
	private static final String PROPERTY_pickingJobScheduleId = "pickingJobScheduleId";
	private static final String PROPERTY_AD_Client_ID = "AD_Client_ID";
	private static final String PROPERTY_AD_Org_ID = "AD_Org_ID";

	@NonNull
	public Event toEvent(@NonNull final DDOrderReplenishmentRequest request)
	{
		final PickingJobScheduleId pickingJobScheduleId = request.getPickingJobScheduleId();
		final ClientAndOrgId clientAndOrgId = request.getClientAndOrgId();

		return Event.builder()
				.setEventName(EVENT_NAME)
				.putProperty(PROPERTY_pickingJobScheduleId, pickingJobScheduleId.getRepoId())
				.putProperty(PROPERTY_AD_Client_ID, clientAndOrgId.getClientId().getRepoId())
				.putProperty(PROPERTY_AD_Org_ID, clientAndOrgId.getOrgId().getRepoId())
				// Tie the resulting AD_EventLog (and thus its AD_EventLog_Entry rows) to the assignment that
				// triggered the reconcile. EventLogService.saveEvent copies this into AD_EventLog.AD_Table_ID /
				// Record_ID, which lets callers (incl. tests) pin a log entry to its originating assignment
				// instead of matching any reconcile entry globally.
				.setSourceRecordReference(pickingJobScheduleId.toTableRecordReference())
				// shallBeLogged() is required: without it the event-bus never sets up the EventLogEntryCollector
				// thread-local, the handler throws "Missing thread-local EventLogEntryCollector", and no AD_EventLog is recorded.
				.shallBeLogged()
				.build();
	}

	public DDOrderReplenishmentRequest fromEvent(final @NonNull Event event)
	{
		return DDOrderReplenishmentRequest.builder()
				.pickingJobScheduleId(PickingJobScheduleId.ofRepoId(event.getPropertyAsInt(PROPERTY_pickingJobScheduleId, -1)))
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(
						event.getPropertyAsInt(PROPERTY_AD_Client_ID, -1),
						event.getPropertyAsInt(PROPERTY_AD_Org_ID, -1)))
				.build();
	}
}
