package de.metas.document.archive.mailrecipient.impl;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.IBPartnerBL.RetrieveContactRequest;
import de.metas.bpartner.service.IBPartnerBL.RetrieveContactRequest.ContactType;
import de.metas.document.archive.mailrecipient.DocOutBoundRecipient;
import de.metas.document.archive.mailrecipient.DocOutBoundRecipientId;
import de.metas.document.archive.mailrecipient.DocOutBoundRecipientRepository;
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

	private final DocOutBoundRecipientRepository recipientRepository;
	private final OrderEmailPropagationSysConfigRepository orderEmailPropagationSysConfigRepository;
	private final IBPartnerBL bpartnerBL;
	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IBPartnerBL partnerBL = Services.get(IBPartnerBL.class);

	public InvoiceDocOutboundLogMailRecipientProvider(
			@NonNull final DocOutBoundRecipientRepository recipientRepository,
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
	public Optional<DocOutBoundRecipient> provideMailRecipient(@NonNull final DocOutboundLogMailRecipientRequest request)
	{
		final I_C_Invoice invoiceRecord = request.getRecordRef().getModel(I_C_Invoice.class);

		final boolean propagateToDocOutboundLog = orderEmailPropagationSysConfigRepository.isPropagateToDocOutboundLog(
				ClientAndOrgId.ofClientAndOrg(request.getClientId(), request.getOrgId()));

		final String invoiceEmail = propagateToDocOutboundLog? invoiceRecord.getEMail() : null;

		final String locationEmail = invoiceBL.getLocationEmail(InvoiceId.ofRepoId(invoiceRecord.getC_Invoice_ID()));

		if (invoiceRecord.getAD_User_ID() > 0)
		{
			final DocOutBoundRecipient invoiceUser = recipientRepository.getById(DocOutBoundRecipientId.ofRepoId(invoiceRecord.getAD_User_ID()));

			if (Check.isNotBlank(invoiceEmail))
			{
				return Optional.of(invoiceUser.withEmailAddress(invoiceEmail));
			}

			if (Check.isNotBlank(invoiceUser.getEmailAddress()))
			{
				return Optional.of(invoiceUser);
			}

			if (Check.isNotBlank(locationEmail))
			{
				return Optional.of(invoiceUser.withEmailAddress(locationEmail));
			}

		}

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(invoiceRecord.getC_BPartner_ID());

		final User billContact = bpartnerBL.retrieveContactOrNull(
				RetrieveContactRequest
						.builder()
						.bpartnerId(bpartnerId)
						.bPartnerLocationId(BPartnerLocationId.ofRepoId(bpartnerId, invoiceRecord.getC_BPartner_Location_ID()))
						.contactType(ContactType.BILL_TO_DEFAULT)
						.onlyIfInvoiceEmailEnabled(true)
						.build());

		if (billContact != null && billContact.getId()!= null)
		{
			final DocOutBoundRecipientId recipientId = DocOutBoundRecipientId.ofRepoId(billContact.getId().getRepoId());
			final DocOutBoundRecipient docOutBoundRecipient = recipientRepository.getById(recipientId);

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


	@Override
	public Optional<DocOutBoundRecipient> provideMailCCRecipient(@NonNull final DocOutboundLogMailRecipientRequest request)
	{
		if (request.getRecordRef() == null)
		{
			return Optional.empty();
		}

		final I_C_Invoice invoiceRecord = invoiceBL.getById(InvoiceId.ofRepoId(request.getRecordRef().getRecord_ID()));
		if (!partnerBL.isInvoiceEmailCcToMember(BPartnerId.ofRepoId(invoiceRecord.getC_BPartner_ID())))
		{
			return Optional.empty();
		}

		final OrderId orderId = OrderId.ofRepoIdOrNull(invoiceRecord.getC_Order_ID());
		if (orderId == null)
		{
			return Optional.empty();
		}

		final I_C_Order orderRecord = orderBL.getById(orderId);

		final String locationEmail = orderBL.getLocationEmail(OrderId.ofRepoId(orderRecord.getC_Order_ID()));

		if (orderRecord.getAD_User_ID() > 0)
		{
			final DocOutBoundRecipient orderUser = recipientRepository.getById(DocOutBoundRecipientId.ofRepoId(orderRecord.getAD_User_ID()));

			if (Check.isNotBlank(orderUser.getEmailAddress()))
			{
				return Optional.of(orderUser);
			}

			if (Check.isNotBlank(locationEmail))
			{
				return Optional.of(orderUser.withEmailAddress(locationEmail));
			}

		}

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(orderRecord.getC_BPartner_ID());

		final User billContact = bpartnerBL.retrieveContactOrNull(
				RetrieveContactRequest
						.builder()
						.bpartnerId(bpartnerId)
						.bPartnerLocationId(BPartnerLocationId.ofRepoId(bpartnerId, orderRecord.getC_BPartner_Location_ID()))
						.contactType(ContactType.BILL_TO_DEFAULT)
						.onlyIfInvoiceEmailEnabled(true)
						.build());

		if (billContact != null && billContact.getId() != null)
		{
			final DocOutBoundRecipientId recipientId = DocOutBoundRecipientId.ofRepoId(UserId.toRepoId(billContact.getId()));
			final DocOutBoundRecipient docOutBoundRecipient = recipientRepository.getById(recipientId);


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
