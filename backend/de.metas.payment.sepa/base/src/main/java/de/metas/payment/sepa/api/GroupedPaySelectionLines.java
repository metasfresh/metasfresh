package de.metas.payment.sepa.api;

import com.google.common.collect.ImmutableList;
import de.metas.invoice.InvoiceId;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_PaySelectionLine;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Stream;

@EqualsAndHashCode
@ToString
public class GroupedPaySelectionLines implements Iterable<I_C_PaySelectionLine>
{
	public static GroupedPaySelectionLines EMPTY = new GroupedPaySelectionLines(ImmutableList.of());

	@Getter
	@NonNull
	private final ImmutableList<I_C_PaySelectionLine> list;

	private GroupedPaySelectionLines(@NonNull final Collection<I_C_PaySelectionLine> list)
	{
		this.list = list.stream()
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public @NonNull Iterator<I_C_PaySelectionLine> iterator() {return list.iterator();}

	public static GroupedPaySelectionLines ofCollection(final Collection<I_C_PaySelectionLine> list)
	{
		return !list.isEmpty() ? new GroupedPaySelectionLines(list) : EMPTY;
	}

	@NonNull
	public static Collector<I_C_PaySelectionLine, ?, GroupedPaySelectionLines> collect()
	{
		return GuavaCollectors.collectUsingListAccumulator(GroupedPaySelectionLines::ofCollection);
	}

	@NonNull
	public BigDecimal getAmt()
	{
		return list.stream()
				.map(I_C_PaySelectionLine::getPayAmt)
				.reduce(BigDecimal::add)
				.orElseThrow(() -> new AdempiereException("No Amt found in " + list));
	}

	@NonNull
	public String getAggregatedDescription(@NonNull final Function<InvoiceId, String> invoiceId2Description)
	{
		final List<String> aggregatedDescription = list.stream()
				.map(line -> {
					final InvoiceId invoiceId = InvoiceId.ofRepoId(line.getC_Invoice_ID());
					return invoiceId2Description.apply(invoiceId);
				})
				.filter(Check::isNotBlank)
				.collect(ImmutableList.toImmutableList());

		if (Check.isEmpty(aggregatedDescription))
		{
			return "";
		}

		return String.join(",", aggregatedDescription);
	}

	@NonNull
	public String getAggregatedRemittanceInfo()
	{
		final List<String> aggregatedRemittanceInfo = list.stream()
				.map(I_C_PaySelectionLine::getReference)
				.filter(Check::isNotBlank)
				.collect(ImmutableList.toImmutableList());

		if (Check.isEmpty(aggregatedRemittanceInfo))
		{
			return "";
		}

		return String.join(",", aggregatedRemittanceInfo);
	}

	public boolean isSinglePaySelectionLineGroup() {return list.size() == 1;}

	public boolean isMultiplePaySelectionLinesGroup() {return list.size() > 1;}
	
	@NonNull
	public Stream<I_C_PaySelectionLine> stream() {return list.stream();}
}
