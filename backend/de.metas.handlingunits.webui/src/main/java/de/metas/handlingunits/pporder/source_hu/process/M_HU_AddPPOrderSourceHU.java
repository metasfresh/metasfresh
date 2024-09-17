package de.metas.handlingunits.pporder.source_hu.process;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.pporder.source_hu.PPOrderSourceHUService;
import de.metas.i18n.BooleanWithReason;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.handlingunits.HUEditorProcessTemplate;
import org.compiere.SpringContextHolder;
import org.eevolution.api.PPOrderId;

public class M_HU_AddPPOrderSourceHU extends HUEditorProcessTemplate implements IProcessPrecondition
{
	private final PPOrderSourceHUService ppOrderSourceHUService = SpringContextHolder.instance.getBean(PPOrderSourceHUService.class);

	@Param(parameterName = "PP_Order_ID", mandatory = true)
	private PPOrderId ppOrderId;

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!isSingleSelectedRow())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection().toInternal();
		}

		final HuId huId = getSingleSelectedRow().getHuId();
		final BooleanWithReason huEligible = ppOrderSourceHUService.checkEligibleToAddAsSourceHU(huId);
		if (huEligible.isFalse())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason(huEligible.getReason());
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final HuId huId = getSingleSelectedRow().getHuId();
		ppOrderSourceHUService.addSourceHU(ppOrderId, huId);
		return MSG_OK;
	}
}
