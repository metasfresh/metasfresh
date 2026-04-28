package de.metas.manufacturing.order.weighting.run.interceptor;

import com.google.common.collect.ImmutableList;
import de.metas.manufacturing.order.weighting.run.PPOrderWeightingRunId;
import de.metas.manufacturing.order.weighting.run.PPOrderWeightingRunService;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;
import org.eevolution.model.I_PP_Order_Weighting_RunCheck;
import org.springframework.stereotype.Component;

@Interceptor(I_PP_Order_Weighting_RunCheck.class)
@Component
public class PP_Order_Weighting_RunCheck
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final PPOrderWeightingRunService ppOrderWeightingRunService;

	public PP_Order_Weighting_RunCheck(
			@NonNull final PPOrderWeightingRunService ppOrderWeightingRunService)
	{
		this.ppOrderWeightingRunService = ppOrderWeightingRunService;
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void afterSave(final I_PP_Order_Weighting_RunCheck runCheck)
	{
		scheduleUpdateFromChecks(runCheck);
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_DELETE })
	public void afterDelete(final I_PP_Order_Weighting_RunCheck runCheck)
	{
		scheduleUpdateFromChecks(runCheck);
	}

	private void scheduleUpdateFromChecks(final I_PP_Order_Weighting_RunCheck runCheck)
	{
		if (InterfaceWrapperHelper.isUIAction(runCheck))
		{
			final PPOrderWeightingRunId weightingRunId = PPOrderWeightingRunId.ofRepoId(runCheck.getPP_Order_Weighting_Run_ID());

			trxManager.accumulateAndProcessAfterCommit(
					"PP_Order_WeightRun - updateFromChecks",
					ImmutableList.of(weightingRunId),
					ppOrderWeightingRunService::updateFromChecks);
		}
	}

}
