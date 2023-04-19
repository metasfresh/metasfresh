/*
 * #%L
 * de-metas-camel-grssignum
 * %%
 * Copyright (C) 2022 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.camel.externalsystems.grssignum.to_grs.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Value
@JsonDeserialize(builder = JsonBOMAdditionalInfo.JsonBOMAdditionalInfoBuilder.class)
public class JsonBOMAdditionalInfo
{
	@JsonProperty("Produktion_Maschinengrößen")
	@Nullable
	List<Map<String, BigDecimal>> plantConfig;

	@JsonProperty("Lagerungsbedingungen_MHD_in_Monaten")
	@Nullable
	Integer guaranteeMonths;

	@JsonProperty("Lagerungsbedingungen_Lagerungstemperatur")
	@Nullable
	String warehouseTemperature;

	@JsonProperty("Rezeptur_Angaben_GTIN")
	@Nullable
	String gtin;

	@JsonProperty("Rezeptur_Angaben_Landwirtschaftliche_Herkunft")
	@Nullable
	String agricultureOrigin;

	@Builder
	public JsonBOMAdditionalInfo(
			@Nullable @JsonProperty("Produktion_Maschinengrößen") final List<Map<String, BigDecimal>> plantConfig,
			@Nullable @JsonProperty("Lagerungsbedingungen_MHD_in_Monaten") final Integer guaranteeMonths,
			@Nullable @JsonProperty("Lagerungsbedingungen_Lagerungstemperatur") final String warehouseTemperature,
			@Nullable @JsonProperty("Rezeptur_Angaben_GTIN") final String gtin,
			@Nullable @JsonProperty("Rezeptur_Angaben_Landwirtschaftliche_Herkunft") final String agricultureOrigin)
	{
		this.plantConfig = plantConfig;
		this.guaranteeMonths = guaranteeMonths;
		this.warehouseTemperature = warehouseTemperature;
		this.gtin = gtin;
		this.agricultureOrigin = agricultureOrigin;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonPOJOBuilder(withPrefix = "")
	static class JsonBOMAdditionalInfoBuilder
	{
	}

	@Nullable
	@JsonIgnore
	public String getGuaranteeMonthsAsString()
	{
		return Optional.ofNullable(guaranteeMonths)
				.map(String::valueOf)
				.orElse(null);
	}
}
