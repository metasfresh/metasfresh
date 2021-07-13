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
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.i18n.Language;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.process.PInstanceId;
import de.metas.process.ProcessInfo;
import de.metas.report.DocumentReportFlavor;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.archive.api.ArchiveInfo;
import org.adempiere.archive.api.ArchiveRequest;
import org.adempiere.archive.api.ArchiveResult;
import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.archive.api.IArchiveDAO;
import org.adempiere.archive.api.IArchiveStorageFactory;
import org.adempiere.archive.spi.IArchiveStorage;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.service.IClientDAO;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.IClientOrgAware;
import org.compiere.model.I_AD_Archive;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_Process;
import org.compiere.model.X_AD_Client;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class ArchiveBL implements IArchiveBL
{
	@Override
	@Nullable
	public I_AD_Archive archive(final byte[] data,
								final ArchiveInfo archiveInfo,
								final boolean force,
								final boolean save,
								final String trxName)
	{
		final ArchiveRequest request = createArchiveRequest(data, archiveInfo, force, save, trxName);
		return archive(request).getArchiveRecord();
	}

	@Override
	public @NonNull ArchiveResult archive(@NonNull final ArchiveRequest request)
	{
		if (request.isForce() || isToArchive(request))
		{
			return archive0(request);
		}
		else
		{
			return ArchiveResult.EMPTY;
		}

	}

	private static ArchiveRequest createArchiveRequest(
			final byte[] data,
			final ArchiveInfo archiveInfo,
			final boolean force,
			final boolean save,
			final String trxName)
	{
		final ArchiveRequest.ArchiveRequestBuilder requestBuilder = ArchiveRequest.builder()
				.ctx(Env.getCtx())
				.data(data)
				.force(force)
				.save(save)
				.trxName(trxName);

		if (archiveInfo != null)
		{
			requestBuilder
					.isReport(archiveInfo.isReport())
					.recordRef(archiveInfo.getRecordRef())
					.processId(archiveInfo.getProcessId())
					.pinstanceId(archiveInfo.getPInstanceId())
					.archiveName(archiveInfo.getName())
					.bpartnerId(archiveInfo.getBpartnerId());
		}

		return requestBuilder.build();
	}

	private ArchiveResult archive0(@NonNull final ArchiveRequest request)
	{
		// t.schoemeberg@metas.de, 03787: using the client/org of the archived PO, if possible
		final Properties ctxToUse = createContext(request);

		final IArchiveStorage storage = Services.get(IArchiveStorageFactory.class).getArchiveStorage(ctxToUse);
		final I_AD_Archive archive = storage.newArchive(ctxToUse, request.getTrxName());
		archive.setDocumentFlavor(DocumentReportFlavor.toCode(request.getFlavor()));

		// FRESH-218: extract and set the language to the archive
		final String language = getLanguageFromReport(ctxToUse, request);
		archive.setAD_Language(language);

		archive.setDocumentNo(request.getDocumentNo());
		archive.setName(request.getArchiveName());
		archive.setIsReport(request.isReport());
		//
		archive.setAD_Process_ID(AdProcessId.toRepoId(request.getProcessId()));

		final TableRecordReference recordRef = request.getRecordRef();
		archive.setAD_Table_ID(recordRef != null ? recordRef.getAD_Table_ID() : -1);
		archive.setRecord_ID(recordRef != null ? recordRef.getRecord_ID() : -1);

		archive.setC_BPartner_ID(BPartnerId.toRepoId(request.getBpartnerId()));

		final byte[] data = request.getData();
		storage.setBinaryData(archive, data);

		//FRESH-349: Set ad_pinstance
		archive.setAD_PInstance_ID(PInstanceId.toRepoId(request.getPinstanceId()));

		if (request.isSave())
		{
			InterfaceWrapperHelper.save(archive);
		}

		return ArchiveResult.builder()
				.archiveRecord(archive)
				.data(data)
				.build();
	}

	/**
	 * Return the BPartner's language, in case the request has a jasper report set and this jasper report is a process that uses the BPartner language. If it was not found, fall back to the language set
	 * in the given context
	 * <p>
	 * Task https://metasfresh.atlassian.net/browse/FRESH-218
	 */
	private String getLanguageFromReport(
			@NonNull final Properties ctx,
			@NonNull final ArchiveRequest request)
	{
		if (request.getLanguage() != null)
		{
			return request.getLanguage().getAD_Language();
		}

		// the language from the given context. In case there will be no other language to fit the logic, this is the value to be returned.
		final String initialLanguage = Env.getAD_Language(ctx);

		final AdProcessId processId = request.getProcessId();
		final I_AD_Process process = processId != null
				? Services.get(IADProcessDAO.class).getById(processId)
				: null;

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

		final TableRecordReference recordRef = request.getRecordRef();
		if (recordRef == null)
		{
			return initialLanguage;
		}

		final Object record = recordRef.getModel(PlainContextAware.newWithTrxName(ctx, request.getTrxName()), Object.class);
		final IBPartnerBL bpartnerBL = Services.get(IBPartnerBL.class);
		return bpartnerBL.getLanguageForModel(record).map(Language::getAD_Language).orElse(initialLanguage);
	}

	private Properties createContext(@NonNull final ArchiveRequest request)
	{
		final TableRecordReference recordRef = request.getRecordRef();
		if (recordRef == null)
		{
			return request.getCtx();
		}

		final IClientOrgAware record = recordRef.getModel(
				PlainContextAware.newWithTrxName(request.getCtx(), request.getTrxName()),
				IClientOrgAware.class);
		if (record == null)
		{
			// attached record was not found. return the initial context (an error was already logged by loader)
			return request.getCtx();
		}
		final Properties ctxToUse = Env.deriveCtx(request.getCtx());
		Env.setContext(ctxToUse, Env.CTXNAME_AD_Client_ID, record.getAD_Client_ID());
		Env.setContext(ctxToUse, Env.CTXNAME_AD_Org_ID, record.getAD_Org_ID());
		// setClientOrg(archivedPO);

		return ctxToUse;

	}

	private boolean isToArchive(@NonNull final ArchiveRequest request)
	{
		final String autoArchive = getAutoArchiveType(request.getCtx());

		// Nothing to Archive
		if (autoArchive.equals(X_AD_Client.AUTOARCHIVE_None))
		{
			return false;
		}
		// Archive External only
		if (autoArchive.equals(X_AD_Client.AUTOARCHIVE_ExternalDocuments))
		{
			if (request.isReport())
			{
				return false;
			}
		}
		// Archive Documents only
		if (autoArchive.equals(X_AD_Client.AUTOARCHIVE_Documents))
		{
			if (request.isReport())
			{
				return false;
			}
		}
		return true;
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

	private static String getAutoArchiveType(final Properties ctx)
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
