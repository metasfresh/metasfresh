package de.metas.document.archive.esb.camel.cxf.jaxrs;

/*
 * #%L
 * de.metas.document.archive.esb.camel
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


import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.metas.document.archive.esb.api.ArchiveGetDataRequest;
import de.metas.document.archive.esb.api.ArchiveGetDataResponse;
import de.metas.document.archive.esb.api.ArchiveSetDataRequest;
import de.metas.document.archive.esb.api.ArchiveSetDataResponse;
import de.metas.document.archive.esb.api.IArchiveEndpoint;

@Path(IArchiveEndpoint.PATH_Service)
public class RESTHttpArchiveEndpoint implements IArchiveEndpoint
{
	/**
	 * Retrieves archive data.
	 * 
	 * @param request
	 * @return
	 */
	@POST
	@Path(IArchiveEndpoint.PATH_GetArchiveData)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public ArchiveGetDataResponse getArchiveData(final ArchiveGetDataRequest request)
	{
		return null;
	}

	/**
	 * Creates archive data, sends it to ADempiere, and retrieves feedback.
	 * 
	 * @param request
	 * @return
	 */
	@POST
	@Path(IArchiveEndpoint.PATH_SetArchiveData)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public ArchiveSetDataResponse setArchiveData(final ArchiveSetDataRequest request)
	{
		return null;
	}
}
