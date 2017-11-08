package de.metas.contracts.flatrate.process;

import java.sql.Timestamp;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Ini;

import de.metas.contracts.ContractChangeParameters;
import de.metas.contracts.IContractChangeBL;
import de.metas.contracts.model.I_C_Contract_Change;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.process.JavaProcess;
import de.metas.process.Param;

public class C_Flatrate_Term_Change extends JavaProcess
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public static final String ChangeTerm_ACTION_SwitchContract = "SC";
	public static final String ChangeTerm_ACTION_Cancel = "CA";

	public static final String PARAM_CHANGE_DATE = "EventDate";

	public static final String PARAM_ACTION = I_C_Contract_Change.COLUMNNAME_Action;

	public static final String PARAM_TERMINATION_MEMO = I_C_Flatrate_Term.COLUMNNAME_TerminationMemo;
	public static final String PARAM_TERMINATION_REASON = I_C_Flatrate_Term.COLUMNNAME_TerminationReason;

	@Param(parameterName = PARAM_ACTION, mandatory = true)
	private String action;

	@Param(parameterName = PARAM_CHANGE_DATE, mandatory = true)
	private Timestamp changeDate;

	@Param(parameterName = PARAM_TERMINATION_MEMO, mandatory = false)
	private String terminationMemo;

	@Param(parameterName = PARAM_TERMINATION_REASON, mandatory = false)
	private String terminationReason;

	@Override
	protected String doIt()
	{
		if (ChangeTerm_ACTION_SwitchContract.equals(action))
		{
			throw new AdempiereException("Not implemented");
		}

		if (I_C_Flatrate_Term.Table_Name.equals(getTableName()))
		{
			final I_C_Flatrate_Term currentTerm = InterfaceWrapperHelper.create(getCtx(), getRecord_ID(), I_C_Flatrate_Term.class, get_TrxName());

			final ContractChangeParameters contractChangeParameters = ContractChangeParameters.builder()
					.changeDate(changeDate)
					.isCloseInvoiceCandidate(true)
					.terminationMemo(terminationMemo)
					.terminationReason(terminationReason)
					.build();

			Services.get(IContractChangeBL.class).cancelContract(currentTerm, contractChangeParameters);

			getResult().setRecordToRefreshAfterExecution(TableRecordReference.of(currentTerm));
		}
		else
		{
			final IQueryBuilder<I_C_Flatrate_Term> queryBuilder = createQueryBuilder();
			final int selectionId = createSelection(queryBuilder, getAD_PInstance_ID());
		}
		
		return "@Success@";
	}
	
	private  IQueryBuilder<I_C_Flatrate_Term> createQueryBuilder()
	{
		final IQueryFilter<I_C_Flatrate_Term> userSelectionFilter;
		if(Ini.isClient())
		{
			// In case of Swing, preserve the old functionality, i.e. if no where clause then select all 
			userSelectionFilter = getProcessInfo().getQueryFilter();
		}
		else
		{
			userSelectionFilter = getProcessInfo().getQueryFilterOrElse(null);
			if(userSelectionFilter == null)
			{
				throw new AdempiereException("@NoSelection@");
			}
		}

		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Flatrate_Term.class, getCtx(), ITrx.TRXNAME_None)
				.filter(userSelectionFilter)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient();
	}
}
