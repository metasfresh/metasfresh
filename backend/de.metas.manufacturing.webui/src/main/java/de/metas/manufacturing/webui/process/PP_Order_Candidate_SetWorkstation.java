package de.metas.manufacturing.webui.process;

import com.google.common.collect.ImmutableSet;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.product.ResourceId;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.service.ISysConfigBL;
import org.compiere.SpringContextHolder;
import org.eevolution.model.I_PP_Order_Candidate;
import org.eevolution.productioncandidate.model.PPOrderCandidateId;
import org.eevolution.productioncandidate.service.PPOrderCandidateService;

public class PP_Order_Candidate_SetWorkstation extends ViewBasedProcessTemplate implements IProcessPrecondition
{
	private final PPOrderCandidateService ppOrderCandidateService = SpringContextHolder.instance.getBean(PPOrderCandidateService.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	private static final String PARAM_WorkStation_ID = I_PP_Order_Candidate.COLUMNNAME_WorkStation_ID;
	@Param(parameterName = PARAM_WorkStation_ID, mandatory = false)
	private ResourceId p_Workstation_ID;

	private final int rowsLimit = sysConfigBL.getPositiveIntValue("PP_Order_Candidate_SetWorkstation.rowsLimit", 1000);

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final DocumentIdsSelection selectedRowIds = getSelectedRowIds();
		if (selectedRowIds.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection().toInternal();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		ppOrderCandidateService.setWorkstationId(getSelectedPPOrderCandidateIds(), p_Workstation_ID);
		return MSG_OK;
	}

	private ImmutableSet<PPOrderCandidateId> getSelectedPPOrderCandidateIds()
	{
		final DocumentIdsSelection selectedRowIds = getSelectedRowIds();
		if (selectedRowIds.isAll())
		{
			return getView().streamByIds(selectedRowIds)
					.limit(rowsLimit)
					.map(PP_Order_Candidate_SetWorkstation::extractPPOrderCandidateId)
					.collect(ImmutableSet.toImmutableSet());
		}
		else
		{
			return selectedRowIds.stream()
					.map(PP_Order_Candidate_SetWorkstation::toPPOrderCandidateId)
					.collect(ImmutableSet.toImmutableSet());
		}
	}

	@NonNull
	private static PPOrderCandidateId extractPPOrderCandidateId(final IViewRow row) {return toPPOrderCandidateId(row.getId());}

	@NonNull
	private static PPOrderCandidateId toPPOrderCandidateId(final DocumentId rowId) {return PPOrderCandidateId.ofRepoId(rowId.toInt());}
}
