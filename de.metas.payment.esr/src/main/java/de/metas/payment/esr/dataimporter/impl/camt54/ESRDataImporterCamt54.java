package de.metas.payment.esr.dataimporter.impl.camt54;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Optional;

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
import org.adempiere.util.Check;
import org.adempiere.util.Loggables;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.base.Objects;

import ch.qos.logback.classic.Level;
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

	/**
	 * This constant is used as value in {@code /BkToCstmrDbtCdtNtfctn/Ntfctn/Ntry/NtryDtls/TxDtls/RmtInf/Strd/CdtrRefInf/Tp/CdOrPrtry/Prtry} to indicate that the references given in {@code CdtrRefInf} is an ESR number.
	 */
	public static final String ISR_REFERENCE = "ISR Reference";

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
		if (bkToCstmrDbtCdtNtfctn.getGrpHdr() != null && bkToCstmrDbtCdtNtfctn.getGrpHdr().getAddtlInf() != null)
		{
			Loggables.get().withLogger(logger, Level.INFO).addLog("The given input is a test file: bkToCstmrDbtCdtNtfctn/grpHdr/addtlInf={}", bkToCstmrDbtCdtNtfctn.getGrpHdr().getAddtlInf());
		}

		try (final IAutoCloseable switchContext = Env.switchContext(InterfaceWrapperHelper.getCtx(header, true)))
		{
			final ESRStatementBuilder stmtBuilder = ESRStatement.builder();

			if (bkToCstmrDbtCdtNtfctn.getNtfctn().isEmpty())
			{
				return stmtBuilder.ctrlAmount(BigDecimal.ZERO).ctrlQty(BigDecimal.ZERO).build();
			}
			final AccountNotification12 ntfctn = bkToCstmrDbtCdtNtfctn.getNtfctn().get(0);

			if (ntfctn.getNtry().isEmpty())
			{
				return stmtBuilder.ctrlAmount(BigDecimal.ZERO).ctrlQty(BigDecimal.ZERO).build();
			}
			final ReportEntry8 ntry = ntfctn.getNtry().get(0);

			final BigDecimal ctrAmount = ntry.getAmt().getValue();

			if (ntry.getNtryDtls().isEmpty())
			{
				return stmtBuilder.ctrlAmount(ctrAmount).ctrlQty(BigDecimal.ZERO).build();
			}
			final EntryDetails7 ntryDtl = ntry.getNtryDtls().get(0);

			final BigDecimal ctrlQty = new BigDecimal(ntryDtl.getBtch().getNbOfTxs());

			for (final EntryTransaction8 txDtls : ntryDtl.getTxDtls())
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
				Check.errorUnless(esrReferenceNumberString.isPresent(), "Missing ESR creditor reference");

				final ActiveOrHistoricCurrencyAndAmount transactionDetailAmt = txDtls.getAmt();
				final BigDecimal amountValue;

				// verify that the currency is consistent
				// TODO: this does not really belong into the loader! move it to the matcher code.
				if (header.getC_BP_BankAccount_ID() > 0)
				{
					final String headerCurrencyISO = header.getC_BP_BankAccount().getC_Currency().getISO_Code();
					Check.errorUnless(headerCurrencyISO.equalsIgnoreCase(transactionDetailAmt.getCcy()),
							"Currency {} of C_BP_BankAccount does not match the currency {} of the current xml ntry; C_BP_BankAccount={}",
							headerCurrencyISO, transactionDetailAmt.getCcy(), header.getC_BP_BankAccount());
				}
				// credit-or-debit indicator
				final String trxType;
				if (txDtls.getCdtDbtInd() == CreditDebitCode.CRDT)
				{
					if (ntry.isRvslInd() != null && ntry.isRvslInd())
					{
						trxType = ESRConstants.ESRTRXTYPE_ReverseBooking;
						amountValue = transactionDetailAmt.getValue().negate();
					}
					else
					{
						trxType = ESRConstants.ESRTRXTYPE_CreditMemo;
						amountValue = transactionDetailAmt.getValue();
					}
				}
				else
				{
					// we get charged; currently not supported
					Check.errorIf(true, "Unsupported cdtDbtInd={}", ntry.getCdtDbtInd());
					trxType = "???";
					amountValue = null;
				}

				stmtBuilder.transaction(ESRTransaction.builder()
						.trxType(trxType)
						.transactionKey(mkTrxKey(ntry))
						.amount(amountValue)
						.accountingDate(asTimestamp(ntry.getBookgDt()))
						.paymentDate(asTimestamp(ntry.getValDt()))
						.esrParticipantNo(ntry.getNtryRef())
						.esrReferenceNumber(esrReferenceNumberString.get())
						.build());
			}

			return stmtBuilder
					.ctrlAmount(ctrAmount)
					.ctrlQty(ctrlQty)
					.build();
		}

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

	private String mkTrxKey(@NonNull final ReportEntry8 ntry)
	{
		final ByteArrayOutputStream transactionKey = new ByteArrayOutputStream();
		JAXB.marshal(ntry, transactionKey);
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
