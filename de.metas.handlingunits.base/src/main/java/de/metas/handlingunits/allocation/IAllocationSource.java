package de.metas.handlingunits.allocation;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.List;

import org.adempiere.util.lang.IPair;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUTransaction;

/**
 * Implementations of this interface define a source of allocation, so that qtys from other parts of adempiere (e.g. material receipts) can be allocated to handling units.
 *
 * @author tsa
 */
public interface IAllocationSource
{
	/**
	 * Deallocate requested qty from the source.
	 *
	 * @param request
	 * @return
	 */
	IAllocationResult unload(IAllocationRequest request);

	/**
	 * Fully deallocate (all Qtys) from this source (i.e. empty it).
	 *
	 * @param huContext HU Context
	 * @return list of {@link IAllocationRequest}, {@link IAllocationResult} pairs
	 */
	List<IPair<IAllocationRequest, IAllocationResult>> unloadAll(IHUContext huContext);

	/**
	 * Called by API when an entire unloading round was finished and database transaction is about to COMMIT.<br>
	 * Note that this also means that the full loading was done. 
	 *
	 * Implementations of this method can clean up, process or complete data created while loading
	 *
	 * @param huContext
	 */
	void unloadComplete(IHUContext huContext);
}
