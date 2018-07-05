package de.metas.document.archive.spi.impl;

import java.util.Optional;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Invoice;
import org.compiere.util.Env;
import org.springframework.stereotype.Component;

import de.metas.adempiere.model.I_AD_User;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.document.archive.DocOutBoundRecipient;
import de.metas.document.archive.DocOutBoundRecipientId;
import de.metas.document.archive.DocOutBoundRecipientRepository;
import de.metas.document.archive.DocOutboundLogMailRecipientProvider;
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

@Component
public class InvoiceDocOutboundLogMailRecipientProvider
		implements DocOutboundLogMailRecipientProvider
{

	private final DocOutBoundRecipientRepository userRepository;

	public InvoiceDocOutboundLogMailRecipientProvider(@NonNull final DocOutBoundRecipientRepository userRepository)
	{
		this.userRepository = userRepository;
	}

	@Override
	public boolean isDefault()
	{
		return false;
	}

	@Override
	public String getTableName()
	{
		return I_C_Invoice.Table_Name;
	}

	@Override
	public Optional<DocOutBoundRecipient> provideMailRecipient(@NonNull final I_C_Doc_Outbound_Log docOutboundLogRecord)
	{
		final I_C_Invoice invoiceRecord = TableRecordReference
				.ofReferenced(docOutboundLogRecord)
				.getModel(I_C_Invoice.class);
		if (invoiceRecord.getAD_User_ID() > 0)
		{
			final DocOutBoundRecipient invoiceUser = userRepository.getById(DocOutBoundRecipientId.ofRepoId(invoiceRecord.getAD_User_ID()));
			if (!Check.isEmpty(invoiceUser.getEmailAddress(), true))
			{
				return Optional.of(invoiceUser);
			}
		}

		final IBPartnerBL bpartnerBL = Services.get(IBPartnerBL.class);
		final I_AD_User userRecord = bpartnerBL.retrieveBillContact(Env.getCtx(), invoiceRecord.getC_BPartner_ID(), ITrx.TRXNAME_ThreadInherited);
		if (userRecord.getAD_User_ID() > 0)
		{
			final DocOutBoundRecipient billUser = userRepository.getById(DocOutBoundRecipientId.ofRepoId(invoiceRecord.getAD_User_ID()));
			if (!Check.isEmpty(billUser.getEmailAddress(), true))
			{
				return Optional.of(billUser);
			}
		}
		return Optional.empty();
	}

}
