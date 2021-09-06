/*
 * #%L
 * de.metas.vertical.healthcare.alberta
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.vertical.healthcare.alberta.bpartner.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.util.Check;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.vertical.healthcare.alberta.model.X_AD_User_Alberta;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Arrays;

@AllArgsConstructor
public enum GenderType implements ReferenceListAwareEnum
{
	UNKNOWN(X_AD_User_Alberta.GENDER_Unbekannt),
	FEMALE(X_AD_User_Alberta.GENDER_Weiblich),
	MALE(X_AD_User_Alberta.GENDER_Maennlich),
	DIVERSE(X_AD_User_Alberta.GENDER_Divers),
	;

	private final String code;

	@Override
	public String getCode()
	{
		return code;
	}

	@Nullable
	public static GenderType ofCodeNullable(@Nullable final String code)
	{
		if (Check.isBlank(code))
		{
			return null;
		}

		return ofCode(code);
	}

	@JsonCreator
	public static GenderType ofCode(@NonNull final String code)
	{
		final GenderType type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + GenderType.class + " found for code: " + code);
		}
		return type;
	}

	private static final ImmutableMap<String, GenderType> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), GenderType::getCode);
}
