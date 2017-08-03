package de.metas.payment.esr.dataimporter.impl.camt54;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;
import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.util.StreamReaderDelegate;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Loggables;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Objects;

import ch.qos.logback.classic.Level;
import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;
import de.metas.payment.camt054_001_06.AccountNotification12;
import de.metas.payment.camt054_001_06.ActiveOrHistoricCurrencyAndAmount;
import de.metas.payment.camt054_001_06.BankToCustomerDebitCreditNotificationV06;
import de.metas.payment.camt054_001_06.CreditDebitCode;
import de.metas.payment.camt054_001_06.DateAndDateTimeChoice;
import de.metas.payment.camt054_001_06.Document;
import de.metas.payment.camt054_001_06.EntryDetails7;
import de.metas.payment.camt054_001_06.EntryTransaction8;
import de.metas.payment.camt054_001_06.ReportEntry8;
import de.metas.payment.esr.ESRConstants;
import de.metas.payment.esr.dataimporter.ESRStatement;
import de.metas.payment.esr.dataimporter.ESRStatement.ESRStatementBuilder;
import de.metas.payment.esr.dataimporter.ESRTransaction;
import de.metas.payment.esr.dataimporter.ESRTransaction.ESRTransactionBuilder;
import de.metas.payment.esr.dataimporter.IESRDataImporter;
import de.metas.payment.esr.model.I_ESR_Import;
import lombok.NonNull;

/*
 * #%L
 * de.metas.payment.esr
 * %%
 * Copyright (C) 2017 metas GmbH
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
 * Creates an {@link ESRStatement} from camt.54-XML data.
 * This implementation assumes the incoming XML to comply with the version <b><code>camt.054.001.06</code></b>.
 * However, XML which have versions <code>camt.054.001.04</code> and <code>camt.054.001.05</code> will also work (gh #1093).
 *
 * <p>
 * The XSD file for the xml which this implementation imports is available at <a href="https://www.iso20022.org/documents/messages/camt/schemas/camt.054.001.06.zip">BankToCustomerDebitCreditNotificationV04</a>.<br>
 * Note that
 * <ul>
 * <li>the latest version of the camt.54 XSD can be found <a href="https://www.iso20022.org/payments_messages.page">here</a> and</li>
 * <li>an archive of all older message specifications messages can be found <a href="https://www.iso20022.org/message_archive.page">here</a>.</li>
 * </ul>
 * <p>
 * Also see <a href="https://www.six-interbank-clearing.com/dam/downloads/en/standardization/iso/swiss-recommendations/implementation-guidelines-camt.pdf">Swiss Implementation Guidelines for Cash Management</a>.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class ESRDataImporterCamt54 implements IESRDataImporter
{

	@VisibleForTesting
	static final String MSG_AMBIGOUS_REFERENCE = "ESR_CAMT54_Ambigous_Reference";

	@VisibleForTesting
	static final String MSG_MISSING_ESR_REFERENCE = "ESR_CAMT54_Missing_ESR_Reference";

	private static final String MSG_UNSUPPORTED_CREDIT_DEBIT_CODE_1P = "ESR_CAMT54_UnsupportedCreditDebitCode";

	/**
	 * This constant is used as value in {@code /BkToCstmrDbtCdtNtfctn/Ntfctn/Ntry/NtryDtls/TxDtls/RmtInf/Strd/CdtrRefInf/Tp/CdOrPrtry/Prtry} to indicate that the references given in {@code CdtrRefInf} is an ESR number.
	 */
	private static final String ISR_REFERENCE = "ISR Reference";

	private static final String MSG_BANK_ACCOUNT_MISMATCH_2P = "ESR_CAMT54_BankAccountMismatch";

	private static final transient Logger logger = LogManager.getLogger(ESRDataImporterCamt54.class);

	private final I_ESR_Import header;
	private final InputStream input;

	public ESRDataImporterCamt54(@NonNull final I_ESR_Import header, @NonNull final InputStream input)
	{
		this.input = input;
		this.header = header;
	}

	@Override
	public ESRStatement importData()
	{
		final BankToCustomerDebitCreditNotificationV06 bkToCstmrDbtCdtNtfctn = loadXML();

		if (bkToCstmrDbtCdtNtfctn.getGrpHdr() != null && bkToCstmrDbtCdtNtfctn.getGrpHdr().getAddtlInf() != null)
		{
			Loggables.get().withLogger(logger, Level.INFO).addLog("The given input is a test file: bkToCstmrDbtCdtNtfctn/grpHdr/addtlInf={}", bkToCstmrDbtCdtNtfctn.getGrpHdr().getAddtlInf());
		}

		try (final IAutoCloseable switchContext = Env.switchContext(InterfaceWrapperHelper.getCtx(header, true)))
		{
			return createESRStatement(bkToCstmrDbtCdtNtfctn);
		}
	}

	private ESRStatement createESRStatement(@NonNull final BankToCustomerDebitCreditNotificationV06 bkToCstmrDbtCdtNtfctn)
	{
		final ESRStatementBuilder stmtBuilder = ESRStatement.builder();

		BigDecimal ctrAmount = BigDecimal.ZERO;
		BigDecimal ctrlQty = null; // start with null (not zero) because in the camt.54 file there just might be no information provided at all

		for (final AccountNotification12 ntfctn : bkToCstmrDbtCdtNtfctn.getNtfctn())
		{
			for (final ReportEntry8 ntry : ntfctn.getNtry()) // gh #1947: there can be many ntry records
			{
				ctrAmount = ctrAmount.add(ntry.getAmt().getValue());
				ctrlQty = iterateEntryDetails(stmtBuilder, ctrlQty, ntry);

			} // for ntry
		} // ntfctn
		return stmtBuilder
				.ctrlAmount(ctrAmount)
				.ctrlQty(ctrlQty)
				.build();
	}

	/**
	 * 
	 * @param stmtBuilder builder to which the individual {@link ESRTransaction}s are added.
	 * @param ctrlQty
	 * @param ntry
	 * @return the given {@code ctrlQty}, plus the <code>NbOfTxs</code> of the given {@code ntry}'s {@code ntryDtl}s (if any).
	 */
	private BigDecimal iterateEntryDetails(
			@NonNull final ESRStatementBuilder stmtBuilder,
			@Nullable final BigDecimal ctrlQty,
			@NonNull final ReportEntry8 ntry)
	{
		BigDecimal newCtrlQty = ctrlQty;
		for (final EntryDetails7 ntryDtl : ntry.getNtryDtls())
		{
			if (ntryDtl.getBtch() != null && ntryDtl.getBtch().getNbOfTxs() != null)
			{
				final BigDecimal augend = new BigDecimal(ntryDtl.getBtch().getNbOfTxs());
				newCtrlQty = (newCtrlQty == null) ? augend : newCtrlQty.add(augend);
			}

			final List<ESRTransaction> transactions = iterateTransactionDetails(ntry, ntryDtl);
			stmtBuilder.transactions(transactions);

		} // ntryDtl

		return newCtrlQty;
	}

	private List<ESRTransaction> iterateTransactionDetails(
			@NonNull final ReportEntry8 ntry,
			@NonNull final EntryDetails7 ntryDtl)
	{
		List<ESRTransaction> transactions = new ArrayList<>();

		for (final EntryTransaction8 txDtls : ntryDtl.getTxDtls())
		{
			final ESRTransactionBuilder trxBuilder = ESRTransaction.builder();

			extractAndSetEsrReference(txDtls, trxBuilder);

			verifyTransactionCurrency(txDtls, trxBuilder);

			extractAmountAndType(ntry, txDtls, trxBuilder);

			final ESRTransaction esrTransaction = trxBuilder
					.accountingDate(asTimestamp(ntry.getBookgDt()))
					.paymentDate(asTimestamp(ntry.getValDt()))
					.esrParticipantNo(ntry.getNtryRef())
					.transactionKey(mkTrxKey(txDtls))
					.build();
			transactions.add(esrTransaction);
		}
		return transactions;
	}

	private void extractAmountAndType(
			@NonNull final ReportEntry8 ntry,
			@NonNull final EntryTransaction8 txDtls,
			@NonNull final ESRTransactionBuilder trxBuilder)
	{

		// credit-or-debit indicator
		if (txDtls.getCdtDbtInd() == CreditDebitCode.CRDT)
		{
			final ActiveOrHistoricCurrencyAndAmount transactionDetailAmt = txDtls.getAmt();
			if (ntry.isRvslInd() != null && ntry.isRvslInd())
			{
				trxBuilder
						.trxType(ESRConstants.ESRTRXTYPE_ReverseBooking)
						.amount(transactionDetailAmt.getValue().negate());
			}
			else
			{
				trxBuilder
						.trxType(ESRConstants.ESRTRXTYPE_CreditMemo)
						.amount(transactionDetailAmt.getValue());
			}
		}
		else
		{
			// we get charged; currently not supported
			final IMsgBL msgBL = Services.get(IMsgBL.class);
			trxBuilder
					.errorMsg(msgBL.getMsg(Env.getCtx(), MSG_UNSUPPORTED_CREDIT_DEBIT_CODE_1P, new Object[] { ntry.getCdtDbtInd() }))
					.trxType("???")
					.amount(null);
		}
	}

	/**
	 * Verifies that the currency is consistent.
	 * 
	 * @param txDtls
	 * @param trxBuilder
	 */
	private void verifyTransactionCurrency(
			@NonNull final EntryTransaction8 txDtls,
			@NonNull final ESRTransactionBuilder trxBuilder)
	{
		if (header.getC_BP_BankAccount_ID() <= 0)
		{
			return; // nothing to do
		}

		// TODO: this does not really belong into the loader! move it to the matcher code.
		final ActiveOrHistoricCurrencyAndAmount transactionDetailAmt = txDtls.getAmt();

		final String headerCurrencyISO = header.getC_BP_BankAccount().getC_Currency().getISO_Code();
		if (!headerCurrencyISO.equalsIgnoreCase(transactionDetailAmt.getCcy()))
		{
			final IMsgBL msgBL = Services.get(IMsgBL.class);
			trxBuilder.errorMsg(msgBL.getMsg(Env.getCtx(), MSG_BANK_ACCOUNT_MISMATCH_2P,
					new Object[] { headerCurrencyISO, transactionDetailAmt.getCcy() }));
		}
	}

	private void extractAndSetEsrReference(
			@NonNull final EntryTransaction8 txDtls,
			@NonNull final ESRTransactionBuilder trxBuilder)
	{
		final IMsgBL msgBL = Services.get(IMsgBL.class);

		final Optional<String> esrReferenceNumberString = extractEsrReference(txDtls);
		if (esrReferenceNumberString.isPresent())
		{
			trxBuilder.esrReferenceNumber(esrReferenceNumberString.get());
		}
		else
		{
			final Optional<String> fallback = extractReferenceFallback(txDtls);
			if (fallback.isPresent())
			{
				trxBuilder.esrReferenceNumber(fallback.get());
				trxBuilder.errorMsg(msgBL.getMsg(Env.getCtx(), MSG_AMBIGOUS_REFERENCE));
			}
			else
			{
				trxBuilder.errorMsg(msgBL.getMsg(Env.getCtx(), MSG_MISSING_ESR_REFERENCE));
			}
		}
	}

	/**
	 * Gets <code>TxDtls/RmtInf/Strd/CdtrRefInf/Ref</code><br>
	 * from a <code>CdtrRefInf</code> element<br>
	 * that has <code>CdtrRefInf/Tp/CdOrPrtry == "ISR Reference"</code>.
	 * 
	 * @param txDtls
	 * @return
	 * 
	 * @task https://github.com/metasfresh/metasfresh/issues/2107
	 */
	private Optional<String> extractEsrReference(@NonNull final EntryTransaction8 txDtls)
	{
		// get the esr reference string out of the XML tree
		final Optional<String> esrReferenceNumberString = txDtls.getRmtInf().getStrd().stream()
				.map(strd -> strd.getCdtrRefInf())

				// it's stored in the cdtrRefInf records whose cdtrRefInf/tp/cdOrPrtry/prtr equals to ISR_REFERENCE
				.filter(cdtrRefInf -> cdtrRefInf != null
						&& cdtrRefInf.getTp() != null
						&& cdtrRefInf.getTp().getCdOrPrtry() != null
						&& cdtrRefInf.getTp().getCdOrPrtry().getPrtry().equals(ISR_REFERENCE))

				.map(cdtrRefInf -> cdtrRefInf.getRef())
				.findFirst();
		return esrReferenceNumberString;
	}

	/**
	 * 
	 * @param txDtls
	 * @return
	 * 
	 * @task https://github.com/metasfresh/metasfresh/issues/2107
	 */
	private Optional<String> extractReferenceFallback(@NonNull final EntryTransaction8 txDtls)
	{
		// get the esr reference string out of the XML tree
		final Optional<String> esrReferenceNumberString = txDtls.getRmtInf().getStrd().stream()
				.map(strd -> strd.getCdtrRefInf())
				.filter(cdtrRefInf -> cdtrRefInf != null)
				.map(cdtrRefInf -> cdtrRefInf.getRef())
				.findFirst();
		return esrReferenceNumberString;
	}

	private BankToCustomerDebitCreditNotificationV06 loadXML()
	{
		final Document document;
		try
		{
			final XMLInputFactory xif = XMLInputFactory.newInstance();
			final XMLStreamReader xsr = xif.createXMLStreamReader(input);

			final JAXBContext context = JAXBContext.newInstance(Document.class);
			final Unmarshaller unmarshaller = context.createUnmarshaller();

			// https://github.com/metasfresh/metasfresh/issues/1903
			// use a delegate to make sure that the unmarshaller won't refuse camt.054.001.04 and amt.054.001.05
			@SuppressWarnings("unchecked")
			final JAXBElement<Document> e = (JAXBElement<Document>)unmarshaller.unmarshal(new MyStreamReaderDelegate(xsr));

			document = e.getValue();
		}
		catch (final JAXBException | XMLStreamException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}

		final BankToCustomerDebitCreditNotificationV06 bkToCstmrDbtCdtNtfctn = document.getBkToCstmrDbtCdtNtfctn();
		return bkToCstmrDbtCdtNtfctn;
	}

	/**
	 * Thanks to <a href="https://stackoverflow.com/a/6747695/1012103">https://stackoverflow.com/a/6747695/1012103</a>
	 *
	 * @author metas-dev <dev@metasfresh.com>
	 * @task https://github.com/metasfresh/metasfresh/issues/1903
	 *
	 */
	private static class MyStreamReaderDelegate extends StreamReaderDelegate
	{
		public MyStreamReaderDelegate(@NonNull final XMLStreamReader xsr)
		{
			super(xsr);
		}

		/**
		 * If the actual namespace is <code>urn:iso:std:iso:20022:tech:xsd:camt.054.001.04</code> or <code>...001.05</code>, then this method returns <code>...001.06</code> instead,
		 * because it works for the XML we process here, and it allows us to also process older XML files.
		 *
		 * @task https://github.com/metasfresh/metasfresh/issues/1903
		 */
		@Override
		public String getNamespaceURI()
		{
			final String namespaceURI = super.getNamespaceURI();
			if (Objects.equal("urn:iso:std:iso:20022:tech:xsd:camt.054.001.04", namespaceURI)
					|| Objects.equal("urn:iso:std:iso:20022:tech:xsd:camt.054.001.05", namespaceURI))
			{
				// listing those two URNs that we replace is not elegant, but simple & easy. that's why I do it.
				return "urn:iso:std:iso:20022:tech:xsd:camt.054.001.06";
			}

			return namespaceURI;
		}

	}

	private String mkTrxKey(@NonNull final EntryTransaction8 txDtls)
	{
		final ByteArrayOutputStream transactionKey = new ByteArrayOutputStream();
		JAXB.marshal(txDtls, transactionKey);
		try
		{
			return transactionKey.toString("UTF-8");
		}
		catch (UnsupportedEncodingException e)
		{
			// won't happen because UTF-8 is supported
			throw AdempiereException.wrapIfNeeded(e);
		}
	}

	private Timestamp asTimestamp(@NonNull final DateAndDateTimeChoice valDt)
	{
		return new Timestamp(valDt.getDt().toGregorianCalendar().getTimeInMillis());
	}

}
