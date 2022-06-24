package de.metas.project.workorder.interceptor;

import de.metas.project.workorder.WOProjectAndStepId;
import de.metas.project.workorder.WOProjectResourceRepository;
import de.metas.project.workorder.WOProjectService;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.compiere.model.I_C_Project_WO_Resource;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
@Interceptor(I_C_Project_WO_Resource.class)
public class C_Project_WO_Resource
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final WOProjectService woProjectService;

	public C_Project_WO_Resource(
			@NonNull final WOProjectService woProjectService)
	{
		this.woProjectService = woProjectService;
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void beforeSave(@NonNull final I_C_Project_WO_Resource record)
	{
		// validate
		WOProjectResourceRepository.fromRecord(record);
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void afterSave(@NonNull final I_C_Project_WO_Resource record)
	{
		scheduleUpdateStepDateRange(WOProjectAndStepId.ofRepoId(record.getC_Project_ID(), record.getC_Project_WO_Step_ID()));
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_DELETE)
	public void afterDelete(@NonNull final I_C_Project_WO_Resource record)
	{
		scheduleUpdateStepDateRange(WOProjectAndStepId.ofRepoId(record.getC_Project_ID(), record.getC_Project_WO_Step_ID()));
	}

	private void scheduleUpdateStepDateRange(@NonNull final WOProjectAndStepId stepId)
	{
		trxManager.getThreadInheritedTrx(OnTrxMissingPolicy.Fail)
				.getPropertyAndProcessAfterCommit(
						"C_Project_WO_Resource.scheduleUpdateStepDateRange",
						HashSet::new,
						woProjectService::updateStepsDateRange
				)
				.add(stepId);
	}
}
