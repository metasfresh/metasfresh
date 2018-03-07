/**
 *
 */
package org.adempiere.impexp.product;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.adempiere.pricing.api.IPriceListDAO;
import org.adempiere.util.Services;
import org.compiere.model.I_C_TaxCategory;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_ProductPrice;

import de.metas.pricing.ProductPrices;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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

/**
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class CreateProductPriceCommand
{

	final ProductPriceRequest request;

	public CreateProductPriceCommand(@NonNull final ProductPriceRequest request)
	{
		this.request = request;
	}

	public void createProductPrice_And_PriceListVersionIfNeeded()
	{
		if (!isValidPriceRecord())
		{
			return;
		}

		final I_M_PriceList priceList = request.getPriceList();
		final Timestamp validDate = request.getValidDate();
		I_M_PriceList_Version plv = Services.get(IPriceListDAO.class).retrievePriceListVersionWithExactValidDate(priceList.getM_PriceList_ID(), validDate);

		if (plv == null)
		{
			plv = createPriceListVersion(priceList, validDate);
		}

		createProductPriceOrUpdateExistentOne(plv);
	}

	private boolean isValidPriceRecord()
	{
		return request.getProduct() != null
				&& request.getPrice().signum() > 0
				&& request.getPriceList() != null
				&& request.getValidDate() != null;
	}

	private I_M_ProductPrice createProductPriceOrUpdateExistentOne(@NonNull final I_M_PriceList_Version plv)
	{
		final I_C_TaxCategory taxCategory = request.getTaxCategory();
		final BigDecimal price = request.getPrice();
		I_M_ProductPrice pp = ProductPrices.retrieveMainProductPriceOrNull(plv, request.getProduct().getM_Product_ID());
		if (pp == null)
		{
			pp = newInstance(I_M_ProductPrice.class, plv);
		}
		pp.setM_PriceList_Version(plv);
		pp.setM_Product(request.getProduct());
		pp.setPriceLimit(price);
		pp.setPriceList(price);
		pp.setPriceStd(price);
		pp.setC_UOM(request.getProduct().getC_UOM());
		pp.setC_TaxCategory(taxCategory);
		save(pp);

		return pp;
	}

	private I_M_PriceList_Version createPriceListVersion(@NonNull final I_M_PriceList priceList, @NonNull final Timestamp validFrom)
	{
		final I_M_PriceList_Version plv = newInstance(I_M_PriceList_Version.class, priceList);
		plv.setName(priceList.getName() + validFrom);
		plv.setValidFrom(validFrom);
		plv.setM_PriceList(priceList);
		plv.setProcessed(true);
		save(plv);

		// now set the previous one as base list
		final I_M_PriceList_Version previousPlv = Services.get(IPriceListDAO.class).retrievePreviousVersionOrNull(plv);
		if (previousPlv != null)
		{
			plv.setM_Pricelist_Version_Base(previousPlv);
			save(plv);
		}

		return plv;
	}
}
