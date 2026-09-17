package de.metas.payment.sepa.api.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.invoice.InvoiceId;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_PaySelectionLine;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@EqualsAndHashCode
@ToString
class AggregatedInvoicePaySelectionLines implements Iterable<I_C_PaySelectionLine>
{
	private static final int MAX_DESCRIPTION_LENGTH = 256;
	private static final int MAX_REMITTANCE_LENGTH = 35;

	public static AggregatedInvoicePaySelectionLines EMPTY = new AggregatedInvoicePaySelectionLines(ImmutableList.of());

	@Getter
	@NonNull
	private final ImmutableList<I_C_PaySelectionLine> list;

	private AggregatedInvoicePaySelectionLines(@NonNull final Collection<I_C_PaySelectionLine> list)
	{
		this.list = ImmutableList.copyOf(list);
	}

	@Override
	public @NonNull Iterator<I_C_PaySelectionLine> iterator() {return list.iterator();}

	public static AggregatedInvoicePaySelectionLines ofCollection(final Collection<I_C_PaySelectionLine> list)
	{
		return !list.isEmpty() ? new AggregatedInvoicePaySelectionLines(list) : EMPTY;
	}

	@NonNull
	public static Collector<I_C_PaySelectionLine, ?, AggregatedInvoicePaySelectionLines> collect()
	{
		return GuavaCollectors.collectUsingListAccumulator(AggregatedInvoicePaySelectionLines::ofCollection);
	}

	@NonNull
	public BigDecimal getAmt()
	{
		if (list.isEmpty())
		{
			throw new AdempiereException("No lines found");
		}

		return list.stream()
				.map(I_C_PaySelectionLine::getPayAmt)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	@NonNull
	public ImmutableSet<InvoiceId> getInvoiceIds()
	{
		if (list.isEmpty())
		{
			return ImmutableSet.of();
		}

		return list.stream()
				.map(line -> {
					final InvoiceId invoiceId = InvoiceId.ofRepoIdOrNull(line.getC_Invoice_ID());
					if (invoiceId == null)
					{
						throw new AdempiereException("No invoice found for paySelectionLine: " + line);
					}
					return invoiceId;
				})
				.collect(ImmutableSet.toImmutableSet());
	}

	@NonNull
	public String getAggregatedDescription(@NonNull final Function<Collection<InvoiceId>, List<I_C_Invoice>> getInvoicesByInvoiceIds)
	{
		if (getInvoiceIds().isEmpty())
		{
			return "";
		}

		final String aggregatedDescription = getInvoicesByInvoiceIds.apply(getInvoiceIds())
				.stream()
				.map(I_C_Invoice::getDescription)
				.filter(Check::isNotBlank)
				.distinct()
				.collect(Collectors.joining(","));

		return StringUtils.trunc(aggregatedDescription, MAX_DESCRIPTION_LENGTH);
	}

	@NonNull
	public String getAggregatedRemittanceInfo()
	{
		if (list.isEmpty())
		{
			return "";
		}

		final String aggregatedRemittanceInfo = list.stream()
				.map(I_C_PaySelectionLine::getReference)
				.filter(Check::isNotBlank)
				.distinct()
				.collect(Collectors.joining(","));

		return StringUtils.trunc(aggregatedRemittanceInfo, MAX_REMITTANCE_LENGTH);
	}

	public boolean isSinglePaySelectionLineGroup() {return list.size() == 1;}

	public boolean isMultiplePaySelectionLinesGroup() {return list.size() > 1;}

	@NonNull
	public Stream<I_C_PaySelectionLine> stream() {return list.stream();}

	public int size() {return list.size();}
}
