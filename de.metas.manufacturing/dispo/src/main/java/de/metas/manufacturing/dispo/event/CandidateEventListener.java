package de.metas.manufacturing.dispo.event;

import de.metas.event.Event;
import de.metas.event.IEventBus;
import de.metas.event.IEventListener;
import de.metas.manufacturing.dispo.Candidate;

/*
 * #%L
 * metasfresh-manufacturing-dispo
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

/**
 * Reacts to events on
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class CandidateEventListener implements IEventListener
{
	enum EventType
	{
		/**
		 * An existing candidate was changed
		 */
		CHANGE,

		/**
		 * A new demand candidate was created. In this case, we need to<br>
		 * create a child candidate with type {@link Candidate.Type#STOCK} and with the negated value of the demand candidate's qty.
		 */
		NEW_DEMAND,

		/**
		 *
		 */
		NEW_SUPPLY,

		/**
		 * A new stock candidate was created. Invalidate all existing "stock" candidates that have the same product and locator and that have a later projected date.
		 */
		NEW_STOCK,
	}

	@Override
	public void onEvent(final IEventBus eventBus, final Event event)
	{
		final EventType eventType = event.getProperty(EventType.class.getName());

		switch (eventType)
		{
			case CHANGE:
				// 1. invalidate the given candidate's "children" if needed, and for "stock"-candidates, also invalidate "later" stock-candidates that refer to the same locator and product
				// 2. revalidate
				break;

			default:
				break;
		}
	}

}
