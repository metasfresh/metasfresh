package de.metas.resource.interceptor;

import de.metas.resource.DatabaseHumanResourceTestGroupRepository;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_S_HumanResourceTestGroup;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_S_HumanResourceTestGroup.class)
@Component
public class S_HumanResourceTestGroup
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void beforeSave(final I_S_HumanResourceTestGroup record)
	{
		DatabaseHumanResourceTestGroupRepository.fromRecord(record); // validate
	}
}
