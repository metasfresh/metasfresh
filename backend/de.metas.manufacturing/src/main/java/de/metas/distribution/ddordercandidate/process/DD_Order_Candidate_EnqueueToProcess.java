package de.metas.distribution.ddordercandidate.process;

import com.google.common.annotations.VisibleForTesting;
import de.metas.distribution.ddordercandidate.DDOrderCandidateService;
import de.metas.impexp.InputDataSourceId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.PInstanceId;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.security.permissions.Access;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.util.DB;
import org.eevolution.model.I_DD_Order_Candidate;

import javax.annotation.Nullable;
import java.math.BigDecimal;

public class DD_Order_Candidate_EnqueueToProcess extends JavaProcess implements IProcessPrecondition
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final SpringContextHolder.Lazy<DDOrderCandidateService> ddOrderCandidateService = SpringContextHolder.lazyBean(DDOrderCandidateService.class);

	@Param(parameterName = I_DD_Order_Candidate.COLUMNNAME_AD_InputDataSource_ID, mandatory = false)
	private InputDataSourceId inputDataSourceId;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	@RunOutOfTrx
	protected void prepare()
	{
		if (createSelection() <= 0)
		{
			throw new AdempiereException("@NoSelection@");
		}
	}

	@Override
	protected String doIt()
	{
		final PInstanceId selectionId = Check.assumeNotNull(getPinstanceId(), "pinstanceId is not null");
		ddOrderCandidateService.get().enqueueToProcess(selectionId);
		return MSG_OK;
	}

	private int createSelection()
	{
		// Resolve grid-selection filter from ProcessInfo (set when called from a WebUI grid with row selection;
		// null when called headlessly from scheduler / API).
		final IQueryBuilder<I_DD_Order_Candidate> gridSelectionQB;
		if (getProcessInfo().getQueryFilterOrElse(null) != null)
		{
			gridSelectionQB = retrieveSelectedRecordsQueryBuilder(I_DD_Order_Candidate.class);
		}
		else
		{
			gridSelectionQB = null;
		}

		final IQueryBuilder<I_DD_Order_Candidate> queryBuilder = buildSelectionQuery(gridSelectionQB, inputDataSourceId);

		final PInstanceId adPInstanceId = Check.assumeNotNull(getPinstanceId(), "adPInstanceId is not null");
		DB.deleteT_Selection(adPInstanceId, ITrx.TRXNAME_ThreadInherited);

		return queryBuilder
				.create()
				.setRequiredAccess(Access.READ)
				.createSelection(adPInstanceId);
	}

	/**
	 * Builds the selection query for DD_Order_Candidates.
	 *
	 * <p>Base filters always applied: Processed=false, QtyToProcess > 0, active records.
	 * <p>When {@code gridSelection} is not null, its filters are also applied (manual WebUI selection).
	 * <p>When {@code source} is not null, an AD_InputDataSource_ID equals-filter is applied.
	 */
	@VisibleForTesting
	IQueryBuilder<I_DD_Order_Candidate> buildSelectionQuery(
			@Nullable final IQueryBuilder<I_DD_Order_Candidate> gridSelection,
			@Nullable final InputDataSourceId source)
	{
		final IQueryBuilder<I_DD_Order_Candidate> queryBuilder = queryBL
				.createQueryBuilder(I_DD_Order_Candidate.class)
				.addEqualsFilter(I_DD_Order_Candidate.COLUMNNAME_Processed, false)
				.addCompareFilter(I_DD_Order_Candidate.COLUMNNAME_QtyToProcess, CompareQueryFilter.Operator.GREATER, BigDecimal.ZERO)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_DD_Order_Candidate.COLUMNNAME_DD_Order_Candidate_ID);

		if (gridSelection != null)
		{
			queryBuilder.addFiltersUnboxed(gridSelection.getCompositeFilter());
		}

		if (source != null)
		{
			queryBuilder.addEqualsFilter(I_DD_Order_Candidate.COLUMNNAME_AD_InputDataSource_ID, source);
		}

		return queryBuilder;
	}
}
