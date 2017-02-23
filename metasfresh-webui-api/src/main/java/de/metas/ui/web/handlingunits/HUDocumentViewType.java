package de.metas.ui.web.handlingunits;

import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.ui.web.view.IDocumentViewType;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


public enum HUDocumentViewType implements IDocumentViewType
{
	LU("LU", true) //
	, TU("TU", true) //
	, VHU("CU", true)
	, HUStorage("CU", false) //
	;

	private final String name;
	private final boolean pureHU;

	private HUDocumentViewType(final String name, final boolean pureHU)
	{
		this.name = name;
		this.pureHU = pureHU;
	}

	@Override
	public String getName()
	{
		return name;
	}

	public boolean isPureHU()
	{
		return pureHU;
	}

	public static final HUDocumentViewType ofHU_UnitType(final String huUnitType)
	{
		if (X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit.equals(huUnitType))
		{
			return LU;
		}
		else if (X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit.equals(huUnitType))
		{
			return TU;
		}
		else if (X_M_HU_PI_Version.HU_UNITTYPE_VirtualPI.equals(huUnitType))
		{
			return VHU;
		}
		else
		{
			throw new IllegalArgumentException("Cannot convert HU_UnitType '" + huUnitType + "' to " + HUDocumentViewType.class);
		}
	}
}