package de.metas.material.event;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.metas.material.event.attributes.AttributesChangedEvent;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.ddorder.DDOrderAdvisedEvent;
import de.metas.material.event.ddorder.DDOrderCreatedEvent;
import de.metas.material.event.ddorder.DDOrderDeletedEvent;
import de.metas.material.event.ddorder.DDOrderDocStatusChangedEvent;
import de.metas.material.event.ddorder.DDOrderRequestedEvent;
import de.metas.material.event.forecast.ForecastCreatedEvent;
import de.metas.material.event.picking.PickingRequestedEvent;
import de.metas.material.event.pporder.PPOrderCandidateAdvisedEvent;
import de.metas.material.event.pporder.PPOrderCandidateCreatedEvent;
import de.metas.material.event.pporder.PPOrderCandidateDeletedEvent;
import de.metas.material.event.pporder.PPOrderCandidateRequestedEvent;
import de.metas.material.event.pporder.PPOrderCandidateUpdatedEvent;
import de.metas.material.event.pporder.PPOrderChangedEvent;
import de.metas.material.event.pporder.PPOrderCreatedEvent;
import de.metas.material.event.pporder.PPOrderDeletedEvent;
import de.metas.material.event.pporder.PPOrderRequestedEvent;
import de.metas.material.event.procurement.PurchaseOfferCreatedEvent;
import de.metas.material.event.procurement.PurchaseOfferDeletedEvent;
import de.metas.material.event.procurement.PurchaseOfferUpdatedEvent;
import de.metas.material.event.purchase.PurchaseCandidateAdvisedEvent;
import de.metas.material.event.purchase.PurchaseCandidateCreatedEvent;
import de.metas.material.event.purchase.PurchaseCandidateRequestedEvent;
import de.metas.material.event.purchase.PurchaseCandidateUpdatedEvent;
import de.metas.material.event.receiptschedule.ReceiptScheduleCreatedEvent;
import de.metas.material.event.receiptschedule.ReceiptScheduleDeletedEvent;
import de.metas.material.event.receiptschedule.ReceiptScheduleUpdatedEvent;
import de.metas.material.event.shipmentschedule.ShipmentScheduleCreatedEvent;
import de.metas.material.event.shipmentschedule.ShipmentScheduleDeletedEvent;
import de.metas.material.event.shipmentschedule.ShipmentScheduleUpdatedEvent;
import de.metas.material.event.simulation.DeactivateAllSimulatedCandidatesEvent;
import de.metas.material.event.simulation.SimulatedDemandCreatedEvent;
import de.metas.material.event.stock.StockChangedEvent;
import de.metas.material.event.stockcandidate.MaterialCandidateChangedEvent;
import de.metas.material.event.stockcandidate.StockCandidateChangedEvent;
import de.metas.material.event.stockestimate.StockEstimateCreatedEvent;
import de.metas.material.event.stockestimate.StockEstimateDeletedEvent;
import de.metas.material.event.supplyrequired.SupplyRequiredEvent;
import de.metas.material.event.tracking.AllEventsProcessedEvent;
import de.metas.material.event.transactions.TransactionCreatedEvent;
import de.metas.material.event.transactions.TransactionDeletedEvent;

/**
 * These are the high-level event pojos. We serialize and deserialize them and let them ride inside {@link de.metas.event.Event} instances.
 * <p>
 * Thanks to <a href="https://spring.io/blog/2016/11/08/cqrs-and-event-sourcing-with-jakub-pilimon">https://spring.io/blog/2016/11/08/cqrs-and-event-sourcing-with-jakub-pilimon</a> for the samples and hints on how to use jackson.
 * Also thanks to <a href="https://reinhard.codes/2015/09/16/lomboks-builder-annotation-and-inheritance">https://reinhard.codes/2015/09/16/lomboks-builder-annotation-and-inheritance/</a> for the hint about using builder with inheritance, but I didn't get it to fly when i also had to provide allargsconstructors to the concrete sub classes
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({

		@JsonSubTypes.Type(name = AttributesChangedEvent.TYPE, value = AttributesChangedEvent.class),

		@JsonSubTypes.Type(name = DDOrderAdvisedEvent.TYPE, value = DDOrderAdvisedEvent.class),
		@JsonSubTypes.Type(name = DDOrderCreatedEvent.TYPE, value = DDOrderCreatedEvent.class),
		@JsonSubTypes.Type(name = DDOrderDocStatusChangedEvent.TYPE, value = DDOrderDocStatusChangedEvent.class),
		@JsonSubTypes.Type(name = DDOrderRequestedEvent.TYPE, value = DDOrderRequestedEvent.class),
		@JsonSubTypes.Type(name = DDOrderDeletedEvent.TYPE, value = DDOrderDeletedEvent.class),

		@JsonSubTypes.Type(name = ForecastCreatedEvent.TYPE, value = ForecastCreatedEvent.class),

		@JsonSubTypes.Type(name = PickingRequestedEvent.TYPE, value = PickingRequestedEvent.class),

		@JsonSubTypes.Type(name = PPOrderCreatedEvent.TYPE, value = PPOrderCreatedEvent.class),
		@JsonSubTypes.Type(name = PPOrderDeletedEvent.TYPE, value = PPOrderDeletedEvent.class),
		@JsonSubTypes.Type(name = PPOrderChangedEvent.TYPE, value = PPOrderChangedEvent.class),
		@JsonSubTypes.Type(name = PPOrderRequestedEvent.TYPE, value = PPOrderRequestedEvent.class),

		@JsonSubTypes.Type(name = PPOrderCandidateAdvisedEvent.TYPE, value = PPOrderCandidateAdvisedEvent.class),
		@JsonSubTypes.Type(name = PPOrderCandidateRequestedEvent.TYPE, value = PPOrderCandidateRequestedEvent.class),
		@JsonSubTypes.Type(name = PPOrderCandidateUpdatedEvent.TYPE, value = PPOrderCandidateUpdatedEvent.class),
		@JsonSubTypes.Type(name = PPOrderCandidateCreatedEvent.TYPE, value = PPOrderCandidateCreatedEvent.class),
		@JsonSubTypes.Type(name = PPOrderCandidateDeletedEvent.TYPE, value = PPOrderCandidateDeletedEvent.class),

		@JsonSubTypes.Type(name = PurchaseCandidateAdvisedEvent.TYPE, value = PurchaseCandidateAdvisedEvent.class),
		@JsonSubTypes.Type(name = PurchaseCandidateCreatedEvent.TYPE, value = PurchaseCandidateCreatedEvent.class),
		@JsonSubTypes.Type(name = PurchaseCandidateUpdatedEvent.TYPE, value = PurchaseCandidateUpdatedEvent.class),
		@JsonSubTypes.Type(name = PurchaseCandidateRequestedEvent.TYPE, value = PurchaseCandidateRequestedEvent.class),

		@JsonSubTypes.Type(name = PurchaseOfferCreatedEvent.TYPE, value = PurchaseOfferCreatedEvent.class),
		@JsonSubTypes.Type(name = PurchaseOfferUpdatedEvent.TYPE, value = PurchaseOfferUpdatedEvent.class),
		@JsonSubTypes.Type(name = PurchaseOfferDeletedEvent.TYPE, value = PurchaseOfferDeletedEvent.class),

		@JsonSubTypes.Type(name = ReceiptScheduleCreatedEvent.TYPE, value = ReceiptScheduleCreatedEvent.class),
		@JsonSubTypes.Type(name = ReceiptScheduleDeletedEvent.TYPE, value = ReceiptScheduleDeletedEvent.class),
		@JsonSubTypes.Type(name = ReceiptScheduleUpdatedEvent.TYPE, value = ReceiptScheduleUpdatedEvent.class),

		@JsonSubTypes.Type(name = ShipmentScheduleCreatedEvent.TYPE, value = ShipmentScheduleCreatedEvent.class),
		@JsonSubTypes.Type(name = ShipmentScheduleDeletedEvent.TYPE, value = ShipmentScheduleDeletedEvent.class),
		@JsonSubTypes.Type(name = ShipmentScheduleUpdatedEvent.TYPE, value = ShipmentScheduleUpdatedEvent.class),

		@JsonSubTypes.Type(name = StockChangedEvent.TYPE, value = StockChangedEvent.class),

		@JsonSubTypes.Type(name = StockEstimateCreatedEvent.TYPE, value = StockEstimateCreatedEvent.class),
		@JsonSubTypes.Type(name = StockEstimateDeletedEvent.TYPE, value = StockEstimateDeletedEvent.class),

		@JsonSubTypes.Type(name = SupplyRequiredEvent.TYPE, value = SupplyRequiredEvent.class),

		@JsonSubTypes.Type(name = TransactionCreatedEvent.TYPE, value = TransactionCreatedEvent.class),
		@JsonSubTypes.Type(name = TransactionDeletedEvent.TYPE, value = TransactionDeletedEvent.class),

		@JsonSubTypes.Type(name = StockCandidateChangedEvent.TYPE, value = StockCandidateChangedEvent.class),
		@JsonSubTypes.Type(name = MaterialCandidateChangedEvent.TYPE, value = MaterialCandidateChangedEvent.class),

		@JsonSubTypes.Type(name = SimulatedDemandCreatedEvent.TYPE, value = SimulatedDemandCreatedEvent.class),
		@JsonSubTypes.Type(name = DeactivateAllSimulatedCandidatesEvent.TYPE, value = DeactivateAllSimulatedCandidatesEvent.class),
		@JsonSubTypes.Type(name = AllEventsProcessedEvent.TYPE, value = AllEventsProcessedEvent.class)
})
public interface MaterialEvent
{
	EventDescriptor getEventDescriptor();
}
