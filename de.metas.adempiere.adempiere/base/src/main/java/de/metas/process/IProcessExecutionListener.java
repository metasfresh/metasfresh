package de.metas.process;

import de.metas.logging.LogManager;

/**
 * {@link IProcess} execution listener to be used by {@link ProcessExecutor}.
 *
 * @author authors of earlier versions of this class are: Jorg Janke
 * @author metas-dev <dev@metasfresh.com>
 */
public interface IProcessExecutionListener
{
	default void onProcessInitError(final ProcessInfo pi)
	{
		final ProcessExecutionResult processResult = pi.getResult();
		final Throwable cause = processResult.getThrowable();
		if (cause != null)
		{
			LogManager.getLogger(IProcessExecutionListener.class).warn("Process initialization failed: {}", pi, cause);
		}
		else
		{
			LogManager.getLogger(IProcessExecutionListener.class).warn("Process initialization failed: {}", pi);
		}
	}

	/**
	 * Lock User Interface.
	 * Called before running the process.
	 *
	 * @param pi process info
	 */
	void lockUI(ProcessInfo pi);

	/**
	 * Unlock User Interface.
	 * Called after the process was executed, even if it was a failure.
	 *
	 * @param pi process info
	 */
	void unlockUI(ProcessInfo pi);
}
