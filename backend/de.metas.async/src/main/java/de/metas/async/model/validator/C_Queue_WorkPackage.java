package de.metas.async.model.validator;

import de.metas.async.AsyncBatchId;
import de.metas.async.QueueWorkPackageId;
import de.metas.async.api.IWorkpackageLogsRepository;
import de.metas.async.api.IWorkpackageParamDAO;
import de.metas.async.asyncbatchmilestone.AsyncBatchMilestone;
import de.metas.async.asyncbatchmilestone.AsyncBatchMilestoneQuery;
import de.metas.async.asyncbatchmilestone.AsyncBatchMilestoneService;
import de.metas.async.model.I_C_Queue_Element;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.ModelValidator;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author cg
 */
@Interceptor(I_C_Queue_WorkPackage.class)
@Component
public class C_Queue_WorkPackage
{
	private static final Logger logger = LogManager.getLogger(C_Queue_WorkPackage.class);

	private final AsyncBatchMilestoneService asyncBatchMilestoneService;

	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	public C_Queue_WorkPackage(@NonNull final AsyncBatchMilestoneService asyncBatchMilestoneService)
	{
		this.asyncBatchMilestoneService = asyncBatchMilestoneService;
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteElements(@NonNull final I_C_Queue_WorkPackage wp)
	{
		final int deleteCount = Services.get(IQueryBL.class).createQueryBuilder(I_C_Queue_Element.class, wp)
				.addEqualsFilter(I_C_Queue_Element.COLUMN_C_Queue_WorkPackage_ID, wp.getC_Queue_WorkPackage_ID())
				.create()
				.delete();
		logger.info("Deleted {} {} that referenced the to-be-deleted {} ", deleteCount, I_C_Queue_Element.Table_Name, wp);
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteParams(@NonNull final I_C_Queue_WorkPackage wp)
	{
		final QueueWorkPackageId workpackageId = QueueWorkPackageId.ofRepoId(wp.getC_Queue_WorkPackage_ID());
		Services.get(IWorkpackageParamDAO.class).deleteWorkpackageParams(workpackageId);
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteLogs(@NonNull final I_C_Queue_WorkPackage wp)
	{
		final IWorkpackageLogsRepository logsRepository = SpringContextHolder.instance.getBean(IWorkpackageLogsRepository.class);

		final QueueWorkPackageId workpackageId = QueueWorkPackageId.ofRepoId(wp.getC_Queue_WorkPackage_ID());
		logsRepository.deleteLogsInTrx(workpackageId);
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = {
			I_C_Queue_WorkPackage.COLUMNNAME_Processed,
			I_C_Queue_WorkPackage.COLUMNNAME_IsError
	})
	public void processMilestoneFromWP(@NonNull final I_C_Queue_WorkPackage wp)
	{
		if (wp.getC_Async_Batch_ID() <= 0)
		{
			return;
		}

		final String trxName = InterfaceWrapperHelper.getTrxName(wp);

		final AsyncBatchId asyncBatchId = AsyncBatchId.ofRepoId(wp.getC_Async_Batch_ID());
		trxManager
				.getTrxListenerManager(trxName)
				.runAfterCommit(() -> processMilestoneInOwnTrx(wp, asyncBatchId));
	}

	private void processMilestoneInOwnTrx(
			@NonNull final I_C_Queue_WorkPackage wp,
			@NonNull final AsyncBatchId asyncBatchId)
	{
		trxManager.runInNewTrx(() -> {

			final AsyncBatchMilestoneQuery query = AsyncBatchMilestoneQuery.builder()
					.asyncBatchId(AsyncBatchId.ofRepoId(wp.getC_Async_Batch_ID()))
					.processed(false)
					.build();
			final List<AsyncBatchMilestone> milestones = asyncBatchMilestoneService.getByQuery(query);

			if (milestones.size() > 0)
			{
				asyncBatchMilestoneService.processAsyncBatchMilestone(asyncBatchId, milestones, ITrx.TRXNAME_ThreadInherited);
			}
		});
	}
}
