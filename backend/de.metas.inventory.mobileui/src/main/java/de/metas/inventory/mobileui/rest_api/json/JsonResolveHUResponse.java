package de.metas.inventory.mobileui.rest_api.json;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HuId;
import de.metas.inventory.InventoryLineId;
import de.metas.inventory.mobileui.job.qrcode.ScannedCodeResolveResponse;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
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
	@NonNull @Singular ImmutableList<JsonEligibleLine> eligibleLines;

	public static JsonResolveHUResponse of(final ScannedCodeResolveResponse response)
	{
		return builder()
				.eligibleLines(response.getEligibleLines()
						.stream()
						.map(JsonEligibleLine::of)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	//
	//
	//

	@Value
	@Builder
	public static class JsonEligibleLine
	{
		@Nullable InventoryLineId lineId;
		@NonNull @Singular("eligibleHU") ImmutableList<JsonEligibleHU> eligibleHUs;

		private static JsonEligibleLine of(final ScannedCodeResolveResponse.EligibleLine line)
		{
			return builder()
					.lineId(line.getLineId())
					.eligibleHUs(line.getEligibleHUs()
							.stream()
							.map(JsonEligibleHU::of)
							.collect(ImmutableList.toImmutableList())
					)
					.build();
		}
	}

	//
	//
	//

	@Value
	@Builder
	@Jacksonized
	public static class JsonEligibleHU
	{
		@Nullable HuId huId;
		@NonNull ProductId productId;
		@NonNull String uom;
		@NonNull BigDecimal qty;

		boolean hasBestBeforeDateAttribute;
		@Nullable @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") LocalDate bestBeforeDate;
		boolean hasLotNoAttribute;
		@Nullable String lotNo;

		private static JsonEligibleHU of(final ScannedCodeResolveResponse.EligibleHU hu)
		{
			return builder()
					.huId(hu.getHuId())
					.productId(hu.getProductId())
					.uom(hu.getQty().getUOMSymbol())
					.qty(hu.getQty().toBigDecimal())
					.hasBestBeforeDateAttribute(hu.isHasBestBeforeDateAttribute())
					.bestBeforeDate(hu.getBestBeforeDate())
					.hasLotNoAttribute(hu.isHasLotNoAttribute())
					.lotNo(hu.getLotNo())
					.build();
		}

	}

}
