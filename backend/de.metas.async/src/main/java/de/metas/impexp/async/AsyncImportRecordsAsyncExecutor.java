package de.metas.impexp.async;

import java.util.Properties;

import org.compiere.util.Env;
import org.springframework.stereotype.Component;

import de.metas.async.QueueWorkPackageId;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.impexp.AsyncImportRecordsResponse;
import de.metas.impexp.ImportRecordsAsyncExecutor;
import de.metas.impexp.ImportRecordsRequest;
import de.metas.util.Services;
import lombok.NonNull;

@Component
public final class AsyncImportRecordsAsyncExecutor implements ImportRecordsAsyncExecutor
{
	private final IWorkPackageQueueFactory workPackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);

	@Override
	public AsyncImportRecordsResponse schedule(@NonNull final ImportRecordsRequest request)
	{
		final Properties ctx = Env.getCtx();
		final QueueWorkPackageId workpackageId = workPackageQueueFactory
				.getQueueForEnqueuing(ctx, AsyncImportWorkpackageProcessor.class)
				.newBlock()
				.setContext(ctx)
				.newWorkpackage()
				.parameters(request.toParams())
				.buildAndGetId();

		return AsyncImportRecordsResponse.builder()
				.workpackageId(workpackageId.getRepoId())
				.build();
	}
}
