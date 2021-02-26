/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.postgrest.client;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.util.lang.ReferenceListAwareEnum;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_AD_Process;

import java.util.Arrays;

public enum PostgRESTResponseFormat implements ReferenceListAwareEnum
{
	CSV(X_AD_Process.POSTGRESTRESPONSEFORMAT_Csv, "text/csv"),
	JSON(X_AD_Process.POSTGRESTRESPONSEFORMAT_Json, "application/json");

	@Getter
	private final String code;

	@Getter
	private final String contentType;

	PostgRESTResponseFormat(
			@NonNull final String code,
			@NonNull final String contentType)
	{
		this.code = code;
		this.contentType = contentType;
	}

	public static PostgRESTResponseFormat ofCode(@NonNull final String code)
	{
		final PostgRESTResponseFormat type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + PostgRESTResponseFormat.class + " found for code: " + code);
		}
		return type;
	}

	private static final ImmutableMap<String, PostgRESTResponseFormat> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), PostgRESTResponseFormat::getCode);
}
