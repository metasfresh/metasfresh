package de.metas.async.model.validator;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidator;

import de.metas.async.model.I_C_Queue_Processor;
import de.metas.async.processor.IQueueProcessorExecutorService;
import de.metas.async.processor.IQueueProcessorsExecutor;

@Validator(I_C_Queue_Processor.class)
public class C_Queue_Processor
{
	@ModelChange(timings = ModelValidator.TYPE_AFTER_DELETE)
	public void processorDeleted(final I_C_Queue_Processor queueProcessorDef)
	{
		final IQueueProcessorExecutorService queueProcessorExecutorService = Services.get(IQueueProcessorExecutorService.class);
		if (queueProcessorExecutorService.isInitialized())
		{
			final IQueueProcessorsExecutor executor = queueProcessorExecutorService.getExecutor();
			executor.removeQueueProcessor(queueProcessorDef.getC_Queue_Processor_ID());
		}
	}
}
