package de.metas.dataentry.data.json;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.dataentry.DataEntryListValueId;
import de.metas.CreatedUpdatedInfo;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
public class JSONDataEntryRecord
{
	Map<Integer, LocalDate> dates;

	Map<Integer, DataEntryListValueId> listValues;

	Map<Integer, BigDecimal> numbers;

	Map<Integer, String> strings;

	Map<Integer, Boolean> yesNos;

	Map<Integer, CreatedUpdatedInfo> createdUpdatedInfos;

	@Builder
	@JsonCreator
	private JSONDataEntryRecord(
			@Singular @JsonProperty("dates") final Map<Integer, LocalDate> dates,
			@Singular @JsonProperty("listValues") final Map<Integer, DataEntryListValueId> listValues,
			@Singular @JsonProperty("numbers") final Map<Integer, BigDecimal> numbers,
			@Singular @JsonProperty("strings") final Map<Integer, String> strings,
			@Singular @JsonProperty("yesNos") final Map<Integer, Boolean> yesNos,
			@Singular @JsonProperty("createdUpdatedInfos") final Map<Integer, CreatedUpdatedInfo> createdUpdatedInfos)
	{
		this.dates = dates;
		this.listValues = listValues;
		this.numbers = numbers;
		this.strings = strings;
		this.yesNos = yesNos;
		this.createdUpdatedInfos = createdUpdatedInfos;
	}

}
