package de.metas.document.archive.mailrecipient.impl;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.IBPartnerBL.RetrieveContactRequest;
import de.metas.bpartner.service.IBPartnerBL.RetrieveContactRequest.ContactType;
import de.metas.document.archive.mailrecipient.DocOutBoundRecipient;
import de.metas.document.archive.mailrecipient.DocOutBoundRecipientCC;
import de.metas.document.archive.mailrecipient.DocOutBoundRecipientId;
import de.metas.document.archive.mailrecipient.DocOutBoundRecipientService;
import de.metas.document.archive.mailrecipient.DocOutBoundRecipients;
import de.metas.document.archive.mailrecipient.DocOutboundLogMailRecipientProvider;
import de.metas.document.archive.mailrecipient.DocOutboundLogMailRecipientRequest;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.order.impl.OrderEmailPropagationSysConfigRepository;
import de.metas.organization.ClientAndOrgId;
import de.metas.user.User;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.springframework.stereotype.Component;

import java.util.Optional;

/*
 * #%L
 * de.metas.document.archive.base
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

@Component
public class InvoiceDocOutboundLogMailRecipientProvider
		implements DocOutboundLogMailRecipientProvider
{

	private final DocOutBoundRecipientService recipientRepository;
	private final OrderEmailPropagationSysConfigRepository orderEmailPropagationSysConfigRepository;
	private final IBPartnerBL bpartnerBL;
	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IBPartnerBL partnerBL = Services.get(IBPartnerBL.class);

	public InvoiceDocOutboundLogMailRecipientProvider(
			@NonNull final DocOutBoundRecipientService recipientRepository,
			@NonNull final OrderEmailPropagationSysConfigRepository orderEmailPropagationSysConfigRepository,
			@NonNull final IBPartnerBL bpartnerBL)
	{
		this.recipientRepository = recipientRepository;
		this.orderEmailPropagationSysConfigRepository = orderEmailPropagationSysConfigRepository;
		this.bpartnerBL = bpartnerBL;
	}

	@Override
	public boolean isDefault()
	{
		return false;
	}

	@Override
	public String getTableName()
	{
		return I_C_Invoice.Table_Name;
	}

	@Override
	public Optional<DocOutBoundRecipients> provideMailRecipient(@NonNull final DocOutboundLogMailRecipientRequest request)
	{
		if (request.getRecordRef() == null)
		{
			return Optional.empty();
		}

		final InvoiceId invoiceId = request.getRecordRef().getIdAssumingTableName(I_C_Invoice.Table_Name, InvoiceId::ofRepoId);
		final I_C_Invoice invoiceRecord = invoiceBL.getById(invoiceId);

		final DocOutBoundRecipient to = getMailTo(invoiceRecord).orElse(null);
		final DocOutBoundRecipientCC cc = getMailCC(invoiceRecord);
		if (to == null && cc == null)
		{
			return Optional.empty();
		}

		return Optional.of(
				DocOutBoundRecipients.builder()
						.to(to)
						.cc(cc)
						.build()
		);
	}

	public Optional<DocOutBoundRecipient> getMailTo(final I_C_Invoice invoiceRecord)
	{
		final ClientAndOrgId clientAndOrgId = ClientAndOrgId.ofClientAndOrg(invoiceRecord.getAD_Client_ID(), invoiceRecord.getAD_Org_ID());
		final boolean propagateToDocOutboundLog = orderEmailPropagationSysConfigRepository.isPropagateToDocOutboundLog(clientAndOrgId);
		final String invoiceEmail = propagateToDocOutboundLog ? invoiceRecord.getEMail() : null;

		final String locationEmail = invoiceBL.getLocationEmail(InvoiceId.ofRepoId(invoiceRecord.getC_Invoice_ID()));

		final UserId billContactId = UserId.ofRepoIdOrNull(invoiceRecord.getAD_User_ID());
		if (billContactId != null && billContactId.isRegularUser())
		{
			final DocOutBoundRecipient billRecipient = recipientRepository.getById(DocOutBoundRecipientId.ofUserId(billContactId));
			if (Check.isNotBlank(invoiceEmail))
			{
				return Optional.of(billRecipient.withEmailAddress(invoiceEmail));
			}
			if (billRecipient.isEmailAddressSet())
			{
				return Optional.of(billRecipient);
			}
			if (Check.isNotBlank(locationEmail))
			{
				return Optional.of(billRecipient.withEmailAddress(locationEmail));
			}
		}

		final BPartnerId billPartnerId = BPartnerId.ofRepoId(invoiceRecord.getC_BPartner_ID());
		final User billContact = bpartnerBL.retrieveContactOrNull(
				RetrieveContactRequest
						.builder()
						.bpartnerId(billPartnerId)
						.bPartnerLocationId(BPartnerLocationId.ofRepoId(billPartnerId, invoiceRecord.getC_BPartner_Location_ID()))
						.contactType(ContactType.BILL_TO_DEFAULT)
						.onlyIfInvoiceEmailEnabled(true)
						.build());
		if (billContact != null)
		{
			final DocOutBoundRecipient docOutBoundRecipient = recipientRepository.ofUser(billContact);

			if (Check.isNotBlank(invoiceEmail))
			{
				return Optional.of(docOutBoundRecipient.withEmailAddress(invoiceEmail));
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

	private DocOutBoundRecipientCC getMailCC(final I_C_Invoice invoiceRecord)
	{
		final BPartnerId billPartnerId = BPartnerId.ofRepoId(invoiceRecord.getC_BPartner_ID());
		if (!partnerBL.isInvoiceEmailCcToMember(billPartnerId))
		{
			return null;
		}

		final OrderId orderId = OrderId.ofRepoIdOrNull(invoiceRecord.getC_Order_ID());
		if (orderId == null)
		{
			return null;
		}

		final I_C_Order orderRecord = orderBL.getById(orderId);
		final UserId shipToContactId = UserId.ofRepoIdOrNull(orderRecord.getAD_User_ID());
		if (shipToContactId != null && shipToContactId.isRegularUser())
		{
			final DocOutBoundRecipient shipToRecipient = recipientRepository.getByUserId(shipToContactId)
					.withEmailAddressIfEmpty(() -> orderBL.getLocationEmail(orderRecord));
			return shipToRecipient.isEmailAddressSet() ? DocOutBoundRecipientCC.of(shipToRecipient) : null;
		}
		else
		{
			final BPartnerId shipBPartnerId = BPartnerId.ofRepoId(orderRecord.getC_BPartner_ID());
			final User defaultShipToContact = bpartnerBL.retrieveContactOrNull(
					RetrieveContactRequest
							.builder()
							.bpartnerId(shipBPartnerId)
							.bPartnerLocationId(BPartnerLocationId.ofRepoId(shipBPartnerId, invoiceRecord.getC_BPartner_Location_ID()))
							.contactType(ContactType.SHIP_TO_DEFAULT)
							.build());
			if (defaultShipToContact != null)
			{
				final DocOutBoundRecipient defaultShipToRecipient = recipientRepository.ofUser(defaultShipToContact)
						.withEmailAddressIfEmpty(() -> orderBL.getLocationEmail(orderRecord));

				return defaultShipToRecipient.isEmailAddressSet() ? DocOutBoundRecipientCC.of(defaultShipToRecipient) : null;
			}
			else
			{
				return null;
			}
		}
	}
}
