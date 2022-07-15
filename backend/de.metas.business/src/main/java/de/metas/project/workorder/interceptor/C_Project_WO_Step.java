package de.metas.project.workorder.interceptor;

import de.metas.project.workorder.WOProjectStepId;
import de.metas.project.workorder.WOProjectStepRepository;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.compiere.model.I_C_Project_WO_Step;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.UUID;

@Component
@Interceptor(I_C_Project_WO_Step.class)
public class C_Project_WO_Step
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void beforeSave(@NonNull final I_C_Project_WO_Step record)
	{
		// validate
		WOProjectStepRepository.fromRecord(record);
	}

}
