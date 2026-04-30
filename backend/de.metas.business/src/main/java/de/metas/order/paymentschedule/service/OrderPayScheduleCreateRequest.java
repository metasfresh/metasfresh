package de.metas.order.paymentschedule.service;

import com.google.common.collect.ImmutableList;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.order.paymentschedule.OrderPayScheduleStatus;
import de.metas.payment.paymentterm.PaymentTermBreakId;
import de.metas.payment.paymentterm.ReferenceDateType;
import de.metas.util.Check;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.LocalDate;

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

		@NonNull OrderPayScheduleStatus status;
		@Nullable LocalDate referenceDate;
		@NonNull LocalDate dueDate;
		@NonNull Money dueAmount;
		/**
		 * Base amount for this schedule line.
		 * For LC / initial OD rows this equals the order GrandTotal.
		 * For iter-3 Delivery sub-rows it equals the receipt with-tax value.
		 */
		@NonNull Money baseAmount;

		int offsetDays;

		public boolean isPaid() {return status.isPaid();}
	}
}
