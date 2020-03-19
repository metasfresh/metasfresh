/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

package de.metas.attachments.listener;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.attachments.AttachmentEntry;
import de.metas.i18n.IADMessageDAO;
import de.metas.javaclasses.IJavaClassBL;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Table_AttachmentListener;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class TableAttachmentListenerService
{
	private final INotificationBL notificationBL = Services.get(INotificationBL.class);
	private final IJavaClassBL javaClassBL = Services.get(IJavaClassBL.class);
	private final IADMessageDAO adMessageDAO = Services.get(IADMessageDAO.class);
	private final TableAttachmentListenerRepository tableAttachmentListenerRepository;

	public TableAttachmentListenerService(final TableAttachmentListenerRepository tableAttachmentListenerRepository)
	{
		this.tableAttachmentListenerRepository = tableAttachmentListenerRepository;
	}

	public ImmutableList<AttachmentListenerActionResult> notifyAttachmentListeners(@NonNull final AttachmentEntry attachmentEntry)
	{
		return attachmentEntry.getLinkedRecords()
				.stream()
		        .map(linkedRecord -> notifyAttachmentListenersFor(linkedRecord, attachmentEntry))
				.flatMap(Collection::stream)
				.collect(ImmutableList.toImmutableList());
	}

	private ImmutableList<AttachmentListenerActionResult> notifyAttachmentListenersFor(final TableRecordReference tableRecordReference, final AttachmentEntry attachmentEntry)
	{
		return tableAttachmentListenerRepository.getById(tableRecordReference.getAdTableId())
				.stream()
				.map(listenerSettings ->
				{
					final AttachmentListener attachmentListener = javaClassBL.newInstance(listenerSettings.getListenerJavaClassId());

					final AttachmentListenerConstants.ListenerWorkStatus status = attachmentListener.afterPersist(attachmentEntry, tableRecordReference);

					notifyUser(listenerSettings, tableRecordReference, status);

					return new AttachmentListenerActionResult(attachmentListener, status);
				})
				.collect(ImmutableList.toImmutableList());
	}

	/**
	 * Notifies the user about the process finalizing work if {@link AttachmentListenerSettings#isSendNotification()}
	 *
	 * @param attachmentListenerSettings data from {@link I_AD_Table_AttachmentListener}
	 * @param tableRecordReference       reference of the table
	 */
	@VisibleForTesting
	void notifyUser(final AttachmentListenerSettings attachmentListenerSettings,
					final TableRecordReference tableRecordReference,
					final AttachmentListenerConstants.ListenerWorkStatus listenerWorkStatus)
	{
		if (attachmentListenerSettings.isSendNotification())
		{
			final String adMessageContent = adMessageDAO.retrieveValueById( attachmentListenerSettings.getAdMessageId().getRepoId() );

			final UserNotificationRequest userNotificationRequest = UserNotificationRequest.builder()
					.contentADMessage(adMessageContent)
					.contentADMessageParam(listenerWorkStatus.getValue())
					.recipientUserId(Env.getLoggedUserId())
					.targetAction(UserNotificationRequest.TargetRecordAction.of(tableRecordReference))
					.build();
			notificationBL.send(userNotificationRequest);
		}
	}
}
