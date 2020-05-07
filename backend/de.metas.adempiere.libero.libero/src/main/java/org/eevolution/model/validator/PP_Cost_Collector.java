package org.eevolution.model.validator;

import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;

/*
 * #%L
 * de.metas.adempiere.libero.libero
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.util.Services;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.ModelValidator;
import org.eevolution.api.IPPCostCollectorBL;
import org.eevolution.model.I_PP_Cost_Collector;

import de.metas.material.planning.pporder.LiberoException;

@Interceptor(I_PP_Cost_Collector.class)
public class PP_Cost_Collector
{
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
			final I_M_Warehouse wh = cc.getM_Warehouse();
			final I_M_Locator locator = Services.get(IWarehouseBL.class).getDefaultLocator(wh);
			if (locator != null)
			{
				cc.setM_Locator(locator);
			}
		}

		final IPPCostCollectorBL ppCostCollectorBL = Services.get(IPPCostCollectorBL.class);

		//
		// Case: Material Issue and By/Co-Products receipts
		// => validate against PP_Order_BOMLine which is mandatory
		final boolean isMaterialIssue = ppCostCollectorBL.isMaterialIssue(cc, true); // considerCoProductsAsIssue=true
		if (isMaterialIssue)
		{
			if (cc.getPP_Order_BOMLine_ID() <= 0)
			{
				throw new FillMandatoryException(I_PP_Cost_Collector.COLUMNNAME_PP_Order_BOMLine_ID);
			}
			// If no UOM, use the UOM from BOMLine
			if (cc.getC_UOM_ID() <= 0)
			{
				cc.setC_UOM_ID(cc.getPP_Order_BOMLine().getC_UOM_ID());
			}
			// If Cost Collector UOM differs from BOM Line UOM then throw exception because this conversion is not supported yet
			if (cc.getC_UOM_ID() != cc.getPP_Order_BOMLine().getC_UOM_ID())
			{
				throw new LiberoException("@PP_Cost_Collector_ID@ @C_UOM_ID@ <> @PP_Order_BOMLine_ID@ @C_UOM_ID@");
			}
		}

		//
		// Case: Activity Control
		// => validate against PP_Order_Node which is mandatory
		final boolean isActivityControl = ppCostCollectorBL.isActivityControl(cc);
		if (isActivityControl && cc.getPP_Order_Node_ID() <= 0)
		{
			throw new FillMandatoryException(I_PP_Cost_Collector.COLUMNNAME_PP_Order_Node_ID);
		}
	}

}
