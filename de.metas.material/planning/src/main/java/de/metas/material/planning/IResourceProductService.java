package de.metas.material.planning;

import java.sql.Timestamp;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_S_Resource;
import org.compiere.model.I_S_ResourceType;

/*
 * #%L
 * metasfresh-material-planning
 * %%
 * Copyright (C) 2017 metas GmbH
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

public interface IResourceProductService extends ISingletonService
{

	/**
	 * Set resource-ID, name, description and value of the given {@code product} to values from the given {@code resource}.
	 * <b>
	 * <b>Important:</b> {@link I_M_Product#setValue(String)} is set to the resource's value with a prepended {@code "PR"}.<br>
	 * That "PR" is a QnD solution to the possible problem that if the production resource's value is set to its ID (like '1000000") there is probably already a product with the same value<br>
	 * Issue https://github.com/metasfresh/metasfresh/issues/1580.
	 *
	 * @param resource
	 * @param product
	 * @return true if changed
	 */
	boolean setResourceToProduct(I_S_Resource parent, I_M_Product product);

	boolean setResourceTypeToProduct(I_S_ResourceType parent, I_M_Product product);

	I_M_Product retrieveProductForResource(I_S_Resource resource);

	/**
	 * Get how many hours/day a is available.
	 * Minutes, secords and millis are discarded.
	 *
	 * @return available hours
	 */
	int getTimeSlotHoursForResourceType(I_S_ResourceType resourceType);

	/**
	 * Get available days / week.
	 *
	 * @return available days / week
	 */
	int getAvailableDaysWeekForResourceType(I_S_ResourceType resourceType);

	Timestamp getDayStartForResourceType(I_S_ResourceType resourceType, Timestamp date);

	Timestamp getDayEndForResourceType(I_S_ResourceType resourceType, Timestamp date);

	/**
	 * @return true if a resource of this type is generally available
	 *         (i.e. active, at least 1 day available, at least 1 hour available)
	 */
	boolean isAvailableForResourceType(I_S_ResourceType resourceType);

	boolean isDayAvailableForResourceType(I_S_ResourceType resourceType, Timestamp dateTime);

}
