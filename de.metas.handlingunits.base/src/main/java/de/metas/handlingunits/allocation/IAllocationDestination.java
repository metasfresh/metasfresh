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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.handlingunits.IHUContext;

/**
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IAllocationDestination
{
	/**
	 * Allocate the quantities from the given {@code request} to "some" storage.
	 * 
	 * @param request
	 * @return a result instance that tells the caller how much was loaded.
	 */
	IAllocationResult load(IAllocationRequest request);

	/**
	 * Called by API when an entire loading round was finished and database transaction is about to COMMIT.
	 *
	 * Implementations of this method can clean up, process or complete data created while loading
	 *
	 * @param huContext
	 */
	void loadComplete(IHUContext huContext);
}
