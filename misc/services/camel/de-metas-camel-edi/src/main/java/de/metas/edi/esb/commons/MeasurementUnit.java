/*
 * #%L
 * de-metas-edi-esb-camel
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

package de.metas.edi.esb.commons;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import lombok.Getter;

public enum MeasurementUnit
{
	KILO("KGM", false), //
	LITR("LTR", false), //
	METR("MTR", false), //
	SQMT("MTK", false), //
	CBMT("MTQ", false), //
	PIEC("PCE", false), //
	PACK("TU", true), //
	COLI("COLI", true), //
	DISP("DISP", true), //
	CART("KRT", true); //

	private final String metasfreshUOM;

	@Getter
	private final boolean tuUOM;

	MeasurementUnit(
			String metasfreshUOM,
			boolean tuUOM)
	{
		this.metasfreshUOM = metasfreshUOM;
		this.tuUOM = tuUOM;
	}

	private static final Map<String, MeasurementUnit> metasfresh2edi = ImmutableMap.<String, MeasurementUnit> builder()
			.put(KILO.metasfreshUOM, KILO)
			.put(LITR.metasfreshUOM, LITR)
			.put(METR.metasfreshUOM, METR)
			.put(SQMT.metasfreshUOM, SQMT)
			.put(CBMT.metasfreshUOM, CBMT)
			.put(PIEC.metasfreshUOM, PIEC)
			.put(PACK.metasfreshUOM, PACK)
			.put(CART.metasfreshUOM, CART)
			.put(COLI.metasfreshUOM, COLI)
			.build();

	private static final Map<MeasurementUnit, String> edi2metasfresh = ImmutableMap.<MeasurementUnit, String> builder()
			.put(KILO, KILO.metasfreshUOM)
			.put(LITR, LITR.metasfreshUOM)
			.put(METR, METR.metasfreshUOM)
			.put(SQMT, SQMT.metasfreshUOM)
			.put(CBMT, CBMT.metasfreshUOM)
			.put(PIEC, PIEC.metasfreshUOM)
			.put(PACK, PACK.metasfreshUOM)
			.put(COLI, COLI.metasfreshUOM)
			.put(DISP, PIEC.metasfreshUOM) // (!)
			.put(CART, CART.metasfreshUOM)
			.build();

	public static MeasurementUnit fromMetasfreshUOM(String metasfreshUOM)
	{
		return metasfresh2edi.get(metasfreshUOM);
	}

	public static String fromEdiUOM(MeasurementUnit metasfreshUOM)
	{
		return edi2metasfresh.get(metasfreshUOM);
	}
}
