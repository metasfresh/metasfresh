package de.metas.procurement.base.balance;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.adempiere.util.ISingletonService;
import org.apache.cxf.jaxrs.ext.Oneway;

import de.metas.procurement.base.model.I_PMM_Balance;

/*
 * #%L
 * de.metas.procurement.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * Service used to process PMM_Balance various change events and update the {@link I_PMM_Balance} records.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Path("/de.metas.procurement/balance")
public interface IPMMBalanceChangeEventProcessor extends ISingletonService
{
	/**
	 * Submit event to be processed
	 * 
	 * @param event
	 */
	@POST
	@Path("event")
	@Oneway
	void addEvent(final PMMBalanceChangeEvent event);
}
