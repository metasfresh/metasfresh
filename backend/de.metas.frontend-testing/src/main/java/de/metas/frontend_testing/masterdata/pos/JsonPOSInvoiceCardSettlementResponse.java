package de.metas.frontend_testing.masterdata.pos;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonPOSInvoiceCardSettlementResponse
{
	int id;
}
