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

package de.metas.postfinance;

import de.metas.attachments.AttachmentEntryCreateRequest;
import de.metas.attachments.AttachmentTags;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.postfinance.PostFinanceBPartnerConfig;
import de.metas.bpartner.postfinance.PostFinanceBPartnerConfigRepository;
import de.metas.bpartner.service.BPartnerQuery;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.document.refid.api.IReferenceNoDAO;
import de.metas.document.refid.model.I_C_ReferenceNo_Doc;
import de.metas.document.refid.model.I_C_ReferenceNo_Type;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.postfinance.generated.DownloadFile;
import de.metas.postfinance.model.XmlCustomerRegistration;
import de.metas.postfinance.model.XmlCustomerSubscriptionFormField;
import de.metas.postfinance.repository.CustomerRegistrationMessage;
import de.metas.postfinance.repository.CustomerRegistrationMessageCreateRequest;
import de.metas.postfinance.repository.CustomerRegistrationMessageQuery;
import de.metas.postfinance.repository.CustomerRegistrationMessageRepository;
import de.metas.postfinance.util.XMLUtil;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Invoice;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static de.metas.postfinance.PostFinanceConstants.DOCUMENT_REFID_ReferenceNo_Type_InvoiceReferenceNumber;

@Service
public class CustomerRegistrationMessageService
{
	private final IReferenceNoDAO referenceNoDAO = Services.get(IReferenceNoDAO.class);
	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	private final IBPartnerDAO partnerDAO = Services.get(IBPartnerDAO.class);

	private final CustomerRegistrationMessageRepository customerRegistrationMessageRepository;
	private final PostFinanceBPartnerConfigRepository postFinanceBPartnerConfigRepository;

	public CustomerRegistrationMessageService(
			@NonNull final CustomerRegistrationMessageRepository customerRegistrationMessageRepository,
			@NonNull final PostFinanceBPartnerConfigRepository postFinanceBPartnerConfigRepository)
	{
		this.customerRegistrationMessageRepository = customerRegistrationMessageRepository;
		this.postFinanceBPartnerConfigRepository = postFinanceBPartnerConfigRepository;
	}

	public void processCustomerRegistrationMessages(@NonNull final List<DownloadFile> customerRegistrationMessageFiles)
	{
		customerRegistrationMessageFiles
				.forEach(this::processDownloadFie);
	}

	public void processCustomerRegistrationMessages(@NonNull final Stream<CustomerRegistrationMessage> customerRegistrationMessages)
	{
		customerRegistrationMessages
				.forEach(this::processIfRequired);
	}

	@NonNull
	public Stream<CustomerRegistrationMessage> streamByQuery(@NonNull final CustomerRegistrationMessageQuery query)
	{
		return customerRegistrationMessageRepository.streamByQuery(query);
	}

	private void processIfRequired(@NonNull final CustomerRegistrationMessage customerRegistrationMessage)
	{
		if (customerRegistrationMessage.getSubscriptionType().isDeRegistration())
		{
			postFinanceBPartnerConfigRepository.deleteByReceiverEBillId(customerRegistrationMessage.getCustomerEBillId());
			customerRegistrationMessageRepository.markAsProcessed(customerRegistrationMessage.getId());
		}
		else
		{
			Optional.ofNullable(customerRegistrationMessage.getBPartnerId())
					.ifPresent(bPartnerId -> {
						postFinanceBPartnerConfigRepository.createOrUpdate(bPartnerId, customerRegistrationMessage.getCustomerEBillId());
						customerRegistrationMessageRepository.markAsProcessed(customerRegistrationMessage.getId());
					});
		}
	}

	private void processDownloadFie(@NonNull final DownloadFile downloadFile)
	{
		final AttachmentEntryCreateRequest attachmentEntryCreateRequest = createAttachmentRequest(downloadFile);

		XMLUtil.getXmlCustomerRegistrationMessage(downloadFile)
				.customerRegistrations()
				.stream()
				.map(customerRegistration -> toCustomerRegistrationMessageRequest(customerRegistration, attachmentEntryCreateRequest))
				.map(customerRegistrationMessageRepository::create)
				.forEach(this::processIfRequired);
	}

	@NonNull
	private AttachmentEntryCreateRequest createAttachmentRequest(@NonNull final DownloadFile downloadFile)
	{
		final AttachmentTags attachmentTags = AttachmentTags.builder()
				.tag(AttachmentTags.TAGNAME_IS_DOCUMENT, StringUtils.ofBoolean(true))
				.build();

		return AttachmentEntryCreateRequest
				.builderFromByteArray(
						downloadFile.getFilename().getValue(),
						downloadFile.getData().getValue())
				.tags(attachmentTags)
				.build();
	}

	@NonNull
	private CustomerRegistrationMessageCreateRequest toCustomerRegistrationMessageRequest(
			@NonNull final XmlCustomerRegistration customerRegistration,
			@NonNull final AttachmentEntryCreateRequest attachmentEntryCreateRequest)
	{
		final String qrCode;
		final BPartnerId bPartnerId;
		switch (SubscriptionType.ofCode(customerRegistration.subscriptionType()))
		{
			case REGISTRATION ->
			{
				qrCode = null;
				bPartnerId = customerRegistration.getCustomerNbr()
						.map(XmlCustomerSubscriptionFormField::value)
						.flatMap(value -> partnerDAO.retrieveBPartnerIdBy(BPartnerQuery.builder()
																				  .bpartnerValue(value)
																				  .build()))
						.orElse(null);
			}
			case DIRECT_REGISTRATION ->
			{
				qrCode = customerRegistration.creditorReference();

				final I_C_ReferenceNo_Type invoiceReferenceNoType = referenceNoDAO.retrieveRefNoTypeByName(DOCUMENT_REFID_ReferenceNo_Type_InvoiceReferenceNumber);
				bPartnerId = referenceNoDAO.getReferenceNoId(qrCode, invoiceReferenceNoType)
						.map(referenceNoDAO::retrieveDocAssignments)
						.flatMap(list -> list
								.stream()
								.filter(refNoDoc -> refNoDoc.getAD_Table_ID() == InterfaceWrapperHelper.getTableId(I_C_Invoice.class))
								.findFirst())
						.map(I_C_ReferenceNo_Doc::getRecord_ID)
						.map(InvoiceId::ofRepoIdOrNull)
						.map(invoiceBL::getById)
						.map(I_C_Invoice::getC_BPartner_ID)
						.map(BPartnerId::ofRepoId)
						.orElse(null);
			}
			case DE_REGISTRATION ->
			{
				qrCode = null;
				bPartnerId = postFinanceBPartnerConfigRepository.getByReceiverEBillId(customerRegistration.recipientId())
						.map(PostFinanceBPartnerConfig::getBPartnerId)
						.orElse(null);
			}
			default ->
			{
				qrCode = null;
				bPartnerId = null;
			}
		}

		return CustomerRegistrationMessageCreateRequest.builder()
				.customerEBillId(customerRegistration.recipientId())
				.subscriptionType(SubscriptionType.ofCode(customerRegistration.subscriptionType()))
				.qrCode(qrCode)
				.bPartnerId(bPartnerId)
				.attachmentEntryCreateRequest(attachmentEntryCreateRequest)
				.build();
	}
}
