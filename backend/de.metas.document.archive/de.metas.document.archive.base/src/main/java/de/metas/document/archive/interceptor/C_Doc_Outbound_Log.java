package de.metas.document.archive.interceptor;

import de.metas.document.archive.api.impl.DocOutboundLogHeaderPopulator;
import de.metas.document.archive.api.impl.DocOutboundService;
import de.metas.document.archive.mailrecipient.DocOutBoundRecipient;
import de.metas.document.archive.mailrecipient.DocOutBoundRecipientId;
import de.metas.document.archive.mailrecipient.DocOutBoundRecipientService;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.order.impl.OrderEmailPropagationSysConfigRepository;
import de.metas.organization.ClientAndOrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.persistence.ModelDynAttributeAccessor;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

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
	/**
	 * Set on a log while code that derives the recipient, its address and the invoice-e-mail flag itself saves it,
	 * so that {@link #updateFromRecipientId(I_C_Doc_Outbound_Log)} does not overwrite them with the values of the recipient contact.
	 */
	public static final ModelDynAttributeAccessor<I_C_Doc_Outbound_Log, Boolean> DYNATTR_SkipUpdateFromRecipientId = new ModelDynAttributeAccessor<>("SkipUpdateFromRecipientId", Boolean.class);

	private final DocOutBoundRecipientService docOutBoundRecipientService;
	private final OrderEmailPropagationSysConfigRepository orderEmailPropagationSysConfigRepository;
	private final DocOutboundService docOutBoundService;
	private final DocOutboundLogHeaderPopulator docOutboundLogHeaderPopulator;

	public C_Doc_Outbound_Log(@NonNull final DocOutBoundRecipientService docOutBoundRecipientService,
			@NonNull final OrderEmailPropagationSysConfigRepository orderEmailPropagationSysConfigRepo,
			@NonNull final DocOutboundService docOutboundService,
			@NonNull final DocOutboundLogHeaderPopulator docOutboundLogHeaderPopulator)
	{
		this.docOutBoundRecipientService = docOutBoundRecipientService;
		this.orderEmailPropagationSysConfigRepository = orderEmailPropagationSysConfigRepo;
		this.docOutBoundService = docOutboundService;
		this.docOutboundLogHeaderPopulator = docOutboundLogHeaderPopulator;

		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	@CalloutMethod(columnNames = I_C_Doc_Outbound_Log.COLUMNNAME_CurrentEMailRecipient_ID)
	@ModelChange( //
			timings = {ModelValidator.TYPE_BEFORE_CHANGE }, //
			ifColumnsChanged = I_C_Doc_Outbound_Log.COLUMNNAME_CurrentEMailRecipient_ID)
	public void updateFromRecipientId(@NonNull final I_C_Doc_Outbound_Log docOutboundlogRecord)
	{
		if (DYNATTR_SkipUpdateFromRecipientId.getValue(docOutboundlogRecord, false))
		{
			return;
		}

		final DocOutBoundRecipientId userId = DocOutBoundRecipientId.ofRepoIdOrNull(docOutboundlogRecord.getCurrentEMailRecipient_ID());
		if (userId == null)
		{
			docOutboundlogRecord.setCurrentEMailAddress(null);
			return;
		}

		final DocOutBoundRecipient user = docOutBoundRecipientService.getById(userId);

		final String documentEmail = docOutBoundService.getDocumentEmail(docOutboundlogRecord);
		final String userEmailAddress = user.getEmailAddress();

		final boolean propagateToDocOutboundLog = orderEmailPropagationSysConfigRepository.isPropagateToDocOutboundLog(
				ClientAndOrgId.ofClientAndOrg(docOutboundlogRecord.getAD_Client_ID(), docOutboundlogRecord.getAD_Org_ID()));

		if (propagateToDocOutboundLog && (Check.isNotBlank(documentEmail)))
		{
			docOutboundlogRecord.setCurrentEMailAddress(documentEmail);
		}
		else if (!Check.isBlank(userEmailAddress))
		{
			docOutboundlogRecord.setCurrentEMailAddress(userEmailAddress);
		}
		else
		{
			final String locationEmail = docOutBoundService.getLocationEmail(docOutboundlogRecord);
			docOutboundlogRecord.setCurrentEMailAddress(locationEmail);
		}

		docOutboundlogRecord.setIsInvoiceEmailEnabled(user.isInvoiceAsEmail()); // might be true even if the mailaddress is empty!
	}

	// this column is not user-editable, so we don't need it to be a callout
	@ModelChange( //
			timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, //
			ifColumnsChanged = I_C_Doc_Outbound_Log.COLUMNNAME_C_BPartner_ID)
	public void updateFromBPartnerId(@NonNull final I_C_Doc_Outbound_Log docOutboundlogRecord)
	{
		docOutboundLogHeaderPopulator.populateFromBPartner(docOutboundlogRecord);
	}

}
