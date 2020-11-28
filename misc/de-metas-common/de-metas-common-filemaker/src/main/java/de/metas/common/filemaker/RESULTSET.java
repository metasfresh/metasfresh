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
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.List;

@Value

public class RESULTSET
{
	@JacksonXmlProperty(isAttribute = true, localName = "FOUND")
	int found;

	@JsonProperty("ROW")
	@JacksonXmlElementWrapper(useWrapping = false)
	List<ROW> rows;

	@Builder
	@JsonCreator
	public RESULTSET(
			@JacksonXmlProperty(isAttribute = true, localName = "FOUND") final int found,
			@JsonProperty("ROW") @Singular final List<ROW> rows)
	{
		this.found = found;
		this.rows = rows;
	}
}
