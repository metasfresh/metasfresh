package de.metas.edi.process.export.impl;

/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.util.Collections;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.util.Env;

import de.metas.edi.api.IEDIDocumentBL;
import de.metas.edi.api.ValidationState;
import de.metas.edi.model.I_C_Invoice;
import de.metas.edi.model.I_EDI_Document_Extension;
import de.metas.esb.edi.model.I_EDI_cctop_invoic_v;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.util.Services;

public class C_InvoiceExport extends AbstractEdiDocExtensionExport<I_C_Invoice>
{
	/**
	 * EXP_Format.Value for exporting Invoice EDI documents
	 */
	private static final String CST_INVOICE_EXP_FORMAT = "EDI_cctop_invoic_v";

	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	public C_InvoiceExport(final I_C_Invoice invoice, final String tableIdentifier, final ClientId clientId)
	{
		super(invoice, tableIdentifier, clientId);
	}

	@Override
	public List<Exception> doExport()
	{
		final IEDIDocumentBL ediDocumentBL = Services.get(IEDIDocumentBL.class);

		final I_C_Invoice document = getDocument();

		final List<Exception> feedback = ediDocumentBL.isValidInvoice(document);

		final String EDIStatus = document.getEDI_ExportStatus();
		final ValidationState validationState = ediDocumentBL.updateInvalid(document, EDIStatus, feedback, true); // saveLocally=true
		if (ValidationState.ALREADY_VALID != validationState)
		{
			// otherwise, it's either INVALID, or freshly updated (which, keeping the old logic, must be dealt with in one more step)
			return feedback;
		}
		assertEligible(document);

		// Mark the invoice as: EDI starting
		document.setEDI_ExportStatus(I_EDI_Document_Extension.EDI_EXPORTSTATUS_SendingStarted);
		InterfaceWrapperHelper.save(document);

		try
		{
			exportEDI(I_EDI_cctop_invoic_v.class, C_InvoiceExport.CST_INVOICE_EXP_FORMAT, I_EDI_cctop_invoic_v.Table_Name, I_EDI_cctop_invoic_v.COLUMNNAME_C_Invoice_ID);
		}
		catch (final Exception e)
		{
			document.setEDI_ExportStatus(I_EDI_Document_Extension.EDI_EXPORTSTATUS_Error);

			final ITranslatableString errorMsgTrl = TranslatableStrings.parse(e.getLocalizedMessage());
			document.setEDIErrorMsg(errorMsgTrl.translate(Env.getAD_Language()));

			InterfaceWrapperHelper.save(document);

			throw AdempiereException
					.wrapIfNeeded(e)
					.markAsUserValidationError();
		}

		return Collections.emptyList();
	}
}
