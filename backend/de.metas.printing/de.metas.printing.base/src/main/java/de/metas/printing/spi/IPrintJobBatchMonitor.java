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


import de.metas.printing.model.I_C_Print_Job;
import de.metas.printing.model.I_C_Print_Job_Instructions;

/**
 * Monitor to be used during {@link I_C_Print_Job} generation process. On each important event, this monitor will be informed.
 * 
 * @author tsa
 * 
 */
public interface IPrintJobBatchMonitor
{
	/**
	 * Method called before a print job is created.
	 * 
	 * @return false if we shall stop the batch processing
	 */
	boolean printJobBeforeCreate();

	/**
	 * Method called after a print job was created.
	 * 
	 * @param printJobInstructions
	 * @return false if we shall stop the batch processing
	 */
	boolean printJobCreated(I_C_Print_Job_Instructions printJobInstructions);

	/**
	 * Method called after all print jobs from batch were created
	 */
	void finish();
}
