/**
 *
 */
package de.metas.letter.interceptor;

import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.MPInstance;
import org.compiere.model.ModelValidator;

import de.metas.async.api.IAsyncBatchListeners;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.model.X_C_Async_Batch_Type;
import de.metas.process.ProcessInfo;
import de.metas.process.ProcessInfoParameter;

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

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = I_C_Async_Batch.COLUMNNAME_Processed)
	public void print(final I_C_Async_Batch asyncBatch)
	{

		if (asyncBatch.isProcessed())
		{
			//
			// trigger printing if was an invoicing batch
			if (MO73Constants.C_Async_Batch_InternalName_InvoicingWizardSingle.equals(asyncBatch.getC_Async_Batch_Type().getInternalName())
					|| MO73Constants.C_Async_Batch_InternalName_InvoicingWizardMass.equals(asyncBatch.getC_Async_Batch_Type().getInternalName())
					|| InvoiceCandidate_Constants.C_Async_Batch_InternalName_InvoiceCandidate.equals(asyncBatch.getC_Async_Batch_Type().getInternalName())
					|| Dunning_Constants.C_Async_Batch_InternalName_DunningDoc.equals(asyncBatch.getC_Async_Batch_Type().getInternalName())
					|| Archive_Constants.C_Async_Batch_InternalName_ReCreatePDF.equals(asyncBatch.getC_Async_Batch_Type().getInternalName()))
			{
				runPrintingProcess(asyncBatch);
			}
		}

	}

	private void runPrintingProcess(final I_C_Async_Batch asyncBatch)
	{
		final IProcessPA processPA = Services.get(IProcessPA.class);
		final int processId = processPA.retrieveProcessId(PrintDocumentsAutomatically.class, null);
		Check.assume(processId > 0, "No process found");

		final Properties ctx = InterfaceWrapperHelper.getCtx(asyncBatch);
		final ITrx trx = Services.get(ITrxManager.class).getTrx(InterfaceWrapperHelper.getTrxName(asyncBatch));
		Check.assume(trx != null, "trx not null");

		// do not try to pdf print the context is not ok for pdf printing
		final boolean contextOK = prepareContext(ctx);
		if (!contextOK)
		{
			return;
		}

		final MPInstance instance = new MPInstance(ctx, processId, 0, 0); // tableId=0, Record_ID=0
		instance.saveEx();

		final ProcessInfo pi = new ProcessInfo(PrintDocumentsAutomatically.MSG_PrintDocumentsAutomatically, processId);
		pi.setTitle(PrintDocumentsAutomatically.MSG_PrintDocumentsAutomatically);
		pi.setAD_PInstance_ID(instance.getAD_PInstance_ID());
		pi.setAD_User_ID(asyncBatch.getCreatedBy()); // we need this to set in here in order to be user as userToPrint
		pi.setCtx(ctx);

		final ProcessInfoParameter asyncBatchId = new ProcessInfoParameter(I_C_Async_Batch.COLUMNNAME_C_Async_Batch_ID, asyncBatch.getC_Async_Batch_ID(), null, I_C_Async_Batch.COLUMNNAME_C_Async_Batch_ID, null);

		final String itemName;
		if (Dunning_Constants.C_Async_Batch_InternalName_DunningDoc.equals(asyncBatch.getC_Async_Batch_Type().getInternalName()))
		{
			itemName = I_C_Printing_Queue.ITEM_NAME_Mahnung;
		}
		else
		{
			itemName = I_C_Printing_Queue.ITEM_NAME_Rechnung;
		}

		final ProcessInfoParameter itemNameParam = new ProcessInfoParameter(I_C_Printing_Queue.COLUMNNAME_ItemName, itemName, null, I_C_Printing_Queue.COLUMNNAME_ItemName, null);
		pi.setParameter(new ProcessInfoParameter[] {asyncBatchId,itemNameParam});

		// run process
		try
		{
			ProcessCtl.process(null, 0, null, pi, trx);
		}
		catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}

	}

}
