package de.metas.inbound.mail.request;

import de.metas.bpartner.BPartnerId;
import de.metas.inbound.mail.InboundEMail;
import de.metas.inbound.mail.InboundEMailListener;
import de.metas.inbound.mail.InboundEMailRepository;
import de.metas.inbound.mail.config.InboundEMailConfig;
import de.metas.request.RequestId;
import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_R_Request;
import org.compiere.model.X_R_Request;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Component;

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

@Component
public class RequestFromInboundEMailListener implements InboundEMailListener
{
	private final InboundEMailRepository mailsRepo;

	public RequestFromInboundEMailListener(@NonNull final InboundEMailRepository mailsRepo)
	{
		this.mailsRepo = mailsRepo;
	}

	@Override
	public InboundEMail onInboundEMailReceived(final InboundEMailConfig config, final InboundEMail email)
	{
		if (email.getRequestId() != null)
		{
			return email;
		}

		if (!config.isCreateRequest())
		{
			return email;
		}

		RequestId requestId = mailsRepo.getRequestIdForMessageId(email.getInitialMessageId());
		if (requestId == null)
		{
			requestId = createRequest(config, email);
		}

		return email.withRequestId(requestId);
	}

	private RequestId createRequest(final InboundEMailConfig config, final InboundEMail email)
	{
		final I_R_Request request = InterfaceWrapperHelper.newInstance(I_R_Request.class);
		request.setSummary(email.getSubject());
		request.setConfidentialType(X_R_Request.CONFIDENTIALTYPE_Internal);
		request.setAD_Org_ID(config.getOrgId().getRepoId());
		request.setR_RequestType_ID(config.getRequestTypeId().getRepoId());
		request.setStartTime(TimeUtil.asTimestamp(email.getReceivedDate()));
		request.setDateTrx(TimeUtil.asTimestamp(email.getReceivedDate()));

		final ClientId adClientId = ClientId.ofRepoId(request.getAD_Client_ID());
		final IUserDAO usersRepo = Services.get(IUserDAO.class);
		final UserId userId = usersRepo.retrieveUserIdByEMail(email.getFrom(), adClientId);
		if (userId != null)
		{
			request.setAD_User_ID(userId.getRepoId());

			final BPartnerId bpartnerId = usersRepo.getBPartnerIdByUserId(userId);
			request.setC_BPartner_ID(BPartnerId.toRepoId(bpartnerId));
		}

		InterfaceWrapperHelper.save(request);

		return RequestId.ofRepoId(request.getR_Request_ID());
	}
}
