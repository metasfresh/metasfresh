package org.adempiere.archive.api.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerAware;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.process.PInstanceId;
import de.metas.process.ProcessInfo;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.archive.api.ArchiveInfo;
import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.archive.api.IArchiveDAO;
import org.adempiere.archive.api.IArchiveStorageFactory;
import org.adempiere.archive.spi.IArchiveStorage;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.pdf.Document;
import org.adempiere.service.IClientDAO;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.IClientOrgAware;
import org.compiere.model.I_AD_Archive;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_Process;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.X_AD_Client;
import org.compiere.print.layout.LayoutEngine;
import org.compiere.util.Env;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class ArchiveBL implements IArchiveBL
{
	@Override
	public I_AD_Archive archive(final byte[] data,
			final ArchiveInfo archiveInfo,
			final boolean force,
			final String trxName)
	{
		final boolean save = true;
		return archive(data, archiveInfo, force, save, trxName);
	}

	@Override
	public I_AD_Archive archive(final byte[] data,
			final ArchiveInfo archiveInfo,
			final boolean force,
			final boolean save,
			final String trxName)
	{
		final Properties ctx = Env.getCtx();
		if (force || isToArchive(ctx, archiveInfo))
		{
			return archive0(ctx, data, archiveInfo, save, trxName);
		}

		return null;
	}

	@Override
	public I_AD_Archive archive(final LayoutEngine layout,
			final ArchiveInfo archiveInfo,
			final boolean force,
			final String trxName)
	{
		final Properties ctx = layout.getCtx();
		if (force || isToArchive(ctx, archiveInfo))
		{
			final byte[] data = Document.getPDFAsArray(layout.getPageable(false));	// No Copy
			if (data == null)
			{
				return null;
			}

			return archive0(ctx, data, archiveInfo, true, trxName);
		}

		return null;
	}

	private I_AD_Archive archive0(final Properties ctx,
			final byte[] data,
			final ArchiveInfo archiveInfo,
			final boolean save,
			final String trxName)
	{
		// t.schoemeberg@metas.de, 03787: using the client/org of the archived PO, if possible
		final Properties ctxToUse = createContext(ctx, archiveInfo, trxName);

		final IArchiveStorage storage = Services.get(IArchiveStorageFactory.class).getArchiveStorage(ctxToUse);
		final I_AD_Archive archive = storage.newArchive(ctxToUse, trxName);

		// FRESH-218: extract and set the language to the archive
		final String language = getLanguageFromReport(ctxToUse, archiveInfo, trxName);
		archive.setAD_Language(language);

		archive.setName(archiveInfo.getName());
		archive.setIsReport(archiveInfo.isReport());
		//
		archive.setAD_Process_ID(AdProcessId.toRepoId(archiveInfo.getProcessId()));

		final TableRecordReference recordRef = archiveInfo.getRecordRef();
		archive.setAD_Table_ID(recordRef != null ? recordRef.getAD_Table_ID() : -1);
		archive.setRecord_ID(recordRef != null ? recordRef.getRecord_ID() : -1);

		archive.setC_BPartner_ID(BPartnerId.toRepoId(archiveInfo.getBpartnerId()));
		storage.setBinaryData(archive, data);

		//FRESH-349: Set ad_pinstance

		archive.setAD_PInstance_ID(PInstanceId.toRepoId(archiveInfo.getPInstanceId()));

		if (save)
		{
			InterfaceWrapperHelper.save(archive);
		}
		return archive;
	}

	/**
	 * Return the BPartner's language, in case the archiveInfo has a jasper report set and this jasper report is a process that uses the BPartner language. If it was not found, fall back to the language set
	 * in the given context
	 *
	 * Task https://metasfresh.atlassian.net/browse/FRESH-218
	 */
	private String getLanguageFromReport(Properties ctx, ArchiveInfo archiveInfo, String trxName)
	{
		// the language from the given context. In case there will be no other language to fit the logic, this is the value to be returned.
		final String initialLanguage = Env.getAD_Language(ctx);

		final I_AD_Process process = Services.get(IADProcessDAO.class).getById(archiveInfo.getProcessId());

		// make sure there is a process set in the PrintInfo
		if (process == null)
		{
			return initialLanguage;
		}

		// in case the found process does not have the isUseBPartnerLanguage on true, there is no reason to search more
		final boolean isUseBPartnerLanguage = process.isUseBPartnerLanguage();

		if (!isUseBPartnerLanguage)
		{
			return initialLanguage;
		}

		final TableRecordReference recordRef = archiveInfo.getRecordRef();
		if (recordRef == null)
		{
			return initialLanguage;
		}

		// make sure the record linked with the PrintInfo is bpartner aware (has a C_BPartner_ID column)
		final IBPartnerAware bpRecord = recordRef.getModel(new PlainContextAware(ctx, trxName), IBPartnerAware.class);

		if (bpRecord == null)
		{
			// Act like before
			return initialLanguage;
		}

		// if the record really is BPartner aware, we should be able to load the bpartner
		final I_C_BPartner partner = bpRecord.getC_BPartner();

		// this shall not happen
		if (partner == null)
		{
			// Act like before
			return initialLanguage;
		}

		// return the language of the linked bpartner
		return partner.getAD_Language();

	}

	private final Properties createContext(final Properties ctx, final ArchiveInfo archiveInfo, final String trxName)
	{
		final TableRecordReference recordRef = archiveInfo.getRecordRef();
		if(recordRef == null)
		{
			return ctx;
		}

		final IClientOrgAware record = recordRef.getModel(new PlainContextAware(ctx, trxName), IClientOrgAware.class);
		if (record == null)
		{
			// attached record was not found. return the initial context (an error was already logged by loader)
			return ctx;
		}
		final Properties ctxToUse = Env.deriveCtx(ctx);
		Env.setContext(ctxToUse, Env.CTXNAME_AD_Client_ID, record.getAD_Client_ID());
		Env.setContext(ctxToUse, Env.CTXNAME_AD_Org_ID, record.getAD_Org_ID());
		// setClientOrg(archivedPO);

		return ctxToUse;

	}

	@Override
	public boolean isToArchive(final ArchiveInfo archiveInfo)
	{
		final Properties ctx = Env.getCtx();
		return isToArchive(ctx, archiveInfo);
	}

	private boolean isToArchive(final Properties ctx, final ArchiveInfo archiveInfo)
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
			if (archiveInfo == null // avoid NPE when exporting to PDF from the print preview window
					|| archiveInfo.isReport())
			{
				return false;
			}
		}
		// Archive Documents only
		if (autoArchive.equals(X_AD_Client.AUTOARCHIVE_Documents))
		{
			if (archiveInfo == null // avoid NPE when exporting to PDF from the print preview window
					|| archiveInfo.isReport())
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
		{
			return false;
		}
		// Archive External only
		if (autoArchive.equals(X_AD_Client.AUTOARCHIVE_ExternalDocuments))
		{
			if (processInfo.isReportingProcess())
			{
				return false;
			}
		}
		// Archive Documents only
		if (autoArchive.equals(X_AD_Client.AUTOARCHIVE_Documents))
		{
			if (processInfo.isReportingProcess())
			{
				return false;
			}
		}
		return true;
	}

	private String getAutoArchiveType(final Properties ctx)
	{
		final I_AD_Client client = Services.get(IClientDAO.class).retriveClient(ctx, Env.getAD_Client_ID(ctx));
		final String aaClient = client.getAutoArchive();
		final String aaRole = null; // role.getAutoArchive(); // TODO
		String aa = aaClient;
		if (aa == null)
		{
			aa = X_AD_Client.AUTOARCHIVE_None;
		}
		if (aaRole != null)
		{
			if (aaRole.equals(X_AD_Client.AUTOARCHIVE_AllReportsDocuments))
			{
				aa = aaRole;
			}
			else if (aaRole.equals(X_AD_Client.AUTOARCHIVE_Documents)
					&& !aaClient
							.equals(X_AD_Client.AUTOARCHIVE_AllReportsDocuments))
			{
				aa = aaRole;
			}
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

	@Override
	public Optional<I_AD_Archive> getLastArchive(@NonNull final TableRecordReference reference)
	{
		final IArchiveDAO archiveDAO = Services.get(IArchiveDAO.class);
		final List<I_AD_Archive> lastArchives = archiveDAO.retrieveLastArchives(Env.getCtx(), reference, QueryLimit.ONE);

		if (lastArchives.isEmpty())
		{
			return Optional.empty();
		}
		else
		{
			return Optional.of(lastArchives.get(0));
		}
	}

	@Override
	public Optional<byte[]> getLastArchiveBinaryData(@NonNull final TableRecordReference reference)
	{
		return getLastArchive(reference).map(this::getBinaryData);
	}
}
