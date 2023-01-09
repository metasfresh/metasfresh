package de.metas.async.model.validator;

import de.metas.async.AsyncBatchId;
import de.metas.async.QueueWorkPackageId;
import de.metas.async.api.IWorkpackageLogsRepository;
import de.metas.async.api.IWorkpackageParamDAO;
import de.metas.async.model.I_C_Queue_Element;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.service.AsyncBatchService;
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

/**
 * @author cg
 */
@Interceptor(I_C_Queue_WorkPackage.class)
@Component
public class C_Queue_WorkPackage
{
	private static final Logger logger = LogManager.getLogger(C_Queue_WorkPackage.class);

	private final AsyncBatchService asyncBatchService;

	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	public C_Queue_WorkPackage(@NonNull final AsyncBatchService asyncBatchService)
	{
		this.asyncBatchService = asyncBatchService;
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

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = {
			I_C_Queue_WorkPackage.COLUMNNAME_Processed,
			I_C_Queue_WorkPackage.COLUMNNAME_IsError
	})
	public void processBatchFromWP(@NonNull final I_C_Queue_WorkPackage wp)
	{
		if (wp.getC_Async_Batch_ID() <= 0)
		{
			return;
		}

		final String trxName = InterfaceWrapperHelper.getTrxName(wp);

		final AsyncBatchId asyncBatchId = AsyncBatchId.ofRepoId(wp.getC_Async_Batch_ID());
		trxManager
				.getTrxListenerManager(trxName)
				.runAfterCommit(() -> processBatchInOwnTrx(asyncBatchId));
	}

	private void processBatchInOwnTrx(@NonNull final AsyncBatchId asyncBatchId)
	{
		trxManager.runInNewTrx(() -> asyncBatchService.checkProcessed(asyncBatchId, ITrx.TRXNAME_ThreadInherited));
	}
}
