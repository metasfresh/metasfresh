package de.metas.commission.service;

/*
 * #%L
 * de.metas.commission.base
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
import java.util.Properties;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_Order;

import de.metas.commission.interfaces.I_C_OrderLine;

public interface IOrderLineBL extends ISingletonService
{

	/**
	 * Update the discounts of the given order line and its "siblings"
	 * 
	 * @param ctx
	 * @param ol
	 * @param oldVal
	 * @param ignoreOlPoints when summing up the ol's commission points, ignore the given order. This is required if the given order is just deleted
	 * @param nullIfOk
	 * @param trxName
	 * @return
	 */
	String updateDiscounts(Properties ctx, I_C_OrderLine ol, BigDecimal oldVal,
			boolean ignoreOlPoints, boolean nullIfOk, String trxName);

	String updateDiscounts(Properties ctx, I_C_Order orderPO, boolean nullIfOk, String trxName);

	void ignore(int orderLineId);

	void unignore(int orderLineId);

}
