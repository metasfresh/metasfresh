package de.metas.attachments;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZoneId;

import de.metas.CreatedUpdatedInfo;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.user.UserId;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_AD_AttachmentEntry;
import org.compiere.model.X_AD_AttachmentEntry;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;

import de.metas.util.Check;
import lombok.NonNull;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
public class AttachmentEntryFactory
{
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	public AttachmentEntry createAndSaveEntry(@NonNull final AttachmentEntryCreateRequest request)
	{
		final AttachmentEntryType type = request.getType();
		final String filename = request.getFilename();
		final String contentType = request.getContentType();
		final byte[] data = request.getData();
		final URI url = request.getUrl();

		// Make sure the attachment is saved

		// Create entry
		final I_AD_AttachmentEntry attachmentEntryRecord = newInstance(I_AD_AttachmentEntry.class);

		//
		attachmentEntryRecord.setFileName(filename);
		attachmentEntryRecord.setType(type.getCode());

		switch (type)
		{
			case Data:
				attachmentEntryRecord.setBinaryData(data);
				attachmentEntryRecord.setContentType(contentType);
				break;
			case URL:
				Check.assumeNotNull(url, "Parameter url is not null");
				attachmentEntryRecord.setURL(url.toString());
				break;
			case LocalFileURL:
				Check.assumeNotNull(url, "Parameter url is not null");
				attachmentEntryRecord.setContentType(contentType);
				attachmentEntryRecord.setURL(url.toString());
				break;
			default:
				throw new AdempiereException("Type not supported: " + type).setParameter("request", request);
		}

		if (request.getTags() != null)
		{
			attachmentEntryRecord.setTags(request.getTags().getTagsAsString());
		}
		else
		{
			attachmentEntryRecord.setTags(null);
		}
		// we need to save for type=Data in order not to loose the byte[] if any.
		// we also save for type==URL so be more "predictable"
		saveRecord(attachmentEntryRecord);

		return toAttachmentEntry(attachmentEntryRecord);
	}

	public AttachmentEntry toAttachmentEntry(@NonNull final I_AD_AttachmentEntry entryRecord)
	{
		final ZoneId timeZone = orgDAO.getTimeZone(OrgId.ofRepoIdOrAny(entryRecord.getAD_Org_ID()));

		return AttachmentEntry.builder()
				.id(AttachmentEntryId.ofRepoIdOrNull(entryRecord.getAD_AttachmentEntry_ID()))
				.name(entryRecord.getFileName())
				.type(AttachmentEntryType.ofCode(entryRecord.getType()))
				.filename(entryRecord.getFileName())
				.mimeType(entryRecord.getContentType())
				.url(extractUriOrNull(entryRecord))
				.tags(AttachmentTags.ofString(entryRecord.getTags()))
				.createdUpdatedInfo(CreatedUpdatedInfo.of(
						TimeUtil.asZonedDateTime(entryRecord.getCreated(), timeZone), UserId.ofRepoId(entryRecord.getCreatedBy()),
						TimeUtil.asZonedDateTime(entryRecord.getUpdated(), timeZone), UserId.ofRepoId(entryRecord.getUpdatedBy())))
				.build();
	}

	@Nullable
	private static URI extractUriOrNull(final I_AD_AttachmentEntry entryRecord)
	{
		final String url = entryRecord.getURL();
		if (Check.isEmpty(url, true))
		{
			return null;
		}

		try
		{
			return new URI(url);
		}
		catch (final URISyntaxException ex)
		{
			throw new AdempiereException("Invalid URL: " + url, ex)
					.setParameter("entryRecord", entryRecord);
		}
	}

	public void syncToRecord(
			@NonNull final AttachmentEntry attachmentEntry,
			@NonNull final I_AD_AttachmentEntry attachmentEntryRecord)
	{
		attachmentEntryRecord.setFileName(attachmentEntry.getFilename());
		attachmentEntryRecord.setType(attachmentEntry.getType().getCode());
		attachmentEntryRecord.setFileName(attachmentEntry.getFilename());

		if (attachmentEntry.getUrl() != null)
		{
			attachmentEntryRecord.setURL(attachmentEntry.getUrl().toString());
		}
		else
		{
			attachmentEntryRecord.setURL(null);
		}
		attachmentEntryRecord.setTags(attachmentEntry.getTags().getTagsAsString());

	}
}
