package de.metas.material.planning.pporder;

import org.adempiere.util.Loggables;
import org.compiere.model.I_M_Product;
import org.eevolution.model.I_PP_Product_Planning;
import org.eevolution.model.X_PP_Product_Planning;
import org.springframework.stereotype.Service;

import de.metas.material.planning.IMaterialDemandMatcher;
import de.metas.material.planning.IMaterialPlanningContext;

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
/**
 * This implementation figures out if a particular demand could be matched by a PPOrder.
 * The business logic of the {@link #matches(IMaterialPlanningContext)} method is coming from
 * <code>/de.metas.adempiere.libero.libero/src/main/java/org/eevolution/mrp/spi/impl/PPOrderMRPSupplyProducer.java</code>
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Service
public class PPOrderDemandMatcher implements IMaterialDemandMatcher
{
	/**
	 *
	 */
	@Override
	public boolean matches(final IMaterialPlanningContext mrpContext)
	{
		final I_PP_Product_Planning productPlanning = mrpContext.getProductPlanning();
		if (productPlanning == null)
		{
			Loggables.get().addLog("Given MRP context has no PP_Product_Planning; mrpContext={}", mrpContext);
			return false;
		}

		final I_M_Product product = mrpContext.getM_Product();
		final boolean isManufactured = X_PP_Product_Planning.ISMANUFACTURED_Yes.equals(productPlanning.getIsManufactured());
		if (!isManufactured)
		{
			Loggables.get().addLog("Product {}_{} is configured to not be manufactured; product={}", product.getValue(), product.getName(), product);
			return false;
		}

		return true;
	}
}
