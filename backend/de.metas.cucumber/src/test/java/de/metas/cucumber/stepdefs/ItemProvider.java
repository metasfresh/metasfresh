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

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.text.MessageFormat;

public interface ItemProvider<T>
{
	@Value
	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	public class ProviderResult<T>
	{
		public static <T> ProviderResult<T> resultWasFound(final @Nullable T result)
		{
			return new ProviderResult<>(true, result, null);
		}

		public static <T> ProviderResult<T> resultWasNotFound(final @Nullable String log)
		{
			return new ProviderResult<>(false, null, log);
		}

		public static <T> ProviderResult<T> resultWasNotFound(final @NonNull String log, final @NonNull Object ...args)
		{
			final String logMessage = MessageFormat.format(log, args);

			return new ProviderResult<>(false, null, logMessage);
		}

		boolean resultFound;
		T result;
		String log;
	}

	ProviderResult<T> execute();
}
