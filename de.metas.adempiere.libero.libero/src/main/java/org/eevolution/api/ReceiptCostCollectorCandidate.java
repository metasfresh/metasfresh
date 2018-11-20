package org.eevolution.api;

import java.time.LocalDateTime;

import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.LocatorId;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;

import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.time.SystemTime;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * Instances of this class can be passed to {@link IPPCostCollectorBL#createReceipt(IReceiptCostCollectorCandidate)} to have it generate and process a receipt. <br>
 * <p>
 * Note that in the context of a "co-product", a receipt is a negative issue (but that should not bother the user).
 *
 */
@Value
public class ReceiptCostCollectorCandidate
{
	I_PP_Order PP_Order;
	/** manufacturing order's BOM Line if this is a co/by-product receipt; <code>null</code> otherwise */
	I_PP_Order_BOMLine PP_Order_BOMLine;

	LocalDateTime movementDate;

	ProductId productId;

	Quantity qtyToReceive;
	Quantity qtyScrap;
	Quantity qtyReject;

	LocatorId locatorId;
	AttributeSetInstanceId attributeSetInstanceId;

	@Builder(toBuilder = true)
	private ReceiptCostCollectorCandidate(
			final I_PP_Order PP_Order,
			final I_PP_Order_BOMLine PP_Order_BOMLine,
			final LocalDateTime movementDate,
			final ProductId productId,
			@NonNull final Quantity qtyToReceive,
			final Quantity qtyScrap,
			final Quantity qtyReject,
			final LocatorId locatorId,
			final AttributeSetInstanceId attributeSetInstanceId)
	{
		this.PP_Order = PP_Order;
		this.PP_Order_BOMLine = PP_Order_BOMLine;
		this.movementDate = movementDate != null ? movementDate : SystemTime.asLocalDateTime();
		this.productId = productId;
		this.qtyToReceive = qtyToReceive;
		this.qtyScrap = qtyScrap != null ? qtyScrap : qtyToReceive.toZero();
		this.qtyReject = qtyReject != null ? qtyReject : qtyToReceive.toZero();
		this.locatorId = locatorId;
		this.attributeSetInstanceId = attributeSetInstanceId != null ? attributeSetInstanceId : AttributeSetInstanceId.NONE;
	}
}
