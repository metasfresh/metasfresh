/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.ui.web.view.invalidation;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import de.metas.cache.CCache;
import de.metas.ui.web.base.model.I_WEBUI_ViewInvalidateOnChange;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Reads the {@link I_WEBUI_ViewInvalidateOnChange} config table into an in-memory
 * trigger-table-name &rarr; {@link WindowId} map, cached in a table-scoped {@link CCache} that
 * auto-invalidates whenever a config row changes.
 * <p>
 * On a vanilla instance the table is empty, so both accessors return empty sets and
 * {@link ConfiguredViewInvalidationListener} is a total no-op.
 * <p>
 * Pattern mirrors {@code de.metas.incoterms.IncotermsRepository}.
 *
 * Repository Tables: WEBUI_ViewInvalidateOnChange
 * Repository Cluster: WebuiViewInvalidateOnChangeRepository
 */
@Repository
public class WebuiViewInvalidateOnChangeRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);

	private final CCache<Integer, TriggerTableToWindows> cache = CCache.<Integer, TriggerTableToWindows>builder()
			.tableName(I_WEBUI_ViewInvalidateOnChange.Table_Name)
			.maximumSize(1)
			.build();

	/**
	 * @return the window ids whose views must be full-invalidated when a record of the given
	 * trigger table changes; empty if the table is not configured as a trigger.
	 */
	public Set<WindowId> getWindowIdsToInvalidateForTable(@NonNull final String tableName)
	{
		return getMapping().getWindowIds(tableName);
	}

	/**
	 * @return all configured trigger table names (for the listener's cheap pre-filter).
	 */
	public Set<String> getAllTriggerTableNames()
	{
		return getMapping().getAllTableNames();
	}

	private TriggerTableToWindows getMapping()
	{
		return cache.getOrLoadNonNull(0, this::retrieve);
	}

	private TriggerTableToWindows retrieve()
	{
		final ImmutableSetMultimap.Builder<String, WindowId> builder = ImmutableSetMultimap.builder();

		queryBL.createQueryBuilder(I_WEBUI_ViewInvalidateOnChange.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.forEach(record -> {
					final String tableName = adTableDAO.retrieveTableName(AdTableId.ofRepoId(record.getAD_Table_ID()));
					final WindowId windowId = WindowId.of(record.getAD_Window_ID());
					builder.put(tableName, windowId);
				});

		return new TriggerTableToWindows(builder.build());
	}

	private static final class TriggerTableToWindows
	{
		private final ImmutableSetMultimap<String, WindowId> windowIdsByTableName;

		private TriggerTableToWindows(@NonNull final ImmutableSetMultimap<String, WindowId> windowIdsByTableName)
		{
			this.windowIdsByTableName = windowIdsByTableName;
		}

		public Set<WindowId> getWindowIds(@NonNull final String tableName)
		{
			return windowIdsByTableName.get(tableName);
		}

		public Set<String> getAllTableNames()
		{
			return ImmutableSet.copyOf(windowIdsByTableName.keySet());
		}
	}
}
