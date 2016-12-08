package org.compiere.process;

import org.adempiere.impexp.IImportProcess;
import org.adempiere.impexp.IImportProcessFactory;
import org.adempiere.util.Check;
import org.adempiere.util.Services;

import de.metas.process.RunOutOfTrx;
import de.metas.process.JavaProcess;

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

	public AbstractImportJavaProcess(Class<ImportRecordType> importRecordClass)
	{
		super();

		Check.assumeNotNull(importRecordClass, "importRecordClass not null");
		this.importRecordClass = importRecordClass;
	}

	@Override
	protected final void prepare()
	{
		importProcess = Services.get(IImportProcessFactory.class).newImportProcess(importRecordClass);
		importProcess.setCtx(getCtx());
		importProcess.setParameters(getParameterAsIParams());
		importProcess.setLoggable(this);
	}

	// NOTE: we shall run this process out of transaction because the actual import process is managing the transaction
	@Override
	@RunOutOfTrx
	protected final String doIt() throws Exception
	{
		importProcess.run();
		return MSG_OK;
	}

}
