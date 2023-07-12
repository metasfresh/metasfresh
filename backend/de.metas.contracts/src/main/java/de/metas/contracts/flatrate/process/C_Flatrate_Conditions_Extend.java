package de.metas.contracts.flatrate.process;

import de.metas.calendar.standard.YearAndCalendarId;
import de.metas.calendar.standard.YearId;
import de.metas.contracts.ConditionsId;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_ModCntr_Settings;
import de.metas.contracts.modular.settings.ModularContractSettingsDAO;
import de.metas.contracts.modular.settings.ModularContractSettingsQuery;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessExecutionResult.RecordsToOpen.OpenTarget;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Year;
import org.compiere.util.Ini;

import static de.metas.contracts.model.X_C_Flatrate_Conditions.TYPE_CONDITIONS_ModularContract;
import static de.metas.contracts.model.X_C_Flatrate_Conditions.ONFLATRATETERMEXTEND_ExtensionNotAllowed;

public class C_Flatrate_Conditions_Extend extends JavaProcess implements IProcessPrecondition
{
	public final static String MSG_SETTINGS_WITH_SAME_YEAR_ALREADY_EXISTS = "@MSG_SETTINGS_WITH_SAME_YEAR_ALREADY_EXISTS@";

	private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);
	private final ModularContractSettingsDAO modularContractSettingsDAO = SpringContextHolder.instance.getBean(ModularContractSettingsDAO.class);

	@Param(parameterName = I_ModCntr_Settings.COLUMNNAME_C_Year_ID, mandatory = true)
	private int p_C_Year_ID;

	@Override
	protected String doIt()
	{
		final I_C_Year newYear = InterfaceWrapperHelper.load(YearId.ofRepoId(p_C_Year_ID), I_C_Year.class);
		final I_C_Flatrate_Conditions conditions = flatrateDAO.getConditionsById(ConditionsId.ofRepoId(getRecord_ID()));
		final I_ModCntr_Settings settings = conditions.getModCntr_Settings();

		Check.assumeNotNull(settings, " Should never happen because settings are mandatory for modular contract conditions", conditions);

		final ProductId productId = ProductId.ofRepoId(settings.getM_Product_ID());
		final YearAndCalendarId yearAndCalendarId = YearAndCalendarId.ofRepoIdOrNull(p_C_Year_ID, settings.getC_Calendar_ID());

		final ModularContractSettingsQuery query = ModularContractSettingsQuery.builder()
				.productId(productId)
				.yearAndCalendarId(yearAndCalendarId)
				.build();

		if (modularContractSettingsDAO.isSettingsExists(query))
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
