package org.eevolution.model.validator;

import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.LiberoException;
import de.metas.uom.UomId;
import de.metas.util.Services;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.ModelValidator;
import org.eevolution.api.CostCollectorType;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.model.I_PP_Cost_Collector;

@Interceptor(I_PP_Cost_Collector.class)
public class PP_Cost_Collector
{
	private final IPPOrderBOMBL orderBOMBL = Services.get(IPPOrderBOMBL.class);

	@Init
	public void init()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(new org.eevolution.callout.PP_Cost_Collector());
	}

	/**
	 * Validates given cost collector and set missing fields if possible.
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void validateAndSetMissingFields(final I_PP_Cost_Collector cc)
	{
		//
		// Set default locator, if not set and we have the warehouse:
		if (cc.getM_Locator_ID() <= 0 && cc.getM_Warehouse_ID() > 0)
		{
			final WarehouseId warehouseId = WarehouseId.ofRepoId(cc.getM_Warehouse_ID());
			final LocatorId locatorId = Services.get(IWarehouseBL.class).getOrCreateDefaultLocatorId(warehouseId);
			cc.setM_Locator_ID(locatorId.getRepoId());
		}

		//
		// Case: Material Issue and By/Co-Products receipts
		// => validate against PP_Order_BOMLine which is mandatory
		final CostCollectorType costCollectorType = CostCollectorType.ofCode(cc.getCostCollectorType());
		final PPOrderBOMLineId orderBOMLineId = PPOrderBOMLineId.ofRepoIdOrNull(cc.getPP_Order_BOMLine_ID());
		if (costCollectorType.isAnyComponentIssueOrCoProduct(orderBOMLineId))
		{
			if (orderBOMLineId == null)
			{
				throw new FillMandatoryException(I_PP_Cost_Collector.COLUMNNAME_PP_Order_BOMLine_ID);
			}

			final UomId bomLineUOMId = orderBOMBL.getBOMLineUOMId(orderBOMLineId);

			// If no UOM, use the UOM from BOMLine
			if (cc.getC_UOM_ID() <= 0)
			{
				cc.setC_UOM_ID(bomLineUOMId.getRepoId());
			}
			// If Cost Collector UOM differs from BOM Line UOM then throw exception because this conversion is not supported yet
			if (cc.getC_UOM_ID() != bomLineUOMId.getRepoId())
			{
				throw new LiberoException("@PP_Cost_Collector_ID@ @C_UOM_ID@ <> @PP_Order_BOMLine_ID@ @C_UOM_ID@");
			}
		}
		//
		// Case: Activity Control
		// => validate against PP_Order_Node which is mandatory
		else if (costCollectorType.isActivityControl())
		{
			if (cc.getPP_Order_Node_ID() <= 0)
			{
				throw new FillMandatoryException(I_PP_Cost_Collector.COLUMNNAME_PP_Order_Node_ID);
			}
		}
	}

}
