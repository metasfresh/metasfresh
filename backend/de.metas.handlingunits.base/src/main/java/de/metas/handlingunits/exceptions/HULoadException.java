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


import org.adempiere.util.Check;

import de.metas.handlingunits.allocation.IAllocationDestination;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.allocation.impl.HULoader;

/**
 * Exception thrown when {@link HULoader} fails to fully load the requested qty to it's {@link IAllocationDestination}.
 *
 * @author tsa
 *
 */
public class HULoadException extends HUException
{
	/**
	 *
	 */
	private static final long serialVersionUID = 6554990044648764732L;

	private final IAllocationRequest request;
	private final IAllocationResult result;

	/**
	 *
	 * @param message
	 * @param request initial request
	 * @param result last load result, which failed
	 */
	public HULoadException(final String message, final IAllocationRequest request, final IAllocationResult result)
	{
		super(message);

		Check.assumeNotNull(request, "request not null");
		this.request = request;

		Check.assumeNotNull(result, "result not null");
		this.result = result;
	}

	public IAllocationRequest getAllocationRequest()
	{
		return request;
	}

	public IAllocationResult getAllocationResult()
	{
		return result;
	}
}
