package org.adempiere.ad.table.api.impl;

import com.google.common.collect.ImmutableList;
import de.metas.adempiere.service.impl.TooltipType;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.impl.TableIdsCache.TableInfo;
import org.adempiere.ad.table.api.impl.TableIdsCache.TableInfoMap;
import org.adempiere.exceptions.AdempiereException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

class TableIdsCacheTest
{
	private static final AdTableId EXISTING_TABLE_ID = AdTableId.ofRepoId(318);
	private static final String EXISTING_TABLE_NAME = "C_Order";
	private static final AdTableId MISSING_TABLE_ID = AdTableId.ofRepoId(999_999);
	private static final String MISSING_TABLE_NAME = "NoSuchTable";

	private static TableInfo tableInfo(final AdTableId adTableId, final String tableName)
	{
		return TableInfo.builder()
				.adTableId(adTableId)
				.tableName(tableName)
				.entityType("D")
				.tooltipType(TooltipType.DEFAULT)
				.build();
	}

	private static TableInfoMap mapOf(final TableInfo... tableInfos)
	{
		return new TableInfoMap(ImmutableList.copyOf(tableInfos));
	}

	@Test
	@DisplayName("repeatedly looking up a missing table name reloads AD_Table only once")
	void repeatedMissingTableName_reloadsOnlyOnce()
	{
		final AtomicInteger loadCount = new AtomicInteger();
		final TableIdsCache cache = new TableIdsCache(() -> {
			loadCount.incrementAndGet();
			return mapOf(tableInfo(EXISTING_TABLE_ID, EXISTING_TABLE_NAME));
		});

		for (int i = 0; i < 5; i++)
		{
			assertThatThrownBy(() -> cache.getTableInfo(MISSING_TABLE_NAME))
					.isInstanceOf(AdempiereException.class);
		}

		// initial load + exactly one reset-triggered reload
		assertThat(loadCount.get()).isEqualTo(2);
	}

	@Test
	@DisplayName("repeatedly looking up a missing AD_Table_ID reloads AD_Table only once")
	void repeatedMissingTableId_reloadsOnlyOnce()
	{
		final AtomicInteger loadCount = new AtomicInteger();
		final TableIdsCache cache = new TableIdsCache(() -> {
			loadCount.incrementAndGet();
			return mapOf(tableInfo(EXISTING_TABLE_ID, EXISTING_TABLE_NAME));
		});

		for (int i = 0; i < 5; i++)
		{
			assertThatThrownBy(() -> cache.getTableInfo(MISSING_TABLE_ID))
					.isInstanceOf(AdempiereException.class);
		}

		assertThat(loadCount.get()).isEqualTo(2);
	}

	@Test
	@DisplayName("a table created after the cache was loaded is still picked up (recovery path kept)")
	void justCreatedTableName_isFoundAfterReload()
	{
		final AtomicInteger loadCount = new AtomicInteger();
		final TableIdsCache cache = new TableIdsCache(() -> loadCount.incrementAndGet() == 1
				? mapOf(tableInfo(EXISTING_TABLE_ID, EXISTING_TABLE_NAME))
				: mapOf(tableInfo(EXISTING_TABLE_ID, EXISTING_TABLE_NAME), tableInfo(MISSING_TABLE_ID, MISSING_TABLE_NAME)));

		assertThat(cache.getTableInfo(MISSING_TABLE_NAME).getTableName()).isEqualTo(MISSING_TABLE_NAME);
		assertThat(loadCount.get()).isEqualTo(2);
	}

	@Test
	@DisplayName("an AD_Table_ID created after the cache was loaded is still picked up (recovery path kept)")
	void justCreatedTableId_isFoundAfterReload()
	{
		final AtomicInteger loadCount = new AtomicInteger();
		final TableIdsCache cache = new TableIdsCache(() -> loadCount.incrementAndGet() == 1
				? mapOf(tableInfo(EXISTING_TABLE_ID, EXISTING_TABLE_NAME))
				: mapOf(tableInfo(EXISTING_TABLE_ID, EXISTING_TABLE_NAME), tableInfo(MISSING_TABLE_ID, MISSING_TABLE_NAME)));

		assertThat(cache.getTableInfo(MISSING_TABLE_ID).getAdTableId()).isEqualTo(MISSING_TABLE_ID);
		assertThat(loadCount.get()).isEqualTo(2);
	}

	@Test
	@DisplayName("an existing table is served from the cache without any reload")
	void existingTable_noReload()
	{
		final AtomicInteger loadCount = new AtomicInteger();
		final TableIdsCache cache = new TableIdsCache(() -> {
			loadCount.incrementAndGet();
			return mapOf(tableInfo(EXISTING_TABLE_ID, EXISTING_TABLE_NAME));
		});

		for (int i = 0; i < 5; i++)
		{
			assertThat(cache.getTableInfo(EXISTING_TABLE_NAME).getAdTableId()).isEqualTo(EXISTING_TABLE_ID);
			assertThat(cache.getTableInfo(EXISTING_TABLE_ID).getTableName()).isEqualTo(EXISTING_TABLE_NAME);
		}

		assertThat(loadCount.get()).isEqualTo(1);
	}
}
