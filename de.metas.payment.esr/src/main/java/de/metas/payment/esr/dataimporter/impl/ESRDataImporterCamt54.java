package de.metas.payment.esr.dataimporter.impl;

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

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Loggables;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.util.Env;
import org.slf4j.Logger;

import ch.qos.logback.classic.Level;
import de.metas.logging.LogManager;
import de.metas.payment.camt054.jaxb.AccountNotification12;
import de.metas.payment.camt054.jaxb.ActiveOrHistoricCurrencyAndAmount;
import de.metas.payment.camt054.jaxb.BankToCustomerDebitCreditNotificationV06;
import de.metas.payment.camt054.jaxb.CreditDebitCode;
import de.metas.payment.camt054.jaxb.DateAndDateTimeChoice;
import de.metas.payment.camt054.jaxb.Document;
import de.metas.payment.camt054.jaxb.ReportEntry8;
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
 * Creates an {@link ESRStatement} from camt.54 XML data.
 * <p>
 * <b>Important:</b> currently the control quantity and control amount are not taken from the XML data, but simply summed up from the transactions imported by this implementation!
 *
 * The XSD file for the xml this implementation imports is available at <a href="https://www.iso20022.org/documents/messages/camt/schemas/camt.054.001.04.zip">BankToCustomerDebitCreditNotificationV04</a>.
 *
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
			final JAXBContext context = JAXBContext.newInstance(Document.class);
			final Unmarshaller unmarshaller = context.createUnmarshaller();

			@SuppressWarnings("unchecked")
			final JAXBElement<Document> e = (JAXBElement<Document>)unmarshaller.unmarshal(input);
			document = e.getValue();
		}
		catch (final JAXBException e)
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

			// https://github.com/metasfresh/metasfresh/issues/1873
			// get the values; if possible, read them from the XML in future
			BigDecimal ctrAmount = BigDecimal.ZERO;
			BigDecimal ctrlQty = BigDecimal.ZERO;

			for (final AccountNotification12 ntfctn : bkToCstmrDbtCdtNtfctn.getNtfctn())
			{
				for (final ReportEntry8 ntry : ntfctn.getNtry())
				{
					final ActiveOrHistoricCurrencyAndAmount amt = ntry.getAmt();
					final BigDecimal amountValue;

					// verify that the currency is consistent
					// TODO: this does not really belong into the loader! move it to the matcher code.
					if (header.getC_BP_BankAccount_ID() > 0)
					{
						final String headerCurrencyISO = header.getC_BP_BankAccount().getC_Currency().getISO_Code();
						Check.errorUnless(headerCurrencyISO.equalsIgnoreCase(amt.getCcy()),
								"Currency {} of C_BP_BankAccount does not match the currency {} of the current xml ntry; C_BP_BankAccount={}",
								headerCurrencyISO, amt.getCcy(), header.getC_BP_BankAccount());
					}
					// credit-or-debit indicator
					final String trxType;
					if (ntry.getCdtDbtInd() == CreditDebitCode.CRDT)
					{
						if (ntry.isRvslInd() != null && ntry.isRvslInd())
						{
							trxType = ESRConstants.ESRTRXTYPE_ReverseBooking;
							amountValue = amt.getValue().negate();
						}
						else
						{
							trxType = ESRConstants.ESRTRXTYPE_CreditMemo;
							amountValue = amt.getValue();
						}
					}
					else
					{
						// we get charged currently not supported
						Check.errorIf(true, "Unsupported cdtDbtInd={}", ntry.getCdtDbtInd());
						trxType = "???";
						amountValue = null;
					}

					// get the esr reference string out of the XML tree
					final Optional<String> esrReferenceNumberString = ntry.getNtryDtls().stream()
							.flatMap(ntryDtl -> ntryDtl.getTxDtls().stream())
							.flatMap(txDtl -> txDtl.getRmtInf().getStrd().stream())
							.map(strd -> strd.getCdtrRefInf())

							// it's stored in the cdtrRefInf records whose cdtrRefInf/tp/cdOrPrtry/prtr equals to ISR_REFERENCE
							.filter(cdtrRefInf -> cdtrRefInf != null
									&& cdtrRefInf.getTp() != null
									&& cdtrRefInf.getTp().getCdOrPrtry() != null
									&& cdtrRefInf.getTp().getCdOrPrtry().getPrtry().equals(ISR_REFERENCE))

							.map(cdtrRefInf -> cdtrRefInf.getRef())
							.findFirst();
					Check.errorUnless(esrReferenceNumberString.isPresent(), "Missing ESR creditor reference");

					stmtBuilder.transaction(ESRTransaction.builder()
							.trxType(trxType)
							.transactionKey(mkTrxKey(ntry))
							.amount(amountValue)
							.accountingDate(asTimestamp(ntry.getBookgDt()))
							.paymentDate(asTimestamp(ntry.getValDt()))
							.esrParticipantNo(ntry.getNtryRef())
							.esrReferenceNumber(esrReferenceNumberString.get())
							.build());

					// https://github.com/metasfresh/metasfresh/issues/1873
					// for now, just sum up the values because it's not clear if and where they are stored in the XML
					ctrAmount = ctrAmount.add(amountValue);
					ctrlQty = ctrlQty.add(BigDecimal.ONE);
				}
			}
			return stmtBuilder
					.ctrlAmount(ctrAmount)
					.ctrlQty(ctrlQty)
					.build();
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
