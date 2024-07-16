package de.metas.dunning.document.archive;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.IBPartnerBL.RetrieveContactRequest;
import de.metas.bpartner.service.IBPartnerBL.RetrieveContactRequest.ContactType;
import de.metas.common.util.CoalesceUtil;
import de.metas.document.archive.mailrecipient.DocOutBoundRecipient;
import de.metas.document.archive.mailrecipient.DocOutBoundRecipientId;
import de.metas.document.archive.mailrecipient.DocOutBoundRecipientRepository;
import de.metas.document.archive.mailrecipient.DocOutboundLogMailRecipientProvider;
import de.metas.document.archive.mailrecipient.DocOutboundLogMailRecipientRequest;
import de.metas.dunning.DunningDocId;
import de.metas.dunning.invoice.DunningService;
import de.metas.dunning.model.I_C_DunningDoc;
import de.metas.order.impl.OrderEmailPropagationSysConfigRepository;
import de.metas.organization.ClientAndOrgId;
import de.metas.user.User;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_Invoice;
import org.springframework.stereotype.Component;

import java.util.List;
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
public class DunningDocOutboundLogMailRecipientProvider
		implements DocOutboundLogMailRecipientProvider
{

	private final DocOutBoundRecipientRepository recipientRepository;
	private final OrderEmailPropagationSysConfigRepository orderEmailPropagationSysConfigRepository;
	private final IBPartnerBL bpartnerBL;
	private final DunningService dunningService;

	public DunningDocOutboundLogMailRecipientProvider(
			@NonNull final DocOutBoundRecipientRepository recipientRepository,
			@NonNull final OrderEmailPropagationSysConfigRepository orderEmailPropagationSysConfigRepository,
			@NonNull final IBPartnerBL bpartnerBL,
			@NonNull final DunningService dunningService)
	{
		this.recipientRepository = recipientRepository;
		this.orderEmailPropagationSysConfigRepository = orderEmailPropagationSysConfigRepository;
		this.bpartnerBL = bpartnerBL;
		this.dunningService = dunningService;
	}

	@Override
	public boolean isDefault()
	{
		return false;
	}

	@Override
	public String getTableName()
	{
		return I_C_DunningDoc.Table_Name;
	}

	/**
	 * Provides the bill contact based on the dunning's bPartner and bPartner-location.
	 * Note that we don't have a dunning-contact field in {@code C_DunningDoc}.
	 */
	@Override
	public Optional<DocOutBoundRecipient> provideMailRecipient(@NonNull final DocOutboundLogMailRecipientRequest request)
	{
		final I_C_DunningDoc dunningRecord = request.getRecordRef()
				.getModel(I_C_DunningDoc.class);

		final DunningDocId dunningDocId = DunningDocId.ofRepoId(dunningRecord.getC_DunningDoc_ID());
		final List<I_C_Invoice> dunnedInvoices = dunningService.retrieveDunnedInvoices(dunningDocId);
		final int singleCommonInvoiceContactId = CollectionUtils.extractSingleElementOrDefault(dunnedInvoices, I_C_Invoice::getAD_User_ID, -1);

		final I_AD_User dunningContact =  dunningRecord.getC_Dunning_Contact();
		DocOutBoundRecipient docOutBoundRecipient = null;

		if (dunningContact!= null  && dunningContact.isDunningContact())
		{
			docOutBoundRecipient = recipientRepository.getById(DocOutBoundRecipientId.ofRepoId(dunningContact.getAD_User_ID()));
		}
		else if (singleCommonInvoiceContactId > 0)
		{
			docOutBoundRecipient = recipientRepository.getById(DocOutBoundRecipientId.ofRepoId(singleCommonInvoiceContactId));
		}
		else
		{
			final BPartnerId bpartnerId = BPartnerId.ofRepoId(dunningRecord.getC_BPartner_ID());
			final BPartnerLocationId bPartnerLocationId = BPartnerLocationId.ofRepoId(bpartnerId, dunningRecord.getC_BPartner_Location_ID());
			final User billContact = bpartnerBL.retrieveContactOrNull(
					RetrieveContactRequest
							.builder()
							.bpartnerId(bpartnerId)
							.bPartnerLocationId(bPartnerLocationId)
							.contactType(ContactType.BILL_TO_DEFAULT)
							.build());

			if (billContact != null)
			{
				Loggables.addLog("Found billContact={} with a mail address for bpartnerId={} and bPartnerLocationId={}", billContact, bpartnerId, bPartnerLocationId);

				final DocOutBoundRecipientId recipientId = DocOutBoundRecipientId.ofRepoId(billContact.getId().getRepoId());
				docOutBoundRecipient = recipientRepository.getById(recipientId);
			}
		}


		if (docOutBoundRecipient == null)
		{
			Loggables.addLog("Found no dunning contact/bill contact with a mail address for bpartnerId={} ", dunningRecord.getC_BPartner_ID());
			return Optional.empty();
		}
		else
		{
			final boolean propagateToDocOutboundLog = orderEmailPropagationSysConfigRepository.isPropagateToDocOutboundLog(ClientAndOrgId.ofClientAndOrg(request.getClientId(), request.getOrgId()));

			final String invoiceEmail = propagateToDocOutboundLog ? CollectionUtils.extractSingleElementOrDefault(dunnedInvoices,
																												  invoice -> CoalesceUtil.coalesce(invoice.getEMail(), ""),
																												  "") : null;
			final String locationEmail = dunningService.getLocationEmail(dunningDocId);

			if (Check.isNotBlank(docOutBoundRecipient.getEmailAddress()))
			{
				Loggables.addLog("The dunned invoices all have invoiceUser={}, so we take that user as the dunning mail's participant", docOutBoundRecipient);
				return Optional.of(docOutBoundRecipient);
			}

			if (Check.isNotBlank(invoiceEmail))
			{
				Loggables.addLog("The dunned invoices all have invoiceUser={} and the invoice has the email {} so we take that user as the dunning mail's participant, with this email address", docOutBoundRecipient, invoiceEmail);
				return Optional.of(docOutBoundRecipient.withEmailAddress(invoiceEmail));
			}

			if (Check.isNotBlank(locationEmail))
			{
				Loggables.addLog("The dunned invoices all have invoiceUser={} and the location has the email {} so we take that user as the dunning mail's participant, with this email address", docOutBoundRecipient, locationEmail);
				return Optional.of(docOutBoundRecipient.withEmailAddress(invoiceEmail));
			}

			Loggables.addLog("The dunned invoices' common invoiceUser={} has not mail address", docOutBoundRecipient);
			return Optional.empty();

		}

	}

}
