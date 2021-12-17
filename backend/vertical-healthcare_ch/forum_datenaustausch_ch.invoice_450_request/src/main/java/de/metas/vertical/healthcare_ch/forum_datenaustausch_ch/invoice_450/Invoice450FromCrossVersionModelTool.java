/*
 * #%L
 * vertical-healthcare_ch.forum_datenaustausch_ch.invoice_450.request
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

package de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.CoalesceUtil;
import de.metas.util.Check;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.commons.XmlMode;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.BalanceTGType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.BalanceTPType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.BfsDataType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.BillerAddressType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.BodyType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.CaseDetailType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.CompanyType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.CreditType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.DebitorAddressType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.DiagnosisType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.DocumentType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.DocumentsType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.EmployerAddressType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.Esr9Type;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.EsrAddressType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.EsrQRType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.EsrRedType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.GarantType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.GrouperDataType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.GuarantorAddressType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.InstructionType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.InstructionsType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.InsuranceAddressType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.InvoiceType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.IvgLawType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.KvgLawType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.MvgLawType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.ObjectFactory;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.OnlineAddressType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.OrgLawType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.PatientAddressType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.PatientAddressType.Card;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.PayantType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.PayloadType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.PersonType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.PostalAddressType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.ProcessingType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.ProcessingType.Demand;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.PrologType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.ProviderAddressType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.ReferrerAddressType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.ReminderType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.RequestType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.ServiceExType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.ServiceType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.ServicesType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.SoftwareType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.TelecomAddressType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.TransportType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.TransportType.Via;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.TreatmentType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.UvgLawType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.VatRateType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.VatType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.VvgLawType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.XtraAmbulatoryType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.XtraDRGType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.XtraDrugType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.XtraHospitalType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.XtraStationaryType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.ZipType;
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
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.esr.XmlEsr9;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.esr.XmlEsrQR;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.esr.XmlEsrRed;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.law.XmlIvg;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.law.XmlKvg;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.law.XmlMvg;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.law.XmlOrg;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.law.XmlUvg;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.law.XmlVvg;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.prolog.XmlSoftware;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.service.XmlRecordServiceType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.service.XmlServiceEx;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.service.XmlXtraDrg;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.service.XmlXtraDrug;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlBiller;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlDebitor;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlEmployer;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlGuarantor;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlInsurance;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlPatient;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlPatientCard;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlProvider;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlReferrer;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.treatment.XmlBfsData;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.treatment.XmlCaseDetail;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.treatment.XmlDiagnosis;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.treatment.XmlGrouperData;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.treatment.XmlXtraAmbulatory;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.treatment.XmlXtraStationary;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.vat.XmlVat;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.vat.XmlVatRate;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.processing.XmlDemand;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.processing.XmlInstruction;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.processing.XmlTransport;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.processing.XmlTransport.XmlVia;
import lombok.NonNull;

import javax.annotation.Nullable;
import javax.xml.bind.JAXBElement;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static de.metas.util.Check.fail;

@SuppressWarnings("null")
public class Invoice450FromCrossVersionModelTool
{
	public static final Invoice450FromCrossVersionModelTool INSTANCE = new Invoice450FromCrossVersionModelTool();

	private final ObjectFactory jaxbRequestObjectFactory = new ObjectFactory();

	private static final long VALIDATION_STATUS_OK = 0L;

	private Invoice450FromCrossVersionModelTool()
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
		processingType.setPrintCopyToGuarantor(processing.getPrintCustomerCopy());

		processingType.setTransport(createTransportType(processing.getTransport()));

		processingType.setInstructions(createValidationsType(processing.getInstructions()));

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

	private InstructionsType createValidationsType(@NonNull final List<XmlInstruction> instructions)
	{
		if (instructions.isEmpty())
		{
			return null;
		}
		final InstructionsType validationsType = jaxbRequestObjectFactory.createInstructionsType();
		for (final XmlInstruction instruction : instructions)
		{
			validationsType.getInstruction().add(createInstructionType(instruction));
		}

		return validationsType;
	}

	private InstructionType createInstructionType(@NonNull final XmlInstruction validation)
	{
		final InstructionType validationType = jaxbRequestObjectFactory.createInstructionType();

		validationType.setToken(validation.getToken());
		validationType.setValue(validation.getValue());

		return validationType;
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
		payloadType.setIfStornoFollowupInvoiceProbable(CoalesceUtil.coalesce(payload.getIfStornoFollowupInvoiceProbable(), true));

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
		creditType.setRequestTimestamp(credit.getRequestTimestamp().intValue());
		creditType.setRequestId(credit.getRequestId());

		return creditType;
	}

	private InvoiceType createInvoiceType(@NonNull final XmlInvoice invoice)
	{
		final InvoiceType invoiceType = jaxbRequestObjectFactory.createInvoiceType();

		invoiceType.setRequestTimestamp(invoice.getRequestTimestamp().intValue());
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
		reminderType.setRequestTimestamp(reminder.getRequestTimestamp().intValue());
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

		createTiersTypes(bodyType, body.getTiers(), body.getBalance());

		createEsrType(bodyType, body.getEsr(), body.getTiers().getBiller());

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

	private BalanceTGType createBalanceTGType(@NonNull final XmlBalance balance)
	{
		final BalanceTGType balanceType = jaxbRequestObjectFactory.createBalanceTGType();

		balanceType.setCurrency(balance.getCurrency());
		balanceType.setAmount(balance.getAmount());
		balanceType.setAmountReminder(balance.getAmountReminder());
		balanceType.setAmountPrepaid(balance.getAmountPrepaid());
		balanceType.setAmountDue(balance.getAmountDue());
		balanceType.setAmountObligations(balance.getAmountObligations());

		balanceType.setVat(createVatType(balance.getVat()));

		return balanceType;
	}

	private BalanceTPType createBalanceTPType(@NonNull final XmlBalance balance)
	{
		final BalanceTPType balanceType = jaxbRequestObjectFactory.createBalanceTPType();

		balanceType.setCurrency(balance.getCurrency());
		balanceType.setAmount(balance.getAmount());
		balanceType.setAmountReminder(balance.getAmountReminder());
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
			@NonNull final XmlEsr esr,
			@NonNull final XmlBiller biller)
	{
		if (esr instanceof XmlEsrQR)
		{
			bodyType.setEsrQR(createEsrQRType((XmlEsrQR)esr, biller));
		}
		else if (esr instanceof XmlEsr9)
		{
			bodyType.setEsr9(createEsr9Type((XmlEsr9)esr, biller));
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

	private EsrQRType createEsrQRType(@NonNull final XmlEsrQR esrQr, final @NonNull XmlBiller biller)
	{
		final EsrQRType esrQRType = jaxbRequestObjectFactory.createEsrQRType();

		esrQRType.setBank(createEsrAddressType(esrQr.getBank()));
		final XmlAddress creditor = esrQr.getCreditor();
		if (creditor != null)
		{
			esrQRType.setCreditor(createEsrAddressType(creditor));
		}
		else
		{
			esrQRType.setCreditor(createEsrAddressType(biller));
		}

		esrQRType.getPaymentReason().addAll(esrQr.getPaymentReason());
		esrQRType.setType(esrQr.getType());
		esrQRType.setIban(esrQr.getIban());

		esrQRType.setReferenceNumber(esrQr.getReferenceNumber());
		esrQRType.setCustomerNote(esrQr.getCustomerNote());

		return esrQRType;
	}

	private Esr9Type createEsr9Type(@NonNull final XmlEsr9 esr9, final @NonNull XmlBiller biller)
	{
		final Esr9Type esr9Type = jaxbRequestObjectFactory.createEsr9Type();

		esr9Type.setType(esr9.getType());

		esr9Type.setParticipantNumber(esr9.getParticipantNumber());
		esr9Type.setReferenceNumber(esr9.getReferenceNumber());
		esr9Type.setCodingLine(esr9.getCodingLine());

		esr9Type.setBank(createEsrAddressType(esr9.getBank()));
		final XmlAddress creditor = esr9.getCreditor();
		if (creditor != null)
		{
			esr9Type.setCreditor(createEsrAddressType(creditor));
		}
		else
		{
			esr9Type.setCreditor(createEsrAddressType(biller));
		}

		return esr9Type;
	}

	private EsrRedType createEsrRedType(@NonNull final XmlEsrRed esrRed)
	{
		final EsrRedType esrRedType = jaxbRequestObjectFactory.createEsrRedType();

		esrRedType.setPaymentTo(esrRed.getPaymentTo());

		esrRedType.setPostAccount(esrRed.getPostAccount());
		esrRedType.setIban(esrRed.getIban());
		esrRedType.setReferenceNumber(esrRed.getReferenceNumber());
		esrRedType.setCodingLine1(esrRed.getCodingLine1());
		esrRedType.setCodingLine2(esrRed.getCodingLine2());

		esrRedType.setBank(createEsrAddressType(esrRed.getBank()));

		return esrRedType;
	}

	private EsrAddressType createEsrAddressType(@Nullable final XmlAddress address)
	{
		if (address == null)
		{
			return null; // we are dealing with a VESR transaction
		}

		final boolean hasCompanyAddress = !Check.isEmpty(address.getCompany());
		final boolean hasPersonAddress = !Check.isEmpty(address.getPerson());

		final EsrAddressType esrAddressType = jaxbRequestObjectFactory.createEsrAddressType();
		if (hasCompanyAddress)
		{
			esrAddressType.setCompany(createCompanyType(address.getCompany()));
		}
		if (hasPersonAddress)
		{
			esrAddressType.setPerson(createPersonType(address.getPerson()));
		}
		return esrAddressType;
	}

	private EsrAddressType createEsrAddressType(final XmlBiller biller)
	{
		if (biller == null)
		{
			return null; // we are dealing with a VESR transaction
		}

		final boolean hasCompanyAddress = !Check.isEmpty(biller.getCompany());
		final boolean hasPersonAddress = !Check.isEmpty(biller.getPerson());

		final EsrAddressType esrAddressType = jaxbRequestObjectFactory.createEsrAddressType();
		if (hasCompanyAddress)
		{
			esrAddressType.setCompany(createCompanyType(biller.getCompany()));
		}
		if (hasPersonAddress)
		{
			esrAddressType.setPerson(createPersonType(biller.getPerson()));
		}
		return esrAddressType;
	}

	private void createTiersTypes(
			final BodyType bodyType,
			final XmlTiers tiers,
			final XmlBalance balance)
	{
		switch (tiers.getType())
		{
			case GARANT:
				bodyType.setTiersGarant(createGarantType(tiers, balance));
				break;
			case PAYANT:
				bodyType.setTiersPayant(createPayantType(tiers, balance));
				break;
			default:
				throw Check.fail("Unexpected triers={}; tiers={}", tiers.getType(), tiers);
		}
	}

	private GarantType createGarantType(@NonNull final XmlTiers tiers, final XmlBalance balance)
	{
		final GarantType garantType = jaxbRequestObjectFactory.createGarantType();

		garantType.setPaymentPeriod(tiers.getPaymentPeriod());
		garantType.setBiller(createBillerAddressType(tiers.getBiller()));
		final XmlDebitor debitor = tiers.getDebitor();
		final XmlInsurance insurance = tiers.getInsurance();
		if (debitor != null)
		{
			garantType.setDebitor(createDebitorAddressType(debitor));
		}
		else
		{
			garantType.setDebitor(createDebitorAddressType(insurance));
		}
		garantType.setProvider(createProviderAddressType(tiers.getProvider()));
		garantType.setInsurance(createInsuranceAddresType(insurance));
		garantType.setPatient(createPatientAddressType(tiers.getPatient()));
		garantType.setInsured(createPatientAddressTypeOrNull(tiers.getInsured()));
		garantType.setGuarantor(createGuarantorAddressType(tiers.getGuarantor()));
		garantType.setReferrer(createReferrerAddressType(tiers.getReferrer()));
		garantType.setEmployer(createEmployerAddressType(tiers.getEmployer()));
		garantType.setBalance(createBalanceTGType(CoalesceUtil.coalesce(tiers.getBalance(), balance)));

		return garantType;
	}

	private PayantType createPayantType(@NonNull final XmlTiers tiers, final XmlBalance balance)
	{
		final PayantType payantType = jaxbRequestObjectFactory.createPayantType();

		payantType.setPaymentPeriod(tiers.getPaymentPeriod());
		payantType.setBiller(createBillerAddressType(tiers.getBiller()));
		final XmlDebitor debitor = tiers.getDebitor();
		final XmlInsurance insurance = tiers.getInsurance();
		if (debitor != null)
		{
			payantType.setDebitor(createDebitorAddressType(debitor));
		}
		else
		{
			payantType.setDebitor(createDebitorAddressType(insurance));
		}
		payantType.setProvider(createProviderAddressType(tiers.getProvider()));
		payantType.setInsurance(createInsuranceAddresType(tiers.getInsurance()));
		payantType.setPatient(createPatientAddressType(tiers.getPatient()));
		payantType.setInsured(createPatientAddressTypeOrNull(tiers.getInsured()));
		payantType.setGuarantor(createGuarantorAddressType(tiers.getGuarantor()));
		payantType.setReferrer(createReferrerAddressType(tiers.getReferrer()));
		payantType.setEmployer(createEmployerAddressType(tiers.getEmployer()));
		payantType.setBalance(createBalanceTPType(CoalesceUtil.coalesce(tiers.getBalance(), balance)));

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

	private DebitorAddressType createDebitorAddressType(final XmlDebitor debitor)
	{
		if (debitor == null)
		{
			return null;
		}
		final DebitorAddressType debitorAddressType = jaxbRequestObjectFactory.createDebitorAddressType();

		debitorAddressType.setCompany(createCompanyType(debitor.getCompany()));
		debitorAddressType.setPerson(createPersonType(debitor.getPerson()));
		debitorAddressType.setEanParty(debitor.getEanParty());

		return debitorAddressType;
	}

	private DebitorAddressType createDebitorAddressType(final XmlInsurance insurance)
	{
		if (insurance == null)
		{
			return null;
		}
		final DebitorAddressType debitorAddressType = jaxbRequestObjectFactory.createDebitorAddressType();

		debitorAddressType.setCompany(createCompanyType(insurance.getCompany()));
		debitorAddressType.setPerson(createPersonType(insurance.getPerson()));
		debitorAddressType.setEanParty(insurance.getEanParty());

		return debitorAddressType;
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

		treatmentType.getDiagnosis().addAll(createDiagnosis(treatment.getDiagnoses()));

		treatmentType.setXtraHospital(createXtraHospitalType(treatment));

		return treatmentType;
	}

	private Collection<? extends DiagnosisType> createDiagnosis(final List<XmlDiagnosis> diagnoses)
	{
		if (diagnoses == null || diagnoses.isEmpty())
		{
			return Collections.emptyList();
		}

		final ImmutableList.Builder<DiagnosisType> diagnosisBuilder = ImmutableList.builder();
		for (final XmlDiagnosis diagnosis : diagnoses)
		{
			diagnosisBuilder.add(createDiagnosisType(diagnosis));
		}

		return diagnosisBuilder.build();
	}

	private DiagnosisType createDiagnosisType(final XmlDiagnosis diagnosis)
	{
		final DiagnosisType diagnosisType = jaxbRequestObjectFactory.createDiagnosisType();
		diagnosisType.setType(diagnosis.getType());
		diagnosisType.setCode(diagnosis.getCode());
		diagnosisType.setValue(diagnosis.getValue());
		return diagnosisType;
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
			if (service instanceof XmlRecordServiceType)
			{
				serviceType = createRecordServiceType((XmlRecordServiceType)service);
			}
			else if (service instanceof XmlServiceEx)
			{
				serviceType = createRecordTarmedType((XmlServiceEx)service);
			}
			else
			{
				serviceType = null;
				//Check.fail("Unexpected service type={}; service={}", service.getClass(), service);
			}
			servicesType.getServiceExOrService().add(serviceType);
		}
		return servicesType;
	}

	private ServiceType createRecordServiceType(@NonNull final XmlRecordServiceType xRecordTarmed)
	{
		final ServiceType serviceType = jaxbRequestObjectFactory.createServiceType();

		serviceType.setXtraDrg(createXtraDRGType(xRecordTarmed.getXtraDrg()));
		serviceType.setXtraDrug(createXtraDrug(xRecordTarmed.getXtraDrug()));
		serviceType.setRecordId(xRecordTarmed.getRecordId());
		serviceType.setTariffType(xRecordTarmed.getTariffType());
		serviceType.setCode(xRecordTarmed.getCode());
		serviceType.setRefCode(xRecordTarmed.getRefCode());
		serviceType.setName(xRecordTarmed.getName());
		serviceType.setSession(xRecordTarmed.getSession());
		serviceType.setQuantity(xRecordTarmed.getQuantity());
		serviceType.setDateBegin(xRecordTarmed.getDateBegin());
		serviceType.setDateEnd(xRecordTarmed.getDateEnd());
		serviceType.setProviderId(xRecordTarmed.getProviderId());
		serviceType.setResponsibleId(xRecordTarmed.getResponsibleId());
		serviceType.setUnit(xRecordTarmed.getUnit());
		serviceType.setUnitFactor(xRecordTarmed.getUnitFactor());
		serviceType.setUnitFactor(xRecordTarmed.getUnitFactor());
		serviceType.setExternalFactor(xRecordTarmed.getExternalFactor());
		serviceType.setAmount(xRecordTarmed.getAmount());
		serviceType.setVatRate(xRecordTarmed.getVatRate());
		serviceType.setObligation(xRecordTarmed.getObligation());
		serviceType.setSectionCode(xRecordTarmed.getSectionCode());
		serviceType.setRemark(xRecordTarmed.getRemark());
		serviceType.setServiceAttributes(xRecordTarmed.getServiceAttributes());

		return serviceType;
	}

	private XtraDRGType createXtraDRGType(final XmlXtraDrg xmlXtraDrg)
	{
		if (xmlXtraDrg == null)
		{
			return null;
		}
		final XtraDRGType xtraDRGType = jaxbRequestObjectFactory.createXtraDRGType();
		xtraDRGType.setCostFraction(xmlXtraDrg.getCostFraction());

		return xtraDRGType;
	}

	private ServiceExType createRecordTarmedType(@NonNull final XmlServiceEx xServiceEx)
	{
		final ServiceExType serviceExType = jaxbRequestObjectFactory.createServiceExType();

		serviceExType.setRecordId(xServiceEx.getRecordId());
		serviceExType.setTariffType(xServiceEx.getTariffType());
		serviceExType.setCode(xServiceEx.getCode());
		serviceExType.setRefCode(xServiceEx.getRefCode());
		serviceExType.setName(xServiceEx.getName());
		serviceExType.setSession(xServiceEx.getSession());
		serviceExType.setQuantity(xServiceEx.getQuantity());
		serviceExType.setDateBegin(xServiceEx.getDateBegin());
		serviceExType.setDateEnd(xServiceEx.getDateEnd());
		serviceExType.setProviderId(xServiceEx.getProviderId());
		serviceExType.setResponsibleId(xServiceEx.getResponsibleId());
		serviceExType.setBillingRole(xServiceEx.getBillingRole());
		serviceExType.setMedicalRole(xServiceEx.getMedicalRole());
		serviceExType.setBodyLocation(xServiceEx.getBodyLocation());
		serviceExType.setTreatment(xServiceEx.getTreatment());
		serviceExType.setUnitMt(xServiceEx.getUnitMt());
		serviceExType.setUnitFactorMt(xServiceEx.getUnitFactorMt());
		serviceExType.setScaleFactorMt(xServiceEx.getScaleFactorMt());
		serviceExType.setExternalFactorMt(xServiceEx.getExternalFactorMt());
		serviceExType.setAmountMt(xServiceEx.getAmountMt());
		serviceExType.setUnitTt(xServiceEx.getUnitTt());
		serviceExType.setUnitFactorTt(xServiceEx.getUnitFactorTt());
		serviceExType.setScaleFactorTt(xServiceEx.getScaleFactorTt());
		serviceExType.setExternalFactorTt(xServiceEx.getExternalFactorTt());
		serviceExType.setAmountTt(xServiceEx.getAmountTt());
		serviceExType.setAmount(xServiceEx.getAmount());
		serviceExType.setVatRate(xServiceEx.getVatRate());
		serviceExType.setObligation(xServiceEx.getObligation());
		serviceExType.setSectionCode(xServiceEx.getSectionCode());
		serviceExType.setRemark(xServiceEx.getRemark());
		serviceExType.setServiceAttributes(xServiceEx.getServiceAttributes());

		return serviceExType;
	}

	private XtraDrugType createXtraDrug(@Nullable final XmlXtraDrug xtraDrug)
	{
		if (xtraDrug == null)
		{
			return null;
		}

		final XtraDrugType xtraDrugType = jaxbRequestObjectFactory.createXtraDrugType();
		xtraDrugType.setIndicated(xtraDrug.getIndicated());
		xtraDrugType.setIocmCategory(xtraDrug.getIocmCategory());
		xtraDrugType.setDelivery(xtraDrug.getDelivery());
		xtraDrugType.setRegulationAttributes(xtraDrug.getRegulationAttributes());
		xtraDrugType.setLimitation(xtraDrug.getLimitation());

		return xtraDrugType;
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
		documentType.setTitle(document.getTitle());

		documentType.setBase64(document.getBase64());
		documentType.setUrl(document.getUrl());

		return documentType;
	}
}
