package de.metas.handlingunits.allocation.transfer.impl;

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


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

import org.adempiere.model.IContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.collections.ListUtils;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHUTrxBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.IAllocationDestination;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.allocation.IHUContextProcessor;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.HUListAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.allocation.impl.IMutableAllocationResult;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;

/**
 * Tool used to take a given Qty from a given VHU and load it to each given TUs.
 *
 * @author tsa
 *
 */
public class HUDistributeBuilder
{
	// Services
	private final transient IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);
	private final transient IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);
	private final transient IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	// Parameters
	private IContextAware _context;
	private I_M_HU _vhuToDistribute;
	private Collection<I_M_HU> _tusToDistributeOn;
	private BigDecimal _qtyCUsPerTU;

	public final void distribute()
	{
		final IMutableHUContext huContextInitial = huContextFactory.createMutableHUContextForProcessing(getContext());
		huTrxBL.createHUContextProcessorExecutor(huContextInitial)
				.run(new IHUContextProcessor()
				{
					@Override
					public IMutableAllocationResult process(final IHUContext huContext0)
					{
						// Make a copy of the processing context, we will need to modify it
						final IMutableHUContext huContext = huContext0.copyAsMutable();

						// Register our split HUTrxListener because this "distribute" operation is similar with a split
						// More, this will allow our listeners to execute on-split operations (e.g. linking the newly created VHU to same document as the source VHU).
						huContext.getTrxListeners().addListener(HUSplitBuilderTrxListener.instance);

						// Execute the actual distribution
						distributeVHUToTUs(huContext);

						return NULL_RESULT; // we don't care
					}
				});
	}

	private final void distributeVHUToTUs(final IHUContext huContext)
	{
		//
		// Get the TUs on which we will distribute the VHU's qty
		final Collection<I_M_HU> tuHUs = getTUsToDistributeOn();

		//
		// Allocate given Qty on each TU (list allocation allocates only once)
		for (final I_M_HU tuHU : tuHUs)
		{
			distributeVHUToTU0(huContext, tuHU);
		}
	}

	private final void distributeVHUToTU0(final IHUContext huContext, final I_M_HU tuHU)
	{
		//
		// Get the VHU whom quantity we need to distribute
		final I_M_HU vhu = getVHUToDistribute();
		// If the VHU was already destroyed, there is nothing we can do
		if (handlingUnitsBL.isDestroyed(vhu))
		{
			return;
		}

		//
		// Get the quantity that we need to put in each TU
		final BigDecimal qtyCUsPerTU = getQtyCUsPerTU();

		//
		// Get VHU product storage
		final IHUStorageFactory huStorageFactory = huContext.getHUStorageFactory();
		final IHUStorage vhuHUStorage = huStorageFactory.getStorage(vhu);
		final List<IHUProductStorage> vhuProductStorages = vhuHUStorage.getProductStorages();
		final IHUProductStorage vhuProductStorage = ListUtils.singleElement(vhuProductStorages);
		if (vhuProductStorage.isEmpty())
		{
			handlingUnitsBL.destroyIfEmptyStorage(huContext, vhu);
			return;
		}

		//
		// Allocate ONLY with the INPUT QTY
		final I_M_Product cuProduct = vhuProductStorage.getM_Product();
		final I_C_UOM cuUOM = vhuProductStorage.getC_UOM();
		final Object cuTrxReferencedModel = null;
		final Timestamp date = SystemTime.asTimestamp();
		final IAllocationRequest allocationRequest = AllocationUtils.createQtyRequest(
				huContext,
				cuProduct, // Product
				qtyCUsPerTU, // Qty
				cuUOM, // UOM
				date, // Date
				cuTrxReferencedModel // Referenced model
				);

		//
		// Allocate FROM VHU
		final IAllocationSource source = new HUListAllocationSourceDestination(vhu);

		//
		// Allocate TO selected TUs
		final IAllocationDestination destination = new HUListAllocationSourceDestination(tuHU);

		//
		// Perform allocation
		final HULoader loader = new HULoader(source, destination);
		loader.setAllowPartialUnloads(true); // it's OK to have partial unloads (i.e. the VHU has not enough quantity)
		loader.setAllowPartialLoads(false);
		loader.setForceLoad(true); // always force the loading to TU
		loader.load(allocationRequest);

		//
		// Destroy VHU if empty
		handlingUnitsBL.destroyIfEmptyStorage(huContext, vhu);
	}

	public HUDistributeBuilder setContext(final IContextAware context)
	{
		_context = context;
		return this;
	}

	public IContextAware getContext()
	{
		Check.assumeNotNull(_context, "_context not null");
		return _context;
	}

	public HUDistributeBuilder setVHUToDistribute(final I_M_HU vhuToDistribute)
	{
		_vhuToDistribute = vhuToDistribute;
		return this;
	}

	private I_M_HU getVHUToDistribute()
	{
		Check.assumeNotNull(_vhuToDistribute, "_vhuToDistribute not null");
		return _vhuToDistribute;
	}

	public HUDistributeBuilder setTUsToDistributeOn(final Collection<I_M_HU> tusToDistributeOn)
	{
		_tusToDistributeOn = tusToDistributeOn;
		return this;
	}

	private Collection<I_M_HU> getTUsToDistributeOn()
	{
		Check.assumeNotEmpty(_tusToDistributeOn, "_tusToDistributeOn not empty");
		return _tusToDistributeOn;
	}

	public HUDistributeBuilder setQtyCUsPerTU(final BigDecimal qtyCUsPerTU)
	{
		_qtyCUsPerTU = qtyCUsPerTU;
		return this;
	}

	private BigDecimal getQtyCUsPerTU()
	{
		Check.assumeNotNull(_qtyCUsPerTU, "_qtyCUsPerTU not null");
		Check.assume(_qtyCUsPerTU.signum() > 0, "Qty CUs/TU > 0");
		return _qtyCUsPerTU;
	}
}
