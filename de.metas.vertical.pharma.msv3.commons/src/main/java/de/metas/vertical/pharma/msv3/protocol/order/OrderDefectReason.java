package de.metas.vertical.pharma.msv3.protocol.order;

import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableMap;

import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.BestellungDefektgrund;
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
	NO_INFO(BestellungDefektgrund.KEINE_ANGABE), //
	MISSING(BestellungDefektgrund.FEHLT_ZURZEIT), //
	MANUFACTURER_NOT_AVAILABLE(BestellungDefektgrund.HERSTELLER_NICHT_LIEFERBAR), //
	ONLY_DIRECT(BestellungDefektgrund.NUR_DIREKT), //
	NOT_GUIDED(BestellungDefektgrund.NICHT_GEFUEHRT), //
	UNKNOWN_ITEM_NO(BestellungDefektgrund.ARTIKEL_NR_UNBEKANNT), //
	OUT_OF_TRADE(BestellungDefektgrund.AUSSER_HANDEL), //
	NO_REFERENCE(BestellungDefektgrund.KEIN_BEZUG), //
	TRANSPORT_EXCLUSION(BestellungDefektgrund.TRANSPORTAUSSCHLUSS) //
	;

	@Getter
	private final BestellungDefektgrund v2SoapCode;

	OrderDefectReason(final BestellungDefektgrund v2SoapCode)
	{
		this.v2SoapCode = v2SoapCode;
	}
	
	public String value()
	{
		return v2SoapCode.value();
	}

	public static OrderDefectReason fromV2SoapCode(final BestellungDefektgrund v2SoapCode)
	{
		final OrderDefectReason type = v2SoapCode2type.get(v2SoapCode);
		if (type == null)
		{
			throw new NoSuchElementException("No " + OrderDefectReason.class + " found for " + v2SoapCode);
		}
		return type;
	}

	private static final ImmutableMap<BestellungDefektgrund, OrderDefectReason> v2SoapCode2type = Stream.of(values())
			.collect(ImmutableMap.toImmutableMap(OrderDefectReason::getV2SoapCode, Function.identity()));

}
