package de.metas.edi.process.export.impl;

/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2024 metas GmbH
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
import de.metas.edi.api.EDIDesadvId;
import de.metas.edi.api.EDIExportStatus;
import de.metas.edi.api.EDIType;
import de.metas.edi.api.IDesadvDAO;
import de.metas.edi.api.impl.EDIDesadvInOutRepository;
import de.metas.edi.model.I_EDI_Document;
import de.metas.edi.model.I_M_InOut;
import de.metas.esb.edi.model.I_EDI_Desadv;
import de.metas.esb.edi.model.I_M_InOut_Desadv_V;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.inout.IInOutBL;
import de.metas.inout.InOutId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.process.rpl.exp.CreateAttachmentRequest;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.util.Env;

import java.util.Collections;
import java.util.List;

public class EDI_DESADV_InOut_Export extends AbstractExport<I_EDI_Document>
{
	private final IInOutBL inOutBL = Services.get(IInOutBL.class);
	private final IDesadvDAO desadvDAO = Services.get(IDesadvDAO.class);
	private final EDIDesadvInOutRepository ediDesadvInOutRepository = SpringContextHolder.instance.getBean(EDIDesadvInOutRepository.class);

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
		final I_M_InOut shipment = getShipmentRecord();
		shipment.setEDI_ExportStatus(EDIExportStatus.SendingStarted.getCode());
		InterfaceWrapperHelper.save(shipment);

		try
		{
			exportEDI(I_M_InOut_Desadv_V.class,
					  EDI_DESADV_InOut_Export.CST_DESADV_EXP_FORMAT,
					  I_M_InOut_Desadv_V.Table_Name,
					  I_M_InOut_Desadv_V.COLUMNNAME_M_InOut_ID,
					  CreateAttachmentRequest.builder()
							  .target(TableRecordReference.of(I_EDI_Desadv.Table_Name, shipment.getEDI_Desadv_ID()))
							  .attachmentName(EDI_DESADV_InOut_Export.CST_DESADV_EXP_FORMAT + "_" + shipment.getDocumentNo() + ".xml")
							  .build());
		}
		catch (final Exception e)
		{
			final ITranslatableString errorMsgTrl = TranslatableStrings.parse(e.getLocalizedMessage());
			final String errorMsg = errorMsgTrl.translate(Env.getAD_Language());

			// Flip status to Error on every DESADV linked to this (potentially consolidated) shipment via the junction table.
			// A consolidated shipment can carry N DESADVs (one per aggregated order); the shipment-level EDI_Desadv_ID
			// reflects only one of them and must not be used as the sole status carrier.
			for (final EDIDesadvId desadvId : ediDesadvInOutRepository.listDesadvsForInOut(InOutId.ofRepoId(shipment.getM_InOut_ID())))
			{
				final I_EDI_Desadv desadv = desadvDAO.retrieveById(desadvId);
				desadv.setEDI_ExportStatus(EDIExportStatus.Error.getCode());
				InterfaceWrapperHelper.save(desadv);
			}

			shipment.setEDI_ExportStatus(EDIExportStatus.Error.getCode());
			shipment.setEDIErrorMsg(errorMsg);
			InterfaceWrapperHelper.save(shipment);

			throw AdempiereException.wrapIfNeeded(e);
		}

		return Collections.emptyList();
	}

	@Override
	@NonNull
	public BPartnerId getBPartnerId()
	{
		return inOutBL.getEffectiveDropshipPartnerId(getShipmentRecord());
	}

	@NonNull
	private I_M_InOut getShipmentRecord()
	{
		final I_M_InOut_Desadv_V desadvInOut = InterfaceWrapperHelper.create(getDocument(), I_M_InOut_Desadv_V.class);
		return InterfaceWrapperHelper.create(inOutBL.getById(InOutId.ofRepoId(desadvInOut.getM_InOut_ID())), I_M_InOut.class);
	}

	@Override
	@NonNull
	public EDIType getEDIType()
	{
		return EDIType.DESADV;
	}
}
