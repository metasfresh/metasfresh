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

package de.metas.postfinance.customerregistration;

import de.metas.attachments.AttachmentEntryCreateRequest;
import de.metas.attachments.AttachmentTags;
import de.metas.bpartner.BPartnerId;
import de.metas.document.refid.api.IReferenceNoDAO;
import de.metas.document.refid.model.I_C_ReferenceNo_Doc;
import de.metas.document.refid.model.I_C_ReferenceNo_Type;
import de.metas.externalreference.ExternalReferenceQuery;
import de.metas.externalreference.ExternalReferenceRepository;
import de.metas.externalreference.OtherExternalSystem;
import de.metas.externalreference.bpartner.BPartnerExternalReferenceType;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.organization.OrgId;
import de.metas.postfinance.bpartnerconfig.PostFinanceBPartnerConfig;
import de.metas.postfinance.bpartnerconfig.PostFinanceBPartnerConfigRepository;
import de.metas.postfinance.customerregistration.repository.CustomerRegistrationMessage;
import de.metas.postfinance.customerregistration.repository.CustomerRegistrationMessageCreateRequest;
import de.metas.postfinance.customerregistration.repository.CustomerRegistrationMessageQuery;
import de.metas.postfinance.customerregistration.repository.CustomerRegistrationMessageRepository;
import de.metas.postfinance.customerregistration.util.XMLUtil;
import de.metas.postfinance.jaxb.DownloadFile;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Invoice;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static de.metas.postfinance.PostFinanceConstants.CUSTOM_FIELD_BPARTNEREXTERNALREFERENCE;
import static de.metas.postfinance.PostFinanceConstants.DOCUMENT_REFID_ReferenceNo_Type_InvoiceReferenceNumber;

@Service
public class CustomerRegistrationMessageService
{
	private final IReferenceNoDAO referenceNoDAO = Services.get(IReferenceNoDAO.class);
	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);

	private final CustomerRegistrationMessageRepository customerRegistrationMessageRepository;
	private final PostFinanceBPartnerConfigRepository postFinanceBPartnerConfigRepository;
	private final ExternalReferenceRepository externalReferenceRepository;

	public CustomerRegistrationMessageService(
			@NonNull final CustomerRegistrationMessageRepository customerRegistrationMessageRepository,
			@NonNull final PostFinanceBPartnerConfigRepository postFinanceBPartnerConfigRepository,
			@NonNull final ExternalReferenceRepository externalReferenceRepository)
	{
		this.customerRegistrationMessageRepository = customerRegistrationMessageRepository;
		this.postFinanceBPartnerConfigRepository = postFinanceBPartnerConfigRepository;
		this.externalReferenceRepository = externalReferenceRepository;
	}

	public void processCustomerRegistrationMessages(
			@NonNull final List<DownloadFile> customerRegistrationMessageFiles,
			@NonNull final OrgId orgId)
	{
		customerRegistrationMessageFiles
				.forEach(downloadFile -> processDownloadFie(downloadFile, orgId));
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

	private void processDownloadFie(
			@NonNull final DownloadFile downloadFile,
			@NonNull final OrgId orgId)
	{
		final AttachmentEntryCreateRequest attachmentEntryCreateRequest = createAttachmentRequest(downloadFile);

		XMLUtil.getCustomerRegistrationMessage(downloadFile)
				.getCustomerRegistration()
				.stream()
				.map(customerRegistration -> toCustomerRegistrationMessageRequest(customerRegistration, attachmentEntryCreateRequest, orgId))
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
			@NonNull final CustomerRegistration customerRegistration,
			@NonNull final AttachmentEntryCreateRequest attachmentEntryCreateRequest,
			@NonNull final OrgId orgId)
	{
		final SubscriptionType subscriptionType = SubscriptionType.ofCode(customerRegistration.getSubscriptionType());
		final CustomerRegistrationMessageCreateRequest.CustomerRegistrationMessageCreateRequestBuilder builder = CustomerRegistrationMessageCreateRequest.builder()
				.customerEBillId(customerRegistration.getRecipientID())
				.subscriptionType(subscriptionType)
				.attachmentEntryCreateRequest(attachmentEntryCreateRequest);

		switch (subscriptionType)
		{
			case REGISTRATION ->
			{
				return builder
						.qrCode(null)
						.bPartnerId(getBPartnerIdByExternalId(customerRegistration, orgId)
											.orElse(null))
						.build();
			}
			case DIRECT_REGISTRATION ->
			{
				final String qrCode = customerRegistration.getCreditorReference();
				return builder
						.qrCode(qrCode)
						.bPartnerId(getBPartnerIdByQRCode(qrCode)
											.orElse(null))
						.build();
			}
			case DE_REGISTRATION ->
			{
				return builder
						.qrCode(null)
						.bPartnerId(postFinanceBPartnerConfigRepository.getByReceiverEBillId(customerRegistration.getRecipientID())
											.map(PostFinanceBPartnerConfig::getBPartnerId)
											.orElse(null))
						.build();
			}
			default ->
			{
				return builder.build();
			}
		}
	}

	@NonNull
	private Optional<BPartnerId> getBPartnerIdByExternalId(
			@NonNull final CustomerRegistration customerRegistration,
			@NonNull final OrgId orgId)
	{
		return customerRegistration.getCustomerSubscriptionFormField()
                .stream()
                .filter(formField -> CUSTOM_FIELD_BPARTNEREXTERNALREFERENCE.equals(formField.getTechnicalID()))
                .map(CustomerSubscriptionFormField::getValue)
                .map(externalId -> externalReferenceRepository.getReferencedRecordIdOrNullBy(
                        ExternalReferenceQuery.builder()
                                .orgId(orgId)
                                .externalSystem(OtherExternalSystem.OTHER)
                                .externalReference(externalId)
                                .externalReferenceType(BPartnerExternalReferenceType.BPARTNER)
                                .build()))
				.filter(Objects::nonNull)
				.map(BPartnerId::ofRepoId)
				.findFirst();
	}

	@NonNull
	private Optional<BPartnerId> getBPartnerIdByQRCode(@NonNull final String qrCode)
	{
		final I_C_ReferenceNo_Type invoiceReferenceNoType = referenceNoDAO.retrieveRefNoTypeByName(DOCUMENT_REFID_ReferenceNo_Type_InvoiceReferenceNumber);
		return referenceNoDAO.getReferenceNoId(qrCode, invoiceReferenceNoType)
				.map(referenceNoDAO::retrieveDocAssignments)
				.flatMap(list -> list
						.stream()
						.filter(refNoDoc -> refNoDoc.getAD_Table_ID() == InterfaceWrapperHelper.getTableId(I_C_Invoice.class))
						.findFirst())
				.map(I_C_ReferenceNo_Doc::getRecord_ID)
				.map(InvoiceId::ofRepoIdOrNull)
				.map(invoiceBL::getById)
				.map(I_C_Invoice::getC_BPartner_ID)
				.map(BPartnerId::ofRepoId);
	}
}
