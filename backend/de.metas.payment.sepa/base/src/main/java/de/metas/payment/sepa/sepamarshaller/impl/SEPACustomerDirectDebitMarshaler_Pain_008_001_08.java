package de.metas.payment.sepa.sepamarshaller.impl;

import de.metas.banking.mandate.DirectDebitMandate;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.common.util.time.SystemTime;
import de.metas.location.CountryId;
import de.metas.location.ICountryDAO;
import de.metas.location.ILocationDAO;
import de.metas.location.LocationId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.payment.sepa.api.ISEPADocumentBL;
import de.metas.payment.sepa.api.ISEPADocumentDAO;
import de.metas.payment.sepa.api.SEPAExportContext;
import de.metas.payment.sepa.api.SepaUtils;
import de.metas.payment.sepa.jaxb.sct.pain_008_001_08.AccountIdentification4Choice;
import de.metas.payment.sepa.jaxb.sct.pain_008_001_08.ActiveOrHistoricCurrencyAndAmount;
import de.metas.payment.sepa.jaxb.sct.pain_008_001_08.BranchAndFinancialInstitutionIdentification6;
import de.metas.payment.sepa.jaxb.sct.pain_008_001_08.CashAccount38;
import de.metas.payment.sepa.jaxb.sct.pain_008_001_08.ChargeBearerType1Code;
import de.metas.payment.sepa.jaxb.sct.pain_008_001_08.CustomerDirectDebitInitiationV08;
import de.metas.payment.sepa.jaxb.sct.pain_008_001_08.DirectDebitTransaction10;
import de.metas.payment.sepa.jaxb.sct.pain_008_001_08.DirectDebitTransactionInformation23;
import de.metas.payment.sepa.jaxb.sct.pain_008_001_08.DocumentSDD;
import de.metas.payment.sepa.jaxb.sct.pain_008_001_08.FinancialInstitutionIdentification18;
import de.metas.payment.sepa.jaxb.sct.pain_008_001_08.GenericFinancialIdentification1;
import de.metas.payment.sepa.jaxb.sct.pain_008_001_08.GenericPersonIdentification1;
import de.metas.payment.sepa.jaxb.sct.pain_008_001_08.GroupHeader83;
import de.metas.payment.sepa.jaxb.sct.pain_008_001_08.LocalInstrument2Choice;
import de.metas.payment.sepa.jaxb.sct.pain_008_001_08.MandateRelatedInformation14;
import de.metas.payment.sepa.jaxb.sct.pain_008_001_08.ObjectFactory;
import de.metas.payment.sepa.jaxb.sct.pain_008_001_08.Party38Choice;
import de.metas.payment.sepa.jaxb.sct.pain_008_001_08.PartyIdentification135;
import de.metas.payment.sepa.jaxb.sct.pain_008_001_08.PaymentIdentification6;
import de.metas.payment.sepa.jaxb.sct.pain_008_001_08.PaymentInstruction29;
import de.metas.payment.sepa.jaxb.sct.pain_008_001_08.PaymentMethod2Code;
import de.metas.payment.sepa.jaxb.sct.pain_008_001_08.PaymentTypeInformation29;
import de.metas.payment.sepa.jaxb.sct.pain_008_001_08.PersonIdentification13;
import de.metas.payment.sepa.jaxb.sct.pain_008_001_08.PersonIdentificationSchemeName1Choice;
import de.metas.payment.sepa.jaxb.sct.pain_008_001_08.PostalAddress24;
import de.metas.payment.sepa.jaxb.sct.pain_008_001_08.RemittanceInformation16;
import de.metas.payment.sepa.jaxb.sct.pain_008_001_08.SequenceType3Code;
import de.metas.payment.sepa.jaxb.sct.pain_008_001_08.ServiceLevel8Choice;
import de.metas.payment.sepa.model.I_SEPA_Export;
import de.metas.payment.sepa.model.I_SEPA_Export_Line;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.compiere.util.TimeUtil;

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
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

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
 * Direct debit export in the format pain.008.001.08 of the German banks (DK, {@code pain.008.001.08_GBIC_5.xsd}), for the CORE and B2B schemes.
 * <p>
 * One payment information block per export line. The creditor is the exporting organisation (its linked business partner's bill-to address); the mandate data come from the {@link SEPAExportContext}.
 * <p>
 * Note on the JAXB types: the generated {@code *SDD} classes are the XSD's restrictions of the ISO base types. The XSD already binds each element to its restriction,
 * so we instantiate the ISO base types; using the {@code *SDD} subclasses would make JAXB emit superfluous {@code xsi:type} attributes. Only the root is a {@link DocumentSDD}.
 */
public class SEPACustomerDirectDebitMarshaler_Pain_008_001_08 implements SEPAMarshaler
{
	private static final String PAIN_008_001_08 = "pain.008.001.08";
	private static final String PAIN_008_001_08_NAMESPACE = DocumentSDD.class.getPackage().getAnnotation(XmlSchema.class).namespace();
	private static final String PAIN_008_001_08_SCHEMALOCATION = PAIN_008_001_08_NAMESPACE + " " + PAIN_008_001_08 + ".xsd";

	private static final String SERVICE_LEVEL_SEPA = "SEPA";
	private static final String CREDITOR_SCHEME_NAME_SEPA = "SEPA";
	private static final String CURRENCY_EUR = "EUR";
	private static final String BIC_NOTPROVIDED = "NOTPROVIDED";

	/**
	 * Maximum lengths of the GBIC_5 types (Max140Text_SDD for names, Max140Text, Max70Text, Max16Text, Max35Text).
	 */
	private static final int MAX_LENGTH_NAME = 70;
	private static final int MAX_LENGTH_REMITTANCE_INFO = 140;
	private static final int MAX_LENGTH_STREET_NAME = 70;
	private static final int MAX_LENGTH_POST_CODE = 16;
	private static final int MAX_LENGTH_TOWN_NAME = 35;

	private static final String encoding = "UTF-8";

	private final ObjectFactory objectFactory = new ObjectFactory();
	private final DatatypeFactory datatypeFactory;
	@NonNull private final SEPAExportContext exportContext;
	@NonNull private final ISEPADocumentBL sepaDocumentBL = Services.get(ISEPADocumentBL.class);
	@NonNull private final ISEPADocumentDAO sepaDocumentDAO = Services.get(ISEPADocumentDAO.class);
	@NonNull private final IBPartnerOrgBL bpartnerOrgBL = Services.get(IBPartnerOrgBL.class);
	@NonNull private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	@NonNull private final ILocationDAO locationDAO = Services.get(ILocationDAO.class);
	@NonNull private final ICountryDAO countryDAO = Services.get(ICountryDAO.class);
	@NonNull private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	public SEPACustomerDirectDebitMarshaler_Pain_008_001_08(@NonNull final SEPAExportContext exportContext)
	{
		try
		{
			datatypeFactory = DatatypeFactory.newInstance();
		}
		catch (final DatatypeConfigurationException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
		this.exportContext = exportContext;
	}

	@Override
	public void marshal(@NonNull final I_SEPA_Export sepaDocument, @NonNull final OutputStream out)
	{
		try
		{
			marshal(createDocument(sepaDocument), out);
		}
		catch (final RuntimeException e)
		{
			final AdempiereException exception = AdempiereException.wrapIfNeeded(e);
			if (exception.getRecord() == null)
			{
				exception.setRecord(TableRecordReference.of(sepaDocument));
			}
			throw exception;
		}
	}

	private void marshal(@NonNull final DocumentSDD xmlDocument, @NonNull final OutputStream out)
	{
		final Writer xmlWriter;
		try
		{
			xmlWriter = new OutputStreamWriter(out, encoding);
		}
		catch (final UnsupportedEncodingException e)
		{
			throw new AdempiereException("Could not use encoding " + encoding, e);
		}

		try
		{
			final JAXBElement<DocumentSDD> jaxbDocument = objectFactory.createDocument(xmlDocument);
			final Marshaller marshaller = JAXBContext.newInstance(DocumentSDD.class.getPackage().getName()).createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, PAIN_008_001_08_SCHEMALOCATION);
			marshaller.marshal(jaxbDocument, xmlWriter);
		}
		catch (final JAXBException e)
		{
			throw new AdempiereException("Marshalling error", e);
		}
	}

	private DocumentSDD createDocument(@NonNull final I_SEPA_Export sepaDocument)
	{
		final List<I_SEPA_Export_Line> sepaDocumentLines = sepaDocumentBL.retrieveLines(sepaDocument);
		if (sepaDocumentLines.isEmpty())
		{
			throw AdempiereException.newWithTranslatableMessage("@NoLines@: " + sepaDocument.getDocumentNo());
		}

		final CustomerDirectDebitInitiationV08 cstmrDrctDbtInitn = new CustomerDirectDebitInitiationV08();
		final GroupHeader83 groupHeader = createGroupHeader(sepaDocument);
		cstmrDrctDbtInitn.setGrpHdr(groupHeader);

		final PartyIdentification135 creditor = createCreditor(sepaDocument);
		final XMLGregorianCalendar requestedCollectionDate = toXMLDate(TimeUtil.asLocalDate(
				sepaDocument.getPaymentDate(),
				orgDAO.getTimeZone(OrgId.ofRepoId(sepaDocument.getAD_Org_ID()))));

		for (final I_SEPA_Export_Line line : sepaDocumentLines)
		{
			if (!line.isActive() || line.isError())
			{
				continue;
			}

			final PaymentInstruction29 pmtInf = createPaymentInstruction(sepaDocument, line, groupHeader.getMsgId(), cstmrDrctDbtInitn.getPmtInf().size() + 1, creditor, requestedCollectionDate);
			cstmrDrctDbtInitn.getPmtInf().add(pmtInf);
			groupHeader.setCtrlSum(groupHeader.getCtrlSum().add(pmtInf.getCtrlSum()));
		}

		// one transaction per payment information block
		groupHeader.setNbOfTxs(String.valueOf(cstmrDrctDbtInitn.getPmtInf().size()));

		sepaDocument.setProcessed(!cstmrDrctDbtInitn.getPmtInf().isEmpty());
		sepaDocumentDAO.save(sepaDocument);

		final DocumentSDD document = new DocumentSDD();
		document.setCstmrDrctDbtInitn(cstmrDrctDbtInitn);
		return document;
	}

	private GroupHeader83 createGroupHeader(@NonNull final I_SEPA_Export sepaDocument)
	{
		final GroupHeader83 groupHeader = new GroupHeader83();
		groupHeader.setMsgId(SepaDkTextConverter.toIdentifier(sepaDocument.getDocumentNo()));
		groupHeader.setCreDtTm(datatypeFactory.newXMLGregorianCalendar(SystemTime.asGregorianCalendar()));
		groupHeader.setCtrlSum(ZERO);

		final PartyIdentification135 initgPty = new PartyIdentification135();
		initgPty.setNm(toCreditorName(sepaDocument));
		groupHeader.setInitgPty(initgPty);
		return groupHeader;
	}

	private PaymentInstruction29 createPaymentInstruction(
			@NonNull final I_SEPA_Export sepaHdr,
			@NonNull final I_SEPA_Export_Line line,
			@NonNull final String msgId,
			final int index,
			@NonNull final PartyIdentification135 creditor,
			@NonNull final XMLGregorianCalendar requestedCollectionDate)
	{
		final DirectDebitMandate mandate = exportContext.getDirectDebitMandate(line.getSEPA_Export_Line_ID());

		final PaymentInstruction29 pmtInf = new PaymentInstruction29();
		pmtInf.setPmtInfId(SepaDkTextConverter.toIdentifier(msgId + "-" + index));
		pmtInf.setPmtMtd(PaymentMethod2Code.DD);
		pmtInf.setNbOfTxs("1");

		final ServiceLevel8Choice svcLvl = new ServiceLevel8Choice();
		svcLvl.setCd(SERVICE_LEVEL_SEPA);
		final LocalInstrument2Choice lclInstrm = new LocalInstrument2Choice();
		lclInstrm.setCd(mandate.getType().getCode());
		final PaymentTypeInformation29 pmtTpInf = new PaymentTypeInformation29();
		pmtTpInf.getSvcLvl().add(svcLvl);
		pmtTpInf.setLclInstrm(lclInstrm);
		pmtTpInf.setSeqTp(mandate.isRecurring() ? SequenceType3Code.RCUR : SequenceType3Code.OOFF);
		pmtInf.setPmtTpInf(pmtTpInf);

		pmtInf.setReqdColltnDt(requestedCollectionDate);
		pmtInf.setCdtr(creditor);
		pmtInf.setCdtrAcct(createCashAccount(sepaHdr.getIBAN()));
		pmtInf.setCdtrAgt(createFinancialInstitution(sepaHdr.getSwiftCode()));
		pmtInf.setChrgBr(ChargeBearerType1Code.SLEV);
		pmtInf.setCdtrSchmeId(createCreditorSchemeIdOrNull(sepaHdr.getSEPA_CreditorIdentifier()));

		final DirectDebitTransactionInformation23 drctDbtTxInf = createDirectDebitTransactionInformation(pmtInf.getPmtInfId(), line, mandate);
		pmtInf.getDrctDbtTxInf().add(drctDbtTxInf);
		pmtInf.setCtrlSum(drctDbtTxInf.getInstdAmt().getValue());

		return pmtInf;
	}

	private DirectDebitTransactionInformation23 createDirectDebitTransactionInformation(
			@NonNull final String pmtInfId,
			@NonNull final I_SEPA_Export_Line line,
			@NonNull final DirectDebitMandate mandate)
	{
		final DirectDebitTransactionInformation23 drctDbtTxInf = new DirectDebitTransactionInformation23();

		final PaymentIdentification6 pmtId = new PaymentIdentification6();
		pmtId.setEndToEndId(SepaDkTextConverter.toIdentifier(pmtInfId + "-0001"));
		drctDbtTxInf.setPmtId(pmtId);

		final BigDecimal amount = line.getAmt();
		Check.assume(amount != null && amount.signum() > 0, "Invalid amount '{}' for {}", amount, line);
		final ActiveOrHistoricCurrencyAndAmount instdAmt = new ActiveOrHistoricCurrencyAndAmount();
		instdAmt.setCcy(CURRENCY_EUR);
		instdAmt.setValue(amount);
		drctDbtTxInf.setInstdAmt(instdAmt);

		final MandateRelatedInformation14 mndtRltdInf = new MandateRelatedInformation14();
		mndtRltdInf.setMndtId(SepaDkTextConverter.toIdentifier(mandate.getReference()));
		mndtRltdInf.setDtOfSgntr(toXMLDate(mandate.getMandateDate()));
		mndtRltdInf.setAmdmntInd(Boolean.FALSE);
		final DirectDebitTransaction10 drctDbtTx = new DirectDebitTransaction10();
		drctDbtTx.setMndtRltdInf(mndtRltdInf);
		drctDbtTxInf.setDrctDbtTx(drctDbtTx);

		drctDbtTxInf.setDbtrAgt(createFinancialInstitution(line.getSwiftCode()));

		final PartyIdentification135 dbtr = new PartyIdentification135();
		final String debtorName = SepaDkTextConverter.toText(SepaUtils.getBPartnerNameById(line.getC_BPartner_ID()), MAX_LENGTH_NAME);
		Check.assumeNotNull(debtorName, "Debtor of {} has a name", line);
		dbtr.setNm(debtorName);
		drctDbtTxInf.setDbtr(dbtr);

		drctDbtTxInf.setDbtrAcct(createCashAccount(line.getIBAN()));

		final String ustrd = SepaDkTextConverter.toOptionalText(line.getDescription(), MAX_LENGTH_REMITTANCE_INFO);
		if (ustrd != null)
		{
			final RemittanceInformation16 rmtInf = new RemittanceInformation16();
			rmtInf.getUstrd().add(ustrd);
			drctDbtTxInf.setRmtInf(rmtInf);
		}

		return drctDbtTxInf;
	}

	private String toCreditorName(@NonNull final I_SEPA_Export sepaDocument)
	{
		final String creditorName = SepaDkTextConverter.toText(sepaDocument.getSEPA_CreditorName(), MAX_LENGTH_NAME);
		Check.assumeNotNull(creditorName, "{} has a creditor name", sepaDocument);
		return creditorName;
	}

	private PartyIdentification135 createCreditor(@NonNull final I_SEPA_Export sepaDocument)
	{
		final PartyIdentification135 cdtr = new PartyIdentification135();
		cdtr.setNm(toCreditorName(sepaDocument));

		final I_C_BPartner_Location location = getCreditorLocationOrNull(OrgId.ofRepoId(sepaDocument.getAD_Org_ID()));
		if (location != null)
		{
			final PostalAddress24 pstlAdr = createPstlAdr(locationDAO.getById(LocationId.ofRepoId(location.getC_Location_ID())));
			if (isCompleteAddress(pstlAdr))
			{
				cdtr.setPstlAdr(pstlAdr);
			}
		}
		return cdtr;
	}

	/**
	 * @return the default bill-to location of the organisation's linked business partner, else its first active bill-to location; {@code null} if there is none
	 */
	@Nullable
	private I_C_BPartner_Location getCreditorLocationOrNull(@NonNull final OrgId orgId)
	{
		final I_C_BPartner orgBPartner = bpartnerOrgBL.retrieveLinkedBPartner(orgId);
		if (orgBPartner == null)
		{
			return null;
		}

		return bpartnerDAO.retrieveBPartnerLocations(BPartnerId.ofRepoId(orgBPartner.getC_BPartner_ID()))
				.stream()
				.filter(location -> location.isBillToDefault() || location.isBillTo())
				.min(Comparator.comparing((I_C_BPartner_Location location) -> !location.isBillToDefault())
						.thenComparing(I_C_BPartner_Location::getC_BPartner_Location_ID))
				.orElse(null);
	}

	private PostalAddress24 createPstlAdr(@NonNull final I_C_Location location)
	{
		final PostalAddress24 pstlAdr = new PostalAddress24();
		pstlAdr.setStrtNm(SepaDkTextConverter.toOptionalText(location.getAddress1(), MAX_LENGTH_STREET_NAME));
		pstlAdr.setPstCd(SepaDkTextConverter.toOptionalText(location.getPostal(), MAX_LENGTH_POST_CODE));
		pstlAdr.setTwnNm(SepaDkTextConverter.toOptionalText(location.getCity(), MAX_LENGTH_TOWN_NAME));
		pstlAdr.setCtry(StringUtils.trimBlankToNull(countryDAO.getCountryCode(CountryId.ofRepoId(location.getC_Country_ID()))));
		return pstlAdr;
	}

	// copied from SEPAVendorCreditTransferMarshaler_Pain_001_001_09_CH_03#isCompleteAddress
	private static boolean isCompleteAddress(@NonNull final PostalAddress24 pstlAdr)
	{
		return Check.isNotBlank(pstlAdr.getTwnNm()) && Check.isNotBlank(pstlAdr.getCtry());
	}

	private static CashAccount38 createCashAccount(@Nullable final String iban)
	{
		final AccountIdentification4Choice id = new AccountIdentification4Choice();
		id.setIBAN(StringUtils.trimBlankToNull(iban));
		final CashAccount38 cashAccount = new CashAccount38();
		cashAccount.setId(id);
		return cashAccount;
	}

	/**
	 * @return the bank identified by its BIC, or as {@code NOTPROVIDED} (IBAN-only) if there is no BIC
	 */
	private static BranchAndFinancialInstitutionIdentification6 createFinancialInstitution(@Nullable final String bic)
	{
		final FinancialInstitutionIdentification18 finInstnId = new FinancialInstitutionIdentification18();
		final String bicNorm = StringUtils.trimBlankToNull(bic);
		if (bicNorm != null)
		{
			finInstnId.setBICFI(bicNorm);
		}
		else
		{
			final GenericFinancialIdentification1 othr = new GenericFinancialIdentification1();
			othr.setId(BIC_NOTPROVIDED);
			finInstnId.setOthr(othr);
		}

		final BranchAndFinancialInstitutionIdentification6 agent = new BranchAndFinancialInstitutionIdentification6();
		agent.setFinInstnId(finInstnId);
		return agent;
	}

	@Nullable
	private static PartyIdentification135 createCreditorSchemeIdOrNull(@Nullable final String creditorIdentifier)
	{
		final String creditorIdentifierNorm = StringUtils.trimBlankToNull(creditorIdentifier);
		if (creditorIdentifierNorm == null)
		{
			return null;
		}

		final PersonIdentificationSchemeName1Choice schmeNm = new PersonIdentificationSchemeName1Choice();
		schmeNm.setPrtry(CREDITOR_SCHEME_NAME_SEPA);
		final GenericPersonIdentification1 othr = new GenericPersonIdentification1();
		othr.setId(creditorIdentifierNorm);
		othr.setSchmeNm(schmeNm);
		final PersonIdentification13 prvtId = new PersonIdentification13();
		prvtId.getOthr().add(othr);
		final Party38Choice id = new Party38Choice();
		id.setPrvtId(prvtId);
		final PartyIdentification135 cdtrSchmeId = new PartyIdentification135();
		cdtrSchmeId.setId(id);
		return cdtrSchmeId;
	}

	private XMLGregorianCalendar toXMLDate(@NonNull final LocalDate date)
	{
		// not SepaUtils.toXMLGregorianCalendarDate like the Swiss writer: that formats a Date in the JVM zone, which could shift an org-zone LocalDate by one day

		return datatypeFactory.newXMLGregorianCalendar(date.toString());
	}

	@Override
	public String toString()
	{
		return "SEPACustomerDirectDebitMarshaler_Pain_008_001_08";
	}
}
