package de.metas.tourplanning.api;

/*
 * #%L
 * de.metas.swat.base
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


import org.adempiere.model.IContextAware;
import org.adempiere.util.ISingletonService;

import de.metas.tourplanning.model.I_M_DeliveryDay;
import de.metas.tourplanning.model.I_M_Tour_Instance;

public interface ITourInstanceBL extends ISingletonService
{
	boolean isGenericTourInstance(I_M_Tour_Instance tourInstance);

	/**
	 * Checks {@link I_M_DeliveryDay#getM_Tour_Instance_ID()} assignment and if needed creates one.
	 * 
	 * @param deliveryDay
	 */
	void updateTourInstanceAssigment(I_M_DeliveryDay deliveryDay);

	void assignToTourInstance(I_M_DeliveryDay deliveryDay, I_M_Tour_Instance tourInstance);

	I_M_Tour_Instance createTourInstanceDraft(IContextAware context, I_M_DeliveryDay deliveryDay);

	void process(I_M_Tour_Instance tourInstance);

	void unprocess(I_M_Tour_Instance tourInstance);

}
