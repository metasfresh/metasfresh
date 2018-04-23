package org.eevolution.model.validator;

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

import java.util.List;

import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Product;
import org.compiere.model.ModelValidator;
import org.eevolution.api.IProductBOMBL;
import org.eevolution.api.IProductBOMDAO;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.I_PP_Product_BOMLine;
import org.eevolution.model.X_PP_Order_BOMLine;

import de.metas.material.planning.pporder.LiberoException;

@Validator(I_PP_Product_BOMLine.class)
public class PP_Product_BOMLine
{
	@Init
	public void init()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(new org.eevolution.callout.PP_Product_BOMLine());
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void validate(final I_PP_Product_BOMLine bomLine)
	{
		if (X_PP_Order_BOMLine.COMPONENTTYPE_Variant.equals(bomLine.getComponentType()) //
				&& Check.isEmpty(bomLine.getVariantGroup()))
		{
			throw new LiberoException("@MandatoryVariant@");
		}

		final boolean valid = Services.get(IProductBOMBL.class).isValidVariantGroup(bomLine);
		if (!valid)
		{
			throw new LiberoException("@NoSuchVariantGroup@");
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = I_PP_Order_BOMLine.COLUMNNAME_VariantGroup)
	public void setVariantGroupToVA(final I_PP_Product_BOMLine bomLine)
	{
		if (X_PP_Order_BOMLine.COMPONENTTYPE_Component.equals(bomLine.getComponentType()))
		{
			final IProductBOMDAO bomDAO = Services.get(IProductBOMDAO.class);
			final List<I_PP_Product_BOMLine> bomLines = bomDAO.retrieveLines(bomLine.getPP_Product_BOM());
			for (I_PP_Product_BOMLine bl : bomLines)
			{
				if (X_PP_Order_BOMLine.COMPONENTTYPE_Variant.equals(bl.getComponentType()) && noAccordinglyVariantGroup(bl))
				{
					bl.setVariantGroup(bomLine.getVariantGroup());
					InterfaceWrapperHelper.save(bl);
				}
			}
		}
	}

	private boolean noAccordinglyVariantGroup(final I_PP_Product_BOMLine bomLine)
	{
		final IProductBOMDAO bomDAO = Services.get(IProductBOMDAO.class);
		final List<I_PP_Product_BOMLine> bomLines = bomDAO.retrieveLines(bomLine.getPP_Product_BOM());
		for (I_PP_Product_BOMLine bl : bomLines)
		{
			if (bl.getComponentType().equals(X_PP_Order_BOMLine.COMPONENTTYPE_Component) && bomLine.getVariantGroup().equals(bl.getVariantGroup()))
			{
				return false;
			}
		}

		return true;
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_DELETE })
	public void updateProductLowestLevelCode(final I_PP_Product_BOMLine bomLine)
	{
		final I_M_Product product = bomLine.getM_Product();
		final int lowLevel = Services.get(IProductBOMBL.class).calculateProductLowestLevel(product.getM_Product_ID());

		product.setLowLevel(lowLevel); // update lowlevel
		InterfaceWrapperHelper.save(product);

		// TODO: if product's low level changed, we need to update the low level from all bom components where this product is in bom header.
	}
}
