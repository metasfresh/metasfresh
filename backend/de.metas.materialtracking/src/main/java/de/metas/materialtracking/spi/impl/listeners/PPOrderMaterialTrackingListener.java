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


import org.adempiere.model.InterfaceWrapperHelper;
import org.eevolution.model.I_PP_Order;

import com.google.common.annotations.VisibleForTesting;

import de.metas.materialtracking.IMaterialTrackingPPOrderBL;
import de.metas.materialtracking.MTLinkRequest;
import de.metas.materialtracking.MaterialTrackingListenerAdapter;
import de.metas.materialtracking.model.I_M_Material_Tracking_Ref;
import de.metas.util.Services;

public final class PPOrderMaterialTrackingListener extends MaterialTrackingListenerAdapter
{
	public static final transient PPOrderMaterialTrackingListener instance = new PPOrderMaterialTrackingListener();

	private PPOrderMaterialTrackingListener()
	{
	}

	/**
	 * Set {@link I_M_Material_Tracking_Ref#COLUMN_IsQualityInspectionDoc}.
	 */
	@Override
	public void beforeModelLinked(final MTLinkRequest request,
			final I_M_Material_Tracking_Ref materialTrackingRef)
	{
		final I_PP_Order ppOrder = InterfaceWrapperHelper.create(request.getModel(), I_PP_Order.class);
		setIsQualityInspectionDoc(materialTrackingRef, ppOrder);
	}

	/**
	 * Sets {@link I_M_Material_Tracking_Ref#COLUMN_IsQualityInspectionDoc} flag.
	 */
	@VisibleForTesting void setIsQualityInspectionDoc(final I_M_Material_Tracking_Ref materialTrackingRef, final I_PP_Order ppOrder)
	{
		final IMaterialTrackingPPOrderBL materialTrackingPPOrderBL = Services.get(IMaterialTrackingPPOrderBL.class);
		final boolean isQualityInspectionDoc = materialTrackingPPOrderBL.isQualityInspection(ppOrder);
		materialTrackingRef.setIsQualityInspectionDoc(isQualityInspectionDoc);
	}
}
