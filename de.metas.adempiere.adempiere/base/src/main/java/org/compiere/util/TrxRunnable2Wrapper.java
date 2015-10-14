/**
 * 
 */
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


import org.adempiere.util.Check;

/**
 * Wraps an {@link TrxRunnable} and make it to behave like an {@link TrxRunnable2}.
 * 
 * @author tsa
 *
 */
public final class TrxRunnable2Wrapper implements TrxRunnable2
{
	private final TrxRunnable runnable;

	public TrxRunnable2Wrapper(TrxRunnable r)
	{
		super();

		Check.assumeNotNull(r, "runnable not null");
		this.runnable = r;
	}

	@Override
	public String toString()
	{
		return "TrxRunnable2Wrapper [runnable=" + runnable + "]";
	}

	@Override
	public void run(String trxName) throws Exception
	{
		runnable.run(trxName);
	}

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
