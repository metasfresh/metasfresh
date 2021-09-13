/*
 * #%L
 * de.metas.elasticsearch
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

package de.metas.fulltextsearch.query;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.fulltextsearch.config.FTSJoinColumn;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.List;

@Value
@Builder
public class FTSSearchResultItem
{
	@NonNull KeyValueMap key;
	@Nullable String json;

	@Value
	@Builder
	public static class KeyValue
	{
		@NonNull String targetTableColumnName;
		@NonNull String selectionTableColumnName;
		@NonNull FTSJoinColumn.ValueType valueType;
		@Nullable Object value;

		public static KeyValue ofJoinColumnAndValue(
				@NonNull final FTSJoinColumn joinColumn,
				@Nullable final Object value)
		{
			return builder()
					.targetTableColumnName(joinColumn.getTargetTableColumnName())
					.selectionTableColumnName(joinColumn.getSelectionTableColumnName())
					.valueType(joinColumn.getValueType())
					.value(value)
					.build();
		}
	}

	@EqualsAndHashCode
	@ToString
	public static class KeyValueMap
	{
		private final ImmutableMap<String, KeyValue> keysBySelectionTableColumnName;

		public KeyValueMap(@NonNull final List<KeyValue> keys)
		{
			if (keys.isEmpty())
			{
				throw new AdempiereException("empty key not allowed");
			}

			keysBySelectionTableColumnName = Maps.uniqueIndex(keys, KeyValue::getSelectionTableColumnName);
		}

		@Nullable
		public Object getValueBySelectionTableColumnName(final String selectionTableColumName)
		{
			final KeyValue keyValue = keysBySelectionTableColumnName.get(selectionTableColumName);
			return keyValue != null ? keyValue.getValue() : null;
		}

	}
}
