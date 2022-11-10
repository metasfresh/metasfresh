package de.metas.manufacturing.order.weighting.interceptor;

import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.lang.Percent;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;
import org.eevolution.model.I_PP_Order_Weighting_Run;
import org.springframework.stereotype.Component;

@Interceptor(I_PP_Order_Weighting_Run.class)
@Component
public class PP_Order_Weighting_Run
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void beforeSave(final I_PP_Order_Weighting_Run weightingRun)
	{
		if (InterfaceWrapperHelper.isValueChanged(weightingRun,
				I_PP_Order_Weighting_Run.COLUMNNAME_TargetWeight,
				I_PP_Order_Weighting_Run.COLUMNNAME_Tolerance_Perc,
				I_PP_Order_Weighting_Run.COLUMNNAME_C_UOM_ID))
		{
			final Quantity targetWeight = Quantitys.create(weightingRun.getTargetWeight(), UomId.ofRepoId(weightingRun.getC_UOM_ID()));
			final Percent tolerancePerc = Percent.of(weightingRun.getTolerance_Perc());
			final Quantity tolerance = targetWeight.multiply(tolerancePerc);

			weightingRun.setMinWeight(targetWeight.subtract(tolerance).toBigDecimal());
			weightingRun.setMaxWeight(targetWeight.add(tolerance).toBigDecimal());
		}

	}
}
