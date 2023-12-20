package de.metas.gplr.report.repository;

import com.google.common.annotations.VisibleForTesting;
import de.metas.gplr.report.model.GPLRReportNote;
import de.metas.organization.OrgId;
import lombok.NonNull;
import org.compiere.model.I_GPLR_Report_Note;

public class GPLRReportNote_Mapper
{
	static void updateRecord(
			@NonNull final I_GPLR_Report_Note record,
			@NonNull final GPLRReportNote from,
			@NonNull final OrgId orgId)
	{
		record.setAD_Org_ID(orgId.getRepoId());
		record.setSource(from.getSourceDocument());
		record.setNote(from.getText());
	}

	@VisibleForTesting
	static GPLRReportNote fromRecord(
			@NonNull final I_GPLR_Report_Note record)
	{
		return GPLRReportNote.builder()
				.sourceDocument(record.getSource())
				.text(record.getNote())
				.build();
	}
}
