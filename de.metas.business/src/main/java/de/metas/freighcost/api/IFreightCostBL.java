package de.metas.freighcost.api;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import org.compiere.model.MOrder;

import de.metas.adempiere.model.I_C_Order;
import de.metas.adempiere.model.I_OrderOrInOut;
import de.metas.freighcost.spi.IFreightCostFreeEvaluator;
import de.metas.util.ISingletonService;

/**
 * 
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php/Versandkostenermittlung/_-berechnung_(2009_0027_G28)'>DV-Konzept (2009_0027_G28)</a>"
 */
// // This class was formerly known as de.metas.adempiere.service.IFreightCostBL
public interface IFreightCostBL extends ISingletonService
{
	boolean checkIfFree(I_OrderOrInOut orderOrInOut);

	/**
	 * Computes the freight cost for a given shipment.
	 * 
	 * @param inOut
	 * @param trxName
	 * @return
	 */
	BigDecimal computeFreightCostForInOut(int inOutId, String trxName);

	BigDecimal computeFreightCostForOrder(Properties ctx, I_C_Order order, String trxName);

	BigDecimal computeFreightCostForOrder(MOrder order);

	boolean isFreightCostProduct(Properties ctx, int productId, String trxName);

	void evalAddFreightCostLine(final MOrder order);

	/**
	 * Register an <code>IFreightCostFreeEvaluator</code> that is consulted on the question whether a given line should
	 * be free of freight cost.
	 * 
	 * Example: subscription shipments are free, so the subscription module registers an evaluator to this effect
	 * 
	 * @param evaluator
	 *            the evaluator implementation to be registered
	 */
	void registerFreightCostFreeEvaluator(IFreightCostFreeEvaluator evaluator);

}
