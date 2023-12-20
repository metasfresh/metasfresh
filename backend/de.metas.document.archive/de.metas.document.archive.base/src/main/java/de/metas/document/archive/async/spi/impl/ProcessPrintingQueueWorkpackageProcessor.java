package de.metas.document.archive.async.spi.impl;

import de.metas.async.api.IQueueDAO;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.archive.api.IArchiveEventManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.PO;

import java.util.List;
import java.util.Properties;

/**
 * Iterates the workpackage's POs and for each po retrieves the referencing {@code AD_Archive} records
 * and invokes {@link IArchiveEventManager#fireVoidDocument(org.compiere.model.I_AD_Archive)} on them.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public class ProcessPrintingQueueWorkpackageProcessor implements IWorkpackageProcessor
{

	@Override
	public Result processWorkPackage(@NonNull final I_C_Queue_WorkPackage workpackage, @NonNull final String localTrxName)
	{
		final IQueueDAO queueDAO = Services.get(IQueueDAO.class);

		final List<PO> list = queueDAO.retrieveAllItems(workpackage, PO.class);
		for (final PO po : list)
		{
			fireVoidDocumentForArchive(po);
		}
		return Result.SUCCESS;
	}

	private void fireVoidDocumentForArchive(@NonNull final PO po)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(po);

		final org.adempiere.archive.api.IArchiveDAO archiveDAO = Services.get(org.adempiere.archive.api.IArchiveDAO.class);

		final List<org.compiere.model.I_AD_Archive> archives = archiveDAO.retrieveLastArchives(ctx, TableRecordReference.of(po), QueryLimit.NO_LIMIT);

		for (final org.compiere.model.I_AD_Archive archive : archives)
		{
			Services.get(IArchiveEventManager.class).fireVoidDocument(archive);
		}
	}
}
