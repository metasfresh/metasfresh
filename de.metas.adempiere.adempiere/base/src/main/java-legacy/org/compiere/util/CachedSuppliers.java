package org.compiere.util;

import java.util.function.Function;
import java.util.function.Supplier;

import org.adempiere.util.Functions;

import lombok.NonNull;
import lombok.ToString;

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

public final class CachedSuppliers
{
	private CachedSuppliers()
	{
	}

	public static final <T> Supplier<T> renewOnCacheReset(final Supplier<T> supplier)
	{
		return new RenewOnCacheResetSupplier<>(supplier);
	}

	@ToString
	private static final class RenewOnCacheResetSupplier<T> implements Supplier<T>
	{
		private final Function<Long, T> delegate;

		private RenewOnCacheResetSupplier(@NonNull final Supplier<T> delegate)
		{
			this.delegate = Functions.memoizingLastCall(lastCacheReset -> delegate.get());
		}

		@Override
		public T get()
		{
			final long lastCacheResetNow = CacheMgt.get().getLastCacheReset();
			return delegate.apply(lastCacheResetNow);
		}
	}
}
