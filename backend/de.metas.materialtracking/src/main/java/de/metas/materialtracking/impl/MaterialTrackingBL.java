package de.metas.materialtracking.impl;

import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;

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

import com.google.common.collect.Maps;

import de.metas.logging.LogManager;
import de.metas.materialtracking.IMaterialTrackingBL;
import de.metas.materialtracking.IMaterialTrackingDAO;
import de.metas.materialtracking.IMaterialTrackingListener;
import de.metas.materialtracking.MTLinkRequest;
import de.metas.materialtracking.MTLinkRequest.IfModelAlreadyLinked;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.materialtracking.model.I_M_Material_Tracking_Ref;
import de.metas.materialtracking.spi.impl.listeners.CompositeMaterialTrackingListener;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;

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
	public void linkModelToMaterialTracking(@NonNull final MTLinkRequest request)
	{
		final I_M_Material_Tracking materialTracking = request.getMaterialTrackingRecord();
		final Object model = request.getModel();

		final IMaterialTrackingDAO materialTrackingDAO = Services.get(IMaterialTrackingDAO.class);

		//
		// Retrieve existing reference
		// and if exists and it's not linked to our material tracking, delete it
		final List<I_M_Material_Tracking_Ref> existingRefs = materialTrackingDAO.retrieveMaterialTrackingRefsForModel(model);
		final Map<Integer, I_M_Material_Tracking_Ref> materialTrackingId2refs = Maps.uniqueIndex(existingRefs, I_M_Material_Tracking_Ref::getM_Material_Tracking_ID);
		if (!existingRefs.isEmpty())
		{
			final I_M_Material_Tracking_Ref existingRef = materialTrackingId2refs.get(materialTracking.getM_Material_Tracking_ID());
			if (existingRef != null)
			{
				// Case: material tracking was not changed => do nothing
				final BigDecimal oldQtyIssued = existingRef.getQtyIssued();
				final boolean needToUpdateQtyIssued = request.getQtyIssued() != null && request.getQtyIssued().compareTo(oldQtyIssued) != 0;
				final String msg;
				if (needToUpdateQtyIssued)
				{
					msg = ": M_Material_Tracking_ID=" + materialTracking.getM_Material_Tracking_ID() + " of existing M_Material_Tracking_Ref is valid; update qtyIssued from " + oldQtyIssued + " to " + request.getQtyIssued();

					existingRef.setQtyIssued(request.getQtyIssued());
					save(existingRef);
					listeners.afterQtyIssuedChanged(existingRef, oldQtyIssued);
				}
				else
				{
					msg = ": M_Material_Tracking_ID=" + materialTracking.getM_Material_Tracking_ID() + " of existing M_Material_Tracking_Ref is still valid; nothing to do";
				}
				logRequest(request, msg);
				return;

			}

			//
			// If material tracking don't match and we're going under the assumption that they're already assigned, do NOT drop the assignment to create a new one.
			// Instead, notify the user that he misconfigured something
			if (IfModelAlreadyLinked.FAIL.equals(request.getIfModelAlreadyLinked()))
			{
				String currentIds = existingRefs.stream().map(ref -> Integer.toString(ref.getM_Material_Tracking_ID())).collect(Collectors.joining(", "));

				throw new AdempiereException("Cannot assign model to a different material tracking"
						+ "\n Model: " + request.getModel()
						+ "\n Material trackings (current): " + currentIds
						+ "\n Material tracking (new): " + materialTracking);
			}
			else if (IfModelAlreadyLinked.UNLINK_FROM_PREVIOUS.equals(request.getIfModelAlreadyLinked()))
			{
				final I_M_Material_Tracking_Ref refToUnlink;
				if (existingRefs.size() == 1 && request.getPreviousMaterialTrackingId() == null)
				{
					refToUnlink = existingRefs.get(0);
				}
				else
				{
					refToUnlink = materialTrackingId2refs.get(request.getPreviousMaterialTrackingId());
				}

				if (refToUnlink != null)
				{
			// Case: material tracking changed => delete old link
					unlinkModelFromMaterialTracking(model, refToUnlink);
		}
			}

		}

		//
		// Create the new link
		createMaterialTrackingRef(request);
	}

	private final I_M_Material_Tracking_Ref createMaterialTrackingRef(@NonNull final MTLinkRequest request)
	{
		final IMaterialTrackingDAO materialTrackingDAO = Services.get(IMaterialTrackingDAO.class);
		final I_M_Material_Tracking_Ref refNew = materialTrackingDAO.createMaterialTrackingRefNoSave(request.getMaterialTrackingRecord(), request.getModel());
		if (request.getQtyIssued() != null)
		{
			refNew.setQtyIssued(request.getQtyIssued());
		}

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
		Loggables.addLog(request.getModel() + msgSuffix); // don't be too verbose in the user/admin output; keep it readable.
	}

	@Override
	public boolean unlinkModelFromMaterialTrackings(final Object model)
	{
		final IMaterialTrackingDAO materialTrackingDAO = Services.get(IMaterialTrackingDAO.class);
		final List<I_M_Material_Tracking_Ref> existingRefs = materialTrackingDAO.retrieveMaterialTrackingRefsForModel(model);
		for (final I_M_Material_Tracking_Ref exitingRef : existingRefs)
		{
			unlinkModelFromMaterialTracking(model, exitingRef);
		}
		return true;
	}

	@Override
	public boolean unlinkModelFromMaterialTrackings(final Object model, @NonNull final I_M_Material_Tracking materialTracking)
	{
		final IMaterialTrackingDAO materialTrackingDAO = Services.get(IMaterialTrackingDAO.class);
		final List<I_M_Material_Tracking_Ref> existingRefs = materialTrackingDAO.retrieveMaterialTrackingRefsForModel(model);
		if (existingRefs.isEmpty())
		{
			return false;
		}

		boolean atLeastOneUnlinked = false;
		for (final I_M_Material_Tracking_Ref exitingRef : existingRefs)
		{
			if (exitingRef.getM_Material_Tracking_ID() != materialTracking.getM_Material_Tracking_ID())
			{
				continue;
		}
			unlinkModelFromMaterialTracking(model, exitingRef);
			atLeastOneUnlinked = true;
	}
		return atLeastOneUnlinked;
	}

	private final void unlinkModelFromMaterialTracking(final Object model, final I_M_Material_Tracking_Ref ref)
	{
		final I_M_Material_Tracking materialTrackingOld = ref.getM_Material_Tracking();

		InterfaceWrapperHelper.delete(ref);
		listeners.afterModelUnlinked(model, materialTrackingOld);
	}

}
