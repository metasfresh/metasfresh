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

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import de.metas.common.filemaker.ROW.ROWBuilder;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

@Value
@Builder
public class METADATA
{
	@Singular
	@JacksonXmlElementWrapper(useWrapping = false)
	@JsonProperty("FIELD")
	List<FIELD> fields;

	public METADATA(@JsonProperty("FIELD") final List<FIELD> fields)
	{
		this.fields = fields;
	}

	@NonNull
	public ROW createROW(@NonNull final Map<String, String> valuesByName)
	{
		final ROWBuilder row = ROW.builder();

		for (final FIELD field : fields)
		{
			final String fieldName = field.getName();
			final String value = valuesByName.get(fieldName);
			row.col(COL.of(value));
		}

		return row.build();
	}
}
