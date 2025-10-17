package de.metas.order.paymentschedule;

import com.google.common.collect.ImmutableList;
import de.metas.invoice.InvoiceId;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.Instant;

@Value
public class InvoicePayScheduleCreateRequest
{
	@NonNull InvoiceId invoiceId;
	@NonNull ImmutableList<Line> lines;

	@Builder
	private InvoicePayScheduleCreateRequest(
			@NonNull final InvoiceId invoiceId,
			@NonNull final ImmutableList<Line> lines)
	{
		Check.assumeNotEmpty(lines, "lines shall not empty");

		this.invoiceId = invoiceId;
		this.lines = lines;
	}

	//

	@Value
	@Builder
	public static class Line
	{
		@NonNull OrderId orderId;
		@NonNull OrderPayScheduleId orderPayScheduleId;
		@NonNull Instant dueDate;
		@NonNull Money dueAmount;

	}
}
