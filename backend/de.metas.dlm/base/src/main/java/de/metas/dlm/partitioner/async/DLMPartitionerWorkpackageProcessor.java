package de.metas.dlm.partitioner.async;

import com.google.common.collect.ImmutableMap;
import de.metas.async.api.IQueueDAO;
import de.metas.async.api.IWorkPackageBuilder;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.api.IWorkpackageParamDAO;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.common.util.time.SystemTime;
import de.metas.dlm.IDLMService;
import de.metas.dlm.model.I_DLM_Partition_Config;
import de.metas.dlm.partitioner.IPartitionerService;
import de.metas.dlm.partitioner.PartitionRequestFactory;
import de.metas.dlm.partitioner.PartitionRequestFactory.CreatePartitionAsyncRequest;
import de.metas.dlm.partitioner.PartitionRequestFactory.CreatePartitionRequest;
import de.metas.dlm.partitioner.PartitionRequestFactory.CreatePartitionRequest.OnNotDLMTable;
import de.metas.dlm.partitioner.config.PartitionConfig;
import de.metas.process.PInstanceId;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.api.IParams;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Env;

import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

/*
 * #%L
 * metasfresh-dlm
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class DLMPartitionerWorkpackageProcessor extends WorkpackageProcessorAdapter
{

	private static final String PARAM_OLDEST_FIRST = "OldestFirst";
	private static final String PARAM_DONT_REENQUEUE_AFTER = "DontReEnqueueAfter";
	private static final String PARAM_COUNT = "Count";
	private static final String PARAM_DLM_PARTITION_CONFIG_ID = "DLM_Partition_Config_ID";

	/**
	 *
	 * @param request the request to enqueue.
	 * @param adPInstanceId <code>AD_Pisntance_ID</code> from which the package was scheduled. Optional, can be less or equal 0
	 * @return
	 */
	public static I_C_Queue_WorkPackage schedule(final CreatePartitionAsyncRequest request, final PInstanceId adPInstanceId)
	{
		final Properties ctx = Env.getCtx();

		final ImmutableMap<String, ? extends Object> parameters = ImmutableMap.of(
				PARAM_OLDEST_FIRST, request.isOldestFirst(),
				PARAM_COUNT, request.getCount(),
				PARAM_DONT_REENQUEUE_AFTER, request.getDontReEnqueueAfter(),
				PARAM_DLM_PARTITION_CONFIG_ID, request.getConfig().getDLM_Partition_Config_ID());

		final IWorkPackageQueue queue = Services.get(IWorkPackageQueueFactory.class)
				.getQueueForEnqueuing(ctx, DLMPartitionerWorkpackageProcessor.class);

		final IWorkPackageBuilder wpBuilder = queue
				.newWorkPackage()
				.setAD_PInstance_ID(adPInstanceId)
				.parameters(parameters);

		// Workpackage element
		if (request.getRecordToAttach() != null)
		{
			wpBuilder.addElement(request.getRecordToAttach());
		}

		// Build & enqueue
		return wpBuilder.buildAndEnqueue();
	}

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workPackage, final String localTrxName)
	{
		final IQueueDAO queueDAO = Services.get(IQueueDAO.class);
		final IWorkpackageParamDAO workpackageParamDAO = Services.get(IWorkpackageParamDAO.class);
		final IPartitionerService partitionerService = Services.get(IPartitionerService.class);
		final IDLMService dlmService = Services.get(IDLMService.class);

		final IParams workpackageParams = workpackageParamDAO.retrieveWorkpackageParams(workPackage);

		final boolean oldestFirst = workpackageParams.getParameterAsBool(PARAM_OLDEST_FIRST);

		final int count = workpackageParams.getParameterAsInt(PARAM_COUNT, 0);

		final Timestamp dontReEnQueueAfter = workpackageParams.getParameterAsTimestamp(PARAM_DONT_REENQUEUE_AFTER);

		final int dlmConfigId = workpackageParams.getParameterAsInt(PARAM_DLM_PARTITION_CONFIG_ID, -1);
		final I_DLM_Partition_Config configDB = InterfaceWrapperHelper.create(InterfaceWrapperHelper.getCtx(workPackage), dlmConfigId, I_DLM_Partition_Config.class, ITrx.TRXNAME_None);
		final PartitionConfig config = dlmService.loadPartitionConfig(configDB);

		final ILoggable loggable = Loggables.get();

		final ITableRecordReference tableRefToAttach;
		final List<Object> recordsToAttach = queueDAO.retrieveAllItems(workPackage, Object.class); // note that according to the 'schedule' method, there can be max one item
		if (recordsToAttach.isEmpty())
		{
			tableRefToAttach = null;
		}
		else
		{
			tableRefToAttach = TableRecordReference.ofOrNull(recordsToAttach.get(0));
		}

		// note that the partitioner itself only cares about "sync" requests.
		final CreatePartitionRequest request = PartitionRequestFactory.builder()
				.setConfig(config)
				.setOldestFirst(oldestFirst)
				.setRecordToAttach(tableRefToAttach)
				.setOnNotDLMTable(OnNotDLMTable.FAIL)
				.build();

		loggable.addLog("Going to invoke the partitioner with CreatePartitionRequest={}", request);

		partitionerService.createPartition(request);

		final boolean timeIsOver = dontReEnQueueAfter != null && SystemTime.asDate().after(dontReEnQueueAfter);
		if (timeIsOver)
		{
			loggable.addLog("The time {}={} given to us as parameter has passed; not enqueing another work package.", PARAM_DONT_REENQUEUE_AFTER, dontReEnQueueAfter);
		}
		final boolean countExceeded = count <= 1;
		if (countExceeded)
		{
			loggable.addLog("The count {}={} given to us as parameter means that we won't enqueue another work package.", PARAM_COUNT, count);
		}

		if (!countExceeded && !timeIsOver)
		{
			// create a new async request and enqueue it
			final CreatePartitionAsyncRequest newRequest = PartitionRequestFactory.asyncBuilder(request)
					.setCount(count - 1)
					.setDontReEnqueueAfter(dontReEnQueueAfter).build();

			final PInstanceId pinstanceId = PInstanceId.ofRepoIdOrNull(workPackage.getAD_PInstance_ID());
			final I_C_Queue_WorkPackage nextWorkPackage = schedule(newRequest, pinstanceId);

			loggable.addLog("Scheduled C_Queue_WorkPackage={} with CreatePartitionAsyncRequest={}", nextWorkPackage, newRequest);
		}
		return Result.SUCCESS;
	}
}
