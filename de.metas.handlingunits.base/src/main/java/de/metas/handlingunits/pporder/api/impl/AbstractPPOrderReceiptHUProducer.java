package de.metas.handlingunits.pporder.api.impl;

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


import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.allocation.IHUProducerAllocationDestination;
import de.metas.handlingunits.allocation.ILUTUConfigurationFactory;
import de.metas.handlingunits.allocation.ILUTUProducerAllocationDestination;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.impl.IDocumentLUTUConfigurationManager;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.handlingunits.pporder.api.IPPOrderReceiptHUProducer;

/* package */abstract class AbstractPPOrderReceiptHUProducer implements IPPOrderReceiptHUProducer
{
	// Services
	protected final transient IHUPPOrderBL huPPOrderBL = Services.get(IHUPPOrderBL.class);
	protected final ITrxManager trxManager = Services.get(ITrxManager.class);

	private final Object contextProvider;

	public AbstractPPOrderReceiptHUProducer(final Object contextProvider)
	{
		super();

		Check.assumeNotNull(contextProvider, "contextProvider not null");
		this.contextProvider = contextProvider;
	}

	protected abstract IAllocationSource createAllocationSource();

	protected abstract IDocumentLUTUConfigurationManager createReceiptLUTUConfigurationManager();

	protected abstract IAllocationRequest createAllocationRequest(final IHUContext huContext);

	protected abstract void setAssignedHUs(final Collection<I_M_HU> hus, final String trxName);

	protected final Object getContextProvider()
	{
		return contextProvider;
	}

	private final IHUProducerAllocationDestination createAllocationDestination()
	{
		//
		// LU/TU COnfiguration
		final I_M_HU_LUTU_Configuration lutuConfiguration = createReceiptLUTUConfigurationManager()
				.startEditing() // start editing just to make sure the LU/TU configuration is created if does not already exist
				.pushBackToModel()
				.getLUTUConfiguration();

		//
		// Create LU/TU Producer
		final ILUTUConfigurationFactory lutuConfigurationFactory = Services.get(ILUTUConfigurationFactory.class);
		final ILUTUProducerAllocationDestination lutuProducer = lutuConfigurationFactory.createLUTUProducerAllocationDestination(lutuConfiguration);

		return lutuProducer;
	}

	@Override
	public final List<I_M_HU> createHUs()
	{
		// Keep the functionality with no contextProvider or trxName given as parameters
		final Object contextProviderObj = null;
		final String trxName = ITrx.TRXNAME_None;
		return createHUs(contextProviderObj, trxName);
	}

	@Override
	public final List<I_M_HU> createHUs(final Object contextProviderObj, final String trxName)
	{
		// Services
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		//
		// Create HU Context
		final Object contextProvider;
		if (contextProviderObj == null)
		{
			contextProvider = getContextProvider();
		}
		else
		{
			contextProvider = contextProviderObj;
		}

		final IMutableHUContext huContext;

		if (trxManager.isNull(trxName))
		{
			final IContextAware contextAwareModel = InterfaceWrapperHelper.getContextAware(contextProvider);
			huContext = handlingUnitsBL.createMutableHUContext(contextAwareModel);
		}

		else
		{
			// the HU context must be created in the given transaction
			final Properties ctx = InterfaceWrapperHelper.getCtx(contextProvider);
			huContext = handlingUnitsBL.createMutableHUContext(ctx, trxName);
		}

		trxManager.assertTrxNotNull(huContext);

		//
		// Create Allocation Source
		final IAllocationSource ppOrderAllocationSource = createAllocationSource();

		//
		// Create Allocation Destination
		final IHUProducerAllocationDestination huProducerDestination = createAllocationDestination();
		// 08077
		// this is where the initial HU status comes from when they are created
		// make sure it is Planning
		huProducerDestination.setHUStatus(X_M_HU.HUSTATUS_Planning);

		//
		// Create Allocation Request
		final IAllocationRequest allocationRequest = createAllocationRequest(huContext);

		//
		// Execute transfer
		final HULoader loader = new HULoader(ppOrderAllocationSource, huProducerDestination);
		loader.setAllowPartialUnloads(false);
		loader.setAllowPartialLoads(false);
		final IAllocationResult allocationResult = loader.load(allocationRequest);
		Check.assume(allocationResult.isCompleted(), "Result shall be completed: {}", allocationResult);

		//
		// Assign created HUs to Receipt Cost Collector
		final List<I_M_HU> createdHUs = huProducerDestination.getCreatedHUs();

		setAssignedHUs(createdHUs, trxName);

		//
		// Return created HUs
		return createdHUs;
	}

}
