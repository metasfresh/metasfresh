/**
 * 
 */
package de.metas.async.spi.impl;

import java.util.Properties;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.MNote;

import de.metas.adempiere.model.I_AD_User;
import de.metas.async.api.IAsyncBatchDAO;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.model.I_C_Async_Batch_Type;
import de.metas.async.spi.IAsyncBatchListener;
import de.metas.notification.INotificationBL;

/**
 * @author cg
 *
 */
public class DefaultAsyncBatchListener implements IAsyncBatchListener
{
	@Override
	public void createNotice(final I_C_Async_Batch asyncBatch)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(asyncBatch);
		final String trxName = InterfaceWrapperHelper.getTrxName(asyncBatch);

		final I_C_Async_Batch_Type asyncBatchType = asyncBatch.getC_Async_Batch_Type();
		
		if (IAsyncBatchDAO.ASYNC_BATCH_TYPE_DEFAULT.equals(asyncBatchType.getInternalName()))
		{
			final String msg = Services.get(IMsgBL.class).getMsg(ctx, "Notice_Async_Processed", new Object[] { asyncBatch.getName() });

			if (asyncBatchType.isSendNotification())
			{
				final MNote note = new MNote(ctx, msg, asyncBatch.getCreatedBy(), trxName);
				note.setTextMsg(msg);
				final int AD_Table_ID = Services.get(IADTableDAO.class).retrieveTableId(I_C_Async_Batch.Table_Name);
				note.setAD_Table_ID(AD_Table_ID);
				note.setRecord(AD_Table_ID, asyncBatch.getC_Async_Batch_ID());
				note.saveEx();
			}
			
			if (asyncBatchType.isSendMail())
			{
				final I_AD_User recipient = InterfaceWrapperHelper.create(ctx, asyncBatch.getCreatedBy(), I_AD_User.class, ITrx.TRXNAME_None);
				final int AD_Table_ID = Services.get(IADTableDAO.class).retrieveTableId(I_C_Async_Batch.Table_Name);
				Services.get(INotificationBL.class).notifyUser(recipient, msg, msg, new TableRecordReference(AD_Table_ID, asyncBatch.getC_Async_Batch_ID()));
			}
			
		}
	}

}
