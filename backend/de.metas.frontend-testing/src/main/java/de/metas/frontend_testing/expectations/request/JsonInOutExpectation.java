package de.metas.frontend_testing.expectations.request;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

/**
 * Expectation for a single M_InOut (shipment) generated for a sales order.
 *
 * <p>Usage:
 * <pre>
 * // Assert one shipment with DocStatus=CO
 * "SO1": { "shipments": [{ "docStatus": "CO" }] }
 *
 * // Assert order produced NO shipments (DO_NOT_CREATE policy)
 * "SO1": { "shipments": [] }
 * </pre>
 */
@Value
@Builder
@Jacksonized
public class JsonInOutExpectation
{
	/**
	 * Expected DocStatus of the M_InOut, e.g. "DR" (draft) or "CO" (completed).
	 * When null, no DocStatus assertion is performed on this shipment.
	 */
	@Nullable String docStatus;
}
