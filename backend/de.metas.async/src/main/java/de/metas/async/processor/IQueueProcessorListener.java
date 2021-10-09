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
 * Queue Processor Listener
 * 
 * @author tsa
 * 
 */
public interface IQueueProcessorListener
{
	/**
	 * Method called when an workpackage was processed.
	 * 
	 * @param workpackageProcessor workpackage processor used to process the workpackage
	 */
	void onWorkpackageProcessed(final I_C_Queue_WorkPackage workpackage, final IWorkpackageProcessor workpackageProcessor);
}
