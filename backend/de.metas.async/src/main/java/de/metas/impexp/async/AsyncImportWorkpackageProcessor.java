package de.metas.impexp.async;

import org.compiere.SpringContextHolder;

import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.impexp.ImportRecordsRequest;
import de.metas.impexp.DataImportService;
import de.metas.impexp.processing.IImportProcess;

/**
 * Workpackage processor used to import records enqueued by {@link AsyncImportRecordsAsyncExecutor}.
 *
 * @author tsa
 *
 */
public class AsyncImportWorkpackageProcessor extends WorkpackageProcessorAdapter
{
	// services
	private final DataImportService dataImportService = SpringContextHolder.instance.getBean(DataImportService.class);

	/** @return false. IMPORTANT: let the {@link IImportProcess} manage the transactions */
	@Override
	public boolean isRunInTransaction()
	{
		return false;
	}

	@Override
	public Result processWorkPackage(
			final I_C_Queue_WorkPackage workpackage,
			final String localTrxName_NOTUSED)
	{
		final ImportRecordsRequest request = ImportRecordsRequest.ofParams(getParameters());
		dataImportService.validateAndImportRecordsNow(request);

		return Result.SUCCESS;
	}
}
