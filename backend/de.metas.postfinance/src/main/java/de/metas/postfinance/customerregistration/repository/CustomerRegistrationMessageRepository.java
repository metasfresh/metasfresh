/*
 * #%L
 * de.metas.postfinance
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.postfinance.customerregistration.repository;

import de.metas.attachments.AttachmentEntryService;
import de.metas.bpartner.BPartnerId;
import de.metas.postfinance.customerregistration.SubscriptionType;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import de.metas.postfinance.model.I_PostFinance_Customer_Registration_Message;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class CustomerRegistrationMessageRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final AttachmentEntryService attachmentEntryService;

	public CustomerRegistrationMessageRepository(@NonNull final AttachmentEntryService attachmentEntryService)
	{
		this.attachmentEntryService = attachmentEntryService;
	}

	@NonNull
	public CustomerRegistrationMessage create(@NonNull final CustomerRegistrationMessageCreateRequest customerRegistrationMessageCreateRequest)
	{
		final I_PostFinance_Customer_Registration_Message customerRegistrationMessage = newInstance(I_PostFinance_Customer_Registration_Message.class);

		customerRegistrationMessage.setSubscriptionType(customerRegistrationMessageCreateRequest.getSubscriptionType().getCode());
		customerRegistrationMessage.setPostFinance_Receiver_eBillId(customerRegistrationMessageCreateRequest.getCustomerEBillId());
		customerRegistrationMessage.setQRCode(customerRegistrationMessageCreateRequest.getQrCode());
		customerRegistrationMessage.setC_BPartner_ID(BPartnerId.toRepoId(customerRegistrationMessageCreateRequest.getBPartnerId()));

		saveRecord(customerRegistrationMessage);

		attachmentEntryService.createNewAttachment(customerRegistrationMessage, customerRegistrationMessageCreateRequest.getAttachmentEntryCreateRequest());

		return toCustomerRegistrationMessage(customerRegistrationMessage);
	}

	public void markAsProcessed(@NonNull final CustomerRegistrationMessageId id)
	{
		final I_PostFinance_Customer_Registration_Message customerRegistrationMessage = getRecordById(id);

		if (customerRegistrationMessage.isProcessed())
		{
			return;
		}

		customerRegistrationMessage.setProcessed(true);

		save(customerRegistrationMessage);
	}

	@NonNull
	private I_PostFinance_Customer_Registration_Message getRecordById(@NonNull final CustomerRegistrationMessageId id)
	{
		final I_PostFinance_Customer_Registration_Message customerRegistrationMessage = InterfaceWrapperHelper.load(id, I_PostFinance_Customer_Registration_Message.class);

		if (customerRegistrationMessage == null)
		{
			throw new AdempiereException("No PostFinance_Customer_Registration_Message record found for ID!")
					.appendParametersToMessage()
					.setParameter("CustomerRegistrationMessageId", id);
		}

		return customerRegistrationMessage;
	}

	@NonNull
	public Stream<CustomerRegistrationMessage> streamByQuery(@NonNull final CustomerRegistrationMessageQuery query)
	{
		final IQueryBuilder<I_PostFinance_Customer_Registration_Message> queryBuilder = Optional.ofNullable(query.getQueryBuilder())
				.orElseGet(() -> queryBL.createQueryBuilder(I_PostFinance_Customer_Registration_Message.class)
						.addOnlyActiveRecordsFilter());

		if (query.getIsBPartnerIdSet() != null)
		{
			queryBuilder.addNotNull(I_PostFinance_Customer_Registration_Message.COLUMNNAME_C_BPartner_ID);
		}

		if (query.getProcessed() != null)
		{
			queryBuilder.addEqualsFilter(I_PostFinance_Customer_Registration_Message.COLUMNNAME_Processed, query.getProcessed());
		}

		return queryBuilder.create()
				.iterateAndStream()
				.map(CustomerRegistrationMessageRepository::toCustomerRegistrationMessage);
	}

	@NonNull
	private static CustomerRegistrationMessage toCustomerRegistrationMessage(@NonNull final I_PostFinance_Customer_Registration_Message customerRegistrationMessage)
	{
		return CustomerRegistrationMessage.builder()
				.id(CustomerRegistrationMessageId.ofRepoId(customerRegistrationMessage.getPostFinance_Customer_Registration_Message_ID()))
				.customerEBillId(customerRegistrationMessage.getPostFinance_Receiver_eBillId())
				.subscriptionType(SubscriptionType.ofCode(customerRegistrationMessage.getSubscriptionType()))
				.qrCode(customerRegistrationMessage.getQRCode())
				.bPartnerId(BPartnerId.ofRepoIdOrNull(customerRegistrationMessage.getC_BPartner_ID()))
				.processed(customerRegistrationMessage.isProcessed())
				.build();
	}
}
