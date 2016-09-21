package de.metas.inout;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;

public interface IInOutDAO extends ISingletonService
{
	List<I_M_InOutLine> retrieveLines(I_M_InOut inOut);

	List<I_M_InOutLine> retrieveAllLines(I_M_InOut inOut);

	/**
	 * Retrieve all (active) lines of given <code>inOut</code>.
	 *
	 * @param inOut
	 * @param inoutLineClass
	 * @return <code>inOut</code>'s lines
	 */
	<T extends I_M_InOutLine> List<T> retrieveLines(I_M_InOut inOut, Class<T> inoutLineClass);

	/**
	 * For the given <code>inOut</code> the method returns those inout lines that don't reference an order line.
	 *
	 * @param inOut
	 * @param clazz
	 * @return
	 */
	<T extends I_M_InOutLine> List<T> retrieveLinesWithoutOrderLine(I_M_InOut inOut, Class<T> clazz);

	List<I_M_InOutLine> retrieveLinesForOrderLine(I_C_OrderLine orderLine);

	<T extends I_M_InOutLine> List<T> retrieveLinesForOrderLine(I_C_OrderLine orderLine, Class<T> clazz);

	/**
	 *
	 * @param ctx
	 * @return query to retrieve all {@link I_M_InOutLine}s which are part of a shipment with doc status <code>Draft</code>, <code>InProgress</code> or <code>WaitingConfirmation</code>.
	 */
	IQueryBuilder<I_M_InOutLine> createUnprocessedShipmentLinesQuery(Properties ctx);

	/**
	 * Returns all (including inactive) M_InOutLines that reference the given <code>packingMaterialLine</code> from their <code>M_PackingMaterial_InOutLine_ID</code> value.
	 *
	 * @param packingMaterialLine
	 * @return
	 */
	IQueryBuilder<I_M_InOutLine> retrieveAllReferencingLinesBuilder(I_M_InOutLine packingMaterialLine);

	/**
	 * Returns all the M_InOutLine IDs of the given inout that have quality issues ( QualityDiscountPercent > 0).
	 * 
	 * @param inOut
	 * @return
	 */
	List<Integer> retrieveLinesWithQualityIssues(I_M_InOut inOut);
}
