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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import de.metas.common.util.time.SystemTime;
import org.compiere.model.I_C_UOM;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.IAllocationDestination;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.allocation.IHUContextProcessor;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.HUListAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.allocation.transfer.ITUMergeBuilder;
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;

public class TUMergeBuilder implements ITUMergeBuilder
{
	// services
	private final transient IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final transient IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);

	private final IHUContext huContextInitial;

	private List<I_M_HU> sourceHUs = new ArrayList<>();
	private I_M_HU targetHU;

	private ProductId cuProductId;
	private BigDecimal cuQty = Quantity.QTY_INFINITE;
	private I_C_UOM cuUOM;
	private Object cuTrxReferencedModel;

	public TUMergeBuilder(final IHUContext huContextInitial)
	{
		super();

		this.huContextInitial = huContextInitial;
	}

	@Override
	public TUMergeBuilder setSourceHUs(final List<I_M_HU> sourceHUs)
	{
		this.sourceHUs = sourceHUs;
		return this;
	}

	@Override
	public TUMergeBuilder setTargetTU(final I_M_HU targetHU)
	{
		this.targetHU = targetHU;
		return this;
	}

	@Override
	public TUMergeBuilder setCUProductId(final ProductId cuProductId)
	{
		this.cuProductId = cuProductId;
		return this;
	}

	@Override
	public TUMergeBuilder setCUQty(final BigDecimal qty)
	{
		cuQty = qty;
		return this;
	}

	@Override
	public TUMergeBuilder setCUUOM(final I_C_UOM uom)
	{
		cuUOM = uom;
		return this;
	}

	@Override
	public TUMergeBuilder setCUTrxReferencedModel(final Object trxReferencedModel)
	{
		cuTrxReferencedModel = trxReferencedModel;
		return this;
	}

	@Override
	public void mergeTUs()
	{
		huTrxBL.createHUContextProcessorExecutor(huContextInitial).run((IHUContextProcessor)huContext0 -> {
			// Make a copy of the processing context, we will need to modify it
			final IMutableHUContext huContext = huContext0.copyAsMutable();

			// Register our split HUTrxListener because this "merge" operation is similar with a split
			// More, this will allow our listeners to execute on-split operations (e.g. linking the newly created VHU to same document as the source VHU).
			huContext.getTrxListeners().addListener(HUSplitBuilderTrxListener.instance);

			mergeTUs0(huContext);
			return IHUContextProcessor.NULL_RESULT; // we don't care about the result
		});
	}

	private void mergeTUs0(final IHUContext huContext)
	{
		final IAllocationRequest allocationRequest = createMergeAllocationRequest(huContext);

		//
		// Source: Selected handling units
		final IAllocationSource source = HUListAllocationSourceDestination.of(sourceHUs);

		//
		// Destination: Handling unit we want to merge on
		final IAllocationDestination destination = HUListAllocationSourceDestination.of(targetHU);

		//
		// Perform allocation
		HULoader.of(source, destination)
				.setAllowPartialUnloads(true) // force allow partial unloads when merging
				.setAllowPartialLoads(true) // force allow partial loads when merging
				.load(allocationRequest); // execute it; we don't care about the result

		//
		// Destroy HUs which had their storage emptied
		for (final I_M_HU sourceHU : sourceHUs)
		{
			handlingUnitsBL.destroyIfEmptyStorage(huContext, sourceHU);
		}
	}

	/**
	 * Create an allocation request for the cuQty of the builder
	 *
	 * @param huContext
	 * @param referencedModel referenced model to be used in created request
	 * @return created request
	 */
	private IAllocationRequest createMergeAllocationRequest(final IHUContext huContext)
	{
		final ZonedDateTime date = SystemTime.asZonedDateTime();
		return AllocationUtils.createQtyRequest(
				huContext,
				cuProductId, // Product
				Quantity.of(cuQty, cuUOM), // quantity
				date, // Date
				cuTrxReferencedModel, // Referenced model
				false // force allocation
		);
	}
}
