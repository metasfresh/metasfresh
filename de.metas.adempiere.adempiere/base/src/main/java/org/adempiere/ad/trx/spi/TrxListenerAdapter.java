package org.adempiere.ad.trx.spi;

/*
 * #%L
 * ADempiere ERP - Base
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


import org.adempiere.ad.trx.api.ITrx;

/**
 * An abstract adapter class for receiving transaction events.
 * 
 * The methods in this class are empty. This class exists as convenience for creating listener objects.
 * 
 * @author tsa
 * 
 */
public abstract class TrxListenerAdapter implements ITrxListener
{
	@Override
	public void beforeCommit(ITrx trx)
	{
		// nothing
	}

	@Override
	public void afterCommit(ITrx trx)
	{
		// nothing
	}

	@Override
	public void afterRollback(ITrx trx)
	{
		// nothing
	}

	@Override
	public void afterClose(ITrx trx)
	{
		// nothing
	}
}
