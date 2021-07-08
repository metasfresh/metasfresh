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


import java.util.Properties;

import lombok.NonNull;
import org.adempiere.archive.spi.impl.AbstractArchiveStorage;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_AD_Archive;
import org.compiere.util.Ini;
import org.compiere.util.Util;

import de.metas.document.archive.esb.api.ArchiveGetDataRequest;
import de.metas.document.archive.esb.api.ArchiveGetDataResponse;
import de.metas.document.archive.esb.api.ArchiveSetDataRequest;
import de.metas.document.archive.esb.api.ArchiveSetDataResponse;
import de.metas.document.archive.esb.api.IArchiveEndpoint;
import de.metas.util.Check;
import de.metas.util.Services;

/**
 * Remote Filesystem archive implementation
 * 
 * @author tsa
 * 
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

	public void setEndpoint(final IArchiveEndpoint endpoint)
	{
		Check.assumeNotNull(endpoint, "endpoint not null");
		this.endpoint = endpoint;
	}

	@Override
	public void init(@NonNull final ClientId clientId)
	{
		if (endpoint == null)
		{
			final String endpointClassname = Services.get(ISysConfigBL.class).getValue(
					RemoteArchiveStorage.SYSCONFIG_ArchiveEndpoint,
					RemoteArchiveStorage.DEFAULT_ArchiveEndpoint,
					clientId.getRepoId()
					);
			endpoint = Util.getInstance(IArchiveEndpoint.class, endpointClassname);
		}
	}

	public IArchiveEndpoint getEndpoint()
	{
		return endpoint;
	}

	private final void checkContext()
	{
		Check.assume(Ini.isSwingClient(), "RemoveArchive requires client mode");
		Check.assumeNotNull(endpoint, "endpoint is configured");
	}

	@Override
	public byte[] getBinaryData(final I_AD_Archive archive)
	{
		checkContext();
		Check.assumeNotNull(archive, "archive not null");

		final int archiveId = archive.getAD_Archive_ID();
		if (archiveId <= 0)
		{
			// FIXME: handle the case when adArchiveId <= 0
			throw new IllegalStateException("Retrieving data from a not saved archived is not supported: " + archive);
		}

		final ArchiveGetDataRequest request = new ArchiveGetDataRequest();
		request.setAdArchiveId(archiveId);
		final ArchiveGetDataResponse response = endpoint.getArchiveData(request);
		return response.getData();
	}

	@Override
	public void setBinaryData(final I_AD_Archive archive, final byte[] data)
	{
		checkContext();
		Check.assumeNotNull(archive, "archive not null");

		final int archiveId = archive.getAD_Archive_ID();
		final ArchiveSetDataRequest request = new ArchiveSetDataRequest();
		request.setAdArchiveId(archiveId);
		request.setData(data);

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
