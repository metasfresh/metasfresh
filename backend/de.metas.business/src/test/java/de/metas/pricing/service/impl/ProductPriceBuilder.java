package de.metas.pricing.service.impl;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.math.BigDecimal;

import de.metas.pricing.service.ScalePriceUsage;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_ProductPrice;

import de.metas.pricing.service.IPriceListDAO;
import de.metas.tax.api.TaxCategoryId;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.business
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class ProductPriceBuilder
{
	private final I_M_PriceList_Version plv;
	private final I_M_Product product;
	private BigDecimal price;
	private I_M_AttributeSetInstance asi;
	private TaxCategoryId taxCategoryId;

	public ProductPriceBuilder(final I_M_PriceList_Version plv, final I_M_Product product)
	{
		this.plv = plv;
		this.product = product;
	}

	public I_M_ProductPrice build()
	{
		final I_M_ProductPrice pp = InterfaceWrapperHelper.newInstance(I_M_ProductPrice.class, plv);
		pp.setM_PriceList_Version_ID(plv.getM_PriceList_Version_ID());
		pp.setM_Product_ID(product.getM_Product_ID());
		pp.setC_UOM_ID(product.getC_UOM_ID());
		pp.setPriceStd(price);
		pp.setPriceList(price);
		pp.setPriceLimit(price);

		pp.setIsAttributeDependant(asi != null);
		pp.setM_AttributeSetInstance(asi);

		pp.setC_TaxCategory_ID(taxCategoryId.getRepoId());

		final int nextMatchSeqNo = Services.get(IPriceListDAO.class).retrieveNextMatchSeqNo(pp);
		pp.setMatchSeqNo(nextMatchSeqNo);

		pp.setUseScalePrice(ScalePriceUsage.DONT_USE_SCALE_PRICE.getCode());

		saveRecord(pp);
		return pp;
	}

	public ProductPriceBuilder setASI(final I_M_AttributeSetInstance asi)
	{
		this.asi = asi;
		return this;
	}

	public ProductPriceBuilder setPrice(final int price)
	{
		this.price = BigDecimal.valueOf(price);
		return this;
	}

	public ProductPriceBuilder setTaxCategoryId(final TaxCategoryId taxCategoryId)
	{
		this.taxCategoryId = taxCategoryId;
		return this;
	}
}
