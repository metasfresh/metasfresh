package de.metas.inbound.mail;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

import javax.activation.DataSource;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_Mail;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;

import de.metas.attachments.AttachmentEntryCreateRequest;
import de.metas.attachments.AttachmentEntryService;
import de.metas.request.RequestId;
import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.inbound.mail
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

@Repository
public class InboundEMailRepository
{
	private final ObjectMapper jsonObjectMapper;
	private final AttachmentEntryService attachmentEntryService;

	public InboundEMailRepository(
			@NonNull final ObjectMapper jsonObjectMapper,
			@NonNull AttachmentEntryService attachmentEntryService)
	{
		this.jsonObjectMapper = jsonObjectMapper;
		this.attachmentEntryService = attachmentEntryService;
	}

	public void save(@NonNull final InboundEMail email)
	{
		final I_C_Mail mailRecord = toMailRecord(email);
		InterfaceWrapperHelper.save(mailRecord);

		final List<InboundEMailAttachment> attachments = email.getAttachments();
		if (attachments.isEmpty())
		{
			return;
		}

		final ImmutableList<AttachmentEntryCreateRequest> requests = attachments.stream()
				.map(InboundEMailAttachmentDataSource::new)
				.map(AttachmentEntryCreateRequest::fromDataSource)
				.collect(ImmutableList.toImmutableList());
		for (final AttachmentEntryCreateRequest request : requests)
		{
			attachmentEntryService.createNewAttachment(mailRecord, request);
		}
	}

	private I_C_Mail toMailRecord(final InboundEMail email)
	{
		final I_C_Mail mailRecord = InterfaceWrapperHelper.newInstance(I_C_Mail.class);
		mailRecord.setIsInboundEMail(true);
		mailRecord.setEMail_From(email.getFrom());
		mailRecord.setEMail_To(Joiner.on(", ").join(email.getTo()));
		mailRecord.setEMail_Cc(Joiner.on(", ").join(email.getCc()));
		mailRecord.setEMail_Bcc(Joiner.on(", ").join(email.getBcc()));

		mailRecord.setSubject(email.getSubject());
		mailRecord.setContentText(email.getContent());
		mailRecord.setContentType(email.getContentType());

		mailRecord.setDateReceived(TimeUtil.asTimestamp(email.getReceivedDate()));

		mailRecord.setMessageID(email.getMessageId());
		mailRecord.setInitialMessageID(email.getInitialMessageId());

		mailRecord.setEMailHeadersJSON(toJson(email.getHeaders()));

		mailRecord.setR_Request_ID(RequestId.toRepoId(email.getRequestId()));

		final ClientId adClientId = ClientId.ofRepoId(mailRecord.getAD_Client_ID());
		final IUserDAO usersRepo = Services.get(IUserDAO.class);
		final UserId fromUserId = usersRepo.retrieveUserIdByEMail(email.getFrom(), adClientId);
		if (fromUserId != null)
		{
			mailRecord.setFrom_User_ID(fromUserId.getRepoId());
		}

		return mailRecord;
	}

	private String toJson(final Map<String, Object> map)
	{
		try
		{
			return jsonObjectMapper.writeValueAsString(map);
		}
		catch (final JsonProcessingException ex)
		{
			throw AdempiereException.wrapIfNeeded(ex)
					.setParameter("map", map);
		}
	}

	public RequestId getRequestIdForMessageId(@NonNull final String messageId)
	{
		final List<Integer> requestIds = Services.get(IQueryBL.class).createQueryBuilder(I_C_Mail.class)
				.addEqualsFilter(I_C_Mail.COLUMN_InitialMessageID, messageId)
				.addNotNull(I_C_Mail.COLUMN_R_Request_ID)
				.orderBy(I_C_Mail.COLUMN_R_Request_ID)
				.create()
				.listDistinct(I_C_Mail.COLUMNNAME_R_Request_ID, Integer.class);
		if (requestIds.isEmpty())
		{
			return null;
		}
		return RequestId.ofRepoId(requestIds.get(0));
	}

	private static final class InboundEMailAttachmentDataSource implements DataSource
	{
		private InboundEMailAttachment attachment;

		private InboundEMailAttachmentDataSource(@NonNull final InboundEMailAttachment attachment)
		{
			this.attachment = attachment;
		}

		@Override
		public String getName()
		{
			return attachment.getFilename();
		}

		@Override
		public String getContentType()
		{
			return attachment.getContentType();
		}

		@Override
		public InputStream getInputStream() throws IOException
		{
			return Files.newInputStream(attachment.getTempFile());
		}

		@Override
		public OutputStream getOutputStream() throws IOException
		{
			throw new UnsupportedOperationException();
		}

	}
}
