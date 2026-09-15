package de.metas.doctextline;

import de.metas.inout.InOutId;
import de.metas.order.OrderId;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

/**
 * The one document a {@link DocTextLine} belongs to: exactly one of order or shipment/receipt.
 */
@Value
public class DocTextLineDocumentRef
{
	@Nullable OrderId orderId;
	@Nullable InOutId inOutId;

	public static DocTextLineDocumentRef ofOrderId(@NonNull final OrderId orderId)
	{
		return new DocTextLineDocumentRef(orderId, null);
	}

	public static DocTextLineDocumentRef ofInOutId(@NonNull final InOutId inOutId)
	{
		return new DocTextLineDocumentRef(null, inOutId);
	}

	private DocTextLineDocumentRef(@Nullable final OrderId orderId, @Nullable final InOutId inOutId)
	{
		Check.assume((orderId == null) != (inOutId == null), "Exactly one of orderId/inOutId must be set");
		this.orderId = orderId;
		this.inOutId = inOutId;
	}
}
