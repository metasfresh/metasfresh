package org.adempiere.archive.api.impl;

import de.metas.process.AdProcessId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Archive;
import org.compiere.model.I_C_BPartner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ArchiveDAOTest
{
	private static final AdProcessId PROCESS_1 = AdProcessId.ofRepoId(101);
	private static final AdProcessId PROCESS_2 = AdProcessId.ofRepoId(102);

	private ArchiveDAO archiveDAO;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		archiveDAO = new ArchiveDAO();
	}

	private static TableRecordReference newRecordRef()
	{
		final I_C_BPartner record = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);
		InterfaceWrapperHelper.saveRecord(record);
		return TableRecordReference.of(record);
	}

	private static void createArchive(final TableRecordReference recordRef, final AdProcessId processId, final boolean active)
	{
		final I_AD_Archive archive = InterfaceWrapperHelper.newInstance(I_AD_Archive.class);
		archive.setAD_Table_ID(recordRef.getAD_Table_ID());
		archive.setRecord_ID(recordRef.getRecord_ID());
		archive.setAD_Process_ID(processId.getRepoId());
		archive.setIsActive(active);
		InterfaceWrapperHelper.saveRecord(archive);
	}

	@Test
	void countArchives_countsActiveArchivesOfTheRecordOnly_optionallyOfOneProcess()
	{
		final TableRecordReference record = newRecordRef();
		final TableRecordReference otherRecord = newRecordRef();
		createArchive(record, PROCESS_1, true);
		createArchive(record, PROCESS_1, true);
		createArchive(record, PROCESS_2, true);
		createArchive(record, PROCESS_1, false);
		createArchive(otherRecord, PROCESS_1, true);

		assertThat(archiveDAO.countArchives(record, null)).isEqualTo(3);
		assertThat(archiveDAO.countArchives(record, PROCESS_1)).isEqualTo(2);
		assertThat(archiveDAO.countArchives(record, PROCESS_2)).isEqualTo(1);
		assertThat(archiveDAO.countArchives(otherRecord, PROCESS_2)).isZero();
	}
}
