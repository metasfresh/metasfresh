package de.metas.ui.web.handlingunits.util;

import org.adempiere.util.Check;
import org.adempiere.util.Services;

import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.storage.IHUProductStorage;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

public final class HUPackingInfos
{
	private HUPackingInfos()
	{
	}

	public static final IHUPackingInfo of(final I_M_HU_LUTU_Configuration lutuConfig)
	{
		return new LUTUConfigAsPackingInfo(lutuConfig);
	}

	public static final IHUPackingInfo of(final I_M_HU hu)
	{
		Check.assumeNotNull(hu, "Parameter hu is not null");

		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		
		if (handlingUnitsBL.isAggregateHU(hu))
		{
			return new AggregatedTUPackingInfo(hu);
		}

		final String huUnitType = handlingUnitsBL.getHU_UnitType(hu);
		if (X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit.equals(huUnitType))
		{
			return new LUPIPackingInfo(handlingUnitsBL.getPI(hu));
		}
		else if (X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit.equals(huUnitType))
		{
			return new TUPackingInfo(hu);
		}
		else if (X_M_HU_PI_Version.HU_UNITTYPE_VirtualPI.equals(huUnitType))
		{
			return new VHUPackingInfo(hu);
		}

		throw new IllegalArgumentException("HU type not supported: " + huUnitType
				+ "\n HU: " + hu);
	}

	public IHUPackingInfo of(final IHUProductStorage huProductStorage)
	{
		return new VHUPackingInfo(huProductStorage);
	}
}
