package de.metas.document.archive.interceptor;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.table.api.IADTableDAO;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.document.archive.mailrecipient.DocOutBoundRecipient;
import de.metas.document.archive.mailrecipient.DocOutBoundRecipientId;
import de.metas.document.archive.mailrecipient.DocOutBoundRecipientRepository;
import de.metas.document.archive.model.I_C_BPartner;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.util.Services;
import de.metas.util.StringUtils;
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
@Interceptor(I_C_Doc_Outbound_Log.class)
@Component
public class C_Doc_Outbound_Log
{
	private final DocOutBoundRecipientRepository docOutBoundRecipientRepository;

	public C_Doc_Outbound_Log(@NonNull final DocOutBoundRecipientRepository docOutBoundRecipientRepository)
	{
		this.docOutBoundRecipientRepository = docOutBoundRecipientRepository;
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	@CalloutMethod(columnNames = I_C_Doc_Outbound_Log.COLUMNNAME_CurrentEMailRecipient_ID)
	@ModelChange( //
			timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, //
			ifColumnsChanged = I_C_Doc_Outbound_Log.COLUMNNAME_CurrentEMailRecipient_ID)
	public void updateFromRecipientId(I_C_Doc_Outbound_Log docOutboundlogRecord)
	{
		final DocOutBoundRecipientId userId = DocOutBoundRecipientId.ofRepoIdOrNull(docOutboundlogRecord.getCurrentEMailRecipient_ID());
		if (userId == null)
		{
			docOutboundlogRecord.setCurrentEMailAddress(null);
			return;
		}

		final DocOutBoundRecipient user = docOutBoundRecipientRepository.getById(userId);
		docOutboundlogRecord.setCurrentEMailAddress(user.getEmailAddress()); // might be empty!
		docOutboundlogRecord.setIsInvoiceEmailEnabled(user.isInvoiceAsEmail()); // might be true even if the mailaddress is empty!
	}

	// this column is not user-editable, so we don't need it to be a callout
	@ModelChange( //
			timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, //
			ifColumnsChanged = I_C_Doc_Outbound_Log.COLUMNNAME_C_BPartner_ID)
	public void updateFromBPartnerId(I_C_Doc_Outbound_Log docOutboundlogRecord)
	{
		if (docOutboundlogRecord.getC_BPartner_ID() <= 0)
		{
			// reset everything
			docOutboundlogRecord.setCurrentEMailRecipient_ID(-1);
			docOutboundlogRecord.setCurrentEMailAddress(null);
			docOutboundlogRecord.setIsInvoiceEmailEnabled(false);
			return;
		}

		final int invoiceTableId = Services.get(IADTableDAO.class).retrieveTableId(I_C_Invoice.Table_Name);
		if (docOutboundlogRecord.getAD_Table_ID() != invoiceTableId)
		{
			docOutboundlogRecord.setIsInvoiceEmailEnabled(false);
			return;
		}

		final I_C_BPartner bpartnerRecord = loadOutOfTrx(docOutboundlogRecord.getC_BPartner_ID(), I_C_BPartner.class);
		docOutboundlogRecord.setIsInvoiceEmailEnabled(StringUtils.toBoolean(bpartnerRecord.getIsInvoiceEmailEnabled()));
	}
}
