package de.metas.storage;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Set;

import com.google.common.collect.ImmutableSet;

/**
 * A storage segment can identify a set of concrete storage records.
 * <p>
 * Note that storage segments are conceptually somehow close to {@link IStorageQuery}, but in practice the two are intended for different things:
 * <ul>
 * <li>storage query: used to specify which {@link IStorageRecord} shall be retrieved/loaded by the system. I.e. other modules use a query to obtain storage records. Current use case: the shipment
 * schedule module retrieves storage records to find out which on-hand quantities can be allocated to which shipment schedule records.
 * <li>storage segment: used to specify a set of records which are of interest in whatever context. I.e. other modules are notified that they shall do something about a particular set of storages.
 * Current use case: for a given HU(-change), we create a storage segment that is then passed to the shipment-schedule in order to invalidate schedule records that are affected by this segment.
 * </ul>
 *
 */
public interface IStorageSegment
{
	Integer ANY = null;

	Set<Integer> getM_Product_IDs();

	Set<Integer> getM_Locator_IDs();

	Set<Integer> getC_BPartner_IDs();

	default Set<Integer> getBill_BPartner_IDs()
	{
		return ImmutableSet.of();
	}

	default Set<IStorageAttributeSegment> getAttributes()
	{
		return ImmutableSet.of();
	}

}
