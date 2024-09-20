package de.metas.pos.remote;

import de.metas.pos.POSOrderExternalId;
import de.metas.pos.POSOrderStatus;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;

@Value
@Builder
public class RemotePOSOrder
{
	@NonNull POSOrderExternalId uuid;
	@NonNull List<RemotePOSOrderLine> lines;
	@NonNull List<RemotePOSPayment> payments;
}
