package de.metas.contracts.impexp.processing;

import de.metas.contracts.impexp.process.ImportModularContractLog;
import de.metas.contracts.model.I_I_ModCntr_Log;
import de.metas.impexp.processing.IImportProcessFactory;
import org.springframework.context.annotation.Configuration;
import de.metas.util.Services;

@Configuration
public class ImportModularContractLogConfiguration
{
	public ImportModularContractLogConfiguration()
	{
		registerStandardImportProcesses();
	}

	private void registerStandardImportProcesses()
	{
		final IImportProcessFactory importProcessesFactory = Services.get(IImportProcessFactory.class);
		importProcessesFactory.registerImportProcess(I_I_ModCntr_Log.class, ImportModularContractLog.class);
	}
}
