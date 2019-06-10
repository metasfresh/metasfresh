package de.metas.replenishment.impexp;

import org.compiere.model.I_I_Replenish;
import org.compiere.process.AbstractImportJavaProcess;

/**
 * Import {@link I_I_Replenish} records to {@link I_M_replenish}.
 * 
 * @author cg
 */
public class ImportReplenishment extends AbstractImportJavaProcess<I_I_Replenish>
{

	public ImportReplenishment()
	{
		super(I_I_Replenish.class);
	}
}
