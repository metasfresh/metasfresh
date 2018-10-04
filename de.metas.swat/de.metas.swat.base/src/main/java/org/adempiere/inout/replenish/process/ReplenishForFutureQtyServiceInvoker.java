package org.adempiere.inout.replenish.process;

/*
 * #%L
 * de.metas.swat.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


/*test*/
import java.math.BigDecimal;

import org.adempiere.inout.replenish.service.IReplenishForFutureQty;
import org.compiere.model.MWarehouse;
import org.compiere.model.X_T_Replenish;
import org.compiere.util.ReplenishInterface;

import de.metas.util.Services;

public class ReplenishForFutureQtyServiceInvoker implements ReplenishInterface {

	public BigDecimal getQtyToOrder(final MWarehouse wh,
			final X_T_Replenish replenishPO) {

		return Services.get(IReplenishForFutureQty.class)
				.getQtyToOrder(wh, replenishPO, null);
	}

}
