package de.metas.order;

import org.adempiere.util.ISingletonService;

import de.metas.adempiere.model.I_C_Order;
import de.metas.interfaces.I_C_OrderLine;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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

public interface IOrderLinePricingConditions extends ISingletonService
{
	/**
	 * If the de.metas.order.NoPriceConditionsColorName sysconfig is set and the orderLine.M_DiscountSchemaBreak_ID is not set, the orderLine.NoPriceConditionsColor_ID will be set to the colourId corresponding with the name provided in the sys config.
	 * If the de.metas.order.NoPriceConditionsColorName sysconfig is set and the orderLine.M_DiscountSchemaBreak_ID is set, the orderLine.NoPriceConditionsColor_ID will be set to null because a color warning is not needed.
	 * If the sys config is not set, do nothing because the functionality is not needed
	 * 
	 * @param orderLine
	 * @task #3835
	 */
	void updateNoPriceConditionsColor(I_C_OrderLine orderLine);

	/**
	 * Throw an error message if the sysconfig for mandatory pricing conditions is set ( see de.metas.order.impl.OrderLineBL.SYSCONFIG_NoPriceConditionsColorName) but the order contains lines that don't have the pricing conditions set.
	 */
	void failForMissingPricingConditions(I_C_Order order);

}
