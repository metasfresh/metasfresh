package de.metas.invoice.export;

import static de.metas.util.lang.CoalesceUtil.coalesceSuppliers;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.detail.InvoiceDetailItem;
import de.metas.invoice.detail.InvoiceLineWithDetails;
import de.metas.invoice.detail.InvoiceWithDetails;
import de.metas.invoice.detail.InvoiceWithDetailsRepository;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.invoice.service.InvoiceUtil;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.model.commontypes.XmlCompany;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.model.commontypes.XmlOnline;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.model.commontypes.XmlPerson;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.model.commontypes.XmlPostal;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.model.commontypes.XmlTelecom;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.XmlBody;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.XmlService;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.XmlTiers;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.law.XmlKvg;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlGuarantor;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlInsurance;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlReferrer;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Invoice;
import org.slf4j.Logger;
import org.slf4j.MDC.MDCCloseable;

import com.google.common.collect.ImmutableList;

import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryService;
import de.metas.attachments.AttachmentTags;
import de.metas.attachments.listener.AttachmentListener;
import de.metas.attachments.listener.AttachmentListenerConstants.ListenerWorkStatus;
import de.metas.bpartner.composite.BPartnerComposite;
import de.metas.bpartner.composite.BPartnerLocation;
import de.metas.bpartner.composite.repository.BPartnerCompositeRepository;
import de.metas.bpartner.service.BPartnerQuery;
import de.metas.invoice_gateway.spi.InvoiceExportClientFactory;
import de.metas.javaclasses.model.I_AD_JavaClass;
import de.metas.logging.LogManager;
import de.metas.logging.TableRecordMDC;
import de.metas.util.collections.CollectionUtils;
import de.metas.util.lang.ExternalId;
import de.metas.util.xml.XmlIntrospectionUtil;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.CrossVersionServiceRegistry;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.HealthcareCHHelper;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.commons.ForumDatenaustauschChConstants;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.CrossVersionRequestConverter;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.XmlRequest;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlBiller;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlPatient;
import lombok.NonNull;

import javax.annotation.Nullable;
import javax.xml.datatype.XMLGregorianCalendar;

/*
 * #%L
 * vertical-healthcare_ch.forum_datenaustausch_ch.invoice_base
 * %%
 * Copyright (C) 2020 metas GmbH
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

/**
 * Note that we use {@link BPartnerComposite} which is why this class needs to be in here as opposed being way down in the healthcare-ch modules.
 * <p>
 * Important: when renaming this class, please make sure to also update its {@link I_AD_JavaClass} record.
 */
public class HealthcareXMLAttachedToInvoiceListener implements AttachmentListener
{
	private final CrossVersionServiceRegistry crossVersionServiceRegistry = SpringContextHolder.instance.getBean(CrossVersionServiceRegistry.class);

	private final AttachmentEntryService attachmentEntryService = SpringContextHolder.instance.getBean(AttachmentEntryService.class);

	private final BPartnerCompositeRepository bpartnerCompositeRepository = SpringContextHolder.instance.getBean(BPartnerCompositeRepository.class);

	private final InvoiceWithDetailsRepository invoiceWithDetailsRepository = SpringContextHolder.instance.getBean(InvoiceWithDetailsRepository.class);

	private static final Logger logger = LogManager.getLogger(HealthcareXMLAttachedToInvoiceListener.class);

	@Override
	public ListenerWorkStatus afterRecordLinked(
			@NonNull final AttachmentEntry attachmentEntry,
			@NonNull final TableRecordReference tableRecordReference)
	{
		try (final MDCCloseable ignored = TableRecordMDC.putTableRecordReference(tableRecordReference))
		{
			if (!I_C_Invoice.Table_Name.equals(tableRecordReference.getTableName()))
			{
				logger.debug("tableRecord reference has tableName={}; -> doing nothing", tableRecordReference.getTableName());
				return ListenerWorkStatus.NOT_APPLIED;
			}

			final AttachmentTags tags = attachmentEntry.getTags();
			if (tags.hasTagSetToTrue(AttachmentTags.TAGNAME_IS_DOCUMENT))
			{
				logger.debug("given attachmentEntry with id={} (filename={}) is tagged as document (exported from original-xml + invoice). We need the orgiginal XML; -> doing nothing",
						attachmentEntry.getId(), attachmentEntry.getFilename());
				return ListenerWorkStatus.NOT_APPLIED;
			}
			if (!tags.hasTagSetToString(
					InvoiceExportClientFactory.ATTACHMENT_TAGNAME_EXPORT_PROVIDER,
					ForumDatenaustauschChConstants.INVOICE_EXPORT_PROVIDER_ID))
			{
				logger.debug("given attachmentEntry with id={} (filename={}) is not tagged as {}={}; -> doing nothing",
						attachmentEntry.getId(), attachmentEntry.getFilename(), InvoiceExportClientFactory.ATTACHMENT_TAGNAME_EXPORT_PROVIDER, ForumDatenaustauschChConstants.INVOICE_EXPORT_PROVIDER_ID);
				return ListenerWorkStatus.NOT_APPLIED;
			}

			final I_C_Invoice invoiceRecord = tableRecordReference.getModel(I_C_Invoice.class);

			final byte[] attachmentData = attachmentEntryService.retrieveData(attachmentEntry.getId());
			final String xsdName = coalesceSuppliers(
					() -> tags.getTagValueOrNull(ForumDatenaustauschChConstants.XSD_NAME),
					() -> XmlIntrospectionUtil.extractXsdValueOrNull(attachmentData));

			final CrossVersionRequestConverter requestConverter = crossVersionServiceRegistry.getRequestConverterForXsdName(xsdName);
			final XmlRequest xRequest = requestConverter.toCrossVersionRequest(new ByteArrayInputStream(attachmentData));

			extractBeneficiaryToInvoice(xRequest, invoiceRecord, attachmentEntry);

			extractInvoiceDetails(xRequest, invoiceRecord);

			return ListenerWorkStatus.SUCCESS;
		}
	}

	private void extractBeneficiaryToInvoice(
			@NonNull final XmlRequest xRequest,
			@NonNull final I_C_Invoice invoiceRecord,
			@NonNull final AttachmentEntry attachmentEntry /*needed for logging*/)
	{
		final XmlPatient patient = xRequest.getPayload().getBody().getTiers().getPatient();
		final XmlBiller biller = xRequest.getPayload().getBody().getTiers().getBiller();
		final ExternalId externalId = HealthcareCHHelper.createBPartnerExternalIdForPatient(biller.getEanParty(), patient.getSsn());
		if (externalId == null)
		{
			logger.debug("patient-XML data extracted from attachmentEntry with id={} (filename={}) has no patient-SSN or biller-EAN; -> doing nothing",
					attachmentEntry.getId(), attachmentEntry.getFilename());
		}

		final ImmutableList<BPartnerComposite> bpartners = bpartnerCompositeRepository.getByQuery(BPartnerQuery.builder().externalId(externalId).build());
		if (bpartners.isEmpty())
		{
			logger.debug("externalId={} of the patient-XML data extracted from attachmentEntry with id={} (filename={}) has no matching C_BPartner; -> doing nothing",
					externalId.getValue(), attachmentEntry.getId(), attachmentEntry.getFilename());
		}
		final BPartnerComposite bPartner = CollectionUtils.singleElement(bpartners);

		if (invoiceRecord.getBeneficiary_BPartner_ID() > 0)
		{
			logger.debug("C_Invoice with ID={} already has a Beneficiary_BPartner_ID > 0; -> doing nothing",
					invoiceRecord.getC_Invoice_ID());
		}

		final int beneficiaryRepoId = bPartner.getBpartner().getId().getRepoId();
		invoiceRecord.setBeneficiary_BPartner_ID(beneficiaryRepoId);
		logger.debug("set Beneficiary_BPartner_ID={} from the patient-XML data's SSN={} extracted from attachmentEntry with id={} (filename={})",
				beneficiaryRepoId, patient.getSsn(), attachmentEntry.getId(), attachmentEntry.getFilename());

		final BPartnerLocation location = bPartner
				.extractShipToLocation()
				.orElseGet(() -> bPartner.extractBillToLocation().orElse(null));
		if (location != null)
		{
			invoiceRecord.setBeneficiary_Location_ID(location.getId().getRepoId());
			logger.debug("set setBeneficiary_Location_ID={} from the patient-XML data's SSN={} extracted from attachmentEntry with id={} (filename={})",
					location.getId().getRepoId(), patient.getSsn(), attachmentEntry.getId(), attachmentEntry.getFilename());
		}

		saveRecord(invoiceRecord);
	}

	private void extractInvoiceDetails(
			@NonNull final XmlRequest xRequest,
			@NonNull final I_C_Invoice invoiceRecord)
	{
		final XmlBody body = xRequest.getPayload().getBody();
		final XmlTiers tiers = body.getTiers();
		final XmlBiller biller = tiers.getBiller();

		final InvoiceWithDetails.InvoiceWithDetailsBuilder invoiceWithDetails = InvoiceWithDetails
				.builder()
				.id(InvoiceId.ofRepoId(invoiceRecord.getC_Invoice_ID()));
		invoiceWithDetails
				.detailItem(InvoiceDetailItem.builder().label("Biller_ZSR").description(biller.getZsr()).build())
				.detailItem(InvoiceDetailItem.builder().label("Biller_GLN").description(biller.getEanParty()).build());

		setBillerDetails(biller, invoiceWithDetails, "Biller_");
		setGuarantorDetails(tiers.getGuarantor(), invoiceWithDetails, "Guarantor_");

		final XmlPatient patient = tiers.getPatient();
		setPatientDetails(patient, invoiceWithDetails, "Patient_");
		setIfNotBlank(invoiceWithDetails, patient.getSsn(), "Patient_SSN");
		setDateIfNotNull(invoiceWithDetails, patient.getBirthdate(), "Patient_BirthDate");

		final XmlKvg kvg = body.getLaw().getKvg();
		if (kvg != null)
		{
			setIfNotBlank(invoiceWithDetails, kvg.getInsuredId(), "KVG");
		}

		// referrer
		setReferrerDetails(tiers.getReferrer(), invoiceWithDetails, "Referrer_");
		setIfNotBlank(invoiceWithDetails, tiers.getReferrer().getZsr(), "Referrer_ZSR");
		setIfNotBlank(invoiceWithDetails, tiers.getReferrer().getEanParty(), "Referrer_GLN");

		setInsuranceIfNotBlank(invoiceWithDetails, tiers.getInsurance());

		setDateIfNotNull(invoiceWithDetails, body.getTreatment().getDateBegin(), "Treatment_Date_Begin");
		setDateIfNotNull(invoiceWithDetails, body.getTreatment().getDateEnd(), "Treatment_Date_End");

		final ImmutableMap<Integer, XmlService> //
				recordId2xService = Maps.uniqueIndex(body.getServices(), XmlService::getRecordId);

		final List<I_C_InvoiceLine> lineRecords = Services.get(IInvoiceDAO.class).retrieveLines(InvoiceId.ofRepoId(invoiceRecord.getC_Invoice_ID()));
		for (final I_C_InvoiceLine lineRecord : lineRecords)
		{
			final List<String> externalIds = InvoiceUtil.splitExternalIds(lineRecord.getExternalIds());
			if (externalIds.size() != 1)
			{
				continue;
			}
			final int recordId = InvoiceUtil.extractRecordId(externalIds);
			final XmlService serviceForRecordId = recordId2xService.get(recordId);
			if (serviceForRecordId == null)
			{
				continue;
			}

			final InvoiceLineWithDetails.InvoiceLineWithDetailsBuilder line = InvoiceLineWithDetails.builder();

			createItemIfNotBlank(serviceForRecordId.getName(), "Service_Name").ifPresent(line::detailItem);
			extractLocalDate(serviceForRecordId.getDateBegin(), "Service_Date").ifPresent(line::detailItem);
			invoiceWithDetails.line(line.build());
		}

		invoiceWithDetailsRepository.save(invoiceWithDetails.build());
	}

	private void setInsuranceIfNotBlank(
			@NonNull final InvoiceWithDetails.InvoiceWithDetailsBuilder invoiceWithDetails,
			final XmlInsurance insurance)
	{
		if (insurance == null)
		{
			return;
		}
		final XmlCompany company = insurance.getCompany();
		if (company == null)
		{
			return;
		}
		setIfNotBlank(invoiceWithDetails, company.getCompanyname(), "Insurance_CompanyName");
	}

	private void setDateIfNotNull(
			@NonNull final InvoiceWithDetails.InvoiceWithDetailsBuilder invoiceWithDetails,
			@Nullable final XMLGregorianCalendar date,
			@NonNull final String label)
	{
		extractLocalDate(date, label).ifPresent(invoiceWithDetails::detailItem);
	}

	private Optional<InvoiceDetailItem> extractLocalDate(
			@Nullable final XMLGregorianCalendar date,
			@NonNull final String label)
	{
		if (date == null)
		{
			return Optional.empty();
		}

		final LocalDate localDate = date.toGregorianCalendar().toZonedDateTime().toLocalDate();
		return Optional.of(InvoiceDetailItem.builder()
				.label(label)
				.date(localDate).build());
	}

	private void setBillerDetails(
			@NonNull final XmlBiller biller,
			@NonNull final InvoiceWithDetails.InvoiceWithDetailsBuilder invoiceWithDetails,
			@NonNull final String labelPrefix)
	{
		setPersonDetailsIfNotNull(invoiceWithDetails, labelPrefix, biller.getPerson());
		setCompanyDetailsIfNotNull(invoiceWithDetails, labelPrefix, biller.getCompany());
	}

	private void setGuarantorDetails(
			@NonNull final XmlGuarantor guarantor,
			@NonNull final InvoiceWithDetails.InvoiceWithDetailsBuilder invoiceWithDetails,
			@NonNull final String labelPrefix)
	{
		setPersonDetailsIfNotNull(invoiceWithDetails, labelPrefix, guarantor.getPerson());
		setCompanyDetailsIfNotNull(invoiceWithDetails, labelPrefix, guarantor.getCompany());
	}

	private void setPatientDetails(
			@NonNull final XmlPatient patient,
			@NonNull final InvoiceWithDetails.InvoiceWithDetailsBuilder invoiceWithDetails,
			@NonNull final String labelPrefix)
	{
		setPersonDetailsIfNotNull(invoiceWithDetails, labelPrefix, patient.getPerson());
	}

	private void setReferrerDetails(
			@NonNull final XmlReferrer referrer,
			@NonNull final InvoiceWithDetails.InvoiceWithDetailsBuilder invoiceWithDetails,
			@NonNull final String labelPrefix)
	{
		setPersonDetailsIfNotNull(invoiceWithDetails, labelPrefix, referrer.getPerson());
		setCompanyDetailsIfNotNull(invoiceWithDetails, labelPrefix, referrer.getCompany());
	}

	private void setPersonDetailsIfNotNull(
			@NonNull final InvoiceWithDetails.InvoiceWithDetailsBuilder invoiceWithDetails,
			@NonNull final String labelPrefix,
			@Nullable final XmlPerson person)
	{
		if (person != null)
		{
			setPhoneIfNotNull(invoiceWithDetails, person.getOnline(), labelPrefix + "_Email");
			setEmailIfNotNull(invoiceWithDetails, person.getTelecom(), labelPrefix + "_Phone");

			setPostalIfNotNull(invoiceWithDetails, person.getPostal(), labelPrefix);

			setIfNotBlank(invoiceWithDetails, person.getSalutation(), labelPrefix + "_Salutation");
			setIfNotBlank(invoiceWithDetails, person.getTitle(), labelPrefix + "_Title");
			setIfNotBlank(invoiceWithDetails, person.getGivenname(), labelPrefix + "_GivenName");
			setIfNotBlank(invoiceWithDetails, person.getFamilyname(), labelPrefix + "_FamilyName");
		}
	}

	private void setCompanyDetailsIfNotNull(
			@NonNull final InvoiceWithDetails.InvoiceWithDetailsBuilder invoiceWithDetails,
			@NonNull final String labelPrefix,
			@Nullable final XmlCompany billerCompany)
	{
		if (billerCompany != null)
		{
			setPhoneIfNotNull(invoiceWithDetails, billerCompany.getOnline(), labelPrefix + "_Email");
			setEmailIfNotNull(invoiceWithDetails, billerCompany.getTelecom(), labelPrefix + "_Phone");

			setPostalIfNotNull(invoiceWithDetails, billerCompany.getPostal(), labelPrefix);

			setIfNotBlank(invoiceWithDetails, billerCompany.getCompanyname(), labelPrefix + "_GivenName");
			setIfNotBlank(invoiceWithDetails, billerCompany.getDepartment(), labelPrefix + "_FamilyName");
		}
	}

	private void setPostalIfNotNull(
			@NonNull final InvoiceWithDetails.InvoiceWithDetailsBuilder invoiceWithDetails,
			@NonNull final XmlPostal postal,
			@NonNull final String labelPrefix)
	{
		setIfNotBlank(invoiceWithDetails, postal.getZip(), labelPrefix + "_ZIP");
		setIfNotBlank(invoiceWithDetails, postal.getCity(), labelPrefix + "_City");
		setIfNotBlank(invoiceWithDetails, postal.getStreet(), labelPrefix + "_Street");
	}

	private void setEmailIfNotNull(
			@NonNull final InvoiceWithDetails.InvoiceWithDetailsBuilder invoiceWithDetails,
			@NonNull final XmlTelecom telecom,
			@NonNull final String detailItemLabel)
	{
		if (telecom != null)
		{
			final List<String> phones = telecom.getPhones();
			if (phones != null && !phones.isEmpty())
			{
				setIfNotBlank(invoiceWithDetails, phones.get(0), detailItemLabel);
			}
		}
	}

	private void setPhoneIfNotNull(
			@NonNull final InvoiceWithDetails.InvoiceWithDetailsBuilder invoiceWithDetails,
			@NonNull final XmlOnline online,
			@NonNull final String detailItemLabel)
	{
		if (online != null)
		{
			final List<String> emails = online.getEmails();
			if (emails != null && !emails.isEmpty())
			{
				setIfNotBlank(invoiceWithDetails, emails.get(0), detailItemLabel);
			}
		}
	}

	private void setIfNotBlank(
			@NonNull final InvoiceWithDetails.InvoiceWithDetailsBuilder invoiceWithDetails,
			@Nullable final String description,
			@NonNull final String detailItemLabel)
	{
		createItemIfNotBlank(description, detailItemLabel).ifPresent(invoiceWithDetails::detailItem);
	}

	private Optional<InvoiceDetailItem> createItemIfNotBlank(
			@Nullable final String description,
			@NonNull final String detailItemLabel)
	{
		if (Check.isBlank(description))
		{
			return Optional.empty();
		}
		return Optional.of(InvoiceDetailItem.builder().label(detailItemLabel).description(description).build());
	}
}
