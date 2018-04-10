package de.metas.handlingunits.allocation.impl;

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Locator;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Version;

/**
 * Helper class used for grouping given TUs and build LUs from them.
 *
 * @author tsa
 *
 */
public class LULoader
{
	private final IHUContext huContext;

	private final List<LULoaderInstance> luInstances = new ArrayList<>();

	public LULoader(final IHUContext huContext)
	{
		Check.assumeNotNull(huContext, "huContext not null");
		Services.get(ITrxManager.class).assertTrxNotNull(huContext);

		this.huContext = huContext;
	}

	/**
	 * Add given Transport Unit (TU) to a Loading Unit (LU).
	 *
	 * If there already exists an LU which accepts the given TU then the TU will be added to that LU. If not, a new LU will be created.
	 *
	 * @param tuHU may not be {@code null}
	 * @throws AdempiereException if TU could not be added
	 */
	public void addTU(final I_M_HU tuHU)
	{
		Check.assumeNotNull(tuHU, "tuHU not null");

		//
		// Iterate current LUs and try to add our TU to one of them
		for (final LULoaderInstance luInstance : luInstances)
		{
			if (luInstance.addTU(tuHU))
			{
				return;
			}
		}

		//
		// None of current LUs accepted our TU, e.g. because there is no more free capacity in their wrapped LUs, or maybe because the TU's C_BPartner didn't match.
		// => create another LU
		final LULoaderInstance luInstance = createLULoaderInstanceForTU(tuHU);
		if (!luInstance.addTU(tuHU))
		{
			// shall not happen
			throw new AdempiereException("Internal error: LU is not accepting our TU even if it was created for it")
					.appendParametersToMessage()
					.setParameter("LU", luInstance)
					.setParameter("TU", tuHU);
		}
	}

	/**
	 * Create a new LU instance which is able to accept our TU.
	 *
	 * This instance will be automatically enqueued to our current LU instances.
	 *
	 * @param tuHU
	 * @return LU instance; never return null
	 */
	private LULoaderInstance createLULoaderInstanceForTU(final I_M_HU tuHU)
	{
		final I_C_BPartner bpartner = tuHU.getC_BPartner();
		final int bpartnerLocationId = tuHU.getC_BPartner_Location_ID();
		final I_M_Locator locator = tuHU.getM_Locator();
		final String huStatus = tuHU.getHUStatus();
		final I_M_HU_PI_Version tuPIVersion = Services.get(IHandlingUnitsBL.class).getPIVersion(tuHU);
		final LULoaderInstance luInstance = new LULoaderInstance(huContext, bpartner, bpartnerLocationId, locator, huStatus, tuPIVersion);

		luInstances.add(luInstance);
		return luInstance;
	}

	/**
	 * Gets created Loading Units (LUs)
	 *
	 * @return created loading units
	 */
	public List<I_M_HU> getLU_HUs()
	{
		if (luInstances.isEmpty())
		{
			return Collections.emptyList();
		}

		final List<I_M_HU> luHUs = new ArrayList<>(luInstances.size());
		for (final LULoaderInstance luInstance : luInstances)
		{
			final I_M_HU luHU = luInstance.getLU_HU();
			luHUs.add(luHU);
		}

		return luHUs;
	}
}
