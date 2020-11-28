package de.metas.materialtracking.spi.impl.listeners;

import java.math.BigDecimal;

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

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.materialtracking.IMaterialTrackingListener;
import de.metas.materialtracking.MTLinkRequest;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.materialtracking.model.I_M_Material_Tracking_Ref;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

public final class CompositeMaterialTrackingListener implements IMaterialTrackingListener
{
	private final Map<String, List<IMaterialTrackingListener>> tableName2listeners = new HashMap<>();

	public void addMaterialTrackingListener(final String tableName, @NonNull final IMaterialTrackingListener listener)
	{
		Check.assumeNotEmpty(tableName, "tableName not empty");

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

	@Override
	public void beforeModelLinked(
			@NonNull final MTLinkRequest request,
			@NonNull final I_M_Material_Tracking_Ref materialTrackingRef)
	{
		for (final IMaterialTrackingListener listener : getListenersForModel(request.getModel()))
		{
			listener.beforeModelLinked(request, materialTrackingRef);
		}
	}

	@Override
	public void afterModelLinked(final MTLinkRequest request)
	{
		for (final IMaterialTrackingListener listener : getListenersForModel(request.getModel()))
		{
			listener.afterModelLinked(request);
		}
	}

	@Override
	public void afterModelUnlinked(final Object model,
			final I_M_Material_Tracking materialTrackingOld)
	{
		for (final IMaterialTrackingListener listener : getListenersForModel(model))
		{
			listener.afterModelUnlinked(model, materialTrackingOld);
		}
	}

	@Override
	public void afterQtyIssuedChanged(
			@NonNull final I_M_Material_Tracking_Ref materialTrackingRef,
			@NonNull final BigDecimal oldValue)
	{
		final String tableName = Services.get(IADTableDAO.class).retrieveTableName(materialTrackingRef.getAD_Table_ID());
		for (final IMaterialTrackingListener listener : getListenersForTableName(tableName))
		{
			listener.afterQtyIssuedChanged(materialTrackingRef, oldValue);
}
	}

	private List<IMaterialTrackingListener> getListenersForModel(final Object model)
	{
		final String tableName = InterfaceWrapperHelper.getModelTableName(model);
		return getListenersForTableName(tableName);
	}

	private List<IMaterialTrackingListener> getListenersForTableName(final String tableName)
	{
		final List<IMaterialTrackingListener> listeners = tableName2listeners.get(tableName);
		if (listeners == null || listeners.isEmpty())
		{
			return Collections.emptyList();
		}

		return listeners;
	}

}
