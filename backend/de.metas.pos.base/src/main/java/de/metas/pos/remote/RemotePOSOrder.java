package de.metas.pos.remote;

import de.metas.pos.POSOrderExternalId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class RemotePOSOrder
{
	@NonNull POSOrderExternalId uuid;
	@NonNull List<RemotePOSOrderLine> lines;
	@NonNull List<RemotePOSPayment> payments;
}
