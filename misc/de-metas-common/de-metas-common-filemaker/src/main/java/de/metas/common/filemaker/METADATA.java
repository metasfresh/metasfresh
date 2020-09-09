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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.google.common.collect.ImmutableMap;

import de.metas.common.filemaker.ROW.ROWBuilder;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import lombok.experimental.NonFinal;

@Value
public class METADATA
{
	@JacksonXmlElementWrapper(useWrapping = false)
	@JsonProperty("FIELD")
	List<FIELD> fields;

	@JsonIgnore
	@NonFinal
	private transient ImmutableMap<String, Integer> _fieldName2FieldIndex; // lazy

	@Builder
	public METADATA(
			@JsonProperty("FIELD") @NonNull @Singular final List<FIELD> fields)
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

	public COL getCell(@NonNull final ROW row, @NonNull final String fieldName)
	{
		final int fieldIndex = getFieldIndexByName(fieldName);
		return row.getCols().get(fieldIndex);
	}

	private int getFieldIndexByName(final String fieldName)
	{
		ImmutableMap<String, Integer> fieldName2FieldIndex = this._fieldName2FieldIndex;
		if (fieldName2FieldIndex == null)
		{
			fieldName2FieldIndex = this._fieldName2FieldIndex = createFieldName2FieldIndexMap();
		}

		final Integer fieldIndex = fieldName2FieldIndex.get(fieldName);
		if (fieldIndex == null)
		{
			throw new IllegalArgumentException("Unknow field `" + fieldName + "` in " + this);
		}

		return fieldIndex;
	}

	private ImmutableMap<String, Integer> createFieldName2FieldIndexMap()
	{
		ImmutableMap.Builder<String, Integer> builder = ImmutableMap.<String, Integer> builder();
		for (int i = 0, size = fields.size(); i < size; i++)
		{
			final String fieldName = fields.get(i).getName();
			builder.put(fieldName, i);
		}

		return builder.build();
	}
}
