/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.contracts.modular.interest;

import de.metas.contracts.model.I_ModCntr_Interest_Run;
import de.metas.contracts.modular.interest.run.InterestRunId;
import de.metas.contracts.modular.invgroup.InvoicingGroupId;
import de.metas.contracts.modular.invgroup.interceptor.ModCntrInvoicingGroupRepository;
import de.metas.event.Topic;
import de.metas.event.Type;
import de.metas.i18n.AdMessageKey;
import de.metas.logging.LogManager;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class InterestComputationNotificationsProducer
{
	public static final Topic EVENTBUS_TOPIC = Topic.builder().name("de.metas.modcntr.interestRun.UserNotifications").type(Type.DISTRIBUTED).build();
	public static final AdMessageKey MSG_Event_InvoiceGenerated = AdMessageKey.of("Event_InterestRunGenerated");
	private static final Logger logger = LogManager.getLogger(InterestComputationNotificationsProducer.class);
	private final INotificationBL notificationBL = Services.get(INotificationBL.class);

	@NonNull private final ModCntrInvoicingGroupRepository modCntrInvoicingGroupRepository;

	/**
	 * Post events about given interest run that was created.
	 */
	public void notifyGenerated(@NonNull final InterestRunId runId,
			@NonNull final InvoicingGroupId invoicingGroupId,
			@NonNull final UserId recipientUserId)
	{
		try
		{
			postNotification(createInterestRunGeneratedEvent(runId, invoicingGroupId, recipientUserId));
		}
		catch (final Exception ex)
		{
			logger.warn("Failed creating event for interest run {}. Ignored.", runId, ex);
		}
	}

	private UserNotificationRequest createInterestRunGeneratedEvent(@NonNull final InterestRunId runId,
			@NonNull final InvoicingGroupId invoicingGroupId,
			@NonNull final UserId recipientUserId)
	{

		final TableRecordReference invoiceRef = TableRecordReference.of(I_ModCntr_Interest_Run.Table_Name, runId);

		final String invoicingGroupName = modCntrInvoicingGroupRepository.getById(invoicingGroupId).name();
		return newUserNotificationRequest().recipientUserId(recipientUserId)
				.contentADMessage(MSG_Event_InvoiceGenerated)
				.contentADMessageParam(invoicingGroupName)
				.targetAction(UserNotificationRequest.TargetRecordAction.of(invoiceRef))
				.build();
	}

	private UserNotificationRequest.UserNotificationRequestBuilder newUserNotificationRequest()
	{
		return UserNotificationRequest.builder().topic(EVENTBUS_TOPIC);
	}

	private void postNotification(final UserNotificationRequest notification)
	{
		notificationBL.sendAfterCommit(notification);
	}
}
