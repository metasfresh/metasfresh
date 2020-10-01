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
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.List;

@Value
public class ROW
{
	@JacksonXmlProperty(isAttribute = true, localName = "MODID")
	String modId;

	@JacksonXmlProperty(isAttribute = true, localName = "RECORDID")
	String recordId;

	@JsonProperty("COL")
	@JacksonXmlElementWrapper(useWrapping = false)
	ImmutableList<COL> cols;

	@Builder(toBuilder = true)
	@JsonCreator
	public ROW(
			@JacksonXmlProperty(isAttribute = true, localName = "MODID") final String modId,
			@JacksonXmlProperty(isAttribute = true, localName = "RECORDID") final String recordId,
			@Singular @JsonProperty("COL") final List<COL> cols)
	{
		this.modId = modId;
		this.recordId = recordId;
		this.cols = ImmutableList.copyOf(cols);
	}
}
