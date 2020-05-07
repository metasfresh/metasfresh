package org.adempiere.process.event.impl;

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

import java.util.HashSet;
import java.util.Set;

import org.adempiere.process.event.IProcessEventListener;
import org.adempiere.process.event.IProcessEventSupport;
import org.adempiere.process.event.ProcessEvent;

public final class ProcessEventSupport implements IProcessEventSupport {

	private Set<IProcessEventListener> listeners = new HashSet<IProcessEventListener>();

	public void addListener(IProcessEventListener listener) {
		synchronized (listeners) {
			listeners.add(listener);
		}
	}

	public void removeListener(IProcessEventListener listener) {
		synchronized (listeners) {
			listeners.remove(listener);
		}
	}

	public void fireProcessEvent(final ProcessEvent event) {

		synchronized (listeners) {
			for (IProcessEventListener listener : listeners) {
				listener.processEvent(event);
			}
		}
	}

}
