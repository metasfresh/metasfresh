package de.metas.fresh.picking;

/*
 * #%L
 * de.metas.fresh.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.adempiere.util.Check;

import de.metas.adempiere.form.terminal.DefaultKeyLayout;
import de.metas.adempiere.form.terminal.ITerminalKey;
import de.metas.adempiere.form.terminal.context.ITerminalContext;

public class DeliveryDateKeyLayout extends DefaultKeyLayout
{

	public DeliveryDateKeyLayout(final ITerminalContext tc)
	{
		super(tc);
	}

	/**
	 * Currently set dates
	 */
	private Set<Date> _deliveryDates = Collections.emptySet();

	/**
	 * Create keys and set them from given {@link Date}s set.
	 *
	 * @param deliveryDates
	 */
	public void createAndSetKeysFromDates(final Set<Date> deliveryDates)
	{
		//
		// Normalize and sort new Delivery Dates
		final Set<Date> deliveryDatesSorted;
		if (deliveryDates == null || deliveryDates.isEmpty())
		{
			deliveryDatesSorted = Collections.emptySet();
		}
		else
		{
			deliveryDatesSorted = new TreeSet<Date>(deliveryDates);
		}

		//
		// Check if there will be an actual change
		if (Check.equals(_deliveryDates, deliveryDatesSorted))
		{
			return;
		}

		// gh #458: pass the actual business logic to the super class which also will handle the ITerminalContextReferences.
		disposeCreateDetachReverences(
				() -> {

					final List<ITerminalKey> deliveryDateKeys = new ArrayList<ITerminalKey>(deliveryDatesSorted.size());

					//
					// Create new DeliveryDate Keys
					for (final Date deliveryDate : deliveryDatesSorted)
					{
						final DeliveryDateKey deliveryDateKey = new DeliveryDateKey(getTerminalContext(), deliveryDate);
						deliveryDateKeys.add(deliveryDateKey);
					}

					//
					// Set new Keys
					setKeys(deliveryDateKeys);
					_deliveryDates = deliveryDatesSorted;
					return null;
				});
	}
}
