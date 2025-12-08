package de.metas.frontend_testing.masterdata.pp_order;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonPPOrderResponse
{
	String documentNo;
}
