package de.metas.forex;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
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
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
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

	/**
	 * @return foreign currency (i.e. the order currency)
	 */
	@NonNull
	public CurrencyId getForeignCurrencyId()
	{
		return orderCurrencyId;
	}

	/**
	 * @return local currency (i.e. the other one, which is not the order currency)
	 */
	@NonNull
	public CurrencyId getLocalCurrencyId() {return CurrencyId.equals(fromCurrencyId, orderCurrencyId) ? toCurrencyId : fromCurrencyId;}
}
