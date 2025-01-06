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

import de.metas.material.planning.pporder.LiberoException;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.compiere.model.ModelValidator;
import org.eevolution.api.BOMComponentType;
import org.eevolution.api.IProductBOMBL;
import org.eevolution.api.IProductBOMDAO;
import org.eevolution.api.impl.ProductBOMDAO;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.I_PP_Product_BOMLine;

import java.util.List;

@Validator(I_PP_Product_BOMLine.class)
public class PP_Product_BOMLine
{
	@Init
	public void init()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(new org.eevolution.callout.PP_Product_BOMLine());
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void beforeSave(final I_PP_Product_BOMLine bomLine)
	{
		validate(bomLine);

		//
		// Update Line#
		if (bomLine.getLine() <= 0)
		{
			final IProductBOMDAO productBOMsRepo = Services.get(IProductBOMDAO.class);
			final int lastLineNo = productBOMsRepo.retrieveLastLineNo(bomLine.getPP_Product_BOM_ID());
			bomLine.setLine(lastLineNo + 10);
		}

	}

	private void validate(final I_PP_Product_BOMLine bomLine)
	{
		final BOMComponentType componentType = BOMComponentType.ofCode(bomLine.getComponentType());

		//
		// For Co/By Products, Qty should be always negative:
		if (componentType.isCoProduct()
				&& Services.get(IProductBOMBL.class).getQtyExcludingScrap(bomLine).signum() >= 0)
		{
			throw new LiberoException("@Qty@ > 0");
		}

		if (componentType.isVariant() && Check.isEmpty(bomLine.getVariantGroup()))
		{
			throw new LiberoException("@MandatoryVariant@");
		}

		final boolean valid = Services.get(IProductBOMBL.class).isValidVariantGroup(bomLine);
		if (!valid)
		{
			throw new LiberoException("@NoSuchVariantGroup@");
		}

		ProductBOMDAO.extractIssuingToleranceSpec(bomLine);
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = I_PP_Order_BOMLine.COLUMNNAME_VariantGroup)
	public void setVariantGroupToVA(final I_PP_Product_BOMLine bomLine)
	{
		final BOMComponentType currentComponentType = BOMComponentType.ofCode(bomLine.getComponentType());
		if (currentComponentType.isComponent())
		{
			final IProductBOMDAO bomDAO = Services.get(IProductBOMDAO.class);
			final List<I_PP_Product_BOMLine> bomLines = bomDAO.retrieveLines(bomLine.getPP_Product_BOM());
			for (I_PP_Product_BOMLine bl : bomLines)
			{
				final BOMComponentType componentType = BOMComponentType.ofCode(bl.getComponentType());
				if (componentType.isVariant() && noAccordinglyVariantGroup(bl))
				{
					bl.setVariantGroup(bomLine.getVariantGroup());
					bomDAO.save(bl);
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
			final BOMComponentType componentType = BOMComponentType.ofCode(bl.getComponentType());
			if (componentType.isComponent() && bomLine.getVariantGroup().equals(bl.getVariantGroup()))
			{
				return false;
			}
		}

		return true;
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE }, skipIfCopying = true)
	public void checkingBOMCycle(final I_PP_Product_BOMLine bomLine)
	{
		final ProductId productId = ProductId.ofRepoId(bomLine.getM_Product_ID());
		Services.get(IProductBOMBL.class).checkCycles(productId);
	}
}
