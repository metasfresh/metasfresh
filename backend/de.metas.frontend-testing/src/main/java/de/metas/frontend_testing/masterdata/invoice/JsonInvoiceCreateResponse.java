package de.metas.frontend_testing.masterdata.invoice;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonInvoiceCreateResponse
{
	@NonNull String id;
	@NonNull String documentNo;
}
