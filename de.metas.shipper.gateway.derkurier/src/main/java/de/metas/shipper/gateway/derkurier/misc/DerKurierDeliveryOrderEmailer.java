package de.metas.shipper.gateway.derkurier.misc;

import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;

import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.IAttachmentDAO;
import de.metas.email.EMail;
import de.metas.email.EMailAttachment;
import de.metas.email.IMailBL;
import de.metas.email.Mailbox;
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
	static final String SYSCONFIG_DerKurier_DeliveryOrder_EmailSubject = "DerKurier_DeliveryOrder_EmailSubject";

	@VisibleForTesting
	static final String SYSCONFIG_DerKurier_DeliveryOrder_EmailMessage = "DerKurier_DeliveryOrder_EmailMessage";

	private final DerKurierShipperConfigRepository derKurierShipperConfigRepository;

	public DerKurierDeliveryOrderEmailer(
			@NonNull final DerKurierShipperConfigRepository derKurierShipperConfigRepository)
	{
		this.derKurierShipperConfigRepository = derKurierShipperConfigRepository;
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
		final String message = retrieveSysConfig(SYSCONFIG_DerKurier_DeliveryOrder_EmailMessage);
		final String subject = retrieveSysConfig(SYSCONFIG_DerKurier_DeliveryOrder_EmailSubject);

		final IMailBL mailBL = Services.get(IMailBL.class);
		final EMail eMail = mailBL.createEMail(Env.getCtx(),
				mailBox,
				mailTo,
				subject,
				message,
				false // html=false
		);

		final String filename = attachmentEntry.getFilename();
		final byte[] data = Services.get(IAttachmentDAO.class).retrieveData(attachmentEntry);
		final EMailAttachment emailAttachment = EMailAttachment.of(filename, data);

		eMail.addAttachment(emailAttachment);
		mailBL.send(eMail);
	}

	private String retrieveSysConfig(@NonNull final String sysConfigName)
	{
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		final int adClientId = Env.getAD_Client_ID(Env.getCtx());
		final int adOrgId = Env.getAD_Org_ID(Env.getCtx());

		final String value = sysConfigBL.getValue(sysConfigName, "", adClientId, adOrgId);

		return Check.assumeNotEmpty(value, "Sysconfig value for key={} is not empty; AD_Client_ID={}; AD_Org_ID={}", sysConfigName, adClientId, adOrgId);
	}

}
