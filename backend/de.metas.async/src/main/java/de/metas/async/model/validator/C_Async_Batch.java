/**
 * 
 */
package de.metas.async.model.validator;

import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidator;

import de.metas.async.api.IAsyncBatchListeners;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.model.X_C_Async_Batch_Type;

/**
 * @author cg
 *
 */

@Validator(I_C_Async_Batch.class)
public class C_Async_Batch
{

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE,
			ifColumnsChanged = I_C_Async_Batch.COLUMNNAME_Processed)
	public void updateProcessed(final I_C_Async_Batch asyncBatch)
	{
		//
		// Our batch was processed right now => notify user by sending email
		if (asyncBatch.isProcessed() && X_C_Async_Batch_Type.NOTIFICATIONTYPE_AsyncBatchProcessed.equals(asyncBatch.getC_Async_Batch_Type().getNotificationType()))
		{
			Services.get(IAsyncBatchListeners.class).notify(asyncBatch);
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE,
			ifColumnsChanged = { I_C_Async_Batch.COLUMNNAME_CountProcessed, I_C_Async_Batch.COLUMNNAME_LastProcessed_WorkPackage_ID })
	public void updateCountProcessed(final I_C_Async_Batch asyncBatch)
	{
		//
		// Our batch was not processed => notify user with note when workpackage processed
		if (!asyncBatch.isProcessed() && X_C_Async_Batch_Type.NOTIFICATIONTYPE_WorkpackageProcessed.equals(asyncBatch.getC_Async_Batch_Type().getNotificationType()))
		{
			Services.get(IAsyncBatchListeners.class).notify(asyncBatch);
		}
	}

}
