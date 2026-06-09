package de.metas.handlingunits.allocation.impl;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.allocation.IHUContextProcessor;
import de.metas.handlingunits.allocation.IHUContextProcessorExecutor;
import de.metas.handlingunits.attribute.IHUTransactionAttributeBuilder;
import de.metas.handlingunits.attribute.impl.HUTransactionAttributeBuilder;
import de.metas.handlingunits.empties.IHUEmptiesService;
import de.metas.handlingunits.spi.impl.HUPackingMaterialDocumentLineCandidate;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.TrxCallable;

import java.util.List;

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
	 * <p>
	 * This field will be set when we start a processing and it will be cleared after.
	 */
	private IHUTransactionAttributeBuilder trxAttributesBuilder;

	public HUContextProcessorExecutor(@NonNull final IHUContext huContext)
	{
		huContextInitial = huContext;
	}

	@Override
	public IHUTransactionAttributeBuilder getTrxAttributesBuilder()
	{
		return Check.assumeNotNull(trxAttributesBuilder, "trxAttributesBuilder not null");
	}

	@Override
	public IMutableAllocationResult run(@NonNull final IHUContextProcessor processor)
	{
		final String trxName = huContextInitial.getTrxName();
		return trxManager.call(trxName, new TrxCallable<IMutableAllocationResult>()
		{
			// Note: If trxName from initial request is not null, ITrxManager.call() won't commit in case of success!

			/**
			 * True if our processing was a success
			 */
			private boolean success = false;

			@Override
			public IMutableAllocationResult call()
			{
				//
				// Create a context identical like the one we got, but using our local transaction name
				final IHUContext huContextInLocalTrx = huContextFactory.deriveWithTrxName(huContextInitial, ITrx.TRXNAME_ThreadInherited);

				//
				// Create HU Attribute transactions collector and register it to Attribute Storage
				// TODO: for now, we are storing the transaction collector in a class field, but in future it shall be wrapped somewhere in HUContext
				trxAttributesBuilder = new HUTransactionAttributeBuilder(huContextInLocalTrx);

				//
				// Do the actual processing
				final IMutableAllocationResult result = processor.process(huContextInLocalTrx);

				//
				// If there are remaining attribute transactions, add them in a new transaction and process it
				{
					final IAllocationResult attributeTrxsResult = trxAttributesBuilder.createAndProcessAllocationResult();

					//
					// Merge result back to our final result (which will be returned at the end)
					// NOTE: it could be that the result is null because we don't care about it
					if (result != null)
					{
						AllocationUtils.mergeAllocationResult(result, attributeTrxsResult);
					}
				}

				//
				// Create packing material movements (if needed and required)
				final List<HUPackingMaterialDocumentLineCandidate> candidates = huContextInLocalTrx
						.getHUPackingMaterialsCollector()
						.getAndClearCandidates();
				if (!candidates.isEmpty())
				{
					huEmptiesService.newEmptiesMovementProducer()
							.setEmptiesMovementDirectionAuto()
							.addCandidates(candidates)
							.createMovements();
				}
				//
				// If we reach this point it was a success
				success = true;
				return result;
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
	}
}
