package de.metas.ui.web.vaadin.window.components;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

import org.adempiere.util.Check;

/*
 * #%L
 * de.metas.ui.web.vaadin
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

class CompositeFieldDependenciesBinder implements IFieldDependenciesBinder
{
	private final LinkedHashSet<IFieldDependenciesBinder> dependencyBinders = new LinkedHashSet<>();

	public CompositeFieldDependenciesBinder()
	{
		super();
	}

	public CompositeFieldDependenciesBinder add(final IFieldDependenciesBinder binder)
	{
		Check.assumeNotNull(binder, "binder not null");
		dependencyBinders.add(binder);
		return this;
	}

	public CompositeFieldDependenciesBinder addAll(final List<IFieldDependenciesBinder> binders)
	{
		dependencyBinders.addAll(binders);
		return this;
	}

	public CompositeFieldDependenciesBinder addAll(final IFieldDependenciesBinder[] binders)
	{
		return addAll(Arrays.asList(binders));
	}

	@Override
	public void bind(IFieldGroup fieldGroup)
	{
		for (final IFieldDependenciesBinder dependencyBinder : dependencyBinders)
		{
			dependencyBinder.bind(fieldGroup);
		}
	}

	@Override
	public void unbind()
	{
		for (final IFieldDependenciesBinder dependencyBinder : dependencyBinders)
		{
			dependencyBinder.unbind();
		}
	}

}
