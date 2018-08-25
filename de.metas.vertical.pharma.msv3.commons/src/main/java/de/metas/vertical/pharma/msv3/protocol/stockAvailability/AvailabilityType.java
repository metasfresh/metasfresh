package de.metas.vertical.pharma.msv3.protocol.stockAvailability;

import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableMap;

import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-pharma.msv3.server
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

public enum AvailabilityType
{
	SPECIFIC(
			de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.VerfuegbarkeitTyp.SPEZIFISCH,
			de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.VerfuegbarkeitTyp.SPEZIFISCH),
	//
	NON_SPECIFIC(
			de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.VerfuegbarkeitTyp.UNSPEZIFISCH,
			de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.VerfuegbarkeitTyp.UNSPEZIFISCH),
	//
	;

	@Getter
	private de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.VerfuegbarkeitTyp v1SoapCode;

	@Getter
	private de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.VerfuegbarkeitTyp v2SoapCode;

	private AvailabilityType(
			final de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.VerfuegbarkeitTyp v1SoapCode,
			final de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.VerfuegbarkeitTyp v2SoapCode)
	{
		this.v1SoapCode = v1SoapCode;
		this.v2SoapCode = v2SoapCode;
	}

	public String value()
	{
		return v2SoapCode.value();
	}

	public static AvailabilityType fromV1SoapCode(@NonNull final de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.VerfuegbarkeitTyp v1SoapCode)
	{
		final AvailabilityType type = typesByValue.get(v1SoapCode.value());
		if (type == null)
		{
			throw new NoSuchElementException("No " + AvailabilityType.class + " found for " + v1SoapCode);
		}
		return type;
	}

	public static AvailabilityType fromV2SoapCode(@NonNull final de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.VerfuegbarkeitTyp v2SoapCode)
	{
		final AvailabilityType type = typesByValue.get(v2SoapCode.value());
		if (type == null)
		{
			throw new NoSuchElementException("No " + AvailabilityType.class + " found for " + v2SoapCode);
		}
		return type;
	}

	private static final ImmutableMap<String, AvailabilityType> typesByValue = Stream.of(values())
			.collect(ImmutableMap.toImmutableMap(AvailabilityType::value, Function.identity()));
}
