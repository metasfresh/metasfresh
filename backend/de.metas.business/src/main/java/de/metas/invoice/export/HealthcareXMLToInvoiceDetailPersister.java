/*
 * #%L
 * de.metas.business
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

package de.metas.invoice.export;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.invoice.InvoiceAndLineId;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.detail.InvoiceDetailItem;
import de.metas.invoice.detail.InvoiceLineWithDetails;
import de.metas.invoice.detail.InvoiceWithDetails;
import de.metas.invoice.detail.InvoiceWithDetailsRepository;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.lang.ExternalIdsUtil;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.model.commontypes.XmlCompany;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.model.commontypes.XmlOnline;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.model.commontypes.XmlPerson;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.model.commontypes.XmlPostal;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.model.commontypes.XmlTelecom;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.XmlRequest;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.XmlBody;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.XmlService;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.XmlTiers;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.law.XmlKvg;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlBiller;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlGuarantor;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlInsurance;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlPatient;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlReferrer;
import lombok.NonNull;
import org.compiere.model.I_C_Invoice;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class HealthcareXMLToInvoiceDetailPersister
{
	public static final String LABEL_PREFIX_REFERRER = "Referrer";
	public static final String LABEL_REFERRER_ZSR = "Referrer_ZSR";
	public static final String LABEL_REFERRER_GLN = "Referrer_GLN";
	public static final String LABEL_TREATMENT_DATE_BEGIN = "Treatment_Date_Begin";
	public static final String LABEL_TREATMENT_DATE_END = "Treatment_Date_End";
	public static final String LABEL_KVG_INSURED_ID = "KVG_InsuredId";
	public static final String LABEL_PATIENT_BIRTH_DATE = "Patient_BirthDate";
	public static final String LABEL_PATIENT_SSN = "Patient_SSN";
	public static final String LABEL_PREFIX_PATIENT = "Patient";
	public static final String LABEL_PREFIX_GUARANTOR = "Guarantor";
	public static final String LABEL_PREFIX_BILLER = "Biller";
	public static final String LABEL_BILLER_ZSR = "Biller_ZSR";
	public static final String LABEL_BILLER_GLN = "Biller_GLN";
	public static final String LABEL_SERVICE_NAME = "Service_Name";
	public static final String LABEL_SERVICE_DATE = "Service_Date";
	public static final String LABEL_INSURANCE_COMPANY_NAME = "Insurance_CompanyName";
	public static final String LABEL_SUFFIX_EMAIL = "_Email";
	public static final String LABEL_SUFFIX_PHONE = "_Phone";
	public static final String LABEL_SUFFIX_GIVEN_NAME = "_GivenName";
	public static final String LABEL_SUFFIX_FAMILY_NAME = "_FamilyName";
	public static final String LABEL_SUFFIX_ZIP = "_ZIP";
	public static final String LABEL_SUFFIX_CITY = "_City";
	public static final String LABEL_SUFFIX_STREET = "_Street";

	private final InvoiceWithDetailsRepository invoiceWithDetailsRepository;

	public HealthcareXMLToInvoiceDetailPersister(@NonNull final InvoiceWithDetailsRepository invoiceWithDetailsRepository)
	{
		this.invoiceWithDetailsRepository = invoiceWithDetailsRepository;
	}

	/**
	 * Create or update {@link org.compiere.model.I_C_Invoice_Detail} records according to the given {@code xRequest}.
	 */
	public void extractInvoiceDetails(
			@NonNull final XmlRequest xRequest,
			@NonNull final I_C_Invoice invoiceRecord)
	{
		final XmlBody body = xRequest.getPayload().getBody();
		final XmlTiers tiers = body.getTiers();
		final XmlBiller biller = tiers.getBiller();

		final InvoiceId invoiceId = InvoiceId.ofRepoId(invoiceRecord.getC_Invoice_ID());
		final InvoiceWithDetails.InvoiceWithDetailsBuilder invoiceWithDetails = InvoiceWithDetails
				.builder()
				.id(invoiceId)
				.orgId(OrgId.ofRepoId(invoiceRecord.getAD_Org_ID()));
		setIfNotBlank(invoiceWithDetails, biller.getZsr(), LABEL_BILLER_ZSR);
		setIfNotBlank(invoiceWithDetails, biller.getEanParty(), LABEL_BILLER_GLN);

		setBillerDetails(biller, invoiceWithDetails, LABEL_PREFIX_BILLER);
		setGuarantorDetails(tiers.getGuarantor(), invoiceWithDetails, LABEL_PREFIX_GUARANTOR);

		final XmlPatient patient = tiers.getPatient();
		setPatientDetails(patient, invoiceWithDetails, LABEL_PREFIX_PATIENT);
		setIfNotBlank(invoiceWithDetails, patient.getSsn(), LABEL_PATIENT_SSN);
		setDateIfNotNull(invoiceWithDetails, patient.getBirthdate(), LABEL_PATIENT_BIRTH_DATE);

		final XmlKvg kvg = body.getLaw().getKvg();
		if (kvg != null)
		{
			setIfNotBlank(invoiceWithDetails, kvg.getInsuredId(), LABEL_KVG_INSURED_ID);
		}

		// referrer
		setReferrerDetailsIfNotNull(tiers.getReferrer(), invoiceWithDetails, LABEL_PREFIX_REFERRER);

		setInsuranceIfNotBlank(invoiceWithDetails, tiers.getInsurance());

		setDateIfNotNull(invoiceWithDetails, body.getTreatment().getDateBegin(), LABEL_TREATMENT_DATE_BEGIN);
		setDateIfNotNull(invoiceWithDetails, body.getTreatment().getDateEnd(), LABEL_TREATMENT_DATE_END);

		final ImmutableMap<Integer, XmlService> //
				recordId2xService = Maps.uniqueIndex(body.getServices(), XmlService::getRecordId);

		final List<I_C_InvoiceLine> lineRecords = Services.get(IInvoiceDAO.class).retrieveLines(invoiceId);
		for (final I_C_InvoiceLine lineRecord : lineRecords)
		{
			final List<String> externalIds = ExternalIdsUtil.splitExternalIds(lineRecord.getExternalIds());
			if (externalIds.size() != 1)
			{
				continue;
			}
			final int recordId = ExternalIdsUtil.extractSingleRecordId(externalIds);
			final XmlService serviceForRecordId = recordId2xService.get(recordId);
			if (serviceForRecordId == null)
			{
				continue;
			}

			final InvoiceLineWithDetails.InvoiceLineWithDetailsBuilder line = InvoiceLineWithDetails
					.builder()
					.id(InvoiceAndLineId.ofRepoId(invoiceId, lineRecord.getC_InvoiceLine_ID()));

			createItemIfNotBlank(serviceForRecordId.getName(), LABEL_SERVICE_NAME).ifPresent(line::detailItem);
			extractLocalDate(serviceForRecordId.getDateBegin(), LABEL_SERVICE_DATE).ifPresent(line::detailItem);

			invoiceWithDetails.line(line.build());
		}

		invoiceWithDetailsRepository.save(invoiceWithDetails.build());
	}

	private void setInsuranceIfNotBlank(
			@NonNull final InvoiceWithDetails.InvoiceWithDetailsBuilder invoiceWithDetails,
			@Nullable final XmlInsurance insurance)
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
		setIfNotBlank(invoiceWithDetails, company.getCompanyname(), LABEL_INSURANCE_COMPANY_NAME);
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

	private void setReferrerDetailsIfNotNull(
			@Nullable final XmlReferrer referrer,
			@NonNull final InvoiceWithDetails.InvoiceWithDetailsBuilder invoiceWithDetails,
			@NonNull final String labelPrefix)
	{
		if (referrer != null)
		{
			setPersonDetailsIfNotNull(invoiceWithDetails, labelPrefix, referrer.getPerson());
			setCompanyDetailsIfNotNull(invoiceWithDetails, labelPrefix, referrer.getCompany());
			setIfNotBlank(invoiceWithDetails, referrer.getZsr(), LABEL_REFERRER_ZSR);
			setIfNotBlank(invoiceWithDetails, referrer.getEanParty(), LABEL_REFERRER_GLN);
		}
	}

	private void setPersonDetailsIfNotNull(
			@NonNull final InvoiceWithDetails.InvoiceWithDetailsBuilder invoiceWithDetails,
			@NonNull final String labelPrefix,
			@Nullable final XmlPerson person)
	{
		if (person != null)
		{
			setPhoneIfNotNull(invoiceWithDetails, person.getTelecom(), labelPrefix + LABEL_SUFFIX_PHONE);
			setEmailIfNotNull(invoiceWithDetails, person.getOnline(), labelPrefix + LABEL_SUFFIX_EMAIL);

			setPostalIfNotNull(invoiceWithDetails, person.getPostal(), labelPrefix);

			setIfNotBlank(invoiceWithDetails, person.getSalutation(), labelPrefix + "_Salutation");
			setIfNotBlank(invoiceWithDetails, person.getTitle(), labelPrefix + "_Title");
			setIfNotBlank(invoiceWithDetails, person.getGivenname(), labelPrefix + LABEL_SUFFIX_GIVEN_NAME);
			setIfNotBlank(invoiceWithDetails, person.getFamilyname(), labelPrefix + LABEL_SUFFIX_FAMILY_NAME);
		}
	}

	private void setCompanyDetailsIfNotNull(
			@NonNull final InvoiceWithDetails.InvoiceWithDetailsBuilder invoiceWithDetails,
			@NonNull final String labelPrefix,
			@Nullable final XmlCompany billerCompany)
	{
		if (billerCompany != null)
		{
			setPhoneIfNotNull(invoiceWithDetails, billerCompany.getTelecom(), labelPrefix + LABEL_SUFFIX_PHONE);
			setEmailIfNotNull(invoiceWithDetails, billerCompany.getOnline(), labelPrefix + LABEL_SUFFIX_EMAIL);

			setPostalIfNotNull(invoiceWithDetails, billerCompany.getPostal(), labelPrefix);

			setIfNotBlank(invoiceWithDetails, billerCompany.getCompanyname(), labelPrefix + LABEL_SUFFIX_GIVEN_NAME);
			setIfNotBlank(invoiceWithDetails, billerCompany.getDepartment(), labelPrefix + LABEL_SUFFIX_FAMILY_NAME);
		}
	}

	private void setPostalIfNotNull(
			@NonNull final InvoiceWithDetails.InvoiceWithDetailsBuilder invoiceWithDetails,
			@NonNull final XmlPostal postal,
			@NonNull final String labelPrefix)
	{
		setIfNotBlank(invoiceWithDetails, postal.getZip(), labelPrefix + LABEL_SUFFIX_ZIP);
		setIfNotBlank(invoiceWithDetails, postal.getCity(), labelPrefix + LABEL_SUFFIX_CITY);
		setIfNotBlank(invoiceWithDetails, postal.getStreet(), labelPrefix + LABEL_SUFFIX_STREET);
	}

	private void setPhoneIfNotNull(
			@NonNull final InvoiceWithDetails.InvoiceWithDetailsBuilder invoiceWithDetails,
			@Nullable final XmlTelecom telecom,
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

	private void setEmailIfNotNull(
			@NonNull final InvoiceWithDetails.InvoiceWithDetailsBuilder invoiceWithDetails,
			@Nullable final XmlOnline online,
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
