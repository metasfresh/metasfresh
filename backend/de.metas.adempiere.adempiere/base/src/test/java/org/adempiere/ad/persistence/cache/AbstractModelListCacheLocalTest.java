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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package org.adempiere.ad.persistence.cache;

import com.google.common.collect.ImmutableList;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_C_BPartner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.lang.ref.WeakReference;
import java.util.Comparator;
import java.util.List;

import static org.adempiere.test.GarbageCollectorTestHelper.assertGarbageCollectorClearsUnreferencedObject;
import static org.adempiere.test.GarbageCollectorTestHelper.runGarbageCollectorUntilCleared;
import static org.assertj.core.api.Assertions.assertThat;

class AbstractModelListCacheLocalTest
{
	private static final String ITEM = "item";

	private static class TestModelListCacheLocal extends AbstractModelListCacheLocal<I_C_BPartner, String>
	{
		TestModelListCacheLocal(final I_C_BPartner parentModel)
		{
			super(parentModel);
		}

		@Override
		@Nullable
		protected Comparator<String> createItemsComparator()
		{
			return null;
		}

		@Override
		protected List<String> retrieveItems(final IContextAware ctx, final I_C_BPartner parentModel)
		{
			return ImmutableList.of(ITEM);
		}

		@Override
		protected Object mkKey(final String item)
		{
			return item;
		}
	}

	private WeakReference<I_C_BPartner> parentRef;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	void getItems_worksAfterGC_whenOnlyTheCacheReferencesTheParent()
	{
		final TestModelListCacheLocal cache = createCache();
		assertGarbageCollectorClearsUnreferencedObject(() -> InterfaceWrapperHelper.newInstance(I_C_BPartner.class));

		runGarbageCollectorUntilCleared(parentRef);

		assertThat(cache.getItems()).containsExactly(ITEM);
	}

	/**
	 * Creates the parent in a separate method, so that nothing but the returned cache can keep it reachable.
	 */
	private TestModelListCacheLocal createCache()
	{
		final I_C_BPartner parent = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);
		parentRef = new WeakReference<>(parent);
		return new TestModelListCacheLocal(parent);
	}
}
