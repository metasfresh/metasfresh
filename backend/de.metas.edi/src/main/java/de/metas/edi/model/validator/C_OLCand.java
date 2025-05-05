package de.metas.edi.model.validator;

import de.metas.edi.api.IEDIOLCandBL;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_C_OLCand.class)
@Component
public class C_OLCand
{
	private final IEDIOLCandBL ediOlCandBL = Services.get(IEDIOLCandBL.class);

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_C_OLCand.COLUMNNAME_M_HU_PI_Item_Product_ID, I_C_OLCand.COLUMNNAME_M_HU_PI_Item_Product_Override_ID }
	)
	public void setManualQtyItemCapacity(final I_C_OLCand olCand)
	{
		final de.metas.handlingunits.model.I_C_OLCand olc = InterfaceWrapperHelper.create(olCand, de.metas.handlingunits.model.I_C_OLCand.class);
		olc.setIsManualQtyItemCapacity(ediOlCandBL.isManualQtyItemCapacity(olc));
	}
}
