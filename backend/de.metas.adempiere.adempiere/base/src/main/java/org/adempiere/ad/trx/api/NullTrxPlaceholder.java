package org.adempiere.ad.trx.api;

import java.sql.SQLException;
import java.util.Date;
import java.util.function.Function;
import java.util.function.Supplier;

import org.adempiere.exceptions.DBException;

import javax.annotation.Nullable;


/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
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
 * {@link ITrx} implementation to be used as null/out-of-transaction placeholder where null {@link ITrx} are not allowed (e.g. guava cache).
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class NullTrxPlaceholder implements ITrx
{
	public static final transient NullTrxPlaceholder instance = new NullTrxPlaceholder();

	public static ITrx boxNotNull(@Nullable final ITrx trx)
	{
		return trx != null ? trx : instance;
	}

	@Nullable
	public static ITrx unboxToNull(@Nullable final ITrx trx)
	{
		return trx != instance ? trx : null;
	}

	private NullTrxPlaceholder()
	{
	}

	@Override
	public String getTrxName()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean start()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean close()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isActive()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isAutoCommit()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Date getStartTime()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean commit(final boolean throwException) throws SQLException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean rollback()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean rollback(final boolean throwException) throws SQLException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean rollback(final ITrxSavepoint savepoint) throws DBException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public ITrxSavepoint createTrxSavepoint(final String name) throws DBException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void releaseSavepoint(final ITrxSavepoint savepoint)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public ITrxListenerManager getTrxListenerManager()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public ITrxManager getTrxManager()
	{
		throw new UnsupportedOperationException();

	}

	@Override
	public <T> T setProperty(final String name, final Object value)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T getProperty(final String name)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T getProperty(final String name, final Supplier<T> valueInitializer)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T getProperty(final String name, final Function<ITrx, T> valueInitializer)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T setAndGetProperty(final String name, final Function<T, T> valueRemappingFunction)
	{
		throw new UnsupportedOperationException();
	}

}
