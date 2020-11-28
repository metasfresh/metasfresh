package de.metas.rest_api.order;

import java.time.ZonedDateTime;
import java.util.List;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public class JsonSalesOrderCreateRequest
{
	@JsonProperty("docTypeName")
	String docTypeName;

	@JsonProperty("shipBPartnerCode")
	String shipBPartnerCode;

	@JsonProperty("datePromised")
	ZonedDateTime datePromised;

	@JsonProperty("lines")
	List<JsonSalesOrderLine> lines;

	@Builder
	@JsonCreator
	private JsonSalesOrderCreateRequest(
			@JsonProperty("docTypeName") @Nullable final String docTypeName,
			@JsonProperty("shipBPartnerCode") @NonNull final String shipBPartnerCode,
			@JsonProperty("datePromised") @NonNull final ZonedDateTime datePromised,
			@JsonProperty("lines") @NonNull @Singular final List<JsonSalesOrderLine> lines)
	{
		Check.assumeNotEmpty(shipBPartnerCode, "shipBPartnerCode is not empty");
		Check.assumeNotEmpty(lines, "lines is not empty");

		this.docTypeName = docTypeName;
		this.shipBPartnerCode = shipBPartnerCode;
		this.datePromised = datePromised;
		this.lines = ImmutableList.copyOf(lines);
	}

}
