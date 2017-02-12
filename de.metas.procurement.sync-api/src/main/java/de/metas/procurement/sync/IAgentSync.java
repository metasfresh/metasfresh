package de.metas.procurement.sync;

import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.apache.cxf.jaxrs.ext.Oneway;

import de.metas.procurement.sync.protocol.SyncBPartnersRequest;
import de.metas.procurement.sync.protocol.SyncConfirmation;
import de.metas.procurement.sync.protocol.SyncInfoMessageRequest;
import de.metas.procurement.sync.protocol.SyncProductsRequest;
import de.metas.procurement.sync.protocol.SyncRfQ;

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
 * This is implemented in the procurementUI (agent) and called from metasfresh (server).
 * <p>
 * Note that currently we don't have to use the Consumes and Produces annotations, because we specify those types in the client.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Path("/agentsync")
public interface IAgentSync
{
	/**
	 *
	 * @param request
	 * @return
	 */
	@POST
	@Path("bpartners")
	@Oneway
	void syncBPartners(final SyncBPartnersRequest request);

	@POST
	@Path("products")
	@Oneway
	void syncProducts(final SyncProductsRequest request);

	@POST
	@Path("infoMessage")
	@Oneway
	void syncInfoMessage(final SyncInfoMessageRequest request);

	@POST
	@Path("confirmations")
	@Oneway
	void confirm(final List<SyncConfirmation> syncConfirmations);

	@POST
	@Path("rfqs")
	@Oneway
	void syncRfQs(List<SyncRfQ> syncRfqs);
	
	@POST
	@Path("closeRfqs")
	@Oneway
	void closeRfQs(final List<SyncRfQCloseEvent> syncRfQCloseEvents);
}
