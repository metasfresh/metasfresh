package de.metas.frontend_testing.masterdata.pp_order;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.eevolution.api.PPOrderId;

@Value
@Builder
@Jacksonized
public class JsonPPOrderResponse
{
	@NonNull PPOrderId ppOrderId;
	String documentNo;
}
