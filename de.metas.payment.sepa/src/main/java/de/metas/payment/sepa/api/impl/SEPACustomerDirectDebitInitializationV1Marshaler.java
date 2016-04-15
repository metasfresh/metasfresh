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
import de.metas.payment.sepa.sdd.jaxb.v1.AccountIdentification4Choice;
import de.metas.payment.sepa.sdd.jaxb.v1.ActiveOrHistoricCurrencyAndAmount;
import de.metas.payment.sepa.sdd.jaxb.v1.BranchAndFinancialInstitutionIdentification4;
import de.metas.payment.sepa.sdd.jaxb.v1.CashAccount16;
import de.metas.payment.sepa.sdd.jaxb.v1.ChargeBearerType1Code;
import de.metas.payment.sepa.sdd.jaxb.v1.CustomerDirectDebitInitiationV02;
import de.metas.payment.sepa.sdd.jaxb.v1.DirectDebitTransaction6;
import de.metas.payment.sepa.sdd.jaxb.v1.DirectDebitTransactionInformation9;
import de.metas.payment.sepa.sdd.jaxb.v1.Document;
import de.metas.payment.sepa.sdd.jaxb.v1.FinancialInstitutionIdentification7;
import de.metas.payment.sepa.sdd.jaxb.v1.GroupHeader39;
import de.metas.payment.sepa.sdd.jaxb.v1.JAXBConstantsV1;
import de.metas.payment.sepa.sdd.jaxb.v1.LocalInstrument2Choice;
import de.metas.payment.sepa.sdd.jaxb.v1.MandateRelatedInformation6;
import de.metas.payment.sepa.sdd.jaxb.v1.PartyIdentification32;
import de.metas.payment.sepa.sdd.jaxb.v1.PaymentIdentification1;
import de.metas.payment.sepa.sdd.jaxb.v1.PaymentInstructionInformation4;
import de.metas.payment.sepa.sdd.jaxb.v1.PaymentMethod2Code;
import de.metas.payment.sepa.sdd.jaxb.v1.PaymentTypeInformation20;
import de.metas.payment.sepa.sdd.jaxb.v1.RemittanceInformation5;
import de.metas.payment.sepa.sdd.jaxb.v1.SequenceType1Code;
import de.metas.payment.sepa.sdd.jaxb.v1.ServiceLevel8Choice;

/**
 * SEPA Customer Direct Debit Initialization V1 Marshaler (Exporter)
 * 
 * @author tsa
 * @see http://docs.oracle.com/cd/E16582_01/doc.91/e15104/sepa_dir_debit_xml_appx.htm
 */
public class SEPACustomerDirectDebitInitializationV1Marshaler
{
	private final DatatypeFactory datatypeFactory;

	public SEPACustomerDirectDebitInitializationV1Marshaler()
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
			final JAXBElement<Document> jaxbDocument = JAXBConstantsV1.JAXB_ObjectFactory.createJAXBElement(xmlDocument);

			final JAXBContext jaxbContext = JAXBContext.newInstance(JAXBConstantsV1.JAXB_ContextPath);
			final Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty("jaxb.formatted.output", Boolean.TRUE);
			marshaller.setProperty("jaxb.schemaLocation", "http://www.w3.org/2001/XMLSchema-instance");
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
			final GroupHeader39 groupHeader = new GroupHeader39();
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
				final PartyIdentification32 initiatingParty = new PartyIdentification32();
				initiatingParty.setNm(sepaDocument.getSEPA_CreditorIdentifier());
				groupHeader.setInitgPty(initiatingParty);
			}
		}

		//
		// Payment Informations: create one PaymentInstructionInformation4 for each line
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

			final PaymentInstructionInformation4 pmtInf = createPaymentInstructionInformation(customerDirectDebitInitiation, sepaDocument, line);
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

	private PaymentInstructionInformation4 createPaymentInstructionInformation(
			final CustomerDirectDebitInitiationV02 customerDirectDebitInitiation,
			final I_SEPA_Export sepaHdr,
			final I_SEPA_Export_Line line)
	{
		final PaymentInstructionInformation4 paymentInformation = new PaymentInstructionInformation4();

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
			final PaymentTypeInformation20 pmtTpInf = new PaymentTypeInformation20();
			paymentInformation.setPmtTpInf(pmtTpInf);

			// Service Level: Hard-coded value of SEPA.
			{
				final ServiceLevel8Choice svcLvl = new ServiceLevel8Choice();
				svcLvl.setCd("SEPA");
				pmtTpInf.setSvcLvl(svcLvl);
			}

			// Local instrument: Hard-coded value of CORE.
			{
				final LocalInstrument2Choice lclInstrm = new LocalInstrument2Choice();
				lclInstrm.setCd("CORE");
				pmtTpInf.setLclInstrm(lclInstrm);
			}

			// Sequence Type: Valid values are FNAL, FRST, OOFF, RCUR
			pmtTpInf.setSeqTp(SequenceType1Code.OOFF);
		}

		//
		// RequestedCollectionDate: Due date of the collection
		final Date dueDate = Services.get(ISEPADocumentBL.class).getDueDate(line);
		paymentInformation.setReqdColltnDt(newXMLGregorianCalendar(dueDate));

		//
		// Creditor BPartner (Cdtr)
		// NOTE: actually it's same as GroupHeader's initiating party
		{
			final PartyIdentification32 creditorId = copyPartyIdentification32(customerDirectDebitInitiation.getGrpHdr().getInitgPty());
			paymentInformation.setCdtr(creditorId);
		}

		//
		// Creditor BPartner Account
		{
			final CashAccount16 cdtrAcct = new CashAccount16();
			paymentInformation.setCdtrAcct(cdtrAcct);

			final AccountIdentification4Choice id = new AccountIdentification4Choice();
			id.setIBAN(sepaHdr.getIBAN());
			cdtrAcct.setId(id);
		}

		//
		// Creditor Agent (i.e. Bank)
		{
			final BranchAndFinancialInstitutionIdentification4 creditorAgt = new BranchAndFinancialInstitutionIdentification4();
			paymentInformation.setCdtrAgt(creditorAgt);

			final FinancialInstitutionIdentification7 finInstnId = new FinancialInstitutionIdentification7();
			finInstnId.setBIC(sepaHdr.getSwiftCode());
			creditorAgt.setFinInstnId(finInstnId);
		}

		// Charge Bearer Type
		// Hard-coded value of SLEV.
		paymentInformation.setChrgBr(ChargeBearerType1Code.SLEV);

		//
		// Direct Debit Transaction Information
		{
			final DirectDebitTransactionInformation9 directDebitTrxInfo = createDirectDebitTransactionInformation(paymentInformation, line);
			paymentInformation.getDrctDbtTxInf().add(directDebitTrxInfo);

			//
			// UPDATE: Payment Information's Control Sum
			// NOTE: we have only one line
			paymentInformation.setCtrlSum(directDebitTrxInfo.getInstdAmt().getValue());
		}

		return paymentInformation;
	}

	private DirectDebitTransactionInformation9 createDirectDebitTransactionInformation(
			final PaymentInstructionInformation4 paymentInformation,
			final I_SEPA_Export_Line line)
	{
		final DirectDebitTransactionInformation9 directDebitTrxInfo = new DirectDebitTransactionInformation9();

		//
		// Payment ID
		// EndToEndId: A unique key generated by the system for each payment.
		{
			final String endToEndId = paymentInformation.getPmtInfId() + "-0001";

			final PaymentIdentification1 pmtId = new PaymentIdentification1();
			pmtId.setEndToEndId(endToEndId);
			directDebitTrxInfo.setPmtId(pmtId);
		}

		//
		// Amount/Currency
		{
			final ActiveOrHistoricCurrencyAndAmount instdAmt = new ActiveOrHistoricCurrencyAndAmount();
			instdAmt.setCcy("EUR"); // Hard-coded value of EUR

			final BigDecimal amount = line.getAmt();
			Check.assume(amount != null && amount.signum() > 0, "Invalid amount '{}' for {}'", amount, line);
			instdAmt.setValue(amount);

			directDebitTrxInfo.setInstdAmt(instdAmt);
		}

		//
		// Direct Debit Transaction
		{
			final DirectDebitTransaction6 directDebitTrx = new DirectDebitTransaction6();
			directDebitTrxInfo.setDrctDbtTx(directDebitTrx);

			final MandateRelatedInformation6 mandateRelatedInf = new MandateRelatedInformation6();
			directDebitTrx.setMndtRltdInf(mandateRelatedInf);

			// AmendmentIndicator: Valid values are True and False.
			mandateRelatedInf.setAmdmntInd(Boolean.FALSE);
		}

		//
		// Debitor Agent (i.e. Bank)
		{
			BranchAndFinancialInstitutionIdentification4 debitorAgent = new BranchAndFinancialInstitutionIdentification4();
			directDebitTrxInfo.setDbtrAgt(debitorAgent);

			final FinancialInstitutionIdentification7 finInstId = new FinancialInstitutionIdentification7();
			debitorAgent.setFinInstnId(finInstId);
			finInstId.setBIC(line.getSwiftCode());
		}

		//
		// Debitor BPartner
		{

			final PartyIdentification32 debitor = new PartyIdentification32();
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
			final CashAccount16 debitorAcct = new CashAccount16();
			directDebitTrxInfo.setDbtrAcct(debitorAcct);

			final AccountIdentification4Choice id = new AccountIdentification4Choice();
			debitorAcct.setId(id);
			id.setIBAN(line.getIBAN());
		}

		// Remittance Info
		{
			final RemittanceInformation5 remittanceInfo = new RemittanceInformation5();
			directDebitTrxInfo.setRmtInf(remittanceInfo);

			// FIXME: shall create a new line for each CR?
			remittanceInfo.getUstrd().add(line.getDescription());
		}

		return directDebitTrxInfo;
	}

	private XMLGregorianCalendar newXMLGregorianCalendar(final Date date)
	{
		final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
		final String dateStr = df.format(date);

		return datatypeFactory.newXMLGregorianCalendar(dateStr);
	}

	private PartyIdentification32 copyPartyIdentification32(final PartyIdentification32 partyId)
	{
		final PartyIdentification32 partyIdCopy = new PartyIdentification32();

		partyIdCopy.setNm(partyId.getNm());

		// NOTE: address is not mandatory
		// FIXME: copy address if exists

		return partyIdCopy;
	}
}
