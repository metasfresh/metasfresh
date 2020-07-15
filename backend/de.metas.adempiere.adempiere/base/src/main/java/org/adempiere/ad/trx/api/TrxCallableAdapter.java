package org.adempiere.ad.trx.api;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public abstract class TrxCallableAdapter<ResultType> implements TrxCallable<ResultType>
{
	// makes no sense to allow developer to leave this method empty
	@Override
	public abstract ResultType call() throws Exception;

	/**
	 * At this level is just throwing the exception which will cause the transaction/savepoint to be rolled back and exception propagated to upper level.
	 */
	@Override
	public boolean doCatch(final Throwable e) throws Throwable
	{
		throw e;
	}

	@Override
	public void doFinally()
	{
		// nothing
	}

}
