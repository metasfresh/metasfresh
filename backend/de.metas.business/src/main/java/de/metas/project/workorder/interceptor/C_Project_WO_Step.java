package de.metas.project.workorder.interceptor;

import de.metas.project.workorder.step.WOProjectStepRepository;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_Project_WO_Step;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Component
@Interceptor(I_C_Project_WO_Step.class)
public class C_Project_WO_Step
{
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void afterSave(@NonNull final I_C_Project_WO_Step record)
	{
		// validate
		WOProjectStepRepository.ofRecord(record);
	}

}
