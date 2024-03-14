package de.metas.payment.esr.api.impl;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.allocation.api.IAllocationBL;
import de.metas.allocation.api.IAllocationDAO;
import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryId;
import de.metas.attachments.AttachmentEntryService;
import de.metas.banking.BankAccount;
import de.metas.banking.BankAccountId;
import de.metas.banking.BankStatementAndLineAndRefId;
import de.metas.banking.BankStatementLineId;
import de.metas.banking.api.IBPBankAccountDAO;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.calendar.standard.IPeriodBL;
import de.metas.document.DocBaseType;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.lock.api.ILockManager;
import de.metas.logging.LogManager;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentId;
import de.metas.payment.TenderType;
import de.metas.payment.api.IPaymentBL;
import de.metas.payment.api.IPaymentDAO;
import de.metas.payment.esr.ESRConstants;
import de.metas.payment.esr.ESRImportId;
import de.metas.payment.esr.actionhandler.IESRActionHandler;
import de.metas.payment.esr.api.IESRImportBL;
import de.metas.payment.esr.api.IESRImportDAO;
import de.metas.payment.esr.api.RunESRImportRequest;
import de.metas.payment.esr.dataimporter.ESRDataLoaderFactory;
import de.metas.payment.esr.dataimporter.ESRDataLoaderUtil;
import de.metas.payment.esr.dataimporter.ESRImportEnqueuer;
import de.metas.payment.esr.dataimporter.ESRImportEnqueuerDataSource;
import de.metas.payment.esr.dataimporter.ESRStatement;
import de.metas.payment.esr.dataimporter.ESRTransaction;
import de.metas.payment.esr.dataimporter.IESRDataImporter;
import de.metas.payment.esr.dataimporter.impl.v11.ESRTransactionLineMatcherUtil;
import de.metas.payment.esr.exception.ESRImportLockedException;
import de.metas.payment.esr.model.I_ESR_Import;
import de.metas.payment.esr.model.I_ESR_ImportFile;
import de.metas.payment.esr.model.I_ESR_ImportLine;
import de.metas.payment.esr.model.X_ESR_ImportLine;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.PeriodClosedException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.IMutable;
import org.adempiere.util.lang.Mutable;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;
import org.compiere.model.MAllocationHdr;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.compiere.util.TrxRunnable;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static org.adempiere.model.InterfaceWrapperHelper.getCtx;
import static org.adempiere.model.InterfaceWrapperHelper.getTrxName;
import static org.adempiere.model.InterfaceWrapperHelper.refresh;

@Service
public class ESRImportBL implements IESRImportBL
{
	private static final Logger logger = LogManager.getLogger(ESRImportBL.class);
	private final IESRImportDAO esrImportDAO = Services.get(IESRImportDAO.class);
	private final IPaymentBL paymentBL = Services.get(IPaymentBL.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final IBPBankAccountDAO bpBankAccountDAO = Services.get(IBPBankAccountDAO.class);
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final IPaymentDAO paymentDAO = Services.get(IPaymentDAO.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);
	private final IPeriodBL periodBL = Services.get(IPeriodBL.class);
	private final ILockManager lockManager = Services.get(ILockManager.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IAllocationBL allocationBL = Services.get(IAllocationBL.class);
	private final IAllocationDAO allocationDAO = Services.get(IAllocationDAO.class);
	private final IOrgDAO orgsRepo = Services.get(IOrgDAO.class);

	/**
	 * @task <a href="https://github.com/metasfresh/metasfresh/issues/2118">https://github.com/metasfresh/metasfresh/issues/2118</a>
	 */
	private static final String CFG_PROCESS_UNSPPORTED_TRX_TYPES = "de.metas.payment.esr.ProcessUnspportedTrxTypes";

	private static final String MSG_GroupLinesNegativeAmount = "GroupLinesNegativeAmount";

	private static final AdMessageKey ESR_NO_HAS_WRONG_ORG_2P = AdMessageKey.of("de.metas.payment.esr.EsrNoHasWrongOrg");

	/**
	 * Filled by {@link #registerActionHandler(String, IESRActionHandler)}.
	 */
	private final Map<String, IESRActionHandler> handlers = new ConcurrentHashMap<>();

	// 03928
	private static final ArrayKey NO_INVOICE_KEY = Util.mkKey("NoInvoiceKey");

	private final AttachmentEntryService attachmentEntryService;

	public ESRImportBL(@NonNull final AttachmentEntryService attachmentEntryService)
	{
		this.attachmentEntryService = attachmentEntryService;
	}

	private void lockAndProcess(
			@NonNull final I_ESR_Import esrImport,
			@NonNull final Runnable processor)
	{
		trxManager.assertThreadInheritedTrxNotExists();

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
	public void loadESRImportFile(final I_ESR_Import esrImport)
	{
		lockAndProcess(esrImport, () -> loadAndEvaluateESRImportFile0(esrImport));
	}

	private void loadAndEvaluateESRImportFile0(@NonNull final I_ESR_Import esrImport)
	{

		final ImmutableList<I_ESR_ImportFile> esrImportFiles = esrImportDAO.retrieveActiveESRImportFiles(esrImport);

		for (final I_ESR_ImportFile esrImportFile : esrImportFiles)
		{
			//
			// Fetch data to be imported from attachment
			final AttachmentEntryId attachmentEntryId = AttachmentEntryId.ofRepoIdOrNull(esrImportFile.getAD_AttachmentEntry_ID());

			final byte[] data = attachmentEntryService.retrieveData(attachmentEntryId);

			// there is no actual data
			if (data == null || data.length == 0)
			{
				return;
			}

			final ByteArrayInputStream in = new ByteArrayInputStream(data);

			loadAndEvaluateESRImportStream(esrImportFile, in);
		}

		esrImportDAO.validateEsrImport(esrImport);

	}

	@VisibleForTesting
	public void loadAndEvaluateESRImportStream(
			@NonNull final I_ESR_ImportFile esrImportFile,
			@NonNull final InputStream in)
	{
		int countLines = 0;
		if (sysConfigBL.getBooleanValue(ESRConstants.SYSCONFIG_CHECK_DUPLICATED, false))
		{
			countLines = esrImportDAO.countLines(esrImportFile, null);
		}

		final IESRDataImporter loader = ESRDataLoaderFactory.createImporter(esrImportFile, in);
		final ESRStatement esrStatement = loader.importData();
		try
		{
			in.close();
		}
		catch (final IOException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}

		esrImportFile.setESR_Control_Amount(esrStatement.getCtrlAmount());
		esrImportFile.setESR_Control_Trx_Qty(esrStatement.getCtrlQty());
		esrImportFile.setIsReceipt(true);
		for (final String errorMsg : esrStatement.getErrorMsgs())
		{
			esrImportFile.setDescription(ESRDataLoaderUtil.addMsgToString(esrImportFile.getDescription(), errorMsg));
		}

		// TODO verify that the bankaccounts match!
		esrImportDAO.save(esrImportFile);

		final List<ESRTransaction> transactions = esrStatement.getTransactions();
		int lineNo = 0;
		for (final ESRTransaction esrTransaction : transactions)
		{
			lineNo++;

			//
			// create line only if does not exist
			I_ESR_ImportLine existentLine = null;
			// if there are already lines before starting reading the file, means that we already tried to import once
			if (countLines > 0)
			{
				existentLine = esrImportDAO.fetchLineForESRLineText(esrImportFile, esrTransaction.getTransactionKey());
				if (existentLine != null)
				{
					continue;
				}
			}

			createEsrImportLine(esrImportFile, lineNo, esrTransaction);
		}

		evaluate(esrImportFile);
	}

	private I_ESR_ImportLine createEsrImportLine(final I_ESR_ImportFile esrImportFile, final int lineNo, final ESRTransaction esrTransaction)
	{
		final I_ESR_ImportLine importLine = ESRDataLoaderUtil.newLine(esrImportFile);
		importLine.setLineNo(lineNo * 10);

		// first that the more basic error messages that might have been collected by the importer
		for (final String errorMsg : esrTransaction.getErrorMsgs())
		{
			ESRDataLoaderUtil.addImportErrorMsg(importLine, errorMsg);
		}

		importLine.setESRPostParticipantNumber(esrTransaction.getEsrParticipantNo());
		importLine.setESRFullReferenceNumber(esrTransaction.getEsrReferenceNumber());
		importLine.setPaymentDate(TimeUtil.asTimestamp(esrTransaction.getPaymentDate()));
		importLine.setAccountingDate(TimeUtil.asTimestamp(esrTransaction.getAccountingDate()));
		importLine.setAmount(esrTransaction.getAmount());
		importLine.setESRTrxType(esrTransaction.getTrxType());
		importLine.setESRLineText(esrTransaction.getTransactionKey());
		importLine.setType(esrTransaction.getType().getCode());

		esrImportDAO.save(importLine);
		return importLine;
	}

	private void evaluate(final I_ESR_ImportFile esrImportFile)
	{
		BigDecimal importAmt = BigDecimal.ZERO;
		int trxQty = 0;

		final ImmutableList<I_ESR_ImportLine> esrImportLines = esrImportDAO.retrieveActiveESRImportLinesFromFile(esrImportFile);

		for (final I_ESR_ImportLine importLine : esrImportLines)
		{
			//
			// now do different validations with the values loaded from the input file
			evaluateLine(importLine);

			importAmt = importAmt.add(importLine.getAmount());
			trxQty++;
		}

		final boolean hasLines = esrImportLines.size() > 0;
		final boolean fitAmounts = importAmt.compareTo(esrImportFile.getESR_Control_Amount()) == 0;
		final boolean fitTrxQtys = evaluateTrxQty(esrImportFile, trxQty);

		esrImportFile.setIsValid(hasLines && fitAmounts && fitTrxQtys);

		if (!hasLines)
		{
			esrImportFile.setDescription(ESRDataLoaderUtil.addMsgToString(esrImportFile.getDescription(), "ESR Document has no lines"));
		}
		if (!fitAmounts)
		{
			esrImportFile.setDescription(ESRDataLoaderUtil.addMsgToString(esrImportFile.getDescription(), "The calculated amount for lines ("
					+ importAmt
					+ ")  does not fit the control amount ("
					+ esrImportFile.getESR_Control_Amount()
					+ "). The document will not be processed."));
		}
		if (!fitTrxQtys)
		{
			esrImportFile.setDescription(ESRDataLoaderUtil.addMsgToString(esrImportFile.getDescription(),
																		  "The counted transactions ("
																				  + trxQty
																				  + ") do not fit the control transaction quantities ("
																				  + esrImportFile.getESR_Control_Trx_Qty()
																				  + "). The document will not be processed."));
		}
		esrImportDAO.save(esrImportFile);
	}

	@VisibleForTesting
	boolean evaluateTrxQty(
			@NonNull final I_ESR_ImportFile esrImportFile,
			final int trxQty)
	{
		if (InterfaceWrapperHelper.isNull(esrImportFile, I_ESR_ImportFile.COLUMNNAME_ESR_Control_Trx_Qty))
		{
			// in the case of camt-54, the value is not mandatory. If it is not provided, we need to assume that it's OK
			return true;
		}

		final boolean fitTrxQtys = (new BigDecimal(trxQty).compareTo(esrImportFile.getESR_Control_Trx_Qty()) == 0);
		return fitTrxQtys;
	}

	@VisibleForTesting
	public void evaluateLine(@NonNull final I_ESR_ImportLine importLine)
	{
		if (isReverseBookingLine(importLine))
		{
			// set payment action
			importLine.setESR_Payment_Action(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Reverse_Booking);

			// set error message for the user

			ESRDataLoaderUtil.addMatchErrorMsg(importLine, msgBL.getMsg(Env.getCtx(), ESRConstants.ESR_Reverse_Booking));
		}
		final I_ESR_ImportFile esrImportFile = esrImportDAO.getImportFileById(importLine.getESR_ImportFile_ID());
		// post account number
		if (esrImportFile.getC_BP_BankAccount_ID() > 0) // TODO this might not be the case in unit tests.
		{
			ESRDataLoaderUtil.evaluateESRAccountNumber(esrImportFile, importLine);
		}

		// The reference number of the ESR Import line
		ESRDataLoaderUtil.evaluateEsrReferenceNumber(importLine);

		// task 05917: check if the the payment date from the ESR file is OK for us
		try
		{
			periodBL.testPeriodOpen(Env.getCtx(), importLine.getPaymentDate(), DocBaseType.APPayment, importLine.getAD_Org_ID());
		}
		catch (final PeriodClosedException p)
		{
			ESRDataLoaderUtil.addMatchErrorMsg(importLine, p.getLocalizedMessage());
		}

		// Done with interesting data.
		//
		// Update IsValid flag
		final boolean isValid = Check.isBlank(importLine.getImportErrorMsg()) && Check.isBlank(importLine.getMatchErrorMsg());
		importLine.setIsValid(isValid);
		if (isValid)
		{
			importLine.setESR_Document_Status(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_TotallyMatched);
		}
		esrImportDAO.save(importLine);
	}

	/**
	 * Groups the given lines so that we can create one payment for each line.
	 * <p>
	 * to be more specific, the lines are grouped the following properties:
	 * <ul>
	 * <li>AD_Org_ID</li>
	 * <li>C_BPartner_ID</li>
	 * <li>C_Invoice_ID</li>
	 * <li>AccountNo</li>
	 * <li>AccountingDate</li>
	 * <li>PaymentDate</li>
	 * </ul>
	 */
	private Map<ArrayKey, List<I_ESR_ImportLine>> groupLines(final List<I_ESR_ImportLine> importedLines)
	{
		final Map<ArrayKey, List<I_ESR_ImportLine>> key2Lines = new HashMap<>();

		for (final I_ESR_ImportLine line : importedLines)
		{

			if (ESRTransactionLineMatcherUtil.isControlLine(line))
			{
				continue;
			}

			final ArrayKey key = mkESRLineKey(line);

			List<I_ESR_ImportLine> linesOfInvoice = key2Lines.computeIfAbsent(key, k -> new ArrayList<>());
			linesOfInvoice.add(line);

		}

		return key2Lines;
	}

	private ArrayKey mkESRLineKey(final I_ESR_ImportLine line)
	{
		final ArrayKey key;

		if (line.getC_Invoice_ID() > 0
				&& line.getC_Invoice().getAD_Org_ID() == line.getAD_Org_ID())
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

	@Override
	public int process(final I_ESR_Import esrImport)
	{
		final IMutable<Integer> processedLinesCount = new Mutable<>();
		lockAndProcess(esrImport, () -> {
			final int linesCount = processAndCountLines(esrImport);
			processedLinesCount.setValue(linesCount);
		});

		return processedLinesCount.getValue();
	}

	private int processAndCountLines(final I_ESR_Import esrImport)
	{
		if (esrImport.isProcessed())
		{
			throw AdempiereException.newWithTranslatableMessage("@Processed@: @Y@");
		}

		final List<I_ESR_ImportFile> allFiles = esrImportDAO.retrieveActiveESRImportFiles(esrImport);

		int totalNumberOfLines = 0;
		for (I_ESR_ImportFile file : allFiles)
		{
			final int numberOfLinesFromFile = processLinesFromFile(file);

			if (file.getDescription() != null)
			{
				esrImport.setDescription(esrImport.getDescription() + file.getDescription());
			}
			esrImportDAO.saveOutOfTrx(esrImport); // out of transaction: we want to not be rollback

			totalNumberOfLines = totalNumberOfLines + numberOfLinesFromFile;
		}

		// cg: just make sure that the esr import is not set to processed too early
		if (areAllFilesProcessed(allFiles))
		{
			esrImport.setProcessed(true);
			esrImportDAO.save(esrImport);
		}
		return totalNumberOfLines;
	}

	private int processLinesFromFile(final I_ESR_ImportFile file)
	{
		final List<I_ESR_ImportLine> allLines = esrImportDAO.retrieveActiveESRImportLinesFromFile(file);
		final List<I_ESR_ImportLine> linesToProcess = new ArrayList<>();

		try
		{
			if (allLines.isEmpty())
			{
				throw AdempiereException.noLines();
			}

			for (final I_ESR_ImportLine line : allLines)
			{
				final boolean lineWasAlreadyHandled = handleEsrImportLine(line);
				if (!lineWasAlreadyHandled)
				{
					linesToProcess.add(line);
				}
			}

			final Map<ArrayKey, List<I_ESR_ImportLine>> invoiceKey2Line = groupLines(linesToProcess);
			// 03808: Check if sum is not negative for any group

			final boolean isPositiveGroupLinesAmount = checkPositiveGroupLinesAmount(invoiceKey2Line);

			Check.assume(isPositiveGroupLinesAmount, MSG_GroupLinesNegativeAmount);

			final Set<ArrayKey> keysSeen = new HashSet<>();

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
					trxManager.runInThreadInheritedTrx(() -> processLinesNoInvoice(linesNoInvoices));
				}
				else
				{
					final List<I_ESR_ImportLine> linesForKey = invoiceKey2Line.get(key);
					try
					{
						trxManager.runInThreadInheritedTrx(() -> processLinesWithInvoice(linesForKey));
					}
					catch (final Exception e)
					{
						logger.warn(e.getLocalizedMessage(), e);
						for (final I_ESR_ImportLine lineWithError : linesForKey)
						{
							ESRDataLoaderUtil.addMatchErrorMsg(lineWithError, e.getLocalizedMessage());
							esrImportDAO.save(lineWithError);
						}
					}
				}
			}

			esrImportDAO.save(file);
			return linesToProcess.size();
		}
		catch (final Exception e)
		{
			// if there is an an assumption error, catch it to add a message and the release it
			final String message = e.getMessage();
			if (message != null && message.startsWith("Assumption failure:"))
			{
				file.setDescription(file.getDescription() + " > Fehler: Es ist ein Fehler beim Import aufgetreten! " + e.getLocalizedMessage());
				esrImportDAO.saveOutOfTrx(file); // out of transaction: we want to not be rollback
			}

			throw AdempiereException.wrapIfNeeded(e);
		}
		finally
		{
			// cg: just make sure that the esr import is not set to processed too early
			if (areAllLinesProcessed(allLines))
			{
				file.setProcessed(true);
				esrImportDAO.save(file);
			}
		}
	}

	private boolean handleEsrImportLine(@NonNull final I_ESR_ImportLine line)
	{
		// skip the control line
		if (ESRTransactionLineMatcherUtil.isControlLine(line))
		{
			line.setESR_Payment_Action(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Control_Line);
			esrImportDAO.save(line);
			return true;
		}

		// skip reverse booking lines from regular processing
		// the admin should deal with them manually
		if (isReverseBookingLine(line))
		{
			line.setESR_Payment_Action(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Reverse_Booking);
			handleUnsupportedTrxType(line);
			esrImportDAO.save(line);
			return true;
		}
		if (ESRConstants.ESRTRXTYPE_UNKNOWN.equals(line.getESRTrxType()))
		{
			line.setESR_Payment_Action(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Unable_To_Assign_Income);
			handleUnsupportedTrxType(line);
			esrImportDAO.save(line);
			return true;
		}

		// Skip already processed lines
		if (line.isProcessed())
		{
			return true;
		}

		// skip lines that have a payment, but are not are not yet processed (because a user needs to select an action)
		// 08500: skip the lines with payments
		refresh(line);
		if (line.getC_Payment_ID() > 0)
		{
			return true;
		}
		// Check/Validate
		if (!line.isValid())
		{
			evaluateLine(line);
		}
		// finally, skip lines that have no bpartner set
		if (line.getC_BPartner_ID() <= 0)
		{
			return true;
		}

		refresh(line);
		final PaymentId paymentId = fetchDuplicatePaymentIfExists(line);
		if (paymentId != null)
		{
			line.setESR_Payment_Action(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Duplicate_Payment);
			handleUnsupportedTrxType(line);
			line.setC_Payment_ID(paymentId.getRepoId());
			esrImportDAO.save(line);
			return true;
		}
		return false;
	}

	private PaymentId fetchDuplicatePaymentIfExists(@NonNull final I_ESR_ImportLine line)
	{
		final Optional<PaymentId> existentPaymentId = esrImportDAO.findExistentPaymentId(line);

		return existentPaymentId.orElse(null);
	}

	/**
	 * task https://github.com/metasfresh/metasfresh/issues/2118
	 */
	private void handleUnsupportedTrxType(@NonNull final I_ESR_ImportLine line)
	{
		final boolean flagUnsupportedTypesAsDone = sysConfigBL.getBooleanValue(CFG_PROCESS_UNSPPORTED_TRX_TYPES, false, line.getAD_Client_ID(), line.getAD_Org_ID());
		if (flagUnsupportedTypesAsDone)
		{
			line.setIsValid(true);
			line.setProcessed(true);
		}
	}

	/* package */
	void processLinesNoInvoice(final List<I_ESR_ImportLine> linesNoInvoices)
	{
		for (final I_ESR_ImportLine line : linesNoInvoices)
		{
			if (line.getC_Payment_ID() > 0)
			{
				continue; // 04607 whatever we do, don't create another payment if there is already one
			}

			refresh(line);
			if (line.getC_Payment_ID() > 0)
			{
				return;
			}

			final I_C_Payment payment = createUnlinkedPaymentForLine(line, line.getAmount());
			Check.assume(payment.getAD_Org_ID() == line.getAD_Org_ID(), "Payment has the same org as {}", line);

			final BankAccountId bankAccountId = BankAccountId.ofRepoId(line.getESR_Import().getC_BP_BankAccount_ID());
			final BankAccount bankAccount = bpBankAccountDAO.getById(bankAccountId);

			payment.setC_Currency_ID(bankAccount.getCurrencyId().getRepoId());

			if (sysConfigBL.getBooleanValue(ESRConstants.SYSCONFIG_EAGER_PAYMENT_ALLOCATION, true)) // task 09167: calling with true to preserve the old behavior
			{
				payment.setIsAutoAllocateAvailableAmt(true); // task 07783
			}
			paymentBL.save(payment);
			// guard; there was some crappy beforeSave() code in MPayment, there might be more
			Check.assume(payment.getAD_Org_ID() == line.getAD_Org_ID(), "Payment has the same org as {}", line);

			line.setC_Payment_ID(payment.getC_Payment_ID());

			// despite the amounts are fitting, we can't set this status, because there was a problem with the invoice that we found (e.g. wrong org),
			// and the user might for example want to transfer the money back to the customer
			// however, if we set the action to 'F', it will become read-only and (maybe worse) the user does not see that she still has to decide and/or do something in this case.
			// if (line.getC_Payment().getPayAmt().compareTo(line.getESR_Invoice_Openamt()) == 0)
			// {
			// line.setESR_Payment_Action(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Fit_Amounts);
			// }
			esrImportDAO.save(line);

			documentBL.processEx(payment, IDocument.ACTION_Complete, IDocument.STATUS_Completed);

			esrImportDAO.save(line);
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

		return paymentBL.newInboundReceiptBuilder()
				.adOrgId(OrgId.ofRepoId(line.getAD_Org_ID()))
				.orgBankAccountId(BankAccountId.ofRepoIdOrNull(line.getESR_Import().getC_BP_BankAccount_ID()))
				.accountNo(line.getAccountNo())
				.dateAcct(TimeUtil.asLocalDate(line.getAccountingDate()))
				.dateTrx(TimeUtil.asLocalDate(line.getPaymentDate()))
				.bpartnerId(BPartnerId.ofRepoId(line.getC_BPartner_ID()))
				.tenderType(TenderType.DirectDeposit)
				.payAmt(payAmt)
				.createNoSave();
	}

	private boolean isInvoicePaidInCurrentImport(final List<I_ESR_ImportLine> lines, final I_C_Invoice invoice)
	{

		for (final I_ESR_ImportLine line : lines)
		{
			if (line.getC_Payment_ID() > 0)
			{
				final I_C_Payment payment = paymentDAO.getById(PaymentId.ofRepoId(line.getC_Payment_ID()));
				if (payment.getC_Invoice_ID() == invoice.getC_Invoice_ID())
				{
					return true;
				}
			}
		}

		return false;
	}

	private boolean checkPositiveGroupLinesAmount(final Map<ArrayKey, List<I_ESR_ImportLine>> invoiceKey2Line)
	{
		for (final ArrayKey key : invoiceKey2Line.keySet())
		{
			final List<I_ESR_ImportLine> linesForKey = invoiceKey2Line.get(key);

			BigDecimal sum = BigDecimal.ZERO;
			for (final I_ESR_ImportLine line : linesForKey)
			{
				sum = sum.add(line.getAmount());
			}
			if (sum.compareTo(BigDecimal.ZERO) < 0)
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * Creates a payment for the given lines. Assumes that all lines reference the same invoice. Method is default visible to make it testable.
	 */
	/* package */void processLinesWithInvoice(final List<I_ESR_ImportLine> importLines)
	{
		// 04607 whatever we do, don't create another payment if there is already one
		final List<I_ESR_ImportLine> importLinesWithoutPayment = new ArrayList<>();

		// 05808: lines with payments with no invoices set should be also processed
		final List<I_ESR_ImportLine> importLinesWithPaymentNoInvoice = new ArrayList<>();

		for (final I_ESR_ImportLine importLine : importLines)
		{
			// 05808: Also process lines that have payments, but payments do not have invoices
			if (importLine.getC_Payment_ID() <= 0)
			{
				importLinesWithoutPayment.add(importLine);
			}
			else
			{
				final I_C_Payment payment = paymentDAO.getById(PaymentId.ofRepoId(importLine.getC_Payment_ID()));
				if (payment.getC_Invoice_ID() <= 0)
				{
					importLinesWithPaymentNoInvoice.add(importLine);
				}
			}
		}

		if (!importLinesWithPaymentNoInvoice.isEmpty())
		{
			processLinesWithPaymentNoInvoice(importLinesWithPaymentNoInvoice, true);
		}
		if (importLinesWithoutPayment.isEmpty())
		{
			return; // nothing to do
		}

		processLinesWithPaymentNoInvoice(importLinesWithoutPayment, false);
	}

	private void processLinesWithPaymentNoInvoice(final List<I_ESR_ImportLine> importLinesWithPaymentNoInvoice, final boolean withPayment)
	{
		for (final I_ESR_ImportLine importLine : importLinesWithPaymentNoInvoice)
		{
			final BigDecimal sum = importLine.getAmount();

			refresh(importLine);

			int importLinePaymentRecordId = importLine.getC_Payment_ID();

			final PaymentId paymentId = PaymentId.ofRepoIdOrNull(importLinePaymentRecordId);
			I_C_Payment payment = paymentId == null ? null
					: paymentDAO.getById(paymentId);

			if (withPayment)
			{
				Check.assumeNotNull(payment, "{} has a payment", importLine);
			}
			else
			{

				if (importLinePaymentRecordId <= 0)
				{
					payment = createUnlinkedPaymentForLine(importLine, sum);

					if (sysConfigBL.getBooleanValue(ESRConstants.SYSCONFIG_EAGER_PAYMENT_ALLOCATION, true)) // task 09167: calling with true to preserve the old behavior
					{
						payment.setIsAutoAllocateAvailableAmt(true); // task 07783
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

			final BigDecimal invoiceOpenAmt = invoiceDAO.retrieveOpenAmt(invoice);
			final BigDecimal overUnderAmt = sum.subtract(invoiceOpenAmt);
			if (X_ESR_ImportLine.ESR_DOCUMENT_STATUS_TotallyMatched.equals(importLine.getESR_Document_Status())
					&& !isInvoicePaidInCurrentImport(importLinesWithPaymentNoInvoice, invoice) && BigDecimal.ZERO.compareTo(overUnderAmt) == 0)
			{
				payment.setC_Invoice_ID(invoice.getC_Invoice_ID());
				payment.setOverUnderAmt(overUnderAmt);
			}

			payment.setDocStatus(IDocument.STATUS_Drafted);
			payment.setDocAction(IDocument.ACTION_Complete);

			paymentBL.save(payment);

			// guard; there was some crappy code in MPayment, there might be more
			Check.assume(payment.getAD_Org_ID() == importLine.getAD_Org_ID(), "Payment has the same org as {}", importLine);

			documentBL.processEx(payment, IDocument.ACTION_Complete, IDocument.STATUS_Completed);

			importLine.setC_Payment_ID(payment.getC_Payment_ID());
			esrImportDAO.save(importLine);

			updateLinesOpenAmt(importLine, invoice); // note that there might be further lines for this invoice
			esrImportDAO.save(importLine); // saving, because updateLinesOpenAmt doesn't save the line it was called with
		}

	}

	@Override
	public void linkInvoiceToPayment(final I_ESR_ImportLine importLine)
	{
		final I_C_Payment payment = paymentDAO.getById(PaymentId.ofRepoId(importLine.getC_Payment_ID()));

		Check.assume(payment.getAD_Org_ID() == importLine.getAD_Org_ID(), "Payment has the same org as {}", importLine);

		Check.assume(!Objects.equals(payment.getDocStatus(), IDocument.STATUS_Drafted), "Payment shoould not be drafted}");

		final I_C_Invoice invoice = importLine.getC_Invoice();
		Check.assumeNotNull(invoice, "{} has an invoice", importLine);

		// make sure that we don't end up with inter-org allocations
		Check.assume(invoice.getAD_Org_ID() == payment.getAD_Org_ID(), "Invoice {} and payment {} have the same AD_Org_ID");

		final String trxName = trxManager.getThreadInheritedTrxName(OnTrxMissingPolicy.ReturnTrxNone);
		trxManager.run(trxName, (TrxRunnable)trxName1 -> {
			// must assure that the invoice has transaction
			refresh(invoice, trxName1);

			final boolean ignoreIsAutoAllocateAvailableAmt = true; // task 09167: when processing ESR lines (i.e. from this method) we always allocate the payment to the invoice.

			allocationBL.autoAllocateSpecificPayment(invoice,
													 payment,
													 ignoreIsAutoAllocateAvailableAmt);
			esrImportDAO.save(importLine); // saving, because updateLinesOpenAmt doesn't save the line it was called with
		});

	}

	@Override
	public int calculateESRCheckDigit(@NonNull final String text)
	{
		return new ESRCheckDigitBuilder().calculateESRCheckDigit(text);
	}

	@Override
	public boolean isProcessed(final I_ESR_Import esrImport)
	{
		final List<I_ESR_ImportLine> allLines = esrImportDAO.retrieveLines(esrImport);
		return areAllLinesProcessed(allLines);
	}

	private boolean areAllLinesProcessed(final List<I_ESR_ImportLine> allLines)
	{
		if (Check.isEmpty(allLines))
		{
			return false;
		}
		return allLines.stream()
				.allMatch(line -> line.isProcessed());
	}

	private boolean areAllFilesProcessed(final List<I_ESR_ImportFile> allFiles)
	{
		if (Check.isEmpty(allFiles))
		{
			return false;
		}

		return allFiles.stream()
				.allMatch(file -> file.isProcessed());
	}

	@Override
	public void complete(
			final I_ESR_Import esrImport,
			final String message)
	{
		if (esrImport.isProcessed())
		{
			throw AdempiereException.newWithTranslatableMessage("@Processed@: @Y@");
		}

		process(esrImport);

		final List<I_ESR_ImportFile> allFiles = esrImportDAO.retrieveActiveESRImportFiles(esrImport);

		for (final I_ESR_ImportFile file : allFiles)
		{
			processLinesFromFile(message, file);
		}

		// cg: just make sure that the esr import is not set to processed too early
		if (areAllFilesProcessed(allFiles))
		{
			esrImport.setProcessed(true);
			esrImportDAO.save(esrImport);
		}

	}

	private void processLinesFromFile(final String message, final I_ESR_ImportFile file)
	{
		final List<I_ESR_ImportLine> linesOfFile = esrImportDAO.retrieveActiveESRImportLinesFromFile(file);
		for (final I_ESR_ImportLine line : linesOfFile)
		{
			handleEsrImportLine(message, line);
		}

		if (areAllLinesProcessed(linesOfFile))
		{
			file.setProcessed(true);
			esrImportDAO.save(file);
		}
	}

	private void handleEsrImportLine(final String message, final I_ESR_ImportLine line)
	{
		if (line.isProcessed())
		{
			return;
		}
		if (!line.isActive())
		{
			return;
		}

		// check partners first
		final BPartnerId esrPartnerId = BPartnerId.ofRepoIdOrNull(line.getC_BPartner_ID());
		final I_C_BPartner invPartner = line.getC_Invoice_ID() > 0
				? bpartnerDAO.getById(line.getC_Invoice().getC_BPartner_ID())
				: null;

		final PaymentId importLinePaymentId = PaymentId.ofRepoIdOrNull(line.getC_Payment_ID());
		final I_C_Payment importLinePayment = importLinePaymentId == null ? null
				: paymentDAO.getById(importLinePaymentId);

		final int paymentPartnerRecordId = importLinePayment == null ? -1
				: importLinePayment.getC_BPartner_ID();

		final I_C_BPartner paymentPartner = paymentPartnerRecordId <= 0 ? null
				: bpartnerDAO.getById(paymentPartnerRecordId);

		if (esrPartnerId != null)
		{
			if (invPartner != null)
			{
				if (invPartner.getC_BPartner_ID() != esrPartnerId.getRepoId())
				{
					final AdempiereException ex = AdempiereException.newWithTranslatableMessage("@" + ESRConstants.ESR_DIFF_INV_PARTNER + "@");
					logger.warn(ex.getLocalizedMessage(), ex);
					ESRDataLoaderUtil.addMatchErrorMsg(line, ex.getLocalizedMessage());
					esrImportDAO.save(line);
					return;
				}
			}

			if (paymentPartner != null)
			{
				if (paymentPartner.getC_BPartner_ID() != esrPartnerId.getRepoId())
				{
					final AdempiereException ex = AdempiereException.newWithTranslatableMessage("@" + ESRConstants.ESR_DIFF_PAYMENT_PARTNER + "@");
					logger.warn(ex.getLocalizedMessage(), ex);
					ESRDataLoaderUtil.addMatchErrorMsg(line, ex.getLocalizedMessage());
					esrImportDAO.save(line);
					return;
				}
			}
		}

		final String actionType = line.getESR_Payment_Action();
		if (Check.isEmpty(actionType, true))
		{
			final AdempiereException ex = AdempiereException.newWithTranslatableMessage("@" + ESRConstants.ERR_ESR_LINE_WITH_NO_PAYMENT_ACTION + "@");
			logger.warn(ex.getLocalizedMessage(), ex);
			ESRDataLoaderUtil.addMatchErrorMsg(line, ex.getLocalizedMessage());
			esrImportDAO.save(line);
			return;
		}

		final IESRActionHandler handler = handlers.get(actionType);
		if (handler == null)
		{
			final AdempiereException ex = AdempiereException.newWithTranslatableMessage("@NotSupported@ @ESR_Payment_Action@: " + actionType);
			logger.warn(ex.getLocalizedMessage(), ex);
			ESRDataLoaderUtil.addMatchErrorMsg(line, ex.getLocalizedMessage());
			esrImportDAO.save(line);
			return;
		}

		try
		{
			final boolean processed = handler.process(line, message);
			line.setProcessed(processed);
		}
		catch (final Exception e)
		{
			logger.warn(e.getLocalizedMessage(), e);
			ESRDataLoaderUtil.addMatchErrorMsg(line, e.getLocalizedMessage());
			esrImportDAO.save(line);
			return;
		}

		esrImportDAO.save(line);
	}

	private boolean isReverseBookingLine(final I_ESR_ImportLine line)
	{
		final String trxType = line.getESRTrxType();
		return ESRConstants.ESRTRXTYPE_ReverseBooking.equalsIgnoreCase(trxType);
	}

	@Override
	public void registerActionHandler(final String actionName, final IESRActionHandler handler)
	{
		handlers.put(actionName, handler);
	}

	@Override
	public void setInvoice(@NonNull final I_ESR_ImportLine importLine, @NonNull final I_C_Invoice invoice)
	{

		// we always update the open amount, even if the invoice reference hasn't changed
		updateLinesOpenAmt(importLine, invoice);

		// cg: if we have a payment and the open amount matches pay amount set status allocate with current invoice
		if (importLine.getC_Invoice_ID() == invoice.getC_Invoice_ID() && importLine.getC_Payment_ID() > 0)
		{
			final BigDecimal externalAllocationsSum = allocationDAO.retrieveAllocatedAmtIgnoreGivenPaymentIDs(invoice, ImmutableSet.of());
			final BigDecimal invoiceOpenAmt = invoice.getGrandTotal().subtract(externalAllocationsSum);
			final PaymentId paymentId = fetchDuplicatePaymentIfExists(importLine);
			if (importLine.getAmount().compareTo(invoiceOpenAmt) == 0)
			{
				if (paymentId != null)
				{
					importLine.setESR_Payment_Action(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Duplicate_Payment);
				}
				else
				{
					importLine.setESR_Payment_Action(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Allocate_Payment_With_Current_Invoice);
				}
			}

			else if (invoice.isPaid() && paymentId != null)
			{
				importLine.setESR_Payment_Action(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Duplicate_Payment);
			}
			else if (X_ESR_ImportLine.ESR_DOCUMENT_STATUS_PartiallyMatched.equals(importLine.getESR_Document_Status()) && paymentId == null)
			{
				importLine.setESR_Payment_Action(null);
			}

			final I_C_Payment payment = paymentDAO.getById(PaymentId.ofRepoId(importLine.getC_Payment_ID()));

			if (invoice.isPaid() && !paymentBL.isMatchInvoice(payment, invoice))
			{
				ESRDataLoaderUtil.addMatchErrorMsg(importLine, "Rechnung " + invoice.getDocumentNo() + " wurde im System als bereits bezahlt markiert");
				importLine.setESR_Document_Status(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_PartiallyMatched);
			}
		}
		else if (invoice.isPaid())
		{
			ESRDataLoaderUtil.addMatchErrorMsg(importLine, "Rechnung " + invoice.getDocumentNo() + " wurde im System als bereits bezahlt markiert");
			importLine.setESR_Document_Status(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_PartiallyMatched);
		}

		if (invoice.getAD_Org_ID() != importLine.getAD_Org_ID())
		{
			final Properties ctx = getCtx(importLine);

			final String invoiceOrgName = orgsRepo.retrieveOrgValue(invoice.getAD_Org_ID());
			final String importLineOrgName = orgsRepo.retrieveOrgValue(importLine.getAD_Org_ID());

			ESRDataLoaderUtil.addMatchErrorMsg(importLine,
											   msgBL.getMsg(ctx, ESR_NO_HAS_WRONG_ORG_2P, new Object[] {
													   invoiceOrgName,
													   importLineOrgName }));
		}

		importLine.setC_Invoice_ID(invoice.getC_Invoice_ID());
		importLine.setC_BPartner_ID(invoice.getC_BPartner_ID());

		importLine.setESR_Invoice_Grandtotal(invoice.getGrandTotal());

	}

	@Override
	public void reverseAllocationForPayment(final I_C_Payment payment)
	{
		final Properties ctx = getCtx(payment);
		final String trxName = getTrxName(payment);

		final MAllocationHdr[] allocs = MAllocationHdr.getOfPayment(ctx, payment.getC_Payment_ID(), trxName);
		for (final MAllocationHdr alloc : allocs)
		{
			documentBL.processEx(alloc, IDocument.ACTION_Reverse_Correct, IDocument.STATUS_Reversed);
		}

		payment.setC_Invoice_ID(-1);
	}

	/**
	 * Updates all lines that reference the given {@code esrImportLine}'s import and the given {@code invoice}. For those lines that already reference a payment, this method may also set the
	 * {@code Processed} flag and may set the {@code ESR_Payment_Action} to {@link X_ESR_ImportLine#ESR_PAYMENT_ACTION_Fit_Amounts}. Note that this method also saves all updated lines <b>besides</b>
	 * the given {@code esrImportLine}.
	 * <p>
	 * <b>IMPORTANT:</b> as written this method might update the given {@code esrImportLine}, but does <b>not</b> save it. The decision to save or not is left to the caller.
	 */
	/* package */
	void updateLinesOpenAmt(@NonNull final I_ESR_ImportLine esrImportLine, @NonNull final I_C_Invoice invoice)
	{
		final List<I_ESR_ImportLine> linesWithSameInvoice = esrImportDAO.retrieveLinesForInvoice(esrImportLine, invoice);

		updateOpenAmtAndStatusDontSave(invoice, linesWithSameInvoice);

		for (final I_ESR_ImportLine lineToSave : linesWithSameInvoice)
		{
			if (lineToSave != esrImportLine)
			{
				// not saving the given line, because this method might be called out of an MV already
				esrImportDAO.save(lineToSave);
			}
		}
	}

	// note: package level for testing purpose
	/* package */void updateOpenAmtAndStatusDontSave(@NonNull final I_C_Invoice invoice, final List<I_ESR_ImportLine> linesWithSameInvoice)
	{
		/*
			Can't use the DAO from above because of mocked tests
		 */
		final IAllocationDAO allocationDAOLocal = Services.get(IAllocationDAO.class);

		// We start by collecting the C_Payment_IDs from our lines
		final Set<PaymentId> linesOwnPaymentIDs = new HashSet<>();
		for (final I_ESR_ImportLine importLine : linesWithSameInvoice)
		{
			final PaymentId importLinePaymentId = PaymentId.ofRepoIdOrNull(importLine.getC_Payment_ID());
			final I_C_Payment importLinePayment = importLinePaymentId == null ? null
					: paymentDAO.getById(importLinePaymentId);

			// if the invoice is paid with the current line, exclude it from computing
			if (importLinePayment != null && paymentBL.isMatchInvoice(importLinePayment, invoice))
			{
				linesOwnPaymentIDs.add(importLinePaymentId);
			}
		}

		// Then we get the invoice GrandTotal MINUS the amounts that were already allocated from UNRELATED payments, credit-memos etc.
		// So in invoiceOpenAmt we IGNORE the payments of our lines..if there are no other payments or credit-memos, then the open amount is the invoice's GrandTotal, even if our lines actually paid
		// the whole invoice.
		final BigDecimal externalAllocationsSum = allocationDAOLocal.retrieveAllocatedAmtIgnoreGivenPaymentIDs(invoice, linesOwnPaymentIDs);
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
				if (fullyMatchedImportLine.getC_Payment_ID() > 0 && !X_ESR_ImportLine.ESR_PAYMENT_ACTION_Duplicate_Payment.equals(fullyMatchedImportLine.getESR_Payment_Action()))
				{
					final I_C_Payment payment = paymentDAO.getById(PaymentId.ofRepoId(fullyMatchedImportLine.getC_Payment_ID()));

					final int invoiceId = fullyMatchedImportLine.getC_Invoice_ID();
					final I_C_Invoice invoiceESR = invoiceId <= 0 ? null : invoiceBL.getById(InvoiceId.ofRepoId(invoiceId));

					if (invoiceESR != null && paymentBL.isMatchInvoice(payment, invoiceESR))
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

	private static byte[] computeMD5ChecksumAsBytes(final String filename)
	{
		try (final InputStream fis = new FileInputStream(filename))
		{
			return computeMD5ChecksumAsBytes(fis);
		}
		catch (final IOException ex)
		{
			throw AdempiereException.wrapIfNeeded(ex);
		}
	}

	private static byte[] computeMD5ChecksumAsBytes(final InputStream in)
	{
		try
		{
			final byte[] buffer = new byte[1024];
			final MessageDigest complete = MessageDigest.getInstance("MD5");
			int numRead;

			do
			{
				numRead = in.read(buffer);
				if (numRead > 0)
				{
					complete.update(buffer, 0, numRead);
				}
			}
			while (numRead != -1);

			return complete.digest();
		}
		catch (final IOException | NoSuchAlgorithmException ex)
		{
			throw AdempiereException.wrapIfNeeded(ex);
		}
	}

	private static final String convertMD5BytesToString(final byte[] md5)
	{
		String result = "";

		for (byte element : md5)
		{
			result += Integer.toString((element & 0xff) + 0x100, 16).substring(1);
		}
		return result;
	}

	@Override
	public String computeMD5Checksum(final String filename)
	{
		final byte[] md5 = computeMD5ChecksumAsBytes(filename);
		return convertMD5BytesToString(md5);
	}

	@Override
	public String computeMD5Checksum(final byte[] fileContent)
	{
		final byte[] md5 = computeMD5ChecksumAsBytes(new ByteArrayInputStream(fileContent));
		return convertMD5BytesToString(md5);
	}

	@Override
	public void unlinkESRImportLinesFromBankStatement(@NonNull final Collection<BankStatementLineId> bankStatementLineIds)
	{
		final List<I_ESR_ImportLine> esrImportLines = esrImportDAO.retrieveAllLinesByBankStatementLineIds(bankStatementLineIds);

		if (esrImportLines.isEmpty())
		{
			return;
		}

		for (final I_ESR_ImportLine esrImportLine : esrImportLines)
		{
			unlinkESRImportLineFromBankStatement(esrImportLine);
		}

		final ImmutableSet<ESRImportId> esrImportIds = extractESRImportIds(esrImportLines);
		updateESRImportReconciledStatus(esrImportIds);
	}

	@Override
	public void scheduleESRImportFor(final RunESRImportRequest runESRImportRequest)
	{
		final AttachmentEntry fromAttachmentEntry = attachmentEntryService.getById(runESRImportRequest.getAttachmentEntryId());

		ESRImportEnqueuer.newInstance()
				.esrImport(runESRImportRequest.getEsrImport())
				.fromDataSource(
						ESRImportEnqueuerDataSource.builder()
								.filename(fromAttachmentEntry.getFilename())
								.content(attachmentEntryService.retrieveData(fromAttachmentEntry.getId()))
								.attachmentEntryId(fromAttachmentEntry.getId())
								.build())
				.asyncBatchName(runESRImportRequest.getAsyncBatchName())
				.asyncBatchDesc(runESRImportRequest.getAsyncBatchDescription())
				.pinstanceId(runESRImportRequest.getPInstanceId())
				.loggable(runESRImportRequest.getLoggable())
				.execute();
	}

	@Override
	public void linkBankStatementLinesByPaymentIds(@NonNull final Map<PaymentId, BankStatementAndLineAndRefId> bankStatementLineRefIdIndexByPaymentId)
	{
		if (bankStatementLineRefIdIndexByPaymentId.isEmpty())
		{
			return;
		}

		final Set<PaymentId> paymentIds = bankStatementLineRefIdIndexByPaymentId.keySet();

		final List<I_ESR_ImportLine> esrImportLines = esrImportDAO.retrieveLines(paymentIds);

		final ImmutableMap<PaymentId, I_ESR_ImportLine> esrImportLinesByPaymentId = Maps.uniqueIndex(
				esrImportLines,
				esrImportLine -> PaymentId.ofRepoId(esrImportLine.getC_Payment_ID()));

		for (final Map.Entry<PaymentId, I_ESR_ImportLine> e : esrImportLinesByPaymentId.entrySet())
		{
			final PaymentId paymentId = e.getKey();
			final I_ESR_ImportLine esrImportLine = e.getValue();
			final BankStatementAndLineAndRefId bankStatementLineRefId = bankStatementLineRefIdIndexByPaymentId.get(paymentId);

			linkESRImportLineToBankStatement(esrImportLine, bankStatementLineRefId);
		}

		final ImmutableSet<ESRImportId> esrImportIds = extractESRImportIds(esrImportLines);
		updateESRImportReconciledStatus(esrImportIds);
	}

	@Override
	public I_ESR_Import getById(final ESRImportId esrImportId)
	{
		return esrImportDAO.getById(esrImportId);
	}

	private static ImmutableSet<ESRImportId> extractESRImportIds(@NonNull final List<I_ESR_ImportLine> esrImportLines)
	{
		return esrImportLines.stream()
				.map(esrImportLine -> ESRImportId.ofRepoId(esrImportLine.getESR_Import_ID()))
				.collect(ImmutableSet.toImmutableSet());
	}

	@VisibleForTesting
	void updateESRImportReconciledStatus(@NonNull final Set<ESRImportId> esrImportIds)
	{
		if (esrImportIds.isEmpty())
		{
			// shall NOT happen
			return;
		}

		final ImmutableSet<ESRImportId> notReconciledESRImportIds = esrImportDAO.retrieveNotReconciledESRImportIds(esrImportIds);

		for (final I_ESR_Import esrImport : esrImportDAO.getByIds(esrImportIds))
		{
			final ESRImportId esrImportnId = ESRImportId.ofRepoId(esrImport.getESR_Import_ID());
			final boolean isReconciled = !notReconciledESRImportIds.contains(esrImportnId);

			esrImport.setIsReconciled(isReconciled);
			esrImportDAO.save(esrImport);
		}
	}

	@Override
	public void linkESRImportLineToBankStatement(@NonNull final I_ESR_ImportLine esrImportLine, @NonNull final BankStatementAndLineAndRefId bankStatementLineRefId)
	{
		esrImportLine.setC_BankStatement_ID(bankStatementLineRefId.getBankStatementId().getRepoId());
		esrImportLine.setC_BankStatementLine_ID(bankStatementLineRefId.getBankStatementLineId().getRepoId());
		esrImportLine.setC_BankStatementLine_Ref_ID(bankStatementLineRefId.getBankStatementLineRefId().getRepoId());
		esrImportDAO.save(esrImportLine);
	}

	private void unlinkESRImportLineFromBankStatement(final I_ESR_ImportLine esrImportLine)
	{
		esrImportLine.setC_BankStatement_ID(-1);
		esrImportLine.setC_BankStatementLine_ID(-1);
		esrImportLine.setC_BankStatementLine_Ref_ID(-1);
		esrImportDAO.save(esrImportLine);
	}

	@Override
	public Set<PaymentId> getPaymentIds(@NonNull final ESRImportId esrImportId)
	{
		return esrImportDAO.retrieveLines(esrImportId)
				.stream()
				.map(line -> PaymentId.ofRepoIdOrNull(line.getC_Payment_ID()))
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
	}

}
