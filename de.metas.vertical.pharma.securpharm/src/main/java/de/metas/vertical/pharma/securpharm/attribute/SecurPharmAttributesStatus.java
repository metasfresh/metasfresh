/*
 *
 * * #%L
 * * %%
 * * Copyright (C) <current year> metas GmbH
 * * %%
 * * This program is free software: you can redistribute it and/or modify
 * * it under the terms of the GNU General Public License as
 * * published by the Free Software Foundation, either version 2 of the
 * * License, or (at your option) any later version.
 * *
 * * This program is distributed in the hope that it will be useful,
 * * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * * GNU General Public License for more details.
 * *
 * * You should have received a copy of the GNU General Public
 * * License along with this program. If not, see
 * * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * * #L%
 *
 */

package de.metas.vertical.pharma.securpharm.attribute;

import java.util.Arrays;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.util.lang.ReferenceListAwareEnum;
import lombok.Getter;
import lombok.NonNull;

public enum SecurPharmAttributesStatus implements ReferenceListAwareEnum
{
	UNKNOW("N"), //
	OK("Y"), //
	FRAUD("F"), //
	ERROR("E") //
	;

	@Getter
	final String code;

	SecurPharmAttributesStatus(final String code)
	{
		this.code = code;
	}

	@NonNull
	public static SecurPharmAttributesStatus ofNullableCodeOrKnown(@Nullable final String code)
	{
		return code != null ? ofCode(code) : UNKNOW;
	}

	public static SecurPharmAttributesStatus ofCode(@NonNull final String code)
	{
		final SecurPharmAttributesStatus type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + SecurPharmAttributesStatus.class + " found for code: " + code);
		}
		return type;
	}

	private static final ImmutableMap<String, SecurPharmAttributesStatus> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), SecurPharmAttributesStatus::getCode);

	public boolean isUnknown()
	{
		return this == UNKNOW;
	}

	public boolean isOK()
	{
		return this == OK;
	}

	public boolean isFraud()
	{
		return this == FRAUD;
	}

	public boolean isError()
	{
		return this == ERROR;
	}
}
