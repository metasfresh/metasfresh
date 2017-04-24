package de.metas.handlingunits.attribute;

import java.util.Collection;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.adempiere.util.ISingletonService;

import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_PP_Cost_Collector;
import de.metas.handlingunits.model.I_PP_Order_ProductAttribute;

public interface IPPOrderProductAttributeBL extends ISingletonService
{

	/**
	 * Transfer PP_Order's attributes (see {@link I_PP_Order_ProductAttribute}) to
	 * <ul>
	 * <li>given HUs
	 * <li>all already received HUs
	 * </ul>
	 * 
	 * @param ppOrder
	 * @param hus
	 */
	void updateHUAttributes(Collection<I_M_HU> hus, final int fromPPOrderId);
	
	default void updateHUAttributes(final I_M_HU hu, final int fromPPOrderId)
	{
		updateHUAttributes(ImmutableSet.of(hu), fromPPOrderId);
	}

	/**
	 * Create new PP_Order_ProductAttribute entries for the given cost collector
	 * 
	 * @param costCollector
	 */
	void addPPOrderProductAttributes(I_PP_Cost_Collector costCollector);


}
