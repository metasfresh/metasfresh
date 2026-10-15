package de.metas.payment.sepa.sepamarshaller.impl;

import ch.qos.logback.classic.Level;
import com.google.common.annotations.VisibleForTesting;
import de.metas.banking.Bank;
import de.metas.banking.BankAccount;
import de.metas.banking.BankAccountId;
import de.metas.banking.api.BankAccountService;
import de.metas.banking.api.BankRepository;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.pair.IPair;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.Currency;
import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyDAO;
import de.metas.location.CountryId;
import de.metas.location.ICountryDAO;
import de.metas.location.ILocationDAO;
import de.metas.location.LocationId;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.payment.esr.model.I_C_BP_BankAccount;
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
import de.metas.payment.sepa.jaxb.sct.pain_001_001_09_ch_03.CreditorReferenceInformation2;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_09_ch_03.CreditorReferenceType1Choice;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_09_ch_03.CreditorReferenceType2;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_09_ch_03.CustomerCreditTransferInitiationV09;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_09_ch_03.DateAndDateTime2Choice;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_09_ch_03.DocumentPain001Ch;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_09_ch_03.DocumentType3Code;
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
import de.metas.payment.sepa.jaxb.sct.pain_001_001_09_ch_03.StructuredRemittanceInformation16;
import de.metas.payment.sepa.model.I_SEPA_Export;
import de.metas.payment.sepa.model.I_SEPA_Export_Line;
import de.metas.util.Check;
import de.metas.util.Loggables;
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
import org.slf4j.Logger;

import javax.annotation.Nullable;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlSchema;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
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
 * <li>"D" (domestic) with an IBAN or QR-IBAN of the creditor (internally {@link #PAYMENT_TYPE_3})</li>
 * <li>"S" (SEPA) with an IBAN of the creditor (internally {@link #PAYMENT_TYPE_5})</li>
 * <li>"X" (V1 foreign currency domestic, V2 cross-border; internally {@link #PAYMENT_TYPE_6})</li>
 * </ul>
 * ISR payments no longer exist in the IG and are rejected with a {@link SepaMarshallerException}.
 * <p>
 * Remittance information (IG 3.14.2, 4.3): QR reference ("QRR") with a QR-IBAN; ISO 11649 creditor reference ("SCOR") if the structured reference is one; otherwise unstructured.
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
	 * Title: "ISR". Not supported by this format (IG 3.15 has no ISR payment type anymore).
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
	 * Title: "Foreign" (IG 3.15: payment type "X"; V1 foreign currency domestic, V2 cross-border)
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
	 * IG 4.3 RmtInf/Strd/CdtrRefInf/Tp/CdOrPrtry/Prtry: "QRR = QR reference (in association with QR-bill)"
	 */
	private static final String CREDITOR_REFERENCE_TYPE_QRR = "QRR";

	/**
	 * IG 4.3 RmtInf/Strd/CdtrRefInf/Tp/Issr: with "ISO", the SCOR reference is validated according to ISO 11649 for every payment type (IG 3.14.2)
	 */
	private static final String CREDITOR_REFERENCE_ISSUER_ISO = "ISO";

	/**
	 * IG 3.14.2: ISO 11649 creditor reference: "RF", two check digits, max. 25 characters
	 */
	private static final int CREDITOR_REFERENCE_MIN_LENGTH = 5;
	private static final int CREDITOR_REFERENCE_MAX_LENGTH = 25;

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
	private final BankRepository bankRepository;
	private final SEPAExportContext exportContext;
	private final BankAccountService bankAccountService;
	private final ILocationDAO locationDAO = Services.get(ILocationDAO.class);
	private final IBPartnerDAO partnerDAO = Services.get(IBPartnerDAO.class);
	private final ICountryDAO countryDAO = Services.get(ICountryDAO.class);
	private final ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);
	private final ISEPADocumentDAO sepaDocumentDAO = Services.get(ISEPADocumentDAO.class);
	private final ISEPADocumentBL sepaDocumentBL = Services.get(ISEPADocumentBL.class);
	private final IBPartnerBL bpartnerBL = Services.get(IBPartnerBL.class);

	private static final Logger logger = LogManager.getLogger(SEPAVendorCreditTransferMarshaler_Pain_001_001_09_CH_03.class);

	private static final String encoding = "UTF-8";

	private int endToEndIdCounter = 0;
	private int pmtInfCounter = 0;
	private final Map<PaymentInstruction30, Integer> pmtInfNumbers = new IdentityHashMap<>();
	private final Set<ArrayKey> omittedAddressWarnings = new HashSet<>();

	public SEPAVendorCreditTransferMarshaler_Pain_001_001_09_CH_03(
			@NonNull final BankRepository bankRepository,
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

		this.bankRepository = bankRepository;
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
		final List<I_SEPA_Export_Line> sepaDocumentLines = sepaDocumentDAO.retrieveLines(sepaDocument);
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

		// This name is also our Dbtr/Nm. IG 4.2: Dbtr is mandatory (1..1), and IG 3.4 forbids empty group elements; the name is the only Dbtr data we have
		// IG 4.1 InitgPty/Nm and 4.2 Dbtr/Nm: "S: max. 70 characters"; the group header applies to the whole message, so we use 70 for all payment types
		final String debtorName = truncate(SepaUtils.replaceForbiddenChars(sepaDocument.getSEPA_CreditorName()), MAX_LENGTH_NAME_SEPA);
		if (debtorName == null)
		{
			throw new SepaMarshallerException("SEPA_Export " + sepaDocument.getDocumentNo() + " has no SEPA_CreditorName (the name of the paying organisation, sent as debtor name)."
					+ " It is taken from the name of the business partner linked to the organisation; please set that name and create the SEPA export again.");
		}

		final PartyIdentification135 initgPty = objectFactory.createPartyIdentification135();
		initgPty.setNm(debtorName);
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
		return currencyDAO.getCurrencyCodeById(currencyId);
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
		final Date dueDate = sepaDocumentBL.getDueDate(sepaLine);
		final DateAndDateTime2Choice reqdExctnDt = objectFactory.createDateAndDateTime2Choice();
		reqdExctnDt.setDt(SepaUtils.toXMLGregorianCalendarDate(datatypeFactory, dueDate));
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
		final BranchAndFinancialInstitutionIdentification6 dbtrAgt = createAgent(debtorBic, debtorBic == null ? SepaUtils.extractIIDFromSwissIbanOrNull(iban, () -> "the debtor account of SEPA_Export " + sepaHdr.getDocumentNo()) : null);
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
			final Currency currency = currencyDAO.getById(currencyId);
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
		cdtTrfTxInf.setCdtrAgt(createCreditorAgent(line, bankAccount, paymentType));

		// Creditor BPartner
		cdtTrfTxInf.setCdtr(createCreditor(line, bankAccount, paymentType));

		// creditor BPartner Account
		{
			final String iban = extractCreditorIban(line, bankAccount);
			final String otherAccountIdentification = CoalesceUtil.coalesceSuppliers(
					() -> StringUtils.trimBlankToNull(line.getOtherAccountIdentification()),
					() -> bankAccount != null ? bankAccount.getAccountNo() : null);
			cdtTrfTxInf.setCdtrAcct(createCashAccount(iban, otherAccountIdentification));
		}

		// Remittance Info; a structured reference is also used (without whitespace) as EndToEndId if so configured
		final CreditorReferenceInformation2 creditorReference = createCreditorReferenceOrNull(line, bankAccount);
		final String reference = creditorReference != null
				? creditorReference.getRef()
				: SepaUtils.replaceForbiddenChars(StringUtils.trimBlankToOptional(line.getStructuredRemittanceInfo()).orElseGet(line::getDescription));
		cdtTrfTxInf.setRmtInf(createRemittanceInformation(creditorReference, reference));

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
	 * <li>QR-IBAN: no CdtrAgt (IG 3.12 option 1; IG 4.3 "D: The Creditor Agent can be omitted if the IBAN/QR-IBAN (CH/LI) is provided"), so we don't send the QR-IID as if it was an IID.</li>
	 * <li>"D" or "X" with a CH/LI IBAN: IID taken from the IBAN as ClrSysMmbId with CHBCC (IG 3.12 option 2; IG 4.3 "D: Must contain CHBCC", "X: (V1, domestic) - Must contain CHBCC").
	 * If both IBAN and BIC are known, the agent is determined from the IBAN anyway, and BICFI and ClrSysMmbId must not be used together (IG 3.12 limitations).</li>
	 * <li>otherwise BICFI if known (IG 3.12 option 3, "recommended for payment type X")</li>
	 * <li>"S" without BIC: no CdtrAgt (IG 3.12 option 1, IG 4.3 "S: The indication of the Creditor Agent is optional")</li>
	 * <li>"X" without BIC: name and address of the bank (IG 3.12 option 5; IG 4.3 CdtrAgt/FinInstnId/PstlAdr "X: Must be supplied when &lt;Name&gt; is used")</li>
	 * </ul>
	 */
	@Nullable
	private BranchAndFinancialInstitutionIdentification6 createCreditorAgent(
			@NonNull final I_SEPA_Export_Line line,
			@Nullable final BankAccount bankAccount,
			@NonNull final String paymentType)
	{
		if (isQrIbanPayment(line, bankAccount))
		{
			return null;
		}

		final String iidFromIban = PAYMENT_TYPE_5.equals(paymentType) ? null : SepaUtils.extractIIDFromSwissIbanOrNull(line.getIBAN(), () -> "line " + SepaUtils.createInfo(line));
		final String bic = iidFromIban == null ? StringUtils.trimBlankToNull(line.getSwiftCode()) : null;
		final BranchAndFinancialInstitutionIdentification6 agent = createAgent(bic, iidFromIban);
		if (agent != null || !PAYMENT_TYPE_6.equals(paymentType))
		{
			return agent;
		}

		return createCreditorAgentFromBankNameAndAddress(line, bankAccount);
	}

	private BranchAndFinancialInstitutionIdentification6 createCreditorAgentFromBankNameAndAddress(
			@NonNull final I_SEPA_Export_Line line,
			@Nullable final BankAccount bankAccount)
	{
		final Bank bank = bankAccount != null && bankAccount.getBankId() != null ? bankRepository.getById(bankAccount.getBankId()) : null;
		final String bankName = bank != null ? truncate(SepaUtils.replaceForbiddenChars(bank.getBankName()), MAX_LENGTH_NAME) : null;
		final PostalAddress24 bankAddress = bank != null && bank.getLocationId() != null ? createPstlAdr(null, locationDAO.getById(bank.getLocationId())) : null;
		if (bankName == null || bankAddress == null || !isCompleteAddress(bankAddress))
		{
			throw new SepaMarshallerException("Foreign payment (payment type X) and the creditor account is not a CH/LI IBAN: the creditor's bank has neither a BIC nor a bank address (name, town and country)."
					+ " Please set the BIC or the address of the bank; " + SepaUtils.createInfo(line));
		}

		final FinancialInstitutionIdentification18 finInstnId = objectFactory.createFinancialInstitutionIdentification18();
		finInstnId.setNm(bankName);
		finInstnId.setPstlAdr(bankAddress);

		final BranchAndFinancialInstitutionIdentification6 agent = objectFactory.createBranchAndFinancialInstitutionIdentification6();
		agent.setFinInstnId(finInstnId);
		return agent;
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

	/**
	 * IG 4.3 RmtInf: "Either structured or unstructured."
	 */
	@Nullable
	private RemittanceInformation16 createRemittanceInformation(
			@Nullable final CreditorReferenceInformation2 creditorReference,
			@Nullable final String reference)
	{
		final RemittanceInformation16 rmtInf = objectFactory.createRemittanceInformation16();
		if (creditorReference != null)
		{
			// IG 4.3 RmtInf/Strd: "Only one occurrence is allowed."
			final StructuredRemittanceInformation16 strd = objectFactory.createStructuredRemittanceInformation16();
			strd.setCdtrRefInf(creditorReference);
			rmtInf.getStrd().add(strd);
			return rmtInf;
		}

		// IG 4.3 RmtInf is optional and IG 3.4 forbids empty elements, so without a reference there is no RmtInf (.03 sent Ustrd=".")
		if (Check.isBlank(reference))
		{
			return null;
		}

		// IG 4.3 RmtInf/Ustrd: only one occurrence, maximum 140 characters
		rmtInf.getUstrd().add(StringUtils.trunc(reference, 140, TruncateAt.STRING_START));
		return rmtInf;
	}

	/**
	 * IG 3.14.2 and 4.3 RmtInf/Strd/CdtrRefInf:
	 * <ul>
	 * <li>QR-IBAN: the structured reference must be a QR reference, sent with Prtry "QRR" (IG 4.3 Strd "D: This element must be used in combination with a QR-IBAN";
	 * Ustrd "D: This element must not be supplied in combination with a QR-IBAN").</li>
	 * <li>otherwise, if the structured reference is an ISO 11649 creditor reference: Cd "SCOR" with Issr "ISO" (IG 4.3 Tp/CdOrPrtry/Cd "Only the code SCOR is allowed"; IG 5.1 example).</li>
	 * </ul>
	 *
	 * @return {@code null} if the reference shall be sent unstructured
	 */
	@Nullable
	private CreditorReferenceInformation2 createCreditorReferenceOrNull(
			@NonNull final I_SEPA_Export_Line line,
			@Nullable final BankAccount bankAccount)
	{
		final String structuredReference = removeWhitespace(line.getStructuredRemittanceInfo());

		final CreditorReferenceType1Choice cdOrPrtry = objectFactory.createCreditorReferenceType1Choice();
		final CreditorReferenceType2 tp = objectFactory.createCreditorReferenceType2();
		tp.setCdOrPrtry(cdOrPrtry);
		if (isQrIbanPayment(line, bankAccount))
		{
			if (!SepaUtils.isValidQrReference(structuredReference))
			{
				// only reached if the account has a QR-IBAN but no IBAN, so there is no other way to pay it
				throw newInvalidQrReferenceException(line, structuredReference, "the bank account has only a QR-IBAN, so a valid QR reference (27 digits incl. check digit) is required");
			}
			cdOrPrtry.setPrtry(CREDITOR_REFERENCE_TYPE_QRR);
		}
		else if (bankAccount != null && Check.isNotBlank(bankAccount.getQR_IBAN()) && isQrReferenceShaped(structuredReference))
		{
			// IBAN + QR-IBAN account and a reference that looks like a QR reference, but fails the check digit: most likely a typo of the QR-bill reference.
			// Sending it to the normal IBAN as Ustrd would break the creditor's automatic matching, so we stop here.
			throw newInvalidQrReferenceException(line, structuredReference, "the reference has the format of a QR reference (27 digits), but its check digit is wrong");
		}
		else if (isValidCreditorReference(structuredReference))
		{
			cdOrPrtry.setCd(DocumentType3Code.SCOR);
			tp.setIssr(CREDITOR_REFERENCE_ISSUER_ISO);
		}
		else
		{
			return null;
		}

		final CreditorReferenceInformation2 cdtrRefInf = objectFactory.createCreditorReferenceInformation2();
		cdtrRefInf.setTp(tp);
		cdtrRefInf.setRef(structuredReference);
		return cdtrRefInf;
	}

	private AdempiereException newInvalidQrReferenceException(
			@NonNull final I_SEPA_Export_Line line,
			@Nullable final String structuredReference,
			@NonNull final String reason)
	{
		return new AdempiereException(SepaUtils.ERR_SEPA_Export_InvalidReference, SepaUtils.createInfo(line))
				.appendParametersToMessage()
				.setParameter("structuredReference", structuredReference != null ? structuredReference : "no structured reference")
				.setParameter("reason", reason);
	}

	/**
	 * IG 3.14.2: the QR reference has 26 digits plus one check digit.
	 *
	 * @return {@code true} if the given text has the format of a QR reference (27 digits), regardless of its check digit
	 */
	private static boolean isQrReferenceShaped(@Nullable final String reference)
	{
		if (reference == null || reference.length() != SepaUtils.QR_REFERENCE_LENGTH)
		{
			return false;
		}
		for (int i = 0; i < SepaUtils.QR_REFERENCE_LENGTH; i++)
		{
			if (!isDigit(reference.charAt(i)))
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * IG 3.14.2: ISO 11649 creditor reference: "RF" in positions 1-2, check digits in positions 3-4, max. 25 alphanumeric characters.
	 * The check is ISO 7064 MOD 97-10 over the reference with its first four characters moved to the end, letters counting as 10..35.
	 */
	@VisibleForTesting
	static boolean isValidCreditorReference(@Nullable final String reference)
	{
		if (reference == null
				|| reference.length() < CREDITOR_REFERENCE_MIN_LENGTH
				|| reference.length() > CREDITOR_REFERENCE_MAX_LENGTH
				|| !reference.startsWith("RF")
				|| !isDigit(reference.charAt(2))
				|| !isDigit(reference.charAt(3)))
		{
			return false;
		}

		int remainder = 0;
		for (int i = 0; i < reference.length(); i++)
		{
			final char c = reference.charAt((i + 4) % reference.length());
			final int value = Character.digit(c, 36);
			if (value < 0 || c > 'z')
			{
				return false; // not alphanumeric (also rejects non-ASCII digits)
			}
			remainder = ((value > 9 ? remainder * 100 : remainder * 10) + value) % 97;
		}
		return remainder == 1;
	}

	private static boolean isDigit(final char c)
	{
		return c >= '0' && c <= '9';
	}

	@Nullable
	private static String removeWhitespace(@Nullable final String text)
	{
		if (text == null)
		{
			return null;
		}
		final StringBuilder sb = new StringBuilder(text.length());
		for (int i = 0; i < text.length(); i++)
		{
			final char c = text.charAt(i);
			if (!Character.isWhitespace(c))
			{
				sb.append(c);
			}
		}
		return sb.length() == 0 ? null : sb.toString();
	}

	private static boolean isQrIbanPayment(@NonNull final I_SEPA_Export_Line line, @Nullable final BankAccount bankAccount)
	{
		return bankAccount != null && isQrIbanPayment(bankAccount.getQR_IBAN(), bankAccount.getIBAN(), line.getStructuredRemittanceInfo());
	}

	/**
	 * A payment goes to the QR-IBAN if the account has one and either the reference is a QR reference or the account has no (normal) IBAN.
	 * IG 3.14.2 / 4.3: the QR reference is "only permitted and is required in connection with a QR-IBAN"; with any other reference, an account that also has an IBAN is paid to that IBAN (SCOR / Ustrd / X V1).
	 */
	private static boolean isQrIbanPayment(
			@Nullable final String qrIban,
			@Nullable final String iban,
			@Nullable final String structuredRemittanceInfo)
	{
		return Check.isNotBlank(qrIban)
				&& (Check.isBlank(iban) || SepaUtils.isValidQrReference(removeWhitespace(structuredRemittanceInfo)));
	}

	/**
	 * The export line's IBAN is the bank account's IBAN, or its QR-IBAN if it has no IBAN.
	 * IG 3.14.2 / 4.3: the QR reference may only be used with a QR-IBAN in "Creditor Account/IBAN", so for a QR-IBAN payment we always send the QR-IBAN.
	 */
	@Nullable
	private static String extractCreditorIban(@NonNull final I_SEPA_Export_Line line, @Nullable final BankAccount bankAccount)
	{
		return isQrIbanPayment(line, bankAccount) ? bankAccount.getQR_IBAN().trim() : StringUtils.trimBlankToNull(line.getIBAN());
	}

	private PartyIdentification135 createCreditor(
			@NonNull final I_SEPA_Export_Line line,
			@Nullable final BankAccount bankAccount,
			@NonNull final String paymentType)
	{
		final String name;
		if (bankAccount == null)
		{
			name = SepaUtils.getFirstNonEmpty(
					line::getSEPA_MandateRefNo,
					() -> SepaUtils.getBPartnerNameById(line.getC_BPartner_ID()));
		}
		else
		{
			name = SepaUtils.getFirstNonEmpty(
					bankAccount::getAccountName,
					line::getSEPA_MandateRefNo,
					() -> SepaUtils.getBPartnerNameById(line.getC_BPartner_ID()));
		}
		final PartyIdentification135 cdtr = objectFactory.createPartyIdentification135();
		cdtr.setNm(truncate(SepaUtils.replaceForbiddenChars(name), PAYMENT_TYPE_5.equals(paymentType) ? MAX_LENGTH_NAME_SEPA : MAX_LENGTH_NAME));
		if (cdtr.getNm() == null)
		{
			// IG 4.3 Cdtr/Nm: "Must be supplied" (mandatory in the XSD, too)
			throw new SepaMarshallerException("The payment has no creditor name. Please set the account holder name of the bank account or the name of the business partner; " + SepaUtils.createInfo(line));
		}

		// Bank account is authoritative: when it carries any address data we use it
		// verbatim and never consult the partner billing location. The partner
		// location is only loaded as a fallback for the empty-bank-account case.
		final boolean useBankAccount = bankAccount != null && !bankAccount.isAddressEmpty();

		final I_C_Location location;
		final String addressSource;
		if (useBankAccount)
		{
			location = null;
			addressSource = "address of the bank account " + CoalesceUtil.coalesce(bankAccount.getIBAN(), bankAccount.getQR_IBAN(), bankAccount.getAccountNo());
		}
		else
		{
			final Properties ctx = InterfaceWrapperHelper.getCtx(line);
			final I_C_BPartner_Location billToLocation = partnerDAO.retrieveBillToLocation(ctx, line.getC_BPartner_ID(), true, ITrx.TRXNAME_None);
			if (billToLocation == null)
			{
				return cdtr; // IG 4.3: Cdtr/PstlAdr is optional
			}
			location = locationDAO.getById(LocationId.ofRepoId(billToLocation.getC_Location_ID()));
			addressSource = "location " + billToLocation.getName();
		}

		// IG 3.11: structured addresses for all payment types (.03 used AdrLine-only addresses for SEPA, which is no longer accepted)
		final PostalAddress24 pstlAdr = createPstlAdr(bankAccount, location);
		final String missingAddressParts = getMissingAddressPartsOrNull(pstlAdr);
		if (missingAddressParts == null)
		{
			cdtr.setPstlAdr(pstlAdr);
		}
		else if (PAYMENT_TYPE_6.equals(paymentType))
		{
			throw new AdempiereException("The address of business partner " + SepaUtils.getBPartnerNameById(line.getC_BPartner_ID()) + " (" + addressSource + ") has no " + missingAddressParts + "."
					+ " " + PAIN_001_001_09_CH_03 + " requires town and country; please complete the address.")
					.appendParametersToMessage()
					.setParameter("SEPA_Export_Line", line);
		}
		else
		{
			// D and S: Cdtr/PstlAdr is optional (IG 4.3), so we leave out the incomplete address instead of sending an invalid one and warn the user.
			// X (above): the export fails, so that the address is completed before a foreign payment is sent.
			warnIncompleteAddressOmitted(line, addressSource, missingAddressParts);
		}

		return cdtr;
	}

	/**
	 * IG 3.11: if a postal address is sent, the sub-elements TwnNm and Ctry "must always be supplied".
	 * This is the single place where the completeness of the creditor address is decided.
	 *
	 * @return the missing parts ("town", "country" or "town and country"), or {@code null} if the address is complete
	 */
	@Nullable
	private static String getMissingAddressPartsOrNull(@NonNull final PostalAddress24 pstlAdr)
	{
		final boolean townMissing = Check.isBlank(pstlAdr.getTwnNm());
		final boolean countryMissing = Check.isBlank(pstlAdr.getCtry());
		if (townMissing && countryMissing)
		{
			return "town and country";
		}
		else if (townMissing)
		{
			return "town";
		}
		else if (countryMissing)
		{
			return "country";
		}
		return null;
	}

	private static boolean isCompleteAddress(@NonNull final PostalAddress24 pstlAdr)
	{
		return getMissingAddressPartsOrNull(pstlAdr) == null;
	}

	/**
	 * Tells the user (in the log of the export process) that a payment was exported without the creditor address; once per partner and address.
	 */
	private void warnIncompleteAddressOmitted(
			@NonNull final I_SEPA_Export_Line line,
			@NonNull final String addressSource,
			@NonNull final String missingAddressParts)
	{
		final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(line.getC_BPartner_ID());
		if (!omittedAddressWarnings.add(ArrayKey.of(bpartnerId, addressSource, missingAddressParts)))
		{
			return;
		}

		Loggables.withLogger(logger, Level.WARN).addLog(
				"Business partner {} ({}): the {} has no {}. The address was left out of the payment file, because " + PAIN_001_001_09_CH_03 + " only allows an address with town and country."
						+ " Please complete the address.",
				bpartnerBL.getBPartnerValue(bpartnerId), bpartnerBL.getBPartnerName(bpartnerId), addressSource, missingAddressParts);
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
			if (Check.isNotBlank(bpBankAccount.getAccountCountry()))
			{
				SepaUtils.assertValidAccountCountry(bpBankAccount); // a missing country is handled by createCreditor (see getMissingAddressPartsOrNull)
			}

			final PostalAddress24 pstlAdr = objectFactory.createPostalAddress24();
			splitStreetAndNumber(SepaUtils.replaceForbiddenChars(bpBankAccount.getAccountStreet()), pstlAdr);
			pstlAdr.setPstCd(truncate(SepaUtils.replaceForbiddenChars(bpBankAccount.getAccountZip()), MAX_LENGTH_POST_CODE));
			pstlAdr.setTwnNm(truncate(SepaUtils.replaceForbiddenChars(bpBankAccount.getAccountCity()), MAX_LENGTH_TOWN_NAME));
			pstlAdr.setCtry(StringUtils.trimBlankToNull(bpBankAccount.getAccountCountry()));
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

	/**
	 * Payment type detection as in the .03 marshaller (except for the ESR flag, QR-IBAN and foreign currencies, see below), restricted to the payment types this marshaller supports.
	 */
	@NonNull
	private String extractPaymentType(@NonNull final I_SEPA_Export_Line line)
	{
		final I_C_BP_BankAccount bPBankAccount = InterfaceWrapperHelper.create(line.getC_BP_BankAccount(), I_C_BP_BankAccount.class);

		final boolean qrIbanAccount = Check.isNotBlank(bPBankAccount.getQR_IBAN());
		final boolean qrIbanPayment = isQrIbanPayment(bPBankAccount.getQR_IBAN(), bPBankAccount.getIBAN(), line.getStructuredRemittanceInfo());
		final String paymentType;
		if (bPBankAccount.isEsrAccount()
				&& Check.isNotBlank(line.getStructuredRemittanceInfo())
				&& !qrIbanAccount
				&& !isValidCreditorReference(removeWhitespace(line.getStructuredRemittanceInfo())))
		{
			// an ESR account with an ISO 11649 reference is a QR-bill with IBAN and SCOR reference (IG 3.16), not an ISR
			paymentType = PAYMENT_TYPE_1;
		}
		else
		{
			final CurrencyCode currencyCode = extractCurrencyCode(line);
			final String iban = qrIbanPayment ? bPBankAccount.getQR_IBAN() : line.getIBAN();

			// IG 3.15 (figure 10): without SEPA service level, only a CH/LI IBAN makes it "D"; unlike .03, the ESR flag of the account no longer counts
			if (SepaUtils.isSwissIBAN(iban))
			{
				final boolean domesticCurrency = currencyCode.isEuro() || currencyCode.isCHF();
				// IG 3.16: "The QR-IBAN and the QR reference are intended for billing in CHF" (EUR until November 2027)
				Check.errorIf(qrIbanPayment && !domesticCurrency,
							  SepaMarshallerException.class,
							  "line {} has a QR-IBAN, but the currency is {} instead of 'CHF' or 'EUR'",
							  SepaUtils.createInfo(line), currencyCode);

				// "domestic" IBAN; it contains the IID and we will use it. IG 3.15: in a currency other than CHF/EUR it is "X" (V1, foreign currency domestic)
				paymentType = domesticCurrency ? PAYMENT_TYPE_3 : PAYMENT_TYPE_6;
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
			// IG 3.15: the payment types are D, S, X and C; there is no ISR payment type anymore
			throw new SepaMarshallerException(PAIN_001_001_09_CH_03 + " does not support ISR payments (the ISR was replaced by the QR-bill)."
					+ " Clear the ESR flag of the bank account, set a QR-IBAN, or use an ISO 11649 (RF) creditor reference; " + SepaUtils.createInfo(line));
		}
		return paymentType;
	}

	@Override
	public String toString()
	{
		return "SEPAVendorCreditTransferMarshaler_Pain_001_001_09_CH_03";
	}
}
