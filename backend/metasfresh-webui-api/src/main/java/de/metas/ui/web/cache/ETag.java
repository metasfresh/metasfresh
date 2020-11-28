package de.metas.ui.web.cache;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMap;

import lombok.EqualsAndHashCode;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

@EqualsAndHashCode
public final class ETag
{
	public static final ETag of(final int version)
	{
		return new ETag(version, ImmutableMap.of());
	}

	public static final ETag of(final long version, final Map<String, String> attributes)
	{
		return new ETag(version, ImmutableMap.copyOf(attributes));
	}

	private final long version;
	private final ImmutableMap<String, String> attributes;

	private transient volatile String _etagString = null; // lazy

	private ETag(final long version, final ImmutableMap<String, String> attributes)
	{
		this.version = version;
		this.attributes = attributes;
	}

	@Override
	public String toString()
	{
		return toETagString();
	}

	public String toETagString()
	{
		String etagString = _etagString;
		if (etagString == null)
		{
			_etagString = etagString = buildETagString();
		}
		return etagString;
	}

	private String buildETagString()
	{
		final StringBuilder etagString = new StringBuilder();
		etagString.append("v=").append(version);

		if (!attributes.isEmpty())
		{
			final String attributesStr = attributes.entrySet()
					.stream()
					.sorted(Comparator.comparing(entry -> entry.getKey()))
					.map(entry -> entry.getKey() + "=" + entry.getValue())
					.collect(Collectors.joining("#"));
			etagString.append("#").append(attributesStr);
		}

		return etagString.toString();
	}

	public final ETag overridingAttributes(final Map<String, String> overridingAttributes)
	{
		if (overridingAttributes.isEmpty())
		{
			return this;
		}

		if (attributes.isEmpty())
		{
			return new ETag(version, ImmutableMap.copyOf(overridingAttributes));
		}

		final Map<String, String> newAttributes = new HashMap<>(attributes);
		newAttributes.putAll(overridingAttributes);
		return new ETag(version, ImmutableMap.copyOf(newAttributes));
	}
}
