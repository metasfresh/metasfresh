package de.metas.procurement.sync;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.apache.cxf.jaxrs.ext.Oneway;

import de.metas.procurement.sync.protocol.SyncBPartner;
import de.metas.procurement.sync.protocol.SyncProduct;
import de.metas.procurement.sync.protocol.SyncProductSuppliesRequest;
import de.metas.procurement.sync.protocol.SyncRfQChangeRequest;
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
 * <p>
 * Note that currently we don't have to use the Consumes and Produces annotations, because we specify those types in the client.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Path("/serversync")
public interface IServerSync
{
	/**
	 * Get all partners that have at least one contract, with all their contracts and products. Also get all products that do not belong to a particular contract, but can be offered by the vendor none
	 * the less.
	 *
	 * @return
	 */
	@GET
	@Path("bpartners")
	public List<SyncBPartner> getAllBPartners();

	@GET
	@Path("allProducts")
	public List<SyncProduct> getAllProducts();

	@GET
	@Path("infoMessage")
	public String getInfoMessage();

	/**
	 * Report a product supply to metasfresh. Create <code>PMM_QtyReport_Event</code>.
	 *
	 * @param request
	 * @return
	 */
	@POST
	@Path("productSupplies")
	@Oneway
	public void reportProductSupplies(SyncProductSuppliesRequest request);

	/**
	 * Report the supplier's forecast.
	 *
	 * @param request
	 * @return
	 */
	@POST
	@Path("weeklySupply")
	@Oneway
	public void reportWeekSupply(SyncWeeklySupplyRequest request);
	
	@POST
	@Path("rfqChanges")
	@Oneway
	public void reportRfQChanges(SyncRfQChangeRequest request);
}
