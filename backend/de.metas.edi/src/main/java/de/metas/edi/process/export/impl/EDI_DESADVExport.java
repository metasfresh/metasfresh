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

import de.metas.bpartner.BPartnerId;
import de.metas.edi.api.EDIExportStatus;
import de.metas.edi.api.EDIType;
import de.metas.edi.api.ValidationState;
import de.metas.edi.api.impl.DesadvBL;
import de.metas.edi.api.impl.EDIDocumentBL;
import de.metas.edi.model.I_EDI_Document;
import de.metas.esb.edi.model.I_EDI_Desadv;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.inout.IInOutBL;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.SpringContextHolder;
import org.compiere.util.Env;

import java.util.Collections;
import java.util.List;

public class EDI_DESADVExport extends AbstractExport<I_EDI_Document>
{
	/**
	 * EXP_Format.Value for exporting InOut EDI documents
	 */
	private static final String CST_DESADV_EXP_FORMAT = "EDI_Exp_Desadv";

	private final IInOutBL shipmentBL = Services.get(IInOutBL.class);

	@NonNull private final EDIDocumentBL ediDocumentBL = SpringContextHolder.instance.getBean(EDIDocumentBL.class);
	@NonNull private final DesadvBL desadvBL = SpringContextHolder.instance.getBean(DesadvBL.class);

	public EDI_DESADVExport(final I_EDI_Desadv desadv, final String tableIdentifier, final ClientId clientId)
	{
		super(InterfaceWrapperHelper.create(desadv, I_EDI_Document.class), tableIdentifier, clientId);
	}

	@Override
	public List<Exception> doExport()
	{
		final I_EDI_Document document = getDocument();
		final I_EDI_Desadv desadv = getDesadvRecord();

		final List<Exception> feedback = ediDocumentBL.isValidDesAdv(desadv);

		final EDIExportStatus EDIStatus = EDIExportStatus.ofCode(document.getEDI_ExportStatus());
		final ValidationState validationState = ediDocumentBL.updateInvalid(document, EDIStatus, feedback, true); // saveLocally=true
		if (!validationState.isAlreadyValid())
		{
			// otherwise, it's either INVALID, or freshly updated (which, keeping the old logic, must be dealt with in one more step)
			return feedback;
		}

		// Mark the document as: EDI starting
		document.setEDI_ExportStatus(EDIExportStatus.SendingStarted.getCode());
		InterfaceWrapperHelper.save(document);

		desadvBL.retrieveAllInOuts(desadv)
				.stream()
				.peek(shipment -> shipment.setEDI_ExportStatus(EDIExportStatus.SendingStarted.getCode()))
				.forEach(shipmentBL::save);

		try
		{
			exportEDI(I_EDI_Desadv.class, EDI_DESADVExport.CST_DESADV_EXP_FORMAT, I_EDI_Desadv.Table_Name, I_EDI_Desadv.COLUMNNAME_EDI_Desadv_ID);
		}
		catch (final Exception e)
		{
			document.setEDI_ExportStatus(EDIExportStatus.Error.getCode());

			final ITranslatableString errorMsgTrl = TranslatableStrings.parse(e.getLocalizedMessage());
			document.setEDIErrorMsg(errorMsgTrl.translate(Env.getAD_Language()));
			InterfaceWrapperHelper.save(document);

			throw AdempiereException
					.wrapIfNeeded(e)
					.markAsUserValidationError();
		}

		return Collections.emptyList();
	}

	@Override
	@NonNull
	public BPartnerId getBPartnerId()
	{
		return desadvBL.getEffectiveDropshipPartnerId(getDesadvRecord());
	}

	@NonNull
	private I_EDI_Desadv getDesadvRecord()
	{
		return InterfaceWrapperHelper.create(getDocument(), I_EDI_Desadv.class);
	}

	@Override
	@NonNull
	public EDIType getEDIType()
	{
		return EDIType.DESADV;
	}
}
