package de.metas.inbound.mail;

import java.util.List;
import java.util.Map;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Mail;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;

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
	private final ObjectMapper jsonObjectMapper;

	public InboundEMailRepository(@NonNull final ObjectMapper jsonObjectMapper)
	{
		this.jsonObjectMapper = jsonObjectMapper;
	}

	public void save(@NonNull final InboundEMail email)
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

		InterfaceWrapperHelper.save(mailRecord);
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
}
