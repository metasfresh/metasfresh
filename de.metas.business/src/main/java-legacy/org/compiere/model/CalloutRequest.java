/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.model;

import java.util.Properties;

import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.util.Env;

import de.metas.email.IMailBL;
import de.metas.email.IMailTextBuilder;

/**
 * R_Request Callouts
 *
 * @author based on original CalloutRequest class developed by: Jorg Janke
 * @author metasfresh
 */
public class CalloutRequest extends CalloutEngine
{
	/**
	 * Request - Copy Mail Text
	 *
	 * Triggered by {@link I_R_Request#COLUMN_R_MailText_ID}.
	 */
	public String copyMail(final ICalloutField calloutField)
	{
		final I_R_Request request = calloutField.getModel(I_R_Request.class);

		final int R_MailText_ID = request.getR_MailText_ID();
		if (R_MailText_ID <= 0)
		{
			return NO_ERROR;
		}

		final Properties ctx = calloutField.getCtx();
		final IMailTextBuilder mailTextBuilder = Services.get(IMailBL.class)
				.newMailTextBuilder(InterfaceWrapperHelper.create(ctx, R_MailText_ID, I_R_MailText.class, ITrx.TRXNAME_None));

		final int userId = request.getAD_User_ID();
		if (userId > 0)
		{
			mailTextBuilder.setAD_User(userId);
		}
		final int bpartnerId = request.getC_BPartner_ID();
		if (bpartnerId > 0)
		{
			mailTextBuilder.setC_BPartner(bpartnerId);
		}

		String txt = mailTextBuilder.getMailText();
		txt = Env.parseContext(ctx, calloutField.getWindowNo(), txt, false, true);
		request.setResult(txt);

		return NO_ERROR;
	}

	/**
	 * Request - Copy Response Text
	 *
	 * Triggered by {@link I_R_Request#COLUMN_R_StandardResponse_ID}.
	 */
	public String copyResponse(final ICalloutField calloutField)
	{
		final I_R_Request request = calloutField.getModel(I_R_Request.class);

		final I_R_StandardResponse standardResponse = request.getR_StandardResponse();
		if (standardResponse == null)
		{
			return NO_ERROR;
		}

		String txt = standardResponse.getResponseText();
		txt = Env.parseContext(calloutField.getCtx(), calloutField.getWindowNo(), txt, false, true);
		request.setResult(txt);

		return NO_ERROR;
	}

	/**
	 * Request - Change of Request Type
	 *
	 * Triggered by {@link I_R_Request#COLUMN_R_RequestType_ID}.
	 */
	public String type(final ICalloutField calloutField)
	{
		final I_R_Request request = calloutField.getModel(I_R_Request.class);

		request.setR_Status(null);

		final int R_RequestType_ID = request.getR_RequestType_ID();
		if (R_RequestType_ID <= 0)
		{
			return NO_ERROR;
		}

		final MRequestType requestType = MRequestType.get(calloutField.getCtx(), R_RequestType_ID);
		final int R_Status_ID = requestType.getDefaultR_Status_ID();
		if (R_Status_ID > 0)
		{
			request.setR_Status_ID(R_Status_ID);
		}

		return NO_ERROR;
	}
}
