package de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440;

import de.metas.common.util.CoalesceUtil;
import de.metas.util.Check;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.commons.XmlMode;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.BalanceType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.BfsDataType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.BillerAddressType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.BodyType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.CaseDetailType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.CompanyType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.CreditType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.DocumentType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.DocumentsType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.EmployerAddressType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.Esr5Type;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.Esr9Type;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.EsrAddressType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.EsrRedType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.GarantType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.GrouperDataType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.GuarantorAddressType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.InsuranceAddressType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.InvoiceType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.IvgLawType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.KvgLawType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.MvgLawType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.ObjectFactory;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.OnlineAddressType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.OrgLawType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.PatientAddressType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.PatientAddressType.Card;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.PayantType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.PayloadType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.PersonType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.PostalAddressType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.ProcessingType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.ProcessingType.Demand;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.PrologType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.ProviderAddressType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.RecordDRGType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.RecordDrugType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.RecordDrugType.XtraDrug;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.RecordLabType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.RecordMigelType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.RecordOtherType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.RecordParamedType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.RecordServiceType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.RecordTarmedType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.ReferrerAddressType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.ReminderType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.RequestType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.ServicesType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.SoftwareType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.TelecomAddressType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.TransportType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.TransportType.Via;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.TreatmentType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.UvgLawType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.ValidationResultType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.ValidationType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.ValidationsType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.VatRateType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.VatType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.VvgLawType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.XtraAmbulatoryType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.XtraHospitalType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.XtraStationaryType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.ZipType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.model.commontypes.XmlCompany;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.model.commontypes.XmlInvoice;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.model.commontypes.XmlOnline;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.model.commontypes.XmlPerson;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.model.commontypes.XmlPostal;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.model.commontypes.XmlTelecom;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.XmlPayload;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.XmlProcessing;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.XmlRequest;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.XmlBody;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.XmlCredit;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.XmlReminder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.XmlBalance;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.XmlDocument;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.XmlEsr;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.XmlLaw;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.XmlProlog;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.XmlService;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.XmlTiers;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.XmlTreatment;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.esr.XmlAddress;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.esr.XmlEsr5;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.esr.XmlEsr9;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.esr.XmlEsrRed;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.law.XmlIvg;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.law.XmlKvg;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.law.XmlMvg;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.law.XmlOrg;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.law.XmlUvg;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.law.XmlVvg;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.prolog.XmlSoftware;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.service.XmlRecordDrg;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.service.XmlRecordDrug;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.service.XmlRecordLab;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.service.XmlRecordMigel;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.service.XmlRecordOther;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.service.XmlRecordParamed;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.service.XmlRecordService;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.service.XmlServiceEx;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.service.XmlXtraDrug;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlBiller;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlEmployer;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlGuarantor;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlInsurance;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlPatient;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlPatientCard;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlProvider;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlReferrer;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.treatment.XmlBfsData;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.treatment.XmlCaseDetail;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.treatment.XmlGrouperData;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.treatment.XmlXtraAmbulatory;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.treatment.XmlXtraStationary;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.vat.XmlVat;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.vat.XmlVatRate;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.processing.XmlDemand;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.processing.XmlTransport;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.processing.XmlTransport.XmlVia;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.processing.XmlValidation;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.processing.XmlValidationResult;
import lombok.NonNull;

import javax.annotation.Nullable;
import javax.xml.bind.JAXBElement;
import java.math.BigInteger;
import java.util.List;

import static de.metas.util.Check.fail;

/*
 * #%L
 * metasfresh-healthcare.invoice_gateway.forum_datenaustausch_ch.invoice_commons
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

public class Invoice440FromCrossVersionModelTool
{
	public static final Invoice440FromCrossVersionModelTool INSTANCE = new Invoice440FromCrossVersionModelTool();

	private final ObjectFactory jaxbRequestObjectFactory = new ObjectFactory();

	private static final long VALIDATION_STATUS_OK = 0L;

	private Invoice440FromCrossVersionModelTool()
	{
	}

	public JAXBElement<RequestType> fromCrossVersionModel(@NonNull final XmlRequest xRequest)
	{
		final RequestType requestType = jaxbRequestObjectFactory.createRequestType();
		requestType.setModus(asModeAsString(xRequest.getModus()));

		requestType.setLanguage(xRequest.getLanguage());
		requestType.setValidationStatus(VALIDATION_STATUS_OK); // we create correctly encoded invoices

		requestType.setProcessing(createProcessingType(xRequest.getProcessing()));

		requestType.setPayload(createPayloadType(xRequest.getPayload()));

		return jaxbRequestObjectFactory.createRequest(requestType);
	}

	private String asModeAsString(@NonNull final XmlMode mode)
	{
		switch (mode)
		{
			// the strings are hardcoded in the generated jaxb code.
			case PRODUCTION:
				return "production";
			case TEST:
				return "test";
			default:
				fail("Unexpected XmlMode={}", mode);
				return null;
		}
	}

	private ProcessingType createProcessingType(@NonNull final XmlProcessing processing)
	{
		final ProcessingType processingType = jaxbRequestObjectFactory.createProcessingType();

		processingType.setPrintAtIntermediate(processing.getPrintAtIntermediate());
		processingType.setPrintPatientCopy(processing.getPrintCustomerCopy());

		processingType.setTransport(createTransportType(processing.getTransport()));

		processingType.setValidations(createValidationsType(processing.getValidations()));

		processingType.setDemand(createDemand(processing.getDemand()));

		return processingType;
	}

	private TransportType createTransportType(@NonNull final XmlTransport transport)
	{
		final TransportType transportType = jaxbRequestObjectFactory.createTransportType();

		transportType.setFrom(transport.getFrom());
		transportType.setTo(transport.getTo());

		for (final XmlVia via : transport.getVias())
		{
			transportType.getVia().add(createTransportTypeVia(via));
		}
		return transportType;
	}

	private Via createTransportTypeVia(@NonNull final XmlVia via)
	{
		final Via transportTypeVia = jaxbRequestObjectFactory.createTransportTypeVia();

		transportTypeVia.setSequenceId(via.getSequenceId());
		transportTypeVia.setVia(via.getVia());

		return transportTypeVia;
	}

	private ValidationsType createValidationsType(@NonNull final List<XmlValidation> validations)
	{
		if (validations.isEmpty())
		{
			return null;
		}
		final ValidationsType validationsType = jaxbRequestObjectFactory.createValidationsType();
		for (final XmlValidation validation : validations)
		{
			validationsType.getValidation().add(createValidationType(validation));
		}

		return validationsType;
	}

	private ValidationType createValidationType(@NonNull final XmlValidation validation)
	{
		final ValidationType validationType = jaxbRequestObjectFactory.createValidationType();

		validationType.setOriginator(validation.getOriginator());
		validationType.setRemark(validation.getRemark());
		validationType.setStatus(validation.getStatus());

		for (final XmlValidationResult validationResult : validation.getValidationresults())
		{
			validationType.getValidationResult().add(createValidationResultType(validationResult));
		}

		return validationType;
	}

	private ValidationResultType createValidationResultType(@NonNull final XmlValidationResult validationResult)
	{
		final ValidationResultType validationResultType = jaxbRequestObjectFactory.createValidationResultType();

		validationResultType.setRemark(validationResult.getRemark());
		validationResultType.setRecordId(validationResult.getRecordId());
		validationResultType.setCostUnit(validationResult.getCostUnit());
		validationResultType.setStatus(validationResult.getStatus());

		return validationResultType;
	}

	private Demand createDemand(@Nullable final XmlDemand demand)
	{
		if (demand == null)
		{
			return null;
		}

		final Demand processingTypeDemand = jaxbRequestObjectFactory.createProcessingTypeDemand();

		processingTypeDemand.setTcDemandId(demand.getTcDemandId());
		processingTypeDemand.setTcToken(demand.getTcToken());
		processingTypeDemand.setInsuranceDemandDate(demand.getInsuranceDemandDate());
		processingTypeDemand.setInsuranceDemandId(demand.getInsuranceDemandId());

		return processingTypeDemand;
	}

	private PayloadType createPayloadType(@NonNull final XmlPayload payload)
	{
		final PayloadType payloadType = jaxbRequestObjectFactory.createPayloadType();

		payloadType.setType(payload.getType());
		payloadType.setStorno(payload.getStorno());
		payloadType.setCopy(payload.getCopy());
		payloadType.setCreditAdvice(payload.getCreditAdvice());

		payloadType.setCredit(createCreditType(payload.getCredit()));

		payloadType.setInvoice(createInvoiceType(payload.getInvoice()));

		payloadType.setReminder(createReminderType(payload.getReminder()));

		payloadType.setBody(createBodyType(payload.getBody()));

		return payloadType;
	}

	private CreditType createCreditType(@Nullable final XmlCredit credit)
	{
		if (credit == null)
		{
			return null;
		}
		final CreditType creditType = jaxbRequestObjectFactory.createCreditType();

		creditType.setRequestDate(credit.getRequestDate());
		creditType.setRequestTimestamp(credit.getRequestTimestamp());
		creditType.setRequestId(credit.getRequestId());

		return creditType;
	}

	private InvoiceType createInvoiceType(@NonNull final XmlInvoice invoice)
	{
		final InvoiceType invoiceType = jaxbRequestObjectFactory.createInvoiceType();

		invoiceType.setRequestTimestamp(invoice.getRequestTimestamp());
		invoiceType.setRequestDate(invoice.getRequestDate());
		invoiceType.setRequestId(invoice.getRequestId());

		return invoiceType;
	}

	private ReminderType createReminderType(@Nullable final XmlReminder reminder)
	{
		if (reminder == null)
		{
			return null;
		}

		final ReminderType reminderType = jaxbRequestObjectFactory.createReminderType();
		reminderType.setRequestTimestamp(reminder.getRequestTimestamp());
		reminderType.setRequestDate(reminder.getRequestDate());
		reminderType.setRequestId(reminder.getRequestId());

		reminderType.setReminderLevel(reminder.getReminderLevel());
		reminderType.setReminderText(reminder.getReminderText());

		return reminderType;
	}

	private BodyType createBodyType(@NonNull final XmlBody body)
	{
		final BodyType bodyType = jaxbRequestObjectFactory.createBodyType();

		bodyType.setPlace(body.getPlace());
		bodyType.setRole(body.getRole());
		bodyType.setRoleTitle(body.getRoleTitle());

		bodyType.setProlog(createPrologType(body.getProlog()));

		bodyType.setRemark(body.getRemark());

		bodyType.setBalance(createBalanceType(CoalesceUtil.coalesce(body.getBalance(), body.getTiers().getBalance())));

		createEsrType(bodyType, body.getEsr());

		createTiersTypes(bodyType, body.getTiers());

		createLawTypes(bodyType, body.getLaw());

		bodyType.setTreatment(createTreatmentType(body.getTreatment()));

		bodyType.setServices(createServicesType(body.getServices()));

		bodyType.setDocuments(createDocumentsType(body.getDocuments()));

		return bodyType;
	}

	private PrologType createPrologType(@NonNull final XmlProlog prolog)
	{
		final PrologType prologType = jaxbRequestObjectFactory.createPrologType();

		prologType.setPackage(createSoftwareType(prolog.getPkg()));
		prologType.setGenerator(createSoftwareType(prolog.getGenerator()));

		return prologType;
	}

	private SoftwareType createSoftwareType(@NonNull final XmlSoftware software)
	{
		final SoftwareType softwareType = jaxbRequestObjectFactory.createSoftwareType();

		softwareType.setName(software.getName());
		softwareType.setCopyright(software.getCopyright());
		softwareType.setDescription(software.getDescription());
		softwareType.setVersion(software.getVersion());
		softwareType.setId(software.getId());

		return softwareType;
	}

	private BalanceType createBalanceType(@NonNull final XmlBalance balance)
	{
		final BalanceType balanceType = jaxbRequestObjectFactory.createBalanceType();

		balanceType.setCurrency(balance.getCurrency());
		balanceType.setAmount(balance.getAmount());
		balanceType.setAmountReminder(balance.getAmountReminder());
		balanceType.setAmountPrepaid(balance.getAmountPrepaid());
		balanceType.setAmountDue(balance.getAmountDue());
		balanceType.setAmountObligations(balance.getAmountObligations());

		balanceType.setVat(createVatType(balance.getVat()));

		return balanceType;
	}

	private VatType createVatType(@NonNull final XmlVat vat)
	{
		final VatType vatType = jaxbRequestObjectFactory.createVatType();

		vatType.setVatNumber(vat.getVatNumber());
		vatType.setVat(vat.getVat());

		for (final XmlVatRate vatRate : vat.getVatRates())
		{
			vatType.getVatRate().add(createVatRateType(vatRate));
		}

		return vatType;
	}

	private VatRateType createVatRateType(@NonNull final XmlVatRate vatRate)
	{
		final VatRateType vatRateType = jaxbRequestObjectFactory.createVatRateType();
		vatRateType.setAmount(vatRate.getAmount());
		vatRateType.setVatRate(vatRate.getVatRate());
		vatRateType.setVat(vatRate.getVat());

		return vatRateType;
	}

	private void createEsrType(
			@NonNull final BodyType bodyType,
			@NonNull final XmlEsr esr)
	{
		if (esr instanceof XmlEsr5)
		{
			bodyType.setEsr5(createEsr5Type((XmlEsr5)esr));
		}
		else if (esr instanceof XmlEsr9)
		{
			bodyType.setEsr9(createEsr9Type((XmlEsr9)esr));
		}
		else if (esr instanceof XmlEsrRed)
		{
			bodyType.setEsrRed(createEsrRedType((XmlEsrRed)esr));
		}
		else
		{
			Check.fail("Unexpected esr type={}; esr={}", esr.getClass(), esr);
		}
	}

	private Esr5Type createEsr5Type(@NonNull final XmlEsr5 esr5)
	{
		final Esr5Type esr5Type = jaxbRequestObjectFactory.createEsr5Type();

		esr5Type.setType(esr5.getType());

		esr5Type.setParticipantNumber(esr5.getParticipantNumber());
		esr5Type.setReferenceNumber(esr5.getReferenceNumber());
		esr5Type.setCodingLine(esr5.getCodingLine());

		esr5Type.setBank(createEsrAddressType(esr5.getBank()));

		return esr5Type;
	}

	private Esr9Type createEsr9Type(@NonNull final XmlEsr9 esr9)
	{
		final Esr9Type esr9Type = jaxbRequestObjectFactory.createEsr9Type();

		esr9Type.setType(esr9.getType());

		esr9Type.setParticipantNumber(esr9.getParticipantNumber());
		esr9Type.setReferenceNumber(esr9.getReferenceNumber());
		esr9Type.setCodingLine(esr9.getCodingLine());

		esr9Type.setBank(createEsrAddressType(esr9.getBank()));

		return esr9Type;
	}

	private EsrRedType createEsrRedType(@NonNull final XmlEsrRed esrRed)
	{
		final EsrRedType esrRedType = jaxbRequestObjectFactory.createEsrRedType();

		esrRedType.setPaymentTo(esrRed.getPaymentTo());
		esrRedType.setEsrAttributes(esrRed.getEsrAttributes());

		esrRedType.setPostAccount(esrRed.getPostAccount());
		esrRedType.setIban(esrRed.getIban());
		esrRedType.setReferenceNumber(esrRed.getReferenceNumber());
		esrRedType.setCodingLine1(esrRed.getCodingLine1());
		esrRedType.setCodingLine2(esrRed.getCodingLine2());

		esrRedType.setBank(createEsrAddressType(esrRed.getBank()));

		return esrRedType;
	}

	private EsrAddressType createEsrAddressType(@Nullable final XmlAddress bank)
	{
		if (bank == null)
		{
			return null; // we are dealing with a VESR transaction
		}

		final boolean hasCompanyAddress = !Check.isEmpty(bank.getCompany());
		final boolean hasPersonAddress = !Check.isEmpty(bank.getPerson());

		final EsrAddressType esrAddressType = jaxbRequestObjectFactory.createEsrAddressType();
		if (hasCompanyAddress)
		{
			esrAddressType.setCompany(createCompanyType(bank.getCompany()));
		}
		if (hasPersonAddress)
		{
			esrAddressType.setPerson(createPersonType(bank.getPerson()));
		}
		return esrAddressType;
	}

	private void createTiersTypes(
			final BodyType bodyType,
			final XmlTiers tiers)
	{
		switch (tiers.getType())
		{
			case GARANT:
				bodyType.setTiersGarant(createGarantType(tiers));
				break;
			case PAYANT:
				bodyType.setTiersPayant(createPayantType(tiers));
				break;
			default:
				Check.fail("Unexpected triers={}; tiers={}", tiers.getType(), tiers);
				break;
		}
	}

	private GarantType createGarantType(@NonNull final XmlTiers tiers)
	{
		final GarantType garantType = jaxbRequestObjectFactory.createGarantType();

		garantType.setPaymentPeriod(tiers.getPaymentPeriod());
		garantType.setBiller(createBillerAddressType(tiers.getBiller()));
		garantType.setProvider(createProviderAddressType(tiers.getProvider()));
		garantType.setInsurance(createInsuranceAddresType(tiers.getInsurance()));
		garantType.setPatient(createPatientAddressType(tiers.getPatient()));
		garantType.setInsured(createPatientAddressTypeOrNull(tiers.getInsured()));
		garantType.setGuarantor(createGuarantorAddressType(tiers.getGuarantor()));
		garantType.setReferrer(createReferrerAddressType(tiers.getReferrer()));
		garantType.setEmployer(createEmployerAddressType(tiers.getEmployer()));

		return garantType;
	}

	private PayantType createPayantType(@NonNull final XmlTiers tiers)
	{
		final PayantType payantType = jaxbRequestObjectFactory.createPayantType();

		payantType.setPaymentPeriod(tiers.getPaymentPeriod());
		payantType.setBiller(createBillerAddressType(tiers.getBiller()));
		payantType.setProvider(createProviderAddressType(tiers.getProvider()));
		payantType.setInsurance(createInsuranceAddresType(tiers.getInsurance()));
		payantType.setPatient(createPatientAddressType(tiers.getPatient()));
		payantType.setInsured(createPatientAddressTypeOrNull(tiers.getInsured()));
		payantType.setGuarantor(createGuarantorAddressType(tiers.getGuarantor()));
		payantType.setReferrer(createReferrerAddressType(tiers.getReferrer()));
		payantType.setEmployer(createEmployerAddressType(tiers.getEmployer()));

		return payantType;
	}

	private BillerAddressType createBillerAddressType(@NonNull final XmlBiller biller)
	{
		final BillerAddressType billerAddressType = jaxbRequestObjectFactory.createBillerAddressType();

		billerAddressType.setEanParty(biller.getEanParty());
		billerAddressType.setSpecialty(biller.getSpecialty());
		billerAddressType.setUidNumber(biller.getUidNumber());
		billerAddressType.setZsr(biller.getZsr());

		billerAddressType.setCompany(createCompanyType(biller.getCompany()));
		billerAddressType.setPerson(createPersonType(biller.getPerson()));

		return billerAddressType;
	}

	private ProviderAddressType createProviderAddressType(@NonNull final XmlProvider tiersProvider)
	{
		final ProviderAddressType providerAddressType = jaxbRequestObjectFactory.createProviderAddressType();

		providerAddressType.setEanParty(tiersProvider.getEanParty());
		providerAddressType.setZsr(tiersProvider.getZsr());
		providerAddressType.setSpecialty(tiersProvider.getSpecialty());

		providerAddressType.setCompany(createCompanyType(tiersProvider.getCompany()));
		providerAddressType.setPerson(createPersonType(tiersProvider.getPerson()));

		return providerAddressType;
	}

	private InsuranceAddressType createInsuranceAddresType(@Nullable final XmlInsurance tiersInsurance)
	{
		if (tiersInsurance == null)
		{
			return null;
		}

		final InsuranceAddressType insuranceAddressType = jaxbRequestObjectFactory.createInsuranceAddressType();

		insuranceAddressType.setEanParty(tiersInsurance.getEanParty());

		insuranceAddressType.setCompany(createCompanyType(tiersInsurance.getCompany()));
		insuranceAddressType.setPerson(createPersonType(tiersInsurance.getPerson()));

		return insuranceAddressType;
	}

	private PatientAddressType createPatientAddressTypeOrNull(@Nullable final XmlPatient tiersInsured)
	{
		if (tiersInsured == null)
		{
			return null;
		}
		return createPatientAddressType(tiersInsured);
	}

	private PatientAddressType createPatientAddressType(@NonNull final XmlPatient tiersPatient)
	{
		final PatientAddressType patientAddressType = jaxbRequestObjectFactory.createPatientAddressType();
		patientAddressType.setBirthdate(tiersPatient.getBirthdate());
		patientAddressType.setGender(tiersPatient.getGender());
		patientAddressType.setPerson(createPersonType(tiersPatient.getPerson()));
		patientAddressType.setSsn(tiersPatient.getSsn());

		patientAddressType.setCard(createCard(tiersPatient.getCard()));

		return patientAddressType;
	}

	private Card createCard(@Nullable final XmlPatientCard patientCard)
	{
		if (patientCard == null)
		{
			return null;
		}

		final Card card = jaxbRequestObjectFactory.createPatientAddressTypeCard();
		card.setCardId(patientCard.getCardId());
		card.setExpiryDate(patientCard.getExpiryDate());
		card.setValidationDate(patientCard.getValidationDate());
		card.setValidationId(patientCard.getValidationId());
		card.setValidationServer(patientCard.getValidationServer());

		return card;
	}

	private GuarantorAddressType createGuarantorAddressType(@NonNull final XmlGuarantor tiersGuarantor)
	{
		final GuarantorAddressType guarantorAddressType = jaxbRequestObjectFactory.createGuarantorAddressType();
		guarantorAddressType.setCompany(createCompanyType(tiersGuarantor.getCompany()));
		guarantorAddressType.setPerson(createPersonType(tiersGuarantor.getPerson()));

		return guarantorAddressType;
	}

	private ReferrerAddressType createReferrerAddressType(@Nullable final XmlReferrer tiersReferrer)
	{
		if (tiersReferrer == null)
		{
			return null;
		}

		final ReferrerAddressType referrerAddressType = jaxbRequestObjectFactory.createReferrerAddressType();

		referrerAddressType.setEanParty(tiersReferrer.getEanParty());
		referrerAddressType.setZsr(tiersReferrer.getZsr());
		referrerAddressType.setSpecialty(tiersReferrer.getSpecialty());

		referrerAddressType.setCompany(createCompanyType(tiersReferrer.getCompany()));
		referrerAddressType.setPerson(createPersonType(tiersReferrer.getPerson()));

		return referrerAddressType;
	}

	private EmployerAddressType createEmployerAddressType(@Nullable final XmlEmployer tiersEmployer)
	{
		if (tiersEmployer == null)
		{
			return null;
		}

		final EmployerAddressType employerAddressType = jaxbRequestObjectFactory.createEmployerAddressType();

		employerAddressType.setEanParty(tiersEmployer.getEanParty());
		employerAddressType.setRegNumber(tiersEmployer.getRegNumber());

		employerAddressType.setCompany(createCompanyType(tiersEmployer.getCompany()));
		employerAddressType.setPerson(createPersonType(tiersEmployer.getPerson()));

		return employerAddressType;
	}

	private CompanyType createCompanyType(@Nullable final XmlCompany company)
	{
		if (company == null)
		{
			return null;
		}
		final CompanyType companyType = jaxbRequestObjectFactory.createCompanyType();

		companyType.setCompanyname(company.getCompanyname());
		companyType.setDepartment(company.getDepartment());

		companyType.setSubaddressing(company.getSubaddressing());
		companyType.setPostal(createPostalType(company.getPostal()));
		companyType.setTelecom(createTelecomAddressType(company.getTelecom()));
		companyType.setOnline(createOnlineType(company.getOnline()));

		return companyType;
	}

	private PersonType createPersonType(@Nullable final XmlPerson person)
	{
		if (person == null)
		{
			return null;
		}
		final PersonType personType = jaxbRequestObjectFactory.createPersonType();
		personType.setSalutation(person.getSalutation());
		personType.setTitle(person.getTitle());

		personType.setFamilyname(person.getFamilyname());
		personType.setGivenname(person.getGivenname());

		personType.setSubaddressing(person.getSubaddressing());
		personType.setPostal(createPostalType(person.getPostal()));
		personType.setTelecom(createTelecomAddressType(person.getTelecom()));
		personType.setOnline(createOnlineType(person.getOnline()));

		return personType;
	}

	private TelecomAddressType createTelecomAddressType(@Nullable final XmlTelecom telecom)
	{
		if (telecom == null)
		{
			return null;
		}

		final TelecomAddressType telecomAddressType = jaxbRequestObjectFactory.createTelecomAddressType();

		telecomAddressType.getFax().addAll(telecom.getFaxes());
		telecomAddressType.getPhone().addAll(telecom.getPhones());

		return telecomAddressType;
	}

	private OnlineAddressType createOnlineType(@Nullable final XmlOnline online)
	{
		if (online == null)
		{
			return null;
		}

		final OnlineAddressType onlineAddressType = jaxbRequestObjectFactory.createOnlineAddressType();

		onlineAddressType.getEmail().addAll(online.getEmails());
		onlineAddressType.getUrl().addAll(online.getUrls());

		return onlineAddressType;
	}

	private PostalAddressType createPostalType(@NonNull final XmlPostal postal)
	{
		final PostalAddressType postalAddressType = jaxbRequestObjectFactory.createPostalAddressType();

		postalAddressType.setPobox(postal.getPobox());
		postalAddressType.setCity(postal.getCity());
		postalAddressType.setStreet(postal.getStreet());
		postalAddressType.setZip(createZipType(postal));

		return postalAddressType;
	}

	private ZipType createZipType(@NonNull final XmlPostal postal)
	{
		final ZipType zipType = jaxbRequestObjectFactory.createZipType();

		zipType.setCountrycode(postal.getCountryCode());
		zipType.setStatecode(postal.getStateCode());
		zipType.setValue(postal.getZip());

		return zipType;
	}

	private void createLawTypes(
			@NonNull final BodyType bodyType,
			@NonNull final XmlLaw law)
	{
		bodyType.setKvg(createKvgLawType(law.getKvg()));
		bodyType.setVvg(createVvgLawType(law.getVvg()));
		bodyType.setUvg(createUvgLawType(law.getUvg()));
		bodyType.setIvg(createIvgLawType(law.getIvg()));
		bodyType.setMvg(createMvgLawType(law.getMvg()));
		bodyType.setOrg(createOrgLawType(law.getOrg()));
	}

	private KvgLawType createKvgLawType(@Nullable final XmlKvg kvg)
	{
		if (kvg == null)
		{
			return null;
		}
		final KvgLawType kvgLawType = jaxbRequestObjectFactory.createKvgLawType();

		kvgLawType.setInsuredId(kvg.getInsuredId());
		kvgLawType.setCaseId(kvg.getCaseId());
		kvgLawType.setCaseDate(kvg.getCaseDate());
		kvgLawType.setContractNumber(kvg.getContractNumber());

		return kvgLawType;
	}

	private VvgLawType createVvgLawType(@Nullable final XmlVvg vvg)
	{
		if (vvg == null)
		{
			return null;
		}
		final VvgLawType vvgLawType = jaxbRequestObjectFactory.createVvgLawType();

		vvgLawType.setInsuredId(vvg.getInsuredId());
		vvgLawType.setCaseId(vvg.getCaseId());
		vvgLawType.setCaseDate(vvg.getCaseDate());
		vvgLawType.setContractNumber(vvg.getContractNumber());

		return vvgLawType;
	}

	private UvgLawType createUvgLawType(@Nullable final XmlUvg uvg)
	{
		if (uvg == null)
		{
			return null;
		}
		final UvgLawType uvgLawType = jaxbRequestObjectFactory.createUvgLawType();

		uvgLawType.setInsuredId(uvg.getInsuredId());
		uvgLawType.setCaseId(uvg.getCaseId());
		uvgLawType.setCaseDate(uvg.getCaseDate());
		uvgLawType.setContractNumber(uvg.getContractNumber());
		uvgLawType.setSsn(uvg.getSsn());

		return uvgLawType;
	}

	private IvgLawType createIvgLawType(@Nullable final XmlIvg ivg)
	{
		if (ivg == null)
		{
			return null;
		}
		final IvgLawType ivgLawType = jaxbRequestObjectFactory.createIvgLawType();

		ivgLawType.setCaseId(ivg.getCaseId());
		ivgLawType.setCaseDate(ivg.getCaseDate());
		ivgLawType.setContractNumber(ivg.getContractNumber());
		ivgLawType.setSsn(ivg.getSsn());
		ivgLawType.setNif(ivg.getNif());

		return ivgLawType;
	}

	private MvgLawType createMvgLawType(@Nullable final XmlMvg mvg)
	{
		if (mvg == null)
		{
			return null;
		}
		final MvgLawType mvgLawType = jaxbRequestObjectFactory.createMvgLawType();

		mvgLawType.setInsuredId(mvg.getInsuredId());
		mvgLawType.setCaseId(mvg.getCaseId());
		mvgLawType.setCaseDate(mvg.getCaseDate());
		mvgLawType.setContractNumber(mvg.getContractNumber());
		mvgLawType.setSsn(mvg.getSsn());

		return mvgLawType;
	}

	private OrgLawType createOrgLawType(@Nullable final XmlOrg org)
	{
		if (org == null)
		{
			return null;
		}
		final OrgLawType orgLawType = jaxbRequestObjectFactory.createOrgLawType();

		orgLawType.setInsuredId(org.getInsuredId());
		orgLawType.setCaseId(org.getCaseId());
		orgLawType.setCaseDate(org.getCaseDate());
		orgLawType.setContractNumber(org.getContractNumber());

		return orgLawType;
	}

	private TreatmentType createTreatmentType(@NonNull final XmlTreatment treatment)
	{
		final TreatmentType treatmentType = jaxbRequestObjectFactory.createTreatmentType();
		treatmentType.setDateBegin(treatment.getDateBegin());
		treatmentType.setDateEnd(treatment.getDateEnd());
		treatmentType.setCanton(treatment.getCanton());
		treatmentType.setReason(treatment.getReason());
		treatmentType.setApid(treatment.getApid());
		treatmentType.setAcid(treatment.getAcid());

		treatmentType.setXtraHospital(createXtraHospitalType(treatment));

		return treatmentType;
	}

	private XtraHospitalType createXtraHospitalType(@NonNull final XmlTreatment treatment)
	{
		if (treatment.getXtraAmbulatory() == null && treatment.getXtraStationary() == null)
		{
			return null;
		}
		final XtraHospitalType xtraHospitalType = jaxbRequestObjectFactory.createXtraHospitalType();

		xtraHospitalType.setAmbulatory(createXtraAmbulatoryType(treatment.getXtraAmbulatory()));
		xtraHospitalType.setStationary(createXtraStationaryType(treatment.getXtraStationary()));

		return xtraHospitalType;
	}

	private XtraAmbulatoryType createXtraAmbulatoryType(@Nullable final XmlXtraAmbulatory xtraAmbulatory)
	{
		if (xtraAmbulatory == null)
		{
			return null;
		}
		final XtraAmbulatoryType xtraAmbulatoryType = jaxbRequestObjectFactory.createXtraAmbulatoryType();

		xtraAmbulatoryType.setHospitalizationType(xtraAmbulatory.getHospitalizationType());
		xtraAmbulatoryType.setHospitalizationMode(xtraAmbulatory.getHospitalizationMode());
		xtraAmbulatoryType.setClazz(xtraAmbulatory.getClazz());
		xtraAmbulatoryType.setSectionMajor(xtraAmbulatory.getSectionMajor());

		return xtraAmbulatoryType;
	}

	private XtraStationaryType createXtraStationaryType(@Nullable final XmlXtraStationary xtraStationary)
	{
		if (xtraStationary == null)
		{
			return null;
		}
		final XtraStationaryType xtraStationaryType = jaxbRequestObjectFactory.createXtraStationaryType();

		xtraStationaryType.setHospitalizationDate(xtraStationary.getHospitalizationDate());
		xtraStationaryType.setTreatmentDays(xtraStationary.getTreatmentDays());
		xtraStationaryType.setHospitalizationType(xtraStationary.getHospitalizationType());
		xtraStationaryType.setHospitalizationMode(xtraStationary.getHospitalizationMode());
		xtraStationaryType.setClazz(xtraStationary.getClazz());
		xtraStationaryType.setSectionMajor(xtraStationary.getSectionMajor());
		xtraStationaryType.setHasExpenseLoading(xtraStationary.getHasExpenseLoading());
		xtraStationaryType.setDoCostAssessment(xtraStationary.getDoCostAssessment());

		xtraStationaryType.setAdmissionType(createGrouperDataType(xtraStationary.getAdmissionType()));
		xtraStationaryType.setDischargeType(createGrouperDataType(xtraStationary.getDischargeType()));
		xtraStationaryType.setProviderType(createGrouperDataType(xtraStationary.getProviderType()));

		xtraStationaryType.setBfsResidenceBeforeAdmission(createBfsDataType(xtraStationary.getBfsResidenceBeforeAdmission()));
		xtraStationaryType.setBfsAdmissionType(createBfsDataType(xtraStationary.getBfsAdmissionType()));
		xtraStationaryType.setBfsDecisionForDischarge(createBfsDataType(xtraStationary.getBfsDecisionForDischarge()));
		xtraStationaryType.setBfsResidenceAfterDischarge(createBfsDataType(xtraStationary.getBfsResidenceAfterDischarge()));

		for (final XmlCaseDetail caseDetail : xtraStationary.getCaseDetails())
		{
			xtraStationaryType.getCaseDetail().add(createcaseDetailType(caseDetail));
		}

		return xtraStationaryType;
	}

	private GrouperDataType createGrouperDataType(@NonNull final XmlGrouperData grouperData)
	{
		final GrouperDataType grouperDataType = jaxbRequestObjectFactory.createGrouperDataType();

		grouperDataType.setName(grouperData.getName());
		grouperDataType.setNumber(grouperData.getNumber());

		return grouperDataType;
	}

	private BfsDataType createBfsDataType(@NonNull final XmlBfsData bfsData)
	{
		final BfsDataType bfsDataType = jaxbRequestObjectFactory.createBfsDataType();

		bfsDataType.setCode(bfsData.getCode());
		bfsDataType.setName(bfsData.getName());

		return bfsDataType;
	}

	private CaseDetailType createcaseDetailType(@NonNull final XmlCaseDetail caseDetail)
	{
		final CaseDetailType caseDetailType = jaxbRequestObjectFactory.createCaseDetailType();

		caseDetailType.setRecordId(caseDetail.getRecordId());
		caseDetailType.setTariffType(caseDetail.getTariffType());
		caseDetailType.setCode(caseDetail.getCode());
		caseDetailType.setDateBegin(caseDetail.getDateBegin());
		caseDetailType.setDateEnd(caseDetail.getDateEnd());
		caseDetailType.setAcid(caseDetail.getAcid());

		return caseDetailType;
	}

	private ServicesType createServicesType(@NonNull final List<XmlService> services)
	{
		final ServicesType servicesType = jaxbRequestObjectFactory.createServicesType();

		for (final XmlService service : services)
		{
			final Object serviceType;
			if (service instanceof XmlServiceEx)
			{
				serviceType = createRecordTarmedType((XmlServiceEx)service);
			}
			else if (service instanceof XmlRecordDrg)
			{
				serviceType = createRecordDRGType((XmlRecordDrg)service);
			}
			else if (service instanceof XmlRecordLab)
			{
				serviceType = createRecordLabType((XmlRecordLab)service);
			}
			else if (service instanceof XmlRecordMigel)
			{
				serviceType = createRecordMigelType((XmlRecordMigel)service);
			}
			else if (service instanceof XmlRecordParamed)
			{
				serviceType = createRecordParamedType((XmlRecordParamed)service);
			}
			else if (service instanceof XmlRecordDrug)
			{
				serviceType = createRecordDrugType((XmlRecordDrug)service);
			}
			else if (service instanceof XmlRecordOther)
			{
				serviceType = createRecordOtherType((XmlRecordOther)service);
			}
			else
			{
				serviceType = null;
				Check.fail("Unexpected service type={}; service={}", service.getClass(), service);
			}
			servicesType.getRecordTarmedOrRecordDrgOrRecordLab().add(serviceType);
		}
		return servicesType;
	}

	private RecordTarmedType createRecordTarmedType(@NonNull final XmlServiceEx xRecordTarmed)
	{
		final RecordTarmedType recordTarmedType = jaxbRequestObjectFactory.createRecordTarmedType();

		recordTarmedType.setRecordId(xRecordTarmed.getRecordId());
		recordTarmedType.setTariffType(xRecordTarmed.getTariffType());
		recordTarmedType.setCode(xRecordTarmed.getCode());
		recordTarmedType.setRefCode(xRecordTarmed.getRefCode());
		recordTarmedType.setName(xRecordTarmed.getName());
		recordTarmedType.setSession(xRecordTarmed.getSession());
		recordTarmedType.setQuantity(xRecordTarmed.getQuantity());
		recordTarmedType.setDateBegin(xRecordTarmed.getDateBegin());
		recordTarmedType.setDateEnd(xRecordTarmed.getDateEnd());
		recordTarmedType.setProviderId(xRecordTarmed.getProviderId());
		recordTarmedType.setResponsibleId(xRecordTarmed.getResponsibleId());
		recordTarmedType.setBillingRole(xRecordTarmed.getBillingRole());
		recordTarmedType.setMedicalRole(xRecordTarmed.getMedicalRole());
		recordTarmedType.setBodyLocation(xRecordTarmed.getBodyLocation());
		recordTarmedType.setTreatment(xRecordTarmed.getTreatment());
		recordTarmedType.setUnitMt(xRecordTarmed.getUnitMt());
		recordTarmedType.setUnitFactorMt(xRecordTarmed.getUnitFactorMt());
		recordTarmedType.setScaleFactorMt(xRecordTarmed.getScaleFactorMt());
		recordTarmedType.setExternalFactorMt(xRecordTarmed.getExternalFactorMt());
		recordTarmedType.setAmountMt(xRecordTarmed.getAmountMt());
		recordTarmedType.setUnitTt(xRecordTarmed.getUnitTt());
		recordTarmedType.setUnitFactorTt(xRecordTarmed.getUnitFactorTt());
		recordTarmedType.setScaleFactorTt(xRecordTarmed.getScaleFactorTt());
		recordTarmedType.setExternalFactorTt(xRecordTarmed.getExternalFactorTt());
		recordTarmedType.setAmountTt(xRecordTarmed.getAmountTt());
		recordTarmedType.setAmount(xRecordTarmed.getAmount());
		recordTarmedType.setVatRate(xRecordTarmed.getVatRate());
		recordTarmedType.setValidate(xRecordTarmed.getValidate());
		recordTarmedType.setObligation(xRecordTarmed.getObligation());
		recordTarmedType.setSectionCode(xRecordTarmed.getSectionCode());
		recordTarmedType.setRemark(xRecordTarmed.getRemark());
		recordTarmedType.setServiceAttributes(xRecordTarmed.getServiceAttributes());

		return recordTarmedType;
	}

	private RecordDRGType createRecordDRGType(@NonNull final XmlRecordDrg xRecordDrg)
	{
		final RecordDRGType recordDRGType = jaxbRequestObjectFactory.createRecordDRGType();

		setServiceTypeFields(recordDRGType, xRecordDrg.getRecordService());

		recordDRGType.setTariffType(xRecordDrg.getTariffType());
		recordDRGType.setCostFraction(xRecordDrg.getCostFraction());

		return recordDRGType;
	}

	private RecordLabType createRecordLabType(@NonNull final XmlRecordLab xRecordLab)
	{
		final RecordLabType recordLabType = jaxbRequestObjectFactory.createRecordLabType();

		setServiceTypeFields(recordLabType, xRecordLab.getRecordService());

		recordLabType.setTariffType(xRecordLab.getTariffType());

		return recordLabType;
	}

	private Object createRecordMigelType(@NonNull final XmlRecordMigel xRecordMigel)
	{
		final RecordMigelType recordMigelType = jaxbRequestObjectFactory.createRecordMigelType();

		setServiceTypeFields(recordMigelType, xRecordMigel.getRecordService());

		recordMigelType.setTariffType(xRecordMigel.getTariffType());

		return recordMigelType;
	}

	private RecordParamedType createRecordParamedType(@NonNull final XmlRecordParamed xRecordParamed)
	{
		final RecordParamedType recordParamedType = jaxbRequestObjectFactory.createRecordParamedType();

		setServiceTypeFields(recordParamedType, xRecordParamed.getRecordService());

		recordParamedType.setTariffType(xRecordParamed.getTariffType());

		return recordParamedType;
	}

	private RecordDrugType createRecordDrugType(@NonNull final XmlRecordDrug xmlRecordDrug)
	{
		final RecordDrugType recordDrugType = jaxbRequestObjectFactory.createRecordDrugType();

		setServiceTypeFields(recordDrugType, xmlRecordDrug.getRecordService());

		recordDrugType.setTariffType(xmlRecordDrug.getTariffType());
		recordDrugType.setXtraDrug(createXtraDrug(xmlRecordDrug.getXtraDrug()));

		return recordDrugType;
	}

	private XtraDrug createXtraDrug(@Nullable final XmlXtraDrug xtraDrug)
	{
		if (xtraDrug == null)
		{
			return null;
		}

		final XtraDrug xtraDrugType = jaxbRequestObjectFactory.createRecordDrugTypeXtraDrug();
		xtraDrugType.setIndicated(xtraDrug.getIndicated());
		xtraDrugType.setIocmCategory(xtraDrug.getIocmCategory());
		xtraDrugType.setDelivery(xtraDrug.getDelivery());
		xtraDrugType.setRegulationAttributes(xtraDrug.getRegulationAttributes());
		xtraDrugType.setLimitation(xtraDrug.getLimitation());

		return xtraDrugType;
	}

	private RecordOtherType createRecordOtherType(@Nullable final XmlRecordOther xmlRecordOther)
	{
		final RecordOtherType recordOtherType = jaxbRequestObjectFactory.createRecordOtherType();

		setServiceTypeFields(recordOtherType, xmlRecordOther.getRecordService());

		recordOtherType.setTariffType(xmlRecordOther.getTariffType());

		return recordOtherType;
	}

	private void setServiceTypeFields(
			@NonNull final RecordServiceType recordServiceType,
			@NonNull final XmlRecordService recordService)
	{
		recordServiceType.setRecordId(recordService.getRecordId());
		recordServiceType.setCode(recordService.getCode());
		recordServiceType.setRefCode(recordService.getRefCode());
		recordServiceType.setName(recordService.getName());
		recordServiceType.setSession(recordService.getSession());
		recordServiceType.setQuantity(recordService.getQuantity());
		recordServiceType.setDateBegin(recordService.getDateBegin());
		recordServiceType.setDateEnd(recordService.getDateEnd());
		recordServiceType.setProviderId(recordService.getProviderId());
		recordServiceType.setResponsibleId(recordService.getResponsibleId());
		recordServiceType.setUnit(recordService.getUnit());
		recordServiceType.setUnitFactor(recordService.getUnitFactor());
		recordServiceType.setExternalFactor(recordService.getExternalFactor());
		recordServiceType.setAmount(recordService.getAmount());
		recordServiceType.setVatRate(recordService.getVatRate());
		recordServiceType.setValidate(recordService.getValidate());
		recordServiceType.setObligation(recordService.getObligation());
		recordServiceType.setSectionCode(recordService.getSectionCode());
		recordServiceType.setRemark(recordService.getRemark());
		recordServiceType.setServiceAttributes(recordService.getServiceAttributes());
	}

	private DocumentsType createDocumentsType(@NonNull final List<XmlDocument> documents)
	{
		if (documents.isEmpty())
		{
			return null;
		}
		final DocumentsType documentsType = jaxbRequestObjectFactory.createDocumentsType();
		documentsType.setNumber(BigInteger.valueOf(documents.size()));

		for (final XmlDocument document : documents)
		{
			documentsType.getDocument().add(createDocumentType(document));
		}
		return documentsType;
	}

	private DocumentType createDocumentType(@NonNull final XmlDocument document)
	{
		final DocumentType documentType = jaxbRequestObjectFactory.createDocumentType();

		documentType.setFilename(document.getFilename());
		documentType.setMimeType(document.getMimeType());
		documentType.setViewer(document.getViewer());

		documentType.setBase64(document.getBase64());
		documentType.setUrl(document.getUrl());

		return documentType;
	}
}
