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

import de.metas.common.util.CoalesceUtil;
import de.metas.edi.esb.commons.SystemTime;
import de.metas.edi.esb.commons.Util;
import de.metas.edi.esb.jaxb.metasfreshinhousev2.EDICctop119VType;
import de.metas.edi.esb.jaxb.metasfreshinhousev2.EDICctopInvoicVType;
import jakarta.xml.bind.JAXBElement;
import lombok.NonNull;
import org.smooks.edifact.binding.d01b.BGMBeginningOfMessage;
import org.smooks.edifact.binding.d01b.C002DocumentMessageName;
import org.smooks.edifact.binding.d01b.C106DocumentMessageIdentification;
import org.smooks.edifact.binding.d01b.C506Reference;
import org.smooks.edifact.binding.d01b.C507DateTimePeriod;
import org.smooks.edifact.binding.d01b.C516MonetaryAmount;
import org.smooks.edifact.binding.d01b.DTMDateTimePeriod;
import org.smooks.edifact.binding.d01b.E1153ReferenceCodeQualifier;
import org.smooks.edifact.binding.d01b.INVOIC;
import org.smooks.edifact.binding.d01b.Interchange;
import org.smooks.edifact.binding.d01b.MOAMonetaryAmount;
import org.smooks.edifact.binding.d01b.Message;
import org.smooks.edifact.binding.d01b.RFFReference;
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

		final Date ediSendDate = SystemTime.asDate();
		final String dateSend = Util.toFormattedStringDate(ediSendDate, "yyyyMMdd");
		final String timeSend = Util.toFormattedStringDate(ediSendDate, "HHmm");
		
		final String dateTimeInvoiced = Util.toFormattedStringDate(Util.toDate(xmlCctopInvoice.getDateInvoiced()), "yyyyMMddHHmmss");
		final String dateTimeAcct = Util.toFormattedStringDate(Util.toDate(
				CoalesceUtil.coalesceNotNull(xmlCctopInvoice.getDateAcct(), xmlCctopInvoice.getDateInvoiced())), "yyyyMMddHHmmss");
		final String dateTimeDelivered = Util.toFormattedStringDate(Util.toDate(xmlCctopInvoice.getMovementDate()), "yyyyMMddHHmmss");
		final String dateTimeOrdered = Util.toFormattedStringDate(Util.toDate(xmlCctopInvoice.getDateOrdered()), "yyyyMMddHHmmss");
		final Map<String, EDICctop119VType> locationType2Address = xmlCctopInvoice.getEDICctop119V().stream()
				.collect(Collectors.toMap(
						EDICctop119VType::getEancomLocationtype,
						ediCctop -> ediCctop));

		final BigDecimal testIndicator = null; // new BigDecimal("1");
		// EANCOM = EAN + Communication 
		final String communicationsAgreement = "EANCOM" + locationType2Address.get("BY").getGLN() + locationType2Address.get("SU").getGLN() + "503";
		final String applicationReference = "INVOIC01BRX";
		final String associationAssignedCode = "EAN010";
		final String interchangeReceipientGLN = "7611937000723"; // inclear why it's not xmlCctopInvoice.getReceivergln()

		return new Interchange().
				withUNA(new UNA().
						withCompositeSeparator(":").
						withFieldSeparator("+").
						withDecimalSeparator(".").
						withEscapeCharacter("?").
						withRepeatSeparator("*").
						withSegmentTerminator("'")).
				withUNB( // https://unece.org/trade/uncefact/unedifact/part-4-Annex-B
						new UNBInterchangeHeader().
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
								withE0035(testIndicator)).
				withMessage(new Message().
						withContent(new JAXBElement<>(new QName("UNH"), UNHMessageHeader.class, new UNHMessageHeader().
								withE0062("1").
								withS009(new S009MessageIdentifier().
										withE0065(E0065MessageType.INVOIC).
										withE0052("D").
										withE0054("01B").
										withE0051(E0051ControllingAgencyCoded.UN).
										withE0057(associationAssignedCode)))).
						withContent(new JAXBElement<>(new QName("http://www.ibm.com/dfdl/edi/un/edifact/D01B", "INVOIC", "D01B"), INVOIC.class, new INVOIC().
								withBGM(new BGMBeginningOfMessage() /* https://service.unece.org/trade/untdid/d01b/trsd/trsdbgm.htm */.
										withC002(new C002DocumentMessageName().withE1001("380")).
										withC106(new C106DocumentMessageIdentification()
												.withE1004(xmlCctopInvoice.getInvoiceDocumentno())).
										withE1225("9") /* Message function code = Original*/).
								withDTM(new DTMDateTimePeriod() /* https://service.unece.org/trade/untdid/d01b/trsd/trsddtm.htm */.
										withC507(new C507DateTimePeriod().
												withE2005("137") /*Document/message date/time*/.
												withE2380(dateTimeInvoiced).
												withE2379("204") /*Date or time or period format code = CCYYMMDDHHMMSS */)).
								withDTM(new DTMDateTimePeriod() /* https://service.unece.org/trade/untdid/d01b/trsd/trsddtm.htm */.
										withC507(new C507DateTimePeriod().
												withE2005("454") /*Accounting value date*/.
												withE2380(dateTimeAcct).
												withE2379("204") /*Date or time or period format code = CCYYMMDDHHMMSS */)).
								withDTM(new DTMDateTimePeriod() /* https://service.unece.org/trade/untdid/d01b/trsd/trsddtm.htm */.
										withC507(new C507DateTimePeriod().
												withE2005("35") /*Delivery date/time, actual*/.
												withE2380(dateTimeDelivered).
												withE2379("204") /*Date or time or period format code = CCYYMMDDHHMMSS */)).
								withSegGrp1(new INVOIC.SegGrp1().
										withRFF(new RFFReference() /* https://service.unece.org/trade/untdid/d01b/trsd/trsdrff.htm */. 
												withC506(new C506Reference().
														withE1153(E1153ReferenceCodeQualifier.ON /*Order Number*/).
														withE1154(xmlCctopInvoice.getPOReference())))).
								withDTM(new DTMDateTimePeriod() /* https://service.unece.org/trade/untdid/d01b/trsd/trsddtm.htm */.
										withC507(new C507DateTimePeriod().
												withE2005("171") /*Reference date/time*/.
												withE2380(dateTimeOrdered).
												withE2379("204") /*Date or time or period format code = CCYYMMDDHHMMSS */)).
								withUNS(new UNSSectionControl().
										withE0081(E0081SectionIdentification.S)).
								withSegGrp50(new INVOIC.SegGrp50().
										withMOA(new MOAMonetaryAmount().
												withC516(new C516MonetaryAmount().
														withE5025("64").
														withE5004(new BigDecimal("100.95")).
														withE6345("GBP")))))).
						withContent(new JAXBElement<>(new QName("UNT"), UNTMessageTrailer.class, new UNTMessageTrailer().
								withE0074(new BigDecimal(36)).
								withE0062("30")))).
				withUNZ(new UNZInterchangeTrailer().
						withE0036(new BigDecimal(1)).
						withE0020(messageSequenceNo));
	}
}
