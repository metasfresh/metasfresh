package de.metas.edi.process;

/*
 * #%L
 * de.metas.edi
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

import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.edi.api.IDesadvDAO;
import de.metas.edi.async.spi.impl.EDIWorkpackageProcessor;
import de.metas.edi.model.I_M_InOut;
import de.metas.esb.edi.model.I_EDI_Desadv;
import de.metas.esb.edi.model.X_EDI_Desadv;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.processor.api.FailTrxItemExceptionHandler;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorExecutorService;
import org.adempiere.ad.trx.processor.spi.TrxItemProcessorAdapter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class EDI_Desadv_InOut_EnqueueForExport extends JavaProcess implements IProcessPrecondition
{
	private final IWorkPackageQueueFactory workPackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);
	private final ITrxItemProcessorExecutorService trxItemProcessorExecutorService = Services.get(ITrxItemProcessorExecutorService.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IDesadvDAO desadvDAO = Services.get(IDesadvDAO.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		if (!sysConfigBL.getBooleanValue(EDIWorkpackageProcessor.SYS_CONFIG_OneDesadvPerShipment, false))
		{
			return ProcessPreconditionsResolution.reject();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final Properties ctx = getCtx();

		final IWorkPackageQueue queue = workPackageQueueFactory.getQueueForEnqueuing(ctx, EDIWorkpackageProcessor.class);

		trxItemProcessorExecutorService
				.<I_EDI_Desadv, Void>createExecutor()
				.setContext(getCtx(), ITrx.TRXNAME_None)
				.setExceptionHandler(FailTrxItemExceptionHandler.instance)
				.setProcessor(new TrxItemProcessorAdapter<I_EDI_Desadv, Void>()
				{
					@Override
					public void process(final I_EDI_Desadv desadv)
					{
						enqueueShipmentsForDesadv(queue, desadv);
					}
				})
				.process(createIterator());

		return MSG_OK;
	}

	private void enqueueShipmentsForDesadv(
			final IWorkPackageQueue queue,
			final I_EDI_Desadv desadv)
	{
		final List<I_M_InOut> shipments = desadvDAO.retrieveShipmentsPendingExport(desadv);

		final String trxName = InterfaceWrapperHelper.getTrxName(desadv);

		shipments.forEach(shipment -> {
			queue
					.newWorkPackage()
					.setAD_PInstance_ID(getPinstanceId())
					.bindToTrxName(trxName)
					.addElement(shipment)
					.buildAndEnqueue();

			shipment.setEDI_ExportStatus(I_M_InOut.EDI_EXPORTSTATUS_Enqueued);
			InterfaceWrapperHelper.save(shipment);
		});

	}

	@NonNull
	private Iterator<I_EDI_Desadv> createIterator()
	{
		final IQueryBuilder<I_EDI_Desadv> queryBuilder = queryBL.createQueryBuilder(I_EDI_Desadv.class, getCtx(), get_TrxName())
				.addOnlyActiveRecordsFilter()
				.addInArrayOrAllFilter(I_EDI_Desadv.COLUMNNAME_EDI_ExportStatus, X_EDI_Desadv.EDI_EXPORTSTATUS_Error, X_EDI_Desadv.EDI_EXPORTSTATUS_Pending)
				.filter(getProcessInfo().getQueryFilterOrElseFalse());

		queryBuilder.orderBy()
				.addColumn(I_EDI_Desadv.COLUMNNAME_POReference)
				.addColumn(I_EDI_Desadv.COLUMNNAME_EDI_Desadv_ID);

		return queryBuilder
				.create()
				.iterate(I_EDI_Desadv.class);
	}
}
