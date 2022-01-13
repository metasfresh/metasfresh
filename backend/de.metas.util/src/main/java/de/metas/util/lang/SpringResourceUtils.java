/*
 * #%L
 * de.metas.util
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

package de.metas.util.lang;

import com.google.common.io.ByteStreams;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

import java.io.IOException;

@UtilityClass
public class SpringResourceUtils
{
	public byte[] toByteArray(@NonNull final Resource resource)
	{
		final byte[] byteArray;
		if (resource instanceof ByteArrayResource)
		{
			return ((ByteArrayResource)resource).getByteArray();
		}

		try
		{
			return ByteStreams.toByteArray(resource.getInputStream());
		}
		catch (final IOException e)
		{
			throw new RuntimeException("Unable to access inputstram of resource " + resource, e);
		}
	}

	// we have this trivial method just for the sake of completeness, as we also have toByteArray
	public Resource fromByteArray(@NonNull final byte[] byteArray)
	{
		return new ByteArrayResource(byteArray);
	}
}
