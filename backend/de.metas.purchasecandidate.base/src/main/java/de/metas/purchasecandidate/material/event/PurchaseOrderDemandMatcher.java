package de.metas.purchasecandidate.material.event;

import de.metas.material.planning.IMaterialDemandMatcher;
import de.metas.material.planning.IMaterialPlanningContext;
import de.metas.material.planning.ProductPlanning;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.springframework.stereotype.Service;

/*
 * #%L
 * metasfresh-mrp
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Service
public class PurchaseOrderDemandMatcher implements IMaterialDemandMatcher
{
	@Override
	public boolean matches(@NonNull final IMaterialPlanningContext mrpContext)
	{
		final ProductPlanning productPlanning = mrpContext.getProductPlanning();

		if (productPlanning.isPurchased())
		{
			return true;
		}

		final ProductId productId = mrpContext.getProductId();
		final String productName = Services.get(IProductBL.class).getProductValueAndName(productId);
		Loggables.addLog(
				"Product {} is not set to be purchased; PurchaseOrderDemandMatcher returns false; productPlanning={}; product={}",
				productName, productPlanning, productId);
		return false;
	}

}
