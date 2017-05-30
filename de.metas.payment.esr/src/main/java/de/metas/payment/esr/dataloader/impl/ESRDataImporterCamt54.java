package de.metas.payment.esr.dataloader.impl;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Optional;

import javax.xml.bind.JAXB;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.payment.camt054.jaxb.AccountNotification12;
import de.metas.payment.camt054.jaxb.ActiveOrHistoricCurrencyAndAmount;
import de.metas.payment.camt054.jaxb.BankToCustomerDebitCreditNotificationV06;
import de.metas.payment.camt054.jaxb.CreditDebitCode;
import de.metas.payment.camt054.jaxb.DateAndDateTimeChoice;
import de.metas.payment.camt054.jaxb.Document;
import de.metas.payment.camt054.jaxb.ReportEntry8;
import de.metas.payment.esr.dataloader.ESRStatement;
import de.metas.payment.esr.dataloader.ESRStatement.ESRStatementBuilder;
import de.metas.payment.esr.dataloader.ESRTransaction;
import de.metas.payment.esr.dataloader.IESRDataImporter;
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
	public ESRStatement load()
	{
		final Document document = JAXB.unmarshal(input, Document.class);

		final BankToCustomerDebitCreditNotificationV06 bkToCstmrDbtCdtNtfctn = document.getBkToCstmrDbtCdtNtfctn();
		if (bkToCstmrDbtCdtNtfctn.getGrpHdr() != null && bkToCstmrDbtCdtNtfctn.getGrpHdr().getAddtlInf() != null)
		{
			logger.info("The given input is a test file: bkToCstmrDbtCdtNtfctn/grpHdr/addtlInf={}", bkToCstmrDbtCdtNtfctn.getGrpHdr().getAddtlInf());
		}

		try (final IAutoCloseable switchContext = Env.switchContext(InterfaceWrapperHelper.getCtx(header, true)))
		{
			final ESRStatementBuilder stmtBuilder = ESRStatement.builder();
			for (final AccountNotification12 ntfctn : bkToCstmrDbtCdtNtfctn.getNtfctn())
			{
				for (final ReportEntry8 ntry : ntfctn.getNtry())
				{
					final ActiveOrHistoricCurrencyAndAmount amt = ntry.getAmt();

					// verify that the currency is consistent
					if (header.getC_BP_BankAccount_ID() > 0)
					{
						final String headerCurrencyISO = header.getC_BP_BankAccount().getC_Currency().getISO_Code();
						Check.errorIf(headerCurrencyISO.equalsIgnoreCase(amt.getCcy()),
								"Currency {} of C_BP_BankAccount does not match the currency {} of the current xml ntry; C_BP_BankAccount={}",
								headerCurrencyISO, amt.getCcy(), header.getC_BP_BankAccount());
					}
					// credit-or-debit indicator
					if (ntry.getCdtDbtInd() == CreditDebitCode.CRDT)
					{
						// we get money. this is the usual case
					}
					else
					{
						// we get charged currently not supported
						Check.errorIf(true, "Unsupported cdtDbtInd={}", ntry.getCdtDbtInd());
					}

					final Optional<String> esrReferenceNumberString = ntry.getNtryDtls().stream()
							.flatMap(ntryDtl -> ntryDtl.getTxDtls().stream())
							.flatMap(txDtl -> txDtl.getRmtInf().getStrd().stream())
							.map(strd -> strd.getCdtrRefInf())
							.filter(cdtrRefInf -> cdtrRefInf != null
									&& cdtrRefInf.getTp() != null
									&& cdtrRefInf.getTp().getCdOrPrtry() != null
									&& cdtrRefInf.getTp().getCdOrPrtry().getPrtry().equals(ISR_REFERENCE))
							.map(cdtrRefInf -> cdtrRefInf.getRef())
							.findFirst();
					Check.errorUnless(esrReferenceNumberString.isPresent(), "Missing ESR creditor reference");

					stmtBuilder.transaction(ESRTransaction.builder()
							.amount(amt.getValue())
							.accountingDate(asTimestamp(ntry.getBookgDt()))
							.paymentDate(asTimestamp(ntry.getValDt()))
							.esrParticipantNo(ntry.getNtryRef())
							.esrReferenceNumber(esrReferenceNumberString.get())
							.build());
				}
			}
			return stmtBuilder.build();
		}

	}

	private Timestamp asTimestamp(final DateAndDateTimeChoice valDt)
	{
		return new Timestamp(valDt.getDt().toGregorianCalendar().getTimeInMillis());
	}

}
