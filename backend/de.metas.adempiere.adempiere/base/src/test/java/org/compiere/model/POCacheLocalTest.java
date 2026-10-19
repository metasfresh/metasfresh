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

package org.compiere.model;

import de.metas.cache.CacheMgt;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.Env;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.ref.WeakReference;
import java.util.Properties;

import static org.adempiere.test.GarbageCollectorTestHelper.assertGarbageCollectorClearsUnreferencedObject;
import static org.adempiere.test.GarbageCollectorTestHelper.runGarbageCollectorUntilCleared;
import static org.assertj.core.api.Assertions.assertThat;

class POCacheLocalTest
{
	private Properties ctx;
	private WeakReference<PO> parentRef;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		ctx = Env.getCtx();
		FakeTablePO.registerPOInfo();
	}

	@AfterEach
	void afterEach()
	{
		CacheMgt.get().reset(); // drop the fake table's POInfo
	}

	/**
	 * The cache-local can be the last holder of its parent while one of its methods runs.
	 * E.g. in {@code X_AD_JavaClass.getAD_JavaClass_Type()} -> {@code PO.get_ValueAsPO} -> {@code POCacheLocal.get()},
	 * the parent is no longer used by any caller once {@code get_POCacheLocal} has returned.
	 */
	@Test
	void get_worksAfterGC_whenOnlyTheCacheLocalReferencesTheParent()
	{
		final FakeTablePO child = new FakeTablePO(ctx);
		final POCacheLocal cacheLocal = createCacheLocalReferencingChild(child);
		assertGarbageCollectorClearsUnreferencedObject(() -> new FakeTablePO(ctx));

		// with a strong reference the parent is never cleared; with a weak one it is, and the next call used to fail
		runGarbageCollectorUntilCleared(parentRef);

		assertThat(cacheLocal.get()).isSameAs(child);
	}

	/**
	 * Creates the parent in a separate method, so that nothing but the returned cache-local can keep it reachable.
	 */
	private POCacheLocal createCacheLocalReferencingChild(final FakeTablePO child)
	{
		final FakeTablePO parent = new FakeTablePO(ctx);
		parentRef = new WeakReference<>(parent);

		final POCacheLocal cacheLocal = POCacheLocal.newInstance(parent, FakeTablePO.REF_COLUMN_NAME, FakeTablePO.TABLE_NAME);
		cacheLocal.set(child);
		assertThat(cacheLocal.get()).isSameAs(child);
		return cacheLocal;
	}
}
