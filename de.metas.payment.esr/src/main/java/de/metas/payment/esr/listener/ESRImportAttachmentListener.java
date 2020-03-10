/*
 * #%L
 * de.metas.payment.esr
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

package de.metas.payment.esr.listener;

import ch.qos.logback.classic.Level;
import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.listener.AttachmentListener;
import de.metas.attachments.listener.AttachmentListenerSettings;
import de.metas.i18n.IADMessageDAO;
import de.metas.logging.LogManager;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.payment.esr.api.IESRImportBL;
import de.metas.payment.esr.api.RunESRImportCriteria;
import de.metas.payment.esr.model.I_ESR_Import;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Table_AttachmentListener;
import org.compiere.util.Env;
import org.slf4j.Logger;

import static de.metas.payment.esr.ESRConstants.ESR_ASYNC_BATCH_DESC;
import static de.metas.payment.esr.ESRConstants.ESR_ASYNC_BATCH_NAME;

public class ESRImportAttachmentListener implements AttachmentListener
{
	private static final Logger logger = LogManager.getLogger(ESRImportAttachmentListener.class);

	@Override
	public void afterPersist(final AttachmentListenerSettings attachmentListenerSettings,
							final AttachmentEntry attachmentEntry,
							final TableRecordReference tableRecordReference)
	{
		final I_ESR_Import esrImport = InterfaceWrapperHelper.load(tableRecordReference.getRecord_ID(), I_ESR_Import.class);

		final RunESRImportCriteria runESRImportCriteria = new RunESRImportCriteria(esrImport,
				attachmentEntry.getId(),
				ESR_ASYNC_BATCH_NAME,
				ESR_ASYNC_BATCH_DESC,
				Loggables.withLogger(logger, Level.DEBUG),
				null);

		Services.get(IESRImportBL.class).runESRImportFor(runESRImportCriteria);

		notifyUser(attachmentListenerSettings, tableRecordReference);
	}

	/**
	 *  Notifies the user about the process finalizing work if {@link AttachmentListenerSettings#isSendNotification()}
	 *
	 * @param attachmentListenerSettings	data from {@link I_AD_Table_AttachmentListener}
	 * @param tableRecordReference			reference of the table
	 */
	private void notifyUser(final AttachmentListenerSettings attachmentListenerSettings,
							final TableRecordReference tableRecordReference)
	{
		if ( attachmentListenerSettings.isSendNotification() )
		{

			 //see org.adempiere.model.validator.AD_Table_AttachmentListener#beforeSave(I_AD_Table_AttachmentListener)
			Check.assumeNotNull(attachmentListenerSettings.getAdMessageId(),
					"An AD_Message_ID must be present when notifications are enabled.");

			final String adMessageContent = Services.get(IADMessageDAO.class)
					.retrieveValueById( attachmentListenerSettings.getAdMessageId().getRepoId() );

			final UserNotificationRequest userNotificationRequest = UserNotificationRequest.builder()
					.contentADMessage(adMessageContent)
					.recipientUserId( Env.getLoggedUserId() )
					.targetAction( UserNotificationRequest.TargetRecordAction.of(tableRecordReference) )
					.build();
			Services.get(INotificationBL.class).send(userNotificationRequest);
		}
	}
}
