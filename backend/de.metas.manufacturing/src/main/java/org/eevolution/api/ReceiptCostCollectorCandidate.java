package org.eevolution.api;

import de.metas.common.util.time.SystemTime;
import de.metas.material.planning.pporder.PPOrderBOMLineId;
import de.metas.material.planning.pporder.PPOrderId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.LocatorId;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

/**
 * Instances of this class can be passed to {@link IPPCostCollectorBL#createReceipt(ReceiptCostCollectorCandidate)} to have it generate and process a receipt. <br>
 * <p>
 * Note that in the context of a "co-product", a receipt is a negative issue (but that should not bother the user).
 */
@Value
public class ReceiptCostCollectorCandidate
{
	I_PP_Order order;
	/**
	 * manufacturing order's BOM Line if this is a co/by-product receipt; <code>null</code> otherwise
	 */
	I_PP_Order_BOMLine orderBOMLine;

	ZonedDateTime movementDate;

	ProductId productId;

	Quantity qtyToReceive;
	Quantity qtyScrap;
	Quantity qtyReject;

	LocatorId locatorId;
	AttributeSetInstanceId attributeSetInstanceId;

	int pickingCandidateId;

	@Builder
	private ReceiptCostCollectorCandidate(
			@NonNull final I_PP_Order order,
			@Nullable final I_PP_Order_BOMLine orderBOMLine,
			@Nullable final ZonedDateTime movementDate,
			@NonNull final ProductId productId,
			@NonNull final Quantity qtyToReceive,
			@Nullable final Quantity qtyScrap,
			@Nullable final Quantity qtyReject,
			@Nullable final LocatorId locatorId,
			@Nullable final AttributeSetInstanceId attributeSetInstanceId,
			//
			final int pickingCandidateId)
	{
		this.order = order;
		this.orderBOMLine = orderBOMLine;
		this.movementDate = movementDate != null ? movementDate : SystemTime.asZonedDateTime();
		this.productId = productId;
		this.qtyToReceive = qtyToReceive;
		this.qtyScrap = qtyScrap != null ? qtyScrap : qtyToReceive.toZero();
		this.qtyReject = qtyReject != null ? qtyReject : qtyToReceive.toZero();
		this.locatorId = locatorId != null ? locatorId : extractLocatorId(order);
		this.attributeSetInstanceId = attributeSetInstanceId != null ? attributeSetInstanceId : AttributeSetInstanceId.NONE;
		this.pickingCandidateId = pickingCandidateId > 0 ? pickingCandidateId : -1;
	}

	private static LocatorId extractLocatorId(final I_PP_Order order)
	{
		return LocatorId.ofRepoId(order.getM_Warehouse_ID(), order.getM_Locator_ID());
	}

	public PPOrderId getOrderId()
	{
		return PPOrderId.ofRepoId(getOrder().getPP_Order_ID());
	}

	@Nullable
	public PPOrderBOMLineId getOrderBOMLineId()
	{
		final I_PP_Order_BOMLine orderBOMLine = getOrderBOMLine();
		return orderBOMLine != null
				? PPOrderBOMLineId.ofRepoId(orderBOMLine.getPP_Order_BOMLine_ID())
				: null;
	}

	public boolean isCoOrByProductReceipt()
	{
		return getOrderBOMLine() != null;
	}
}
