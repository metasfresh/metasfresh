package de.metas.printing.spi.impl;

import org.adempiere.archive.api.IArchiveDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ObjectUtils;
import org.slf4j.Logger;

import de.metas.document.archive.model.I_AD_Archive;
import de.metas.dunning.model.I_C_DunningDoc;
import de.metas.logging.LogManager;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.printing.model.X_C_Printing_Queue;
import de.metas.printing.spi.PrintingQueueHandlerAdapter;

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
		super();
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
		}
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
