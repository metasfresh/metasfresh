package de.metas.inventory.mobileui.rest_api.json;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HuId;
import de.metas.inventory.InventoryLineId;
import de.metas.inventory.mobileui.job.qrcode.ResolveHUResponse;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonResolveHUResponse
{
	@Nullable InventoryLineId lineId;
	@Nullable HuId huId;
	@NonNull ProductId productId;
	@NonNull String uom;
	@NonNull BigDecimal qtyBooked;
	@NonNull List<JsonResolveHUResponseAttribute> attributes;

	public static JsonResolveHUResponse of(@NonNull final ResolveHUResponse response, @NonNull final String adLanguage)
	{
		return builder()
				.lineId(response.getLineId())
				.huId(response.getHuId())
				.productId(response.getProductId())
				.uom(response.getQtyBooked().getUOMSymbol())
				.qtyBooked(response.getQtyBooked().toBigDecimal())
				.attributes(response.getAttributes()
						.stream()
						.map(attribute -> JsonResolveHUResponseAttribute.of(attribute, adLanguage))
						.collect(ImmutableList.toImmutableList()))
				.build();
	}
}
