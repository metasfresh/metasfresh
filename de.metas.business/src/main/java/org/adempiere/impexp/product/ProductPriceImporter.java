/**
 *
 */
package org.adempiere.impexp.product;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.IPriceListDAO;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.util.TimeUtil;

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
public class ProductPriceImporter
{

	private final ProductPriceCreateRequest request;

	public ProductPriceImporter(@NonNull final ProductPriceCreateRequest request)
	{
		this.request = request;
	}

	public void createProductPrice_And_PriceListVersionIfNeeded()
	{
		if (!isValidPriceRecord())
		{
			throw new AdempiereException("ProductPriceImporter.InvalidProductPriceList");
		}

		if (request.getPrice().signum() > 0)
		{
			final I_M_PriceList_Version plv = getCreatePriceListVersion(request.getPriceListId(), request.getValidDate());
			createProductPriceOrUpdateExistentOne(plv);
		}
	}

	private boolean isValidPriceRecord()
	{
		return request.getProductId() > 0
				&& request.getPriceListId() > 0
				&& request.getValidDate() != null;
	}

	private I_M_PriceList_Version getCreatePriceListVersion(final int priceListId, @NonNull final LocalDate validDate)
	{
		final I_M_PriceList_Version plv = Services.get(IPriceListDAO.class).retrievePriceListVersionWithExactValidDate(priceListId, TimeUtil.asTimestamp(validDate));
		return plv == null ? createPriceListVersion(priceListId, validDate) : plv;
	}

	private I_M_ProductPrice createProductPriceOrUpdateExistentOne(@NonNull final I_M_PriceList_Version plv)
	{
		final BigDecimal price = request.getPrice();
		I_M_ProductPrice pp = ProductPrices.retrieveMainProductPriceOrNull(plv, request.getProductId());
		if (pp == null)
		{
			pp = newInstance(I_M_ProductPrice.class, plv);
		}
		pp.setM_PriceList_Version(plv);
		pp.setM_Product_ID(request.getProductId());
		pp.setPriceLimit(price);
		pp.setPriceList(price);
		pp.setPriceStd(price);
		final I_C_UOM uom = InterfaceWrapperHelper.load(request.getProductId(), I_M_Product.class).getC_UOM();
		pp.setC_UOM(uom);
		pp.setC_TaxCategory_ID(request.getTaxCategoryId());
		save(pp);

		return pp;
	}

	private I_M_PriceList_Version createPriceListVersion(final int priceListId, @NonNull final LocalDate validFrom)
	{
		final I_M_PriceList_Version plv = newInstance(I_M_PriceList_Version.class);
		plv.setName(validFrom.toString());
		plv.setValidFrom(TimeUtil.asTimestamp(validFrom));
		plv.setM_PriceList_ID(priceListId);
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
