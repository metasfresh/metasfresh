package de.metas.dunning.export;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;
import com.google.common.io.ByteStreams;

import ch.qos.logback.classic.Level;
import de.metas.attachments.AttachmentEntryCreateRequest;
import de.metas.attachments.AttachmentEntryService;
import de.metas.attachments.AttachmentTags;
import de.metas.dunning.DunningDocId;
import de.metas.dunning.model.I_C_DunningDoc;
import de.metas.dunning_gateway.api.DunningExportServiceRegistry;
import de.metas.dunning_gateway.spi.DunningExportClient;
import de.metas.dunning_gateway.spi.DunningExportClientFactory;
import de.metas.dunning_gateway.spi.model.DunningExportResult;
import de.metas.dunning_gateway.spi.model.DunningToExport;
import de.metas.logging.LogManager;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.StringUtils;
import lombok.NonNull;

/*
 * #%L
 * de.metas.dunning
 * %%
 * Copyright (C) 2018 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Service
public class DunningExportService
{

	private static final Logger logger = LogManager.getLogger(DunningExportService.class);

	private final DunningToExportFactory dunningToExportFactory;
	private final AttachmentEntryService attachmentEntryService;
	private final DunningExportServiceRegistry dunningExportServiceRegistry;

	private DunningExportService(
			@NonNull final DunningToExportFactory dunningToExportFactory,
			@NonNull final DunningExportServiceRegistry dunningExportServiceRegistry,
			@NonNull final AttachmentEntryService attachmentEntryService)
	{
		this.attachmentEntryService = attachmentEntryService;
		this.dunningToExportFactory = dunningToExportFactory;
		this.dunningExportServiceRegistry = dunningExportServiceRegistry;
	}

	public void exportDunnings(@NonNull final ImmutableList<DunningDocId> dunningDocIds)
	{
		for (final DunningDocId dunningDocId : dunningDocIds)
		{
			final List<DunningToExport> dunningsToExport = dunningToExportFactory.getCreateForId(dunningDocId);
			for (final DunningToExport dunningToExport : dunningsToExport)
			{
				exportDunning(dunningToExport);
			}
		}
	}

	private void exportDunning(@NonNull final DunningToExport dunningToExport)
	{
		final ILoggable loggable = Loggables.withLogger(logger, Level.DEBUG);

		final List<DunningExportClient> exportClients = dunningExportServiceRegistry.createExportClients(dunningToExport);
		if (exportClients.isEmpty())
		{
			loggable.addLog("DunningExportService - Found no DunningExportClient implementors for dunningDocId={}; dunningToExport={}", dunningToExport.getId(), dunningToExport);
			return; // nothing more to do
		}

		final List<AttachmentEntryCreateRequest> attachmentEntryCreateRequests = new ArrayList<>();
		for (final DunningExportClient exportClient : exportClients)
		{
			final List<DunningExportResult> exportResults = exportClient.export(dunningToExport);
			for (final DunningExportResult exportResult : exportResults)
			{
				attachmentEntryCreateRequests.add(createAttachmentRequest(exportResult));
			}
		}

		for (final AttachmentEntryCreateRequest attachmentEntryCreateRequest : attachmentEntryCreateRequests)
		{
			attachmentEntryService.createNewAttachment(
					TableRecordReference.of(I_C_DunningDoc.Table_Name, dunningToExport.getId()),
					attachmentEntryCreateRequest);
			loggable.addLog("DunningExportService - Attached export data to dunningDocId={}; attachment={}", dunningToExport.getId(), attachmentEntryCreateRequest);
		}
	}

	private AttachmentEntryCreateRequest createAttachmentRequest(@NonNull final DunningExportResult exportResult)
	{
		byte[] byteArrayData;
		try
		{
			byteArrayData = ByteStreams.toByteArray(exportResult.getData());
		}
		catch (IOException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
		final AttachmentTags attachmentTags = AttachmentTags.builder()
				.tag(AttachmentTags.TAGNAME_IS_DOCUMENT, StringUtils.ofBoolean(true)) // other than the "input" xml with was more or less just a template, this is a document
				.tag(AttachmentTags.TAGNAME_BPARTNER_RECIPIENT_ID, Integer.toString(exportResult.getRecipientId().getRepoId()))
				.tag(DunningExportClientFactory.ATTATCHMENT_TAGNAME_EXPORT_PROVIDER, exportResult.getDunningExportProviderId())
				.build();
		final AttachmentEntryCreateRequest attachmentEntryCreateRequest = AttachmentEntryCreateRequest
				.builderFromByteArray(
						exportResult.getFileName(),
						byteArrayData)
				.tags(attachmentTags)
				.build();
		return attachmentEntryCreateRequest;
	}

}
