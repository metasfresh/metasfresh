package de.metas.inout.event;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import com.google.common.collect.ImmutableList;
import de.metas.document.engine.DocStatus;
import de.metas.event.Topic;
import de.metas.event.Type;
import de.metas.i18n.AdMessageKey;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.notification.UserNotificationRequest.TargetRecordAction;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_InOut;
import org.compiere.util.Env;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Helper class used for sending user notifications about generated or reversed shipments/receipts.
 *
 * @author tsa
 */
public final class InOutUserNotificationsProducer
{
	public static InOutUserNotificationsProducer newInstance()
	{
		return new InOutUserNotificationsProducer();
	}

	/**
	 * Topic used to send notifications about shipments/receipts that were generated/reversed asynchronously
	 */
	public static final Topic EVENTBUS_TOPIC = Topic.builder()
			.name("de.metas.inout.UserNotifications")
			.type(Type.REMOTE)
			.build();

	// services
	private final transient INotificationBL notificationBL = Services.get(INotificationBL.class);

	private static final AdMessageKey MSG_Event_ShipmentGenerated = AdMessageKey.of("Event_ShipmentGenerated");
	private static final AdMessageKey MSG_Event_ShipmentReversed = AdMessageKey.of("Event_ShipmentReversed");
	private static final AdMessageKey MSG_Event_ShipmentError = AdMessageKey.of("Event_ShipmentError");
	//
	private static final AdMessageKey MSG_Event_ReceiptGenerated = AdMessageKey.of("Event_ReceiptGenerated");
	private static final AdMessageKey MSG_Event_ReceiptReversed = AdMessageKey.of("Event_ReceiptReversed");

	private InOutUserNotificationsProducer()
	{
	}

	/**
	 * Post events about given shipment/receipts that were processed.
	 *
	 * @see #notifyInOutProcessed(I_M_InOut)
	 */
	public InOutUserNotificationsProducer notifyInOutsProcessed(final Collection<? extends I_M_InOut> inouts)
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
	 */
	public final InOutUserNotificationsProducer notifyInOutProcessed(final I_M_InOut inout)
	{
		Check.assumeNotNull(inout, "inout not null");
		notifyInOutsProcessed(Collections.singleton(inout));
		return this;
	}

	private UserNotificationRequest createUserNotification(@NonNull final I_M_InOut inout)
	{
		final I_C_BPartner bpartner = InterfaceWrapperHelper.load(inout.getC_BPartner_ID(), I_C_BPartner.class);
		final String bpValue = bpartner.getValue();
		final String bpName = bpartner.getName();

		final AdMessageKey adMessage = getNotificationAD_Message(inout);
		final UserId recipientUserId = getNotificationRecipientUserId(inout);

		final TableRecordReference inoutRef = TableRecordReference.of(inout);

		return newUserNotificationRequest()
				.recipientUserId(recipientUserId)
				.contentADMessage(adMessage)
				.contentADMessageParam(inoutRef)
				.contentADMessageParam(bpValue)
				.contentADMessageParam(bpName)
				.targetAction(TargetRecordAction.of(inoutRef))
				.build();
	}

	private UserNotificationRequest.UserNotificationRequestBuilder newUserNotificationRequest()
	{
		return UserNotificationRequest.builder()
				.topic(EVENTBUS_TOPIC);
	}

	private AdMessageKey getNotificationAD_Message(final I_M_InOut inout)
	{
		final DocStatus docStatus = DocStatus.ofNullableCodeOrUnknown(inout.getDocStatus());
		if (docStatus.isReversedOrVoided())
		{
			return inout.isSOTrx() ? MSG_Event_ShipmentReversed : MSG_Event_ReceiptReversed;
		}
		else
		{
			return inout.isSOTrx() ? MSG_Event_ShipmentGenerated : MSG_Event_ReceiptGenerated;
		}
	}

	private UserId getNotificationRecipientUserId(final I_M_InOut inout)
	{
		//
		// In case of reversal i think we shall notify the current user too
		final DocStatus docStatus = DocStatus.ofNullableCodeOrUnknown(inout.getDocStatus());
		if (docStatus.isReversedOrVoided())
		{
			final int currentUserId = Env.getAD_User_ID(Env.getCtx()); // current/triggering user
			if (currentUserId > 0)
			{
				return UserId.ofRepoId(currentUserId);
			}

			return UserId.ofRepoId(inout.getUpdatedBy()); // last updated
		}
		//
		// Fallback: notify only the creator
		else
		{
			return UserId.ofRepoId(inout.getCreatedBy());
		}
	}

	public InOutUserNotificationsProducer notifyShipmentError(final String sourceInfo, final String errorMessage)
	{
		postNotification(newUserNotificationRequest()
				.recipientUserId(Env.getLoggedUserId())
				.contentADMessage(MSG_Event_ShipmentError)
				.contentADMessageParam(sourceInfo)
				.contentADMessageParam(errorMessage)
				.build());

		return this;
	}

	private void postNotification(final UserNotificationRequest notification)
	{
		notificationBL.sendAfterCommit(notification);
	}

	private void postNotifications(final List<UserNotificationRequest> notifications)
	{
		Services.get(INotificationBL.class).sendAfterCommit(notifications);
	}

}
