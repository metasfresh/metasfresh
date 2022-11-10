package de.metas.manufacturing.order.weighting.interceptor;

import de.metas.manufacturing.order.weighting.PPOrderWeightingRunId;
import de.metas.manufacturing.order.weighting.PPOrderWeightingRunService;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.eevolution.model.I_PP_Order_Weighting_Run;
import org.eevolution.model.I_PP_Order_Weighting_RunCheck;
import org.springframework.stereotype.Component;

@Interceptor(I_PP_Order_Weighting_RunCheck.class)
@Component
public class PP_Order_Weighting_RunCheck
{
	private final PPOrderWeightingRunService ppOrderWeightingRunService;

	public PP_Order_Weighting_RunCheck(
			@NonNull final PPOrderWeightingRunService ppOrderWeightingRunService)
	{
		this.ppOrderWeightingRunService = ppOrderWeightingRunService;
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void afterSave(final I_PP_Order_Weighting_RunCheck runCheck)
	{
		final PPOrderWeightingRunId weightingRunId = PPOrderWeightingRunId.ofRepoId(runCheck.getPP_Order_Weighting_Run_ID());
		ppOrderWeightingRunService.updateFromChecks(weightingRunId);
		// TODO
	}
}
