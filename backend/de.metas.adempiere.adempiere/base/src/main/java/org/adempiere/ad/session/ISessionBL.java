package org.adempiere.ad.session;

import de.metas.util.ISingletonService;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Properties;

public interface ISessionBL extends ISingletonService
{
	MFSession getSessionById(final Properties ctx, final int AD_Session_ID);

	/**
	 * @return current session or null
	 */
	@Nullable
	MFSession getCurrentSession(Properties ctx);

	/**
	 * @return the current session's id, creating a session if there is none (e.g. a batch/import save with
	 * no logged-in user) — so a caller stamping an audit column always gets a real {@code AD_Session_ID}
	 * rather than a null it would have to special-case.
	 */
	@NonNull
	default AdSessionId getCurrentOrCreateSessionId(final Properties ctx)
	{
		return AdSessionId.ofRepoId(getCurrentOrCreateNewSession(ctx).getAD_Session_ID());
	}

	/**
	 * Gets current session if exists. If not, creates a new session
	 *
	 * @return current session (existing or new); never returns null
	 */
	MFSession getCurrentOrCreateNewSession(Properties ctx);

	void logoutCurrentSession();

	/**
	 * @return true if record change log system is enabled
	 */
	boolean isChangeLogEnabled();

	/**
	 * Disable change log system on current thread.
	 * <p>
	 * Useful if for example, you are running a process which is creating/changing a lot of master data which has change log enabled but in your case you don't want to do change logs because of
	 * performance issues.
	 *
	 * @param disable true if it shall be disabled on current thread
	 */
	void setDisableChangeLogsForThread(final boolean disable);

}
