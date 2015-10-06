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


import org.adempiere.util.ISingletonService;
import org.eevolution.model.I_PP_Order;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_PP_Cost_Collector;



public interface IPPOrderProductAttributeBL extends ISingletonService
{

	/**
	 * Updates the HUAttributes of the given HU based on the PP_Order_ProductAttributes of the PPOrder
	 * 
	 * @param ppOrder
	 * @param hu
	 */
	void updateHUAttributes(I_PP_Order ppOrder, I_M_HU hu);

	/**
	 * Create new PP_Order_ProductAttribute entries for the given cost collector
	 * 
	 * @param costCollector
	 */
	void addPPOrderProductAttributes(I_PP_Cost_Collector costCollector);

}
