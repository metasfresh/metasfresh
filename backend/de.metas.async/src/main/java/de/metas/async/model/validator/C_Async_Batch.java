/**
 *
 */
package de.metas.async.model.validator;

import de.metas.async.api.AsyncBatchType;
import de.metas.async.api.IAsyncBatchBL;
import de.metas.async.api.IAsyncBatchListeners;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.model.X_C_Async_Batch_Type;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.compiere.model.ModelValidator;

import java.util.Objects;

/**
 * @author cg
 *
 */

@Validator(I_C_Async_Batch.class)
public class C_Async_Batch
{
	private final IAsyncBatchBL asyncBatchBL = Services.get(IAsyncBatchBL.class);
	private final IAsyncBatchListeners asyncBatchListeners = Services.get(IAsyncBatchListeners.class);

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE,
			ifColumnsChanged = I_C_Async_Batch.COLUMNNAME_Processed)
	public void updateProcessed(final I_C_Async_Batch asyncBatch)
	{
		//
		// Our batch was processed right now => notify user by sending email
		if (asyncBatch.isProcessed()
				&& isNotificationType(asyncBatch, X_C_Async_Batch_Type.NOTIFICATIONTYPE_AsyncBatchProcessed))
		{
			asyncBatchListeners.notify(asyncBatch);
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE,
			ifColumnsChanged = { I_C_Async_Batch.COLUMNNAME_CountProcessed, I_C_Async_Batch.COLUMNNAME_LastProcessed_WorkPackage_ID })
	public void updateCountProcessed(final I_C_Async_Batch asyncBatch)
	{
		//
		// Our batch was not processed => notify user with note when workpackage processed
		if (!asyncBatch.isProcessed()
				&& isNotificationType(asyncBatch, X_C_Async_Batch_Type.NOTIFICATIONTYPE_WorkpackageProcessed))
		{
			asyncBatchListeners.notify(asyncBatch);
		}
	}

	private boolean isNotificationType(@NonNull final I_C_Async_Batch asyncBatch, @NonNull final String expectedNotificationType)
	{
		final String notificationType = asyncBatchBL.getAsyncBatchType(asyncBatch).map(AsyncBatchType::getNotificationType).orElse(null);
		return Objects.equals(notificationType, expectedNotificationType);
	}
}
