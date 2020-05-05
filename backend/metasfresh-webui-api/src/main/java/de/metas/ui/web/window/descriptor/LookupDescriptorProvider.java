package de.metas.ui.web.window.descriptor;

import java.util.Optional;

import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor.LookupSource;

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

/**
 * Provides {@link LookupDescriptor} for a given {@link LookupScope}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@FunctionalInterface
public interface LookupDescriptorProvider
{
	public static enum LookupScope
	{
		DocumentField, DocumentFilter
	}

	/**
	 * @return lookup descriptor or null
	 */
	Optional<LookupDescriptor> provideForScope(LookupScope scope);

	default Optional<LookupDescriptor> provide()
	{
		return provideForScope(LookupScope.DocumentField);
	}

	default Optional<LookupDescriptor> provideForFilter()
	{
		return provideForScope(LookupScope.DocumentFilter);
	}

	default boolean isNumericKey()
	{
		return provide()
				.map(LookupDescriptor::isNumericKey)
				.orElse(false);
	}

	default Optional<String> getTableName()
	{
		return provide()
				.flatMap(LookupDescriptor::getTableName);
	}

	default Optional<LookupSource> getLookupSourceType()
	{
		return provide()
				.map(LookupDescriptor::getLookupSourceType);
	}

}
