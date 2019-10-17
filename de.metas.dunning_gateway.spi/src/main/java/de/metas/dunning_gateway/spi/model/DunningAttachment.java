package de.metas.dunning_gateway.spi.model;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

/*
 * #%L
 * metasfresh-invoice_gateway.spi
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Value
@ToString(exclude = "data")
public class DunningAttachment
{
	String fileName;
	String mimeType;
	ImmutableMap<String, String> tags;
	
	@Getter(AccessLevel.PRIVATE)
	byte[] data;

	@Builder
	private DunningAttachment(
			@NonNull final String fileName,
			@NonNull final String mimeType,
			@NonNull final byte[] data,
			@NonNull final Map<String, String> tags)
	{
		this.fileName = fileName;
		this.mimeType = mimeType;
		this.data = data;
		this.tags = ImmutableMap.copyOf(tags);
	}

	public InputStream getDataAsInputStream()
	{
		return new ByteArrayInputStream(getData());
	}
}
