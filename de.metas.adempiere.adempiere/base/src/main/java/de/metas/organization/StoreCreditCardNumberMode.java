package de.metas.organization;

import java.util.Arrays;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_AD_OrgInfo;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import lombok.Getter;
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

public enum StoreCreditCardNumberMode
{
	STORE(X_AD_OrgInfo.STORECREDITCARDDATA_Speichern), //
	DONT_STORE(X_AD_OrgInfo.STORECREDITCARDDATA_NichtSpeichern), //
	LAST_4_DIGITS(X_AD_OrgInfo.STORECREDITCARDDATA_Letzte4Stellen) //
	;

	@Getter
	private String code;

	StoreCreditCardNumberMode(@NonNull final String code)
	{
		this.code = code;
	}

	public static StoreCreditCardNumberMode ofCode(@NonNull final String code)
	{
		StoreCreditCardNumberMode type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + StoreCreditCardNumberMode.class + " found for code: " + code);
		}
		return type;
	}

	private static final ImmutableMap<String, StoreCreditCardNumberMode> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), StoreCreditCardNumberMode::getCode);
}
