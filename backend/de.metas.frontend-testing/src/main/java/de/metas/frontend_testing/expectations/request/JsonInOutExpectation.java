package de.metas.frontend_testing.expectations.request;

import de.metas.document.engine.DocStatus;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/**
 * Expectation for a single M_InOut (shipment) generated for a sales order.
 *
 * <p>Usage:
 * <pre>
 * // Assert one completed shipment
 * "SO1": { "shipments": [{ "docStatus": "CO" }] }
 *
 * // Assert one completed shipment with total movement qty = 2
 * "SO1": { "shipments": [{ "docStatus": "CO", "movementQty": 2 }] }
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
	 * Expected DocStatus of the M_InOut.
	 * Wire value is the 2-char code (e.g. "CO", "DR").
	 * When null, no DocStatus assertion is performed on this shipment.
	 */
	@Nullable DocStatus docStatus;

	/**
	 * Expected total movement quantity across all lines of the M_InOut.
	 * This is the sum of {@code M_InOutLine.MovementQty} for all active lines.
	 * When null, no movementQty assertion is performed on this shipment.
	 */
	@Nullable BigDecimal movementQty;
}
