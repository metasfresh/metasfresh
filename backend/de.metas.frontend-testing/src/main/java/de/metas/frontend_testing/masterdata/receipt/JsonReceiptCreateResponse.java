package de.metas.frontend_testing.masterdata.receipt;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonReceiptCreateResponse
{
	@NonNull String id;
	@NonNull String documentNo;
}
