package de.metas.payment.sepa.api.impl;

/*
 * #%L
 * de.metas.payment.sepa
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;

import de.metas.payment.sepa.api.ISEPADocumentBL;
import de.metas.payment.sepa.api.ISEPADocumentDAO;
import de.metas.payment.sepa.model.I_SEPA_Export;
import de.metas.payment.sepa.model.I_SEPA_Export_Line;
import de.metas.payment.sepa.sdd.jaxb.v3.AccountIdentificationSEPA;
import de.metas.payment.sepa.sdd.jaxb.v3.ActiveOrHistoricCurrencyAndAmountSEPA;
import de.metas.payment.sepa.sdd.jaxb.v3.ActiveOrHistoricCurrencyCodeEUR;
import de.metas.payment.sepa.sdd.jaxb.v3.BranchAndFinancialInstitutionIdentificationSEPA3;
import de.metas.payment.sepa.sdd.jaxb.v3.CashAccountSEPA1;
import de.metas.payment.sepa.sdd.jaxb.v3.CashAccountSEPA2;
import de.metas.payment.sepa.sdd.jaxb.v3.ChargeBearerTypeSEPACode;
import de.metas.payment.sepa.sdd.jaxb.v3.CustomerDirectDebitInitiationV02;
import de.metas.payment.sepa.sdd.jaxb.v3.DirectDebitTransactionInformationSDD;
import de.metas.payment.sepa.sdd.jaxb.v3.DirectDebitTransactionSDD;
import de.metas.payment.sepa.sdd.jaxb.v3.Document;
import de.metas.payment.sepa.sdd.jaxb.v3.FinancialInstitutionIdentificationSEPA3;
import de.metas.payment.sepa.sdd.jaxb.v3.GroupHeaderSDD;
import de.metas.payment.sepa.sdd.jaxb.v3.IdentificationSchemeNameSEPA;
import de.metas.payment.sepa.sdd.jaxb.v3.JAXBConstantsV3;
import de.metas.payment.sepa.sdd.jaxb.v3.LocalInstrumentSEPA;
import de.metas.payment.sepa.sdd.jaxb.v3.MandateRelatedInformationSDD;
import de.metas.payment.sepa.sdd.jaxb.v3.PartyIdentificationSEPA1;
import de.metas.payment.sepa.sdd.jaxb.v3.PartyIdentificationSEPA2;
import de.metas.payment.sepa.sdd.jaxb.v3.PartyIdentificationSEPA3;
import de.metas.payment.sepa.sdd.jaxb.v3.PartyIdentificationSEPA5;
import de.metas.payment.sepa.sdd.jaxb.v3.PartySEPA2;
import de.metas.payment.sepa.sdd.jaxb.v3.PaymentIdentificationSEPA;
import de.metas.payment.sepa.sdd.jaxb.v3.PaymentInstructionInformationSDD;
import de.metas.payment.sepa.sdd.jaxb.v3.PaymentMethod2Code;
import de.metas.payment.sepa.sdd.jaxb.v3.PaymentTypeInformationSDD;
import de.metas.payment.sepa.sdd.jaxb.v3.PersonIdentificationSEPA2;
import de.metas.payment.sepa.sdd.jaxb.v3.RemittanceInformationSEPA1Choice;
import de.metas.payment.sepa.sdd.jaxb.v3.RestrictedPersonIdentificationSEPA;
import de.metas.payment.sepa.sdd.jaxb.v3.RestrictedPersonIdentificationSchemeNameSEPA;
import de.metas.payment.sepa.sdd.jaxb.v3.SequenceType1Code;
import de.metas.payment.sepa.sdd.jaxb.v3.ServiceLevelSEPA;

public class SEPACustomerDirectDebitInitializationV3Marshaler
{
	private final DatatypeFactory datatypeFactory;

	public SEPACustomerDirectDebitInitializationV3Marshaler()
	{
		try
		{
			datatypeFactory = DatatypeFactory.newInstance();
		}
		catch (DatatypeConfigurationException e)
		{
			throw new AdempiereException(e);
		}
	}

	public void marshal(final I_SEPA_Export sepaDocument, final OutputStream out)
	{
		Check.assumeNotNull(sepaDocument, "sepaDocument not null");
		Check.assumeNotNull(out, "out not null");

		try
		{
			final Document xmlDocument = createDocument(sepaDocument);
			marshal(xmlDocument, out);
		}
		catch (Exception e)
		{
			throw new AdempiereException("Error while marshaling " + sepaDocument, e);
		}
	}

	public void marshal(final Document xmlDocument, final OutputStream out)
	{
		Check.assumeNotNull(xmlDocument, "xmlDocument not null");
		Check.assumeNotNull(out, "out not null");

		final Writer xmlWriter = new OutputStreamWriter(out);

		try
		{
			final JAXBElement<Document> jaxbDocument = JAXBConstantsV3.JAXB_ObjectFactory.createJAXBElement(xmlDocument);

			final JAXBContext jaxbContext = JAXBContext.newInstance(JAXBConstantsV3.JAXB_ContextPath);
			final Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty("jaxb.formatted.output", Boolean.TRUE);
			marshaller.setProperty("jaxb.schemaLocation", "urn:sepade:xsd:pain.008.001.01 pain.008.001.01.xsd");
			marshaller.marshal(jaxbDocument, xmlWriter);
		}
		catch (JAXBException e)
		{
			throw new AdempiereException("Marshalling error", e);
		}
	}

	public String toString(final Document document)
	{
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		marshal(document, out);

		return out.toString();
	}

	public Document createDocument(final I_SEPA_Export sepaDocument)
	{
		final Document document = new Document();

		final CustomerDirectDebitInitiationV02 customerDirectDebitInitiation = new CustomerDirectDebitInitiationV02();
		document.setCstmrDrctDbtInitn(customerDirectDebitInitiation);

		//
		// Group Header
		{
			final GroupHeaderSDD groupHeader = new GroupHeaderSDD();
			customerDirectDebitInitiation.setGrpHdr(groupHeader);

			// MessageIdentification
			groupHeader.setMsgId(sepaDocument.getDocumentNo()); // unique identifier in our system

			// CreationDateTime: The system-generated date and time stamp at the time of formatting of the message.
			groupHeader.setCreDtTm(datatypeFactory.newXMLGregorianCalendar(SystemTime.asGregorianCalendar()));

			// Number of transactions: The total number of direct debit transaction blocks in the message.
			// NOTE: You can have only one direct debit transaction by payment information.
			// NOTE: it will be set later, after we know the number of lines
			groupHeader.setNbOfTxs(null);

			// Control Sum: This is the total amount of the collection.
			// NOTE: it will be set later, after each payment information
			groupHeader.setCtrlSum(BigDecimal.ZERO);

			//
			// Initiating party
			{
				final PartyIdentificationSEPA1 initiatingParty = new PartyIdentificationSEPA1();
				initiatingParty.setNm(sepaDocument.getSEPA_CreditorIdentifier());
				groupHeader.setInitgPty(initiatingParty);
				groupHeader.setInitgPty(initiatingParty);
			}
		}

		//
		// Payment Informations: create one PaymentInstructionInformationSDD for each line
		final Iterator<I_SEPA_Export_Line> sepaDocumentLines = Services.get(ISEPADocumentDAO.class).retrieveLines(sepaDocument);
		if (!sepaDocumentLines.hasNext())
		{
			throw new AdempiereException("@NoLines@: " + sepaDocument);
		}

		while (sepaDocumentLines.hasNext())
		{
			final I_SEPA_Export_Line line = sepaDocumentLines.next();

			if ((!line.isActive()) || (line.isError()))
			{
				// Error on line. Don't create payment instruction information.
				continue;
			}

			final PaymentInstructionInformationSDD pmtInf = createPaymentInstructionInformation(customerDirectDebitInitiation, sepaDocument, line);
			Check.assumeNotNull(pmtInf, "pmtInf not null");

			customerDirectDebitInitiation.getPmtInf().add(pmtInf);

			// Update GroupHeader's control amount
			final BigDecimal groupHeaderCtrlAmt = customerDirectDebitInitiation.getGrpHdr().getCtrlSum();
			final BigDecimal groupHeaderCtrlAmtNew = groupHeaderCtrlAmt.add(pmtInf.getCtrlSum());
			customerDirectDebitInitiation.getGrpHdr().setCtrlSum(groupHeaderCtrlAmtNew);
		}

		//
		// Number of transactions: The total number of direct debit transaction blocks in the message.
		// NOTE: You can have only one direct debit transaction by payment information.
		customerDirectDebitInitiation.getGrpHdr().setNbOfTxs(String.valueOf(customerDirectDebitInitiation.getPmtInf().size()));

		sepaDocument.setProcessed(customerDirectDebitInitiation.getPmtInf().size() > 0);
		InterfaceWrapperHelper.save(sepaDocument);
		return document;
	}

	private PaymentInstructionInformationSDD createPaymentInstructionInformation(
			final CustomerDirectDebitInitiationV02 customerDirectDebitInitiation,
			final I_SEPA_Export sepaHdr,
			final I_SEPA_Export_Line line)
	{
		final PaymentInstructionInformationSDD paymentInformation = new PaymentInstructionInformationSDD();

		// PaymentInformationIdentification: A system-generated internal code.
		{
			// Current index of this payment instruction information
			final int currentIndex = customerDirectDebitInitiation.getPmtInf().size() + 1;
			final String pmtInfId = customerDirectDebitInitiation.getGrpHdr().getMsgId() + "-" + currentIndex;
			paymentInformation.setPmtInfId(pmtInfId);
		}

		// Payment method: Hard-coded value of DD.
		paymentInformation.setPmtMtd(PaymentMethod2Code.DD);

		// Number of transactions: Hard-coded with a value of 1. You can inform only one payment per payment information block.
		paymentInformation.setNbOfTxs("1");

		//
		// Control Sum: This is the total amount of the collection.
		// NOTE: because NumberOfTransactions is ONE, we can assume this is the line Amount, but we will update it later
		paymentInformation.setCtrlSum(null);

		// Payment Type Information (PmtTpInf)
		{
			final PaymentTypeInformationSDD pmtTpInf = new PaymentTypeInformationSDD();
			paymentInformation.setPmtTpInf(pmtTpInf);

			// Service Level: Hard-coded value of SEPA.
			{
				final ServiceLevelSEPA svcLvl = new ServiceLevelSEPA();
				svcLvl.setCd("SEPA");
				pmtTpInf.setSvcLvl(svcLvl);
			}

			// Local instrument: Hard-coded value of COR1.
			{
				final LocalInstrumentSEPA lclInstrm = new LocalInstrumentSEPA();
				lclInstrm.setCd("COR1");
				pmtTpInf.setLclInstrm(lclInstrm);
			}

			// Sequence Type: Valid values are FNAL, FRST, OOFF, RCUR
			pmtTpInf.setSeqTp(SequenceType1Code.OOFF);
			
//			//FIXME: Is this needed?
//			{
//				final CategoryPurposeSEPA ctgyPurp = new CategoryPurposeSEPA();
//				ctgyPurp.setCd("DCRD");
//
//				pmtTpInf.setCtgyPurp(ctgyPurp);
//			}
		}

		//
		// RequestedCollectionDate: Due date of the collection
		final Date dueDate = Services.get(ISEPADocumentBL.class).getDueDate(line);
		paymentInformation.setReqdColltnDt(newXMLGregorianCalendar(dueDate));

		//
		// Creditor BPartner (Cdtr)
		// NOTE: actually it's same as GroupHeader's initiating party
		{
			final PartyIdentificationSEPA5 creditorId = convertPartyIdentificationSEPA5(customerDirectDebitInitiation.getGrpHdr().getInitgPty());
			paymentInformation.setCdtr(creditorId);
		}

		//
		// Creditor BPartner Account
		{
			final CashAccountSEPA1 cdtrAcct = new CashAccountSEPA1();
			paymentInformation.setCdtrAcct(cdtrAcct);

			final AccountIdentificationSEPA id = new AccountIdentificationSEPA();
			id.setIBAN(sepaHdr.getIBAN());
			cdtrAcct.setId(id);
		}

		//
		// Creditor Agent (i.e. Bank)
		{
			final BranchAndFinancialInstitutionIdentificationSEPA3 creditorAgt = new BranchAndFinancialInstitutionIdentificationSEPA3();
			paymentInformation.setCdtrAgt(creditorAgt);

			final FinancialInstitutionIdentificationSEPA3 finInstnId = new FinancialInstitutionIdentificationSEPA3();
			finInstnId.setBIC(sepaHdr.getSwiftCode());
			creditorAgt.setFinInstnId(finInstnId);
		}

		// Charge Bearer Type
		// Hard-coded value of SLEV.
		paymentInformation.setChrgBr(ChargeBearerTypeSEPACode.SLEV);

		//
		// Direct Debit Transaction Information
		{
			final DirectDebitTransactionInformationSDD directDebitTrxInfo = createDirectDebitTransactionInformation(paymentInformation, line);
			paymentInformation.getDrctDbtTxInf().add(directDebitTrxInfo);

			//
			// UPDATE: Payment Information's Control Sum
			// NOTE: we have only one line
			paymentInformation.setCtrlSum(directDebitTrxInfo.getInstdAmt().getValue());
		}
		
		{
			paymentInformation.setCdtrSchmeId(convertPartyIdentificationSEPA3(customerDirectDebitInitiation.getGrpHdr().getInitgPty()));
		}

		return paymentInformation;
	}

	private DirectDebitTransactionInformationSDD createDirectDebitTransactionInformation(
			final PaymentInstructionInformationSDD paymentInformation,
			final I_SEPA_Export_Line line)
	{
		final DirectDebitTransactionInformationSDD directDebitTrxInfo = new DirectDebitTransactionInformationSDD();

		//
		// Payment ID
		// EndToEndId: A unique key generated by the system for each payment.
		{
			final String endToEndId = paymentInformation.getPmtInfId() + "-0001";

			final PaymentIdentificationSEPA pmtId = new PaymentIdentificationSEPA();
			pmtId.setEndToEndId(endToEndId);
			directDebitTrxInfo.setPmtId(pmtId);
		}

		//
		// Amount/Currency
		{
			final ActiveOrHistoricCurrencyAndAmountSEPA instdAmt = new ActiveOrHistoricCurrencyAndAmountSEPA();
			instdAmt.setCcy(ActiveOrHistoricCurrencyCodeEUR.fromValue("EUR")); // Hard-coded value of EUR

			final BigDecimal amount = line.getAmt();
			Check.assume(amount != null && amount.signum() > 0, "Invalid amount '{}' for {}'", amount, line);
			instdAmt.setValue(amount);

			directDebitTrxInfo.setInstdAmt(instdAmt);
		}

		//
		// Direct Debit Transaction
		{
			final DirectDebitTransactionSDD directDebitTrx = new DirectDebitTransactionSDD();
			directDebitTrxInfo.setDrctDbtTx(directDebitTrx);

			final MandateRelatedInformationSDD mandateRelatedInf = new MandateRelatedInformationSDD();
			directDebitTrx.setMndtRltdInf(mandateRelatedInf);

			// AmendmentIndicator: Valid values are True and False.
			mandateRelatedInf.setAmdmntInd(Boolean.FALSE);
			mandateRelatedInf.setDtOfSgntr(newXMLGregorianCalendar(line.getSEPA_Export().getPaymentDate()));
			// FIXME : Not sure.
			mandateRelatedInf.setMndtId(line.getSEPA_MandateRefNo());
		}

		//
		// Debitor Agent (i.e. Bank)
		{
			BranchAndFinancialInstitutionIdentificationSEPA3 debitorAgent = new BranchAndFinancialInstitutionIdentificationSEPA3();
			directDebitTrxInfo.setDbtrAgt(debitorAgent);

			final FinancialInstitutionIdentificationSEPA3 finInstId = new FinancialInstitutionIdentificationSEPA3();
			debitorAgent.setFinInstnId(finInstId);
			finInstId.setBIC(line.getSwiftCode());
		}

		//
		// Debitor BPartner
		{

			final PartyIdentificationSEPA2 debitor = new PartyIdentificationSEPA2();
			directDebitTrxInfo.setDbtr(debitor);

			debitor.setNm(line.getSEPA_MandateRefNo());

			// FIXME: Debitor Address
			// NOTE: this is not mandatory
			// final PostalAddress6 postalAddr = new PostalAddress6();
			// debitor.setPstlAdr(postalAddr);
			// postalAddr.setCtry("NL");
			// postalAddr.getAdrLine().add("Test Address 12");
			// postalAddr.getAdrLine().add("555 test");
		}

		//
		// Debitor BPartner Account
		{
			final CashAccountSEPA2 debitorAcct = new CashAccountSEPA2();
			directDebitTrxInfo.setDbtrAcct(debitorAcct);

			final AccountIdentificationSEPA id = new AccountIdentificationSEPA();
			debitorAcct.setId(id);
			id.setIBAN(line.getIBAN());
		}

		// Remittance Info
		{
			final RemittanceInformationSEPA1Choice remittanceInfo = new RemittanceInformationSEPA1Choice();
			directDebitTrxInfo.setRmtInf(remittanceInfo);
		    // FIXME: For now, just add a " " to make sure the node exists.
			remittanceInfo.setUstrd(line.getDescription()+" ");
		}

		return directDebitTrxInfo;
	}

	private XMLGregorianCalendar newXMLGregorianCalendar(final Date date)
	{
		final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		final String dateStr = df.format(date);

		return datatypeFactory.newXMLGregorianCalendar(dateStr);
	}

	private PartyIdentificationSEPA5 convertPartyIdentificationSEPA5(final PartyIdentificationSEPA1 partyId)
	{
		final PartyIdentificationSEPA5 partyIdCopy = new PartyIdentificationSEPA5();

		partyIdCopy.setNm(partyId.getNm());

		// NOTE: address is not mandatory
		// FIXME: copy address if exists

		return partyIdCopy;
	}
	
	private PartyIdentificationSEPA3 convertPartyIdentificationSEPA3(final PartyIdentificationSEPA1 partyId)
	{
		final PartyIdentificationSEPA3 partyIdCopy = new PartyIdentificationSEPA3();
		final PartySEPA2 partySEPA = new PartySEPA2();
		partyIdCopy.setId(partySEPA);
		
		final PersonIdentificationSEPA2 prvtId = new PersonIdentificationSEPA2();
		partySEPA.setPrvtId(prvtId);
		
		final RestrictedPersonIdentificationSEPA othr = new RestrictedPersonIdentificationSEPA();
		prvtId.setOthr(othr);
		
		final RestrictedPersonIdentificationSchemeNameSEPA schemeNm = new RestrictedPersonIdentificationSchemeNameSEPA();
		othr.setId(partyId.getNm());
		othr.setSchmeNm(schemeNm);
		schemeNm.setPrtry(IdentificationSchemeNameSEPA.valueOf("SEPA"));
		
		// NOTE: address is not mandatory
		// FIXME: copy address if exists

		return partyIdCopy;
	}

}
