package de.metas.forex;

import de.metas.currency.FixedConversionRate;
import de.metas.money.CurrencyId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Value
public class ForexContractRef
{
	@Nullable ForexContractId forexContractId;
	@NonNull CurrencyId orderCurrencyId;
	@NonNull CurrencyId fromCurrencyId;
	@NonNull CurrencyId toCurrencyId;
	@NonNull BigDecimal currencyRate;

	@Builder
	@Jacksonized
	private ForexContractRef(
			@Nullable final ForexContractId forexContractId,
			@NonNull final CurrencyId orderCurrencyId,
			@NonNull final CurrencyId fromCurrencyId,
			@NonNull final CurrencyId toCurrencyId,
			@NonNull final BigDecimal currencyRate)
	{
		if (currencyRate.signum() <= 0)
		{
			throw new AdempiereException("Currency rate shall be greater than zero");
		}
		if (CurrencyId.equals(fromCurrencyId, toCurrencyId))
		{
			throw new AdempiereException("From and To currencies cannot be equal");
		}

		this.forexContractId = forexContractId;
		this.orderCurrencyId = orderCurrencyId;
		this.fromCurrencyId = fromCurrencyId;
		this.toCurrencyId = toCurrencyId;
		this.currencyRate = currencyRate;
	}

	public FixedConversionRate toFixedConversionRate()
	{
		return FixedConversionRate.builder()
				.fromCurrencyId(fromCurrencyId)
				.toCurrencyId(toCurrencyId)
				.multiplyRate(currencyRate)
				.build();
	}

}
