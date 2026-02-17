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

import com.google.common.collect.ImmutableList;
import de.metas.edi.api.EDIExportStatus;
import de.metas.edi.api.impl.DesadvBL;
import de.metas.edi.api.impl.EDIBPartnerConfigService;
import de.metas.edi.async.spi.impl.EDIWorkpackageProcessor;
import de.metas.edi.process.export.enqueue.DesadvEnqueuer;
import de.metas.edi.process.export.enqueue.EnqueueDesadvRequest;
import de.metas.edi.process.export.enqueue.EnqueueDesadvResult;
import de.metas.esb.edi.model.I_EDI_Desadv;
import de.metas.esb.edi.model.X_EDI_Desadv;
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
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.compiere.SpringContextHolder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Send EDI documents for selected desadv entries.
 * This process is used if {@link EDIWorkpackageProcessor#SYS_CONFIG_OneDesadvPerShipment} is {@code N}.
 */
public class EDI_Desadv_EnqueueForExport extends JavaProcess implements IProcessPrecondition
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	@NonNull private final DesadvBL desadvBL = SpringContextHolder.instance.getBean(DesadvBL.class);
	@NonNull private final EDIBPartnerConfigService edibPartnerConfigService = SpringContextHolder.instance.getBean(EDIBPartnerConfigService.class);
	@NonNull private final ExternalSystemScriptedExportConversionService externalSystemScriptedExportConversionService = SpringContextHolder.instance.getBean(ExternalSystemScriptedExportConversionService.class);


	private final DesadvEnqueuer desadvEnqueuer = SpringContextHolder.instance.getBean(DesadvEnqueuer.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		if (sysConfigBL.getBooleanValue(EDIWorkpackageProcessor.SYS_CONFIG_OneDesadvPerShipment, false))
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

	private Iterator<I_EDI_Desadv> createIterator()
	{
		final IQueryBuilder<I_EDI_Desadv> queryBuilder = createEDIDesadvQueryBuilder();

		final Iterator<I_EDI_Desadv> iterator = queryBuilder
				.create()
				.iterate(I_EDI_Desadv.class);
		if(!iterator.hasNext())
		{
			addLog("Found no EDI_Desadvs to enqueue within the current selection");
		}
		return iterator;
	}

	private IQueryBuilder<I_EDI_Desadv> createEDIDesadvQueryBuilder()
	{
		final IQueryFilter<I_EDI_Desadv> processQueryFilter = getProcessInfo().getQueryFilterOrElseFalse();

		final IQueryBuilder<I_EDI_Desadv> queryBuilder = queryBL.createQueryBuilder(I_EDI_Desadv.class, getCtx(), get_TrxName())
				.addOnlyActiveRecordsFilter()
				.addInArrayOrAllFilter(I_EDI_Desadv.COLUMNNAME_EDI_ExportStatus, X_EDI_Desadv.EDI_EXPORTSTATUS_Error, X_EDI_Desadv.EDI_EXPORTSTATUS_Pending)
				.filter(processQueryFilter);

		queryBuilder.orderBy()
				.addColumn(I_EDI_Desadv.COLUMNNAME_POReference)
				.addColumn(I_EDI_Desadv.COLUMNNAME_EDI_Desadv_ID);

		return queryBuilder;
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

		// Enqueue selected desadvs as workpackages
		final EnqueueDesadvRequest enqueueDesadvRequest = EnqueueDesadvRequest.builder()
				.pInstanceId(getPinstanceId())
				.ctx(getCtx())
				.desadvIterator(createIterator())
				.build();

		final EnqueueDesadvResult result = desadvEnqueuer.enqueue(enqueueDesadvRequest);

		final List<I_EDI_Desadv> skippedDesadvList = result.getSkippedDesadvList();

		// display the desadvs that didn't meet the sum percentage requirement
		if (!skippedDesadvList.isEmpty())
		{
			desadvBL.createMsgsForDesadvsBelowMinimumFulfilment(ImmutableList.copyOf(skippedDesadvList));
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
				I_EDI_Desadv.Table_Name,
				ClientId.ofRepoId(desadv.getAD_Client_ID())
		);

		configs.forEach(config -> externalSystemScriptedExportConversionService.executeInvokeScriptedExportConversionAction(config, desadv.getEDI_Desadv_ID()));
	}
}
