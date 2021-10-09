/*
 * #%L
 * de-metas-camel-externalsystems-common
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

package de.metas.camel.externalsystems.common;

import de.metas.common.rest_api.common.JsonMetasfreshId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class PInstanceLogger
{
	@NonNull
	ProcessLogger processLogger;
	@Nullable
	JsonMetasfreshId pInstanceId;

	public void logMessage(@NonNull final String logMessage)
	{
		if (pInstanceId == null)
		{
			return;
		}

		processLogger.logMessage(logMessage, pInstanceId.getValue());
	}

	@NonNull
	public static PInstanceLogger of(@NonNull final ProcessLogger processLogger)
	{
		return PInstanceLogger.builder()
				.processLogger(processLogger)
				.build();
	}
}
