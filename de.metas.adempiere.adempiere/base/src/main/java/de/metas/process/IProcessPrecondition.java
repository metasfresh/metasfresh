package de.metas.process;

/**
 * Let your process implement this interface if you want to use it as related process in toolbar and you want to control when the process is applicable
 *
 * @author ad
 * @task http://dewiki908/mediawiki/index.php/03077:_Related_processes_-_let_them_specify_if_are_applicable_for_a_given_context_%282012080210000093%29
 */
public interface IProcessPrecondition
{
	public static interface PreconditionsContext
	{
		String getTableName();

		<T> T getModel(final Class<T> modelClass);
	}

	/**
	 * Determines if a process should be displayed in current context.
	 * <p>
	 * <b>IMPORTANT:</b> this method will not be invoked on the same instance that shall later execute <code>prepare()</code> {@link JavaProcess#doIt(String, String, Object[])}, so it does not make any
	 * sense to set any values to be used later.
	 *
	 * @param context
	 * @return true if the process will be displayed.
	 */
	boolean isPreconditionApplicable(PreconditionsContext context);
}
