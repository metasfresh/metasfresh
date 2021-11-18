package de.metas.manufacturing.job.model;

import de.metas.handlingunits.HuId;
import de.metas.i18n.ITranslatableString;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eevolution.api.PPOrderBOMLineId;

import javax.annotation.Nullable;

@Value
public class FinishedGoodsReceiveLine
{
	@NonNull FinishedGoodsReceiveLineId id;
	@NonNull ProductId productId;
	@NonNull ITranslatableString productName;
	@NonNull Quantity qtyToReceive;
	@NonNull Quantity qtyReceived;
	@Nullable PPOrderBOMLineId coProductBOMLineId;

	@Nullable HuId aggregateToLUId;

	@Builder(toBuilder = true)
	private FinishedGoodsReceiveLine(
			@NonNull final ProductId productId,
			@NonNull final ITranslatableString productName,
			@NonNull final Quantity qtyToReceive,
			@NonNull final Quantity qtyReceived,
			@Nullable final PPOrderBOMLineId coProductBOMLineId,
			@Nullable final HuId aggregateToLUId)
	{
		this.productId = productId;
		this.productName = productName;
		this.qtyToReceive = qtyToReceive;
		this.qtyReceived = qtyReceived;
		this.coProductBOMLineId = coProductBOMLineId;

		this.aggregateToLUId = aggregateToLUId;

		this.id = coProductBOMLineId == null
				? FinishedGoodsReceiveLineId.FINISHED_GOODS
				: FinishedGoodsReceiveLineId.ofCOProductBOMLineId(coProductBOMLineId);
	}

	public FinishedGoodsReceiveLine withAggregateToLUId(@NonNull final HuId aggregateToLUId)
	{
		return !HuId.equals(this.aggregateToLUId, aggregateToLUId)
				? toBuilder().aggregateToLUId(aggregateToLUId).build()
				: this;
	}
}
