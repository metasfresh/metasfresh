package de.metas.inoutcandidate.qty_reservation.qty_delivered_update;

import com.google.common.collect.ImmutableList;
import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.event.Topic;
import de.metas.inout.IInOutBL;
import de.metas.inout.InOutId;
import de.metas.inoutcandidate.qty_reservation.QtyReservationRepository;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class UpdateQtyDeliveredDispatcher
{
	private static final Topic TOPIC_updateQtyDeliveredFromShipment = Topic.localAndAsync(UpdateQtyDeliveredDispatcher.class.getSimpleName() + ".events");

	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IInOutBL inOutBL = Services.get(IInOutBL.class);
	@NonNull private final QtyReservationRepository qtyReservationRepository;
	@NonNull private final IEventBus eventBus;

	public UpdateQtyDeliveredDispatcher(
			@NonNull final QtyReservationRepository qtyReservationRepository,
			@NonNull final IEventBusFactory eventBusFactory)
	{
		this.qtyReservationRepository = qtyReservationRepository;
		this.eventBus = eventBusFactory.getEventBus(TOPIC_updateQtyDeliveredFromShipment);
	}

	@PostConstruct
	public void postConstruct()
	{
		this.eventBus.subscribeOn(UpdateQtyDeliveredRequest.class, this::handleEvent);
	}

	private void handleEvent(@NonNull final UpdateQtyDeliveredRequest request)
	{
		UpdateQtyDeliveredCommand.builder()
				.qtyReservationRepository(qtyReservationRepository)
				.inOutBL(inOutBL)
				.request(request)
				.build().execute();
	}

	public void fireShipmentChanged(@NonNull final InOutId shipmentId)
	{

		trxManager.accumulateAndProcessAfterCommit(
				TOPIC_updateQtyDeliveredFromShipment.getFullName(),
				ImmutableList.of(
						UpdateQtyDeliveredRequest.builder()
								.shipmentId(shipmentId)
								.build()
				),
				eventBus::enqueueObjectsCollection
		);
	}
}
