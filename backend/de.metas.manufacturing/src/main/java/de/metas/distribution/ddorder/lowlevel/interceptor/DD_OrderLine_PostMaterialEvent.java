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

package de.metas.distribution.ddorder.lowlevel.interceptor;

import de.metas.distribution.ddorder.lowlevel.DDOrderLowLevelService;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.ddorder.DDOrder;
import de.metas.material.event.ddorder.DDOrderDeletedEvent;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.ddorder.DistributionNetworkRepository;
import de.metas.material.replenish.ReplenishInfoRepository;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;
import org.eevolution.model.I_DD_OrderLine;
import org.springframework.stereotype.Component;

@Interceptor(I_DD_OrderLine.class)
@Component
@RequiredArgsConstructor
public class DD_OrderLine_PostMaterialEvent
{
	@NonNull private final IProductPlanningDAO productPlanningDAO = Services.get(IProductPlanningDAO.class);
	@NonNull final DistributionNetworkRepository distributionNetworkRepository;
	@NonNull private final DDOrderLowLevelService ddOrderLowLevelService;
	@NonNull private final ReplenishInfoRepository replenishInfoRepository;
	@NonNull private final PostMaterialEventService materialEventService;

	private DDOrderLoader newLoader()
	{
		return DDOrderLoader.builder()
				.productPlanningDAO(productPlanningDAO)
				.distributionNetworkRepository(distributionNetworkRepository)
				.ddOrderLowLevelService(ddOrderLowLevelService)
				.replenishInfoRepository(replenishInfoRepository)
				.build();
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

		final DDOrder ddOrder = newLoader().loadWithSingleLine(ddOrderLineRecord);
		materialEventService.enqueueEventAfterNextCommit(DDOrderDeletedEvent.of(ddOrder));
	}
}
