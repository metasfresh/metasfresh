package de.metas.dunning.api.impl;

/*
 * #%L
 * de.metas.dunning
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


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adempiere.util.Check;

import de.metas.dunning.api.IDunningEventDispatcher;
import de.metas.dunning.invoice.api.IInvoiceSourceBL.DunningDocLineSourceEvent;
import de.metas.dunning.model.I_C_DunningDoc_Line_Source;
import de.metas.dunning.model.I_C_Dunning_Candidate;
import de.metas.dunning.spi.IDunningCandidateListener;
import de.metas.dunning.spi.IDunningDocLineSourceListener;
import lombok.NonNull;

public class DunningEventDispatcher implements IDunningEventDispatcher
{
	private final Map<String, List<IDunningCandidateListener>> dunningCandidateListeners = new HashMap<String, List<IDunningCandidateListener>>();
	private final Map<DunningDocLineSourceEvent, List<IDunningDocLineSourceListener>> dunningDocLineSourceListeners = new HashMap<DunningDocLineSourceEvent, List<IDunningDocLineSourceListener>>();

	@Override
	public boolean registerDunningCandidateListener(final String eventName, final IDunningCandidateListener listener)
	{
		Check.assumeNotNull(eventName, "eventName not null");
		Check.assumeNotNull(listener, "listener not null");

		List<IDunningCandidateListener> eventListeners = dunningCandidateListeners.get(eventName);
		if (eventListeners == null)
		{
			eventListeners = new ArrayList<IDunningCandidateListener>();
			dunningCandidateListeners.put(eventName, eventListeners);
		}

		if (eventListeners.contains(listener))
		{
			return false;
		}

		eventListeners.add(listener);
		return true;
	}

	@Override
	public void fireDunningCandidateEvent(
			@NonNull final String eventName, 
			@NonNull final I_C_Dunning_Candidate candidate)
	{
		synchronized (dunningCandidateListeners)
		{
			final List<IDunningCandidateListener> listeners = dunningCandidateListeners.get(eventName);
			if (listeners == null)
			{
				return;
			}

			for (final IDunningCandidateListener listener : listeners)
			{
				listener.onEvent(eventName, candidate);
			}
		}
	}

	@Override
	public boolean registerDunningDocLineSourceListener(final DunningDocLineSourceEvent eventName, final IDunningDocLineSourceListener listener)
	{
		Check.assumeNotNull(eventName, "eventName not null");
		Check.assumeNotNull(listener, "listener not null");

		List<IDunningDocLineSourceListener> eventListeners = dunningDocLineSourceListeners.get(eventName);
		if (eventListeners == null)
		{
			eventListeners = new ArrayList<IDunningDocLineSourceListener>();
			dunningDocLineSourceListeners.put(eventName, eventListeners);
		}

		if (eventListeners.contains(listener))
		{
			return false;
		}

		eventListeners.add(listener);
		return true;
	}

	@Override
	public void fireDunningDocLineSourceEvent(final DunningDocLineSourceEvent eventName, final I_C_DunningDoc_Line_Source source)
	{
		Check.assumeNotNull(eventName, "eventName not null");
		Check.assumeNotNull(source, "source not null");

		synchronized (dunningDocLineSourceListeners)
		{
			final List<IDunningDocLineSourceListener> listeners = dunningDocLineSourceListeners.get(eventName);
			if (listeners == null)
			{
				return;
			}

			for (final IDunningDocLineSourceListener listener : listeners)
			{
				listener.onEvent(eventName, source);
			}
		}
	}
}
