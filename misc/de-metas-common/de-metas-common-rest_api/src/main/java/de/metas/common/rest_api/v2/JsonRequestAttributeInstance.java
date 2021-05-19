/*
 * #%L
 * de-metas-common-rest_api
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.common.rest_api.v2;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Value
public class JsonRequestAttributeInstance
{
	String attributeCode;

	String valueStr;
	BigDecimal valueNumber;
	LocalDate valueDate;

	@JsonCreator
	@Builder
	private JsonRequestAttributeInstance(
			@JsonProperty("attributeCode") @NonNull final String attributeCode,
			@JsonProperty("valueStr") @Nullable final String valueStr,
			@JsonProperty("valueNumber") @Nullable final BigDecimal valueNumber,
			@JsonProperty("valueDate") @Nullable final LocalDate valueDate)
	{
		this.attributeCode = attributeCode;
		this.valueStr = valueStr;
		this.valueNumber = valueNumber;
		this.valueDate = valueDate;
	}
}
