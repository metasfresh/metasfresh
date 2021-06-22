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
import de.metas.user.User;
import de.metas.util.Check;
import lombok.NonNull;
import org.compiere.model.I_C_Order;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OrderDocOutboundLogMailRecipientProvider implements DocOutboundLogMailRecipientProvider
{
	private final DocOutBoundRecipientRepository recipientRepository;
	private final IBPartnerBL bpartnerBL;

	public OrderDocOutboundLogMailRecipientProvider(
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
		return I_C_Order.Table_Name;
	}

	@Override
	public Optional<DocOutBoundRecipient> provideMailRecipient(
			@NonNull final DocOutboundLogMailRecipientRequest request)
	{
		final I_C_Order orderRecord = request.getRecordRef()
				.getModel(I_C_Order.class);

		final int orderUserRecordId = orderRecord.getAD_User_ID();
		if (orderUserRecordId > 0)
		{
			final DocOutBoundRecipient orderUser = recipientRepository.getById(DocOutBoundRecipientId.ofRepoId(orderUserRecordId));
			if (!Check.isEmpty(orderUser.getEmailAddress(), true))
			{
				return Optional.of(orderUser);
			}
		}

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(orderRecord.getC_BPartner_ID());

		final User billContact = bpartnerBL.retrieveContactOrNull(
				IBPartnerBL.RetrieveContactRequest
						.builder()
						.bpartnerId(bpartnerId)
						.bPartnerLocationId(BPartnerLocationId.ofRepoId(bpartnerId, orderRecord.getC_BPartner_Location_ID()))
						.contactType(IBPartnerBL.RetrieveContactRequest.ContactType.BILL_TO_DEFAULT)
						.filter(user -> !Check.isEmpty(user.getEmailAddress(), true))
						.build());
		if (billContact != null)
		{
			final DocOutBoundRecipientId recipientId = DocOutBoundRecipientId.ofRepoId(billContact.getId().getRepoId());
			final DocOutBoundRecipient docOutBoundRecipient = recipientRepository.getById(recipientId);
			return Optional.of(docOutBoundRecipient);
		}
		return Optional.empty();
	}
}