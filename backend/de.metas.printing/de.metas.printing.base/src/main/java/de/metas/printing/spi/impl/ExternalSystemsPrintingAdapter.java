/*
 * #%L
 * de.metas.printing.base
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

package de.metas.printing.spi.impl;

import de.metas.audit.data.ExternalSystemParentConfigId;
import de.metas.document.archive.model.I_AD_Archive;
import de.metas.printing.ExternalSystemsPrintingService;
import de.metas.printing.PrintRequest;
import de.metas.printing.api.IPrintingDAO;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.printing.spi.PrintingQueueHandlerAdapter;
import de.metas.util.Services;
import org.adempiere.archive.ArchiveId;
import org.springframework.stereotype.Service;

@Service
public class ExternalSystemsPrintingAdapter extends PrintingQueueHandlerAdapter
{
	private final ExternalSystemsPrintingService printingService;

	private final IPrintingDAO printingDAO = Services.get(IPrintingDAO.class);


	public ExternalSystemsPrintingAdapter(final ExternalSystemsPrintingService printingService)
	{
		this.printingService = printingService;
	}

	@Override
	public void afterEnqueueBeforeSave(I_C_Printing_Queue queueItem, I_AD_Archive printOut)
	{
		final String transactionId = printingDAO.getTransactionIdForAdArchiveId(ArchiveId.ofRepoId(printOut.getAD_Archive_ID()));
		printingService.print(PrintRequest.builder()
						.id(ExternalSystemParentConfigId.ofRepoId(42))//ExternalSystemParentConfigId.ofRepoId(queueItem.getAD_PrinterHW().getExternalSystem_Config_ID()))
						.transactionId(transactionId)
				.build());
	}

	@Override
	public boolean isApplyHandler(final I_C_Printing_Queue queueItem, final I_AD_Archive printOut)
	{
		// return queueItem.getAD_PrinterHW().getExternalSystem_Config_ID() > 1
		// 		&& printOut.getAD_Table_ID() == getTableIdofPrintPackage();
		return true;
	}

	private int getTableIdofPrintPackage()
	{
		return 100; // TODO
	}

}
