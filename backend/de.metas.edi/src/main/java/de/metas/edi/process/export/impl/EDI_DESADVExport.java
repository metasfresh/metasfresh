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
import de.metas.edi.model.I_EDI_Document;
import de.metas.edi.model.I_EDI_Document_Extension;
import de.metas.esb.edi.model.I_EDI_Desadv;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.util.Services;

public class EDI_DESADVExport extends AbstractExport<I_EDI_Document>
{
	/**
	 * EXP_Format.Value for exporting InOut EDI documents
	 */
	private static final String CST_DESADV_EXP_FORMAT = "EDI_Exp_Desadv";

	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	public EDI_DESADVExport(final I_EDI_Desadv desadv, final String tableIdentifier, final ClientId clientId)
	{
		super(InterfaceWrapperHelper.create(desadv, I_EDI_Document.class), tableIdentifier, clientId);
	}

	@Override
	public List<Exception> doExport()
	{
		final IEDIDocumentBL ediDocumentBL = Services.get(IEDIDocumentBL.class);

		final I_EDI_Document document = getDocument();

		final I_EDI_Desadv desadv = InterfaceWrapperHelper.create(document, I_EDI_Desadv.class);

		final List<Exception> feedback = ediDocumentBL.isValidDesAdv(desadv);

		final String EDIStatus = document.getEDI_ExportStatus();
		final ValidationState validationState = ediDocumentBL.updateInvalid(document, EDIStatus, feedback, true); // saveLocally=true
		if (ValidationState.ALREADY_VALID != validationState)
		{
			// otherwise, it's either INVALID, or freshly updated (which, keeping the old logic, must be dealt with in one more step)
			return feedback;
		}

		// Mark the document as: EDI starting
		document.setEDI_ExportStatus(I_EDI_Document_Extension.EDI_EXPORTSTATUS_SendingStarted);
		InterfaceWrapperHelper.save(document);

		try
		{
			exportEDI(I_EDI_Desadv.class, EDI_DESADVExport.CST_DESADV_EXP_FORMAT, I_EDI_Desadv.Table_Name, I_EDI_Desadv.COLUMNNAME_EDI_Desadv_ID);
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
