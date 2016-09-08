package de.metas.request.api.impl;

import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_M_InOut;

import de.metas.adempiere.service.IBPartnerOrgBL;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.request.api.IRequestDAO;
import de.metas.request.api.IRequestTypeDAO;
import de.metas.request.model.I_R_Request;
import de.metas.request.service.async.spi.impl.C_Request_CreateFromInout;

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
	@Override
	public void createRequestFromInOutLine(final I_M_InOutLine line)
	{
		final IRequestTypeDAO requestTypeDAO = Services.get(IRequestTypeDAO.class);
		if (line == null)
		{
			// Shall not happen. Do nothing
			return;
		}

		if (Check.isEmpty(line.getQualityNote()))
		{
			// Shall not happen. Do nothing
			return;
		}

		final I_R_Request request = InterfaceWrapperHelper.newInstance(I_R_Request.class, line);

		request.setAD_Org_ID(line.getAD_Org_ID());

		// data from line
		request.setM_InOut_ID(line.getM_InOut_ID());
		request.setM_Product_ID(line.getM_Product_ID());
		request.setQualityNote(line.getQualityNote());

		// data from inout
		final I_M_InOut inOut = line.getM_InOut();

		request.setC_BPartner_ID(inOut.getC_BPartner_ID());
		request.setDateDelivered(inOut.getMovementDate());

		final Properties ctx = InterfaceWrapperHelper.getCtx(line);

		if (inOut.isSOTrx())
		{
			// vendor complaint request type
			request.setR_RequestType(requestTypeDAO.retrieveVendorRequestType(ctx));
		}
		else
		{
			// customer complaint request type
			request.setR_RequestType(requestTypeDAO.retrieveCustomerRequestType(ctx));
		}

		// TODO :ASK FOR MESSAGE
		request.setSummary(line.getQualityNote());

		// TODO whi shall this be?
		final I_AD_User userInCharge = Services.get(IBPartnerOrgBL.class).retrieveUserInChargeOrNull(ctx, line.getAD_Org_ID(), ITrx.TRXNAME_None);

		if (userInCharge != null)
		{
			request.setSalesRep(userInCharge);
		}
		InterfaceWrapperHelper.save(request);
	}

	@Override
	public void createRequest(final Properties ctx, final Set<Integer> inOutLineIds, final String trxName)
	{
		C_Request_CreateFromInout.createWorkpackage(ctx, inOutLineIds, trxName);

	}

}
