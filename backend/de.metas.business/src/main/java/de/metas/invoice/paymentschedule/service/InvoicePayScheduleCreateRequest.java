package de.metas.invoice.paymentschedule.service;

import com.google.common.collect.ImmutableList;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.paymentschedule.InvoicePayScheduleLineId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.paymentschedule.OrderAndPayScheduleId;
import de.metas.payment.paymentterm.PayScheduleId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.List;

@Value
public class InvoicePayScheduleCreateRequest
{
	@NonNull InvoiceId invoiceId;
	@NonNull ImmutableList<Line> lines;

	@Builder
	private InvoicePayScheduleCreateRequest(
			@NonNull final InvoiceId invoiceId,
			@NonNull final List<Line> lines)
	{
		Check.assumeNotEmpty(lines, "lines shall not empty");

		this.invoiceId = invoiceId;
		this.lines = ImmutableList.copyOf(lines);
	}

	//

	@Value
	@Builder
	public static class Line
	{
		@Builder.Default boolean valid = true;

		@NonNull Instant dueDate;
		@NonNull Money dueAmount;

		@Nullable Instant discountDate;
		@Nullable Money discountAmount;

		@Nullable OrderAndPayScheduleId orderAndPayScheduleId;
		@Nullable PayScheduleId paymentTermScheduleId;

		public CurrencyId getCurrencyId() {return Money.getCommonCurrencyIdOfAll(dueAmount, discountAmount);}
	}
}
