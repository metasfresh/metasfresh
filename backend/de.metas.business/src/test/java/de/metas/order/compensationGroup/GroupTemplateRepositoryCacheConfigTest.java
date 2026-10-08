package de.metas.order.compensationGroup;

import de.metas.cache.CCache;
import de.metas.cache.CCacheConfig;
import de.metas.cache.CCacheStatsPredicate;
import de.metas.cache.CacheMgt;
import de.metas.order.model.I_C_CompensationGroup_Schema;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.business
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

/** The schema cache must hold more than a handful of schemas and must not depend on the instance-wide default cache type. */
class GroupTemplateRepositoryCacheConfigTest
{
	private GroupTemplateRepository repository;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		repository = GroupTemplateRepository.newInstanceForUnitTesting();
	}

	@Test
	void cache_isBoundedLRU()
	{
		final String cacheName = I_C_CompensationGroup_Schema.Table_Name;

		assertThat(CacheMgt.get().streamStats(CCacheStatsPredicate.builder().cacheNameContains(cacheName).build()).filter(stats -> stats.getName().equals(cacheName)))
				.isNotEmpty()
				.allSatisfy(stats -> assertThat(stats.getConfig())
						.returns(CCache.CacheMapType.LRU, CCacheConfig::getCacheMapType)
						.returns(100, CCacheConfig::getMaximumSize));
	}
}
