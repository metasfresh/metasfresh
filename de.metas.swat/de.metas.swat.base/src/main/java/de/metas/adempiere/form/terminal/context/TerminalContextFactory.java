/**
 *
 */
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Properties;

import org.adempiere.util.Check;
import org.adempiere.util.collections.IdentityHashSet;
import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ImmutablePair;
import org.adempiere.util.proxy.WeakWrapper;
import org.compiere.Adempiere;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.POSKeyLayout;
import de.metas.adempiere.form.terminal.swing.SwingTerminalFactory;
import de.metas.logging.LogManager;

/**
 * @author tsa
 *
 */
public class TerminalContextFactory
{
	private static final TerminalContextFactory instance = new TerminalContextFactory();

	public static TerminalContextFactory get()
	{
		return TerminalContextFactory.instance;
	}

	private final transient Logger logger = LogManager.getLogger(getClass());

	/* Internal list of created terminal contexts, to avoid being GCed */
	private final IdentityHashSet<ITerminalContext> terminalContexts = new IdentityHashSet<ITerminalContext>();

	/**
	 * Create a new terminal context and references. to destroy a terminal context, please use {@link #destroy(ITerminalContext)}.
	 *
	 * @return both the newly created context and its first (and at this point only one) references instance. Also see {@link ITerminalContext#deleteReferences(ITerminalContextReferences)} on why the caller needs both.
	 */
	public IPair<ITerminalContext, ITerminalContextReferences> createContextAndRefs()
	{
		final TerminalContext terminalContext = new TerminalContext();
		final TerminalContextReferences newReferences = terminalContext.newReferences();

		// Set context
		final Properties ctx = Env.getCtx();
		terminalContext.setCtx(ctx);

		//
		// Setup components factory
		final ITerminalFactory factory = new SwingTerminalFactory(terminalContext); // TODO: support other factory implementations
		terminalContext.setTerminalFactory(factory);

		// Set logged in user
		terminalContext.setAD_User_ID(Env.getAD_User_ID(ctx));

		// Set default font size to be used
		terminalContext.setDefaultFontSize(14);

		//
		// Setup standard keyboards
		if (!Adempiere.isUnitTestMode())
		{
			terminalContext.setTextKeyLayout(new POSKeyLayout(terminalContext, 540006)); // TODO HARDCODED Keyboard UPPERCASE (de_DE)
			terminalContext.setNumericKeyLayout(new POSKeyLayout(terminalContext, 50002)); // TODO: HARDCODED numeric pad
		}

		//
		// Add terminal context to current list of terminal contexts
		terminalContexts.add(terminalContext);

		// Return the references
		return ImmutablePair.<ITerminalContext, ITerminalContextReferences> of(terminalContext, newReferences);
	}

	/**
	 *
	 * @param terminalContext the context to be destroyed. If <code>null</code> this method does nothing. This method assumes the context was created with {@link #createContextAndRefs()}.
	 */
	public void destroy(final ITerminalContext terminalContext)
	{
		if (terminalContext == null)
		{
			return;
		}

		final ITerminalContext terminalContextUnwrapped = WeakWrapper.unwrap(terminalContext);
		Check.assumeNotNull(terminalContextUnwrapped, "terminalContextUnwrapped not null");
		Check.assume(terminalContextUnwrapped instanceof TerminalContext, "Param 'terminalContext'={} is instanceof TerminalContext", terminalContext);

		final TerminalContext tcToUse = (TerminalContext)terminalContext;

		tcToUse.dispose();

		if (!terminalContexts.remove(terminalContextUnwrapped))
		{
			if (!tcToUse.isDisposed())
			{
				logger.warn("Cannot remove {} from internal terminal contexts list because it was not found."
						+ "\nCurrent contexts are: {}", new Object[] { terminalContext, terminalContexts });
			}
		}
	}
}
