package de.metas.order.costs.invoice;

import com.google.common.collect.ImmutableList;
import de.metas.currency.CurrencyPrecision;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.util.lang.Percent;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import java.util.Iterator;
import java.util.List;

@EqualsAndHashCode
@ToString
public class CreateMatchInvoicePlan implements Iterable<CreateMatchInvoicePlanLine>
{
	@NonNull private final ImmutableList<CreateMatchInvoicePlanLine> list;

	private CreateMatchInvoicePlan(@NonNull final List<CreateMatchInvoicePlanLine> list)
	{
		if (list.isEmpty())
		{
			throw new AdempiereException("No plan lines");
		}

		this.list = ImmutableList.copyOf(list);
	}

	public static CreateMatchInvoicePlan ofList(List<CreateMatchInvoicePlanLine> list)
	{
		return new CreateMatchInvoicePlan(list);
	}

	public boolean isEmpty()
	{
		return list.isEmpty();
	}

	@Override
	public Iterator<CreateMatchInvoicePlanLine> iterator()
	{
		return list.iterator();
	}

	public void setCostAmountInvoiced(@NonNull final Money invoicedAmt, @NonNull final CurrencyPrecision precision)
	{
		final CurrencyId currencyId = invoicedAmt.getCurrencyId();
		final Money totalCostAmountReceived = getReceiptCostAmount();
		final Money totalInvoicedAmtDiff = invoicedAmt.subtract(totalCostAmountReceived);
		if (totalInvoicedAmtDiff.isZero())
		{
			return;
		}

		if (isEmpty())
		{
			throw new AdempiereException("Cannot spread invoice amount difference if there is no line");
		}

		Money totalInvoicedAmtDiffDistributed = Money.zero(currencyId);
		for (int i = 0, lastIndex = list.size() - 1; i <= lastIndex; i++)
		{
			final CreateMatchInvoicePlanLine line = list.get(i);
			final Money lineCostAmountReceived = line.getCostAmountReceived();
			final Money lineInvoicedAmtDiff;
			if (i != lastIndex)
			{
				final Percent percentage = lineCostAmountReceived.percentageOf(totalCostAmountReceived);
				lineInvoicedAmtDiff = totalInvoicedAmtDiff.multiply(percentage, precision);
			}
			else
			{
				lineInvoicedAmtDiff = totalInvoicedAmtDiff.subtract(totalInvoicedAmtDiffDistributed);
			}

			line.setCostAmountInvoiced(lineCostAmountReceived.add(lineInvoicedAmtDiff));

			totalInvoicedAmtDiffDistributed = totalInvoicedAmtDiffDistributed.add(lineInvoicedAmtDiff);
		}
	}

	private Money getReceiptCostAmount()
	{
		return list.stream().map(CreateMatchInvoicePlanLine::getCostAmountReceived).reduce(Money::add)
				.orElseThrow(() -> new AdempiereException("No lines")); // shall not happen
	}

	public Money getInvoicedAmountDiff()
	{
		return list.stream().map(CreateMatchInvoicePlanLine::getInvoicedAmountDiff).reduce(Money::add)
				.orElseThrow(() -> new AdempiereException("No lines")); // shall not happen
	}

}
