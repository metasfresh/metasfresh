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

package org.adempiere.model;

import de.metas.cache.CacheMgt;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.FakeTablePO;
import org.compiere.util.Env;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.ref.WeakReference;
import java.lang.reflect.Proxy;
import java.util.Properties;

import static org.adempiere.test.GarbageCollectorTestHelper.assertGarbageCollectorClearsUnreferencedObject;
import static org.adempiere.test.GarbageCollectorTestHelper.runGarbageCollectorUntilCleared;
import static org.assertj.core.api.Assertions.assertThat;

class POWrapperCacheLocalTest
{
	/**
	 * Model interface for {@link FakeTablePO}; not implemented by it, so {@link POWrapper#create(Object, Class)} wraps it.
	 */
	public interface I_Test_FakeTable
	{
		@SuppressWarnings("unused") String Table_Name = FakeTablePO.TABLE_NAME;
	}

	private Properties ctx;
	private WeakReference<POWrapper> parentRef;

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

	@Test
	void get_worksAfterGC_whenOnlyTheCacheLocalReferencesTheParent()
	{
		final FakeTablePO child = new FakeTablePO(ctx);
		final POWrapperCacheLocal cacheLocal = createCacheLocalReferencingChild(child);
		assertGarbageCollectorClearsUnreferencedObject(this::newPOWrapper);

		// a strong parent reference is never cleared; a weak one would be, and the next call would then fail
		runGarbageCollectorUntilCleared(parentRef);

		assertThat(cacheLocal.get()).isSameAs(child);
	}

	/**
	 * Creates the parent in a separate method, so that nothing but the returned cache-local can keep it reachable.
	 */
	private POWrapperCacheLocal createCacheLocalReferencingChild(final FakeTablePO child)
	{
		final POWrapper parent = newPOWrapper();
		parentRef = new WeakReference<>(parent);

		final POWrapperCacheLocal cacheLocal = new POWrapperCacheLocal(parent, FakeTablePO.REF_COLUMN_NAME, FakeTablePO.TABLE_NAME);
		cacheLocal.set(child);
		assertThat(cacheLocal.get()).isSameAs(child);
		return cacheLocal;
	}

	private POWrapper newPOWrapper()
	{
		final I_Test_FakeTable model = POWrapper.create(new FakeTablePO(ctx), I_Test_FakeTable.class);
		return (POWrapper)Proxy.getInvocationHandler(model);
	}
}
