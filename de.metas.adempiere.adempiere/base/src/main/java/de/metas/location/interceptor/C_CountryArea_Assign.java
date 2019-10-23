package de.metas.location.interceptor;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_CountryArea_Assign;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.location.ICountryAreaBL;
import de.metas.util.Services;

@Interceptor(I_C_CountryArea_Assign.class)
@Component
public final class C_CountryArea_Assign
{

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void validate(I_C_CountryArea_Assign assignment)
	{
		Services.get(ICountryAreaBL.class).validate(assignment);
	}
}
