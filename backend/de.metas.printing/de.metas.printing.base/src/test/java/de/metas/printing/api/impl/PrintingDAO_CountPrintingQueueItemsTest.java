package de.metas.printing.api.impl;

import de.metas.printing.api.IPrintingDAO;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.process.AdProcessId;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Archive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PrintingDAO_CountPrintingQueueItemsTest
{
	private static final AdProcessId LABEL_PROCESS = AdProcessId.ofRepoId(584998);
	private static final AdProcessId OTHER_PROCESS = AdProcessId.ofRepoId(540412);
	private static final TableRecordReference RECORD = TableRecordReference.of("M_HU", 1000);
	private static final TableRecordReference OTHER_RECORD = TableRecordReference.of("M_HU", 1001);

	private IPrintingDAO printingDAO;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		printingDAO = Services.get(IPrintingDAO.class);
	}

	private I_AD_Archive archive(final TableRecordReference recordRef, final AdProcessId processId)
	{
		final I_AD_Archive archive = InterfaceWrapperHelper.newInstance(I_AD_Archive.class);
		archive.setAD_Table_ID(recordRef.getAD_Table_ID());
		archive.setRecord_ID(recordRef.getRecord_ID());
		archive.setAD_Process_ID(processId.getRepoId());
		InterfaceWrapperHelper.saveRecord(archive);
		return archive;
	}

	private void queueItem(final I_AD_Archive archive)
	{
		final I_C_Printing_Queue item = InterfaceWrapperHelper.newInstance(I_C_Printing_Queue.class);
		item.setAD_Archive_ID(archive.getAD_Archive_ID());
		InterfaceWrapperHelper.saveRecord(item);
	}

	@Test
	void countsTheQueueItemsOfTheRecordsArchives_optionallyOfOneProcess()
	{
		final I_AD_Archive labelArchive = archive(RECORD, LABEL_PROCESS);
		queueItem(labelArchive);
		queueItem(labelArchive);
		queueItem(archive(RECORD, OTHER_PROCESS));
		archive(RECORD, LABEL_PROCESS); // archive without queue item
		queueItem(archive(OTHER_RECORD, LABEL_PROCESS));

		assertThat(printingDAO.countPrintingQueueItems(RECORD, LABEL_PROCESS)).isEqualTo(2);
		assertThat(printingDAO.countPrintingQueueItems(RECORD, null)).isEqualTo(3);
	}
}
