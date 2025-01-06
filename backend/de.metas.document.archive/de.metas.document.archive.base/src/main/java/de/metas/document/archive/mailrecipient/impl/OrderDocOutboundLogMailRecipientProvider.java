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
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.order.impl.OrderEmailPropagationSysConfigRepository;
import de.metas.organization.ClientAndOrgId;
import de.metas.user.User;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OrderDocOutboundLogMailRecipientProvider implements DocOutboundLogMailRecipientProvider
{
	private final DocOutBoundRecipientRepository recipientRepository;
	private final OrderEmailPropagationSysConfigRepository orderEmailPropagationSysConfigRepository;
	private final IBPartnerBL bpartnerBL;
	private final IOrderBL orderBL = Services.get(IOrderBL.class);


	public OrderDocOutboundLogMailRecipientProvider(
			@NonNull final DocOutBoundRecipientRepository recipientRepository,
			@NonNull final IBPartnerBL bpartnerBL,
			@NonNull final OrderEmailPropagationSysConfigRepository orderEmailPropagationSysConfigRepository)
	{
		this.recipientRepository = recipientRepository;
		this.bpartnerBL = bpartnerBL;
		this.orderEmailPropagationSysConfigRepository = orderEmailPropagationSysConfigRepository;
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

		final String orderEmail = orderEmailPropagationSysConfigRepository.isPropagateToDocOutboundLog(
				ClientAndOrgId.ofClientAndOrg(request.getClientId(), request.getOrgId())) ?
				orderRecord.getEMail() : null;

		final String locationEmail = orderBL.getLocationEmail(OrderId.ofRepoId(orderRecord.getC_Order_ID()));

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(orderRecord.getC_BPartner_ID());
		final I_C_BPartner bpartnerPO =  bpartnerBL.getById(bpartnerId);

		final int orderUserRecordId = orderRecord.getAD_User_ID();
		if (orderUserRecordId > 0)
		{
			final DocOutBoundRecipient orderUser = recipientRepository.getById(DocOutBoundRecipientId.ofRepoId(orderUserRecordId));
			if (!Check.isBlank(orderUser.getEmailAddress()))

			if (Check.isNotBlank(orderEmail))
			{
				return Optional.of(orderUser.withEmailAddress(orderEmail));
			}

			if (Check.isNotBlank(orderUser.getEmailAddress()))
			{
				return Optional.of(orderUser);
			}

			if (Check.isNotBlank(locationEmail))
			{
				return Optional.of(orderUser.withEmailAddress(locationEmail));
			}

			if (Check.isNotBlank(bpartnerPO.getEMail()))
			{
				return Optional.of(orderUser.withEmailAddress(bpartnerPO.getEMail()));
			}
		}

		final int billingUserRecordId = orderRecord.getBill_User_ID();
		if (billingUserRecordId > 0)
		{
			final DocOutBoundRecipient orderBillingUser = recipientRepository.getById(DocOutBoundRecipientId.ofRepoId(billingUserRecordId));

			if (Check.isNotBlank(orderEmail))
			{
				return Optional.of(orderBillingUser.withEmailAddress(orderEmail));
			}

			if (!Check.isBlank(orderBillingUser.getEmailAddress()))
			{
				return Optional.of(orderBillingUser);
			}

			if (Check.isNotBlank(bpartnerPO.getEMail()))
			{
				return Optional.of(orderBillingUser.withEmailAddress(bpartnerPO.getEMail()));
			}
		}


		final User billContact = bpartnerBL.retrieveContactOrNull(
				IBPartnerBL.RetrieveContactRequest
						.builder()
						.bpartnerId(bpartnerId)
						.bPartnerLocationId(BPartnerLocationId.ofRepoId(bpartnerId, orderRecord.getC_BPartner_Location_ID()))
						.contactType(IBPartnerBL.RetrieveContactRequest.ContactType.BILL_TO_DEFAULT)
						.onlyActive(true)
						.filter(user -> !Check.isEmpty(user.getEmailAddress(), true))
						.build());
		if (billContact != null)
		{
			final DocOutBoundRecipientId recipientId = DocOutBoundRecipientId.ofRepoId(billContact.getId().getRepoId());
			final DocOutBoundRecipient docOutBoundRecipient = recipientRepository.getById(recipientId);

			if (Check.isNotBlank(orderEmail))
			{
				return Optional.of(docOutBoundRecipient.withEmailAddress(orderEmail));
			}

			if (Check.isNotBlank(locationEmail))
			{
				return Optional.of(docOutBoundRecipient.withEmailAddress(locationEmail));
			}

			if (Check.isNotBlank(docOutBoundRecipient.getEmailAddress()))
			{
				return Optional.of(docOutBoundRecipient);
			}

			if (Check.isNotBlank(bpartnerPO.getEMail()))
			{
				return Optional.of(docOutBoundRecipient.withEmailAddress(bpartnerPO.getEMail()));
			}
		}

		return Optional.empty();
	}
}