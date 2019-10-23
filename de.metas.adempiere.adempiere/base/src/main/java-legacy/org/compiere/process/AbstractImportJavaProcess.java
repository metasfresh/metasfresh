package org.compiere.process;

import de.metas.impexp.processing.IImportProcess;
import de.metas.impexp.processing.IImportProcessFactory;
import de.metas.process.JavaProcess;
import de.metas.process.RunOutOfTrx;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * {@link IImportProcess} to {@link JavaProcess} adapter.
 * 
 * @author tsa
 *
 * @param <ImportRecordType>
 */
public abstract class AbstractImportJavaProcess<ImportRecordType> extends JavaProcess
{
	private final Class<ImportRecordType> importRecordClass;
	private IImportProcess<ImportRecordType> importProcess;

	public AbstractImportJavaProcess(@NonNull final Class<ImportRecordType> importRecordClass)
	{
		this.importRecordClass = importRecordClass;
	}

	@Override
	protected final void prepare()
	{
		final IImportProcessFactory importProcessFactory = Services.get(IImportProcessFactory.class);
		importProcess = importProcessFactory.newImportProcess(importRecordClass);
		importProcess.setCtx(getCtx());
		importProcess.setParameters(getParameterAsIParams());
		importProcess.setLoggable(this);
	}

	// NOTE: we shall run this process out of transaction because the actual import process is managing the transaction
	@Override
	@RunOutOfTrx
	protected final String doIt()
	{
		importProcess.run();
		return MSG_OK;
	}

}
