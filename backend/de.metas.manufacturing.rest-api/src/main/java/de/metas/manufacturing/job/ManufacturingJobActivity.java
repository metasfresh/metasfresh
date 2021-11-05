package de.metas.manufacturing.job;

import com.google.common.collect.ImmutableList;
import de.metas.i18n.ITranslatableString;
import de.metas.material.planning.pporder.PPRoutingActivityType;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eevolution.api.PPOrderRoutingActivityId;
import org.eevolution.api.PPOrderRoutingActivityStatus;

import javax.annotation.Nullable;

@Value
@Builder
public class ManufacturingJobActivity
{
	@NonNull ManufacturingJobActivityId id;
	@NonNull String name;
	@NonNull PPRoutingActivityType type;

	@Nullable RawMaterialsIssue rawMaterialsIssue;
	@Nullable FinishedGoodsReceive finishedGoodsReceive;

	@NonNull PPOrderRoutingActivityId orderRoutingActivityId;
	@NonNull PPOrderRoutingActivityStatus status;

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
