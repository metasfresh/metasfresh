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
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.ProductPlanningId;
import de.metas.material.planning.ddorder.DDOrderUtil;
import de.metas.material.planning.ddorder.DistributionNetworkAndLineId;
import de.metas.material.planning.ddorder.DistributionNetworkRepository;
import de.metas.material.replenish.ReplenishInfoRepository;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.springframework.stereotype.Component;

import static de.metas.distribution.ddorder.lowlevel.interceptor.DD_Order_PostMaterialEvent.createAndInitPPOrderPojoBuilder;
import static de.metas.distribution.ddorder.lowlevel.interceptor.DD_Order_PostMaterialEvent.createDDOrderLinePojo;

@Interceptor(I_DD_OrderLine.class)
@Component
public class DD_OrderLine_PostMaterialEvent
{
	private final ReplenishInfoRepository replenishInfoRepository;
	private final PostMaterialEventService postMaterialEventService;
	private final IProductPlanningDAO productPlanningDAO = Services.get(IProductPlanningDAO.class);
	@NonNull private final DistributionNetworkRepository distributionNetworkRepository;

	public DD_OrderLine_PostMaterialEvent(
			@NonNull final ReplenishInfoRepository replenishInfoRepository,
			@NonNull final PostMaterialEventService postMaterialEventService,
			@NonNull final DistributionNetworkRepository distributionNetworkRepository)
	{
		this.replenishInfoRepository = replenishInfoRepository;
		this.postMaterialEventService = postMaterialEventService;
		this.distributionNetworkRepository = distributionNetworkRepository;
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

		final ProductPlanningId productPlanningId = ProductPlanningId.ofRepoId(ddOrder.getPP_Product_Planning_ID());
		final DistributionNetworkAndLineId networkAndLineId = DistributionNetworkAndLineId.ofRepoIds(oldDDOrderLine.getDD_NetworkDistribution_ID(), oldDDOrderLine.getDD_NetworkDistributionLine_ID());
		final int durationDays = DDOrderUtil.calculateDurationDays(
				productPlanningDAO.getById(productPlanningId), distributionNetworkRepository.getLineById(networkAndLineId));

		ddOrderBuilder.lines(ImmutableList.of(createDDOrderLinePojo(replenishInfoRepository, oldDDOrderLine, ddOrder, durationDays)));

		final DDOrderDeletedEvent event = DDOrderDeletedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(ddOrder.getAD_Client_ID(), ddOrder.getAD_Org_ID()))
				.ddOrder(ddOrderBuilder.build())
				.build();

		postMaterialEventService.enqueueEventNow(event);
	}
}
