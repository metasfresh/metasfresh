package de.metas.handlingunits.shipmentschedule.integrationtest;

/*
 * #%L
 * de.metas.handlingunits.base
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adempiere.util.lang.ITableRecordReference;
import org.junit.Assert;

import de.metas.event.Event;
import de.metas.event.IEventBus;
import de.metas.event.IEventListener;
import de.metas.inout.event.InOutProcessedEventBus;
import de.metas.inout.model.I_M_InOut;

/**
 * Listens to InOutGenerate topic, collects the inouts which were notified and later can compare with a given list.
 *
 * @author tsa
 *
 */
public class InOutGeneratedNotificationChecker implements IEventListener
{
	public static final InOutGeneratedNotificationChecker createAnSubscribe()
	{
		final InOutGeneratedNotificationChecker notificationsChecker = new InOutGeneratedNotificationChecker();
		InOutProcessedEventBus.newInstance().subscribe(notificationsChecker);

		return notificationsChecker;
	}

	private final Set<Integer> notifiedInOutIds = new HashSet<>();

	private InOutGeneratedNotificationChecker()
	{
		super();
	}

	@Override
	public void onEvent(final IEventBus eventBus, final Event event)
	{
		final ITableRecordReference inoutRecord = event.getRecord();
		final int inoutId = inoutRecord.getRecord_ID();

		notifiedInOutIds.add(inoutId);
	}

	/**
	 * Asserts that all the inouts from given list were notified on the bus.
	 *
	 * @param inouts
	 */
	public void assertAllNotified(final Iterable<? extends I_M_InOut> inouts)
	{
		final List<I_M_InOut> inoutsNotNotified = new ArrayList<>();

		for (final I_M_InOut inout : inouts)
		{
			final int inoutId = inout.getM_InOut_ID();
			if (!notifiedInOutIds.contains(inoutId))
			{
				inoutsNotNotified.add(inout);
			}
		}

		if (inoutsNotNotified.isEmpty())
		{
			return;
		}

		Assert.fail("Following inouts were expected to be notified by they were not: " + inoutsNotNotified);
	}
}
