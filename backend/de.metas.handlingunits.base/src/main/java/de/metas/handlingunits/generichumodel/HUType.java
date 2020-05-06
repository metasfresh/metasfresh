package de.metas.handlingunits.generichumodel;

import static de.metas.util.Check.isEmpty;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.collect.ImmutableMap;

import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

/** Please keep in sync with {@link X_M_HU_PI_Version#HU_UNITTYPE_AD_Reference_ID}. */
public enum HUType implements ReferenceListAwareEnum
{
	TransportUnit(X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit),

	LoadLogistiqueUnit(X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit),

	VirtualPI(X_M_HU_PI_Version.HU_UNITTYPE_VirtualPI);

	@Getter
	private String code;

	private HUType(@NonNull final String code)
	{
		this.code = code;
	}

	public static HUType ofCodeOrNull(@Nullable final String code)
	{
		if (isEmpty(code, true))
		{
			return null;
		}
		return ofCode(code);
	}

	public static HUType ofCode(@NonNull final String code)
	{
		HUType type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + HUType.class + " found for code: " + code);
		}
		return type;
	}

	private static final ImmutableMap<String, HUType> typesByCode = ReferenceListAwareEnums.indexByCode(values());

	public boolean isLU()
	{
		return this == LoadLogistiqueUnit;
	}

	public boolean isTU()
	{
		return this == TransportUnit;
	}

	public boolean isVHU()
	{
		return this == VirtualPI;
	}
}
