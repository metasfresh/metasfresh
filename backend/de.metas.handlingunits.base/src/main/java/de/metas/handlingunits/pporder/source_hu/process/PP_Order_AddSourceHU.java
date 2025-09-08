package de.metas.handlingunits.pporder.source_hu.process;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.pporder.source_hu.PPOrderSourceHUService;
import de.metas.i18n.BooleanWithReason;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.eevolution.api.PPOrderId;

public class PP_Order_AddSourceHU extends JavaProcess implements IProcessPrecondition
{
	private final PPOrderSourceHUService ppOrderSourceHUService = SpringContextHolder.instance.getBean(PPOrderSourceHUService.class);

	@Param(parameterName = "M_HU_ID", mandatory = true)
	private HuId huId;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection().toInternal();
		}

		final PPOrderId ppOrderId = PPOrderId.ofRepoId(context.getSingleSelectedRecordId());
		final BooleanWithReason ppOrderEligible = ppOrderSourceHUService.checkEligibleToAddToManufacturingOrder(ppOrderId);
		if (ppOrderEligible.isFalse())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason(ppOrderEligible.getReason());
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final PPOrderId ppOrderId = PPOrderId.ofRepoId(getRecord_ID());
		ppOrderSourceHUService.addSourceHU(ppOrderId, huId);
		return MSG_OK;
	}
}
