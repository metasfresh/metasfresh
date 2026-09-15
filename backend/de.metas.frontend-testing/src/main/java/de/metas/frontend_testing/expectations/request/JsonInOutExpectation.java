package de.metas.frontend_testing.expectations.request;

import de.metas.document.engine.DocStatus;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;

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
 * // Assert the shipment has EXACTLY one line: product P1, MovementQty 8 (no counter-row)
 * "SO1": { "shipments": [{ "docStatus": "CO", "lines": [{ "product": "P1", "movementQty": 8 }] }] }
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

	/**
	 * Expected lines of the M_InOut, in {@code M_InOutLine.Line} order.
	 *
	 * <p>When non-null, the shipment must have EXACTLY these lines: the actual active lines, ordered by
	 * {@code M_InOutLine.Line} ascending, are matched positionally against this list — same count, no extra
	 * or missing line (this is what rules out a spurious negative counter-row). Within each line,
	 * {@code product} / {@code movementQty} are asserted only when set.
	 *
	 * <p>When null, no line-level assertion is performed.
	 */
	@Nullable List<JsonInOutLineExpectation> lines;
}
