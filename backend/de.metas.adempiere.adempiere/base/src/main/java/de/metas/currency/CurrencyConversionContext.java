package de.metas.currency;

import com.google.common.base.MoreObjects;
import de.metas.currency.FixedConversionRateMap.FixedConversionRate;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.service.ClientId;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

@Value
@Builder(toBuilder = true)
public class CurrencyConversionContext
{
	@NonNull
	LocalDate conversionDate;
	@NonNull
	CurrencyConversionTypeId conversionTypeId;
	@NonNull
	ClientId clientId;
	@NonNull
	OrgId orgId;

	@NonNull
	@Default
	Optional<CurrencyPrecision> precision = Optional.empty();

	@NonNull
	@Default
	FixedConversionRateMap fixedConversionRates = FixedConversionRateMap.EMPTY;

	/**
	 * @return a summary, user friendly, string representation
	 */
	public String getSummary()
	{
		// NOTE: keep it short because we want to append it to Fact_Acct.Description

		return MoreObjects.toStringHelper(this)
				.add("date", conversionDate)
				.add("conversionTypeId", conversionTypeId.getRepoId())
				.toString();
	}

	public CurrencyConversionContext withPrecision(@NonNull final CurrencyPrecision precision)
	{
		final CurrencyPrecision precisionOld = getPrecision().orElse(null);
		if (Objects.equals(precisionOld, precision))
		{
			return this;
		}
		else
		{
			return toBuilder().precision(Optional.of(precision)).build();
		}
	}

	public CurrencyConversionContext withFixedConversionRate(
			@NonNull final CurrencyId fromCurrencyId,
			@NonNull final CurrencyId toCurrencyId,
			@NonNull final BigDecimal multiplyRate)
	{
		final FixedConversionRate rate = FixedConversionRate.builder()
				.fromCurrencyId(fromCurrencyId)
				.toCurrencyId(toCurrencyId)
				.multiplyRate(multiplyRate)
				.build();

		return toBuilder()
				.fixedConversionRates(fixedConversionRates.addingConversionRate(rate))
				.build();
	}

	public boolean hasFixedConversionRate(
			@NonNull final CurrencyId fromCurrencyId,
			@NonNull final CurrencyId toCurrencyId)
	{
		return fixedConversionRates.hasMultiplyRate(fromCurrencyId, toCurrencyId);
	}

	public BigDecimal getFixedConversionRate(
			@NonNull final CurrencyId fromCurrencyId,
			@NonNull final CurrencyId toCurrencyId)
	{
		return fixedConversionRates.getMultiplyRate(fromCurrencyId, toCurrencyId);
	}
}
