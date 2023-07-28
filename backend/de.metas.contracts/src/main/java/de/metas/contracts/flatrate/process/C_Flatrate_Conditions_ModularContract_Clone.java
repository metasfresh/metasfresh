package de.metas.contracts.flatrate.process;

import de.metas.calendar.standard.YearId;
import de.metas.contracts.ConditionsId;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_ModCntr_Settings;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessExecutionResult.RecordsToOpen.OpenTarget;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Ini;

import static de.metas.contracts.model.X_C_Flatrate_Conditions.TYPE_CONDITIONS_ModularContract;
import static de.metas.contracts.model.X_C_Flatrate_Conditions.ONFLATRATETERMEXTEND_ExtensionNotAllowed;

public class C_Flatrate_Conditions_ModularContract_Clone extends JavaProcess implements IProcessPrecondition
{
	private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);

	@Param(parameterName = I_ModCntr_Settings.COLUMNNAME_C_Year_ID, mandatory = true)
	private int p_C_Year_ID;

	@Override
	protected String doIt()
	{
		final ConditionsId clonedConditionsID = flatrateBL.cloneConditionsToNewYear(ConditionsId.ofRepoId(getRecord_ID()), YearId.ofRepoId(p_C_Year_ID));
		if (clonedConditionsID != null)
		{
			final int adWindowId = getProcessInfo().getAD_Window_ID();
			final TableRecordReference ref = TableRecordReference.of(I_C_Flatrate_Conditions.Table_Name, clonedConditionsID);
			if (adWindowId > 0 && !Ini.isSwingClient())
			{
				getResult().setRecordToOpen(ref, adWindowId, OpenTarget.SingleDocument);
			}
		}

		return MSG_OK;
	}

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		if (!I_C_Flatrate_Conditions.Table_Name.equals(context.getTableName()))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("The process only runs with C_Flatrate_Conditions table");
		}

		final ConditionsId conditionsId = ConditionsId.ofRepoId(context.getSingleSelectedRecordId());
		final I_C_Flatrate_Conditions currentConditions = flatrateDAO.getConditionsById(conditionsId);

		if (!TYPE_CONDITIONS_ModularContract.equals(currentConditions.getType_Conditions()))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("The process runs only  when contract of type  ModularContract");
		}

		if (!ONFLATRATETERMEXTEND_ExtensionNotAllowed.equals(currentConditions.getOnFlatrateTermExtend()))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("The process runs only  when  ONFLATRATETERMEXTEND = ExtensionNotAllowed");
		}

		return ProcessPreconditionsResolution.accept();
	}

}
