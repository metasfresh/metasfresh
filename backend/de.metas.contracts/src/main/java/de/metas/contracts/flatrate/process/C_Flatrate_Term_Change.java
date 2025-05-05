package de.metas.contracts.flatrate.process;

import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.i18n.AdMessageKey;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.PInstanceId;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.IQuery;

public class C_Flatrate_Term_Change extends C_Flatrate_Term_Change_Base
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);

	private final static AdMessageKey MSG_REJECT_MODULAR_AND_INTERIM_CONTRACTS = AdMessageKey.of("de.metas.contracts.flatrate.process.reject.cancelInterimOrModular");

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("No selection");
		}

		if (flatrateDAO.isExistsModularOrInterimContract(context.getQueryFilter(I_C_Flatrate_Term.class)))
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_REJECT_MODULAR_AND_INTERIM_CONTRACTS));
		}

		return ProcessPreconditionsResolution.accept();
	}


	@Override
	@RunOutOfTrx
	protected void prepare()
	{
		final IQueryBuilder<I_C_Flatrate_Term> queryBuilder = createQueryBuilder();
		final int selectionCount = createSelection(queryBuilder, getPinstanceId());
		if (selectionCount <= 0)
		{
			throw new AdempiereException("@NoSelection@");
		}
	}

	@Override
	protected Iterable<I_C_Flatrate_Term> getFlatrateTermsToChange()
	{
		return retrieveSelection(getPinstanceId());
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

	private Iterable<I_C_Flatrate_Term> retrieveSelection(final PInstanceId adPInstanceId)
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
