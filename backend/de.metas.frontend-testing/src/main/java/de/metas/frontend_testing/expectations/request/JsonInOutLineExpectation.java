package de.metas.frontend_testing.expectations.request;

import de.metas.frontend_testing.masterdata.Identifier;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/**
 * Expectation for a single M_InOutLine of a shipment, used inside {@link JsonInOutExpectation#getLines()}.
 *
 * <p>Both fields are opt-in: a {@code null} field is NOT asserted.
 *
 * <pre>
 * // assert this line ships product P1 with MovementQty 8
 * { "product": "P1", "movementQty": 8 }
 * // assert only the qty of this line, regardless of product
 * { "movementQty": 8 }
 * </pre>
 */
@Value
@Builder
@Jacksonized
public class JsonInOutLineExpectation
{
	/**
	 * Expected product of the line (M_InOutLine.M_Product_ID), referenced by its masterdata map key.
	 * When null, no product assertion is performed on this line.
	 */
	@Nullable Identifier product;

	/**
	 * Expected M_InOutLine.MovementQty.
	 * When null, no qty assertion is performed on this line.
	 */
	@Nullable BigDecimal movementQty;
}
