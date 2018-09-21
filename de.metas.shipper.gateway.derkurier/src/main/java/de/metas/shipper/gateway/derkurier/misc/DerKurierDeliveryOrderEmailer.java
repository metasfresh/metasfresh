package de.metas.shipper.gateway.derkurier.misc;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;

import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.IAttachmentBL;
import de.metas.attachments.IAttachmentDAO;
import de.metas.email.EMail;
import de.metas.email.IMailBL;
import de.metas.email.Mailbox;
import de.metas.i18n.IMsgBL;
import de.metas.shipper.gateway.derkurier.DerKurierConstants;
import de.metas.shipping.api.ShipperTransportationId;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

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

@Service
public class DerKurierDeliveryOrderEmailer
{
	@VisibleForTesting
	static final String SYSCONFIG_DerKurier_DeliveryOrder_EmailSubject = "de.metas.shipper.gateway.derkurier.DerKurier_DeliveryOrder_EmailSubject";

	@VisibleForTesting
	static final String SYSCONFIG_DerKurier_DeliveryOrder_EmailMessage = "de.metas.shipper.gateway.derkurier.DerKurier_DeliveryOrder_EmailMessage_1P";

	private final DerKurierShipperConfigRepository derKurierShipperConfigRepository;

	public DerKurierDeliveryOrderEmailer(
			@NonNull final DerKurierShipperConfigRepository derKurierShipperConfigRepository)
	{
		this.derKurierShipperConfigRepository = derKurierShipperConfigRepository;
	}

	public void sendShipperTransportationAsEmail(@NonNull final ShipperTransportationId shipperTransportationId)
	{
		final I_M_ShipperTransportation shipperTransportationRecord = loadOutOfTrx(shipperTransportationId, I_M_ShipperTransportation.class);

		final int shipperId = shipperTransportationRecord.getM_Shipper_ID();
		final DerKurierShipperConfig shipperConfig = derKurierShipperConfigRepository
				.retrieveConfigForShipperId(shipperId);

		final String emailAddress = shipperConfig.getDeliveryOrderRecipientEmailOrNull();
		if (emailAddress == null)
		{
			return;
		}

		final IAttachmentBL attachmentBL = Services.get(IAttachmentBL.class);
		final AttachmentEntry attachmentEntry = attachmentBL.getEntryByFilenameOrNull(shipperTransportationRecord, DerKurierDeliveryOrderService.SHIPPER_TRANSPORTATION_ATTACHMENT_FILENAME);

		sendAttachmentAsEmail(shipperId, attachmentEntry);
	}

	public void sendAttachmentAsEmail(final int shipperId, @NonNull final AttachmentEntry attachmentEntry)
	{
		Check.assumeGreaterThanZero(shipperId, "shipperId");

		final DerKurierShipperConfig shipperConfig = derKurierShipperConfigRepository
				.retrieveConfigForShipperId(shipperId);

		final String emailAddress = shipperConfig.getDeliveryOrderRecipientEmailOrNull();
		if (emailAddress == null)
		{
			return;
		}
		final Mailbox deliveryOrderMailBox = shipperConfig.getDeliveryOrderMailBoxOrNull();

		sendAttachmentAsEmail(deliveryOrderMailBox, emailAddress, attachmentEntry);
	}

	@VisibleForTesting
	void sendAttachmentAsEmail(
			@NonNull final Mailbox mailBox,
			@NonNull final String mailTo,
			@NonNull final AttachmentEntry attachmentEntry)
	{
		final IMsgBL msgBL = Services.get(IMsgBL.class);
		final IMailBL mailBL = Services.get(IMailBL.class);

		final byte[] data = Services.get(IAttachmentDAO.class).retrieveData(attachmentEntry);
		final String csvDataString = new String(data, DerKurierConstants.CSV_DATA_CHARSET);

		final String subject = msgBL.getMsg(Env.getCtx(), SYSCONFIG_DerKurier_DeliveryOrder_EmailSubject);
		final String message = msgBL.getMsg(Env.getCtx(), SYSCONFIG_DerKurier_DeliveryOrder_EmailMessage, new Object[] { csvDataString });

		final EMail eMail = mailBL.createEMail(Env.getCtx(),
				mailBox,
				mailTo,
				subject,
				message,
				false // html=false
		);

		mailBL.send(eMail);

		// we don't have an AD_Archive..
		// final I_AD_User user = loadOutOfTrx(Env.getAD_User_ID(), I_AD_User.class);
		// final IArchiveEventManager archiveEventManager = Services.get(IArchiveEventManager.class);
		// archiveEventManager.fireEmailSent(
		// null,
		// X_C_Doc_Outbound_Log_Line.ACTION_EMail,
		// user,
		// null/* from */,
		// mailTo,
		// null /* cc */,
		// null /* bcc */,
		// IArchiveEventManager.STATUS_MESSAGE_SENT);
	}
}
