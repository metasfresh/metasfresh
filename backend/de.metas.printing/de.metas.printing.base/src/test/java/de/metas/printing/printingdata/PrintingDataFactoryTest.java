/*
 * #%L
 * de.metas.printing.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.printing.printingdata;

import com.google.common.collect.ImmutableList;
import de.metas.document.archive.api.ArchiveFileNameService;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.organization.OrgId;
import de.metas.printing.HardwarePrinterRepository;
import de.metas.printing.OutputType;
import de.metas.printing.PrintingQueueItemId;
import de.metas.printing.api.impl.Helper;
import de.metas.printing.api.util.PdfCollator;
import de.metas.printing.model.I_AD_PrinterHW;
import de.metas.printing.model.I_AD_PrinterRouting;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.util.Services;
import de.metas.workplace.WorkplaceRepository;
import de.metas.workplace.WorkplaceService;
import de.metas.workplace.WorkplaceUserAssignRepository;
import lombok.NonNull;
import org.adempiere.archive.api.IArchiveStorageFactory;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Archive;
import org.compiere.model.I_C_Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class PrintingDataFactoryTest
{

	private Helper helper;
	private PrintingDataFactory printingDataFactory;
	private IArchiveStorageFactory archiveStorageFactory;

	@BeforeEach
	void setup(@NonNull final TestInfo testInfo)
	{
		AdempiereTestHelper.get().init();

		helper = new Helper(testInfo);
		helper.setup();
		printingDataFactory = new PrintingDataFactory(new HardwarePrinterRepository(), new ArchiveFileNameService());
		archiveStorageFactory = Services.get(IArchiveStorageFactory.class);
	}

	private enum Mode
	{
		with_C_Doc_Outbound_Log,
		without_C_Doc_Outbound_Log;
	}

	@ParameterizedTest
	@EnumSource(Mode.class)
	void createPrintingDataForQueueItem(@NonNull final Mode mode)
	{
		final WorkplaceService workplaceService = new WorkplaceService(new WorkplaceRepository(), new WorkplaceUserAssignRepository());
		SpringContextHolder.registerJUnitBean(workplaceService);

		// given
		final byte[] binaryPdfData = new PdfCollator()
				.addPages(helper.getPdf("01"), 1, 3) // First 3 pages
				.toByteArray();

		helper.getCreatePrinterHW("hwPrinter", OutputType.Store);
		final I_AD_PrinterRouting printerRouting = helper.createPrinterRouting("logicalPrinter", null, 10, -1, 1, 100);

		final I_C_Order referencedDocument = newInstance(I_C_Order.class);
		saveRecord(referencedDocument);

		final I_AD_Archive archiveRecord = newInstance(I_AD_Archive.class);
		archiveRecord.setName("archiveName");
		archiveRecord.setAD_Table_ID(InterfaceWrapperHelper.getTableId(I_C_Order.class));
		archiveRecord.setRecord_ID(referencedDocument.getC_Order_ID());
		archiveStorageFactory.getArchiveStorage(archiveRecord).setBinaryData(archiveRecord, binaryPdfData);
		saveRecord(archiveRecord);
		if (mode.equals(Mode.with_C_Doc_Outbound_Log))
		{
			final I_C_Doc_Outbound_Log docOutboundLogRecord = newInstance(I_C_Doc_Outbound_Log.class);
			docOutboundLogRecord.setAD_Table_ID(InterfaceWrapperHelper.getTableId(I_C_Order.class));
			docOutboundLogRecord.setRecord_ID(referencedDocument.getC_Order_ID());
			saveRecord(docOutboundLogRecord);
		}
		helper.createPrinterConfigAndMatching(null, "hwPrinter", null, 10, "logicalPrinter", null);

		final I_C_Printing_Queue printingQueueRecord = newInstance(I_C_Printing_Queue.class);
		printingQueueRecord.setAD_Archive_ID(archiveRecord.getAD_Archive_ID());
		printingQueueRecord.setAD_Org_ID(23);
		saveRecord(printingQueueRecord);

		// when
		final ImmutableList<PrintingData> printingData = printingDataFactory.createPrintingDataForQueueItem(printingQueueRecord);

		// then
		assertThat(printingData).hasSize(1);
		assertThat(printingData.get(0).hasData()).isTrue();
		assertThat(printingData.get(0).getPrintingQueueItemId()).isEqualTo(PrintingQueueItemId.ofRepoId(printingQueueRecord.getC_Printing_Queue_ID()));
		assertThat(printingData.get(0).getDocumentFileName()).isEqualTo("C_Order-100007.pdf"); // the file name is not so nice, because there is not documentName, docType etc set up
		assertThat(printingData.get(0).getNumberOfPages()).isEqualTo(3);
		assertThat(printingData.get(0).getOrgId()).isEqualTo(OrgId.ofRepoId(23));
		assertThat(printingData.get(0).getSegments()).isNotEmpty()
				.extracting("pageFrom", "pageTo", "printerRoutingId.repoId")
				.containsExactly(tuple(1, 3, printerRouting.getAD_PrinterRouting_ID()));
	}
}