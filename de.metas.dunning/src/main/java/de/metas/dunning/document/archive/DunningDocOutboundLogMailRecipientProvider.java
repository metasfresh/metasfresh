package de.metas.dunning.document.archive;

import java.util.Optional;

import org.adempiere.user.User;
import org.adempiere.util.Check;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.stereotype.Component;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.IBPartnerBL.RetrieveBillContactRequest;
import de.metas.document.archive.DocOutBoundRecipient;
import de.metas.document.archive.DocOutBoundRecipientId;
import de.metas.document.archive.DocOutBoundRecipientRepository;
import de.metas.document.archive.DocOutboundLogMailRecipientProvider;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.dunning.model.I_C_DunningDoc;
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

@Component
public class DunningDocOutboundLogMailRecipientProvider
		implements DocOutboundLogMailRecipientProvider
{

	private final DocOutBoundRecipientRepository recipientRepository;
	private final IBPartnerBL bpartnerBL;

	public DunningDocOutboundLogMailRecipientProvider(
			@NonNull final DocOutBoundRecipientRepository recipientRepository,
			@NonNull final IBPartnerBL bpartnerBL)
	{
		this.recipientRepository = recipientRepository;
		this.bpartnerBL = bpartnerBL;
	}

	@Override
	public boolean isDefault()
	{
		return false;
	}

	@Override
	public String getTableName()
	{
		return I_C_DunningDoc.Table_Name;
	}

	/**
	 * Provides the bill contact based on the dunning's bPartner and bPartner-location.
	 * Note that we don't have a dunning-contact field in {@code C_DunningDoc}.
	 */
	@Override
	public Optional<DocOutBoundRecipient> provideMailRecipient(@NonNull final I_C_Doc_Outbound_Log docOutboundLogRecord)
	{
		final I_C_DunningDoc dunningRecord = TableRecordReference
				.ofReferenced(docOutboundLogRecord)
				.getModel(I_C_DunningDoc.class);

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(dunningRecord.getC_BPartner_ID());
		final RetrieveBillContactRequest request = RetrieveBillContactRequest
				.builder()
				.bpartnerId(bpartnerId)
				.bPartnerLocationId(BPartnerLocationId.ofRepoId(bpartnerId, dunningRecord.getC_BPartner_Location_ID()))
				.filter(user -> !Check.isEmpty(user.getEmailAddress(), true))
				.build();

		final User billContact = bpartnerBL.retrieveBillContactOrNull(request);
		if (billContact != null)
		{
			final DocOutBoundRecipient docOutBoundRecipient = recipientRepository.getById(DocOutBoundRecipientId.ofRepoId(billContact.getId().getRepoId()));
			return Optional.of(docOutBoundRecipient);
		}
		return Optional.empty();
	}

}
