package de.metas.document.archive.spi.impl;

/*
 * #%L
 * de.metas.document.archive.base
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


import org.adempiere.util.Check;

import de.metas.document.archive.esb.api.ArchiveGetDataRequest;
import de.metas.document.archive.esb.api.ArchiveGetDataResponse;
import de.metas.document.archive.esb.api.ArchiveSetDataRequest;
import de.metas.document.archive.esb.api.ArchiveSetDataResponse;
import de.metas.document.archive.esb.api.IArchiveEndpoint;

public class StaticMockedArchiveEndpoint implements IArchiveEndpoint
{
	private static IArchiveEndpoint archiveEndpoint;

	@Override
	public ArchiveGetDataResponse getArchiveData(final ArchiveGetDataRequest request)
	{
		return StaticMockedArchiveEndpoint.getArchiveEndpoint().getArchiveData(request);
	}

	@Override
	public ArchiveSetDataResponse setArchiveData(final ArchiveSetDataRequest request)
	{
		return StaticMockedArchiveEndpoint.getArchiveEndpoint().setArchiveData(request);
	}

	public static IArchiveEndpoint getArchiveEndpoint()
	{
		Check.assumeNotNull(StaticMockedArchiveEndpoint.archiveEndpoint, "endpoint shall be set");
		return StaticMockedArchiveEndpoint.archiveEndpoint;
	}

	public static void setArchiveEndpoint(final IArchiveEndpoint endpoint)
	{
		Check.assumeNotNull(endpoint, "endpoint not null");
		StaticMockedArchiveEndpoint.archiveEndpoint = endpoint;
	}
}
