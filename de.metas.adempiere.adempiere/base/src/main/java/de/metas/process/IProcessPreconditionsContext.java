package de.metas.process;

import java.util.List;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * Preconditions checking context.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IProcessPreconditionsContext
{
	/**
	 * @return underlying AD_Window_ID or <code>-1</code> if not available
	 */
	int getAD_Window_ID();

	/**
	 * @return underlying table name or <code>null</code>
	 */
	String getTableName();

	/**
	 * @param modelClass
	 * @return single selected row's model
	 */
	<T> T getSelectedModel(final Class<T> modelClass);

	/**
	 * @param modelClass
	 * @return all selected rows's model(s)
	 */
	<T> List<T> getSelectedModels(final Class<T> modelClass);

	/**
	 * Gets how many rows were selected.
	 * In case the size is not determined, an exception is thrown.
	 * 
	 * Instead of calling this method, always consider using {@link #isNoSelection()}, {@link #isSingleSelection()}, {@link #isMoreThanOneSelected()} if applicable.
	 */
	int getSelectionSize();

	/** @return true if there is no selected rows */
	default boolean isNoSelection()
	{
		return getSelectionSize() <= 0;
	}

	/** @return true if only one row is selected */
	default boolean isSingleSelection()
	{
		return getSelectionSize() == 1;
	}

	/** @return true if there are more then one selected row */
	default boolean isMoreThanOneSelected()
	{
		return getSelectionSize() > 1;
	}
}
