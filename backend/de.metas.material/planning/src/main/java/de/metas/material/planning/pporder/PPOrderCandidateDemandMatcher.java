/*
 * #%L
 * metasfresh-material-planning
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

package de.metas.material.planning.pporder;

import de.metas.material.planning.IMaterialDemandMatcher;
import de.metas.material.planning.IMaterialPlanningContext;
import de.metas.material.planning.ProductPlanning;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class PPOrderCandidateDemandMatcher implements IMaterialDemandMatcher
{
	@Override
	public boolean matches(@NonNull final IMaterialPlanningContext mrpContext)
	{
		final ProductPlanning productPlanning = mrpContext.getProductPlanning();

		final boolean manufactured = productPlanning.isManufactured();
		final boolean pickingOrder = productPlanning.isPickingOrder(); // basically, picking orders are different beast.
		if (manufactured && !pickingOrder)
		{
			return true;
		}

		final ProductId productId = mrpContext.getProductId();
		final String productName = Services.get(IProductBL.class).getProductValueAndName(productId);
		Loggables.addLog(
				"Product {} is not set to be manufactured; PPOrderCandidateDemandMatcher returns false; productPlanning={}; product={}",
				productName, productPlanning, productId);
		return false;
	}
}
