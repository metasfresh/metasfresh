package de.metas.forex;

import de.metas.common.util.CoalesceUtil;
import de.metas.money.CurrencyId;
import de.metas.util.NumberUtils;
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
	@Nullable BigDecimal currencyRate;

	@Builder
	@Jacksonized
	private ForexContractRef(
			@Nullable final ForexContractId forexContractId,
			@NonNull final CurrencyId orderCurrencyId,
			@NonNull final CurrencyId fromCurrencyId,
			@NonNull final CurrencyId toCurrencyId,
			@Nullable final BigDecimal currencyRate)
	{
		final BigDecimal currencyRateNorm = NumberUtils.zeroToNull(currencyRate);
		if (CoalesceUtil.countNotNulls(forexContractId, currencyRateNorm) == 0)
		{
			throw new AdempiereException("At least of of forexContractId or currencyRate shall be set");
		}
		if (currencyRateNorm != null && currencyRateNorm.signum() < 0)
		{
			throw new AdempiereException("Invalid negative currency rate: " + currencyRateNorm);
		}
		if (CurrencyId.equals(fromCurrencyId, toCurrencyId))
		{
			throw new AdempiereException("From and To currencies cannot be equal");
		}

		this.forexContractId = forexContractId;
		this.orderCurrencyId = orderCurrencyId;
		this.fromCurrencyId = fromCurrencyId;
		this.toCurrencyId = toCurrencyId;
		this.currencyRate = currencyRateNorm;
	}

	@Nullable
	public static ForexContractRef ofNullableForexContractId(@Nullable final ForexContractId forexContractId)
	{
		return forexContractId != null
				? builder().forexContractId(forexContractId).build()
				: null;
	}
}
