package de.metas.dunning.export.async;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import de.metas.async.api.IQueueDAO;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.async.spi.WorkpackagesOnCommitSchedulerTemplate;
import de.metas.dunning.DunningDocId;
import de.metas.dunning.export.DunningExportService;
import de.metas.dunning.model.I_C_DunningDoc;
import de.metas.logging.LogManager;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.List;

public class C_DunningDoc_CreateExportData implements IWorkpackageProcessor
{
	private static final Logger logger = LogManager.getLogger(C_DunningDoc_CreateExportData.class);

	private static final WorkpackagesOnCommitSchedulerTemplate<I_C_DunningDoc> //
	SCHEDULER = WorkpackagesOnCommitSchedulerTemplate
			.newModelScheduler(C_DunningDoc_CreateExportData.class, I_C_DunningDoc.class)
			.setCreateOneWorkpackagePerModel(true);

	public static void scheduleOnTrxCommit(final I_C_DunningDoc dunningDocRecord)
	{
		SCHEDULER.schedule(dunningDocRecord);
	}

	private final transient IQueueDAO queueDAO = Services.get(IQueueDAO.class);

	private final transient DunningExportService dunningExportService = SpringContextHolder.instance.getBean(DunningExportService.class);

	@Override
	public Result processWorkPackage(
			@NonNull final I_C_Queue_WorkPackage workpackage,
			@Nullable final String localTrxName)
	{
		final List<I_C_DunningDoc> dunningDocRecords = queueDAO.retrieveAllItemsSkipMissing(workpackage, I_C_DunningDoc.class);
		for (final I_C_DunningDoc dunningDocRecord : dunningDocRecords)
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("Going to export data for dunningDocRecord={}", dunningDocRecord);

			final DunningDocId dunningDocId = DunningDocId.ofRepoId(dunningDocRecord.getC_DunningDoc_ID());
			dunningExportService.exportDunnings(ImmutableList.of(dunningDocId));
		}
		return Result.SUCCESS;
	}
}
