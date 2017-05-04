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
import java.util.List;

import org.adempiere.util.ISingletonService;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_PP_Cost_Collector;

public interface IHUPPCostCollectorBL extends ISingletonService
{
	/**
	 * Assign given HUs to specified cost collector.
	 *
	 * @param cc
	 * @param husToAssign
	 */
	void assignHUs(org.eevolution.model.I_PP_Cost_Collector cc, Collection<I_M_HU> husToAssign);

	/**
	 * Asserts there are no HU assignments to given cost collector
	 * 
	 * @param cc
	 */
	void assertNoHUAssignments(org.eevolution.model.I_PP_Cost_Collector cc);

	/**
	 * @param cc
	 * @return top level HUs which were assigned to given cost collector
	 */
	List<I_M_HU> getTopLevelHUs(org.eevolution.model.I_PP_Cost_Collector cc);

	/**
	 * Restores the assigned HUs from {@link I_PP_Cost_Collector#getSnapshot_UUID()}.
	 * 
	 * @param costCollector
	 */
	void restoreTopLevelHUs(I_PP_Cost_Collector costCollector);
}
