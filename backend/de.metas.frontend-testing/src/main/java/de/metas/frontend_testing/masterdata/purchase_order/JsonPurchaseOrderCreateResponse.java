package de.metas.frontend_testing.masterdata.purchase_order;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonPurchaseOrderCreateResponse
{
	@NonNull String id;
	@NonNull String documentNo;
}
