package org.compiere.process;

import de.metas.impexp.processing.IImportProcess;
import de.metas.impexp.processing.IImportProcessFactory;
import de.metas.organization.OrgId;
import de.metas.process.JavaProcess;
import de.metas.process.RunOutOfTrx;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.service.ISysConfigBL;

/**
 * {@link IImportProcess} to {@link JavaProcess} adapter.
 *
 * @param <ImportRecordType>
 * @author tsa
 */
public abstract class AbstractImportJavaProcess<ImportRecordType> extends JavaProcess
{
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final IImportProcessFactory importProcessFactory = Services.get(IImportProcessFactory.class);
	private final Class<ImportRecordType> importRecordClass;

	private static final String SYSCONFIG_BatchSize = "Import.BatchSize";
	private static final int DEFAULT_BatchSize = 1000;

	public AbstractImportJavaProcess(@NonNull final Class<ImportRecordType> importRecordClass)
	{
		this.importRecordClass = importRecordClass;
	}

	// NOTE: we shall run this process out of transaction because the actual import process is managing the transaction
	@Override
	@RunOutOfTrx
	protected final String doIt()
	{
		newImportProcess().run();

		return MSG_OK;
	}

	private IImportProcess<ImportRecordType> newImportProcess()
	{
		final IImportProcess<ImportRecordType> importProcess = importProcessFactory.newImportProcess(importRecordClass)
				.async(false)
				.setCtx(getCtx())
				.setLoggable(this)
				.setParameters(getParameterAsIParams())
				.limit(QueryLimit.NO_LIMIT)
				.notifyUserId(getUserId());

		customizeImportProcess(importProcess);

		return importProcess;
	}

	protected void customizeImportProcess(@NonNull final IImportProcess<ImportRecordType> importProcess) {}

	protected final QueryLimit getBatchSize()
	{
		int batchSize = sysConfigBL.getIntValue(SYSCONFIG_BatchSize, -1, getClientId().getRepoId(), OrgId.ANY.getRepoId());
		return QueryLimit.ofInt(batchSize > 0 ? batchSize : DEFAULT_BatchSize);
	}

}
