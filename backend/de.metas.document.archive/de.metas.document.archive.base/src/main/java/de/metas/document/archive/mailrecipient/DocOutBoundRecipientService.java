package de.metas.document.archive.mailrecipient;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.document.archive.model.I_C_BPartner;
import de.metas.i18n.Language;
import de.metas.user.User;
import de.metas.user.UserId;
import de.metas.util.OptionalBoolean;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

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

@Service
@RequiredArgsConstructor
public class DocOutBoundRecipientService
{
	@NonNull private final IBPartnerBL bpartnerBL;

	public DocOutBoundRecipient getById(@NonNull final DocOutBoundRecipientId id)
	{
		return getByUserId(id.toUserId());
	}

	public DocOutBoundRecipient getByUserId(@NonNull final UserId userId)
	{
		final User user = bpartnerBL.getContactById(userId);
		return ofUser(user);
	}

	public DocOutBoundRecipient ofUser(@NonNull final User user)
	{
		final Language userLanguage = user.getLanguage();

		@Nullable final BPartnerId bpartnerId = user.getBpartnerId();
		@Nullable final I_C_BPartner bpartnerRecord = bpartnerId != null ? bpartnerBL.getById(bpartnerId, I_C_BPartner.class) : null;
		@Nullable final Language bPartnerLanguage = bpartnerRecord != null ? bpartnerBL.getLanguage(bpartnerRecord).orElse(null) : null;
		final boolean isInvoiceAsEmail = computeInvoiceAsEmail(bpartnerRecord, user.getIsInvoiceEmailEnabled());

		return DocOutBoundRecipient.builder()
				.id(DocOutBoundRecipientId.ofUserId(user.getIdNotNull()))
				.emailAddress(user.getEmailAddress())
				.invoiceAsEmail(isInvoiceAsEmail)
				.userLanguage(userLanguage)
				.bPartnerLanguage(bPartnerLanguage)
				.build();
	}

	private boolean computeInvoiceAsEmail(@Nullable final I_C_BPartner bpartnerRecord, @NonNull final OptionalBoolean defaultValue)
	{
		return extractInvoiceEmailEnabled(bpartnerRecord)
				.ifUnknown(defaultValue)
				.orElseFalse();
	}

	private static OptionalBoolean extractInvoiceEmailEnabled(@Nullable final I_C_BPartner bpartnerRecord)
	{
		return bpartnerRecord != null ? OptionalBoolean.ofNullableString(bpartnerRecord.getIsInvoiceEmailEnabled()) : OptionalBoolean.UNKNOWN;
	}

}
