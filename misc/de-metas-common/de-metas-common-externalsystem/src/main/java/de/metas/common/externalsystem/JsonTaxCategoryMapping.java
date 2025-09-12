package de.metas.common.externalsystem;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

@Value
public class JsonTaxCategoryMapping
{
	@NonNull
	String taxCategory;

	@NonNull List<BigDecimal> taxRates;

	@Builder
	@JsonCreator
	public JsonTaxCategoryMapping(
			@JsonProperty("taxCategory") @NonNull final String taxCategory,
			@JsonProperty("taxRates") @NonNull final List<BigDecimal> taxRates)
	{
		this.taxCategory = taxCategory;
		this.taxRates = taxRates;
	}
}
