package org.compiere.model;

import org.adempiere.ad.ui.spi.ITabCallout;
import org.adempiere.util.Check;
import org.compiere.model.StateChangeEvent.StateChangeEventType;

import com.google.common.base.MoreObjects;

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

/**
 * Listen on {@link GridTab}'s {@link StateChangeEvent}s and call the proper {@link ITabCallout} methods.
 */
/*package */final class GridTabCalloutStateChangeListener implements StateChangeListener
{
	public static final void bind(final GridTab gridTab, final ITabCallout callouts)
	{
		if(callouts == null || callouts == ITabCallout.NULL)
		{
			return; // nothing to bind
		}
		
		final GridTabCalloutStateChangeListener listener = new GridTabCalloutStateChangeListener(callouts);
		gridTab.addStateChangeListener(listener);
	}
	
	private final ITabCallout callouts;

	private GridTabCalloutStateChangeListener(final ITabCallout callouts)
	{
		Check.assumeNotNull(callouts, "callouts not null");
		this.callouts = callouts;
	}
	
	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.addValue(callouts)
				.toString();
	}

	@Override
	public void stateChange(final StateChangeEvent event)
	{
		final GridTab gridTab = event.getSource();
		final StateChangeEventType eventType = event.getEventType();
		switch (eventType)
		{
			case DATA_REFRESH_ALL:
				callouts.onRefreshAll(gridTab);
				break;
			case DATA_REFRESH:
				callouts.onRefresh(gridTab);
				break;
			case DATA_NEW:
				callouts.onNew(gridTab);
				break;
			case DATA_DELETE:
				callouts.onDelete(gridTab);
				break;
			case DATA_SAVE:
				callouts.onSave(gridTab);
				break;
			case DATA_IGNORE:
				callouts.onIgnore(gridTab);
				break;
			default:
				// tolerate all other events, event if they are meaningless for us
				// throw new AdempiereException("EventType " + eventType + " is not supported");
				break;
		}
	}

}
