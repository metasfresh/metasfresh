package de.metas.fresh.ordercheckup.printing.spi.impl;

/*
 * #%L
 * de.metas.fresh.base
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

import com.google.common.collect.ImmutableSet;
import de.metas.document.archive.model.I_AD_Archive;
import de.metas.fresh.model.I_C_Order_MFGWarehouse_Report;
import de.metas.fresh.ordercheckup.IOrderCheckupBL;
import de.metas.logging.LogManager;
import de.metas.printing.api.IPrintingQueueBL;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.printing.spi.PrintingQueueHandlerAdapter;
import de.metas.util.Services;
import org.adempiere.ad.table.api.IADTableDAO;
import org.slf4j.Logger;

/**
 * The job of this handler is
 * <ul>
 * <li>intercept {@link I_C_Order_MFGWarehouse_Report} prints
 * <li>set the user to print to {@link I_C_Order_MFGWarehouse_Report#getAD_User_Responsible_ID()}.
 * </ul>
 *
 * @author ts
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/09028_Produktionsauftrag-Bestellkontrolle_automatisch_ausdrucken_%28106402701484%29
 */
public class OrderCheckupPrintingQueueHandler extends PrintingQueueHandlerAdapter
{
	public static final transient OrderCheckupPrintingQueueHandler instance = new OrderCheckupPrintingQueueHandler();

	private static final transient Logger logger = LogManager.getLogger(OrderCheckupPrintingQueueHandler.class);

	private final transient IOrderCheckupBL orderCheckupBL = Services.get(IOrderCheckupBL.class);

	/**
	 * Set the number of copies to the configured value.
	 * Note that this handler is invoked after the "standard" handler, because the main validator of de.metas.fresh
	 * has a higher SeqNo and is inititalized later.
	 *
	 * @task http://dewiki908/mediawiki/index.php/09467_Bestellkontrolle_2x_ausdrucken_%28103975976435%29
	 */
	@Override
	public void afterEnqueueBeforeSave(final I_C_Printing_Queue queueItem, final I_AD_Archive printOut)
	{
		final int copies = orderCheckupBL.getNumberOfCopies(queueItem, printOut);
		queueItem.setCopies(copies);
	}

	@Override
	public void afterEnqueueAfterSave(final I_C_Printing_Queue queueItem, final I_AD_Archive printOut)
	{
		//
		// Get the underlying report if applies
		final I_C_Order_MFGWarehouse_Report report = orderCheckupBL.getReportOrNull(printOut);
		if (report == null)
		{
			return;
		}

		setUserToPrint(queueItem, report);
	}

	/**
	 * Returns <code>true</code> if the given archive's <code>AD_Table_ID</code> is the ID of the <code>C_Order_MFGWarehouse_Report</code> table.
	 */
	@Override
	public boolean isApplyHandler(final I_C_Printing_Queue queueItem_IGNOED, final I_AD_Archive printOut)
	{
		return Services.get(IADTableDAO.class).isTableId(I_C_Order_MFGWarehouse_Report.Table_Name, printOut.getAD_Table_ID());
	}



	private final void setUserToPrint(final I_C_Printing_Queue queueItem, final I_C_Order_MFGWarehouse_Report report)
	{
		//
		// Get the user that shall be used for printing.
		// If there is no printing user, don't print it.
		final int userToPrintId = report.getAD_User_Responsible_ID();
		if (userToPrintId <= 0)
		{
			logger.info("Cancel from printing because there was no user to print: {}", report);
			queueItem.setIsActive(false);
			return;
		}

		//
		// Set the new recipient
		Services.get(IPrintingQueueBL.class).setPrintoutForOtherUsers(queueItem, ImmutableSet.of(userToPrintId));
	}

	private OrderCheckupPrintingQueueHandler()
	{
	}
}
