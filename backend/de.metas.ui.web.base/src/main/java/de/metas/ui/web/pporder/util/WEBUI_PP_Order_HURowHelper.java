/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2022 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.ui.web.pporder.util;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.OnOverDelivery;
import de.metas.handlingunits.picking.PickFrom;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.handlingunits.picking.requests.PickRequest;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.logging.LogManager;
import de.metas.picking.api.PickingSlotId;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.pporder.PPOrderLineRow;
import de.metas.ui.web.pporder.PPOrderLinesView;
import de.metas.ui.web.view.IViewRow;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.eevolution.api.PPOrderId;
import org.slf4j.Logger;

import javax.annotation.Nullable;

public class WEBUI_PP_Order_HURowHelper
{

	private static final Logger logger = LogManager.getLogger(WEBUI_PP_Order_HURowHelper.class);
	private final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	public final static void pickHU(final WEBUI_PPOrder_PickingContext context)
	{
		final PickingCandidateService pickingCandidateService = SpringContextHolder.instance.getBean(PickingCandidateService.class);

		pickingCandidateService.pickHU(PickRequest.builder()
											   .shipmentScheduleId(context.getShipmentScheduleId())
											   .pickFrom(PickFrom.ofHuId(context.getHuId()))
											   .qtyToPick(context.getQtyToPick())
											   .pickingSlotId(context.getPickingSlotId())
											   .build());
		// NOTE: we are not moving the HU to shipment schedule's locator.
		pickingCandidateService.processForHUIds(ImmutableSet.of(context.getHuId()),
												context.getShipmentScheduleId(),
												OnOverDelivery.ofTakeWholeHUFlag(context.isTakeWholeHU()),
												context.getPpOrderId());
	}


	@Nullable
	public final static HURow toHURowOrNull(final IViewRow viewRow)
	{
		if (viewRow instanceof HUEditorRow)
		{
			final HUEditorRow huRow = HUEditorRow.cast(viewRow);
			return HURow.builder()
					.huId(huRow.getHuId())
					.topLevelHU(huRow.isTopLevel())
					.huStatusActive(huRow.isHUStatusActive())
					.build();
		}
		else if (viewRow instanceof PPOrderLineRow)
		{
			final PPOrderLineRow ppOrderLineRow = PPOrderLineRow.cast(viewRow);

			// this process does not apply to source HUs
			if (ppOrderLineRow.isSourceHU())
			{
				return null;
			}

			if (!ppOrderLineRow.getType().isHUOrHUStorage())
			{
				return null;
			}
			return HURow.builder()
					.huId(ppOrderLineRow.getHuId())
					.topLevelHU(ppOrderLineRow.isTopLevelHU())
					.huStatusActive(ppOrderLineRow.isHUStatusActive())
					.qty(ppOrderLineRow.getQty())
					.build();
		}
		else
		{
			//noinspection ThrowableNotThrown
			new AdempiereException("Row type not supported: " + viewRow).throwIfDeveloperModeOrLogWarningElse(logger);
			return null;
		}
	}

	public final static boolean isEligibleHU(final HURow row)
	{
		final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		final I_M_HU hu = handlingUnitsBL.getById(row.getHuId());
		// Multi product HUs are not allowed - see https://github.com/metasfresh/metasfresh/issues/6709
		return huContextFactory
				.createMutableHUContext()
				.getHUStorageFactory()
				.getStorage(hu)
				.isSingleProductStorage();
	}

}