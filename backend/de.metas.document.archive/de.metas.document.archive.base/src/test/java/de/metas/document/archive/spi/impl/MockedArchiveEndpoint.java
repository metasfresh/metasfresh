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

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.MTable;
import org.compiere.util.Env;
import org.compiere.util.Ini;

import de.metas.document.archive.esb.api.ArchiveGetDataRequest;
import de.metas.document.archive.esb.api.ArchiveGetDataResponse;
import de.metas.document.archive.esb.api.ArchiveSetDataRequest;
import de.metas.document.archive.esb.api.ArchiveSetDataResponse;
import de.metas.document.archive.esb.api.IArchiveEndpoint;
import de.metas.document.archive.model.I_AD_Archive;
import de.metas.document.archive.rpl.requesthandler.ArchiveGetDataHandlerConverter;
import de.metas.document.archive.rpl.requesthandler.ArchiveSetDataHandlerConverter;

public class MockedArchiveEndpoint implements IArchiveEndpoint
{
	@Override
	public ArchiveGetDataResponse getArchiveData(final ArchiveGetDataRequest request)
	{
		final Properties ctx = Env.getCtx();
		final boolean clientModeOld = Ini.isSwingClient();

		// NOTE: here we just simulate what replication is doing

		try
		{
			// Make sure we are running in Server mode
			Ini.setClient(false);

			// Lookup for our archive
			final I_AD_Archive archiveRequest = InterfaceWrapperHelper.create(ctx, request.getAdArchiveId(), I_AD_Archive.class, ITrx.TRXNAME_None);
			if (archiveRequest == null)
			{
				throw new AdempiereException("No archive found for AD_Archive_ID=" + request.getAdArchiveId());
			}

			// Now call the replication request handler
			final I_AD_Archive archiveResponse = ArchiveGetDataHandlerConverter.instance.convert(archiveRequest);

			// Return response
			final ArchiveGetDataResponse response = new ArchiveGetDataResponse();
			response.setAdArchiveId(archiveResponse.getAD_Archive_ID());
			response.setData(archiveResponse.getBinaryData());

			return response;
		}
		finally
		{
			// Restore Client/Server mode
			Ini.setClient(clientModeOld);
		}

	}

	@Override
	public ArchiveSetDataResponse setArchiveData(final ArchiveSetDataRequest request)
	{
		final Properties ctx = Env.getCtx();
		final boolean clientModeOld = Ini.isSwingClient();

		// NOTE: here we just simulate what replication is doing

		try
		{
			// Make sure we are running in Server mode
			Ini.setClient(false);

			// Save the received "XML"
			final I_AD_Archive archiveRequest = InterfaceWrapperHelper.create(ctx, I_AD_Archive.class, ITrx.TRXNAME_None);
			archiveRequest.setIsFileSystem(false);
			archiveRequest.setAD_Table_ID(MTable.getTable_ID(org.compiere.model.I_AD_Archive.Table_Name));
			archiveRequest.setRecord_ID(request.getAdArchiveId());
			archiveRequest.setBinaryData(request.getData());
			InterfaceWrapperHelper.save(archiveRequest);

			// Now call the replication request handler
			final I_AD_Archive archiveResponse = ArchiveSetDataHandlerConverter.instance.convert(archiveRequest);

			// Return temporary archive's ID
			final ArchiveSetDataResponse response = new ArchiveSetDataResponse();
			response.setAdArchiveId(archiveResponse.getAD_Archive_ID());

			return response;
		}
		finally
		{
			// Restore Client/Server mode
			Ini.setClient(clientModeOld);
		}
	}
}
