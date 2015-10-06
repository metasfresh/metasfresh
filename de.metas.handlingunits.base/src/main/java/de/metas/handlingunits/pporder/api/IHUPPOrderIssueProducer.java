package de.metas.handlingunits.pporder.api;

/*
 * #%L
 * de.metas.handlingunits.base
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


import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_PP_Cost_Collector;

/**
 * Issues given HUs to configured Order BOM Lines.
 *
 * @author tsa
 *
 */
public interface IHUPPOrderIssueProducer
{
	/**
	 * Sets the transaction name that shall be used.
	 *
	 * By default, {@link ITrx#TRXNAME_None} is used.
	 *
	 * @param trxName
	 */
	IHUPPOrderIssueProducer setTrxName(String trxName);

	/**
	 * Issue given <code>HUs</code> to configured {@link I_PP_Order_BOMLine}s.
	 *
	 * @param hus
	 * @return generated manufacturing order issue cost collectors
	 */
	List<I_PP_Cost_Collector> createIssues(final Collection<I_M_HU> hus);

	/**
	 * Convenient way of calling {@link #createIssues(Collection)}.
	 *
	 * @param hu
	 * @return generated manufacturing order issue cost collectors
	 */
	List<I_PP_Cost_Collector> createIssues(I_M_HU hu);

	/**
	 * Sets movement date to be used in generated underlying {@link I_PP_Cost_Collector}s.
	 *
	 * @param movementDate
	 */
	IHUPPOrderIssueProducer setMovementDate(final Date movementDate);

	/**
	 * Sets manufacturing order BOM Lines which needs to be considered when issuing.
	 *
	 * @param targetOrderBOMLines
	 */
	IHUPPOrderIssueProducer setTargetOrderBOMLines(final List<I_PP_Order_BOMLine> targetOrderBOMLines);

	/**
	 * Consider all BOM lines of given manufacturing order, when issuing.
	 *
	 * @param ppOrder
	 */
	IHUPPOrderIssueProducer setTargetOrderBOMLines(I_PP_Order ppOrder);

	/**
	 * Convenient way of calling {@link #setTargetOrderBOMLines(List)} with one bom line.
	 *
	 * @param targetOrderBOMLine
	 */
	IHUPPOrderIssueProducer setTargetOrderBOMLine(I_PP_Order_BOMLine targetOrderBOMLine);
}
