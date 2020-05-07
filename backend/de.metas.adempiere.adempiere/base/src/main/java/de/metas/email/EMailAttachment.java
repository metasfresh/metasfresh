package de.metas.email;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URI;

import javax.activation.DataSource;
import javax.activation.URLDataSource;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Util;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import lombok.EqualsAndHashCode;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * EMail attachment.
 * 
 * @author metas-dev <dev@metasfresh.com>
 * @see EMail#getAttachments()
 */
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@SuppressWarnings("serial")
@EqualsAndHashCode
public final class EMailAttachment implements Serializable
{
	public static EMailAttachment of(@NonNull final File file)
	{
		final String filename = file.getName();
		final byte[] content = Util.readBytes(file);
		final URI uri = null;
		return new EMailAttachment(filename, content, uri);
	}

	public static EMailAttachment of(@NonNull final String filename, @NonNull final File file)
	{
		final byte[] content = Util.readBytes(file);
		final URI uri = null;
		return new EMailAttachment(filename, content, uri);
	}

	public static EMailAttachment of(@NonNull final String filename, final byte[] content)
	{
		final URI uri = null;
		return new EMailAttachment(filename, content, uri);
	}

	public static EMailAttachment of(@NonNull final URI uri)
	{
		final String filename = null;
		final byte[] content = null;
		return new EMailAttachment(filename, content, uri);
	}

	public static EMailAttachment of(@NonNull final Resource resource)
	{
		try
		{
			return of(resource.getURI());
		}
		catch (IOException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}

	@JsonProperty("filename")
	private final String filename;
	@JsonProperty("content")
	private final byte[] content;
	@JsonProperty("uri")
	private final URI uri;

	@JsonCreator
	private EMailAttachment(
			@JsonProperty("filename") final String filename,
			@JsonProperty("content") final byte[] content,
			@JsonProperty("uri") final URI uri)
	{
		this.filename = filename;
		this.content = content;
		this.uri = uri;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("filename", filename)
				.add("uri", uri)
				.add("content.size", content != null ? content.length : null)
				.toString();
	}

	@JsonIgnore
	public DataSource createDataSource()
	{
		if (uri != null)
		{
			try
			{
				return new URLDataSource(uri.toURL());
			}
			catch (final MalformedURLException ex)
			{
				throw new AdempiereException("@Invalid@ @URL@: " + uri, ex);
			}
		}
		else
		{
			return ByteArrayBackedDataSource.of(filename, content);
		}
	}
}
