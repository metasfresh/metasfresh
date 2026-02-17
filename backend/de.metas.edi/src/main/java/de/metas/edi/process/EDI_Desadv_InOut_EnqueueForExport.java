package de.metas.edi.process;

/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2024 metas GmbH
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

import com.google.common.collect.ImmutableSet;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.edi.api.EDIExportStatus;
import de.metas.edi.api.IDesadvDAO;
import de.metas.edi.api.impl.EDIBPartnerConfigService;
import de.metas.edi.async.spi.impl.EDIWorkpackageProcessor;
import de.metas.edi.model.I_M_InOut;
import de.metas.esb.edi.model.I_EDI_Desadv;
import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.scriptedexportconversion.ExternalSystemScriptedExportConversionConfig;
import de.metas.externalsystem.scriptedexportconversion.ExternalSystemScriptedExportConversionService;
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
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.compiere.SpringContextHolder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

/**
 * This process is used if {@link EDIWorkpackageProcessor#SYS_CONFIG_OneDesadvPerShipment} is {@code Y}.
 */
public class EDI_Desadv_InOut_EnqueueForExport extends JavaProcess implements IProcessPrecondition
{
	@NonNull private final IWorkPackageQueueFactory workPackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);
	@NonNull private final ITrxItemProcessorExecutorService trxItemProcessorExecutorService = Services.get(ITrxItemProcessorExecutorService.class);
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final IDesadvDAO desadvDAO = Services.get(IDesadvDAO.class);
	@NonNull private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	@NonNull private final EDIBPartnerConfigService edibPartnerConfigService = SpringContextHolder.instance.getBean(EDIBPartnerConfigService.class);
	@NonNull private final ExternalSystemScriptedExportConversionService externalSystemScriptedExportConversionService = SpringContextHolder.instance.getBean(ExternalSystemScriptedExportConversionService.class);

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
		final Iterator<I_EDI_Desadv> selectedRecords = createIterator();
		final List<I_EDI_Desadv> replicationInterfaceDesadvs = new ArrayList<>();
		final List<I_EDI_Desadv> externalSystemDesadvs = new ArrayList<>();

		while(selectedRecords.hasNext())
		{
			final I_EDI_Desadv desadv = selectedRecords.next();
			if(!EDIExportStatus.ofCode(desadv.getEDI_ExportStatus()).isPendingOrError())
			{
				addLog("Skipped DESADV with ID {}, because of ExportStatus not pending or error (Export Status: {})", desadv.getEDI_Desadv_ID(), desadv.getEDI_ExportStatus());
				continue;
			}
			else if (edibPartnerConfigService.isDESADVReplicationInterfaceRecipient(desadv))
			{
				replicationInterfaceDesadvs.add(desadv);
			}
			else if (edibPartnerConfigService.isDESADVExternalSystemRecipient(desadv))
			{
				externalSystemDesadvs.add(desadv);
			}
			addLog("Skipped DESADV with ID {}, because of EdiBPartnerConfig not eligible", desadv.getEDI_Desadv_ID());
		}

		enqueueReplicationInterfaceDesadvs(replicationInterfaceDesadvs);
		executeExternalSystemDesadvs(externalSystemDesadvs);

		return MSG_OK;
	}

	@NonNull
	private Iterator<I_EDI_Desadv> createIterator()
	{
		final IQueryBuilder<I_EDI_Desadv> queryBuilder = queryBL.createQueryBuilder(I_EDI_Desadv.class, getCtx(), get_TrxName())
				.addOnlyActiveRecordsFilter()
				.filter(getProcessInfo().getQueryFilterOrElseFalse());

		queryBuilder.orderBy()
				.addColumn(I_EDI_Desadv.COLUMNNAME_POReference)
				.addColumn(I_EDI_Desadv.COLUMNNAME_EDI_Desadv_ID);

		final Iterator<I_EDI_Desadv> iterator = queryBuilder
				.create()
				.iterate(I_EDI_Desadv.class);
		if(!iterator.hasNext())
		{
			addLog("Found no EDI_Desadvs to enqueue within the current selection");
		}
		return iterator;
	}

	private void enqueueReplicationInterfaceDesadvs(@NonNull final List<I_EDI_Desadv> replicationInterfaceDesadvs)
	{
		if(replicationInterfaceDesadvs.isEmpty())
		{
			addLog("Found no EDI_Desadvs to enqueue via replication interface");
			return;
		}
		else
		{
			addLog("Enqueuing {} EDI_Desadvs  via replication interface", replicationInterfaceDesadvs.size());
		}

		final Properties ctx = getCtx();
		final IWorkPackageQueue queue = workPackageQueueFactory.getQueueForEnqueuing(ctx, EDIWorkpackageProcessor.class);
		trxItemProcessorExecutorService
				.<I_EDI_Desadv, Void>createExecutor()
				.setContext(ctx, ITrx.TRXNAME_None)
				.setExceptionHandler(FailTrxItemExceptionHandler.instance)
				.setProcessor(new TrxItemProcessorAdapter<I_EDI_Desadv, Void>()
				{
					@Override
					public void process(@NonNull final I_EDI_Desadv desadv)
					{
						enqueueShipmentsForDesadv(queue, desadv);
					}
				})
				.process(replicationInterfaceDesadvs.iterator());
	}

	private void enqueueShipmentsForDesadv(
			final @NonNull IWorkPackageQueue queue,
			final @NonNull I_EDI_Desadv desadv)
	{
		final List<I_M_InOut> shipments = desadvDAO.retrieveShipmentsWithStatus(desadv, ImmutableSet.of(EDIExportStatus.Pending, EDIExportStatus.Error));
		final String trxName = InterfaceWrapperHelper.getTrxName(desadv);

		for (final I_M_InOut shipment : shipments)
		{
			queue.newWorkPackage()
					.setAD_PInstance_ID(getPinstanceId())
					.bindToTrxName(trxName)
					.addElement(shipment)
					.buildAndEnqueue();

			shipment.setEDI_ExportStatus(EDIExportStatus.Enqueued.getCode());
			InterfaceWrapperHelper.save(shipment);

			addLog("Enqueued M_InOut_ID={} via Replication Interface for EDI_Desadv_ID={}", shipment.getM_InOut_ID(), desadv.getEDI_Desadv_ID());
		}
		
		if(shipments.isEmpty())
		{
			addLog("Found no M_InOuts to enqueue via Replication Interface for EDI_Desadv_ID={}", desadv.getEDI_Desadv_ID());
		}
		else
		{
			desadv.setEDI_ExportStatus(EDIExportStatus.Enqueued.getCode());
			InterfaceWrapperHelper.save(desadv);
		}
	}

	private void executeExternalSystemDesadvs(@NonNull final List<I_EDI_Desadv> externalSystemDesadvs)
	{
		if(externalSystemDesadvs.isEmpty())
		{
			addLog("Found no EDI_Desadvs to send via external system");
			return;
		}
		else
		{
			addLog("Sending {} EDI_Desadvs  via external system", externalSystemDesadvs.size());
		}

		externalSystemDesadvs.forEach(this::executeExternalSystemDesadv);
	}

	private void executeExternalSystemDesadv(@NonNull final I_EDI_Desadv desadv)
	{
		final ExternalSystemParentConfigId parentConfigId = edibPartnerConfigService.getDESADVExternalSystemParentConfigId(desadv);
		final List<ExternalSystemScriptedExportConversionConfig> configs = externalSystemScriptedExportConversionService.getByParentConfigIdAndTableAndClientId(
				parentConfigId,
				I_M_InOut.Table_Name,
				ClientId.ofRepoId(desadv.getAD_Client_ID())
		);
		final List<I_M_InOut> shipments = desadvDAO.retrieveShipmentsWithStatus(desadv, ImmutableSet.of(EDIExportStatus.Pending, EDIExportStatus.Error));

		if(shipments.isEmpty())
		{
			addLog("Found no M_InOuts to send via External System for EDI_Desadv_ID={}", desadv.getEDI_Desadv_ID());
		}
		for (final I_M_InOut shipment : shipments)
		{
			configs.forEach(config -> externalSystemScriptedExportConversionService.executeInvokeScriptedExportConversionAction(config, shipment.getM_InOut_ID()));
		}
	}
}
