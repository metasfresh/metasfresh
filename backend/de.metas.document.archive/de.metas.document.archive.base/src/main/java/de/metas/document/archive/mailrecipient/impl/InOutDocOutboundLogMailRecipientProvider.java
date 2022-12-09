/*
 * #%L
 * de.metas.document.archive.base
 * %%
 * Copyright (C) 2022 metas GmbH
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
import de.metas.inout.IInOutBL;
import de.metas.inout.InOutId;
import de.metas.order.impl.OrderEmailPropagationSysConfigRepository;
import de.metas.organization.ClientAndOrgId;
import de.metas.user.User;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_M_InOut;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class InOutDocOutboundLogMailRecipientProvider implements DocOutboundLogMailRecipientProvider
{

	private final DocOutBoundRecipientRepository recipientRepository;
	private final OrderEmailPropagationSysConfigRepository orderEmailPropagationSysConfigRepository;
	private final IBPartnerBL bpartnerBL;
	private final IInOutBL inoutBL = Services.get(IInOutBL.class);

	public InOutDocOutboundLogMailRecipientProvider(
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
		return I_M_InOut.Table_Name;
	}

	@Override
	public Optional<DocOutBoundRecipient> provideMailRecipient(@NonNull final DocOutboundLogMailRecipientRequest request)
	{
		final I_M_InOut inOutRecord = request.getRecordRef()
				.getModel(I_M_InOut.class);

		final boolean propagateToDocOutboundLog = orderEmailPropagationSysConfigRepository.isPropagateToDocOutboundLog(
				ClientAndOrgId.ofClientAndOrg(request.getClientId(), request.getOrgId()));

		final String inoutEmail = propagateToDocOutboundLog ? inOutRecord.getEMail(): null;
		final String locationEmail = inoutBL.getLocationEmail(InOutId.ofRepoId(inOutRecord.getM_InOut_ID()));

		if (inOutRecord.getAD_User_ID() > 0)
		{
			final DocOutBoundRecipient inOutUser = recipientRepository.getById(DocOutBoundRecipientId.ofRepoId(inOutRecord.getAD_User_ID()));

			if (Check.isNotBlank(inoutEmail))
			{
				return Optional.of(inOutUser.withEmailAddress(inoutEmail));
			}

			if (Check.isNotBlank(inOutUser.getEmailAddress()))
			{
				return Optional.of(inOutUser);
			}

			if (Check.isNotBlank(locationEmail))
			{
				return Optional.of(inOutUser.withEmailAddress(locationEmail));
			}

		}

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(inOutRecord.getC_BPartner_ID());

		final User billContact = bpartnerBL.retrieveContactOrNull(
				IBPartnerBL.RetrieveContactRequest
						.builder()
						.bpartnerId(bpartnerId)
						.bPartnerLocationId(BPartnerLocationId.ofRepoId(bpartnerId, inOutRecord.getC_BPartner_Location_ID()))
						.contactType(IBPartnerBL.RetrieveContactRequest.ContactType.BILL_TO_DEFAULT)
						.build());

		if (billContact != null)
		{
			final DocOutBoundRecipientId recipientId = DocOutBoundRecipientId.ofRepoId(billContact.getId().getRepoId());
			final DocOutBoundRecipient docOutBoundRecipient = recipientRepository.getById(recipientId);

			if (Check.isNotBlank(inoutEmail))
			{
				return Optional.of(docOutBoundRecipient.withEmailAddress(inoutEmail));
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