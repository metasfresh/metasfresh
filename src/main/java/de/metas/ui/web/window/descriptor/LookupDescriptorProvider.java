package de.metas.ui.web.window.descriptor;

import java.util.Optional;
import java.util.function.Function;

import de.metas.util.Functions;
import lombok.NonNull;

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
	/** @return provider which returns given {@link LookupDescriptor} for any scope */
	static LookupDescriptorProvider singleton(@NonNull final LookupDescriptor lookupDescriptor)
	{
		return (scope) -> lookupDescriptor;
	}

	/** @return provider which calls the given function (memoized) */
	static LookupDescriptorProvider fromMemoizingFunction(final Function<LookupScope, LookupDescriptor> providerFunction)
	{
		final Function<LookupScope, LookupDescriptor> providerFunctionMemoized = Functions.memoizing(providerFunction);
		return (scope) -> providerFunctionMemoized.apply(scope);
	}

	/** Provider which returns <code>null</code> for any scope) */
	LookupDescriptorProvider NULL = (scope) -> null;

	public static enum LookupScope
	{
		DocumentField, DocumentFilter
	}

	/**
	 * @return lookup descriptor or null
	 */
	LookupDescriptor provideForScope(LookupScope scope);

	default boolean isNumericKey()
	{
		final LookupDescriptor descriptorForScope = provideForScope(LookupScope.DocumentField);
		if (descriptorForScope == null)
		{
			return false;
		}
		return descriptorForScope.isNumericKey();
	}

	default Optional<String> getTableName()
	{
		final LookupDescriptor lookupDescriptor = provideForScope(LookupScope.DocumentField);
		return lookupDescriptor != null ? lookupDescriptor.getTableName() : Optional.empty();
	}
}
