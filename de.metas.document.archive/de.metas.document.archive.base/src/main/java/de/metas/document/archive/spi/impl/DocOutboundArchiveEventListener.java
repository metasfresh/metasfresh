package de.metas.document.archive.spi.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.archive.api.IArchiveDAO;
import org.adempiere.archive.api.IArchiveEventManager;
import org.adempiere.archive.spi.IArchiveEventListener;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Archive;
import org.compiere.model.I_AD_User;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.document.archive.api.IBPartnerBL;
import de.metas.document.archive.api.IDocOutboundDAO;
import de.metas.document.archive.model.I_C_BPartner;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log_Line;
import de.metas.document.archive.model.X_C_Doc_Outbound_Log_Line;
import de.metas.document.engine.IDocActionBL;

public class DocOutboundArchiveEventListener implements IArchiveEventListener
{
	/**
	 * Creates and saves {@link I_C_Doc_Outbound_Log}
	 *
	 * @param archive
	 * @return {@link I_C_Doc_Outbound_Log}
	 */
	private I_C_Doc_Outbound_Log createLog(final I_AD_Archive archive)
	{
		// Services
		final IDocActionBL docActionBL = Services.get(IDocActionBL.class);

		final int adTableId = archive.getAD_Table_ID();
		final int recordId = archive.getRecord_ID();

		final Properties ctx = InterfaceWrapperHelper.getCtx(archive);
		final String trxName = InterfaceWrapperHelper.getTrxName(archive);

		final I_C_Doc_Outbound_Log docExchange = InterfaceWrapperHelper.create(ctx, I_C_Doc_Outbound_Log.class, trxName);
		docExchange.setAD_Org_ID(archive.getAD_Org_ID());
		docExchange.setAD_Table_ID(adTableId);
		docExchange.setRecord_ID(recordId);
		docExchange.setC_BPartner_ID(archive.getC_BPartner_ID());
		
		//
		final int doctypeID = docActionBL.getC_DocType_ID(ctx, adTableId, recordId);
		docExchange.setC_DocType_ID(doctypeID);
		
		//
		// set isInvoiceEmailEnabled
		final Object archiveReferencedModel = Services.get(IArchiveDAO.class).retrieveReferencedModel(archive, Object.class);
		if (archiveReferencedModel != null)
		{
			final boolean isInvoiceDocument = InterfaceWrapperHelper.isInstanceOf(archiveReferencedModel, I_C_Invoice.class);
			final Boolean matchingisInvoiceEmailEnabled;
			// in case of invoice document, enable email only if is enabled in partner
			if (isInvoiceDocument)
			{
				final I_C_Invoice invoice = InterfaceWrapperHelper.create(archiveReferencedModel, I_C_Invoice.class);
				final I_C_BPartner bpartner = InterfaceWrapperHelper.create(invoice.getC_BPartner(), I_C_BPartner.class);
				final de.metas.document.archive.model.I_AD_User user = InterfaceWrapperHelper.create(invoice.getAD_User(), de.metas.document.archive.model.I_AD_User.class);
				matchingisInvoiceEmailEnabled = Services.get(IBPartnerBL.class).isInvoiceEmailEnabled(bpartner, user);
				
			}
			else
			{
				// set by defualt on Y for all other documents
				matchingisInvoiceEmailEnabled = Boolean.TRUE;
			}
			 
			docExchange.setIsInvoiceEmailEnabled(matchingisInvoiceEmailEnabled);
		}

		
		
		docExchange.setDateLastEMail(null);
		docExchange.setDateLastPrint(null);

		final String docStatus = docActionBL.getDocStatusOrNull(ctx, adTableId, recordId);
		docExchange.setDocStatus(docStatus);

		docExchange.setDocumentNo(archive.getName());

		final Timestamp documentDate = docActionBL.getDocumentDate(ctx, adTableId, recordId);
		docExchange.setDateDoc(documentDate); // task 08905: Also set the the documentDate
		
		InterfaceWrapperHelper.save(docExchange);

		return docExchange;
	}

	/**
	 * Creates {@link I_C_Doc_Outbound_Log_Line}.
	 *
	 * NOTE: it is not saving the created log line
	 *
	 * @param archive
	 * @return {@link I_C_Doc_Outbound_Log_Line}
	 */
	// tsa: protected to be unit tested
	protected I_C_Doc_Outbound_Log_Line createLogLine(final I_AD_Archive archive)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(archive);
		final String trxName = InterfaceWrapperHelper.getTrxName(archive);

		I_C_Doc_Outbound_Log docExchange = Services.get(IDocOutboundDAO.class).retrieveLog(archive);

		if (docExchange == null)
		{
			// no log found, create a new one
			docExchange = createLog(archive);
		}

		final I_C_Doc_Outbound_Log_Line docExchangeLine = InterfaceWrapperHelper.create(ctx, I_C_Doc_Outbound_Log_Line.class, trxName);
		docExchangeLine.setC_Doc_Outbound_Log_ID(docExchange.getC_Doc_Outbound_Log_ID());
		docExchangeLine.setAD_Archive_ID(archive.getAD_Archive_ID());
		docExchangeLine.setAD_Org_ID(archive.getAD_Org_ID());
		docExchangeLine.setAD_Table_ID(archive.getAD_Table_ID());
		docExchangeLine.setRecord_ID(archive.getRecord_ID());

		final IDocActionBL documentBL = Services.get(IDocActionBL.class);

		// We need to use DocumentNo if possible, else fallback to archive's name
		// see http://dewiki908/mediawiki/index.php/03918_Massendruck_f%C3%BCr_Mahnungen_%282013021410000132%29#IT2_-_G01_-_Mass_Printing
		String documentNo = documentBL.getDocumentNo(ctx, archive.getAD_Table_ID(), archive.getRecord_ID());
		if (Check.isEmpty(documentNo, true))
		{
			documentNo = archive.getName();
		}
		docExchangeLine.setDocumentNo(documentNo);

		final String docStatus = documentBL.getDocStatusOrNull(ctx, archive.getAD_Table_ID(), archive.getRecord_ID());
		docExchangeLine.setDocStatus(docStatus);

		final int doctypeID = documentBL.getC_DocType_ID(ctx, archive.getAD_Table_ID(), archive.getRecord_ID());
		docExchangeLine.setC_DocType_ID(doctypeID);

		// docExchangeLine.setCopies(Copies);
		// docExchangeLine.setAD_User_ID(AD_User_ID);
		// docExchangeLine.setC_BPartner_ID(archive.getC_BPartner_ID());

		return docExchangeLine;
	}

	@Override
	public void onPdfUpdate(final I_AD_Archive archive, final I_AD_User user)
	{
		Check.assumeNotNull(archive, "archive not null");

		if (!isLoggableArchive(archive))
		{
			return;
		}

		final I_C_Doc_Outbound_Log_Line docExchangeLine = createLogLine(archive);
		docExchangeLine.setAction(X_C_Doc_Outbound_Log_Line.ACTION_PdfExport);
		docExchangeLine.setAD_User(user);

		InterfaceWrapperHelper.save(docExchangeLine);
	}

	@Override
	public void onEmailSent(final I_AD_Archive archive, final String action, final I_AD_User user, final String from, final String to, final String cc, final String bcc, final String status)
	{
		Check.assumeNotNull(archive, "archive not null");

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

		InterfaceWrapperHelper.save(docExchangeLine);
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

		InterfaceWrapperHelper.save(docExchangeLine);
	}

	/**
	 * We don't generate logs for archives without table IDs
	 *
	 * @param archive
	 * @return
	 */
	private boolean isLoggableArchive(final I_AD_Archive archive)
	{
		// task 05334: be robust agains archive==null
		if (archive == null || archive.getAD_Table_ID() <= 0)
		{
			return false;
		}

		return true;
	}
}
