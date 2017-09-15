package de.metas.handlingunits;

/*
 * #%L
 * de.metas.handlingunits.base
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

import java.util.List;

import org.adempiere.util.ISingletonService;

public interface IHUStatusBL extends ISingletonService
{
	/**
	 * Tell if the storages of HUs with the given {@code huStatus} shall be considered when computing the on hand quantity.<br>
	 * E.g. planned HUs have a storage, but shall not be considered.
	 * 
	 * @param huStatus
	 * @return
	 */
	boolean isQtyOnHand(String huStatus);

	/**
	 * See {@link #isQtyOnHand(String)} to get the idea.
	 * 
	 * @return
	 */
	List<String> getQtyOnHandStatuses();

	/**
	 * Tell if the packing materials of empty HUs with the given status can be moved to the dedicated empties warehouse.
	 * 
	 * @param huStatus
	 * @return
	 */
	boolean isMovePackagingToEmptiesWarehouse(String huStatus);

	void assertAllowedStatusChange(String oldHuStatus, String newHuStatus);

	void assertAllowedLocatorChange(String huStatus);
}
