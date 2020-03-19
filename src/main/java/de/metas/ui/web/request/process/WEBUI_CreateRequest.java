package de.metas.ui.web.request.process;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessExecutionResult.RecordsToOpen.OpenTarget;
import de.metas.process.RunOutOfTrx;
import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.model.DocumentCollection;
import de.metas.ui.web.window.model.IDocumentChangesCollector.ReasonSupplier;
import de.metas.ui.web.window.model.NullDocumentChangesCollector;
import de.metas.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_R_Request;
import org.springframework.beans.factory.annotation.Autowired;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class WEBUI_CreateRequest extends JavaProcess
{
	@Autowired
	private DocumentCollection documentCollection;

	private final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);
	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);

	public WEBUI_CreateRequest()
	{
		SpringContextHolder.instance.autowire(this);
	}

	@Override
	@RunOutOfTrx
	protected String doIt() throws Exception
	{
		final String tableName = getTableName();
		if (I_C_BPartner.Table_Name.equals(tableName))
		{
			final I_C_BPartner bPartner = bPartnerDAO.getById(getProcessInfo().getRecord_ID());
			createRequestFromBPartner(bPartner);
		}
		else if (I_M_InOut.Table_Name.equals(tableName))
		{
			final I_M_InOut shipment = inOutDAO.getById(InOutId.ofRepoId(getProcessInfo().getRecord_ID()));
			createRequestFromShipment(shipment);
		}
		else
		{
			throw new IllegalStateException("Not supported: " + tableName);
		}
		return MSG_OK;
	}

	private void createRequestFromBPartner(final I_C_BPartner bpartner)
	{
		final I_AD_User defaultContact = Services.get(IBPartnerDAO.class).retrieveDefaultContactOrNull(bpartner, I_AD_User.class);

		final ImmutableList.Builder<JSONDocumentChangedEvent> events = ImmutableList.builder();
		events.add(JSONDocumentChangedEvent.replace(I_R_Request.COLUMNNAME_SalesRep_ID, getAD_User_ID()));
		events.add(JSONDocumentChangedEvent.replace(I_R_Request.COLUMNNAME_C_BPartner_ID, bpartner.getC_BPartner_ID()));
		if (defaultContact != null)
		{
			events.add(JSONDocumentChangedEvent.replace(I_R_Request.COLUMNNAME_AD_User_ID, defaultContact.getAD_User_ID()));
		}

		final DocumentPath documentPath = DocumentPath.builder()
				.setDocumentType(WindowConstants.WINDOWID_R_Request)
				.setDocumentId(DocumentId.NEW_ID_STRING)
				.allowNewDocumentId()
				.build();

		final DocumentId documentId = documentCollection.forDocumentWritable(documentPath, NullDocumentChangesCollector.instance, document -> {
			document.processValueChanges(events.build(), ReasonSupplier.NONE);
			return document.getDocumentId();
		});

		getResult().setRecordToOpen(TableRecordReference.of(I_R_Request.Table_Name, documentId.toInt()), documentPath.getWindowId().toInt(), OpenTarget.SingleDocumentModal);
	}

	private void createRequestFromShipment(final I_M_InOut shipment)
	{

		final I_C_BPartner bPartner = bPartnerDAO.getById(shipment.getC_BPartner_ID());
		final I_AD_User defaultContact = Services.get(IBPartnerDAO.class).retrieveDefaultContactOrNull(bPartner, I_AD_User.class);

		final ImmutableList.Builder<JSONDocumentChangedEvent> events = ImmutableList.builder();
		events.add(JSONDocumentChangedEvent.replace(I_R_Request.COLUMNNAME_SalesRep_ID, getAD_User_ID()));
		events.add(JSONDocumentChangedEvent.replace(I_R_Request.COLUMNNAME_C_BPartner_ID, shipment.getC_BPartner_ID()));
		events.add(JSONDocumentChangedEvent.replace(I_R_Request.COLUMNNAME_M_InOut_ID, shipment.getM_InOut_ID()));
		events.add(JSONDocumentChangedEvent.replace(I_R_Request.COLUMNNAME_DateDelivered, shipment.getMovementDate()));
		if (defaultContact != null)
		{
			events.add(JSONDocumentChangedEvent.replace(I_R_Request.COLUMNNAME_AD_User_ID, defaultContact.getAD_User_ID()));
		}

		final DocumentPath documentPath = DocumentPath.builder()
				.setDocumentType(WindowConstants.WINDOWID_R_Request)
				.setDocumentId(DocumentId.NEW_ID_STRING)
				.allowNewDocumentId()
				.build();

		final DocumentId documentId = documentCollection.forDocumentWritable(documentPath, NullDocumentChangesCollector.instance, document -> {
			document.processValueChanges(events.build(), ReasonSupplier.NONE);
			return document.getDocumentId();
		});

		getResult().setRecordToOpen(TableRecordReference.of(I_R_Request.Table_Name, documentId.toInt()), documentPath.getWindowId().toInt(), OpenTarget.SingleDocumentModal);
	}
}
