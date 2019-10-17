package de.metas.process;

import java.util.Arrays;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_AD_Process_Para;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.util.lang.ReferenceListAwareEnum;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public enum BarcodeScannerType implements ReferenceListAwareEnum
{
	QRCode(X_AD_Process_Para.BARCODESCANNERTYPE_QRCode), //
	Datamatrix(X_AD_Process_Para.BARCODESCANNERTYPE_Datamatrix), //
	Barcode(X_AD_Process_Para.BARCODESCANNERTYPE_Barcode) //
	;

	private final String code;

	private BarcodeScannerType(final String code)
	{
		this.code = code;
	}

	@Override
	@JsonValue
	public String getCode()
	{
		return code;
	}

	@JsonCreator
	public static BarcodeScannerType ofCode(@NonNull final String code)
	{
		BarcodeScannerType type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + BarcodeScannerType.class + " found for code: " + code);
		}
		return type;
	}

	private static final ImmutableMap<String, BarcodeScannerType> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), BarcodeScannerType::getCode);
}
