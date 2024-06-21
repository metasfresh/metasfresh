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

import de.metas.edi.model.I_EDI_Document;
import de.metas.edi.model.I_EDI_Document_Extension;
import de.metas.edi.model.I_M_InOut;
import de.metas.esb.edi.model.I_EDI_Desadv;
import de.metas.esb.edi.model.I_M_InOut_Desadv_V;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.inout.IInOutBL;
import de.metas.inout.InOutId;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.process.rpl.exp.CreateAttachmentRequest;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Env;

import java.util.Collections;
import java.util.List;

public class EDI_DESADV_InOut_Export extends AbstractExport<I_EDI_Document>
{
	private final IInOutBL shipmentBL = Services.get(IInOutBL.class);

	/**
	 * EXP_Format.Value for exporting InOut EDI documents
	 */
	private static final String CST_DESADV_EXP_FORMAT = "EDI_Exp_Desadv";

	public EDI_DESADV_InOut_Export(final I_M_InOut_Desadv_V inoutDesadv, final String tableIdentifier, final ClientId clientId)
	{
		super(InterfaceWrapperHelper.create(inoutDesadv, I_EDI_Document.class), tableIdentifier, clientId);
	}

	@Override
	public List<Exception> doExport()
	{

		final I_EDI_Document document = getDocument();
		final I_M_InOut_Desadv_V desadvInOut = InterfaceWrapperHelper.create(document, I_M_InOut_Desadv_V.class);

		final I_M_InOut shipment = InterfaceWrapperHelper.create(shipmentBL.getById(InOutId.ofRepoId(desadvInOut.getM_InOut_ID())), I_M_InOut.class);
		shipment.setEDI_ExportStatus(I_EDI_Document_Extension.EDI_EXPORTSTATUS_SendingStarted);
		InterfaceWrapperHelper.save(shipment);

		try
		{
			exportEDI(I_M_InOut_Desadv_V.class,
					  EDI_DESADV_InOut_Export.CST_DESADV_EXP_FORMAT,
					  I_M_InOut_Desadv_V.Table_Name,
					  I_M_InOut_Desadv_V.COLUMNNAME_M_InOut_Desadv_ID,
					  CreateAttachmentRequest.builder()
							  .target(TableRecordReference.of(I_EDI_Desadv.Table_Name, shipment.getEDI_Desadv_ID()))
							  .attachmentName(EDI_DESADV_InOut_Export.CST_DESADV_EXP_FORMAT + "_" + shipment.getDocumentNo() + ".xml")
							  .build());
		}
		catch (final Exception e)
		{
			shipment.setEDI_ExportStatus(I_EDI_Document_Extension.EDI_EXPORTSTATUS_Error);

			final ITranslatableString errorMsgTrl = TranslatableStrings.parse(e.getLocalizedMessage());
			shipment.setEDIErrorMsg(errorMsgTrl.translate(Env.getAD_Language()));
			InterfaceWrapperHelper.save(shipment);

			throw AdempiereException.wrapIfNeeded(e);
		}

		return Collections.emptyList();
	}
}
