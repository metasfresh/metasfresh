package de.metas.async.processor;

/*
 * #%L
 * de.metas.async
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


import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.IWorkpackageProcessor;

/**
 * Dispatcher used for routing {@link IQueueProcessor}'s events
 * 
 * @author tsa
 * 
 */
public interface IQueueProcessorEventDispatcher
{
	/**
	 * Register given <code>listener</code> for given workpackage
	 */
	void registerListener(IQueueProcessorListener listener, int workpackageId);

	/**
	 * Unregister given <code>listener</code> for given workpackage
	 */
	boolean unregisterListener(IQueueProcessorListener listener, int workpackageId);

	/**
	 * Unregister all listeners for given workpackage
	 */
	boolean unregisterListeners(int workpackageId);

	/**
	 * Notify all listeners that given workpackage was processed
	 */
	void fireWorkpackageProcessed(I_C_Queue_WorkPackage workPackage, IWorkpackageProcessor workPackageProcessor);
}
