/*
 * #%L
 * de-metas-camel-metasfresh
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

package de.metas.camel.externalsystems.metasfresh.restapi;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.util.UUID;

@UtilityClass
public class FilenameUtil
{
	@NonNull
	public static String computeFileName(@NonNull final String externalSystemConfigValue)
	{
		return externalSystemConfigValue + "_" + Instant.now().toEpochMilli() + "_" + UUID.randomUUID();
	}

	@NonNull
	public static String getExternalSystemConfigValue(@NonNull final String filename)
	{
		return filename.substring(0, filename.indexOf('_'));
	}
}
