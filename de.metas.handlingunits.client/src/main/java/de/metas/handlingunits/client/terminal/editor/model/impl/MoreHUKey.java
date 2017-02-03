package de.metas.handlingunits.client.terminal.editor.model.impl;

/*
 * #%L
 * de.metas.handlingunits.client
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
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.util.Check;
import org.compiere.model.IQuery;
import org.compiere.util.KeyNamePair;

import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.client.terminal.editor.model.IHUKey;
import de.metas.handlingunits.client.terminal.editor.model.IHUKeyChildrenFilter;
import de.metas.handlingunits.client.terminal.editor.model.IHUKeyFactory;
import de.metas.handlingunits.client.terminal.editor.model.IHUKeyNameBuilder;
import de.metas.handlingunits.document.IHUDocumentLineFinder;
import de.metas.handlingunits.model.I_M_HU;

/**
 * The "+10 items" button which is fetching from underlying {@link IHUQueryBuilder} a specified number of items, each time is pressed.
 *
 * @author tsa
 *
 */
public class MoreHUKey extends AbstractHUKey
{
	// Parameters
	private final IHUQueryBuilder husQuery;
	private final IHUDocumentLineFinder documentLineFinder;

	private final String id;
	private final KeyNamePair value;

	/** How many {@link HUKey}s to fetch on one round */
	private int fetchSize = 10;
	/** True if there no more {@link HUKey}s to extract */
	private boolean empty = false;

	public MoreHUKey(final IHUKeyFactory keyFactory, final IHUQueryBuilder husQuery, final IHUDocumentLineFinder documentLineFinder)
	{
		super(keyFactory);

		Check.assumeNotNull(husQuery, "husQuery not null");
		this.husQuery = husQuery.copy();

		this.documentLineFinder = documentLineFinder;

		id = UUID.randomUUID().toString();
		value = new KeyNamePair(0, "+");
	}

	@Override
	public Object getAggregationKey()
	{
		return id;
	}

	@Override
	public boolean isReadonly()
	{
		return true;
	}

	@Override
	public String getId()
	{
		return id;
	}

	@Override
	public KeyNamePair getValue()
	{
		return value;
	}

	@Override
	protected IHUKeyNameBuilder createHUKeyNameBuilder()
	{
		return new MoreHUKeyNameBuilder(this);
	}

	@Override
	protected Collection<IHUKey> preprocessChildrenBeforeAdding(final Collection<IHUKey> children)
	{
		// nothing
		return children;
	}

	@Override
	public boolean isGrouping()
	{
		return false;
	}

	/**
	 * @return always {@code false}.
	 */
	@Override
	public boolean isVirtualPI()
	{
		return false;
	}

	/**
	 *
	 * @return how many items shall be fetched and added when user presses on this button
	 */
	public int getFetchSize()
	{
		return fetchSize;
	}

	/**
	 * @param fetchSize how many items shall be fetched and added when user presses on this button
	 */
	public void setFetchSize(final int fetchSize)
	{
		if (this.fetchSize == fetchSize)
		{
			return;
		}

		Check.assume(fetchSize > 0, "fetchSize > 0");
		this.fetchSize = fetchSize;
		updateName();
	}

	/**
	 * Load another {@link #getFetchSize()} HUs and add them to it's parent.
	 *
	 * @return true if, after extraction, there could be more keys to be retrieved
	 */
	public final boolean extractNextKeys()
	{
		if (empty)
		{
			return false;
		}

		final IHUQueryBuilder husQuery = this.husQuery.copy(); // use copy of query (filters will be applied)

		final IHUKey parent = getParent();
		if (parent != null) // normally, there will always be a parent for this type of key
		{
			final IHUKeyChildrenFilter childrenFilter = parent.getChildrenFilter();
			if (childrenFilter != null)
			{
				final IQuery<I_M_HU> childrenSubQuery = childrenFilter.getQuery();
				husQuery.setInSubQueryFilter(childrenSubQuery);
			}
		}

		final List<I_M_HU> hus = husQuery.list(fetchSize);
		final boolean hasMoreHUs = hus.size() >= fetchSize;

		extractHUKeys(hus, hasMoreHUs);
		empty = !hasMoreHUs;

		return hasMoreHUs;
	}

	/**
	 * Extracts all {@link HUKey}s.
	 */
	public final void extractAllKeys()
	{
		if (empty)
		{
			return;
		}
		final List<I_M_HU> hus = husQuery.list();
		final boolean hasMoreHUs = false;

		extractHUKeys(hus, hasMoreHUs);
		empty = true;
	}

	/**
	 * Create {@link IHUKey}s from given HUs and add them to it's parent.
	 *
	 * @param hus
	 * @param hasMoreHUs true if there will be more to extract in future. If false then this button will be completelly removed because it's not useful anymore.
	 * @return extracted keys
	 */
	private final List<IHUKey> extractHUKeys(final List<I_M_HU> hus, final boolean hasMoreHUs)
	{
		if (hus == null || hus.isEmpty())
		{
			return Collections.emptyList();
		}

		final IHUKeyFactory keyFactory = getKeyFactory();
		final List<IHUKey> huKeys = keyFactory.createKeys(hus, documentLineFinder);

		// Add HUKeys to parent
		final IHUKey parentKey = getParent();
		Check.assumeNotNull(parentKey, "parentKey not null");
		final List<IHUKey> huKeysAdded = parentKey.addChildren(huKeys);

		//
		// Make sure this button is the last one (if needed)
		parentKey.removeChild(this);
		if (hasMoreHUs)
		{
			updateName();
			parentKey.addChild(this);
		}

		//
		// Exclude extracted HUs next time
		husQuery.addHUsToExclude(hus);

		// After first run, don't throw errors if there are no HU Keys
		husQuery.setErrorIfNoHUs(false, null);

//		//
//		// The parent's filter shall be applied to the existing HUs
//		parentKey.applyChildrenFilter();

		return huKeysAdded;
	}

	/**
	 * Extract all {@link IHUKey}s by using configured filter and additionally applying given filter (logical AND).
	 *
	 * @param filter
	 * @return extracted keys
	 */
	public List<IHUKey> extractAllKeys(final IQueryFilter<I_M_HU> filter)
	{
		final IHUQueryBuilder husQuery = this.husQuery.copy();
		husQuery.addFilter(filter);

		final List<I_M_HU> hus = husQuery.list();
		if (hus.isEmpty())
		{
			return Collections.emptyList();
		}

		final boolean hasMoreHUs = true; // we don't know... so consider it true
		final List<IHUKey> huKeys = extractHUKeys(hus, hasMoreHUs);
		return huKeys;
	}

	@Override
	public void notifyKeyPressed()
	{
		extractNextKeys();
	}

	public IQueryBuilder<I_M_HU> getQueryBuilder()
	{
		return husQuery.createQueryBuilder();
	}

	public void resetEmptyStatus()
	{
		empty = false;
	}

	@Override
	public boolean isDestroyed()
	{
		// TODO: i think in future we can consider this destroyed if it's empty (BUT make sure it's not just temporary empty) 
		return false;
	}
}
