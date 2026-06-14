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
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
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
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final SpringContextHolder.Lazy<DDOrderCandidateService> ddOrderCandidateService = SpringContextHolder.lazyBean(DDOrderCandidateService.class);

	@Param(parameterName = I_DD_Order_Candidate.COLUMNNAME_AD_InputDataSource_ID, mandatory = false)
	private InputDataSourceId inputDataSourceId;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	@RunOutOfTrx
	protected String doIt()
	{
		final PInstanceId selectionId = createSelection();
		ddOrderCandidateService.get().enqueueToProcess(selectionId);
		return MSG_OK;
	}

	private PInstanceId createSelection()
	{
		final PInstanceId adPInstanceId = Check.assumeNotNull(getPinstanceId(), "adPInstanceId is not null");
		DB.deleteT_Selection(adPInstanceId, ITrx.TRXNAME_ThreadInherited);

		final int count = selectionQueryBuilder(getProcessInfo().getQueryFilterOrElse(null), inputDataSourceId)
				.create()
				.setRequiredAccess(Access.READ)
				.createSelection(adPInstanceId);

		if (count <= 0)
		{
			throw new AdempiereException("@NoSelection@");
		}
		return adPInstanceId;
	}

	@VisibleForTesting
	IQueryBuilder<I_DD_Order_Candidate> selectionQueryBuilder(
			@Nullable final IQueryFilter<I_DD_Order_Candidate> userSelectionFilter,
			@Nullable final InputDataSourceId source)
	{
		final IQueryBuilder<I_DD_Order_Candidate> queryBuilder = queryBL
				.createQueryBuilder(I_DD_Order_Candidate.class)
				.addEqualsFilter(I_DD_Order_Candidate.COLUMNNAME_Processed, false)
				.addCompareFilter(I_DD_Order_Candidate.COLUMNNAME_QtyToProcess, CompareQueryFilter.Operator.GREATER, BigDecimal.ZERO)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_DD_Order_Candidate.COLUMNNAME_DD_Order_Candidate_ID);

		if (userSelectionFilter != null)
		{
			queryBuilder.filter(userSelectionFilter);
		}

		if (source != null)
		{
			queryBuilder.addEqualsFilter(I_DD_Order_Candidate.COLUMNNAME_AD_InputDataSource_ID, source);
		}

		return queryBuilder;
	}
}
