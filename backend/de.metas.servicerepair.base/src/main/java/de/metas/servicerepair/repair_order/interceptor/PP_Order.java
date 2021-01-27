/*
 * #%L
 * de.metas.servicerepair.base
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

package de.metas.servicerepair.repair_order.interceptor;

import de.metas.servicerepair.project.service.ServiceRepairProjectService;
import de.metas.servicerepair.repair_order.RepairManufacturingOrderService;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.compiere.model.ModelValidator;
import org.eevolution.model.I_PP_Order;
import org.springframework.stereotype.Component;

@Interceptor(I_PP_Order.class)
@Component
public class PP_Order
{
	private final ServiceRepairProjectService repairProjectService;
	private final RepairManufacturingOrderService repairManufacturingOrderService;

	public PP_Order(
			@NonNull final ServiceRepairProjectService repairProjectService,
			@NonNull final RepairManufacturingOrderService repairManufacturingOrderService)
	{
		this.repairProjectService = repairProjectService;
		this.repairManufacturingOrderService = repairManufacturingOrderService;
	}

	@DocValidate(timings = ModelValidator.TIMING_AFTER_CLOSE)
	public void onAfterClose(@NonNull final I_PP_Order record)
	{
		repairManufacturingOrderService
				.extractFromRecord(record)
				.ifPresent(repairProjectService::importCostsFromRepairOrderAndMarkTaskCompleted);
	}

	@DocValidate(timings = ModelValidator.TIMING_BEFORE_UNCLOSE)
	public void onBeforeUnClose(@NonNull final I_PP_Order record)
	{
		repairManufacturingOrderService
				.extractFromRecord(record)
				.ifPresent(repairProjectService::unimportCostsFromRepairOrder);
	}
}
