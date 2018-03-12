package de.metas.handlingunits.report;

import java.util.List;

import org.adempiere.util.Services;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

@ToString(exclude = { "hu", "_includedHUs" })
public class HUToReportWrapper implements HUToReport
{
	public static final HUToReportWrapper of(final I_M_HU hu)
	{
		return new HUToReportWrapper(hu);
	}

	private final I_M_HU hu;
	private final int huId;
	private final int bpartnerId;
	private final String huUnitType;

	private List<HUToReport> _includedHUs; // lazy

	private HUToReportWrapper(@NonNull final I_M_HU hu)
	{
		this.hu = hu;
		this.huId = hu.getM_HU_ID();
		this.bpartnerId = hu.getC_BPartner_ID();
		this.huUnitType = extractHUType(hu);
	}

	private static final String extractHUType(final I_M_HU hu)
	{
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		if (handlingUnitsBL.isLoadingUnit(hu))
		{
			return X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit;
		}
		else if (handlingUnitsBL.isTransportUnitOrAggregate(hu))
		{
			return X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit;
		}
		else if (handlingUnitsBL.isVirtual(hu))
		{
			return X_M_HU_PI_Version.HU_UNITTYPE_VirtualPI;
		}
		else
		{
			throw new HUException("Unknown HU type: " + hu); // shall hot happen
		}
	}

	@Override
	public int getHUId()
	{
		return huId;
	}

	@Override
	public int getBPartnerId()
	{
		return bpartnerId;
	}

	@Override
	public String getHUUnitType()
	{
		return huUnitType;
	}
	
	@Override
	public boolean isTopLevel()
	{
		return Services.get(IHandlingUnitsBL.class).isTopLevel(hu);
	}

	@Override
	public List<HUToReport> getIncludedHUs()
	{
		if (_includedHUs == null)
		{
			final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
			this._includedHUs = handlingUnitsDAO.retrieveIncludedHUs(hu)
					.stream()
					.map(HUToReportWrapper::of)
					.collect(ImmutableList.toImmutableList());
		}
		return _includedHUs;
	}
}
