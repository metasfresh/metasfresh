package de.metas.request.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.getTableId;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_InOut;
import org.compiere.model.X_R_Request;
import org.compiere.util.Env;
import org.eevolution.model.I_DD_OrderLine;

import de.metas.i18n.IMsgBL;
import de.metas.inout.api.IQualityNoteDAO;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.inout.model.I_M_QualityNote;
import de.metas.request.api.IRequestDAO;
import de.metas.request.api.IRequestTypeDAO;
import de.metas.request.model.I_R_Request;
import lombok.NonNull;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class RequestDAO implements IRequestDAO
{
	public static final String MSG_R_Request_From_InOut_Summary = "R_Request_From_InOut_Summary";

	public static final String ATTR_NAME_QualityNotice = "QualityNotice";

	@Override
	public void createRequestFromInOutLine(@NonNull final I_M_InOutLine line)
	{
		if (Check.isEmpty(line.getQualityDiscountPercent()))
		{
			// Shall not happen. Do nothing
			return;
		}

		// Create a new request
		final I_R_Request request = InterfaceWrapperHelper.newInstance(I_R_Request.class, line);

		setDefaultRequestData(request);
		updateRequestFromInOutLine(request, line);
		
		save(request);
	}

	private void setDefaultRequestData(final I_R_Request request)
	{
		final IMsgBL msgBL = Services.get(IMsgBL.class);

		// summary from AD_Message
		final String summary = msgBL.getMsg(Env.getCtx(), MSG_R_Request_From_InOut_Summary);
		request.setSummary(summary);

		// confidential type internal
		request.setConfidentialType(X_R_Request.CONFIDENTIALTYPE_Internal);
	}

	private void updateRequestFromInOutLine(final I_R_Request request, final I_M_InOutLine line)
	{
		request.setAD_Org_ID(line.getAD_Org_ID());
		request.setM_Product_ID(line.getM_Product_ID());

		final I_M_InOut inOut = line.getM_InOut();
		updateRequestFromInout(request, inOut);

		updateQualityNote(request, line.getM_AttributeSetInstance());

	}

	private void updateQualityNote(final I_R_Request request, final I_M_AttributeSetInstance asiLine)
	{
		final IQualityNoteDAO qualityNoteDAO = Services.get(IQualityNoteDAO.class);

		// find the quality note M_Attribute
		final I_M_Attribute qualityNoteAttribute = qualityNoteDAO.getQualityNoteAttribute(Env.getCtx());

		if (qualityNoteAttribute == null)
		{
			// nothing to do. Quality Note attribute not defined

			return;
		}

		final I_M_AttributeInstance qualityNoteAI = Services.get(IAttributeDAO.class).retrieveAttributeInstance(asiLine, qualityNoteAttribute.getM_Attribute_ID());

		if (qualityNoteAI == null)
		{
			// nothing to do
			return;
		}

		final String qualityNoteValueFromASI = qualityNoteAI.getValue();

		if (Check.isEmpty(qualityNoteValueFromASI))
		{
			// nothing to do
			return;
		}

		final I_M_QualityNote qualityNote = qualityNoteDAO.retrieveQualityNoteForValue(Env.getCtx(), qualityNoteValueFromASI);

		Check.assumeNotNull(qualityNote, "QualityNote not nul");

		// set the qualityNote to the request.
		// Note: If the inout line on which the request is based has more than one qualityNotes, only the first one is set into the request
		request.setM_QualityNote(qualityNote);

		// in case there is a qualitynote set, also set the Performance type based on it
		request.setPerformanceType(qualityNote.getPerformanceType());
	}

	private void updateRequestFromInout(final I_R_Request request, final I_M_InOut inOut)
	{
		final IRequestTypeDAO requestTypeDAO = Services.get(IRequestTypeDAO.class);

		request.setAD_Table_ID(getTableId(I_M_InOut.class));
		request.setRecord_ID(inOut.getM_InOut_ID());

		request.setC_BPartner_ID(inOut.getC_BPartner_ID());
		request.setAD_User_ID(inOut.getAD_User_ID());
		request.setDateDelivered(inOut.getMovementDate());

		if (inOut.isSOTrx())
		{
			// customer complaint request type
			request.setR_RequestType(requestTypeDAO.retrieveCustomerRequestType());
		}
		else
		{
			// vendor complaint request type
			request.setR_RequestType(requestTypeDAO.retrieveVendorRequestType());
		}

	}

	@Override
	public void createRequestFromDDOrderLine(I_DD_OrderLine line)
	{
		// TODO Auto-generated method stub

	}

}
