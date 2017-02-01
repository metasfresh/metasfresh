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
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.bpartner.service.IBPartnerDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.adempiere.util.jaxb.DynamicObjectFactory;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_Location;

import de.metas.adempiere.model.I_C_BPartner_Location;
import de.metas.payment.sepa.api.ISEPADocumentBL;
import de.metas.payment.sepa.api.ISEPADocumentDAO;
import de.metas.payment.sepa.api.SepaMarshallerException;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_03_ch_02.AccountIdentification4ChoiceCH;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_03_ch_02.ActiveOrHistoricCurrencyAndAmount;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_03_ch_02.AmountType3Choice;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_03_ch_02.BranchAndFinancialInstitutionIdentification4CH;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_03_ch_02.BranchAndFinancialInstitutionIdentification4CHBicOrClrId;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_03_ch_02.CashAccount16CHId;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_03_ch_02.CashAccount16CHIdTpCcy;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_03_ch_02.ChargeBearerType1Code;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_03_ch_02.ClearingSystemIdentification2Choice;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_03_ch_02.ClearingSystemMemberIdentification2;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_03_ch_02.CreditTransferTransactionInformation10CH;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_03_ch_02.CreditorReferenceInformation2;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_03_ch_02.CustomerCreditTransferInitiationV03CH;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_03_ch_02.Document;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_03_ch_02.FinancialInstitutionIdentification7CH;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_03_ch_02.FinancialInstitutionIdentification7CHBicOrClrId;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_03_ch_02.GenericAccountIdentification1CH;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_03_ch_02.GenericFinancialIdentification1CH;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_03_ch_02.GroupHeader32CH;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_03_ch_02.LocalInstrument2Choice;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_03_ch_02.ObjectFactory;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_03_ch_02.PartyIdentification32CH;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_03_ch_02.PartyIdentification32CHName;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_03_ch_02.PartyIdentification32CHNameAndId;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_03_ch_02.PaymentIdentification1;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_03_ch_02.PaymentInstructionInformation3CH;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_03_ch_02.PaymentMethod3Code;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_03_ch_02.PaymentTypeInformation19CH;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_03_ch_02.PostalAddress6CH;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_03_ch_02.RemittanceInformation5CH;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_03_ch_02.ServiceLevel8Choice;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_03_ch_02.StructuredRemittanceInformation7;
import de.metas.payment.sepa.model.I_SEPA_Export;
import de.metas.payment.sepa.model.I_SEPA_Export_Line;
import de.metas.payment.sepa.spi.ISEPAMarshaller;

/**
 * Written according to "Schweizer Implementation Guidelines für Kunde-an-Bank-Meldungen für Überweisungen im Zahlungsverkehr", "Version 1.4/30.06.2013". There link is
 * <a href="http://www.six-interbank-clearing.com/en/home/standardization/iso-payments/customer-bank/implementation-guidelines.html">here</a>.
 * <p>
 * Important note:<b>This is a partial implementation!!</b> The above-mentioned document specifies 9 different mayment modes ("Zahlarten") for different business case, ranging from scanned ESR-String,
 * domestic transaction to "dowhat you want, it's the banks problem" arbitrary currencies and ways to specify the accounts and banks</b>
 * <p>
 * This marshaler supports:
 * <ul>
 * <li>Zahlart 1: ESR-Zahlschein. A ESR reference, usually scanned from a payment slip. Note that for this Zahlart, we don't (and in fact are forbidden to) deliver information about the creditor's
 * bank (i.e. no BIC, no BC).
 * <li>Zahlart 3: domestic (switzerland/lichtenstein) credit transaction with the IBAN or swizz Postbank-AccountNo to identify the the account and the BIC or swizz BC ("Bank Code") to identify the
 * bank. Note: the BC is contained in the swizz IBAN (link <a href="http://www.swissiban.com/de.htm">here</a>), and this marshaler prefers that BC over a BIC/swift code that might or might not be
 * contained in the respective export line.
 * <li>Zahlart 5: european SEPA (the <i>real</i> ones) transactions. Just IBAN and BIC are allowed. Note: if there is no BIC available from the respective export line, then we output
 * <code>NOTPROVIDED</code>. This <b>should</b> work (google for "IBAN-only transaction")
 * </ul>
 * <p>
 * All other payment modes are <b>not</b> supported (or maybe just "incidentally", when I think of Zahlart 6).
 */
public class SEPACustomerCTIMarshaler_Pain_001_001_03_CH_02 implements ISEPAMarshaller
{
	public static final String REGEXP_STREET_AND_NUMER_SPLIT = "^([^0-9]+) ?([0-9]+.*$)?";

	private static final String BIC_NOTPROVIDED = "NOTPROVIDED";

	/**
	 * Identifier of the <b>Pa</b>yment <b>In</b>itiation format (XSD) used by this marshaller.
	 */
	private static final String PAIN_001_001_03_CH_02 = "pain.001.001.03.ch.02";

	private static final String ZAHLUNGS_ART_1 = "ZAHLUNGS_ART_1";
	private static final String ZAHLUNGS_ART_2_1 = "ZAHLUNGS_ART_2_1";
	private static final String ZAHLUNGS_ART_2_2 = "ZAHLUNGS_ART_2_2";
	private static final String ZAHLUNGS_ART_3 = "ZAHLUNGS_ART_3";
	private static final String ZAHLUNGS_ART_4 = "ZAHLUNGS_ART_4";
	private static final String ZAHLUNGS_ART_5 = "ZAHLUNGS_ART_5";
	private static final String ZAHLUNGS_ART_6 = "ZAHLUNGS_ART_6";
	private static final String ZAHLUNGS_ART_7 = "ZAHLUNGS_ART_7";
	private static final String ZAHLUNGS_ART_8 = "ZAHLUNGS_ART_8";

	private final DatatypeFactory datatypeFactory;
	private final String encoding = "UTF-8";

	public SEPACustomerCTIMarshaler_Pain_001_001_03_CH_02()
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

	private void marshal(final Document xmlDocument, final OutputStream out)
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
			marshaller.setProperty("jaxb.schemaLocation", "urn:sepade:xsd:" + PAIN_001_001_03_CH_02 + " " + PAIN_001_001_03_CH_02 + ".xsd");
			marshaller.marshal(jaxbDocument, xmlWriter);
		}
		catch (final JAXBException e)
		{
			throw new AdempiereException("Marshalling error", e.getCause());
		}
	}

	@Override
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

	private Document createDocument(final I_SEPA_Export sepaDocument)
	{
		final Document document = new Document();

		final CustomerCreditTransferInitiationV03CH creditTransferInitiation = new CustomerCreditTransferInitiationV03CH();
		document.setCstmrCdtTrfInitn(creditTransferInitiation);
		//
		// Group Header
		{
			final GroupHeader32CH groupHeaderSCT = new GroupHeader32CH();
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

			final PartyIdentification32CHNameAndId initgPty = new PartyIdentification32CHNameAndId();
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

			final PaymentInstructionInformation3CH payInsInf = createPaymentInstructionInformation(creditTransferInitiation, sepaDocument, line);
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

	private PaymentInstructionInformation3CH createPaymentInstructionInformation(
			final CustomerCreditTransferInitiationV03CH customerCreditTransferInitiation,
			final I_SEPA_Export sepaHdr,
			final I_SEPA_Export_Line line)
	{
		final PaymentInstructionInformation3CH pmtInf = new PaymentInstructionInformation3CH();

		// PaymentInformationIdentification: A system-generated internal code.
		{
			// Current index of this payment instruction information
			final int currentIndex = customerCreditTransferInitiation.getPmtInf().size() + 1;
			final String pmtInfId = customerCreditTransferInitiation.getGrpHdr().getMsgId() + "-" + currentIndex;
			pmtInf.setPmtInfId(pmtInfId);
		}

		// Sets payment code - credit transfer.
		pmtInf.setPmtMtd(PaymentMethod3Code.TRF);

		// task 08354: set "Batch Booking" to false. What we want from the bank is a statement with one line per pmtInf. Even with batch booking = true, this should be the case
		// (according to
		// http://www.six-interbank-clearing.com/dam/downloads/de/standardization/iso/swiss-recommendations/implementation-guidelines-ct/standardization_isopayments_iso_20022_ch_implementation_guidelines_ct.pdf
		// page 20)
		// but it isn't! Therefore, we try it with setting this to false, to make our intent more clear and hopefully get our detailed statement.
		pmtInf.setBtchBookg(Boolean.FALSE);

		// Setting the control amount later.
		pmtInf.setCtrlSum(null);

		// zahlungsart
		final String paymentMode = getPaymentMode(line);

		//
		// Payment type information.
		{
			final PaymentTypeInformation19CH pmtTpInf = new PaymentTypeInformation19CH();
			pmtInf.setPmtTpInf(pmtTpInf);

			// service level
			if (paymentMode == ZAHLUNGS_ART_5)
			{
				// ServiceLEvel.Code "SEPA" does not work if we are doing transactions in swizz. TODO: consider to introduce a decent switch
				// Service level - Hard-coded value of SEPA.
				final ServiceLevel8Choice svcLvl = new ServiceLevel8Choice();
				svcLvl.setCd("SEPA");
				pmtTpInf.setSvcLvl(svcLvl);
			}
			else if (paymentMode == ZAHLUNGS_ART_1)
			{
				// local instrument
				final LocalInstrument2Choice lclInstrm = new LocalInstrument2Choice();
				lclInstrm.setPrtry("CH01"); // Zahlungsart 1
				pmtTpInf.setLclInstrm(lclInstrm);
			}
			else if (paymentMode == ZAHLUNGS_ART_2_1)
			{
				// local instrument
				final LocalInstrument2Choice lclInstrm = new LocalInstrument2Choice();
				lclInstrm.setPrtry("CH02"); // Zahlungsart 2.1
				pmtTpInf.setLclInstrm(lclInstrm);
			}
			else if (paymentMode == ZAHLUNGS_ART_2_2)
			{
				// local instrument
				final LocalInstrument2Choice lclInstrm = new LocalInstrument2Choice();
				lclInstrm.setPrtry("CH03"); // Zahlungsart 2.2
				pmtTpInf.setLclInstrm(lclInstrm);
			}
		}

		final Date dueDate = Services.get(ISEPADocumentBL.class).getDueDate(line);
		pmtInf.setReqdExctnDt(newXMLGregorianCalendar(dueDate));

		//
		// debitor
		pmtInf.setDbtr(copyPartyIdentificationSEPA2(
				customerCreditTransferInitiation.getGrpHdr().getInitgPty()));

		//
		// debitor Account
		final String iban = sepaHdr.getIBAN();
		final CashAccount16CHIdTpCcy dbtrAcct = new CashAccount16CHIdTpCcy();
		pmtInf.setDbtrAcct(dbtrAcct);
		final AccountIdentification4ChoiceCH id = new AccountIdentification4ChoiceCH();
		dbtrAcct.setId(id);
		if (Check.isEmpty(iban, true))
		{
			final GenericAccountIdentification1CH othr = new GenericAccountIdentification1CH();
			id.setOthr(othr);
			othr.setId(line.getOtherAccountIdentification());
		}
		else
		{
			id.setIBAN(iban.replaceAll(" ", ""));
		}
		//
		// Debitor Agent (i.e. Bank)
		{
			final BranchAndFinancialInstitutionIdentification4CHBicOrClrId dbtrAgt = new BranchAndFinancialInstitutionIdentification4CHBicOrClrId();
			pmtInf.setDbtrAgt(dbtrAgt);

			final FinancialInstitutionIdentification7CHBicOrClrId finInstnId = new FinancialInstitutionIdentification7CHBicOrClrId();
			finInstnId.setBIC(sepaHdr.getSwiftCode());
			dbtrAgt.setFinInstnId(finInstnId);
		}

		// Charge Bearer Type
		// Hard-coded value of SLEV.
		pmtInf.setChrgBr(ChargeBearerType1Code.SLEV);

		//
		// Credit Transfer Transaction Information
		{
			final CreditTransferTransactionInformation10CH creditTransferTrxInfo = createCreditTransferTransactionInformation(pmtInf, line);
			pmtInf.getCdtTrfTxInf().add(creditTransferTrxInfo);

			pmtInf.setCtrlSum(creditTransferTrxInfo.getAmt().getInstdAmt().getValue());
		}

		return pmtInf;
	}

	private CreditTransferTransactionInformation10CH createCreditTransferTransactionInformation(final PaymentInstructionInformation3CH paymentInstruction,
			final I_SEPA_Export_Line line)
	{
		final CreditTransferTransactionInformation10CH cdtTrfTxInf = new CreditTransferTransactionInformation10CH();

		//
		// Payment ID
		// EndToEndId: A unique key generated by the system for each payment.
		{
			final String endToEndId = paymentInstruction.getPmtInfId() + "-0001";

			final PaymentIdentification1 pmtId = new PaymentIdentification1();
			pmtId.setEndToEndId(endToEndId);
			cdtTrfTxInf.setPmtId(pmtId);
		}

		//
		// Amount/Currency
		{
			final AmountType3Choice amt = new AmountType3Choice();
			final ActiveOrHistoricCurrencyAndAmount instdAmt = new ActiveOrHistoricCurrencyAndAmount();

			final String currencyIsoCode = line.getC_Currency().getISO_Code();
			instdAmt.setCcy(currencyIsoCode);

			final BigDecimal amount = line.getAmt();
			Check.assume(amount != null && amount.signum() > 0, "Invalid amount {} for {}", amount, line);
			instdAmt.setValue(amount);

			amt.setInstdAmt(instdAmt);

			cdtTrfTxInf.setAmt(amt);
		}

		final String paymentMode = getPaymentMode(line);

		//
		// Creditor Agent (i.e. Bank)
		// not allowed for 1, 2.1, 7 and 8, must for the rest
		if (paymentMode != ZAHLUNGS_ART_1
				&& paymentMode != ZAHLUNGS_ART_2_1
				&& paymentMode != ZAHLUNGS_ART_7
				&& paymentMode != ZAHLUNGS_ART_8)
		{

			final BranchAndFinancialInstitutionIdentification4CH cdtrAgt = new BranchAndFinancialInstitutionIdentification4CH();
			cdtTrfTxInf.setCdtrAgt(cdtrAgt);

			final FinancialInstitutionIdentification7CH finInstnId = new FinancialInstitutionIdentification7CH();
			cdtrAgt.setFinInstnId(finInstnId);

			final String bcFromIBAN = extractBCFromIban(line.getIBAN(), line);
			if (!Check.isEmpty(bcFromIBAN))
			{
				// this is our best bet. Even data in adempiere might be wrong/outdated
				final ClearingSystemMemberIdentification2 clrSysMmbId = new ClearingSystemMemberIdentification2();
				finInstnId.setClrSysMmbId(clrSysMmbId);

				clrSysMmbId.setMmbId(bcFromIBAN);

				// we set the BC, but we need to also indicate the sort of MmbId value
				final ClearingSystemIdentification2Choice clrSysId = new ClearingSystemIdentification2Choice();
				clrSysMmbId.setClrSysId(clrSysId);

				// has to be CHBCC for payment modes 2.2, 3, 4; note if we do paymentMode 5 ("real" SEPA), we ren't in this if-block to start with
				clrSysId.setCd("CHBCC");
			}
			else if (!Check.isEmpty(line.getSwiftCode(), true))
			{
				finInstnId.setBIC(line.getSwiftCode());
			}
			else
			{
				// let the bank see what it can do
				finInstnId.setBIC(BIC_NOTPROVIDED);
			}

			//
			// Name
			if (paymentMode == ZAHLUNGS_ART_2_2
					|| paymentMode == ZAHLUNGS_ART_4
					|| paymentMode == ZAHLUNGS_ART_6)
			{
				final String bankName = getBankNameIfAny(line);

				Check.errorIf(Check.isEmpty(bankName, true), SepaMarshallerException.class,
						"Zahlart={}, but line {} has no information about the bank name",
						paymentMode, createInfo(line));

				finInstnId.setNm(bankName);
			}

			if (paymentMode == ZAHLUNGS_ART_4
					|| paymentMode == ZAHLUNGS_ART_6)
			{
				// see if we can also export the bank's address
				if (line.getC_BP_BankAccount_ID() > 0
						&& line.getC_BP_BankAccount().getC_Bank_ID() > 0
						&& line.getC_BP_BankAccount().getC_Bank().getC_Location_ID() > 0)
				{
					final I_C_Location bankLocation = line.getC_BP_BankAccount().getC_Bank().getC_Location();
					final PostalAddress6CH pstlAdr = createPstlAdr(bankLocation);

					finInstnId.setPstlAdr(pstlAdr);
				}
			}

			//
			// Other
			if (paymentMode == ZAHLUNGS_ART_2_2)
			{
				final GenericFinancialIdentification1CH othr = new GenericFinancialIdentification1CH();
				finInstnId.setOthr(othr);

				othr.setId(line.getOtherAccountIdentification());
			}
		}

		//
		// Creditor BPartner
		{
			final PartyIdentification32CHName cdtr = new PartyIdentification32CHName();
			cdtTrfTxInf.setCdtr(cdtr);

			;

			// Note: since old age we use SEPA_MandateRefNo for the creditor's name (I don't remember why)
			// task 09617: prefer C_BP_BankAccount.A_Name if available; keep SEPA_MandateRefNo, because setting it as "cdtr/name" might be a best practice
			cdtr.setNm(
					getFirstNonEmpty(
							line.getSEPA_MandateRefNo(),
							line.getC_BP_BankAccount().getA_Name(),
							line.getC_BPartner().getName()));

			// task 08655: also provide the creditor's address
			final Properties ctx = InterfaceWrapperHelper.getCtx(line);
			final I_C_BPartner_Location billToLocation = Services.get(IBPartnerDAO.class).retrieveBillToLocation(ctx, line.getC_BPartner_ID(), true, ITrx.TRXNAME_None);
			if (billToLocation != null)
			{
				cdtr.setPstlAdr(createPstlAdr(line.getC_BP_BankAccount(), billToLocation.getC_Location()));
			}
		}

		//
		// creditor BPartner Account
		{
			final CashAccount16CHId cdtrAcct = new CashAccount16CHId();
			cdtTrfTxInf.setCdtrAcct(cdtrAcct);

			final AccountIdentification4ChoiceCH id = new AccountIdentification4ChoiceCH();
			cdtrAcct.setId(id);

			// note that we prefer "otherAccountIdentification", if it is set, because it is the less general case,
			// and so if set, it is probably inteneded to be used (and we can't use both other and IBAN)
			final String otherAccountIdentification = line.getOtherAccountIdentification();
			if (Check.isEmpty(otherAccountIdentification, true))
			{
				final String iban = line.getIBAN();

				id.setIBAN(iban.replaceAll(" ", "")); // this is ofc the more frequent case (..on a global scale)
			}
			else
			{
				// task 07789
				final GenericAccountIdentification1CH othr = new GenericAccountIdentification1CH();
				othr.setId(otherAccountIdentification); // for task 07789, this needs to contain the ESR TeilehmerNr or PostkontoNr
				id.setOthr(othr);
			}
		}

		// Remittance Info
		{
			final RemittanceInformation5CH rmfInf = new RemittanceInformation5CH();
			if (Check.isEmpty(line.getStructuredRemittanceInfo(), true) || paymentMode == ZAHLUNGS_ART_5)
			{
				Check.errorIf(paymentMode == ZAHLUNGS_ART_1, SepaMarshallerException.class,
						"SEPA_ExportLine {} has to have StructuredRemittanceInfo", createInfo(line));

				// note: we use the structuredRemittanceInfo in ustrd, if we do SEPA (zahlart 5),
				// because it's much less complicated
				final String reference = Check.isEmpty(line.getStructuredRemittanceInfo(), true)
						? line.getDescription()
						: line.getStructuredRemittanceInfo();

				// provide the line-description (if set) as unstructured remittance info
				if (Check.isEmpty(reference, true))
				{
					// at least add a "." to make sure the node exists.
					rmfInf.setUstrd(".");
				}
				else
				{
					rmfInf.setUstrd(reference);
				}
			}
			else
			{
				// task 07789
				final StructuredRemittanceInformation7 strd = new StructuredRemittanceInformation7();
				rmfInf.setStrd(strd);
				final CreditorReferenceInformation2 cdtrRefInf = new CreditorReferenceInformation2();
				strd.setCdtrRefInf(cdtrRefInf);
				cdtrRefInf.setRef(line.getStructuredRemittanceInfo());
			}
			cdtTrfTxInf.setRmtInf(rmfInf);
		}

		return cdtTrfTxInf;
	}

	private String getFirstNonEmpty(final String... values)
	{
		for (final String value : values)
		{
			if (!Check.isEmpty(value, true))
			{
				return value.trim();
			}
		}
		return null;
	}

	/**
	 * @task 08655
	 * @TODO: extract the core part into a general address-BL (also move the unit tests along); also handle the case of number-first (like e.g. in france)
	 */
	/* package */PostalAddress6CH createPstlAdr(final I_C_Location location)
	{
		final PostalAddress6CH pstlAdr = new PostalAddress6CH();

		splitStreetAndNumber(location.getAddress1(), pstlAdr);

		pstlAdr.setPstCd(getFirstNonEmpty(location.getPostal()));
		pstlAdr.setTwnNm(getFirstNonEmpty(location.getCity()));
		pstlAdr.setCtry(getFirstNonEmpty(location.getC_Country().getCountryCode())); // note: C_Location.C_Country is a mandatory column

		return pstlAdr;
	}

	private PostalAddress6CH createPstlAdr(final org.compiere.model.I_C_BP_BankAccount bpBankAccount,
			final I_C_Location location)
	{
		final PostalAddress6CH pstlAdr = createPstlAdr(location);
		if (bpBankAccount == null)
		{
			return pstlAdr;
		}
		splitStreetAndNumber(bpBankAccount.getA_Street(), pstlAdr);

		pstlAdr.setPstCd(getFirstNonEmpty(bpBankAccount.getA_Zip(), pstlAdr.getPstCd()));
		pstlAdr.setTwnNm(getFirstNonEmpty(bpBankAccount.getA_City(), pstlAdr.getTwnNm()));
		pstlAdr.setCtry(getFirstNonEmpty(bpBankAccount.getA_Country(), pstlAdr.getCtry()));

		return pstlAdr;
	}

	private void splitStreetAndNumber(final String streetAndNumber,
			final PostalAddress6CH pstlAdr)
	{
		if (Check.isEmpty(streetAndNumber, true))
		{
			return;
		}

		final Pattern pattern = Pattern.compile(REGEXP_STREET_AND_NUMER_SPLIT);
		final Matcher matcher = pattern.matcher(streetAndNumber);
		if (!matcher.matches())
		{
			return;
		}

		final String street = matcher.group(1);
		final String number = matcher.group(2);

		pstlAdr.setStrtNm(getFirstNonEmpty(street));
		pstlAdr.setBldgNb(getFirstNonEmpty(number));
	}

	/**
	 * for IBANs that start with "CH" or "LI", this method extracts the swizz Banking code and returns it. If the given <code>iban</code> is <code>null</code>, emtpy of not "CH"/"LI", it returns
	 * <code>null</code>.
	 *
	 * @param iban
	 * @return
	 * @see <a href="http://www.swissiban.com/de.htm">http://www.swissiban.com/de.htm</a> for what it does (it's simple).
	 */
	private String extractBCFromIban(final String iban, final I_SEPA_Export_Line line)
	{
		if (Check.isEmpty(iban, true))
		{
			return null;
		}
		if (!isSwizzIBAN(iban))
		{
			return null;
		}

		final int bcStartIdx = 4;
		final int bcEndIdx = 9;
		final String ibanToUse = iban.replaceAll(" ", "");

		Check.errorIf(ibanToUse.length() < bcEndIdx,
				SepaMarshallerException.class,
				"Given IBAN {} for line {} is to short. Pls verify that it's actually an IBAN at all",
				iban, createInfo(line));
		return ibanToUse.substring(bcStartIdx, bcEndIdx);
	}

	/**
	 * Returns true if the given IBAN is supposed to contain a swizz bank code (BC). This can be assumes if the given IBAN (stripped from spaces) starts with either "CH" or "LI".
	 *
	 * @param iban
	 * @return
	 */
	private boolean isSwizzIBAN(final String iban)
	{
		if (Check.isEmpty(iban, true))
		{
			return false;
		}
		final String ibanToUse = iban.replaceAll(" ", "");
		final boolean isIbanWithBC = ibanToUse.startsWith("CH") || ibanToUse.startsWith("LI");

		return isIbanWithBC;
	}

	protected String getBankNameIfAny(final I_SEPA_Export_Line line)
	{
		final org.compiere.model.I_C_Bank bank = line.getC_BP_BankAccount().getC_Bank();
		final String bankName = bank == null ? "" : bank.getName();
		return bankName;
	}

	private PartyIdentification32CH copyPartyIdentificationSEPA2(final PartyIdentification32CHNameAndId initgPty)
	{
		final PartyIdentification32CH partyCopy = new PartyIdentification32CH();

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
		return PAIN_001_001_03_CH_02;
	}

	@Override
	public boolean isSupportsOtherCurrencies()
	{
		return true;
	}

	@Override
	public boolean isSupportsGenericAccountIdentification()
	{
		return true;
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

	private String getPaymentMode(final I_SEPA_Export_Line line)
	{
		final String paymentMode;
		final de.metas.payment.esr.model.I_C_BP_BankAccount bPBankAccount = InterfaceWrapperHelper.create(line.getC_BP_BankAccount(), de.metas.payment.esr.model.I_C_BP_BankAccount.class);
		if (bPBankAccount.isEsrAccount() && !Check.isEmpty(line.getStructuredRemittanceInfo(), true))
		{
			paymentMode = ZAHLUNGS_ART_1;
		}
		else
		{
			final String currencyIso = bPBankAccount.getC_Currency().getISO_Code();

			final String iban = line.getIBAN();

			final boolean swizzIban = isSwizzIBAN(iban);
			if (swizzIban || bPBankAccount.isEsrAccount())
			{
				// "domestic" IBAN. it contains the bank code (BC) and we will use it.
				Check.errorIf(!"EUR".equals(currencyIso) && !"CHF".equals(currencyIso),
						SepaMarshallerException.class,
						"line {} has a swizz IBAN, but the currency is {} instead of 'CHF' or 'EUR'",
						createInfo(line), currencyIso);

				paymentMode = ZAHLUNGS_ART_3; // we can go with zahlart 2.2
			}
			else
			{
				Check.errorIf(Check.isEmpty(iban, true), SepaMarshallerException.class,
						"line {} has a non-ESR bank account, and no IBAN",
						createInfo(line));

				// international IBAN
				Check.errorIf(!"EUR".equals(currencyIso),
						SepaMarshallerException.class,
						"line {} has a non-IBAN {}, but the currency is {} instead of 'EUR'",
						line, iban, currencyIso);
				paymentMode = ZAHLUNGS_ART_5; //
			}
		}

		return paymentMode;
	}

	private String createInfo(final I_SEPA_Export_Line line)
	{
		final StringBuilder sb = new StringBuilder();
		if (line.getC_BPartner_ID() > 0)
		{
			sb.append("@C_BPartner_ID@ ");
			sb.append(line.getC_BPartner().getName());
		}
		if (line.getC_BP_BankAccount_ID() > 0)
		{
			sb.append(" @C_BP_BankAccount_ID@ ");
			sb.append(line.getC_BP_BankAccount().getDescription());
		}
		return Services.get(IMsgBL.class).parseTranslation(InterfaceWrapperHelper.getCtx(line), sb.toString());
	}
}
