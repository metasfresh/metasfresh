package de.metas.adempiere.form.terminal.swing;

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


import java.awt.Component;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.adempiere.form.terminal.IExecuteBeforePainingSupport;

/**
 * {@link IExecuteBeforePainingSupport} for {@link java.awt.Component}.
 * 
 * @author tsa
 *
 */
public class ComponentExecuteBeforePainingSupport implements IExecuteBeforePainingSupport
{
	// services
	private static final transient Logger logger = LogManager.getLogger(ComponentExecuteBeforePainingSupport.class);

	private final Component comp;

	private final Queue<Runnable> updaters = new ConcurrentLinkedQueue<>();

	public ComponentExecuteBeforePainingSupport(final Component comp)
	{
		super();
		Check.assumeNotNull(comp, "comp not null");
		this.comp = comp;
	}

	/**
	 * Execute all enqueued updaters.
	 */
	public final void executeEnqueuedUpdaters()
	{
		// guard against null when it's called when object is not yet constructed
		if (updaters == null)
		{
			return;
		}

		//
		// Iterate updaters and execute them
		Runnable updater;
		while ((updater = updaters.poll()) != null)
		{
			executeUpdater(updater);
		}
	}

	/**
	 * Execute given updater.
	 * 
	 * @param updater
	 */
	private final void executeUpdater(final Runnable updater)
	{
		try
		{
			updater.run();
		}
		catch (final Exception e)
		{
			logger.warn("Error while running updater " + updater + " on " + this + ". Ignored.", e);
		}
	}

	@Override
	public void executeBeforePainting(final Runnable updater)
	{
		if (updater == null)
		{
			return;
		}

		// If component is showing then execute the updater right away
		if (comp.isShowing())
		{
			executeEnqueuedUpdaters();
			executeUpdater(updater);
		}
		// If component is not showing, enqueue the updater
		else
		{
			updaters.remove(updater);
			updaters.add(updater);
			comp.invalidate(); // flag that the component needs to be replainted
		}
	}

	@Override
	public final void clearExecuteBeforePaintingQueue()
	{
		updaters.clear();
	}
}
