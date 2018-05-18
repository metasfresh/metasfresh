package de.metas.ui.web.order.sales.pricingConditions.view;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_PaymentTerm;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.I_M_Product;
import org.compiere.util.Evaluatees;

import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import lombok.NonNull;

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

public class PricingConditionsRowLookups
{
	public static PricingConditionsRowLookups newInstance()
	{
		return new PricingConditionsRowLookups();
	}

	private final LookupDataSource bpartnerLookup;
	private final LookupDataSource productLookup;
	private final LookupDataSource priceTypeLookup;
	private final LookupDataSource pricingSystemLookup;
	private final LookupDataSource paymentTermLookup;

	private PricingConditionsRowLookups()
	{
		final LookupDataSourceFactory lookupFactory = LookupDataSourceFactory.instance;
		bpartnerLookup = lookupFactory.searchInTableLookup(I_C_BPartner.Table_Name);
		productLookup = lookupFactory.searchInTableLookup(I_M_Product.Table_Name);
		priceTypeLookup = lookupFactory.listByReferenceId(PriceType.AD_Reference_ID);
		pricingSystemLookup = lookupFactory.searchInTableLookup(I_M_PricingSystem.Table_Name);
		paymentTermLookup = lookupFactory.searchInTableLookup(I_C_PaymentTerm.Table_Name);
	}

	public LookupValue lookupBPartner(final int bpartnerId)
	{
		return bpartnerLookup.findById(bpartnerId);
	}

	public LookupValue lookupProduct(final int productId)
	{
		return productLookup.findById(productId);
	}

	public LookupValue lookupPriceType(@NonNull final PriceType priceType)
	{
		return priceTypeLookup.findById(priceType.getCode());
	}

	public LookupValue lookupPricingSystem(final int pricingSystemId)
	{
		return pricingSystemLookup.findById(pricingSystemId);
	}

	public LookupValue lookupPaymentTerm(final int paymentTermId)
	{
		return paymentTermLookup.findById(paymentTermId);
	}

	public LookupValuesList getFieldTypeahead(final String fieldName, final String query)
	{
		return getLookupDataSource(fieldName).findEntities(Evaluatees.empty(), query);
	}

	public LookupValuesList getFieldDropdown(final String fieldName)
	{
		return getLookupDataSource(fieldName).findEntities(Evaluatees.empty(), 20);
	}

	private LookupDataSource getLookupDataSource(final String fieldName)
	{
		if (PricingConditionsRow.FIELDNAME_PaymentTerm.equals(fieldName))
		{
			return paymentTermLookup;
		}
		else if (PricingConditionsRow.FIELDNAME_PriceType.equals(fieldName))
		{
			return priceTypeLookup;
		}
		else if (PricingConditionsRow.FIELDNAME_BasePricingSystem.equals(fieldName))
		{
			return pricingSystemLookup;
		}
		else
		{
			throw new AdempiereException("Field " + fieldName + " does not exist or it's not a lookup field");
		}
	}
}
