package de.metas.handlingunits.materialtracking.spi.impl;

import de.metas.organization.ClientAndOrgId;
import lombok.NonNull;

import java.util.HashMap;

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
import java.util.Map;

import org.compiere.util.Env;
import org.eevolution.model.I_PP_Order;
import org.slf4j.Logger;

import de.metas.handlingunits.HUConstants;
import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.materialtracking.IHUMaterialTrackingBL;
import de.metas.handlingunits.materialtracking.IHUPPOrderMaterialTrackingBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Assignment;
import de.metas.handlingunits.model.I_PP_Cost_Collector;
import de.metas.logging.LogManager;
import de.metas.materialtracking.IMaterialTrackingBL;
import de.metas.materialtracking.MTLinkRequest;
import de.metas.materialtracking.MaterialTrackingListenerAdapter;
import de.metas.materialtracking.MTLinkRequest.IfModelAlreadyLinked;
import de.metas.materialtracking.MTLinkRequest.MTLinkRequestBuilder;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.ToString;

/**
 * For link requests whose <code>model</code> has an {@link I_M_HU_Assignment} this listener updates the <code>M_Material_Tracking</code> HU-attributes of the assigned HUs.
 * <p>
 * <b>Important:</b> this listener does nothing, unless the given request's {@link MTLinkRequest#getParams()} has {@link HUConstants#PARAM_CHANGE_HU_MAterial_Tracking_ID} <code>=true</code>.
 *
 * @author metas-dev <dev@metasfresh.com>
 * @task http://dewiki908/mediawiki/index.php/09106_Material-Vorgangs-ID_nachtr%C3%A4glich_erfassen_%28101556035702%29
 */
@ToString
public class HUDocumentLineLineMaterialTrackingListener extends MaterialTrackingListenerAdapter
{
	private static final transient Logger logger = LogManager.getLogger(HUDocumentLineLineMaterialTrackingListener.class);

	public static HUDocumentLineLineMaterialTrackingListener INSTANCE = new HUDocumentLineLineMaterialTrackingListener();

	private HUDocumentLineLineMaterialTrackingListener()
	{
		// nothing
	}

	@Override
	public void afterModelLinked(@NonNull final MTLinkRequest request)
	{
		if (!request.getParams().getParameterAsBool(HUConstants.PARAM_CHANGE_HU_MAterial_Tracking_ID))
		{
			logger.debug(
					"request {} has no Params or has {} == false; nothing to do",
					new Object[] { request, HUConstants.PARAM_CHANGE_HU_MAterial_Tracking_ID });
			return;
		}

		final IHUAssignmentDAO huAssignmentDAO = Services.get(IHUAssignmentDAO.class);

		final List<I_M_HU_Assignment> huAssignmentsForModel = huAssignmentDAO.retrieveTopLevelHUAssignmentsForModel(request.getModel());
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
	 * <li>updates the given HU's M_Material_Tracking_ID HU_Attribute
	 * <li>checks if the HU was issued to a PP_Order and updates the PP_Order's M_Material_Tracking_Refs
	 * </ul>
	 */
	private void processLinkRequestForHU(@NonNull final I_M_HU hu, @NonNull final MTLinkRequest request)
	{
		final I_M_Material_Tracking materialTracking = request.getMaterialTrackingRecord();

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
			final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
			final IHUPPOrderMaterialTrackingBL huPPOrderMaterialTrackingBL = Services.get(IHUPPOrderMaterialTrackingBL.class);

			final IMutableHUContext huContext = handlingUnitsBL.createMutableHUContext(Env.getCtx(), ClientAndOrgId.ofClientAndOrg(hu.getAD_Client_ID(), hu.getAD_Org_ID()));
			final I_M_Material_Tracking previousMaterialTrackingRecord = huPPOrderMaterialTrackingBL.extractMaterialTrackingIfAny(huContext, hu);

			final MTLinkRequestBuilder requestBuilder = request
					.toBuilder()
					.ifModelAlreadyLinked(IfModelAlreadyLinked.UNLINK_FROM_PREVIOUS);
			if (previousMaterialTrackingRecord != null)
			{
				requestBuilder.previousMaterialTrackingId(previousMaterialTrackingRecord.getM_Material_Tracking_ID());
			}

			final List<I_PP_Cost_Collector> costCollectors = huAssignmentDAO.retrieveModelsForHU(hu, I_PP_Cost_Collector.class);
			final Map<Integer, I_PP_Order> id2pporder = new HashMap<>();
			for (final I_PP_Cost_Collector costCollector : costCollectors)
			{
				if (costCollector.getPP_Order_ID() <= 0)
				{
					continue; // this might never be the case but I'm not well versed with such stuff
				}
				id2pporder.put(costCollector.getPP_Order_ID(), costCollector.getPP_Order());
			}

			for (final I_PP_Order ppOrder : id2pporder.values())
			{
				materialTrackingBL.linkModelToMaterialTracking(requestBuilder.model(ppOrder).build());

				final String msg = "Updated M_Material_Tracking_Ref for PP_Order " + ppOrder + " of M_HU " + hu;
				logger.debug(msg);
				loggable.addLog(msg);
			}
		}
	}
	}
