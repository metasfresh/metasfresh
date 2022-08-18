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

package de.metas.po;

import com.google.common.collect.ImmutableMap;
import de.metas.i18n.AdMessageKey;
import de.metas.util.converter.POValueConverters;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.model.POInfoColumn;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.time.ZoneId;
import java.util.Map;
import java.util.Optional;

@Component
public class CustomColumnDAO
{
	private static final AdMessageKey MSG_CUSTOM_REST_API_COLUMN = AdMessageKey.of("CUSTOM_REST_API_COLUMN");

	public void save(@NonNull final SaveCustomColumnsRequest saveCustomColumnsRequest)
	{
		final PO po = saveCustomColumnsRequest.getPO();

		saveCustomColumnsRequest.getColumnName2Value()
				.forEach((columnName, value) -> setCustomColumn(po, columnName, value, saveCustomColumnsRequest.getZoneId()));

		InterfaceWrapperHelper.save(po);
	}

	@NonNull
	public Map<String, Object> getCustomColumns(@NonNull final PO po, @NonNull final ZoneId zoneId)
	{
		final POInfo poInfo = po.getPOInfo();

		final ImmutableMap.Builder<String, Object> customColumnsCollector = ImmutableMap.builder();

		poInfo.streamColumns(POInfoColumn::isRestAPICustomColumn)
				.forEach(customColumn -> {
					final Object actualValue = convertFromPOValue(po, customColumn, zoneId);

					Optional.ofNullable(actualValue)
							.ifPresent(nonNullValue -> customColumnsCollector.put(customColumn.getColumnName(), nonNullValue));
				});

		return customColumnsCollector.build();
	}

	private void setCustomColumn(
			@NonNull final PO po,
			@NonNull final String columnName,
			@Nullable final Object valueToSet,
			@NonNull final ZoneId zoneId)
	{
		final POInfo poInfo = po.getPOInfo();

		if (!poInfo.isRestAPICustomColumn(columnName))
		{
			throw new AdempiereException(MSG_CUSTOM_REST_API_COLUMN, columnName)
					.markAsUserValidationError();
		}

		final Class<?> columnTargetClass = poInfo.getColumnClass(columnName);
		if (columnTargetClass == null)
		{
			throw new AdempiereException("Cannot get the actual PO value if targetClass is missing!")
					.appendParametersToMessage()
					.setParameter("TableName", poInfo.getTableName())
					.setParameter("ColumnName", columnName);
		}

		final int displayType = poInfo.getColumnDisplayType(columnName);
		final Object convertedValue = POValueConverters.convertToPOValue(valueToSet, columnTargetClass, displayType, zoneId);

		po.set_ValueOfColumn(columnName, convertedValue);
	}

	@Nullable
	private Object convertFromPOValue(@NonNull final PO po, @NonNull final POInfoColumn poInfoColumn, @NonNull final ZoneId zoneId)
	{
		final Object poValue = po.get_Value(poInfoColumn.getColumnName());

		return POValueConverters.convertFromPOValue(poValue, poInfoColumn.getDisplayType(), zoneId);
	}
}
