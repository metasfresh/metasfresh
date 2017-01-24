package de.metas.handlingunits.allocation;

import de.metas.handlingunits.IHUTransaction;

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

import de.metas.handlingunits.model.I_M_HU;

/**
 * Implementors are responsible to allocate <b>or deallocate</b> from/to a given hu, according to a given request.
 * Actually, in both cases, their job is to create and return an {@link IAllocationResult} that contains a bunch of {@link IHUTransaction}s 
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IAllocationStrategy
{
	/**
	 * Execute the <code>request</code> on given HU.
	 *
	 * @param hu
	 * @param request
	 * @return result
	 */
	IAllocationResult execute(I_M_HU hu, IAllocationRequest request);
}
