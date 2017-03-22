package de.metas.handlingunits.materialtracking.spi.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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

import java.util.List;

import org.adempiere.util.ILoggable;
import org.adempiere.util.Loggables;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ObjectUtils;
import org.eevolution.model.I_PP_Order;
import org.slf4j.Logger;

import de.metas.handlingunits.HUConstants;
import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.handlingunits.materialtracking.IHUMaterialTrackingBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Assignment;
import de.metas.handlingunits.model.I_PP_Cost_Collector;
import de.metas.logging.LogManager;
import de.metas.materialtracking.IMaterialTrackingBL;
import de.metas.materialtracking.MTLinkRequest;
import de.metas.materialtracking.MaterialTrackingListenerAdapter;
import de.metas.materialtracking.model.I_M_Material_Tracking;

/**
 * For link requests whose <code>model</code> has an {@link I_M_HU_Assignment} this listener updates the <code>M_Material_Tracking</code> HU-attributes of the assigned HUs.
 * <p>
 * <b>Important:</b> this listener does nothing, unless the given request's {@link MTLinkRequest#getParams()} has {@link HUConstants#PARAM_CHANGE_HU_MAterial_Tracking_ID} <code>=true</code>.
 *
 * @author metas-dev <dev@metasfresh.com>
 * @task http://dewiki908/mediawiki/index.php/09106_Material-Vorgangs-ID_nachtr%C3%A4glich_erfassen_%28101556035702%29
 */
public class HUDocumentLineLineMaterialTrackingListener extends MaterialTrackingListenerAdapter
{
	private static final transient Logger logger = LogManager.getLogger(HUDocumentLineLineMaterialTrackingListener.class);

	public static HUDocumentLineLineMaterialTrackingListener INSTANCE = new HUDocumentLineLineMaterialTrackingListener();

	private HUDocumentLineLineMaterialTrackingListener()
	{
		// nothing
	}

	@Override
	public void afterModelLinked(final MTLinkRequest request)
	{
		if (!request.getParams().getParameterAsBool(HUConstants.PARAM_CHANGE_HU_MAterial_Tracking_ID))
		{
			logger.debug(
					"request {} has no Params or has {} == false; nothing to do",
					new Object[] { request, HUConstants.PARAM_CHANGE_HU_MAterial_Tracking_ID });
			return;
		}

		final IHUAssignmentDAO huAssignmentDAO = Services.get(IHUAssignmentDAO.class);

		final List<I_M_HU_Assignment> huAssignmentsForModel = huAssignmentDAO.retrieveHUAssignmentsForModel(request.getModel());
		for (final I_M_HU_Assignment huAssignment : huAssignmentsForModel)
		{
			if (huAssignment.getM_HU_ID() > 0)
			{
				processLinkRequestForHU(huAssignment.getM_HU(), request);
			}
		}
	}

	/**
	 * This method:
	 * <ul>
	 * <li>updates the given HU's M_Matrial_Tracking_ID HU_Attribute
	 * <li>checks if the HU was issued to a PP_Order and updates the PP_Order's M_Material_Tracking_Refs
	 * </ul>
	 *
	 * @param hu
	 * @param request
	 */
	private void processLinkRequestForHU(final I_M_HU hu, final MTLinkRequest request)
	{
		final I_M_Material_Tracking materialTracking = request.getMaterialTracking();

		//
		// update the HU itself
		{
			final IHUMaterialTrackingBL huMaterialTrackingBL = Services.get(IHUMaterialTrackingBL.class);
			huMaterialTrackingBL.updateHUAttributeRecursive(hu, materialTracking, null); // update all HUs, no matter which state
		}

		//
		// check if the HU is assigned to a PP_Order and also update that PP_Order's material tracking reference
		{
			final ILoggable loggable = Loggables.get();

			final IHUAssignmentDAO huAssignmentDAO = Services.get(IHUAssignmentDAO.class);
			final IMaterialTrackingBL materialTrackingBL = Services.get(IMaterialTrackingBL.class);

			final List<I_PP_Cost_Collector> costCollectors = huAssignmentDAO.retrieveModelsForHU(hu, I_PP_Cost_Collector.class);
			for (final I_PP_Cost_Collector costCollector : costCollectors)
			{
				if (costCollector.getPP_Order_ID() <= 0)
				{
					continue; // this might never be the case but I'm not well versed with such stuff
				}

				final I_PP_Order ppOrder = costCollector.getPP_Order();

				materialTrackingBL.linkModelToMaterialTracking(
						MTLinkRequest.builder(request)
								.setModel(ppOrder)
								.setAssumeNotAlreadyAssigned(false) // unlink if necessary
								.build());

				final String msg = "Updated M_Material_Tracking_Ref for PP_Order " + ppOrder + " of M_HU " + hu;
				logger.debug(msg);
				loggable.addLog(msg);
			}
		}
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}
}
