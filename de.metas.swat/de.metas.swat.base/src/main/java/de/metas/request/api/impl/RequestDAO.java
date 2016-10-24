package de.metas.request.api.impl;

import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeValue;
import org.compiere.model.I_M_InOut;
import org.compiere.model.X_R_Request;

import de.metas.adempiere.service.IBPartnerOrgBL;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.request.api.IRequestDAO;
import de.metas.request.api.IRequestTypeDAO;
import de.metas.request.model.I_R_Request;

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
	public void createRequestFromInOutLine(final I_M_InOutLine line)
	{
		final IRequestTypeDAO requestTypeDAO = Services.get(IRequestTypeDAO.class);
		if (line == null)
		{
			// Shall not happen. Do nothing
			return;
		}

		if (Check.isEmpty(line.getQualityDiscountPercent()))
		{
			// Shall not happen. Do nothing
			return;
		}

		// Create a new request
		final I_R_Request request = InterfaceWrapperHelper.newInstance(I_R_Request.class, line);

		// ID of the inout header
		final int inOutID = line.getM_InOut_ID();

		// Must have the same org as the inout line
		final int orgID = line.getAD_Org_ID();
		request.setAD_Org_ID(orgID);

		// data from line
		request.setM_InOut_ID(inOutID);
		request.setM_Product_ID(line.getM_Product_ID());

		final String qualityNoteName = line.getQualityNote();

		// set QualityNote based on the string provided in the inout line
		setQualityNote(request, qualityNoteName);

		// data from inout
		final I_M_InOut inOut = line.getM_InOut();

		request.setAD_Table_ID(InterfaceWrapperHelper.getTableId(I_M_InOut.class));
		request.setRecord_ID(inOutID);

		request.setC_BPartner_ID(inOut.getC_BPartner_ID());
		request.setAD_User_ID(inOut.getAD_User_ID());
		request.setDateDelivered(inOut.getMovementDate());

		final Properties ctx = InterfaceWrapperHelper.getCtx(line);

		if (inOut.isSOTrx())
		{
			// customer complaint request type
			request.setR_RequestType(requestTypeDAO.retrieveCustomerRequestType(ctx));
		}
		else
		{
			// vendor complaint request type
			request.setR_RequestType(requestTypeDAO.retrieveVendorRequestType(ctx));
		}

		// Default data
		final IMsgBL msgBL = Services.get(IMsgBL.class);

		// summary from AD_Message
		final String summary = msgBL.getMsg(ctx, MSG_R_Request_From_InOut_Summary);
		request.setSummary(summary);

		// the sales rep will be the user in charge of the organization
		final I_AD_User userInCharge = Services.get(IBPartnerOrgBL.class).retrieveUserInChargeOrNull(ctx, orgID, ITrx.TRXNAME_None);

		if (userInCharge != null)
		{
			request.setSalesRep(userInCharge);
		}

		// confidential type internal
		request.setConfidentialType(X_R_Request.CONFIDENTIALTYPE_Internal);

		// save the request
		InterfaceWrapperHelper.save(request);
	}

	/**
	 * When coming from an inoutLine the quality Note is a String.
	 * This method's purpose is to find the M_AttributeValue entry of the attribute QualityNote that has a similar name and set it in the Request.
	 * 
	 * @param request
	 * @param QualityNoteName
	 */
	private void setQualityNote(final I_R_Request request, final String qualityNoteName)
	{
		if (qualityNoteName == null)
		{
			// Requests can be created from inout lines even if they do not have a quality Notice set but they have a quality Discoutn Percent.
			request.setQualityNote(null);
		}

		else
		{

			final Properties ctx = InterfaceWrapperHelper.getCtx(request);

			final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);

			final I_M_Attribute qualityNoteAttr = attributeDAO.retrieveAttributeByValue(ctx, ATTR_NAME_QualityNotice, I_M_Attribute.class);

			final I_M_AttributeValue attributeValue = attributeDAO.retrieveAttributeValueOrNull_ForName(qualityNoteAttr, qualityNoteName);

			if (attributeValue == null)
			{
				request.setQualityNote(null);
			}

			else
			{

				// set the found value in the Request's qualityNote even if it is null
				request.setQualityNote(attributeValue.getValue());
			}

		}
	}
}
