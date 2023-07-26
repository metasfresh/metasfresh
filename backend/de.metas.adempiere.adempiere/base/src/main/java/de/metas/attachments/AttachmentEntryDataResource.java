package de.metas.attachments;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;

import javax.annotation.Nullable;

import org.springframework.core.io.AbstractResource;

import com.google.common.base.MoreObjects;

import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public final class AttachmentEntryDataResource extends AbstractResource
{
	private final byte[] source;
	private final String filename;
	private final String description;

	@Builder
	private AttachmentEntryDataResource(
			@Nullable final byte[] source,
			@NonNull final String filename,
			@Nullable final String description)
	{
		this.source = source != null ? source : new byte[] {};
		this.filename = filename;
		this.description = description;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("filename", filename)
				.add("description", description)
				.add("contentLength", source.length)
				.toString();
	}

	@Override
	public int hashCode()
	{
		return 1;
	}

	@Override
	public boolean equals(final Object other)
	{
		if (other instanceof AttachmentEntryDataResource)
		{
			return Arrays.equals(source, ((AttachmentEntryDataResource)other).source);
		}
		else
		{
			return false;
		}
	}

	@Override
	public String getFilename()
	{
		return filename;
	}

	@Override
	public String getDescription()
	{
		return description;
	}

	@Override
	public long contentLength()
	{
		return source.length;
	}

	@Override
	public InputStream getInputStream()
	{
		return new ByteArrayInputStream(source);
	}

}
