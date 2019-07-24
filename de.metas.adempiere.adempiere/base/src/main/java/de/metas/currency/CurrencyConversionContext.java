package de.metas.currency;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

import org.adempiere.service.ClientId;

import com.google.common.base.MoreObjects;

import de.metas.money.CurrencyConversionTypeId;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;

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

	/** @return a summary, user friendly, string representation */
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
}
