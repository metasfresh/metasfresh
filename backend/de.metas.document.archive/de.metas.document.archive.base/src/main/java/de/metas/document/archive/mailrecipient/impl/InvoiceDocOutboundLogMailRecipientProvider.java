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
import de.metas.user.User;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_C_Invoice;
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
	private final IBPartnerBL bpartnerBL;
	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);

	public InvoiceDocOutboundLogMailRecipientProvider(
			@NonNull final DocOutBoundRecipientRepository recipientRepository,
			@NonNull final IBPartnerBL bpartnerBL)
	{
		this.recipientRepository = recipientRepository;
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
		final I_C_Invoice invoiceRecord = request.getRecordRef()
				.getModel(I_C_Invoice.class);

		final String invoiceEmail = invoiceRecord.getEMail();
		final String locationEmail = invoiceBL.getLocationEmail(InvoiceId.ofRepoId(invoiceRecord.getC_Invoice_ID()));

		if (invoiceRecord.getAD_User_ID() > 0)
		{
			final DocOutBoundRecipient invoiceUser = recipientRepository.getById(DocOutBoundRecipientId.ofRepoId(invoiceRecord.getAD_User_ID()));

			if (!Check.isEmpty(invoiceEmail, true))
			{
				return Optional.of(invoiceUser.withEmailAddress(invoiceEmail));
			}

			if (!Check.isEmpty(invoiceUser.getEmailAddress(), true))
			{
				return Optional.of(invoiceUser);
			}

			if (!Check.isEmpty(locationEmail))
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
						.build());

		if (billContact != null)
		{
			final DocOutBoundRecipientId recipientId = DocOutBoundRecipientId.ofRepoId(billContact.getId().getRepoId());
			final DocOutBoundRecipient docOutBoundRecipient = recipientRepository.getById(recipientId);

			if (!Check.isEmpty(invoiceEmail, true))
			{
				return Optional.of(docOutBoundRecipient.withEmailAddress(invoiceEmail));
			}

			if (!Check.isEmpty(locationEmail, true))
			{
				return Optional.of(docOutBoundRecipient.withEmailAddress(locationEmail));
			}

			if (!Check.isEmpty(docOutBoundRecipient.getEmailAddress(), true))
			{
				return Optional.of(docOutBoundRecipient);
			}
		}

		return Optional.empty();

	}

}
