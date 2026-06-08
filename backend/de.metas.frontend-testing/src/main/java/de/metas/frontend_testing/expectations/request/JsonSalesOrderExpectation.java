package de.metas.frontend_testing.expectations.request;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Expectation for a sales order (C_Order), keyed by the order's identifier string in the
 * {@code salesOrders} map of {@link JsonExpectations}.
 *
 * <p>Example consumer-side JSON shape:
 * <pre>
 * Backend.expect({
 *   salesOrders: {
 *     'SO1': {
 *       // assert one completed shipment
 *       shipments: [{ docStatus: 'CO' }]
 *     },
 *     'SO2': {
 *       // assert order produced NO shipments (empty list = absence assertion)
 *       shipments: []
 *     },
 *     'SO3': {
 *       // assert FIFO partial delivery via shipment-schedule quantities
 *       // (useful when mass-printing creates one combined per-customer shipment
 *       //  instead of a separate shipment per SO):
 *       shipmentSchedule: { qtyDelivered: 2, qtyToDeliver: 0 }
 *     }
 *   }
 * });
 * </pre>
 */
@Value
@Builder
@Jacksonized
public class JsonSalesOrderExpectation
{
	/**
	 * Ordered list of expected M_InOut (shipment) documents for this sales order.
	 *
	 * <ul>
	 *   <li>Non-null, non-empty list: assert that exactly N shipments were created, matching in order.</li>
	 *   <li>Empty list ({@code []}): assert that NO shipments exist for this order.</li>
	 *   <li>Null: no shipment assertion at all (field omitted from JSON).</li>
	 * </ul>
	 */
	@Nullable List<JsonInOutExpectation> shipments;

	/**
	 * Assert the order's shipment-schedule delivered/remaining quantities (M_ShipmentSchedule).
	 *
	 * <p>Use this instead of {@link #shipments} when mass-printing creates one combined per-customer
	 * shipment (so per-SO M_InOut lookup is not meaningful) but you still need to verify the FIFO
	 * split per order via {@code QtyDelivered} / {@code QtyToDeliver}.
	 *
	 * <p>Null means no shipment-schedule assertion (field omitted from JSON).
	 */
	@Nullable JsonShipmentScheduleExpectation shipmentSchedule;
}
