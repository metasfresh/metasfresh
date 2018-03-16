package de.metas.vertical.pharma.msv3.protocol.stockAvailability;

import de.metas.vertical.pharma.vendor.gateway.msv3.schema.VerfuegbarkeitDefektgrund;
import lombok.Getter;

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
	private final VerfuegbarkeitDefektgrund soapCode;

	StockAvailabilitySubstitutionReason(final VerfuegbarkeitDefektgrund soapCode)
	{
		this.soapCode = soapCode;
	}
}
