package de.metas.currency;

import java.time.LocalDate;

import org.adempiere.service.ClientId;
import org.adempiere.service.OrgId;

import com.google.common.base.MoreObjects;

import de.metas.money.CurrencyConversionTypeId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
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

	/** @return a summary, user friendly, string representation */
	public String getSummary()
	{
		// NOTE: keep it short because we want to append it to Fact_Acct.Description

		return MoreObjects.toStringHelper(this)
				.add("date", conversionDate)
				.add("conversionTypeId", conversionTypeId.getRepoId())
				.toString();
	}
}
