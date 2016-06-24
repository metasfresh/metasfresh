package de.metas.rfq.impl;

import java.io.File;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.compiere.model.I_AD_User;
import org.compiere.model.MClient;
import org.compiere.print.ReportEngine;
import org.compiere.util.EMail;
import org.slf4j.Logger;

import com.google.common.base.Joiner;

import de.metas.logging.LogManager;
import de.metas.rfq.IRfQResponsePublisher;
import de.metas.rfq.model.I_C_RfQResponse;

/*
 * #%L
 * de.metas.rfq
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

public class MailRfqResponsePublisher implements IRfQResponsePublisher
{
	public static final transient MailRfqResponsePublisher instance = new MailRfqResponsePublisher();

	private static final Logger logger = LogManager.getLogger(MailRfqResponsePublisher.class);

	private MailRfqResponsePublisher()
	{
		super();
	}

	@Override
	public void publish(final I_C_RfQResponse response)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(response);
		final I_AD_User userTo = response.getAD_User();
		if (userTo == null)
		{
			logger.error("Skip sending RfQ response mail because there is no user for {}", response);
			return; // false;
		}
		final String userEmail = userTo.getEMail();
		if (Check.isEmpty(userEmail, true))
		{
			logger.error("Skip sending RfQ response mail because user {} has no email: {}", response);
			return; // false;
		}

		//
		final String subject = "RfQ: " + response.getName();

		//
		String message = Joiner.on("\n")
				.skipNulls()
				.join(response.getDescription(), response.getHelp());
		if (message == null)
		{
			message = response.getName();
		}

		//
		// Send it
		final MClient client = MClient.get(ctx);
		final EMail email = client.createEMail(userEmail, subject, message);
		email.addAttachment(createPDF(response));
		if (EMail.SENT_OK.equals(email.send()))
		{
			response.setDateInvited(new Timestamp(System.currentTimeMillis()));
			InterfaceWrapperHelper.save(response);
			return; // true;
		}

		// return false;
	}

	private File createPDF(final I_C_RfQResponse response)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(response);
		final ReportEngine re = ReportEngine.get(ctx, ReportEngine.RFQ, response.getC_RfQResponse_ID());
		if (re == null)
		{
			return null;
		}
		return re.getPDF();
	}
}
