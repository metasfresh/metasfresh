package de.metas.vertical.pharma.msv3.protocol.order;

import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableMap;

import lombok.Getter;

/*
 * #%L
 * metasfresh-pharma.msv3.commons
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

public enum OrderDefectReason
{
	NO_INFO(
			de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.BestellungDefektgrund.KEINE_ANGABE,
			de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.BestellungDefektgrund.KEINE_ANGABE),
	//
	MISSING(
			de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.BestellungDefektgrund.FEHLT_ZURZEIT,
			de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.BestellungDefektgrund.FEHLT_ZURZEIT),
	//
	MANUFACTURER_NOT_AVAILABLE(
			null, // de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.BestellungDefektgrund.HERSTELLER_NICHT_LIEFERBAR,
			de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.BestellungDefektgrund.HERSTELLER_NICHT_LIEFERBAR),
	//
	ONLY_DIRECT(
			de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.BestellungDefektgrund.NUR_DIREKT,
			de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.BestellungDefektgrund.NUR_DIREKT),
	//
	NOT_GUIDED(
			de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.BestellungDefektgrund.NICHT_GEFUEHRT,
			de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.BestellungDefektgrund.NICHT_GEFUEHRT),
	//
	UNKNOWN_ITEM_NO(
			de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.BestellungDefektgrund.ARTIKEL_NR_UNBEKANNT,
			de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.BestellungDefektgrund.ARTIKEL_NR_UNBEKANNT),
	//
	OUT_OF_TRADE(
			de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.BestellungDefektgrund.AUSSER_HANDEL,
			de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.BestellungDefektgrund.AUSSER_HANDEL),
	//
	NO_REFERENCE(
			de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.BestellungDefektgrund.KEIN_BEZUG,
			de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.BestellungDefektgrund.KEIN_BEZUG),
	//
	TRANSPORT_EXCLUSION(
			de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.BestellungDefektgrund.TRANSPORTAUSSCHLUSS,
			de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.BestellungDefektgrund.TRANSPORTAUSSCHLUSS)
	//
	;

	@Getter
	private final de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.BestellungDefektgrund v1SoapCode;
	@Getter
	private final de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.BestellungDefektgrund v2SoapCode;

	OrderDefectReason(
			final de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.BestellungDefektgrund v1SoapCode,
			final de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.BestellungDefektgrund v2SoapCode)
	{
		this.v1SoapCode = v1SoapCode;
		this.v2SoapCode = v2SoapCode;
	}

	public String value()
	{
		return v2SoapCode.value();
	}

	public static OrderDefectReason fromV1SoapCode(final de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.BestellungDefektgrund soapCode)
	{
		final OrderDefectReason type = typesByValue.get(soapCode.value());
		if (type == null)
		{
			throw new NoSuchElementException("No " + OrderDefectReason.class + " found for " + soapCode);
		}
		return type;
	}

	public static OrderDefectReason fromV2SoapCode(final de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.BestellungDefektgrund soapCode)
	{
		final OrderDefectReason type = typesByValue.get(soapCode.value());
		if (type == null)
		{
			throw new NoSuchElementException("No " + OrderDefectReason.class + " found for " + soapCode);
		}
		return type;
	}

	private static final ImmutableMap<String, OrderDefectReason> typesByValue = Stream.of(values())
			.collect(ImmutableMap.toImmutableMap(OrderDefectReason::value, Function.identity()));

}
