package de.metas.impexp.async;

import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.attachments.AttachmentEntryService;
import de.metas.impexp.DataImportService;
import de.metas.impexp.ImportRecordsRequest;
import de.metas.impexp.ValidateAndActualImportRecordsResult;
import de.metas.impexp.processing.IImportProcess;
import de.metas.util.Loggables;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;

import javax.annotation.Nullable;
import java.nio.file.Path;

/**
 * Workpackage processor used to import records enqueued by {@link AsyncImportRecordsAsyncExecutor}.
 *
 * @author tsa
 */
public class AsyncImportWorkpackageProcessor extends WorkpackageProcessorAdapter
{
	// services
	private final DataImportService dataImportService = SpringContextHolder.instance.getBean(DataImportService.class);
	private final AttachmentEntryService attachmentEntryService = SpringContextHolder.instance.getBean(AttachmentEntryService.class);

	/**
	 * @return false. IMPORTANT: let the {@link IImportProcess} manage the transactions
	 */
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
		final ValidateAndActualImportRecordsResult result = dataImportService.validateAndImportRecordsNow(request);

		attachMigrationScript(request.getLogMigrationScriptsSpec(), result);

		return Result.SUCCESS;
	}

	private void attachMigrationScript(
			@Nullable final ImportRecordsRequest.LogMigrationScriptsSpec logMigrationScriptsSpec,
			@NonNull final ValidateAndActualImportRecordsResult result)
	{
		if (logMigrationScriptsSpec == null)
		{
			return;
		}

		final Path sqlMigrationScript = result.getSqlMigrationScript();
		if (sqlMigrationScript == null)
		{
			return;
		}

		final TableRecordReference attachMigrationScriptsFileTo = logMigrationScriptsSpec.getAttachMigrationScriptsFileTo();
		if (attachMigrationScriptsFileTo == null)
		{
			return;
		}

		attachmentEntryService.createNewAttachment(attachMigrationScriptsFileTo, sqlMigrationScript);
		Loggables.addLog("Attached {} to {}", sqlMigrationScript, attachMigrationScriptsFileTo);
	}
}
