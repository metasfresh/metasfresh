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


import java.util.List;
import java.util.Properties;

import org.adempiere.util.ISingletonService;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;

import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.impl.IDocumentLUTUConfigurationManager;
import de.metas.handlingunits.model.I_M_HU;

/**
 * Provides specific business logic for interconnection between manufacturing order and handling units module.
 *
 * @author tsa
 *
 */
public interface IHUPPOrderBL extends ISingletonService
{
	/**
	 * Generates receipt HUs based on receipt cost collector Qty.
	 *
	 * Generated HUs will be automatically assigned to given cost collector.
	 *
	 * @param cc finished goods receipt cost collector
	 * @return generated HUs
	 */
	List<I_M_HU> createReceiptHandlingUnits(I_PP_Cost_Collector cc);

	IDocumentLUTUConfigurationManager createReceiptLUTUConfigurationManager(I_PP_Order ppOrder);

	IDocumentLUTUConfigurationManager createReceiptLUTUConfigurationManager(I_PP_Order_BOMLine ppOrderBOMLine);

	/**
	 * @param ppOrder
	 * @return the created allocation source, based on ppOrder
	 */
	IAllocationSource createAllocationSourceForPPOrder(de.metas.handlingunits.model.I_PP_Order ppOrder);

	IHUPPOrderIssueProducer createIssueProducer(Properties ctx);
}
