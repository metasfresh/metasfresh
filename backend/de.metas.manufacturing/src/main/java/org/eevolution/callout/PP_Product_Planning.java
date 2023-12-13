package org.eevolution.callout;

import de.metas.material.maturing.MaturingConfigId;
import de.metas.material.maturing.MaturingConfigLine;
import de.metas.material.maturing.MaturingConfigLineId;
import de.metas.material.maturing.MaturingConfigRepository;
import de.metas.material.planning.ProductPlanning;
import de.metas.material.planning.impl.ProductPlanningDAO;
import de.metas.product.ProductId;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.model.CalloutEngine;
import org.compiere.model.ModelValidator;
import org.eevolution.model.I_PP_Product_Planning;

import java.util.List;

@Callout(I_PP_Product_Planning.class)
@Interceptor(I_PP_Product_Planning.class)
public class PP_Product_Planning extends CalloutEngine
{
	final MaturingConfigRepository maturingConfigRepo;

	public PP_Product_Planning(final MaturingConfigRepository maturingConfigRepo)
	{
		this.maturingConfigRepo = maturingConfigRepo;
	}


	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE,
			ifColumnsChanged = { I_PP_Product_Planning.COLUMNNAME_IsMatured })
	public void validateMandatoryBOMVersionsAndWarehouseId(final I_PP_Product_Planning productPlanningRecord)
	{
		final ProductPlanning productPlanning = ProductPlanningDAO.fromRecord(productPlanningRecord);

		if (!productPlanning.isMatured())
		{
			return;
		}

		if (productPlanning.getBomVersionsId() == null)
		{
			throw new FillMandatoryException(I_PP_Product_Planning.COLUMNNAME_PP_Product_BOMVersions_ID);
		}

		if (productPlanning.getWarehouseId() == null)
		{
			throw new FillMandatoryException(I_PP_Product_Planning.COLUMNNAME_M_Warehouse_ID);
		}
	}
}
