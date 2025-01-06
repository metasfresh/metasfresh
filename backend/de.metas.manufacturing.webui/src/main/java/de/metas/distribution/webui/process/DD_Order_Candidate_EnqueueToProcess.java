package de.metas.distribution.webui.process;

import de.metas.distribution.ddordercandidate.DDOrderCandidateService;
import de.metas.process.IProcessPrecondition;
import de.metas.process.PInstanceId;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.security.permissions.Access;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
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

public class DD_Order_Candidate_EnqueueToProcess extends ViewBasedProcessTemplate implements IProcessPrecondition
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final DDOrderCandidateService ddOrderCandidateService = SpringContextHolder.instance.getBean(DDOrderCandidateService.class);

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (getSelectedRowIds().isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection().toInternal();
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

	protected PInstanceId createSelection()
	{
		final IQueryBuilder<I_DD_Order_Candidate> queryBuilder = selectionQueryBuilder();

		final PInstanceId adPInstanceId = Check.assumeNotNull(getPinstanceId(), "adPInstanceId is not null");
		DB.deleteT_Selection(adPInstanceId, ITrx.TRXNAME_ThreadInherited);

		final int count = queryBuilder
				.create()
				.setRequiredAccess(Access.READ)
				.createSelection(adPInstanceId);
		if (count <= 0)
		{
			throw new AdempiereException("@NoSelection@");
		}

		return adPInstanceId;
	}

	protected IQueryBuilder<I_DD_Order_Candidate> selectionQueryBuilder()
	{
		final IQueryFilter<I_DD_Order_Candidate> userSelectionFilter = getProcessInfo().getQueryFilterOrElse(null);
		if (userSelectionFilter == null)
		{
			throw new AdempiereException("@NoSelection@");
		}

		return queryBL
				.createQueryBuilder(I_DD_Order_Candidate.class)
				.addEqualsFilter(I_DD_Order_Candidate.COLUMNNAME_Processed, false)
				.addCompareFilter(I_DD_Order_Candidate.COLUMNNAME_QtyToProcess, CompareQueryFilter.Operator.GREATER, BigDecimal.ZERO)
				.filter(userSelectionFilter)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_DD_Order_Candidate.COLUMNNAME_DD_Order_Candidate_ID);
	}

}
