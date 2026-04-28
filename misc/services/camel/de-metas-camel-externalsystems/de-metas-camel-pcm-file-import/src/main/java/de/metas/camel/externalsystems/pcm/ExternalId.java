/*
 * #%L
 * de-metas-camel-pcm-file-import
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.camel.externalsystems.pcm;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import static de.metas.camel.externalsystems.pcm.PCMConstants.PCM_SYSTEM_NAME;

@Value
@Builder
public class ExternalId
{
	private static final String PREFIX = "ext-" + PCM_SYSTEM_NAME + "-";

	@NonNull
	String id;

	public static ExternalId ofId(@NonNull final String id)
	{
		if (id.startsWith(PREFIX))
		{
			return new ExternalId(id.replace(PREFIX, ""));
		}
		return new ExternalId(id);
	}

	@NonNull
	public String toExternalIdentifierString()
	{
		return PREFIX + id;
	}
}
