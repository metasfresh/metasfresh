package de.metas.contracts.process;

import de.metas.contracts.model.I_I_ModCntr_Log;
import lombok.NonNull;
import org.compiere.process.AbstractImportJavaProcess;

public class ImportModularContractLog extends AbstractImportJavaProcess<I_I_ModCntr_Log>
{
	public ImportModularContractLog(@NonNull final Class<I_I_ModCntr_Log> importRecordClass)
	{
		super(importRecordClass);
	}
}
