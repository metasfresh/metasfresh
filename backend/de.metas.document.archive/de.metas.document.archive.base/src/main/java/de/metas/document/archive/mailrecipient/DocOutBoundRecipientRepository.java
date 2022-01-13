package de.metas.document.archive.mailrecipient;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.IBPartnerDAO;
import org.compiere.model.I_AD_User;
import de.metas.document.archive.model.I_C_BPartner;
import de.metas.i18n.Language;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

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

@Repository
public class DocOutBoundRecipientRepository
{
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final IBPartnerBL bpartnerBL;

	public DocOutBoundRecipientRepository(
			@NonNull final IBPartnerBL bpartnerBL)
	{
		this.bpartnerBL = bpartnerBL;
	}

	public DocOutBoundRecipient getById(@NonNull final DocOutBoundRecipientId id)
	{
		final I_AD_User userRecord = loadOutOfTrx(id.getRepoId(), I_AD_User.class);
		return ofRecord(userRecord);
	}

	private DocOutBoundRecipient ofRecord(@NonNull final I_AD_User userRecord)
	{
		final Language userLanguage = Language.asLanguage(userRecord.getAD_Language());

		final Language bPartnerLanguage = bpartnerBL.getLanguageForModel(userRecord).orElse(null);

		return DocOutBoundRecipient.builder()
				.id(DocOutBoundRecipientId.ofRepoId(userRecord.getAD_User_ID()))
				.emailAddress(userRecord.getEMail())
				.invoiceAsEmail(computeInvoiceAsEmail(userRecord))
				.userLanguage(userLanguage)
				.bPartnerLanguage(bPartnerLanguage)
				.build();
	}

	private boolean computeInvoiceAsEmail(@NonNull final I_AD_User userRecord)
	{
		final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(userRecord.getC_BPartner_ID());
		if (bpartnerId != null)
		{
			final I_C_BPartner bpartnerRecord = bpartnerDAO.getById(bpartnerId, I_C_BPartner.class);
			final String isInvoiceEmailEnabled = bpartnerRecord.getIsInvoiceEmailEnabled();
			if (!Check.isEmpty(isInvoiceEmailEnabled, true))
			{
				return StringUtils.toBoolean(isInvoiceEmailEnabled); // we have our result
			}
		}
		return StringUtils.toBoolean(userRecord.getIsInvoiceEmailEnabled());
	}
}
