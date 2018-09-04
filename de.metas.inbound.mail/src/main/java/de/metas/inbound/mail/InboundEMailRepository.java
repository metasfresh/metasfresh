package de.metas.inbound.mail;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

import javax.activation.DataSource;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.user.UserId;
import org.adempiere.user.api.IUserDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Mail;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;

import de.metas.attachments.IAttachmentBL;
import de.metas.logging.LogManager;
import de.metas.request.RequestId;
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
	private static final Logger logger = LogManager.getLogger(InboundEMailRepository.class);

	private final ObjectMapper jsonObjectMapper;

	public InboundEMailRepository(@NonNull final ObjectMapper jsonObjectMapper)
	{
		this.jsonObjectMapper = jsonObjectMapper;
	}

	public void save(@NonNull final InboundEMail email)
	{
		final I_C_Mail mailRecord = toMailRecord(email);
		InterfaceWrapperHelper.save(mailRecord);

		final List<InboundEMailAttachment> attachments = email.getAttachments();
		if (!attachments.isEmpty())
		{
			final IAttachmentBL attachmentBL = Services.get(IAttachmentBL.class);
			attachmentBL.addEntriesFromDataSources(mailRecord, attachments.stream()
					.map(InboundEMailAttachmentDataSource::new)
					.collect(ImmutableList.toImmutableList()));
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
		final UserId fromUserId = getUserIdByEMailOrNull(email.getFrom(), adClientId);
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

	private UserId getUserIdByEMailOrNull(final String email, final ClientId adClientId)
	{
		if (Check.isEmpty(email, true))
		{
			return null;
		}

		final String emailNorm = extractEMailAddressOrNull(email);
		if (Check.isEmpty(emailNorm, true))
		{
			return null;
		}

		return Services.get(IUserDAO.class).retrieveUserIdByEMail(emailNorm, adClientId);
	}

	private static final String extractEMailAddressOrNull(final String email)
	{
		try
		{
			return new InternetAddress(email).getAddress();
		}
		catch (AddressException e)
		{
			logger.warn("Invalid email address `{}`. Returning null.", email, e);
			return null;
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
