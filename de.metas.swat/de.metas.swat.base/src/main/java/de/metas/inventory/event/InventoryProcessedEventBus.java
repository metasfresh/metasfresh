package de.metas.inventory.event;

import java.util.Collection;
import java.util.Collections;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_M_Inventory;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class InventoryProcessedEventBus extends QueueableForwardingEventBus
{
	/**
	 * M_Inventory internal use
	 */
	private static final int WINDOW_INTERNAL_INVENTORY = 341; // FIXME: HARDCODED


	public static final InventoryProcessedEventBus newInstance()
	{
		final IEventBus eventBus = Services.get(IEventBusFactory.class).getEventBus(EVENTBUS_TOPIC);
		return new InventoryProcessedEventBus(eventBus);
	}



	/** Topic used to send notifications about shipments/receipts that were generated/reversed asynchronously */
	public static final Topic EVENTBUS_TOPIC = Topic.builder()
			.name("de.metas.inventory.Inventory.ProcessedEvents")
			.type(Type.REMOTE)
			.build();

	// services
	private final transient IDocumentBL docActionBL = Services.get(IDocumentBL.class);

	private static final String MSG_Event_InventoryGenerated = "Event_InventoryGenerated";

	private InventoryProcessedEventBus(final IEventBus delegate)
	{
		super(delegate);
	}

	@Override
	public InventoryProcessedEventBus queueEvents()
	{
		super.queueEvents();
		return this;
	}

	@Override
	public InventoryProcessedEventBus queueEventsUntilTrxCommit(final String trxName)
	{
		super.queueEventsUntilTrxCommit(trxName);
		return this;
	}

	@Override
	public InventoryProcessedEventBus queueEventsUntilCurrentTrxCommit()
	{
		super.queueEventsUntilCurrentTrxCommit();
		return this;
	}

	/**
	 * Post events about given inventories that were processed.
	 *
	 * @param inouts
	 * @see #notify(I_M_Inventory)
	 */
	public InventoryProcessedEventBus notify(final Collection<? extends I_M_Inventory> inventories)
	{
		if (inventories == null || inventories.isEmpty())
		{
			return this;
		}

		for (final I_M_Inventory inventory : inventories)
		{
			final Event event = createInventoryGeneratedEvent(inventory);
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
	public final InventoryProcessedEventBus notify(final I_M_Inventory inventory)
	{
		Check.assumeNotNull(inventory, "inventory not null");
		notify(Collections.singleton(inventory));
		return this;
	}

	private final Event createInventoryGeneratedEvent(final I_M_Inventory inventory)
	{
		Check.assumeNotNull(inventory, "inventory not null");

		final String adMessage = getNotificationAD_Message(inventory);
		final int recipientUserId = getNotificationRecipientUserId(inventory);

		final Event event = Event.builder()
				.setDetailADMessage(adMessage, TableRecordReference.of(inventory))
				.addRecipient_User_ID(recipientUserId)
				.setRecord(TableRecordReference.of(inventory))
				.setSuggestedWindowId(WINDOW_INTERNAL_INVENTORY)
				.build();
		return event;
	}

	private final String getNotificationAD_Message(final I_M_Inventory inventory)
	{
			return  MSG_Event_InventoryGenerated;
	}

	private final int getNotificationRecipientUserId(final I_M_Inventory inventory)
	{
		//
		// In case of reversal i think we shall notify the current user too
		if(docActionBL.isDocumentReversedOrVoided(inventory))
		{
			final int currentUserId = Env.getAD_User_ID(Env.getCtx()); // current/triggering user
			if(currentUserId > 0)
			{
				return currentUserId;
			}

			return inventory.getUpdatedBy(); // last updated
		}
		//
		// Fallback: notify only the creator
		else
		{
			return inventory.getCreatedBy();
		}
	}
}

