package de.metas.ordercandidate.modelvalidator;

import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_Scheduler;
import org.compiere.model.ModelValidator;

import de.metas.ordercandidate.api.OLCandProcessorRepository;

@Validator(I_AD_Scheduler.class)
public class AD_Scheduler
{

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void resetOLCandProcessorReference(final I_AD_Scheduler scheduler)
	{
		Adempiere.getBean(OLCandProcessorRepository.class).handleADSchedulerBeforeDelete(scheduler.getAD_Scheduler_ID());
	}
}
