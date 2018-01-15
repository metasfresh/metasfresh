package de.metas.material.planning.pporder;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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
import java.util.List;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_M_Product;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOM;
import org.eevolution.model.I_PP_Order_BOMLine;

public interface IPPOrderBOMDAO extends ISingletonService
{

	/**
	 * Retrieve (active) BOM Lines for a specific PP Order
	 * 
	 * @param order
	 * @return
	 */
	List<I_PP_Order_BOMLine> retrieveOrderBOMLines(I_PP_Order order);

	/**
	 * Retrieve (active) BOM Lines for a specific PP Order
	 * 
	 * @param order
	 * @param orderBOMLineClass
	 * @return
	 */
	<T extends I_PP_Order_BOMLine> List<T> retrieveOrderBOMLines(I_PP_Order order, Class<T> orderBOMLineClass);

	/**
	 * Retrive all (active or not) BOM Lines for given <code>orderBOM</code>.
	 * 
	 * @param orderBOM
	 * @return
	 */
	List<I_PP_Order_BOMLine> retrieveAllOrderBOMLines(I_PP_Order_BOM orderBOM);

	/**
	 * Retrive (active) Order BOM Line alternatives for given Component BOM Line.
	 * 
	 * @param orderBOMLine
	 * @param orderBOMLineClass
	 * @return
	 */
	<T extends I_PP_Order_BOMLine> List<T> retrieveOrderBOMLineAlternatives(I_PP_Order_BOMLine orderBOMLine, Class<T> orderBOMLineClass);

	/**
	 * Retrieve Main Component BOM Line for a given alternative BOM Line
	 * 
	 * @param orderBOMLineAlternative
	 * @return
	 */
	I_PP_Order_BOMLine retrieveComponentBOMLineForAlternative(I_PP_Order_BOMLine orderBOMLineAlternative);

	I_PP_Order_BOM retrieveOrderBOM(I_PP_Order order);

	int retrieveNextLineNo(I_PP_Order order);

	I_PP_Order_BOMLine retrieveOrderBOMLine(I_PP_Order ppOrder, I_M_Product product);

	/**
	 * @return recorded Qty Usage Variance so far
	 */
	BigDecimal retrieveQtyUsageVariance(I_PP_Order_BOMLine orderBOMLine);
}
