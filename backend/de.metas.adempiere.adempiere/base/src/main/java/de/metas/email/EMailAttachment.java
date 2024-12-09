package de.metas.email;

<<<<<<< HEAD
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

=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
<<<<<<< HEAD

import lombok.EqualsAndHashCode;
import lombok.NonNull;
=======
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Util;
import org.springframework.core.io.Resource;

import javax.activation.DataSource;
import javax.activation.URLDataSource;
import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URI;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

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
<<<<<<< HEAD
 * 
=======
 *
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
<<<<<<< HEAD
 * 
=======
 *
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * EMail attachment.
<<<<<<< HEAD
 * 
=======
 *
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 * @author metas-dev <dev@metasfresh.com>
 * @see EMail#getAttachments()
 */
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
<<<<<<< HEAD
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
=======
@Value
public class EMailAttachment implements Serializable
{
	public static EMailAttachment of(@NonNull final File file)
	{
		return of(file.getName(), file);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	public static EMailAttachment of(@NonNull final String filename, @NonNull final File file)
	{
<<<<<<< HEAD
		final byte[] content = Util.readBytes(file);
		final URI uri = null;
		return new EMailAttachment(filename, content, uri);
=======
		return of(filename, Util.readBytes(file));
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	public static EMailAttachment of(@NonNull final String filename, final byte[] content)
	{
<<<<<<< HEAD
		final URI uri = null;
		return new EMailAttachment(filename, content, uri);
=======
		return new EMailAttachment(filename, content, null);
	}

	public static EMailAttachment ofNullable(@NonNull final String filename, @Nullable final byte[] content)
	{
		return content != null && content.length != 0 ? of(filename, content) : null;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	public static EMailAttachment of(@NonNull final URI uri)
	{
<<<<<<< HEAD
		final String filename = null;
		final byte[] content = null;
		return new EMailAttachment(filename, content, uri);
=======
		return new EMailAttachment(null, null, uri);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	public static EMailAttachment of(@NonNull final Resource resource)
	{
		try
		{
			return of(resource.getURI());
		}
		catch (IOException e)
		{
<<<<<<< HEAD
=======
			//noinspection DataFlowIssue
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			throw AdempiereException.wrapIfNeeded(e);
		}
	}

<<<<<<< HEAD
	@JsonProperty("filename")
	private final String filename;
	@JsonProperty("content")
	private final byte[] content;
	@JsonProperty("uri")
	private final URI uri;
=======
	@JsonProperty("filename") String filename;
	@JsonProperty("content") byte[] content;
	@JsonProperty("uri") URI uri;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

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
