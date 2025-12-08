package de.metas.pos.payment_gateway;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class POSCardReader
{
	@NonNull POSCardReaderExternalId externalId;
	@NonNull String name;
}
