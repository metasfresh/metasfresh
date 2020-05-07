package de.metas.handlingunits.allocation.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.TrxRunnable2;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.allocation.IHUContextProcessor;
import de.metas.handlingunits.allocation.IHUContextProcessorExecutor;
import de.metas.handlingunits.attribute.IHUTransactionAttributeBuilder;
import de.metas.handlingunits.attribute.impl.HUTransactionAttributeBuilder;
import de.metas.handlingunits.empties.IHUEmptiesService;
import lombok.NonNull;

public class HUContextProcessorExecutor implements IHUContextProcessorExecutor
{
	//
	// Services that we use
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);
	private final transient IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);
	private final transient IHUEmptiesService huEmptiesService = Services.get(IHUEmptiesService.class);

	private final IHUContext huContextInitial;

	/**
	 * Holds Attribute Transactions Collector while we are processing.
	 *
	 * This field will be set when we start a processing and it will be cleared after.
	 */
	private IHUTransactionAttributeBuilder trxAttributesBuilder;

	private boolean automaticallyMovePackingMaterials = true;

	public HUContextProcessorExecutor(final IHUContext huContext)
	{
		super();
		Check.assumeNotNull(huContext, "huContext not null");
		huContextInitial = huContext;
	}

	@Override
	public IHUTransactionAttributeBuilder getTrxAttributesBuilder()
	{
		Check.assumeNotNull(trxAttributesBuilder, "trxAttributesBuilder not null");
		return trxAttributesBuilder;
	}

	@Override
	public IMutableAllocationResult run(@NonNull final IHUContextProcessor processor)
	{
		final IMutableAllocationResult[] result = new IMutableAllocationResult[] { null };

		final String trxName = huContextInitial.getTrxName();
		trxManager.run(trxName, new TrxRunnable2()
		{
			// Note: If trxName from initial request is not null, ITrxManager.run() won't commit in case of success!

			/**
			 * True if our processing was a success
			 */
			private boolean success = false;

			@Override
			public void run(final String localTrxName) throws Exception
			{
				//
				// Create a context identical like the one we got, but using our local transaction name
				final IHUContext huContextInLocalTrx = huContextFactory.deriveWithTrxName(huContextInitial, localTrxName);

				//
				// Create HU Attribute transactions collector and register it to Attribute Storage
				// TODO: for now, we are storing the transaction collector in a class field, but in future it shall be wrapped somewhere in HUContext
				trxAttributesBuilder = new HUTransactionAttributeBuilder(huContextInLocalTrx);

				//
				// Do the actual processing
				result[0] = processor.process(huContextInLocalTrx);

				//
				// If there are remaining attribute transactions, add them in a new transaction and process it
				{
					final IAllocationResult attributeTrxsResult = trxAttributesBuilder.createAndProcessAllocationResult();

					//
					// Merge result back to our final result (which will be returned at the end)
					// NOTE: it could be that the result is null because we don't care about it
					if (result[0] != null)
					{
						AllocationUtils.mergeAllocationResult(result[0], attributeTrxsResult);
					}
				}

				//
				// Create packing material movements (if needed and required)
				if(isAutomaticallyMovePackingMaterials())
				{
					huEmptiesService.newEmptiesMovementProducer()
							.setEmptiesMovementDirectionAuto()
							.addCandidates(huContextInLocalTrx.getHUPackingMaterialsCollector().getAndClearCandidates())
							.createMovements();
				}

				//
				// If we reach this point it was a success
				success = true;
			}

			@Override
			public boolean doCatch(final Throwable e) throws Throwable
			{
				success = false;

				// Just pass it through
				throw e;
			}

			@Override
			public void doFinally()
			{
				//
				// NOTE: don't do any kind of processing here because we are running out-of-transaction
				//

				//
				// Dispose the attribute transactiosn builder/listeners
				if (trxAttributesBuilder != null)
				{
					// If there was a failure, discard all collected transactions first
					if (!success)
					{
						trxAttributesBuilder.clearTransactions();
					}

					// We are disposing (i.e. unregistering all underlying listeners) of this builder,
					// no matter if this processing was a success or not because,
					// there nothing we can do anyway with this builder
					trxAttributesBuilder.dispose();
					trxAttributesBuilder = null;
				}
			}
		});

		return result[0];
	}

	@Override
	public HUContextProcessorExecutor setAutomaticallyMovePackingMaterials(final boolean automaticallyMovePackingMaterials)
	{
		this.automaticallyMovePackingMaterials = automaticallyMovePackingMaterials;
		return this;
	}

	private boolean isAutomaticallyMovePackingMaterials()
	{
		return automaticallyMovePackingMaterials;
	}
}
