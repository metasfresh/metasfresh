package de.metas.request.callout;

import java.util.Properties;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_R_MailText;
import org.compiere.model.I_R_Request;
import org.compiere.model.I_R_StandardResponse;
import org.compiere.model.MRequestType;
import org.compiere.util.Env;

import de.metas.email.IMailBL;
import de.metas.email.IMailTextBuilder;

/*
 * #%L
 * de.metas.business
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 *
 * @author based on original org.compiere.model.CalloutRequest class developed by: Jorg Janke
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Callout(I_R_Request.class)
public class R_Request
{
	@CalloutMethod(columnNames = I_R_Request.COLUMNNAME_R_MailText_ID)
	public void onR_MailText_ID(final I_R_Request request, final ICalloutField calloutField)
	{
		final int R_MailText_ID = request.getR_MailText_ID();
		if (R_MailText_ID <= 0)
		{
			return;
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
	}

	@CalloutMethod(columnNames = I_R_Request.COLUMNNAME_R_StandardResponse_ID)
	public void onR_StandardResponse_ID(final I_R_Request request, final ICalloutField calloutField)
	{
		final I_R_StandardResponse standardResponse = request.getR_StandardResponse();
		if (standardResponse == null)
		{
			return;
		}

		String txt = standardResponse.getResponseText();
		txt = Env.parseContext(calloutField.getCtx(), calloutField.getWindowNo(), txt, false, true);
		request.setResult(txt);
	}

	@CalloutMethod(columnNames = I_R_Request.COLUMNNAME_R_RequestType_ID)
	public void onR_RequestType_ID(final I_R_Request request)
	{
		request.setR_Status(null);

		final int R_RequestType_ID = request.getR_RequestType_ID();
		if (R_RequestType_ID <= 0)
		{
			return;
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(request);
		final MRequestType requestType = MRequestType.get(ctx, R_RequestType_ID);
		final int R_Status_ID = requestType.getDefaultR_Status_ID();
		if (R_Status_ID > 0)
		{
			request.setR_Status_ID(R_Status_ID);
		}
	}

}
