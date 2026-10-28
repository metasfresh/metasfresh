package de.metas.order.paymentschedule.referenced_docs.material_receipt;

import com.google.common.collect.ImmutableList;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.LocalDate;

/**
 * Snapshot of one completed receipt relevant to this order's delivery sub-rows.
 */
@Value
@Builder
public class MaterialReceipt
{
	@NonNull InOutId id;
	@NonNull OrderId orderId;
	@NonNull LocalDate movementDate;
	@NonNull ImmutableList<Line> lines;

	public boolean containsInOutLineId(@NonNull final InOutLineId inOutLineId)
	{
		return lines.stream().anyMatch(line -> InOutLineId.equals(line.getId(), inOutLineId));
	}

	//
	//
	//

	@Value
	@Builder
	public static class Line
	{
		@NonNull InOutLineId id;
		@NonNull Quantity movementQty;
		@Nullable OrderAndLineId orderLineId;
	}
}
