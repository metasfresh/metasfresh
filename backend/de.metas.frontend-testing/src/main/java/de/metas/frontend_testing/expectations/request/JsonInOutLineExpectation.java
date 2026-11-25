package de.metas.frontend_testing.expectations.request;

import de.metas.frontend_testing.masterdata.Identifier;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Map;

/**
 * Expectation for a single M_InOutLine of a shipment, used inside {@link JsonInOutExpectation#getLines()}.
 *
 * <p>All fields are opt-in: a {@code null} field is NOT asserted.
 *
 * <pre>
 * // assert this line ships product P1 with MovementQty 8
 * { "product": "P1", "movementQty": 8 }
 * // assert only the qty of this line, regardless of product
 * { "movementQty": 8 }
 * // assert the catch qty (catch UOM) of this line
 * { "product": "P1", "qtyDeliveredCatch": 2.5 }
 * // assert the attribute values of the line's attribute set instance; null = the attribute has no value
 * { "product": "P1", "movementQty": 3, "attributes": { "Lot-Nummer": "LOT-A" } }
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

	/**
	 * Expected M_InOutLine.QtyDeliveredCatch (catch UOM).
	 * When null, no assertion.
	 */
	@Nullable BigDecimal qtyDeliveredCatch;

	/**
	 * Expected attribute values of the line's attribute set instance (M_InOutLine.M_AttributeSetInstance_ID), keyed by
	 * attribute value (e.g. {@code Lot-Nummer}). A {@code null} map value expects the attribute to have no value.
	 * Value syntax: see {@link de.metas.frontend_testing.expectations.HUAttributeAssertions}.
	 * When the map is null, no attribute assertion is performed on this line.
	 */
	@Nullable Map<String, String> attributes;
}
