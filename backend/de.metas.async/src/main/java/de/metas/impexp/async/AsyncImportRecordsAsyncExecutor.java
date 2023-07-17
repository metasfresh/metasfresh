package de.metas.impexp.async;

import de.metas.async.QueueWorkPackageId;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.impexp.AsyncImportRecordsResponse;
import de.metas.impexp.ImportRecordsAsyncExecutor;
import de.metas.impexp.ImportRecordsRequest;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.util.Env;
import org.springframework.stereotype.Component;

import java.util.Properties;

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
				.newWorkPackage()
				.parameters(request.toParams())
				.buildAndGetId();

		return AsyncImportRecordsResponse.builder()
				.workpackageId(workpackageId.getRepoId())
				.build();
	}
}
