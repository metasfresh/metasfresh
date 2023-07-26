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


import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;

import de.metas.async.model.I_C_Queue_PackageProcessor;
import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.util.ISingletonService;

public interface IWorkpackageProcessorFactory extends ISingletonService
{
	IWorkpackageProcessor getWorkpackageProcessor(Properties ctx, int packageProcessorId);

	IWorkpackageProcessor getWorkpackageProcessor(I_C_Queue_PackageProcessor packageProcessorDef);

	boolean isWorkpackageProcessorBlacklisted(final int workpackageProcessorId);

	/**
	 * Validate given package processor
	 * 
	 * @param packageProcessorDef
	 * @throws AdempiereException if something went wrong (i.e. package processor definition is not ok)
	 */
	void validateWorkpackageProcessor(I_C_Queue_PackageProcessor packageProcessorDef);

	/**
	 * Gets mutable statistics for given workpackageProcessor
	 * 
	 * @param workpackageProcessor
	 * @return mutable statistics instance
	 */
	IMutableQueueProcessorStatistics getWorkpackageProcessorStatistics(final IWorkpackageProcessor workpackageProcessor);

	/**
	 * Gets mutable statistics for given workpackageProcessorDef
	 * 
	 * @param workpackageProcessorDef
	 * @return mutable statistics instance
	 */
	IMutableQueueProcessorStatistics getWorkpackageProcessorStatistics(final I_C_Queue_PackageProcessor workpackageProcessorDef);
}
