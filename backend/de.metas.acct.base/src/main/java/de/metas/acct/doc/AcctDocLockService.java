package de.metas.acct.doc;

import de.metas.error.AdIssueId;
import org.compiere.acct.PostingStatus;

import javax.annotation.Nullable;

public interface AcctDocLockService
{
	/**
	 * @return true if locked
	 */
	boolean lock(AcctDocModel docModel, boolean force, boolean repost);

	/**
	 * @return true if unlocked
	 */
	boolean unlock(AcctDocModel docModel,
				   @Nullable PostingStatus newPostingStatus,
				   @Nullable AdIssueId postingErrorIssueId);
}
