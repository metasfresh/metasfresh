package de.metas.frontend_testing.masterdata.sales_order;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonSalesOrderCreateResponse
{
	@NonNull String id;
	@NonNull String documentNo;
	/** Translated into the request language, as the mobile UI shows it (e.g. in the distribution caption) */
	@NonNull String docTypeName;
}
