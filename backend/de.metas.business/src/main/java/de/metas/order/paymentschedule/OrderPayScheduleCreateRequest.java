package de.metas.order.paymentschedule;

import com.google.common.collect.ImmutableList;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.payment.paymentterm.PaymentTermBreakId;
import de.metas.payment.paymentterm.ReferenceDateType;
import de.metas.util.Check;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.Instant;

@Value
public class OrderPayScheduleCreateRequest
{
	@NonNull OrderId orderId;
	@NonNull ImmutableList<Line> lines;

	@Builder
	private OrderPayScheduleCreateRequest(
			@NonNull final OrderId orderId,
			@NonNull final ImmutableList<Line> lines)
	{
		Check.assumeNotEmpty(lines, "lines shall not empty");
		
		this.orderId = orderId;
		this.lines = lines;
	}

	//
	//
	//
	//
	//

	@Value
	@Builder
	public static class Line
	{
		@NonNull PaymentTermBreakId paymentTermBreakId;
		@NonNull ReferenceDateType referenceDateType;
		@NonNull Percent percent;

		@NonNull OrderPayScheduleStatus orderPayScheduleStatus;
		@NonNull Instant dueDate;
		@NonNull Money dueAmount;
	}
}
