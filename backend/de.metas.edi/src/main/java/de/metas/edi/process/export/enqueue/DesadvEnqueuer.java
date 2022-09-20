/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.edi.process.export.enqueue;

import com.google.common.collect.ImmutableList;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.edi.async.spi.impl.EDIWorkpackageProcessor;
import de.metas.esb.edi.model.I_EDI_Desadv;
import de.metas.esb.edi.model.X_EDI_Desadv;
import de.metas.process.PInstanceId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.processor.api.FailTrxItemExceptionHandler;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorExecutorService;
import org.adempiere.ad.trx.processor.spi.TrxItemProcessorAdapter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Service
public class DesadvEnqueuer
{
	private final IWorkPackageQueueFactory workPackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);
	private final ITrxItemProcessorExecutorService trxItemProcessorExecutorService = Services.get(ITrxItemProcessorExecutorService.class);

	@NonNull
	public EnqueueDesadvResult enqueue(@NonNull final EnqueueDesadvRequest enqueueDesadvRequest)
	{
		final IWorkPackageQueue queue = workPackageQueueFactory.getQueueForEnqueuing(enqueueDesadvRequest.getCtx(), EDIWorkpackageProcessor.class);

		final ImmutableList.Builder<I_EDI_Desadv> skippedDesadvCollector = ImmutableList.builder();

		trxItemProcessorExecutorService
				.<I_EDI_Desadv, Void>createExecutor()
				.setContext(enqueueDesadvRequest.getCtx(), ITrx.TRXNAME_None)
				.setExceptionHandler(FailTrxItemExceptionHandler.instance)
				.setProcessor(new TrxItemProcessorAdapter<I_EDI_Desadv, Void>()
				{
					@Override
					public void process(final I_EDI_Desadv desadv)
					{

						// make sure the desadvs that don't meet the sum percentage requirement won't get enqueued
						final BigDecimal currentSumPercentage = desadv.getFulfillmentPercent();
						if (currentSumPercentage.compareTo(desadv.getFulfillmentPercentMin()) < 0)
						{
							skippedDesadvCollector.add(desadv);
						}
						else
						{
							// note: in here, the desadv has the item processor's trxName (as of this task)
							enqueueDesadv(enqueueDesadvRequest.getPInstanceId(), queue, desadv);
						}
					}
				})
				.process(enqueueDesadvRequest.getDesadvIterator());

		return EnqueueDesadvResult.builder()
				.skippedDesadvList(skippedDesadvCollector.build())
				.build();
	}

	private void enqueueDesadv(
			@Nullable final PInstanceId pInstanceId,
			@NonNull final IWorkPackageQueue queue,
			@NonNull final I_EDI_Desadv desadv)
	{
		final String trxName = InterfaceWrapperHelper.getTrxName(desadv);

		queue
				.newWorkPackage()
				.setAD_PInstance_ID(pInstanceId)
				.bindToTrxName(trxName)
				.addElement(desadv)
				.buildAndEnqueue();

		desadv.setEDI_ExportStatus(X_EDI_Desadv.EDI_EXPORTSTATUS_Enqueued);
		InterfaceWrapperHelper.save(desadv);
	}
}
