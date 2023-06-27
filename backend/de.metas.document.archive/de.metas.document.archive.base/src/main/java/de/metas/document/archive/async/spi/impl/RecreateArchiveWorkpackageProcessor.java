package de.metas.document.archive.async.spi.impl;

import de.metas.async.Async_Constants;
import de.metas.async.api.IQueueDAO;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.document.archive.spi.impl.DefaultModelArchiver;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.ad.persistence.TableModelLoader;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.PO;

import java.util.List;
import java.util.Properties;

/**
 * @author cg
 *
 */
public class RecreateArchiveWorkpackageProcessor implements IWorkpackageProcessor
{
	// services
	private final IQueueDAO queueDAO = Services.get(IQueueDAO.class);
	private final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workpackage, final String localTrxName)
	{
		final  I_C_Async_Batch asyncBatch = workpackage.getC_Async_Batch();
		Check.assumeNotNull(asyncBatch, "Async batch is not null");

		final Properties ctx = InterfaceWrapperHelper.getCtx(workpackage);
		final String trxName = InterfaceWrapperHelper.getTrxName(workpackage);

		final List<I_C_Doc_Outbound_Log> logs = queueDAO.retrieveAllItems(workpackage, I_C_Doc_Outbound_Log.class);

		logs.forEach(docOutboundLog -> {
			final PO po = TableModelLoader.instance.getPO(ctx, adTableDAO.retrieveTableName(docOutboundLog.getAD_Table_ID()), docOutboundLog.getRecord_ID(), trxName);
			if (workpackage.getC_Async_Batch_ID() > 0)
			{
				InterfaceWrapperHelper.setDynAttribute(po, Async_Constants.C_Async_Batch, workpackage.getC_Async_Batch());
			}

			DefaultModelArchiver.of(po).archive();
			InterfaceWrapperHelper.save(docOutboundLog);
		});
		return Result.SUCCESS;
	}

}
