package de.metas.ui.web.window.model.lookup;

import java.util.List;
import java.util.Optional;

import org.compiere.util.CCache.CCacheStats;

import com.google.common.collect.ImmutableList;

import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.WindowId;

/*
 * #%L
 * metasfresh-webui-api
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public interface LookupDataSourceFetcher
{
	LookupValue LOOKUPVALUE_NULL = IntegerLookupValue.of(-1, "");

	boolean isNumericKey();

	LookupDataSourceContext.Builder newContextForFetchingById(Object id);

	LookupValue retrieveLookupValueById(LookupDataSourceContext evalCtx);

	LookupDataSourceContext.Builder newContextForFetchingList();

	LookupValuesList retrieveEntities(LookupDataSourceContext evalCtx);

	//
	// Caching
	//@formatter:off
	/** @return true if this fetcher already has caching embedded so on upper levels, caching is not needed */
	boolean isCached();
	/** @return cache prefix; relevant only if {@link #isCached()} returns <code>false</code> */
	String getCachePrefix();
	default List<CCacheStats> getCacheStats() { return ImmutableList.of(); }
	//@formatter:on

	/** @return tableName if available */
	Optional<String> getLookupTableName();

	/** @return optional WindowId to be used when zooming into */
	Optional<WindowId> getZoomIntoWindowId();
}
