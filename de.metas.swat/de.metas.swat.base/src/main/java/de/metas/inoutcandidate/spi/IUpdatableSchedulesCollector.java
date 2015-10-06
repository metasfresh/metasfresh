package de.metas.inoutcandidate.spi;

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


import java.util.List;
import java.util.Properties;

import org.adempiere.inout.util.CachedObjects;
import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;

import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.api.OlAndSched;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;

/**
 * This collector is currently disabled because,
 * <ul>
 * <li>it is a major performance ball-breaker
 * <li>it deals with portage free amounts (delivery values below which a delivery is postoned) and non-items (to make sure they are delivered alongside with there fellow items).
 * </ul>
 * both is totally irrelevant for our current customer(s). Should we once again have a customer who needs one of these features, then don't reenable this collector, but implement a performant way. <br>
 * Example/Brainfart for a better implementation: decorate a {@link de.metas.storage.IStorageSegment} to specify this additional criteria an let the {@link IShipmentSchedulePA} invalidate them
 * directly. But don't go back to loading and iterating vast numbers or records the way it is done here. It doesn't scale at all.
 * 
 * @author ts
 *
 */
public interface IUpdatableSchedulesCollector extends ISingletonService {

	/**
	 * <b>Do nothing, just returns <code>seed</code>.</b>
	 * <strike>For the given list of {@link OlAndSched} instances that need updating
	 * this method adds additional lines that also need updating.</strike>
	 * 
	 * @param seed
	 *            usually a list whose {@link I_M_ShipmentSchedule}s all have
	 *            <code>IsValid='N'</code>.
	 * @param cachedObjects
	 *            all {@link I_C_Order}s, {@link I_C_BPartner}s and the like
	 *            will be used from this cache. If one isn't there, it will be
	 *            loaded and added.
	 * @param trxName
	 * @return
	 */
	List<OlAndSched> collectUpdatableLines(Properties ctx,
			List<OlAndSched> seed, CachedObjects cachedObjects, String trxName);

}
