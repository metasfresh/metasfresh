/*
 * #%L
 * de-metas-common-filemaker
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.common.filemaker;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Builder;
import lombok.Value;

@Value
@JsonPropertyOrder({ "DATEFORMAT", "LAYOUT", "NAME", "RECORDS", "TIMEFORMAT" })
public class DATABASE
{
	@JacksonXmlProperty(isAttribute = true, localName = "DATEFORMAT")
	String dateFormat = "D.m.yyyy";

	@JacksonXmlProperty(isAttribute = true, localName = "LAYOUT")
	String layout = "xml";

	@JacksonXmlProperty(isAttribute = true, localName = "NAME")
	String name;

	@JacksonXmlProperty(isAttribute = true, localName = "RECORDS")
	String records;

	@JacksonXmlProperty(isAttribute = true, localName = "TIMEFORMAT")
	String timeFormat = "k:mm:ss ";

	@Builder
	@JsonCreator
	public DATABASE(
			@JacksonXmlProperty(isAttribute = true, localName = "NAME") final String name,
			@JacksonXmlProperty(isAttribute = true, localName = "RECORDS") final String records)
	{
		this.name = name;
		this.records = records;
	}
}
