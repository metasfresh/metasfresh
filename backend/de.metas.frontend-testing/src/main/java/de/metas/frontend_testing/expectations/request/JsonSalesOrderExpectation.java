package de.metas.frontend_testing.expectations.request;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;
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
 *     'SO_A': {
 *       // assert FIFO partial delivery via shipped qty on shipment lines
 *       // (aggregation-independent: sums movementQty across all processed M_InOutLines
 *       //  for this order, regardless of how many M_InOut documents they belong to):
 *       shippedQty: 2
 *     },
 *     '1000123': {
 *       // assert the order's compensation groups, matched in id order (record ids)
 *       compensationGroups: [{ flatrateTermId: 1000456, compensationGroupSchemaId: 1000789 }]
 *     },
 *     'SO3': {
 *       // assert order lines by product (masterdata keys), incl. the calibration data
 *       lines: [{ product: 'P1', qtyEntered: 15, calibrationFactor: 1.5, calibrationRule: 'R1', qtyEnteredUncalibrated: 10 }]
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
	 * Assert the total shipped quantity for this order, summed across all PROCESSED
	 * (completed) shipment lines (M_InOutLine.MovementQty), regardless of how many
	 * M_InOut documents the lines belong to.
	 *
	 * <p>Use this for aggregation-independent FIFO checks: it verifies the order's demand
	 * was fulfilled on the right total qty on shipment line(s), without assuming whether
	 * mass-printing groups them into one or several shipment documents.
	 *
	 * <p>Null means no shipped-qty assertion (field omitted from JSON).
	 */
	@Nullable BigDecimal shippedQty;

	/**
	 * The order's expected compensation groups (C_Order_CompensationGroup), matched 1:1 in id order.
	 * Null means no compensation-group assertion (field omitted from JSON).
	 */
	@Nullable List<JsonOrderCompensationGroupExpectation> compensationGroups;

	/**
	 * Expected order lines (C_OrderLine), each matched by product (see {@link JsonOrderLineExpectation}); the
	 * order's other lines are not asserted. Null means no order-line assertion (field omitted from JSON).
	 */
	@Nullable List<JsonOrderLineExpectation> lines;
}
