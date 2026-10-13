package de.metas.payment.sepa.sepamarshaller.impl;

import com.google.common.annotations.VisibleForTesting;
import de.metas.banking.BankAccount;
import de.metas.banking.BankAccountId;
import de.metas.banking.api.BankAccountService;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.pair.IPair;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.Currency;
import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyDAO;
import de.metas.i18n.IMsgBL;
import de.metas.location.CountryId;
import de.metas.location.ICountryDAO;
import de.metas.location.ILocationDAO;
import de.metas.location.LocationId;
import de.metas.money.CurrencyId;
import de.metas.payment.sepa.api.ISEPADocumentBL;
import de.metas.payment.sepa.api.ISEPADocumentDAO;
import de.metas.payment.sepa.api.SEPAExportContext;
import de.metas.payment.sepa.api.SepaMarshallerException;
import de.metas.payment.sepa.api.SepaUtils;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_09_ch_03.AccountIdentification4Choice;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_09_ch_03.ActiveOrHistoricCurrencyAndAmount;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_09_ch_03.AmountType4Choice;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_09_ch_03.BranchAndFinancialInstitutionIdentification6;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_09_ch_03.CashAccount38;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_09_ch_03.ChargeBearerType1Code;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_09_ch_03.ClearingSystemIdentification2Choice;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_09_ch_03.ClearingSystemMemberIdentification2;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_09_ch_03.Contact4;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_09_ch_03.CreditTransferTransaction34;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_09_ch_03.CustomerCreditTransferInitiationV09;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_09_ch_03.DateAndDateTime2Choice;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_09_ch_03.DocumentPain001Ch;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_09_ch_03.FinancialInstitutionIdentification18;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_09_ch_03.GenericAccountIdentification1;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_09_ch_03.GroupHeader85;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_09_ch_03.ObjectFactory;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_09_ch_03.OtherContact1;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_09_ch_03.PartyIdentification135;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_09_ch_03.PaymentIdentification6;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_09_ch_03.PaymentInstruction30;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_09_ch_03.PaymentMethod3Code;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_09_ch_03.PaymentTypeInformation26;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_09_ch_03.PostalAddress24;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_09_ch_03.RemittanceInformation16;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_09_ch_03.ServiceLevel8Choice;
import de.metas.payment.sepa.model.I_SEPA_Export;
import de.metas.payment.sepa.model.I_SEPA_Export_Line;
import de.metas.util.Check;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import de.metas.util.StringUtils.TruncateAt;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.Adempiere;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.compiere.util.Util.ArrayKey;

import javax.annotation.Nullable;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlSchema;
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
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Supplier;
import java.util.regex.Pattern;

import static java.math.BigDecimal.ZERO;

/*
 * #%L
 * de.metas.payment.sepa.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

/**
 * Credit transfer export in the Swiss "pain.001.001.09.ch.03" format.
 * <p>
 * Written according to the "Swiss Implementation Guidelines for Customer-Bank Messages for Credit Transfer", SPS 2026 version 2.3 ("IG" in the comments below; chapter numbers refer to it).
 * Ported from {@link SEPAVendorCreditTransferMarshaler_Pain_001_001_03_CH_02}; every deviation from it is commented with the IG chapter.
 * <p>
 * Supported payment types (IG 3.15):
 * <ul>
 * <li>"D" (domestic) with an IBAN of the creditor (internally {@link #PAYMENT_TYPE_3})</li>
 * <li>"S" (SEPA) with an IBAN of the creditor (internally {@link #PAYMENT_TYPE_5})</li>
 * </ul>
 * QR-IBAN / QR reference payments, foreign ("X") payments and anything else are rejected with a {@link SepaMarshallerException} for now.
 * <p>
 * Note on the JAXB types: the generated {@code *Pain001Ch} classes are the XSD's CH restrictions of the ISO base types. The XSD already binds each element to its CH restriction,
 * so we instantiate the ISO base types; using the {@code *Pain001Ch} subclasses would make JAXB emit superfluous {@code xsi:type} attributes. Only the root is a {@link DocumentPain001Ch}.
 */
public class SEPAVendorCreditTransferMarshaler_Pain_001_001_09_CH_03 implements SEPAMarshaler
{
	private static final String PAIN_001_001_09_CH_03 = "pain.001.001.09.ch.03";

	/**
	 * The XSD's {@code targetNamespace}, taken from the JAXB classes generated from that very XSD.
	 */
	private static final String PAIN_001_001_09_CH_03_NAMESPACE = DocumentPain001Ch.class.getPackage().getAnnotation(XmlSchema.class).namespace();
	private static final String PAIN_001_001_09_CH_03_SCHEMALOCATION = PAIN_001_001_09_CH_03_NAMESPACE + " " + PAIN_001_001_09_CH_03 + ".xsd";

	/**
	 * Title: "ISR". Not supported by this format.
	 */
	private static final String PAYMENT_TYPE_1 = "PAYMENT_TYPE_1";

	/**
	 * Title: "IBAN/postal account and IID/BIC" (IG 3.15: payment type "D")
	 */
	private static final String PAYMENT_TYPE_3 = "PAYMENT_TYPE_3";

	/**
	 * Title: "Foreign SEPA" (IG 3.15: payment type "S")
	 */
	private static final String PAYMENT_TYPE_5 = "PAYMENT_TYPE_5";

	/**
	 * Title: "Foreign" (IG 3.15: payment type "X"). Not supported yet.
	 */
	private static final String PAYMENT_TYPE_6 = "PAYMENT_TYPE_6";

	/**
	 * Clearing system code of the Swiss/Liechtenstein IID (IG 4.2 / 4.3: "Only CHBCC is permitted in Switzerland").
	 */
	private static final String CLEARING_SYSTEM_CHBCC = "CHBCC";

	/**
	 * Fallback EndToEndId if the reference is used as EndToEndId but there is none.
	 */
	private static final String END_TO_END_ID_NOTPROVIDED = "NOTPROVIDED";

	/**
	 * IG 3.2: restricted character set for MsgId, PmtInfId, InstrId and EndToEndId (also enforced by the XSD type Max35Text_pain001_ch).
	 */
	private static final Pattern REFERENCE_FORBIDDEN_CHARS = Pattern.compile("[^A-Za-z0-9 '()+,\\-./:?]");
	private static final int REFERENCE_MAX_LENGTH = 35;

	/**
	 * IG 3.9: version of the implemented Swiss Implementation Guidelines, in the format nnnn (SPS 2026 version 2.3).
	 */
	private static final String SPS_IG_VERSION = "0230";

	/**
	 * Maximum lengths of the XSD types (Max140Text, Max70Text, Max16Text, Max35Text) resp. of the IG.
	 */
	private static final int MAX_LENGTH_NAME = 140;
	private static final int MAX_LENGTH_NAME_SEPA = 70; // IG 3.11 / 4.3 Cdtr/Nm: "S: max. 70 characters"
	private static final int MAX_LENGTH_STREET_NAME = 70;
	private static final int MAX_LENGTH_BUILDING_NUMBER = 16;
	private static final int MAX_LENGTH_POST_CODE = 16;
	private static final int MAX_LENGTH_TOWN_NAME = 35;

	private final ObjectFactory objectFactory;
	private final DatatypeFactory datatypeFactory;
	private final SEPAExportContext exportContext;
	private final BankAccountService bankAccountService;
	private final ILocationDAO locationDAO = Services.get(ILocationDAO.class);
	private final IBPartnerDAO partnerDAO = Services.get(IBPartnerDAO.class);
	private final ICountryDAO countryDAO = Services.get(ICountryDAO.class);

	private static final String encoding = "UTF-8";

	private int endToEndIdCounter = 0;
	private int pmtInfCounter = 0;
	private final Map<PaymentInstruction30, Integer> pmtInfNumbers = new IdentityHashMap<>();

	public SEPAVendorCreditTransferMarshaler_Pain_001_001_09_CH_03(
			@NonNull final SEPAExportContext exportContext,
			@NonNull final BankAccountService bankAccountService)
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

		this.exportContext = exportContext;
		this.bankAccountService = bankAccountService;
	}

	@Override
	public void marshal(
			@NonNull final I_SEPA_Export sepaDocument,
			@NonNull final OutputStream out)
	{
		try
		{
			final DocumentPain001Ch xmlDocument = createDocument(sepaDocument);
			marshal(xmlDocument, out);
		}
		catch (final RuntimeException e)
		{
			throw AdempiereException.wrapIfNeeded(e)
					.appendParametersToMessage()
					.setParameter("sepaDocument", sepaDocument);
		}
	}

	private void marshal(
			@NonNull final DocumentPain001Ch xmlDocument,
			@NonNull final OutputStream out)
	{
		// We force UTF-8 encoding (IG 3.1)
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
			final JAXBElement<DocumentPain001Ch> jaxbDocument = objectFactory.createDocument(xmlDocument);

			final JAXBContext jaxbContext = JAXBContext.newInstance(DocumentPain001Ch.class.getPackage().getName());

			final Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			// the namespace is the ISO one (urn:iso:...:pain.001.001.09); the CH restrictions live in the SIX XSD file (IG 3.6, Annex A)
			marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, PAIN_001_001_09_CH_03_SCHEMALOCATION);
			marshaller.marshal(jaxbDocument, xmlWriter);
		}
		catch (final JAXBException e)
		{
			throw new AdempiereException("Marshalling error", e);
		}
	}

	@VisibleForTesting
	DocumentPain001Ch createDocument(@NonNull final I_SEPA_Export sepaDocument)
	{
		final DocumentPain001Ch document = new DocumentPain001Ch();

		final CustomerCreditTransferInitiationV09 creditTransferInitiation = objectFactory.createCustomerCreditTransferInitiationV09();
		document.setCstmrCdtTrfInitn(creditTransferInitiation);

		creditTransferInitiation.setGrpHdr(createGroupHeader(sepaDocument));

		// Payment Informations
		final List<I_SEPA_Export_Line> sepaDocumentLines = Services.get(ISEPADocumentDAO.class).retrieveLines(sepaDocument);
		if (sepaDocumentLines.isEmpty())
		{
			throw new AdempiereException("The given SEPA_Export record has no lines with active=Y and error=N")
					.appendParametersToMessage()
					.setParameter("SEPA_Export", sepaDocument);
		}

		final Map<ArrayKey, PaymentInstruction30> key2pmtInf = new LinkedHashMap<>();

		int nbOfTxs = 0;

		for (final I_SEPA_Export_Line sepaLine : sepaDocumentLines)
		{
			final PaymentInstruction30 pmtInf = key2pmtInf
					.computeIfAbsent(
							createKey(sepaLine),
							c -> createAndAddPmtInf(creditTransferInitiation, sepaDocument, sepaLine));

			// Credit Transfer Transaction Information
			final CreditTransferTransaction34 cdtTrfTxInf = createCreditTransferTransactionInformation(pmtInf, sepaLine);
			pmtInf.getCdtTrfTxInf().add(cdtTrfTxInf);

			final BigDecimal transactionAmount = cdtTrfTxInf.getAmt().getInstdAmt().getValue();
			pmtInf.setCtrlSum(pmtInf.getCtrlSum().add(transactionAmount));

			nbOfTxs++;
		}

		for (final PaymentInstruction30 pmtInf : key2pmtInf.values())
		{
			// Update GroupHeader's control amount
			final BigDecimal groupHeaderCtrlAmt = creditTransferInitiation.getGrpHdr().getCtrlSum();
			creditTransferInitiation.getGrpHdr().setCtrlSum(groupHeaderCtrlAmt.add(pmtInf.getCtrlSum()));
		}

		// Number of transactions: The total number of credit transfer transactions (C-level) in the message.
		creditTransferInitiation.getGrpHdr().setNbOfTxs(String.valueOf(nbOfTxs));

		sepaDocument.setProcessed(!creditTransferInitiation.getPmtInf().isEmpty());
		InterfaceWrapperHelper.save(sepaDocument);
		return document;
	}

	private GroupHeader85 createGroupHeader(@NonNull final I_SEPA_Export sepaDocument)
	{
		final GroupHeader85 groupHeader = objectFactory.createGroupHeader85();

		// MessageIdentification: unique identifier in our system; IG 3.2: restricted character set
		groupHeader.setMsgId(toReferenceText(sepaDocument.getDocumentNo()));

		// CreationDateTime: The system-generated date and time stamp at the time of formatting of the message.
		groupHeader.setCreDtTm(datatypeFactory.newXMLGregorianCalendar(SystemTime.asGregorianCalendar()));

		// NOTE: number of transactions and control sum are set later, after we know the lines
		groupHeader.setNbOfTxs(null);
		groupHeader.setCtrlSum(ZERO);

		final PartyIdentification135 initgPty = objectFactory.createPartyIdentification135();
		initgPty.setNm(truncate(SepaUtils.replaceForbiddenChars(sepaDocument.getSEPA_CreditorName()), MAX_LENGTH_NAME)); // IG 3.4: no empty Nm
		initgPty.setCtctDtls(createSoftwareInformation());
		groupHeader.setInitgPty(initgPty);

		return groupHeader;
	}

	/**
	 * IG 3.9 / 4.1: the software information goes into InitgPty/CtctDtls/Othr as ChanlTp/Id pairs (in .03 it was CtctDtls/Nm + CtctDtls/Othr; CtctDtls/Nm no longer exists in the CH schema).
	 */
	private Contact4 createSoftwareInformation()
	{
		final Contact4 ctctDtls = objectFactory.createContact4();
		ctctDtls.getOthr().add(createOtherContact("NAME", "metasfresh"));
		ctctDtls.getOthr().add(createOtherContact("PRVD", "metas GmbH"));

		// if we must truncate, then keep the end of the version string (STRING_START), like the .03 marshaller does
		final String versionString = Adempiere.getBuildVersion().getFullVersion();
		ctctDtls.getOthr().add(createOtherContact("VRSN", StringUtils.trunc(versionString.trim(), 128, TruncateAt.STRING_START))); // Id is Max128Text

		ctctDtls.getOthr().add(createOtherContact("SPSV", SPS_IG_VERSION));

		return ctctDtls;
	}

	private OtherContact1 createOtherContact(@NonNull final String channelType, @NonNull final String id)
	{
		final OtherContact1 othr = objectFactory.createOtherContact1();
		othr.setChanlTp(channelType);
		othr.setId(id);
		return othr;
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

	private PaymentInstruction30 createAndAddPmtInf(
			@NonNull final CustomerCreditTransferInitiationV09 cstmrCdtTrfInitn,
			@NonNull final I_SEPA_Export sepaHdr,
			@NonNull final I_SEPA_Export_Line sepaLine)
	{
		final PaymentInstruction30 pmtInf = objectFactory.createPaymentInstruction30();
		cstmrCdtTrfInitn.getPmtInf().add(pmtInf);

		// PaymentInformationIdentification: A system-generated internal code.
		pmtInfCounter++;
		pmtInfNumbers.put(pmtInf, pmtInfCounter);
		pmtInf.setPmtInfId(StringUtils.formatMessage("PMTINF-{}", pmtInfCounter));

		// Sets payment code - credit transfer.
		pmtInf.setPmtMtd(PaymentMethod3Code.TRF);

		pmtInf.setBtchBookg(extractBatchFlag(sepaLine));

		// Setting the control amount later.
		pmtInf.setCtrlSum(ZERO);

		// Payment type information.
		final String paymentType = extractPaymentType(sepaLine);
		if (PAYMENT_TYPE_5.equals(paymentType))
		{
			// IG 4.2 PmtTpInf/SvcLvl: "S: Must be used", "S: Only SEPA permitted". For "D", SEPA is not permitted, so there is no PmtTpInf at all.
			final ServiceLevel8Choice svcLvl = objectFactory.createServiceLevel8Choice();
			svcLvl.setCd("SEPA");

			final PaymentTypeInformation26 pmtTpInf = objectFactory.createPaymentTypeInformation26();
			pmtTpInf.getSvcLvl().add(svcLvl);
			pmtInf.setPmtTpInf(pmtTpInf);
		}

		// IG 4.2 ReqdExctnDt: now a choice of Dt / DtTm; we deliver the date
		final Date dueDate = Services.get(ISEPADocumentBL.class).getDueDate(sepaLine);
		final DateAndDateTime2Choice reqdExctnDt = objectFactory.createDateAndDateTime2Choice();
		reqdExctnDt.setDt(newXMLGregorianCalendar(dueDate));
		pmtInf.setReqdExctnDt(reqdExctnDt);

		// debitor
		final PartyIdentification135 dbtr = objectFactory.createPartyIdentification135();
		dbtr.setNm(cstmrCdtTrfInitn.getGrpHdr().getInitgPty().getNm());
		pmtInf.setDbtr(dbtr);

		// debitor Account
		final String iban = StringUtils.trimBlankToNull(sepaHdr.getIBAN());
		pmtInf.setDbtrAcct(createCashAccount(iban, sepaLine.getOtherAccountIdentification()));

		// Debitor Agent (i.e. Bank)
		// IG 4.2 DbtrAgt: BICFI (was BIC) or the IID as ClrSysMmbId; IG 3.4: no empty FinInstnId
		final String debtorBic = StringUtils.trimBlankToNull(sepaHdr.getSwiftCode());
		final BranchAndFinancialInstitutionIdentification6 dbtrAgt = createAgent(debtorBic, debtorBic == null ? extractIIDFromIban(iban) : null);
		if (dbtrAgt == null)
		{
			throw new SepaMarshallerException("The SEPA_Export has neither a BIC nor a CH/LI IBAN to identify the debtor's bank; SEPA_Export=" + sepaHdr);
		}
		pmtInf.setDbtrAgt(dbtrAgt);

		// Charge Bearer Type: Hard-coded value of SLEV (IG 4.2: allowed at B-level, "S: If used, then SLEV or SHAR")
		pmtInf.setChrgBr(ChargeBearerType1Code.SLEV);
		return pmtInf;
	}

	private CreditTransferTransaction34 createCreditTransferTransactionInformation(
			@NonNull final PaymentInstruction30 pmtInf,
			@NonNull final I_SEPA_Export_Line line)
	{
		final CreditTransferTransaction34 cdtTrfTxInf = objectFactory.createCreditTransferTransaction34();

		// Amount/Currency
		{
			final CurrencyId currencyId = CurrencyId.ofRepoId(line.getC_Currency_ID());
			final Currency currency = Services.get(ICurrencyDAO.class).getById(currencyId);
			final CurrencyCode currencyIsoCode = currency.getCurrencyCode();

			final BigDecimal amount = NumberUtils.stripTrailingDecimalZeros(line.getAmt());
			Check.errorIf(amount == null || amount.signum() <= 0, "Invalid amount={} of SEPA_Export_Line={}", amount, line);
			Check.errorIf(amount.scale() > currency.getPrecision().toInt(),
						  "Invalid number of decimal points; amount={} has {} decimal points, but the currency {} only allows {}; SEPA_Export_Line={}",
						  amount, amount.scale(), currencyIsoCode, currency.getPrecision(), line);

			final ActiveOrHistoricCurrencyAndAmount instdAmt = objectFactory.createActiveOrHistoricCurrencyAndAmount();
			instdAmt.setCcy(currencyIsoCode.toThreeLetterCode());
			instdAmt.setValue(amount);

			final AmountType4Choice amt = objectFactory.createAmountType4Choice();
			amt.setInstdAmt(instdAmt);
			cdtTrfTxInf.setAmt(amt);
		}

		final String paymentType = extractPaymentType(line);

		final BankAccount bankAccount = Optional.ofNullable(BankAccountId.ofRepoIdOrNull(line.getC_BP_BankAccount_ID()))
				.map(bankAccountService::getByIdNotNull)
				.orElse(null);

		// Creditor Agent (i.e. Bank)
		cdtTrfTxInf.setCdtrAgt(createCreditorAgent(line, paymentType));

		// Creditor BPartner
		cdtTrfTxInf.setCdtr(createCreditor(line, bankAccount, paymentType));

		// creditor BPartner Account
		{
			final String iban = StringUtils.trimBlankToNull(line.getIBAN());
			final String otherAccountIdentification = CoalesceUtil.coalesceSuppliers(
					() -> StringUtils.trimBlankToNull(line.getOtherAccountIdentification()),
					() -> bankAccount != null ? bankAccount.getAccountNo() : null);
			cdtTrfTxInf.setCdtrAcct(createCashAccount(iban, otherAccountIdentification));
		}

		final String reference = SepaUtils.replaceForbiddenChars(StringUtils.trimBlankToOptional(line.getStructuredRemittanceInfo()).orElseGet(line::getDescription));

		// Remittance Info
		cdtTrfTxInf.setRmtInf(createRemittanceInformation(line, bankAccount, reference));

		// Payment ID
		// EndToEndId: A unique key, depending on process parameter generated by the system for each payment.
		{
			final String endToEndId;
			if (exportContext.isReferenceAsEndToEndId())
			{
				// IG 3.2 + 4.3: EndToEndId is max. 35 characters of the restricted reference character set (.03 allowed up to 65 characters)
				endToEndId = CoalesceUtil.coalesce(toReferenceText(reference), END_TO_END_ID_NOTPROVIDED);
			}
			else
			{
				endToEndIdCounter++;
				endToEndId = StringUtils.formatMessage("ENDTOENDID-{}", endToEndIdCounter);
			}
			final PaymentIdentification6 pmtId = objectFactory.createPaymentIdentification6();
			pmtId.setEndToEndId(endToEndId);
			// the number of the PmtInf this transaction belongs to (the PmtInfs are not necessarily filled one after the other)
			pmtId.setInstrId(StringUtils.formatMessage("INSTRID-{}-{}", pmtInfNumbers.get(pmtInf), pmtInf.getCdtTrfTxInf().size() + 1));

			cdtTrfTxInf.setPmtId(pmtId);
		}
		return cdtTrfTxInf;
	}

	/**
	 * IG 3.12 and 4.3 CdtrAgt; unlike .03, we never send the placeholder BIC "NOTPROVIDED":
	 * <ul>
	 * <li>"D" with a CH/LI IBAN: IID taken from the IBAN as ClrSysMmbId with CHBCC (IG 3.12 option 2; "D: Must contain CHBCC").
	 * If both IBAN and BIC are known, the agent is determined from the IBAN anyway, and BICFI and ClrSysMmbId must not be used together (IG 3.12 limitations).</li>
	 * <li>"S" or "D" without a CH/LI IBAN: BICFI if known (IG 3.12 option 3), otherwise no CdtrAgt (IG 3.12 option 1, IG 4.3 "S: The indication of the Creditor Agent is optional").</li>
	 * </ul>
	 */
	@Nullable
	private BranchAndFinancialInstitutionIdentification6 createCreditorAgent(
			@NonNull final I_SEPA_Export_Line line,
			@NonNull final String paymentType)
	{
		final String iidFromIban = PAYMENT_TYPE_3.equals(paymentType) ? extractIIDFromIban(line.getIBAN()) : null;
		final String bic = iidFromIban == null ? StringUtils.trimBlankToNull(line.getSwiftCode()) : null;
		return createAgent(bic, iidFromIban);
	}

	/**
	 * Creates a financial institution identification (IG 3.12), identified either by IID (ClrSysMmbId with CHBCC) or by BICFI, never both.
	 *
	 * @return {@code null} if neither is given, because empty group elements are not allowed (IG 3.4)
	 */
	@Nullable
	private BranchAndFinancialInstitutionIdentification6 createAgent(@Nullable final String bic, @Nullable final String iid)
	{
		final FinancialInstitutionIdentification18 finInstnId = objectFactory.createFinancialInstitutionIdentification18();
		if (Check.isNotBlank(iid))
		{
			final ClearingSystemIdentification2Choice clrSysId = objectFactory.createClearingSystemIdentification2Choice();
			clrSysId.setCd(CLEARING_SYSTEM_CHBCC);

			final ClearingSystemMemberIdentification2 clrSysMmbId = objectFactory.createClearingSystemMemberIdentification2();
			clrSysMmbId.setClrSysId(clrSysId);
			clrSysMmbId.setMmbId(iid);
			finInstnId.setClrSysMmbId(clrSysMmbId);
		}
		else if (Check.isNotBlank(bic))
		{
			finInstnId.setBICFI(bic.trim()); // IG 3.12: "BIC" is now "BICFI"
		}
		else
		{
			return null;
		}

		final BranchAndFinancialInstitutionIdentification6 agent = objectFactory.createBranchAndFinancialInstitutionIdentification6();
		agent.setFinInstnId(finInstnId);
		return agent;
	}

	private CashAccount38 createCashAccount(@Nullable final String iban, @Nullable final String otherAccountIdentification)
	{
		final AccountIdentification4Choice id = objectFactory.createAccountIdentification4Choice();
		if (Check.isNotBlank(iban))
		{
			id.setIBAN(iban.replaceAll(" ", ""));
		}
		else
		{
			final GenericAccountIdentification1 othr = objectFactory.createGenericAccountIdentification1();
			othr.setId(otherAccountIdentification);
			id.setOthr(othr);
		}

		final CashAccount38 cashAccount = objectFactory.createCashAccount38();
		cashAccount.setId(id);
		return cashAccount;
	}

	@Nullable
	private RemittanceInformation16 createRemittanceInformation(
			@NonNull final I_SEPA_Export_Line line,
			@Nullable final BankAccount bankAccount,
			@Nullable final String reference)
	{
		if (bankAccount != null && Check.isNotBlank(bankAccount.getQR_IBAN()))
		{
			// IG 4.3 RmtInf: with a QR-IBAN, Ustrd must not be used, but Strd/CdtrRefInf with a QR reference must
			throw new SepaMarshallerException("QR-IBAN payments are not supported yet by " + PAIN_001_001_09_CH_03 + "; " + createInfo(line));
		}

		// IG 4.3 RmtInf is optional and IG 3.4 forbids empty elements, so without a reference there is no RmtInf (.03 sent Ustrd=".")
		if (Check.isBlank(reference))
		{
			return null;
		}

		// IG 4.3 RmtInf/Ustrd: only one occurrence, maximum 140 characters
		final RemittanceInformation16 rmtInf = objectFactory.createRemittanceInformation16();
		rmtInf.getUstrd().add(StringUtils.trunc(reference, 140, TruncateAt.STRING_START));
		return rmtInf;
	}

	@SafeVarargs
	@Nullable
	private final String getFirstNonEmpty(@NonNull final Supplier<String>... values)
	{
		final String result = CoalesceUtil.firstValidValue(Check::isNotBlank, values);
		return result != null ? result.trim() : null;
	}

	private PartyIdentification135 createCreditor(
			@NonNull final I_SEPA_Export_Line line,
			@Nullable final BankAccount bankAccount,
			@NonNull final String paymentType)
	{
		final String name;
		if (bankAccount == null)
		{
			name = getFirstNonEmpty(
					line::getSEPA_MandateRefNo,
					() -> getBPartnerNameById(line.getC_BPartner_ID()));
		}
		else
		{
			name = getFirstNonEmpty(
					bankAccount::getAccountName,
					line::getSEPA_MandateRefNo,
					() -> getBPartnerNameById(line.getC_BPartner_ID()));
		}
		final PartyIdentification135 cdtr = objectFactory.createPartyIdentification135();
		cdtr.setNm(truncate(SepaUtils.replaceForbiddenChars(name), PAYMENT_TYPE_5.equals(paymentType) ? MAX_LENGTH_NAME_SEPA : MAX_LENGTH_NAME));

		// Bank account is authoritative: when it carries any address data we use it
		// verbatim and never consult the partner billing location. The partner
		// location is only loaded as a fallback for the empty-bank-account case.
		final boolean useBankAccount = bankAccount != null && !bankAccount.isAddressEmpty();

		final I_C_Location location;
		if (useBankAccount)
		{
			location = null;
		}
		else
		{
			final Properties ctx = InterfaceWrapperHelper.getCtx(line);
			final I_C_BPartner_Location billToLocation = partnerDAO.retrieveBillToLocation(ctx, line.getC_BPartner_ID(), true, ITrx.TRXNAME_None);
			if (billToLocation == null)
			{
				return cdtr;
			}
			location = locationDAO.getById(LocationId.ofRepoId(billToLocation.getC_Location_ID()));
		}

		// IG 3.11: structured addresses for all payment types (.03 used AdrLine-only addresses for SEPA, which is no longer accepted)
		cdtr.setPstlAdr(toCompletePstlAdrOrNull(createPstlAdr(bankAccount, location)));

		return cdtr;
	}

	/**
	 * IG 3.11: if a postal address is sent, TwnNm and Ctry must always be supplied. For now an incomplete address is not sent at all.
	 * This is the single place where the completeness of the creditor address is decided (to be turned into a validation error with the partner name).
	 */
	@Nullable
	private static PostalAddress24 toCompletePstlAdrOrNull(@NonNull final PostalAddress24 pstlAdr)
	{
		final boolean complete = Check.isNotBlank(pstlAdr.getTwnNm()) && Check.isNotBlank(pstlAdr.getCtry());
		return complete ? pstlAdr : null;
	}

	/**
	 * Cuts the given text to the given maximum length (keeping the beginning), as required by the XSD types / IG field lengths.
	 *
	 * @return {@code null} if the text is blank, because empty elements are not allowed (IG 3.4)
	 */
	@Nullable
	private static String truncate(@Nullable final String text, final int maxLength)
	{
		final String textNorm = StringUtils.trimBlankToNull(text);
		if (textNorm == null)
		{
			return null;
		}
		return textNorm.length() <= maxLength ? textNorm : textNorm.substring(0, maxLength).trim();
	}

	/**
	 * Structured postal address (IG 3.11: StrtNm, BldgNb, PstCd, TwnNm, Ctry; TwnNm and Ctry must always be supplied).
	 * If the bank account carries any address data, it is authoritative and emitted verbatim; otherwise the given location is used.
	 */
	@VisibleForTesting
	PostalAddress24 createPstlAdr(
			@Nullable final BankAccount bpBankAccount,
			@Nullable final I_C_Location location)
	{
		if (bpBankAccount != null && !bpBankAccount.isAddressEmpty())
		{
			SepaUtils.assertValidAccountCountry(bpBankAccount);

			final PostalAddress24 pstlAdr = objectFactory.createPostalAddress24();
			splitStreetAndNumber(SepaUtils.replaceForbiddenChars(bpBankAccount.getAccountStreet()), pstlAdr);
			pstlAdr.setPstCd(truncate(SepaUtils.replaceForbiddenChars(bpBankAccount.getAccountZip()), MAX_LENGTH_POST_CODE));
			pstlAdr.setTwnNm(truncate(SepaUtils.replaceForbiddenChars(bpBankAccount.getAccountCity()), MAX_LENGTH_TOWN_NAME));
			pstlAdr.setCtry(bpBankAccount.getAccountCountry());
			return pstlAdr;
		}

		Objects.requireNonNull(location, "location must be set when bank account address is empty");

		final PostalAddress24 pstlAdr = objectFactory.createPostalAddress24();
		splitStreetAndNumber(SepaUtils.replaceForbiddenChars(location.getAddress1()), pstlAdr);
		pstlAdr.setPstCd(truncate(SepaUtils.replaceForbiddenChars(location.getPostal()), MAX_LENGTH_POST_CODE));
		pstlAdr.setTwnNm(truncate(SepaUtils.replaceForbiddenChars(location.getCity()), MAX_LENGTH_TOWN_NAME));
		pstlAdr.setCtry(countryDAO.getById(CountryId.ofRepoId(location.getC_Country_ID())).getCountryCode());
		return pstlAdr;
	}

	private void splitStreetAndNumber(
			@Nullable final String streetAndNumber,
			@NonNull final PostalAddress24 pstlAdr)
	{
		final IPair<String, String> splitStreetAndHouseNumber = de.metas.common.util.StringUtils.splitStreetAndHouseNumberOrNull(streetAndNumber);
		if (splitStreetAndHouseNumber == null)
		{
			// e.g. "12 rue de la Paix" (number first) cannot be split; IG 3.11 permits the building number within StrtNm
			pstlAdr.setStrtNm(truncate(streetAndNumber, MAX_LENGTH_STREET_NAME));
			return;
		}

		pstlAdr.setStrtNm(truncate(splitStreetAndHouseNumber.getLeft(), MAX_LENGTH_STREET_NAME));
		pstlAdr.setBldgNb(truncate(splitStreetAndHouseNumber.getRight(), MAX_LENGTH_BUILDING_NUMBER));
	}

	/**
	 * For IBANs that start with "CH" or "LI", this method extracts the Swiss institution identification (IID, formerly "BC") and returns it.
	 *
	 * @return {@code null} if the given {@code iban} is {@code null}, empty or not "CH"/"LI".
	 */
	@Nullable
	private static String extractIIDFromIban(@Nullable final String iban)
	{
		if (!isSwissIBAN(iban))
		{
			return null;
		}

		final int iidStartIdx = 4;
		final int iidEndIdx = 9;
		final String ibanToUse = iban.replaceAll(" ", "");

		Check.errorIf(ibanToUse.length() < iidEndIdx,
					  SepaMarshallerException.class,
					  "Given IBAN {} is to short. Please verify that it's actually an IBAN at all",
					  iban);
		return ibanToUse.substring(iidStartIdx, iidEndIdx);
	}

	/**
	 * @return true if the given IBAN (stripped from spaces) starts with either "CH" or "LI", i.e. contains a Swiss IID.
	 */
	private static boolean isSwissIBAN(@Nullable final String iban)
	{
		if (Check.isBlank(iban))
		{
			return false;
		}
		final String ibanToUse = iban.replaceAll(" ", "");
		return ibanToUse.startsWith("CH") || ibanToUse.startsWith("LI");
	}

	/**
	 * IG 3.2: the reference elements may only contain {@code A-Z a-z 0-9 space ' ( ) + , - . / : ?}, must not start with a space or "/", must not end with "/" and must not contain "//".
	 * Other characters are replaced by spaces; the result is truncated to 35 characters, keeping the end (like the .03 marshaller does for references).
	 *
	 * @return {@code null} if nothing usable remains
	 */
	@VisibleForTesting
	@Nullable
	static String toReferenceText(@Nullable final String text)
	{
		if (Check.isBlank(text))
		{
			return null;
		}

		String reference = REFERENCE_FORBIDDEN_CHARS.matcher(text).replaceAll(" ");
		reference = reference.replaceAll("/{2,}", "/");
		reference = StringUtils.trunc(reference.trim(), REFERENCE_MAX_LENGTH, TruncateAt.STRING_START);
		reference = stripSpacesAndSlashes(reference);

		return reference.isEmpty() ? null : reference;
	}

	/**
	 * Removes leading and trailing whitespace and "/" without a regex, so the runtime stays linear for any input.
	 */
	private static String stripSpacesAndSlashes(@NonNull final String text)
	{
		int start = 0;
		int end = text.length();
		while (start < end && isSpaceOrSlash(text.charAt(start)))
		{
			start++;
		}
		while (end > start && isSpaceOrSlash(text.charAt(end - 1)))
		{
			end--;
		}
		return text.substring(start, end);
	}

	private static boolean isSpaceOrSlash(final char c)
	{
		return c == '/' || Character.isWhitespace(c);
	}

	private XMLGregorianCalendar newXMLGregorianCalendar(final Date date)
	{
		final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return datatypeFactory.newXMLGregorianCalendar(df.format(date));
	}

	/**
	 * Payment type detection as in the .03 marshaller (except for the ESR flag, see below), restricted to the payment types this marshaller supports.
	 */
	@NonNull
	private String extractPaymentType(@NonNull final I_SEPA_Export_Line line)
	{
		final de.metas.payment.esr.model.I_C_BP_BankAccount bPBankAccount = InterfaceWrapperHelper.create(
				line.getC_BP_BankAccount(),
				de.metas.payment.esr.model.I_C_BP_BankAccount.class);

		final String paymentType;
		if (bPBankAccount.isEsrAccount() && Check.isNotBlank(line.getStructuredRemittanceInfo()) && Check.isBlank(bPBankAccount.getQR_IBAN()))
		{
			paymentType = PAYMENT_TYPE_1;
		}
		else
		{
			final CurrencyCode currencyCode = extractCurrencyCode(line);
			final String iban = line.getIBAN();

			// IG 3.15 (figure 10): without SEPA service level, only a CH/LI IBAN makes it "D"; unlike .03, the ESR flag of the account no longer counts
			if (isSwissIBAN(iban))
			{
				// "domestic" IBAN. it contains the IID and we will use it.
				Check.errorIf(!currencyCode.isEuro() && !currencyCode.isCHF(),
							  SepaMarshallerException.class,
							  "line {} has a swiss IBAN, but the currency is {} instead of 'CHF' or 'EUR'",
							  createInfo(line), currencyCode);

				paymentType = PAYMENT_TYPE_3;
			}
			else if (Check.isNotBlank(iban) && currencyCode.isEuro())
			{
				paymentType = PAYMENT_TYPE_5;
			}
			else
			{
				paymentType = PAYMENT_TYPE_6;
			}
		}

		if (PAYMENT_TYPE_1.equals(paymentType))
		{
			throw new SepaMarshallerException(PAIN_001_001_09_CH_03 + " does not support ISR payments; only domestic (payment type D) and SEPA (payment type S) payments with an IBAN are supported; " + createInfo(line));
		}
		else if (PAYMENT_TYPE_6.equals(paymentType))
		{
			throw new SepaMarshallerException(PAIN_001_001_09_CH_03 + " does not support foreign (payment type X) payments yet; only domestic (payment type D) and SEPA (payment type S) payments with an IBAN are supported; " + createInfo(line));
		}
		return paymentType;
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

	private String getBPartnerNameById(final int bpartnerRepoId)
	{
		final IBPartnerBL bpartnerService = Services.get(IBPartnerBL.class);
		return bpartnerService.getBPartnerName(BPartnerId.ofRepoIdOrNull(bpartnerRepoId));
	}

	@Override
	public String toString()
	{
		return "SEPAVendorCreditTransferMarshaler_Pain_001_001_09_CH_03";
	}
}
