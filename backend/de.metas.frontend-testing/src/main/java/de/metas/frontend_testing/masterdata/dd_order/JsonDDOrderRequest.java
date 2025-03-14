package de.metas.frontend_testing.masterdata.dd_order;

import de.metas.frontend_testing.masterdata.Identifier;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonDDOrderRequest
{
	@NonNull Identifier warehouseFrom;
	@NonNull Identifier warehouseTo;
	@NonNull Identifier warehouseInTransit;
	@Nullable Identifier plant;
	@NonNull List<Line> lines;

	//
	//
	//

	@Value
	@Builder
	@Jacksonized
	public static class Line
	{
		@NonNull Identifier product;
		@NonNull BigDecimal qtyEntered;
	}
}
