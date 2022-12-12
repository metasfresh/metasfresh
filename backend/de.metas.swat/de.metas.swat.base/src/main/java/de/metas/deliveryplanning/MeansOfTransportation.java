/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.deliveryplanning;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.X_M_MeansOfTransportation;

import javax.annotation.Nullable;

public enum MeansOfTransportation implements ReferenceListAwareEnum
{
	Plane(X_M_MeansOfTransportation.TYPE_MOT_Plane),
	Train(X_M_MeansOfTransportation.TYPE_MOT_Train),
	Truck(X_M_MeansOfTransportation.TYPE_MOT_Truck),
	Vessel(X_M_MeansOfTransportation.TYPE_MOT_Vessel);

	private static final ReferenceListAwareEnums.ValuesIndex<MeansOfTransportation> typesByCode = ReferenceListAwareEnums.index(values());

	@Getter
	private final String code;

	MeansOfTransportation(@NonNull final String code)
	{
		this.code = code;
	}

	@Nullable
	public static MeansOfTransportation ofNullableCode(@Nullable final String code)
	{
		return ofNullableCode(code, null);
	}

	@Nullable
	public static MeansOfTransportation ofNullableCode(@Nullable final String code, @Nullable final MeansOfTransportation fallbackValue)
	{
		return code != null ? ofCode(code) : fallbackValue;
	}

	public static MeansOfTransportation ofCode(@NonNull final String code)
	{
		return typesByCode.ofCode(code);
	}

	@Nullable
	public static String toCodeOrNull(@Nullable final MeansOfTransportation type)
	{
		return type != null ? type.getCode() : null;
	}
}
