package de.metas.materialtracking.spi.impl.listeners;

/*
 * #%L
 * de.metas.materialtracking
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


import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.materialtracking.IMaterialTrackingListener;
import de.metas.materialtracking.MTLinkRequest;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.materialtracking.model.I_M_Material_Tracking_Ref;
import de.metas.util.Check;

public final class CompositeMaterialTrackingListener implements IMaterialTrackingListener
{
	private final Map<String, List<IMaterialTrackingListener>> tableName2listeners = new HashMap<>();

	public void addMaterialTrackingListener(final String tableName, final IMaterialTrackingListener listener)
	{
		Check.assumeNotEmpty(tableName, "tableName not empty");
		Check.assumeNotNull(listener, "listener not null");

		List<IMaterialTrackingListener> listeners = tableName2listeners.get(tableName);
		if (listeners == null)
		{
			listeners = new CopyOnWriteArrayList<>();
			tableName2listeners.put(tableName, listeners);
		}

		// Don't add it again if it was already added
		if (listeners.contains(listener))
		{
			return;
		}

		listeners.add(listener);
	}

	private final List<IMaterialTrackingListener> getListenersForTable(final Object model)
	{
		final String tableName = InterfaceWrapperHelper.getModelTableName(model);
		final List<IMaterialTrackingListener> listeners = tableName2listeners.get(tableName);
		if (listeners == null || listeners.isEmpty())
		{
			return Collections.emptyList();
		}

		return listeners;
	}

	@Override
	public void beforeModelLinked(final MTLinkRequest request, 
			final I_M_Material_Tracking_Ref materialTrackingRef)
	{
		for (final IMaterialTrackingListener listener : getListenersForTable(request.getModel()))
		{
			listener.beforeModelLinked(request, materialTrackingRef);
		}
	}

	@Override
	public void afterModelLinked(final MTLinkRequest request)
	{
		for (final IMaterialTrackingListener listener : getListenersForTable(request.getModel()))
		{
			listener.afterModelLinked(request);
		}
	}

	@Override
	public void afterModelUnlinked(final Object model, 
			final I_M_Material_Tracking materialTrackingOld)
	{
		for (final IMaterialTrackingListener listener : getListenersForTable(model))
		{
			listener.afterModelUnlinked(model, materialTrackingOld);
		}
	}

}
