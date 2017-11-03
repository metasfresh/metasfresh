package de.metas.ui.web.attachments;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.stream.Stream;

import org.adempiere.archive.api.IArchiveDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.ImmutablePair;
import org.compiere.model.I_AD_Archive;
import org.compiere.model.I_AD_AttachmentEntry;
import org.compiere.util.Env;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.base.Joiner;
import com.google.common.base.MoreObjects;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.jgoodies.common.base.Objects;

import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.IAttachmentBL;
import de.metas.ui.web.attachments.json.JSONAttachment;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.events.DocumentWebsocketPublisher;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * Document attachments facade.
 *
 * NOTE to developers: introduced to hide behind legacy attachments code.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
final class DocumentAttachments
{
	public static final String ID_PREFIX_Attachment = "ATT";
	public static final String ID_PREFIX_Archive = "ARR";
	private static final String ID_SEPARATOR = "_";
	private static final Splitter ID_Splitter = Splitter.on(ID_SEPARATOR);
	private static final Joiner ID_Joiner = Joiner.on(ID_SEPARATOR);

	private final transient IAttachmentBL attachmentsBL = Services.get(IAttachmentBL.class);

	private final DocumentPath documentPath;
	private final ITableRecordReference recordRef;
	private final DocumentEntityDescriptor entityDescriptor;
	private final DocumentWebsocketPublisher websocketPublisher;

	@Builder
	private DocumentAttachments(
			@NonNull final DocumentPath documentPath,
			@NonNull final ITableRecordReference recordRef,
			@NonNull final DocumentEntityDescriptor entityDescriptor,
			@NonNull final DocumentWebsocketPublisher websocketPublisher)
	{
		this.documentPath = documentPath;
		this.recordRef = recordRef;
		this.entityDescriptor = entityDescriptor;
		this.websocketPublisher = websocketPublisher;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.addValue(recordRef)
				.toString();
	}

	public List<JSONAttachment> toJson()
	{
		final Stream<IDocumentAttachmentEntry> attachments = attachmentsBL.getEntries(recordRef)
				.stream()
				.map(entry -> DocumentAttachmentEntry.of(buildId(ID_PREFIX_Attachment, entry.getId()), entry));

		final Stream<DocumentArchiveEntry> archives = Services.get(IArchiveDAO.class).retrieveLastArchives(Env.getCtx(), recordRef, 10)
				.stream()
				.map(archive -> DocumentArchiveEntry.of(buildId(ID_PREFIX_Archive, archive.getAD_Archive_ID()), archive));

		return Stream.concat(attachments, archives)
				.map(JSONAttachment::of)
				.collect(ImmutableList.toImmutableList());
	}

	public void addEntry(final MultipartFile file) throws IOException
	{
		Check.assumeNotNull(file, "Parameter file is not null");
		final String name = file.getOriginalFilename();
		final byte[] data = file.getBytes();

		attachmentsBL.addEntry(recordRef, name, data);

		notifyRelatedDocumentTabsChanged();
	}

	public void addURLEntry(final String name, final URI url)
	{
		attachmentsBL.addURLEntry(recordRef, name, url);

		notifyRelatedDocumentTabsChanged();
	}

	public IDocumentAttachmentEntry getEntry(final DocumentId id)
	{
		final IPair<String, Integer> prefixAndId = toPrefixAndEntryId(id);
		final String idPrefix = prefixAndId.getLeft();
		final int entryId = prefixAndId.getRight();

		if (ID_PREFIX_Attachment.equals(idPrefix))
		{
			final AttachmentEntry entry = attachmentsBL.getEntryById(recordRef, entryId);
			if (entry == null)
			{
				throw new EntityNotFoundException(id.toJson());
			}
			return DocumentAttachmentEntry.of(id, entry);
		}
		else if (ID_PREFIX_Archive.equals(idPrefix))
		{
			final I_AD_Archive archive = Services.get(IArchiveDAO.class).retrieveArchiveOrNull(Env.getCtx(), recordRef, entryId);
			if (archive == null)
			{
				throw new EntityNotFoundException(id.toJson());
			}
			return DocumentArchiveEntry.of(id, archive);
		}
		else
		{
			throw new EntityNotFoundException(id.toJson());
		}
	}

	public void deleteEntry(final DocumentId id)
	{
		final IPair<String, Integer> prefixAndId = toPrefixAndEntryId(id);
		final String idPrefix = prefixAndId.getLeft();
		final int entryId = prefixAndId.getRight();

		if (ID_PREFIX_Attachment.equals(idPrefix))
		{
			attachmentsBL.deleteEntryForModel(recordRef, entryId);
			notifyRelatedDocumentTabsChanged();
		}
		else if (ID_PREFIX_Archive.equals(idPrefix))
		{
			final I_AD_Archive archive = Services.get(IArchiveDAO.class).retrieveArchiveOrNull(Env.getCtx(), recordRef, entryId);
			if (archive == null)
			{
				throw new EntityNotFoundException(id.toJson());
			}
			InterfaceWrapperHelper.delete(archive);
		}
		else
		{
			throw new EntityNotFoundException(id.toJson());
		}
	}

	private static IPair<String, Integer> toPrefixAndEntryId(final DocumentId id)
	{
		final List<String> idParts = ID_Splitter.splitToList(id.toJson());
		if (idParts.size() != 2)
		{
			throw new IllegalArgumentException("Invalid attachment ID");
		}

		final String idPrefix = idParts.get(0);
		final int entryId = Integer.parseInt(idParts.get(1));

		return ImmutablePair.of(idPrefix, entryId);
	}

	private static final DocumentId buildId(final String idPrefix, final int entryId)
	{
		return DocumentId.ofString(ID_Joiner.join(idPrefix, entryId));
	}

	/**
	 * If the document contains some attachment related tabs, it will send an "stale" notification to frontend.
	 * In this way, frontend will be aware of this and will have to refresh the tab.
	 */
	private void notifyRelatedDocumentTabsChanged()
	{
		final ImmutableSet<DetailId> attachmentRelatedTabIds = entityDescriptor
				.getIncludedEntities().stream()
				.filter(includedEntityDescriptor -> Objects.equals(includedEntityDescriptor.getTableNameOrNull(), I_AD_AttachmentEntry.Table_Name))
				.map(includedEntityDescriptor -> includedEntityDescriptor.getDetailId())
				.collect(ImmutableSet.toImmutableSet());
		if (attachmentRelatedTabIds.isEmpty())
		{
			return;
		}

		websocketPublisher.staleTabs(documentPath.getWindowId(), documentPath.getDocumentId(), attachmentRelatedTabIds);
	}

}
