package de.metas.order.costs.invoice;

import com.google.common.collect.ImmutableList;
import de.metas.currency.CurrencyPrecision;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.util.lang.Percent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import java.util.Iterator;
import java.util.List;

@EqualsAndHashCode
@ToString
public class CreateMatchInvoicePlan implements Iterable<CreateMatchInvoicePlanLine>
{
	@Getter
	@NonNull private final ImmutableList<CreateMatchInvoicePlanLine> lines;

	private CreateMatchInvoicePlan(@NonNull final List<CreateMatchInvoicePlanLine> lines)
	{
		if (lines.isEmpty())
		{
			throw new AdempiereException("No plan lines");
		}

		this.lines = ImmutableList.copyOf(lines);
	}

	public static CreateMatchInvoicePlan ofList(List<CreateMatchInvoicePlanLine> lines)
	{
		return new CreateMatchInvoicePlan(lines);
	}

	public boolean isEmpty()
	{
		return lines.isEmpty();
	}

	@Override
	public Iterator<CreateMatchInvoicePlanLine> iterator()
	{
		return lines.iterator();
	}

	public void setCostAmountInvoiced(@NonNull final Money invoicedAmt, @NonNull final CurrencyPrecision precision)
	{
		final CurrencyId currencyId = invoicedAmt.getCurrencyId();
		final Money totalCostAmountInOut = getCostAmountInOut();
		final Money totalInvoicedAmtDiff = invoicedAmt.subtract(totalCostAmountInOut);
		if (totalInvoicedAmtDiff.isZero())
		{
			return;
		}

		if (isEmpty())
		{
			throw new AdempiereException("Cannot spread invoice amount difference if there is no line");
		}

		Money totalInvoicedAmtDiffDistributed = Money.zero(currencyId);
		for (int i = 0, lastIndex = lines.size() - 1; i <= lastIndex; i++)
		{
			final CreateMatchInvoicePlanLine line = lines.get(i);
			final Money lineCostAmountInOut = line.getCostAmountInOut();
			final Money lineInvoicedAmtDiff;
			if (i != lastIndex)
			{
				final Percent percentage = lineCostAmountInOut.percentageOf(totalCostAmountInOut);
				lineInvoicedAmtDiff = totalInvoicedAmtDiff.multiply(percentage, precision);
			}
			else
			{
				lineInvoicedAmtDiff = totalInvoicedAmtDiff.subtract(totalInvoicedAmtDiffDistributed);
			}

			line.setCostAmountInvoiced(lineCostAmountInOut.add(lineInvoicedAmtDiff));

			totalInvoicedAmtDiffDistributed = totalInvoicedAmtDiffDistributed.add(lineInvoicedAmtDiff);
		}
	}

	private Money getCostAmountInOut()
	{
		return lines.stream().map(CreateMatchInvoicePlanLine::getCostAmountInOut).reduce(Money::add)
				.orElseThrow(() -> new AdempiereException("No lines")); // shall not happen
	}

	public Money getInvoicedAmountDiff()
	{
		return lines.stream().map(CreateMatchInvoicePlanLine::getInvoicedAmountDiff).reduce(Money::add)
				.orElseThrow(() -> new AdempiereException("No lines")); // shall not happen
	}

}
