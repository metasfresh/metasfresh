package de.metas.payment.esr.dataimporter.impl.camt54;

import com.google.common.annotations.VisibleForTesting;
import de.metas.currency.CurrencyCode;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.payment.camt054_001_02.AccountNotification2;
import de.metas.payment.camt054_001_02.ActiveOrHistoricCurrencyAndAmount;
import de.metas.payment.camt054_001_02.AmountAndCurrencyExchange3;
import de.metas.payment.camt054_001_02.AmountAndCurrencyExchangeDetails3;
import de.metas.payment.camt054_001_02.BankToCustomerDebitCreditNotificationV02;
import de.metas.payment.camt054_001_02.CreditDebitCode;
import de.metas.payment.camt054_001_02.DateAndDateTimeChoice;
import de.metas.payment.camt054_001_02.Document;
import de.metas.payment.camt054_001_02.EntryDetails1;
import de.metas.payment.camt054_001_02.EntryTransaction2;
import de.metas.payment.camt054_001_02.ObjectFactory;
import de.metas.payment.camt054_001_02.ReportEntry2;
import de.metas.payment.esr.ESRConstants;
import de.metas.payment.esr.dataimporter.ESRStatement;
import de.metas.payment.esr.dataimporter.ESRStatement.ESRStatementBuilder;
import de.metas.payment.esr.dataimporter.ESRTransaction;
import de.metas.payment.esr.dataimporter.ESRTransaction.ESRTransactionBuilder;
import de.metas.payment.esr.dataimporter.ESRType;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
 * This implementation assumes the incoming XML to comply with the version <b><code>camt.054.001.02</code></b>.
 *
 * <p>
 * Lots of methods are duplicated from <code>ESRDataImporterCamt54v06</code>
 * Important logical differences are in methods:
 * <ul>
 * <li><code>de.metas.payment.esr.dataimporter.impl.camt54.ESRDataImporterCamt54v02.loadXML()/code>
 * <li><code>de.metas.payment.esr.dataimporter.impl.camt54.ESRDataImporterCamt54v02.verifyTransactionCurrency(EntryTransaction8, ESRTransactionBuilder)/code>
 * <li><code>de.metas.payment.esr.dataimporter.impl.camt54.ESRDataImporterCamt54v02.extractAmountAndType(ReportEntry8, EntryTransaction8, ESRTransactionBuilder)</code>
 * </ul>
 * For the rest, the difference is the object generated from xsd
 *
 * <p>
 * The XSD file for the xml which this implementation imports is available at <a href="https://www.iso20022.org/documents/messages/camt/schemas/camt.054.001.02.zip">BankToCustomerDebitCreditNotificationV02</a>.<br>
 * Note that
 * <ul>
 * <li>the latest version of the camt.54 XSD can be found <a href="https://www.iso20022.org/payments_messages.page">here</a> and</li>
 * <li>an archive of all older message specifications messages can be found <a href="https://www.iso20022.org/message_archive.page">here</a>.</li>
 * </ul>
 * <p>
 * Also see <a href="https://www.six-interbank-clearing.com/dam/downloads/en/standardization/iso/swiss-recommendations/implementation-guidelines-camt.pdf">Swiss Implementation Guidelines for Cash Management</a>.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public class ESRDataImporterCamt54v02
{
	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	@Nullable private final CurrencyCode bankAccountCurrencyCode;
	@NonNull private final String adLanguage;

	@Builder
	private ESRDataImporterCamt54v02(
			@Nullable final CurrencyCode bankAccountCurrencyCode,
			@NonNull final String adLanguage)
	{
		this.bankAccountCurrencyCode = bankAccountCurrencyCode;
		this.adLanguage = adLanguage;
	}

	/**
	 * create ESRStatement using <code>BankToCustomerDebitCreditNotificationV02</code>
	 */
	public ESRStatement createESRStatement(@NonNull final BankToCustomerDebitCreditNotificationV02 bkToCstmrDbtCdtNtfctn)
	{
		final ESRStatementBuilder stmtBuilder = ESRStatement.builder();

		BigDecimal ctrAmount = BigDecimal.ZERO;

		BigDecimal ctrlQty = ESRDataImporterCamt54.CTRL_QTY_NOT_YET_SET;

		for (final AccountNotification2 ntfctn : bkToCstmrDbtCdtNtfctn.getNtfctn())
		{
			for (final ReportEntry2 ntry : ntfctn.getNtry()) // gh #1947: there can be many ntry records
			{
				final BigDecimal ntryAmt = ntry.getAmt().getValue()
						.multiply(getCrdDbtMultiplier(ntry.getCdtDbtInd()))
						.multiply(getRvslMultiplier(ntry));

				ctrAmount = ctrAmount.add(ntryAmt);
				ctrlQty = iterateEntryDetails(stmtBuilder, ctrlQty, ntry);
			} // for ntry
		} // ntfctn

		// only use the control qty if all ntry had one set. If one was null, then forward null
		final BigDecimal ctrlQtyForStatement = ctrlQty.compareTo(ESRDataImporterCamt54.CTRL_QTY_AT_LEAST_ONE_NULL) == 0 ? null : ctrlQty;

		return stmtBuilder
				.ctrlAmount(ctrAmount)
				.ctrlQty(ctrlQtyForStatement)
				.build();
	}

	/**
	 * iterateEntryDetails for version 2 <code>BankToCustomerDebitCreditNotificationV02</code>
	 *
	 * @param stmtBuilder builder to which the individual {@link ESRTransaction}s are added.
	 * @return the given {@code ctrlQty}, plus the <code>NbOfTxs</code> of the given {@code ntry}'s {@code ntryDtl}s (if any).
	 */
	@VisibleForTesting
	BigDecimal iterateEntryDetails(
			@NonNull final ESRStatementBuilder stmtBuilder,
			@NonNull final BigDecimal ctrlQty,
			@NonNull final ReportEntry2 ntry)
	{
		BigDecimal newCtrlQty = ctrlQty;
		for (final EntryDetails1 ntryDtl : ntry.getNtryDtls())
		{
			if (ctrlQty.compareTo(ESRDataImporterCamt54.CTRL_QTY_AT_LEAST_ONE_NULL) == 0
					|| ntryDtl.getBtch() == null || ntryDtl.getBtch().getNbOfTxs() == null)
			{
				// the current ntryDtl has no control qty, or an earlier one already didn't have a control qty
				newCtrlQty = ESRDataImporterCamt54.CTRL_QTY_AT_LEAST_ONE_NULL;
			}
			else
			{
				final BigDecimal augend = new BigDecimal(ntryDtl.getBtch().getNbOfTxs());
				if (ctrlQty.compareTo(ESRDataImporterCamt54.CTRL_QTY_NOT_YET_SET) == 0)
				{
					// not yet set
					newCtrlQty = augend;
				}
				else
				{
					newCtrlQty = newCtrlQty.add(augend);
				}
			}

			final List<ESRTransaction> transactions = iterateTransactionDetails(ntry, ntryDtl);
			stmtBuilder.transactions(transactions);

		} // ntryDtl

		return newCtrlQty;
	}

	/**
	 * iterateTransactionDetails for version 2 <code>BankToCustomerDebitCreditNotificationV02</code>
	 */
	private List<ESRTransaction> iterateTransactionDetails(
			@NonNull final ReportEntry2 ntry,
			@NonNull final EntryDetails1 ntryDtl)
	{
		final List<ESRTransaction> transactions = new ArrayList<>();

		int countQRR = 0;
		for (final EntryTransaction2 txDtl : ntryDtl.getTxDtls())
		{
			final ESRTransactionBuilder trxBuilder = ESRTransaction.builder();

			new ReferenceStringHelper().extractAndSetEsrReference(txDtl, trxBuilder);

			new ReferenceStringHelper().extractAndSetType(txDtl, trxBuilder);

			verifyTransactionCurrency(txDtl, trxBuilder);

			extractAmountAndType(ntry, txDtl, trxBuilder);

			final ESRTransaction esrTransaction = trxBuilder
					.accountingDate(asTimestamp(ntry.getBookgDt()))
					.paymentDate(asTimestamp(ntry.getValDt()))
					.esrParticipantNo(ntry.getNtryRef())
					.transactionKey(mkTrxKey(txDtl))
					.build();
			transactions.add(esrTransaction);

			if (ESRType.TYPE_QRR.equals(esrTransaction.getType()))
			{
				countQRR++;
			}
		}

		if (countQRR != 0 && countQRR != transactions.size())
		{
			throw new AdempiereException(ESRDataImporterCamt54.MSG_MULTIPLE_TRANSACTIONS_TYPES);
		}

		return transactions;
	}

	/**
	 * extractAmountAndType for version 2 <code>BankToCustomerDebitCreditNotificationV02</code>
	 */
	private void extractAmountAndType(
			@NonNull final ReportEntry2 ntry,
			@NonNull final EntryTransaction2 txDtls,
			@NonNull final ESRTransactionBuilder trxBuilder)
	{
		// credit-or-debit indicator

		final AmountAndCurrencyExchange3 transactionDetailAmt = txDtls.getAmtDtls();
		final AmountAndCurrencyExchangeDetails3 transactionInstdAmt = transactionDetailAmt.getInstdAmt();
		final ActiveOrHistoricCurrencyAndAmount amt = transactionInstdAmt.getAmt();

		final BigDecimal amount = amt.getValue()
				.multiply(getCrdDbtMultiplier(ntry.getCdtDbtInd()))
				.multiply(getRvslMultiplier(ntry));
		trxBuilder.amount(amount);

		if (ntry.getCdtDbtInd() == CreditDebitCode.CRDT)
		{
			if (isReversal(ntry))
			{
				trxBuilder.trxType(ESRConstants.ESRTRXTYPE_ReverseBooking);
			}
			else
			{
				trxBuilder.trxType(ESRConstants.ESRTRXTYPE_CreditMemo);
			}
		}
		else
		{
			// we get charged; currently not supported
			trxBuilder.trxType(ESRConstants.ESRTRXTYPE_UNKNOWN)
					.errorMsg(getErrorMsg(ESRDataImporterCamt54.MSG_UNSUPPORTED_CREDIT_DEBIT_CODE_1P, ntry.getCdtDbtInd()));
		}
	}

	/**
	 * getCrdDbtMultiplier for version 2 <code>BankToCustomerDebitCreditNotificationV02</code>
	 */
	private BigDecimal getCrdDbtMultiplier(@NonNull final CreditDebitCode creditDebitCode)
	{
		if (creditDebitCode == CreditDebitCode.CRDT)
		{
			return BigDecimal.ONE;
		}
		return BigDecimal.ONE.negate();
	}

	/**
	 * getRvslMultiplier for version 2 <code>BankToCustomerDebitCreditNotificationV02</code>
	 */
	private BigDecimal getRvslMultiplier(@NonNull final ReportEntry2 ntry)
	{
		if (isReversal(ntry))
		{
			return BigDecimal.ONE.negate();
		}
		return BigDecimal.ONE;
	}

	/**
	 * isReversal for version 2 <code>BankToCustomerDebitCreditNotificationV02</code>
	 */
	private boolean isReversal(@NonNull final ReportEntry2 ntry)
	{
		return ntry.isRvslInd() != null && ntry.isRvslInd();
	}

	/**
	 * Verifies that the currency is consistent.
	 * iterateTransactionDetails for version 2 <code>BankToCustomerDebitCreditNotificationV02</code>
	 */
	private void verifyTransactionCurrency(
			@NonNull final EntryTransaction2 txDtls,
			@NonNull final ESRTransactionBuilder trxBuilder)
	{
		if (bankAccountCurrencyCode == null)
		{
			return; // nothing to do
		}

		final AmountAndCurrencyExchange3 transactionDetailAmt = txDtls.getAmtDtls();
		final AmountAndCurrencyExchangeDetails3 transactionInstdAmt = transactionDetailAmt.getInstdAmt();
		final ActiveOrHistoricCurrencyAndAmount amt = transactionInstdAmt.getAmt();

		final String bankAccountCurrencyCode3L = bankAccountCurrencyCode.toThreeLetterCode();
		if (!bankAccountCurrencyCode3L.equalsIgnoreCase(amt.getCcy()))
		{
			trxBuilder.errorMsg(getErrorMsg(ESRDataImporterCamt54.MSG_BANK_ACCOUNT_MISMATCH_2P, bankAccountCurrencyCode3L, amt.getCcy()));
		}
	}

	public static BankToCustomerDebitCreditNotificationV02 loadXML(@NonNull final MultiVersionStreamReaderDelegate xsr)
	{
		final Document document;
		try
		{
			// https://stackoverflow.com/questions/20410202/jaxb-unmarshalling-not-working-expected-elements-are-none
			// use ObjectFactory for creating the context because otherwise unmarshalling will not work
			final JAXBContext context = JAXBContext.newInstance(ObjectFactory.class);
			final Unmarshaller unmarshaller = context.createUnmarshaller();

			@SuppressWarnings("unchecked") final JAXBElement<Document> e = (JAXBElement<Document>)unmarshaller.unmarshal(xsr);
			document = e.getValue();

		}
		catch (final JAXBException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}

		return document.getBkToCstmrDbtCdtNtfctn();
	}

	private String getErrorMsg(@NonNull final AdMessageKey adMessage, @Nullable final Object... params)
	{
		return msgBL.getTranslatableMsgText(adMessage, params).translate(adLanguage);
	}

	/**
	 * Marshals the given {@code} into an XML string and return that as the "key".
	 * mkTrxKey for version 2 <code>BankToCustomerDebitCreditNotificationV02</code>
	 */
	private static String mkTrxKey(@NonNull final EntryTransaction2 txDtl)
	{
		final ByteArrayOutputStream transactionKey = new ByteArrayOutputStream();
		JAXB.marshal(txDtl, transactionKey);
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

	/**
	 * asTimestamp for version 6 <code>BankToCustomerDebitCreditNotificationV06</code>
	 */
	private static Timestamp asTimestamp(@NonNull final DateAndDateTimeChoice valDt)
	{
		return new Timestamp(valDt.getDt().toGregorianCalendar().getTimeInMillis());
	}

}