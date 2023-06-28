package de.metas.gplr.report.repository;

import de.metas.gplr.report.model.GPLRReportNote;
import de.metas.gplr.report.repository.GPLRReportNote_Mapper;
import de.metas.organization.OrgId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_GPLR_Report_Note;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("NewClassNamingConvention")
class GPLRReportNote_Mapper_Test
{
	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	void save_load()
	{
		final OrgId orgId = OrgId.ofRepoId(111);
		final GPLRReportNote reportPart = GPLRReportNote.builder()
				.sourceDocument("sourceDocument")
				.text("line1\nline2\nline3")
				.build();

		final I_GPLR_Report_Note record = InterfaceWrapperHelper.newInstance(I_GPLR_Report_Note.class);
		GPLRReportNote_Mapper.updateRecord(record, reportPart, orgId);
		final GPLRReportNote reportPart2 = GPLRReportNote_Mapper.fromRecord(record);
		assertThat(reportPart2).usingRecursiveComparison().isEqualTo(reportPart);
	}
}