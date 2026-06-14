package de.metas.distribution.ddordercandidate.process;

import de.metas.distribution.ddordercandidate.DDOrderCandidateService;
import de.metas.impexp.InputDataSourceId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.PInstanceId;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.security.permissions.Access;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.util.DB;
import org.eevolution.model.I_DD_Order_Candidate;

import java.math.BigDecimal;

public class DD_Order_Candidate_EnqueueToProcess extends JavaProcess implements IProcessPrecondition
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final DDOrderCandidateService ddOrderCandidateService = SpringContextHolder.instance.getBean(DDOrderCandidateService.class);

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
	protected String doIt()
	{
		final PInstanceId selectionId = createSelection();
		ddOrderCandidateService.enqueueToProcess(selectionId);
		return MSG_OK;
	}

	private PInstanceId createSelection()
	{
		final PInstanceId adPInstanceId = Check.assumeNotNull(getPinstanceId(), "adPInstanceId is not null");
		DB.deleteT_Selection(adPInstanceId, ITrx.TRXNAME_ThreadInherited);

		final int count = selectionQueryBuilder()
				.create()
				.setRequiredAccess(Access.READ)
				.createSelection(adPInstanceId);
		if (count <= 0)
		{
			throw new AdempiereException("@NoSelection@");
		}
		return adPInstanceId;
	}

	private IQueryBuilder<I_DD_Order_Candidate> selectionQueryBuilder()
	{
		// Present when run manually from a WebUI grid (the row selection); null when run headless (scheduler / API).
		final IQueryFilter<I_DD_Order_Candidate> userSelectionFilter = getProcessInfo().getQueryFilterOrElse(null);

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
		if (inputDataSourceId != null)
		{
			queryBuilder.addEqualsFilter(I_DD_Order_Candidate.COLUMNNAME_AD_InputDataSource_ID, inputDataSourceId);
		}
		return queryBuilder;
	}
}
