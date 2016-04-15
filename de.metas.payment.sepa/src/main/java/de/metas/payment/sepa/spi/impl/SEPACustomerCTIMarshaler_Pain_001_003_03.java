package de.metas.payment.sepa.spi.impl;

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


import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

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
import org.adempiere.util.jaxb.DynamicObjectFactory;
import org.adempiere.util.time.SystemTime;

import de.metas.payment.sepa.api.ISEPADocumentBL;
import de.metas.payment.sepa.api.ISEPADocumentDAO;
import de.metas.payment.sepa.jaxb.sct.pain_001_003_03.ObjectFactory;
import de.metas.payment.sepa.jaxb.sct.pain_001_003_03.AccountIdentificationSEPA;
import de.metas.payment.sepa.jaxb.sct.pain_001_003_03.ActiveOrHistoricCurrencyAndAmountSEPA;
import de.metas.payment.sepa.jaxb.sct.pain_001_003_03.ActiveOrHistoricCurrencyCodeEUR;
import de.metas.payment.sepa.jaxb.sct.pain_001_003_03.AmountTypeSEPA;
import de.metas.payment.sepa.jaxb.sct.pain_001_003_03.BranchAndFinancialInstitutionIdentificationSEPA1;
import de.metas.payment.sepa.jaxb.sct.pain_001_003_03.BranchAndFinancialInstitutionIdentificationSEPA3;
import de.metas.payment.sepa.jaxb.sct.pain_001_003_03.CashAccountSEPA1;
import de.metas.payment.sepa.jaxb.sct.pain_001_003_03.CashAccountSEPA2;
import de.metas.payment.sepa.jaxb.sct.pain_001_003_03.ChargeBearerTypeSEPACode;
import de.metas.payment.sepa.jaxb.sct.pain_001_003_03.CreditTransferTransactionInformationSCT;
import de.metas.payment.sepa.jaxb.sct.pain_001_003_03.CreditorReferenceInformationSEPA1;
import de.metas.payment.sepa.jaxb.sct.pain_001_003_03.CustomerCreditTransferInitiationV03;
import de.metas.payment.sepa.jaxb.sct.pain_001_003_03.Document;
import de.metas.payment.sepa.jaxb.sct.pain_001_003_03.FinancialInstitutionIdentificationSEPA1;
import de.metas.payment.sepa.jaxb.sct.pain_001_003_03.FinancialInstitutionIdentificationSEPA3;
import de.metas.payment.sepa.jaxb.sct.pain_001_003_03.GroupHeaderSCT;
import de.metas.payment.sepa.jaxb.sct.pain_001_003_03.PartyIdentificationSEPA1;
import de.metas.payment.sepa.jaxb.sct.pain_001_003_03.PartyIdentificationSEPA2;
import de.metas.payment.sepa.jaxb.sct.pain_001_003_03.PaymentIdentificationSEPA;
import de.metas.payment.sepa.jaxb.sct.pain_001_003_03.PaymentInstructionInformationSCT;
import de.metas.payment.sepa.jaxb.sct.pain_001_003_03.PaymentMethodSCTCode;
import de.metas.payment.sepa.jaxb.sct.pain_001_003_03.PaymentTypeInformationSCT1;
import de.metas.payment.sepa.jaxb.sct.pain_001_003_03.RemittanceInformationSEPA1Choice;
import de.metas.payment.sepa.jaxb.sct.pain_001_003_03.ServiceLevelSEPA;
import de.metas.payment.sepa.jaxb.sct.pain_001_003_03.StructuredRemittanceInformationSEPA1;
import de.metas.payment.sepa.model.I_SEPA_Export;
import de.metas.payment.sepa.model.I_SEPA_Export_Line;
import de.metas.payment.sepa.spi.ISEPAMarshaller;

/**
 * 
 * @deprecated this is an older SEPA-only marshaler. It is supposed to work (was validated/tested around july 2014), as of now, I would recommend using
 *             {@link SEPACustomerCTIMarshaler_Pain_001_001_03_CH_02}, because it should also cover SEPA, plus Switzerland/Lichtensteinian domestic transactions. That's why i'm depricating this
 *             implementation for now.
 */
@Deprecated
public class SEPACustomerCTIMarshaler_Pain_001_003_03 implements ISEPAMarshaller
{
	private static final String PAIN_001_003_03 = "pain.001.003.03";

	private final DatatypeFactory datatypeFactory;
	private final String encoding = "UTF-8";

	public SEPACustomerCTIMarshaler_Pain_001_003_03()
	{
		try
		{
			datatypeFactory = DatatypeFactory.newInstance();
		}
		catch (final DatatypeConfigurationException e)
		{
			throw new AdempiereException(e);
		}
	}

	public void marshal(final Document xmlDocument, final OutputStream out)
	{
		Check.assumeNotNull(xmlDocument, "xmlDocument not null");
		Check.assumeNotNull(out, "out not null");

		Writer xmlWriter;

		//
		// We force UTF-8 encoding.
		try
		{
			xmlWriter = new OutputStreamWriter(out, encoding);
		}
		catch (UnsupportedEncodingException e)
		{
			throw new AdempiereException("Could not use encoding " + encoding + ": " + e.getLocalizedMessage());
		}

		try
		{
			final JAXBElement<Document> jaxbDocument = new DynamicObjectFactory(new ObjectFactory()).createJAXBElement(xmlDocument);

			final String jaxbContextPath = Document.class.getPackage().getName();
			final JAXBContext jaxbContext = JAXBContext.newInstance(jaxbContextPath);

			final Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty("jaxb.formatted.output", Boolean.TRUE);
			marshaller.setProperty("jaxb.schemaLocation", "urn:sepade:xsd:" + PAIN_001_003_03 + " " + PAIN_001_003_03 + ".xsd");
			marshaller.marshal(jaxbDocument, xmlWriter);
		}
		catch (final JAXBException e)
		{
			throw new AdempiereException("Marshalling error", e.getCause());
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
		catch (final Exception e)
		{
			throw new AdempiereException("Error while marshaling " + sepaDocument, e);
		}
	}

	public Document createDocument(final I_SEPA_Export sepaDocument)
	{
		final Document document = new Document();

		final CustomerCreditTransferInitiationV03 creditTransferInitiation = new CustomerCreditTransferInitiationV03();
		document.setCstmrCdtTrfInitn(creditTransferInitiation);
		//
		// Group Header
		{
			final GroupHeaderSCT groupHeaderSCT = new GroupHeaderSCT();
			creditTransferInitiation.setGrpHdr(groupHeaderSCT);

			// MessageIdentification
			groupHeaderSCT.setMsgId(sepaDocument.getDocumentNo()); // unique identifier in our system

			// CreationDateTime: The system-generated date and time stamp at the time of formatting of the message.
			groupHeaderSCT.setCreDtTm(datatypeFactory.newXMLGregorianCalendar(SystemTime.asGregorianCalendar()));

			// Number of transactions: The total number of direct debit transaction blocks in the message.
			// NOTE: You can have only one direct debit transaction by payment information.
			// NOTE: it will be set later, after we know the number of lines
			groupHeaderSCT.setNbOfTxs(null);

			// Control Sum: This is the total amount of the collection.
			// NOTE: it will be set later, after each payment information
			groupHeaderSCT.setCtrlSum(BigDecimal.ZERO);

			final PartyIdentificationSEPA1 initgPty = new PartyIdentificationSEPA1();
			initgPty.setNm(sepaDocument.getSEPA_CreditorIdentifier());
			groupHeaderSCT.setInitgPty(initgPty);

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

			if (!line.isActive() || line.isError())
			{
				// Error on line. Don't create payment instruction information.
				continue;
			}

			final PaymentInstructionInformationSCT payInsInf = createPaymentInstructionInformation(creditTransferInitiation, sepaDocument, line);
			Check.assumeNotNull(payInsInf, "Payment instruction not null");

			creditTransferInitiation.getPmtInf().add(payInsInf);

			// Update GroupHeader's control amount
			final BigDecimal groupHeaderCtrlAmt = creditTransferInitiation.getGrpHdr().getCtrlSum();
			final BigDecimal groupHeaderCtrlAmtNew = groupHeaderCtrlAmt.add(payInsInf.getCtrlSum());
			creditTransferInitiation.getGrpHdr().setCtrlSum(groupHeaderCtrlAmtNew);

		}
		//
		// Number of transactions: The total number of direct debit transaction blocks in the message.
		// NOTE: You can have only one direct debit transaction by payment information.
		creditTransferInitiation.getGrpHdr().setNbOfTxs(String.valueOf(creditTransferInitiation.getPmtInf().size()));

		sepaDocument.setProcessed(creditTransferInitiation.getPmtInf().size() > 0);
		InterfaceWrapperHelper.save(sepaDocument);
		return document;

	}

	private PaymentInstructionInformationSCT createPaymentInstructionInformation(
			final CustomerCreditTransferInitiationV03 customerCreditTransferInitiation,
			final I_SEPA_Export sepaHdr,
			final I_SEPA_Export_Line line)
	{
		final PaymentInstructionInformationSCT paymentInstruction = new PaymentInstructionInformationSCT();

		// PaymentInformationIdentification: A system-generated internal code.
		{
			// Current index of this payment instruction information
			final int currentIndex = customerCreditTransferInitiation.getPmtInf().size() + 1;
			final String pmtInfId = customerCreditTransferInitiation.getGrpHdr().getMsgId() + "-" + currentIndex;
			paymentInstruction.setPmtInfId(pmtInfId);
		}

		// Sets payment code - credit transfer.
		paymentInstruction.setPmtMtd(PaymentMethodSCTCode.TRF);

		// Setting the control amount later.
		paymentInstruction.setCtrlSum(null);

		//
		// Payment information.
		{
			final PaymentTypeInformationSCT1 paymentTypeInformation = new PaymentTypeInformationSCT1();
			paymentInstruction.setPmtTpInf(paymentTypeInformation);

			// Service level - Hard-coded value of SEPA.
			final ServiceLevelSEPA svcLvl = new ServiceLevelSEPA();
			svcLvl.setCd("SEPA");
			paymentTypeInformation.setSvcLvl(svcLvl);

		}

		final Date dueDate = Services.get(ISEPADocumentBL.class).getDueDate(line);
		paymentInstruction.setReqdExctnDt(newXMLGregorianCalendar(dueDate));

		//
		// Creditor BPartner Account

		final CashAccountSEPA1 dbtrAcct = new CashAccountSEPA1();
		paymentInstruction.setDbtrAcct(dbtrAcct);

		final AccountIdentificationSEPA id = new AccountIdentificationSEPA();
		id.setIBAN(sepaHdr.getIBAN());
		dbtrAcct.setId(id);

		paymentInstruction.setDbtr(convertPartyIdentificationSEPA2(customerCreditTransferInitiation.getGrpHdr().getInitgPty()));

		//
		// Creditor Agent (i.e. Bank)
		{
			final BranchAndFinancialInstitutionIdentificationSEPA3 creditorAgt = new BranchAndFinancialInstitutionIdentificationSEPA3();
			paymentInstruction.setDbtrAgt(creditorAgt);

			final FinancialInstitutionIdentificationSEPA3 finInstnId = new FinancialInstitutionIdentificationSEPA3();
			finInstnId.setBIC(sepaHdr.getSwiftCode());
			creditorAgt.setFinInstnId(finInstnId);
		}

		// Charge Bearer Type
		// Hard-coded value of SLEV.
		paymentInstruction.setChrgBr(ChargeBearerTypeSEPACode.SLEV);

		//
		// Credit Transfer Transaction Information
		{
			final CreditTransferTransactionInformationSCT creditTransferTrxInfo = createCreditTransferTransactionInformation(paymentInstruction, line);
			paymentInstruction.getCdtTrfTxInf().add(creditTransferTrxInfo);

			paymentInstruction.setCtrlSum(creditTransferTrxInfo.getAmt().getInstdAmt().getValue());
		}

		return paymentInstruction;
	}

	private CreditTransferTransactionInformationSCT createCreditTransferTransactionInformation(final PaymentInstructionInformationSCT paymentInstruction, final I_SEPA_Export_Line line)
	{
		final CreditTransferTransactionInformationSCT cdtTrfTxInf = new CreditTransferTransactionInformationSCT();

		//
		// Payment ID
		// EndToEndId: A unique key generated by the system for each payment.
		{
			final String endToEndId = paymentInstruction.getPmtInfId() + "-0001";

			final PaymentIdentificationSEPA pmtId = new PaymentIdentificationSEPA();
			pmtId.setEndToEndId(endToEndId);
			cdtTrfTxInf.setPmtId(pmtId);
		}

		//
		// Amount/Currency
		{
			final String currencyIsoCode = line.getC_Currency().getISO_Code();
			Check.errorUnless(ActiveOrHistoricCurrencyCodeEUR.EUR.name().equals(currencyIsoCode),
					"SEPA_Export_Line {} has an invalid currency {}. This marshaller supports only 'EUR'",
					line, currencyIsoCode);

			final AmountTypeSEPA instdAmount = new AmountTypeSEPA();
			final ActiveOrHistoricCurrencyAndAmountSEPA activeAmt = new ActiveOrHistoricCurrencyAndAmountSEPA();
			activeAmt.setCcy(ActiveOrHistoricCurrencyCodeEUR.EUR);

			final BigDecimal amount = line.getAmt();
			Check.errorIf(amount != null && amount.signum() > 0, "Invalid amount '{}' for {}'", amount, line);
			activeAmt.setValue(amount);

			instdAmount.setInstdAmt(activeAmt);

			cdtTrfTxInf.setAmt(instdAmount);
		}

		//
		// Creditor Agent (i.e. Bank)
		{
			final BranchAndFinancialInstitutionIdentificationSEPA1 creditorAgent = new BranchAndFinancialInstitutionIdentificationSEPA1();
			cdtTrfTxInf.setCdtrAgt(creditorAgent);

			final FinancialInstitutionIdentificationSEPA1 finInstId = new FinancialInstitutionIdentificationSEPA1();
			creditorAgent.setFinInstnId(finInstId);
			finInstId.setBIC(line.getSwiftCode());
		}

		//
		// Creditor BPartner
		{

			final PartyIdentificationSEPA2 creditor = new PartyIdentificationSEPA2();
			cdtTrfTxInf.setCdtr(creditor);

			creditor.setNm(line.getSEPA_MandateRefNo());

			// TODO: Note: Creditor address not mandatory (I hope)
		}

		//
		// Debitor BPartner Account
		{
			final CashAccountSEPA2 creditorAcct = new CashAccountSEPA2();
			cdtTrfTxInf.setCdtrAcct(creditorAcct);

			final AccountIdentificationSEPA id = new AccountIdentificationSEPA();
			creditorAcct.setId(id);
			final String iban = line.getIBAN();
			Check.errorIf(Check.isEmpty(iban, true),
					"marshaler {} requireds a IBAN in line {}. (Note: this marshaller/pain does not support GenericAccountIdentification)",
					this, line);
			id.setIBAN(iban);
		}

		// Remittance Info
		{
			final RemittanceInformationSEPA1Choice rmfInf = new RemittanceInformationSEPA1Choice();
			if (Check.isEmpty(line.getStructuredRemittanceInfo(), true))
			{
				// provide the line-description (if set) as unstructured remittance info
				if (Check.isEmpty(line.getDescription(), true))
				{
					// at least add a " " to make sure the node exists.
					rmfInf.setUstrd(" ");
				}
				else
				{
					rmfInf.setUstrd(line.getDescription() + " ");
				}
			}
			else
			{
				// task 07789
				final StructuredRemittanceInformationSEPA1 strd = new StructuredRemittanceInformationSEPA1();
				rmfInf.setStrd(strd);
				final CreditorReferenceInformationSEPA1 cdtrRefInf = new CreditorReferenceInformationSEPA1();
				strd.setCdtrRefInf(cdtrRefInf);
				cdtrRefInf.setRef(line.getStructuredRemittanceInfo());
			}
			cdtTrfTxInf.setRmtInf(rmfInf);
		}

		return cdtTrfTxInf;
	}

	private PartyIdentificationSEPA2 convertPartyIdentificationSEPA2(final PartyIdentificationSEPA1 initgPty)
	{
		final PartyIdentificationSEPA2 partyCopy = new PartyIdentificationSEPA2();

		partyCopy.setId(initgPty.getId());
		partyCopy.setNm(initgPty.getNm());

		return partyCopy;
	}

	private XMLGregorianCalendar newXMLGregorianCalendar(final Date date)
	{
		final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		final String dateStr = df.format(date);

		return datatypeFactory.newXMLGregorianCalendar(dateStr);
	}

	@Override
	public String getPainIdentifier()
	{
		return PAIN_001_003_03;
	}

	@Override
	public boolean isSupportsOtherCurrencies()
	{
		return false;
	}

	@Override
	public boolean isSupportsGenericAccountIdentification()
	{
		return false;
	}

	@Override
	public Set<SupportedTransactionType> getSupportedTransactionTypes()
	{
		return Collections.singleton(SupportedTransactionType.credit);
	}

	@Override
	public String toString()
	{
		return String
				.format("SEPACustomerCTIMarshaler_Pain_001_001_03_CH_02 [getPainIdentifier()=%s, isSupportsOtherCurrencies()=%s, isSupportsGenericAccountIdentification()=%s, getSupportedTransactionTypes()=%s]",
						getPainIdentifier(), isSupportsOtherCurrencies(), isSupportsGenericAccountIdentification(), getSupportedTransactionTypes());
	}

}
