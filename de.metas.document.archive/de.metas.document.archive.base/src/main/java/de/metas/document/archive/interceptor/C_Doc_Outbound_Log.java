package de.metas.document.archive.interceptor;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.user.User;
import org.adempiere.user.UserId;
import org.adempiere.user.UserRepository;
import org.adempiere.util.Services;
import org.springframework.stereotype.Component;

import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import lombok.NonNull;

/*
 * #%L
 * de.metas.document.archive.base
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

@Callout(I_C_Doc_Outbound_Log.class)
@Component
public class C_Doc_Outbound_Log
{
	private final UserRepository userRepository;

	public C_Doc_Outbound_Log(@NonNull final UserRepository userRepository)
	{
		this.userRepository = userRepository;
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	@CalloutMethod(columnNames = I_C_Doc_Outbound_Log.COLUMNNAME_CurrentEMailRecipient_ID)
	public void updateMailAddress(I_C_Doc_Outbound_Log docOutboundlogRecord)
	{
		final UserId userId = UserId.ofRepoIdOrNull(docOutboundlogRecord.getCurrentEMailRecipient_ID());
		if (userId == null)
		{
			docOutboundlogRecord.setCurrentEMailAddress(null);
			return;
		}
		final User user = userRepository.getById(userId);
		docOutboundlogRecord.setCurrentEMailAddress(user.getEmailAddress());
	}
}
