package de.metas.ui.web.handlingunits;

import org.adempiere.exceptions.AdempiereException;

import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;

import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.ui.web.view.IViewRowType;
import de.metas.ui.web.view.ViewRowTypeIconNames;

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

public enum HUEditorRowType implements IViewRowType
{
	LU(ViewRowTypeIconNames.ICONNAME_LU, true) //
	, TU(ViewRowTypeIconNames.ICONNAME_TU, true) //
	, VHU(ViewRowTypeIconNames.ICONNAME_CU, true) //
	, HUStorage(ViewRowTypeIconNames.ICONNAME_CU, false) //
	;

	private final String name;
	private final boolean pureHU;

	private HUEditorRowType(final String name, final boolean pureHU)
	{
		this.name = name;
		this.pureHU = pureHU;
	}

	@Override
	@JsonValue
	public String getName()
	{
		return name;
	}

	/** @return true if it's a pure HU (i.e. not {@link #HUStorage}) */
	public boolean isPureHU()
	{
		return pureHU;
	}

	public boolean isCU()
	{
		return this == VHU || this == HUStorage;
	}

	public static final HUEditorRowType ofHU_UnitType(final String huUnitType)
	{
		final HUEditorRowType type = huUnitType2type.get(huUnitType);
		if (type == null)
		{
			throw new IllegalArgumentException("Cannot convert HU_UnitType '" + huUnitType + "' to " + HUEditorRowType.class);
		}
		return type;
	}

	public String toHUUnitTypeOrNull()
	{
		if (this == HUStorage)
		{
			return X_M_HU_PI_Version.HU_UNITTYPE_VirtualPI;
		}
		return huUnitType2type.inverse().get(this);

	}

	public String toHUUnitType()
	{
		final String unitType = toHUUnitTypeOrNull();
		if (unitType == null)
		{
			throw new AdempiereException("Cannot convert " + this + " to HU_UnitType");
		}
		return unitType;
	}

	private static final BiMap<String, HUEditorRowType> huUnitType2type = ImmutableBiMap.<String, HUEditorRowType> builder()
			.put(X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit, LU)
			.put(X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit, TU)
			.put(X_M_HU_PI_Version.HU_UNITTYPE_VirtualPI, VHU)
			.build();
}
