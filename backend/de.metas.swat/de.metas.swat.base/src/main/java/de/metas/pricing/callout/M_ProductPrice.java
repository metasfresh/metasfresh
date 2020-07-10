package de.metas.pricing.callout;

/*
 * #%L
 * de.metas.swat.base
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

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.api.ICalloutField;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_ProductPrice;

import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.UomId;
import de.metas.util.Services;

@Callout(I_M_ProductPrice.class)
public class M_ProductPrice
{
	/**
	 * Set {@link I_M_ProductPrice}'s C_UOM_ID to {@link I_M_Product}'s C_UOM_ID
	 *
	 * @param productPrice
	 * @param field
	 */
	@CalloutMethod(columnNames = { I_M_ProductPrice.COLUMNNAME_M_Product_ID })
	public void onProductID(final I_M_ProductPrice productPrice, final ICalloutField field)
	{
		final ProductId productId = ProductId.ofRepoIdOrNull(productPrice.getM_Product_ID());
		if (productId != null)
		{
			final UomId stockingUOMId = Services.get(IProductBL.class).getStockUOMId(productId);
			productPrice.setC_UOM_ID(stockingUOMId.getRepoId());
		}
	}

	@CalloutMethod(columnNames = { I_M_ProductPrice.COLUMNNAME_IsAttributeDependant })
	public void onIsAttributeDependent(final I_M_ProductPrice productPrice)
	{
		if (!productPrice.isAttributeDependant())
		{
			return;
		}

		if (productPrice.getSeqNo() <= 0)
		{
			final int nextMatchSeqNo = Services.get(IPriceListDAO.class).retrieveNextMatchSeqNo(productPrice);
			productPrice.setMatchSeqNo(nextMatchSeqNo);
		}
	}

	@CalloutMethod(columnNames = { I_M_ProductPrice.COLUMNNAME_M_PriceList_Version_ID })
	public void onPriceListVersionId(final I_M_ProductPrice productPrice)
	{
		setTaxCategoryIdFromPriceListVersion(productPrice);
	}

	static void setTaxCategoryIdFromPriceListVersion(final I_M_ProductPrice productPrice)
	{
		final PriceListVersionId priceListVersionId = PriceListVersionId.ofRepoIdOrNull(productPrice.getM_PriceList_Version_ID());
		if (priceListVersionId == null)
		{
			return;
		}

		final IPriceListDAO priceListsRepo = Services.get(IPriceListDAO.class);
		final TaxCategoryId defaultTaxCategoryId = priceListsRepo
				.getDefaultTaxCategoryByPriceListVersionId(priceListVersionId)
				.orElse(null);
		if (defaultTaxCategoryId == null)
		{
			return;
		}

		productPrice.setC_TaxCategory_ID(defaultTaxCategoryId.getRepoId());
	}
}
