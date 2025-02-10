package de.metas.frontend_testing.masterdata.sales_order;

import de.metas.frontend_testing.masterdata.Identifier;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonSalesOrderCreateRequest
{
	@NonNull Identifier bpartner;
	@NonNull ZonedDateTime datePromised;
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
		@NonNull BigDecimal qty;
		@Nullable Identifier piItemProduct;
	}
}
