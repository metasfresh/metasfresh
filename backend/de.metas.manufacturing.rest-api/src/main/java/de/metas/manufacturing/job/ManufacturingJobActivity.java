package de.metas.manufacturing.job;

import com.google.common.collect.ImmutableList;
import de.metas.i18n.ITranslatableString;
import de.metas.material.planning.pporder.PPRoutingActivityType;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eevolution.api.PPOrderRoutingActivityCode;

import javax.annotation.Nullable;

@Value
@Builder
public class ManufacturingJobActivity
{
	@NonNull ManufacturingJobActivityId id;
	@NonNull PPOrderRoutingActivityCode code;
	@NonNull PPRoutingActivityType type;

	@Nullable RawMaterialsIssue rawMaterialsIssue;
	@Nullable FinishedGoodsReceive finishedGoodsReceive;

	//
	//
	//
	//
	//

	@Value
	@Builder
	public static class RawMaterialsIssue
	{
		@NonNull ImmutableList<RawMaterialsIssueLine> lines;
	}

	@Value
	@Builder
	public static class RawMaterialsIssueLine
	{
		@NonNull ProductId productId;
		@NonNull ITranslatableString productName;
		@NonNull Quantity qtyToIssue;
		@NonNull Quantity qtyIssued;
	}

	@Value
	@Builder
	public static class FinishedGoodsReceive
	{
		@NonNull ImmutableList<FinishedGoodsReceiveLine> lines;
	}

	@Value
	@Builder
	public static class FinishedGoodsReceiveLine
	{
		@NonNull ProductId productId;
		@NonNull ITranslatableString productName;
		@NonNull Quantity qtyToReceive;
		@NonNull Quantity qtyReceived;
		boolean isByOrCoProduct;
	}
}
