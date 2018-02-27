package de.metas.contracts.flatrate.process;

import java.sql.Timestamp;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;

import de.metas.contracts.IContractChangeBL;
import de.metas.contracts.IContractChangeBL.ContractChangeParameters;
import de.metas.contracts.model.I_C_Contract_Change;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.RunOutOfTrx;

public class C_Flatrate_Term_Change extends JavaProcess
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IContractChangeBL contractChangeBL = Services.get(IContractChangeBL.class);


	public static final String PARAM_CHANGE_DATE = "EventDate";

	public static final String PARAM_ACTION = I_C_Contract_Change.COLUMNNAME_Action;

	public static final String PARAM_TERMINATION_MEMO = I_C_Flatrate_Term.COLUMNNAME_TerminationMemo;
	public static final String PARAM_TERMINATION_REASON = I_C_Flatrate_Term.COLUMNNAME_TerminationReason;

	public static final String PARAM_ONLY_TERMINATE_CURRENT_TERM = "OnlyTerminateCurrentTerm";

	public static final String PARAM_IsCreditOpenInvoices ="IsCreditOpenInvoices";

	@Param(parameterName = PARAM_ACTION, mandatory = true)
	private String action;

	@Param(parameterName = PARAM_CHANGE_DATE, mandatory = true)
	private Timestamp changeDate;

	@Param(parameterName = PARAM_TERMINATION_MEMO, mandatory = false)
	private String terminationMemo;

	@Param(parameterName = PARAM_TERMINATION_REASON, mandatory = false)
	private String terminationReason;

	@Param(parameterName = PARAM_IsCreditOpenInvoices, mandatory = false)
	private boolean isCreditOpenInvoices;

	@Override
	@RunOutOfTrx
	protected void prepare()
	{
		final IQueryBuilder<I_C_Flatrate_Term> queryBuilder = createQueryBuilder();
		final int selectionCount = createSelection(queryBuilder, getAD_PInstance_ID());
		if (selectionCount <= 0)
		{
			throw new AdempiereException("@NoSelection@");
		}

	}

	@Override
	protected String doIt()
	{
		if (IContractChangeBL.ChangeTerm_ACTION_SwitchContract.equals(action))
		{
			throw new AdempiereException("Not implemented");
		}

		final ContractChangeParameters contractChangeParameters = ContractChangeParameters.builder()
				.changeDate(changeDate)
				.isCloseInvoiceCandidate(true)
				.terminationMemo(terminationMemo)
				.terminationReason(terminationReason)
				.isCreditOpenInvoices(isCreditOpenInvoices)
				.action(action)
				.build();

		final Iterable<I_C_Flatrate_Term> flatrateTerms = retrieveSelection(getAD_PInstance_ID());
		flatrateTerms.forEach(currentTerm -> contractChangeBL.cancelContract(currentTerm, contractChangeParameters));

		return "@Success@";
	}

	private IQueryBuilder<I_C_Flatrate_Term> createQueryBuilder()
	{
		final IQueryFilter<I_C_Flatrate_Term> userSelectionFilter = getProcessInfo().getQueryFilterOrElse(null);
		if (userSelectionFilter == null)
		{
			throw new AdempiereException("@NoSelection@");
		}

		return queryBL
				.createQueryBuilder(I_C_Flatrate_Term.class)
				.filter(userSelectionFilter)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient();
	}

	private final Iterable<I_C_Flatrate_Term> retrieveSelection(final int adPInstanceId)
	{
		return () -> queryBL
				.createQueryBuilder(I_C_Flatrate_Term.class)
				.setOnlySelection(adPInstanceId)
				.orderBy()
				.addColumn(I_C_Flatrate_Term.COLUMN_C_Flatrate_Term_ID)
				.endOrderBy()
				.create()
				.setOption(IQuery.OPTION_GuaranteedIteratorRequired, false)
				.setOption(IQuery.OPTION_IteratorBufferSize, 50)
				.iterate(I_C_Flatrate_Term.class);
	}
}
