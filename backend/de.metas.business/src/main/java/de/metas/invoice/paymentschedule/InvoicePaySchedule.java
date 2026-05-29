package de.metas.invoice.paymentschedule;

import com.google.common.collect.ImmutableList;
import de.metas.invoice.InvoiceId;
import de.metas.money.Money;
import de.metas.util.GuavaCollectors;
import de.metas.util.collections.CollectionUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;

@EqualsAndHashCode
@ToString
public class InvoicePaySchedule
{
	@NonNull @Getter private final ImmutableList<InvoicePayScheduleLine> lines;
	@NonNull @Getter private final InvoiceId invoiceId;

	private InvoicePaySchedule(@NonNull final List<InvoicePayScheduleLine> lines)
	{
		if (lines.isEmpty())
		{
			throw AdempiereException.noLines();
		}

		this.lines = ImmutableList.copyOf(lines);
		this.invoiceId = CollectionUtils.extractSingleElement(lines, InvoicePayScheduleLine::getInvoiceId);
	}

	public static InvoicePaySchedule ofList(@NonNull final List<InvoicePayScheduleLine> lines)
	{
		return new InvoicePaySchedule(lines);
	}

	public static Optional<InvoicePaySchedule> optionalOfList(@NonNull final List<InvoicePayScheduleLine> lines)
	{
		return lines.isEmpty() ? Optional.empty() : Optional.of(ofList(lines));
	}

	public static Collector<InvoicePayScheduleLine, ?, Optional<InvoicePaySchedule>> collect()
	{
		return GuavaCollectors.collectUsingListAccumulator(InvoicePaySchedule::optionalOfList);
	}

	public boolean isValid()
	{
		return lines.stream().allMatch(InvoicePayScheduleLine::isValid);
	}

	public boolean validate(@NonNull final Money invoiceGrandTotal)
	{
		final boolean isValid = computeIsValid(invoiceGrandTotal);
		markAsValid(isValid);
		return isValid;
	}

	private boolean computeIsValid(@NonNull final Money invoiceGrandTotal)
	{
		final Money totalDue = getTotalDueAmt();
		return invoiceGrandTotal.isEqualByComparingTo(totalDue);
	}

	private void markAsValid(final boolean isValid)
	{
		lines.forEach(line -> line.setValid(isValid));
	}

	private Money getTotalDueAmt()
	{
		return getLines()
				.stream()
				.map(InvoicePayScheduleLine::getDueAmount)
				.reduce(Money::add)
				.orElseThrow(() -> new AdempiereException("No lines"));
	}
}
