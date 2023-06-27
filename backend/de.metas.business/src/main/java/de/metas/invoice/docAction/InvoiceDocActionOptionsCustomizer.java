/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.invoice.docAction;

import com.google.common.collect.ImmutableSet;
import de.metas.adempiere.model.I_C_Invoice;
import de.metas.document.engine.DocActionOptionsContext;
import de.metas.document.engine.IDocActionOptionsCustomizer;
import de.metas.document.engine.IDocument;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.location.CountryId;
import de.metas.location.ICountryDAO;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class InvoiceDocActionOptionsCustomizer implements IDocActionOptionsCustomizer
{
	private static final String PARAM_C_Invoice_ID = I_C_Invoice.COLUMNNAME_C_Invoice_ID;
	private static final String PARAM_IsFixedInvoice = I_C_Invoice.COLUMNNAME_IsFixedInvoice;
	private static final ImmutableSet<String> PARAMETERS = ImmutableSet.of(PARAM_C_Invoice_ID, PARAM_IsFixedInvoice);
	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	private final ICountryDAO countryDAO = Services.get(ICountryDAO.class);

	@Override
	public String getAppliesToTableName() {return I_C_Invoice.Table_Name;}

	@Override
	public Set<String> getParameters() {return PARAMETERS;}

	@Override
	public void customizeValidActions(final DocActionOptionsContext optionsCtx)
	{
		final Set<String> docActions = new LinkedHashSet<>(optionsCtx.getDocActions());

		final String docStatus = optionsCtx.getDocStatus();
		final InvoiceId invoiceId = extractInvoiceId(optionsCtx);
		final Optional<CountryId> countryId = invoiceBL.getBillToCountryId(invoiceId);

		final boolean isEnforceCorrectionInvoice;
		isEnforceCorrectionInvoice = countryId.filter(countryDAO::isEnforceCorrectionInvoice).isPresent();

		if (IDocument.STATUS_InProgress.equals(docStatus)
				&& optionsCtx.getSoTrx().isSales()
				&& isEnforceCorrectionInvoice)
		{
			docActions.remove(IDocument.ACTION_Void);
		}

		if (IDocument.STATUS_Drafted.equals(docStatus)
				&& optionsCtx.getSoTrx().isSales()
				&& isEnforceCorrectionInvoice)
		{
			docActions.remove(IDocument.ACTION_Void);
		}

		if (IDocument.STATUS_Completed.equals(docStatus)
				&& optionsCtx.getSoTrx().isSales()
				&& isEnforceCorrectionInvoice)
		{
			docActions.remove(IDocument.ACTION_Reverse_Correct);
			docActions.remove(IDocument.ACTION_ReActivate);
			docActions.remove(IDocument.ACTION_Void);
		}

		if (IDocument.STATUS_Completed.equals(docStatus)
				&& optionsCtx.getSoTrx().isSales()
				&& isEnforceCorrectionInvoice)
		{
			docActions.remove(IDocument.ACTION_Reverse_Correct);
			docActions.remove(IDocument.ACTION_ReActivate);
			docActions.remove(IDocument.ACTION_Void);
		}

		final boolean isFixedInvoice = extractIsFixedInvoice(optionsCtx);
		if (IDocument.STATUS_Completed.equals(docStatus) && isFixedInvoice)
		{
			docActions.remove(IDocument.ACTION_Reverse_Correct);
			docActions.remove(IDocument.ACTION_ReActivate);
			docActions.remove(IDocument.ACTION_Void);
		}

		if (IDocument.STATUS_Closed.equals(docStatus)
				&& optionsCtx.getSoTrx().isSales()
				&& isEnforceCorrectionInvoice)
		{
			docActions.remove(IDocument.ACTION_Void);
		}

		if (IDocument.STATUS_Closed.equals(docStatus) && isFixedInvoice)
		{
			docActions.remove(IDocument.ACTION_Void);
		}

		optionsCtx.setDocActions(ImmutableSet.copyOf(docActions));
	}



	private static InvoiceId extractInvoiceId(final DocActionOptionsContext optionsCtx)
	{
		final String invoiceIdStr = optionsCtx.getParameterValue(PARAM_C_Invoice_ID);
		return InvoiceId.ofRepoId(NumberUtils.asInt(invoiceIdStr));
	}

	private static boolean extractIsFixedInvoice(final DocActionOptionsContext optionsCtx)
	{
		final String isFixedInvoice = optionsCtx.getParameterValue(PARAM_IsFixedInvoice);
		return StringUtils.toBoolean(isFixedInvoice);
	}
}
