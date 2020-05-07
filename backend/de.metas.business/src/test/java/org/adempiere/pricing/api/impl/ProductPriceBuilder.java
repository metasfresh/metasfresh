package org.adempiere.pricing.api.impl;

import java.math.BigDecimal;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.IPriceListDAO;
import org.adempiere.util.Services;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_ProductPrice;

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

	public ProductPriceBuilder(final I_M_PriceList_Version plv, final I_M_Product product)
	{
		this.plv = plv;
		this.product = product;
	}

	public I_M_ProductPrice build()
	{
		final I_M_ProductPrice pp = InterfaceWrapperHelper.newInstance(I_M_ProductPrice.class, plv);
		pp.setM_PriceList_Version(plv);
		pp.setM_Product(product);
		pp.setPriceStd(price);
		pp.setPriceList(price);
		pp.setPriceLimit(price);

		pp.setIsAttributeDependant(asi != null);
		pp.setM_AttributeSetInstance(asi);
		
		final int nextMatchSeqNo = Services.get(IPriceListDAO.class).retrieveNextMatchSeqNo(pp);
		pp.setMatchSeqNo(nextMatchSeqNo);

		InterfaceWrapperHelper.save(pp);
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

}
