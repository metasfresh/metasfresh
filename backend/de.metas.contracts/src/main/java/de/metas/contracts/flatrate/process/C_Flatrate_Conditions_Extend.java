package de.metas.contracts.flatrate.process;

import de.metas.calendar.standard.YearId;
import de.metas.contracts.ConditionsId;
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
import org.adempiere.model.CopyRecordFactory;
import org.adempiere.model.CopyRecordSupport;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Year;
import org.compiere.model.PO;
import org.compiere.util.Ini;

import static de.metas.contracts.model.X_C_Flatrate_Conditions.TYPE_CONDITIONS_ModularContract;
import static de.metas.contracts.model.X_C_Flatrate_Conditions.ONFLATRATETERMEXTEND_ExtensionNotAllowed;

public class C_Flatrate_Conditions_Extend extends JavaProcess implements IProcessPrecondition
{
	private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
	public final static String C_YEAR_ID = "C_Year_ID";

	@Param(parameterName = C_YEAR_ID, mandatory = true)
	private int p_C_Year_ID;

	@Override
	protected String doIt()
	{
		final I_C_Year newYear = InterfaceWrapperHelper.load(YearId.ofRepoId(p_C_Year_ID), I_C_Year.class);
		final I_C_Flatrate_Conditions currentConditions = flatrateDAO.getConditionsById(ConditionsId.ofRepoId(getRecord_ID()));
		final I_ModCntr_Settings newModCntrSettings = extendModCntr_Settings(currentConditions.getModCntr_Settings(), newYear);

		if (newModCntrSettings != null)
		{
			final I_C_Flatrate_Conditions newFlatrateConditions = extendFlatrateConditionsWithSettings(currentConditions, newModCntrSettings, newYear);

			final int adWindowId = getProcessInfo().getAD_Window_ID();
			final TableRecordReference ref = TableRecordReference.of(newFlatrateConditions);
			if (adWindowId > 0 && !Ini.isSwingClient())
			{
				getResult().setRecordToOpen(ref, adWindowId, OpenTarget.SingleDocument);
			}
		}

		return MSG_OK;
	}

	private I_C_Flatrate_Conditions extendFlatrateConditionsWithSettings(@NonNull final I_C_Flatrate_Conditions conditions, @NonNull final I_ModCntr_Settings settings, I_C_Year year)
	{
		final I_C_Flatrate_Conditions newFlatrateConditions = InterfaceWrapperHelper.newInstance(I_C_Flatrate_Conditions.class, conditions);

		final PO from = InterfaceWrapperHelper.getPO(conditions);
		final PO to = InterfaceWrapperHelper.getPO(newFlatrateConditions);

		PO.copyValues(from, to, true);

		newFlatrateConditions.setName(conditions.getName().concat("-" + year.getFiscalYear()));
		newFlatrateConditions.setModCntr_Settings(settings);

		InterfaceWrapperHelper.save(newFlatrateConditions);

		// sub details

		final CopyRecordSupport childCRS = CopyRecordFactory.getCopyRecordSupport(I_C_Flatrate_Conditions.Table_Name);
		childCRS.setParentPO(to);
		childCRS.setBase(true);
		childCRS.copyRecord(from, InterfaceWrapperHelper.getTrxName(conditions));

		return newFlatrateConditions;
	}

	@NonNull
	private I_ModCntr_Settings extendModCntr_Settings(@NonNull final I_ModCntr_Settings modCntrSettings, @NonNull final I_C_Year year)
	{
		final I_ModCntr_Settings newModCntrSettings = InterfaceWrapperHelper.newInstance(I_ModCntr_Settings.class, modCntrSettings);

		final PO from = InterfaceWrapperHelper.getPO(modCntrSettings);
		final PO to = InterfaceWrapperHelper.getPO(newModCntrSettings);

		PO.copyValues(from, to, true);

		newModCntrSettings.setC_Year_ID(year.getC_Year_ID());
		newModCntrSettings.setName(newModCntrSettings.getName().concat("-" + year.getFiscalYear()));

		InterfaceWrapperHelper.save(newModCntrSettings);

		final CopyRecordSupport childCRS = CopyRecordFactory.getCopyRecordSupport(I_ModCntr_Settings.Table_Name);
		childCRS.setParentPO(to);
		childCRS.setBase(true);
		childCRS.copyRecord(from, InterfaceWrapperHelper.getTrxName(modCntrSettings));

		return newModCntrSettings;
	}

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(IProcessPreconditionsContext context)
	{
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
			return ProcessPreconditionsResolution.rejectWithInternalReason("Extension Not Allowed");
		}

		return ProcessPreconditionsResolution.accept();
	}

}
