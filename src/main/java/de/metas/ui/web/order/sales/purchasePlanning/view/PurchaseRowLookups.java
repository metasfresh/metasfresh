package de.metas.ui.web.order.sales.purchasePlanning.view;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.translate;

import org.adempiere.bpartner.BPartnerId;
import org.adempiere.bpartner.service.IBPartnerDAO;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Product;

import de.metas.product.IProductBL;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.ui.web.window.datatypes.json.JSONLookupValue;

/*
 * #%L
 * metasfresh-webui-api
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

class PurchaseRowLookups
{
	public static final PurchaseRowLookups newInstance()
	{
		return new PurchaseRowLookups();
	}

	private final IProductDAO productsRepo = Services.get(IProductDAO.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IBPartnerDAO bpartnersRepo = Services.get(IBPartnerDAO.class);

	private PurchaseRowLookups()
	{
	}

	public JSONLookupValue createProductLookupValue(final ProductId productId)
	{
		final String productValue = null;
		final String productName = null;
		return createProductLookupValue(productId, productValue, productName);
	}

	public JSONLookupValue createProductLookupValue(
			final ProductId productId,
			final String productValue,
			final String productName)
	{
		if (productId == null)
		{
			return null;
		}

		final I_M_Product product = productsRepo.getById(productId);
		if (product == null)
		{
			return JSONLookupValue.unknown(productId.getRepoId());
		}

		final String productValueEffective = !Check.isEmpty(productValue, true) ? productValue.trim() : product.getValue();
		final String productNameEffective = !Check.isEmpty(productName, true) ? productName.trim() : product.getName();
		final String displayName = productValueEffective + "_" + productNameEffective;
		return JSONLookupValue.of(product.getM_Product_ID(), displayName);
	}

	public JSONLookupValue createASILookupValue(final AttributeSetInstanceId attributeSetInstanceId)
	{
		if (attributeSetInstanceId == null)
		{
			return null;
		}

		final I_M_AttributeSetInstance asi = loadOutOfTrx(attributeSetInstanceId.getRepoId(), I_M_AttributeSetInstance.class);
		if (asi == null)
		{
			return null;
		}

		String description = asi.getDescription();
		if (Check.isEmpty(description, true))
		{
			description = "<" + attributeSetInstanceId.getRepoId() + ">";
		}

		return JSONLookupValue.of(attributeSetInstanceId.getRepoId(), description);
	}

	public JSONLookupValue createBPartnerLookupValue(final BPartnerId bpartnerId)
	{
		if (bpartnerId == null)
		{
			return null;
		}

		final I_C_BPartner bpartner = bpartnersRepo.getById(bpartnerId);
		if (bpartner == null)
		{
			return null;
		}

		final String displayName = bpartner.getValue() + "_" + bpartner.getName();
		return JSONLookupValue.of(bpartner.getC_BPartner_ID(), displayName);
	}

	public String createUOMLookupValueForProductId(final ProductId productId)
	{
		if (productId == null)
		{
			return null;
		}

		final I_C_UOM uom = productBL.getStockingUOM(productId);
		if (uom == null)
		{
			return null;
		}

		return createUOMLookupValue(uom);
	}

	private String createUOMLookupValue(final I_C_UOM uom)
	{
		return translate(uom, I_C_UOM.class).getUOMSymbol();
	}

}
