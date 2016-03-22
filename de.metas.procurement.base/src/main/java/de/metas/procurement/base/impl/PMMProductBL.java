package de.metas.procurement.base.impl;

import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.interfaces.I_C_BPartner_Product;
import de.metas.procurement.base.IPMMProductBL;
import de.metas.procurement.base.IPMMProductDAO;
import de.metas.procurement.base.model.I_PMM_Product;
import de.metas.purchasing.api.IBPartnerProductDAO;

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
		final I_M_HU_PI_Item_Product huPiItemProd = pmmProduct.getM_HU_PI_Item_Product();
		if (huPiItemProd != null)
		{
			pmmProduct.setValidFrom(huPiItemProd.getValidFrom());
			pmmProduct.setValidTo(huPiItemProd.getValidTo());
			pmmProduct.setC_BPartner_ID(huPiItemProd.getC_BPartner_ID());
			pmmProduct.setPackDescription(huPiItemProd.getDescription());

			final I_M_Product product = huPiItemProd.getM_Product();
			pmmProduct.setM_Product(product);
			pmmProduct.setProductName(product.getName());
		}
		else
		{
			pmmProduct.setValidFrom(null);
			pmmProduct.setValidTo(null);
			pmmProduct.setC_BPartner(null);
			pmmProduct.setPackDescription(null);
			pmmProduct.setProductName(null);
		}

		//
		// Update from C_BPartner_Product
		if (pmmProduct.getC_BPartner_ID() > 0 && pmmProduct.getM_Product_ID() > 0)
		{
			final IBPartnerProductDAO bpartnerProductDAO = Services.get(IBPartnerProductDAO.class);

			final I_C_BPartner bpartner = pmmProduct.getC_BPartner();
			final I_M_Product product = pmmProduct.getM_Product();
			final I_C_BPartner_Product bpartnerProduct = InterfaceWrapperHelper.create(
					bpartnerProductDAO.retrieveBPartnerProductAssociation(bpartner, product),
					I_C_BPartner_Product.class);
			if (bpartnerProduct != null && !Check.isEmpty(bpartnerProduct.getProductName(), true))
			{
				pmmProduct.setProductName(bpartnerProduct.getProductName());
			}
		}

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
