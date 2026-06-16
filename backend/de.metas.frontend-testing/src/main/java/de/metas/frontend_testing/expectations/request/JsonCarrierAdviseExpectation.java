package de.metas.frontend_testing.expectations.request;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

/**
 * Carrier-advise expectation for a single shipment schedule (matched by its order line's product).
 *
 * <p>Consumer-side JSON shape:
 * <pre>
 * Backend.expect({
 *   salesOrders: {
 *     'SO1': {
 *       carrierAdvise: {
 *         // assert the schedule for product P1 has a resolved carrier product named "<shipperName>"
 *         // and the advising status reached Completed
 *         'P1': { carrierProductName: 'nShift Local', advisingStatus: 'CO' }
 *       }
 *     }
 *   }
 * });
 * </pre>
 */
@Value
@Builder
@Jacksonized
public class JsonCarrierAdviseExpectation
{
	/** Expected M_ShipmentSchedule.Carrier_Advising_Status code (e.g. {@code CO} for Completed). Null = not asserted. */
	@Nullable String advisingStatus;

	/** Expected name of the resolved Carrier_Product (for the no-gateway flow this equals the shipper name). Null = not asserted. */
	@Nullable String carrierProductName;

	/** When true, assert a Carrier_Product_ID is set on the schedule. When false, assert none is set. Null = not asserted. */
	@Nullable Boolean carrierProductSet;
}
