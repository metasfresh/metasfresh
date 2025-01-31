/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package org.adempiere.ad.persistence.custom_columns;

import com.google.common.collect.ImmutableMap;
import de.metas.i18n.AdMessageKey;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.Null;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.model.POInfoColumn;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CustomColumnService
{
	private static final AdMessageKey MSG_CUSTOM_REST_API_COLUMN = AdMessageKey.of("CUSTOM_REST_API_COLUMN");

	@NonNull private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	@NonNull private final CustomColumnRepository repository;

	public void setCustomColumns(@NonNull final PO record, @NonNull final Map<String, Object> valuesByColumnName)
	{
		if (valuesByColumnName.isEmpty())
		{
			return;
		}

		final POInfo poInfo = record.getPOInfo();
		assertRestAPIColumnNames(poInfo.getTableName(), valuesByColumnName.keySet());

		final CustomColumnsPOValues poValues = CustomColumnsConverters.convertToPOValues(valuesByColumnName, poInfo, extractZoneId(record));

		setCustomColumns(record, poValues);
	}

	private static void setCustomColumns(final @NonNull PO record, final CustomColumnsPOValues poValues)
	{
		poValues.forEach((columnName, value) -> record.set_ValueOfColumn(columnName, Null.unbox(value)));
	}

	@NonNull
	public Map<String, Object> getCustomColumnsAsMap(@NonNull final PO po)
	{
		final ZoneId zoneId = orgDAO.getTimeZone(OrgId.ofRepoId(po.getAD_Org_ID()));

		final POInfo poInfo = po.getPOInfo();

		final ImmutableMap.Builder<String, Object> customColumnsCollector = ImmutableMap.builder();

		streamCustomRestAPIColumns(poInfo)
				.forEach(customColumn -> {
					final Object actualValue = CustomColumnsConverters.convertFromPOValue(po, customColumn, zoneId);

					Optional.ofNullable(actualValue)
							.ifPresent(nonNullValue -> customColumnsCollector.put(customColumn.getColumnName(), nonNullValue));
				});

		return customColumnsCollector.build();
	}

	private Stream<POInfoColumn> streamCustomRestAPIColumns(final POInfo poInfo)
	{
		final RESTApiTableInfo tableInfo = repository.getByTableNameOrNull(poInfo.getTableName());
		if (tableInfo == null)
		{
			return Stream.empty();
		}
		else
		{
			return poInfo.streamColumns(tableInfo::isCustomRestAPIColumn);
		}
	}

	private void assertRestAPIColumnNames(final String tableName, final Collection<String> columnNames)
	{
		if (columnNames.isEmpty())
		{
			return;
		}

		final RESTApiTableInfo tableInfo = repository.getByTableNameOrNull(tableName);

		final Collection<String> notValidColumnNames;
		if (tableInfo == null)
		{
			notValidColumnNames = columnNames;
		}
		else
		{
			notValidColumnNames = columnNames.stream()
					.filter(columnName -> !tableInfo.isCustomRestAPIColumn(columnName))
					.collect(Collectors.toList());
		}

		if (!notValidColumnNames.isEmpty())
		{
			final String notValidColumnNamesStr = notValidColumnNames.stream()
					.map(columnName -> tableName + "." + columnName)
					.collect(Collectors.joining(", "));
			throw new AdempiereException(MSG_CUSTOM_REST_API_COLUMN, notValidColumnNamesStr)
					.markAsUserValidationError();
		}
	}

	@NonNull
	public CustomColumnsJsonValues getCustomColumnsJsonValues(@NonNull final PO record)
	{
		final ZoneId zoneId = extractZoneId(record);
		return convertToJsonValues(record, zoneId);
	}

	@NonNull
	private CustomColumnsJsonValues convertToJsonValues(final @NonNull PO record, final ZoneId zoneId)
	{
		final POInfo poInfo = record.getPOInfo();

		final ImmutableMap.Builder<String, Object> map = ImmutableMap.builder();

		streamCustomRestAPIColumns(poInfo)
				.forEach(customColumn -> {
					final String columnName = customColumn.getColumnName();
					final Object poValue = record.get_Value(columnName);
					final Object jsonValue = CustomColumnsConverters.convertToJsonValue(poValue, customColumn.getDisplayType(), zoneId);
					if (jsonValue != null)
					{
						map.put(columnName, jsonValue);
					}
				});

		return CustomColumnsJsonValues.ofJsonValuesMap(map.build());
	}

	private ZoneId extractZoneId(final @NonNull PO record) {return orgDAO.getTimeZone(OrgId.ofRepoId(record.getAD_Org_ID()));}
}
