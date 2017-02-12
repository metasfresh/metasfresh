package de.metas.adempiere.form.terminal.context;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.adempiere.util.IMultitonService;

import de.metas.adempiere.form.terminal.IDisposable;

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
