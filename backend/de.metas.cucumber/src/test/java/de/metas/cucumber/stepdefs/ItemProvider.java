/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.cucumber.stepdefs;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import de.metas.util.Check;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;
import org.opentest4j.MultipleFailuresError;

import javax.annotation.Nullable;
import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

public interface ItemProvider<T>
{
	@Value
	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	public class ProviderResult<T>
	{
		boolean resultFound;
		T result;
		String log;

		public static <T> ProviderResult<T> resultWasFound(final @Nullable T result)
		{
			return new ProviderResult<>(true, result, null);
		}

		public static <T> ProviderResult<T> resultWasNotFound(final @Nullable String log)
		{
			return new ProviderResult<>(false, null, log);
		}

		public static <T> ProviderResult<T> resultWasNotFound(final @NonNull String log, final @NonNull Object... args)
		{
			final String logMessage = MessageFormat.format(log, args);

			return new ProviderResult<>(false, null, logMessage);
		}

		public static <T> ProviderResult<T> resultWasNotFound(@NonNull final Throwable throwable)
		{
			return resultWasNotFound(extractMessage(throwable));
		}

		public static String extractMessage(final Throwable throwable)
		{
			if (throwable instanceof MultipleFailuresError)
			{
				return extractMessage_MultipleFailuresError((MultipleFailuresError)throwable);
			}
			else
			{
				return extractMessage_Generic(throwable);
			}
		}

		private static String extractMessage_MultipleFailuresError(final MultipleFailuresError multipleFailuresError)
		{
			return multipleFailuresError.getFailures().stream().map(ProviderResult::extractMessage).filter(Check::isNotBlank).collect(Collectors.joining(" | "));
		}

		private static String extractMessage_Generic(final Throwable throwable)
		{
			final String message = throwable.getMessage();
			if (message == null)
			{
				return "";
			}

			final List<String> lines = Splitter.on("\n")
					.trimResults()
					.omitEmptyStrings()
					.splitToList(message);

			return Joiner.on(" ").skipNulls().join(lines);
		}

	}

	ProviderResult<T> execute();
}
