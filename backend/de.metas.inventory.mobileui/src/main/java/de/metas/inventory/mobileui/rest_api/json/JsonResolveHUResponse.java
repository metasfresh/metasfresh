package de.metas.inventory.mobileui.rest_api.json;

import com.fasterxml.jackson.annotation.JsonFormat;
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
import java.time.LocalDate;

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

	boolean hasBestBeforeDateAttribute;
	@Nullable @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") LocalDate bestBeforeDate;
	boolean hasLotNoAttribute;
	@Nullable String lotNo;

	public static JsonResolveHUResponse of(@NonNull final ResolveHUResponse response)
	{
		return builder()
				.lineId(response.getLineId())
				.huId(response.getHuId())
				.productId(response.getProductId())
				.uom(response.getQtyBooked().getUOMSymbol())
				.qtyBooked(response.getQtyBooked().toBigDecimal())
				.hasBestBeforeDateAttribute(response.isHasBestBeforeDateAttribute())
				.bestBeforeDate(response.getBestBeforeDate())
				.hasLotNoAttribute(response.isHasLotNoAttribute())
				.lotNo(response.getLotNo())
				.build();
	}
}
