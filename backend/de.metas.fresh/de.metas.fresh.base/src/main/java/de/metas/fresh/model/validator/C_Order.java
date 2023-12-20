package de.metas.fresh.model.validator;

/*
 * #%L
 * de.metas.fresh.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.bpartner.service.IBPartnerBL;
import de.metas.handlingunits.model.I_C_Order;
import de.metas.i18n.Language;
import de.metas.letter.BoilerPlate;
import de.metas.letter.BoilerPlateId;
import de.metas.letter.BoilerPlateRepository;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.spi.IWarehouseAdvisor;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.ModelValidator;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;
import org.springframework.stereotype.Component;

@Interceptor(I_C_Order.class)
public class C_Order
{
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_NEW)
	public void setWarehouse(final I_C_Order order)
	{
		final WarehouseId warehouseId = Services.get(IWarehouseAdvisor.class).evaluateOrderWarehouse(order);
		if (warehouseId != null)
		{
			order.setM_Warehouse_ID(warehouseId.getRepoId());
		}
	}
}
