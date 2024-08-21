/**
 *
 */
package de.metas.letter.interceptor;

import de.metas.async.api.IAsyncBatchBL;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.letter.LetterConstants;
import de.metas.letter.service.SerialLetterService;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

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
	private final IAsyncBatchBL asyncBatchBL = Services.get(IAsyncBatchBL.class);
	private final SerialLetterService serialLetterService;

	public C_Async_Batch(@NonNull final SerialLetterService serialLetterService) {this.serialLetterService = serialLetterService;}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = I_C_Async_Batch.COLUMNNAME_Processed)
	public void print(final I_C_Async_Batch asyncBatch)
	{
		if (asyncBatch.isProcessed()
				&& asyncBatchBL.isAsyncBatchTypeInternalName(asyncBatch, LetterConstants.C_Async_Batch_InternalName_CreateLettersAsync))
		{
			serialLetterService.printAutomaticallyLetters(asyncBatch);
		}
	}
}
