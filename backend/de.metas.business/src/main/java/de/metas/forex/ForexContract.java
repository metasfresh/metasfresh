package de.metas.forex;

import de.metas.currency.FixedConversionRate;
import de.metas.document.engine.DocStatus;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import java.math.BigDecimal;

@EqualsAndHashCode
@ToString
@Builder
public class ForexContract
{
	@Getter @NonNull private final ForexContractId id;
	@Getter @NonNull private final OrgId orgId;
	@Getter @NonNull private final DocStatus docStatus;

	@Getter @NonNull private final CurrencyId currencyId;
	@Getter @NonNull private final CurrencyId toCurrencyId;
	@Getter @NonNull private final BigDecimal currencyRate;

	@Getter @NonNull private final Money amount;
	@Getter @NonNull private Money allocatedAmount;
	@Getter @NonNull private Money openAmount;

	public void setAllocatedAmountAndUpdate(@NonNull final Money allocatedAmount)
	{
		if (allocatedAmount.isNegative())
		{
			throw new AdempiereException("Negative allocated amount is not allowed");
		}

		this.allocatedAmount = allocatedAmount;
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
