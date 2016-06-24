package de.metas.rfq.event.impl;

import java.util.concurrent.CopyOnWriteArrayList;

import org.adempiere.util.Check;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.rfq.event.IRfQEventListener;
import de.metas.rfq.model.I_C_RfQ;
import de.metas.rfq.model.I_C_RfQResponse;

/*
 * #%L
 * de.metas.rfq
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/* package */final class CompositeRfQEventListener implements IRfQEventListener
{
	private static final Logger logger = LogManager.getLogger(CompositeRfQEventListener.class);

	private final CopyOnWriteArrayList<IRfQEventListener> listeners = new CopyOnWriteArrayList<>();

	public void addListener(final IRfQEventListener listener)
	{
		Check.assumeNotNull(listener, "listener not null");
		final boolean added = listeners.addIfAbsent(listener);
		if (!added)
		{
			logger.warn("Skip adding {} because it was already added", listener);
		}
	}

	@Override
	public void onBeforeComplete(final I_C_RfQ rfq)
	{
		for (final IRfQEventListener listener : listeners)
		{
			listener.onBeforeComplete(rfq);
		}
	}

	@Override
	public void onAfterComplete(final I_C_RfQ rfq)
	{
		for (final IRfQEventListener listener : listeners)
		{
			listener.onAfterComplete(rfq);
		}
	}

	@Override
	public void onBeforeClose(final I_C_RfQ rfq)
	{
		for (final IRfQEventListener listener : listeners)
		{
			listener.onBeforeClose(rfq);
		}
	}

	@Override
	public void onAfterClose(final I_C_RfQ rfq)
	{
		for (final IRfQEventListener listener : listeners)
		{
			listener.onAfterClose(rfq);
		}
	}

	@Override
	public void onDraftCreated(final I_C_RfQResponse rfqResponse)
	{
		for (final IRfQEventListener listener : listeners)
		{
			listener.onDraftCreated(rfqResponse);
		}
	}

	@Override
	public void onBeforeComplete(final I_C_RfQResponse rfqResponse)
	{
		for (final IRfQEventListener listener : listeners)
		{
			listener.onBeforeComplete(rfqResponse);
		}
	}

	@Override
	public void onAfterComplete(final I_C_RfQResponse rfqResponse)
	{
		for (final IRfQEventListener listener : listeners)
		{
			listener.onAfterComplete(rfqResponse);
		}
	}

	@Override
	public void onBeforeClose(final I_C_RfQResponse rfqResponse)
	{
		for (final IRfQEventListener listener : listeners)
		{
			listener.onBeforeClose(rfqResponse);
		}
	}

	@Override
	public void onAfterClose(final I_C_RfQResponse rfqResponse)
	{
		for (final IRfQEventListener listener : listeners)
		{
			listener.onAfterClose(rfqResponse);
		}
	}

	@Override
	public void onBeforeUnClose(final I_C_RfQ rfq)
	{
		for (final IRfQEventListener listener : listeners)
		{
			listener.onBeforeUnClose(rfq);
		}
	}

	@Override
	public void onAfterUnClose(final I_C_RfQ rfq)
	{
		for (final IRfQEventListener listener : listeners)
		{
			listener.onAfterUnClose(rfq);
		}
	}

	@Override
	public void onBeforeUnClose(final I_C_RfQResponse rfqResponse)
	{
		for (final IRfQEventListener listener : listeners)
		{
			listener.onBeforeUnClose(rfqResponse);
		}
	}

	@Override
	public void onAfterUnClose(final I_C_RfQResponse rfqResponse)
	{
		for (final IRfQEventListener listener : listeners)
		{
			listener.onAfterUnClose(rfqResponse);
		}
	}
}
