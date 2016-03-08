package de.metas.procurement.sync;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import de.metas.procurement.sync.protocol.SyncBPartnersRequest;

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
 *
 * @author metas-dev <dev@metas-fresh.com>
 *
 */
@Path("/sync")
public interface IAgentSync
{
	/**
	 *
	 * @param request
	 * @return
	 */
	@POST
	@Path("bpartners")
	Response syncBPartners(final SyncBPartnersRequest request);
}
