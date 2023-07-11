package de.metas.contracts.flatrate.process;

import de.metas.cache.CacheMgt;
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
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Year;
import org.compiere.util.Ini;



import static de.metas.contracts.model.X_C_Flatrate_Conditions.TYPE_CONDITIONS_ModularContract;
import static de.metas.contracts.model.X_C_Flatrate_Conditions.ONFLATRATETERMEXTEND_ExtensionNotAllowed;

public class C_Flatrate_Conditions_Extend extends JavaProcess implements IProcessPrecondition
{
	public final static String MSG_SETTINGS_WITH_SAME_YEAR_ALREADY_EXISTS = "@MSG_SETTINGS_WITH_SAME_YEAR_ALREADY_EXISTS@";

	private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);

	@Param(parameterName = I_ModCntr_Settings.COLUMNNAME_C_Year_ID, mandatory = true)
	private int p_C_Year_ID;

	@Override
	protected String doIt()
	{
		final I_C_Year newYear = InterfaceWrapperHelper.load(YearId.ofRepoId(p_C_Year_ID), I_C_Year.class);
		final I_C_Flatrate_Conditions conditions = flatrateDAO.getConditionsById(ConditionsId.ofRepoId(getRecord_ID()));

		if (isSettingsWithSameYear(conditions.getModCntr_Settings(), newYear))
		{
			throw new AdempiereException(MSG_SETTINGS_WITH_SAME_YEAR_ALREADY_EXISTS);
		}

		final I_C_Flatrate_Conditions newConditions = flatrateBL.extendConditionsToNewYear(conditions, newYear);
		if (newConditions != null)
		{
			final int adWindowId = getProcessInfo().getAD_Window_ID();
			final TableRecordReference ref = TableRecordReference.of(newConditions);
			if (adWindowId > 0 && !Ini.isSwingClient())
			{
				getResult().setRecordToOpen(ref, adWindowId, OpenTarget.SingleDocument);
			}
		}

		return MSG_OK;
	}

	private boolean isSettingsWithSameYear(@NonNull final I_ModCntr_Settings settings, @NonNull final I_C_Year newYear)
	{
		return settings.getC_Year_ID() == newYear.getC_Year_ID();
	}

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(IProcessPreconditionsContext context)
	{
		CacheMgt.get().reset(); // reset cache to ensure that we get years for the  settings calendar whenever changed

		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		if (!I_C_Flatrate_Conditions.Table_Name.equals(context.getTableName()))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("The process only runs with C_Flatrate_Conditions table");
		}

		final I_C_Flatrate_Conditions currentConditions = context.getSelectedModel(I_C_Flatrate_Conditions.class);

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
