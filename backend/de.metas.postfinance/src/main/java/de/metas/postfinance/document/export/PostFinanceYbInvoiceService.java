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

package de.metas.postfinance.document.export;

import com.google.common.collect.ImmutableList;
import de.metas.attachments.AttachmentEntryCreateRequest;
import de.metas.attachments.AttachmentEntryService;
import de.metas.attachments.AttachmentEntryType;
import de.metas.attachments.AttachmentTags;
import de.metas.banking.BankAccount;
import de.metas.banking.api.IBPBankAccountDAO;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.postfinance.PostFinanceBPartnerConfig;
import de.metas.bpartner.postfinance.PostFinanceBPartnerConfigRepository;
import de.metas.bpartner.postfinance.PostFinanceOrgConfig;
import de.metas.bpartner.postfinance.PostFinanceOrgConfigRepository;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.EmptyUtil;
import de.metas.document.DocBaseType;
import de.metas.document.archive.DocOutboundLogId;
import de.metas.document.archive.api.IDocOutboundDAO;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log_Line;
import de.metas.document.archive.model.X_C_Doc_Outbound_Log;
import de.metas.document.refid.api.IReferenceNoDAO;
import de.metas.document.refid.model.I_C_ReferenceNo;
import de.metas.document.refid.model.I_C_ReferenceNo_Type;
import de.metas.dunning_gateway.spi.model.DunningToExport;
import de.metas.i18n.ILanguageDAO;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoice_gateway.spi.model.InvoiceLine;
import de.metas.invoice_gateway.spi.model.InvoiceTax;
import de.metas.invoice_gateway.spi.model.export.InvoiceToExport;
import de.metas.location.Location;
import de.metas.location.LocationId;
import de.metas.location.LocationRepository;
import de.metas.organization.OrgId;
import de.metas.postfinance.B2BServiceWrapper;
import de.metas.postfinance.docoutboundlog.PostFinanceLogCreateRequest;
import de.metas.postfinance.docoutboundlog.PostFinanceLogRepository;
import de.metas.postfinance.jaxb.ArrayOfProcessedInvoice;
import de.metas.postfinance.jaxb.Invoice;
import de.metas.postfinance.jaxb.ProcessedInvoice;
import de.metas.postfinance.paperBillReferences.PaperBillReference;
import de.metas.postfinance.paperBillReferences.PaperBillReferencesRepository;
import de.metas.postfinance.ybinvoice.v2.AccountAssignmentType;
import de.metas.postfinance.ybinvoice.v2.AchievementDateType;
import de.metas.postfinance.ybinvoice.v2.AddressType;
import de.metas.postfinance.ybinvoice.v2.AppendixType;
import de.metas.postfinance.ybinvoice.v2.BillHeaderType;
import de.metas.postfinance.ybinvoice.v2.BillType;
import de.metas.postfinance.ybinvoice.v2.DeliveryType;
import de.metas.postfinance.ybinvoice.v2.Envelope;
import de.metas.postfinance.ybinvoice.v2.HeaderType;
import de.metas.postfinance.ybinvoice.v2.LineItemType;
import de.metas.postfinance.ybinvoice.v2.ObjectFactory;
import de.metas.postfinance.ybinvoice.v2.PartyType;
import de.metas.postfinance.ybinvoice.v2.Reference;
import de.metas.postfinance.ybinvoice.v2.SummaryType;
import de.metas.postfinance.ybinvoice.v2.TaxDetailType;
import de.metas.postfinance.ybinvoice.v2.TaxLineItemType;
import de.metas.postfinance.ybinvoice.v2.TaxType;
import de.metas.postfinance.ybinvoice.v2.YbInvoiceType;
import de.metas.product.IProductBL;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.Tax;
import de.metas.tax.api.TaxId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import de.metas.user.api.IUserBL;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.archive.AdArchive;
import org.adempiere.archive.ArchiveId;
import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.util.Env;
import org.compiere.util.MimeType;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static de.metas.postfinance.B2BServiceWrapper.B2B_SERVICE_OBJECT_FACTORY;
import static de.metas.postfinance.document.export.PostFinanceDocumentType.BILL;

@Service
@RequiredArgsConstructor
public class PostFinanceYbInvoiceService
{
	@NonNull private final B2BServiceWrapper b2BServiceWrapper;
	@NonNull private final AttachmentEntryService attachmentEntryService;
	@NonNull private final PostFinanceLogRepository postFinanceLogRepository;
	@NonNull private final PostFinanceBPartnerConfigRepository postFinanceBPartnerConfigRepository;
	@NonNull private final LocationRepository locationRepository;
	@NonNull private final PostFinanceOrgConfigRepository postFinanceOrgConfigRepository;
	@NonNull private final PaperBillReferencesRepository paperBillReferencesRepository;
	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	private final IBPartnerBL bPartnerBL = Services.get(IBPartnerBL.class);
	private final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final ITaxBL taxBL = Services.get(ITaxBL.class);
	private final IDocOutboundDAO docOutboundDAO = Services.get(IDocOutboundDAO.class);
	private final IArchiveBL archiveBL = Services.get(IArchiveBL.class);
	private final IUserBL userBL = Services.get(IUserBL.class);
	private final IBPBankAccountDAO bankAccountDAO = Services.get(IBPBankAccountDAO.class);
	private final IReferenceNoDAO referenceNoDAO = Services.get(IReferenceNoDAO.class);
	private final ILanguageDAO languageDAO = Services.get(ILanguageDAO.class);
	private static final Logger logger = Logger.getLogger(PostFinanceYbInvoiceService.class.getName());
	private static final String YB_INVOICE_BILL_DETAILS_TYPE_PDF_APPENDIX = "PDFAppendix";
	private static final String YB_INVOICE_PAPER_BILL_ID = "41100000301089304";
	private static final JAXBElement<String> XML_FILE_TYPE = B2B_SERVICE_OBJECT_FACTORY.createInvoiceFileType("XML");
	private static final String BILL_SERVER = "IPECeBILLServer";
	private static final String YB_INVOICE_USE_CASE_CREATE = "CreateybInvoice";
	private static final String YB_INVOICE_SESSION_ID = "1";
	private static final String METASFRESH = "metasfresh";
	private static final short YB_INVOICE_STATUS = 0;
	private static final BigDecimal YB_INVOICE_VERSION = BigDecimal.valueOf(2.0);
	private static final String INVOICE_REFERENCE = "InvoiceReference";
	private static final String AD_LANGUAGE_DE = "de_DE";

	public static final ObjectFactory YB_INVOICE_OBJECT_FACTORY = new ObjectFactory();


	public void exportToPostFinance(@NonNull final String billerId, @NonNull final List<PostFinanceYbInvoiceResponse> invoices)
	{
		final ArrayOfProcessedInvoice arrayOfProcessedInvoice = b2BServiceWrapper.uploadFilesReport(billerId, invoices);
		arrayOfProcessedInvoice.getProcessedInvoice()
				.forEach(processedInvoice -> handleProcessedInvoice(processedInvoice, invoices));
	}

	private void handleProcessedInvoice(@NonNull final ProcessedInvoice processedInvoice, @NonNull final List<PostFinanceYbInvoiceResponse> invoices)
	{
		final String transactionId = processedInvoice.getTransactionID().getValue();
		final List<PostFinanceYbInvoiceResponse> matchingInvoice = invoices.stream()
				.filter(invoice -> invoice.getTransactionId().equals(transactionId))
				.toList();
		if(matchingInvoice.size() != 1)
		{
			logger.log(Level.SEVERE, "PostFinance document export result couldn't be matched to document. This shouldn't happen!");
			return;
		}
		final PostFinanceYbInvoiceResponse postFinanceYbInvoiceResponse = matchingInvoice.get(0);

		final byte[] data;
		final TableRecordReference docOutboundLogReference = postFinanceYbInvoiceResponse.getDocOutboundLogReference();
		try
		{
			final JAXBContext contextObj = JAXBContext.newInstance(ProcessedInvoice.class);

			final Marshaller marshallerObj = contextObj.createMarshaller();
			marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			final JAXBElement<ProcessedInvoice> jaxbElement =
					new JAXBElement<>(new QName("", "processedInvoice"),
									  ProcessedInvoice.class,
									  processedInvoice);

			final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			marshallerObj.marshal(jaxbElement, outputStream);
			data = outputStream.toByteArray();
		}
		catch (final JAXBException e)
		{
			handleExceptions(
					docOutboundLogReference,
					new PostFinanceExportException("Error on attaching PostFinance document export result", e)
			);
			return;
		}

		final AttachmentTags attachmentTags = AttachmentTags.builder()
				.tag(AttachmentTags.TAGNAME_IS_DOCUMENT, StringUtils.ofBoolean(true))
				.build();

		attachmentEntryService.createNewAttachment(ImmutableList.of(docOutboundLogReference,
																	postFinanceYbInvoiceResponse.getPInstanceReference()),
												   AttachmentEntryCreateRequest.builder()
														   .type(AttachmentEntryType.Data)
														   .filename(transactionId + "_postFinance_upload_processing_result.xml")
														   .data(data)
														   .contentType(MimeType.TYPE_XML)
														   .tags(attachmentTags)
														   .build());


		final DocOutboundLogId docOutboundLogId = docOutboundLogReference.getIdAssumingTableName(I_C_Doc_Outbound_Log.Table_Name, DocOutboundLogId::ofRepoId);
		if(processedInvoice.getProcessingState().getValue().equals("OK"))
		{
			docOutboundDAO.setPostFinanceExportStatus(docOutboundLogId, X_C_Doc_Outbound_Log.POSTFINANCE_EXPORT_STATUS_OK);
			postFinanceLogRepository.create(PostFinanceLogCreateRequest.builder()
													.docOutboundLogId(docOutboundLogId)
													.message("PostFinance upload successful")
													.transactionId(postFinanceYbInvoiceResponse.getTransactionId())
													.build());
		}
		else
		{
			handleExceptions(
					docOutboundLogReference,
					new PostFinanceExportException("PostFinance upload failed see attached " + transactionId + "_postFinance_upload_processing_result.xml")
			);
		}
	}

	public void handleExceptions(
			@NonNull final TableRecordReference docOutboundLogReference,
			@NonNull final PostFinanceExportException postFinanceExportException)
	{
		final DocOutboundLogId docOutboundLogId = docOutboundLogReference.getIdAssumingTableName(I_C_Doc_Outbound_Log.Table_Name, DocOutboundLogId::ofRepoId);
		postFinanceLogRepository.create(PostFinanceLogCreateRequest.builder()
												.docOutboundLogId(docOutboundLogId)
												.message(postFinanceExportException.getMessage())
												.postFinanceExportException(postFinanceExportException)
												.build());
		docOutboundDAO.setPostFinanceExportStatus(docOutboundLogId, X_C_Doc_Outbound_Log.POSTFINANCE_EXPORT_STATUS_Error);
	}

	public Envelope prepareExportData(
			@NonNull final PostFinanceYbInvoiceRequest postFinanceYbInvoiceRequest,
			@NonNull final InvoiceToExport invoiceToExport)
	{
		final YbInvoiceType ybInvoiceType = YB_INVOICE_OBJECT_FACTORY.createYbInvoiceType();
		ybInvoiceType.setDeliveryInfo(getDeliveryType(invoiceToExport));
		ybInvoiceType.setBill(getBillType(invoiceToExport));

		final DocOutboundLogId docOutboundLogId = DocOutboundLogId.ofRepoId(postFinanceYbInvoiceRequest.getDocOutboundLogReference().getRecord_ID());
		final AppendixType appendixType = getAppendixType(docOutboundLogId);
		ybInvoiceType.setAppendix(appendixType);

		final Envelope envelope = getEnvelopeWithHeader(invoiceToExport);
		envelope.setBody(ybInvoiceType);

		return envelope;
	}

	private AppendixType getAppendixType(@NonNull final DocOutboundLogId docOutboundLogId)
	{
		final I_C_Doc_Outbound_Log_Line docOutboundLogLine = docOutboundDAO.retrieveCurrentPDFArchiveLogLineOrNull(docOutboundLogId);
		if(docOutboundLogLine == null)
		{
			throw new PostFinanceExportException("No PDF archive data found");
		}
		final AdArchive archive = archiveBL.getById(ArchiveId.ofRepoId(docOutboundLogLine.getAD_Archive_ID()));

		final AppendixType appendixType = YB_INVOICE_OBJECT_FACTORY.createAppendixType();
		final AppendixType.Document document = YB_INVOICE_OBJECT_FACTORY.createAppendixTypeDocument();

		document.setMimeType(MimeType.TYPE_PDF_APPENDIX);
		document.setValue(Base64.getEncoder().encodeToString(archive.getArchiveData()));
		appendixType.getDocument().add(document);
		return appendixType;
	}

	private Envelope  getEnvelopeWithHeader(@NonNull final InvoiceToExport invoiceToExport)
	{
		final HeaderType headerType = getHeaderType();
		headerType.setFrom(bPartnerBL.getBPartnerName(invoiceToExport.getBiller().getId()));

		final Envelope envelope = YB_INVOICE_OBJECT_FACTORY.createEnvelope();
		envelope.setHeader(headerType);
		return envelope;
	}

	private DeliveryType getDeliveryType(@NonNull final InvoiceToExport invoiceToExport)
	{
		final DeliveryType deliveryType = YB_INVOICE_OBJECT_FACTORY.createDeliveryType();
		final OrgId orgId = OrgId.ofRepoId(bPartnerBL.getById(invoiceToExport.getBiller().getId()).getAD_OrgBP_ID());
		final PostFinanceOrgConfig postFinanceOrgConfig = postFinanceOrgConfigRepository.getByOrgId(orgId);

		final String billerId = postFinanceOrgConfig.getBillerId();
		try
		{
			deliveryType.setBillerID(Long.parseLong(billerId));
		}
		catch(final NumberFormatException nfe)
		{
			throw new PostFinanceExportException("Sender eBillId should be a number, but got " + billerId);
		}

		final Optional<PostFinanceBPartnerConfig> postFinanceBPartnerConfigOptional = postFinanceBPartnerConfigRepository.getByBPartnerId(invoiceToExport.getRecipient().getId());
		final String eBillAccountID;
		if(postFinanceBPartnerConfigOptional.isPresent())
		{
			eBillAccountID = postFinanceBPartnerConfigOptional.get().getReceiverEBillId();
		}
		else if(postFinanceOrgConfig.isUsePaperBill())
		{
			eBillAccountID = YB_INVOICE_PAPER_BILL_ID;
		}
		else
		{
			throw new PostFinanceExportException("BillPartner has no BillerId set and PaperBill isn't active for this organisation");
		}

		deliveryType.setDeliveryDate(toXMLCalendar(invoiceToExport.getInvoiceDate()));

		deliveryType.setTransactionID(getTransactionId(invoiceToExport));
		try
		{
			deliveryType.setEBillAccountID(Long.parseLong(eBillAccountID));
		}
		catch(final NumberFormatException nfe)
		{
			throw new PostFinanceExportException("Receiver eBillId should be a number, but got " + eBillAccountID);
		}

		deliveryType.setBillDetailsType(YB_INVOICE_BILL_DETAILS_TYPE_PDF_APPENDIX);


		return deliveryType;
	}

	public String getTransactionId(@NonNull final InvoiceToExport invoiceToExport)
	{
		final String docSubType = invoiceToExport.getDocBaseAndSubType().getDocSubType();
		return invoiceToExport.getId().getRepoId()
				+ invoiceToExport.getDocBaseAndSubType().getDocBaseType().getCode()
				+ (EmptyUtil.isBlank(docSubType) ? "" : docSubType)
				+ invoiceToExport.getDocumentNumber();
	}

	public String getTransactionId(@NonNull final DunningToExport dunningToExport)
	{
		return dunningToExport.getId().getRepoId()
				+ DocBaseType.DunningDoc.getCode()
				+ dunningToExport.getDocumentNumber();
	}

	private BillType getBillType(@NonNull final InvoiceToExport invoiceToExport)
	{

		final BillType.Header header = YB_INVOICE_OBJECT_FACTORY.createBillTypeHeader();

		header.setDocumentType(BILL.toString());
		header.setDocumentID(getTransactionId(invoiceToExport));

		final XMLGregorianCalendar invoiceDate = toXMLCalendar(invoiceToExport.getInvoiceDate());
		header.setDocumentDate(invoiceDate);
		header.setSenderParty(getSenderParty(invoiceToExport));
		header.setReceiverParty(getReceiverParty(invoiceToExport));

		final AchievementDateType achievementDateType = YB_INVOICE_OBJECT_FACTORY.createAchievementDateType();
		achievementDateType.setStartDateAchievement(invoiceDate);
		achievementDateType.setEndDateAchievement(invoiceDate);
		header.setAchievementDate(achievementDateType);

		header.setCurrency(invoiceToExport.getAmount().getCurrency());

		final I_C_Invoice invoiceRecord = invoiceBL.getById(invoiceToExport.getId());
		if(EmptyUtil.isNotBlank(invoiceRecord.getPOReference()))
		{
			final Reference orderReference = YB_INVOICE_OBJECT_FACTORY.createReference();
			orderReference.setReferenceType("OrderReference");
			orderReference.setReferencePosition("0");
			orderReference.setReferenceValue(invoiceRecord.getPOReference());

			final AccountAssignmentType accountAssignmentType = YB_INVOICE_OBJECT_FACTORY.createAccountAssignmentType();
			accountAssignmentType.setOrderReference(orderReference);
			header.setAccountAssignment(accountAssignmentType);
		}

		final String adLanguageRecipient = bPartnerDAO.getById(invoiceToExport.getRecipient().getId()).getAD_Language();
		final String adLanguageBiller = bPartnerDAO.getById(invoiceToExport.getBiller().getId()).getAD_Language();
		final String languageISO = languageDAO.retrieveByAD_Language(CoalesceUtil.firstNotBlank(adLanguageRecipient, adLanguageBiller, AD_LANGUAGE_DE)).getLanguageISO();
		header.setLanguage(languageISO);

		if(!invoiceToExport.getDocBaseAndSubType().getDocBaseType().isARCreditMemo())
		{
			header.setPaymentInformation(getPaymentInformation(invoiceToExport));
		}

		final BillType billType = YB_INVOICE_OBJECT_FACTORY.createBillType();
		billType.setHeader(header);
		billType.setLineItems(getLineItems(invoiceToExport));
		billType.setSummary(getSummaryType(invoiceToExport));

		return billType;
	}


	private BillHeaderType.SenderParty getSenderParty(@NonNull final InvoiceToExport invoiceToExport)
	{
		final BillHeaderType.SenderParty senderParty = YB_INVOICE_OBJECT_FACTORY.createBillHeaderTypeSenderParty();

		senderParty.setTaxLiability(invoiceToExport.getInvoiceTaxes().isEmpty() ? "FRE" : "VAT");
		senderParty.setPartyType(YB_INVOICE_OBJECT_FACTORY.createPartyType());

		final AddressType addressType = YB_INVOICE_OBJECT_FACTORY.createAddressType();
		final BPartnerId bPartnerId = invoiceToExport.getBiller().getId();
		addressType.setCompanyName(bPartnerBL.getBPartnerName(bPartnerId));

		final IBPartnerDAO.BPartnerLocationQuery query = IBPartnerDAO.BPartnerLocationQuery.builder()
				.type(IBPartnerDAO.BPartnerLocationQuery.Type.BILL_TO)
				.bpartnerId(bPartnerId)
				.build();

		final LocationId locationId = bPartnerDAO.getLocationId(bPartnerDAO.retrieveBPartnerLocationId(query));

		final Location location = locationRepository.getByLocationId(locationId);

		if(location.getStreetAddress() == null
				|| location.getPostal() == null
				|| location.getCity() == null
				|| location.getCountryCode() == null)
		{
			throw new PostFinanceExportException("Biller Address incomplete");
		}

		addressType.setAddress1(location.getStreetAddress());
		addressType.setZIP(location.getPostal());
		addressType.setCity(location.getCity());
		addressType.setCountry(location.getCountryCode());

		final Optional<UserId> userId = bPartnerDAO.getDefaultContactId(bPartnerId);
		if(userId.isPresent())
		{
			final I_AD_User userRecord = userBL.getById(userId.get());
			if(userRecord.getEMail() != null)
			{
				addressType.setEmail(userRecord.getEMail());
			}
		}

		senderParty.getPartyType().setAddress(addressType);
		final I_C_BPartner partnerRecord = bPartnerDAO.getById(bPartnerId);
		if(EmptyUtil.isNotBlank(partnerRecord.getTaxID()))
		{
			senderParty.getPartyType().setTaxID(partnerRecord.getTaxID());
		}


		return senderParty;
	}

	private BillHeaderType.ReceiverParty getReceiverParty(@NonNull final InvoiceToExport invoiceToExport)
	{
		final AddressType addressType = YB_INVOICE_OBJECT_FACTORY.createAddressType();

		final BPartnerId bPartnerId = invoiceToExport.getRecipient().getId();
		addressType.setCompanyName(bPartnerBL.getBPartnerName(bPartnerId));

		final IBPartnerDAO.BPartnerLocationQuery query = IBPartnerDAO.BPartnerLocationQuery.builder()
				.type(IBPartnerDAO.BPartnerLocationQuery.Type.BILL_TO)
				.bpartnerId(bPartnerId)
				.build();

		final LocationId locationId = bPartnerDAO.getLocationId(bPartnerDAO.retrieveBPartnerLocationId(query));
		final Location location = locationRepository.getByLocationId(locationId);

		if(location.getStreetAddress() == null
				|| location.getPostal() == null
				|| location.getCity() == null
				|| location.getCountryCode() == null)
		{
			throw new PostFinanceExportException("Receiver Address incomplete");
		}

		addressType.setAddress1(location.getStreetAddress());
		addressType.setZIP(location.getPostal());
		addressType.setCity(location.getCity());
		addressType.setCountry(location.getCountryCode());

		final Optional<UserId> userId = bPartnerDAO.getDefaultContactId(bPartnerId);
		if(userId.isPresent())
		{
			final I_AD_User userRecord = userBL.getById(userId.get());
			if(EmptyUtil.isNotBlank(userRecord.getEMail()))
			{
				addressType.setEmail(userRecord.getEMail());
			}
			if(EmptyUtil.isNotBlank(userRecord.getLastname()))
			{
				addressType.setFamilyName(userRecord.getLastname());
			}
			if(EmptyUtil.isNotBlank(userRecord.getFirstname()))
			{
				addressType.setGivenName(userRecord.getFirstname());
			}
		}

		final PartyType partyType = YB_INVOICE_OBJECT_FACTORY.createPartyType();
		partyType.setAddress(addressType);

		final I_C_BPartner partnerRecord = bPartnerDAO.getById(bPartnerId);
		if(EmptyUtil.isNotBlank(partnerRecord.getTaxID()))
		{
			partyType.setTaxID(partnerRecord.getTaxID());
		}

		final BillHeaderType.ReceiverParty receiverParty = YB_INVOICE_OBJECT_FACTORY.createBillHeaderTypeReceiverParty();

		final OrgId orgId = OrgId.ofRepoId(bPartnerBL.getById(invoiceToExport.getBiller().getId()).getAD_OrgBP_ID());
		final PostFinanceOrgConfig postFinanceOrgConfig = postFinanceOrgConfigRepository.getByOrgId(orgId);
		final Optional<PostFinanceBPartnerConfig> postFinanceBPartnerConfigOptional = postFinanceBPartnerConfigRepository.getByBPartnerId(invoiceToExport.getRecipient().getId());
		if(postFinanceBPartnerConfigOptional.isEmpty() && postFinanceOrgConfig.isUsePaperBill())
		{
			paperBillReferencesRepository.retrievePaperBillReferences(orgId)
					.map(this::toReference)
					.forEach(reference -> partyType.getAdditionalReference().add(reference));
		}

		receiverParty.setPartyType(partyType);

		return receiverParty;
	}

	private Reference toReference(@NonNull final PaperBillReference paperBillReference)
	{
		final Reference reference = YB_INVOICE_OBJECT_FACTORY.createReference();
		reference.setReferencePosition(paperBillReference.getReferencePosition());
		reference.setReferenceType(paperBillReference.getReferenceType());
		reference.setReferenceValue(paperBillReference.getReferenceValue());

		return reference;
	}



	private BillHeaderType.PaymentInformation getPaymentInformation(@NonNull final InvoiceToExport invoiceToExport)
	{
		final BillHeaderType.PaymentInformation paymentInformation = YB_INVOICE_OBJECT_FACTORY.createBillHeaderTypePaymentInformation();
		paymentInformation.setPaymentType("IBAN");

		final I_C_Invoice invoiceRecord = invoiceBL.getById(invoiceToExport.getId());
		if(invoiceRecord.getDueDate() != null)
		{
			final GregorianCalendar dueDate = new GregorianCalendar();
			dueDate.setTime(invoiceRecord.getDueDate());
			paymentInformation.setPaymentDueDate(toXMLCalendar(dueDate));
		}

		final BPartnerId bPartnerId = invoiceToExport.getBiller().getId();
		final Optional<BankAccount> bankAccountOptional = bankAccountDAO.getDefaultESRBankAccount(bPartnerId);
		if(bankAccountOptional.isEmpty())
		{
			throw new PostFinanceExportException("Missing default bank account for OrgBPartner " + bPartnerId);
		}

		final BankAccount bankAccount = bankAccountOptional.get();
		final BillHeaderType.PaymentInformation.IBAN iban = YB_INVOICE_OBJECT_FACTORY.createBillHeaderTypePaymentInformationIBAN();
		if(bankAccount.getQR_IBAN() == null)
		{
			throw new PostFinanceExportException("Missing QR-IBAN on default bank account for OrgBPartner " + bPartnerId);
		}
		iban.setCreditorName(bPartnerBL.getBPartnerName(bPartnerId));
		iban.setIBAN(bankAccount.getQR_IBAN());

		final I_C_ReferenceNo_Type refType = referenceNoDAO.retrieveRefNoTypeByName(INVOICE_REFERENCE);
		final TableRecordReference invoiceRef = TableRecordReference.of(invoiceRecord);

		final Optional<I_C_ReferenceNo> refNo= referenceNoDAO.retrieveRefNo(invoiceRef, refType);
		if(refNo.isEmpty())
		{
			throw new PostFinanceExportException("No InvoiceReference found for invoice " + invoiceToExport.getId());
		}
		iban.setCreditorReference(refNo.get().getReferenceNo());
		paymentInformation.setIBAN(iban);
		paymentInformation.setFixAmount(invoiceRecord.isFixedInvoice() ? "Yes" : "No");

		return paymentInformation;
	}

	private BillType.LineItems getLineItems(@NonNull final InvoiceToExport invoiceToExport)
	{
		final BillType.LineItems lineItems = YB_INVOICE_OBJECT_FACTORY.createBillTypeLineItems();
		invoiceToExport.getInvoiceLines().forEach(invoiceLine -> lineItems.getLineItem().add(getLineItemType(invoiceLine)));

		return lineItems;
	}

	private LineItemType getLineItemType(@NonNull final InvoiceLine invoiceLine)
	{
		final LineItemType lineItem = YB_INVOICE_OBJECT_FACTORY.createLineItemType();
		final I_C_InvoiceLine lineRecord = invoiceBL.getLineById(invoiceLine.getId());

		lineItem.setLineItemType("NORMAL");
		lineItem.setLineItemID(String.valueOf(lineRecord.getLine()));
		lineItem.setProductDescription(productBL.getProductName(invoiceLine.getProductId()));
		lineItem.setQuantity(lineRecord.getQtyInvoiced());
		lineItem.setQuantityDescription(uomDAO.getName(UomId.ofRepoId(lineRecord.getC_UOM_ID())).translate(Env.getAD_Language()));
		lineItem.setPriceUnit(BigDecimal.ONE);
		lineItem.setPriceExclusiveTax(lineRecord.getPriceActual());
		lineItem.setAmountExclusiveTax(lineRecord.getLineNetAmt());

		final TaxDetailType taxDetailType = YB_INVOICE_OBJECT_FACTORY.createTaxDetailType();
		final Tax tax = taxBL.getTaxById(TaxId.ofRepoId(lineRecord.getC_Tax_ID()));
		taxDetailType.setRate(tax.getRate());
		taxDetailType.setAmount(lineRecord.getTaxAmt());
		taxDetailType.setBaseAmountExclusiveTax(lineRecord.getLineNetAmt());

		final TaxLineItemType taxLineItemType = YB_INVOICE_OBJECT_FACTORY.createTaxLineItemType();
		taxLineItemType.setTaxDetail(taxDetailType);
		taxLineItemType.setTotalTax(lineRecord.getTaxAmt());

		return lineItem;
	}

	public Invoice createInvoiceAndAttachments(
			@NonNull final Envelope envelope,
			@NonNull final PostFinanceYbInvoiceRequest postFinanceYbInvoiceRequest)
	{
		final byte[] data;
		try
		{
			final JAXBContext contextObj = JAXBContext.newInstance(Envelope.class);

			final Marshaller marshallerObj = contextObj.createMarshaller();
			marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			marshallerObj.marshal(envelope, outputStream);
			data = outputStream.toByteArray();
		}
		catch (final Exception e)
		{
			throw new PostFinanceExportException(e);
		}

		final Invoice invoice = B2B_SERVICE_OBJECT_FACTORY.createInvoice();
		invoice.setData(B2B_SERVICE_OBJECT_FACTORY.createInvoiceData(data));
		invoice.setFileType(XML_FILE_TYPE);
		invoice.setTransactionID(B2B_SERVICE_OBJECT_FACTORY.createInvoiceTransactionID(envelope.getBody().getDeliveryInfo().getTransactionID()));

		final AttachmentTags attachmentTags = AttachmentTags.builder()
				.tag(AttachmentTags.TAGNAME_IS_DOCUMENT, StringUtils.ofBoolean(true))
				.build();

		attachmentEntryService.createNewAttachment(ImmutableList.of(postFinanceYbInvoiceRequest.getDocOutboundLogReference(),
																	postFinanceYbInvoiceRequest.getPInstanceReference()),
												   AttachmentEntryCreateRequest.builder()
														   .type(AttachmentEntryType.Data)
														   .filename(envelope.getBody().getDeliveryInfo().getTransactionID() + "_postFinance_upload.xml")
														   .data(data)
														   .contentType(MimeType.TYPE_XML)
														   .tags(attachmentTags)
														   .build());

		return invoice;
	}

	private HeaderType getHeaderType()
	{
		final HeaderType headerType = YB_INVOICE_OBJECT_FACTORY.createHeaderType();
		headerType.setTo(BILL_SERVER);
		headerType.setUseCase(YB_INVOICE_USE_CASE_CREATE);
		headerType.setSessionID(YB_INVOICE_SESSION_ID);
		headerType.setVersion(YB_INVOICE_VERSION);
		headerType.setStatus(YB_INVOICE_STATUS);
		headerType.setSoftwareName(METASFRESH);

		return headerType;
	}

	private SummaryType getSummaryType(@NonNull final InvoiceToExport invoiceToExport)
	{

		final TaxType taxType = YB_INVOICE_OBJECT_FACTORY.createTaxType();
		invoiceToExport.getInvoiceTaxes().forEach(invoiceTax -> taxType.getTaxDetail().add(getSummaryTaxDetail(invoiceTax)));

		final I_C_Invoice invoiceRecord = invoiceBL.getById(invoiceToExport.getId());
		taxType.setTotalTax(invoiceRecord.getGrandTotal().subtract(invoiceRecord.getTotalLines()));

		final SummaryType summaryType = YB_INVOICE_OBJECT_FACTORY.createSummaryType();
		summaryType.setTax(taxType);

		if(invoiceToExport.getDocBaseAndSubType().getDocBaseType().isARCreditMemo())
		{
			summaryType.setTotalAmountExclusiveTax(invoiceRecord.getTotalLines().negate());
			summaryType.setTotalAmountInclusiveTax(invoiceRecord.getGrandTotal().negate());
			summaryType.setTotalAmountDue(invoiceRecord.getGrandTotal().negate());
		}
		else
		{
			summaryType.setTotalAmountExclusiveTax(invoiceRecord.getTotalLines());
			summaryType.setTotalAmountInclusiveTax(invoiceRecord.getGrandTotal());
			summaryType.setTotalAmountDue(invoiceRecord.getGrandTotal());
		}



		summaryType.setTotalAmountPaid(BigDecimal.ZERO);

		return summaryType;
	}

	private TaxDetailType getSummaryTaxDetail(@NonNull final InvoiceTax invoiceTax)
	{
		final TaxDetailType taxDetailType = YB_INVOICE_OBJECT_FACTORY.createTaxDetailType();
		taxDetailType.setRate(invoiceTax.getRatePercent());
		taxDetailType.setAmount(invoiceTax.getVatAmount());
		taxDetailType.setBaseAmountExclusiveTax(invoiceTax.getBaseAmount());
		return taxDetailType;
	}

	public XMLGregorianCalendar toXMLCalendar(@NonNull final GregorianCalendar gregorianCalendar)
	{
		try
		{
			return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
		}
		catch (final DatatypeConfigurationException e)
		{
			throw new PostFinanceExportException("Error converting to XMLGregorianCalendar date", e);
		}
	}
}
