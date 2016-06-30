package de.metas.rfq.impl;

import java.sql.Timestamp;

import org.adempiere.archive.api.IArchiveEventManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.ILoggable;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_User;
import org.compiere.util.ByteArrayDataSource;
import org.compiere.util.EMail;
import org.slf4j.Logger;

import de.metas.document.archive.model.I_AD_Archive;
import de.metas.document.archive.model.X_C_Doc_Outbound_Log_Line;
import de.metas.document.archive.spi.impl.DefaultModelArchiver;
import de.metas.logging.LogManager;
import de.metas.notification.IMailBL;
import de.metas.notification.IMailTextBuilder;
import de.metas.rfq.IRfqBL;
import de.metas.rfq.IRfqDAO;
import de.metas.rfq.model.I_C_RfQResponse;
import de.metas.rfq.model.I_C_RfQ_Topic;

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

/* package */class MailRfqResponsePublisherInstance
{
	public static final MailRfqResponsePublisherInstance newInstance()
	{
		return new MailRfqResponsePublisherInstance();
	}

	// services
	private static final transient Logger logger = LogManager.getLogger(MailRfqResponsePublisherInstance.class);
	private final transient IRfqBL rfqBL = Services.get(IRfqBL.class);
	private final transient IRfqDAO rfqDAO = Services.get(IRfqDAO.class);
	private final transient IMailBL mailBL = Services.get(IMailBL.class);
	private final transient IArchiveEventManager archiveEventManager = Services.get(IArchiveEventManager.class);

	public static enum RfQReportType
	{
		Invitation, Won, Lost,
	};

	private MailRfqResponsePublisherInstance()
	{
		super();
	}

	public void publish(final I_C_RfQResponse rfqResponse)
	{
		final RfQReportType rfqReportType = getRfQReportType(rfqResponse);
		publish(rfqResponse, rfqReportType);

	}

	public void publish(final I_C_RfQResponse rfqResponse, final RfQReportType rfqReportType)
	{
		//
		// Check and get the user's mail where we will send the email to
		final I_AD_User userTo = rfqResponse.getAD_User();
		if (userTo == null)
		{
			logError("@Error@ {}: @NotFound@ @AD_User_ID@", rfqBL.getSummary(rfqResponse));
			return;
		}
		final String userToEmail = userTo.getEMail();
		if (Check.isEmpty(userToEmail, true))
		{
			logError("@Error@ {}: @NotFound@ @AD_User_ID@ @Email@ - ", rfqBL.getSummary(rfqResponse), userTo);
			return; // false;
		}

		//
		final IMailTextBuilder mailTextBuilder = createMailTextBuilder(rfqResponse, rfqReportType);

		//
		final String subject = mailTextBuilder.getMailHeader();
		final String message = mailTextBuilder.getFullMailText();
		final DefaultModelArchiver archiver = DefaultModelArchiver.of(rfqResponse)
				.setAD_PrintFormat_ID(getAD_PrintFormat_ID(rfqResponse, rfqReportType));
		final I_AD_Archive pdfArchive = archiver.archive();
		final byte[] pdfData = archiver.getPdfData();

		//
		// Send it
		final EMail email = mailBL.createEMail(
				rfqResponse.getAD_Client() //
				, (String)null // mailCustomType
				, (I_AD_User)null // from
				, userToEmail // to
				, subject, message, mailTextBuilder.isHtml() // html
		);
		email.addAttachment(ByteArrayDataSource.of("rfq.pdf", pdfData));
		email.send();

		//
		// Fire mail sent/not sent event (even if there were some errors)
		archiveEventManager.fireEmailSent(
				pdfArchive // archive
				, X_C_Doc_Outbound_Log_Line.ACTION_EMail // action
				, (I_AD_User)null // user
				, email.getFrom().getAddress() // from
				, email.getTo().getAddress() // to
				, (String)null // cc
				, (String)null // bcc
				, email.getSentMsg() // status
		);

		//
		// Update RfQ response (if success)
		if (email.isSentOK())
		{
			rfqResponse.setDateInvited(new Timestamp(System.currentTimeMillis()));
			InterfaceWrapperHelper.save(rfqResponse);
		}
		else
		{
			// TODO: log errors etc. handle connection exceptions!!!
		}
	}

	public RfQReportType getRfQReportType(final I_C_RfQResponse rfqResponse)
	{

		if (rfqBL.isDraft(rfqResponse))
		{
			return RfQReportType.Invitation;
		}
		else if (rfqDAO.hasSelectedWinnerLines(rfqResponse))
		{
			return RfQReportType.Won;
		}
		else
		{
			return RfQReportType.Lost;
		}
	}

	private int getAD_PrintFormat_ID(final I_C_RfQResponse rfqResponse, final RfQReportType rfqReportType)
	{
		final I_C_RfQ_Topic rfqTopic = rfqResponse.getC_RfQ().getC_RfQ_Topic();

		if (rfqReportType == RfQReportType.Invitation)
		{
			return rfqTopic.getRfQ_Invitation_PrintFormat_ID();
		}
		else if (rfqReportType == RfQReportType.Won)
		{
			return rfqTopic.getRfQ_Win_PrintFormat_ID();
		}
		else if (rfqReportType == RfQReportType.Lost)
		{
			return rfqTopic.getRfQ_Lost_PrintFormat_ID();
		}
		else
		{
			throw new AdempiereException("@Invalid@ @Type@: " + rfqReportType);
		}
	}

	private IMailTextBuilder createMailTextBuilder(final I_C_RfQResponse rfqResponse, final RfQReportType rfqReportType)
	{
		final I_C_RfQ_Topic rfqTopic = rfqResponse.getC_RfQ().getC_RfQ_Topic();

		final IMailTextBuilder mailTextBuilder;
		if (rfqReportType == RfQReportType.Invitation)
		{
			mailTextBuilder = mailBL.newMailTextBuilder(rfqTopic.getRfQ_Invitation_MailText());
		}
		else if (rfqReportType == RfQReportType.Won)
		{
			mailTextBuilder = mailBL.newMailTextBuilder(rfqTopic.getRfQ_Win_MailText());
		}
		else if (rfqReportType == RfQReportType.Lost)
		{
			mailTextBuilder = mailBL.newMailTextBuilder(rfqTopic.getRfQ_Lost_MailText());
		}
		else
		{
			throw new AdempiereException("@Invalid@ @Type@: " + rfqReportType);
		}

		mailTextBuilder.setC_BPartner(rfqResponse.getC_BPartner());
		mailTextBuilder.setAD_User(rfqResponse.getAD_User());
		mailTextBuilder.setRecord(rfqResponse);
		return mailTextBuilder;
	}

	private ILoggable getLoggable()
	{
		return ILoggable.THREADLOCAL.getLoggable();
	}

	private void logError(final String msg, final Object... msgParameters)
	{
		getLoggable().addLog(msg, msgParameters);
		logger.error(msg, msgParameters);
	}

}
