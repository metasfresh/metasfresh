package de.metas.project.workorder.interceptor;

import de.metas.i18n.AdMessageKey;
import de.metas.project.workorder.step.WOProjectStep;
import de.metas.project.workorder.step.WOProjectStepRepository;
import de.metas.project.workorder.step.WOProjectStepService;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Project_WO_Step;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Component
@Interceptor(I_C_Project_WO_Step.class)
public class C_Project_WO_Step
{
	private static final AdMessageKey ERROR_MSG_STEP_NO_START_DATE_END_DATE_SPECIFIED = AdMessageKey.of("de.metas.project.workorder.interceptor.WOStepNoStartDateEndDateSpecified");

	@NonNull final WOProjectStepService woProjectStepService;

	public C_Project_WO_Step(final @NonNull WOProjectStepService woProjectStepService)
	{
		this.woProjectStepService = woProjectStepService;
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void afterSave(@NonNull final I_C_Project_WO_Step record)
	{
		// validate
		WOProjectStepRepository.ofRecord(record);
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE },
			ifColumnsChanged = { I_C_Project_WO_Step.COLUMNNAME_IsManuallyLocked })
	public void validateManuallyLocked(@NonNull final I_C_Project_WO_Step record)
	{
		final WOProjectStep step = WOProjectStepRepository.ofRecord(record);

		if (!step.isManuallyLocked())
		{
			return;
		}

		if (step.getDateRange() != null)
		{
			woProjectStepService.validateWOStep(step);
			return;
		}

		throw new AdempiereException(ERROR_MSG_STEP_NO_START_DATE_END_DATE_SPECIFIED)
				.markAsUserValidationError();
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void computeStepDueDate(@NonNull final I_C_Project_WO_Step record)
	{
		final WOProjectStep step = WOProjectStepRepository.ofRecord(record);

		if (step.getWoDueDate() != null)
		{
			return;
		}

		woProjectStepService.setStepDueDate(step);
	}
}
