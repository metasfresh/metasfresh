package de.metas.payment.esr.dataimporter.impl.v11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Date;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;
import de.metas.payment.esr.dataimporter.ESRStatement;
import de.metas.payment.esr.dataimporter.ESRStatement.ESRStatementBuilder;
import de.metas.payment.esr.dataimporter.ESRTransaction;
import de.metas.payment.esr.dataimporter.ESRTransaction.ESRTransactionBuilder;
import de.metas.payment.esr.dataimporter.ESRType;
import de.metas.payment.esr.dataimporter.IESRDataImporter;
import de.metas.payment.esr.model.X_ESR_ImportLine;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.PlainStringLoggable;
import de.metas.util.Services;
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
 * Data importer for line-based v11 ESR files.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class ESRDataImporterV11 implements IESRDataImporter
{
	public final static  AdMessageKey ERR_WRONG_CTRL_LINE_LENGTH =  AdMessageKey.of("ESR_Wrong_Ctrl_Line_Length");

	private static final transient Logger logger = LogManager.getLogger(ESRDataImporterV11.class);

	private final InputStream input;

	public ESRDataImporterV11(@NonNull final InputStream input)
	{
		this.input = input;
	}

	@Override
	public ESRStatement importData()
	{
		final InputStreamReader inputStreamReader = new InputStreamReader(input);

		try (final BufferedReader reader = new BufferedReader(inputStreamReader))
		{
			final ESRStatementBuilder builder = ESRStatement.builder();

			BigDecimal importAmt = BigDecimal.ZERO;

			int controlLineCount = 0;

			String currentTextLine;
			while ((currentTextLine = reader.readLine()) != null)
			{
				if (Check.isEmpty(currentTextLine, true))
				{
					logger.debug("Skip empty currentTextLine");
					continue;
				}

				final String trimmedtextLine = currentTextLine.trim();

				if (ESRReceiptLineMatcherUtil.isReceiptLine(trimmedtextLine))
				{
					if (controlLineCount > 0)
					{
						builder.errorMsg("More than one control line found");
						continue;
					}
					if (ESRReceiptLineMatcherUtil.isReceiptLineWithWrongLength(trimmedtextLine))
					{
						builder.errorMsg(Services.get(IMsgBL.class).getMsg(Env.getCtx(), ERR_WRONG_CTRL_LINE_LENGTH, new Object[] { trimmedtextLine.length() }));
						continue;
					}

					final BigDecimal ctrlAmount = ESRReceiptLineMatcherUtil.extractCtrlAmount(trimmedtextLine);
					builder.ctrlAmount(ctrlAmount);

					final BigDecimal ctrlQty = ESRReceiptLineMatcherUtil.extractCtrlQty(trimmedtextLine);
					builder.ctrlQty(ctrlQty);

					controlLineCount++;
				}
				else if (!ESRTransactionLineMatcherUtil.isControlLine(trimmedtextLine))
				{
					final ESRTransaction trx = createTransaction(trimmedtextLine);
					importAmt = importAmt.add(trx.getAmountNotNull());

					builder.transaction(trx);
				}
			}

			if (controlLineCount < 1)
			{
				Loggables.addLog("No control lines found");
			}

			return builder
					.build();
		}
		catch (final IOException e)
		{
			throw new AdempiereException(e.getLocalizedMessage(), e);
		}

	}

	private ESRTransaction createTransaction(final String currentTextLine)
	{
		final ESRTransactionBuilder esrTransactionBuilder = ESRTransaction.builder();

		final PlainStringLoggable errorsLoggable = Loggables.newPlainStringLoggable();
		try (final IAutoCloseable tmpLoggable = Loggables.temporarySetLoggable(errorsLoggable))
		{
			final String esrTrxType = ESRTransactionLineMatcherUtil.extractEsrTrxType(currentTextLine);
			esrTransactionBuilder.trxType(esrTrxType);

			if (ESRTransactionLineMatcherUtil.isCorrectTransactionLineLength(currentTextLine))
			{
				final String postAccountNo = ESRTransactionLineMatcherUtil.extractPostAccountNo(currentTextLine);
				esrTransactionBuilder.esrParticipantNo(postAccountNo);

				final BigDecimal amount = ESRTransactionLineMatcherUtil.extractAmount(currentTextLine);
				esrTransactionBuilder.amount(amount);

				final String referenceNumberStr = ESRTransactionLineMatcherUtil.extractReferenceNumberStr(currentTextLine);
				esrTransactionBuilder.esrReferenceNumber(referenceNumberStr);

				final Date paymentDate = ESRTransactionLineMatcherUtil.extractPaymentDate(currentTextLine);
				esrTransactionBuilder.paymentDate(paymentDate);

				final Date accountingDate = ESRTransactionLineMatcherUtil.extractAccountingDate(currentTextLine);
				esrTransactionBuilder.accountingDate(accountingDate);
				
				esrTransactionBuilder.type(ESRType.TYPE_ESR);
			}
			else
			{
				// if the length is not correct, there is nothing to do here, but errorsLoggable should contain an error message
			}
		}

		esrTransactionBuilder.transactionKey(currentTextLine);
		esrTransactionBuilder.errorMsgs(errorsLoggable.getSingleMessages());

		return esrTransactionBuilder.build();
	}
}
