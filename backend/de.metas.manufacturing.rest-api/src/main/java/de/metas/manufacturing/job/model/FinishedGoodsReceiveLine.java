package de.metas.manufacturing.job.model;

import de.metas.i18n.ITranslatableString;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.workflow.rest_api.model.WFActivityStatus;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.eevolution.api.PPOrderBOMLineId;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
public class FinishedGoodsReceiveLine
{
	@NonNull FinishedGoodsReceiveLineId id;
	@NonNull ProductId productId;
	@NonNull ITranslatableString productName;
	@NonNull ImmutableAttributeSet attributes;
	@NonNull Quantity qtyToReceive;
	@NonNull Quantity qtyReceived;
	@Nullable PPOrderBOMLineId coProductBOMLineId;

	@Nullable ReceivingTarget receivingTarget;

	@NonNull WFActivityStatus status;

	@Builder(toBuilder = true)
	private FinishedGoodsReceiveLine(
			@NonNull final ProductId productId,
			@NonNull final ITranslatableString productName,
			@NonNull final ImmutableAttributeSet attributes,
			@NonNull final Quantity qtyToReceive,
			@NonNull final Quantity qtyReceived,
			@Nullable final PPOrderBOMLineId coProductBOMLineId,
			@Nullable final ReceivingTarget receivingTarget)
	{
		this.productId = productId;
		this.productName = productName;
		this.attributes = attributes;
		this.qtyToReceive = qtyToReceive;
		this.qtyReceived = qtyReceived;
		this.coProductBOMLineId = coProductBOMLineId;

		this.receivingTarget = receivingTarget;

		this.id = coProductBOMLineId == null
				? FinishedGoodsReceiveLineId.FINISHED_GOODS
				: FinishedGoodsReceiveLineId.ofCOProductBOMLineId(coProductBOMLineId);

		this.status = computeStatus(qtyToReceive, qtyReceived);
	}

	private static WFActivityStatus computeStatus(
			@NonNull final Quantity qtyToReceive,
			@NonNull final Quantity qtyReceived)
	{
		if (qtyReceived.isZero())
		{
			return WFActivityStatus.NOT_STARTED;
		}

		final Quantity qtyToReceiveRemaining = qtyToReceive.subtract(qtyReceived);
		return qtyToReceiveRemaining.signum() != 0 ? WFActivityStatus.IN_PROGRESS : WFActivityStatus.COMPLETED;
	}

	public FinishedGoodsReceiveLine withReceivingTarget(@Nullable final ReceivingTarget receivingTarget)
	{
		return !Objects.equals(this.receivingTarget, receivingTarget)
				? toBuilder().receivingTarget(receivingTarget).build()
				: this;
	}
}
