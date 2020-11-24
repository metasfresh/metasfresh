package de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;

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
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.model.commontypes.XmlCompany.XmlCompanyBuilder;
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
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.XmlProcessing;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.XmlRequest;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.XmlPayload.XmlPayloadBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.XmlProcessing.XmlProcessingBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.XmlRequest.XmlRequestBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.XmlBody;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.XmlCredit;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.XmlReminder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.XmlBody.XmlBodyBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.XmlCredit.XmlCreditBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.XmlReminder.XmlReminderBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.XmlBalance;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.XmlDocument;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.XmlEsr;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.XmlLaw;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.XmlProlog;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.XmlService;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.XmlTiers;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.XmlTreatment;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.XmlBalance.XmlBalanceBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.XmlDocument.XmlDocumentBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.XmlLaw.XmlLawBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.XmlProlog.XmlPrologBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.XmlTiers.Tiers;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.XmlTiers.XmlTiersBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.XmlTreatment.XmlTreatmentBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.esr.XmlBank;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.esr.XmlEsr5;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.esr.XmlEsr9;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.esr.XmlEsrRed;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.esr.XmlBank.XmlBankBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.esr.XmlEsr5.XmlEsr5Builder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.esr.XmlEsr9.XmlEsr9Builder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.esr.XmlEsrRed.XmlEsrRedBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.law.XmlIvg;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.law.XmlKvg;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.law.XmlMvg;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.law.XmlOrg;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.law.XmlUvg;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.law.XmlVvg;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.law.XmlIvg.XmlIvgBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.law.XmlKvg.XmlKvgBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.law.XmlMvg.XmlMvgBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.law.XmlOrg.XmlOrgBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.law.XmlUvg.XmlUvgBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.law.XmlVvg.XmlVvgBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.prolog.XmlSoftware;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.prolog.XmlSoftware.XmlSoftwareBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.service.XmlRecordDrg;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.service.XmlRecordDrug;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.service.XmlRecordLab;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.service.XmlRecordMigel;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.service.XmlRecordOther;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.service.XmlRecordParamed;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.service.XmlRecordService;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.service.XmlRecordTarmed;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.service.XmlXtraDrug;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.service.XmlRecordDrg.XmlRecordDrgBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.service.XmlRecordDrug.XmlRecordDrugBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.service.XmlRecordLab.XmlRecordLabBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.service.XmlRecordMigel.XmlRecordMigelBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.service.XmlRecordOther.XmlRecordOtherBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.service.XmlRecordParamed.XmlRecordParamedBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.service.XmlRecordService.XmlRecordServiceBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.service.XmlRecordTarmed.XmlRecordTarmedBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.service.XmlXtraDrug.XmlXtraDrugBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlBiller;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlEmployer;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlGuarantor;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlInsurance;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlPatient;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlPatientCard;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlProvider;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlReferrer;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlBiller.XmlBillerBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlEmployer.XmlEmployerBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlGuarantor.XmlGuarantorBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlInsurance.XmlInsuranceBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlPatient.XmlPatientBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlPatientCard.XmlPatientCardBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlProvider.XmlProviderBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlReferrer.XmlReferrerBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.treatment.XmlBfsData;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.treatment.XmlCaseDetail;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.treatment.XmlGrouperData;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.treatment.XmlXtraAmbulatory;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.treatment.XmlXtraStationary;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.treatment.XmlBfsData.XmlBfsDataBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.treatment.XmlCaseDetail.XmlCaseDetailBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.treatment.XmlGrouperData.XmlGrouperDataBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.treatment.XmlXtraAmbulatory.XmlXtraAmbulatoryBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.treatment.XmlXtraStationary.XmlXtraStationaryBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.vat.XmlVat;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.vat.XmlVatRate;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.vat.XmlVat.XmlVatBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.vat.XmlVatRate.XmlVatRateBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.processing.XmlDemand;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.processing.XmlTransport;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.processing.XmlValidation;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.processing.XmlValidationResult;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.processing.XmlDemand.XmlDemandBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.processing.XmlTransport.XmlTransportBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.processing.XmlTransport.XmlVia;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.processing.XmlTransport.XmlVia.XmlViaBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.processing.XmlValidation.XmlValidationBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.processing.XmlValidationResult.XmlValidationResultBuilder;
import lombok.NonNull;

/*
 * #%L
 * vertical-healthcare_ch.invoice_gateway.forum_datenaustausch_ch.invoice_commons
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

public class Invoice440ToCrossVersionModelTool
{
	public static final Invoice440ToCrossVersionModelTool INSTANCE = new Invoice440ToCrossVersionModelTool();

	private Invoice440ToCrossVersionModelTool()
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
		xProcessing.printPatientCopy(processingType.isPrintPatientCopy());

		xProcessing.transport(createXmlTransport(processingType.getTransport()));

		xProcessing.validations(createXmlValidations(processingType.getValidations()));

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

	private ImmutableList<XmlValidation> createXmlValidations(@Nullable final ValidationsType validations)
	{
		if (validations == null)
		{
			return ImmutableList.of();
		}
		final ImmutableList.Builder<XmlValidation> xValidations = ImmutableList.builder();

		for (final ValidationType validation : validations.getValidation())
		{
			xValidations.add(createXmlValidation(validation));
		}

		return xValidations.build();
	}

	private XmlValidation createXmlValidation(@Nullable final ValidationType validationType)
	{
		if (validationType == null)
		{
			return null;
		}

		final XmlValidationBuilder xValidation = XmlValidation.builder();

		xValidation.originator(validationType.getOriginator());
		xValidation.remark(validationType.getRemark());
		xValidation.status(validationType.getStatus());

		for (final ValidationResultType validationResult : validationType.getValidationResult())
		{
			xValidation.validationresult(createXmlValidationResult(validationResult));
		}

		return xValidation.build();
	}

	private XmlValidationResult createXmlValidationResult(@NonNull final ValidationResultType validationResultType)
	{
		final XmlValidationResultBuilder xValidationResult = XmlValidationResult.builder();

		xValidationResult.remark(validationResultType.getRemark());
		xValidationResult.recordId(validationResultType.getRecordId());
		xValidationResult.costUnit(validationResultType.getCostUnit());
		xValidationResult.status(validationResultType.getStatus());

		return xValidationResult.build();
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
		xPayload.creditAdvice(payloadType.isCreditAdvice());

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
		xCredit.requestTimestamp(creditType.getRequestTimestamp());
		xCredit.requestId(creditType.getRequestId());

		return xCredit.build();
	}

	private XmlInvoice createXmlInvoice(@NonNull final InvoiceType invoiceType)
	{
		final XmlInvoiceBuilder xInvoice = XmlInvoice.builder();

		xInvoice.requestTimestamp(invoiceType.getRequestTimestamp());
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

		xReminder.requestTimestamp(reminderType.getRequestTimestamp());
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

		xBody.balance(createXmlBalance(bodyType.getBalance()));

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

	private XmlBalance createXmlBalance(@NonNull final BalanceType balanceType)
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
		if (bodyType.getEsr5() != null)
		{
			return createXmlEsr5(bodyType.getEsr5());
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

	private XmlEsr5 createXmlEsr5(@NonNull final Esr5Type esr5Type)
	{
		final XmlEsr5Builder xEsr5 = XmlEsr5.builder();

		xEsr5.type(esr5Type.getType());

		xEsr5.participantNumber(esr5Type.getParticipantNumber());
		xEsr5.referenceNumber(esr5Type.getReferenceNumber());
		xEsr5.codingLine(esr5Type.getCodingLine());

		xEsr5.bank(createXmlBank(esr5Type.getBank()));

		return xEsr5.build();
	}

	private XmlEsr9 createXmlEsr9(@NonNull final Esr9Type esr9Type)
	{
		final XmlEsr9Builder xEsr9 = XmlEsr9.builder();

		xEsr9.type(esr9Type.getType());

		xEsr9.participantNumber(esr9Type.getParticipantNumber());
		xEsr9.referenceNumber(esr9Type.getReferenceNumber());
		xEsr9.codingLine(esr9Type.getCodingLine());

		xEsr9.bank(createXmlBank(esr9Type.getBank()));

		return xEsr9.build();
	}

	private XmlEsrRed createXmlEsrRed(@NonNull final EsrRedType esrRedType)
	{
		final XmlEsrRedBuilder xEsrRed = XmlEsrRed.builder();

		xEsrRed.paymentTo(esrRedType.getPaymentTo());
		xEsrRed.esrAttributes(esrRedType.getEsrAttributes());

		xEsrRed.postAccount(esrRedType.getPostAccount());
		xEsrRed.iban(esrRedType.getIban());
		xEsrRed.referenceNumber(esrRedType.getReferenceNumber());
		xEsrRed.codingLine1(esrRedType.getCodingLine1());
		xEsrRed.codingLine2(esrRedType.getCodingLine2());

		xEsrRed.bank(createXmlBank(esrRedType.getBank()));

		return xEsrRed.build();
	}

	private XmlBank createXmlBank(@Nullable final EsrAddressType esrAddressType)
	{
		if (esrAddressType == null)
		{
			return null;
		}
		final XmlBankBuilder xBank = XmlBank.builder();

		xBank.company(createXmlCompany(esrAddressType.getCompany()));
		xBank.person(createXmlPerson(esrAddressType.getPerson()));

		return xBank.build();
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
		xtiers.provider(createXmlProvider(garantType.getProvider()));
		xtiers.insurance(createXmlInsurance(garantType.getInsurance()));
		xtiers.patient(createXmlPatient(garantType.getPatient()));
		xtiers.insured(createXmlPatientOrNull(garantType.getInsured()));
		xtiers.guarantor(createXmlGuarantor(garantType.getGuarantor()));
		xtiers.referrer(createXmlReferrer(garantType.getReferrer()));
		xtiers.employer(createXmlEmployer(garantType.getEmployer()));

		return xtiers.build();
	}

	private XmlTiers createXmlTiers(@NonNull final PayantType payantType)
	{
		final XmlTiersBuilder xtiers = XmlTiers.builder();
		xtiers.type(Tiers.PAYANT);

		xtiers.paymentPeriod(payantType.getPaymentPeriod());
		xtiers.biller(createXmlBiller(payantType.getBiller()));
		xtiers.provider(createXmlProvider(payantType.getProvider()));
		xtiers.insurance(createXmlInsurance(payantType.getInsurance()));
		xtiers.patient(createXmlPatient(payantType.getPatient()));
		xtiers.insured(createXmlPatientOrNull(payantType.getInsured()));
		xtiers.guarantor(createXmlGuarantor(payantType.getGuarantor()));
		xtiers.referrer(createXmlReferrer(payantType.getReferrer()));
		xtiers.employer(createXmlEmployer(payantType.getEmployer()));

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
		xKvg.contractNumber(kvgLawType.getContractNumber());

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
		xVvg.contractNumber(vvgLawType.getContractNumber());

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
		xUvg.contractNumber(uvgLawType.getContractNumber());
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
		xIvg.contractNumber(ivgLawType.getContractNumber());
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
		xMvg.contractNumber(mvgLawType.getContractNumber());
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
		xOrg.contractNumber(orgLawType.getContractNumber());

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

		xTreatment.xtraAmbulatory(createXmlXtraAmbulatory(treatmentType.getXtraHospital()));
		xTreatment.xtraStationary(createXmlXtraStationary(treatmentType.getXtraHospital()));

		return xTreatment.build();
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
		xXtraStationary.doCostAssessment(xtraStationaryType.isDoCostAssessment());

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

		for (final Object service : servicesType.getRecordTarmedOrRecordDrgOrRecordLab())
		{
			xServices.add(createXmlService(service));
		}

		return xServices.build();
	}

	private XmlService createXmlService(@NonNull final Object service)
	{
		final XmlService xService;

		if (service instanceof RecordTarmedType)
		{
			xService = createXmlRecordTarmed((RecordTarmedType)service);
		}
		else if (service instanceof RecordDRGType)
		{
			xService = createXmlRecordDrg((RecordDRGType)service);
		}
		else if (service instanceof RecordLabType)
		{
			xService = createXmlRecordLab((RecordLabType)service);
		}
		else if (service instanceof RecordMigelType)
		{
			xService = createXmlRecordMigel((RecordMigelType)service);
		}
		else if (service instanceof RecordParamedType)
		{
			xService = createXmlRecordParamed((RecordParamedType)service);
		}
		else if (service instanceof RecordDrugType)
		{
			xService = createRecordXmlRecordDrug((RecordDrugType)service);
		}
		else if (service instanceof RecordOtherType)
		{
			xService = createRecordXmlRecordOther((RecordOtherType)service);
		}
		else
		{
			xService = null;
			Check.fail("Unexpected service type={}; service={}", service.getClass(), service);
		}

		return xService;
	}

	private XmlRecordTarmed createXmlRecordTarmed(@NonNull final RecordTarmedType recordTarmedType)
	{
		final XmlRecordTarmedBuilder xRecordTarmed = XmlRecordTarmed.builder();

		xRecordTarmed.recordId(recordTarmedType.getRecordId());
		xRecordTarmed.name(recordTarmedType.getName());
		xRecordTarmed.dateBegin(recordTarmedType.getDateBegin());
		xRecordTarmed.tariffType(recordTarmedType.getTariffType());
		xRecordTarmed.code(recordTarmedType.getCode());
		xRecordTarmed.refCode(recordTarmedType.getRefCode());
		xRecordTarmed.name(recordTarmedType.getName());
		xRecordTarmed.session(recordTarmedType.getSession());
		xRecordTarmed.quantity(recordTarmedType.getQuantity());
		xRecordTarmed.dateBegin(recordTarmedType.getDateBegin());
		xRecordTarmed.dateEnd(recordTarmedType.getDateEnd());
		xRecordTarmed.providerId(recordTarmedType.getProviderId());
		xRecordTarmed.responsibleId(recordTarmedType.getResponsibleId());
		xRecordTarmed.billingRole(recordTarmedType.getBillingRole());
		xRecordTarmed.medicalRole(recordTarmedType.getMedicalRole());
		xRecordTarmed.bodyLocation(recordTarmedType.getBodyLocation());
		xRecordTarmed.treatment(recordTarmedType.getTreatment());
		xRecordTarmed.unitMt(recordTarmedType.getUnitMt());
		xRecordTarmed.unitFactorMt(recordTarmedType.getUnitFactorMt());
		xRecordTarmed.scaleFactorMt(recordTarmedType.getScaleFactorMt());
		xRecordTarmed.externalFactorMt(recordTarmedType.getExternalFactorMt());
		xRecordTarmed.amountMt(recordTarmedType.getAmountMt());
		xRecordTarmed.unitTt(recordTarmedType.getUnitTt());
		xRecordTarmed.unitFactorTt(recordTarmedType.getUnitFactorTt());
		xRecordTarmed.scaleFactorTt(recordTarmedType.getScaleFactorTt());
		xRecordTarmed.externalFactorTt(recordTarmedType.getExternalFactorTt());
		xRecordTarmed.amountTt(recordTarmedType.getAmountTt());
		xRecordTarmed.amount(recordTarmedType.getAmount());
		xRecordTarmed.vatRate(recordTarmedType.getVatRate());
		xRecordTarmed.validate(recordTarmedType.isValidate());
		xRecordTarmed.obligation(recordTarmedType.isObligation());
		xRecordTarmed.sectionCode(recordTarmedType.getSectionCode());
		xRecordTarmed.remark(recordTarmedType.getRemark());
		xRecordTarmed.serviceAttributes(recordTarmedType.getServiceAttributes());

		return xRecordTarmed.build();
	}

	private XmlRecordDrg createXmlRecordDrg(@NonNull final RecordDRGType recordDRGType)
	{
		final XmlRecordDrgBuilder xRecordDrg = XmlRecordDrg.builder();

		xRecordDrg.recordService(createXmlRecordService(recordDRGType));

		xRecordDrg.tariffType(recordDRGType.getTariffType());
		xRecordDrg.costFraction(recordDRGType.getCostFraction());

		return xRecordDrg.build();
	}

	private XmlRecordLab createXmlRecordLab(@NonNull final RecordLabType recordLabType)
	{
		final XmlRecordLabBuilder xRecordLab = XmlRecordLab.builder();

		xRecordLab.recordService(createXmlRecordService(recordLabType));

		xRecordLab.tariffType(recordLabType.getTariffType());

		return xRecordLab.build();
	}

	private XmlRecordMigel createXmlRecordMigel(@NonNull final RecordMigelType recordMigelType)
	{
		final XmlRecordMigelBuilder xRecordMigel = XmlRecordMigel.builder();

		xRecordMigel.recordService(createXmlRecordService(recordMigelType));

		xRecordMigel.tariffType(recordMigelType.getTariffType());

		return xRecordMigel.build();
	}

	private XmlRecordParamed createXmlRecordParamed(@NonNull final RecordParamedType recordParamedType)
	{
		final XmlRecordParamedBuilder xRecordParamed = XmlRecordParamed.builder();

		xRecordParamed.recordService(createXmlRecordService(recordParamedType));

		xRecordParamed.tariffType(recordParamedType.getTariffType());

		return xRecordParamed.build();
	}

	private XmlRecordDrug createRecordXmlRecordDrug(@NonNull final RecordDrugType recordDrugType)
	{
		final XmlRecordDrugBuilder xRecordDrug = XmlRecordDrug.builder();

		xRecordDrug.recordService(createXmlRecordService(recordDrugType));

		xRecordDrug.tariffType(recordDrugType.getTariffType());
		xRecordDrug.xtraDrug(createXmlXtraDrug(recordDrugType.getXtraDrug()));

		return xRecordDrug.build();
	}

	private XmlXtraDrug createXmlXtraDrug(@Nullable final XtraDrug xtraDrug)
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

	private XmlRecordOther createRecordXmlRecordOther(@NonNull final RecordOtherType recordOtherType)
	{
		final XmlRecordOtherBuilder xRecordOther = XmlRecordOther.builder();

		xRecordOther.recordService(createXmlRecordService(recordOtherType));

		xRecordOther.tariffType(recordOtherType.getTariffType());

		return xRecordOther.build();
	}

	private XmlRecordService createXmlRecordService(@NonNull final RecordServiceType recordServiceType)
	{
		final XmlRecordServiceBuilder xRecordService = XmlRecordService.builder();

		xRecordService.recordId(recordServiceType.getRecordId());
		xRecordService.name(recordServiceType.getName());
		xRecordService.dateBegin(recordServiceType.getDateBegin());
		xRecordService.code(recordServiceType.getCode());
		xRecordService.refCode(recordServiceType.getRefCode());
		xRecordService.name(recordServiceType.getName());
		xRecordService.session(recordServiceType.getSession());
		xRecordService.quantity(recordServiceType.getQuantity());
		xRecordService.dateBegin(recordServiceType.getDateBegin());
		xRecordService.dateEnd(recordServiceType.getDateEnd());
		xRecordService.providerId(recordServiceType.getProviderId());
		xRecordService.responsibleId(recordServiceType.getResponsibleId());
		xRecordService.unit(recordServiceType.getUnit());
		xRecordService.unitFactor(recordServiceType.getUnitFactor());
		xRecordService.externalFactor(recordServiceType.getExternalFactor());
		xRecordService.amount(recordServiceType.getAmount());
		xRecordService.vatRate(recordServiceType.getVatRate());
		xRecordService.validate(recordServiceType.isValidate());
		xRecordService.obligation(recordServiceType.isObligation());
		xRecordService.sectionCode(recordServiceType.getSectionCode());
		xRecordService.remark(recordServiceType.getRemark());
		xRecordService.serviceAttributes(recordServiceType.getServiceAttributes());

		return xRecordService.build();
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
		xDocument.viewer(documentType.getViewer());

		xDocument.base64(documentType.getBase64());
		xDocument.url(documentType.getUrl());

		return xDocument.build();
	}

}
