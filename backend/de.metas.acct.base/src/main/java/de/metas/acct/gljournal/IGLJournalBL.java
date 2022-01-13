package de.metas.acct.gljournal;

import de.metas.document.DocTypeId;
import de.metas.organization.OrgId;
import org.adempiere.service.ClientId;
import org.compiere.model.I_GL_Journal;

import de.metas.util.ISingletonService;

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
}
