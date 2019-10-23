package org.compiere.util;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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


/**
 * Implement what you need adapter for {@link TrxRunnable} / {@link TrxRunnable2}.
 * 
 * @author tsa
 *
 */
public abstract class TrxRunnableAdapter implements TrxRunnable2
{

	// makes no sense to allow developer to leave this method empty
	@Override
	public abstract void run(String localTrxName) throws Exception;

	/**
	 * At this level is just throwing the exception which will cause the transaction/savepoint to be rolled back and exception propagated to upper level.
	 */
	@Override
	public boolean doCatch(Throwable e) throws Throwable
	{
		throw e;
	}

	@Override
	public void doFinally()
	{
		// nothing
	}

}
