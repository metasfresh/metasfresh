package de.metas.acct.gljournal;

import de.metas.document.DocTypeId;
import de.metas.organization.OrgId;
import lombok.NonNull;
import org.adempiere.service.ClientId;
import org.compiere.model.I_GL_Journal;

import de.metas.util.ISingletonService;
import org.compiere.model.I_GL_JournalLine;

import java.util.List;

public interface IGLJournalBL extends ISingletonService
{

	/**
	 * Document Status is Complete or Closed
	 *
	 * @return true if CO, CL or RE
	 */
	boolean isComplete(I_GL_Journal glJournal);

	void unpost(I_GL_Journal glJournal);

	DocTypeId getDocTypeGLJournal(ClientId clientId, OrgId orgId);

	void assertSamePeriod(
			@NonNull I_GL_Journal journal,
			@NonNull I_GL_JournalLine line);

	void assertSamePeriod(
			@NonNull I_GL_Journal journal,
			@NonNull List<I_GL_JournalLine> lines);
}
