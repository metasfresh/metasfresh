package de.metas.rest_api.v2.bpartner.bpartnercomposite;

import com.google.common.collect.ImmutableSet;
import de.metas.cache.CCacheStats;
import de.metas.cache.CacheLabel;
import de.metas.cache.CacheMgt;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.business.rest-api-impl
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

class BPartnerCompositeCacheByLookupKeyTest
{
	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
	}

	/**
	 * One of these caches is created per {@code JsonRetrieverService}, i.e. once per BPartner REST API call.
	 * <p>
	 * {@link CacheMgt} keeps one {@code CachesGroup} per distinct {@link CacheLabel} for the lifetime of the JVM: the
	 * group holds its caches weakly, so a dead cache is collected, but the group itself is a strong value in
	 * {@code cachesByLabel} and is never removed. Therefore the label must NOT depend on the instance — otherwise
	 * every API call leaks one group (~1kB of guava-map scaffolding) that nothing can ever reclaim.
	 */
	@Test
	void cacheLabels_doNotGrow_whenManyInstancesAreCreated()
	{
		// the first instance legitimately introduces this cache's labels; measure from there
		final List<BPartnerCompositeCacheByLookupKey> held = new ArrayList<>();
		held.add(new BPartnerCompositeCacheByLookupKey());
		final Set<CacheLabel> labelsAfterFirstInstance = distinctLabelsOfLiveCaches();

		for (int i = 0; i < 100; i++)
		{
			// keep them reachable: CacheMgt holds its caches weakly, and a collected cache would hide the leak
			held.add(new BPartnerCompositeCacheByLookupKey());
		}

		assertThat(distinctLabelsOfLiveCaches())
				.as("creating further instances must not introduce additional cache labels")
				.isEqualTo(labelsAfterFirstInstance);
		assertThat(held).hasSize(101); // keep `held` strongly referenced until the assertions are done
	}

	private static Set<CacheLabel> distinctLabelsOfLiveCaches()
	{
		return CacheMgt.get()
				.streamStats()
				.map(CCacheStats::getLabels)
				.flatMap(Set::stream)
				.collect(ImmutableSet.toImmutableSet());
	}
}
