package de.metas.forex;

import de.metas.currency.FixedConversionRate;
import de.metas.document.engine.DocStatus;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import java.math.BigDecimal;
import java.time.Instant;

@EqualsAndHashCode
@ToString
public class ForexContract
{
	@Getter @NonNull private final ForexContractId id;
	@Getter @NonNull private final String documentNo;
	@Getter @NonNull private final Instant created;
	@Getter @NonNull private final UserId createdBy;

	@Getter @NonNull private final OrgId orgId;
	@Getter @NonNull private final DocStatus docStatus;

	@Getter @NonNull private final Instant validityDate;
	@Getter @NonNull private final Instant maturityDate;

	@Getter @NonNull private final CurrencyId currencyId;
	@Getter @NonNull private final CurrencyId toCurrencyId;
	@Getter @NonNull private final BigDecimal currencyRate;

	@Getter @NonNull private final Money amount;
	@Getter @NonNull private Money allocatedAmount;
	@Getter @NonNull private Money openAmount;

	@Builder
	private ForexContract(
			final @NonNull ForexContractId id,
			final @NonNull String documentNo,
			final @NonNull Instant created,
			final @NonNull UserId createdBy,
			final @NonNull OrgId orgId,
			final @NonNull DocStatus docStatus,
			final @NonNull Instant validityDate,
			final @NonNull Instant maturityDate,
			final @NonNull CurrencyId currencyId,
			final @NonNull CurrencyId toCurrencyId,
			final @NonNull BigDecimal currencyRate,
			final @NonNull Money amount,
			final @NonNull Money allocatedAmount,
			final @NonNull Money openAmount)
	{
		if (maturityDate.isBefore(validityDate))
		{
			throw new AdempiereException("Maturity Date cannot be earlier than Validity Date");
		}

		if (CurrencyId.equals(currencyId, toCurrencyId))
		{
			throw new AdempiereException("From and To Currencies cannot be the same");
		}

		Money.assertSameCurrency(amount, allocatedAmount, openAmount);
		if (!CurrencyId.equals(amount.getCurrencyId(), currencyId))
		{
			throw new AdempiereException("Amounts shall be in From currency");
		}

		if (amount.signum() < 0)
		{
			throw new AdempiereException("Amount shall be positive");
		}

		this.id = id;
		this.documentNo = documentNo;
		this.created = created;
		this.createdBy = createdBy;
		this.orgId = orgId;
		this.docStatus = docStatus;
		this.validityDate = validityDate;
		this.maturityDate = maturityDate;
		this.currencyId = currencyId;
		this.toCurrencyId = toCurrencyId;
		this.currencyRate = currencyRate;
		this.amount = amount;
		this.allocatedAmount = allocatedAmount;
		this.openAmount = openAmount;
	}

	public void setAllocatedAmountAndUpdate(@NonNull final Money allocatedAmount)
	{
		if (allocatedAmount.isNegative())
		{
			throw new AdempiereException("Negative allocated amount is not allowed");
		}

		this.allocatedAmount = allocatedAmount;
		updateOpenAmount();
	}

	public void updateOpenAmount()
	{
		this.openAmount = this.amount.subtract(this.allocatedAmount).toZeroIfNegative();
	}

	public void assertCanAllocate(@NonNull final Money amountToAllocate)
	{
		if (!docStatus.isCompleted())
		{
			throw new AdempiereException("Cannot allocate to a contract which is not completed");
		}

		if (amountToAllocate.signum() <= 0)
		{
			throw new AdempiereException("Amount to allocate shall be greater than zero");
		}
		if (!openAmount.isGreaterThanOrEqualTo(amountToAllocate))
		{
			throw new AdempiereException("Not enough open amount");
		}
	}

	public FixedConversionRate toFixedConversionRate()
	{
		return FixedConversionRate.builder()
				.fromCurrencyId(currencyId)
				.toCurrencyId(toCurrencyId)
				.multiplyRate(currencyRate)
				.build();
	}

}
