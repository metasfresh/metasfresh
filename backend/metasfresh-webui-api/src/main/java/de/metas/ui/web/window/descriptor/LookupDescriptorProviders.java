package de.metas.ui.web.window.descriptor;

import java.util.Optional;
import java.util.function.Function;

import javax.annotation.Nullable;

import de.metas.ui.web.window.descriptor.LookupDescriptorProvider.LookupScope;
import de.metas.util.Functions;
import de.metas.util.Functions.MemoizingFunction;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * metasfresh-webui-api
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

@UtilityClass
public class LookupDescriptorProviders
{
	/** Provider which returns <code>Optional.empty()</code> for any scope) */
	public static LookupDescriptorProvider NULL = new NullLookupDescriptorProvider();

	/** @return provider which returns given {@link LookupDescriptor} for any scope */
	public static LookupDescriptorProvider singleton(@NonNull final LookupDescriptor lookupDescriptor)
	{
		return new SingletonLookupDescriptorProvider(lookupDescriptor);
	}

	public static LookupDescriptorProvider ofNullableInstance(@Nullable final LookupDescriptor lookupDescriptor)
	{
		return lookupDescriptor != null ? singleton(lookupDescriptor) : NULL;
	}

	/** @return provider which calls the given function (memoized) */
	public static LookupDescriptorProvider fromMemoizingFunction(final Function<LookupScope, LookupDescriptor> providerFunction)
	{
		return new MemoizingFunctionLookupDescriptorProvider(providerFunction);
	}

	private static class NullLookupDescriptorProvider implements LookupDescriptorProvider
	{
		@Override
		public Optional<LookupDescriptor> provideForScope(final LookupScope scope)
		{
			return Optional.empty();
		}
	}

	@ToString
	private static class SingletonLookupDescriptorProvider implements LookupDescriptorProvider
	{
		private final Optional<LookupDescriptor> lookupDescriptor;

		private SingletonLookupDescriptorProvider(@NonNull final LookupDescriptor lookupDescriptor)
		{
			this.lookupDescriptor = Optional.of(lookupDescriptor);
		}

		@Override
		public Optional<LookupDescriptor> provideForScope(final LookupScope scope)
		{
			return lookupDescriptor;
		}
	}

	@ToString
	private static class MemoizingFunctionLookupDescriptorProvider implements LookupDescriptorProvider
	{
		private final MemoizingFunction<LookupScope, LookupDescriptor> providerFunctionMemoized;

		private MemoizingFunctionLookupDescriptorProvider(@NonNull final Function<LookupScope, LookupDescriptor> providerFunction)
		{
			providerFunctionMemoized = Functions.memoizing(providerFunction);
		}

		@Override
		public Optional<LookupDescriptor> provideForScope(final LookupScope scope)
		{
			return Optional.ofNullable(providerFunctionMemoized.apply(scope));
		}
	}
}
