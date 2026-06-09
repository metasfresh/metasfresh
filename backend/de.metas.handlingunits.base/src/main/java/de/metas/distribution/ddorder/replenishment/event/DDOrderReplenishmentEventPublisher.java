package de.metas.distribution.ddorder.replenishment.event;

import de.metas.event.Event;
import de.metas.event.IEventBusFactory;
import de.metas.inoutcandidate.model.I_M_Picking_Job_Schedule;
import de.metas.picking.api.PickingJobScheduleId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.LinkedHashSet;

@Component
@RequiredArgsConstructor
public class DDOrderReplenishmentEventPublisher
{
	public static final String PROPERTY_pickingJobScheduleId = "pickingJobScheduleId";
	public static final String PROPERTY_AD_Client_ID = "AD_Client_ID";
	public static final String PROPERTY_AD_Org_ID = "AD_Org_ID";
	private static final String EVENT_NAME = "DDOrderPickingReconcile";

	@NonNull private final IEventBusFactory eventBusFactory;

	public void publishOne(@NonNull final PickingJobScheduleId pickingJobScheduleId)
	{
		// The assignment's AD_Client_ID/AD_Org_ID are carried into the async handler (which runs on an EventBus pool thread with no AD context).
		// The assignment may already be deleted (afterDelete reconcile) — loadOutOfTrx so a deleted-but-committed row is still readable for its client/org.
		final I_M_Picking_Job_Schedule jobSchedule = InterfaceWrapperHelper.loadOutOfTrx(pickingJobScheduleId.getRepoId(), I_M_Picking_Job_Schedule.class);

		// shallBeLogged() is required: without it the event-bus never sets up the EventLogEntryCollector
		// thread-local, the handler throws "Missing thread-local EventLogEntryCollector", and no AD_EventLog is recorded.
		final Event event = Event.builder()
				.setEventName(EVENT_NAME)
				.putProperty(PROPERTY_pickingJobScheduleId, pickingJobScheduleId.getRepoId())
				.putProperty(PROPERTY_AD_Client_ID, jobSchedule != null ? jobSchedule.getAD_Client_ID() : 0)
				.putProperty(PROPERTY_AD_Org_ID, jobSchedule != null ? jobSchedule.getAD_Org_ID() : 0)
				// Tie the resulting AD_EventLog (and thus its AD_EventLog_Entry rows) to the assignment that
				// triggered the reconcile. EventLogService.saveEvent copies this into AD_EventLog.AD_Table_ID /
				// Record_ID, which lets callers (incl. tests) pin a log entry to its originating assignment
				// instead of matching any reconcile entry globally.
				.setSourceRecordReference(TableRecordReference.of(I_M_Picking_Job_Schedule.Table_Name, pickingJobScheduleId.getRepoId()))
				.shallBeLogged()
				.build();
		eventBusFactory.getEventBus(DDOrderReplenishmentConstants.TOPIC).enqueueEvent(event);
	}

	public void publishAll(@NonNull final Collection<PickingJobScheduleId> pickingJobScheduleIds)
	{
		for (final PickingJobScheduleId pickingJobScheduleId : new LinkedHashSet<>(pickingJobScheduleIds))
		{
			publishOne(pickingJobScheduleId);
		}
	}
}
