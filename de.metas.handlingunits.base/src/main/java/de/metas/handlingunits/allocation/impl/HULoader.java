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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.uom.api.IUOMConversionContext;
import org.adempiere.uom.api.Quantity;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUTransaction;
import de.metas.handlingunits.IHUTransactionAttribute;
import de.metas.handlingunits.IHUTrxBL;
import de.metas.handlingunits.allocation.IAllocationDestination;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationRequestBuilder;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.allocation.IHUContextProcessor;
import de.metas.handlingunits.allocation.IHUContextProcessorExecutor;
import de.metas.handlingunits.attribute.IHUTransactionAttributeBuilder;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.attribute.strategy.IHUAttributeTransferRequest;
import de.metas.handlingunits.attribute.strategy.IHUAttributeTransferRequestBuilder;
import de.metas.handlingunits.attribute.strategy.impl.HUAttributeTransferRequestBuilder;
import de.metas.handlingunits.exceptions.HULoadException;
import de.metas.handlingunits.impl.HUTransaction;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;

public class HULoader
{
	private final IAllocationSource source;
	private final IAllocationDestination destination;

	private boolean allowPartialUnloads = false;
	private boolean allowPartialLoads = false;
	private boolean forceLoad = false;
	private boolean skipAttributesTransfer = false;

	/**
	 * The current executor. Will be <code>null</code> while no loading is taking place.
	 */
	private IHUContextProcessorExecutor currentInContextExecutor = null;

	//
	// Services that we use
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);
	private final transient IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);
	private final transient IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

	public HULoader(final IAllocationSource source, final IAllocationDestination destination)
	{
		super();
		// this.huContext = huContext;
		this.source = source;
		this.destination = destination;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	/**
	 *
	 * @return true true if partial unloads are allowed (i.e. source does not have all the requested qty)
	 */
	public boolean isAllowPartialUnloads()
	{
		return allowPartialUnloads;
	}

	/**
	 *
	 * @param allowPartialUnloads true if partial unloads are allowed (i.e. source does not have all the requested qty)
	 */
	public void setAllowPartialUnloads(final boolean allowPartialUnloads)
	{
		this.allowPartialUnloads = allowPartialUnloads;
	}

	/**
	 *
	 * @return true if partial loads are allowed (i.e. destination can not accept all qty that was unloaded from source)
	 */
	public boolean isAllowPartialLoads()
	{
		return allowPartialLoads;
	}

	/**
	 *
	 * @param allowPartialLoads true if partial loads are allowed (i.e. destination can not accept all qty that was unloaded from source)
	 */
	public void setAllowPartialLoads(final boolean allowPartialLoads)
	{
		this.allowPartialLoads = allowPartialLoads;
	}

	public void setForceLoad(final boolean forceLoad)
	{
		this.forceLoad = forceLoad;
	}

	public final boolean isForceLoad()
	{
		return forceLoad;
	}

	/**
	 * Transfer request qty from <code>source</code> to <code>destination</code>. If the <code>Qty</code> of the given <code>request</code> is zero, then the method does nothing and just return
	 * {@link AllocationUtils#nullResult()}.
	 *
	 * Note that transactions from result will be already processed.
	 *
	 * @param request
	 * @return result
	 */
	public IAllocationResult load(final IAllocationRequest request)
	{
		//
		// Check if our request is valid
		Check.assumeNotNull(request, "unloadRequest not null");
		if (request.getQty().signum() == 0)
		{
			// Zero Qty Request => nothing to do
			return AllocationUtils.nullResult();
		}

		final IHUContext huContextInitial = request.getHUContext();
		return processInHUContext(huContextInitial, new IHUContextProcessor()
		{
			@Override
			public IMutableAllocationResult process(final IHUContext huContext)
			{
				//
				// Create the new allocation request, identical with given one, but the concept is with given transaction
				final IAllocationRequest unloadRequestInLocalTrx = AllocationUtils.derive(request)
						.setHUContext(huContext)
						.create();

				return unloadSourceThenLoadDestination(unloadRequestInLocalTrx);
			}
		});
	}

	/**
	 * Execute given <code>processor</code> in HU context.
	 *
	 * Mainly this will take care of
	 * <ul>
	 * <li>managing database transaction</li>
	 * <li>collecting and automatically processing {@link IHUTransactionAttribute}s</li>
	 * <li>creating packing materials/empties movements if needed (see {@link IHUContext#getHUPackingMaterialsCollector()})</li>
	 * </ul>
	 *
	 * @param huContext
	 * @param processor
	 * @return
	 */
	private final IAllocationResult processInHUContext(final IHUContext huContext, final IHUContextProcessor processor)
	{
		currentInContextExecutor = huTrxBL.createHUContextProcessorExecutor(huContext);
		try
		{
			return currentInContextExecutor.run(processor);
		}
		finally
		{
			currentInContextExecutor = null;
		}
	}

	private IHUTransactionAttributeBuilder getTrxAttributesBuilder()
	{
		Check.assumeNotNull(currentInContextExecutor, "currentInContextExecutor not null");
		return currentInContextExecutor.getTrxAttributesBuilder();
	}

	/**
	 * Makes sure we are running in a valid processing context. i.e.
	 * <ul>
	 * <li>a database transaction was already setup
	 * <li>attribute transactions collector was already setup
	 * </ul>
	 *
	 * @param huContext
	 */
	private final void assertValidProcessingContext(final IHUContext huContext)
	{
		Check.assumeNotNull(huContext, "huContext not null");
		Check.assume(!trxManager.isNull(huContext.getTrxName()),
				"HU Context shall not have null transaction: {}", huContext);

		final IHUTransactionAttributeBuilder trxAttributesBuilder = getTrxAttributesBuilder();
		Check.assumeNotNull(trxAttributesBuilder, "Attribute Transactions Collector shall be configured at this point");
	}

	/**
	 * Transfer request qty from <code>source</code> to <code>destination</code>.
	 * <p>
	 * background-info: depending on the used {@link IAllocationSource}, everything the request's full qty might be unloaded from the source (i.e. {@link IHUTransaction}s are created).<br>
	 * If not all from the given {@code unloadRequest} is unloaded, then the it will only try to load the lesser qtys which were unloaded.<br>
	 * However, in the end, only the stuff that will be "accepted" by the {@link IAllocationDestination} will really be moved.<br>
	 *
	 * NOTEs:
	 * <ul>
	 * <li>that transactions from result will be already processed.
	 * <li>context's trxName will be used, no transaction management will be performed (see {@link #load(IAllocationRequest)} which is doing the magic)
	 * <li>this method assumes we are running in a valid processing context (see {@link #assertValidProcessingContext(IHUContext)})
	 * </ul>
	 *
	 * @param unloadRequest
	 * @return result (already processed)
	 */
	private final IMutableAllocationResult unloadSourceThenLoadDestination(final IAllocationRequest unloadRequest)
	{
		//
		// HU Context to use
		final IHUContext huContext = unloadRequest.getHUContext();
		assertValidProcessingContext(huContext);

		//
		// Unload requested qty from "source"
		final IAllocationResult unloadResult = source.unload(unloadRequest);
		final IAllocationRequest unloadRequestActual;
		if (unloadResult.isCompleted())
		{
			unloadRequestActual = unloadRequest;
		}
		else
		{
			if (!isAllowPartialUnloads())
			{
				throw new AdempiereException("Cannot fully unload all qty for " + unloadRequest + "."
						+ "\nSource: " + source
						+ "\nUnload result: " + unloadResult);
			}

			//
			// If it was a partial unload, we need to adjust the unloadRequestActual with QtyAllocated (i.e. how much was actually unloaded)
			unloadRequestActual = AllocationUtils.createQtyRequest(unloadRequest, unloadResult.getQtyAllocated());
		}

		//
		// Load to destination what we unloaded from source
		final IMutableAllocationResult finalResult = loadToDestination(huContext, unloadResult, unloadRequestActual);

		//
		// Notify listeners that load was completed
		huContext.getTrxListeners().afterLoad(huContext, Collections.singletonList(finalResult));

		//
		// Notify our source that the unload was completed
		source.unloadComplete(huContext);

		return finalResult;
	}

	/**
	 * Loads from <code>unloadResult</code> to our destination. It does so by iterating the unloadResults {@link IHUTransaction}s trx candidates.
	 * For each trx candidates, the code will attempts to loadto the {@link IAllocationDestination}.
	 * After those trx candidates are iterated and loading was attempted, the <b>actual</b> unload {@link IHUTransaction}s will be created based of what was actually loaded to the destination.
	 *
	 * After running this method:
	 * <ul>
	 * <li>resulting unload/load transactions will be already processed
	 * <li>attribute transactions will be also already processed
	 * </ul>
	 *
	 * NOTEs:
	 * <ul>
	 * <li>this method assumes we are running in a valid processing context (see {@link #assertValidProcessingContext(IHUContext)})
	 * </ul>
	 *
	 * @param huContext
	 * @param unloadResult unload result to be loaded (see {@link IAllocationSource#unload(IAllocationRequest)})
	 * @param unloadRequestActual actual unloadRequest that was issued on source in order to get the unloadResult; we are using it only to propagate context values from it to requests that will be
	 *            created to load on destination
	 * @return load result (will contain also unload transactions); the result will be already processed
	 */
	private IMutableAllocationResult loadToDestination(final IHUContext huContext,
			final IAllocationResult unloadResult,
			final IAllocationRequest unloadRequestActual)
	{
		assertValidProcessingContext(huContext);

		//
		// Create final result
		// We will initialize with QtyToAllocate = allocated Qty from unload Result
		// Later we will merge into this partial results that we get while we load the Qty
		final IMutableAllocationResult finalResult = AllocationUtils.createMutableAllocationResult(unloadResult.getQtyAllocated());

		//
		// Iterate each unload transaction from unloadResult and try to load it to "destination"
		for (final IHUTransaction unloadTrx : unloadResult.getTransactions())
		{
			final IAllocationRequest loadRequest = createQtyLoadRequest(unloadRequestActual, unloadTrx);

			final IAllocationResult loadResult = destination.load(loadRequest);
			final IAllocationRequest loadRequestActual;
			if (loadResult.isCompleted())
			{
				loadRequestActual = loadRequest;
			}
			else
			{
				if (!isAllowPartialLoads())
				{
					final String errmsg = "Cannot fully load " + loadRequest + "."
							+ "\nUnload request actual: " + unloadRequestActual
							+ "\nLast result was: " + loadResult
							+ "\nDestination: " + destination;
					throw new HULoadException(errmsg, unloadRequestActual, loadResult);
				}

				//
				// If it was a partial load (i.e. unloaded qty does not fit into destination)
				// we need to create the loadRequestActual using only the qty that was allocated
				loadRequestActual = AllocationUtils.createQtyRequest(loadRequest, loadResult.getQtyAllocated());

				//
				// Notify "source" that we will cancel the unload for Qty that could not be loaded
				// NOTE: this is not needed because "source" is not doing actual changes, that's why we process transactions after each round (see below)
				// source.unloadCancel(unloadResult, unloadTrx, loadResult.getQtyToAllocate());
			}

			final List<IHUTransaction> trxs = new ArrayList<IHUTransaction>();

			//
			// Iterate each load transaction:
			// * create it's counterpart unload transaction (taking properties from unloadTrx)
			// * transfer attributes
			// also now aggregate the IHUTransactions to avoid UC problems with receipt schedule allocations and others that are created per trx-candidate
			final List<IHUTransaction> aggregatedLoadTransactions = huTrxBL.aggregateTransactions(loadResult.getTransactions());

			BigDecimal qtyUnloaded = BigDecimal.ZERO;
			for (final IHUTransaction loadTrx : aggregatedLoadTransactions)
			{
				final IHUTransaction unloadTrxPartial = createPartialUnloadTransaction(unloadTrx, loadTrx);
				unloadTrxPartial.pair(loadTrx);

				// Transfer attributes if allowed
				if (!isSkipAttributesTransfer())
				{
					transferAttributes(huContext, unloadTrxPartial, loadTrx, qtyUnloaded); // qtyUnloaded will be assumed before actual unload so that we know to decrease from inside the HUStorage
				}

				// Notify the listeners that we performed an unload/load transaction
				huContext.getTrxListeners().onUnloadLoadTransaction(huContext, unloadTrxPartial, loadTrx);

				trxs.add(unloadTrxPartial);
				trxs.add(loadTrx);

				final BigDecimal unloadTrxPartial_Qty = unloadTrxPartial.getQuantity().getQty().negate();
				qtyUnloaded = qtyUnloaded.add(unloadTrxPartial_Qty);
			}

			//
			// At the end of load transaction iteration
			// the qtyUnloaded shall be the same with unload Qty from initial unload transaction
			// NOTE: mainly this is an development error (internal error which shall not happen)
			if (qtyUnloaded.compareTo(loadRequestActual.getQty()) != 0)
			{
				throw new AdempiereException("Qty unloaded shall be equal unloadTrx qty."
						+ "\n QtyUnloaded: " + qtyUnloaded
						+ "\n Actual Load request Qty: " + loadRequestActual.getQty()
						+ "\n unloadTrx: " + unloadTrx
						+ "\n Transactions: " + trxs);
			}

			//
			// Create and process transactions after each round of unload->load allocation.
			// If we are not doing like this, on next round, the storage will not consider previous allocations that were performed
			// TODO: this is subject to change - see http://dewiki908/mediawiki/index.php/06154_Packtischdialog_Performance_with_many_HU_%28109788330471%29
			final IHUTransactionAttributeBuilder trxAttributesBuilder = getTrxAttributesBuilder();
			final List<IHUTransactionAttribute> attributeTrxs = trxAttributesBuilder.getAndClearTransactions();
			final IAllocationResult result = AllocationUtils.createQtyAllocationResult(
					loadResult.getQtyToAllocate(),   // qtyToAllocate
					loadResult.getQtyAllocated(),   // qtyAllocated
					trxs, //
					attributeTrxs // attribute transactions
			);

			huTrxBL.createTrx(huContext, result);

			//
			// Merge result back to our final result (which will be returned at the end)
			AllocationUtils.mergeAllocationResult(finalResult, result);
		}

		// NOTE: if there some remaining Attribute transactions, it will processed later on caller

		destination.loadComplete(huContext);

		return finalResult;
	}

	private final IAllocationRequest createQtyLoadRequest(final IAllocationRequest unloadRequestActual, final IHUTransaction unloadTrx)
	{
		final IAllocationRequestBuilder builder = AllocationUtils.createQtyLoadRequestBuilder(unloadRequestActual, unloadTrx);
		if (isForceLoad())
		{
			builder.setForceQtyAllocation(true);
		}

		return builder.create();
	}

	// static
	private IHUTransaction createPartialUnloadTransaction(final IHUTransaction unloadTrx, final IHUTransaction loadTrx)
	{
		final I_M_Product unloadTrx_Product = unloadTrx.getProduct();
		final Quantity qtyUnloadFull = unloadTrx.getQuantity();

		final IUOMConversionContext uomConversionCtx = uomConversionBL.createConversionContext(unloadTrx_Product);

		final Quantity qtyUnloadPartial = loadTrx
				.getQuantity()
				.negate()
				.convertTo(uomConversionCtx, qtyUnloadFull.getUOM());

		return new HUTransaction(
				unloadTrx.getReferencedModel(),
				unloadTrx.getM_HU_Item(),   // HU Item
				unloadTrx.getVHU_Item(),   // VHU Item
				unloadTrx_Product,
				qtyUnloadPartial,
				unloadTrx.getDate());
	}

	// static
	private IAttributeStorage getAttributeStorageOrNull(final IHUTransactionAttributeBuilder attributeStorageBuilder, final IHUTransaction trx, final boolean useVHU)
	{
		final IAttributeStorageFactory attributeStorageFactory = attributeStorageBuilder.getAttributeStorageFactory();

		//
		// If transaction has an HU set, that we use HU Storage
		final I_M_HU hu = getM_HU(trx, useVHU);
		if (hu != null)
		{
			final IAttributeStorage huAttributeStorage = attributeStorageFactory.getAttributeStorage(hu); // createNew=true
			return huAttributeStorage;
		}

		//
		// If transaction has a Referenced Model set, try using that
		final Object referencedModel = trx.getReferencedModel();
		if (referencedModel != null)
		{
			final IAttributeStorage huAttributeSet = attributeStorageFactory.getAttributeStorageIfHandled(referencedModel); // createNew=true
			if (huAttributeSet != null)
			{
				return huAttributeSet;
			}
		}

		//
		// There is no AttributeSet that can be fetched from given transaction
		return null;
	}

	private I_M_HU getM_HU(final IHUTransaction trx, final boolean useVHU)
	{
		final I_M_HU hu;
		if (useVHU)
		{
			final I_M_HU_Item vhuItem = trx.getVHU_Item();
			hu = vhuItem == null ? null : vhuItem.getM_HU();
		}
		else
		{
			hu = trx.getM_HU();
		}

		return hu;
	}

	private void transferAttributes(final IHUContext huContext, final IHUTransaction trxFrom, final IHUTransaction trxTo, final BigDecimal qtyUnloaded)
	{
		transferAttributes(huContext, trxFrom, trxTo, qtyUnloaded, false); // useVHU=false
		transferAttributes(huContext, trxFrom, trxTo, qtyUnloaded, true); // useVHU=true
	}

	private void transferAttributes(final IHUContext huContext, final IHUTransaction trxFrom, final IHUTransaction trxTo, final BigDecimal qtyUnloaded, final boolean useVHU)
	{
		// FIXME: handle the case when only one Trx has attributes, but we need to create the attributes there because we just created the HU (for example)

		final IHUTransactionAttributeBuilder trxAttributesBuilder = getTrxAttributesBuilder();

		final IAttributeStorage attributeStorageFrom = getAttributeStorageOrNull(trxAttributesBuilder, trxFrom, useVHU);
		if (attributeStorageFrom == null)
		{
			return;
		}

		final IAttributeStorage attributeStorageTo = getAttributeStorageOrNull(trxAttributesBuilder, trxTo, useVHU);
		if (attributeStorageTo == null)
		{
			return;
		}

		final IHUAttributeTransferRequestBuilder requestBuilder = new HUAttributeTransferRequestBuilder(huContext)
				.setProduct(trxTo.getProduct())
				.setQuantity(trxTo.getQuantity())
				.setAttributeStorageFrom(attributeStorageFrom)
				.setAttributeStorageTo(attributeStorageTo)
				.setVHUTransfer(useVHU);

		final IHUStorageFactory storageFactory = huContext.getHUStorageFactory();
		final I_M_HU huFrom = getM_HU(trxFrom, useVHU);
		if (huFrom != null)
		{
			final IHUStorage huStorageFrom = storageFactory.getStorage(huFrom);
			requestBuilder.setHUStorageFrom(huStorageFrom);
		}

		final I_M_HU huTo = getM_HU(trxTo, useVHU);
		if (huTo != null)
		{
			final IHUStorage huStorageTo = storageFactory.getStorage(huTo);
			requestBuilder.setHUStorageTo(huStorageTo);
		}

		requestBuilder.setQtyUnloaded(qtyUnloaded);

		final IHUAttributeTransferRequest request = requestBuilder.create();
		trxAttributesBuilder.transferAttributes(request);
	}

	public void unloadAllFromSource(final IHUContext huContext)
	{
		processInHUContext(huContext, new IHUContextProcessor()
		{
			@Override
			public IMutableAllocationResult process(final IHUContext huContext)
			{
				final List<IAllocationResult> loadResults = new ArrayList<>();

				for (final IPair<IAllocationRequest, IAllocationResult> unloadPair : source.unloadAll(huContext))
				{
					final IAllocationRequest unloadRequest = AllocationUtils.derive(unloadPair.getLeft())
							.setForceQtyAllocation(true) // we need to force pushing everything
							.create();

					final IAllocationResult unloadResult = unloadPair.getRight();
					final IMutableAllocationResult loadResult = loadToDestination(huContext, unloadResult, unloadRequest);

					loadResults.add(loadResult);
				}

				//
				// Notify listeners that load was completed
				huContext.getTrxListeners().afterLoad(huContext, loadResults);

				//
				// Notify the source that our unload was completed
				source.unloadComplete(huContext);

				// NOTE: we are returning null because we actually can transfer more then one type of product and our IAllocationResult is product oriented
				return null;
			}
		});
	}

	/**
	 * Advises the {@link HULoader} to skip transferring the attributes.
	 * 
	 * @param skipAttributesTransfer true if the loader shall NOT transfer the attributes
	 */
	public void setSkipAttributesTransfer(final boolean skipAttributesTransfer)
	{
		this.skipAttributesTransfer = skipAttributesTransfer;
	}

	/**
	 * @return true if attributes shall NOT be transferred
	 */
	public boolean isSkipAttributesTransfer()
	{
		return skipAttributesTransfer;
	}
}
