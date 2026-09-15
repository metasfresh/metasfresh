package de.metas.distribution.mobileui.external_services.sourcedoc;

import de.metas.order.OrderId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class SalesOrderRef
{
	@NonNull OrderId id;
	@NonNull String documentNo;
}
