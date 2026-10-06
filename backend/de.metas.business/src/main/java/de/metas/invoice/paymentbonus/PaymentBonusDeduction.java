package de.metas.invoice.paymentbonus;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.currency.CurrencyPrecision;
import de.metas.invoice.InvoiceId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.tax.api.Tax;
import de.metas.tax.api.TaxId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The bonus that a customer deducts when paying a sales invoice ("bei Zahlung"): a percentage of the invoice's net goods value, plus the VAT of the bonus products on top.
 * It is booked as a payment bonus credit memo with one line per bonus product, which is allocated against the invoice.
 */
@Value
public class PaymentBonusDeduction
{
	@NonNull OrgId orgId;

	/** The sales invoice that the customer paid less. */
	@NonNull InvoiceId invoiceId;

	/** The invoice's partner; the credit memo is issued to it. */
	@NonNull BPartnerId customerId;

	@NonNull CurrencyId currencyId;

	@NonNull CurrencyPrecision precision;

	@NonNull ImmutableList<PaymentBonusDeductionLine> lines;

	@Builder(toBuilder = true)
	private PaymentBonusDeduction(
			@NonNull final OrgId orgId,
			@NonNull final InvoiceId invoiceId,
			@NonNull final BPartnerId customerId,
			@NonNull final CurrencyId currencyId,
			@NonNull final CurrencyPrecision precision,
			@NonNull @Singular final ImmutableList<PaymentBonusDeductionLine> lines)
	{
		Check.assumeNotEmpty(lines, "lines");
		lines.forEach(line -> line.getNetAmt().assertCurrencyId(currencyId));

		this.orgId = orgId;
		this.invoiceId = invoiceId;
		this.customerId = customerId;
		this.currencyId = currencyId;
		this.precision = precision;
		this.lines = lines;
	}

	public Money getNetAmount()
	{
		return lines.stream().map(PaymentBonusDeductionLine::getNetAmt).reduce(Money.zero(currencyId), Money::add);
	}

	/**
	 * @return the net amount plus the VAT on top; the VAT is computed per tax on the sum of its lines, like the credit memo's own taxes
	 */
	public Money getGrossAmount()
	{
		return computeGrossAmount(lines);
	}

	private Money computeGrossAmount(@NonNull final List<PaymentBonusDeductionLine> lines)
	{
		final Map<TaxId, Tax> taxesById = new LinkedHashMap<>();
		final Map<TaxId, BigDecimal> netAmtsByTaxId = new LinkedHashMap<>();
		for (final PaymentBonusDeductionLine line : lines)
		{
			final TaxId taxId = line.getTax().getTaxId();
			taxesById.put(taxId, line.getTax());
			netAmtsByTaxId.merge(taxId, line.getNetAmt().toBigDecimal(), BigDecimal::add);
		}

		BigDecimal grossAmt = BigDecimal.ZERO;
		for (final Map.Entry<TaxId, BigDecimal> entry : netAmtsByTaxId.entrySet())
		{
			grossAmt = grossAmt.add(taxesById.get(entry.getKey()).calculateGross(entry.getValue(), precision.toInt()));
		}
		return Money.of(grossAmt, currencyId);
	}

	/**
	 * Scales the lines' net amounts so that the gross amount is the given one, e.g. because the customer deducted a different amount than computed.
	 * Because of the rounding of the VAT, not every gross amount can be reached; then the result's gross amount is the closest one.
	 */
	public PaymentBonusDeduction withGrossAmount(@NonNull final Money grossAmountToReach)
	{
		grossAmountToReach.assertCurrencyId(currencyId);
		Check.assume(grossAmountToReach.signum() > 0, "The gross amount of a payment bonus needs to be positive; grossAmountToReach={}", grossAmountToReach);

		final Money grossAmount = getGrossAmount();
		if (grossAmount.isEqualByComparingTo(grossAmountToReach))
		{
			return this;
		}

		final BigDecimal factor = grossAmountToReach.toBigDecimal().divide(grossAmount.toBigDecimal(), 12, RoundingMode.HALF_UP);
		final List<PaymentBonusDeductionLine> scaledLines = new ArrayList<>();
		for (final PaymentBonusDeductionLine line : lines)
		{
			scaledLines.add(line.withNetAmt(line.getNetAmt().multiply(factor).round(precision)));
		}

		// the rounding of the scaled lines and of their VAT can miss the gross amount by a few cents; correct the biggest line, one cent at a time
		final int biggestLineIndex = scaledLines.indexOf(scaledLines.stream().max(Comparator.comparing(line -> line.getNetAmt().toBigDecimal())).get());
		final BigDecimal oneCent = BigDecimal.ONE.movePointLeft(precision.toInt());
		for (int i = 0; i < 10; i++)
		{
			final Money difference = grossAmountToReach.subtract(computeGrossAmount(scaledLines));
			if (difference.signum() == 0)
			{
				break;
			}

			final PaymentBonusDeductionLine biggestLine = scaledLines.get(biggestLineIndex);
			final BigDecimal step = difference.signum() > 0 ? oneCent : oneCent.negate();
			scaledLines.set(biggestLineIndex, biggestLine.withNetAmt(biggestLine.getNetAmt().add(Money.of(step, currencyId))));

			final Money newDifference = grossAmountToReach.subtract(computeGrossAmount(scaledLines));
			if (newDifference.abs().isGreaterThanOrEqualTo(difference.abs()))
			{
				scaledLines.set(biggestLineIndex, biggestLine); // no closer; keep the previous one
				break;
			}
		}

		return toBuilder()
				.clearLines()
				.lines(scaledLines)
				.build();
	}
}
