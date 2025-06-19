package de.metas.rest_api.utils.v2;

import com.google.common.collect.ImmutableMap;
import de.metas.common.rest_api.v2.JsonErrorItem;
import de.metas.common.rest_api.v2.JsonErrorItem.JsonErrorItemBuilder;
import de.metas.i18n.ITranslatableString;
import de.metas.util.GuavaCollectors;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.Null;
import org.compiere.util.Trace;

import javax.annotation.Nullable;
import java.util.Map;

/*
 * #%L
 * de.metas.business.rest-api-impl
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

@UtilityClass
public class JsonErrors
{
	public static JsonErrorItem ofThrowable(
			@NonNull final Throwable throwable,
			@NonNull final String adLanguage)
	{
		return ofThrowable(throwable, adLanguage, null);
	}

	public static JsonErrorItem ofThrowable(
			@NonNull final Throwable throwable,
			@NonNull final String adLanguage,
			@Nullable final ITranslatableString detail)
	{
		final Throwable cause = AdempiereException.extractCause(throwable);

		final JsonErrorItemBuilder builder = JsonErrorItem.builder()
				.message(AdempiereException.extractMessageTrl(cause).translate(adLanguage))
				.errorCode(AdempiereException.extractErrorCodeOrNull(cause))
				.userFriendlyError(AdempiereException.isUserValidationError(cause))
				.stackTrace(Trace.toOneLineStackTraceString(cause.getStackTrace()))
				.parameters(extractParameters(throwable, adLanguage))
				.errorCode(AdempiereException.extractErrorCodeOrNull(throwable))
				.throwable(throwable);
		if (detail != null)
		{
			builder.detail(detail.translate(adLanguage));
		}
		return builder.build();
	}

	private static Map<String, String> extractParameters(@NonNull final Throwable throwable, @NonNull final String adLanguage)
	{
		return convertParametersMapToJson(AdempiereException.extractParameters(throwable), adLanguage);
	}

	@NonNull
	public static Map<String, String> convertParametersMapToJson(@Nullable final Map<String, Object> map, @NonNull final String adLanguage)
	{
		if (map == null || map.isEmpty())
		{
			return ImmutableMap.of();
		}

		return map
				.entrySet()
				.stream()
				.map(e -> GuavaCollectors.entry(e.getKey(), convertParameterToJson(e.getValue(), adLanguage)))
				.collect(GuavaCollectors.toImmutableMap());
	}

	@NonNull
	private static String convertParameterToJson(@Nullable final Object value, @NonNull final String adLanguage)
	{
		if (value == null || Null.isNull(value))
		{
			return "<null>";
		}
		else if (value instanceof ITranslatableString string)
		{
			return string.translate(adLanguage);
		}
		else if (value instanceof RepoIdAware aware)
		{
			return String.valueOf(aware.getRepoId());
		}
		else if (value instanceof ReferenceListAwareEnum enum1)
		{
			return enum1.getCode();
		}
		else
		{
			return value.toString();
		}
	}
}
