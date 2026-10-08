package de.metas.frontend_testing.masterdata.compensation_group;

import de.metas.frontend_testing.masterdata.Identifier;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/**
 * A compensation (e.g. discount) line of a schema, persisted as {@code C_CompensationGroup_SchemaLine}.
 * Every group created from the schema gets one compensation order line for it.
 */
@Value
@Builder
@Jacksonized
public class JsonCompensationGroupSchemaCompensationLine
{
	/**
	 * Identifier of a product previously created in the {@code products} section of the same request.
	 * The product's {@code GroupCompensationType} / {@code GroupCompensationAmtType} decide the line's type;
	 * when not set they default to a percent discount.
	 */
	@NonNull Identifier product;

	/**
	 * The discount percentage ({@code C_CompensationGroup_SchemaLine.CompleteOrderDiscount}).
	 */
	@Nullable BigDecimal percentage;
}
