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

import com.google.common.annotations.VisibleForTesting;
import de.metas.document.archive.esb.api.ArchiveGetDataRequest;
import de.metas.document.archive.esb.api.ArchiveGetDataResponse;
import de.metas.document.archive.esb.api.ArchiveSetDataRequest;
import de.metas.document.archive.esb.api.ArchiveSetDataResponse;
import de.metas.document.archive.esb.api.IArchiveEndpoint;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.archive.ArchiveId;
import org.adempiere.archive.spi.impl.AbstractArchiveStorage;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_AD_Archive;
import org.compiere.util.Ini;
import org.compiere.util.Util;

import java.util.Properties;

/**
 * Remote Filesystem archive implementation
 *
 * @author tsa
 */
public class RemoteArchiveStorage extends AbstractArchiveStorage
{
	public static final String SYSCONFIG_ArchiveEndpoint = "de.metas.document.archive.spi.impl.RemoteArchiveStorage.ArchiveEndpoint";
	public static final String DEFAULT_ArchiveEndpoint = RestHttpArchiveEndpoint.class.getName();

	private IArchiveEndpoint endpoint;

	public RemoteArchiveStorage()
	{
		super();
	}

	@VisibleForTesting
	public void setEndpoint(@NonNull final IArchiveEndpoint endpoint)
	{
		this.endpoint = endpoint;
	}

	@Override
	public void init(@NonNull final ClientId clientId)
	{
		if (endpoint == null)
		{
			final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
			final String endpointClassname = sysConfigBL.getValue(SYSCONFIG_ArchiveEndpoint, DEFAULT_ArchiveEndpoint, clientId.getRepoId());
			endpoint = Util.getInstance(IArchiveEndpoint.class, endpointClassname);
		}
	}

	public IArchiveEndpoint getEndpoint()
	{
		return endpoint;
	}

	private void checkContext()
	{
		Check.assume(Ini.isSwingClient(), "RemoveArchive requires client mode");
		Check.assumeNotNull(endpoint, "endpoint is configured");
	}

	@Override
	public byte[] getBinaryData(final I_AD_Archive archive)
	{
		checkContext();
		Check.assumeNotNull(archive, "archive not null");

		final ArchiveId archiveId = ArchiveId.ofRepoIdOrNull(archive.getAD_Archive_ID());
		if (archiveId == null)
		{
			// FIXME: handle the case when archive is not saved
			throw new IllegalStateException("Retrieving data from a not saved archived is not supported: " + archive);
		}

		final ArchiveGetDataResponse response = endpoint.getArchiveData(
				ArchiveGetDataRequest.builder()
						.adArchiveId(archiveId.getRepoId())
						.build()
		);
		return response.getData();
	}

	@Override
	public void setBinaryData(@NonNull final I_AD_Archive archive, final byte[] data)
	{
		checkContext();
		Check.assumeNotNull(archive, "archive not null");

		final ArchiveSetDataRequest request = ArchiveSetDataRequest.builder()
				.adArchiveId(archive.getAD_Archive_ID())
				.data(data)
				.build();

		final ArchiveSetDataResponse response = endpoint.setArchiveData(request);
		final int tempArchiveId = response.getAdArchiveId();

		transferData(archive, tempArchiveId);
	}

	private void transferData(final I_AD_Archive archive, final int fromTempArchiveId)
	{
		Check.assume(fromTempArchiveId > 0, "fromTempArchiveId > 0");

		final Properties ctx = InterfaceWrapperHelper.getCtx(archive);
		final String trxName = InterfaceWrapperHelper.getTrxName(archive);
		final I_AD_Archive tempArchive = InterfaceWrapperHelper.create(ctx, fromTempArchiveId, I_AD_Archive.class, trxName);
		Check.assumeNotNull(tempArchive, "Temporary archive not found for AD_Archive_ID={}", fromTempArchiveId);

		transferData(archive, tempArchive);

		InterfaceWrapperHelper.delete(tempArchive);
	}

	private void transferData(final I_AD_Archive archive, final I_AD_Archive fromTempArchive)
	{
		Check.assume(fromTempArchive.isFileSystem(), "Temporary archive {} shall be saved in filesystem", fromTempArchive);

		archive.setIsFileSystem(fromTempArchive.isFileSystem());
		archive.setBinaryData(fromTempArchive.getBinaryData());
	}
}
