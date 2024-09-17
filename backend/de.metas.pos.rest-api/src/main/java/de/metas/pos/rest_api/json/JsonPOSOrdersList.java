package de.metas.pos.rest_api.json;

import com.google.common.collect.ImmutableList;
import de.metas.pos.POSOrder;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonPOSOrdersList
{
	@NonNull List<JsonPOSOrder> list;

	public static JsonPOSOrdersList of(@NonNull final List<POSOrder> orders, @NonNull final JsonContext jsonContext)
	{
		return builder()
				.list(orders.stream()
						.map(order -> JsonPOSOrder.of(order, jsonContext))
						.collect(ImmutableList.toImmutableList()))
				.build();
	}
}
