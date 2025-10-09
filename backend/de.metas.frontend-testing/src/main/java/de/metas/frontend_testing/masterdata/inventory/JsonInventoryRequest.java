package de.metas.frontend_testing.masterdata.inventory;

import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.sales_order.JsonSalesOrderCreateRequest;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonInventoryRequest
{
	@NonNull Identifier warehouse;
	@Nullable ZonedDateTime date;
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
	}
}
