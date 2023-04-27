/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.contracts.printing.impl;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.document.archive.model.I_AD_Archive;
import de.metas.logging.LogManager;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.printing.spi.PrintingQueueHandlerAdapter;
import de.metas.printing.spi.impl.DocumentPrintingQueueHandler;
import de.metas.util.Services;
import org.adempiere.archive.api.IArchiveDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.slf4j.Logger;


/**
 *
 * This handler adds the dropship info fetched from flatrate term to a given queueItem
 *
 */
public class FlatrateTermPrintingQueueHandler extends PrintingQueueHandlerAdapter
{
	private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
	private static final Logger logger = LogManager.getLogger(FlatrateTermPrintingQueueHandler.class);

	public static final transient FlatrateTermPrintingQueueHandler instance = new FlatrateTermPrintingQueueHandler();

	/**
	 * Allays returns <code>true</code>.
	 */
	@Override
	public boolean isApplyHandler(I_C_Printing_Queue queue_IGNORED, I_AD_Archive printout_IGNORED)
	{
		return true;
	}


	@Override
	public void afterEnqueueBeforeSave(final I_C_Printing_Queue queueItem, final I_AD_Archive archive)
	{
		logger.debug("C_Printing_Queue={}; AD_Archive {} has [AD_Table_ID={}, Record_ID={}, C_BPartner_ID={}];",
				new Object[] { queueItem, archive, archive.getAD_Table_ID(), archive.getRecord_ID(), archive.getC_BPartner_ID() });

		final Object archiveRefencedModel = Services.get(IArchiveDAO.class).retrieveReferencedModel(archive, Object.class);
		if (archiveRefencedModel == null)
		{
			logger.debug("AD_Archive {} does not reference a PO; returning", archive);
			return;
		}

		if (InterfaceWrapperHelper.isInstanceOf(archiveRefencedModel, I_C_Invoice.class))
		{
			final I_C_Invoice invoice = InterfaceWrapperHelper.create(archiveRefencedModel, I_C_Invoice.class);
			handleInvoices(queueItem, invoice);
		}

	}

	private void handleInvoices(final I_C_Printing_Queue queueItem, final I_C_Invoice invoice)
	{
		final I_C_Flatrate_Term flatrateTerm = flatrateDAO.retrieveFirstFlatrateTerm(invoice);
		queueItem.setC_BPartner_ID(flatrateTerm.getDropShip_BPartner_ID());
		queueItem.setAD_User_ID(flatrateTerm.getDropShip_User_ID());
		queueItem.setC_BPartner_Location_ID(flatrateTerm.getDropShip_Location_ID());
	}
}
