package de.metas.manufacturing.order.weighting.run.interceptor;

import de.metas.manufacturing.order.weighting.run.PPOrderWeightingRunId;
import de.metas.manufacturing.order.weighting.run.PPOrderWeightingRunRepository;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;
import org.eevolution.model.I_PP_Order_Weighting_Run;
import org.springframework.stereotype.Component;

@Interceptor(I_PP_Order_Weighting_Run.class)
@Component
public class PP_Order_Weighting_Run
{
	private final PPOrderWeightingRunRepository ppOrderWeightingRunRepository;

	public PP_Order_Weighting_Run(
			final @NonNull PPOrderWeightingRunRepository ppOrderWeightingRunRepository)
	{
		this.ppOrderWeightingRunRepository = ppOrderWeightingRunRepository;
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void beforeSave(final I_PP_Order_Weighting_Run record)
	{
		if (InterfaceWrapperHelper.isUIAction(record))
		{
			final boolean isUOMChanged = InterfaceWrapperHelper.isValueChanged(record, I_PP_Order_Weighting_Run.COLUMNNAME_C_UOM_ID);
			if (InterfaceWrapperHelper.isValueChanged(record, I_PP_Order_Weighting_Run.COLUMNNAME_TargetWeight)
					|| InterfaceWrapperHelper.isValueChanged(record, I_PP_Order_Weighting_Run.COLUMNNAME_Tolerance_Perc)
					|| isUOMChanged)
			{
				ppOrderWeightingRunRepository.updateWhileSaving(
						record,
						weightingRun -> {
							if (isUOMChanged)
							{
								weightingRun.updateUOMFromHeaderToChecks();
							}

							weightingRun.updateTargetWeightRange();
						}
				);
			}
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_DELETE })
	public void beforeDelete(final I_PP_Order_Weighting_Run record)
	{
		final PPOrderWeightingRunId weightingRunId = PPOrderWeightingRunId.ofRepoId(record.getPP_Order_Weighting_Run_ID());
		ppOrderWeightingRunRepository.deleteChecks(weightingRunId);
	}

}
