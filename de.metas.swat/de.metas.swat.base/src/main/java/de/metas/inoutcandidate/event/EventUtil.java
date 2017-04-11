package de.metas.inoutcandidate.event;

import java.util.Set;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.ModelValidator;

import de.metas.event.Event;
import de.metas.event.Event.Builder;
import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.manufacturing.event.ReceiptScheduleEventBus;
import de.metas.manufacturing.event.ManufactoringEventBus;
import de.metas.manufacturing.event.ShipmentScheduleEventBus;

/*
 * #%L
 * de.metas.swat.base
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

public class EventUtil
{
	public static final EventUtil INSTANCE = new EventUtil();

	private EventUtil()
	{

	}

	public static EventUtil get()
	{
		return INSTANCE;
	}

	public void fireEventAfterCommit(final Object schedule, final int type)
	{
		switch (type)
		{
			case ModelValidator.TYPE_AFTER_DELETE:
				fireDeleteEvent(schedule);
				break;
			case ModelValidator.TYPE_AFTER_NEW:
				fireNewOrChangedEvent(schedule, type);
				break;
			case ModelValidator.TYPE_AFTER_CHANGE:
				fireNewOrChangedEvent(schedule, type);
				break;
			default:
				// nothing to do for any other type
				break;
		}
	}

	private void fireDeleteEvent(final Object schedule)
	{
		Check.errorIf(schedule == null, "Param 'schedule' may not be null");

		final IEventBus eventBus = getEventBus(schedule);

		final Event event = Event.builder()
				.setRecord(TableRecordReference.of(schedule))
				.putProperty(ManufactoringEventBus.MODEL_INTERCEPTOR_TIMING, ModelValidator.TYPE_AFTER_DELETE)
				.build();
		final String trxName = InterfaceWrapperHelper.getTrxName(schedule);

		postAfterCommit(eventBus, event, trxName);
	}

	private IEventBus getEventBus(final Object schedule)
	{
		final IEventBusFactory eventBusFactory = Services.get(IEventBusFactory.class);

		if (schedule instanceof I_M_ShipmentSchedule)
		{
			return eventBusFactory.getEventBus(ShipmentScheduleEventBus.EVENTBUS_TOPIC);
		}
		else if (schedule instanceof I_M_ReceiptSchedule)
		{
			return eventBusFactory.getEventBus(ReceiptScheduleEventBus.EVENTBUS_TOPIC);
		}

		Check.errorIf(true, "Param 'schedule' has an unsuported type: {}", schedule);
		return null; // won't be reached
	}

	private void fireNewOrChangedEvent(final Object schedule, final int type)
	{
		final Builder eventBuilder = Event.builder()
				.putProperty(ManufactoringEventBus.MODEL_INTERCEPTOR_TIMING, type)
				.setRecord(TableRecordReference.of(schedule));

		final IEventBus eventBus = getEventBus(schedule);

		final Set<String> modelColumnNames = InterfaceWrapperHelper.getModelColumnNames(schedule.getClass());
		for (final String columnName : modelColumnNames)
		{
			if (!InterfaceWrapperHelper.isValueChanged(schedule, columnName))
			{
				continue;
			}

			final Object value = InterfaceWrapperHelper.getValueOrNull(schedule, columnName);
			eventBuilder.putPropertyFromObject(columnName, value);
		}

		final Event event = eventBuilder.build();
		final String trxName = InterfaceWrapperHelper.getTrxName(schedule);

		postAfterCommit(eventBus, event, trxName);
	}

	private void postAfterCommit(final IEventBus eventBus, final Event event, final String trxName)
	{
		Services.get(ITrxManager.class)
				.getTrxListenerManager(trxName)
				.onAfterCommit(() -> eventBus.postEvent(event));
	}
}
