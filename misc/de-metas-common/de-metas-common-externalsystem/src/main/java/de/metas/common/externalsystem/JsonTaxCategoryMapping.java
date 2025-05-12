/*
 * #%L
 * de-metas-common-externalsystem
 * %%
 * Copyright (C) 2024 metas GmbH
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
