/**
 *
 */
package de.metas.letter.interceptor;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.Adempiere;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.async.model.I_C_Async_Batch;
import de.metas.letter.LetterConstants;
import de.metas.letter.service.SerialLetterService;

/*
 * #%L
 * marketing-serialleter
 * %%
 * Copyright (C) 2018 metas GmbH
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
@Interceptor(I_C_Async_Batch.class)
@Component
public class C_Async_Batch
{
	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = I_C_Async_Batch.COLUMNNAME_Processed)
	public void print(final I_C_Async_Batch asyncBatch)
	{

		if (asyncBatch.isProcessed()
				&& asyncBatch.getC_Async_Batch_Type_ID() > 0
				&& LetterConstants.C_Async_Batch_InternalName_CreateLettersAsync.equals(asyncBatch.getC_Async_Batch_Type().getInternalName()))
		{
			runPrintingProcess(asyncBatch); 
		}
	}

	private void runPrintingProcess(final I_C_Async_Batch asyncBatch)
	{
		final SerialLetterService serialLetterService = Adempiere.getBean(SerialLetterService.class);
		serialLetterService.printAutomaticallyLetters(asyncBatch);
	}
}
