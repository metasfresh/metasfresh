package de.metas.payment.esr.api.impl;

/*
 * #%L
 * de.metas.payment.esr
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.ITrxRunConfig;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.invoice.service.IInvoiceDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.IAttachmentBL;
import org.adempiere.service.IOrgDAO;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.adempiere.util.lang.IMutable;
import org.adempiere.util.lang.Mutable;
import org.compiere.model.I_AD_Attachment;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;
import org.compiere.model.MAllocationHdr;
import org.compiere.model.X_C_DocType;
import org.compiere.process.DocAction;
import org.compiere.util.Env;
import org.compiere.util.TrxRunnable;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;
import org.slf4j.Logger;

import de.metas.allocation.api.IAllocationBL;
import de.metas.allocation.api.IAllocationDAO;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.banking.model.I_C_BankStatementLine;
import de.metas.banking.model.I_C_BankStatementLine_Ref;
import de.metas.document.engine.IDocActionBL;
import de.metas.lock.api.ILockManager;
import de.metas.logging.LogManager;
import de.metas.payment.api.DefaultPaymentBuilder.TenderType;
import de.metas.payment.api.IPaymentBL;
import de.metas.payment.esr.ESRConstants;
import de.metas.payment.esr.api.IESRImportBL;
import de.metas.payment.esr.api.IESRImportDAO;
import de.metas.payment.esr.api.IESRLineMatcher;
import de.metas.payment.esr.exception.ESRImportLockedException;
import de.metas.payment.esr.model.I_C_BP_BankAccount;
import de.metas.payment.esr.model.I_ESR_Import;
import de.metas.payment.esr.model.I_ESR_ImportLine;
import de.metas.payment.esr.model.X_ESR_ImportLine;
import de.metas.payment.esr.processor.impl.LoadESRImportFileWorkpackageProcessor;
import de.metas.payment.esr.spi.IESRActionHandler;

public class ESRImportBL implements IESRImportBL
{
	private static final transient Logger logger = LogManager.getLogger(ESRImportBL.class);

	private static final String MSG_GroupLinesNegativeAmount = "GroupLinesNegativeAmount";

	private static final String ESR_NO_HAS_WRONG_ORG_2P = "de.metas.payment.esr.EsrNoHasWrongOrg";

	private final IAttachmentBL attachementBL = Services.get(IAttachmentBL.class);

	/**
	 * Filled by {@link #registerActionHandler(String, IESRActionHandler)}.
	 */
	private final Map<String, IESRActionHandler> handlers = new ConcurrentHashMap<String, IESRActionHandler>();

	// 03928
	private static final ArrayKey NO_INVOICE_KEY = Util.mkKey("NoInvoiceKey");

	private final IESRLineMatcher esrLineMatcher;

	public ESRImportBL()
	{
		this(new ESRLineMatcher());
	}

	/**
	 * package visible constructor for testing. Allows it to set a mock-matcher.
	 *
	 * @param matcher
	 */
	/* package */ESRImportBL(final IESRLineMatcher matcher)
	{
		this.esrLineMatcher = matcher;
	}

	private final void lockAndProcess(final I_ESR_Import esrImport, final Runnable processor)
	{
		Check.assumeNotNull(esrImport, "esrImport not null");

		final ILockManager lockManager = Services.get(ILockManager.class);
		if (!lockManager.lock(esrImport))
		{
			throw new ESRImportLockedException(esrImport);
		}

		try
		{
			processor.run();
		}
		finally
		{
			final boolean unlocked = lockManager.unlock(esrImport);
			if (!unlocked)
			{
				final AdempiereException ex = new AdempiereException("Could not be unlocked: " + esrImport);
				logger.warn(ex.getLocalizedMessage() + " [IGNORED]", ex);
			}
		}
	}

	@Override
	public void loadESRImportFile(final I_ESR_Import esrImport, final String filename, final I_C_Async_Batch asyncBatch)
	{
		Services.get(IESRImportDAO.class).deleteLines(esrImport);

		final File file = new File(filename);

		// 03928: Create attachment
		// attaching the file first, so that it's available for our support, if anything goes wrong
		final I_AD_Attachment attachment = Services.get(IAttachmentBL.class).createAttachment(esrImport, file);

		Check.errorUnless(attachment != null, "File {} not attached to {}", file, esrImport);

		// enqueue for imporitng async
		enqueESRImport(esrImport, asyncBatch);
	}

	@Override
	public void loadESRImportFile(final I_ESR_Import esrImport, final ITrxRunConfig trxRunConfig)
	{
		lockAndProcess(esrImport, new Runnable()
		{

			@Override
			public void run()
			{
				loadESRImportFile0(esrImport, trxRunConfig);
			}
		});
	}

	public void loadESRImportFile0(final I_ESR_Import esrImport, final ITrxRunConfig trxRunConfig)
	{
		final I_AD_Attachment attachment = attachementBL.getAttachment(esrImport);
		final byte[] data = attachementBL.getFirstEntryAsBytesOrNull(attachment);

		// there is no actual data
		if (data == null || data.length == 0)
		{
			return;
		}

		final ByteArrayInputStream in = new ByteArrayInputStream(data);

		int countLines = 0;
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		if (sysConfigBL.getBooleanValue(ESRConstants.SYSCONFIG_CHECK_DUPLICATED, false))
		{
			countLines = Services.get(IESRImportDAO.class).countLines(esrImport, null);
		}

		BufferedReader reader = null;
		try
		{
			reader = new BufferedReader(new InputStreamReader(in));

			BigDecimal importAmt = BigDecimal.ZERO;
			int trxQty = 0;
			int lineNo = 0;
			while (reader.ready())
			{
				final String currentTextLine = reader.readLine();

				// task 06281: skipping empty lines
				if (Check.isEmpty(currentTextLine))
				{
					continue;
				}

				// for row number
				lineNo++;

				//
				// create line only if does not exist
				I_ESR_ImportLine existentLine = null;
				// if there are already lines before starting reading the file, means that we already tried to import once
				if (countLines > 0)
				{
					existentLine = Services.get(IESRImportDAO.class).fetchLineForESRLineText(esrImport, currentTextLine);
				}

				if (existentLine == null)
				{
				final I_ESR_ImportLine line = createESRImportLine(esrImport, currentTextLine, lineNo, trxRunConfig);

					if (isControlLine(line))
					{
						// The control lines do not contain relevant information about the bank account
						continue;
					}
					else
					{
						importAmt = importAmt.add(line.getAmount());
						trxQty++;
					}
				}
			}

			final boolean hasLines = lineNo > 0;

			final boolean fitAmounts = importAmt.compareTo(esrImport.getESR_Control_Amount()) == 0;

			final boolean fitTrxQtys = (new BigDecimal(trxQty).compareTo(esrImport.getESR_Control_Trx_Qty()) == 0);

			esrImport.setIsValid(hasLines && fitAmounts && fitTrxQtys);

			Check.assume(hasLines, "ESR Document has lines");

			Check.assume((fitAmounts), "The calculated amount for lines ("
					+ importAmt
					+ ")  does not fit the control amount ("
					+ esrImport.getESR_Control_Amount()
					+ "). The document will not be processed.");

			Check.assume((fitTrxQtys), "The counted transactions ("
					+ trxQty
					+ ") do not fit the control transaction quantities ("
					+ esrImport.getESR_Control_Trx_Qty()
					+ "). The document will not be processed.");
		}
		catch (IOException e)
		{
			throw new AdempiereException(e.getLocalizedMessage(), e);
		}
		catch (AdempiereException e)
		{
			// if there is an an asumption error, catch it to add a message and the release it
			final String message = e.getMessage();
			if (message.startsWith("Assumption failure:"))
			{
				esrImport.setDescription(esrImport.getDescription() + " > Fehler: Es ist ein Fehler beim Import aufgetreten! " + e.getLocalizedMessage());
				InterfaceWrapperHelper.save(esrImport, ITrx.TRXNAME_None); // out of transaction: we want to not be rollback
			}

			throw new AdempiereException(e.getLocalizedMessage(), e);
		}
		finally
		{
			if (reader != null)
			{
				try
				{
					reader.close();
				}
				catch (IOException e)
				{
					logger.warn(e.getLocalizedMessage(), e);
				}
			}
		}

		matchESRImport(esrImport);
	}

	/**
	 * Groups the given lines so that we can create one payment for each line.
	 *
	 * to be more specific, the lines are grouped the following properties:
	 * <ul>
	 * <li>AD_Org_ID</li>
	 * <li>C_BPartner_ID</li>
	 * <li>C_Invoice_ID</li>
	 * <li>AccountNo</li>
	 * <li>AccountingDate</li>
	 * <li>PaymentDate</li>
	 * </ul>
	 *
	 * @param importedLines
	 * @return
	 */
	private Map<ArrayKey, List<I_ESR_ImportLine>> groupLines(final List<I_ESR_ImportLine> importedLines)
	{
		final Map<ArrayKey, List<I_ESR_ImportLine>> key2Lines = new HashMap<Util.ArrayKey, List<I_ESR_ImportLine>>();

		for (final I_ESR_ImportLine line : importedLines)
		{
			if (isControlLine(line))
			{
				continue;
			}

			final ArrayKey key = mkESRLineKey(line);

			List<I_ESR_ImportLine> linesOfInvoice = key2Lines.get(key);
			if (linesOfInvoice == null)
			{
				linesOfInvoice = new ArrayList<I_ESR_ImportLine>();
				key2Lines.put(key, linesOfInvoice);
			}
			linesOfInvoice.add(line);

		}

		return key2Lines;
	}

	private ArrayKey mkESRLineKey(final I_ESR_ImportLine line)
	{
		final ArrayKey key;

		if ((line.getC_Invoice_ID() > 0)
				&& (line.getC_Invoice().isPaid() == false)
				&& line.getC_Invoice().getAD_Org_ID() == line.getAD_Org_ID() // only if orgs match
		// we also want to handle invoices that are already paid, because this line links them to another payment
		/* && !line.getC_Invoice().isPaid() */)
		{
			key = Util.mkKey(
					line.getAD_Org_ID(),
					line.getC_BPartner_ID(),
					line.getC_Invoice_ID(),
					line.getAccountNo(),
					line.getAccountingDate(),
					line.getPaymentDate());
		}
		else
		{
			// note that we thread invoices with different AD_Org_ID as if not existing. We do reference the invoice, but that's only for information.
			// !! We won't allocate them against the payment !!
			key = NO_INVOICE_KEY;
		}
		return key;
	}

	private void matchESRImport(final I_ESR_Import esrImport)
	{
		final List<I_ESR_ImportLine> lines = Services.get(IESRImportDAO.class).retrieveLinesForTrxTypes(esrImport, ESRConstants.ESRTRXTYPES_Control);
		if (lines.isEmpty())
		{
			throw new AdempiereException("No control lines found");
		}
		if (lines.size() > 1)
		{
			throw new AdempiereException("More then one control line found (" + lines + ")");
		}

		final I_ESR_ImportLine controlLine = lines.get(0);

		final String esrTrxType = controlLine.getESRTrxType();

		if (ESRConstants.ESRTRXTYPE_Receipt.equals(esrTrxType))
		{
			esrImport.setIsReceipt(true);
		}
		else if (ESRConstants.ESRTRXTYPE_Payment.equals(esrTrxType))
		{
			// esrImport.setIsReceipt(false);
			throw new AdempiereException("@NotSupported@ @ESRTrxType@: " + esrTrxType);
		}
		else
		{
			throw new AdempiereException("@NotSupported@ @ESRTrxType@: " + esrTrxType);
		}

		InterfaceWrapperHelper.save(esrImport);
	}

	/**
	 * Updates the ESR Line, if that line is not already processed. The BPartner value could have been changed. Also a new document could have been created)
	 *
	 * @param esrImportLine
	 * @param trxRunConfig
	 */
	/* package */
	void matchESRImportLine(
			final I_ESR_ImportLine esrImportLine,
			final ITrxRunConfig trxRunConfig)
	{
		if (esrImportLine.isProcessed())
		{
			return; // nothing to do; don't change a lines that is aready processed
		}

		final String trxName = InterfaceWrapperHelper.getTrxName(esrImportLine);
		// 04582: match each line within its own TrxRunner, so that (depending on the trxRunConfig), it will be committed and thus release its locks
		Services.get(ITrxManager.class).run(trxName, trxRunConfig, new TrxRunnable()
		{
			@Override
			public void run(String localTrxName) throws Exception
			{
				try
				{
					esrLineMatcher.match(esrImportLine);
				}
				catch (Exception e)
				{
					logger.info(e.getLocalizedMessage(), e);

					esrImportLine.setIsValid(false);
					esrImportLine.setProcessed(false);
					addErrorMsg(esrImportLine, e.getLocalizedMessage());
				}

				try
				{
					InterfaceWrapperHelper.save(esrImportLine);
				}
				catch (Exception e)
				{
					// if there is an an assumption error, catch it to add a message and the release it
					final I_ESR_Import esrImport = esrImportLine.getESR_Import();
					esrImport.setDescription(esrImport.getDescription() + " > " + e.getLocalizedMessage());
					InterfaceWrapperHelper.save(esrImport, ITrx.TRXNAME_None); // out of transaction: we want to not be rollback
					throw new AdempiereException(e);
				}

			}
		});
	}

	private I_ESR_ImportLine createESRImportLine(
			final I_ESR_Import esrImport,
			final String esrImportLineText,
			final int rowNumber,
			final ITrxRunConfig trxRunConfig)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(esrImport);
		final String trxName = InterfaceWrapperHelper.getTrxName(esrImport);

		final I_ESR_ImportLine[] importLine = new I_ESR_ImportLine[1];

		// 04582: create each line within its own TrxRunner, so that (depending on the trxRunConfig), it will be committed and thus release its locks
		Services.get(ITrxManager.class).run(trxName, trxRunConfig, new TrxRunnable()
		{
			@Override
			public void run(String localTrxName) throws Exception
			{
				importLine[0] = InterfaceWrapperHelper.create(ctx, I_ESR_ImportLine.class, trxName);
				importLine[0].setAD_Org_ID(esrImport.getAD_Org_ID());
				importLine[0].setESR_Import(esrImport);
				importLine[0].setLineNo(rowNumber);
				importLine[0].setESRLineText(esrImportLineText); // the whole line text

				// all lines of one esrImport have the same C_BP_BankAccount_ID, so in future these two column can be remove from the line
				importLine[0].setC_BP_BankAccount(esrImport.getC_BP_BankAccount());
				importLine[0].setAccountNo(esrImport.getC_BP_BankAccount().getAccountNo());
			}
		});

		matchESRImportLine(importLine[0], trxRunConfig);
		return importLine[0];
	}

	@Override
	public void enqueESRImport(final I_ESR_Import esrImport, final I_C_Async_Batch asyncBatch)
	{
		// NOTE: locking not required

		final Properties ctx = InterfaceWrapperHelper.getCtx(esrImport);
		final IWorkPackageQueue queue = Services.get(IWorkPackageQueueFactory.class).getQueueForEnqueuing(ctx, LoadESRImportFileWorkpackageProcessor.class);

		queue.newBlock()
				.setContext(ctx)
				.newWorkpackage()
				.setC_Async_Batch(asyncBatch) // set the async batch in workpackage in order to track it
				.addElement(esrImport)
				.build();
	}

	@Override
	public int process(final I_ESR_Import esrImport, final ITrxRunConfig trxRunConfig)
	{
		final IMutable<Integer> processedLinesCount = new Mutable<Integer>();
		lockAndProcess(esrImport, new Runnable()
		{

			@Override
			public void run()
			{
				final int count = process0(esrImport, trxRunConfig);
				processedLinesCount.setValue(count);
			}
		});

		return processedLinesCount.getValue();
	}

	private int process0(final I_ESR_Import esrImport, final ITrxRunConfig trxRunConfig)
	{
		if (esrImport.isProcessed())
		{
			throw new AdempiereException("@Processed@: @Y@");
		}

		final List<I_ESR_ImportLine> linesToProcess = new ArrayList<I_ESR_ImportLine>();
		final List<I_ESR_ImportLine> allLines = Services.get(IESRImportDAO.class).retrieveLines(esrImport);
		try
		{
			
			if (allLines.isEmpty())
			{
				throw new AdempiereException("@NoLines@");
			}

			for (final I_ESR_ImportLine line : allLines)
			{
				// skip the control line
				if (isControlLine(line))
				{
					line.setESR_Payment_Action(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Control_Line);
					InterfaceWrapperHelper.save(line);
					continue;
				}
				// skip reverse booking lines from regular processing
				// the admin should deal with them manually
				if (isReverseBookingLine(line))
				{
					line.setESR_Payment_Action(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Reverse_Booking);
					InterfaceWrapperHelper.save(line);
					continue;
				}

				// Skip already processed lines
				if (line.isProcessed())
				{
					continue;
				}
				// skip lines that have a payment, but are not are not yet processed (because a user needs to select an action)
				// 08500: skip the lines with payments
				InterfaceWrapperHelper.refresh(line);
				if (line.getC_Payment_ID() > 0)
				{
					continue;
				}
				// Check/Validate
				if (!line.isValid())
				{
					matchESRImportLine(line, trxRunConfig);
				}
				// finally, skip lines that have no bpartner set
				if (line.getC_BPartner_ID() <= 0)
				{
					continue;
				}
				linesToProcess.add(line);
			}

			final Map<ArrayKey, List<I_ESR_ImportLine>> invoiceKey2Line = groupLines(linesToProcess);
			// 03808: Check if sum is not negative for any group

			final boolean isPositiveGroupLinesAmount = checkPositiveGroupLinesAmount(invoiceKey2Line);

			Check.assume(isPositiveGroupLinesAmount, MSG_GroupLinesNegativeAmount);

			final Properties ctx = InterfaceWrapperHelper.getCtx(esrImport);
			final String trxName = InterfaceWrapperHelper.getTrxName(esrImport);

			final Set<ArrayKey> keysSeen = new HashSet<Util.ArrayKey>();

			for (final I_ESR_ImportLine lineToProcess : linesToProcess)
			{
				// important: when processing our grouped lines, we still to do so in the order of those lines, to avoid problems with multiple lines that have the same invoice but different payments!
				final ArrayKey key = mkESRLineKey(lineToProcess);
				if (!keysSeen.add(key))
				{
					continue;
				}

				// 04582: process each key within it's own TrxRunner, so that (depending on the trxRunConfig), it will be committed and thus release its locks
				if (NO_INVOICE_KEY.equals(key))
				{
					final List<I_ESR_ImportLine> linesNoInvoices = invoiceKey2Line.get(key);
					if (linesNoInvoices == null)
					{
						continue;
					}
					processLinesNoInvoice(ctx, linesNoInvoices, trxName, trxRunConfig);
				}
				else
				{
					final List<I_ESR_ImportLine> linesForKey = invoiceKey2Line.get(key);
					try
					{
						processLinesWithInvoice(linesForKey, trxName, trxRunConfig);
					}
					catch (Exception e)
					{
						logger.warn(e.getLocalizedMessage(), e);
						for (final I_ESR_ImportLine lineWithError : linesForKey)
						{
							addErrorMsg(lineWithError, e.getLocalizedMessage());
							InterfaceWrapperHelper.save(lineWithError);
						}
					}
				}
			}

			InterfaceWrapperHelper.save(esrImport);
			return linesToProcess.size();
		}
		catch (Exception e)
		{
			// if there is an an asumption error, catch it to add a message and the release it
			final String message = e.getMessage();
			if (message.startsWith("Assumption failure:"))
			{
				esrImport.setDescription(esrImport.getDescription() + " > Fehler: Es ist ein Fehler beim Import aufgetreten! " + e.getLocalizedMessage());
				InterfaceWrapperHelper.save(esrImport, ITrx.TRXNAME_None); // out of transaction: we want to not be rollback
			}

			throw new AdempiereException(e.getLocalizedMessage(), e);
		}
		finally
		{
			// cg: just make sure that the esr import is not set to processed too early
			if (isAllLinesProcessed(allLines))
			{
				esrImport.setProcessed(true);
				InterfaceWrapperHelper.save(esrImport);
			}
		}
	}

	/* package */
	void processLinesNoInvoice(final Properties ctx, final List<I_ESR_ImportLine> linesNoInvoices, final String trxName, ITrxRunConfig trxRunConfig)
	{
		for (final I_ESR_ImportLine line : linesNoInvoices)
		{
			if (line.getC_Payment_ID() > 0)
			{
				continue; // 04607 whatever we do, don't create another payment if there is already one
			}

			Services.get(ITrxManager.class).run(trxName, trxRunConfig, new TrxRunnable()
			{
				@Override
				public void run(String localTrxName) throws Exception
				{
					InterfaceWrapperHelper.refresh(line);
					if (line.getC_Payment_ID() > 0)
					{
						return;
					}

					final I_C_Payment payment = createUnlinkedPaymentForLine(line, line.getAmount());
					Check.assume(payment.getAD_Org_ID() == line.getAD_Org_ID(), "Payment has the same org as {}", line);

					final I_C_BP_BankAccount bankAccount = InterfaceWrapperHelper.create(ctx, line.getESR_Import().getC_BP_BankAccount_ID(), I_C_BP_BankAccount.class, trxName);

					de.metas.banking.model.I_C_Payment paym = InterfaceWrapperHelper.create(payment, de.metas.banking.model.I_C_Payment.class);
					paym.setC_Currency_ID(bankAccount.getC_Currency_ID());

					final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
					if (sysConfigBL.getBooleanValue(ESRConstants.SYSCONFIG_EAGER_PAYMENT_ALLOCATION, true)) // task 09167: calling with true to preserve the old behavior
					{
						paym.setIsAutoAllocateAvailableAmt(true); // task 07783
					}
					InterfaceWrapperHelper.save(paym);
					// guard; there was some crappy beforeSave() code in MPayment, there might be more
					Check.assume(payment.getAD_Org_ID() == line.getAD_Org_ID(), "Payment has the same org as {}", line);

					line.setC_Payment(payment);

					// despite the amounts are fitting, we can't set this status, because there was a problem with the invoice that we found (e.g. wrong org),
					// and the user might for example want to transfer the money back to the customer
					// however, if we set the action to 'F', it will become read-only and (maybe worse) the user does not see that she still has to decide and/or do something in this case.
					// if (line.getC_Payment().getPayAmt().compareTo(line.getESR_Invoice_Openamt()) == 0)
					// {
					// line.setESR_Payment_Action(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Fit_Amounts);
					// }
					InterfaceWrapperHelper.save(line);

					Services.get(IDocActionBL.class).processEx(payment, DocAction.ACTION_Complete, DocAction.STATUS_Completed);

					InterfaceWrapperHelper.save(line);
				}
			});
		}

	}

	/**
	 * @param line
	 * @param payAmt
	 * @return created (but NOT SAVED) payment
	 */
	private I_C_Payment createUnlinkedPaymentForLine(final I_ESR_ImportLine line, final BigDecimal payAmt)
	{
		// 04607
		Check.errorIf(line.getC_Payment_ID() > 0, "ESR_ImportLine {} has already C_Payment_ID {}", line, line.getC_Payment_ID());

		return Services.get(IPaymentBL.class).newBuilder(line)
				.setDocbaseType(X_C_DocType.DOCBASETYPE_ARReceipt)
				.setIsReceipt(true)
				.setAD_Org_ID(line.getAD_Org_ID())
				.setC_BP_BankAccount_ID(line.getESR_Import().getC_BP_BankAccount_ID())
				.setAccountNo(line.getAccountNo())
				.setDateAcct(line.getAccountingDate())
				.setDateTrx(line.getPaymentDate())
				.setC_BPartner_ID(line.getC_BPartner_ID())
				.setTenderType(TenderType.ACH)
				.setPayAmt(payAmt)
				.createNoSave();
	}

	private boolean isInvoicePaidInCurrentImport(final List<I_ESR_ImportLine> lines, I_C_Invoice invoice)
	{

		for (final I_ESR_ImportLine line : lines)
		{
			if (line.getC_Payment_ID() > 0)
			{
				final I_C_Payment payment = line.getC_Payment();
				if (payment.getC_Invoice_ID() == invoice.getC_Invoice_ID())
				{
					return true;
				}
			}
		}

		return false;
	}

	private boolean checkPositiveGroupLinesAmount(Map<ArrayKey, List<I_ESR_ImportLine>> invoiceKey2Line)
	{
		for (final ArrayKey key : invoiceKey2Line.keySet())
		{
			final List<I_ESR_ImportLine> linesForKey = invoiceKey2Line.get(key);

			BigDecimal sum = Env.ZERO;
			for (final I_ESR_ImportLine line : linesForKey)
			{
				sum.add(line.getAmount());
			}
			if (sum.compareTo(Env.ZERO) < 0)
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * Creates a payment for the given lines. Assumes that all lines reference the same invoice. Method is default visible to make it testable.
	 *
	 * @param importLines
	 * @param trxName
	 * @param trxRunConfig
	 */
	/* package */void processLinesWithInvoice(
			final List<I_ESR_ImportLine> importLines,
			final String trxName,
			final ITrxRunConfig trxRunConfig)
	{
		// 04607 whatever we do, don't create another payment if there is already one
		final List<I_ESR_ImportLine> importLinesWithoutPayment = new ArrayList<I_ESR_ImportLine>();

		// 05808: lines with payments with no invoices set should be also processed
		final List<I_ESR_ImportLine> importLinesWithPaymentNoInvoice = new ArrayList<I_ESR_ImportLine>();

		for (final I_ESR_ImportLine importLine : importLines)
		{
			// 05808: Also process lines that have payments, but payments do not have invoices
			if (importLine.getC_Payment_ID() <= 0)
			{
				importLinesWithoutPayment.add(importLine);
			}
			else
			{
				if (importLine.getC_Payment().getC_Invoice_ID() <= 0)
				{
					importLinesWithPaymentNoInvoice.add(importLine);
				}
			}
		}

		if (!importLinesWithPaymentNoInvoice.isEmpty())
		{
			processLinesWithPaymentNoInvoice(importLinesWithPaymentNoInvoice, trxName, trxRunConfig, true);
		}
		if (importLinesWithoutPayment.isEmpty())
		{
			return; // nothing to do
		}

		processLinesWithPaymentNoInvoice(importLinesWithoutPayment, trxName, trxRunConfig, false);
	}

	private void processLinesWithPaymentNoInvoice(final List<I_ESR_ImportLine> importLinesWithPaymentNoInvoice, final String trxName, final ITrxRunConfig trxRunConfig, final boolean withPayment)
	{
		Services.get(ITrxManager.class).run(trxName, trxRunConfig, new TrxRunnable()
		{
			@Override
			public void run(String localTrxName) throws Exception
			{

				for (final I_ESR_ImportLine importLine : importLinesWithPaymentNoInvoice)
				{
					final BigDecimal sum = importLine.getAmount();

					InterfaceWrapperHelper.refresh(importLine);
					I_C_Payment payment = importLine.getC_Payment();

					if (withPayment)
					{

						Check.assumeNotNull(payment, "{} has a payment", importLine);
					}
					else
					{
						if (importLine.getC_Payment_ID() <= 0)
						{
							payment = createUnlinkedPaymentForLine(importLine, sum);
							de.metas.banking.model.I_C_Payment paym = InterfaceWrapperHelper.create(payment, de.metas.banking.model.I_C_Payment.class);

							final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
							if (sysConfigBL.getBooleanValue(ESRConstants.SYSCONFIG_EAGER_PAYMENT_ALLOCATION, true)) // task 09167: calling with true to preserve the old behavior
							{
								paym.setIsAutoAllocateAvailableAmt(true); // task 07783
							}
						}
					}

					Check.assume(payment.getAD_Org_ID() == importLine.getAD_Org_ID(), "Payment has the same org as {}", importLine);

					final I_C_Invoice invoice = importLine.getC_Invoice();
					Check.assumeNotNull(invoice, "{} has an invoice", importLine);

					// make sure that we don't end up with inter-org allocations
					Check.assume(invoice.getAD_Org_ID() == payment.getAD_Org_ID(), "Invoice {} and payment {} have the same AD_Org_ID");

					payment.setC_Currency_ID(invoice.getC_Currency_ID());

					// Note that we set OverUnderAmt to make it clear the we don't have Discount or WriteOff
					final BigDecimal invoiceOpenAmt = Services.get(IInvoiceDAO.class).retrieveOpenAmt(invoice);
					final BigDecimal overUnderAmt = sum.subtract(invoiceOpenAmt);
					if (X_ESR_ImportLine.ESR_DOCUMENT_STATUS_TotallyMatched.equals(importLine.getESR_Document_Status())
							&& !isInvoicePaidInCurrentImport(importLinesWithPaymentNoInvoice, invoice) && BigDecimal.ZERO.compareTo(overUnderAmt) == 0)
					{
						payment.setC_Invoice_ID(invoice.getC_Invoice_ID());
						payment.setOverUnderAmt(overUnderAmt);
					}

					payment.setDocStatus(DocAction.STATUS_Drafted);
					payment.setDocAction(DocAction.ACTION_Complete);

					InterfaceWrapperHelper.save(payment);

					// guard; there was some crappy code in MPayment, there might be more
					Check.assume(payment.getAD_Org_ID() == importLine.getAD_Org_ID(), "Payment has the same org as {}", importLine);

					Services.get(IDocActionBL.class).processEx(payment, DocAction.ACTION_Complete, DocAction.STATUS_Completed);
					final boolean ignoreProcessed = false;
					Services.get(IInvoiceBL.class).testAllocation(invoice, ignoreProcessed);
					InterfaceWrapperHelper.save(invoice);

					importLine.setC_Payment(payment);
					InterfaceWrapperHelper.save(importLine);

					updateLinesOpenAmt(importLine, invoice); // note that there might be further lines for this invoice
					InterfaceWrapperHelper.save(importLine); // saving, because updateLinesOpenAmt doesn't save the line it was called with
				}
			}
		});
	}

	@Override
	public void linkInvoiceToPayment(final I_ESR_ImportLine importLine)
	{
		final I_C_Payment payment = importLine.getC_Payment();

		Check.assume(payment.getAD_Org_ID() == importLine.getAD_Org_ID(), "Payment has the same org as {}", importLine);

		Check.assume(!Check.equals(payment.getDocStatus(), DocAction.STATUS_Drafted), "Payment shoould not be drafted}");

		final I_C_Invoice invoice = importLine.getC_Invoice();
		Check.assumeNotNull(invoice, "{} has an invoice", importLine);

		// make sure that we don't end up with inter-org allocations
		Check.assume(invoice.getAD_Org_ID() == payment.getAD_Org_ID(), "Invoice {} and payment {} have the same AD_Org_ID");

		final ITrxManager trxManager = Services.get(ITrxManager.class);

		final String trxName = trxManager.getThreadInheritedTrxName(OnTrxMissingPolicy.ReturnTrxNone);
		trxManager.run(trxName, new TrxRunnable()
		{
			@Override
			public void run(String trxName) throws Exception
			{
				// must assure that the invoice has transaction
				InterfaceWrapperHelper.refresh(invoice, trxName);

				final boolean ignoreIsAutoAllocateAvailableAmt = true; // task 09167: when processing ESR lines (i.e. from this method) we always allocate the payment to the invoice.
				Services.get(IAllocationBL.class).autoAllocateSpecificPayment(invoice,
						InterfaceWrapperHelper.create(payment, de.metas.banking.model.I_C_Payment.class),
						ignoreIsAutoAllocateAvailableAmt);
				InterfaceWrapperHelper.save(importLine); // saving, because updateLinesOpenAmt doesn't save the line it was called with
			}
		});

	}

	@Override
	public int calculateESRCheckDigit(final String text)
	{
		return new ESRCheckDigitBuilder().calculateESRCheckDigit(text);
	}

	@Override
	public boolean isProcessed(final I_ESR_Import esrImport)
	{
		final List<I_ESR_ImportLine> allLines = Services.get(IESRImportDAO.class).retrieveLines(esrImport);
		return isAllLinesProcessed(allLines);
	}

	private boolean isAllLinesProcessed(final List<I_ESR_ImportLine> allLines)
	{
		if (allLines.size() == 0)
		{
			return false;
		}

		final List<I_ESR_ImportLine> linesProcessed = new ArrayList<I_ESR_ImportLine>();
		for (final I_ESR_ImportLine line : allLines)
		{
			if (line.isProcessed())
			{
				linesProcessed.add(line);
			}
		}

		return allLines.size() == linesProcessed.size();
	}

	@Override
	public void complete(
			final I_ESR_Import esrImport,
			final String message,
			final ITrxRunConfig trxRunConfig)
	{
		if (esrImport.isProcessed())
		{
			throw new AdempiereException("@Processed@: @Y@");
		}

		// create payments before completing
		process(esrImport, trxRunConfig);

		final List<I_ESR_ImportLine> allLines = Services.get(IESRImportDAO.class).retrieveLines(esrImport);

		final String trxName = InterfaceWrapperHelper.getTrxName(esrImport);

		for (final I_ESR_ImportLine line : allLines)
		{
			// 04582: process each line within it's own TrxRunner, so that (depending on the trxRunConfig), it will be committed and thus release its locks
			Services.get(ITrxManager.class).run(trxName, trxRunConfig, new TrxRunnable()
			{
				@Override
				public void run(String localTrxName) throws Exception
				{
					if (line.isProcessed())
					{
						return; // this is usually true for the 999-line
					}
					if (!line.isActive())
					{
						return;
					}

					// check partners first
					final I_C_BPartner esrPartner = line.getC_BPartner();
					final I_C_BPartner invPartner = line.getC_Invoice_ID() > 0 ? line.getC_Invoice().getC_BPartner() : null;
					final I_C_BPartner paymentPartner = line.getC_Payment_ID() > 0 ? line.getC_Payment().getC_BPartner() : null;
					if (esrPartner != null)
					{
						if (invPartner != null)
						{
							if (invPartner.getC_BPartner_ID() != esrPartner.getC_BPartner_ID())
							{
								final AdempiereException ex = new AdempiereException("@" + ESRConstants.ESR_DIFF_INV_PARTNER + "@");
								logger.warn(ex.getLocalizedMessage(), ex);
								addErrorMsg(line, ex.getLocalizedMessage());
								InterfaceWrapperHelper.save(line);
								return;
							}
						}

						if (paymentPartner != null)
						{
							if (paymentPartner.getC_BPartner_ID() != esrPartner.getC_BPartner_ID())
							{
								final AdempiereException ex = new AdempiereException("@" + ESRConstants.ESR_DIFF_PAYMENT_PARTNER + "@");
								logger.warn(ex.getLocalizedMessage(), ex);
								addErrorMsg(line, ex.getLocalizedMessage());
								InterfaceWrapperHelper.save(line);
								return;
							}
						}
					}

					final String actionType = line.getESR_Payment_Action();
					if (Check.isEmpty(actionType, true))
					{
						final AdempiereException ex = new AdempiereException("@" + ESRConstants.ERR_ESR_LINE_WITH_NO_PAYMENT_ACTION + "@");
						logger.warn(ex.getLocalizedMessage(), ex);
						addErrorMsg(line, ex.getLocalizedMessage());
						InterfaceWrapperHelper.save(line);
						return;
					}

					final IESRActionHandler handler = handlers.get(actionType);
					if (handler == null)
					{
						final AdempiereException ex = new AdempiereException("@NotSupported@ @ESR_Payment_Action@: " + actionType);
						logger.warn(ex.getLocalizedMessage(), ex);
						addErrorMsg(line, ex.getLocalizedMessage());
						InterfaceWrapperHelper.save(line);
						return;
					}

					try
					{
						final boolean processed = handler.process(line, message);
						line.setProcessed(processed);
					}
					catch (Exception e)
					{
						logger.warn(e.getLocalizedMessage(), e);
						addErrorMsg(line, e.getLocalizedMessage());
						InterfaceWrapperHelper.save(line);
						return;
					}

					InterfaceWrapperHelper.save(line);
				}
			});
		}

		// cg: just make sure that the esr import is not set to processed too early
		if (isAllLinesProcessed(allLines))
		{
			esrImport.setProcessed(true);
			InterfaceWrapperHelper.save(esrImport);
		}

	}

	@Override
	public void addErrorMsg(I_ESR_ImportLine importLine, String msg)
	{
		if (Check.isEmpty(msg, true))
		{
			return;
		}

		String errorMsg = importLine.getErrorMsg();
		if (errorMsg == null)
		{
			errorMsg = "";
		}

		if (!Check.isEmpty(errorMsg, true))
		{
			errorMsg += "; ";
		}

		errorMsg += msg;
		importLine.setErrorMsg(errorMsg);
	}

	@Override
	public void addErrorMsgInFront(I_ESR_ImportLine importLine, String msg)
	{
		if (Check.isEmpty(msg, true))
		{
			return;
		}

		String errorMsg = importLine.getErrorMsg();
		if (errorMsg == null)
		{
			errorMsg = "";
		}

		final StringBuffer err = new StringBuffer();
		err.append(msg);
		if (!Check.isEmpty(errorMsg, true))
		{
			err.append("; ");
		}
		err.append(errorMsg);

		importLine.setErrorMsg(err.toString());
	}

	private boolean isControlLine(final I_ESR_ImportLine line)
	{
		final String trxType = line.getESRTrxType();
		return ESRConstants.ESRTRXTYPE_Payment.equals(trxType)
				|| ESRConstants.ESRTRXTYPE_Receipt.equals(trxType);
	}

	private boolean isReverseBookingLine(final I_ESR_ImportLine line)
	{
		final String trxType = line.getESRTrxType();
		return ESRConstants.ESRTRXTYPE_ReverseBooking.equals(trxType);
	}

	@Override
	public void registerActionHandler(String actionName, IESRActionHandler handler)
	{
		handlers.put(actionName, handler);
	}

	@Override
	public void setInvoice(final I_ESR_ImportLine importLine, final I_C_Invoice invoice)
	{
		// we always update the open amount, even if the invoice reference hasn't changed
		updateLinesOpenAmt(importLine, invoice);

		// cg: if we have a payment and the open amount matches pay amount set status allocate with current invoice
		if (importLine.getC_Invoice_ID() == invoice.getC_Invoice_ID() && importLine.getC_Payment_ID() > 0)
		{
			// services
			final IAllocationDAO allocationDAO = Services.get(IAllocationDAO.class);
			final Set<Integer> linesOwnPaymentIDs = new HashSet<Integer>();
			final BigDecimal externalAllocationsSum = allocationDAO.retrieveAllocatedAmtIgnoreGivenPaymentIDs(invoice, linesOwnPaymentIDs);
			final BigDecimal invoiceOpenAmt = invoice.getGrandTotal().subtract(externalAllocationsSum);
			if (importLine.getAmount().compareTo(invoiceOpenAmt) == 0)
			{
				importLine.setESR_Payment_Action(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Allocate_Payment_With_Current_Invoice);
			}
			else if (X_ESR_ImportLine.ESR_DOCUMENT_STATUS_PartiallyMatched.equals(importLine.getESR_Document_Status()))
			{
				importLine.setESR_Payment_Action(null);
			}

			if (invoice.isPaid() && !Services.get(IPaymentBL.class).isMatchInvoice(importLine.getC_Payment(), invoice))
			{
				Services.get(IESRImportBL.class).addErrorMsg(importLine, "Rechnung " + invoice.getDocumentNo() + " wurde im System als bereits bezahlt markiert");
				importLine.setESR_Document_Status(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_PartiallyMatched);
			}
		}
		else if (invoice.isPaid())
		{
			Services.get(IESRImportBL.class).addErrorMsg(importLine, "Rechnung " + invoice.getDocumentNo() + " wurde im System als bereits bezahlt markiert");
			importLine.setESR_Document_Status(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_PartiallyMatched);
		}

		if (invoice.getAD_Org_ID() != importLine.getAD_Org_ID())
		{
			final Properties ctx = InterfaceWrapperHelper.getCtx(importLine);
			final I_AD_Org invoiceOrg = Services.get(IOrgDAO.class).retrieveOrg(ctx, invoice.getAD_Org_ID());
			Services.get(IESRImportBL.class).addErrorMsg(importLine,
					Services.get(IMsgBL.class).getMsg(ctx, ESR_NO_HAS_WRONG_ORG_2P, new Object[] {
							invoiceOrg.getValue(),
							importLine.getAD_Org().getValue()
					}));
		}

		importLine.setC_Invoice(invoice);
		importLine.setC_BPartner_ID(invoice.getC_BPartner_ID());

		importLine.setESR_Invoice_Grandtotal(invoice.getGrandTotal());

	}

	@Override
	public void reverseAllocationForPayment(final I_C_Payment payment)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(payment);
		final String trxName = InterfaceWrapperHelper.getTrxName(payment);

		final MAllocationHdr[] allocs = MAllocationHdr.getOfPayment(ctx, payment.getC_Payment_ID(), trxName);
		for (MAllocationHdr alloc : allocs)
		{
			Services.get(IDocActionBL.class).processEx(alloc, DocAction.ACTION_Reverse_Correct, DocAction.STATUS_Reversed);
		}

		payment.setC_Invoice_ID(-1);
	}

	/**
	 * Updates all lines that reference the given {@code esrImportLine}'s import and the given {@code invoice}. For those lines that already reference a payment, this method may also set the
	 * {@code Processed} flag and may set the {@code ESR_Payment_Action} to {@link X_ESR_ImportLine#ESR_PAYMENT_ACTION_Fit_Amounts}. Note that this method also saves all updated lines <b>besides</b>
	 * the given {@code esrImportLine}.
	 * <p>
	 * <b>IMPORTANT:</b> as written this method might update the given {@code esrImportLine}, but does <b>not</b> save it. The decision to save or not is left to the caller.
	 *
	 * @param esrImportLine
	 * @param invoice
	 */
	/* package */
	void updateLinesOpenAmt(final I_ESR_ImportLine esrImportLine, final I_C_Invoice invoice)
	{
		final List<I_ESR_ImportLine> linesWithSameInvoice = Services.get(IESRImportDAO.class).retrieveLinesForInvoice(esrImportLine, invoice);

		updateOpenAmtAndStatusDontSave(invoice, linesWithSameInvoice);

		for (final I_ESR_ImportLine lineToSave : linesWithSameInvoice)
		{
			if (lineToSave != esrImportLine)
			{
				// not saving the given line, because this method might be called out of an MV already
				InterfaceWrapperHelper.save(lineToSave);
			}
		}
	}

	// note: package level for testing purpose
	/* package */void updateOpenAmtAndStatusDontSave(final I_C_Invoice invoice, final List<I_ESR_ImportLine> linesWithSameInvoice)
	{
		// services
		final IAllocationDAO allocationDAO = Services.get(IAllocationDAO.class);

		// We start by collecting the C_Payment_IDs from our lines
		final Set<Integer> linesOwnPaymentIDs = new HashSet<Integer>();
		for (final I_ESR_ImportLine importLine : linesWithSameInvoice)
		{
			// if the invoice is paid with the current line, exclude it from computing
			if (importLine.getC_Payment_ID() > 0 && Services.get(IPaymentBL.class).isMatchInvoice(importLine.getC_Payment(), invoice))
			{
				linesOwnPaymentIDs.add(importLine.getC_Payment_ID());
			}
		}

		// Then we get the invoice GrandTotal MINUS the amounts that were already allocated from UNRELATED payments, credit-memos etc.
		// So in invoiceOpenAmt we IGNORE the payments of our lines..if there are no other payments or credit-memos, then the open amount is the invoice's GrandTotal, even if our lines actually paid
		// the whole invoice.
		final BigDecimal externalAllocationsSum = allocationDAO.retrieveAllocatedAmtIgnoreGivenPaymentIDs(invoice, linesOwnPaymentIDs);
		final BigDecimal invoiceOpenAmt = invoice.getGrandTotal().subtract(externalAllocationsSum);

		boolean openAmtTrhesholdCrossed = false;
		BigDecimal sum = BigDecimal.ZERO;
		for (int i = 0; i < linesWithSameInvoice.size(); i++)
		{
			final I_ESR_ImportLine importLine = linesWithSameInvoice.get(i);

			sum = sum.add(importLine.getAmount());

			final BigDecimal newOpenAmt = invoiceOpenAmt.subtract(sum);

			importLine.setESR_Invoice_Openamt(newOpenAmt);

			final int idxSetProcessed;
			if (sum.compareTo(invoiceOpenAmt) == 0)
			{
				openAmtTrhesholdCrossed = true;
				idxSetProcessed = i;
			}
			else if (sum.compareTo(invoiceOpenAmt) > 0 && !openAmtTrhesholdCrossed)
			{
				openAmtTrhesholdCrossed = true;
				// note: if i=0 and we are already above the openAmt with the first line, then idxSetProcessed = -1 is totally fine.
				idxSetProcessed = i - 1;
			}
			else
			{
				idxSetProcessed = -1;
			}

			for (int j = 0; j <= idxSetProcessed; j++)
			{
				final I_ESR_ImportLine fullyMatchedImportLine = linesWithSameInvoice.get(j);
				if (fullyMatchedImportLine.getC_Payment_ID() > 0)
				{
					final I_C_Payment payment = fullyMatchedImportLine.getC_Payment();
					final I_C_Invoice invoiceESR = fullyMatchedImportLine.getC_Invoice();
					if (Services.get(IPaymentBL.class).isMatchInvoice(payment, invoiceESR))
					{
						fullyMatchedImportLine.setProcessed(true);
						fullyMatchedImportLine.setESR_Payment_Action(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Fit_Amounts);
					}
					else
					{
						if (fullyMatchedImportLine.getAmount().compareTo(invoiceOpenAmt) == 0)
						{
							fullyMatchedImportLine.setESR_Payment_Action(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Allocate_Payment_With_Current_Invoice);
						}
					}
				}
			}
		}
	}

	private byte[] createChecksum(String filename) throws Exception
	{
		InputStream fis = new FileInputStream(filename);

		byte[] buffer = new byte[1024];
		MessageDigest complete = MessageDigest.getInstance("MD5");
		int numRead;

		do
		{
			numRead = fis.read(buffer);
			if (numRead > 0)
			{
				complete.update(buffer, 0, numRead);
			}
		}
		while (numRead != -1);

		fis.close();
		return complete.digest();
	}

	@Override
	public String getMD5Checksum(String filename) throws Exception
	{
		byte[] b = createChecksum(filename);
		String result = "";

		for (int i = 0; i < b.length; i++)
		{
			result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
		}
		return result;
	}

	@Override
	public void unlinkESRImportLinesFor(final I_C_BankStatementLine bankStatementLine)
	{
		final IESRImportDAO esrImportDAO = Services.get(IESRImportDAO.class);
		for (final I_ESR_ImportLine esrImportLine : esrImportDAO.retrieveLinesForBankStatementLine(bankStatementLine))
		{
			unlinkESRImportLineFromBankStatement(esrImportLine);
		}
	}

	@Override
	public void unlinkESRImportLinesFor(final I_C_BankStatementLine_Ref bankStatementLineRef)
	{
		final IESRImportDAO esrImportDAO = Services.get(IESRImportDAO.class);
		for (final I_ESR_ImportLine esrImportLine : esrImportDAO.retrieveAllLinesForBankStatementLineRef(bankStatementLineRef))
		{
			unlinkESRImportLineFromBankStatement(esrImportLine);
		}
	}
	
	private final void unlinkESRImportLineFromBankStatement(final I_ESR_ImportLine esrImportLine)
	{
		esrImportLine.setC_BankStatementLine(null);
		esrImportLine.setC_BankStatementLine_Ref(null);
		InterfaceWrapperHelper.save(esrImportLine);
	}
	
	@Override
	public boolean isV11File(String filename)
	{
		Check.assume(!Check.isEmpty(filename,true), "Filename can not be empty!");
		return filename.matches(".*v11") || filename.matches(".*V11");
	}
}
