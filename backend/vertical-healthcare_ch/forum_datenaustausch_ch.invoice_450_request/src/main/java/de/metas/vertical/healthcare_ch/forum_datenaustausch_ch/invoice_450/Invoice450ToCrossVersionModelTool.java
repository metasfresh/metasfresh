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
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.model.commontypes.XmlCompany.XmlCompanyBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.model.commontypes.XmlInvoice;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.model.commontypes.XmlInvoice.XmlInvoiceBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.model.commontypes.XmlOnline;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.model.commontypes.XmlOnline.XmlOnlineBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.model.commontypes.XmlPerson;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.model.commontypes.XmlPerson.XmlPersonBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.model.commontypes.XmlPostal;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.model.commontypes.XmlPostal.XmlPostalBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.model.commontypes.XmlTelecom;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.model.commontypes.XmlTelecom.XmlTelecomBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.XmlPayload;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.XmlPayload.XmlPayloadBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.XmlProcessing;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.XmlProcessing.XmlProcessingBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.XmlRequest;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.XmlRequest.XmlRequestBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.XmlBody;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.XmlBody.XmlBodyBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.XmlCredit;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.XmlCredit.XmlCreditBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.XmlReminder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.XmlReminder.XmlReminderBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.XmlBalance;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.XmlBalance.XmlBalanceBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.XmlDocument;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.XmlDocument.XmlDocumentBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.XmlEsr;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.XmlLaw;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.XmlLaw.XmlLawBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.XmlProlog;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.XmlProlog.XmlPrologBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.XmlService;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.XmlTiers;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.XmlTiers.Tiers;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.XmlTiers.XmlTiersBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.XmlTreatment;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.XmlTreatment.XmlTreatmentBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.esr.XmlAddress;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.esr.XmlAddress.XmlAddressBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.esr.XmlEsr9;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.esr.XmlEsr9.XmlEsr9Builder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.esr.XmlEsrQR;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.esr.XmlEsrRed;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.esr.XmlEsrRed.XmlEsrRedBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.law.XmlIvg;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.law.XmlIvg.XmlIvgBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.law.XmlKvg;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.law.XmlKvg.XmlKvgBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.law.XmlMvg;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.law.XmlMvg.XmlMvgBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.law.XmlOrg;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.law.XmlOrg.XmlOrgBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.law.XmlUvg;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.law.XmlUvg.XmlUvgBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.law.XmlVvg;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.law.XmlVvg.XmlVvgBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.prolog.XmlSoftware;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.prolog.XmlSoftware.XmlSoftwareBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.service.XmlRecordServiceType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.service.XmlRecordServiceType.XmlRecordServiceTypeBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.service.XmlServiceEx;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.service.XmlXtraDrg;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.service.XmlXtraDrg.XmlXtraDrgBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.service.XmlXtraDrug;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.service.XmlXtraDrug.XmlXtraDrugBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlBiller;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlBiller.XmlBillerBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlDebitor;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlEmployer;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlEmployer.XmlEmployerBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlGuarantor;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlGuarantor.XmlGuarantorBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlInsurance;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlInsurance.XmlInsuranceBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlPatient;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlPatient.XmlPatientBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlPatientCard;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlPatientCard.XmlPatientCardBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlProvider;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlProvider.XmlProviderBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlReferrer;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlReferrer.XmlReferrerBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.treatment.XmlBfsData;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.treatment.XmlBfsData.XmlBfsDataBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.treatment.XmlCaseDetail;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.treatment.XmlCaseDetail.XmlCaseDetailBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.treatment.XmlDiagnosis;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.treatment.XmlGrouperData;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.treatment.XmlGrouperData.XmlGrouperDataBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.treatment.XmlXtraAmbulatory;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.treatment.XmlXtraAmbulatory.XmlXtraAmbulatoryBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.treatment.XmlXtraStationary;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.treatment.XmlXtraStationary.XmlXtraStationaryBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.vat.XmlVat;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.vat.XmlVat.XmlVatBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.vat.XmlVatRate;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.vat.XmlVatRate.XmlVatRateBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.processing.XmlDemand;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.processing.XmlDemand.XmlDemandBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.processing.XmlInstruction;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.processing.XmlInstruction.XmlInstructionBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.processing.XmlTransport;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.processing.XmlTransport.XmlTransportBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.processing.XmlTransport.XmlVia;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.processing.XmlTransport.XmlVia.XmlViaBuilder;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Invoice450ToCrossVersionModelTool
{
	public static final Invoice450ToCrossVersionModelTool INSTANCE = new Invoice450ToCrossVersionModelTool();

	private Invoice450ToCrossVersionModelTool()
	{
	}

	public XmlRequest toCrossVersionModel(@NonNull final RequestType invoice440Request)
	{
		final XmlRequestBuilder builder = XmlRequest.builder();

		updateBuilderForRequest(invoice440Request, builder);

		return builder.build();
	}

	private void updateBuilderForRequest(
			@NonNull final RequestType requestType,
			@NonNull final XmlRequestBuilder xRequest)
	{
		final XmlMode mode = extractXmlMode(requestType);

		xRequest.language(requestType.getLanguage())
				.modus(mode)
				.validationStatus(requestType.getValidationStatus());

		xRequest.processing(createXmlProcessing(requestType.getProcessing()));

		xRequest.payload(createXmlPayload(requestType.getPayload()));
	}

	private XmlMode extractXmlMode(final RequestType requestType)
	{
		// the string is hardcoded in the generated jaxb code.
		return "production".equals(requestType.getModus()) ? XmlMode.PRODUCTION : XmlMode.TEST;
	}

	private XmlProcessing createXmlProcessing(@NonNull final ProcessingType processingType)
	{
		final XmlProcessingBuilder xProcessing = XmlProcessing.builder();

		xProcessing.printAtIntermediate(processingType.isPrintAtIntermediate());
		xProcessing.printCustomerCopy(processingType.isPrintCopyToGuarantor());

		xProcessing.transport(createXmlTransport(processingType.getTransport()));

		xProcessing.instructions(createXmlInstructions(processingType.getInstructions()));

		xProcessing.demand(createXmlDemand(processingType.getDemand()));

		return xProcessing.build();
	}

	private XmlTransport createXmlTransport(@NonNull final TransportType transportType)
	{
		final XmlTransportBuilder xTransport = XmlTransport.builder();

		xTransport.from(transportType.getFrom());
		xTransport.to(transportType.getTo());

		for (final Via via : transportType.getVia())
		{
			xTransport.via(createXmlVia(via));
		}

		return xTransport.build();
	}

	private XmlVia createXmlVia(@NonNull final Via via)
	{
		final XmlViaBuilder xVia = XmlVia.builder();

		xVia.sequenceId(via.getSequenceId());
		xVia.via(via.getVia());

		return xVia.build();
	}

	private ImmutableList<XmlInstruction> createXmlInstructions(@Nullable final InstructionsType validations)
	{
		if (validations == null)
		{
			return ImmutableList.of();
		}
		final ImmutableList.Builder<XmlInstruction> xInstructions = ImmutableList.builder();

		for (final InstructionType instruction : validations.getInstruction())
		{
			xInstructions.add(createXmlInstruction(instruction));
		}

		return xInstructions.build();
	}

	private XmlInstruction createXmlInstruction(@Nullable final InstructionType validationType)
	{
		if (validationType == null)
		{
			return null;
		}

		final XmlInstructionBuilder xValidation = XmlInstruction.builder();

		xValidation.token(validationType.getToken());
		xValidation.value(validationType.getValue());

		return xValidation.build();
	}

	private XmlDemand createXmlDemand(@Nullable final Demand demand)
	{
		if (demand == null)
		{
			return null;
		}

		final XmlDemandBuilder xDemand = XmlDemand.builder();

		xDemand.tcDemandId(demand.getTcDemandId());
		xDemand.tcToken(demand.getTcToken());
		xDemand.insuranceDemandDate(demand.getInsuranceDemandDate());
		xDemand.insuranceDemandId(demand.getInsuranceDemandId());

		return xDemand.build();
	}

	private XmlPayload createXmlPayload(@NonNull final PayloadType payloadType)
	{
		final XmlPayloadBuilder xPayload = XmlPayload.builder();

		xPayload.type(payloadType.getType());
		xPayload.storno(payloadType.isStorno());
		xPayload.copy(payloadType.isCopy());
		xPayload.ifStornoFollowupInvoiceProbable(payloadType.isIfStornoFollowupInvoiceProbable());

		xPayload.credit(createXmlCredit(payloadType.getCredit()));

		xPayload.invoice(createXmlInvoice(payloadType.getInvoice()));

		xPayload.reminder(createXmlReminder(payloadType.getReminder()));

		xPayload.body(createXmlBody(payloadType.getBody()));

		return xPayload.build();
	}

	private XmlCredit createXmlCredit(@Nullable final CreditType creditType)
	{
		if (creditType == null)
		{
			return null;
		}

		final XmlCreditBuilder xCredit = XmlCredit.builder();

		xCredit.requestDate(creditType.getRequestDate());
		xCredit.requestTimestamp(BigInteger.valueOf(creditType.getRequestTimestamp()));
		xCredit.requestId(creditType.getRequestId());

		return xCredit.build();
	}

	private XmlInvoice createXmlInvoice(@NonNull final InvoiceType invoiceType)
	{
		final XmlInvoiceBuilder xInvoice = XmlInvoice.builder();

		xInvoice.requestTimestamp(BigInteger.valueOf(invoiceType.getRequestTimestamp()));
		xInvoice.requestDate(invoiceType.getRequestDate());
		xInvoice.requestId(invoiceType.getRequestId());

		return xInvoice.build();
	}

	private XmlReminder createXmlReminder(@Nullable final ReminderType reminderType)
	{
		if (reminderType == null)
		{
			return null;
		}

		final XmlReminderBuilder xReminder = XmlReminder.builder();

		xReminder.requestTimestamp(BigInteger.valueOf(reminderType.getRequestTimestamp()));
		xReminder.requestDate(reminderType.getRequestDate());
		xReminder.requestId(reminderType.getRequestId());

		xReminder.reminderLevel(reminderType.getReminderLevel());
		xReminder.reminderText(reminderType.getReminderText());

		return xReminder.build();
	}

	private XmlBody createXmlBody(@NonNull final BodyType bodyType)
	{
		final XmlBodyBuilder xBody = XmlBody.builder();

		xBody.place(bodyType.getPlace());
		xBody.role(bodyType.getRole());
		xBody.roleTitle(bodyType.getRoleTitle());

		xBody.prolog(createXmlProlog(bodyType.getProlog()));

		xBody.remark(bodyType.getRemark());

		xBody.esr(createXmlEsr(bodyType));

		xBody.tiers(createXmlTiers(bodyType));

		xBody.law(createXmlLaw(bodyType));

		xBody.treatment(createXmlTreatment(bodyType.getTreatment()));

		xBody.services(createXmlServices(bodyType.getServices()));

		xBody.documents(createXmlDocuments(bodyType.getDocuments()));

		return xBody.build();
	}

	private XmlProlog createXmlProlog(@NonNull final PrologType prolog)
	{
		final XmlPrologBuilder xProlog = XmlProlog.builder();

		xProlog.pkg(createXmlSoftware(prolog.getPackage()));
		xProlog.generator(createXmlSoftware(prolog.getGenerator()));

		return xProlog.build();
	}

	private XmlSoftware createXmlSoftware(@NonNull final SoftwareType softwareType)
	{
		final XmlSoftwareBuilder xSoftware = XmlSoftware.builder();

		xSoftware.name(softwareType.getName());
		xSoftware.copyright(softwareType.getCopyright());
		xSoftware.description(softwareType.getDescription());
		xSoftware.version(softwareType.getVersion());
		xSoftware.id(softwareType.getId());

		return xSoftware.build();
	}

	private XmlBalance createXmlBalance(@NonNull final BalanceTGType balanceType)
	{
		final XmlBalanceBuilder xBalance = XmlBalance.builder();

		xBalance.currency(balanceType.getCurrency());
		xBalance.amount(balanceType.getAmount());
		xBalance.amountReminder(balanceType.getAmountReminder());
		xBalance.amountPrepaid(balanceType.getAmountPrepaid());
		xBalance.amountDue(balanceType.getAmountDue());
		xBalance.amountObligations(balanceType.getAmountObligations());

		xBalance.vat(createXmlVat(balanceType.getVat()));

		return xBalance.build();
	}

	private XmlBalance createXmlBalance(@NonNull final BalanceTPType balanceType)
	{
		final XmlBalanceBuilder xBalance = XmlBalance.builder();

		xBalance.currency(balanceType.getCurrency());
		xBalance.amount(balanceType.getAmount());
		xBalance.amountReminder(balanceType.getAmountReminder());
		xBalance.amountDue(balanceType.getAmountDue());
		xBalance.amountObligations(balanceType.getAmountObligations());

		xBalance.vat(createXmlVat(balanceType.getVat()));

		return xBalance.build();
	}

	private XmlVat createXmlVat(@NonNull final VatType vatType)
	{
		final XmlVatBuilder xVat = XmlVat.builder();

		xVat.vatNumber(vatType.getVatNumber());
		xVat.vat(vatType.getVat());

		for (final VatRateType vatRate : vatType.getVatRate())
		{
			xVat.vatRate(createXmlVatRate(vatRate));
		}

		return xVat.build();
	}

	private XmlVatRate createXmlVatRate(@NonNull final VatRateType vatRateType)
	{
		final XmlVatRateBuilder xVatRate = XmlVatRate.builder();

		xVatRate.amount(vatRateType.getAmount());
		xVatRate.vatRate(vatRateType.getVatRate());
		xVatRate.vat(vatRateType.getVat());

		return xVatRate.build();
	}

	private XmlEsr createXmlEsr(@NonNull final BodyType bodyType)
	{
		if (bodyType.getEsrQR() != null)
		{
			return createXmlEsrQR(bodyType.getEsrQR());
		}
		else if (bodyType.getEsr9() != null)
		{
			return createXmlEsr9(bodyType.getEsr9());
		}
		else if (bodyType.getEsrRed() != null)
		{
			return createXmlEsrRed(bodyType.getEsrRed());
		}

		Check.fail("Missing esr bodyType={}", bodyType);
		return null;
	}

	private XmlEsrQR createXmlEsrQR(@NonNull final EsrQRType esrQRType)
	{
		final XmlEsrQR.XmlEsrQRBuilder xEsrQR = XmlEsrQR.builder();

		xEsrQR.bank(createXmlAddress(esrQRType.getBank()));
		xEsrQR.creditor(createXmlAddress(esrQRType.getCreditor()));
		xEsrQR.paymentReason(createXmlPaymentReasons(esrQRType.getPaymentReason()));

		xEsrQR.type(esrQRType.getType());

		xEsrQR.iban(esrQRType.getIban());
		xEsrQR.referenceNumber(esrQRType.getReferenceNumber());
		xEsrQR.customerNote(esrQRType.getCustomerNote());

		return xEsrQR.build();
	}

	private List<String> createXmlPaymentReasons(final List<String> paymentReason)
	{
		final List<String> xPaymentReasons = new ArrayList<>(paymentReason);

		xPaymentReasons.addAll(paymentReason);

		return xPaymentReasons;
	}

	private XmlEsr9 createXmlEsr9(@NonNull final Esr9Type esr9Type)
	{
		final XmlEsr9Builder xEsr9 = XmlEsr9.builder();

		xEsr9.type(esr9Type.getType());

		xEsr9.participantNumber(esr9Type.getParticipantNumber());
		xEsr9.referenceNumber(esr9Type.getReferenceNumber());
		xEsr9.codingLine(esr9Type.getCodingLine());

		xEsr9.bank(createXmlAddress(esr9Type.getBank()));
		xEsr9.creditor(createXmlAddress(esr9Type.getCreditor()));

		return xEsr9.build();
	}

	private XmlEsrRed createXmlEsrRed(@NonNull final EsrRedType esrRedType)
	{
		final XmlEsrRedBuilder xEsrRed = XmlEsrRed.builder();

		xEsrRed.paymentTo(esrRedType.getPaymentTo());

		xEsrRed.postAccount(esrRedType.getPostAccount());
		xEsrRed.iban(esrRedType.getIban());
		xEsrRed.referenceNumber(esrRedType.getReferenceNumber());
		xEsrRed.codingLine1(esrRedType.getCodingLine1());
		xEsrRed.codingLine2(esrRedType.getCodingLine2());

		xEsrRed.bank(createXmlAddress(esrRedType.getBank()));

		return xEsrRed.build();
	}

	private XmlAddress createXmlAddress(@Nullable final EsrAddressType esrAddressType)
	{
		if (esrAddressType == null)
		{
			return null;
		}
		final XmlAddressBuilder xAddress = XmlAddress.builder();

		xAddress.company(createXmlCompany(esrAddressType.getCompany()));
		xAddress.person(createXmlPerson(esrAddressType.getPerson()));

		return xAddress.build();
	}

	private XmlTiers createXmlTiers(@NonNull final BodyType bodyType)
	{
		if (bodyType.getTiersGarant() != null)
		{
			return createXmlTiers(bodyType.getTiersGarant());
		}
		else if (bodyType.getTiersPayant() != null)
		{
			return createXmlTiers(bodyType.getTiersPayant());
		}
		Check.fail("Missing tiers bodyType={}", bodyType);
		return null;
	}

	private XmlTiers createXmlTiers(@NonNull final GarantType garantType)
	{
		final XmlTiersBuilder xtiers = XmlTiers.builder();
		xtiers.type(Tiers.GARANT);

		xtiers.paymentPeriod(garantType.getPaymentPeriod());
		xtiers.biller(createXmlBiller(garantType.getBiller()));
		final DebitorAddressType debitor = garantType.getDebitor();
		if (debitor != null)
		{
			xtiers.debitor(createXmlDebitor(debitor));
		}
		xtiers.provider(createXmlProvider(garantType.getProvider()));
		xtiers.insurance(createXmlInsurance(garantType.getInsurance()));
		xtiers.patient(createXmlPatient(garantType.getPatient()));
		xtiers.insured(createXmlPatientOrNull(garantType.getInsured()));
		xtiers.guarantor(createXmlGuarantor(garantType.getGuarantor()));
		xtiers.referrer(createXmlReferrer(garantType.getReferrer()));
		xtiers.employer(createXmlEmployer(garantType.getEmployer()));
		final BalanceTGType balance = garantType.getBalance();
		if (balance != null)
		{
			xtiers.balance(createXmlBalance(balance));
		}

		return xtiers.build();
	}

	private XmlTiers createXmlTiers(@NonNull final PayantType payantType)
	{
		final XmlTiersBuilder xtiers = XmlTiers.builder();
		xtiers.type(Tiers.PAYANT);

		xtiers.paymentPeriod(payantType.getPaymentPeriod());
		xtiers.biller(createXmlBiller(payantType.getBiller()));
		final DebitorAddressType debitor = payantType.getDebitor();
		if (debitor != null)
		{
			xtiers.debitor(createXmlDebitor(debitor));
		}
		xtiers.provider(createXmlProvider(payantType.getProvider()));
		xtiers.insurance(createXmlInsurance(payantType.getInsurance()));
		xtiers.patient(createXmlPatient(payantType.getPatient()));
		xtiers.insured(createXmlPatientOrNull(payantType.getInsured()));
		xtiers.guarantor(createXmlGuarantor(payantType.getGuarantor()));
		xtiers.referrer(createXmlReferrer(payantType.getReferrer()));
		xtiers.employer(createXmlEmployer(payantType.getEmployer()));
		final BalanceTPType balance = payantType.getBalance();
		if (balance != null)
		{
			xtiers.balance(createXmlBalance(balance));
		}

		return xtiers.build();
	}

	private XmlBiller createXmlBiller(@NonNull final BillerAddressType billerAddressType)
	{
		final XmlBillerBuilder xBiller = XmlBiller.builder();

		xBiller.eanParty(billerAddressType.getEanParty());
		xBiller.specialty(billerAddressType.getSpecialty());
		xBiller.uidNumber(billerAddressType.getUidNumber());
		xBiller.zsr(billerAddressType.getZsr());

		xBiller.company(createXmlCompany(billerAddressType.getCompany()));
		xBiller.person(createXmlPerson(billerAddressType.getPerson()));

		return xBiller.build();
	}

	private XmlDebitor createXmlDebitor(@NonNull final DebitorAddressType billerAddressType)
	{
		final XmlDebitor.XmlDebitorBuilder xBiller = XmlDebitor.builder();

		xBiller.company(createXmlCompany(billerAddressType.getCompany()));
		xBiller.person(createXmlPerson(billerAddressType.getPerson()));
		xBiller.eanParty(billerAddressType.getEanParty());

		return xBiller.build();
	}

	private XmlProvider createXmlProvider(@NonNull final ProviderAddressType providerAddressType)
	{
		final XmlProviderBuilder xProvider = XmlProvider.builder();

		xProvider.eanParty(providerAddressType.getEanParty());
		xProvider.zsr(providerAddressType.getZsr());
		xProvider.specialty(providerAddressType.getSpecialty());

		xProvider.company(createXmlCompany(providerAddressType.getCompany()));
		xProvider.person(createXmlPerson(providerAddressType.getPerson()));

		return xProvider.build();
	}

	private XmlInsurance createXmlInsurance(@Nullable final InsuranceAddressType insuranceAddressType)
	{
		if (insuranceAddressType == null)
		{
			return null;
		}
		final XmlInsuranceBuilder xInsurance = XmlInsurance.builder();

		xInsurance.eanParty(insuranceAddressType.getEanParty());

		xInsurance.company(createXmlCompany(insuranceAddressType.getCompany()));
		xInsurance.person(createXmlPerson(insuranceAddressType.getPerson()));

		return xInsurance.build();
	}

	private XmlPatient createXmlPatientOrNull(@Nullable final PatientAddressType insuredPatientAddressType)
	{
		if (insuredPatientAddressType == null)
		{
			return null;
		}
		return createXmlPatient(insuredPatientAddressType);
	}

	private XmlPatient createXmlPatient(@NonNull final PatientAddressType patientAddressType)
	{
		final XmlPatientBuilder xPatient = XmlPatient.builder();

		xPatient.birthdate(patientAddressType.getBirthdate());
		xPatient.gender(patientAddressType.getGender());
		xPatient.person(createXmlPerson(patientAddressType.getPerson()));
		xPatient.ssn(patientAddressType.getSsn());

		xPatient.card(createXmlPatientCard(patientAddressType.getCard()));

		return xPatient.build();
	}

	private XmlPatientCard createXmlPatientCard(@Nullable final Card card)
	{
		if (card == null)
		{
			return null;
		}
		final XmlPatientCardBuilder xPatientCard = XmlPatientCard.builder();

		xPatientCard.cardId(card.getCardId());
		xPatientCard.expiryDate(card.getExpiryDate());
		xPatientCard.validationDate(card.getValidationDate());
		xPatientCard.validationId(card.getValidationId());
		xPatientCard.validationServer(card.getValidationServer());

		return xPatientCard.build();
	}

	private XmlGuarantor createXmlGuarantor(@NonNull final GuarantorAddressType guarantorAddressType)
	{
		final XmlGuarantorBuilder xGuarantor = XmlGuarantor.builder();

		xGuarantor.company(createXmlCompany(guarantorAddressType.getCompany()));
		xGuarantor.person(createXmlPerson(guarantorAddressType.getPerson()));

		return xGuarantor.build();
	}

	private XmlReferrer createXmlReferrer(@Nullable final ReferrerAddressType referrerAddressType)
	{
		if (referrerAddressType == null)
		{
			return null;
		}
		final XmlReferrerBuilder xReferrer = XmlReferrer.builder();

		xReferrer.eanParty(referrerAddressType.getEanParty());
		xReferrer.zsr(referrerAddressType.getZsr());
		xReferrer.specialty(referrerAddressType.getSpecialty());

		xReferrer.company(createXmlCompany(referrerAddressType.getCompany()));
		xReferrer.person(createXmlPerson(referrerAddressType.getPerson()));

		return xReferrer.build();
	}

	private XmlEmployer createXmlEmployer(@Nullable final EmployerAddressType employerAddressType)
	{
		if (employerAddressType == null)
		{
			return null;
		}
		final XmlEmployerBuilder xEmployer = XmlEmployer.builder();

		xEmployer.eanParty(employerAddressType.getEanParty());
		xEmployer.regNumber(employerAddressType.getRegNumber());

		xEmployer.company(createXmlCompany(employerAddressType.getCompany()));
		xEmployer.person(createXmlPerson(employerAddressType.getPerson()));

		return xEmployer.build();
	}

	private XmlCompany createXmlCompany(@Nullable final CompanyType companyType)
	{
		if (companyType == null)
		{
			return null;
		}
		final XmlCompanyBuilder xCompany = XmlCompany.builder();

		xCompany.companyname(companyType.getCompanyname());
		xCompany.department(companyType.getDepartment());

		xCompany.subaddressing(companyType.getSubaddressing());
		xCompany.postal(createXmlPostal(companyType.getPostal()));
		xCompany.telecom(createXmlTelecom(companyType.getTelecom()));
		xCompany.online(createXmlOnline(companyType.getOnline()));

		return xCompany.build();
	}

	private XmlPerson createXmlPerson(@Nullable final PersonType personType)
	{
		if (personType == null)
		{
			return null;
		}
		final XmlPersonBuilder xPerson = XmlPerson.builder();

		xPerson.salutation(personType.getSalutation());
		xPerson.title(personType.getTitle());
		xPerson.familyname(personType.getFamilyname());
		xPerson.givenname(personType.getGivenname());

		xPerson.subaddressing(personType.getSubaddressing());
		xPerson.postal(createXmlPostal(personType.getPostal()));
		xPerson.telecom(createXmlTelecom(personType.getTelecom()));
		xPerson.online(createXmlOnline(personType.getOnline()));

		return xPerson.build();
	}

	private XmlPostal createXmlPostal(@NonNull final PostalAddressType postalAddressType)
	{
		final XmlPostalBuilder xPostal = XmlPostal.builder();

		xPostal.pobox(postalAddressType.getPobox());
		xPostal.city(postalAddressType.getCity());
		xPostal.street(postalAddressType.getStreet());

		final ZipType zipType = postalAddressType.getZip();
		xPostal.countryCode(zipType.getCountrycode());
		xPostal.stateCode(zipType.getStatecode());
		xPostal.zip(zipType.getValue());

		return xPostal.build();
	}

	private XmlTelecom createXmlTelecom(@Nullable final TelecomAddressType telecomAddressType)
	{
		if (telecomAddressType == null)
		{
			return null;
		}
		final XmlTelecomBuilder xTelecom = XmlTelecom.builder();

		xTelecom.faxes(telecomAddressType.getFax());
		xTelecom.phones(telecomAddressType.getPhone());

		return xTelecom.build();
	}

	private XmlOnline createXmlOnline(@Nullable final OnlineAddressType onlineAddressType)
	{
		if (onlineAddressType == null)
		{
			return null;
		}

		final XmlOnlineBuilder xOnline = XmlOnline.builder();

		xOnline.emails(onlineAddressType.getEmail());
		xOnline.urls(onlineAddressType.getUrl());

		return xOnline.build();
	}

	private XmlLaw createXmlLaw(@NonNull final BodyType bodyType)
	{
		final XmlLawBuilder xLaw = XmlLaw.builder();

		xLaw.kvg(createXmlKvgLaw(bodyType.getKvg()));
		xLaw.vvg(createXmlVvgLaw(bodyType.getVvg()));
		xLaw.uvg(createXmlUvgLaw(bodyType.getUvg()));
		xLaw.ivg(createXmlIvgLaw(bodyType.getIvg()));
		xLaw.mvg(createXmlMvgLaw(bodyType.getMvg()));
		xLaw.org(createXmlOrgLaw(bodyType.getOrg()));

		return xLaw.build();
	}

	private XmlKvg createXmlKvgLaw(@Nullable final KvgLawType kvgLawType)
	{
		if (kvgLawType == null)
		{
			return null;
		}
		final XmlKvgBuilder xKvg = XmlKvg.builder();

		xKvg.insuredId(kvgLawType.getInsuredId());
		xKvg.caseId(kvgLawType.getCaseId());
		xKvg.caseDate(kvgLawType.getCaseDate());

		return xKvg.build();
	}

	private XmlVvg createXmlVvgLaw(@Nullable final VvgLawType vvgLawType)
	{
		if (vvgLawType == null)
		{
			return null;
		}
		final XmlVvgBuilder xVvg = XmlVvg.builder();

		xVvg.insuredId(vvgLawType.getInsuredId());
		xVvg.caseId(vvgLawType.getCaseId());
		xVvg.caseDate(vvgLawType.getCaseDate());

		return xVvg.build();
	}

	private XmlUvg createXmlUvgLaw(@Nullable final UvgLawType uvgLawType)
	{
		if (uvgLawType == null)
		{
			return null;
		}
		final XmlUvgBuilder xUvg = XmlUvg.builder();

		xUvg.insuredId(uvgLawType.getInsuredId());
		xUvg.caseId(uvgLawType.getCaseId());
		xUvg.caseDate(uvgLawType.getCaseDate());
		xUvg.ssn(uvgLawType.getSsn());

		return xUvg.build();
	}

	private XmlIvg createXmlIvgLaw(@Nullable final IvgLawType ivgLawType)
	{
		if (ivgLawType == null)
		{
			return null;
		}
		final XmlIvgBuilder xIvg = XmlIvg.builder();

		xIvg.caseId(ivgLawType.getCaseId());
		xIvg.caseDate(ivgLawType.getCaseDate());
		xIvg.ssn(ivgLawType.getSsn());
		xIvg.nif(ivgLawType.getNif());

		return xIvg.build();
	}

	private XmlMvg createXmlMvgLaw(@Nullable final MvgLawType mvgLawType)
	{
		if (mvgLawType == null)
		{
			return null;
		}
		final XmlMvgBuilder xMvg = XmlMvg.builder();

		xMvg.insuredId(mvgLawType.getInsuredId());
		xMvg.caseId(mvgLawType.getCaseId());
		xMvg.caseDate(mvgLawType.getCaseDate());
		xMvg.ssn(mvgLawType.getSsn());

		return xMvg.build();
	}

	private XmlOrg createXmlOrgLaw(@Nullable final OrgLawType orgLawType)
	{
		if (orgLawType == null)
		{
			return null;
		}
		final XmlOrgBuilder xOrg = XmlOrg.builder();

		xOrg.insuredId(orgLawType.getInsuredId());
		xOrg.caseId(orgLawType.getCaseId());
		xOrg.caseDate(orgLawType.getCaseDate());

		return xOrg.build();
	}

	private XmlTreatment createXmlTreatment(@NonNull final TreatmentType treatmentType)
	{
		final XmlTreatmentBuilder xTreatment = XmlTreatment.builder();

		xTreatment.dateBegin(treatmentType.getDateBegin());
		xTreatment.dateEnd(treatmentType.getDateEnd());
		xTreatment.canton(treatmentType.getCanton());
		xTreatment.reason(treatmentType.getReason());
		xTreatment.apid(treatmentType.getApid());
		xTreatment.acid(treatmentType.getAcid());

		xTreatment.diagnoses(createXmlDiagnoses(treatmentType.getDiagnosis()));

		xTreatment.xtraAmbulatory(createXmlXtraAmbulatory(treatmentType.getXtraHospital()));
		xTreatment.xtraStationary(createXmlXtraStationary(treatmentType.getXtraHospital()));

		return xTreatment.build();
	}

	private Collection<XmlDiagnosis> createXmlDiagnoses(final List<DiagnosisType> diagnosis)
	{
		if (diagnosis == null)
		{
			return null;
		}

		final Collection<XmlDiagnosis> xDiagnoses = new ArrayList<>();

		for (final DiagnosisType diagnosisType : diagnosis)
		{
			final XmlDiagnosis.XmlDiagnosisBuilder xDiagnosis = XmlDiagnosis.builder();

			xDiagnosis.code(diagnosisType.getCode());
			xDiagnosis.type(diagnosisType.getType());
			xDiagnosis.value(diagnosisType.getValue());
			xDiagnoses.add(xDiagnosis.build());
		}

		return xDiagnoses;
	}

	private XmlXtraAmbulatory createXmlXtraAmbulatory(@Nullable final XtraHospitalType xtraHospital)
	{
		if (xtraHospital == null)
		{
			return null;
		}
		final XtraAmbulatoryType xtraAmbulatoryType = xtraHospital.getAmbulatory();
		if (xtraAmbulatoryType == null)
		{
			return null;
		}

		final XmlXtraAmbulatoryBuilder xXtraAmbulatory = XmlXtraAmbulatory.builder();

		xXtraAmbulatory.hospitalizationType(xtraAmbulatoryType.getHospitalizationType());
		xXtraAmbulatory.hospitalizationMode(xtraAmbulatoryType.getHospitalizationMode());
		xXtraAmbulatory.clazz(xtraAmbulatoryType.getClazz());
		xXtraAmbulatory.sectionMajor(xtraAmbulatoryType.getSectionMajor());

		return xXtraAmbulatory.build();
	}

	private XmlXtraStationary createXmlXtraStationary(@Nullable final XtraHospitalType xtraHospital)
	{
		if (xtraHospital == null)
		{
			return null;
		}
		final XtraStationaryType xtraStationaryType = xtraHospital.getStationary();
		if (xtraStationaryType == null)
		{
			return null;
		}

		final XmlXtraStationaryBuilder xXtraStationary = XmlXtraStationary.builder();

		xXtraStationary.hospitalizationDate(xtraStationaryType.getHospitalizationDate());
		xXtraStationary.treatmentDays(xtraStationaryType.getTreatmentDays());
		xXtraStationary.hospitalizationType(xtraStationaryType.getHospitalizationType());
		xXtraStationary.hospitalizationMode(xtraStationaryType.getHospitalizationMode());
		xXtraStationary.clazz(xtraStationaryType.getClazz());
		xXtraStationary.sectionMajor(xtraStationaryType.getSectionMajor());
		xXtraStationary.hasExpenseLoading(xtraStationaryType.isHasExpenseLoading());

		xXtraStationary.admissionType(createXmlGrouperData(xtraStationaryType.getAdmissionType()));
		xXtraStationary.dischargeType(createXmlGrouperData(xtraStationaryType.getDischargeType()));
		xXtraStationary.providerType(createXmlGrouperData(xtraStationaryType.getProviderType()));

		xXtraStationary.bfsResidenceBeforeAdmission(createXmlBfsData(xtraStationaryType.getBfsResidenceBeforeAdmission()));
		xXtraStationary.bfsAdmissionType(createXmlBfsData(xtraStationaryType.getBfsAdmissionType()));
		xXtraStationary.bfsDecisionForDischarge(createXmlBfsData(xtraStationaryType.getBfsDecisionForDischarge()));
		xXtraStationary.bfsResidenceAfterDischarge(createXmlBfsData(xtraStationaryType.getBfsResidenceAfterDischarge()));

		for (final CaseDetailType caseDetail : xtraStationaryType.getCaseDetail())
		{
			xXtraStationary.caseDetail(createXmlCaseDetail(caseDetail));
		}

		return xXtraStationary.build();
	}

	private XmlGrouperData createXmlGrouperData(@NonNull final GrouperDataType grouperDataType)
	{
		final XmlGrouperDataBuilder xGrouperData = XmlGrouperData.builder();

		xGrouperData.name(grouperDataType.getName());
		xGrouperData.number(grouperDataType.getNumber());

		return xGrouperData.build();
	}

	private XmlBfsData createXmlBfsData(@NonNull final BfsDataType bfsDataType)
	{
		final XmlBfsDataBuilder xBfsdata = XmlBfsData.builder();

		xBfsdata.code(bfsDataType.getCode());
		xBfsdata.name(bfsDataType.getName());

		return xBfsdata.build();
	}

	private XmlCaseDetail createXmlCaseDetail(@NonNull final CaseDetailType caseDetailType)
	{
		final XmlCaseDetailBuilder xCaseDetail = XmlCaseDetail.builder();

		xCaseDetail.recordId(caseDetailType.getRecordId());
		xCaseDetail.tariffType(caseDetailType.getTariffType());
		xCaseDetail.code(caseDetailType.getCode());
		xCaseDetail.dateBegin(caseDetailType.getDateBegin());
		xCaseDetail.dateEnd(caseDetailType.getDateEnd());
		xCaseDetail.acid(caseDetailType.getAcid());

		return xCaseDetail.build();
	}

	private ImmutableList<XmlService> createXmlServices(@NonNull final ServicesType servicesType)
	{
		final ImmutableList.Builder<XmlService> xServices = ImmutableList.builder();

		for (final Object service : servicesType.getServiceExOrService())
		{
			xServices.add(createXmlService(service));
		}

		return xServices.build();
	}

	private XmlService createXmlService(@NonNull final Object service)
	{
		final XmlService xService;

		if (service instanceof ServiceType)
		{
			xService = createXmlRecordService((ServiceType)service);
		}
		else if (service instanceof ServiceExType)
		{
			xService = createServiceEx((ServiceExType)service);
		}
		else
		{
			xService = null;
			Check.fail("Unexpected service type={}; service={}", service.getClass(), service);
		}

		return xService;
	}

	private XmlRecordServiceType createXmlRecordService(@NonNull final ServiceType serviceType)
	{
		final XmlRecordServiceTypeBuilder xRecordServiceType = XmlRecordServiceType.builder();

		xRecordServiceType.xtraDrg(createXmlXtraDrg(serviceType.getXtraDrg()));
		xRecordServiceType.xtraDrug(createXmlXtraDrug(serviceType.getXtraDrug()));

		xRecordServiceType.recordId(serviceType.getRecordId());
		xRecordServiceType.tariffType(serviceType.getTariffType());
		xRecordServiceType.code(serviceType.getCode());
		xRecordServiceType.refCode(serviceType.getRefCode());
		xRecordServiceType.name(serviceType.getName());
		xRecordServiceType.session(serviceType.getSession());
		xRecordServiceType.quantity(serviceType.getQuantity());
		xRecordServiceType.dateBegin(serviceType.getDateBegin());
		xRecordServiceType.dateEnd(serviceType.getDateEnd());
		xRecordServiceType.providerId(serviceType.getProviderId());
		xRecordServiceType.responsibleId(serviceType.getResponsibleId());
		xRecordServiceType.unit(serviceType.getUnit());
		xRecordServiceType.unitFactor(serviceType.getUnitFactor());
		xRecordServiceType.externalFactor(serviceType.getExternalFactor());
		xRecordServiceType.amount(serviceType.getAmount());
		xRecordServiceType.vatRate(serviceType.getVatRate());
		xRecordServiceType.obligation(serviceType.isObligation());
		xRecordServiceType.sectionCode(serviceType.getSectionCode());
		xRecordServiceType.remark(serviceType.getRemark());
		xRecordServiceType.serviceAttributes(serviceType.getServiceAttributes());

		return xRecordServiceType.build();
	}

	private XmlXtraDrg createXmlXtraDrg(final XtraDRGType xtraDrg)
	{
		if (xtraDrg == null)
		{
			return null;
		}
		final XmlXtraDrgBuilder xmlXtraDrg = XmlXtraDrg.builder();

		xmlXtraDrg.costFraction(xtraDrg.getCostFraction());

		return xmlXtraDrg.build();
	}

	private XmlServiceEx createServiceEx(@NonNull final ServiceExType serviceExType)
	{
		final XmlServiceEx.XmlServiceExBuilder xmlRecordServiceExBuilder = XmlServiceEx.builder();

		xmlRecordServiceExBuilder.recordId(serviceExType.getRecordId());
		xmlRecordServiceExBuilder.name(serviceExType.getName());
		xmlRecordServiceExBuilder.dateBegin(serviceExType.getDateBegin());
		xmlRecordServiceExBuilder.tariffType(serviceExType.getTariffType());
		xmlRecordServiceExBuilder.code(serviceExType.getCode());
		xmlRecordServiceExBuilder.refCode(serviceExType.getRefCode());
		xmlRecordServiceExBuilder.name(serviceExType.getName());
		xmlRecordServiceExBuilder.session(serviceExType.getSession());
		xmlRecordServiceExBuilder.quantity(serviceExType.getQuantity());
		xmlRecordServiceExBuilder.dateBegin(serviceExType.getDateBegin());
		xmlRecordServiceExBuilder.dateEnd(serviceExType.getDateEnd());
		xmlRecordServiceExBuilder.providerId(serviceExType.getProviderId());
		xmlRecordServiceExBuilder.responsibleId(serviceExType.getResponsibleId());
		xmlRecordServiceExBuilder.billingRole(serviceExType.getBillingRole());
		xmlRecordServiceExBuilder.medicalRole(serviceExType.getMedicalRole());
		xmlRecordServiceExBuilder.bodyLocation(serviceExType.getBodyLocation());
		xmlRecordServiceExBuilder.treatment(serviceExType.getTreatment());
		xmlRecordServiceExBuilder.unitMt(serviceExType.getUnitMt());
		xmlRecordServiceExBuilder.unitFactorMt(serviceExType.getUnitFactorMt());
		xmlRecordServiceExBuilder.scaleFactorMt(serviceExType.getScaleFactorMt());
		xmlRecordServiceExBuilder.externalFactorMt(serviceExType.getExternalFactorMt());
		xmlRecordServiceExBuilder.amountMt(serviceExType.getAmountMt());
		xmlRecordServiceExBuilder.unitTt(serviceExType.getUnitTt());
		xmlRecordServiceExBuilder.unitFactorTt(serviceExType.getUnitFactorTt());
		xmlRecordServiceExBuilder.scaleFactorTt(serviceExType.getScaleFactorTt());
		xmlRecordServiceExBuilder.externalFactorTt(serviceExType.getExternalFactorTt());
		xmlRecordServiceExBuilder.amountTt(serviceExType.getAmountTt());
		xmlRecordServiceExBuilder.amount(serviceExType.getAmount());
		xmlRecordServiceExBuilder.vatRate(serviceExType.getVatRate());
		xmlRecordServiceExBuilder.obligation(serviceExType.isObligation());
		xmlRecordServiceExBuilder.sectionCode(serviceExType.getSectionCode());
		xmlRecordServiceExBuilder.remark(serviceExType.getRemark());
		xmlRecordServiceExBuilder.serviceAttributes(serviceExType.getServiceAttributes());

		return xmlRecordServiceExBuilder.build();
	}

	private XmlXtraDrug createXmlXtraDrug(@Nullable final XtraDrugType xtraDrug)
	{
		if (xtraDrug == null)
		{
			return null;
		}
		final XmlXtraDrugBuilder xXtraDrug = XmlXtraDrug.builder();

		xXtraDrug.indicated(xtraDrug.isIndicated());
		xXtraDrug.iocmCategory(xtraDrug.getIocmCategory());
		xXtraDrug.delivery(xtraDrug.getDelivery());
		xXtraDrug.regulationAttributes(xtraDrug.getRegulationAttributes());
		xXtraDrug.limitation(xtraDrug.isLimitation());

		return xXtraDrug.build();
	}

	private ImmutableList<XmlDocument> createXmlDocuments(@Nullable final DocumentsType documents)
	{
		if (documents == null)
		{
			return ImmutableList.of();
		}

		final ImmutableList.Builder<XmlDocument> xDocuments = ImmutableList.builder();

		for (final DocumentType document : documents.getDocument())
		{
			xDocuments.add(createXmlDocument(document));
		}

		return xDocuments.build();
	}

	private XmlDocument createXmlDocument(@NonNull final DocumentType documentType)
	{
		final XmlDocumentBuilder xDocument = XmlDocument.builder();

		xDocument.filename(documentType.getFilename());
		xDocument.mimeType(documentType.getMimeType());
		xDocument.title(documentType.getTitle());

		xDocument.base64(documentType.getBase64());
		xDocument.url(documentType.getUrl());

		return xDocument.build();
	}

}
