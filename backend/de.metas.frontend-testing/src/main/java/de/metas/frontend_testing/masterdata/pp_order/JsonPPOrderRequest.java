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
