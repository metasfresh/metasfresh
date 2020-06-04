package de.metas.uom;

import javax.annotation.Nullable;

import org.compiere.model.X_C_UOM;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import de.metas.util.lang.ReferenceListAwareEnums.ValuesIndex;
import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2020 metas GmbH
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

public enum UOMType implements ReferenceListAwareEnum
{
	Angle(X_C_UOM.UOMTYPE_Angle), //
	Area(X_C_UOM.UOMTYPE_Area), //
	DataStorage(X_C_UOM.UOMTYPE_DataStorage), //
	Density(X_C_UOM.UOMTYPE_Density), //
	Energy(X_C_UOM.UOMTYPE_Energy), //
	Force(X_C_UOM.UOMTYPE_Force), //
	KitchenMeasures(X_C_UOM.UOMTYPE_KitchenMeasures), //
	Length(X_C_UOM.UOMTYPE_Length), //
	Power(X_C_UOM.UOMTYPE_Power), //
	Pressure(X_C_UOM.UOMTYPE_Pressure), //
	Temperature(X_C_UOM.UOMTYPE_Temperature), //
	Time(X_C_UOM.UOMTYPE_Time), //
	Torque(X_C_UOM.UOMTYPE_Torque), //
	Velocity(X_C_UOM.UOMTYPE_Velocity), //
	VolumeLiquid(X_C_UOM.UOMTYPE_VolumeLiquid), //
	VolumeDry(X_C_UOM.UOMTYPE_VolumeDry), //
	Weight(X_C_UOM.UOMTYPE_Weigth), //
	Currency(X_C_UOM.UOMTYPE_Currency), //
	DataSpeed(X_C_UOM.UOMTYPE_DataSpeed), //
	Frequency(X_C_UOM.UOMTYPE_Frequency), //
	Other(X_C_UOM.UOMTYPE_Other) //
	;

	public static UOMType ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	public static UOMType ofNullableCodeOrOther(@Nullable final String code)
	{
		final UOMType type = index.ofNullableCode(code);
		return type != null ? type : UOMType.Other;
	}

	private static final ValuesIndex<UOMType> index = ReferenceListAwareEnums.index(values());

	@Getter
	private final String code;

	UOMType(@NonNull final String code)
	{
		this.code = code;
	}

	public boolean isWeight()
	{
		return this == Weight;
	}
}
