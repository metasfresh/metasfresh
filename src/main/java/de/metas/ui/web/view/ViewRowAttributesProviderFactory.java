package de.metas.ui.web.view;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.adempiere.exceptions.AdempiereException;

import de.metas.ui.web.window.datatypes.WindowId;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public final class ViewRowAttributesProviderFactory
{
	public static final transient ViewRowAttributesProviderFactory instance = new ViewRowAttributesProviderFactory();

	private final Map<WindowId, Class<? extends IViewRowAttributesProvider>> providerClasses = new ConcurrentHashMap<>();

	private ViewRowAttributesProviderFactory()
	{
		super();
	}

	public void registerProvider(@NonNull final WindowId windowId, @NonNull final Class<? extends IViewRowAttributesProvider> providerClass)
	{
		providerClasses.put(windowId, providerClass);
	}

	public IViewRowAttributesProvider createProviderOrNull(final WindowId windowId)
	{
		final Class<? extends IViewRowAttributesProvider> providerClass = providerClasses.get(windowId);
		if (providerClass == null)
		{
			return null;
		}

		try
		{
			return providerClass.newInstance();
		}
		catch (final Exception e)
		{
			throw new AdempiereException("Cannot instantiate " + providerClass, e);
		}
	}
}
