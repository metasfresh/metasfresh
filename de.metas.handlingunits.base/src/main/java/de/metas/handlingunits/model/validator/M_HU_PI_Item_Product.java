package de.metas.handlingunits.model.validator;

/*
 * #%L
 * de.metas.handlingunits.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Product;
import org.compiere.model.ModelValidator;

import de.metas.handlingunits.IHUCapacityBL;
import de.metas.handlingunits.IHUPIItemProductBL;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.i18n.IMsgBL;

@Validator(I_M_HU_PI_Item_Product.class)
public class M_HU_PI_Item_Product
{

	private static final String MSG_QUANTITY_INVALID = "de.metas.handlingunits.InvalidQuantity";

	@Init
	public void setupCallouts()
	{
		final IProgramaticCalloutProvider calloutProvider = Services.get(IProgramaticCalloutProvider.class);
		calloutProvider.registerAnnotatedCallout(new de.metas.handlingunits.callout.M_HU_PI_Item_Product());
	}

	@ModelChange(
			timings = {
					ModelValidator.TYPE_BEFORE_NEW,
					ModelValidator.TYPE_BEFORE_CHANGE
			})
	public void setNameAndDescription(final I_M_HU_PI_Item_Product itemProduct)
	{
		Services.get(IHUPIItemProductBL.class).setNameAndDescription(itemProduct);
	}

	@ModelChange(
			timings = {
					ModelValidator.TYPE_BEFORE_NEW,
					ModelValidator.TYPE_BEFORE_CHANGE
			},
			ifColumnsChanged = { I_M_HU_PI_Item_Product.COLUMNNAME_IsAllowAnyProduct }
			)
			public void updateItemProduct(final I_M_HU_PI_Item_Product itemProduct)
	{
		if (itemProduct.isAllowAnyProduct())
		{
			itemProduct.setM_Product(null);
			itemProduct.setIsInfiniteCapacity(true);
		}
	}

	@ModelChange(
			timings = {
					ModelValidator.TYPE_BEFORE_NEW,
					ModelValidator.TYPE_BEFORE_CHANGE
			})
	public void validateItemProduct(final I_M_HU_PI_Item_Product itemProduct)
	{
		if (Services.get(IHUCapacityBL.class).isValidItemProduct(itemProduct))
		{
			return;
		}

		final String errorMsg = Services.get(IMsgBL.class).getMsg(InterfaceWrapperHelper.getCtx(itemProduct), M_HU_PI_Item_Product.MSG_QUANTITY_INVALID);
		throw new AdempiereException(errorMsg);
	}

	@ModelChange(
			timings = {
					ModelValidator.TYPE_BEFORE_NEW,
					ModelValidator.TYPE_BEFORE_CHANGE
			})
	public void setUOMItemProduct(final I_M_HU_PI_Item_Product huPiItemProduct)
	{
		final I_M_Product product = huPiItemProduct.getM_Product();

		if (product == null)
		{
			// nothing to do
			return;
		}

		huPiItemProduct.setC_UOM(product.getC_UOM());
	}
}
