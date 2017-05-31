package de.metas.payment.esr.dataloader.impl.v11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Date;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Loggables;
import org.adempiere.util.PlainStringLoggable;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.payment.esr.dataloader.ESRStatement;
import de.metas.payment.esr.dataloader.ESRStatement.ESRStatementBuilder;
import de.metas.payment.esr.dataloader.ESRTransaction;
import de.metas.payment.esr.dataloader.ESRTransaction.ESRTransactionBuilder;
import de.metas.payment.esr.dataloader.IESRDataImporter;
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

public class ESRDataImporterV11 implements IESRDataImporter
{
	public final static String ERR_WRONG_CTRL_LINE_LENGTH = "ESR_Wrong_Ctrl_Line_Length";
	
	private static final transient Logger logger = LogManager.getLogger(ESRDataImporterV11.class);
	
	private final InputStream input;

	public ESRDataImporterV11(@NonNull final InputStream input)
	{
		this.input = input;
	}

	@Override
	public ESRStatement load()
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
					if(ESRReceiptLineMatcherUtil.isReceiptLineWithWrongLength(trimmedtextLine))
					{
						builder.errorMsg(Services.get(IMsgBL.class).getMsg(Env.getCtx(), ERR_WRONG_CTRL_LINE_LENGTH, new Object[]
								{ trimmedtextLine.length() }));
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
				Loggables.get().addLog("No control lines found");
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

		final PlainStringLoggable errorsLoggable = new PlainStringLoggable();
		try (final IAutoCloseable tmpLoggable = Loggables.temporarySetLoggable(errorsLoggable))
		{
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
			}
			else
			{
				// if the length is not correct, there is nothing to do here, but errorsLoggable should contain an error message
			}
		}

		esrTransactionBuilder.errorMsgs(errorsLoggable.getSingleMessages());

		return esrTransactionBuilder.build();

	}

	// public void load(@NonNull final I_ESR_Import esrImport, @NonNull final InputStream input)
	// {
	// final IESRImportBL esrImportBL = Services.get(IESRImportBL.class);
	//
	// final InputStreamReader inputStreamReader = new InputStreamReader(input);
	//
	// try (final BufferedReader reader = new BufferedReader(inputStreamReader);)
	// {
	// BigDecimal importAmt = BigDecimal.ZERO;
	// int trxQty = 0;
	// int lineNo = 0;
	// while (reader.ready())
	// {
	// final String currentTextLine = reader.readLine();
	//
	// // task 06281: skipping empty lines
	// if (Check.isEmpty(currentTextLine))
	// {
	// logger.debug("Skip empty currentTextLine");
	// continue;
	// }
	//
	// // for row number
	// lineNo++;
	//
	// final I_ESR_ImportLine line = createESRImportLine(esrImport, currentTextLine, lineNo);
	//
	// if (esrImportBL.isControlLine(line))
	// {
	// // The control lines do not contain relevant information about the bank account
	// logger.debug("Skip empty currentTextLine");
	// continue;
	// }
	// else
	// {
	// importAmt = importAmt.add(line.getAmount());
	// trxQty++;
	// }
	//
	// }
	//
	// final boolean hasLines = lineNo > 0;
	//
	// final boolean fitAmounts = importAmt.compareTo(esrImport.getESR_Control_Amount()) == 0;
	//
	// final boolean fitTrxQtys = new BigDecimal(trxQty).compareTo(esrImport.getESR_Control_Trx_Qty()) == 0;
	//
	// esrImport.setIsValid(hasLines && fitAmounts && fitTrxQtys);
	//
	// Check.assume(hasLines, "ESR Document has lines");
	//
	// Check.assume(fitAmounts, "The calculated amount for lines ("
	// + importAmt
	// + ") does not fit the control amount ("
	// + esrImport.getESR_Control_Amount()
	// + "). The document will not be processed.");
	//
	// Check.assume(fitTrxQtys, "The counted transactions ("
	// + trxQty
	// + ") do not fit the control transaction quantities ("
	// + esrImport.getESR_Control_Trx_Qty()
	// + "). The document will not be processed.");
	// }
	// catch (final IOException e)
	// {
	// throw new AdempiereException(e.getLocalizedMessage(), e);
	// }
	// catch (final AdempiereException e)
	// {
	// // if there is an an assumption error, catch it to add a message and the release it
	// final String message = e.getMessage();
	// if (message.startsWith("Assumption failure:"))
	// {
	// esrImport.setDescription(esrImport.getDescription() + " > Fehler: Es ist ein Fehler beim Import aufgetreten! " + e.getLocalizedMessage());
	// InterfaceWrapperHelper.save(esrImport, ITrx.TRXNAME_None); // out of transaction: we want to not be rollback
	// }
	//
	// throw new AdempiereException(e.getLocalizedMessage(), e);
	// }
	// }

	// /**
	// * This method creates an {@link I_ESR_ImportLine} and invokes {@link IESRImportBL#matchESRImportLine(I_ESR_ImportLine, ITrxRunConfig)} which also saves it.
	// *
	// * @param esrImport
	// * @param esrImportLineText
	// * @param rowNumber
	// * @return
	// */
	// private I_ESR_ImportLine createESRImportLine(
	// @NonNull final I_ESR_Import esrImport,
	// @NonNull final String esrImportLineText,
	// final int rowNumber)
	// {
	// final String trxName = InterfaceWrapperHelper.getTrxName(esrImport);
	//
	// final Mutable<I_ESR_ImportLine> importLine = new Mutable<>();
	//
	// final ITrxRunConfig trxRunConfig = Services.get(ITrxManager.class).newTrxRunConfigBuilder()
	// .setTrxPropagation(TrxPropagation.NESTED)
	// .setOnRunnableSuccess(OnRunnableSuccess.COMMIT)
	// .setOnRunnableFail(OnRunnableFail.ASK_RUNNABLE)
	// .build();
	//
	// // 04582: create each line within its own TrxRunner, so that (depending on the trxRunConfig), it will be committed and thus release its locks
	// Services.get(ITrxManager.class).run(trxName, trxRunConfig, new TrxRunnable()
	// {
	// @Override
	// public void run(final String localTrxName) throws Exception
	// {
	// final I_ESR_ImportLine newLine = ESRDataLoaderUtil.newLine(esrImport);
	//
	// newLine.setLineNo(rowNumber);
	// newLine.setESRLineText(esrImportLineText); // the whole line text
	// importLine.setValue(newLine);
	// }
	// });
	//
	// ESRLineMatcherUtil.matchESRImportLine(importLine.getValue(), trxRunConfig);
	//
	// return importLine.getValue();
	// }
}
