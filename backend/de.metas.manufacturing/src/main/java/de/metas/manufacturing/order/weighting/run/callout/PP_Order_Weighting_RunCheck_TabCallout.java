package de.metas.manufacturing.order.weighting.run.callout;

import de.metas.manufacturing.order.weighting.run.PPOrderWeightingRunId;
import de.metas.manufacturing.order.weighting.run.PPOrderWeightingRunService;
import lombok.NonNull;
import org.adempiere.ad.callout.api.ICalloutRecord;
import org.adempiere.ad.ui.spi.ITabCallout;
import org.adempiere.ad.ui.spi.TabCallout;
import org.eevolution.model.I_PP_Order_Weighting_RunCheck;

import java.math.BigDecimal;

@TabCallout(I_PP_Order_Weighting_RunCheck.class)
public class PP_Order_Weighting_RunCheck_TabCallout implements ITabCallout
{
	private final PPOrderWeightingRunService ppOrderWeightingRunService;

	public PP_Order_Weighting_RunCheck_TabCallout(
			@NonNull final PPOrderWeightingRunService ppOrderWeightingRunService)
	{
		this.ppOrderWeightingRunService = ppOrderWeightingRunService;
	}

	@Override
	public void onNew(final ICalloutRecord calloutRecord)
	{
		final I_PP_Order_Weighting_RunCheck model = calloutRecord.getModel(I_PP_Order_Weighting_RunCheck.class);
		final PPOrderWeightingRunId weightingRunId = PPOrderWeightingRunId.ofRepoId(model.getPP_Order_Weighting_Run_ID());
		model.setLine(ppOrderWeightingRunService.getNextLineNo(weightingRunId).toInt());
		model.setWeight(BigDecimal.ZERO);
		model.setC_UOM_ID(ppOrderWeightingRunService.getUomId(weightingRunId).getRepoId());
	}
}
