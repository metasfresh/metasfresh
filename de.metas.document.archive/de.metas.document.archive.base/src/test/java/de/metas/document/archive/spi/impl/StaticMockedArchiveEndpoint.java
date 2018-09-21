package de.metas.document.archive.spi.impl;

import de.metas.document.archive.esb.api.ArchiveGetDataRequest;
import de.metas.document.archive.esb.api.ArchiveGetDataResponse;
import de.metas.document.archive.esb.api.ArchiveSetDataRequest;
import de.metas.document.archive.esb.api.ArchiveSetDataResponse;
import de.metas.document.archive.esb.api.IArchiveEndpoint;
import de.metas.util.Check;

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
