package de.metas.shipper.gateway.derkurier.misc;

import org.adempiere.util.Services;
import org.compiere.model.I_AD_MailBox;
import org.compiere.util.Env;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.IAttachmentDAO;
import de.metas.email.EMail;
import de.metas.email.EMailAttachment;
import de.metas.email.IMailBL;
import de.metas.email.Mailbox;
import de.metas.shipper.gateway.derkurier.model.I_DerKurier_Shipper_Config;

/*
 * #%L
 * de.metas.shipper.gateway.spi
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class DerKurierDeliveryOrderEmailer
{
	private static final String SYSCONFIG_DerKurier_DeliveryOrder_EmailSubject = "DerKurier_DeliveryOrder_EmailSubject";
	private static final String SYSCONFIG_DerKurier_DeliveryOrder_EmailMessage = "DerKurier_DeliveryOrder_EmailMessage";
	
	
	public void emailAttachment(final I_DerKurier_Shipper_Config shipperConfig, final AttachmentEntry attachmentEntry)
	{
		final int mailBoxId = shipperConfig.getAD_MailBox_ID();

		if (mailBoxId <= 0)
		{
			// nothing to do
			return;
		}
		final I_AD_MailBox shipperConfigMailBox = loadOutOfTrx(mailBoxId, I_AD_MailBox.class);

		final Mailbox mailbox = Mailbox.builder()
				.smtpHost(shipperConfigMailBox.getSMTPHost())
				.smtpPort(shipperConfigMailBox.getSMTPPort())
				.startTLS(shipperConfigMailBox.isStartTLS())
				.email(shipperConfigMailBox.getEMail())
				.username(shipperConfigMailBox.getUserName())
				.password(shipperConfigMailBox.getPassword())
				.smtpAuthorization(shipperConfigMailBox.isSmtpAuthorization())
				.adUserId(-1)
				.build();

		final String emailAddress = "derKurierEmailAddress@mail.com"; // TODO Please, complete from a field
		final String subject = SYSCONFIG_DerKurier_DeliveryOrder_EmailSubject;
		final String message = SYSCONFIG_DerKurier_DeliveryOrder_EmailMessage;

		final IMailBL mailBL = Services.get(IMailBL.class);

		final EMail eMail = mailBL.createEMail(Env.getCtx(),
				mailbox,
				emailAddress,
				subject,
				message,
				false);

		final EMailAttachment emailAttachment = EMailAttachment.of(attachmentEntry.getFilename(), Services.get(IAttachmentDAO.class).retrieveData(attachmentEntry));
		eMail.addAttachment(emailAttachment);
		mailBL.send(eMail);

	}

}
