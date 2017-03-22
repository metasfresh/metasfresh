package de.metas.handlingunits.impl;

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


import org.adempiere.util.Services;

import de.metas.handlingunits.HUAssignmentListenerAdapter;
import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.IHUAssignmentListener;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.exceptions.HUNotAssignableException;
import de.metas.handlingunits.model.I_M_HU;

/**
 * Standard {@link IHUAssignmentListener} with and {@link #assertAssignable(I_M_HU, Object, String)} method that validates common cases.
 *
 * @author tsa
 * @see IHUAssignmentBL#registerHUAssignmentListener(IHUAssignmentListener)
 */
public final class StandardHUAssignmentListener extends HUAssignmentListenerAdapter
{
	public static final StandardHUAssignmentListener instance = new StandardHUAssignmentListener();

	private StandardHUAssignmentListener()
	{
		super();
	}

	/**
	 * Verifies that the given <code>hu</code> to be assigned is a top-level HU.
	 */
	@Override
	public void assertAssignable(final I_M_HU hu, final Object model, final String trxName) throws HUNotAssignableException
	{
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		if (!handlingUnitsBL.isTopLevel(hu))
		{
			throw new HUNotAssignableException("Assigning HUs which are not top-level is not allowed", model, hu);
		}
	}

}
