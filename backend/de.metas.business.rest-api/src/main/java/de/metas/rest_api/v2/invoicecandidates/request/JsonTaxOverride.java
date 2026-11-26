package de.metas.rest_api.v2.invoicecandidates.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

/*
 * #%L
 * de.metas.business.rest-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Value
public class JsonTaxOverride
{
	@ApiModelProperty(position = 10, required = true, //
			value = "The tax rate in percent, e.g. `19`. Matched scale-insensitively: `19`, `19.0` and `19.00` are equivalent.")
	BigDecimal rate;

	@ApiModelProperty(position = 20, required = true, //
			value = "Identifies the tax category the rate belongs to. Either\n"
					+ "* `int-<C_TaxCategory.InternalName>`, e.g. `int-Transport`\n"
					+ "* or the plain `C_TaxCategory_ID`, e.g. `1000012`\n\n"
					+ "Required: the same rate can exist in several categories, and metasfresh never resolves a tax without one.")
	String taxCategoryIdentifier;

	@Builder
	@JsonCreator
	private JsonTaxOverride(
			@JsonProperty("rate") final BigDecimal rate,
			@JsonProperty("taxCategoryIdentifier") final String taxCategoryIdentifier)
	{
		this.rate = rate;
		this.taxCategoryIdentifier = taxCategoryIdentifier;
	}
}
