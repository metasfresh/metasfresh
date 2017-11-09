package de.metas.contracts.flatrate.process;

import java.sql.Timestamp;
import java.util.Iterator;

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
import de.metas.invoicecandidate.api.IInvoiceCandidateEnqueuer;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.RunOutOfTrx;

public class C_Flatrate_Term_Change extends JavaProcess
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IContractChangeBL contractChangeBL = Services.get(IContractChangeBL.class);

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
	@RunOutOfTrx
	protected void prepare()
	{
		final IQueryBuilder<I_C_Flatrate_Term> queryBuilder = createQueryBuilder();
		final int selectionCount = createSelection(queryBuilder, getAD_PInstance_ID());
		if (selectionCount <= 0)
		{
			throw new AdempiereException(msgBL.getMsg(getCtx(), IInvoiceCandidateEnqueuer.MSG_INVOICE_GENERATE_NO_CANDIDATES_SELECTED_0P));
		}

	}

	@Override
	protected String doIt()
	{
		if (ChangeTerm_ACTION_SwitchContract.equals(action))
		{
			throw new AdempiereException("Not implemented");
		}

		final ContractChangeParameters contractChangeParameters = ContractChangeParameters.builder()
				.changeDate(changeDate)
				.isCloseInvoiceCandidate(true)
				.terminationMemo(terminationMemo)
				.terminationReason(terminationReason)
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
		return new Iterable<I_C_Flatrate_Term>()
		{
			@Override
			public Iterator<I_C_Flatrate_Term> iterator()
			{
				return queryBL
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
		};
	}
}
