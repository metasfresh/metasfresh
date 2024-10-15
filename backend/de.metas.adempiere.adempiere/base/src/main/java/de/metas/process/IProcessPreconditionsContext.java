package de.metas.process;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.element.api.AdTabId;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.util.lang.impl.TableRecordReference;

import com.google.common.collect.ImmutableSet;

import lombok.NonNull;

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
 */
public interface IProcessPreconditionsContext
{
	@Nullable
	AdWindowId getAdWindowId();

	@Nullable
	AdTabId getAdTabId();

	/**
	 * @return underlying table name or <code>null</code>
	 */
	String getTableName();

	/**
	 * @return single selected row's model
	 * @deprecated please use {@link #getSingleSelectedRecordId()} and read via DAO/Repository instead
	 */
	@Deprecated
	@Nullable
	<T> T getSelectedModel(final Class<T> modelClass);

	/**
	 * @return all selected rows's model(s)
	 * @deprecated please use {@link #getSingleSelectedRecordId()} and read via DAO/Repository instead
	 */
	@Deprecated
	<T> List<T> getSelectedModels(final Class<T> modelClass);
	
	@NonNull
	<T> Stream<T> streamSelectedModels(@NonNull final Class<T> modelClass);

	/**
	 * @return single Record_ID; throws exception otherwise
	 */
	int getSingleSelectedRecordId();

	/**
	 * Gets how many rows were selected.
	 * In case the size is not determined, an exception is thrown.
	 * <p>
	 * Instead of calling this method, always consider using {@link #isNoSelection()}, {@link #isSingleSelection()}, {@link #isMoreThanOneSelected()} if applicable.
	 */
	SelectionSize getSelectionSize();

	default boolean isNoSelection()
	{
		return getSelectionSize().isNoSelection();
	}

	default boolean isSingleSelection()
	{
		return getSelectionSize().isSingleSelection();
	}

	default boolean isMoreThanOneSelected()
	{
		return getSelectionSize().isMoreThanOneSelected();
	}

	/**
	 * @return selected included rows of current single selected document
	 */
	default Set<TableRecordReference> getSelectedIncludedRecords()
	{
		return ImmutableSet.of();
	}

	<T> IQueryFilter<T> getQueryFilter(@NonNull Class<T> recordClass);
}
