package de.metas.handlingunits.model.validator;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidator;

import de.metas.handlingunits.model.I_M_ProductPrice;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2017 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Model validator <b>with attached callout</b>. See {@link #unsetIsHUPrice(I_M_ProductPrice)} for what it does.
 * 
 * @author ts
 * @task http://dewiki908/mediawiki/index.php/09045_Changes_on_Excel_Export-Import_%28107395757529%29
 *
 */
@Callout(I_M_ProductPrice.class)
@Validator(I_M_ProductPrice.class)
public class M_ProductPrice
{
	@Init
	public void registerCallout()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}
	
	/**
	 * We now allow PIIPs with unlimited capacity, but in that case, we make sure that the <code>M_ProductPrice_Attribute</code> is not a <code>HUPrice</code>.
	 * @param productPrice
	 */
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE,
			ifColumnsChanged = I_M_ProductPrice.COLUMNNAME_M_HU_PI_Item_Product_ID)
	@CalloutMethod(columnNames = { I_M_ProductPrice.COLUMNNAME_M_HU_PI_Item_Product_ID })
	public void unsetIsHUPrice(final I_M_ProductPrice productPrice)
	{
		if (productPrice.getM_HU_PI_Item_Product_ID() <= 0)
		{
			productPrice.setIsHUPrice(false);
		}
		else if (productPrice.getM_HU_PI_Item_Product().isInfiniteCapacity())
		{
			productPrice.setIsHUPrice(false);
		}
	}
}
