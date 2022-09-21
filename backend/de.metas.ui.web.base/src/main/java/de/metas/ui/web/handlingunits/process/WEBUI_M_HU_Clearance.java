package de.metas.ui.web.handlingunits.process;

import de.metas.handlingunits.ClearanceStatus;
import de.metas.handlingunits.ClearanceStatusInfo;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.handlingunits.HUEditorProcessTemplate;
import de.metas.ui.web.handlingunits.HUEditorRowFilter;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.util.Services;

public class WEBUI_M_HU_Clearance extends HUEditorProcessTemplate implements IProcessPrecondition
{
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	@Param(parameterName = "ClearanceStatus", mandatory = true)
	private String clearanceStatus;

	@Param(parameterName = "ClearanceNote")
	private String clearanceNote;

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
		final ClearanceStatusInfo clearanceStatusInfo = ClearanceStatusInfo.of(ClearanceStatus.ofCode(clearanceStatus), clearanceNote);

		streamSelectedHUs(HUEditorRowFilter.Select.ALL)
				.forEach(hu -> handlingUnitsBL.setClearanceStatusRecursively(HuId.ofRepoId(hu.getM_HU_ID()), clearanceStatusInfo));

		return MSG_OK;
	}
}
