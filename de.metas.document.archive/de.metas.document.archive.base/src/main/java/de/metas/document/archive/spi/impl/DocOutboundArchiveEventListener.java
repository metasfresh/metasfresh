package de.metas.document.archive.spi.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

/*
 * #%L
 * de.metas.document.archive.base
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

import java.sql.Timestamp;
import java.util.Optional;
import java.util.Properties;

import org.adempiere.archive.api.IArchiveDAO;
import org.adempiere.archive.api.IArchiveEventManager;
import org.adempiere.archive.spi.ArchiveEventListenerAdapter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.time.SystemTime;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_Archive;
import org.compiere.model.I_AD_User;

import com.google.common.annotations.VisibleForTesting;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.document.archive.DocOutBoundRecipient;
import de.metas.document.archive.DocOutboundLogMailRecipientRegistry;
import de.metas.document.archive.api.IBPartnerBL;
import de.metas.document.archive.api.IDocOutboundDAO;
import de.metas.document.archive.model.I_C_BPartner;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log_Line;
import de.metas.document.archive.model.X_C_Doc_Outbound_Log_Line;
import de.metas.document.engine.IDocumentBL;
import lombok.NonNull;

public class DocOutboundArchiveEventListener extends ArchiveEventListenerAdapter
{
	@Override
	public void onPdfUpdate(@NonNull final I_AD_Archive archive, final I_AD_User user, final String action)
	{
		if (!isLoggableArchive(archive))
		{
			return;
		}

		final I_C_Doc_Outbound_Log_Line docExchangeLine = createLogLine(archive);
		docExchangeLine.setAction(action);
		docExchangeLine.setAD_User(user);

		save(docExchangeLine);
	}

	@Override
	public void onEmailSent(
			@NonNull final I_AD_Archive archive,
			final String action,
			final I_AD_User user,
			final String from,
			final String to,
			final String cc,
			final String bcc,
			final String status)
	{
		if (!isLoggableArchive(archive))
		{
			return;
		}

		final I_C_Doc_Outbound_Log_Line docExchangeLine = createLogLine(archive);
		docExchangeLine.setAction(action);
		docExchangeLine.setEMail_From(from);
		docExchangeLine.setEMail_To(to);
		docExchangeLine.setEMail_Cc(cc);
		docExchangeLine.setEMail_Bcc(bcc);
		docExchangeLine.setStatus(status);
		docExchangeLine.setAD_User(user);

		save(docExchangeLine);

		final I_C_Doc_Outbound_Log log = docExchangeLine.getC_Doc_Outbound_Log();
		log.setDateLastEMail(SystemTime.asTimestamp());
		save(log);
	}

	@Override
	public void onPrintOut(final I_AD_Archive archive, final I_AD_User user, final String printerName, final int copies, final String status)
	{
		// task 05334: only assume existing archive if the status is "success"
		if (IArchiveEventManager.STATUS_Success.equals(status))
		{
			Check.assumeNotNull(archive, "archive not null");
		}
		if (!isLoggableArchive(archive))
		{
			return;
		}

		final I_C_Doc_Outbound_Log_Line docExchangeLine = createLogLine(archive);

		docExchangeLine.setAction(X_C_Doc_Outbound_Log_Line.ACTION_Print);
		// create stuff
		docExchangeLine.setAD_User(user);
		docExchangeLine.setStatus(status);

		save(docExchangeLine);
	}

	/**
	 * We don't generate logs for archives without table IDs
	 *
	 * @param archive
	 * @return
	 */
	private boolean isLoggableArchive(final I_AD_Archive archive)
	{
		// task 05334: be robust against archive==null
		if (archive == null || archive.getAD_Table_ID() <= 0)
		{
			return false;
		}

		return true;
	}

	/**
	 * Creates {@link I_C_Doc_Outbound_Log_Line}.
	 *
	 * NOTE: it is not saving the created log line
	 *
	 * @param archive
	 * @return {@link I_C_Doc_Outbound_Log_Line}
	 */
	@VisibleForTesting
	I_C_Doc_Outbound_Log_Line createLogLine(@NonNull final I_AD_Archive archive)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(archive);

		I_C_Doc_Outbound_Log docOutboundLog = Services.get(IDocOutboundDAO.class).retrieveLog(archive);

		if (docOutboundLog == null)
		{
			// no log found, create a new one
			docOutboundLog = createLog(archive);
		}

		final I_C_Doc_Outbound_Log_Line docOutboundLogLineRecord = newInstance(I_C_Doc_Outbound_Log_Line.class);

		docOutboundLogLineRecord.setC_Doc_Outbound_Log_ID(docOutboundLog.getC_Doc_Outbound_Log_ID());
		docOutboundLogLineRecord.setAD_Archive_ID(archive.getAD_Archive_ID());
		docOutboundLogLineRecord.setAD_Org_ID(archive.getAD_Org_ID());
		docOutboundLogLineRecord.setAD_Table_ID(archive.getAD_Table_ID());
		docOutboundLogLineRecord.setRecord_ID(archive.getRecord_ID());

		final IDocumentBL documentBL = Services.get(IDocumentBL.class);

		// We need to use DocumentNo if possible, else fallback to archive's name
		// see http://dewiki908/mediawiki/index.php/03918_Massendruck_f%C3%BCr_Mahnungen_%282013021410000132%29#IT2_-_G01_-_Mass_Printing
		String documentNo = documentBL.getDocumentNo(ctx, archive.getAD_Table_ID(), archive.getRecord_ID());
		if (Check.isEmpty(documentNo, true))
		{
			documentNo = archive.getName();
		}
		docOutboundLogLineRecord.setDocumentNo(documentNo);

		final String docStatus = documentBL.getDocStatusOrNull(ctx, archive.getAD_Table_ID(), archive.getRecord_ID());
		docOutboundLogLineRecord.setDocStatus(docStatus);

		final int doctypeID = documentBL.getC_DocType_ID(ctx, archive.getAD_Table_ID(), archive.getRecord_ID());
		docOutboundLogLineRecord.setC_DocType_ID(doctypeID);

		// docExchangeLine.setCopies(Copies);
		// docExchangeLine.setAD_User_ID(AD_User_ID);
		// docExchangeLine.setC_BPartner_ID(archive.getC_BPartner_ID());

		return docOutboundLogLineRecord;
	}

	/**
	 * Creates and saves {@link I_C_Doc_Outbound_Log}
	 *
	 * @param archive
	 * @return {@link I_C_Doc_Outbound_Log}
	 */
	private I_C_Doc_Outbound_Log createLog(final I_AD_Archive archive)
	{
		// Services
		final IDocumentBL docActionBL = Services.get(IDocumentBL.class);

		final int adTableId = archive.getAD_Table_ID();
		final int recordId = archive.getRecord_ID();

		final Properties ctx = InterfaceWrapperHelper.getCtx(archive);

		final I_C_Doc_Outbound_Log docOutboundLogRecord = newInstance(I_C_Doc_Outbound_Log.class);
		docOutboundLogRecord.setAD_Org_ID(archive.getAD_Org_ID());
		docOutboundLogRecord.setAD_Table_ID(adTableId);
		docOutboundLogRecord.setRecord_ID(recordId);
		docOutboundLogRecord.setC_BPartner_ID(archive.getC_BPartner_ID());

		final int doctypeID = docActionBL.getC_DocType_ID(ctx, adTableId, recordId);
		docOutboundLogRecord.setC_DocType_ID(doctypeID);


		docOutboundLogRecord.setDateLastEMail(null);
		docOutboundLogRecord.setDateLastPrint(null);

		final String docStatus = docActionBL.getDocStatusOrNull(ctx, adTableId, recordId);
		docOutboundLogRecord.setDocStatus(docStatus);

		docOutboundLogRecord.setDocumentNo(archive.getName());

		final Timestamp documentDate = docActionBL.getDocumentDate(ctx, adTableId, recordId);
		docOutboundLogRecord.setDateDoc(documentDate); // task 08905: Also set the the documentDate

		setMailRecipient(docOutboundLogRecord);

		save(docOutboundLogRecord);

		return docOutboundLogRecord;
	}

	private void setMailRecipient(@NonNull final I_C_Doc_Outbound_Log docOutboundLogRecord)
	{
		final DocOutboundLogMailRecipientRegistry docOutboundLogMailRecipientRegistry = Adempiere.getBean(DocOutboundLogMailRecipientRegistry.class);

		final Optional<DocOutBoundRecipient> mailRecipient = docOutboundLogMailRecipientRegistry.invokeProvider(docOutboundLogRecord);
		if (!mailRecipient.isPresent())
		{
			return;
		}

		mailRecipient.ifPresent(recipient -> updateRecordWithRecipient(docOutboundLogRecord, recipient));
	}

	private void updateRecordWithRecipient(
			@NonNull final I_C_Doc_Outbound_Log docOutboundLogRecord,
			@NonNull final DocOutBoundRecipient recipient)
	{
		final String tableName = TableRecordReference.ofReferenced(docOutboundLogRecord).getTableName();
		final boolean isInvoiceEmailEnabled = I_C_Invoice.Table_Name.equals(tableName) && recipient.isInvoiceAsEmail();
		docOutboundLogRecord.setIsInvoiceEmailEnabled(isInvoiceEmailEnabled);

		docOutboundLogRecord.setCurrentEMailRecipient_ID(recipient.getId().getRepoId());
		docOutboundLogRecord.setCurrentEMailAddress(recipient.getEmailAddress());
	}
}
