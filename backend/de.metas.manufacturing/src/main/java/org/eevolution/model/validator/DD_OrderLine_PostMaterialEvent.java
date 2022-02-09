/*
 * #%L
 * de.metas.manufacturing
 * %%
 * Copyright (C) 2021 metas GmbH
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

package org.eevolution.model.validator;

import com.google.common.collect.ImmutableList;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.ddorder.DDOrder;
import de.metas.material.event.ddorder.DDOrderDeletedEvent;
import de.metas.material.planning.ddorder.DDOrderUtil;
import de.metas.material.replenish.ReplenishInfoRepository;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.ModelValidator;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.springframework.stereotype.Component;

import static org.eevolution.model.validator.DD_Order_PostMaterialEvent.createAndInitPPOrderPojoBuilder;
import static org.eevolution.model.validator.DD_Order_PostMaterialEvent.createDDOrderLinePojo;

@Interceptor(I_DD_OrderLine.class)
@Component
public class DD_OrderLine_PostMaterialEvent
{
	private final ReplenishInfoRepository replenishInfoRepository;
	private final PostMaterialEventService postMaterialEventService;

	public DD_OrderLine_PostMaterialEvent(
			@NonNull final ReplenishInfoRepository replenishInfoRepository,
			@NonNull final PostMaterialEventService postMaterialEventService)
	{
		this.replenishInfoRepository = replenishInfoRepository;
		this.postMaterialEventService = postMaterialEventService;
	}

	@ModelChange(
			timings = { ModelValidator.TYPE_AFTER_CHANGE },
			ifColumnsChanged = {
					I_DD_OrderLine.COLUMNNAME_M_Product_ID,
					I_DD_OrderLine.COLUMNNAME_M_AttributeSetInstance_ID,
					I_DD_OrderLine.COLUMNNAME_M_Locator_ID,
					I_DD_OrderLine.COLUMNNAME_M_LocatorTo_ID })
	public void fireDeleteDDOrderEvents(final I_DD_OrderLine ddOrderLineRecord)
	{
		if (InterfaceWrapperHelper.isNew(ddOrderLineRecord))
		{
			return;
		}

		final I_DD_OrderLine oldDDOrderLine = InterfaceWrapperHelper.createOld(ddOrderLineRecord, I_DD_OrderLine.class);

		final I_DD_Order ddOrder = ddOrderLineRecord.getDD_Order();
		final DDOrder.DDOrderBuilder ddOrderBuilder = createAndInitPPOrderPojoBuilder(ddOrder);

		final int durationDays = DDOrderUtil.calculateDurationDays(
				ddOrder.getPP_Product_Planning(), oldDDOrderLine.getDD_NetworkDistributionLine());

		ddOrderBuilder.lines(ImmutableList.of(createDDOrderLinePojo(replenishInfoRepository, oldDDOrderLine, ddOrder, durationDays)));

		final DDOrderDeletedEvent event = DDOrderDeletedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(ddOrder.getAD_Client_ID(), ddOrder.getAD_Org_ID()))
				.ddOrder(ddOrderBuilder.build())
				.fromWarehouseId(WarehouseId.ofRepoId(oldDDOrderLine.getM_Locator().getM_Warehouse_ID()))
				.toWarehouseId(WarehouseId.ofRepoId(oldDDOrderLine.getM_LocatorTo().getM_Warehouse_ID()))
				.build();

		postMaterialEventService.postEventAsync(event);
	}
}
