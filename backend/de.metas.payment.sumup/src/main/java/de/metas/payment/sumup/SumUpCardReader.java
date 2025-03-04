package de.metas.payment.sumup;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class SumUpCardReader
{
	@NonNull String name;
	@NonNull SumUpCardReaderExternalId externalId;
	boolean isActive;
}
