package de.metas.handlingunits.inout.impl;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.IContextAware;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;

import de.metas.handlingunits.receiptschedule.impl.ReceiptScheduleHUGenerator;

/*
 * #%L
 * de.metas.handlingunits.base
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

public class CustomerReturnLineHUGenerator
{
	private IContextAware _contextInitial;

	public static final CustomerReturnLineHUGenerator newInstance(final IContextAware context)
	{
		final IContextAware contextToUse;
		if (ITrx.TRXNAME_ThreadInherited.equals(context.getTrxName()))
		{
			contextToUse = context;
		}
		else
		{
			contextToUse = PlainContextAware.newWithThreadInheritedTrx(context.getCtx());
		}
		return new CustomerReturnLineHUGenerator()
				.setContext(contextToUse);
	}

	private final CustomerReturnLineHUGenerator setContext(final IContextAware context)
	{
		// needs to be threadInherited because we run in our own little TrxRunnable and everything created from the request shall be committed when we commit that runnable's local transaction.
		Check.errorUnless(ITrx.TRXNAME_ThreadInherited.equals(context.getTrxName()),
				"The trxName of the given request's HUContext needs to be {} or 'null', but is {}",
				ITrx.TRXNAME_ThreadInherited, context.getTrxName());

		_contextInitial = context;
		return this;
	}
}
