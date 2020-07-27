package de.metas.ui.web.view;

import java.util.Collection;

import com.google.common.collect.ImmutableList;

import de.metas.util.Check;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

public class CompositeViewHeaderPropertiesProvider implements ViewHeaderPropertiesProvider
{
	public static ViewHeaderPropertiesProvider of(final Collection<ViewHeaderPropertiesProvider> providers)
	{
		if (providers.isEmpty())
		{
			return NullViewHeaderPropertiesProvider.instance;
		}
		else if (providers.size() == 1)
		{
			return providers.iterator().next();
		}
		else
		{
			return new CompositeViewHeaderPropertiesProvider(ImmutableList.copyOf(providers));
		}
	}

	private final ImmutableList<ViewHeaderPropertiesProvider> providers;

	private CompositeViewHeaderPropertiesProvider(@NonNull final ImmutableList<ViewHeaderPropertiesProvider> providers)
	{
		Check.assumeNotEmpty(providers, "providers is not empty");
		this.providers = providers;
	}

	@Override
	public ViewHeaderProperties computeHeaderProperties(@NonNull final DefaultView view)
	{
		ViewHeaderProperties result = ViewHeaderProperties.EMPTY;

		for (ViewHeaderPropertiesProvider provider : providers)
		{
			final ViewHeaderProperties properties = provider.computeHeaderProperties(view);
			result = result.append(properties);
		}

		return result;
	}

}
