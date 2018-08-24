package de.metas.vertical.pharma.msv3.protocol.stockAvailability;

import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableMap;

import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.VerfuegbarkeitDefektgrund;
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

public enum StockAvailabilitySubstitutionReason
{
	NO_INFO(VerfuegbarkeitDefektgrund.KEINE_ANGABE), //
	MISSING(VerfuegbarkeitDefektgrund.FEHLT_ZURZEIT), //
	MANUFACTURER_NOT_AVAILABLE(VerfuegbarkeitDefektgrund.HERSTELLER_NICHT_LIEFERBAR), //
	ONLY_DIRECT(VerfuegbarkeitDefektgrund.NUR_DIREKT), //
	NOT_GUIDED(VerfuegbarkeitDefektgrund.NICHT_GEFUEHRT), //
	UNKNOWN_ITEM_NO(VerfuegbarkeitDefektgrund.ARTIKEL_NR_UNBEKANNT), //
	OUT_OF_TRADE(VerfuegbarkeitDefektgrund.AUSSER_HANDEL), //
	NO_REFERENCE(VerfuegbarkeitDefektgrund.KEIN_BEZUG), //
	PART_DEFECT(VerfuegbarkeitDefektgrund.TEILDEFEKT), //
	TRANSPORT_EXCLUSION(VerfuegbarkeitDefektgrund.TRANSPORTAUSSCHLUSS), //
	;

	@Getter
	private final VerfuegbarkeitDefektgrund v2SoapCode;

	StockAvailabilitySubstitutionReason(final VerfuegbarkeitDefektgrund v2SoapCode)
	{
		this.v2SoapCode = v2SoapCode;
	}

	public String value()
	{
		return v2SoapCode.value();
	}

	public static StockAvailabilitySubstitutionReason fromV2SoapCode(@NonNull final VerfuegbarkeitDefektgrund v2SoapCode)
	{
		final StockAvailabilitySubstitutionReason type = typesByValue.get(v2SoapCode.value());
		if (type == null)
		{
			throw new NoSuchElementException("No " + StockAvailabilitySubstitutionReason.class + " found for " + v2SoapCode);
		}
		return type;
	}

	private static final ImmutableMap<String, StockAvailabilitySubstitutionReason> typesByValue = Stream.of(values())
			.collect(ImmutableMap.toImmutableMap(StockAvailabilitySubstitutionReason::value, Function.identity()));
}
