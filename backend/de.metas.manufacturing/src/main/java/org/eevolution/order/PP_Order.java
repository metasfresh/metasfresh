/*
 * #%L
 * de.metas.manufacturing
 * %%
 * Copyright (C) 2024 metas GmbH
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

package org.eevolution.order;

import de.metas.common.util.time.SystemTime;
import de.metas.document.engine.DocStatus;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.eevolution.api.IProductBOMDAO;
import org.eevolution.api.ProductBOMId;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Product_BOM;
import org.springframework.stereotype.Component;

@Component
@Interceptor(I_PP_Order.class)
public class PP_Order
{
	private final IProductBOMDAO productBOMDAO = Services.get(IProductBOMDAO.class);

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = I_PP_Order.COLUMNNAME_PP_Product_BOM_ID)
	public void refreshDateStartSchedule(@NonNull final I_PP_Order ppOrder)
	{
		if (!DocStatus.ofCode(ppOrder.getDocStatus()).isDraftedOrInProgress())
		{
			return;
		}

		final I_PP_Product_BOM newProductBOMVersion = productBOMDAO.getById(ProductBOMId.ofRepoId(ppOrder.getPP_Product_BOM_ID()));

		if (newProductBOMVersion.getValidFrom().after(ppOrder.getDateStartSchedule()))
		{
			ppOrder.setDateStartSchedule(SystemTime.asTimestamp());
		}
	}
}
