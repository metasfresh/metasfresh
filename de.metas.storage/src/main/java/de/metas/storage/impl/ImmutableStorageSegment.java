package de.metas.storage.impl;

/*
 * #%L
 * de.metas.storage
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
import java.util.HashSet;
import java.util.Set;

import de.metas.storage.AbstractStorageSegment;
import de.metas.storage.IStorageAttributeSegment;
import de.metas.storage.IStorageSegment;

/**
 * Plain Immutable {@link IStorageSegment} implementation
 *
 * @author tsa
 *
 */
public class ImmutableStorageSegment extends AbstractStorageSegment
{
	private final Set<Integer> productIds;
	private final Set<Integer> bpartnerIds;
	private final Set<Integer> locatorIds;
	private final Set<IStorageAttributeSegment> attributeSegments;

	public ImmutableStorageSegment(final Set<Integer> productIds,
			final Set<Integer> bpartnerIds,
			final Set<Integer> locatorIds,
			final Set<IStorageAttributeSegment> attributeSegments)
	{
		super();
		this.productIds = copyOrEmpty(productIds);
		this.bpartnerIds = copyOrEmpty(bpartnerIds);
		this.locatorIds = copyOrEmpty(locatorIds);
		this.attributeSegments = copyOrEmpty(attributeSegments);
	}

	private static final <T> Set<T> copyOrEmpty(final Set<T> set)
	{
		if (set == null || set.isEmpty())
		{
			return Collections.emptySet();
		}
		return Collections.unmodifiableSet(new HashSet<>(set));
	}

	@Override
	public Set<Integer> getM_Product_IDs()
	{
		return productIds;
	}

	@Override
	public Set<Integer> getC_BPartner_IDs()
	{
		return bpartnerIds;
	}

	@Override
	public Set<Integer> getM_Locator_IDs()
	{
		return locatorIds;
	}

	@Override
	public Set<IStorageAttributeSegment> getAttributes()
	{
		return attributeSegments;
	}

}
