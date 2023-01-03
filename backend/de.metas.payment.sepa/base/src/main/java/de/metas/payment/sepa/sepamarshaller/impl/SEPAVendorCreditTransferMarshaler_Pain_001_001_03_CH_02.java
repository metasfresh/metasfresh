package de.metas.payment.sepa.sepamarshaller.impl;

import com.google.common.annotations.VisibleForTesting;
import de.metas.banking.Bank;
import de.metas.banking.BankId;
import de.metas.banking.api.BankRepository;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.Currency;
import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyDAO;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.location.ILocationDAO;
import de.metas.money.CurrencyId;
import de.metas.payment.sepa.api.ISEPADocumentBL;
import de.metas.payment.sepa.api.ISEPADocumentDAO;
import de.metas.payment.sepa.api.SEPAExportContext;
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
import de.metas.payment.sepa.jaxb.sct.pain_001_001_03_ch_02.ContactDetails2CH;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_03_ch_02.CreditTransferTransactionInformation10CH;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_03_ch_02.CreditorReferenceInformation2;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_03_ch_02.CreditorReferenceType1Choice;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_03_ch_02.CreditorReferenceType2;
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
import de.metas.util.Check;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import de.metas.util.StringUtils.TruncateAt;
import de.metas.util.xml.DynamicObjectFactory;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IPair;
import org.compiere.Adempiere;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.compiere.util.Util.ArrayKey;

import javax.annotation.Nullable;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Supplier;

import static java.math.BigDecimal.ZERO;

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
public class SEPAVendorCreditTransferMarshaler_Pain_001_001_03_CH_02 implements SEPAMarshaler
{
	public static final String NOTPROVIDED_VALUE = "NOTPROVIDED";

	private static final AdMessageKey ERR_SEPA_Export_InvalidReference = AdMessageKey.of("de.metas.payment.sepa.SEPA_Export_InvalidReference");

	/**
	 * Identifier of the <b>Pa</b>yment <b>In</b>itiation format (XSD) used by this marshaller.
	 */
	private static final String PAIN_001_001_03_CH_02 = "pain.001.001.03.ch.02";
	private static final String PAIN_001_001_03_CH_02_SCHEMALOCATION = "http://www.six-interbank-clearing.com/de/";

	/**
	 * Title: "ISR"
	 */
	private static final String PAYMENT_TYPE_1 = "PAYMENT_TYPE_1";

	/**
	 * Title: "IS 1-Stage". Currently not implemented.
	 */
	private static final String PAYMENT_TYPE_2_1 = "PAYMENT_TYPE_2_1";

	/**
	 * Title: "IS 2-Stage". Currently not implemented.
	 */
	private static final String PAYMENT_TYPE_2_2 = "PAYMENT_TYPE_2_2";

	/**
	 * Title: "IBAN/postal account and IID/BIC"
	 */
	private static final String PAYMENT_TYPE_3 = "PAYMENT_TYPE_3";

	/**
	 * Title: "Foreign currency". Currently not implemented.
	 */
	private static final String PAYMENT_TYPE_4 = "PAYMENT_TYPE_4";

	/**
	 * Title: "Foreign SEPA"
	 */
	private static final String PAYMENT_TYPE_5 = "PAYMENT_TYPE_5";

	/**
	 * Title: "Foreign"
	 */
	private static final String PAYMENT_TYPE_6 = "PAYMENT_TYPE_6";

	/**
	 * Title: "Bank cheque/Postcash domestic and foreign". Currently not implemented.
	 */
	private static final String PAYMENT_TYPE_8 = "PAYMENT_TYPE_8";

	private final ObjectFactory objectFactory;
	private final DatatypeFactory datatypeFactory;
	private final BankRepository bankRepo;
	private final SEPAExportContext exportContext;
	private final ILocationDAO locationDAO = Services.get(ILocationDAO.class);

	private static final String encoding = "UTF-8";

	private int endToEndIdCounter = 0;
	private int pmtInfCounter = 0;

	private static final String FORBIDDEN_CHARS = "([^a-zA-Z0-9\\.,;:'\\+\\-/\\(\\)?\\*\\[\\]\\{\\}\\\\`´~ !\"#%&<>÷=@_$£àáâäçèéêëìíîïñòóôöùúûüýßÀÁÂÄÇÈÉÊËÌÍÎÏÒÓÔÖÙÚÛÜÑ])";

	public SEPAVendorCreditTransferMarshaler_Pain_001_001_03_CH_02(
			@NonNull final BankRepository bankRepo,
			@NonNull final SEPAExportContext exportContext)
	{
		objectFactory = new ObjectFactory();
		try
		{
			datatypeFactory = DatatypeFactory.newInstance();
		}
		catch (final DatatypeConfigurationException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}

		this.bankRepo = bankRepo;
		this.exportContext = exportContext;
	}

	private void marshal(
			@NonNull final Document xmlDocument,
			@NonNull final OutputStream out)
	{
		// We force UTF-8 encoding.
		final Writer xmlWriter;
		try
		{
			xmlWriter = new OutputStreamWriter(out, encoding);
		}
		catch (final UnsupportedEncodingException e)
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
			marshaller.setProperty("jaxb.schemaLocation", PAIN_001_001_03_CH_02_SCHEMALOCATION + PAIN_001_001_03_CH_02 + ".xsd " + PAIN_001_001_03_CH_02 + ".xsd");
			marshaller.marshal(jaxbDocument, xmlWriter);
		}
		catch (final JAXBException e)
		{
			throw new AdempiereException("Marshalling error", e.getCause());
		}
	}

	@Override
	public void marshal(
			@NonNull final I_SEPA_Export sepaDocument,
			@NonNull final OutputStream out)
	{
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

	@VisibleForTesting
	Document createDocument(@NonNull final I_SEPA_Export sepaDocument)
	{
		final Document document = objectFactory.createDocument();

		final CustomerCreditTransferInitiationV03CH creditTransferInitiation = objectFactory.createCustomerCreditTransferInitiationV03CH();
		document.setCstmrCdtTrfInitn(creditTransferInitiation);

		// Group Header
		{
			final GroupHeader32CH groupHeaderSCT = objectFactory.createGroupHeader32CH();
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

			final PartyIdentification32CHNameAndId initgPty = objectFactory.createPartyIdentification32CHNameAndId();
			initgPty.setNm(sepaDocument.getSEPA_CreditorName());

			final ContactDetails2CH ctctDtls = objectFactory.createContactDetails2CH();
			ctctDtls.setNm("metasfresh");

			// if we must truncate, then leave the beginning and discard the end
			// rationale: when we are depending on this, the resp file is probably a bit only and then the "year" is more important to know that the build#
			final String versionString = Adempiere.getBuildVersion().getFullVersion();
			final String truncatedVersionString = StringUtils.trunc(versionString.trim(), 35, TruncateAt.STRING_START);

			ctctDtls.setOthr(truncatedVersionString); // 35 is the max allowed length: https://validation.iso-payments.ch/html/en/CustomerBank/pain.001/0221.htm
			initgPty.setCtctDtls(ctctDtls);

			groupHeaderSCT.setInitgPty(initgPty);
		}

		// Payment Informations: create one PaymentInstructionInformationSDD for each line
		final List<I_SEPA_Export_Line> sepaDocumentLines = Services.get(ISEPADocumentDAO.class).retrieveLines(sepaDocument);
		if (sepaDocumentLines.isEmpty())
		{
			throw new AdempiereException("The given SEPA_Export record has no lines with active=Y and error=N")
					.appendParametersToMessage()
					.setParameter("SEPA_Export", sepaDocument);
		}

		final Map<ArrayKey, PaymentInstructionInformation3CH> currency2pmtInf = new HashMap<>();

		int nbOfTxs = 0;

		for (final I_SEPA_Export_Line sepaLine : sepaDocumentLines)
		{
			final PaymentInstructionInformation3CH pmtInf = currency2pmtInf
					.computeIfAbsent(
							createKey(sepaLine),
							c -> createAndAddPmtInf(creditTransferInitiation, sepaDocument, sepaLine));

			// Credit Transfer Transaction Information
			final CreditTransferTransactionInformation10CH cdtTrfTxInf = createCreditTransferTransactionInformation(pmtInf, sepaLine);
			pmtInf.getCdtTrfTxInf().add(cdtTrfTxInf);

			final BigDecimal transactionAmount = cdtTrfTxInf.getAmt().getInstdAmt().getValue();
			pmtInf.setCtrlSum(pmtInf.getCtrlSum().add(transactionAmount));

			nbOfTxs++;
		}

		for (final PaymentInstructionInformation3CH pmtInf : currency2pmtInf.values())
		{
			// Update GroupHeader's control amount
			final BigDecimal groupHeaderCtrlAmt = creditTransferInitiation.getGrpHdr().getCtrlSum();
			final BigDecimal groupHeaderCtrlAmtNew = groupHeaderCtrlAmt.add(pmtInf.getCtrlSum());
			creditTransferInitiation.getGrpHdr().setCtrlSum(groupHeaderCtrlAmtNew);
		}

		// Number of transactions: The total number of direct debit transaction blocks in the message.
		creditTransferInitiation.getGrpHdr().setNbOfTxs(String.valueOf(nbOfTxs));

		sepaDocument.setProcessed(creditTransferInitiation.getPmtInf().size() > 0);
		InterfaceWrapperHelper.save(sepaDocument);
		return document;

	}

	private ArrayKey createKey(@NonNull final I_SEPA_Export_Line sepaLine)
	{
		if (extractBatchFlag(sepaLine))
		{
			return ArrayKey.of(
					extractPaymentType(sepaLine),
					extractCurrencyCode(sepaLine));
		}
		else
		{
			return ArrayKey.of(sepaLine.getSEPA_Export_Line_ID());
		}
	}

	private boolean extractBatchFlag(@NonNull final I_SEPA_Export_Line sepaLine)
	{
		return sepaLine.getSEPA_Export().isExportBatchBookings();
	}

	private CurrencyCode extractCurrencyCode(final I_SEPA_Export_Line sepaLine)
	{
		final CurrencyId currencyId = CurrencyId.ofRepoId(sepaLine.getC_Currency_ID());
		return Services.get(ICurrencyDAO.class).getCurrencyCodeById(currencyId);
	}

	private PaymentInstructionInformation3CH createAndAddPmtInf(
			@NonNull final CustomerCreditTransferInitiationV03CH cstmrCdtTrfInitn,
			@NonNull final I_SEPA_Export sepaHdr,
			@NonNull final I_SEPA_Export_Line sepaLine)
	{
		final PaymentInstructionInformation3CH pmtInf = objectFactory.createPaymentInstructionInformation3CH();
		cstmrCdtTrfInitn.getPmtInf().add(pmtInf);

		// PaymentInformationIdentification: A system-generated internal code.
		{
			pmtInfCounter++;
			final String pmtInfId = StringUtils.formatMessage("PMTINF-{}", pmtInfCounter);
			pmtInf.setPmtInfId(pmtInfId);
		}

		// Sets payment code - credit transfer.
		pmtInf.setPmtMtd(PaymentMethod3Code.TRF);

		pmtInf.setBtchBookg(extractBatchFlag(sepaLine));

		// Setting the control amount later.
		pmtInf.setCtrlSum(ZERO);

		// zahlungsart
		final String paymentMode = extractPaymentType(sepaLine);

		//
		// Payment type information.
		{
			if (paymentMode == PAYMENT_TYPE_5 || paymentMode == PAYMENT_TYPE_1
					|| paymentMode == PAYMENT_TYPE_2_1	|| paymentMode == PAYMENT_TYPE_2_2)
			{
				final PaymentTypeInformation19CH pmtTpInf = objectFactory.createPaymentTypeInformation19CH();
				pmtInf.setPmtTpInf(pmtTpInf);

				// service level
				if (paymentMode == PAYMENT_TYPE_5)
				{
					// ServiceLEvel.Code "SEPA" does not work if we are doing transactions in swizz.
					// Service level - Hard-coded value of SEPA.
					final ServiceLevel8Choice svcLvl = objectFactory.createServiceLevel8Choice();
					svcLvl.setCd("SEPA");
					pmtTpInf.setSvcLvl(svcLvl);
				}
				else if (paymentMode == PAYMENT_TYPE_1)
				{
					// local instrument
					final LocalInstrument2Choice lclInstrm = objectFactory.createLocalInstrument2Choice();
					lclInstrm.setPrtry("CH01"); // Zahlungsart 1
					pmtTpInf.setLclInstrm(lclInstrm);
				}
				else if (paymentMode == PAYMENT_TYPE_2_1)
				{
					// local instrument
					final LocalInstrument2Choice lclInstrm = objectFactory.createLocalInstrument2Choice();
					lclInstrm.setPrtry("CH02"); // Zahlungsart 2.1
					pmtTpInf.setLclInstrm(lclInstrm);
				}
				else if (paymentMode == PAYMENT_TYPE_2_2)
				{
					// local instrument
					final LocalInstrument2Choice lclInstrm = objectFactory.createLocalInstrument2Choice();
					lclInstrm.setPrtry("CH03"); // Zahlungsart 2.2
					pmtTpInf.setLclInstrm(lclInstrm);
				}
			}
		}

		final Date dueDate = Services.get(ISEPADocumentBL.class).getDueDate(sepaLine);
		pmtInf.setReqdExctnDt(newXMLGregorianCalendar(dueDate));

		//
		// debitor
		pmtInf.setDbtr(copyPartyIdentificationSEPA2(
				cstmrCdtTrfInitn.getGrpHdr().getInitgPty()));

		//
		// debitor Account
		final String iban = sepaHdr.getIBAN();
		final CashAccount16CHIdTpCcy dbtrAcct = objectFactory.createCashAccount16CHIdTpCcy();
		pmtInf.setDbtrAcct(dbtrAcct);
		final AccountIdentification4ChoiceCH id = objectFactory.createAccountIdentification4ChoiceCH();
		dbtrAcct.setId(id);
		if (Check.isEmpty(iban, true))
		{
			final GenericAccountIdentification1CH othr = objectFactory.createGenericAccountIdentification1CH();
			id.setOthr(othr);
			othr.setId(sepaLine.getOtherAccountIdentification());
		}
		else
		{
			id.setIBAN(iban.replaceAll(" ", ""));
		}
		//
		// Debitor Agent (i.e. Bank)
		{
			final BranchAndFinancialInstitutionIdentification4CHBicOrClrId dbtrAgt = objectFactory.createBranchAndFinancialInstitutionIdentification4CHBicOrClrId();
			pmtInf.setDbtrAgt(dbtrAgt);

			final FinancialInstitutionIdentification7CHBicOrClrId finInstnId = objectFactory.createFinancialInstitutionIdentification7CHBicOrClrId();
			finInstnId.setBIC(sepaHdr.getSwiftCode());
			dbtrAgt.setFinInstnId(finInstnId);
		}

		// Charge Bearer Type
		// Hard-coded value of SLEV.
		pmtInf.setChrgBr(ChargeBearerType1Code.SLEV);
		return pmtInf;
	}

	private CreditTransferTransactionInformation10CH createCreditTransferTransactionInformation(
			@NonNull final PaymentInstructionInformation3CH pmtInf,
			@NonNull final I_SEPA_Export_Line line)
	{
		final CreditTransferTransactionInformation10CH cdtTrfTxInf = objectFactory.createCreditTransferTransactionInformation10CH();

		//
		// Amount/Currency
		{
			final AmountType3Choice amt = objectFactory.createAmountType3Choice();
			final ActiveOrHistoricCurrencyAndAmount instdAmt = objectFactory.createActiveOrHistoricCurrencyAndAmount();

			final CurrencyId currencyId = CurrencyId.ofRepoId(line.getC_Currency_ID());
			final Currency currency = Services.get(ICurrencyDAO.class).getById(currencyId);

			final CurrencyCode currencyIsoCode = currency.getCurrencyCode();
			instdAmt.setCcy(currencyIsoCode.toThreeLetterCode());

			final BigDecimal amount = NumberUtils.stripTrailingDecimalZeros(line.getAmt());
			Check.errorIf(amount == null || amount.signum() <= 0, "Invalid amount={} of SEPA_Export_Line={}", amount, line);
			Check.errorIf(amount.scale() > currency.getPrecision().toInt(),
					"Invalid number of decimal points; amount={} has {} decimal points, but the currency {} only allows {}; SEPA_Export_Line={}",
					amount, currencyIsoCode, currency.getPrecision(), line);
			instdAmt.setValue(amount);

			amt.setInstdAmt(instdAmt);

			cdtTrfTxInf.setAmt(amt);
		}

		final String paymentType = extractPaymentType(line);

		//
		// Creditor Agent (i.e. Bank)
		// not allowed for 1, 2.1 and 8, must for the rest
		final I_C_BP_BankAccount bankAccount = line.getC_BP_BankAccount();
		final BankId bankId = BankId.ofRepoIdOrNull(bankAccount.getC_Bank_ID());
		final Bank bankOrNull = bankId == null ? null : bankRepo.getById(bankId);

		if (!Objects.equals(paymentType, PAYMENT_TYPE_1)
				&& !Objects.equals(paymentType, PAYMENT_TYPE_2_1)
				&& !Objects.equals(paymentType, PAYMENT_TYPE_8))
		{

			final BranchAndFinancialInstitutionIdentification4CH cdtrAgt = objectFactory.createBranchAndFinancialInstitutionIdentification4CH();
			cdtTrfTxInf.setCdtrAgt(cdtrAgt);

			final FinancialInstitutionIdentification7CH finInstnId = objectFactory.createFinancialInstitutionIdentification7CH();
			cdtrAgt.setFinInstnId(finInstnId);

			final String bcFromIBAN = extractBCFromIban(line.getIBAN(), line);
			if (!Check.isEmpty(bcFromIBAN, true))
			{
				// this is our best bet. Even data in adempiere might be wrong/outdated
				final ClearingSystemMemberIdentification2 clrSysMmbId = objectFactory.createClearingSystemMemberIdentification2();
				finInstnId.setClrSysMmbId(clrSysMmbId);

				clrSysMmbId.setMmbId(bcFromIBAN);

				// we set the BC, but we need to also indicate the sort of MmbId value
				final ClearingSystemIdentification2Choice clrSysId = objectFactory.createClearingSystemIdentification2Choice();
				clrSysMmbId.setClrSysId(clrSysId);

				// has to be CHBCC for payment modes 2.2, 3, 4; note if we do paymentMode 5 ("real" SEPA), weren't in this if-block to start with
				clrSysId.setCd("CHBCC");
			}
			else if (!Check.isEmpty(line.getSwiftCode(), true))
			{
				finInstnId.setBIC(line.getSwiftCode());
			}
			else
			{
				// // let the bank see what it can do
				finInstnId.setBIC(NOTPROVIDED_VALUE);
			}

			//
			// Name
			if (Objects.equals(paymentType, PAYMENT_TYPE_2_2)
					|| Objects.equals(paymentType, PAYMENT_TYPE_4)
					|| Objects.equals(paymentType, PAYMENT_TYPE_6))
			{
				final boolean hasNoBIC = Check.isEmpty(finInstnId.getBIC(), true) || NOTPROVIDED_VALUE.equals(finInstnId.getBIC());
				if (hasNoBIC)
				{
					final String bankName = getBankNameIfAny(line);
					Check.errorIf(Check.isEmpty(bankName, true), SepaMarshallerException.class,
							"Zahlart={}, but line {} has no information about the bank name",
							paymentType, createInfo(line));

					finInstnId.setNm(bankName);
					finInstnId.setBIC(null); // if we use Nm, then there should be no BIC element
				}
			}

			if (Objects.equals(paymentType, PAYMENT_TYPE_4)
					|| Objects.equals(paymentType, PAYMENT_TYPE_6))
			{
				// see if we can also export the bank's address
				if (line.getC_BP_BankAccount_ID() > 0
						&& bankAccount.getC_Bank_ID() > 0
						&& bankOrNull != null && bankOrNull.getLocationId() != null)
				{
					final I_C_Location bankLocation = locationDAO.getById(bankOrNull.getLocationId());
					final PostalAddress6CH pstlAdr = createStructuredPstlAdr(bankLocation);

					finInstnId.setPstlAdr(pstlAdr);
				}
			}

			//
			// Other
			if (Objects.equals(paymentType, PAYMENT_TYPE_2_2))
			{
				final GenericFinancialIdentification1CH othr = objectFactory.createGenericFinancialIdentification1CH();
				finInstnId.setOthr(othr);

				othr.setId(line.getOtherAccountIdentification());
			}
		}

		//
		// Creditor BPartner
		{
			final PartyIdentification32CHName cdtr = objectFactory.createPartyIdentification32CHName();
			cdtTrfTxInf.setCdtr(cdtr);

			// Note: since old age we use SEPA_MandateRefNo for the creditor's name (I don't remember why)
			// task 09617: prefer C_BP_BankAccount.A_Name if available; keep SEPA_MandateRefNo, because setting it as "cdtr/name" might be a best practice
			cdtr.setNm(getFirstNonEmpty(
					line::getSEPA_MandateRefNo,
					() -> line.getC_BP_BankAccount().getA_Name(),
					() -> getBPartnerNameById(line.getC_BPartner_ID())));

			// task 08655: also provide the creditor's address
			final Properties ctx = InterfaceWrapperHelper.getCtx(line);
			final I_C_BPartner_Location billToLocation = Services.get(IBPartnerDAO.class).retrieveBillToLocation(ctx, line.getC_BPartner_ID(), true, ITrx.TRXNAME_None);
			if (billToLocation != null)
			{
				final PostalAddress6CH pstlAdr;
				if (Objects.equals(paymentType, PAYMENT_TYPE_5) || Objects.equals(paymentType, PAYMENT_TYPE_6))
				{
					pstlAdr = createUnstructuredPstlAdr(bankAccount, billToLocation.getC_Location());
				}
				else
				{
					pstlAdr = createStructuredPstlAdr(bankAccount, billToLocation.getC_Location());
				}
				cdtr.setPstlAdr(pstlAdr);
			}
		}

		//
		// creditor BPartner Account
		{
			final CashAccount16CHId cdtrAcct = objectFactory.createCashAccount16CHId();
			cdtTrfTxInf.setCdtrAcct(cdtrAcct);

			final AccountIdentification4ChoiceCH id = objectFactory.createAccountIdentification4ChoiceCH();
			cdtrAcct.setId(id);

			final String iban = line.getIBAN();
			if (!Check.isEmpty(iban, true) && !Objects.equals(paymentType, PAYMENT_TYPE_1))
			{
				// prefer IBAN, unless we have paypent type 1 (because then we use the ISR participant number)
				id.setIBAN(iban.replaceAll(" ", "")); // this is ofc the more frequent case (..on a global scale)
			}
			else
			{
				final String otherAccountIdentification = line.getOtherAccountIdentification();
				final String accountNo = bankAccount.getAccountNo();

				final GenericAccountIdentification1CH othr = objectFactory.createGenericAccountIdentification1CH();
				id.setOthr(othr);

				if (!Check.isEmpty(otherAccountIdentification, true))
				{
					// task 07789
					othr.setId(otherAccountIdentification); // for task 07789, this needs to contain the ESR TeilehmerNr or PostkontoNr
				}
				else
				{
					othr.setId(bankAccount.getAccountNo());
				}
			}
		}

		// note: we use the structuredRemittanceInfo in ustrd, if we do SEPA (zahlart 5),
		// because it's much less complicated
		final String reference = StringUtils.trimBlankToOptional(line.getStructuredRemittanceInfo()).orElseGet(line::getDescription);

		// Remittance Info
		{
			final RemittanceInformation5CH rmtInf = objectFactory.createRemittanceInformation5CH();
			if (Check.isEmpty(line.getStructuredRemittanceInfo(), true)
					|| paymentType == PAYMENT_TYPE_3
					|| paymentType == PAYMENT_TYPE_5)
			{
				Check.errorIf(Objects.equals(paymentType, PAYMENT_TYPE_1), SepaMarshallerException.class,
							  "SEPA_ExportLine {} has to have StructuredRemittanceInfo", createInfo(line));

				if (!Check.isBlank(bankAccount.getQR_IBAN()))
				{

					if(isInvalidQRReference(reference))
					{
						throw new AdempiereException(ERR_SEPA_Export_InvalidReference,createInfo(line));
					}

					final StructuredRemittanceInformation7 strd = objectFactory.createStructuredRemittanceInformation7();
					rmtInf.setStrd(strd);
					final CreditorReferenceInformation2 cdtrRefInf = objectFactory.createCreditorReferenceInformation2();
					strd.setCdtrRefInf(cdtrRefInf);
					final CreditorReferenceType2 tp = objectFactory.createCreditorReferenceType2();
					cdtrRefInf.setTp(tp);
					final CreditorReferenceType1Choice cdOrPrtry = objectFactory.createCreditorReferenceType1Choice();
					tp.setCdOrPrtry(cdOrPrtry);
					cdOrPrtry.setPrtry("QRR");

					cdtrRefInf.setRef(reference);
				}
				else
				{
					// provide the line-description (if set) as unstructured remittance info
					if (Check.isBlank(reference))
					{
						// at least add a "." to make sure the node exists.
						rmtInf.setUstrd(".");
					}
					else
					{
						final String validReference = StringUtils.trunc(replaceForbiddenChars(reference), 140, TruncateAt.STRING_START);
						rmtInf.setUstrd(validReference);
					}
				}
			}
			else
			{
				// task 07789
				final StructuredRemittanceInformation7 strd = objectFactory.createStructuredRemittanceInformation7();
				rmtInf.setStrd(strd);
				final CreditorReferenceInformation2 cdtrRefInf = objectFactory.createCreditorReferenceInformation2();
				strd.setCdtrRefInf(cdtrRefInf);
				cdtrRefInf.setRef(line.getStructuredRemittanceInfo());
			}
			cdtTrfTxInf.setRmtInf(rmtInf);
		}

		//
		// Payment ID
		// EndToEndId: A unique key:
		// Depending on process parameter generated by the system for each payment.
		//
		{
			final String endToEndId;
			if (exportContext.isReferenceAsEndToEndId())
			{
				endToEndId = CoalesceUtil.coalesce(StringUtils.trunc(replaceForbiddenChars(reference), 65, TruncateAt.STRING_START), NOTPROVIDED_VALUE);
			}
			else
			{
				endToEndIdCounter++;
				endToEndId = StringUtils.formatMessage("ENDTOENDID-{}", endToEndIdCounter);
			}
			final PaymentIdentification1 pmtId = objectFactory.createPaymentIdentification1();
			pmtId.setEndToEndId(endToEndId);

			final String instrId = StringUtils.formatMessage("INSTRID-{}-{}", pmtInfCounter, pmtInf.getCdtTrfTxInf().size() + 1);
			pmtId.setInstrId(instrId);

			cdtTrfTxInf.setPmtId(pmtId);
		}
		return cdtTrfTxInf;
	}

	@SafeVarargs
	@Nullable
	private final String getFirstNonEmpty(@NonNull final Supplier<String>... values)
	{
		final String result = CoalesceUtil.firstValidValue(
				s -> !Check.isEmpty(s, true),
				values);

		if (result != null)
		{
			return result.trim();
		}
		return result;
	}

	private PostalAddress6CH createStructuredPstlAdr(
			@Nullable final org.compiere.model.I_C_BP_BankAccount bpBankAccount,
			@NonNull final I_C_Location location)
	{
		final PostalAddress6CH pstlAdr = createStructuredPstlAdr(location);
		if (bpBankAccount == null)
		{
			return pstlAdr;
		}

		splitStreetAndNumber(bpBankAccount.getA_Street(), pstlAdr);

		pstlAdr.setPstCd(getFirstNonEmpty(
				bpBankAccount::getA_Zip,
				pstlAdr::getPstCd));

		pstlAdr.setTwnNm(getFirstNonEmpty(
				bpBankAccount::getA_City,
				pstlAdr::getTwnNm));

		// Note: don't use the bankAccount's A_Country, because we need an ISO-3166 in this field

		return pstlAdr;
	}

	/**
	 * @task 08655
	 * @TODO: extract the core part into a general address-BL (also move the unit tests along); also handle the case of number-first (like e.g. in france)
	 */
	@VisibleForTesting
	PostalAddress6CH createStructuredPstlAdr(@NonNull final I_C_Location location)
	{
		final PostalAddress6CH pstlAdr = objectFactory.createPostalAddress6CH();

		splitStreetAndNumber(location.getAddress1(), pstlAdr);

		pstlAdr.setCtry(location.getC_Country().getCountryCode()); // note: C_Location.C_Country is a mandatory column
		pstlAdr.setPstCd(location.getPostal());
		pstlAdr.setTwnNm(location.getCity());

		return pstlAdr;
	}

	private void splitStreetAndNumber(
			@Nullable final String streetAndNumber,
			@NonNull final PostalAddress6CH pstlAdr)
	{
		final IPair<String, String> splitStreetAndHouseNumber = StringUtils.splitStreetAndHouseNumberOrNull(streetAndNumber);
		if (splitStreetAndHouseNumber == null)
		{
			return;
		}

		final String street = splitStreetAndHouseNumber.getLeft();
		final String number = splitStreetAndHouseNumber.getRight();
		pstlAdr.setStrtNm(street);
		pstlAdr.setBldgNb(number);
	}

	private PostalAddress6CH createUnstructuredPstlAdr(
			@Nullable final org.compiere.model.I_C_BP_BankAccount bpBankAccount,
			@NonNull final I_C_Location location)
	{
		final PostalAddress6CH pstlAdr = objectFactory.createPostalAddress6CH();

		pstlAdr.setCtry(location.getC_Country().getCountryCode()); // note: C_Location.C_Country is a mandatory column

		final boolean addressInBankAccountIsComplete = bpBankAccount != null
				&& !Check.isEmpty(bpBankAccount.getA_City(), true)
				&& !Check.isEmpty(bpBankAccount.getA_Zip(), true)
				&& !Check.isEmpty(bpBankAccount.getA_Street(), true);
		if (addressInBankAccountIsComplete)
		{
			pstlAdr.getAdrLine().add(bpBankAccount.getA_Street());
			pstlAdr.getAdrLine().add(bpBankAccount.getA_Zip() + " " + bpBankAccount.getA_City());
			return pstlAdr;
		}

		// fall back to the billing location
		final String firstAdrLineFromLocation = location.getAddress1();
		final String secondAddressLineFromLocation = location.getPostal() + " " + location.getCity();
		pstlAdr.getAdrLine().add(firstAdrLineFromLocation);
		pstlAdr.getAdrLine().add(secondAddressLineFromLocation);
		return pstlAdr;
	}

	/**
	 * for IBANs that start with "CH" or "LI", this method extracts the swizz Banking code and returns it. If the given <code>iban</code> is <code>null</code>, emtpy of not "CH"/"LI", it returns
	 * <code>null</code>.
	 *
	 * @see <a href="http://www.swissiban.com/de.htm">http://www.swissiban.com/de.htm</a> for what it does (it's simple).
	 */
	@Nullable
	private String extractBCFromIban(
			@Nullable final String iban,
			@NonNull final I_SEPA_Export_Line line)
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
	 */
	private boolean isSwizzIBAN(@Nullable final String iban)
	{
		if (Check.isEmpty(iban, true))
		{
			return false;
		}
		final String ibanToUse = iban.replaceAll(" ", "");
		return ibanToUse.startsWith("CH") || ibanToUse.startsWith("LI");
	}

	protected String getBankNameIfAny(final I_SEPA_Export_Line line)
	{
		final I_C_BP_BankAccount bpartnerBankAccount = line.getC_BP_BankAccount();
		final BankId bankId = BankId.ofRepoIdOrNull(bpartnerBankAccount.getC_Bank_ID());
		final Bank bank = bankId != null ? bankRepo.getById(bankId) : null;
		return bank == null ? "" : bank.getBankName();
	}

	private PartyIdentification32CH copyPartyIdentificationSEPA2(final PartyIdentification32CHNameAndId initgPty)
	{
		final PartyIdentification32CH partyCopy = objectFactory.createPartyIdentification32CH();

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

	public String getPainIdentifier()
	{
		return PAIN_001_001_03_CH_02;
	}

	public boolean isSupportsOtherCurrencies()
	{
		return true;
	}

	public boolean isSupportsGenericAccountIdentification()
	{
		return true;
	}

	@Override
	public String toString()
	{
		return String.format(
				"SEPACustomerCTIMarshaler_Pain_001_001_03_CH_02 [getPainIdentifier()=%s, isSupportsOtherCurrencies()=%s, isSupportsGenericAccountIdentification()=%s]",
				getPainIdentifier(),
				isSupportsOtherCurrencies(),
				isSupportsGenericAccountIdentification());
	}

	private String extractPaymentType(@NonNull final I_SEPA_Export_Line line)
	{
		final de.metas.payment.esr.model.I_C_BP_BankAccount bPBankAccount = InterfaceWrapperHelper.create(
				line.getC_BP_BankAccount(),
				de.metas.payment.esr.model.I_C_BP_BankAccount.class);

		final String paymentMode;
		if (bPBankAccount.isEsrAccount() && !Check.isEmpty(line.getStructuredRemittanceInfo(), true) && bPBankAccount.getQR_IBAN() == null)
		{
			paymentMode = PAYMENT_TYPE_1;
		}
		else
		{
			final CurrencyCode currencyCode = extractCurrencyCode(line);
			final String iban = line.getIBAN();

			final boolean swizzIban = isSwizzIBAN(iban);
			if (swizzIban || bPBankAccount.isEsrAccount())
			{
				// "domestic" IBAN. it contains the bank code (BC) and we will use it.
				Check.errorIf(!currencyCode.isEuro() && !currencyCode.isCHF(),
						SepaMarshallerException.class,
						"line {} has a swizz IBAN, but the currency is {} instead of 'CHF' or 'EUR'",
						createInfo(line), currencyCode);

				paymentMode = PAYMENT_TYPE_3; // we can go with zahlart 2.2
			}
			else
			{
				final boolean hasIbanAndEurCurrency = !Check.isEmpty(iban, true) && currencyCode.isEuro();
				if (hasIbanAndEurCurrency)
				{
					paymentMode = PAYMENT_TYPE_5;
				}
				else
				{
					paymentMode = PAYMENT_TYPE_6;
				}
			}
		}

		return paymentMode;
	}

	private String createInfo(@NonNull final I_SEPA_Export_Line line)
	{
		final StringBuilder sb = new StringBuilder();
		if (line.getC_BPartner_ID() > 0)
		{
			sb.append("@C_BPartner_ID@ ");
			sb.append(getBPartnerNameById(line.getC_BPartner_ID()));
		}
		if (line.getC_BP_BankAccount_ID() > 0)
		{
			sb.append(" @C_BP_BankAccount_ID@ ");
			sb.append(line.getC_BP_BankAccount().getDescription());
		}
		return Services.get(IMsgBL.class).parseTranslation(InterfaceWrapperHelper.getCtx(line), sb.toString());
	}

	@VisibleForTesting
	@Nullable
	static String replaceForbiddenChars(@Nullable final String input)
	{
		if (input == null)
		{
			return null;
		}
		return input.replaceAll(FORBIDDEN_CHARS, "_");
	}

	private String getBPartnerNameById(final int bpartnerRepoId)
	{
		final IBPartnerBL bpartnerService = Services.get(IBPartnerBL.class);
		return bpartnerService.getBPartnerName(BPartnerId.ofRepoIdOrNull(bpartnerRepoId));
	}

	@VisibleForTesting
	static boolean isInvalidQRReference(@NonNull final String reference)
	{
		if(reference.length() != 27)
		{
			return true;
		}

		final int[] checkSequence = {0,9,4,6,8,2,7,1,3,5};
		int carryOver = 0;

		for (int i = 1; i <= reference.length() - 1; i++)
		{
			final int idx = ((carryOver + Integer.parseInt(reference.substring(i - 1, i))) % 10);
			carryOver = checkSequence[idx];
		}

		return !(Integer.parseInt(reference.substring(26)) == (10 - carryOver) % 10);
	}
}
