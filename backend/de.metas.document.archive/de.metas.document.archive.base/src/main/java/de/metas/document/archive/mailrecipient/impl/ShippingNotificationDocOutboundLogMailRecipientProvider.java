/*
 * #%L
 * de.metas.document.archive.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.document.archive.mailrecipient.impl;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.document.archive.mailrecipient.DocOutBoundRecipient;
import de.metas.document.archive.mailrecipient.DocOutBoundRecipientId;
import de.metas.document.archive.mailrecipient.DocOutBoundRecipientRepository;
import de.metas.document.archive.mailrecipient.DocOutboundLogMailRecipientProvider;
import de.metas.document.archive.mailrecipient.DocOutboundLogMailRecipientRequest;
import de.metas.invoice.InvoiceId;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.order.impl.OrderEmailPropagationSysConfigRepository;
import de.metas.organization.ClientAndOrgId;
import de.metas.shippingnotification.ShippingNotificationId;
import de.metas.shippingnotification.ShippingNotificationService;
import de.metas.shippingnotification.model.I_M_Shipping_Notification;
import de.metas.user.User;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_C_Order;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ShippingNotificationDocOutboundLogMailRecipientProvider implements DocOutboundLogMailRecipientProvider
{
	private final DocOutBoundRecipientRepository recipientRepository;
	private final ShippingNotificationService shippingNotificationService;
	private final IBPartnerBL bpartnerBL;


	public ShippingNotificationDocOutboundLogMailRecipientProvider(
			@NonNull final DocOutBoundRecipientRepository recipientRepository,
			@NonNull final IBPartnerBL bpartnerBL,
			@NonNull final ShippingNotificationService shippingNotificationService)
	{
		this.recipientRepository = recipientRepository;
		this.bpartnerBL = bpartnerBL;
		this.shippingNotificationService = shippingNotificationService;
	}

	@Override
	public boolean isDefault()
	{
		return false;
	}

	@Override
	public String getTableName()
	{
		return I_M_Shipping_Notification.Table_Name;
	}

	@Override
	public Optional<DocOutBoundRecipient> provideMailRecipient(
			@NonNull final DocOutboundLogMailRecipientRequest request)
	{
		final I_M_Shipping_Notification shippingNotificationRecord = request.getRecordRef()
				.getModel(I_M_Shipping_Notification.class);

		final String locationEmail = shippingNotificationService.getLocationEmail(ShippingNotificationId.ofRepoId(shippingNotificationRecord.getM_Shipping_Notification_ID()));

		final BPartnerId bpartnerId = request.getBPartnerId();

		final User billContact = bpartnerBL.retrieveContactOrNull(
				IBPartnerBL.RetrieveContactRequest
						.builder()
						.bpartnerId(bpartnerId)
						.bPartnerLocationId(BPartnerLocationId.ofRepoId(bpartnerId, shippingNotificationRecord.getC_BPartner_Location_ID()))
						.contactType(IBPartnerBL.RetrieveContactRequest.ContactType.BILL_TO_DEFAULT)
						.build());

		if (billContact != null)
		{
			final DocOutBoundRecipientId recipientId = DocOutBoundRecipientId.ofRepoId(billContact.getId().getRepoId());
			final DocOutBoundRecipient docOutBoundRecipient = recipientRepository.getById(recipientId);
			final String contactMail = billContact.getEmailAddress();

			if (Check.isNotBlank(contactMail))
			{
				return Optional.of(docOutBoundRecipient.withEmailAddress(contactMail));
			}

			if (Check.isNotBlank(locationEmail))
			{
				return Optional.of(docOutBoundRecipient.withEmailAddress(locationEmail));
			}

			if (Check.isNotBlank(docOutBoundRecipient.getEmailAddress()))
			{
				return Optional.of(docOutBoundRecipient);
			}
		}

		return Optional.empty();
	}
}