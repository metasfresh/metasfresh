package de.metas.adempiere.form.terminal;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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


import java.util.HashMap;
import java.util.Map;

public class FactorySupport
{
	private Map<Class<?>, IFactory<?>> factories = null;

	public <T> void registerFactory(Class<T> clazz, IFactory<T> factory)
	{
		if (factories == null)
			factories = new HashMap<Class<?>, IFactory<?>>();
		factories.put(clazz, factory);
	}

	public <T> T create(Class<T> clazz, Object source)
	{
		if (factories == null)
			return null;

		@SuppressWarnings("unchecked")
		IFactory<T> factory = (IFactory<T>)factories.get(clazz);

		if (factory == null)
			return null;

		return factory.create(source);
	}

	public void clear()
	{
		if (factories != null)
		{
			factories.clear();
			factories = null;
		}
	}
}
