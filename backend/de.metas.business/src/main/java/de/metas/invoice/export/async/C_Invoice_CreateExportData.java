package de.metas.invoice.export.async;

import java.util.List;

import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Invoice;
import org.slf4j.Logger;
import org.slf4j.MDC.MDCCloseable;

import com.google.common.collect.ImmutableList;

import ch.qos.logback.classic.Level;
import de.metas.async.api.IQueueDAO;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.async.spi.WorkpackagesOnCommitSchedulerTemplate;
import de.metas.invoice.export.InvoiceExportService;
import de.metas.invoice_gateway.spi.model.InvoiceId;
import de.metas.logging.LogManager;
import de.metas.logging.TableRecordMDC;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;

public class C_Invoice_CreateExportData implements IWorkpackageProcessor
{
	private static final Logger logger = LogManager.getLogger(C_Invoice_CreateExportData.class);

	private static final WorkpackagesOnCommitSchedulerTemplate<I_C_Invoice> //
	SCHEDULER = WorkpackagesOnCommitSchedulerTemplate
			.newModelScheduler(C_Invoice_CreateExportData.class, I_C_Invoice.class)
			.setCreateOneWorkpackagePerModel(true);

	public static void scheduleOnTrxCommit(final I_C_Invoice invoiceRecord)
	{
		SCHEDULER.schedule(invoiceRecord);
	}

	private final transient IQueueDAO queueDAO = Services.get(IQueueDAO.class);
	private final transient InvoiceExportService invoiceExportService = SpringContextHolder.instance.getBean(InvoiceExportService.class);

	@Override
	public Result processWorkPackage(
			@NonNull final I_C_Queue_WorkPackage workpackage,
			@NonNull final String localTrxName)
	{
		final List<I_C_Invoice> invoiceRecords = queueDAO.retrieveItemsSkipMissing(workpackage, I_C_Invoice.class, localTrxName);
		for (final I_C_Invoice invoiceRecord : invoiceRecords)
		{
			try (final MDCCloseable ignore = TableRecordMDC.putTableRecordReference(invoiceRecord))
			{
				Loggables.withLogger(logger, Level.DEBUG).addLog("Going to export data for invoiceRecord={}", invoiceRecord);

				final InvoiceId invoiceId = InvoiceId.ofRepoId(invoiceRecord.getC_Invoice_ID());
				invoiceExportService.exportInvoices(ImmutableList.of(invoiceId));
			}
		}
		return Result.SUCCESS;
	}
}
