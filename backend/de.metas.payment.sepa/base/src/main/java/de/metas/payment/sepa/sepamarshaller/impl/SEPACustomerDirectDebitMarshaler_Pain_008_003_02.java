package de.metas.payment.sepa.sepamarshaller.impl;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import de.metas.common.util.time.SystemTime;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.TimeUtil;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.payment.sepa.api.ISEPADocumentBL;
import de.metas.payment.sepa.api.ISEPADocumentDAO;
import de.metas.payment.sepa.jaxb.sct.pain_008_003_02.AccountIdentificationSEPA;
import de.metas.payment.sepa.jaxb.sct.pain_008_003_02.ActiveOrHistoricCurrencyAndAmountSEPA;
import de.metas.payment.sepa.jaxb.sct.pain_008_003_02.ActiveOrHistoricCurrencyCodeEUR;
import de.metas.payment.sepa.jaxb.sct.pain_008_003_02.BranchAndFinancialInstitutionIdentificationSEPA3;
import de.metas.payment.sepa.jaxb.sct.pain_008_003_02.CashAccountSEPA1;
import de.metas.payment.sepa.jaxb.sct.pain_008_003_02.CashAccountSEPA2;
import de.metas.payment.sepa.jaxb.sct.pain_008_003_02.ChargeBearerTypeSEPACode;
import de.metas.payment.sepa.jaxb.sct.pain_008_003_02.CustomerDirectDebitInitiationV02;
import de.metas.payment.sepa.jaxb.sct.pain_008_003_02.DirectDebitTransactionInformationSDD;
import de.metas.payment.sepa.jaxb.sct.pain_008_003_02.DirectDebitTransactionSDD;
import de.metas.payment.sepa.jaxb.sct.pain_008_003_02.Document;
import de.metas.payment.sepa.jaxb.sct.pain_008_003_02.FinancialInstitutionIdentificationSEPA3;
import de.metas.payment.sepa.jaxb.sct.pain_008_003_02.GroupHeaderSDD;
import de.metas.payment.sepa.jaxb.sct.pain_008_003_02.IdentificationSchemeNameSEPA;
import de.metas.payment.sepa.jaxb.sct.pain_008_003_02.LocalInstrumentSEPA;
import de.metas.payment.sepa.jaxb.sct.pain_008_003_02.MandateRelatedInformationSDD;
import de.metas.payment.sepa.jaxb.sct.pain_008_003_02.ObjectFactory;
import de.metas.payment.sepa.jaxb.sct.pain_008_003_02.PartyIdentificationSEPA1;
import de.metas.payment.sepa.jaxb.sct.pain_008_003_02.PartyIdentificationSEPA2;
import de.metas.payment.sepa.jaxb.sct.pain_008_003_02.PartyIdentificationSEPA3;
import de.metas.payment.sepa.jaxb.sct.pain_008_003_02.PartyIdentificationSEPA5;
import de.metas.payment.sepa.jaxb.sct.pain_008_003_02.PartySEPA2;
import de.metas.payment.sepa.jaxb.sct.pain_008_003_02.PaymentIdentificationSEPA;
import de.metas.payment.sepa.jaxb.sct.pain_008_003_02.PaymentInstructionInformationSDD;
import de.metas.payment.sepa.jaxb.sct.pain_008_003_02.PaymentMethod2Code;
import de.metas.payment.sepa.jaxb.sct.pain_008_003_02.PaymentTypeInformationSDD;
import de.metas.payment.sepa.jaxb.sct.pain_008_003_02.PersonIdentificationSEPA2;
import de.metas.payment.sepa.jaxb.sct.pain_008_003_02.RemittanceInformationSEPA1Choice;
import de.metas.payment.sepa.jaxb.sct.pain_008_003_02.RestrictedPersonIdentificationSEPA;
import de.metas.payment.sepa.jaxb.sct.pain_008_003_02.RestrictedPersonIdentificationSchemeNameSEPA;
import de.metas.payment.sepa.jaxb.sct.pain_008_003_02.SequenceType1Code;
import de.metas.payment.sepa.jaxb.sct.pain_008_003_02.ServiceLevelSEPA;
import de.metas.payment.sepa.model.I_SEPA_Export;
import de.metas.payment.sepa.model.I_SEPA_Export_Line;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.common.util.CoalesceUtil;
import de.metas.util.xml.DynamicObjectFactory;
import lombok.NonNull;

public class SEPACustomerDirectDebitMarshaler_Pain_008_003_02 implements SEPAMarshaler
{
	private static final String JAXB_SchemaLocation = "urn:sepade:xsd:pain.008.003.02 pain.008.003.02.xsd";
	private static final String JAXB_ContextPath = ObjectFactory.class.getPackage().getName();
	private static final DynamicObjectFactory JAXB_ObjectFactory = new DynamicObjectFactory(new ObjectFactory());

	private final DatatypeFactory datatypeFactory;

	private final IBPartnerBL bpartnerService = Services.get(IBPartnerBL.class);

	public SEPACustomerDirectDebitMarshaler_Pain_008_003_02()
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

	private void marshal(@NonNull final Document xmlDocument, @NonNull final OutputStream out)
	{
		final Writer xmlWriter = new OutputStreamWriter(out);

		try
		{
			final JAXBElement<Document> jaxbDocument = JAXB_ObjectFactory.createJAXBElement(xmlDocument);

			final JAXBContext jaxbContext = JAXBContext.newInstance(JAXB_ContextPath);
			final Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty("jaxb.formatted.output", Boolean.TRUE);
			marshaller.setProperty("jaxb.schemaLocation", JAXB_SchemaLocation);
			marshaller.marshal(jaxbDocument, xmlWriter);
		}
		catch (final JAXBException e)
		{
			throw new AdempiereException("Marshalling error", e);
		}
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
				initiatingParty.setNm(sepaDocument.getSEPA_CreditorName());
				groupHeader.setInitgPty(initiatingParty);
			}
		}

		//
		// Payment Informations: create one PaymentInstructionInformationSDD for each line
		final List<I_SEPA_Export_Line> sepaDocumentLines = Services.get(ISEPADocumentDAO.class).retrieveLines(sepaDocument);
		if (sepaDocumentLines.isEmpty())
		{
			throw AdempiereException.newWithTranslatableMessage("@NoLines@: " + sepaDocument);
		}

		for (final I_SEPA_Export_Line line : sepaDocumentLines)
		{
			if (!line.isActive() || line.isError())
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
		final PaymentInstructionInformationSDD pmtInf = new PaymentInstructionInformationSDD();

		// PaymentInformationIdentification: A system-generated internal code.
		{
			// Current index of this payment instruction information
			final int currentIndex = customerDirectDebitInitiation.getPmtInf().size() + 1;
			final String pmtInfId = customerDirectDebitInitiation.getGrpHdr().getMsgId() + "-" + currentIndex;
			pmtInf.setPmtInfId(pmtInfId);
		}

		// Payment method: Hard-coded value of DD.
		pmtInf.setPmtMtd(PaymentMethod2Code.DD);

		// Number of transactions: Hard-coded with a value of 1. You can inform only one payment per payment information block.
		pmtInf.setNbOfTxs("1");

		//
		// Control Sum: This is the total amount of the collection.
		// NOTE: because NumberOfTransactions is ONE, we can assume this is the line Amount, but we will update it later
		pmtInf.setCtrlSum(null);

		// Payment Type Information (PmtTpInf)
		{
			final PaymentTypeInformationSDD pmtTpInf = new PaymentTypeInformationSDD();
			pmtInf.setPmtTpInf(pmtTpInf);

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
			// "OOFF": single direct debit without continuation within the mandate.
			// "RCUR": following direct debits within the accompanying mandate"
			// We go with RCUR now
			pmtTpInf.setSeqTp(SequenceType1Code.RCUR);

			// //FIXME: Is this needed?
			// {
			// final CategoryPurposeSEPA ctgyPurp = new CategoryPurposeSEPA();
			// ctgyPurp.setCd("DCRD");
			//
			// pmtTpInf.setCtgyPurp(ctgyPurp);
			// }
		}

		//
		// RequestedCollectionDate: Due date of the collection
		final Date dueDate = Services.get(ISEPADocumentBL.class).getDueDate(line);
		pmtInf.setReqdColltnDt(newXMLGregorianCalendar(dueDate));

		//
		// Creditor BPartner (Cdtr)
		// NOTE: actually it's same as GroupHeader's initiating party
		{
			final PartyIdentificationSEPA5 cdtr = convertPartyIdentificationSEPA5(customerDirectDebitInitiation.getGrpHdr().getInitgPty());
			pmtInf.setCdtr(cdtr);
		}

		//
		// Creditor BPartner Account
		{
			final CashAccountSEPA1 cdtrAcct = new CashAccountSEPA1();
			pmtInf.setCdtrAcct(cdtrAcct);

			final AccountIdentificationSEPA id = new AccountIdentificationSEPA();
			id.setIBAN(sepaHdr.getIBAN());
			cdtrAcct.setId(id);
		}

		//
		// Creditor Agent (i.e. Bank)
		{
			final BranchAndFinancialInstitutionIdentificationSEPA3 creditorAgt = new BranchAndFinancialInstitutionIdentificationSEPA3();
			pmtInf.setCdtrAgt(creditorAgt);

			final FinancialInstitutionIdentificationSEPA3 finInstnId = new FinancialInstitutionIdentificationSEPA3();
			finInstnId.setBIC(sepaHdr.getSwiftCode());
			creditorAgt.setFinInstnId(finInstnId);
		}

		// Charge Bearer Type
		// Hard-coded value of SLEV.
		pmtInf.setChrgBr(ChargeBearerTypeSEPACode.SLEV);

		//
		// Direct Debit Transaction Information
		{
			final DirectDebitTransactionInformationSDD directDebitTrxInfo = createDirectDebitTransactionInformation(pmtInf, line);
			pmtInf.getDrctDbtTxInf().add(directDebitTrxInfo);

			//
			// UPDATE: Payment Information's Control Sum
			// NOTE: we have only one line
			pmtInf.setCtrlSum(directDebitTrxInfo.getInstdAmt().getValue());
		}

		{
			pmtInf.setCdtrSchmeId(convertPartyIdentificationSEPA3(sepaHdr));
		}

		return pmtInf;
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
			Check.assume(amount != null && amount.signum() > 0, "Invalid amount '{0}' for {1}'", amount, line);
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

			// NOTE: put an earlier as its not allowed to be on the same date as the credit. Minus 4 days should do it
			mandateRelatedInf.setDtOfSgntr(newXMLGregorianCalendar(TimeUtil.addDays(line.getSEPA_Export().getPaymentDate(), -4)));

			mandateRelatedInf.setMndtId(getSEPA_MandateRefNo(line));
		}

		//
		// Debitor Agent (i.e. Bank)
		{
			final BranchAndFinancialInstitutionIdentificationSEPA3 debitorAgent = new BranchAndFinancialInstitutionIdentificationSEPA3();
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

			debitor.setNm(getBPartnerNameById(line.getC_BPartner_ID()));

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
			remittanceInfo.setUstrd(line.getDescription() + " ");
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

	private PartyIdentificationSEPA3 convertPartyIdentificationSEPA3(@NonNull final I_SEPA_Export sepaHeader)
	{
		final PartyIdentificationSEPA3 partyIdCopy = new PartyIdentificationSEPA3();
		final PartySEPA2 partySEPA = new PartySEPA2();
		partyIdCopy.setId(partySEPA);

		final PersonIdentificationSEPA2 prvtId = new PersonIdentificationSEPA2();
		partySEPA.setPrvtId(prvtId);

		final RestrictedPersonIdentificationSEPA othr = new RestrictedPersonIdentificationSEPA();
		prvtId.setOthr(othr);

		final RestrictedPersonIdentificationSchemeNameSEPA schemeNm = new RestrictedPersonIdentificationSchemeNameSEPA();
		othr.setId(sepaHeader.getSEPA_CreditorIdentifier()); // it's not mandatory, but we made sure that it was set for SEPA_Exports with this protocol
		othr.setSchmeNm(schemeNm);
		schemeNm.setPrtry(IdentificationSchemeNameSEPA.valueOf("SEPA"));

		// NOTE: address is not mandatory
		// FIXME: copy address if exists

		return partyIdCopy;
	}

	private String getSEPA_MandateRefNo(final I_SEPA_Export_Line line)
	{
		return CoalesceUtil.coalesceSuppliers(
				() -> line.getSEPA_MandateRefNo(),
				() -> getBPartnerValueById(line.getC_BPartner_ID()) + "-1");
	}

	private String getBPartnerValueById(final int bpartnerRepoId)
	{
		return bpartnerService.getBPartnerValue(BPartnerId.ofRepoIdOrNull(bpartnerRepoId)).trim();
	}

	private String getBPartnerNameById(final int bpartnerRepoId)
	{
		return bpartnerService.getBPartnerName(BPartnerId.ofRepoIdOrNull(bpartnerRepoId)).trim();
	}

}
