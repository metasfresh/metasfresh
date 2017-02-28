package de.metas.ui.web.window.model;

import java.io.IOException;
import java.util.List;

import org.adempiere.service.IAttachmentBL;
import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.model.MAttachmentEntry;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.base.MoreObjects;

import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.window.datatypes.json.JSONAttachment;

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
public final class DocumentAttachments
{
	/* package */static DocumentAttachments of(final ITableRecordReference recordRef)
	{
		return new DocumentAttachments(recordRef);
	}

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
		return attachmentsBL.getEntiresForModel(recordRef)
				.stream()
				.map(JSONAttachment::of)
				.collect(GuavaCollectors.toImmutableList());
	}

	public void addEntry(final MultipartFile file) throws IOException
	{
		Check.assumeNotNull(file, "Parameter file is not null");
		final String name = file.getOriginalFilename();
		final byte[] data = file.getBytes();

		attachmentsBL.createAttachment(recordRef, name, data);
	}

	public MAttachmentEntry getEntry(final int id)
	{
		final MAttachmentEntry entry = attachmentsBL.getEntryForModelById(recordRef, id);
		if (entry == null)
		{
			throw new EntityNotFoundException("No attachment found (ID=" + id + ")");
		}
		return entry;
	}

	public void deleteEntry(final int id)
	{
		attachmentsBL.deleteEntryForModel(recordRef, id);
	}

}
