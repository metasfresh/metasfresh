package de.metas.fresh.picking.service;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.util.Map;
import java.util.Properties;

import org.adempiere.util.ISingletonService;

import de.metas.fresh.picking.form.IFreshPackingItem;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;

public interface IPackingService extends ISingletonService
{
	void removeProductQtyFromHU(Properties ctx, I_M_HU hu, Map<I_M_ShipmentSchedule, BigDecimal> schedules2qty);

	IPackingContext createPackingContext(Properties ctx);

	/**
	 * From <code>itemToPack</code> take out the <code>qtyToPack</code>, create a new packed item for that qty and send it to <code>itemPackedProcessor</code>.
	 * 
	 * @param packingContext
	 * @param itemToPack unpacked item that needs to be packed
	 * @param qtyToPack how much qty we need to take out
	 * @param itemPackedProcessor processor used to process our resulting packed item
	 */
	void packItem(IPackingContext packingContext,
			IFreshPackingItem itemToPack,
			BigDecimal qtyToPack,
			IPackingHandler itemPackedProcessor);
}
