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

import de.metas.attachments.AttachmentEntry;
import de.metas.i18n.IADMessageDAO;
import de.metas.javaclasses.IJavaClassBL;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Table_AttachmentListener;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

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

	public void notifyAttachmentListeners(final AttachmentEntry attachmentEntry)
	{
		attachmentEntry.getLinkedRecords().forEach(linkedRecord -> notifyAttachmentListenersFor(linkedRecord, attachmentEntry));
	}

	private void notifyAttachmentListenersFor(final TableRecordReference tableRecordReference, final AttachmentEntry attachmentEntry)
	{
		tableAttachmentListenerRepository.getById(tableRecordReference.getAdTableId())
				.forEach(listenerSettings ->
				{
					final AttachmentListener attachmentListener = javaClassBL.newInstance(listenerSettings.getListenerJavaClassId());

					attachmentListener.afterPersist(attachmentEntry, tableRecordReference);

					notifyUser(listenerSettings, tableRecordReference);
				});
	}

	/**
	 * Notifies the user about the process finalizing work if {@link AttachmentListenerSettings#isSendNotification()}
	 *
	 * @param attachmentListenerSettings data from {@link I_AD_Table_AttachmentListener}
	 * @param tableRecordReference       reference of the table
	 */
	private void notifyUser(final AttachmentListenerSettings attachmentListenerSettings,
							final TableRecordReference tableRecordReference)
	{
		if (attachmentListenerSettings.isSendNotification())
		{
			final String adMessageContent = adMessageDAO.retrieveValueById( attachmentListenerSettings.getAdMessageId().getRepoId() );

			final UserNotificationRequest userNotificationRequest = UserNotificationRequest.builder()
					.contentADMessage(adMessageContent)
					.recipientUserId(Env.getLoggedUserId())
					.targetAction(UserNotificationRequest.TargetRecordAction.of(tableRecordReference))
					.build();
			notificationBL.send(userNotificationRequest);
		}
	}
}
