package de.metas.manufacturing.order.weighting.callout;

import de.metas.manufacturing.order.weighting.PPOrderWeightingRunId;
import de.metas.manufacturing.order.weighting.PPOrderWeightingRunService;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.api.ICalloutRecord;
import org.adempiere.ad.ui.api.ITabCalloutFactory;
import org.adempiere.ad.ui.spi.TabCalloutAdapter;
import org.eevolution.model.I_PP_Order_Weighting_RunCheck;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;

@Component
public class PP_Order_Weighting_RunCheck_TabCallout extends TabCalloutAdapter
{
	private final PPOrderWeightingRunService ppOrderWeightingRunService;

	// FIXME this is not working like this...
	public PP_Order_Weighting_RunCheck_TabCallout(
			@NonNull final PPOrderWeightingRunService ppOrderWeightingRunService)
	{
		this.ppOrderWeightingRunService = ppOrderWeightingRunService;
	}

	@PostConstruct
	public void postConstruct()
	{
		Services.get(ITabCalloutFactory.class).registerTabCalloutForTable(I_PP_Order_Weighting_RunCheck.Table_Name, PP_Order_Weighting_RunCheck_TabCallout.class);
	}

	@Override
	public void onNew(final ICalloutRecord calloutRecord)
	{
		final I_PP_Order_Weighting_RunCheck model = calloutRecord.getModel(I_PP_Order_Weighting_RunCheck.class);
		final PPOrderWeightingRunId weightingRunId = PPOrderWeightingRunId.ofRepoId(model.getPP_Order_Weighting_Run_ID());
		model.setLine(ppOrderWeightingRunService.getNextLineNo(weightingRunId));
		model.setWeight(BigDecimal.ZERO);
		model.setC_UOM_ID(ppOrderWeightingRunService.getUomId(weightingRunId).getRepoId());
	}
}
