package de.metas.handlingunits.exceptions;

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


import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.storage.IHUItemStorage;

/**
 * This exception is thrown when we are dealing with infinite quantities. E.G. when a complete allocation is needed on a (virtual) PI with infinite capacity.
 *
 * @author RC
 *
 */
public class HUInfiniteQtyAllocationException extends HUException
{
	/**
	 *
	 */
	private static final long serialVersionUID = 3930115720300580304L;

	public HUInfiniteQtyAllocationException(final IAllocationRequest request, final IHUItemStorage storage)
	{
		super(buildMsg(request, storage));
	}

	private static String buildMsg(final IAllocationRequest request, final IHUItemStorage storage)
	{
		return "Allocating infinite Qty to a storage with a virtual HU item is not allowed."
				+ "\nRequest: " + request
				+ "\nStorage: " + storage;
	}
}
