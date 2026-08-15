package org.adempiere.ad.session;

import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;

/**
 * {@code AD_Session_ID} — the session a save or a process ran under.
 */
@Value
public class AdSessionId implements RepoIdAware
{
	int repoId;

	public static AdSessionId ofRepoId(final int repoId)
	{
		return new AdSessionId(repoId);
	}

	@Nullable
	public static AdSessionId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new AdSessionId(repoId) : null;
	}

	/**
	 * @return the repo id, or {@code -1} for {@code null} — the "no id" sentinel the PO layer stores as SQL
	 * {@code NULL}, whereas a {@code 0} would be persisted as a literal zero.
	 */
	public static int toRepoId(@Nullable final AdSessionId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	private AdSessionId(final int adSessionId)
	{
		repoId = Check.assumeGreaterThanZero(adSessionId, "adSessionId");
	}
}
