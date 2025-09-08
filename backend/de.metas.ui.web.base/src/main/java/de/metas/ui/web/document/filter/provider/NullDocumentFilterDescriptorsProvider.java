package de.metas.ui.web.document.filter.provider;

import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import java.util.Collection;
import java.util.Map;

/*
 * #%L
 * metasfresh-webui-api
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

@Immutable
public final class NullDocumentFilterDescriptorsProvider implements DocumentFilterDescriptorsProvider
{
	public static final NullDocumentFilterDescriptorsProvider instance = new NullDocumentFilterDescriptorsProvider();

	public static boolean isNull(@Nullable final DocumentFilterDescriptorsProvider provider)
	{
		return provider == null || provider == instance;
	}

	public static boolean isNotNull(@Nullable final DocumentFilterDescriptorsProvider provider)
	{
		return !isNull(provider);
	}

	private NullDocumentFilterDescriptorsProvider()
	{
	}

	@Override
	public Collection<DocumentFilterDescriptor> getAll()
	{
		return ImmutableList.of();
	}

	@Override
	public DocumentFilterDescriptor getByFilterIdOrNull(final String filterId)
	{
		return null;
	}

	/**
	 * NOTE: required for snapshot testing
	 */
	@JsonValue
	private Map<String, String> toJson()
	{
		return ImmutableMap.of();
	}
}
