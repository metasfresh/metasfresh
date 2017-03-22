package org.adempiere.ad.trx.api;

import java.util.concurrent.Callable;

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
@FunctionalInterface
public interface TrxCallable<ResultType> extends Callable<ResultType>
{
	/** Value to be returned by {@link #doCatch(Throwable)} */
	boolean ROLLBACK = true;
	/** Value to be returned by {@link #doCatch(Throwable)} */
	boolean DONT_ROLLBACK = false;
	
	@Override
	ResultType call() throws Exception;

	/**
	 * Method called when {@link #call()} throws an exception. In this method you can handle this exception or throw another exception. If an exception is thrown or method returns true, the
	 * transaction will be rollback.
	 * 
	 * Please note, this method is called before transaction is rolled-back or savepoint is released
	 * 
	 * @param e exception
	 * @return true if transaction should be rollback
	 */
	default boolean doCatch(Throwable e) throws Throwable
	{
		throw e;
	}

	/**
	 * Method called after {@link #run(String)} runs.
	 * 
	 * Please note, this method is called AFTER transaction is rolled-back or savepoint is released
	 */
	default void doFinally()
	{
		// nothing
	}
}
