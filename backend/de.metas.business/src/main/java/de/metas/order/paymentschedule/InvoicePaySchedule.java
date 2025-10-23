package de.metas.order.paymentschedule;

import com.google.common.collect.ImmutableList;
import de.metas.invoice.InvoiceId;
import de.metas.util.GuavaCollectors;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collector;

@EqualsAndHashCode
@ToString
public class InvoicePaySchedule
{
	@NonNull @Getter InvoiceId invoiceId;
	@NonNull @Getter private final ImmutableList<InvoicePayScheduleLine> lines;

	private InvoicePaySchedule(@NonNull final InvoiceId invoiceId, @NonNull final List<InvoicePayScheduleLine> lines)
	{
		this.invoiceId = invoiceId;
		this.lines = ImmutableList.copyOf(lines);
	}

	public static InvoicePaySchedule ofList(@NonNull final InvoiceId invoiceId, @NonNull final List<InvoicePayScheduleLine> lines)
	{
		return new InvoicePaySchedule(invoiceId, lines);
	}

	public static Collector<InvoicePayScheduleLine, ?, Optional<InvoicePaySchedule>> collect()
	{
		return GuavaCollectors.collectUsingListAccumulator((lines) -> {
			if (lines.isEmpty())
			{
				return Optional.empty();
			}
			final InvoiceId invoiceId = lines.get(0).getInvoiceId();
			return Optional.of(new InvoicePaySchedule(invoiceId, lines));
		});
	}

	public void markAsValid(final boolean isValid)
	{
		lines.forEach(line -> line.setValid(isValid));
	}

	public boolean validate(@NonNull final BigDecimal grandTotal)
	{
		final AtomicBoolean isValid = new AtomicBoolean(false);

		final ImmutableList<InvoicePayScheduleLine> schedules = getLines();
		if (schedules.isEmpty())
		{
			return false;
		}

		final BigDecimal totalDue = schedules.stream()
				.map(line -> line.getDueAmount().toBigDecimal())
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		isValid.set(grandTotal.compareTo(totalDue) == 0);

		markAsValid(isValid.get());

		return isValid.get();
	}
}
