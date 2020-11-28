package de.metas.handlingunits.impl;

import de.metas.handlingunits.HUAssignmentListenerAdapter;
import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.IHUAssignmentListener;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.exceptions.HUNotAssignableException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.util.Services;

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
