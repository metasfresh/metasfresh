package de.metas.procurement.sync;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import de.metas.procurement.sync.protocol.SyncBPartner;
import de.metas.procurement.sync.protocol.SyncProduct;
import de.metas.procurement.sync.protocol.SyncProductSuppliesRequest;
import de.metas.procurement.sync.protocol.SyncWeeklySupplyRequest;

/*
 * #%L
 * de.metas.procurement.sync-api
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
 * This is implemented in metasfresh (server) and called from the procurementUI (agent).
 * Tobi nedds to implement this and
 *
 * @author metas-dev <dev@metas-fresh.com>
 *
 */
@Path("/sync")
public interface IServerSync
{
	/**
	 * Get all partners that have a contract.
	 *
	 * @return
	 */
	@GET
	@Path("bpartners")
	public List<SyncBPartner> getAllBPartners();

	/**
	 * Get all products, with our without contract.
	 *
	 * @return
	 */
	@GET
	@Path("products")
	public List<SyncProduct> getAllProducts();

	/**
	 * Report a product supply to metasfresh. Create <code>PMM_QtyReport_Event</code>.
	 *
	 * @param request
	 * @return
	 */
	@POST
	@Path("productSupplies")
	public Response reportProductSupplies(SyncProductSuppliesRequest request);

	/**
	 * Report the supplier's forecast.
	 *
	 * @param request
	 * @return
	 */
	@POST
	@Path("weeklySupply")
	public Response reportWeekSupply(SyncWeeklySupplyRequest request);
}
