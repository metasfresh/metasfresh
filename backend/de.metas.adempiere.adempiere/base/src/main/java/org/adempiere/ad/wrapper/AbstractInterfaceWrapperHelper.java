package org.adempiere.ad.wrapper;

import org.adempiere.ad.persistence.IModelInternalAccessor;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Base class for all {@link IInterfaceWrapperHelper} implementations.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public abstract class AbstractInterfaceWrapperHelper implements IInterfaceWrapperHelper
{
	private static final String COLUMNNAME_SUFFIX_Override = "_Override";

	/**
	 * Gets columnName's override value or null
	 */
	@Nullable
	protected static <T> T getValueOverrideOrNull(final IModelInternalAccessor modelAccessor, final String columnName)
	{
		//
		// Try ColumnName_Override
		// e.g. for "C_Tax_ID", the C_Tax_ID_Override" will be checked
		{
			final String overrideColumnName = (columnName + COLUMNNAME_SUFFIX_Override);
			if (modelAccessor.hasColumnName(overrideColumnName))
			{
				@SuppressWarnings("unchecked")
				final T value = (T)modelAccessor.getValue(overrideColumnName, Object.class);
				if (value != null)
				{
					return value;
				}
			}
		}

		//
		// Try ColumnName_Override_ID
		// e.g. for "C_Tax_ID", the C_Tax_Override_ID" will be checked
		if (columnName.endsWith("_ID"))
		{
			final String overrideColumnName;
			if (columnName.endsWith("_Value_ID"))
			{
				overrideColumnName = (columnName.substring(0, columnName.length() - 9) + COLUMNNAME_SUFFIX_Override + "_Value_ID");
			}
			else
			{
				overrideColumnName = (columnName.substring(0, columnName.length() - 3) + COLUMNNAME_SUFFIX_Override + "_ID");
			}

			if (modelAccessor.hasColumnName(overrideColumnName))
			{
				@SuppressWarnings("unchecked")
				final T value = (T)modelAccessor.getValue(overrideColumnName, Object.class);
				return value; // might be null as well
			}
		}

		// No override values found => return null
		return null;
	}
}
