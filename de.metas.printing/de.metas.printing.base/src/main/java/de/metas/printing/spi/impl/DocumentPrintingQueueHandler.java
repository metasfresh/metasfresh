package de.metas.printing.spi.impl;

/*
 * #%L
 * de.metas.printing.base
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
import java.util.List;
import java.util.Properties;

import org.adempiere.archive.api.IArchiveDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_InOut;
import org.slf4j.Logger;

import com.google.common.base.Optional;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.document.archive.model.I_AD_Archive;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocDAO;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.letters.model.I_C_Letter;
import de.metas.logging.LogManager;
import de.metas.printing.model.I_C_Print_Package;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.printing.model.X_C_Printing_Queue;
import de.metas.printing.spi.PrintingQueueHandlerAdapter;

/**
 * 
 * This handler adds <code>DocAction</code> specific info to a given queueItem, if that item's printOut references a DocAction PO.
 * <ul>
 * <li>C_DocType_ID: the DocAction's doc type</li>
 * <li>AD_User_ID: the DocAction's Doc_User_ID (DocAction interface method)</li>
 * </ul>
 * 
 */
public class DocumentPrintingQueueHandler extends PrintingQueueHandlerAdapter
{
	public static final transient DocumentPrintingQueueHandler instance = new DocumentPrintingQueueHandler();

	private static final Logger logger = LogManager.getLogger(DocumentPrintingQueueHandler.class);

	/**
	 * Extracts information from the given <code>archive</code> and sets it to the given <code>queueItem</code>.<br>
	 * Particular, about
	 * <ul>
	 * <li><code>C_BPartner_ID</code>: the method tries to take if from <code>AD_Archive.C_BPartner_ID</code>, falling back to the <code>C_BPartner_ID</code> or the PO referenced by the AD_Archive
	 * record (if there is any). Note that in case of AD_Archive records referencing a <code>C_Invoice</code> or a <code>M_InOut</code> this C_BPartner_ID might be overwritten further down in this
	 * method.
	 * <li><code>Copies</code>: this value is currently set from <code>C_BPartner.DocumentCopies</code>, but only for items that reference an invoice. For all other items, the value is set to
	 * <code>1</code> (task 08958).
	 * </ul>
	 */
	@Override
	public void afterEnqueueBeforeSave(final I_C_Printing_Queue queueItem, final I_AD_Archive archive)
	{
		logger.debug("C_Printing_Queue={}; AD_Archive {} has [AD_Table_ID={}, Record_ID={}, C_BPartner_ID={}];",
				new Object[] { queueItem, archive, archive.getAD_Table_ID(), archive.getRecord_ID(), archive.getC_BPartner_ID() });

		//
		// try to set the C_BPartner_ID for our queueItem
		if (archive.getC_BPartner_ID() > 0)
		{
			logger.debug("Setting column of C_Printing_Queue {} from AD_Archive {}: [C_BPartner_ID={}]",
					new Object[] { queueItem, archive, archive.getC_BPartner_ID() });
			queueItem.setC_BPartner_ID(archive.getC_BPartner_ID()); // may be overridden further down.
		}

		// further down we use informations from the PO referenced by the archive, *if* the archive references any
		final Object archiveRerencedModel = Services.get(IArchiveDAO.class).retrieveReferencedModel(archive, Object.class);
		if (archiveRerencedModel == null)
		{
			logger.debug("AD_Archive {} does not reference a PO; returning", archive);
			return;
		}

		if (archive.getC_BPartner_ID() < 0 && InterfaceWrapperHelper.hasModelColumnName(archiveRerencedModel, I_C_BPartner.COLUMNNAME_C_BPartner_ID))
		{
			// the archive itself did not reference a C_BPartner_ID, so we try the object that is referenced by the archive
			final Optional<Integer> bpartnerID = InterfaceWrapperHelper.getValue(archiveRerencedModel, I_C_BPartner.COLUMNNAME_C_BPartner_ID);
			if (bpartnerID.isPresent())
			{
				logger.debug("Setting column of C_Printing_Queue {} from PO {} that is referenced by AD_Archive {}: [C_BPartner_ID={}]",
						new Object[] { queueItem, archiveRerencedModel, archive, bpartnerID });
				queueItem.setC_BPartner_ID(bpartnerID.get()); // may be overridden further down.
			}
		}

		// special stuff wrt special document type
		final IDocumentBL docActionBL = Services.get(IDocumentBL.class);
		final IDocument document = docActionBL.getDocumentOrNull(archiveRerencedModel);
		if (document != null)
		{
			logger.debug("Setting column of C_Printing_Queue {} from DocAction-PO {} that is referenced by AD_Archive {}: [AD_User_ID={}]",
					new Object[] { queueItem, archiveRerencedModel, archive, document.getDoc_User_ID() });
			queueItem.setAD_User_ID(document.getDoc_User_ID());
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(queueItem);
		final int doctypeID = Services.get(IDocumentBL.class).getC_DocType_ID(ctx, archive.getAD_Table_ID(), archive.getRecord_ID());
		queueItem.setC_DocType_ID(doctypeID);

		// Handles operations specific for invoices.
		if (InterfaceWrapperHelper.isInstanceOf(archiveRerencedModel, I_C_Invoice.class))
		{
			final I_C_Invoice invoice = InterfaceWrapperHelper.create(archiveRerencedModel, I_C_Invoice.class);
			handleInvoices(queueItem, invoice);
		}

		else if (InterfaceWrapperHelper.isInstanceOf(archiveRerencedModel, I_M_InOut.class))
		{
			final I_M_InOut inout = InterfaceWrapperHelper.create(archiveRerencedModel, I_M_InOut.class);
			handleInOuts(queueItem, inout);
		}
		
		handleItemName(queueItem, archiveRerencedModel);
	}

	/**
	 * Sets the ItemName for each document that we process
	 * @param queueItem
	 * @param archiveRerencedModel
	 */
	private void handleItemName(final I_C_Printing_Queue queueItem, Object archiveRerencedModel)
	{
		// Handles operations specific for invoices.
		if (InterfaceWrapperHelper.isInstanceOf(archiveRerencedModel, I_C_Invoice.class))
		{
			queueItem.setItemName(X_C_Printing_Queue.ITEMNAME_Rechnung);
		}
		else if (InterfaceWrapperHelper.isInstanceOf(archiveRerencedModel, I_C_Print_Package.class))
		{
			queueItem.setItemName(X_C_Printing_Queue.ITEMNAME_PDF);
		}
		else if (InterfaceWrapperHelper.isInstanceOf(archiveRerencedModel, I_M_InOut.class))
		{
			queueItem.setItemName(X_C_Printing_Queue.ITEMNAME_VersandWareneingang);
		}
		// TODO factor this code out into a AD_Boilerplate/C_Letter specific handler
		// https://github.com/metasfresh/metasfresh/issues/2128
		else if (InterfaceWrapperHelper.isInstanceOf(archiveRerencedModel, I_C_Letter.class))
		{
			queueItem.setItemName(X_C_Printing_Queue.ITEMNAME_Mitgliedsausweis);
		}
		else
		{
			queueItem.setItemName(X_C_Printing_Queue.ITEMNAME_Sofort_DruckPDF);
		}
	}
	
	private void handleInOuts(final I_C_Printing_Queue queueItem, final I_M_InOut inout)
	{
		// services
		final IShipmentScheduleAllocDAO schedAllocDAO = Services.get(IShipmentScheduleAllocDAO.class);

		queueItem.setC_BPartner(inout.getC_BPartner());
		queueItem.setC_BPartner_Location(inout.getC_BPartner_Location());

		// find the delivery date from the lieferdispo
		if (!inout.isSOTrx())
		{
			return; // do nothing
		}

		// task 08911: use the shipment sched's deliverydate, falling back to C_Order.Datepromised
		// currently, don't use M_InOut.MovementDate because it might be a different date and the user want's to filter by
		final List<I_M_ShipmentSchedule> schedules = schedAllocDAO.retrieveSchedulesForInOut(inout);

		Timestamp deliveryDateToset = computeMaxDeliveryDateFromScheds(schedules);
		if (deliveryDateToset != null)
		{
			queueItem.setDeliveryDate(deliveryDateToset);
		}

		logger.debug("Setting columns of C_Printing_Queue {} from M_InOut {}: [C_BPartner_ID={}, C_BPartner_Location_ID={}, DeliveryDate={}]",
				new Object[] { queueItem, inout, inout.getC_BPartner_ID(), inout.getC_BPartner_Location_ID(), deliveryDateToset });
	}

	private Timestamp computeMaxDeliveryDateFromScheds(final List<I_M_ShipmentSchedule> schedules)
	{
		// Services
		final IShipmentScheduleEffectiveBL schedEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);
		if (schedules.isEmpty())
		{
			// nothing to do
			return null;
		}

		// iterate all scheds and return the maximum
		Timestamp maxDeliveryDate = schedEffectiveBL.getDeliveryDate(schedules.get(0));
		for (int i = 1; i < schedules.size(); i++)
		{
			final Timestamp currentDate = schedEffectiveBL.getDeliveryDate(schedules.get(i));
			if (currentDate.after(maxDeliveryDate))
			{
				maxDeliveryDate = currentDate;
			}
		}
		return maxDeliveryDate;
	}

	private void handleInvoices(final I_C_Printing_Queue queueItem, final I_C_Invoice invoice)
	{
		queueItem.setBill_BPartner_ID(invoice.getC_BPartner_ID());
		queueItem.setBill_Location_ID(invoice.getC_BPartner_Location_ID());
		queueItem.setC_BPartner_ID(invoice.getC_BPartner_ID());
		queueItem.setC_BPartner_Location_ID(invoice.getC_BPartner_Location_ID());

		final int documentCopies = invoice.getC_BPartner().getDocumentCopies();
		if (documentCopies > 0)
		{
			// updating to the partner's preference
			queueItem.setCopies(documentCopies);
		}

		logger.debug(
				"Setting columns of C_Printing_Queue {} from C_Invoice {}: [Bill_BPartner_ID={}, Bill_Location_ID={}, C_BPartner_ID={}, C_BPartner_Location_ID={}, Copies={}]",
				queueItem, invoice, invoice.getC_BPartner_ID(), invoice.getC_BPartner_Location_ID(), invoice.getC_BPartner_ID(), invoice.getC_BPartner_Location_ID(), queueItem.getCopies());
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}
	
	private DocumentPrintingQueueHandler()
	{
		super();
	}

	/**
	 * Allays returns <code>true</code>.
	 */
	@Override
	public boolean isApplyHandler(I_C_Printing_Queue queue_IGNORED, I_AD_Archive printout_IGNORED)
	{
		return true;
	}
}
