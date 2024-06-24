package de.metas.inoutcandidate.modelvalidator;

import de.metas.inoutcandidate.picking_bom.PickingBOMService;
import de.metas.material.planning.ProductPlanning;
import de.metas.material.planning.impl.ProductPlanningDAO;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.eevolution.model.I_PP_Product_Planning;
import org.springframework.stereotype.Component;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Interceptor(I_PP_Product_Planning.class)
@Component
public class PP_Product_Planning
{
	private final PickingBOMService pickingBOMService;

	public PP_Product_Planning(@NonNull final PickingBOMService pickingBOMService)
	{
		this.pickingBOMService = pickingBOMService;
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void beforeSave(@NonNull final I_PP_Product_Planning record)
	{
		final ProductPlanning productPlanning = ProductPlanningDAO.fromRecord(record);

		//
		// Make sure the picking order configuration, if any, is valid
		if (productPlanning.isPickingOrder())
		{
			pickingBOMService.extractPickingOrderConfig(productPlanning); // extract it just to validate
		}
	}

}
