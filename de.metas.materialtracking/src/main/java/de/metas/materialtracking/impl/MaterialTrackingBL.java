package de.metas.materialtracking.impl;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Loggables;
import org.adempiere.util.Services;

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

import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.materialtracking.IMaterialTrackingBL;
import de.metas.materialtracking.IMaterialTrackingDAO;
import de.metas.materialtracking.IMaterialTrackingListener;
import de.metas.materialtracking.MTLinkRequest;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.materialtracking.model.I_M_Material_Tracking_Ref;
import de.metas.materialtracking.spi.impl.listeners.CompositeMaterialTrackingListener;

public class MaterialTrackingBL implements IMaterialTrackingBL
{

	private final CompositeMaterialTrackingListener listeners = new CompositeMaterialTrackingListener();

	/**
	 * Is material tracking module enabled ?
	 *
	 * By default this is disabled and it will be enabled programmatically by material tracking module activator.
	 */
	private boolean _enabled = false;

	private static final transient Logger logger = LogManager.getLogger(MaterialTrackingBL.class);

	@Override
	public boolean isEnabled()
	{
		return _enabled;
	}

	@Override
	public void setEnabled(final boolean enabled)
	{
		_enabled = enabled;
	}

	@Override
	public void addModelTrackingListener(final String tableName, final IMaterialTrackingListener listener)
	{
		listeners.addMaterialTrackingListener(tableName, listener);
	}

	@Override
	public void linkModelToMaterialTracking(final MTLinkRequest request)
	{
		final I_M_Material_Tracking materialTracking = request.getMaterialTracking();
		final Object model = request.getModel();

		Check.assumeNotNull(model, "model not null in MTLinkRequest {}", request);
		Check.assumeNotNull(materialTracking, "materialTracking not null in MTLinkRequest {}", request);

		final IMaterialTrackingDAO materialTrackingDAO = Services.get(IMaterialTrackingDAO.class);

		//
		// Retrieve existing reference
		// and if exists and it's not linked to our material tracking, delete it
		final I_M_Material_Tracking_Ref refExisting = materialTrackingDAO.retrieveMaterialTrackingRefForModel(model);
		if (refExisting != null)
		{
			if (materialTracking.getM_Material_Tracking_ID() == refExisting.getM_Material_Tracking_ID())
			{
				// Case: material tracking was not changed => do nothing
				final String msg = ": M_Material_Tracking_ID=" + materialTracking.getM_Material_Tracking_ID() + " of existing M_Material_Tracking_Ref is still valid; nothing to do";
				logRequest(request, msg);
				return;
			}

			//
			// If material tracking don't match and we're going under the assumption that they're already assigned, do NOT drop the assignment to create a new one.
			// Instead, notify the user that he misconfigured something
			if (request.isAssumeNotAlreadyAssigned())
			{
				throw new AdempiereException("Cannot assign model to a different material tracking"
						+ "\n Model: " + request.getModel()
						+ "\n Material tracking (current): " + refExisting.getM_Material_Tracking()
						+ "\n Material tracking (new): " + materialTracking);
			}

			// Case: material tracking changed => delete old link
			unlinkModelFromMaterialTracking(request.getModel(), refExisting);
		}

		//
		// Create the new link
		createMaterialTrackingRef(request);
	}

	private final I_M_Material_Tracking_Ref createMaterialTrackingRef(final MTLinkRequest request)
	{
		final IMaterialTrackingDAO materialTrackingDAO = Services.get(IMaterialTrackingDAO.class);
		final I_M_Material_Tracking_Ref refNew = materialTrackingDAO.createMaterialTrackingRefNoSave(request.getMaterialTracking(), request.getModel());

		final String msg = ": Linking model with M_Material_Tracking_ID=" + refNew.getM_Material_Tracking_ID();
		logRequest(request, msg);

		listeners.beforeModelLinked(request, refNew);

		InterfaceWrapperHelper.save(refNew);

		listeners.afterModelLinked(request);

		return refNew;
	}

	private void logRequest(final MTLinkRequest request, final String msgSuffix)
	{
		logger.debug(request + msgSuffix); // log the request
		Loggables.get().addLog(request.getModel() + msgSuffix); // don't be too verbose in the user/admin output; keep it readable.
	}

	@Override
	public boolean unlinkModelFromMaterialTracking(final Object model)
	{
		final IMaterialTrackingDAO materialTrackingDAO = Services.get(IMaterialTrackingDAO.class);
		final I_M_Material_Tracking_Ref refExisting = materialTrackingDAO.retrieveMaterialTrackingRefForModel(model);
		if (refExisting == null)
		{
			return false;
		}

		unlinkModelFromMaterialTracking(model, refExisting);
		return true;
	}

	@Override
	public boolean unlinkModelFromMaterialTracking(final Object model, final I_M_Material_Tracking materialTracking)
	{
		Check.assumeNotNull(materialTracking, "materialTracking not null");

		final IMaterialTrackingDAO materialTrackingDAO = Services.get(IMaterialTrackingDAO.class);
		final I_M_Material_Tracking_Ref refExisting = materialTrackingDAO.retrieveMaterialTrackingRefForModel(model);
		if (refExisting == null)
		{
			return false;
		}

		if (refExisting.getM_Material_Tracking_ID() != materialTracking.getM_Material_Tracking_ID())
		{
			return false;
		}

		unlinkModelFromMaterialTracking(model, refExisting);
		return true;
	}

	private final void unlinkModelFromMaterialTracking(final Object model, final I_M_Material_Tracking_Ref ref)
	{
		final I_M_Material_Tracking materialTrackingOld = ref.getM_Material_Tracking();

		InterfaceWrapperHelper.delete(ref);
		listeners.afterModelUnlinked(model, materialTrackingOld);
	}

}
