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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Collections;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.edi.api.IEDIDocumentBL;
import de.metas.edi.api.ValidationState;
import de.metas.edi.model.I_EDI_Document_Extension;
import de.metas.edi.model.I_M_InOut;
import de.metas.util.Services;

public class M_InOutExport extends AbstractEdiDocExtensionExport<I_M_InOut>
{
	/**
	 * EXP_Format.Value for exporting InOut EDI documents
	 */
	private static final String CST_INOUT_EXP_FORMAT = "EDI_Exp_M_InOut";

	public M_InOutExport(final I_M_InOut inOut, final String tableIdentifier, final int clientId)
	{
		super(inOut, tableIdentifier, clientId);
	}

	@Override
	public List<Exception> createExport()
	{
		final IEDIDocumentBL ediDocumentBL = Services.get(IEDIDocumentBL.class);

		final I_M_InOut document = getDocument();

		final List<Exception> feedback = ediDocumentBL.isValidInOut(document);

		final String EDIStatus = document.getEDI_ExportStatus();
		final ValidationState validationState = ediDocumentBL.updateInvalid(document, EDIStatus, feedback, true); // saveLocally=true
		if (ValidationState.ALREADY_VALID != validationState)
		{
			// otherwise, it's either INVALID, or freshly updated (which, keeping the old logic, must be dealt with in one more step)
			return feedback;
		}
		assertEligible(document);

		// Mark the InOut as: EDI starting
		document.setEDI_ExportStatus(I_EDI_Document_Extension.EDI_EXPORTSTATUS_SendingStarted);
		InterfaceWrapperHelper.save(document);

		try
		{
			exportEDI(I_M_InOut.class, M_InOutExport.CST_INOUT_EXP_FORMAT, org.compiere.model.I_M_InOut.Table_Name, org.compiere.model.I_M_InOut.COLUMNNAME_M_InOut_ID);
		}
		catch (final Exception e)
		{
			final String errmsg = e.getLocalizedMessage();
			document.setEDI_ExportStatus(I_EDI_Document_Extension.EDI_EXPORTSTATUS_Error);
			document.setEDIErrorMsg(errmsg);
			InterfaceWrapperHelper.save(document);

			throw new AdempiereException(e);
		}

		return Collections.emptyList();
	}
}
