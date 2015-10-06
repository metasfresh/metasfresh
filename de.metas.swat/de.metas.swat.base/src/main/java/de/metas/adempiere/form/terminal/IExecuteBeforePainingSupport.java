package de.metas.adempiere.form.terminal;

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


/**
 * Classes which implement this interface are able to be lazy updated/painted.
 * 
 * i.e. you just need to enque your updater and next time the component will be painted, your updater will be also executed.
 * 
 * @author tsa
 *
 */
public interface IExecuteBeforePainingSupport
{
	/**
	 * Enqueue an <code>updater</code> which will be executed right before the underlying component is about to be painted by UI.
	 * 
	 * If component is in the visible area of the screen, the updater will be executed immediatelly.
	 * 
	 * @param updater
	 */
	void executeBeforePainting(Runnable updater);

	/**
	 * Remove all enqueued updaters (if any), so they will never be executed.
	 */
	void clearExecuteBeforePaintingQueue();

}