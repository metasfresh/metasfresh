package de.metas.procurement.base.impl;

import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.procurement.base.IPMMProductBL;
import de.metas.procurement.base.IPMMProductDAO;
import de.metas.procurement.base.model.I_PMM_Product;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class PMMProductBL implements IPMMProductBL
{
	@Override
	public void update(final I_PMM_Product pmmProduct)
	{
		//
		// Update PMM_Product from M_HU_PI_Item_Product 
		final I_M_HU_PI_Item_Product huPiItemProd = pmmProduct.getM_HU_PI_Item_Product();
		if (huPiItemProd != null)
		{
			pmmProduct.setValidFrom(huPiItemProd.getValidFrom());
			pmmProduct.setValidTo(huPiItemProd.getValidTo());
			pmmProduct.setC_BPartner_ID(huPiItemProd.getC_BPartner_ID());
			pmmProduct.setPackDescription(huPiItemProd.getDescription());

			final I_M_Product product = huPiItemProd.getM_Product();
			pmmProduct.setM_Product(product);
		}
		else
		{
			pmmProduct.setValidFrom(null);
			pmmProduct.setValidTo(null);
			pmmProduct.setC_BPartner(null);
			pmmProduct.setPackDescription(null);
		}
		
		//
		// Build and set the full product name
		final String productName = PMMProductNameBuilder.newBuilder()
				.setPMM_Product(pmmProduct)
				.build();
		pmmProduct.setProductName(productName);
	}

	@Override
	public void updateByHUPIItemProduct(final I_M_HU_PI_Item_Product huPIItemProduct)
	{
		final List<I_PMM_Product> pmmProducts = Services.get(IPMMProductDAO.class).retrieveByHUPIItemProduct(huPIItemProduct);
		if (pmmProducts.isEmpty())
		{
			return;
		}

		// Prevent changing the IsActive flag is there are some PMM_Products for it
		if (!huPIItemProduct.isActive())
		{
			throw new AdempiereException("@I_M_HU_PI_Item_Product_ID@ @IsActive@=@N@: " + huPIItemProduct.getDescription());
		}

		for (final I_PMM_Product pmmProduct : pmmProducts)
		{
			pmmProduct.setM_HU_PI_Item_Product(huPIItemProduct); // optimization
			update(pmmProduct);
		}
	}

	@Override
	public void updateByProduct(final I_M_Product product)
	{
		final List<I_PMM_Product> pmmProducts = Services.get(IPMMProductDAO.class).retrieveByProduct(product);

		for (final I_PMM_Product pmmProduct : pmmProducts)
		{
			update(pmmProduct);
			InterfaceWrapperHelper.save(pmmProduct);
		}
	}

	@Override
	public void updateByBPartner(final I_C_BPartner bpartner)
	{
		final List<I_PMM_Product> pmmProducts = Services.get(IPMMProductDAO.class).retrieveByBPartner(bpartner);

		for (final I_PMM_Product pmmProduct : pmmProducts)
		{
			update(pmmProduct);
			InterfaceWrapperHelper.save(pmmProduct);
		}
	}
}
