package de.metas.procurement.base.rfq.model.interceptor;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidator;

import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.procurement.base.model.I_PMM_Product;
import de.metas.procurement.base.rfq.model.I_C_RfQLine;

/*
 * #%L
 * de.metas.procurement.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

@Interceptor(I_C_RfQLine.class)
@Callout(value = I_C_RfQLine.class)
public class C_RfQLine
{
	@Init
	public void onInit()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	@CalloutMethod(columnNames = I_C_RfQLine.COLUMNNAME_PMM_Product_ID)
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = I_C_RfQLine.COLUMNNAME_PMM_Product_ID)
	public void onPMM_Product_ID(final I_C_RfQLine rfqLine)
	{
		final I_PMM_Product pmmProduct = rfqLine.getPMM_Product();
		if (pmmProduct == null)
		{
			return;
		}

		rfqLine.setM_Product_ID(pmmProduct.getM_Product_ID());
		// rfqLine.setM_AttributeSetInstance_ID(pmmProduct.getM_AttributeSetInstance_ID()); // don't set it because we want to prevent changing it

		final I_M_HU_PI_Item_Product huPIItemProduct = pmmProduct.getM_HU_PI_Item_Product();
		if (huPIItemProduct != null)
		{
			rfqLine.setC_UOM_ID(huPIItemProduct.getC_UOM_ID());
		}
	}
}
