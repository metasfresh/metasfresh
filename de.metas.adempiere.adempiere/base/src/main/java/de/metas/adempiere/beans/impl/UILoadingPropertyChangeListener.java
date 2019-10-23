package de.metas.adempiere.beans.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.ref.WeakReference;

import de.metas.adempiere.form.IClientUI;
import de.metas.util.Check;
import de.metas.util.Services;

/**
 * Property change listener for long operations (i.e display loading cursor)
 *
 * @author al
 */
public abstract class UILoadingPropertyChangeListener implements PropertyChangeListener
{
	private final WeakReference<Object> componentRef;

	public UILoadingPropertyChangeListener(final Object component)
	{
		super();

		Check.assumeNotNull(component, "component not null");
		this.componentRef = new WeakReference<Object>(component);
	}

	@Override
	public final void propertyChange(final PropertyChangeEvent evt)
	{
		final Object component = componentRef.get();
		if (component == null)
		{
			// NOTE: component reference expired
			return;
		}
		
		Services.get(IClientUI.class).executeLongOperation(component, new Runnable()
		{
			@Override
			public void run()
			{
				propertyChange0(evt);
			}
		});
	}

	protected abstract void propertyChange0(PropertyChangeEvent evt);
}
