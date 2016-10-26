package de.metas.dlm.partitioner.async;

import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.api.IParams;
import org.adempiere.util.time.SystemTime;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableMap;

import de.metas.async.api.IQueueDAO;
import de.metas.async.api.IWorkPackageBlockBuilder;
import de.metas.async.api.IWorkpackageParamDAO;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.connection.IConnectionCustomizerService;
import de.metas.dlm.Partition;
import de.metas.dlm.model.I_DLM_Partition_Config;
import de.metas.dlm.partitioner.IPartitionerService;
import de.metas.dlm.partitioner.PartitionRequestFactory;
import de.metas.dlm.partitioner.PartitionRequestFactory.CreatePartitionAsyncRequest;
import de.metas.dlm.partitioner.PartitionRequestFactory.CreatePartitionRequest;
import de.metas.dlm.partitioner.PartitionRequestFactory.CreatePartitionRequest.OnNotDLMTable;
import de.metas.dlm.partitioner.config.PartitionerConfig;

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

	/**
	 *
	 * @param request the request to enqueue.
	 * @param adPInstanceId <code>AD_Pisntance_ID</code> from which the package was scheduled. Optional, can be less or equal 0
	 */
	public static void schedule(CreatePartitionAsyncRequest request, int adPInstanceId)
	{
		final Properties ctx = Env.getCtx();

		final I_DLM_Partition_Config model = InterfaceWrapperHelper.create(ctx,
				request.getConfig().getDLM_Partition_Config_ID(),
				I_DLM_Partition_Config.class,
				ITrx.TRXNAME_ThreadInherited);

		final ImmutableMap<String, ? extends Object> parameters = ImmutableMap.of(
				PARAM_OLDEST_FIRST, request.isOldestFirst(),
				PARAM_COUNT, request.getCount(),
				PARAM_DONT_REENQUEUE_AFTER, request.getDontReEnqueueAfter());

		final IWorkPackageBlockBuilder blockBuilder = Services.get(IWorkPackageQueueFactory.class)
				.getQueueForEnqueuing(ctx, DLMPartitionerWorkpackageProcessor.class)
				.newBlock()
				.setContext(ctx);

		if (adPInstanceId > 0)
		{
			blockBuilder.setAD_PInstance_Creator_ID(adPInstanceId);
		}

		blockBuilder.newWorkpackage()

		// Workpackage Parameters
				.parameters()
				.setParameters(parameters)
				.end()

		// Workpackage elements
				.addElement(model)

		// Build & enqueue
				.build();

	}

	@Override
	public Result processWorkPackage(I_C_Queue_WorkPackage workPackage, String localTrxName)
	{
		final IQueueDAO queueDAO = Services.get(IQueueDAO.class);
		final IWorkpackageParamDAO workpackageParamDAO = Services.get(IWorkpackageParamDAO.class);
		final IPartitionerService partitionerService = Services.get(IPartitionerService.class);
		final IConnectionCustomizerService connectionCustomizerService = Services.get(IConnectionCustomizerService.class);

		final IParams workpackageParams = workpackageParamDAO.retrieveWorkpackageParams(workPackage);

		final boolean oldestFirst = workpackageParams.getParameterAsBool(PARAM_OLDEST_FIRST);
		final int count = workpackageParams.getParameterAsInt(PARAM_COUNT);
		final Timestamp dontReEnQueueAfter = workpackageParams.getParameterAsTimestamp(PARAM_DONT_REENQUEUE_AFTER);

		final List<I_DLM_Partition_Config> configs = queueDAO.retrieveItems(workPackage, I_DLM_Partition_Config.class, localTrxName);

		final I_DLM_Partition_Config singleConfigDB = configs.get(0);

		final PartitionerConfig config = partitionerService.loadPartitionConfig(singleConfigDB);

		// note that the partitioner itself only cares about "sync" requests.
		final CreatePartitionRequest request = PartitionRequestFactory.builder()
				.setConfig(config)
				.setOldestFirst(oldestFirst)
				.setOnNotDLMTable(OnNotDLMTable.FAIL)
				.build();

		try (final AutoCloseable temporaryCustomizer = connectionCustomizerService.registerTemporaryCustomizer(partitionerService.createConnectionCustomizer()))
		{
			final Partition partition = partitionerService.createPartition(request);
			if (partition.getRecords().isEmpty())
			{
				return Result.SUCCESS;
			}
			partitionerService.storePartition(partition);
		}
		catch (Exception e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}

		if (count >= 1 && dontReEnQueueAfter != null && SystemTime.asDate().before(dontReEnQueueAfter))
		{
			// create a new async request and enqueue it
			final CreatePartitionAsyncRequest newRequest = PartitionRequestFactory.asyncBuilder(request)
					.setCount(count - 1)
					.setDontReEnqueueAfter(dontReEnQueueAfter).build();

			schedule(newRequest,
					workPackage.getC_Queue_Block().getAD_PInstance_Creator_ID());
		}

		return Result.SUCCESS;
	}
}
