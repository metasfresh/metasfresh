package de.metas.acct.gljournal;

import de.metas.document.DocTypeId;
import de.metas.organization.OrgId;
<<<<<<< HEAD
=======
import lombok.NonNull;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import org.adempiere.service.ClientId;
import org.compiere.model.I_GL_Journal;

import de.metas.util.ISingletonService;
<<<<<<< HEAD
=======
import org.compiere.model.I_GL_JournalLine;

import java.util.List;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

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
<<<<<<< HEAD
=======

	void assertSamePeriod(
			@NonNull I_GL_Journal journal,
			@NonNull I_GL_JournalLine line);

	void assertSamePeriod(
			@NonNull I_GL_Journal journal,
			@NonNull List<I_GL_JournalLine> lines);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
