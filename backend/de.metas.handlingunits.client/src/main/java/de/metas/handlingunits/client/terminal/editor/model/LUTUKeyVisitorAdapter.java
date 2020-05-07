package de.metas.handlingunits.client.terminal.editor.model;

/*
 * #%L
 * de.metas.handlingunits.client
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
import org.adempiere.util.Services;
import org.compiere.util.Util;

import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.client.terminal.editor.model.impl.HUKey;
import de.metas.handlingunits.model.I_M_HU;

/**
 * {@link IHUKeyVisitor} implementation which can be used to navigate on:
 * <ul>
 * <li>LU Level; see {@link #visitLU(HUKey)}
 * <li>TU Level; see {@link #visitTU(HUKey, HUKey)}
 * </ul>
 *
 * @author tsa
 *
 */
public abstract class LUTUKeyVisitorAdapter implements IHUKeyVisitor
{
	//
	// Services
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	private HUKey currentLU = null;
	private HUKey currentTU = null;

	/**
	 * Called when we got an LU to visit
	 *
	 * @param luHUKey
	 */
	protected void visitLU(final HUKey luHUKey)
	{
		// nothing; to be implemented in extending classes
	}

	/**
	 * Called when we got an TU to visit
	 *
	 * @param luHUKey LU or null if this TU is not on a LU (e.g. IFCO not on a palete)
	 * @param tuHUKey
	 */
	protected void visitTU(final HUKey luHUKey, final HUKey tuHUKey)
	{
		// nothing; to be implemented in extending classes
	}

	@Override
	public String toString()
	{
		return getClass().getName() + "["
				+ "LU=" + currentLU
				+ ", TU=" + currentTU
				+ "]";
	}

	@Override
	public final VisitResult beforeVisit(final IHUKey key)
	{
		final HUKey huKey = HUKey.castIfPossible(key);
		final I_M_HU hu = huKey == null ? null : huKey.getM_HU();

		if (huKey == null)
		{
			// Case: we got some non-HU key (e.g. Composite, Root etc)
			// => continue navigation
			return VisitResult.CONTINUE;
		}
		else if (handlingUnitsBL.isLoadingUnit(hu))
		{
			setCurrentLU(huKey);
			return VisitResult.CONTINUE;
		}
		else
		{
			setCurrentTU(huKey);
			return VisitResult.SKIP_DOWNSTREAM;
		}
	}

	private final void setCurrentLU(final HUKey huKey)
	{
		Check.assumeNotNull(huKey, "hu not null");

		if (currentLU != null)
		{
			throw new IllegalStateException("Cannot set current LU to '" + huKey + "' because current LU was already set to '" + currentLU + "'");
		}
		if (currentTU != null)
		{
			throw new IllegalStateException("Cannot set current LU when an current TU is currently set");
		}

		currentLU = huKey;
		visitLU(currentLU);
	}

	private final void setCurrentTU(final HUKey huKey)
	{
		Check.assumeNotNull(huKey, "hu not null");

		if (currentTU != null)
		{
			throw new IllegalStateException("Cannot set current TU to '" + huKey + "' because current TU was already set to '" + currentTU + "'");
		}

		// NOTE: currentLU can be:
		// * not null: when we deal with an TU included in a LU
		// * null: when we deal with an TU which is not included in a LU

		currentTU = huKey;
		visitTU(currentLU, currentTU);
	}

	@Override
	public final VisitResult afterVisit(final IHUKey key)
	{
		final HUKey huKey = HUKey.castIfPossible(key);
		if (huKey == null)
		{
			// Case: we got some non-HU key (e.g. Composite, Root etc)
			// => continue navigation
		}
		else if (Util.same(currentLU, huKey))
		{
			currentLU = null;
		}
		else if (Util.same(currentTU, huKey))
		{
			currentTU = null;
		}
		else
		{
			throw new IllegalStateException("On afterVisit we expect the HU to be NULL, current LU or current TU.");
		}

		return VisitResult.CONTINUE;
	}

}
