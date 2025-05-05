package de.metas.order.costs.interceptor;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_InOut_Cost;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_M_InOut_Cost.class)
@Component
public class M_InOut_Cost
{
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void onBeforeDelete(final I_M_InOut_Cost record)
	{
		if (InterfaceWrapperHelper.isUIAction(record))
		{
			throw new AdempiereException("Deleting not allowed");
		}
	}

}
