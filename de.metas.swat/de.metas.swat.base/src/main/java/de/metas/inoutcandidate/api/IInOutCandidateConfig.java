package de.metas.inoutcandidate.api;

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


import org.adempiere.util.ISingletonService;



/**
 * Statefull singleton that contains inout candidate configuration
 * @author ts
 *
 */
public interface IInOutCandidateConfig extends ISingletonService
{
	/**
	 * This method allows it to override the default setting for the value returned by
	 * {@link #isSupportForSchedsWithoutOrderLine()}.
	 * <p>
	 * Background: for some projects, we need de.metas.contracts to act as if scheds without order lines were supported. At the
	 * same time, the project registers an {@link IInOutCandHandlerListener} that makes sure no actual
	 * {@link I_M_ShipmentSchedule}s are created at all.
	 * <p>
	 * This method assumes that it is called only once (during startup).
	 * 
	 * @param support
	 */
	void setSupportForSchedsWithoutOrderLines(boolean support);

	/**
	 * Tells every called if the module supports {@link I_M_ShipmentSchedule}s that don't reference a
	 * <code>C_OrderLine_ID</code>.
	 * <p>
	 * Background: currently this is not the case, and de.metas.contracts needs to take account of this.
	 * 
	 * @return
	 */
	boolean isSupportForSchedsWithoutOrderLine();
}
