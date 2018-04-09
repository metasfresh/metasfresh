package de.metas.printing.async.spi.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.apache.commons.collections4.IteratorUtils;
import org.compiere.model.I_AD_Archive;
import org.compiere.model.I_AD_PInstance;
import org.compiere.model.PrintInfo;

import de.metas.adempiere.report.jasper.OutputType;
import de.metas.adempiere.report.jasper.client.JRClient;
import de.metas.async.Async_Constants;
import de.metas.async.api.IQueueDAO;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.i18n.ILanguageBL;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.Language;
import de.metas.printing.MergePdfByteArrays;
import de.metas.printing.PrintPackagePDFBuilder;
import de.metas.printing.api.IPrintPackageBL;
import de.metas.printing.api.IPrintingDAO;
import de.metas.printing.api.impl.PrintPackageCtx;
import de.metas.printing.model.I_C_Print_Job_Instructions;
import de.metas.printing.model.I_C_Print_Job_Line;
import de.metas.printing.model.I_C_Print_Package;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.printing.model.X_C_Print_Job_Instructions;
import de.metas.process.IADPInstanceDAO;
import de.metas.process.ProcessInfo;
import de.metas.process.ProcessInfoParameter;

/**
 * This processor process the enqueued <code>C_Print_Job_Instructions</code>
 * 
 * <ul> for each C_Print_Job_Instructions, take the job line</ul>
 * <ul> then fetches the print package</ul>
 * <ul> calibrate the pdf from print package</ul>
 * <ul> concatenates all pdf from print packages in one big pdf file per <code>C_Print_Job_Instructions</code></ul>
 * 
 * @author cg
 * 
 */
public class PDFDocPrintingWorkpackageProcessor implements IWorkpackageProcessor
{
	// services
	private final IPrintingDAO dao = Services.get(IPrintingDAO.class);
	private final IQueueDAO queueDAO = Services.get(IQueueDAO.class);
	
	private final String PDFArchiveName = "PDFDocPrintingWorkpackageProcessor_ArchiveName";
	private final String PDFPrintJob_Done = "PDFPrintingAsyncBatchListener_PrintJob_Done_2";
	
	public static final int SummaryPdfPrinting_AD_Process_ID = 540661;

	private I_C_Async_Batch asyncBatch;

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workpackage, final String localTrxName) 
	{
		this.asyncBatch = workpackage.getC_Async_Batch();
		Check.assumeNotNull(asyncBatch, "Async batch is not null");

		final List<I_C_Print_Job_Instructions> list = queueDAO.retrieveItems(workpackage, I_C_Print_Job_Instructions.class, localTrxName);
		for (final I_C_Print_Job_Instructions printjobInstructions : list)
		{
			InterfaceWrapperHelper.refresh(printjobInstructions, localTrxName);
			createPrintPackage(printjobInstructions, localTrxName);
			if (X_C_Print_Job_Instructions.STATUS_Error.equals(printjobInstructions.getStatus()))
			{
				throw new AdempiereException(printjobInstructions.getErrorMsg());
			}

			try
			{
				print(printjobInstructions, workpackage, localTrxName);
			}
			catch (Exception e)
			{
				throw new AdempiereException(e.getMessage());
			}
		}
		return Result.SUCCESS;
	}

	private void print(final I_C_Print_Job_Instructions jobInstructions, final I_C_Queue_WorkPackage workpackage, final String trxName) throws Exception
	{
		//
		// Extract print packages
		int countLines = 0;
		final Iterator<I_C_Print_Job_Line> jobLines = dao.retrievePrintJobLines(jobInstructions);
		final Map<Integer, I_C_Print_Package> printPackages = new LinkedHashMap<Integer, I_C_Print_Package>();

		// needs to be on true in order to have summary which currently in not working 
		// FIXME in a next working increment see gh2128
		// TODO in https://github.com/metasfresh/metasfresh/issues/2128
		boolean isCreateSummary = false; 
		
		for (final I_C_Print_Job_Line jobLine : IteratorUtils.asIterable(jobLines))
		{
			final int printPackageId = jobLine.getC_Print_Package_ID();
			if (printPackageId <= 0)
			{
				continue;
			}

			countLines++;

			if (printPackages.containsKey(printPackageId))
			{
				continue;
			}
			printPackages.put(printPackageId, jobLine.getC_Print_Package());
			
			if (jobLine.getC_Printing_Queue_ID() > 0)
			{
				final I_C_Printing_Queue pq = jobLine.getC_Printing_Queue();
				final String itemName = pq.getItemName();
				if (Check.isEmpty(itemName, true))
				{
					isCreateSummary = false;
				}
			}
		}

		for (final I_C_Print_Package printPackage : printPackages.values())
		{
			final byte[] pdfScaled = new PrintPackagePDFBuilder()
					.setPrintPackage(printPackage)
					.printToBytes();
			if (pdfScaled == null || pdfScaled.length == 0)
			{
				throw new AdempiereException("No PDF data printed!"); // TRL
			}
			final int currentIndex = workpackage.getBatchEnqueuedCount();
			final byte[] mergedPDF;
			if (isCreateSummary)
			{
				final int countExpected = asyncBatch.getCountExpected();
				final byte[] summary = createSummaryPage(jobInstructions, currentIndex, countExpected, countLines);
	
				mergedPDF = new MergePdfByteArrays()
						.add(summary)
						.add(pdfScaled)
						.getMergedPdfByteArray();
			}
			else
			{
				mergedPDF = new MergePdfByteArrays()
						.add(pdfScaled)
						.getMergedPdfByteArray();
			}
			

			// save in archive
			createArchive(printPackage, mergedPDF, asyncBatch, currentIndex, trxName);
		}

	}

	private byte[] createSummaryPage(final I_C_Print_Job_Instructions jobInstructions, final int index, final int countExpected, final int noInvoices)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(jobInstructions);
		final Language language = Services.get(ILanguageBL.class).getOrgLanguage(ctx, jobInstructions.getAD_Org_ID());
		
		final I_AD_PInstance pinstance = Services.get(IADPInstanceDAO.class).createAD_PInstance(ctx, SummaryPdfPrinting_AD_Process_ID, 0, 0);
		pinstance.setIsProcessing(true);
		InterfaceWrapperHelper.save(pinstance);
		
		final StringBuffer msg = new StringBuffer();
		msg.append(Services.get(IMsgBL.class)
				.getMsg(ctx, PDFPrintJob_Done, new Object[] {index, countExpected, noInvoices}));
		
		
		final List<ProcessInfoParameter> piParams = new ArrayList<>();
		piParams.add(ProcessInfoParameter.ofValueObject(X_C_Print_Job_Instructions.COLUMNNAME_C_Print_Job_Instructions_ID, jobInstructions.getC_Print_Job_Instructions_ID()));
		piParams.add(ProcessInfoParameter.ofValueObject("Title", msg.toString()));
		Services.get(IADPInstanceDAO.class).saveParameterToDB(pinstance.getAD_PInstance_ID(), piParams);
		
		final ProcessInfo jasperProcessInfo = ProcessInfo.builder()
				.setCtx(ctx)
				.setAD_Process_ID(SummaryPdfPrinting_AD_Process_ID)
				.setAD_PInstance(pinstance)
				.setReportLanguage(language)
				.setJRDesiredOutputType(OutputType.PDF)
				.build();
		
		final JRClient jrClient = JRClient.get();
		final byte[] pdf = jrClient.report(jasperProcessInfo);
		
		return pdf;

	}

	private void createArchive(I_C_Print_Package printPackage, byte[] data, final I_C_Async_Batch asyncBatch, final int current, final String trxName)
	{
		final String tableName = InterfaceWrapperHelper.getModelTableName(printPackage);
		final int adTableId = Services.get(IADTableDAO.class).retrieveTableId(tableName);
		final int recordId = InterfaceWrapperHelper.getId(printPackage);
		final Properties ctx = InterfaceWrapperHelper.getCtx(asyncBatch);

		final org.adempiere.archive.api.IArchiveBL archiveService = Services.get(org.adempiere.archive.api.IArchiveBL.class);
		PrintInfo printInfo = new PrintInfo(printPackage.getTransactionID() + "_" + printPackage.getBinaryFormat(), adTableId, recordId);
		final I_AD_Archive archive = archiveService.archive(data, printInfo, true, trxName);
		final de.metas.printing.model.I_AD_Archive directArchive = InterfaceWrapperHelper.create(archive, de.metas.printing.model.I_AD_Archive.class);
		directArchive.setIsDirectEnqueue(true);

		final Timestamp today = SystemTime.asDayTimestamp();
		final SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
		final String name = Services.get(IMsgBL.class).getMsg(ctx, PDFArchiveName) + "_" + dt.format(today) + "_PDF_" + current
				+ "_von_" + asyncBatch.getCountExpected() + "_" + asyncBatch.getC_Async_Batch_ID();
		directArchive.setName(name);
		
		//
		// set async batch
		InterfaceWrapperHelper.setDynAttribute(archive, Async_Constants.C_Async_Batch, asyncBatch);
		InterfaceWrapperHelper.save(directArchive);

	}

	private void createPrintPackage(final I_C_Print_Job_Instructions printjobInstructions, final String trxName)
	{
		final IPrintPackageBL printPackageBL = Services.get(IPrintPackageBL.class);

		final Properties ctx = InterfaceWrapperHelper.getCtx(printjobInstructions);
		final I_C_Print_Package printPackage = InterfaceWrapperHelper.create(ctx, I_C_Print_Package.class, trxName);

		// Creating the Print Package template (request):
		final String transactionId = UUID.randomUUID().toString();
		printPackage.setTransactionID(transactionId);
		InterfaceWrapperHelper.save(printPackage, trxName);

		final PrintPackageCtx printCtx = (PrintPackageCtx)printPackageBL.createEmptyInitialCtx();
		printCtx.setHostKey(printjobInstructions.getHostKey());
		printCtx.setTransactionId(printPackage.getTransactionID());

		printPackageBL.addPrintingDataToPrintPackage(printPackage, printjobInstructions, printCtx);

	}
}
