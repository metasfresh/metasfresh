package de.metas.handlingunits.attribute.impl;

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
import java.util.Collections;
import java.util.List;

import org.compiere.model.I_M_Attribute;
import org.slf4j.Logger;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.HUContextProcessorExecutor;
import de.metas.handlingunits.attribute.IHUTransactionAttributeBuilder;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.attribute.strategy.IHUAttributeTransferRequest;
import de.metas.handlingunits.attribute.strategy.IHUAttributeTransferStrategy;
import de.metas.handlingunits.hutransaction.IHUTransactionAttribute;
import de.metas.handlingunits.hutransaction.IHUTransactionCandidate;
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * Standard {@link IHUTransactionAttributeBuilder} implementation.
 *
 * <br/>
 * <br/>
 * <b>WARNING: To be used internally by {@link HUContextProcessorExecutor} only. DO NOT use it directly.</b>
 *
 * @author tsa
 *
 */
public class HUTransactionAttributeBuilder implements IHUTransactionAttributeBuilder
{
	private static final transient Logger logger = LogManager.getLogger(HUTransactionAttributeBuilder.class);

	private final IHUContext huContext;
	private final HUTrxAttributesCollector trxAttributesCollector;
	private final IAttributeStorageFactory attributeStorageFactory;

	public HUTransactionAttributeBuilder(@NonNull final IHUContext huContext)
	{
		this.huContext = huContext;

		//
		// Setup and register HUTrxAttributesCollector to current storage
		trxAttributesCollector = HUTrxAttributesCollector.newInstance();

		//
		// Registers the collector as a listener to current attribute storages.
		// NOTE: it will be unregistered on dispose().
		attributeStorageFactory = huContext.getHUAttributeStorageFactory();
		attributeStorageFactory.addAttributeStorageListener(trxAttributesCollector);
	}

	@Override
	public IAttributeStorageFactory getAttributeStorageFactory()
	{
		return attributeStorageFactory;
	}

	@Override
	public List<IHUTransactionAttribute> getAndClearTransactions()
	{
		return trxAttributesCollector.getAndClearTransactions();
	}

	@Override
	public boolean hasTransactions()
	{
		if (trxAttributesCollector == null)
		{
			return false;
		}
		return !trxAttributesCollector.isEmpty();
	}

	@Override
	public void clearTransactions()
	{
		if (trxAttributesCollector != null)
		{
			trxAttributesCollector.clearTransactions();
		}
	}

	@Override
	public void transferAttributes(@NonNull final IHUAttributeTransferRequest request)
	{
		logger.trace("Transfering attributes for {}", request);

		final IAttributeStorage attributesFrom = request.getAttributesFrom();
		final IAttributeStorage attributesTo = request.getAttributesTo();

		for (final I_M_Attribute attribute : attributesFrom.getAttributes())
		{
			//
			// If "toAttributes" does not support our attribute then skip it
			if (!attributesTo.hasAttribute(attribute))
			{
				logger.trace("Skip transfering attribute {} because target storage does not have it", attribute);
				continue;
			}

			final IHUAttributeTransferStrategy transferFromStrategy = getHUAttributeTransferStrategy(request, attribute);

			//
			// Only transfer value if the request allows it
			if (!transferFromStrategy.isTransferable(request, attribute))
			{
				logger.trace("Skip transfering attribute {} because transfer strategy says so: {}", attribute, transferFromStrategy);
				continue;
			}

			transferFromStrategy.transferAttribute(request, attribute);
			logger.trace("Attribute {} was transfered to target storage", attribute);
		}
	}

	private IHUAttributeTransferStrategy getHUAttributeTransferStrategy(final IHUAttributeTransferRequest request, final I_M_Attribute attribute)
	{
		final IAttributeStorage attributesFrom = request.getAttributesFrom();
		return attributesFrom.retrieveTransferStrategy(attribute);
	}

	@Override
	public IAllocationResult createAllocationResult()
	{
		final List<IHUTransactionAttribute> attributeTrxs = getAndClearTransactions();
		if (attributeTrxs.isEmpty())
		{
			// no transactions, nothing to do
			return AllocationUtils.nullResult();
		}

		return AllocationUtils.createQtyAllocationResult(
				BigDecimal.ZERO, // qtyToAllocate
				BigDecimal.ZERO, // qtyAllocated
				Collections.emptyList(), // trxs
				attributeTrxs // attribute transactions
		);
	}

	@Override
	public IAllocationResult createAndProcessAllocationResult()
	{
		final IAllocationResult result = createAllocationResult();
		Services.get(IHUTrxBL.class).createTrx(huContext, result);

		return result;
	}

	@Override
	public void dispose()
	{
		// Unregister the listener/collector
		if (attributeStorageFactory != null && trxAttributesCollector != null)
		{
			trxAttributesCollector.dispose();
			attributeStorageFactory.removeAttributeStorageListener(trxAttributesCollector);
		}
	}
}
