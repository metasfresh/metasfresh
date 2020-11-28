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
 * The listener interface for intercepting HU assignments/unassignments to/from a document line.<br>
 * It's recommended to use {@link HUAssignmentListenerAdapter} to implement a listener.<br>
 * Use {@link IHUAssignmentBL#registerHUAssignmentListener(IHUAssignmentListener)} to register it.
 *
 * @author tsa
 *
 */
public interface IHUAssignmentListener
{
	/**
	 * Called by API before assigning an HU to a document line (i.e. model).
	 *
	 * Checks if given <code>hu</code> is assignable to <code>model</code>. In case is not assignable, this method throws {@link HUNotAssignableException}.
	 *
	 * @param hu
	 * @param model
	 * @param trxName
	 * @throws HUNotAssignableException if HU is not assignable
	 */
	void assertAssignable(final I_M_HU hu, final Object model, final String trxName) throws HUNotAssignableException;

	/**
	 * Called by API after an HU was assigned to a document line (i.e. model)
	 *
	 * @param hu
	 * @param model
	 * @param trxName
	 */
	void onHUAssigned(final I_M_HU hu, final Object model, final String trxName);

	/**
	 * Called by API after an HU was un-assigned from a document line (i.e. model)
	 *
	 * @param hu
	 * @param modelRef
	 * @param trxName
	 */
	void onHUUnassigned(final IReference<I_M_HU> huRef, final IReference<Object> modelRef, final String trxName);
}
