package de.metas.inoutcandidate.invalidation.segments;

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

import de.metas.storage.IStorageQuery;
import de.metas.storage.IStorageRecord;

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
public interface IShipmentScheduleSegment
{
	Integer ANY = null;

	default boolean isInvalid()
	{
		return isNoProducts() || isNoLocators() || isNoBPartners();
	}

	Set<Integer> getProductIds();

	default boolean isNoProducts()
	{
		final Set<Integer> productIds = getProductIds();
		return productIds == null || productIds.isEmpty();
	}

	default boolean isAnyProduct()
	{
		final Set<Integer> productIds = getProductIds();
		return productIds != null
				? productIds.contains(0) || productIds.contains(-1) || productIds.contains(ANY)
				: false;
	}

	Set<Integer> getBpartnerIds();

	default boolean isNoBPartners()
	{
		final Set<Integer> bpartnerIds = getBpartnerIds();
		return bpartnerIds == null || bpartnerIds.isEmpty();
	}

	default boolean isAnyBPartner()
	{
		final Set<Integer> bpartnerIds = getBpartnerIds();
		return bpartnerIds != null
				? bpartnerIds.contains(0) || bpartnerIds.contains(-1) || bpartnerIds.contains(ANY)
				: false;
	}

	Set<Integer> getLocatorIds();

	default boolean isNoLocators()
	{
		final Set<Integer> locatorIds = getLocatorIds();
		return locatorIds == null || locatorIds.isEmpty();
	}

	Set<Integer> getBillBPartnerIds();

	default boolean isAnyBillBPartner()
	{
		final Set<Integer> billBPartnerIds = getBillBPartnerIds();
		return billBPartnerIds.isEmpty() || billBPartnerIds.contains(0) || billBPartnerIds.contains(-1) || billBPartnerIds.contains(ANY);
	}

	default boolean isAnyLocator()
	{
		final Set<Integer> locatorIds = getLocatorIds();
		return locatorIds != null
				? locatorIds.contains(0) || locatorIds.contains(-1) || locatorIds.contains(ANY)
				: false;
	}

	Set<ShipmentScheduleAttributeSegment> getAttributes();
}
