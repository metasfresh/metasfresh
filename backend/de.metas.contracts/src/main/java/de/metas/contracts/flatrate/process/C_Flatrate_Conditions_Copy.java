package de.metas.contracts.flatrate.process;

import de.metas.calendar.standard.YearId;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessExecutionResult.RecordsToOpen.OpenTarget;
import de.metas.process.ProcessPreconditionsResolution;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Ini;

import java.util.Optional;

public class C_Flatrate_Conditions_Copy extends JavaProcess implements IProcessPrecondition
{

	// todo setup process param correctly
	private int p_c_year_id;

	@Override
	protected String doIt()
	{
		final I_C_Flatrate_Conditions currentFlatrateConditions = getRecord(I_C_Flatrate_Conditions.class);

		final Optional<I_C_Flatrate_Conditions> newFlatrateConditions = copy(currentFlatrateConditions, YearId.ofRepoId(p_c_year_id));

		if (newFlatrateConditions.isPresent())
		{
			final int adWindowId = getProcessInfo().getAD_Window_ID();
			final TableRecordReference ref = TableRecordReference.of(newFlatrateConditions);

			if (adWindowId > 0 && !Ini.isSwingClient())
			{
				getResult().setRecordToOpen(ref, adWindowId, OpenTarget.SingleDocument);
			}
			else
			{
				getResult().setRecordToSelectAfterExecution(ref);
			}
		}

		return MSG_OK;
	}

	private Optional<I_C_Flatrate_Conditions> copy(@NonNull final I_C_Flatrate_Conditions flatrate_conditions, @NonNull final YearId yearId)
	{
		// todo setup proper logic
		return Optional.ofNullable(null);
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

		// todo reject reject when : Contract Type != 'Modular Contract' OR Extending Contract !='Extension Not Allowed'

		return ProcessPreconditionsResolution.accept();
	}

}
