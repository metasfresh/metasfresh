package de.metas.ordercandidate.modelvalidator;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Scheduler;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.ordercandidate.api.OLCandProcessorRepository;

@Interceptor(I_AD_Scheduler.class)
@Component
public class AD_Scheduler
{
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void resetOLCandProcessorReference(final I_AD_Scheduler scheduler)
	{
		SpringContextHolder.instance.getBean(OLCandProcessorRepository.class).handleADSchedulerBeforeDelete(scheduler.getAD_Scheduler_ID());
	}
}
