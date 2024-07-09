package de.metas.material.planning.ddorder;

import de.metas.material.planning.IMaterialDemandMatcher;
import de.metas.material.planning.IMaterialPlanningContext;
import de.metas.material.planning.ProductPlanning;
import de.metas.util.Loggables;
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
/**
 * This implementation figures out if a particular demand could be matched by a DDOrder.<br/>
 * The business logic of the {@link #matches(IMaterialPlanningContext)} method is coming from
 * <code>/de.metas.adempiere.libero.libero/src/main/java/org/eevolution/mrp/spi/impl/DDOrderMRPSupplyProducer.java</code>
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Service
public class DDOrderDemandMatcher implements IMaterialDemandMatcher
{
	@Override
	public boolean matches(final IMaterialPlanningContext mrpContext)
	{
		final ProductPlanning productPlanning = mrpContext.getProductPlanning();

		// Check if there is a distribution network
		if (productPlanning.getDistributionNetworkId() == null)
		{
			Loggables.addLog(
					"No distribution network configured in product data planning of the given mrp context; DDOrderDemandMatcher returns false; productPlanning={}; mrpContext={}",
					productPlanning, mrpContext);
			return false;
		}

		return true;
	}

}
