/**
 * 
 */
package de.metas.async.spi.impl;

import java.util.Properties;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.model.MNote;

import de.metas.async.api.IAsyncBatchDAO;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.model.I_C_Async_Batch_Type;
import de.metas.async.spi.IAsyncBatchListener;

/**
 * @author cg
 *
 */
public class DefaultAsyncBatchListener implements IAsyncBatchListener
{
	private final IAsyncBatchDAO asyncBatchDAO = Services.get(IAsyncBatchDAO.class);

	@Override
	public void createNotice(final I_C_Async_Batch asyncBatch)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(asyncBatch);
		final String trxName = InterfaceWrapperHelper.getTrxName(asyncBatch);

		// exclude case of recreate invoice and invoice wizards
		final I_C_Async_Batch_Type asyncBatchType = asyncBatchDAO.retrieveAsyncBatchType(ctx, de.metas.async.api.impl.AsyncBatchDAO.ASYNC_BATCH_TYPE_VoidAndRecreateInvoice);
		final I_C_Async_Batch_Type asyncBatchTypeSingleInv = asyncBatchDAO.retrieveAsyncBatchType(ctx, de.metas.async.api.impl.AsyncBatchDAO.ASYNC_BATCH_TYPE_INVWIZ_SINGLE);
		final I_C_Async_Batch_Type asyncBatchTypeMassInv = asyncBatchDAO.retrieveAsyncBatchType(ctx, de.metas.async.api.impl.AsyncBatchDAO.ASYNC_BATCH_TYPE_INVWIZ_MASS);

		if (asyncBatch.getC_Async_Batch_Type_ID() != asyncBatchType.getC_Async_Batch_Type_ID()
				&& asyncBatch.getC_Async_Batch_Type_ID() != asyncBatchTypeMassInv.getC_Async_Batch_Type_ID()
				&& asyncBatch.getC_Async_Batch_Type_ID() != asyncBatchTypeSingleInv.getC_Async_Batch_Type_ID())
		{
			final String msg = Services.get(IMsgBL.class).getMsg(ctx, "Notice_Async_Processed", new Object[] { asyncBatch.getName() });

			final MNote note = new MNote(ctx, msg, asyncBatch.getCreatedBy(), trxName);
			note.setTextMsg(msg);
			final int AD_Table_ID = Services.get(IADTableDAO.class).retrieveTableId(I_C_Async_Batch.Table_Name);
			note.setAD_Table_ID(AD_Table_ID);
			note.setRecord(AD_Table_ID, asyncBatch.getC_Async_Batch_ID());
			note.saveEx();

		}
	}

}
