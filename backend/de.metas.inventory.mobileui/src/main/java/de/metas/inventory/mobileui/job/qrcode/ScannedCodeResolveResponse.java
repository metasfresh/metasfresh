package de.metas.inventory.mobileui.job.qrcode;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HuId;
import de.metas.inventory.InventoryLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class ScannedCodeResolveResponse
{
	public static final ScannedCodeResolveResponse EMPTY = ScannedCodeResolveResponse.builder().build();
	
	@NonNull @Singular ImmutableList<EligibleLine> eligibleLines;

	//
	//
	//

	@Value
	@Builder
	public static class EligibleLine
	{
		@Nullable InventoryLineId lineId;
		@NonNull @Singular("eligibleHU") ImmutableList<EligibleHU> eligibleHUs;
	}

	//
	//
	//

	@Value
	@Builder
	public static class EligibleHU
	{
		@Nullable HuId huId;
		@NonNull ProductId productId;
		@NonNull Quantity qty;
	}
}
