package de.metas.ui.web.attachments;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import org.adempiere.archive.api.IArchiveDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.IAttachmentBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.ImmutablePair;
import org.compiere.model.I_AD_Archive;
import org.compiere.model.MAttachmentEntry;
import org.compiere.util.Env;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.base.MoreObjects;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;

import de.metas.ui.web.attachments.json.JSONAttachment;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.window.datatypes.DocumentId;

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
	public static DocumentAttachments of(final ITableRecordReference recordRef)
	{
		return new DocumentAttachments(recordRef);
	}

	public static final String ID_PREFIX_Attachment = "ATT";
	public static final String ID_PREFIX_Archive = "ARR";
	private static final Splitter ID_Splitter = Splitter.on("_");

	private final transient IAttachmentBL attachmentsBL = Services.get(IAttachmentBL.class);

	private final ITableRecordReference recordRef;

	private DocumentAttachments(final ITableRecordReference recordRef)
	{
		Check.assumeNotNull(recordRef, "Parameter recordRef is not null");
		this.recordRef = recordRef;
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
		final Stream<IDocumentAttachmentEntry> attachments = attachmentsBL.getEntiresForModel(recordRef)
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

		attachmentsBL.createAttachment(recordRef, name, data);
	}

	public IDocumentAttachmentEntry getEntry(final DocumentId id)
	{
		final IPair<String, Integer> prefixAndId = toPrefixAndEntryId(id);
		final String idPrefix = prefixAndId.getLeft();
		final int entryId = prefixAndId.getRight();

		if (ID_PREFIX_Attachment.equals(idPrefix))
		{
			final MAttachmentEntry entry = attachmentsBL.getEntryForModelById(recordRef, entryId);
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
		return DocumentId.ofString(idPrefix + "_" + entryId);
	}

}
