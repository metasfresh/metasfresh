package de.metas.printing.spi;

/*
 * #%L
 * de.metas.printing.base
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

import de.metas.document.archive.model.I_AD_Archive;
import de.metas.printing.model.I_C_Printing_Queue;

/**
 * To implement this, I recommend to extend {@link PrintingQueueHandlerAdapter}.
 */
public interface IPrintingQueueHandler
{
	/**
	 * Method will be called after the given <code>queueItem</code> for the given <code>printOut</code> has been created and it's generic fields have been set, but before it is saved.
	 * 
	 * As the item will be saved after this invocation, there is no need to save it for this method's implementors.
	 */
	void afterEnqueueBeforeSave(I_C_Printing_Queue queueItem, I_AD_Archive printOut);

	/**
	 * Method will be called after the item was saved.
	 */
	void afterEnqueueAfterSave(I_C_Printing_Queue queueItem, I_AD_Archive printOut);

	/**
	 * Specifies if a given handler shall be invoked for a particular queueItem and printOut.<br>
	 * Note: not implemented by the adapter, so each concrete handler has to implement this method.
	 */
	boolean isApplyHandler(I_C_Printing_Queue queueItem, I_AD_Archive printOut);
}
