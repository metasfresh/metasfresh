package de.metas.requisition.order_aggregation;

import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeSetInstanceId;

import javax.annotation.Nullable;
import java.util.UUID;

@Value
@Builder
class OrderLineKey
{
	@Nullable ProductId productId;
	@NonNull AttributeSetInstanceId attributeSetInstanceId;
	@Nullable UUID uniqueIdentifier;
}
