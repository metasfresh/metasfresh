package de.metas.manufacturing.order.weighting.spec.interceptor;

import de.metas.manufacturing.order.weighting.spec.WeightingSpecificationsRepository;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.eevolution.model.I_PP_Weighting_Spec;
import org.springframework.stereotype.Component;

@Interceptor(I_PP_Weighting_Spec.class)
@Component
public class PP_Weighting_Spec
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void beforeSave(I_PP_Weighting_Spec record)
	{
		WeightingSpecificationsRepository.fromRecord(record); // validate
	}
}
