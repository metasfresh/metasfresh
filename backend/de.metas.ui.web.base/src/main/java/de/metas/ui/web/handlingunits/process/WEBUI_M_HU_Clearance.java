package de.metas.ui.web.handlingunits.process;

import de.metas.handlingunits.ClearanceStatus;
import de.metas.handlingunits.ClearanceStatusInfo;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.organization.InstantAndOrgId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.handlingunits.HUEditorProcessTemplate;
import de.metas.ui.web.handlingunits.HUEditorRowFilter;
import de.metas.ui.web.handlingunits.HUEditorView;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.util.Services;

import java.time.Instant;

public class WEBUI_M_HU_Clearance extends HUEditorProcessTemplate implements IProcessPrecondition
{
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	@Param(parameterName = I_M_HU.COLUMNNAME_ClearanceStatus, mandatory = true)
	private String clearanceStatus;

	@Param(parameterName = I_M_HU.COLUMNNAME_ClearanceNote)
	private String clearanceNote;

	@Param(parameterName = I_M_HU.COLUMNNAME_ClearanceDate)
	private Instant clearanceDate;

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!isHUEditorView())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not the HU view");
		}

		final DocumentIdsSelection selectedRowIds = getSelectedRowIds();
		if (selectedRowIds.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final ClearanceStatusInfo clearanceStatusInfo = ClearanceStatusInfo.builder()
				.clearanceStatus(ClearanceStatus.ofCode(clearanceStatus))
				.clearanceNote(clearanceNote)
				.clearanceDate(InstantAndOrgId.ofInstant(clearanceDate, getOrgId()))
				.build();

		streamSelectedHUs(HUEditorRowFilter.Select.ALL)
				.forEach(hu -> handlingUnitsBL.setClearanceStatusRecursively(HuId.ofRepoId(hu.getM_HU_ID()), clearanceStatusInfo));

		final HUEditorView view = getView();
		view.invalidateAll();
			
		return MSG_OK;
	}
}
