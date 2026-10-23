package de.metas.frontend_testing.masterdata.pp_order;

import de.metas.frontend_testing.masterdata.Identifier;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Map;

@Value
@Builder
@Jacksonized
public class JsonPPOrderRequest
{
	@NonNull Identifier warehouse;
	@NonNull Identifier product;
	@NonNull BigDecimal qty;
	@NonNull ZonedDateTime datePromised;
	@Nullable Identifier salesOrderLine;
	@Nullable Identifier lutuConfigurationTU;
	@Nullable Identifier piItemProduct;

	/**
	 * Overrides {@code M_HU_LUTU_Configuration.QtyCUsPerTU} of the configuration created for {@link #lutuConfigurationTU}
	 * (requires {@code lutuConfigurationTU}) — to store a quantity per TU that differs from the packing instruction, as operators produce it.
	 */
	@Nullable BigDecimal lutuConfigurationQtyCUsPerTU;
	/**
	 * Overrides {@code M_HU_LUTU_Configuration.QtyTU} (finite) of the configuration created for {@link #lutuConfigurationTU}.
	 */
	@Nullable Integer lutuConfigurationQtyTUs;
	/**
	 * Identifier of a {@code packingInstructions} entry that has an LU: its LU packing instruction item is stored
	 * in the configuration created for {@link #lutuConfigurationTU}.
	 */
	@Nullable Identifier lutuConfigurationLU;

	@Nullable Map<String, Line> lines;

	//
	//
	//

	@Value
	@Builder
	@Jacksonized
	public static class Line
	{
		@NonNull Identifier product;
		@Nullable String expectedPickingInstruction;
	}
}
