package de.metas.frontend_testing.masterdata.compensation_group;

import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.uom.X12DE355;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Value
@Builder
@Jacksonized
public class JsonCompensationGroupSchemaTemplateLine
{
	/**
	 * Identifier of a product previously created in the {@code products} section of the same request.
	 */
	@NonNull Identifier product;

	/**
	 * Quantity (in {@link #uom}, or the product's stock UOM if {@link #uom} is null).
	 */
	@NonNull BigDecimal qty;

	/**
	 * Optional UOM override. If null, the product's stock UOM is used.
	 */
	@Nullable X12DE355 uom;

	/**
	 * If true, the resulting order line is created with {@code IsWithoutCharge=Y} (no price contribution).
	 */
	@Nullable Boolean isWithoutCharge;

	@Nullable Boolean isAllowSeparateInvoicing;

	@Nullable Boolean isHideWhenPrinting;
}
