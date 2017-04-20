package de.metas.handlingunits.attribute;

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

import org.adempiere.util.ISingletonService;
import org.eevolution.model.I_PP_Cost_Collector;

import de.metas.handlingunits.model.I_M_HU_Attribute;
import de.metas.handlingunits.model.I_PP_Order_ProductAttribute;


public interface IPPOrderProductAttributeDAO extends ISingletonService
{

	
	/**
	 * Add PP_OrderProductAttributes for the cost collector, based on the values from the huAttributes
	 * @param costCollector
	 * @param attributes
	 */
	void addPPOrderProductAttributes(I_PP_Cost_Collector costCollector, List<I_M_HU_Attribute> huAttributes);

	/**
	 * @param ppOrderId
	 * @return the PP_Order_ProductAttribute entries for the given ppOrder if exist, EmptyList otherwise
	 */
	List<I_PP_Order_ProductAttribute> retrieveProductAttributesForPPOrder(int ppOrderId);

	/**
	 * Deactivate all PP_Order_ProductAttributes for the given cost collector
	 * 
	 * @param costCollectorId
	 */
	void deactivateForCostCollector(int costCollectorId);

}
