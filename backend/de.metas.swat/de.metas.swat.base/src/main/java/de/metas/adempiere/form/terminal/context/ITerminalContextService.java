package de.metas.adempiere.form.terminal.context;

import de.metas.adempiere.form.terminal.IDisposable;
import de.metas.util.IMultitonService;

/**
 * A regular {@link IMultitonService} which also supports setting and getting the {@link ITerminalContext}.
 *
 * When implementations of this interface will be automatically created and instantiated by {@link ITerminalContext}, the terminal context will be set automatically.
 *
 * Also see {@link ITerminalContext#getService(Class)}.
 *
 * @author tsa
 *
 */
public interface ITerminalContextService extends IMultitonService, IDisposable
{
	/**
	 * Configures {@link ITerminalContext} to be used by this service.
	 *
	 * NOTE: please don't call it directly, it's called only by API.
	 *
	 * @param terminalContext
	 */
	void setTerminalContext(ITerminalContext terminalContext);

	/**
	 * Service's {@link ITerminalContext}
	 *
	 * @return terminal context; never return null
	 */
	ITerminalContext getTerminalContext();
}
