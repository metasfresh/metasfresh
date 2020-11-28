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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.adempiere.util.lang.IReference;

import de.metas.handlingunits.exceptions.HUNotAssignableException;
import de.metas.handlingunits.model.I_M_HU;

/**
 * Adapter implementation of {@link IHUAssignmentListener} which does nothing.
 *
 * Extending classes are free to implement only those methods that are necessary.
 *
 * @author tsa
 *
 */
public abstract class HUAssignmentListenerAdapter implements IHUAssignmentListener
{

	/**
	 * Does nothing.
	 */
	@Override
	public void assertAssignable(final I_M_HU hu, final Object model, final String trxName) throws HUNotAssignableException
	{
		// nothing
	}

	/**
	 * Does nothing.
	 */
	@Override
	public void onHUAssigned(final I_M_HU hu, final Object model, final String trxName)
	{
		// nothing
	}

	/**
	 * Does nothing.
	 */
	@Override
	public void onHUUnassigned(final IReference<I_M_HU> huRef, final IReference<Object> modelRef, final String trxName)
	{
		// nothing
	}
}
