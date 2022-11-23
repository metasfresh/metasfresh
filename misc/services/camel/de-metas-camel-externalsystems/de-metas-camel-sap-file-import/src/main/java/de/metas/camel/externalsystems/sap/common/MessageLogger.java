/*
 * #%L
 * de-metas-camel-sap-file-import
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

package de.metas.camel.externalsystems.sap.common;

import de.metas.camel.externalsystems.common.ProcessLogger;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Builder
@Value
public class MessageLogger
{
	@NonNull
	ProcessLogger processLogger;

	@Nullable
	JsonMetasfreshId pInstanceId;

	@NonNull
	public static MessageLogger of(@NonNull final ProcessLogger processLogger, @Nullable final JsonMetasfreshId pInstanceId)
	{
		return MessageLogger.builder()
				.pInstanceId(pInstanceId)
				.processLogger(processLogger)
				.build();
	}

	public void logErrorMessage(@NonNull final String message)
	{
		processLogger.logMessage(message, JsonMetasfreshId.toValue(pInstanceId));
	}
}