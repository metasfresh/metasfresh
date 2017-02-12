package de.metas.handlingunits.inout.process;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_M_InOut;

import de.metas.handlingunits.inout.IHUInOutBL;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;

/**
 * Deletes and Recreates an inout's packing material lines. Supposed to be called via gear.
 *
 */
public class M_InOut_ResetPackingLines extends JavaProcess implements IProcessPrecondition
{

	@Override
	protected void prepare()
	{
		// nothing
	}

	@Override
	protected String doIt() throws Exception
	{
		final I_M_InOut inout = InterfaceWrapperHelper.create(getCtx(), getRecord_ID(), I_M_InOut.class, getTrxName());
		Services.get(IHUInOutBL.class).recreatePackingMaterialLines(inout);

		return "@Success@";
	}

	/**
	 * Returns <code>true</code> for unprocessed shipments only.
	 */
	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if (!I_M_InOut.Table_Name.equals(context.getTableName()))
		{
			return ProcessPreconditionsResolution.reject();
		}

		final I_M_InOut inout = context.getSelectedModel(I_M_InOut.class);
		return ProcessPreconditionsResolution.acceptIf(inout.isSOTrx() && !inout.isProcessed());
	}

}
