package de.metas.movement.event;

import java.util.Collection;
import java.util.Collections;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Movement;
import org.compiere.util.Env;

import de.metas.document.engine.IDocumentBL;
import de.metas.event.Event;
import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.event.QueueableForwardingEventBus;
import de.metas.event.Topic;
import de.metas.event.Type;

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

public class MovementProcessedEventBus extends QueueableForwardingEventBus
{
	public static final MovementProcessedEventBus newInstance()
	{
		final IEventBus eventBus = Services.get(IEventBusFactory.class).getEventBus(EVENTBUS_TOPIC);
		return new MovementProcessedEventBus(eventBus);
	}

	/** Topic used to send notifications about shipments/receipts that were generated/reversed asynchronously */
	public static final Topic EVENTBUS_TOPIC = Topic.builder()
			.name("de.metas.movement.Movement.ProcessedEvents")
			.type(Type.REMOTE)
			.build();

	// services
	private final transient IDocumentBL docActionBL = Services.get(IDocumentBL.class);

	private static final String MSG_Event_MovementGenerated = "Event_MovementGenerated";

	private MovementProcessedEventBus(final IEventBus delegate)
	{
		super(delegate);
	}

	@Override
	public MovementProcessedEventBus queueEvents()
	{
		super.queueEvents();
		return this;
	}

	@Override
	public MovementProcessedEventBus queueEventsUntilTrxCommit(final String trxName)
	{
		super.queueEventsUntilTrxCommit(trxName);
		return this;
	}

	@Override
	public MovementProcessedEventBus queueEventsUntilCurrentTrxCommit()
	{
		super.queueEventsUntilCurrentTrxCommit();
		return this;
	}

	/**
	 * Post events about given shipment/receipts that were processed.
	 *
	 * @param inouts
	 * @see #notify(I_M_InOut)
	 */
	public MovementProcessedEventBus notify(final Collection<? extends I_M_Movement> movements)
	{
		if (movements == null || movements.isEmpty())
		{
			return this;
		}

		for (final I_M_Movement movement : movements)
		{
			final Event event = createMovementGeneratedEvent(movement);
			postEvent(event);
		}

		return this;
	}

	/**
	 * Post events about given shipment/receipts that were processed, i.e.
	 * <ul>
	 * <li>if inout's DocStatus is Completed, a "generated" notification will be sent
	 * <li>if inout's DocStatus is Voided or Reversed, a "reversed" notification will be sent
	 * </ul>
	 *
	 * @param inout
	 * @return
	 */
	public final MovementProcessedEventBus notify(final I_M_Movement movement)
	{
		Check.assumeNotNull(movement, "inout not null");
		notify(Collections.singleton(movement));
		return this;
	}

	private final Event createMovementGeneratedEvent(final I_M_Movement movement)
	{
		Check.assumeNotNull(movement, "movement not null");

		final String adMessage = getNotificationAD_Message(movement);
		final int recipientUserId = getNotificationRecipientUserId(movement);

		final Event event = Event.builder()
				.setDetailADMessage(adMessage, TableRecordReference.of(movement))
				.addRecipient_User_ID(recipientUserId)
				.setRecord(TableRecordReference.of(movement))
				.build();
		return event;
	}

	private final String getNotificationAD_Message(final I_M_Movement movement)
	{

		return MSG_Event_MovementGenerated;
	}

	private final int getNotificationRecipientUserId(final I_M_Movement movement)
	{
		//
		// In case of reversal i think we shall notify the current user too
		if (docActionBL.isDocumentReversedOrVoided(movement))
		{
			final int currentUserId = Env.getAD_User_ID(Env.getCtx()); // current/triggering user
			if (currentUserId > 0)
			{
				return currentUserId;
			}

			return movement.getUpdatedBy(); // last updated
		}
		//
		// Fallback: notify only the creator
		else
		{
			return movement.getCreatedBy();
		}
	}
}
