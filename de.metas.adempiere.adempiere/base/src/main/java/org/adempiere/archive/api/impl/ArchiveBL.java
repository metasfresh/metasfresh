package org.adempiere.archive.api.impl;

/*
 * #%L
 * ADempiere ERP - Base
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


import java.io.InputStream;
import java.util.Properties;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.archive.api.IArchiveStorageFactory;
import org.adempiere.archive.spi.IArchiveStorage;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pdf.Document;
import org.adempiere.service.IClientDAO;
import org.adempiere.util.Services;
import org.compiere.model.IClientOrgAware;
import org.compiere.model.I_AD_Archive;
import org.compiere.model.I_AD_Client;
import org.compiere.model.PrintInfo;
import org.compiere.model.X_AD_Client;
import org.compiere.print.layout.LayoutEngine;
import org.compiere.process.ProcessInfo;
import org.compiere.util.Env;

public class ArchiveBL implements IArchiveBL
{
	@Override
	public int archive(final byte[] data, final PrintInfo printInfo)
	{
		final boolean force = false;
		final I_AD_Archive archive = archive(data, printInfo, force);
		return archive == null ? -1 : archive.getAD_Archive_ID();
	}

	@Override
	public I_AD_Archive archive(final byte[] data, final PrintInfo printInfo, final boolean force)
	{
		return archive(data, printInfo, force, ITrx.TRXNAME_None);
	}

	@Override
	public I_AD_Archive archive(final byte[] data, final PrintInfo printInfo, final boolean force, final String trxName)
	{
		final Properties ctx = Env.getCtx();
		if (force || isToArchive(ctx, printInfo))
		{
			return archive0(ctx, data, printInfo, trxName);
		}

		return null;
	}

	@Override
	public I_AD_Archive archive(final LayoutEngine layout, final PrintInfo printInfo, final boolean force, final String trxName)
	{
		final Properties ctx = layout.getCtx();
		if (force || isToArchive(ctx, printInfo))
		{
			final byte[] data = Document.getPDFAsArray(layout.getPageable(false));	// No Copy
			if (data == null)
			{
				return null;
			}

			return archive0(ctx, data, printInfo, trxName);
		}

		return null;
	}

	private I_AD_Archive archive0(final Properties ctx, final byte[] data, final PrintInfo info, final String trxName)
	{
		// t.schoemeberg@metas.de, 03787: using the client/org of the archived PO, if possible
		final Properties ctxToUse = createContext(ctx, info.getAD_Table_ID(), info.getRecord_ID(), trxName);

		final IArchiveStorage storage = Services.get(IArchiveStorageFactory.class).getArchiveStorage(ctxToUse);
		final I_AD_Archive archive = storage.newArchive(ctxToUse, trxName);
		archive.setName(info.getName());
		archive.setIsReport(info.isReport());
		//
		archive.setAD_Process_ID(info.getAD_Process_ID());
		archive.setAD_Table_ID(info.getAD_Table_ID());
		archive.setRecord_ID(info.getRecord_ID());
		archive.setC_BPartner_ID(info.getC_BPartner_ID());
		storage.setBinaryData(archive, data);

		InterfaceWrapperHelper.save(archive);
		return archive;
	}

	private final Properties createContext(final Properties ctx, int adTableId, int recordId, final String trxName)
	{
		if (adTableId <= 0)
		{
			return ctx;
		}
		if (recordId <= 0)
		{
			return ctx;
		}

		final String tableName = Services.get(IADTableDAO.class).retrieveTableName(adTableId);
		final IClientOrgAware record = InterfaceWrapperHelper.create(ctx, tableName, recordId, IClientOrgAware.class, trxName);
		if (record == null)
		{
			// attached record was not found. return the initial context (an error was already logged by loader)
			return ctx;
		}
		final Properties ctxToUse = Env.deriveCtx(ctx);
		Env.setContext(ctxToUse, "#AD_Client_ID", record.getAD_Client_ID());
		Env.setContext(ctxToUse, "#AD_Org_ID", record.getAD_Org_ID());
		// setClientOrg(archivedPO);
		return ctxToUse;
	}

	@Override
	public boolean isToArchive(final PrintInfo printInfo)
	{
		final Properties ctx = Env.getCtx();
		return isToArchive(ctx, printInfo);
	}

	public boolean isToArchive(final Properties ctx, final PrintInfo printInfo)
	{
		final String autoArchive = getAutoArchiveType(ctx);
		
		// Nothing to Archive
		if (autoArchive.equals(X_AD_Client.AUTOARCHIVE_None))
		{
			return false;
		}
		// Archive External only
		if (autoArchive.equals(X_AD_Client.AUTOARCHIVE_ExternalDocuments))
		{
			if (printInfo == null // avoid NPE when exporting to PDF from the print preview window 
					|| printInfo.isReport())
			{
				return false;
			}
		}
		// Archive Documents only
		if (autoArchive.equals(X_AD_Client.AUTOARCHIVE_Documents))
		{
			if (printInfo == null // avoid NPE when exporting to PDF from the print preview window 
					|| printInfo.isReport())
			{
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean isToArchive(final ProcessInfo processInfo)
	{
		final Properties ctx = Env.getCtx();
		return isToArchive(ctx, processInfo);
	}

	public boolean isToArchive(final Properties ctx, final ProcessInfo processInfo)
	{
		final String autoArchive = getAutoArchiveType(ctx);
		
		// Nothing to Archive
		if (autoArchive.equals(X_AD_Client.AUTOARCHIVE_None))
			return false;
		// Archive External only
		if (autoArchive.equals(X_AD_Client.AUTOARCHIVE_ExternalDocuments))
		{
			if (processInfo.isReportingProcess())
				return false;
		}
		// Archive Documents only
		if (autoArchive.equals(X_AD_Client.AUTOARCHIVE_Documents))
		{
			if (processInfo.isReportingProcess())
				return false;
		}
		return true;
	}

	private String getAutoArchiveType(final Properties ctx)
	{
		final I_AD_Client client = Services.get(IClientDAO.class).retriveClient(ctx, Env.getAD_Client_ID(ctx));
		String aaClient = client.getAutoArchive();
		String aaRole = null; // role.getAutoArchive(); // TODO
		String aa = aaClient;
		if (aa == null)
			aa = X_AD_Client.AUTOARCHIVE_None;
		if (aaRole != null)
		{
			if (aaRole.equals(X_AD_Client.AUTOARCHIVE_AllReportsDocuments))
				aa = aaRole;
			else if (aaRole.equals(X_AD_Client.AUTOARCHIVE_Documents)
					&& !aaClient
							.equals(X_AD_Client.AUTOARCHIVE_AllReportsDocuments))
				aa = aaRole;
		}
		return aa;
	}

	@Override
	public String getContentType(final I_AD_Archive archive)
	{
		// NOTE: at this moment we are storing only PDFs
		return "application/pdf";
	}

	@Override
	public byte[] getBinaryData(final I_AD_Archive archive)
	{
		return Services.get(IArchiveStorageFactory.class).getArchiveStorage(archive).getBinaryData(archive);
	}

	@Override
	public void setBinaryData(final I_AD_Archive archive, final byte[] data)
	{
		Services.get(IArchiveStorageFactory.class).getArchiveStorage(archive).setBinaryData(archive, data);
	}

	@Override
	public InputStream getBinaryDataAsStream(final I_AD_Archive archive)
	{
		return Services.get(IArchiveStorageFactory.class).getArchiveStorage(archive).getBinaryDataAsStream(archive);
	}
}
