package de.metas.dunning.printing.spi.impl;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.bpartner.service.IBPartnerDAO;
import org.adempiere.archive.api.IArchiveDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.model.I_C_BPartner;
import org.slf4j.Logger;

import de.metas.document.archive.model.I_AD_Archive;
import de.metas.dunning.model.I_C_DunningDoc;
import de.metas.logging.LogManager;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.printing.model.X_C_Printing_Queue;
import de.metas.printing.spi.PrintingQueueHandlerAdapter;
import de.metas.util.Services;

/**
 *
 * This handler adds <code>ItemName</code> specific info to a given queueItem
 *
 */
public class DunningPrintingQueueHandler extends PrintingQueueHandlerAdapter
{
	public static final transient DunningPrintingQueueHandler instance = new DunningPrintingQueueHandler();

	private static final Logger logger = LogManager.getLogger(DunningPrintingQueueHandler.class);

	private DunningPrintingQueueHandler()
	{
	}

	@Override
	public void afterEnqueueBeforeSave(final I_C_Printing_Queue queueItem, final I_AD_Archive archive)
	{
		// further down we use informations from the PO referenced by the archive, *if* the archive references any
		final Object archiveRerencedModel = Services.get(IArchiveDAO.class).retrieveReferencedModel(archive, Object.class);
		if (archiveRerencedModel == null)
		{
			logger.debug("AD_Archive {} does not reference a PO; returning", archive);
			return;
		}

		if (InterfaceWrapperHelper.isInstanceOf(archiveRerencedModel, I_C_DunningDoc.class))
		{
			queueItem.setItemName(X_C_Printing_Queue.ITEMNAME_Mahnung);

			final I_C_DunningDoc dunningDoc = InterfaceWrapperHelper.create(archiveRerencedModel, I_C_DunningDoc.class);
			handleDunnings(queueItem, dunningDoc);
		}
	}

	private void handleDunnings(final I_C_Printing_Queue queueItem, final I_C_DunningDoc dunningDoc)
	{
		queueItem.setBill_BPartner_ID(dunningDoc.getC_BPartner_ID());
		queueItem.setBill_Location_ID(dunningDoc.getC_BPartner_Location_ID());
		queueItem.setC_BPartner_ID(dunningDoc.getC_BPartner_ID());
		queueItem.setC_BPartner_Location_ID(dunningDoc.getC_BPartner_Location_ID());

		logger.debug(
				"Setting columns of C_Printing_Queue {} from C_Invoice {}: [Bill_BPartner_ID={}, Bill_Location_ID={}, C_BPartner_ID={}, C_BPartner_Location_ID={}, Copies={}]",
				queueItem, dunningDoc, dunningDoc.getC_BPartner_ID(), dunningDoc.getC_BPartner_Location_ID(), dunningDoc.getC_BPartner_ID(), dunningDoc.getC_BPartner_Location_ID(), queueItem.getCopies());
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
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
