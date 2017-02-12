package de.metas.product.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.util.Check;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_PriceList;

import de.metas.adempiere.model.I_C_Location;
import de.metas.adempiere.model.I_M_Product;

public class PlainProductPA extends ProductPA
{

	private final POJOLookupMap db = POJOLookupMap.get();

	public POJOLookupMap getDB()
	{
		return db;
	}

	@Override
	public I_M_PriceList retrievePriceListByPricingSyst(final Properties ctx, final int pricingSystemId, final int bPartnerLocationId, final boolean isSOPriceList, final String trxName)
	{
		final List<I_M_PriceList> priceLists = db.getRecords(I_M_PriceList.class, new IQueryFilter<I_M_PriceList>()
		{

			@Override
			public boolean accept(I_M_PriceList pojoPriceList)
			{
				if (pricingSystemId != pojoPriceList.getM_PricingSystem_ID())
				{
					return false;
				}

				if (isSOPriceList != pojoPriceList.isSOPriceList())
				{
					return false;
				}

				if (!pojoPriceList.isActive())
				{
					return false;
				}

				final int countryID = pojoPriceList.getC_Country_ID();
				Check.assume(countryID > 0, "No country in price list");

				final List<I_C_Location> locations = db.getRecords(I_C_Location.class, new IQueryFilter<I_C_Location>()
				{

					@Override
					public boolean accept(I_C_Location pojoLocation)
					{
						if (pojoLocation.getC_Country_ID() != countryID)
						{
							return false;
						}

						return true;
					}

				});

				if (locations.isEmpty())
				{
					return false;
				}

				// TODO: Implement this:
				// + " ORDER BY coalesce(l.C_Location_ID, 0) DESC";

				return true;
			}
		});

		final I_M_PriceList pl;

		if (priceLists.isEmpty())
		{
			pl = null;
		}
		else
		{
			pl = priceLists.get(0);

		}

		return pl;
	}

	@Override
	public I_C_UOM retrieveProductUOM(Properties ctx, int productId)
	{
		final I_M_Product product = retrieveProduct(ctx, productId, true, ITrx.TRXNAME_None);
		return product.getC_UOM();
	}

}
