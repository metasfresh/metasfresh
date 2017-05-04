package de.metas.handlingunits.allocation;

import org.compiere.util.TrxRunnable;

/*
 * #%L
 * de.metas.handlingunits.base
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

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUTransactionAttribute;
import de.metas.handlingunits.IHUTrxBL;
import de.metas.handlingunits.allocation.impl.IMutableAllocationResult;
import de.metas.handlingunits.attribute.IHUTransactionAttributeBuilder;

/**
 * Executor responsible for running {@link IHUContextProcessor}. Use one of the methods in {@link IHUTrxBL} to get an instance.
 * 
 * When you are about to process something related to HUs it is HIGHLY recommended to run your code snippet using this executor.
 *
 * This executor will do
 * <ul>
 * <li>manage database transaction</li>
 * <li>will collect and automatically process {@link IHUTransactionAttribute}s</li>
 * <li>create packing materials/empties movements if needed (see {@link IHUContext#getHUPackingMaterialsCollector()})</li>
 * </ul>
 *
 * @author User
 *
 */
public interface IHUContextProcessorExecutor
{
	/**
	 * Execute the processor and take care of everything (see interface documentation). Run the processor within a {@link TrxRunnable}, but <b>do not</b> commit on successful execution.
	 * 
	 * @param processor
	 * @return result or {@link IHUContextProcessor#NULL_RESULT} if the result is not relevant for that processing.
	 */
	IMutableAllocationResult run(IHUContextProcessor processor);

	/**
	 * Gets current {@link IHUTransactionAttributeBuilder}.
	 * 
	 * NOTE: the {@link IHUTransactionAttributeBuilder} is available only while {@link #run(IHUContextProcessor)} is running.
	 * Not before and not after that.
	 * It shall be accessed only from {@link IHUContextProcessor#process(IHUContext)}.
	 * If you are accessing it outside of that scope, an exception will be thrown.
	 * 
	 * @return current {@link IHUTransactionAttributeBuilder}; never return null.
	 */
	IHUTransactionAttributeBuilder getTrxAttributesBuilder();
}
