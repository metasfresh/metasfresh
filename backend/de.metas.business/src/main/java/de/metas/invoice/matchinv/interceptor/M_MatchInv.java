package de.metas.invoice.matchinv.interceptor;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_MatchInv;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Component
@Interceptor(I_M_MatchInv.class)
public class M_MatchInv
{
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void beforeDelete(final I_M_MatchInv record)
	{
		if (InterfaceWrapperHelper.isUIAction(record))
		{
			throw new AdempiereException("Delete not allowed");
		}
	}
}
