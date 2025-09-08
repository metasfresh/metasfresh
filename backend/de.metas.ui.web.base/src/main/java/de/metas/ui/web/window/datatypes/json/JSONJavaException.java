/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.ui.web.window.datatypes.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import de.metas.error.AdIssueId;
import de.metas.ui.web.window.datatypes.Values;
import de.metas.util.GuavaCollectors;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.IssueReportableExceptions;
import org.compiere.util.Trace;

import javax.annotation.Nullable;
import java.util.Map;

@Value
@Builder
@Jacksonized
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class JSONJavaException
{
	String message;
	boolean userFriendlyError;
	@Nullable @JsonInclude(JsonInclude.Include.NON_EMPTY) String stackTrace;
	@Nullable @JsonInclude(JsonInclude.Include.NON_EMPTY) Map<String, Object> attributes;
	@Nullable @JsonInclude(JsonInclude.Include.NON_EMPTY) AdIssueId adIssueId;

	@Nullable
	public static JSONJavaException ofNullable(@Nullable final Exception exception, @NonNull final JSONOptions jsonOpts)
	{
		return exception != null ? of(exception, jsonOpts) : null;
	}

	@NonNull
	public static JSONJavaException of(@NonNull final Exception exception, @NonNull final JSONOptions jsonOpts)
	{
		return builder()
				.message(AdempiereException.extractMessageTrl(exception).translate(jsonOpts.getAdLanguage()))
				.userFriendlyError(AdempiereException.isUserValidationError(exception))
				.stackTrace(Trace.toOneLineStackTraceString(exception))
				.attributes(extractAttributes(exception, jsonOpts))
				.adIssueId(IssueReportableExceptions.getAdIssueIdOrNull(exception))
				.build();
	}

	@Nullable
	private static Map<String, Object> extractAttributes(@NonNull final Exception exception, @NonNull final JSONOptions jsonOpts)
	{
		if (exception instanceof AdempiereException)
		{
			final Map<String, Object> exceptionAttributes = ((AdempiereException)exception).getParameters();
			if (exceptionAttributes == null || exceptionAttributes.isEmpty())
			{
				return null;
			}

			return exceptionAttributes.entrySet()
					.stream()
					.map(entry -> GuavaCollectors.entry(entry.getKey(), Values.valueToJsonObject(entry.getValue(), jsonOpts, String::valueOf)))
					.collect(GuavaCollectors.toImmutableMap());
		}
		else
		{
			return null;
		}

	}
}
