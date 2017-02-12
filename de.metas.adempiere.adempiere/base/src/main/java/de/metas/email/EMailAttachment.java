package de.metas.email;

import java.io.File;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.Objects;

import javax.activation.DataSource;
import javax.activation.URLDataSource;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.lang.EqualsBuilder;
import org.compiere.util.Util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

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
@SuppressWarnings("serial")
public final class EMailAttachment implements Serializable
{
	/* package */static EMailAttachment of(final File file)
	{
		final String filename = file.getName();
		final byte[] content = Util.readBytes(file);
		return new EMailAttachment(filename, content);
	}

	/* package */static EMailAttachment of(final String filename, final byte[] content)
	{
		return new EMailAttachment(filename, content);
	}

	/* package */static EMailAttachment of(final URI uri)
	{
		return new EMailAttachment(uri);
	}

	@JsonProperty("filename")
	private final String filename;
	@JsonProperty("content")
	private final byte[] content;
	@JsonProperty("uri")
	private final URI uri;

	@JsonIgnore
	private transient Integer _hashcode;

	private EMailAttachment(final String filename, final byte[] content)
	{
		super();
		Check.assumeNotEmpty(filename, "filename not empty");
		this.filename = filename;
		this.content = content;
		uri = null;
	}

	private EMailAttachment(final URI uri)
	{
		super();
		filename = null;
		content = null;

		Check.assumeNotNull(uri, "uri not null");
		this.uri = uri;
	}

	@JsonCreator
	private EMailAttachment(
			@JsonProperty("filename") final String filename//
			, @JsonProperty("content") final byte[] content //
			, @JsonProperty("uri") final URI uri //
	)
	{
		super();
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

	@Override
	public int hashCode()
	{
		if (_hashcode == null)
		{
			_hashcode = Objects.hash(filename, content, uri);
		}
		return _hashcode;
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		final EMailAttachment other = EqualsBuilder.getOther(this, obj);
		if (other == null)
		{
			return false;
		}

		return new EqualsBuilder()
				.append(filename, other.filename)
				.append(uri, other.uri)
				.append(content, other.content)
				.isEqual();
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
