package org.adempiere.ad.table;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;

import org.adempiere.ad.session.ISessionDAO;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.model.POInfo;
import org.compiere.util.DisplayType;
import org.compiere.util.NamePair;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.util.NumberUtils;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

final class RecordChangeLogEntryValuesResolver
{
	public static RecordChangeLogEntryValuesResolver of(@NonNull final POInfo poInfo)
	{
		return new RecordChangeLogEntryValuesResolver(poInfo);
	}

	private static final Logger logger = LogManager.getLogger(RecordChangeLogEntryValuesResolver.class);

	private final POInfo poInfo;

	private final HashMap<String, MLookup> tableLookupsByTableName = new HashMap<>();
	private final HashMap<Integer, MLookup> listLookupsByReferenceId = new HashMap<>();

	private RecordChangeLogEntryValuesResolver(@NonNull final POInfo poInfo)
	{
		this.poInfo = poInfo;
	}

	public Object convertToDisplayValue(final String valueStr, final String columnName)
	{
		final int displayType = poInfo.getColumnDisplayType(columnName);

		try
		{
			if (valueStr == null || ISessionDAO.CHANGELOG_NullValue.equals(valueStr))
			{
				return null;
			}
			else if (DisplayType.isText(displayType))
			{
				return valueStr;
			}
			else if (valueStr.isEmpty())
			{
				return null;
			}
			else if (displayType == DisplayType.YesNo)
			{
				final Boolean valueBoolean = DisplayType.toBoolean(valueStr, null);
				return valueBoolean != null ? valueBoolean : valueStr;
			}
			else if (displayType == DisplayType.Integer)
			{
				final Integer valueInt = NumberUtils.asInteger(valueStr, null);
				return valueInt != null ? valueInt : valueStr;
			}
			else if (DisplayType.isNumeric(displayType))
			{
				final BigDecimal valueBD = NumberUtils.asBigDecimal(valueStr, null);
				return valueBD != null ? valueBD : valueStr;
			}
			else if (displayType == DisplayType.DateTime)
			{
				final Timestamp valueTS = Timestamp.valueOf(valueStr);
				return TimeUtil.asZonedDateTime(valueTS);
			}
			else if (displayType == DisplayType.Date)
			{
				final Timestamp valueTS = Timestamp.valueOf(valueStr);
				return TimeUtil.asLocalDate(valueTS);
			}
			else if (displayType == DisplayType.Time)
			{
				final Timestamp valueTS = Timestamp.valueOf(valueStr);
				return TimeUtil.asLocalTime(valueTS);
			}
			else if (DisplayType.isAnyLookup(displayType))
			{
				final MLookup lookup = getLookupByColumnName(columnName);
				if (lookup == null)
				{
					return valueStr;
				}

				final NamePair valueNP = lookup.get(normalizeLookupId(valueStr, lookup.isNumericKey()));
				if (valueNP == null)
				{
					return valueStr;
				}

				return valueNP;
			}
			else
			{
				return valueStr;
			}
		}
		catch (final Exception ex)
		{
			logger.warn("Failed to convert {} (displayType={}) to object. Returning original value.", valueStr, displayType, ex);
			return valueStr;
		}
	}

	private MLookup getLookupByColumnName(final String columnName)
	{
		final int displayType = poInfo.getColumnDisplayType(columnName);
		if (DisplayType.List == displayType)
		{
			final int adReferenceId = poInfo.getColumnReferenceValueId(columnName);
			if (adReferenceId <= 0)
			{
				return null;
			}

			return listLookupsByReferenceId.computeIfAbsent(adReferenceId, MLookupFactory::searchInList);
		}
		else
		{
			final String referencedTableName = poInfo.getReferencedTableNameOrNull(columnName);
			if (referencedTableName == null)
			{
				return null;
			}

			return tableLookupsByTableName.computeIfAbsent(referencedTableName, MLookupFactory::searchInTable);
		}
	}

	private static Object normalizeLookupId(final Object idObj, final boolean isNumericId)
	{
		// shall not happen
		if (idObj == null)
		{
			return null;
		}

		if (isNumericId)
		{
			return NumberUtils.asInteger(idObj, null);
		}
		else
		{
			return idObj.toString();
		}
	}

}
