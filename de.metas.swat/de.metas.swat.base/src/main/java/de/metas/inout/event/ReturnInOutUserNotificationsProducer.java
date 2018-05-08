package de.metas.inout.event;

import java.util.Collection;
import java.util.List;

import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_InOut;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableList;

import de.metas.document.engine.IDocumentBL;
import de.metas.event.Topic;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import lombok.NonNull;

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

public class ReturnInOutUserNotificationsProducer
{

	/**
	 * M_InOut PO
	 */
	private static final int WINDOW_RETURN_TO_VENDOR = 53098; // FIXME: HARDCODED

	private static final int WINDOW_RETURN_FROM_CUSTOMER = 53097; // FIXME: HARDCODED

	public static final ReturnInOutUserNotificationsProducer newInstance()
	{
		return new ReturnInOutUserNotificationsProducer();
	}

	/** Topic used to send notifications about shipments/receipts that were generated/reversed asynchronously */
	public static final Topic EVENTBUS_TOPIC = InOutUserNotificationsProducer.EVENTBUS_TOPIC;

	// services
	private final transient IDocumentBL docActionBL = Services.get(IDocumentBL.class);

	private static final String MSG_Event_RETURN_FROM_CUSTOMER_Generated = "Event_CustomerReturn_Generated";
	private static final String MSG_Event_RETURN_TO_VENDOR_Generated = "Event_ReturnToVendor_Generated";

	private ReturnInOutUserNotificationsProducer()
	{
	}

	/**
	 * Post events about given shipment/receipts that were processed.
	 *
	 * @param inouts
	 * @see #notify(I_M_InOut)
	 */
	public ReturnInOutUserNotificationsProducer notify(final Collection<? extends I_M_InOut> inouts)
	{
		if (inouts == null || inouts.isEmpty())
		{
			return this;
		}

		postNotifications(inouts.stream()
				.map(this::createUserNotification)
				.collect(ImmutableList.toImmutableList()));

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
	public final ReturnInOutUserNotificationsProducer notify(@NonNull final I_M_InOut inout)
	{
		notify(ImmutableList.of(inout));
		return this;
	}

	private final UserNotificationRequest createUserNotification(@NonNull final I_M_InOut inout)
	{
		final I_C_BPartner bpartner = inout.getC_BPartner();
		final String bpValue = bpartner.getValue();
		final String bpName = bpartner.getName();

		final String adMessage = getNotificationAD_Message(inout);
		final int recipientUserId = getNotificationRecipientUserId(inout);

		final TableRecordReference inoutRef = TableRecordReference.of(inout);

		return newUserNotificationRequest()
				.recipientUserId(recipientUserId)
				.contentADMessage(adMessage)
				.contentADMessageParam(inoutRef)
				.contentADMessageParam(bpValue)
				.contentADMessageParam(bpName)
				.targetRecord(inoutRef)
				.targetADWindowId(getWindowId(inout))
				.build();
	}

	private final UserNotificationRequest.UserNotificationRequestBuilder newUserNotificationRequest()
	{
		return UserNotificationRequest.builder()
				.topic(EVENTBUS_TOPIC);
	}

	private int getWindowId(final I_M_InOut inout)
	{
		return inout.isSOTrx() ? WINDOW_RETURN_FROM_CUSTOMER : WINDOW_RETURN_TO_VENDOR;
	}

	private final String getNotificationAD_Message(final I_M_InOut inout)
	{

		return inout.isSOTrx() ? MSG_Event_RETURN_FROM_CUSTOMER_Generated : MSG_Event_RETURN_TO_VENDOR_Generated;

	}

	private final int getNotificationRecipientUserId(final I_M_InOut inout)
	{
		//
		// In case of reversal i think we shall notify the current user too
		if (docActionBL.isDocumentReversedOrVoided(inout))
		{
			final int currentUserId = Env.getAD_User_ID(Env.getCtx()); // current/triggering user
			if (currentUserId > 0)
			{
				return currentUserId;
			}

			return inout.getUpdatedBy(); // last updated
		}
		//
		// Fallback: notify only the creator
		else
		{
			return inout.getCreatedBy();
		}
	}

	private void postNotifications(final List<UserNotificationRequest> notifications)
	{
		Services.get(INotificationBL.class).sendAfterCommit(notifications);
	}
}
