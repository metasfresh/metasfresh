package de.metas.inoutcandidate.spi.impl;

/*
 * #%L
 * de.metas.swat.base
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


import java.util.concurrent.CopyOnWriteArrayList;

import org.adempiere.util.Check;

import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule_Alloc;
import de.metas.inoutcandidate.spi.IReceiptScheduleListener;

public class CompositeReceiptScheduleListener implements IReceiptScheduleListener
{
	private final CopyOnWriteArrayList<IReceiptScheduleListener> listeners = new CopyOnWriteArrayList<IReceiptScheduleListener>();

	public void addReceiptScheduleListener(final IReceiptScheduleListener listener)
	{
		Check.assumeNotNull(listener, "listener not null");
		listeners.addIfAbsent(listener);
	}

	@Override
	public void onReceiptScheduleAllocReversed(final I_M_ReceiptSchedule_Alloc rsa, final I_M_ReceiptSchedule_Alloc rsaReversal)
	{
		for (final IReceiptScheduleListener listener : listeners)
		{
			listener.onReceiptScheduleAllocReversed(rsa, rsaReversal);
		}
	}

	@Override
	public void onBeforeClose(I_M_ReceiptSchedule receiptSchedule)
	{
		for (final IReceiptScheduleListener listener : listeners)
		{
			listener.onBeforeClose(receiptSchedule);
		}
	}

	@Override
	public void onAfterClose(I_M_ReceiptSchedule receiptSchedule)
	{
		for (final IReceiptScheduleListener listener : listeners)
		{
			listener.onAfterClose(receiptSchedule);
		}
	}

	@Override
	public void onBeforeReopen(I_M_ReceiptSchedule receiptSchedule)
	{
		for (final IReceiptScheduleListener listener : listeners)
		{
			listener.onBeforeReopen(receiptSchedule);
		}
	}

	@Override
	public void onAfterReopen(I_M_ReceiptSchedule receiptSchedule)
	{
		for (final IReceiptScheduleListener listener : listeners)
		{
			listener.onAfterReopen(receiptSchedule);
		}
	}

	@Override
	public String toString()
	{
		return "CompositeReceiptScheduleListener [listeners=" + listeners + "]";
	}

}
