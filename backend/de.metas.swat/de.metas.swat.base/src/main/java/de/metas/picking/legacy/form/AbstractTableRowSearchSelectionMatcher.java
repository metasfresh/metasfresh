package de.metas.picking.legacy.form;

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


import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import org.adempiere.util.Check;
import org.adempiere.util.lang.EqualsBuilder;
import org.adempiere.util.lang.HashcodeBuilder;

/**
 * 
 * Abstract implementation of {@link ITableRowSearchSelectionMatcher} which is implementing some common methods.
 * 
 * Mainly, developer needs to implement {@link #load()} method only.
 * 
 * @author tsa
 * 
 */
public abstract class AbstractTableRowSearchSelectionMatcher implements ITableRowSearchSelectionMatcher
{
	private boolean initialized = false;

	private int bpartnerId = -1;

	/**
	 * Sorted set of M_Product_IDs
	 */
	private final Set<Integer> productIds = new TreeSet<Integer>();
	private final Set<Integer> productIdsRO = Collections.unmodifiableSet(productIds);

	/**
	 * Is this matcher valid?
	 */
	private boolean valid = false;

	@Override
	public final boolean equalsOrNull(final ITableRowSearchSelectionMatcher matcher)
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
	public int hashCode()
	{
		return new HashcodeBuilder()
				.append(bpartnerId)
				.append(productIds)
				.toHashcode();
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		final AbstractTableRowSearchSelectionMatcher other = EqualsBuilder.getOther(this, obj);
		if (other == null)
		{
			return false;
		}

		return new EqualsBuilder()
				.append(bpartnerId, other.bpartnerId)
				.append(productIds, other.productIds)
				.isEqual();
	}

	/**
	 * Initialize this matcher
	 */
	protected final void init()
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
	protected abstract boolean load();

	protected void setC_BPartner_ID(final int bpartnerId)
	{
		Check.assume(!initialized, "not already initialized");
		this.bpartnerId = bpartnerId <= 0 ? -1 : bpartnerId;
	}

	protected void setProductIds(final Set<Integer> productIds)
	{
		Check.assume(!initialized, "not already initialized");
		this.productIds.clear();
		for (final Integer productId : productIds)
		{
			if (productId == null || productId <= 0)
			{
				continue;
			}
			this.productIds.add(productId);
		}
	}

	protected final Set<Integer> getProductIds()
	{
		init();
		return productIdsRO;
	}

	@Override
	public final int getC_BPartner_ID()
	{
		init();
		return bpartnerId;
	}

	@Override
	public final boolean isValid()
	{
		init();
		return valid;
	}

	@Override
	public final boolean isNull()
	{
		//
		// If is not a valid rule we also consider it as null rule
		if (!isValid())
		{
			return false;
		}

		//
		// If there are no products, we consider it null
		final Set<Integer> productIds = getProductIds();
		if (productIds.isEmpty())
		{
			return true;
		}

		return false;
	}

	@Override
	public final boolean match(final TableRowKey key)
	{
		// guard against null (shall not happen)
		if (key == null)
		{
			return false;
		}

		if (!isValid())
		{
			return false;
		}

		final Set<Integer> productIds = getProductIds();

		final int rowProductId = key.getProductId();
		if (!productIds.contains(rowProductId))
		{
			return false;
		}

		return true;
	}
}
