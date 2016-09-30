package de.metas.fresh.picking.form;

/*
 * #%L
 * de.metas.fresh.base
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


import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.EqualsBuilder;
import org.adempiere.util.lang.HashcodeBuilder;
import org.compiere.model.I_M_Product;

import de.metas.adempiere.form.ITableRowSearchSelectionMatcher;
import de.metas.adempiere.form.TableRowKey;
import de.metas.handlingunits.model.I_EDI_M_Product_Lookup_UPC_v;

/**
 * Matchers all rows for given barcode. M_Product.UPC and M_Product.Value are matched.
 *
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/06821_Kommissionier_Terminal_Extension_%28104171338645%29
 *
 */
public class ProductTableRowSearchSelectionMatcher implements ITableRowSearchSelectionMatcher
{
	private boolean initialized = false;

	private final Properties ctx;
	private final String barcode;

	private String matchingType;

	/**
	 * Always disallow multiple inserts in this map on the same key (our query should have one and only one partner-product association)
	 */
	private final Map<Integer, Integer> bpartnerId2productId = new HashMap<Integer, Integer>();

	/**
	 * Last used C_BPartner_ID
	 */
	private int bpartnerId = -1;
	private int productId = -1;

	/**
	 * Is this matcher valid?
	 */
	private boolean valid = false;

	public ProductTableRowSearchSelectionMatcher(final Properties ctx, final String barcode)
	{
		super();

		Check.assumeNotNull(ctx, "ctx not null");
		this.ctx = ctx;

		Check.assumeNotEmpty(barcode, "barcode not empty");
		this.barcode = barcode.trim();
	}

	/**
	 * Initialize this matcher
	 */
	private final void init()
	{
		if (initialized)
		{
			return;
		}

		valid = false;
		try
		{
			valid = load();
		}
		finally
		{
			// we mark it as initialized even if there was an error because we want to avoid calling this twice
			initialized = true;
		}
	}

	/**
	 * Called by {@link #init()} to load underlying data.
	 *
	 * This method shall call setters like {@link #setC_BPartner_ID(int)}, {@link #setProductIds(Set)} to configure this matcher correctly.
	 *
	 * @return true if it was correctly initialized; if this method returns false then {@link #isValid()} will return false
	 */
	private boolean load()
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final List<I_EDI_M_Product_Lookup_UPC_v> upcProductCandidates =
				queryBL.createQueryBuilder(I_EDI_M_Product_Lookup_UPC_v.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_EDI_M_Product_Lookup_UPC_v.COLUMNNAME_UPC, barcode)
				.addEqualsFilter(I_EDI_M_Product_Lookup_UPC_v.COLUMNNAME_UsedForCustomer, true)
				.create()
				.list();

		//
		// Allow for searching via UPC: Take the unique UPCs from PI / BPP information
		if (!upcProductCandidates.isEmpty())
		{
			// E - EAN matched (via association with PIIP/BPartner_Product)
			matchingType = "E";

			// verify that we have just one product (per partner) for the given UPC
			for (final I_EDI_M_Product_Lookup_UPC_v upcProductCand : upcProductCandidates)
			{
				final int productIdNew = upcProductCand.getM_Product_ID();
				final int bpartnerId = upcProductCand.getC_BPartner_ID();
				final Integer productIdOld = bpartnerId2productId.put(bpartnerId, productIdNew);
				Check.assume(productIdOld == null || productIdNew == productIdOld,
						"Different products found for same bpartner {}/upc {} combination: {}, {}",
						bpartnerId, barcode, productIdOld, productIdNew);
			}
		}

		final ICompositeQueryFilter<I_M_Product> upcOrValueFilter = queryBL.createCompositeQueryFilter(I_M_Product.class);
		upcOrValueFilter.setJoinOr();

		//
		// Allow for searching via Product's own UPC & value
		upcOrValueFilter.addEqualsFilter(I_M_Product.COLUMNNAME_UPC, barcode);
		upcOrValueFilter.addEqualsFilter(I_M_Product.COLUMNNAME_Value, barcode);

		final I_M_Product product = queryBL.createQueryBuilder(I_M_Product.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient(ctx)
				.filter(upcOrValueFilter)
				.create()
				.firstOnly(I_M_Product.class);

		// no product was found
		if (product == null && upcProductCandidates.isEmpty())
		{
			return false;
		}

		//
		// Try to also match product
		if (product != null)
		{
			if (barcode.equals(product.getUPC()))
			{
				// E - EAN matched (M_Product.UPC matched)
				matchingType = "E";
			}
			else if (upcProductCandidates.isEmpty() // (safety) this case can only be applied when we aren't scanning by UPC
					&& barcode.equals(product.getValue()))
			{
				// P - Product (when scanned by M_Product.Value)
				matchingType = "P";
			}
			else
			{
				// shall not happen
				throw new IllegalStateException("Cannot detect matching type EAN/Value for " + product + ", barcode=" + barcode);
			}
			productId = product.getM_Product_ID();
		}

		// return load ok
		return true;
	}

	@Override
	public String getName()
	{
		init();
		return matchingType;
	}

	@Override
	public boolean isAllowMultipleResults()
	{
		return false;
	}

	@Override
	public boolean match(final TableRowKey key)
	{
		bpartnerId = key.getBPartnerId();

		//
		// Attempt to search for the productId in the selected UPCs first
		final Integer registeredProductId = bpartnerId2productId.get(bpartnerId);
		if (registeredProductId != null
				&& registeredProductId == key.getProductId())
		{
			return true;
		}

		//
		// Fallback, see if the product was retrieved individually (i.e it's own UPC or Value)
		if (productId > 0)
		{
			if (productId == key.getProductId())
			{
				return true;
			}
			return false;
		}
		return false;
	}

	@Override
	public boolean isValid()
	{
		init();
		return valid;
	}

	@Override
	public boolean isNull()
	{
		//
		// If is not a valid rule we also consider it as null rule
		if (!isValid())
		{
			return false;
		}

		//
		// If there are no products, we consider it null
		final Collection<Integer> productIds = bpartnerId2productId.values();
		if (productIds.isEmpty() && productId <= 0)
		{
			return true;
		}

		return false;
	}

	@Override
	public int getC_BPartner_ID()
	{
		return bpartnerId;
	}

	@Override
	public int hashCode()
	{
		return new HashcodeBuilder()
				.append(bpartnerId2productId)
				.append(bpartnerId)
				.append(productId)
				.toHashcode();
	}

	@Override
	public boolean equalsOrNull(final ITableRowSearchSelectionMatcher matcher)
	{
		//
		// Check if both are functionally null
		if (matcher == null || matcher.isNull())
		{
			return isNull();
		}

		return equals(matcher);
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		final ProductTableRowSearchSelectionMatcher other = EqualsBuilder.getOther(this, obj);
		if (other == null)
		{
			return false;
		}

		return new EqualsBuilder()
				.append(bpartnerId2productId, other.bpartnerId2productId)
				.append(bpartnerId, other.bpartnerId)
				.append(productId, other.productId)
				.isEqual();
	}

	@Override
	public String toString()
	{
		return "ProductTableRowSearchSelectionMatcher [initialized=" + initialized + ", barcode=" + barcode + ", matchingType=" + matchingType + ", bpartnerId2productId=" + bpartnerId2productId + ", bpartnerId=" + bpartnerId + ", productId=" + productId + ", valid=" + valid + "]";
	}


}
