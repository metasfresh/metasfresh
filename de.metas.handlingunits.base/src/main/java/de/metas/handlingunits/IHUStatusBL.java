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

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.ISingletonService;

import de.metas.handlingunits.model.I_M_HU;

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

	/**
	 * Assert that a change of {@link I_M_HU#COLUMN_HUStatus} from the given {@code oldHuStatus} to the given {@code newHuStatus} is allowd.
	 * 
	 * @param oldHuStatus
	 * @param newHuStatus
	 * @throw {@link AdempiereException} if the transition is not allowed.
	 */
	void assertStatusChangeIsAllowed(String oldHuStatus, String newHuStatus);

	/**
	 * Assert that {@link I_M_HU#COLUMN_M_Locator_ID} may be updated in HUs that have the given {@code huStatus}.
	 * 
	 * @param huStatus
	 * @throws AdempiereException if the locatorId may not be updated.
	 */
	void assertLocatorChangeIsAllowed(String huStatus);
}
