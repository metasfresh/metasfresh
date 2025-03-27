/*
 * #%L
 * de-metas-camel-edi
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.edi.esb.invoicexport.edifact;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.CoalesceUtil;
import de.metas.edi.esb.commons.SystemTime;
import de.metas.edi.esb.commons.Util;
import de.metas.edi.esb.jaxb.metasfreshinhousev2.EDICctop119VType;
import de.metas.edi.esb.jaxb.metasfreshinhousev2.EDICctopInvoicVType;
import jakarta.xml.bind.JAXBElement;
import lombok.NonNull;
import org.smooks.edifact.binding.d01b.BGMBeginningOfMessage;
import org.smooks.edifact.binding.d01b.C002DocumentMessageName;
import org.smooks.edifact.binding.d01b.C056DepartmentOrEmployeeDetails;
import org.smooks.edifact.binding.d01b.C059Street;
import org.smooks.edifact.binding.d01b.C076CommunicationContact;
import org.smooks.edifact.binding.d01b.C080PartyName;
import org.smooks.edifact.binding.d01b.C082PartyIdentificationDetails;
import org.smooks.edifact.binding.d01b.C106DocumentMessageIdentification;
import org.smooks.edifact.binding.d01b.C186QuantityDetails;
import org.smooks.edifact.binding.d01b.C212ItemNumberIdentification;
import org.smooks.edifact.binding.d01b.C241DutyTaxFeeType;
import org.smooks.edifact.binding.d01b.C243DutyTaxFeeDetail;
import org.smooks.edifact.binding.d01b.C270Control;
import org.smooks.edifact.binding.d01b.C273ItemDescription;
import org.smooks.edifact.binding.d01b.C504CurrencyDetails;
import org.smooks.edifact.binding.d01b.C506Reference;
import org.smooks.edifact.binding.d01b.C507DateTimePeriod;
import org.smooks.edifact.binding.d01b.C509PriceInformation;
import org.smooks.edifact.binding.d01b.C516MonetaryAmount;
import org.smooks.edifact.binding.d01b.CNTControlTotal;
import org.smooks.edifact.binding.d01b.COMCommunicationContact;
import org.smooks.edifact.binding.d01b.CTAContactInformation;
import org.smooks.edifact.binding.d01b.CUXCurrencies;
import org.smooks.edifact.binding.d01b.DTMDateTimePeriod;
import org.smooks.edifact.binding.d01b.E1153ReferenceCodeQualifier;
import org.smooks.edifact.binding.d01b.E3035PartyFunctionCodeQualifier;
import org.smooks.edifact.binding.d01b.E3139ContactFunctionCode;
import org.smooks.edifact.binding.d01b.E3155CommunicationAddressCodeQualifier;
import org.smooks.edifact.binding.d01b.E5125PriceCodeQualifier;
import org.smooks.edifact.binding.d01b.E5153DutyOrTaxOrFeeTypeNameCode;
import org.smooks.edifact.binding.d01b.E5305DutyOrTaxOrFeeCategoryCode;
import org.smooks.edifact.binding.d01b.E5387PriceSpecificationCode;
import org.smooks.edifact.binding.d01b.E7077DescriptionFormatCode;
import org.smooks.edifact.binding.d01b.E7143ItemTypeIdentificationCode;
import org.smooks.edifact.binding.d01b.IMDItemDescription;
import org.smooks.edifact.binding.d01b.INVOIC;
import org.smooks.edifact.binding.d01b.Interchange;
import org.smooks.edifact.binding.d01b.LINLineItem;
import org.smooks.edifact.binding.d01b.MOAMonetaryAmount;
import org.smooks.edifact.binding.d01b.Message;
import org.smooks.edifact.binding.d01b.NADNameAndAddress;
import org.smooks.edifact.binding.d01b.PATPaymentTermsBasis;
import org.smooks.edifact.binding.d01b.PIAAdditionalProductId;
import org.smooks.edifact.binding.d01b.PRIPriceDetails;
import org.smooks.edifact.binding.d01b.QTYQuantity;
import org.smooks.edifact.binding.d01b.RFFReference;
import org.smooks.edifact.binding.d01b.TAXDutyTaxFeeDetails;
import org.smooks.edifact.binding.service.E0001SyntaxIdentifier;
import org.smooks.edifact.binding.service.E0051ControllingAgencyCoded;
import org.smooks.edifact.binding.service.E0065MessageType;
import org.smooks.edifact.binding.service.E0081SectionIdentification;
import org.smooks.edifact.binding.service.S001SyntaxIdentifier;
import org.smooks.edifact.binding.service.S002InterchangeSender;
import org.smooks.edifact.binding.service.S003InterchangeRecipient;
import org.smooks.edifact.binding.service.S004DateAndTimeOfPreparation;
import org.smooks.edifact.binding.service.S005RecipientReferencePasswordDetails;
import org.smooks.edifact.binding.service.S009MessageIdentifier;
import org.smooks.edifact.binding.service.UNA;
import org.smooks.edifact.binding.service.UNBInterchangeHeader;
import org.smooks.edifact.binding.service.UNHMessageHeader;
import org.smooks.edifact.binding.service.UNSSectionControl;
import org.smooks.edifact.binding.service.UNTMessageTrailer;
import org.smooks.edifact.binding.service.UNZInterchangeTrailer;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

public class EDICctopInvoicVtoD01BConverter
{
	@NonNull
	public Interchange convert(@NonNull final EDICctopInvoicVType xmlCctopInvoice)
	{
		final String messageSequenceNo = xmlCctopInvoice.getSequenceNoAttr().toString();

		final String dateTimeInvoiced = Util.toFormattedStringDate(Util.toDate(xmlCctopInvoice.getDateInvoiced()), "yyyyMMddHHmmss");
		final String dateTimeAcct = Util.toFormattedStringDate(Util.toDate(
				CoalesceUtil.coalesceNotNull(xmlCctopInvoice.getDateAcct(), xmlCctopInvoice.getDateInvoiced())), "yyyyMMddHHmmss");
		final String dateTimeDelivered = Util.toFormattedStringDate(Util.toDate(xmlCctopInvoice.getMovementDate()), "yyyyMMddHHmmss");
		final Map<String, EDICctop119VType> locationType2Address = xmlCctopInvoice.getEDICctop119V().stream()
				.collect(Collectors.toMap(
						EDICctop119VType::getEancomLocationtype,
						ediCctop -> ediCctop));

		return new Interchange().
				withUNA(getUNA()).
				withUNB( // https://unece.org/trade/uncefact/unedifact/part-4-Annex-B
						getUNBInterchangeHeader(xmlCctopInvoice,
								messageSequenceNo,
								locationType2Address.get(E3035PartyFunctionCodeQualifier.BY.value()),
								locationType2Address.get(E3035PartyFunctionCodeQualifier.SU.value()))).
				withMessage(new Message().
						withContent(new JAXBElement<>(new QName("UNH"), UNHMessageHeader.class, new UNHMessageHeader().
								withE0062("1").
								withS009(getS009MessageIdentifier()))).
						withContent(new JAXBElement<>(new QName("http://www.ibm.com/dfdl/edi/un/edifact/D01B", "INVOIC", "D01B"), INVOIC.class, new INVOIC().
								withBGM(new BGMBeginningOfMessage() /* https://service.unece.org/trade/untdid/d01b/trsd/trsdbgm.htm */.
										withC002(new C002DocumentMessageName().withE1001("380")).
										withC106(new C106DocumentMessageIdentification()
												.withE1004(xmlCctopInvoice.getInvoiceDocumentno())).
										withE1225("9") /* Message function code = Original*/).
								withDTM(getDTMDateTimePeriod("137",    /*Document/message date/time*/
										dateTimeInvoiced)).
								withDTM(getDTMDateTimePeriod("454", /*Accounting value date*/
										dateTimeAcct)).
								withDTM(getDTMDateTimePeriod("35", /*Delivery date/time, actual*/
										dateTimeDelivered)).
								withSegGrp1(getSegGrp1(xmlCctopInvoice)).
								withSegGrp2(getSegGrp2(E3035PartyFunctionCodeQualifier.BY, locationType2Address.get(E3035PartyFunctionCodeQualifier.BY.value()))).
								withSegGrp2(getSegGrp2(E3035PartyFunctionCodeQualifier.IV, locationType2Address.get(E3035PartyFunctionCodeQualifier.IV.value()))).
								withSegGrp2(getSegGrp2(E3035PartyFunctionCodeQualifier.DP, locationType2Address.get(E3035PartyFunctionCodeQualifier.DP.value()))).
								withSegGrp2(getSegGrp2withSegGrp5(E3035PartyFunctionCodeQualifier.SU, locationType2Address.get(E3035PartyFunctionCodeQualifier.SU.value()))).
								withSegGrp2(getSegGrp2withSegGrp5(E3035PartyFunctionCodeQualifier.II, locationType2Address.get(E3035PartyFunctionCodeQualifier.SU.value()))).
								withSegGrp6(getSegGrp6(xmlCctopInvoice)).
								withSegGrp7(getSegGrp7(xmlCctopInvoice)).
								withSegGrp8(getSegGrp8(dateTimeDelivered)).
								withSegGrp26(getSegGrp26(xmlCctopInvoice)).
								withSegGrp50(getSegGrp50("77", // Invoice Amount - Total amount to be paid
										xmlCctopInvoice.getGrandTotal())).
								withSegGrp50(getSegGrp50("176", // Invoice Tax Amount
										xmlCctopInvoice.getTotalVat())).
								withSegGrp52(getSegGrp52(xmlCctopInvoice)).
								withUNS(new UNSSectionControl().
										withE0081(E0081SectionIdentification.S)).
								withCNT(new CNTControlTotal().withC270(new C270Control().
										withE6069("2").
										withE6066(BigDecimal.ONE)))
						)).
						withContent(new JAXBElement<>(new QName("UNT"), UNTMessageTrailer.class, new UNTMessageTrailer().
								withE0074(new BigDecimal(45)).
								withE0062("1")))).
				withUNZ(new UNZInterchangeTrailer().
						withE0036(new BigDecimal(1)).
						withE0020(messageSequenceNo));
	}

	@NonNull
	private ImmutableList<INVOIC.SegGrp52> getSegGrp52(@NonNull final EDICctopInvoicVType xmlCctopInvoice)
	{
		return xmlCctopInvoice
				.getEDICctopInvoic500V()
				.stream()
				.map(ediCctopInvoic500VType -> new INVOIC.SegGrp52().
						withTAX(new TAXDutyTaxFeeDetails().
								withE5283("7").
								withC241(new C241DutyTaxFeeType().
										withE5153(E5153DutyOrTaxOrFeeTypeNameCode.VAT)).
								withC243(new C243DutyTaxFeeDetail().
										withE5278(String.valueOf(ediCctopInvoic500VType.getRate())))).
						withMOA(new MOAMonetaryAmount().
								withC516(new C516MonetaryAmount().
										withE5025("124").
										withE5004(ediCctopInvoic500VType.getTaxAmtInfo()))).
						withMOA(new MOAMonetaryAmount().
								withC516(new C516MonetaryAmount().
										withE5025("125").
										withE5004(xmlCctopInvoice.getTotalTaxBaseAmt()))))
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private INVOIC.SegGrp50 getSegGrp50(@NonNull final String e5025, @Nullable final BigDecimal e5004)
	{
		return new INVOIC.SegGrp50().
				withMOA(new MOAMonetaryAmount().
						withC516(new C516MonetaryAmount().
								withE5025(e5025).
								withE5004(e5004)));
	}

	@NonNull
	private ImmutableList<INVOIC.SegGrp26> getSegGrp26(@NonNull final EDICctopInvoicVType xmlCctopInvoice)
	{
		return xmlCctopInvoice
				.getEDICctopInvoic500V()
				.stream()
				.map(ediCctopInvoic500VType -> new INVOIC.SegGrp26().
						withLIN(new LINLineItem().
								withE1082(String.valueOf(ediCctopInvoic500VType.getLine()))).
						withPIA(new PIAAdditionalProductId().
								withE4347("5").
								withC212(new C212ItemNumberIdentification().
										withE7140(ediCctopInvoic500VType.getCustomerProductNo()).
										withE7143(E7143ItemTypeIdentificationCode.IN))).
						withIMD(new IMDItemDescription().
								withE7077(E7077DescriptionFormatCode.F).
								withC273(new C273ItemDescription().
										withE7009("CU").
										withE3055("9").
										withE7008(ediCctopInvoic500VType.getName2() != null
												? ediCctopInvoic500VType.getName().concat(ediCctopInvoic500VType.getName2())
												: ediCctopInvoic500VType.getName()).
										withE3453("DE"))).
						withQTY(new QTYQuantity().
								withC186(new C186QuantityDetails().
										withE6063("47") /* ORDERED QTY */.
										withE6060(String.valueOf(ediCctopInvoic500VType.getQtyInvoiced())).
										withE6411(ediCctopInvoic500VType.getEanComUOM()))).
						withSegGrp27(new INVOIC.SegGrp26.SegGrp27().withMOA(new MOAMonetaryAmount().
								withC516(new C516MonetaryAmount().
										withE5025("203").
										withE5004(xmlCctopInvoice.getTotalLines())))).
						withSegGrp29(new INVOIC.SegGrp26.SegGrp29().
								withPRI(new PRIPriceDetails().
										withC509(new C509PriceInformation().
												withE5125(E5125PriceCodeQualifier.AAA).
												withE5118(ediCctopInvoic500VType.getPriceActual()).
												withE5387(E5387PriceSpecificationCode.NTP).
												withE5284(BigDecimal.ONE).
												withE6411(ediCctopInvoic500VType.getEanComPriceUOM())))).
						withSegGrp30(new INVOIC.SegGrp26.SegGrp30().withRFF(new RFFReference().
								withC506(new C506Reference().
										withE1153(E1153ReferenceCodeQualifier.ON).
										withE1154(xmlCctopInvoice.getPOReference()).
										withE1156(String.valueOf(ediCctopInvoic500VType.getExternalSeqNo()))))).
						withSegGrp34(new INVOIC.SegGrp26.SegGrp34().
								withTAX(new TAXDutyTaxFeeDetails().
										withE5283("7").    // TAX TYPE CODE: 7 means Value-Added Tax (VAT)
												withC241(new C241DutyTaxFeeType().withE5153(E5153DutyOrTaxOrFeeTypeNameCode.VAT)).
										withC243(new C243DutyTaxFeeDetail().
												withE5278(String.valueOf(ediCctopInvoic500VType.getRate())))).
								withMOA(new MOAMonetaryAmount().
										withC516(new C516MonetaryAmount().
												withE5025("124").    // Tax Amount - Total Tax amount
														withE5004(xmlCctopInvoice.getTotalVat()))).
								withMOA(new MOAMonetaryAmount().
										withC516(new C516MonetaryAmount().
												withE5025("125").    // Taxable Amount
														withE5004(xmlCctopInvoice.getTotalTaxBaseAmt())))))
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private ImmutableList<INVOIC.SegGrp6> getSegGrp6(@NonNull final EDICctopInvoicVType xmlCctopInvoice)
	{
		return xmlCctopInvoice
				.getEDICctop901991V()
				.stream()
				.map(cctop901991V -> new INVOIC.SegGrp6().
						withTAX(new TAXDutyTaxFeeDetails().
								withE5283("7").    // TAX TYPE CODE: 7 means Value-Added Tax (VAT)
										withC241(new C241DutyTaxFeeType().withE5153(E5153DutyOrTaxOrFeeTypeNameCode.VAT)).
								withC243(new C243DutyTaxFeeDetail().
										withE5278(String.valueOf(cctop901991V.getRate()))).
								withE5305(E5305DutyOrTaxOrFeeCategoryCode.S)))
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private INVOIC.SegGrp8 getSegGrp8(@NonNull final String e2380)
	{
		return new INVOIC.SegGrp8().
				withPAT(new PATPaymentTermsBasis().
						/* Payment method code - 3 typically refers to direct debit */
								withE4279("3")).
				withDTM(new DTMDateTimePeriod().
						withC507(new C507DateTimePeriod().
								/* Date/Time qualifier - "Terms net due date" (The date by which the payment must be made) */
										withE2005("13").
								withE2380(e2380).
								/* Date or time or period format code = CCYYMMDDHHMMSS */
										withE2379("204")));
	}

	@NonNull
	private INVOIC.SegGrp7 getSegGrp7(@NonNull final EDICctopInvoicVType xmlCctopInvoice)
	{
		return new INVOIC.SegGrp7().
				withCUX(new CUXCurrencies().
						withC504(new C504CurrencyDetails().
								withE6347("2").
								withE6345(xmlCctopInvoice.getISOCode()).
								withE6343("4")));    // ISO 4217 currency qualifier - indicates the number of decimal places used
	}

	@NonNull
	private INVOIC.SegGrp2 getSegGrp2withSegGrp5(
			@NonNull final E3035PartyFunctionCodeQualifier e3035,
			@NonNull final EDICctop119VType ediCctop11SUType)
	{
		return getSegGrp2(e3035, ediCctop11SUType).
				withSegGrp5(new INVOIC.SegGrp2.SegGrp5().
						withCTA(new CTAContactInformation().
								withE3139(E3139ContactFunctionCode.IC).
								withC056(new C056DepartmentOrEmployeeDetails().withE3412(ediCctop11SUType.getContact()))).
						withCOM(new COMCommunicationContact().
								withC076(new C076CommunicationContact().
										withE3148("intercheese-billing@metasfresh.com").    // extract email from ADUser?
												withE3155(E3155CommunicationAddressCodeQualifier.EM))).
						withCOM(new COMCommunicationContact().
								withC076(new C076CommunicationContact().
										withE3148(ediCctop11SUType.getPhone()).
										withE3155(E3155CommunicationAddressCodeQualifier.TE))));
	}

	@NonNull
	private INVOIC.SegGrp2 getSegGrp2(
			@NonNull final E3035PartyFunctionCodeQualifier e3035,
			@NonNull final EDICctop119VType ediCctop119ByType)
	{
		return new INVOIC.SegGrp2().
				withNAD(new NADNameAndAddress().
						withE3035(e3035).
						withC082(new C082PartyIdentificationDetails().
								withE3039(ediCctop119ByType.getGLN()).
								withE3055("9"))
						.withC080(new C080PartyName().withE3036(ediCctop119ByType.getName()))
						.withC059(new C059Street().withE3042(ediCctop119ByType.getAddress2() != null
								? ediCctop119ByType.getAddress1().concat(ediCctop119ByType.getAddress2())
								: ediCctop119ByType.getAddress1()))
						.withE3164(ediCctop119ByType.getCity())
						.withE3251(ediCctop119ByType.getPostal())
						.withE3207(ediCctop119ByType.getCountryCode()))
				.withSegGrp3(new INVOIC.SegGrp2.SegGrp3()
						.withRFF(new RFFReference().
								withC506(new C506Reference().
										withE1153(E1153ReferenceCodeQualifier.VA).
										withE1154(ediCctop119ByType.getVATaxID()))));
	}

	@NonNull
	private INVOIC.SegGrp1 getSegGrp1(@NonNull final EDICctopInvoicVType xmlCctopInvoice)
	{
		final String dateTimeOrdered = Util.toFormattedStringDate(Util.toDate(xmlCctopInvoice.getDateOrdered()), "yyyyMMddHHmmss");

		return new INVOIC.SegGrp1().
				withRFF(new RFFReference() /* https://service.unece.org/trade/untdid/d01b/trsd/trsdrff.htm */.
						withC506(new C506Reference().
								withE1153(E1153ReferenceCodeQualifier.ON /*Order Number*/).
								withE1154(xmlCctopInvoice.getPOReference()))).
				withDTM(new DTMDateTimePeriod() /* https://service.unece.org/trade/untdid/d01b/trsd/trsddtm.htm */.
						withC507(new C507DateTimePeriod().
								withE2005("171") /*Reference date/time*/.
								withE2380(dateTimeOrdered).
								withE2379("204") /*Date or time or period format code = CCYYMMDDHHMMSS */));
	}

	@NonNull
	private DTMDateTimePeriod getDTMDateTimePeriod(
			@NonNull final String e2005,
			@NonNull final String e2380)
	{
		return new DTMDateTimePeriod().withC507(new C507DateTimePeriod().    /* https://service.unece.org/trade/untdid/d01b/trsd/trsddtm.htm */
				withE2005(e2005) /*Document/message date/time*/.
				withE2380(e2380).
				withE2379("204")); /*Date or time or period format code = CCYYMMDDHHMMSS */
	}

	@NonNull
	private UNA getUNA()
	{
		return new UNA().
				withCompositeSeparator(":").
				withFieldSeparator("+").
				withDecimalSeparator(".").
				withEscapeCharacter("?").
				withRepeatSeparator("*").
				withSegmentTerminator("'");
	}

	@NonNull
	private UNBInterchangeHeader getUNBInterchangeHeader(
			@NonNull final EDICctopInvoicVType xmlCctopInvoice,
			@NonNull final String messageSequenceNo,
			@NonNull final EDICctop119VType ediCctop119ByType,
			@NonNull final EDICctop119VType ediCctop11SUType)
	{
		final Date ediSendDate = SystemTime.asDate();
		final String dateSend = Util.toFormattedStringDate(ediSendDate, "yyyyMMdd");
		final String timeSend = Util.toFormattedStringDate(ediSendDate, "HHmm");
		final BigDecimal testIndicator = null; // new BigDecimal("1");
		final String interchangeReceipientGLN = "7611937000723"; // inclear why it's not xmlCctopInvoice.getReceivergln()

		// EANCOM = EAN + Communication 
		final String communicationsAgreement = "EANCOM" + ediCctop119ByType.getGLN() + ediCctop11SUType.getGLN() + "503";
		final String applicationReference = "INVOIC01BRX";

		return new UNBInterchangeHeader().
				withS001(new S001SyntaxIdentifier().
						withE0001(E0001SyntaxIdentifier.UNOC).withE0002("4")).
				withS002(new S002InterchangeSender().
						withE0004(xmlCctopInvoice.getSendergln()).
						withE0007("14")).
				withS003(new S003InterchangeRecipient().
						withE0010(interchangeReceipientGLN).
						withE0007("14")).
				withS004(new S004DateAndTimeOfPreparation().
						withE0017(new BigDecimal(dateSend)).
						withE0019(new BigDecimal(timeSend))).
				withE0020(messageSequenceNo).
				withS005(new S005RecipientReferencePasswordDetails().withE0022("")).
				withE0026(applicationReference).
				withE0032(communicationsAgreement).
				withE0035(testIndicator);
	}

	@NonNull
	private S009MessageIdentifier getS009MessageIdentifier()
	{
		final String associationAssignedCode = "EAN010";

		return new S009MessageIdentifier().
				withE0065(E0065MessageType.INVOIC).
				withE0052("D").
				withE0054("01B").
				withE0051(E0051ControllingAgencyCoded.UN).
				withE0057(associationAssignedCode);
	}
}
